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
import { QueryConditionStatInventory } from '../../entities/api/QueryConditionStatInventory';
import { map, Observable, Subject, tap } from 'rxjs';
import { ActionStat } from '../../services/actions/action-stat';
import { takeUntil } from 'rxjs/operators';
import { ModeStatInventory } from '../../entities/api/ModeStatInventory';

export interface DataNode {
  id: string;
  level: number;
  expandable: boolean;
  data: ModeStatInventory;
  children: DataNode[];
}
type AggItem = {
  id: string;
  variety: string;
  code: string;
  gen?: number;
  data: ModeStatInventory;
  children: Map<string, AggItem>;
};
@Injectable()
export class ServiceStatInventory implements OnDestroy {
  private readonly dispose$ = new Subject<void>();
  private readonly actionStat = inject(ActionStat);

  readonly tableDataSig = computed(() => {
    const tree = this.#dataSig();
    if(this.#expandedNodeIdsSig() === undefined) return [];
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

      result.plantBegin += node.data.plantBegin ?? 0;
      result.plantIn += node.data.plantIn ?? 0;
      result.plantOut += node.data.plantOut ?? 0;
      result.plantAdj += node.data.plantAdj ?? 0;
      result.plantEnd += node.data.plantEnd ?? 0;
    }
    return result;
  });

  requestQuery(condition: QueryConditionStatInventory): Observable<void> {
    return this.actionStat.statInventory(condition).pipe(
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

  private calculateTreeData(rows: ModeStatInventory[]): DataNode[] {
    const root = new Map<string, AggItem>();

    for (const r of rows) {
      const variety = r.variety;
      const varietyCode = r.varietyCode;
      const generation = `${r.generation}`;

      // 品种节点
      let varietyItem = root.get(variety);
      if (!varietyItem) {
        const id = `v:${variety}`;
        varietyItem = this.mkItem(id, variety, '', undefined);
        root.set(variety, varietyItem);
      }

      // 编号节点
      let varietyCodeItem = varietyItem.children.get(varietyCode);
      if (!varietyCodeItem) {
        const id = `v:${variety}|c:${varietyCode}`;
        varietyCodeItem = this.mkItem(id, variety, varietyCode, undefined);
        varietyItem.children.set(varietyCode, varietyCodeItem);
      }

      // 代数节点
      let gItem = varietyCodeItem.children.get(generation);
      if (!gItem) {
        const id = `v:${variety}|c:${varietyCode}|g:${generation}`;
        gItem = this.mkItem(id, variety, varietyCode, r.generation);
        gItem.data = { ...r };
        varietyCodeItem.children.set(generation, gItem);

        // 【边构建边向上累加】叶子创建成功后，逐层加到编号、品种
        this.addData(varietyCodeItem.data, r);
        this.addData(varietyItem.data, r);
      }
    }

    return this.convertToDataNodeList(root);
  }

  private mkItem(
    id: string,
    variety: string,
    varietyCode: string,
    generation?: number,
  ): AggItem {
    return {
      id,
      variety,
      code: varietyCode,
      gen: generation,
      data: this.emptyStat(),
      children: new Map<string, any>(),
    };
  }

  private emptyStat(): ModeStatInventory {
    return {
      variety: '',
      varietyCode: '',
      generation: -1,
      bottleBegin: 0,
      bottleIn: 0,
      bottleOut: 0,
      bottleAdj: 0,
      bottleEnd: 0,
      plantBegin: 0,
      plantIn: 0,
      plantOut: 0,
      plantAdj: 0,
      plantEnd: 0,
    };
  }

  private addData(target: ModeStatInventory, src: ModeStatInventory): void {
    target.bottleBegin += src.bottleBegin ?? 0;
    target.bottleIn += src.bottleIn ?? 0;
    target.bottleOut += src.bottleOut ?? 0;
    target.bottleAdj += src.bottleAdj ?? 0;
    target.bottleEnd += src.bottleEnd ?? 0;

    target.plantBegin += src.plantBegin ?? 0;
    target.plantIn += src.plantIn ?? 0;
    target.plantOut += src.plantOut ?? 0;
    target.plantAdj += src.plantAdj ?? 0;
    target.plantEnd += src.plantEnd ?? 0;
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
          variety: item.variety,
          varietyCode: item.code,
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
