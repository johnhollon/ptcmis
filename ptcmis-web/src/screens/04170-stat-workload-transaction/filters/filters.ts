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
import { Component, EventEmitter, inject, OnDestroy, OnInit, Output, signal } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { combineLatest, startWith, Subject, takeUntil } from 'rxjs';
import {
  QueryConditionStatWorkload,
  QueryConditionStatWorkloadDefault,
} from '../../../entities/api/QueryConditionStatWorkload';
import { DateRange, ModelDateRange } from '../../../components/00500-form-fields/date-range';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { SelectVariety } from '../../../components/variety/select/select-variety';
import { SelectVarietyCodeSimple } from '../../../components/variety-code/select/select-variety-code-simple';
import {
  getCurrentMonth,
  getCurrentQuarter,
  getCurrentYear,
  getMonthDateRange,
  getQuarterDateRange,
  getYearDateRange,
} from '../../../services/utils/date';
import { MatOption, MatSelect } from '@angular/material/select';
import { ActionVarietyCode } from '../../../services/actions/action-variety-code';
import { SelectUser } from '../../../components/user/select/select-user';
import { User } from '../../../entities/models/User';

@Component({
  selector: 'filters-stat-workload-transaction',
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
    MatIconButton,
    MatIcon,
    MatTooltip,
    SelectVariety,
    SelectVarietyCodeSimple,
    MatSelect,
    MatOption,
    SelectUser,
    MatButton,
  ],
  styles: [
    `
      :host {
        display: contents;
      }
      .hide {
        display: none;
      }
    `,
  ],
})
export class CompFilters implements OnInit, OnDestroy {
  @Output() onConditionChanged = new EventEmitter<QueryConditionStatWorkload>();
  @Output() onQuery = new EventEmitter<QueryConditionStatWorkload>();
  @Output() onExport = new EventEmitter<QueryConditionStatWorkload>();

  private condition: QueryConditionStatWorkload = QueryConditionStatWorkloadDefault;

  protected varietySig = signal('');
  private readonly fb = inject(FormBuilder);
  protected readonly filterFormGroup: FormGroup = this.fb.group<QueryConditionStatWorkload>(
    QueryConditionStatWorkloadDefault,
  );
  protected dateTypeSig = signal('');
  protected readonly fcUser = new FormControl<User | null>(null);
  protected readonly fcYear = new FormControl<number>(getCurrentYear());
  protected readonly fcMonth = new FormControl<number>(getCurrentMonth());
  protected readonly fcQuarter = new FormControl<number>(getCurrentQuarter());
  protected readonly fcDateRange = new FormControl<ModelDateRange>({ start: 0, end: 0 });

  private dispose$ = new Subject<void>();

  private actionVarietyCode = inject(ActionVarietyCode);

  protected requestQuery() {
    this.onQuery.emit(this.condition);
  }

  protected requestExport() {
    this.onExport.emit(this.condition);
  }

  ngOnInit(): void {
    combineLatest([
      this.filterFormGroup.valueChanges.pipe(startWith(this.condition)),
      this.fcYear.valueChanges,
      this.fcMonth.valueChanges,
      this.fcQuarter.valueChanges,
      this.fcDateRange.valueChanges,
      this.fcUser.valueChanges,
    ])
      .pipe(takeUntil(this.dispose$))
      .subscribe((_) => {
        const condition: QueryConditionStatWorkload = { ...this.filterFormGroup.getRawValue() };

        condition.userId = this.fcUser.value?.id ?? '';

        const varietyCodeModel = this.actionVarietyCode.varietyCodeEnabledList.find(
          (v) => v.code === condition.varietyCode && v.variety === condition.variety,
        );
        condition.varietyCode = varietyCodeModel?.code || '';

        const dateType = condition.dateType || '按月';
        if (dateType === '按季') {
          condition.dateRange = getQuarterDateRange(
            this.fcYear.value || getCurrentYear(),
            (this.fcQuarter.value || getCurrentQuarter()) as unknown as 1 | 2 | 3 | 4,
          );
        } else if (dateType === '自定义') {
          condition.dateRange = this.fcDateRange.value || { start: 0, end: 0 };
        } else if (dateType === '按年') {
          condition.dateRange = getYearDateRange(this.fcYear.value || getCurrentYear());
        } else {
          condition.dateRange = getMonthDateRange(
            this.fcYear.value || getCurrentYear(),
            this.fcMonth.value || getCurrentMonth(),
          );
        }

        condition.detailShowType = condition.detailShowType || '明细';

        condition.explantCollectionBatch =
          condition.explantCollectionBatch === undefined ||
          condition.explantCollectionBatch === null ||
          condition.explantCollectionBatch < 1
            ? null
            : condition.explantCollectionBatch;
        condition.generation =
          condition.generation === undefined ||
          condition.generation === null ||
          condition.generation < 0
            ? null
            : condition.generation;

        this.condition = condition;

        this.onConditionChanged.emit(condition);
      });

    this.fcYear.setValue(getCurrentYear());
    this.fcMonth.setValue(getCurrentMonth());
    this.fcQuarter.setValue(getCurrentQuarter());
    this.fcDateRange.setValue({ start: 0, end: 0 });

    this.filterFormGroup
      .get('variety')
      ?.valueChanges.pipe(takeUntil(this.dispose$))
      .subscribe((value) => this.varietySig.set(value || ''));
    this.filterFormGroup
      .get('dateType')
      ?.valueChanges.pipe(takeUntil(this.dispose$))
      .subscribe((value) => this.dateTypeSig.set(value || '按月'));

    this.dateTypeSig.set(this.filterFormGroup.get('dateType')?.value || '按月');
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
