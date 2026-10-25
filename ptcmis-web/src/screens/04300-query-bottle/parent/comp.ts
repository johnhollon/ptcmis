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

import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { CdkFixedSizeVirtualScroll, CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import {
  LayoutDrawerOppSimple
} from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { ServiceQueryBottle } from '../viewer/service';
import { BottleLog } from '../../../entities/models/BottleLog';
import { CompTable } from '../table/table';

@Component({
  standalone: true,
  selector: 'parent-query-bottle',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [
    ReactiveFormsModule,
    MatButton,
    CdkVirtualScrollViewport,
    CdkFixedSizeVirtualScroll,
    LayoutDrawerOppSimple,
    CompTable,
  ],
})
export class CompParent {
  @Output() onCancel = new EventEmitter<void>();

  columns: string[] = [
    'id',
    'variety',
    'varietyCode',
    'explantCollectionBatch',
    'generation',
    'plantCount',
    'sourceType',
    'taskTime',
    'discarded',
    'currentStorageStatus',
    'lastOperatorName',
    'idParent',
  ];

  protected service = inject(ServiceQueryBottle);

  tableColumnsLogStorageOut: string[] = [
    'bizType',
    'description',
    'operateStatus',
    'storageStatus',
    'storageReason',
    'discarded',
    'discardReason',
    'inspectorName',
    'operatorName',
    'oppTime',
  ];
  protected logTrackById = (_index: number, row: BottleLog) => row.id;
}
