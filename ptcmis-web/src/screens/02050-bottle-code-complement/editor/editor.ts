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
import { finalize, Subject } from 'rxjs';
import {
  BottleCodeComplementTask,
  BottleCodeComplementTaskDefault,
} from '../../../entities/models/BottleCodeComplementTask';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { SelectVarietyCode } from '../../../components/variety-code/select/select-variety-code';
import { ActionBottleCodeComplementTask } from '../../../services/actions/action-bottle-code-complement-task';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { takeUntil } from 'rxjs/operators';
import { isYmd8Number } from '../../../services/utils/date';
import { MatOption } from '@angular/material/core';
import { MatSelect } from '@angular/material/select';
import { PipeStorageReason } from '../../../components/00200-pipes/pipe-storage-reason';

@Component({
  selector: 'editor-bottle-code-complement-task',
  standalone: true,
  templateUrl: 'editor.html',
  imports: [
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    LayoutDrawerOppSimple,
    SelectVarietyCode,
    MatOption,
    MatSelect,
    PipeStorageReason,
  ],
})

export class CompEditor implements OnInit, OnDestroy {
  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();
  @Input() set model(model: BottleCodeComplementTask) {
    const entity = { ...(model || {}) };
    if (!!entity.variety && !!entity.varietyCode) {
      entity.varietyCode = `${entity.variety}\u200B${entity.varietyCode}`;
    }
    this.form.patchValue(entity);
  }
  dispose$ = new Subject<void>();

  private actionBottleCodeComplementTask = inject(ActionBottleCodeComplementTask);
  private serviceShowMessage = inject(ServiceShowMessage);

  private readonly fb = inject(FormBuilder);
  protected readonly form = this.fb.group({ ...BottleCodeComplementTaskDefault });

  ngOnInit(): void {
    this.form.get('varietyCode')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择品种编号。',
        };
      }
      return null;
    });
    this.form.get('sourceType')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择培养瓶建瓶来源。',
        };
      }
      return null;
    });
    this.form.get('explantCollectionBatch')?.addValidators((control) => {
      if (
        control.value === null ||
        control.value === undefined ||
        control.value < 1 ||
        !Number.isInteger(control.value) ||
        !isYmd8Number(control.value)
      ) {
        return {
          error: '外植体采集批次应是 YYYYMMDD 格式的整数，如20260101。',
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
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected requestSave() {
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }
    const model = this.form.getRawValue();
    if (!model.taskStatus) model.taskStatus = 'created';
    model.createTime = model.createTime ?? 0;
    model.startTime = model.startTime ?? 0;
    model.finishTime = model.finishTime ?? 0;
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
    this.actionBottleCodeComplementTask
      .save(model as BottleCodeComplementTask)
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

  protected requestCancel() {
    this.onCancel.emit();
  }
}
