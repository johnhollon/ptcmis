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

import { Component, effect, EventEmitter, inject, Input, OnDestroy, OnInit, Output, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, Subject, switchMap, tap } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import { CdkFixedSizeVirtualScroll, CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import {
  LayoutDrawerOppSimple
} from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { CodePrint, CodePrintDefault } from '../../../entities/models/CodePrint';
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
  MatTableDataSource
} from '@angular/material/table';
import { CodePrintDetail } from '../../../entities/models/CodePrintDetail';
import { ServiceShowConfirmDialog } from '../../../services/ui/service-show-confirm-dialog';
import { ActionPrinter } from '../../../services/actions/action-printer';
import { BottleFilterCondition, CompFiltersProcessDo } from './filters/filters';
import { PipeCodePrintTableName } from '../../../components/00200-pipes/pipe-code-print-table-name';
import { MatIcon } from '@angular/material/icon';
import { MatContextMenuTrigger, MatMenu, MatMenuContent, MatMenuItem } from '@angular/material/menu';

@Component({
  standalone: true,
  selector: 'process-do-print-code',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    CdkVirtualScrollViewport,
    CdkFixedSizeVirtualScroll,
    PipeTimestamp,
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
    MatHeaderCellDef,
    PipeCodePrintTableName,
    CompFiltersProcessDo,
    MatIcon,
    MatContextMenuTrigger,
    MatMenu,
    MatMenuContent,
    MatMenuItem,
  ],
})
export class CompProcessDo implements OnInit, OnDestroy {
  @Input() model: CodePrint = CodePrintDefault;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();

  private condition: BottleFilterCondition | null = null;

  // 更新版本
  protected updateVersionSig = signal<number>(0);

  // 在库瓶
  readonly #allCodePrefixSig = signal<string[]>([]);
  protected readonly allCodePrefixSig = this.#allCodePrefixSig.asReadonly();
  private readonly allBottleList: CodePrintDetail[] = [];
  private allBottleEffect = effect(() => {
    if (this.updateVersionSig() < 0 || this.allBottleList.length < 1) {
      this.tableDetailDataSource.data = [];
      return;
    }
    if (!this.condition) {
      this.tableDetailDataSource.data = this.allBottleList;
      return;
    }
    const codePrefix = this.condition.codePrefix;
    const keyword = this.condition.keyword;
    const dateRange = this.condition.dateRange;
    const varietyCode = this.condition.generation;
    const generation = this.condition.generation;
    const batch = this.condition.explantCollectionBatch;
    if (
      !codePrefix &&
      !keyword &&
      (!dateRange || (!dateRange.start && !dateRange.end)) &&
      !varietyCode &&
      (generation == null || generation < 0) &&
      (!batch || batch < 1)
    ) {
      this.tableDetailDataSource.data = this.allBottleList;
      return;
    }
    this.tableDetailDataSource.data = this.allBottleList.filter((bottle) => {
      if (
        keyword &&
        !bottle.operatorName.includes(keyword) &&
        !bottle.varietyCode.includes(keyword)
      )
        return false;
      if (dateRange) {
        if (dateRange.start && dateRange.start > bottle.operateTime) return false;
        if (dateRange.end && dateRange.end < bottle.operateTime) return false;
      }
      if (
        generation !== null &&
        generation !== undefined &&
        generation >= 0 &&
        bottle.generation !== generation
      )
        return false;
      if (
        batch !== null &&
        batch !== undefined &&
        batch > 0 &&
        bottle.explantCollectionBatch !== batch
      )
        return false;
      return true;
    });
  });

  private readonly dispose$ = new Subject<void>();

  private actionPrinter = inject(ActionPrinter);
  private serviceShowMessage = inject(ServiceShowMessage);
  private readonly serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);

  tableColumnsDetail: string[] = [
    'code',
    'varietyCode',
    'explantCollectionBatch',
    'generation',
    'operatorName',
    'operateTime',
  ];
  protected tableDetailDataSource = new MatTableDataSource<CodePrintDetail>([]);
  protected logTrackById = (_index: number, row: CodePrintDetail) => row.code;

  ngOnInit(): void {
    this.onRequestStartLoading.emit('正在查询打印明细...');
    this.allBottleList.length = 0;
    this.actionPrinter
      .queryAllDetails(this.model.id)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (bottles) => {
          this.allBottleList.push(...bottles);
          const codePrefixSet = new Set<string>();
          bottles.forEach((bottle) => {
            codePrefixSet.add(bottle.code.substring(0, 6).toUpperCase());
          });
          this.#allCodePrefixSig.set([...codePrefixSet].sort((a, b) => a.localeCompare(b)));
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.allBottleEffect.destroy();
  }

  protected requestQuery(condition: BottleFilterCondition) {
    this.condition = condition;
    this.updateVersionSig.update((v) => v + 1);
  }

  protected requestPrintBatch() {
    this.serviceShowConfirmDialog
      .show('是否打印全部瓶码？')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在发送打印任务...')),
        switchMap((_) =>
          this.actionPrinter.print(
            {
              codePrint: this.model,
              codePrintDetails: this.allBottleList,
            },
            false,
          ),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => this.serviceShowMessage.showInfo('已发送打印任务。'),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestPrint(bottle: CodePrintDetail) {
    this.serviceShowConfirmDialog
      .show(`是否打印 ${bottle.code} 瓶码？`)
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在发送打印任务...')),
        switchMap((_) =>
          this.actionPrinter.print(
            {
              codePrint: this.model,
              codePrintDetails: [bottle],
            },
            false,
          ),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => this.serviceShowMessage.showInfo('已发送打印任务。'),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected readonly oppMenuSelectedModelSig = signal<CodePrint | undefined | null>(undefined);

  protected onOppMenuOpened(item: CodePrint) {
    this.oppMenuSelectedModelSig.set(item);
  }

  protected onOppMenuClosed() {
    this.oppMenuSelectedModelSig.set(undefined);
  }
}
