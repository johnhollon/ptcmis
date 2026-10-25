/*
 * Copyright (C) 2026 陈慧然
 * (aka: Huiran Chen, chenhuiran, John Hollon, johnhollon)
 * ORCID: https://orcid.org/0009-0004-5899-6928
 * <chenhuiran@chenhuiran.com>
 * <johnhollon@johnhollon.com>
 *
 * This file is part of PTCMIS.
 *
 * PTCMIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PTCMIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

import {
  Component,
  computed,
  effect,
  EventEmitter,
  inject,
  OnDestroy,
  Output,
  signal,
} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { CdkFixedSizeVirtualScroll, CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { ServiceQueryBottle } from '../viewer/service';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource,
} from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { PipeBottleStorageStatus } from '../../../components/00200-pipes/pipe-bottle-storage-status';
import { PipeStorageReason } from '../../../components/00200-pipes/pipe-storage-reason';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { Bottle } from '../../../entities/models/Bottle';
import { Subject } from 'rxjs';
import { MatIcon } from '@angular/material/icon';

export interface DataNode {
  id: string;
  level: number;
  expandable: boolean;
  data: Bottle;
  children: DataNode[];
}

@Component({
  standalone: true,
  selector: 'children-query-bottle',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [
    ReactiveFormsModule,
    MatButton,
    CdkVirtualScrollViewport,
    CdkFixedSizeVirtualScroll,
    LayoutDrawerOppSimple,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable,
    MatTooltip,
    PipeBottleStorageStatus,
    PipeStorageReason,
    PipeTimestamp,
    MatIcon,
    MatHeaderCellDef,
  ],
})
export class CompChildren implements OnDestroy {
  @Output() onCancel = new EventEmitter<void>();
  columns: string[] = [
    'id',
    'variety',
    'varietyCode',
    'explantCollectionBatch',
    'generation',
    'plantCount',
    'sourceType',
    'taskTime',
    'discarded',
    'currentStorageStatus',
    'lastOperatorName',
    'idParent',
  ];
  private dispose$ = new Subject<void>();
  protected service = inject(ServiceQueryBottle);
  protected trackById = (_index: number, row: DataNode) => row.data.id;
  protected detailsDataSource = new MatTableDataSource<DataNode>([]);

  readonly #dataSig = computed(() => {
    return this.calculateTreeData(this.service.childrenSig());
  });

  private readonly effectData = effect(() => {
    if (this.service.childrenSig() !== undefined) this.#expandedNodeIdsSig.set(new Set());
  });

  private readonly effectTableDatasource = effect(() => {
    this.detailsDataSource.data = this.flattenTree(this.#dataSig());
  });

  readonly #expandedNodeIdsSig = signal<Set<string>>(new Set());

  toggleNode(node: DataNode): void {
    if (!node.expandable) return;
    this.#expandedNodeIdsSig.update((s) => {
      const ns = new Set(s);
      ns.has(node.id) ? ns.delete(node.id) : ns.add(node.id);
      return ns;
    });
  }

  isExpanded(node: DataNode): boolean {
    return this.#expandedNodeIdsSig().has(node.id);
  }

  private flattenTree(nodes: DataNode[]): DataNode[] {
    const out: DataNode[] = [];
    const walk = (node: DataNode, depth: number) => {
      let current: DataNode = node;
      let kids = current.children;
      current.level = depth;
      current.expandable = kids.length > 0;
      out.push(current);
      if (current.expandable && this.#expandedNodeIdsSig().has(current.id)) {
        for (const child of kids) {
          walk(child, depth + 1);
        }
      }
    };
    for (const n of nodes) {
      walk(n, 0);
    }
    return out;
  }

  /**
   * 【核心重写】根据 Bottle.idParent 构建代际树
   * idParent 为空字符串 = 根节点
   * 修复：循环引用保护 + 孤儿节点自动放入顶层
   */
  private calculateTreeData(rows: Bottle[]): DataNode[] {
    // 1. id -> DataNode map，一次性创建所有节点
    const nodeMap = new Map<string, DataNode>();
    const childMap = new Map<string, DataNode[]>();
    for (const bottle of rows) {
      const node: DataNode = {
        id: bottle.id,
        level: 0,
        expandable: false,
        data: bottle,
        children: [],
      };
      nodeMap.set(bottle.id, node);

      const parentId = bottle.idParent;
      if (!childMap.has(parentId)) {
        childMap.set(parentId, []);
      }
      childMap.get(parentId)!.push(node);
    }

    // 2. 递归挂载子节点，增加 visited 防循环引用
    const attachChildren = (node: DataNode, visited: Set<string>) => {
      if (visited.has(node.id)) return;
      visited.add(node.id);

      const children = childMap.get(node.data.id);
      if (children) {
        node.children = children;
        children.forEach((c) => attachChildren(c, new Set(visited)));
      }
    };

    const rootNodes: DataNode[] = [];
    // 取出原生根节点 idParent = ''
    const rootItems = childMap.get('') ?? [];
    for (const root of rootItems) {
      attachChildren(root, new Set<string>());
      rootNodes.push(root);
    }

    // ========== 处理孤儿节点：父ID存在，但父节点不在当前列表 ==========
    for (const [pid, childList] of childMap) {
      // pid为空已经处理过；pid对应的父节点不存在于nodeMap → 孤儿
      if (pid === '') continue;
      if (!nodeMap.has(pid)) {
        for (const orphanNode of childList) {
          attachChildren(orphanNode, new Set<string>());
          rootNodes.push(orphanNode);
        }
      }
    }

    return rootNodes;
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.effectData.destroy();
    this.effectTableDatasource.destroy();
  }
}
