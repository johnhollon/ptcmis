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
import { MatFormField, MatLabel } from '@angular/material/input';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOption, MatSelect } from '@angular/material/select';

@Component({
  selector: 'dialog-discard',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatFormField,
    MatLabel,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelect,
    MatOption,
  ],
  template: `
    <h2 style="font-size: var(--mat-sys-body-large-size)" mat-dialog-title>
      {{data.title}}：{{ data.idBottle }}
    </h2>
    <mat-dialog-content>
      <div [formGroup]="form" style="padding:16px 0; width: 240px;margin:0 auto;">
        <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
          <mat-label>淘汰原因</mat-label>
          <mat-select formControlName="reason">
            <mat-option value="检测淘汰">检测淘汰</mat-option>
            <mat-option value="机械损伤">机械损伤</mat-option>
            <mat-option value="培养基失效">培养基失效</mat-option>
            <mat-option value="培养基干缩">培养基干缩</mat-option>
            <mat-option value="细菌污染">细菌污染</mat-option>
            <mat-option value="真菌污染">真菌污染</mat-option>
            <mat-option value="内源菌隐污">内源菌隐污</mat-option>
            <mat-option value="玻璃化">玻璃化</mat-option>
            <mat-option value="褐化坏死">褐化坏死</mat-option>
            <mat-option value="黄化衰老">黄化衰老</mat-option>
            <mat-option value="畸形变异">畸形变异</mat-option>
            <mat-option value="愈伤过旺">愈伤过旺</mat-option>
            <mat-option value="苗体徒长">苗体徒长</mat-option>
            <mat-option value="整瓶枯死">整瓶枯死</mat-option>
            <mat-option value="其它原因">其它原因</mat-option>
          </mat-select>
        </mat-form-field>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button matButton="text" (click)="close()">取消</button>
      <button matButton="filled" (click)="do()">确认</button>
    </mat-dialog-actions>
  `,
})
export class DialogTaskBottleDiscard implements OnInit, OnDestroy {
  protected data: DialogTaskBottleDiscardData = inject(MAT_DIALOG_DATA);
  private readonly dialogRef =
    inject<MatDialogRef<DialogTaskBottleDiscard, DialogTaskBottleDiscardData>>(MatDialogRef);
  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup = this.fb.group<DialogTaskBottleDiscardData>({
    idBottle: '',
    reason: '',
  } as DialogTaskBottleDiscardData);

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    this.form.patchValue({ ...this.data });

    this.form.get('reason')?.addValidators((control) => {
      if (!control.value || !control.value.trim()) {
        return {
          error: '应填写淘汰原因。',
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

export interface DialogTaskBottleDiscardData {
  title:string;
  idBottle: string;
  reason: string;
}
