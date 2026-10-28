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
  AfterViewInit,
  Component,
  computed,
  effect,
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  Output,
  signal,
} from '@angular/core';
import { finalize, map, of, Subject, tap } from 'rxjs';
import { TissueTaskView } from '../../../../../entities/api/TissueTaskView';
import { ArchivePanel } from '../../../../../components/00600-archive/archive';
import { LayoutDrawerOppSimple } from '../../../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatDivider } from '@angular/material/list';
import { MatFormField, MatInput, MatLabel, MatSuffix } from '@angular/material/input';
import { PipeStorageInOutStatus } from '../../../../../components/00200-pipes/pipe-storage-in-out-status';
import { PipeTaskStatus } from '../../../../../components/00200-pipes/pipe-task-status';
import { PipeTaskType } from '../../../../../components/00200-pipes/pipe-task-type';
import { PipeTimestamp } from '../../../../../components/00200-pipes/pipe-timestamp';
import {
  CdkFixedSizeVirtualScroll,
  CdkVirtualForOf,
  CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';
import { CompCodeItemSimple } from './code-item/code';
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
import { StorageInOutLog } from '../../../../../entities/models/StorageInOutLog';
import { ActionStorageInOut } from '../../../../../services/actions/action-storage-in-out';
import { ServiceShowMessage } from '../../../../../services/ui/service-show-error-message.service';
import { ServiceTissueTaskProcess } from '../process/service';
import { MatTooltip } from '@angular/material/tooltip';
import { filter, switchMap, takeUntil } from 'rxjs/operators';
import { StorageInOutBottle } from '../../../../../entities/models/StorageInOutBottle';
import { TissueTaskBottle } from '../../../../../entities/models/TissueTaskBottle';
import { APIConst } from '../../../../../entities/api/const-api';
import { TissueTask } from '../../../../../entities/models/TissueTask';
import { Archive } from '../../../../../entities/models/Archive';
import { MatIcon } from '@angular/material/icon';
import { ServiceShowConfirmDialog } from '../../../../../services/ui/service-show-confirm-dialog';

@Component({
  standalone: true,
  selector: 'tissue-task-storage-in',
  templateUrl: 'comp.html',
  imports: [
    ArchivePanel,
    LayoutDrawerOppSimple,
    MatButton,
    MatDivider,
    MatFormField,
    MatInput,
    MatLabel,
    PipeStorageInOutStatus,
    PipeTaskStatus,
    PipeTaskType,
    PipeTimestamp,
    CdkFixedSizeVirtualScroll,
    CdkVirtualForOf,
    CdkVirtualScrollViewport,
    CompCodeItemSimple,
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
    MatHeaderCellDef,
    MatIcon,
    MatIconButton,
    MatSuffix,
  ],
  styleUrls: ['comp.scss'],
})
export class CompStorageIn implements AfterViewInit, OnDestroy {
  @Input() fieldsView: string[] = [];

  @Input() editable = true;
  @Output() onCancel = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<TissueTaskView>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onRequestChangeSubModule = new EventEmitter<string>();
  protected subModuleSelectedSig = signal<string>('process');

  tableColumnsLogStorageOut: string[] = ['operatorTime', 'operatorName', 'status', 'oppReason'];
  protected tableLogsDataSource = new MatTableDataSource<StorageInOutLog>([]);
  protected logTrackById = (_index: number, row: StorageInOutLog) => row.id;

  private actionStorageInOut = inject(ActionStorageInOut);
  private serviceShowMessage = inject(ServiceShowMessage);
  protected serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  protected serviceTissueTaskProcess = inject(ServiceTissueTaskProcess);

  dispose$ = new Subject<void>();

  // 瓶码分类
  protected readonly bottleAllMap = new Map<string, StorageInOutBottle>();
  protected updateVersionSig = signal<number>(0);
  #effect = effect(() => {
    if (this.serviceTissueTaskProcess.updateVersionOfStorageInSig() < 0) return;
    this.doQuery();
  });
  protected readonly bottleParentReturnListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'parent_in',
    );
  });
  protected readonly bottleParentReturnStatSigReadonly = computed(() => {
    const list = this.bottleParentReturnListSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleParentKeepListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'parent_keep_in',
    );
  });
  protected readonly bottleParentKeepStatSigReadonly = computed(() => {
    const list = this.bottleParentKeepListSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleChildInListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'child_in',
    );
  });
  protected readonly bottleChildInStatSigReadonly = computed(() => {
    const list = this.bottleChildInListSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected archivesSig = signal<Archive[]>([]);

  ngAfterViewInit(): void {}

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.#effect.destroy();
  }

  private doQuery() {
    const task = this.serviceTissueTaskProcess.taskSig();
    const storageIn = this.serviceTissueTaskProcess.storageInSig();
    this.bottleAllMap.clear();
    of(storageIn)
      .pipe(
        tap((_) => this.onRequestStartLoading.emit('正在查询...')),
        // 查询日志。
        switchMap((_) => {
          if (!storageIn) return of([] as StorageInOutLog[]);
          if (!storageIn.status) return of([] as StorageInOutLog[]);
          return this.actionStorageInOut.queryAllLogs(storageIn.id);
        }),
        tap((logs) => (this.tableLogsDataSource.data = logs)),
        // 查询入库瓶明细
        switchMap((_) => {
          if (!storageIn) return of([] as StorageInOutBottle[]);
          if (!storageIn.status) return of([] as StorageInOutBottle[]);
          return this.actionStorageInOut.queryBottles(storageIn.id);
        }),
        // 对入库瓶明细进行分类
        map((bottles) => {
          if (!bottles || bottles.length < 1) return bottles;
          for (const bottle of bottles) {
            this.bottleAllMap.set(bottle.idBottle, bottle);
          }
          return bottles;
        }),
        // 如果没有入库瓶明细，则根据任务培养瓶组织入库瓶明细
        tap((bs) => {
          if (!!bs && bs.length > 0) return;
          if (!storageIn || !storageIn.status) return;
          const bottles = [...(task.bottlesParentAll ?? []), ...(task.bottlesChildrenAll ?? [])];
          for (const bottle of bottles) {
            const b = this.calculateStorageInOutBottle(storageIn.id, task.task, bottle);
            if (!b) continue;
            this.bottleAllMap.set(b.idBottle, b);
          }
        }),
        // 查询附件资料
        switchMap((_) => {
          const archives = [] as Archive[];
          if (!storageIn) return of(archives);
          return this.actionStorageInOut.queryArchives(storageIn.id);
        }),
        tap((archives) => this.archivesSig.set(archives)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
        complete: () => this.updateVersionSig.update((v) => v + 1),
      });
  }

  private calculateStorageInOutBottle(
    idInOut: string,
    task: TissueTask,
    bottle: TissueTaskBottle,
  ): StorageInOutBottle | undefined {
    const inOutBottleType =
      bottle.roleInTask === 'parent' && bottle.parentKeep === APIConst.booleans.BOOLEAN_TRUE
        ? 'parent_keep_in'
        : bottle.roleInTask === 'parent' && !bottle.idOppGroup
          ? 'parent_in'
          : bottle.roleInTask === 'child' &&
              bottle.operateStatus === 'finished' &&
              bottle.childCodeStatus === 'checked'
            ? 'child_in'
            : '';
    if (!inOutBottleType) return undefined;
    const plantCount =
      inOutBottleType === 'parent_keep_in' ? bottle.parentKeepPlantCount : bottle.plantCountActual;
    return {
      idInOut: idInOut,
      idBottle: bottle.idBottle,
      variety: bottle.variety,
      varietyCode: bottle.varietyCode,
      explantCollectionBatch: task.explantCollectionBatch,
      // generation: bottle.roleInTask === 'parent' ? task.parentGeneration : task.childGeneration,
      generation:0,
      ignoreStats: APIConst.booleans.BOOLEAN_FALSE,
      inOutBottleType: inOutBottleType,
      plantCountApplied: plantCount,
      plantCountActual: plantCount,
      keep: APIConst.booleans.get(inOutBottleType === 'parent_keep_in'),
      status: 'normal',
      discardReason: '',
    };
  }

  protected requestRefresh() {
    this.onRequestStartLoading.emit('正在刷新状态...');
    this.serviceTissueTaskProcess
      .refresh()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.onSaved.emit();
          this.serviceShowMessage.showInfo('刷新状态成功。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestApply() {
    if (!this.editable) return;
    const storageIn = this.serviceTissueTaskProcess.storageInSig();
    if (!!storageIn && !(!storageIn.status || storageIn.status === 'rej_biz')) {
      return;
    }
    this.onRequestStartLoading.emit('正在申请入库...');
    this.serviceTissueTaskProcess
      .storageInCreate()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.onSaved.emit();
          this.serviceShowMessage.showInfo('申请入库成功。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestDelete() {
    if (!this.editable) return;
    const storageIn = this.serviceTissueTaskProcess.storageInSig();
    if (
      !!storageIn &&
      !(!storageIn.status || storageIn.status === 'apply' || storageIn.status === 'rej_biz')
    ) {
      return;
    }
    this.serviceShowConfirmDialog
      .show('确认删除？', '确认删除')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在删除...')),
        switchMap((_) => this.serviceTissueTaskProcess.storageInDelete()),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.onSaved.emit();
          this.serviceShowMessage.showInfo('删除成功。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }
}
