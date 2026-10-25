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

import {Component, inject, Input, OnDestroy, OnInit, Optional, signal, ViewChild} from '@angular/core';
import {ControlContainer, ControlValueAccessor, FormControl, FormControlDirective, NG_VALUE_ACCESSOR, ReactiveFormsModule,} from '@angular/forms';
import {MatIconButton} from '@angular/material/button';
import {MatFormFieldAppearance, MatSuffix} from '@angular/material/form-field';
import {MatIcon} from '@angular/material/icon';
import {MatError, MatFormField, MatLabel} from '@angular/material/input';
import {MatProgressSpinner} from '@angular/material/progress-spinner';
import {MatOption, MatSelect} from '@angular/material/select';
import {MatTooltip} from '@angular/material/tooltip';
import {finalize, Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {User} from '../../../entities/models/User';
import {ActionUser} from '../../../services/actions/action-user';
import {ServiceShowMessage} from '../../../services/ui/service-show-error-message.service';

@Component({
  selector: 'select-user',
  template: `
    <mat-form-field [appearance]="appearance" floatLabel="always" tabindex="-1">
      <mat-label>{{ label }}</mat-label>
      <mat-select
        [formControl]="control"
        [compareWith]="compareWith"
        tabindex="-1"
        [matTooltip]="toolTip"
      >
        @if (canSelectEmpty) {
          @if (optionsImportantSig().length > 0 && optionsOthersSig().length > 0) {
            <optgroup label="{{ groupNameNull }}">
              <mat-option [value]="null">{{ optionEmptyText }}</mat-option>
            </optgroup>
          } @else {
            <mat-option [value]="null">{{ optionEmptyText }}</mat-option>
          }
        }
        @if (optionsImportantSig().length > 0 && optionsOthersSig().length > 0) {
          <optgroup label="{{ groupNameImportant }}">
            @for (item of optionsImportantSig(); track item.id) {
              <mat-option [value]="item">{{ item.name + ' - ' + item.code }}</mat-option>
            }
          </optgroup>
        } @else {
          @for (item of optionsImportantSig(); track item.id) {
            <mat-option [value]="item">{{ item.name + ' - ' + item.code }}</mat-option>
          }
        }
        @if (optionsImportantSig().length > 0 && optionsOthersSig().length > 0) {
          <optgroup label="{{ groupNameOther }}">
            @for (item of optionsOthersSig(); track item.id) {
              <mat-option [value]="item">{{ item.name + ' - ' + item.code }}</mat-option>
            }
          </optgroup>
        } @else {
          @for (item of optionsOthersSig(); track item.id) {
            <mat-option [value]="item">{{ item.name + ' - ' + item.code }}</mat-option>
          }
        }
      </mat-select>
      @if (showRefreshButton) {
        <button
          tabindex="-1"
          matIconButton
          matSuffix
          [showProgress]="refreshingSig()"
          (click)="doRefresh()"
          type="button"
          matTooltip="刷新"
        >
          <mat-icon>refresh</mat-icon>
          <mat-progress-spinner
            progressIndicator
            mode="indeterminate"
            diameter="20"
            tabindex="-1"
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
      useExisting: SelectUser,
    },
  ],
  standalone: true,
  imports: [
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    MatError,
    ReactiveFormsModule,
    MatIcon,
    MatIconButton,
    MatProgressSpinner,
    MatSuffix,
    MatTooltip,
  ],
})
export class SelectUser implements ControlValueAccessor, OnInit, OnDestroy {
  @Input() label: string = '员工';
  @Input() appearance: MatFormFieldAppearance = 'outline';
  @Input() canSelectEmpty: boolean = false;
  @Input() optionEmptyText: string = '全部';
  @Input() showRefreshButton: boolean = true;
  @Input() groupNameNull: string = '全部员工';
  @Input() groupNameImportant: string = '优先选择';
  @Input() groupNameOther: string = '其他';
  @Input() toolTip: string = '';
  @Input() set usersImportant(usersImportant: User[]) {
    this.optionsImportantSig.set(usersImportant);
    this.calculateGroup();
  }

  protected hasErrorsSig = signal(false);
  protected errorMessageSig = signal('必须选择员工');
  protected refreshingSig = signal(false);
  protected optionsImportantSig = signal<User[]>([]);
  protected optionsOthersSig = signal<User[]>([]);
  private allUsers: User[] = [];

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

  constructor(
    @Optional()
    private readonly formControlContainer?: ControlContainer,
  ) {}
  private dispose$ = new Subject<void>();

  private actionUser = inject(ActionUser);
  private serviceShowMessage = inject(ServiceShowMessage);

  ngOnInit(): void {
    const enabledList = this.actionUser.userEnabledList;
    if (enabledList && enabledList.length > 0) {
      this.allUsers = enabledList;
      this.calculateGroup();
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
    const value = typeof obj === 'string' ? (this.allUsers.find((u) => u.id === obj) ?? null) : obj;
    this.childControl?.valueAccessor?.writeValue?.(value);
    this.control?.setValue(value);
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
    this.actionUser
      .queryAllEnabled()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => {
          this.refreshingSig.set(false);
        }),
      )
      .subscribe({
        next: (result) => {
          this.allUsers = result;
          this.calculateGroup();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  private calculateGroup() {
    const usersImportant = this.optionsImportantSig();
    if (!usersImportant || usersImportant.length < 1) {
      this.optionsOthersSig.set(this.allUsers);
      return;
    }
    const important = usersImportant || [];
    this.optionsOthersSig.set(
      this.allUsers.filter(
        (user) => !important.some((importantUser) => importantUser.id === user.id),
      ),
    );
  }

  protected compareWith = (o1: any, o2: any): boolean => {
    if (o1 === o2) return true;
    if (!o1 || !o2) return false;
    const id1 = typeof o1 === 'string' ? o1 : o1.id;
    const id2 = typeof o2 === 'string' ? o2 : o2.id;
    return id1 === id2;
  };
}
