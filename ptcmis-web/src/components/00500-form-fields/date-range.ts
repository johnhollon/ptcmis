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

import {ChangeDetectionStrategy, Component, inject, input, OnDestroy, signal} from '@angular/core';
import {ControlValueAccessor, FormBuilder, FormGroup, NG_VALUE_ACCESSOR, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldAppearance, MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {isNaN} from 'lodash-es';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

export type ModelDateRange = { start: number | undefined | null, end: number | undefined | null };

@Component({
  selector: 'date-range',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    ReactiveFormsModule,
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: DateRange,
      multi: true,
    },
  ],
  template: `
    <mat-form-field [appearance]="appearance()" floatLabel="always" tabindex="-1">
      <mat-label>{{ label() }}</mat-label>
      <mat-date-range-input [formGroup]="formGroupRanger" [rangePicker]="picker">
        <input
          tabindex="-1"
          matStartDate
          formControlName="start"
          placeholder="输入起日期"
          (dateChange)="onDateChange()"
        />
        <input
          tabindex="-1"
          matEndDate
          formControlName="end"
          placeholder="输入止日期"
          (dateChange)="onDateChange()"
        />
      </mat-date-range-input>
      @if (showHint()) {
        <mat-hint>YYYY‑MM‑DD 至 YYYY‑MM‑DD</mat-hint>
      }
      <mat-datepicker-toggle matSuffix [for]="picker"></mat-datepicker-toggle>
      <mat-date-range-picker #picker>
        <mat-date-range-picker-actions>
          <button matButton matDateRangePickerCancel tabindex="-1">关闭</button>
          @if (showClearButton()) {
            <button matButton="elevated" matDateRangePickerCancel (click)="clear()" tabindex="-1">
              清除
            </button>
          }
          <button matButton="filled" matDateRangePickerApply tabindex="-1">确认</button>
        </mat-date-range-picker-actions>
      </mat-date-range-picker>
    </mat-form-field>
  `,
  styles: [],
})
export class DateRange implements ControlValueAccessor, OnDestroy {
  readonly label = input<string>('起止日期');
  readonly showHint = input<boolean>(true);
  protected readonly appearance = input<MatFormFieldAppearance>('outline');
  // 是否显示清除按钮
  protected readonly showClearButton = input<boolean>(true);
  private readonly isDisabled = signal(false);

  protected formGroupRanger: FormGroup;
  private dispose$ = new Subject<void>();
  private readonly fb = inject(FormBuilder);

  private onChange?: (v: ModelDateRange) => void;
  private onTouched?: () => void;

  constructor() {
    this.formGroupRanger = this.fb.group({
      start: [null as Date | null],
      end: [null as Date | null],
    });

    this.formGroupRanger.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((val) => {
      const { start, end } = val;
      const s =
        start instanceof Date && !isNaN(start.getTime())
          ? new Date(start.getFullYear(), start.getMonth(), start.getDate(), 0, 0, 0, 0).getTime()
          : null;
      const e =
        end instanceof Date && !isNaN(end.getTime())
          ? new Date(end.getFullYear(), end.getMonth(), end.getDate(), 23, 59, 59, 999).getTime()
          : null;

      this.onChange?.({ start: s, end: e });
    });
  }

  writeValue(obj: ModelDateRange | null | undefined): void {
    if (!obj) {
      this.formGroupRanger.reset({ start: null, end: null }, { emitEvent: false });
    } else {
      const { start, end } = obj;
      const startDate = typeof start === 'number' && start > 0 ? new Date(start) : null;
      const endDate = typeof end === 'number' && end > 0 ? new Date(end) : null;
      this.formGroupRanger.setValue({ start: startDate, end: endDate }, { emitEvent: false });
    }
  }

  registerOnChange(fn: (v: ModelDateRange) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (this.isDisabled() === isDisabled) return;
    this.isDisabled.set(isDisabled);
    isDisabled
      ? this.formGroupRanger.disable({ emitEvent: false })
      : this.formGroupRanger.enable({ emitEvent: false });
  }

  protected clear() {
    this.formGroupRanger.setValue({ start: null, end: null }, { emitEvent: false });
  }
  protected onDateChange() {
    this.onTouched?.();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
