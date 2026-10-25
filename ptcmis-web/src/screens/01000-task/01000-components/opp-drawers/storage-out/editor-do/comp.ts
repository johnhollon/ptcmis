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
  EventEmitter,
  inject,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges
} from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, Subject, switchMap, tap } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import { TissueTaskView } from '../../../../../../entities/api/TissueTaskView';
import { ServiceShowMessage } from '../../../../../../services/ui/service-show-error-message.service';
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
import { getDefaultStorageInOutForTask, StorageInOut } from '../../../../../../entities/models/StorageInOut';
import { APIConst } from '../../../../../../entities/api/const-api';
import { CdkFixedSizeVirtualScroll, CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { PipeTimestamp } from '../../../../../../components/00200-pipes/pipe-timestamp';
import { PipeStorageInOutStatus } from '../../../../../../components/00200-pipes/pipe-storage-in-out-status';
import { ActionStorageInOut } from '../../../../../../services/actions/action-storage-in-out';
import { StorageInOutLog } from '../../../../../../entities/models/StorageInOutLog';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { ServiceShowConfirmDialog } from '../../../../../../services/ui/service-show-confirm-dialog';
import { ServiceTissueTaskProcess } from '../../process/service';
import {
  DialogTaskStorageOutRejStore,
  DialogTaskStorageOutRejStoreData
} from '../../dialogs/storage-out/dialog-rej-store';
import { MatDialog } from '@angular/material/dialog';

@Component({
  standalone: true,
  selector: 'tissue-task-storage-out-editor-do',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    CdkVirtualScrollViewport,
    CdkFixedSizeVirtualScroll,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRow,
    MatRow,
    PipeTimestamp,
    PipeStorageInOutStatus,
    MatHeaderRowDef,
    MatRowDef,
    MatButton,
    MatIcon,
  ],
})
export class CompStorageOutEditorDo implements OnInit, OnDestroy, OnChanges {
  @Input() editable = true;
  @Input() model: {
    planBottles: number;
    planPlants: number;
    applyBottles: number;
    applyPlants: number;
    doneBottles: number;
    donePlants: number;
    outBottles: number;
    outPlants: number;
  } | null = null;
  @Input() storageInOut: StorageInOut | null = null;

  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();
  @Output() onRequestCheck = new EventEmitter<void>();

  @Input() tableColumnsLogStorageOut: string[] = [
    'operatorTime',
    'operatorName',
    'status',
    'oppReason',
  ];
  protected tableLogsDataSource = new MatTableDataSource<StorageInOutLog>([]);
  protected logTrackById = (_index: number, row: StorageInOutLog) => row.id;

  private readonly dispose$ = new Subject<void>();

  private actionStorageInOut = inject(ActionStorageInOut);
  private serviceShowMessage = inject(ServiceShowMessage);
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private serviceTissueTaskProcess = inject(ServiceTissueTaskProcess);
  private readonly dialog = inject(MatDialog);

  private fb = inject(FormBuilder);
  protected readonly formGroup: FormGroup = this.fb.group(
    getDefaultStorageInOutForTask({} as TissueTaskView, 'out'),
  );

  protected requestRejStore() {
    if (!this.editable) return;
    if (!this.storageInOut) return;
    // 打开对话框。
    const dialogInputData: DialogTaskStorageOutRejStoreData = {
      id: this.storageInOut.id,
      idTask: this.storageInOut.idTask,
      oppReason: '',
    };

    const dialogRef = this.dialog.open<
      DialogTaskStorageOutRejStore,
      DialogTaskStorageOutRejStoreData,
      DialogTaskStorageOutRejStoreData
    >(DialogTaskStorageOutRejStore, {
      data: dialogInputData,
      width: '300px',
    });

    dialogRef
      .afterClosed()
      .pipe(
        filter((v) => !!v),
        tap((_) => this.onRequestStartLoading.emit('正在驳回库房...')),
        switchMap((v) => this.serviceTissueTaskProcess.storageOutRejStore(v)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('驳回库房成功。');
          this.onSaved.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  private doQueryLog() {
    if (!this.storageInOut) return;
    this.onRequestStartLoading.emit('正在查询操作日志...');
    this.actionStorageInOut
      .queryAllLogs(this.storageInOut.id)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (logs) => (this.tableLogsDataSource.data = logs),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  ngOnInit(): void {
    this.formGroup.get('bottlesApplied')?.addValidators((control: AbstractControl) => {
      if (
        !control.value ||
        control.value < 1 ||
        control.value > 999 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '应大于0，且小于 1,000。',
        };
      }
      return null;
    });
    this.formGroup.get('plantsApplied')?.addValidators((control: AbstractControl) => {
      if (
        !control.value ||
        control.value < 1 ||
        control.value > 9999 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '应大于0，且小于 10,000。',
        };
      }
      return null;
    });
    this.organizeData();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  ngOnChanges(changes: SimpleChanges): void {
    const { storageInOut } = changes;
    if (storageInOut) {
      this.organizeData();
    }
  }

  private organizeData(): void {
    if (!this.model || !this.storageInOut) return;

    const inOut = { ...this.storageInOut };
    inOut.bottlesApplied = 0 - inOut.bottlesApplied;
    inOut.plantsApplied = 0 - inOut.plantsApplied;
    inOut.bottlesKeepInApplied = 0 - inOut.bottlesKeepInApplied;
    inOut.plantsKeepInApplied = 0 - inOut.plantsKeepInApplied;
    inOut.bottlesActual = 0 - inOut.bottlesActual;
    inOut.plantsActual = 0 - inOut.plantsActual;
    inOut.bottlesKeepInActual = 0 - inOut.bottlesKeepInActual;
    inOut.plantsKeepInActual = 0 - inOut.plantsKeepInActual;

    this.formGroup.patchValue(inOut);

    this.doQueryLog();
  }

  protected readonly APIConst = APIConst;
}
