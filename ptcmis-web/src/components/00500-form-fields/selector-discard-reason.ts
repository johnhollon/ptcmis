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

import { ChangeDetectionStrategy, Component, input, OnDestroy, signal } from '@angular/core';
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';
import { FloatLabelType, MatFormFieldAppearance, MatFormFieldModule } from '@angular/material/form-field';
import { MatOption, MatSelect } from '@angular/material/select';
import { Subject, takeUntil } from 'rxjs';
import { TissueTaskType } from '../../entities/models/TissueTask';

@Component({
  selector: 'selector-discard-reason',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [MatFormFieldModule, ReactiveFormsModule, MatSelect, MatOption],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: SelectorDiscardReason,
      multi: true,
    },
  ],
  template: `
    <mat-form-field [appearance]="appearance()" [floatLabel]="floatLabel()">
      <mat-label>{{ label() }}</mat-label>
      <mat-select [formControl]="formControl">
        @if (canShowNone()) {
          <mat-option value="">{{ noneLabel() }}</mat-option>
        }
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
  `,
  styles: [':host { display: contents; }'],
})
export class SelectorDiscardReason implements ControlValueAccessor, OnDestroy {
  // 输入框 Label
  readonly label = input<string>('淘汰原因');
  readonly floatLabel = input<FloatLabelType>('always');
  protected readonly appearance = input<MatFormFieldAppearance>('outline');
  protected readonly canShowNone = input<boolean>(false);
  protected readonly noneLabel = input<string>('未设定');
  private readonly isDisabled = signal(false);

  protected formControl = new FormControl<TissueTaskType | ''>('', { nonNullable: true });

  private dispose$ = new Subject<void>();

  private onTouched?: () => void;
  private onChange?: (v: TissueTaskType | '') => void;

  constructor() {
    this.formControl.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((value) => {
      if (this.formControl.touched) this.onTouched?.();
      this.onChange?.(value);
    });
  }

  writeValue(obj: TissueTaskType | null | undefined): void {
    this.formControl.setValue(obj ?? '', { emitEvent: false });
  }

  registerOnChange(fn: (v: TissueTaskType | '') => void): void {
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
