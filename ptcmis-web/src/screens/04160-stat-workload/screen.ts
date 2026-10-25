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

import { OverlayModule } from '@angular/cdk/overlay';
import { PortalModule } from '@angular/cdk/portal';
import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { isEqual } from 'lodash-es';
import { distinctUntilChanged, finalize, Observable, startWith, Subject, switchMap } from 'rxjs';
import { debounceTime, takeUntil } from 'rxjs/operators';
import {
  LayoutPageFunctionSimpleTitle
} from '../../components/00050-layouts/00200-layout-page-function-simple/components/title/component';
import { LayoutPageFunctionSimple } from '../../components/00050-layouts/00200-layout-page-function-simple/layout';
import { ServiceShowMessage } from '../../services/ui/service-show-error-message.service';
import { CompFilters } from './filters/filters';
import { CompTable } from './table/table';
import { ServiceStatWorkload } from './service';
import {
  QueryConditionStatWorkload,
  QueryConditionStatWorkloadDefault
} from '../../entities/api/QueryConditionStatWorkload';
import { QueryConditionStatInventory } from '../../entities/api/QueryConditionStatInventory';
import { ActionStat } from '../../services/actions/action-stat';

@Component({
  selector: 'screen-stat-workload',
  templateUrl: 'screen.html',
  styleUrl: 'screen.scss',
  standalone: true,
  providers: [ServiceStatWorkload],
  imports: [
    MatTableModule,
    OverlayModule,
    PortalModule,
    MatSnackBarModule,
    FormsModule,
    ReactiveFormsModule,
    LayoutPageFunctionSimpleTitle,
    LayoutPageFunctionSimpleTitle,
    LayoutPageFunctionSimple,
    CompFilters,
    CompTable,
  ],
})
export class ScreenStatWorkload implements OnInit, OnDestroy {
  protected readonly loadingSig = signal<boolean>(false);
  protected readonly loadingMessageSig = signal<string>('');

  private readonly serviceShowErrorMessage = inject(ServiceShowMessage);
  protected readonly service = inject(ServiceStatWorkload);
  private readonly actionStat = inject(ActionStat);

  private readonly condition$ = new Subject<QueryConditionStatWorkload>();
  private readonly dispose$ = new Subject<void>();

  ngOnInit(): void {
    this.condition$
      .pipe(
        startWith(QueryConditionStatWorkloadDefault),
        distinctUntilChanged((old, current) => isEqual(old, current)),
        debounceTime(500),
        switchMap((condition) => this.query(condition)),
        takeUntil(this.dispose$),
      )
      .subscribe();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.condition$.complete();
  }

  protected requestConditionChanged(condition: QueryConditionStatWorkload) {
    this.condition$.next(condition);
  }

  protected requestQuery(condition: QueryConditionStatWorkload) {
    this.query(condition).pipe(takeUntil(this.dispose$)).subscribe();
  }

  protected requestExport(condition: QueryConditionStatWorkload) {
    this.loadingMessageSig.set('正在查询...');
    this.loadingSig.set(true);
    this.actionStat
      .excelStatWorkload(condition)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe((res) => {
        const blob = res.body;
        if (!blob) return;

        const disposition = res.headers.get('content-disposition');
        let fileName = 'export.xlsx';
        if (disposition) {
          const match = disposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
          if (match && match[1]) {
            fileName = decodeURIComponent(match[1].replace(/['"]/g, ''));
          }
        }

        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        a.remove();
        URL.revokeObjectURL(url);
      });
  }

  protected query(condition: QueryConditionStatWorkload): Observable<void> {
    this.loadingMessageSig.set('正在查询...');
    this.loadingSig.set(true);
    return this.service.requestQuery(condition).pipe(
      takeUntil(this.dispose$),
      finalize(() => this.loadingSig.set(false)),
    );
  }
}
