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

import { OverlayModule } from '@angular/cdk/overlay';
import { PortalModule } from '@angular/cdk/portal';
import { Component, EventEmitter, inject, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { Subject, takeUntil } from 'rxjs';
import {
  QueryConditionStorageInOut,
  QueryConditionStorageInOutDefault,
} from '../../../../../../entities/api/QueryConditionStorageInOut';
import { SelectorStorageInOutStatus } from '../../../../../../components/storage-in-out/selector-storage-in-out-status';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
  selector: 'tissue-task-storage-out-filters',
  templateUrl: 'filter.html',
  standalone: true,
  imports: [
    MatTableModule,
    OverlayModule,
    PortalModule,
    ReactiveFormsModule,
    SelectorStorageInOutStatus,
    MatFormField,
    MatInput,
    MatLabel,
    MatIconButton,
    MatIcon,
    MatTooltip,
  ],
  styles: [':host { display: contents; }'],
})
export class ComponentFiltersStorageOut implements OnInit, OnDestroy {
  @Output() onFiltersChanged = new EventEmitter<QueryConditionStorageInOut>();
  @Output() onRefresh = new EventEmitter<void>();

  private dispose$ = new Subject<void>();

  protected filterFormGroup!: FormGroup;
  private fb = inject(FormBuilder);

  ngOnInit(): void {
    this.filterFormGroup = this.fb.group<QueryConditionStorageInOut>(
      QueryConditionStorageInOutDefault,
    );
    this.filterFormGroup.valueChanges
      .pipe(takeUntil(this.dispose$))
      .subscribe((condition: QueryConditionStorageInOut) => {
        this.onFiltersChanged.emit(condition);
      });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
