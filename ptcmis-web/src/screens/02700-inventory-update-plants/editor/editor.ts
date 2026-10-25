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
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, Subject, switchMap, tap } from 'rxjs';
import { Bottle } from '../../../entities/models/Bottle';
import {
  LayoutDrawerOppSimple
} from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { filter, takeUntil } from 'rxjs/operators';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { ServiceShowConfirmDialog } from '../../../services/ui/service-show-confirm-dialog';
import { ActionBottle } from '../../../services/actions/action-bottle';
import { MatTooltip } from '@angular/material/tooltip';
import { MatDivider } from '@angular/material/list';
import { PipeBottleStorageStatus } from '../../../components/00200-pipes/pipe-bottle-storage-status';
import { PipeStorageReason } from '../../../components/00200-pipes/pipe-storage-reason';

@Component({
  selector: 'editor-inventory-update-plants',
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
    PipeTimestamp,
    MatTooltip,
    MatDivider,
    PipeBottleStorageStatus,
    PipeStorageReason,
  ],
})
export class CompEditor implements OnInit, OnDestroy {
  @Input() editable = true;
  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();
  @Input() model: Bottle = null as unknown as Bottle;

  dispose$ = new Subject<void>();

  private actionBottle = inject(ActionBottle);
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private serviceShowMessage = inject(ServiceShowMessage);

  protected formControlPlants = new FormControl<number>(0);
  ngOnInit(): void {
    this.formControlPlants.addValidators((control) => {
      if (
        control.value === null ||
        control.value === undefined ||
        control.value < 1 ||
        control.value > 999 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '应是不小于0、小于1,000的整数。',
        };
      }
      return null;
    });
    this.formControlPlants.patchValue(this.model.plantCount);
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected requestSave() {
    if (!this.formControlPlants.valid) {
      this.formControlPlants.markAllAsTouched();
      return;
    }

    this.serviceShowConfirmDialog
      .show('确认更正瓶内株数？')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在更正株数...')),
        switchMap((_) =>
          this.actionBottle.changePlantCount({
            id: this.model.id,
            plants: this.formControlPlants.value ?? 0,
          }),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('更正成功。');
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
