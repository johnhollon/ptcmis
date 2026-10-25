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
import { DateRange, ModelDateRange } from '../../../../components/00500-form-fields/date-range';
import { MatOption } from '@angular/material/core';
import { MatSelect } from '@angular/material/select';

@Component({
  selector: 'filters-code-print-process-do',
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
    MatOption,
    MatSelect,
    DateRange,
  ],
  styles: [':host { display: contents; }'],
})
export class CompFiltersProcessDo implements OnInit, OnDestroy {
  @Input() codePrefixList: string[] = [];
  @Output() onFiltersChanged = new EventEmitter<BottleFilterCondition>();

  private dispose$ = new Subject<void>();

  protected filterFormGroup!: FormGroup;
  private fb = inject(FormBuilder);

  ngOnInit(): void {
    this.filterFormGroup = this.fb.group<BottleFilterCondition>({
      codePrefix: '',
      keyword: '',
      dateRange: { start: 0, end: 0 },
      generation: null,
      explantCollectionBatch: null,
    });
    this.filterFormGroup.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((condition) => {
      this.onFiltersChanged.emit(condition);
    });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}

export interface BottleFilterCondition {
  codePrefix: string;
  keyword: string;
  dateRange: ModelDateRange;
  generation: number | null;
  explantCollectionBatch: number | null;
}
