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

import {
  Component,
  computed,
  ElementRef,
  inject,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Optional,
  signal,
  SimpleChanges,
  viewChild,
  ViewChild,
} from '@angular/core';
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
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { MatOption } from '@angular/material/select';
import { MatTooltip } from '@angular/material/tooltip';
import { finalize, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { VarietyCode } from '../../../entities/models/VarietyCode';
import { ActionVarietyCode } from '../../../services/actions/action-variety-code';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { MatAutocomplete, MatAutocompleteTrigger } from '@angular/material/autocomplete';

@Component({
  selector: 'select-variety-code-simple',
  standalone: true,
  template: `
    <mat-form-field [appearance]="appearance" floatLabel="always" tabindex="-1">
      <mat-label>{{ label }}</mat-label>
      <input
        #input
        type="text"
        tabindex="-1"
        matInput
        (input)="filter()"
        (focus)="filter()"
        [formControl]="control"
        [matAutocomplete]="autocomplete"
      />
      <mat-autocomplete requireSelection="true" #autocomplete="matAutocomplete">
        @if (canSelectEmpty) {
          <mat-option value="">{{ optionEmptyText }}</mat-option>
        }
        @for (item of filteredOptionsSig(); track item.code) {
          <mat-option [value]="item.code">{{ item.code }} </mat-option>
        }
      </mat-autocomplete>
      @if (showRefreshButton) {
        <button
          matIconButton
          matSuffix
          [showProgress]="refreshingSig()"
          (click)="doRefresh()"
          type="button"
          matTooltip="刷新"
          tabindex="-1"
        >
          <mat-icon tabindex="-1">refresh</mat-icon>
          <mat-progress-spinner
            tabindex="-1"
            progressIndicator
            mode="indeterminate"
            diameter="20"
          />
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
      useExisting: SelectVarietyCodeSimple,
    },
  ],
  imports: [
    MatFormField,
    MatLabel,
    MatError,
    MatIconButton,
    MatIcon,
    MatTooltip,
    MatProgressSpinner,
    ReactiveFormsModule,
    MatSuffix,
    MatOption,
    MatAutocomplete,
    MatAutocompleteTrigger,
    MatInput,
  ],
})
export class SelectVarietyCodeSimple implements ControlValueAccessor, OnInit, OnDestroy, OnChanges {
  @Input() label: string = '品种编号';
  @Input() appearance: MatFormFieldAppearance = 'outline';
  @Input() showRefreshButton: boolean = true;
  @Input() canSelectEmpty: boolean = false;
  @Input() optionEmptyText: string = '无';
  @Input() set variety(variety: string) {
    this.#variety = variety;
    this.updateVersionSig.update((v) => v + 1);
  }
  #variety: string = '';

  private updateVersionSig = signal(0);
  private varietyCodeList: VarietyCode[] = [];
  private filterValue: string = '';
  protected hasErrorsSig = signal(false);
  protected errorMessageSig = signal('必须填写品种编号');
  protected refreshingSig = signal(false);
  protected filteredOptionsSig = computed(()=>{
    if (this.updateVersionSig() < 0) return [] as VarietyCode[];
    let filtered = this.varietyCodeList.filter(
      (model) =>
        (!this.#variety || this.#variety === model.variety) &&
        model.code.toLowerCase().includes(this.filterValue),
    );
    if (!filtered || filtered.length < 1) {
      return this.varietyCodeList;
    }
    return filtered;
  });

  @Input() formControl?: FormControl;
  @Input() formControlName = '';
  get control(): FormControl<string> {
    return (
      this.formControl ||
      (this.formControlContainer?.control?.get(this.formControlName) as FormControl) ||
      (null as unknown as FormControl)
    );
  }
  @ViewChild(FormControlDirective, { static: true }) childControl!: FormControlDirective;
  private input = viewChild.required<ElementRef<HTMLInputElement>>('input');

  private actionVarietyCode = inject(ActionVarietyCode);
  private serviceShowMessage = inject(ServiceShowMessage);
  private dispose$ = new Subject<void>();

  constructor(@Optional() private formControlContainer: ControlContainer) {}

  ngOnInit(): void {
    const enabledList = this.actionVarietyCode.varietyCodeEnabledList;
    if (enabledList && enabledList.length > 0) {
      this.varietyCodeList = [...enabledList];
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

  ngOnChanges(changes: SimpleChanges): void {
    const { variety } = changes;
    if (variety) this.updateVersionSig.update((v) => v + 1);
  }

  filter(): void {
    this.filterValue = this.input().nativeElement.value.toLowerCase();
    this.updateVersionSig.update((v) => v + 1);
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
    this.actionVarietyCode
      .queryAllEnabled()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => {
          this.refreshingSig.set(false);
        }),
      )
      .subscribe({
        next: (result) => {
          this.varietyCodeList = [...result];
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }
}
