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

import { computed, inject, Injectable, OnDestroy, signal } from '@angular/core';
import { QueryConditionStatWorkload } from '../../entities/api/QueryConditionStatWorkload';
import { map, Observable, Subject, tap } from 'rxjs';
import { ActionStat } from '../../services/actions/action-stat';
import { takeUntil } from 'rxjs/operators';
import { ModeStatWorkload } from '../../entities/api/ModeStatWorkload';

export interface DataNode {
  id: string;
  level: number;
  expandable: boolean;
  data: ModeStatWorkload;
  children: DataNode[];
}
type AggItem = {
  id: string;
  userId: string;
  userName: string;
  variety: string;
  code: string;
  batch?: number;
  gen?: number;
  data: ModeStatWorkload;
  children: Map<string, AggItem>;
};
@Injectable()
export class ServiceStatWorkload implements OnDestroy {
  private readonly dispose$ = new Subject<void>();
  private readonly actionStat = inject(ActionStat);

  readonly tableDataSig = computed(() => {
    const tree = this.#dataSig();
    if (this.#expandedNodeIdsSig() === undefined) return [];
    return this.flattenTree(tree);
  });
  readonly sumSig = computed(() => {
    const tree = this.#dataSig();
    const result = this.emptyStat();
    for (const node of tree) {
      result.bottleBegin += node.data.bottleBegin ?? 0;
      result.bottleIn += node.data.bottleIn ?? 0;
      result.bottleOut += node.data.bottleOut ?? 0;
      result.bottleAdj += node.data.bottleAdj ?? 0;
      result.bottleEnd += node.data.bottleEnd ?? 0;
      result.bottleBeginDiscard += node.data.bottleBeginDiscard ?? 0;
      result.bottleDiscard += node.data.bottleDiscard ?? 0;
      result.bottleEndDiscard += node.data.bottleEndDiscard ?? 0;

      result.plantBegin += node.data.plantBegin ?? 0;
      result.plantIn += node.data.plantIn ?? 0;
      result.plantOut += node.data.plantOut ?? 0;
      result.plantAdj += node.data.plantAdj ?? 0;
      result.plantEnd += node.data.plantEnd ?? 0;
      result.plantBeginDiscard += node.data.plantBeginDiscard ?? 0;
      result.plantDiscard += node.data.plantDiscard ?? 0;
      result.plantEndDiscard += node.data.plantEndDiscard ?? 0;
    }
    return result;
  });

  requestQuery(condition: QueryConditionStatWorkload): Observable<void> {
    return this.actionStat.statWorkload(condition).pipe(
      tap((result) => {
        this.#dataSig.set(this.calculateTreeData(result));
        this.#expandedNodeIdsSig.set(new Set());
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }
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

  readonly #expandedNodeIdsSig = signal<Set<string>>(new Set());
  readonly #dataSig = signal<DataNode[]>([]);

  private calculateTreeData(rows: ModeStatWorkload[]): DataNode[] {
    const root = new Map<string, AggItem>();

    for (const r of rows) {
      const userId = r.userId;
      const userName = r.userName;
      const variety = r.variety;
      const varietyCode = r.varietyCode;
      const explantCollectionBatch = `${r.explantCollectionBatch}`;
      const generation = `${r.generation}`;

      // 用户ID节点
      let userIdItem = root.get(userId);
      if (!userIdItem) {
        const id = `i:${userId}`;
        userIdItem = this.mkItem(id, userId, '', '', '', undefined, undefined);
        root.set(userId, userIdItem);
      }

      // 姓名节点
      let userNameItem = userIdItem.children.get(userName);
      if (!userNameItem) {
        const id = `i:${userId}|n:${userName}`;
        userNameItem = this.mkItem(id, userId, userName, '', '', undefined, undefined);
        userIdItem.children.set(userName, userNameItem);
      }

      // 品种节点
      let varietyItem = userNameItem.children.get(variety);
      if (!varietyItem) {
        const id = `i:${userId}|n:${userName}|v:${variety}`;
        varietyItem = this.mkItem(
          id,
          userId,
          userName,
          variety,
          '',
          undefined,
          undefined,
        );
        userNameItem.children.set(variety, varietyItem);
      }

      // 编号节点
      let varietyCodeItem = varietyItem.children.get(varietyCode);
      if (!varietyCodeItem) {
        const id = `i:${userId}|n:${userName}|v:${variety}|c:${varietyCode}`;
        varietyCodeItem = this.mkItem(
          id,
          userId,
          userName,
          variety,
          varietyCode,
          undefined,
          undefined,
        );
        varietyItem.children.set(varietyCode, varietyCodeItem);
      }

      // 代数节点
      let gItem = varietyCodeItem.children.get(generation);
      if (!gItem) {
        const id = `i:${userId}|n:${userName}|v:${variety}|c:${varietyCode}|g:${generation}`;
        gItem = this.mkItem(
          id,
          userId,
          userName,
          variety,
          varietyCode,
          undefined,
          r.generation,
        );
        varietyCodeItem.children.set(generation, gItem);
      }

      // 批次叶子节点：后台保证同维度仅一条，直接赋值row
      let batchItem = gItem.children.get(explantCollectionBatch);
      if (!batchItem) {
        const id = `i:${userId}|n:${userName}|v:${variety}|c:${varietyCode}|g:${generation}|b:${explantCollectionBatch}`;
        batchItem = this.mkItem(
          id,
          userId,
          userName,
          variety,
          varietyCode,
          r.explantCollectionBatch,
          r.generation,
        );
        batchItem.data = { ...r };
        gItem.children.set(explantCollectionBatch, batchItem);

        // 【边构建边向上累加】叶子创建成功后，逐层加到代数、编号、品种
        this.addData(gItem.data, r);
        this.addData(varietyCodeItem.data, r);
        this.addData(varietyItem.data, r);
        this.addData(userNameItem.data, r);
        this.addData(userIdItem.data, r);
      }
    }

    return this.convertToDataNodeList(root);
  }

  private mkItem(
    id: string,
    userId: string,
    userName: string,
    variety: string,
    varietyCode: string,
    explantCollectionBatch?: number,
    generation?: number,
  ): AggItem {
    return {
      id,
      userId,
      userName,
      variety,
      code: varietyCode,
      batch: explantCollectionBatch,
      gen: generation,
      data: this.emptyStat(),
      children: new Map<string, any>(),
    };
  }

  private emptyStat(): ModeStatWorkload {
    return {
      userId: '',
      userName: '',
      userRole: '',
      variety: '',
      varietyCode: '',
      explantCollectionBatch: 0,
      generation: 0,

      bottleBegin: 0,
      bottleIn: 0,
      bottleOut: 0,
      bottleAdj: 0,
      bottleEnd: 0,
      bottleBeginDiscard: 0,
      bottleDiscard: 0,
      bottleEndDiscard: 0,

      plantBegin: 0,
      plantIn: 0,
      plantOut: 0,
      plantAdj: 0,
      plantEnd: 0,
      plantBeginDiscard: 0,
      plantDiscard: 0,
      plantEndDiscard: 0,
    };
  }

  private addData(target: ModeStatWorkload, src: ModeStatWorkload): void {
    target.bottleBegin += src.bottleBegin ?? 0;
    target.bottleIn += src.bottleIn ?? 0;
    target.bottleOut += src.bottleOut ?? 0;
    target.bottleAdj += src.bottleAdj ?? 0;
    target.bottleEnd += src.bottleEnd ?? 0;
    target.bottleBeginDiscard += src.bottleBeginDiscard ?? 0;
    target.bottleDiscard += src.bottleDiscard ?? 0;
    target.bottleEndDiscard += src.bottleEndDiscard ?? 0;

    target.plantBegin += src.plantBegin ?? 0;
    target.plantIn += src.plantIn ?? 0;
    target.plantOut += src.plantOut ?? 0;
    target.plantAdj += src.plantAdj ?? 0;
    target.plantEnd += src.plantEnd ?? 0;
    target.plantBeginDiscard += src.plantBeginDiscard ?? 0;
    target.plantDiscard += src.plantDiscard ?? 0;
    target.plantEndDiscard += src.plantEndDiscard ?? 0;
  }

  private convertToDataNodeList(map: Map<string, AggItem>): DataNode[] {
    const out: DataNode[] = [];
    for (const [_, item] of map) {
      const children = this.convertToDataNodeList(item.children);
      const node: DataNode = {
        id: item.id,
        level: 0,
        expandable: false,
        data: {
          ...item.data,
          userId: item.userId,
          userName: item.userName,
          variety: item.variety,
          varietyCode: item.code,
          explantCollectionBatch: item.batch ?? -1,
          generation: item.gen ?? -1,
        },
        children,
      };
      out.push(node);
    }
    return out;
  }

  private flattenTree(nodes: DataNode[]): DataNode[] {
    const out: DataNode[] = [];
    const walk = (node: DataNode, depth: number) => {
      let current: DataNode = node;
      let kids = current.children;
      while (kids.length === 1) {
        current = kids[0];
        kids = current.children;
      }
      current.level = depth;
      current.expandable = kids.length > 1;
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

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
