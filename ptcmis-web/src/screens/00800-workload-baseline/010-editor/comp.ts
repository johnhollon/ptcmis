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
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatError, MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { Subject } from 'rxjs';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { SelectVarietyCode } from '../../../components/variety-code/select/select-variety-code';
import { APIConst } from '../../../entities/api/const-api';
import {
  WorkloadTransaction,
  WorkloadTransactionDefault,
} from '../../../entities/models/WorkloadTransaction';
import { User } from '../../../entities/models/User';
import { SelectUser } from '../../../components/user/select/select-user';
import { MatOption } from '@angular/material/core';
import { MatSelect } from '@angular/material/select';
import { ActionUser } from '../../../services/actions/action-user';
import { isYmd8Number } from '../../../services/utils/date';

@Component({
  selector: 'editor-workload-baseline',
  templateUrl: 'comp.html',
  imports: [
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    LayoutDrawerOppSimple,
    SelectVarietyCode,
    SelectUser,
    MatOption,
    MatSelect,
  ],
  standalone: true,
})
export class CompEditor implements OnInit, OnDestroy {
  @Output() onCancel = new EventEmitter<void>();
  @Output() onSave = new EventEmitter<WorkloadTransaction>();
  @Input() set model(model: WorkloadTransaction) {
    const m = { ...model };
    m.varietyCode = `${m.variety}\u200B${m.varietyCode}`;
    this.form.patchValue(m);
  }

  dispose$ = new Subject<void>();

  private readonly fb = inject(FormBuilder);
  protected form: FormGroup = this.fb.group({ ...WorkloadTransactionDefault });
  protected readonly fcUser = new FormControl<User | null>(null);

  private actionUser = inject(ActionUser);
  protected userEditableSig = signal(true);

  constructor() {
    this.fcUser.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择员工。',
        };
      }
      return null;
    });

    this.form.get('userRole')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应填写员工身份。',
        };
      }
      return null;
    });
    this.form.get('bizType')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应填写绩效种类。',
        };
      }
      return null;
    });
    this.form.get('varietyCode')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应填写编号。',
        };
      }
      return null;
    });
    this.form.get('generation')?.addValidators((control) => {
      if (
        control.value === null ||
        control.value === undefined ||
        control.value < 0 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '最小为0。',
        };
      }
      return null;
    });
    this.form.get('bottles')?.addValidators((control) => {
      if (
        control.value === null ||
        control.value === undefined ||
        control.value < 0 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '最小为0。',
        };
      }
      return null;
    });
    this.form.get('plants')?.addValidators((control) => {
      if (
        control.value === null ||
        control.value === undefined ||
        control.value < 0 ||
        !Number.isInteger(control.value)
      ) {
        return {
          error: '最小为0。',
        };
      }
      return null;
    });
  }

  ngOnInit(): void {
    const users = this.actionUser.userEnabledList;
    const userId = this.form.get('userId')?.value ?? '';
    if (!userId) {
      this.userEditableSig.set(true);
    } else {
      const user = users.find((user) => user.id === userId);
      this.userEditableSig.set(!!user);
      if (!!user) {
        this.fcUser.patchValue(user);
      }
    }
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected async requestSave(): Promise<void> {
    const model: WorkloadTransaction = this.form.getRawValue();
    const varietyCode = model.varietyCode;
    if (!!varietyCode) {
      const varietyCodeSplit = varietyCode.split('\u200B');
      if (varietyCodeSplit.length > 0) {
        model.variety = varietyCodeSplit[0].trim().toUpperCase();
      }
      if (varietyCodeSplit.length > 1) {
        model.varietyCode = varietyCodeSplit[1].trim().toUpperCase();
      }
    }
    if (this.userEditableSig()) {
      if (this.fcUser.invalid) {
        this.fcUser.markAllAsTouched();
        return;
      }
      const user: User = this.fcUser.value!;
      model.userId = user.id;
      model.userName = user.name;
    }

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.onSave.emit(model);
  }

  protected requestCancel() {
    this.onCancel.emit();
  }
  protected readonly APIConst = APIConst;
}
