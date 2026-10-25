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

import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { MatIcon } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
  selector: 'dialog-update-plant',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatIcon,
    MatFormFieldModule,
    MatTooltip,
  ],
  template: `
    <h2 style="font-size: var(--mat-sys-body-large-size)" mat-dialog-title>
      更改株数：{{ data.idBottle }}
    </h2>
    <mat-dialog-content>
      <div [formGroup]="form" style="padding:16px 0; width: 240px;margin:0 auto;">
        <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
          <mat-label>瓶内株数</mat-label>
          <button matIconButton matSuffix (click)="requestReset()" matTooltip="撤销更改">
            <mat-icon>replay</mat-icon>
          </button>
          <input type="number" min="0" max="999" matInput formControlName="plantCount" />
          @if (form.get('plantCount')?.touched && form.get('plantCount')?.invalid) {
            <mat-error>{{ form.get('plantCount')?.errors?.['error'] || '' }}</mat-error>
          }
        </mat-form-field>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button matButton="text" (click)="close()">取消</button>
      <button matButton="filled" (click)="do()">确认</button>
    </mat-dialog-actions>
  `,
})
export class DialogTaskBottlePlantUpdate implements OnInit, OnDestroy {
  protected data: DialogTaskBottlePlantUpdateData = inject(MAT_DIALOG_DATA);
  private readonly dialogRef =
    inject<MatDialogRef<DialogTaskBottlePlantUpdate, DialogTaskBottlePlantUpdateData>>(
      MatDialogRef,
    );
  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup = this.fb.group<DialogTaskBottlePlantUpdateData>({
    idBottle: '',
    plantCount: 0,
  } as DialogTaskBottlePlantUpdateData);

  protected plantCountOriginal = 0;

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    this.plantCountOriginal = this.data.plantCount;

    this.form.patchValue({ ...this.data });

    this.form.get('plantCount')?.addValidators((control) => {
      if (!control.value || control.value > 999 || !Number.isInteger(control.value)) {
        return {
          error: '应是大于零、小于1000的整数。',
        };
      }
      return null;
    });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected close(): void {
    this.dialogRef.close();
  }

  protected do(): void {
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }
    this.dialogRef.close(this.form.getRawValue());
  }

  protected requestReset() {
    this.form.get('plantCount')?.setValue(this.plantCountOriginal);
  }
}

export interface DialogTaskBottlePlantUpdateData {
  idBottle: string;
  plantCount: number;
}
