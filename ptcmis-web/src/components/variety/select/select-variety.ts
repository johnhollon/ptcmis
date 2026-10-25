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

import { Component, inject, Input, OnDestroy, OnInit, signal, ViewChild } from '@angular/core';
import {
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  FormControlDirective,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatIconButton } from '@angular/material/button';
import { MatFormFieldAppearance, MatSuffix } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import { MatError, MatFormField, MatLabel } from '@angular/material/input';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { MatOption, MatSelect } from '@angular/material/select';
import { MatTooltip } from '@angular/material/tooltip';
import { finalize, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { Variety } from '../../../entities/models/Variety';
import { ActionVariety } from '../../../services/actions/action-variety';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';

@Component({
  selector: 'select-variety',
  standalone: true,
  template: `
    <mat-form-field [appearance]="appearance" tabindex="-1" floatLabel="always">
      <mat-label>{{ label }}</mat-label>
      <mat-select [formControl]="control" tabindex="-1">
        @if (canSelectEmpty) {
          <mat-option value="">{{ optionEmptyText }}</mat-option>
        }
        @for (item of varietyListSig(); track item.name) {
          <mat-option [value]="item.name">{{ item.name }}</mat-option>
        }
      </mat-select>
      @if (showRefreshButton) {
        <button
          matIconButton
          matSuffix
          [showProgress]="refreshingSig()"
          (click)="doRefresh()"
          tabindex="-1"
          type="button"
          matTooltip="刷新列表"
        >
          <mat-icon>refresh</mat-icon>
          <mat-progress-spinner progressIndicator mode="indeterminate" diameter="20" tabindex="" />
        </button>
      }
      @if (hasErrorsSig()) {
        <mat-error>{{ errorMessageSig() }}</mat-error>
      }
    </mat-form-field>
  `,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: SelectVariety,
    },
  ],
  imports: [
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    MatError,
    MatIconButton,
    MatIcon,
    MatTooltip,
    MatProgressSpinner,
    ReactiveFormsModule,
    MatSuffix,
  ],
})
export class SelectVariety implements ControlValueAccessor, OnInit, OnDestroy {
  @Input() label: string = '品种';
  @Input() appearance: MatFormFieldAppearance = 'outline';
  @Input() showRefreshButton: boolean = true;
  @Input() canSelectEmpty: boolean = false;
  @Input() optionEmptyText: string = '全部';

  protected varietyListSig = signal<Variety[]>([]);
  protected hasErrorsSig = signal(false);
  protected errorMessageSig = signal('必须选择品种');
  protected refreshingSig = signal(false);

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

  private actionVariety = inject(ActionVariety);
  private serviceShowMessage = inject(ServiceShowMessage);
  private dispose$ = new Subject<void>();

  constructor(private formControlContainer: ControlContainer) {}

  ngOnInit(): void {
    const enabledList = this.actionVariety.varietyEnabledList;
    if (enabledList && enabledList.length > 0) {
      this.varietyListSig.set([...enabledList]);
    } else {
      this.doRefresh();
    }
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

  protected doRefresh() {
    this.refreshingSig.set(true);
    this.actionVariety
      .queryAllEnabled()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => {
          this.refreshingSig.set(false);
        }),
      )
      .subscribe({
        next: (result) => this.varietyListSig.set(result),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }
}
