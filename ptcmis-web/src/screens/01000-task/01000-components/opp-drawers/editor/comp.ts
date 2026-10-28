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
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { Subject } from 'rxjs';
import { LayoutDrawerOppSimple } from '../../../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { SelectVarietyCode } from '../../../../../components/variety-code/select/select-variety-code';
import { APIConst } from '../../../../../entities/api/const-api';
import { TissueTaskView } from '../../../../../entities/api/TissueTaskView';
import {
  getTissueTaskDefault,
  TissueTask,
  TissueTaskType,
} from '../../../../../entities/models/TissueTask';
import { isYmd8Number } from '../../../../../services/utils/date';
import { SelectVariety } from '../../../../../components/variety/select/select-variety';

@Component({
  selector: 'tissue-task-editor',
  templateUrl: 'comp.html',
  imports: [
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    LayoutDrawerOppSimple,
    SelectVarietyCode,
    SelectVariety,
  ],
  standalone: true,
})
export class CompEditor implements OnInit, OnDestroy {
  @Output() onCancel = new EventEmitter<void>();
  @Output() onSave = new EventEmitter<TissueTask>();
  @Input() new: boolean = false;
  @Input() updateChildGeneration: boolean = true;
  @Input() taskType: TissueTaskType | '' = '';
  @Input() set model(model: TissueTaskView) {
    const entity = { ...(model?.task || {}) };
    this.form.patchValue(entity);
  }
  dispose$ = new Subject<void>();

  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup;

  constructor() {
    this.form = this.fb.group(getTissueTaskDefault(this.taskType));
  }

  ngOnInit(): void {
    this.form.get('variety')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择品种。',
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

    this.form.get('estimatedParentPlants')?.addValidators((control) => {
      if (!control.value || control.value < 1 || !Number.isInteger(control.value)) {
        return {
          error: '应大于0。',
        };
      }
      return null;
    });

    if (this.taskType !== 'induction') {
      this.form.get('parentGeneration')?.addValidators((control) => {
        if (
          control.value === null ||
          control.value === undefined ||
          control.value < 0 ||
          !Number.isInteger(control.value)
        ) {
          return {
            error: '最小应为0。',
          };
        }
        return null;
      });
      this.form.get('estimatedParentBottles')?.addValidators((control) => {
        if (!control.value || control.value < 1 || !Number.isInteger(control.value)) {
          return {
            error: '应大于0。',
          };
        }
        return null;
      });
      this.form.get('estimatedParentKeepBottles')?.addValidators((control) => {
        if (
          control.value === null ||
          control.value === undefined ||
          control.value < 0 ||
          !Number.isInteger(control.value)
        ) {
          return {
            error: '最小应为0。',
          };
        }
        return null;
      });
      this.form.get('estimatedParentKeepPlants')?.addValidators((control) => {
        if (
          control.value === null ||
          control.value === undefined ||
          control.value < 0 ||
          !Number.isInteger(control.value)
        ) {
          return {
            error: '最小应为0。',
          };
        }
        return null;
      });
    }

    if (this.taskType !== 'acclimatization') {
      this.form.get('estimatedChildrenBottles')?.addValidators((control) => {
        if (!control.value || control.value < 1 || !Number.isInteger(control.value)) {
          return {
            error: '应大于0。',
          };
        }
        return null;
      });
      this.form.get('estimatedChildrenPlants')?.addValidators((control) => {
        if (!control.value || control.value < 1 || !Number.isInteger(control.value)) {
          return {
            error: '应大于0。',
          };
        }
        return null;
      });
    }
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
    const model: TissueTask = this.form.getRawValue();
    model.taskType = this.taskType;
    if (!model.taskStatus) model.taskStatus = 'created';
    model.createTime = model.createTime ?? 0;
    model.startTime = model.startTime ?? 0;
    model.finishTime = model.finishTime ?? 0;
    model.modifiable = model.modifiable ?? APIConst.booleans.BOOLEAN_FALSE;
    this.onSave.emit(model);
  }

  protected requestCancel() {
    this.onCancel.emit();
  }
  protected readonly APIConst = APIConst;
}
