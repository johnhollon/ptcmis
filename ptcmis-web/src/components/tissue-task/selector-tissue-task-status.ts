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

import {ChangeDetectionStrategy, Component, input, OnDestroy, signal} from '@angular/core';
import {ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR, ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldAppearance, MatFormFieldModule} from '@angular/material/form-field';
import {MatOption, MatSelect} from '@angular/material/select';
import {Subject, takeUntil} from 'rxjs';
import {TissueTaskStatus} from '../../entities/models/TissueTask';
import {PipeTaskStatus} from '../00200-pipes/pipe-task-status';

@Component({
  selector: 'selector-tissue-task-status',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [MatFormFieldModule, ReactiveFormsModule, MatSelect, MatOption, PipeTaskStatus],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: SelectorTissueTaskStatus,
      multi: true,
    },
  ],
  template: `
    <mat-form-field [appearance]="appearance()" floatLabel="always" tabindex="-1">
      <mat-label>{{ label() }}</mat-label>
      <mat-select [formControl]="formControl" tabIndex="-1">
        @if (canShowNone()) {
          <mat-option value="">所有状态</mat-option>
        }
        <mat-option value="created">{{ 'created' | taskStatus }}</mat-option>
        <mat-option value="processing">{{ 'processing' | taskStatus }}</mat-option>
        <mat-option value="finished">{{ 'finished' | taskStatus }}</mat-option>
      </mat-select>
    </mat-form-field>
  `,
  styles: [],
})
export class SelectorTissueTaskStatus implements ControlValueAccessor, OnDestroy {
  // 输入框 Label
  readonly label = input<string>('');
  protected readonly appearance = input<MatFormFieldAppearance>('outline');
  // 是否显示所有类别Option
  protected readonly canShowNone = input<boolean>(true);
  private readonly isDisabled = signal(false);

  protected formControl = new FormControl<TissueTaskStatus | ''>('', { nonNullable: true });

  private dispose$ = new Subject<void>();

  private onTouched?: () => void;
  private onChange?: (v: TissueTaskStatus | '') => void;

  constructor() {
    this.formControl.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((value) => {
      if (this.formControl.touched) this.onTouched?.();
      this.onChange?.(value);
    });
  }

  writeValue(obj: TissueTaskStatus | null | undefined): void {
    this.formControl.setValue(obj ?? '', { emitEvent: false });
  }

  registerOnChange(fn: (v: TissueTaskStatus | '') => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.isDisabled.set(isDisabled);
    isDisabled
      ? this.formControl.disable({ emitEvent: false })
      : this.formControl.enable({ emitEvent: false });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
