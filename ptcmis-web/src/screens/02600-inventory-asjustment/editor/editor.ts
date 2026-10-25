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

import { Component, EventEmitter, inject, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, Subject, switchMap, tap } from 'rxjs';
import {
  InventoryAdjustment,
  InventoryAdjustmentDefault,
} from '../../../entities/models/InventoryAdjustment';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { SelectVarietyCode } from '../../../components/variety-code/select/select-variety-code';
import { ActionInventoryAdjustment } from '../../../services/actions/action-inventory-adjustment';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { filter, takeUntil } from 'rxjs/operators';
import { CdkFixedSizeVirtualScroll, CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
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
import { InventoryAdjustmentLog } from '../../../entities/models/InventoryAdjustmentLog';
import { genId } from '../../../services/utils/00010-id';
import { ServiceShowConfirmDialog } from '../../../services/ui/service-show-confirm-dialog';
import { PipeInventoryAdjustmentStatus } from '../../../components/00200-pipes/pipe-inventory-adjustment-status';

@Component({
  selector: 'editor-inventory-adjustment',
  standalone: true,
  templateUrl: 'editor.html',
  styleUrls: ['editor.scss'],
  imports: [
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    LayoutDrawerOppSimple,
    SelectVarietyCode,
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
    PipeInventoryAdjustmentStatus,
  ],
})
export class CompEditor implements OnInit, OnDestroy {
  @Input() editable = true;
  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();
  @Input() set model(model: InventoryAdjustment) {
    const entity = { ...(model || {}) };
    if (!!entity.variety && !!entity.varietyCode) {
      entity.varietyCode = `${entity.variety}\u200B${entity.varietyCode}`;
    }
    this.form.patchValue(entity);
  }

  tableColumnsLogStorageOut: string[] = ['operatorTime', 'operatorName', 'status'];
  protected tableLogsDataSource = new MatTableDataSource<InventoryAdjustmentLog>([]);
  protected logTrackById = (_index: number, row: InventoryAdjustmentLog) => row.id;

  dispose$ = new Subject<void>();

  private actionInventoryAdjustment = inject(ActionInventoryAdjustment);
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private serviceShowMessage = inject(ServiceShowMessage);

  private readonly fb = inject(FormBuilder);
  protected readonly form = this.fb.group(InventoryAdjustmentDefault);

  ngOnInit(): void {
    this.form.get('varietyCode')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择品种编号。',
        };
      }
      return null;
    });

    this.form.get('generation')?.addValidators((control) => {
      if (
        control.value === null ||
        control.value === undefined ||
        control.value < 0 ||
        control.value > 99 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '应是不小于0、小于100的整数。',
        };
      }
      return null;
    });

    this.form.get('bottles')?.addValidators((control) => {
      if (
        control.value === null ||
        control.value === undefined ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '应填写整数。',
        };
      }
      return null;
    });

    this.form.get('plants')?.addValidators((control) => {
      if (
        control.value === null ||
        control.value === undefined ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '应填写整数。',
        };
      }
      return null;
    });

    this.form.get('reason')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应填写该项。',
        };
      }
      return null;
    });

    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  private doQuery() {
    const id = this.form.get('id')?.value || '';
    if (!id) return;
    this.onRequestStartLoading.emit('正在查询...');
    this.actionInventoryAdjustment
      .queryAllLogs(this.form.get('id')?.value || '')
      .pipe(
        tap((logs) => (this.tableLogsDataSource.data = logs)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestSave() {
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }
    const model = this.form.getRawValue();
    if (!model.status) model.status = 'created';
    model.operateTime = model.operateTime ?? 0;
    model.id = model.id || genId();
    const varietyCode = model.varietyCode;
    if (!!varietyCode) {
      const varietyCodeSplit = varietyCode.split('\u200B');
      if (varietyCodeSplit.length > 0) {
        model.variety = varietyCodeSplit[0].trim().toUpperCase();
      }
      if (varietyCodeSplit.length > 1) {
        model.varietyCode = varietyCodeSplit[1].trim().toUpperCase();
      }
    }
    this.onRequestStartLoading.emit('正在保存...');
    this.actionInventoryAdjustment
      .save(model as InventoryAdjustment)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('保存成功。');
          this.onSaved.emit();
          this.onCancel.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestFinish() {
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }

    this.serviceShowConfirmDialog
      .show('是否完成此次调差？完成后，调差信息计入库存，且无法再次更改或删除此调差信息。')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在完成调差...')),
        switchMap((_) => {
          const model = this.form.getRawValue();
          if (!model.status) model.status = 'created';
          model.operateTime = model.operateTime ?? 0;
          model.id = model.id || genId();
          const varietyCode = model.varietyCode;
          if (!!varietyCode) {
            const varietyCodeSplit = varietyCode.split('\u200B');
            if (varietyCodeSplit.length > 0) {
              model.variety = varietyCodeSplit[0].trim().toUpperCase();
            }
            if (varietyCodeSplit.length > 1) {
              model.varietyCode = varietyCodeSplit[1].trim().toUpperCase();
            }
          }
          return this.actionInventoryAdjustment.done(model as InventoryAdjustment);
        }),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('执行成功。');
          this.onSaved.emit();
          this.onCancel.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestCancel() {
    this.onCancel.emit();
  }
}
