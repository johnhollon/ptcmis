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

import { Component, computed, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { MatCheckbox } from '@angular/material/checkbox';
import { User } from '../../../../../../../entities/models/User';
import { takeUntil } from 'rxjs/operators';
import { ActionUser } from '../../../../../../../services/actions/action-user';
import { ServiceShowMessage } from '../../../../../../../services/ui/service-show-error-message.service';
import { MatDivider } from '@angular/material/list';

@Component({
  selector: 'dialog-set-task-process-settings',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule,
    MatCheckbox,
    MatDivider,
  ],
  template: `
    <h2 style="font-size: var(--mat-sys-body-large-size)" mat-dialog-title>设置任务分配默认参数</h2>
    <mat-dialog-content>
      <div
        style="padding:16px 0; margin:0 auto; overflow-x:hidden;display: flex;flex-direction: column;align-items: stretch;"
      >
        @if (data.showDefaultPlantsPerChildBottle) {
          <mat-form-field appearance="outline" floatLabel="always">
            <mat-label>子代每瓶默认株数</mat-label>
            <input matInput type="number" [formControl]="plantsCountForChildField" />
            @if (plantsCountForChildField.touched && plantsCountForChildField.invalid) {
              <mat-error>{{ plantsCountForChildField.errors?.['error'] || '' }}</mat-error>
            }
          </mat-form-field>
        }
        <div
          style="min-width:0;padding:12px 0;display: flex; gap: 12px; align-items: stretch; border:solid 1px var(--mat-sys-level-shadow-color-1); border-radius: var(--mat-sys-corner-medium); margin:12px 0 0 0;"
        >
          <div style="flex: 1;">
            <h3 style="font-size: var(--mat-sys-body-medium-size); text-align:center;">
              选择质检员
            </h3>
            <div style="height:300px;overflow-x:hidden;overflow-y:auto; padding:12px;">
              @for (item of usersSig(); track item.id) {
                <mat-checkbox
                  [checked]="!!inspectorIdsSig().has(item.id)"
                  (change)="updateInspector($event.checked, item)"
                >
                  {{ item.name }}({{ item.code }})
                </mat-checkbox>
              }
            </div>
          </div>

          <mat-divider [vertical]="true"></mat-divider>

          <div style="flex: 1;">
            <h3 style="font-size: var(--mat-sys-body-medium-size); text-align:center;">
              选择操作员
            </h3>
            <div style="height:300px;overflow-x:hidden;overflow-y:auto; padding:12px;">
              @for (item of usersSig(); track item.id) {
                <mat-checkbox
                  [checked]="!!operatorIdsSig().has(item.id)"
                  (change)="updateOperator($event.checked, item)"
                >
                  {{ item.name }}({{ item.code }})
                </mat-checkbox>
              }
            </div>
          </div>
        </div>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button matButton="text" (click)="close()">取消</button>
      <button matButton="filled" (click)="do()">保存</button>
    </mat-dialog-actions>
  `,
})
export class DialogTaskSetProcessSettings implements OnInit, OnDestroy {
  private readonly actionUser = inject(ActionUser);
  private serviceShowMessage = inject(ServiceShowMessage);

  protected data: DialogTaskSetProcessSettingsInputData = inject(MAT_DIALOG_DATA);
  private readonly dialogRef =
    inject<MatDialogRef<DialogTaskSetProcessSettings, DialogTaskSetProcessSettingsResponseData>>(
      MatDialogRef,
    );
  protected readonly plantsCountForChildField = new FormControl<number | null>(null);
  protected readonly usersSig = signal<User[]>([]);
  protected readonly inspectorsSig = signal<User[]>([]);
  protected readonly inspectorIdsSig = computed(() => {
    return new Set(this.inspectorsSig().map((u) => u.id));
  });
  protected readonly operatorsSig = signal<User[]>([]);
  protected readonly operatorIdsSig = computed(() => {
    return new Set(this.operatorsSig().map((u) => u.id));
  });

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    const value = { ...this.data };
    if (this.data.showDefaultPlantsPerChildBottle) {
      this.plantsCountForChildField.setValue(value.defaultPlantsPerChildBottle || null);
      this.plantsCountForChildField.addValidators((control) => {
        if (!control.value || control.value > 999 || !Number.isInteger(control.value)) {
          return {
            error: `应是大于0、小于1,000的整数。`,
          };
        }
        return null;
      });
    }
    this.inspectorsSig.set(this.data.inspectors || []);
    this.operatorsSig.set(this.data.operators || []);
    const allUsers = this.actionUser.userEnabledList;
    if (!allUsers || allUsers.length < 1) {
      this.actionUser
        .queryAllEnabled()
        .pipe(takeUntil(this.dispose$))
        .subscribe({
          next: (users) => this.usersSig.set(users),
          error: (error) => this.serviceShowMessage.showError(error),
        });
    } else {
      this.usersSig.set(allUsers);
    }
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  updateInspector(selected: boolean, user: User): void {
    if (selected) {
      this.inspectorsSig.update((arr) => [...new Set([...arr, user])]);
    } else {
      this.inspectorsSig.update((arr) => arr.filter((x) => x.id !== user.id));
    }
  }

  updateOperator(selected: boolean, user: User): void {
    if (selected) {
      this.operatorsSig.update((arr) => [...new Set([...arr, user])]);
    } else {
      this.operatorsSig.update((arr) => arr.filter((x) => x.id !== user.id));
    }
  }

  protected close(): void {
    this.dialogRef.close();
  }

  protected do(): void {
    if (this.data.showDefaultPlantsPerChildBottle) {
      if (!this.plantsCountForChildField.valid) {
        this.plantsCountForChildField.markAllAsTouched();
        return;
      }
    }
    this.dialogRef.close({
      idTask: this.data.idTask,
      defaultPlantsPerChildBottle: this.plantsCountForChildField.value ?? 0,
      inspectors: this.inspectorsSig(),
      operators: this.operatorsSig(),
    });
  }
}

export interface DialogTaskSetProcessSettingsInputData {
  idTask: string;
  showDefaultPlantsPerChildBottle: boolean;
  defaultPlantsPerChildBottle: number;
  inspectors: User[];
  operators: User[];
}

export interface DialogTaskSetProcessSettingsResponseData {
  idTask: string;
  defaultPlantsPerChildBottle: number;
  inspectors: User[];
  operators: User[];
}
