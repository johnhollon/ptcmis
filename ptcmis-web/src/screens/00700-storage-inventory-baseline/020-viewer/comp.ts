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

import { Component, EventEmitter, inject, Input, OnDestroy, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { Subject } from 'rxjs';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { APIConst } from '../../../entities/api/const-api';
import {
  InventoryTransaction,
  InventoryTransactionDefault,
} from '../../../entities/models/InventoryTransaction';

@Component({
  selector: 'storage-inventory-baseline-viewer',
  templateUrl: 'comp.html',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    LayoutDrawerOppSimple,
  ],
  standalone: true,
})
export class CompViewer implements OnDestroy {
  @Output() onCancel = new EventEmitter<void>();
  @Input() set model(model: InventoryTransaction) {
    this.form.patchValue(model);
  }
  private dispose$ = new Subject<void>();

  private readonly fb = inject(FormBuilder);
  protected readonly form: FormGroup;

  constructor() {
    this.form = this.fb.group(InventoryTransactionDefault);
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected requestCancel() {
    this.onCancel.emit();
  }
  protected readonly APIConst = APIConst;
}
