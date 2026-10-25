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

import {Component, Input, OnDestroy, OnInit, signal, ViewChild} from '@angular/core';
import {ControlContainer, ControlValueAccessor, FormControl, FormControlDirective, NG_VALUE_ACCESSOR, ReactiveFormsModule,} from '@angular/forms';
import {MatFormFieldAppearance} from '@angular/material/form-field';
import {MatError, MatFormField, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {PipeRole} from '../../00200-pipes/pipe-role';

@Component({
  selector: 'select-user-role',
  standalone: true,
  template: `
    <mat-form-field [appearance]="appearance" floatLabel="always" tabindex="-1">
      <mat-label>{{ label }}</mat-label>
      <mat-select [formControl]="control" tabindex="-1">
        @if (canSelectEmpty) {
          <mat-option value="">{{ optionEmptyText }}</mat-option>
        }
        <mat-option value="none">{{ 'none' | role }}</mat-option>
        <mat-option value="query">{{ 'query' | role }}</mat-option>
        <mat-option value="task">{{ 'task' | role }}</mat-option>
        <mat-option value="inventory">{{ 'inventory' | role }}</mat-option>
        <mat-option value="task_and_inventory">{{ 'task_and_inventory' | role }}</mat-option>
        <mat-option value="admin">{{ 'admin' | role }}</mat-option>
      </mat-select>
      @if (hasErrorsSig()) {
        <mat-error>{{ errorMessageSig() }}</mat-error>
      }
    </mat-form-field>
  `,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: SelectUserRole,
    },
  ],
  imports: [MatFormField, MatLabel, MatSelect, MatOption, MatError, ReactiveFormsModule, PipeRole],
})
export class SelectUserRole implements ControlValueAccessor, OnInit, OnDestroy {
  @Input() label: string = '系统权限';
  @Input() appearance: MatFormFieldAppearance = 'outline';
  @Input() canSelectEmpty: boolean = false;
  @Input() optionEmptyText: string = '全部';

  protected hasErrorsSig = signal(false);
  protected errorMessageSig = signal('必须选择系统权限');

  @Input() formControl?: FormControl;
  @Input() formControlName = '';
  get control() {
    return (
      this.formControl ||
      (this.formControlContainer?.control?.get(this.formControlName) as FormControl) ||
      (null as unknown as FormControl)
    );
  }
  @ViewChild(FormControlDirective, { static: true }) childControl?: FormControlDirective;

  private dispose$ = new Subject<void>();

  constructor(private formControlContainer: ControlContainer) {}

  ngOnInit(): void {
    this.control?.statusChanges?.pipe(takeUntil(this.dispose$)).subscribe((_) => {
      this.calculateErrors();
    });
    this.calculateErrors();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  calculateErrors() {
    const errors = this.control?.errors;
    if (errors) {
      const errorKeys = Object.keys(errors);
      if (errorKeys && errorKeys.length > 0) {
        this.hasErrorsSig.set(true);
        for (const key of errorKeys) {
          const errorMsg = errors[key];
          if (errorMsg) {
            this.errorMessageSig.set(errorMsg);
            break;
          }
        }
      }
    }
  }

  writeValue(obj: any): void {
    this.childControl?.valueAccessor?.writeValue?.(obj);
  }
  registerOnChange(fn: any): void {
    this.childControl?.valueAccessor?.registerOnChange?.(fn);
  }
  registerOnTouched(fn: any): void {
    this.childControl?.valueAccessor?.registerOnTouched?.(fn);
  }
  setDisabledState?(isDisabled: boolean): void {
    this.childControl?.valueAccessor?.setDisabledState?.(isDisabled);
  }
}
