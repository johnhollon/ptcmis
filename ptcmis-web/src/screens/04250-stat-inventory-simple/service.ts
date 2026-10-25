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

import { inject, Injectable, OnDestroy, signal } from '@angular/core';
import { QueryConditionStatInventory } from '../../entities/api/QueryConditionStatInventory';
import { catchError, map, Observable, of, Subject, tap } from 'rxjs';
import { ActionStat } from '../../services/actions/action-stat';
import { takeUntil } from 'rxjs/operators';
import { MatTableDataSource } from '@angular/material/table';
import { ModeStatInventorySimple } from '../../entities/api/ModeStatInventorySimple';
import { ModeStatInventorySimpleRecord } from '../../entities/api/ModeStatInventorySimpleRecord';
import { ModeStatInventorySimpleRecordGenerationItem } from '../../entities/api/ModeStatInventorySimpleRecordGenerationItem';

@Injectable()
export class ServiceStatInventorySimple implements OnDestroy {
  private readonly dispose$ = new Subject<void>();
  private readonly actionStat = inject(ActionStat);

  private readonly minGeneration = 5;

  // 统计描述
  statDesc: string = '';
  // 数据-期末余额
  endData: ModeStatInventorySimpleRecord = {
    variety: '合计',
    varietyCode: '',
    generationMap: {},
    bottleSum: 0,
    plantSum: 0,
  } as ModeStatInventorySimpleRecord;
  // 数据-流水明细
  detailsDataSource = new MatTableDataSource<ModeStatInventorySimpleRecord>([]);
  // 列定义-流水明细
  columns: string[] = [];
  groupColumns: string[] = [];
  headerColumns: string[] = [];
  generationCount: number = this.minGeneration + 1;

  #updateVersionSig = signal(0);
  updateVersionSig = this.#updateVersionSig.asReadonly();

  requestQuery(condition: QueryConditionStatInventory): Observable<void> {
    return this.actionStat.statInventorySimple(condition).pipe(
      catchError((_) =>
        of({
          maxGeneration: this.minGeneration,
          data: [
            {
              variety: '合计',
              varietyCode: '',
              generationMap: {},
              bottleSum: 0,
              plantSum: 0,
            },
          ],
        } as ModeStatInventorySimple),
      ),
      tap((result) => {
        this.generationCount = result.maxGeneration + 1;
        const groupColumns: string[] = ['group-variety', 'group-varietyCode'];
        const headerColumns: string[] = [];
        const columns: string[] = ['variety', 'varietyCode'];
        for (let generation = 0; generation <= result.maxGeneration; generation++) {
          groupColumns.push('group-g' + generation);
          headerColumns.push('plant' + generation, 'bottle' + generation);
          columns.push('plant' + generation, 'bottle' + generation);
        }
        groupColumns.push('group-sum');
        headerColumns.push('plantSum', 'bottleSum');
        columns.push('plantSum', 'bottleSum');
        this.endData = result.data.pop()!;
        this.detailsDataSource.data = result.data;
        this.groupColumns = groupColumns;
        this.headerColumns = headerColumns;
        this.columns = columns;
        this.#updateVersionSig.update((v) => v + 1);
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
