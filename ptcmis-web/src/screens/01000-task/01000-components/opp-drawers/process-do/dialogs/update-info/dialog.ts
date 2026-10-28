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

import { Component, ElementRef, inject, OnDestroy, OnInit, signal, viewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { User } from '../../../../../../../entities/models/User';
import { SelectUser } from '../../../../../../../components/user/select/select-user';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from '@angular/material/autocomplete';
import { MatIcon } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltip } from '@angular/material/tooltip';
import { SelectVarietyCodeSimple } from '../../../../../../../components/variety-code/select/select-variety-code-simple';

@Component({
  selector: 'dialog-update-info',
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
    MatAutocomplete,
    MatOption,
    MatIcon,
    MatAutocompleteTrigger,
    MatFormFieldModule,
    MatTooltip,
    SelectVarietyCodeSimple,
  ],
  template: `
    <h2 style="font-size: var(--mat-sys-body-large-size)" mat-dialog-title>
      更改作业：{{
        data.idParentBottle || data.parentPlantCount > 0 ? data.parentPlantCount + '株' : ''
      }}
    </h2>
    <mat-dialog-content>
      <div [formGroup]="form" style="padding:16px 0; width: 240px;margin:0 auto;">
        @if (!data.idParentBottle) {
          <select-variety-code-simple
            style="width: 100%;margin-bottom:8px;"
            label="输入品种编号"
            [canSelectEmpty]="true"
            optionEmptyText="尚未选择"
            formControlName="varietyCode"
            [variety]="data.variety ?? ''"
          ></select-variety-code-simple>
          @if (form.get('varietyCode')?.touched && form.get('varietyCode')?.invalid) {
            <mat-error>{{ form.get('varietyCode')?.errors?.['error'] || '' }}</mat-error>
          }
        }
        <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
          <mat-label>母代株数</mat-label>
          <input type="number" min="0" max="999" matInput formControlName="parentPlantCount" />
          @if (form.get('parentPlantCount')?.touched && form.get('parentPlantCount')?.invalid) {
            <mat-error>{{ form.get('parentPlantCount')?.errors?.['error'] || '' }}</mat-error>
          }
        </mat-form-field>
        @if (!!data.idParentBottle) {
          <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
            <mat-label>更换母瓶</mat-label>
            <button
              matIconButton
              matSuffix
              (click)="requestResetIdParentBottle()"
              matTooltip="撤销更换"
            >
              <mat-icon>replay</mat-icon>
            </button>
            <input
              matInput
              #idParentBottleInput
              formControlName="idParentBottle"
              [matAutocomplete]="autoParentBottles"
              (input)="filterParentBottles()"
              (focus)="filterParentBottles()"
            />
            <mat-autocomplete requireSelection="true" #autoParentBottles="matAutocomplete">
              @for (option of filteredParentBottles(); track option) {
                <mat-option [value]="option">{{ option }}</mat-option>
              }
            </mat-autocomplete>
            @if (form.get('idParentBottle')?.touched && form.get('idParentBottle')?.invalid) {
              <mat-error>{{ form.get('idParentBottle')?.errors?.['error'] || '' }}</mat-error>
            }
          </mat-form-field>
          @if (data.showParentKeep) {
            <mat-form-field appearance="outline" style="width: 100%;margin-bottom:8px;">
              <mat-label>留种株数</mat-label>
              <input
                type="number"
                min="0"
                max="999"
                matInput
                formControlName="keepParentPlantCount"
              />
              @if (
                form.get('keepParentPlantCount')?.touched &&
                form.get('keepParentPlantCount')?.invalid
              ) {
                <mat-error
                  >{{ form.get('keepParentPlantCount')?.errors?.['error'] || '' }}
                </mat-error>
              }
            </mat-form-field>
          }
        }
        <select-user
          style="display:block;width: 100%;margin-bottom:8px;"
          formControlName="inspector"
          label="更换质检员"
          groupNameImportant="任务内"
          groupNameOther="任务外"
          [usersImportant]="data.inspectors"
        ></select-user>
        <select-user
          style="display:block;width: 100%;margin-bottom:8px;"
          formControlName="operator"
          label="更换操作员"
          groupNameImportant="任务内"
          groupNameOther="任务外"
          [usersImportant]="data.operators"
        ></select-user>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button matButton="text" (click)="close()">取消</button>
      <button matButton="filled" (click)="do()">更正</button>
    </mat-dialog-actions>
  `,
})
export class DialogTaskOppGroupUpdate implements OnInit, OnDestroy {
  protected data: DialogTaskOppGroupUpdateInputData = inject(MAT_DIALOG_DATA);
  private readonly dialogRef =
    inject<MatDialogRef<DialogTaskOppGroupUpdate, DialogTaskOppGroupUpdateResponseData>>(
      MatDialogRef,
    );
  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup = this.fb.group<DialogTaskOppGroupUpdateResponseData>({
    variety: '',
    varietyCode: '',
    idOppGroup: '',
    idParentBottle: '',
    idParentBottleOriginal: '',
    parentPlantCount: 0,
    inspector: null,
    operator: null,
    keepParentPlantCount: 0,
  } as DialogTaskOppGroupUpdateResponseData);

