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
  EventEmitter,
  inject,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  signal,
  SimpleChanges,
} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, map, of, Subject, tap } from 'rxjs';
import { ActionStorageInOut } from '../../../services/actions/action-storage-in-out';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { PipeTaskType } from '../../../components/00200-pipes/pipe-task-type';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { PipeTaskStatus } from '../../../components/00200-pipes/pipe-task-status';
import { MatDivider } from '@angular/material/list';
import {
  CdkFixedSizeVirtualScroll,
  CdkVirtualForOf,
  CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';
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
import { PipeStorageInOutStatus } from '../../../components/00200-pipes/pipe-storage-in-out-status';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { StorageInOutLog } from '../../../entities/models/StorageInOutLog';
import { filter, switchMap, takeUntil } from 'rxjs/operators';
import { StorageInOutBottle } from '../../../entities/models/StorageInOutBottle';
import { APIConst } from '../../../entities/api/const-api';
import { TissueTaskBottle } from '../../../entities/models/TissueTaskBottle';
import { MatTooltip } from '@angular/material/tooltip';
import { CompCodeItemSimple } from '../code-item/code';
import {
  StorageOutTaskView,
  StorageOutTaskViewDefault,
} from '../../../entities/api/StorageOutTaskView';
import { ActionTissueTask } from '../../../services/actions/action-tissue-task';
import { MatDialog } from '@angular/material/dialog';
import { DialogInRejBiz, DialogInRejBizData } from './dialog/dialog-rej-biz';
import { MatIcon } from '@angular/material/icon';
import { ServiceShowConfirmDialog } from '../../../services/ui/service-show-confirm-dialog';
import { ServiceGlobalCodeScanner } from '../../../services/ui/service-global-code-scanner';
import {
  MatContextMenuTrigger,
  MatMenu,
  MatMenuContent,
  MatMenuItem,
} from '@angular/material/menu';

@Component({
  standalone: true,
  selector: 'storage-in-task-process-do',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    PipeTaskType,
    LayoutDrawerOppSimple,
    PipeTaskStatus,
    MatDivider,
    CdkFixedSizeVirtualScroll,
    CdkVirtualScrollViewport,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable,
    PipeStorageInOutStatus,
    PipeTimestamp,
    MatHeaderCellDef,
    MatHeaderCellDef,
    MatTooltip,
    CdkVirtualForOf,
    CompCodeItemSimple,
    CompCodeItemSimple,
    MatIcon,
    MatContextMenuTrigger,
    MatMenu,
    MatMenuContent,
    MatMenuItem,
  ],
})
export class StorageInTaskProcessDo implements OnInit, OnDestroy, OnChanges {
  @Input() model: StorageOutTaskView = StorageOutTaskViewDefault;
  @Input() editable = false;
  @Input() finishable = false;
  @Input() rejectable = false;
  @Output() onCancel = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();

  tableColumnsLogStorageOut: string[] = ['operatorTime', 'operatorName', 'status', 'oppReason'];
  protected tableLogsDataSource = new MatTableDataSource<StorageInOutLog>([]);
  protected logTrackById = (_index: number, row: StorageInOutLog) => row.id;

