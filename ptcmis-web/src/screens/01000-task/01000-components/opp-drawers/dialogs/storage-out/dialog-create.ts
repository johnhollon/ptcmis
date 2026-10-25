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
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { StorageInOut } from '../../../../../../entities/models/StorageInOut';

@Component({
  selector: 'dialog-task-storage-out-create',
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
    <h2 style="font-size: var(--mat-sys-body-large-size)" mat-dialog-title>申请出库</h2>
    <mat-dialog-content>
      <div
        style="padding:16px 0; margin:0 auto; overflow-x:hidden;display: flex;flex-direction: column;align-items: stretch;"
      >
        <div
          class="form-container main-container"
          data-content-for="content"
          [formGroup]="formGroup"
          style="display: flex;gap:var(--mat-sys-corner-medium)"
        >
          <fieldset
            class="form-container"
            style="padding:var(--mat-sys-corner-large);  border:solid 1px var(--mat-sys-border-color-3); border-radius: var(--mat-sys-corner-medium);"
          >
            <legend>出库瓶数</legend>

            <mat-form-field appearance="outline" class="readonly" floatLabel="always">
              <mat-label>预计使用母代瓶数</mat-label>
              <input matInput [value]="data.planBottles" readonly />
            </mat-form-field>

            <mat-form-field appearance="outline" class="readonly" floatLabel="always">
              <mat-label>累计申请母代瓶数</mat-label>
              <input matInput [value]="data.applyBottles" readonly />
            </mat-form-field>

            <mat-form-field appearance="outline" class="readonly" floatLabel="always">
              <mat-label>累计已备出母代瓶数</mat-label>
              <input matInput [value]="data.doneBottles" readonly />
            </mat-form-field>

            <mat-form-field appearance="outline" class="readonly" floatLabel="always">
              <mat-label>累计已接收母代瓶数</mat-label>
              <input matInput [value]="data.outBottles" readonly />
            </mat-form-field>

            <mat-form-field appearance="outline" class="important" floatLabel="always">
              <mat-label>本次申请出库母代瓶数</mat-label>
              <input matInput type="number" min="1" formControlName="bottles" />
              @if (formGroup.get('bottles')?.touched && formGroup.get('bottles')?.invalid) {
                <mat-error>{{ formGroup.get('bottles')?.errors?.['error'] || '' }} </mat-error>
              }
            </mat-form-field>
          </fieldset>

          <fieldset
            class="form-container"
            style="padding:var(--mat-sys-corner-large);  border:solid 1px var(--mat-sys-border-color-3); border-radius: var(--mat-sys-corner-medium);"
          >
            <legend>出库株数</legend>

            <mat-form-field appearance="outline" class="readonly" floatLabel="always">
              <mat-label>预计使用母代株数</mat-label>
              <input matInput [value]="data.planPlants" readonly />
            </mat-form-field>

            <mat-form-field appearance="outline" class="readonly" floatLabel="always">
              <mat-label>累计申请母代株数</mat-label>
              <input matInput [value]="data.applyPlants" readonly />
            </mat-form-field>

            <mat-form-field appearance="outline" class="readonly" floatLabel="always">
              <mat-label>累计已备出母代株数</mat-label>
              <input matInput [value]="data.donePlants" readonly />
            </mat-form-field>

            <mat-form-field appearance="outline" class="readonly" floatLabel="always">
              <mat-label>累计已接收母代株数</mat-label>
              <input matInput [value]="data.outPlants" readonly />
            </mat-form-field>

            <mat-form-field appearance="outline" class="important" floatLabel="always">
              <mat-label>本次申请出库母代株数</mat-label>
              <input matInput type="number" min="1" formControlName="plants" />
              @if (formGroup.get('plants')?.touched && formGroup.get('plants')?.invalid) {
                <mat-error>{{ formGroup.get('plants')?.errors?.['error'] || '' }} </mat-error>
              }
            </mat-form-field>
          </fieldset>
        </div>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button matButton="text" (click)="close()">取消</button>
      <button matButton="filled" (click)="do()">保存</button>
    </mat-dialog-actions>
  `,
})
export class DialogTaskCreateStorageOut implements OnInit, OnDestroy {
  protected data: DialogTaskCreateStorageOutInputData = inject(MAT_DIALOG_DATA);
  private readonly dialogRef =
    inject<MatDialogRef<DialogTaskCreateStorageOut, DialogTaskCreateStorageOutResponseData>>(
      MatDialogRef,
    );

  private isNew = false;

  private fb = inject(FormBuilder);
  protected readonly formGroup: FormGroup = this.fb.group({ bottles: 0, plants: 0 });

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    this.formGroup.get('bottles')?.addValidators((control: AbstractControl) => {
      if (
        !control.value ||
        control.value < 1 ||
        control.value > 999 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '应大于0，且小于 1,000。',
        };
      }
      return null;
    });
    this.formGroup.get('plants')?.addValidators((control: AbstractControl) => {
      if (
        !control.value ||
        control.value < 1 ||
        control.value > 9999 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '应大于0，且小于 10,000。',
        };
      }
      return null;
    });

    this.isNew = !this.data.storageInOut;
    let bottles = Math.max(0, this.data.planBottles - this.data.applyBottles);
    let plants = Math.max(0, this.data.planPlants - this.data.applyPlants);

    if (!this.isNew) {
      bottles = this.data.storageInOut?.bottlesApplied || 0;
      plants = this.data.storageInOut?.plantsApplied || 0;
    }

    this.formGroup.setValue({ bottles, plants }, { onlySelf: true, emitEvent: false });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected close(): void {
    this.dialogRef.close();
  }

  protected do(): void {
    this.formGroup.markAllAsTouched();
    if (!this.formGroup.valid) {
      return;
    }
    const { bottles, plants } = this.formGroup.getRawValue();
    this.dialogRef.close({
      bottles,
      plants,
      isNew: this.isNew,
    });
  }
}

export interface DialogTaskCreateStorageOutInputData {
  planBottles: number;
  planPlants: number;
  applyBottles: number;
  applyPlants: number;
  doneBottles: number;
  donePlants: number;
  outBottles: number;
  outPlants: number;
  storageInOut: StorageInOut | null;
}

export interface DialogTaskCreateStorageOutResponseData {
  bottles: number;
  plants: number;
  isNew: boolean;
}
