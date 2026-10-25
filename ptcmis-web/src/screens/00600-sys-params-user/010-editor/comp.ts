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

import {Component, EventEmitter, inject, Input, OnDestroy, Output} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatRadioButton, MatRadioGroup} from '@angular/material/radio';
import {Subject} from 'rxjs';
import {LayoutDrawerOppSimple} from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import {SelectUserRole} from '../../../components/user/select/select-role';
import {APIConst} from '../../../entities/api/const-api';
import {User, userDefault} from '../../../entities/models/User';

@Component({
  selector: 'user-editor',
  templateUrl: 'comp.html',
  imports: [
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    LayoutDrawerOppSimple,
    MatRadioGroup,
    MatRadioButton,
    SelectUserRole,
  ],
  standalone: true
})
export class CompEditor implements OnDestroy {
  @Output() onCancel = new EventEmitter<void>();
  @Output() onSave = new EventEmitter<User>();
  @Input() set model(model: User) {
    this.#model = model;
    this.form.patchValue(model);
  }
  #model: User | undefined = undefined;
  dispose$ = new Subject<void>();

  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup;

  constructor() {
    this.form = this.fb.group(userDefault);
    this.form.get('name')?.addValidators(control => {
      if (!control.value) {
        return {
          'error': '应填写姓名。',
        }
      }
      return null;
    });
    this.form.get('code')?.addValidators(control => {
      if (!control.value) {
        return {
          'error': '应填写编号。',
        }
      }
      return null;
    });
    this.form.get('mobile')?.addValidators(control => {
      if (!control.value) {
        return {
          'error': '应填写手机号码。',
        }
      }
      return null;
    });
    this.form.get('password')?.addValidators(control => {
      if (!control.value) {
        return {
          'error': '应填写密码。',
        }
      }
      return null;
    });
    this.form.get('role')?.addValidators(control => {
      if (!control.value) {
        return {
          'error': '应填写员工系统使用权限。',
        }
      }
      return null;
    });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected async requestSave(): Promise<void> {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.onSave.emit(this.form.getRawValue());
  }

  protected requestCancel() {
    this.onCancel.emit();
  }
  protected readonly APIConst = APIConst;
}


