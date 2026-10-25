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
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'dialog-update-password',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatFormField,
    MatLabel,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInput,
  ],
  template: `
    <h2 style="font-size: var(--mat-sys-body-large-size)" mat-dialog-title>更改密码</h2>
    <mat-dialog-content>
      <div [formGroup]="form" style="padding:16px 0; width: 240px;margin:0 auto;">
        <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
          <mat-label>原密码</mat-label>
          <input matInput type="password" formControlName="old" />
          @if (form.get('old')?.touched && form.get('old')?.invalid) {
            <mat-error>{{ form.get('old')?.errors?.['error'] || '' }}</mat-error>
          }
        </mat-form-field>
        <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
          <mat-label>新密码</mat-label>
          <input matInput type="password" formControlName="new" />
          @if (form.get('new')?.touched && form.get('new')?.invalid) {
            <mat-error>{{ form.get('new')?.errors?.['error'] || '' }}</mat-error>
          }
        </mat-form-field>
        <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
          <mat-label>重复新密码</mat-label>
          <input matInput type="password" formControlName="newRepeat" />
          @if (form.get('newRepeat')?.touched && form.get('newRepeat')?.invalid) {
            <mat-error>{{ form.get('newRepeat')?.errors?.['error'] || '' }}</mat-error>
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
export class DialogUpdatePassword implements OnInit, OnDestroy {
  protected data: DialogUpdatePasswordData = inject(MAT_DIALOG_DATA);
  private readonly dialogRef =
    inject<MatDialogRef<DialogUpdatePassword, DialogUpdatePasswordData>>(MatDialogRef);
  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup = this.fb.group<DialogUpdatePasswordData>({
    new: '',
    newRepeat: '',
    old: '',
  } as DialogUpdatePasswordData);

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    this.form.patchValue({ ...this.data });

    this.form.get('new')?.addValidators((control) => {
      if (!control.value || !control.value.trim()) {
        return {
          error: '应填写新密码。',
        };
      }
      return null;
    });
    this.form.get('newRepeat')?.addValidators((control) => {
      if (!control.value || !control.value.trim()) {
        return {
          error: '应填写此项。',
        };
      }
      if (control.value != this.form.get('new')?.value) {
        return {
          error: '输入不一致。',
        };
      }
      return null;
    });
    this.form.get('old')?.addValidators((control) => {
      if (!control.value || !control.value.trim()) {
        return {
          error: '应填写原密码。',
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

export interface DialogUpdatePasswordData {
  new: string;
  newRepeat: string;
  old: string;
}
