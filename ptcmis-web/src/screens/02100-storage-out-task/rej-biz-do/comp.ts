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
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import {
  StorageOutTaskView,
  StorageOutTaskViewDefault,
} from '../../../entities/api/StorageOutTaskView';
import { ActionStorageInOut } from '../../../services/actions/action-storage-in-out';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';

@Component({
  standalone: true,
  selector: 'storage-out-task-rej-biz-do',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    PipeTimestamp,
    LayoutDrawerOppSimple,
  ],
})
export class StorageOutTaskRejBizDo implements OnInit, OnDestroy {
  @Input() model: StorageOutTaskView = StorageOutTaskViewDefault;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();

  private readonly dispose$ = new Subject<void>();

  private actionStorageInOut = inject(ActionStorageInOut);
  private serviceShowMessage = inject(ServiceShowMessage);

  private fb = inject(FormBuilder);
  protected readonly formGroup: FormGroup = this.fb.group({ id: '', idTask: '', oppReason: '' });

  protected requestSave() {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid) {
      return;
    }
    if (!this.model.id || !this.model.idTask) return;
    const model = this.formGroup.getRawValue();
    model.id = this.model.id;
    model.idTask = this.model.idTask;
    this.onRequestStartLoading.emit('正在驳回...');
    this.actionStorageInOut
      .rejBiz(model)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('驳回成功。');
          this.onSaved.emit();
          this.onCancel.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  ngOnInit(): void {
    this.formGroup.get('oppReason')?.addValidators((control: AbstractControl) => {
      if (!control.value || !control.value.trim()) {
        return {
          error: '应填写驳回原因。',
        };
      }
      return null;
    });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
