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

@Component({
  selector: 'dialog-code-complement-assign-code',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
  ],
  template: `
    <h2 style="font-size: var(--mat-sys-body-large-size)" mat-dialog-title>分配瓶码</h2>
    <mat-dialog-content>
      <div [formGroup]="form" style="padding:16px 0; width: 240px;margin:0 auto;">
        <mat-form-field
          appearance="outline"
          style="width: 100%;margin-bottom:8px;"
          class="readonly">
          <mat-label>子瓶瓶码前缀</mat-label>
          <input matInput readonly [value]="data.codePrefix" />
        </mat-form-field>

        <mat-form-field
          appearance="outline"
          style="width: 100%;margin-bottom:8px;"
          class="readonly">
          <mat-label>已分配瓶码个数</mat-label>
          <input matInput readonly [value]="data.bottleCountAssigned" />
        </mat-form-field>

        <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
          <mat-label>本次分配瓶码数量</mat-label>
          <input type="number" min="1" max="9999" matInput formControlName="childBottleCount" />
          @if (form.get('childBottleCount')?.touched && form.get('childBottleCount')?.invalid) {
            <mat-error>{{ form.get('childBottleCount')?.errors?.['error'] || '' }}</mat-error>
          }
        </mat-form-field>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button matButton="text" (click)="close()">取消</button>
      <button matButton="filled" (click)="do()">分配瓶码</button>
    </mat-dialog-actions>
  `,
})
export class DialogAssignCode implements OnInit, OnDestroy {
  protected data: DialogAssignCodeInputData = inject(MAT_DIALOG_DATA);
  private readonly dialogRef =
    inject<MatDialogRef<DialogAssignCode, DialogAssignCodeResponseData>>(MatDialogRef);
  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup = this.fb.group<DialogAssignCodeResponseData>({
    codePrefix: '',
    childBottleCount: 0,
  } as DialogAssignCodeResponseData);

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    const value = { ...this.data };
    this.form.patchValue(value);

    this.form.get('childBottleCount')?.addValidators((control) => {
      if (!control.value || control.value > 9999 || !Number.isInteger(control.value)) {
        return {
          error: `应是大于 0、小于 10,000 的整数。`,
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
}

export interface DialogAssignCodeInputData {
  codePrefix: string;
  bottleCountAssigned: number;
  childBottleCount: number;
}

export interface DialogAssignCodeResponseData {
  childBottleCount: number;
  codePrefix: string;
}
