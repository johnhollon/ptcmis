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
import { DateRange } from '../../../../components/00500-form-fields/date-range';
import { SelectorTissueTaskStatus } from '../../../../components/tissue-task/selector-tissue-task-status';
import {
  Condition,
  ConditionDefault,
  TissueTaskType,
} from '../../../../entities/models/TissueTask';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
  selector: 'filters-tissue-task',
  templateUrl: 'component.html',
  standalone: true,
  imports: [
    MatFormField,
    MatLabel,
    MatTableModule,
    OverlayModule,
    PortalModule,
    MatInput,
    ReactiveFormsModule,
    SelectorTissueTaskStatus,
    DateRange,
    MatIcon,
    MatIconButton,
    MatTooltip,
  ],
  styles: [':host { display: contents; }'],
})
export class ComponentFiltersTissueTask implements OnInit, OnDestroy {
  @Input() taskType: TissueTaskType | '' = '';
  @Output() onFiltersChanged = new EventEmitter<Condition>();
  @Output() onQuery = new EventEmitter<void>();

  private dispose$ = new Subject<void>();

  protected filterFormGroup!: FormGroup;
  private fb = inject(FormBuilder);

  protected requestQuery() {
    this.onQuery.emit();
  }

  ngOnInit(): void {
    this.filterFormGroup = this.fb.group<Condition>(ConditionDefault);
    this.filterFormGroup.valueChanges
      .pipe(takeUntil(this.dispose$))
      .subscribe((condition: Condition) => {
        if (!!this.taskType) condition.taskType = this.taskType;
        if (!condition.explantCollectionBatch) {
          condition.explantCollectionBatch = null;
          this.filterFormGroup
            .get('explantCollectionBatch')
            ?.setValue(null, { onlySelf: true, emitEvent: false });
        }
        this.onFiltersChanged.emit(condition);
      });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