  private idParentBottleOriginal: string = '';
  private idParentBottles: string[] = [];

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    this.idParentBottleOriginal = this.data.idParentBottle;

    if (this.idParentBottleOriginal) {
      this.idParentBottles.push(this.idParentBottleOriginal);
    }
    this.idParentBottles.push(...(this.data.parentCodedBottles ?? []));
    this.idParentBottles = this.idParentBottles.sort((a, b) => a.localeCompare(b));
    this.filteredParentBottles.set([...this.idParentBottles]);

    this.form.patchValue({
      ...this.data,
      idParentBottleOriginal: this.idParentBottleOriginal,
      idOppGroup: this.data.idOppGroup,
    });

    this.form.get('varietyCode')?.addValidators((control) => {
      if (this.data.taskType === 'induction' && !control.value) {
        return {
          error: '应选择品种编号。',
        };
      }
      return null;
    });
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
    if (!this.data.idParentBottle) {
      this.form.get('parentPlantCount')?.addValidators((control) => {
        if (!control.value || control.value > 999 || !Number.isInteger(control.value)) {
          return {
            error: '应是大于零、小于1000的整数。',
          };
        }
        return null;
      });
    } else {
      this.form.get('idParentBottle')?.addValidators((control) => {
        if (!control.value) {
          return {
            error: '必须选择母瓶。',
          };
        }
        return null;
      });
      if (this.data.showParentKeep) {
        this.form.get('keepParentPlantCount')?.addValidators((control) => {
          if (
            control.value !== null &&
            control.value !== undefined &&
            (control.value < 0 || control.value > 999 || !Number.isInteger(control.value))
          ) {
            return {
              error: '应是不小于零、小于1000的整数。',
            };
          }
          return null;
        });
      }
    }
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  filteredParentBottles = signal<string[]>([]);
  idParentBottleInput = viewChild.required<ElementRef<HTMLInputElement>>('idParentBottleInput');
  filterParentBottles(): void {
    const filterValue = this.idParentBottleInput().nativeElement.value.toUpperCase();
    this.filteredParentBottles.set(
      this.idParentBottles.filter((o) => o.toUpperCase().includes(filterValue)),
    );
  }

  protected requestResetIdParentBottle() {
    this.form.get('idParentBottle')?.setValue(this.data.idParentBottle);
  }

  protected close(): void {
    this.dialogRef.close();
  }

  protected do(): void {
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }
    this.dialogRef.close({ ...this.form.getRawValue(), variety: this.data.variety });
  }
}

export interface DialogTaskOppGroupUpdateInputData {
  taskType: string;
  variety: string;
  varietyCode: string;
  idOppGroup: string;
  idParentBottle: string;
  parentPlantCount: number;
  keepParentPlantCount: number;
  inspector: string;
  inspectors: User[];
  operator: string;
  operators: User[];
  parentCodedBottles: string[];
  showParentKeep: boolean;
}

export interface DialogTaskOppGroupUpdateResponseData {
  variety: string;
  varietyCode: string;
  idOppGroup: string;
  idParentBottleOriginal: string;
  idParentBottle: string;
  parentPlantCount: number;
  inspector: User | null;
  operator: User | null;
  keepParentPlantCount: number;
}
