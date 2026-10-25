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
import { Component, EventEmitter, inject, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { Subject, takeUntil } from 'rxjs';
import {
  QueryConditionBottleCodeComplementTask,
  QueryConditionBottleCodeComplementTaskDefault,
} from '../../../entities/api/QueryConditionBottleCodeComplementTask';
import { DateRange } from '../../../components/00500-form-fields/date-range';
import { SelectorBottleCodeComplementTaskStatus } from '../../../components/bottle-code-complement/selector-bottle-code-complement-task-status';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
  selector: 'filters-bottle-code-complement-task',
  templateUrl: 'filters.html',
  standalone: true,
  imports: [
    MatFormField,
    MatLabel,
    MatTableModule,
    OverlayModule,
    PortalModule,
    MatInput,
    ReactiveFormsModule,
    DateRange,
    SelectorBottleCodeComplementTaskStatus,
    MatIconButton,
    MatIcon,
    MatTooltip,
  ],
  styles: [':host { display: contents; }'],
})
export class FiltersBottleCodeComplementTask implements OnInit, OnDestroy {
  @Input() filterFields: string[] = [
    'keyword',
    'taskStatus',
    'explantCollectionBatch',
    'generation',
    'createTime',
    'finishTime',
  ];
  @Output() onFiltersChanged = new EventEmitter<QueryConditionBottleCodeComplementTask>();
  @Output() onQuery = new EventEmitter<void>();

  private dispose$ = new Subject<void>();

  protected filterFormGroup!: FormGroup;
  private fb = inject(FormBuilder);

  protected requestQuery() {
    this.onQuery.emit();
  }

  ngOnInit(): void {
    this.filterFormGroup = this.fb.group<QueryConditionBottleCodeComplementTask>(
      QueryConditionBottleCodeComplementTaskDefault,
    );
    this.filterFormGroup.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((condition) => {
      this.onFiltersChanged.emit(condition);
    });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
