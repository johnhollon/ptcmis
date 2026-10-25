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
import {
  LayoutDrawerOppSimple
} from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { SelectVarietyCode } from '../../../components/variety-code/select/select-variety-code';
import { APIConst } from '../../../entities/api/const-api';
import { getTissueTaskDefault, TissueTask } from '../../../entities/models/TissueTask';
import { MatOption } from '@angular/material/core';
import { MatSelect } from '@angular/material/select';
import { PipeTaskType } from '../../../components/00200-pipes/pipe-task-type';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'storage-out-task-editor',
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
    MatOption,
    MatSelect,
    PipeTaskType,
  ],
  standalone: true,
})
export class CompEditor implements OnInit, OnDestroy {
  @Output() onCancel = new EventEmitter<void>();
  @Output() onSave = new EventEmitter<TissueTask>();
  @Input() new: boolean = false;
  @Input() updateChildGeneration: boolean = true;
  @Input() set model(model: TissueTask) {
    const entity = { ...model };
    if (!!entity.variety && !!entity.varietyCode) {
      entity.varietyCode = `${entity.variety}\u200B${entity.varietyCode}`;
    }
    this.form.patchValue(entity);
  }
  dispose$ = new Subject<void>();

  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup;

  constructor() {
    this.form = this.fb.group(getTissueTaskDefault(''));
  }

  ngOnInit(): void {
    this.form.get('taskType')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择任务类别。',
        };
      }
      if (control.value === 'induction') {
        return {
          error: '应在诱导任务模块新增诱导任务。',
        };
      }
      return null;
    });

    this.form.get('varietyCode')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择品种编号。',
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

    this.form.get('estimatedChildrenBottles')?.addValidators((control) => {
      if (this.form.get('taskType')?.value !== 'acclimatization') {
        if (!control.value || control.value < 1 || !Number.isInteger(control.value)) {
          return {
            error: '应大于0。',
          };
        }
      }
      return null;
    });
    this.form.get('estimatedChildrenPlants')?.addValidators((control) => {
      if (this.form.get('taskType')?.value !== 'acclimatization') {
        if (!control.value || control.value < 1 || !Number.isInteger(control.value)) {
          return {
            error: '应大于0。',
          };
        }
      }
      return null;
    });

    this.form
      .get('taskType')
      ?.valueChanges.pipe(takeUntil(this.dispose$))
      .subscribe({
        next: (value) => {
          if (value === 'acclimatization') {
            this.form.get('estimatedChildrenBottles')?.setValue(0);
            this.form.get('estimatedChildrenPlants')?.setValue(0);
          }
        },
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
    const model: TissueTask = this.form.getRawValue();
    if (!model.taskStatus) model.taskStatus = 'created';
    model.createTime = model.createTime ?? 0;
    model.startTime = model.startTime ?? 0;
    model.finishTime = model.finishTime ?? 0;
    model.modifiable = model.modifiable ?? APIConst.booleans.BOOLEAN_FALSE;
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
    if (model.taskType === 'acclimatization') {
      model.estimatedChildrenBottles = 0;
      model.estimatedChildrenPlants = 0;
    }

    this.onSave.emit(model);
  }

  protected requestCancel() {
    this.onCancel.emit();
  }
  protected readonly APIConst = APIConst;
}
