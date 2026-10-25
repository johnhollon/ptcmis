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
import { QueryConditionStatWorkload } from '../../entities/api/QueryConditionStatWorkload';
import {
  WorkloadTransaction,
  WorkloadTransactionDefault,
} from '../../entities/models/WorkloadTransaction';
import { catchError, map, Observable, of, Subject, tap } from 'rxjs';
import { ActionStat } from '../../services/actions/action-stat';
import { takeUntil } from 'rxjs/operators';
import { MatTableDataSource } from '@angular/material/table';

@Injectable()
export class ServiceStatWorkloadTransaction implements OnDestroy {
  private readonly dispose$ = new Subject<void>();
  private readonly actionStat = inject(ActionStat);

  // 统计描述
  statDesc: string = '';
  // 数据-期初余额
  startData: WorkloadTransaction = WorkloadTransactionDefault;
  // 数据-期末余额
  endData: WorkloadTransaction = WorkloadTransactionDefault;
  // 数据-流水明细
  detailsDataSource = new MatTableDataSource<WorkloadTransaction>([]);
  // 列定义-流水明细
  columns: string[] = [
    'index',
    'userRole',
    'userName',
    'variety',
    'varietyCode',
    'generation',
    'bizType',
    'description',
    'bottles',
    'plants',
    'operateTime',
    'operatorName',
  ];
  // 流水数据过滤输入框列表（表格内）
  filtersForTransaction: string[] = [];

  #updateVersionSig = signal(0);
  updateVersionSig = this.#updateVersionSig.asReadonly();

  requestQuery(condition: QueryConditionStatWorkload): Observable<void> {
    return this.actionStat.statWorkloadTransaction(condition).pipe(
      catchError((_) =>
        of({
          startData: WorkloadTransactionDefault,
          endData: WorkloadTransactionDefault,
          details: [],
          columns: [],
        }),
      ),
      tap((result) => {
        this.startData = result.startData;
        this.endData = result.endData;
        this.detailsDataSource.data = result.details;
        this.#updateVersionSig.update((v) => v + 1);
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }

  requestFilterDetails() {}

  requestExportExcel(): Observable<void> {
    return of();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
