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

import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { User } from '../../../../entities/models/User';
import { SelectUser } from '../../../../components/user/select/select-user';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { InventoryAddBottleDetail } from '../../../../entities/models/InventoryAddBottleDetail';

@Component({
  selector: 'dialog-complement-batch-inventory-add-bottle',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    SelectUser,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
  ],
  template: `
    <h2 style="font-size: var(--mat-sys-body-large-size)" mat-dialog-title>批量补码</h2>
    <mat-dialog-content>
      <div [formGroup]="form" style="padding:16px 0; width: 240px;margin:0 auto;">
        @if (lastBottleSig(); as lastBottleCode) {
          <div
            style="margin-bottom:8px; font-weight: 700;text-align: center; color:var(--mat-sys-primary)"
          >
            子瓶： {{ data.childBottles[0].id }}
            至
            {{ lastBottleCode }}
          </div>
        }
        <mat-form-field
          appearance="outline"
          style="width: 100%;margin-bottom:8px;"
          floatLabel="always"
        >
          <mat-label>分配子瓶数量</mat-label>
          <input
            type="number"
            min="1"
            [max]="data.childBottles.length"
            matInput
            formControlName="childBottleCount"
          />
          @if (form.get('childBottleCount')?.touched && form.get('childBottleCount')?.invalid) {
            <mat-error>{{ form.get('childBottleCount')?.errors?.['error'] || '' }}</mat-error>
          }
        </mat-form-field>
        <mat-form-field
          appearance="outline"
          style="width: 100%;margin-bottom:8px;"
          floatLabel="always"
        >
          <mat-label>子瓶默认每瓶株数</mat-label>
          <input
            type="number"
            min="1"
            max="999"
            matInput
            formControlName="defaultPlantsPerChildBottle"
          />
          @if (
            form.get('defaultPlantsPerChildBottle')?.touched &&
            form.get('defaultPlantsPerChildBottle')?.invalid
          ) {
            <mat-error
              >{{ form.get('defaultPlantsPerChildBottle')?.errors?.['error'] || '' }}
            </mat-error>
          }
        </mat-form-field>
        <select-user
          style="display:block;width: 100%;margin-bottom:8px;"
          formControlName="inspector"
          label="质检员"
        ></select-user>
        <select-user
          style="display:block;width: 100%;margin-bottom:8px;"
          formControlName="operator"
          label="操作员"
        ></select-user>
        @if (data.generation > 0) {
          <mat-form-field
            appearance="outline"
            style="width: 100%;margin-bottom:8px;"
            floatLabel="always"
          >
            <mat-label>母瓶</mat-label>
            <input matInput formControlName="idParent" />
            @if (form.get('idParent')?.touched && form.get('idParent')?.invalid) {
              <mat-error>{{ form.get('idParent')?.errors?.['error'] || '' }} </mat-error>
            }
          </mat-form-field>
        }
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button matButton="text" (click)="close()">取消</button>
      <button matButton="filled" (click)="do()">分配</button>
    </mat-dialog-actions>
  `,
})
export class DialogComplementBatch implements OnInit, OnDestroy {
  protected data: DialogComplementBatchInputData = inject(MAT_DIALOG_DATA);
  protected lastBottleSig = signal('');
  private readonly dialogRef =
    inject<MatDialogRef<DialogComplementBatch, DialogComplementBatchResponseData>>(MatDialogRef);
  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup = this.fb.group<DialogComplementBatchResponseData>({
    idParent: '',
    inspector: null,
    operator: null,
    childBottleCount: 0,
    defaultPlantsPerChildBottle: 0,
  } as DialogComplementBatchResponseData);

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    const value = { ...this.data };
    this.form.patchValue(value);

    this.form.get('inspector')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择质检员。',
        };
      }
      return null;
    });
    this.form.get('operator')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择操作员。',
        };
      }
      return null;
    });
    this.form.get('childBottleCount')?.addValidators((control) => {
      if (
        !control.value ||
        control.value > 999 ||
        !Number.isInteger(control.value) ||
        control.value > this.data.childBottles.length
      ) {
        return {
          error: `应是大于零、小于${this.data.childBottles.length}的整数。`,
        };
      }
      return null;
    });
    this.form.get('defaultPlantsPerChildBottle')?.addValidators((control) => {
      if (!control.value || control.value > 999 || !Number.isInteger(control.value)) {
        return {
          error: '应是大于零、小于1000的整数。',
        };
      }
      return null;
    });
    this.form
      .get('childBottleCount')
      ?.valueChanges.pipe(takeUntil(this.dispose$))
      .subscribe((count) => {
        if (
          !count ||
          count > 999 ||
          !Number.isInteger(count) ||
          count > this.data.childBottles.length
        ) {
          this.lastBottleSig.set('');
        } else {
          this.lastBottleSig.set((this.data.childBottles[count - 1]?.id ?? '').split('-')[1] ?? '');
        }
      });
    if (this.data.generation > 0) {
      this.form.get('idParent')?.addValidators((control) => {
        if (!control.value) {
          return {
            error: '应填写母瓶瓶码。',
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

export interface DialogComplementBatchInputData {
  idParent: string;
  inspector: User | null;
  operator: User | null;
  childBottleCount: number;
  defaultPlantsPerChildBottle: number;
  childBottles: InventoryAddBottleDetail[];
  generation: number;
}

export interface DialogComplementBatchResponseData {
  idParent: string;
  inspector: User | null;
  operator: User | null;
  childBottleCount: number;
  defaultPlantsPerChildBottle: number;
}