  // 瓶码分类
  protected readonly checkedSet = new Set<string>();
  protected readonly bottleAllMap = new Map<string, StorageInOutBottle>();
  #savedVersionSig = 0;
  readonly #updateVersionSig = signal(0);
  protected readonly updateVersionSig = this.#updateVersionSig.asReadonly();
  protected readonly bottleCheckedListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter((bottle) => this.checkedSet.has(bottle.idBottle));
  });
  protected readonly bottleCheckedStatSigReadonly = computed(() => {
    const list = this.bottleCheckedListSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleParentReturnListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'parent_in' && !this.checkedSet.has(bottle.idBottle),
    );
  });
  protected readonly bottleParentReturnStatSigReadonly = computed(() => {
    const unchecked = this.bottleParentReturnListSig();
    const uncheckedPlants = unchecked.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    const bottles = [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'parent_in',
    );
    const plants = bottles.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: bottles.length,
      plantCount: plants,
      bottleCountChecked: bottles.length - unchecked.length,
      plantCountChecked: plants - uncheckedPlants,
    };
  });
  protected readonly bottleParentKeepListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) =>
        bottle.inOutBottleType === 'parent_keep_in' && !this.checkedSet.has(bottle.idBottle),
    );
  });
  protected readonly bottleParentKeepStatSigReadonly = computed(() => {
    const unchecked = this.bottleParentKeepListSig();
    const uncheckedPlants = unchecked.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    const bottles = [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'parent_keep_in',
    );
    const plants = bottles.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: bottles.length,
      plantCount: plants,
      bottleCountChecked: bottles.length - unchecked.length,
      plantCountChecked: plants - uncheckedPlants,
    };
  });
  protected readonly bottleChildInListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'child_in' && !this.checkedSet.has(bottle.idBottle),
    );
  });
  protected readonly bottleChildInStatSigReadonly = computed(() => {
    const unchecked = this.bottleChildInListSig();
    const uncheckedPlants = unchecked.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    const bottles = [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'child_in',
    );
    const plants = bottles.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: bottles.length,
      plantCount: plants,
      bottleCountChecked: bottles.length - unchecked.length,
      plantCountChecked: plants - uncheckedPlants,
    };
  });

  private readonly dispose$ = new Subject<void>();

  private actionTissueTask = inject(ActionTissueTask);
  private actionStorageInOut = inject(ActionStorageInOut);
  private serviceShowMessage = inject(ServiceShowMessage);
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);
  private readonly dialog = inject(MatDialog);

  private doQuery() {
    this.checkedSet.clear();
    this.bottleAllMap.clear();
    this.onRequestStartLoading.emit('正在查询...');
    this.actionStorageInOut
      .queryAllLogs(this.model.id)
      .pipe(
        tap((logs) => (this.tableLogsDataSource.data = logs)),
        // 查询入库瓶明细
        switchMap((_) => this.actionStorageInOut.queryBottles(this.model.id)),
        // 对入库瓶明细进行分类
        map((bottles) => {
          if (!bottles || bottles.length < 1) return false;
          for (const bottle of bottles) {
            this.bottleAllMap.set(bottle.idBottle, bottle);
          }
          return true;
        }),
        // 如果没有入库瓶明细，则查询任务信息
        switchMap((bs) => {
          if (bs) return of(undefined);
          return this.actionTissueTask.queryByTaskId(this.model.idTask);
        }),
        tap((task) => {
          if (!task) return;
          const bottles = [...(task.bottlesParentAll ?? []), ...(task.bottlesChildrenAll ?? [])];
          for (const bottle of bottles) {
            const b = this.calculateStorageInOutBottle(this.model.id, bottle);
            if (!b) continue;
            this.bottleAllMap.set(b.idBottle, b);
          }
        }),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.#updateVersionSig.update((v) => v + 1);
          this.#savedVersionSig = this.updateVersionSig();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  ngOnInit(): void {
    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  ngOnChanges(changes: SimpleChanges): void {
    const { model } = changes;
    if (model && model.currentValue !== model.previousValue) {
      this.doQuery();
    }
  }

  protected requestFinish() {
    if (!this.editable || !this.finishable) return;
    if (this.model.status !== 'apply') return;
    for (const bottle of this.bottleAllMap.values()) {
      if (!this.checkedSet.has(bottle.idBottle)) {
        this.serviceShowMessage.showError(
          '尚有未核对的培养瓶，不能完成入库。若业务提供的培养瓶有误，或株数有误，应执行驳回业务，由业务重新提交入库申请。',
        );
        return;
      }
    }

    this.onRequestStartLoading.emit('正在执行入库...');
    this.actionStorageInOut
      .inDoneForTask(this.model.id)
      .pipe(takeUntil(this.dispose$))
      .subscribe({
        next: (_) => {
          this.onRequestStopLoading.emit();
          this.onSaved.emit();
          this.onCancel.emit();
          this.serviceShowMessage.showInfo('入库成功。');
        },
        error: (error) => {
          this.onRequestStopLoading.emit();
          this.serviceShowMessage.showError(error);
        },
      });
  }

  protected requestRejBiz() {
    if (!this.editable) return;
    if (this.model.status !== 'apply') return;

    const dialogInputData: DialogInRejBizData = {
      inOutId: this.model.id,
      reason: '',
    };

    const dialogRef = this.dialog.open<DialogInRejBiz, DialogInRejBizData, DialogInRejBizData>(
      DialogInRejBiz,
      {
        data: dialogInputData,
      },
    );

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.dispose$))
      .subscribe((result) => {
        if (result !== undefined) {
          const id = result.inOutId;
          if (!id) return;
          const reason = result.reason;
          if (!reason) {
            this.serviceShowMessage.showError('应填写驳回原因。');
            return;
          }
          this.onRequestStartLoading.emit('正在驳回业务...');
          this.actionStorageInOut
            .rejBizForIn({ id: this.model.id, idTask: this.model.idTask, oppReason: reason })
            .pipe(takeUntil(this.dispose$))
            .subscribe({
              next: (_) => {
                this.onRequestStopLoading.emit();
                this.onSaved.emit();
                this.onCancel.emit();
                this.serviceShowMessage.showInfo('驳回业务成功。');
              },
              error: (error) => {
                this.onRequestStopLoading.emit();
                this.serviceShowMessage.showError(error);
              },
            });
        }
      });
  }

  protected requestBottleToChecked(idBottle: string) {
    this.checkedSet.add(idBottle);
    this.#updateVersionSig.update((v) => v + 1);
  }
  protected requestBottleToUnChecked(idBottle: string) {
    this.checkedSet.delete(idBottle);
    this.#updateVersionSig.update((v) => v + 1);
  }
  protected requestCheckScan() {
    this.serviceGlobalCodeScanner
      .start('扫码核对')
      .pipe(takeUntil(this.dispose$))
      .subscribe({
        next: (v) => this.requestBottleToChecked(v),
      });
  }
  protected requestCheckBatch() {
    this.serviceShowConfirmDialog
      .show(
        '确认所有瓶码均已粘贴、瓶数和株数正确，将所有待入库培养瓶置为已核对无误状态？',
        '操作确认',
      )
      .pipe(
        filter((v) => v === 0),
        tap((_) => {
          this.checkedSet.clear();
          for (const bottle of this.bottleAllMap.values()) {
            this.checkedSet.add(bottle.idBottle);
          }
          this.#updateVersionSig.update((v) => v + 1);
        }),
        takeUntil(this.dispose$),
      )
      .subscribe();
  }
  protected requestReCheck() {
    this.serviceShowConfirmDialog
      .show('确认撤销所有已核对记录？', '操作确认')
      .pipe(
        filter((v) => v === 0),
        tap((_) => {
          this.checkedSet.clear();
          this.#updateVersionSig.update((v) => v + 1);
          this.#savedVersionSig = this.updateVersionSig();
        }),
        takeUntil(this.dispose$),
      )
      .subscribe();
  }
  protected requestCancel() {
    if (this.#savedVersionSig === this.updateVersionSig()) {
      this.onCancel.emit();
      return;
    }
    this.serviceShowConfirmDialog
      .show('已经行过操作，确定放弃操作结果，关闭此页面吗？', '退出确认')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onCancel.emit()),
        takeUntil(this.dispose$),
      )
      .subscribe();
  }

  // 右键上下文菜单选中的培养瓶
  protected bottleOppMenuSelectedSig = signal<StorageInOutBottle | null>(null);
  protected onBottleOppMenuOpened(item: StorageInOutBottle) {
    this.bottleOppMenuSelectedSig.set(item);
  }
  protected onBottleOppMenuClosed() {
    this.bottleOppMenuSelectedSig.set(null);
  }

  private calculateStorageInOutBottle(
    idInOut: string,
    bottle: TissueTaskBottle,
  ): StorageInOutBottle | undefined {
    const inOutBottleType =
      bottle.roleInTask === 'parent' && bottle.parentKeep === APIConst.booleans.BOOLEAN_TRUE
        ? 'parent_keep_in'
        : bottle.roleInTask === 'parent' && !bottle.idOppGroup
          ? 'parent_in'
          : bottle.roleInTask === 'child' && bottle.operateStatus === 'finished'
            ? 'child_in'
            : '';
    if (!inOutBottleType) return undefined;
    const plantCount =
      inOutBottleType === 'parent_keep_in' ? bottle.parentKeepPlantCount : bottle.plantCountActual;
    return {
      idInOut: idInOut,
      idBottle: bottle.idBottle,
      variety: this.model.variety,
      varietyCode: this.model.varietyCode,
      explantCollectionBatch: this.model.explantCollectionBatch,
      generation:
        bottle.roleInTask === 'parent' ? this.model.parentGeneration : this.model.childGeneration,
      ignoreStats: APIConst.booleans.BOOLEAN_FALSE,
      inOutBottleType: inOutBottleType,
      plantCountApplied: plantCount,
      plantCountActual: plantCount,
      keep:
        bottle.roleInTask === 'parent_keep_in'
          ? APIConst.booleans.BOOLEAN_TRUE
          : APIConst.booleans.BOOLEAN_FALSE,
      status: 'normal',
      discardReason: '',
    };
  }
}
