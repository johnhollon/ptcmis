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
import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import {
  MatContextMenuTrigger,
  MatMenu,
  MatMenuContent,
  MatMenuItem,
  MatMenuTrigger,
} from '@angular/material/menu';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { Bottle } from '../../../entities/models/Bottle';
import { PipeBottleStorageStatus } from '../../../components/00200-pipes/pipe-bottle-storage-status';
import { PipeStorageReason } from '../../../components/00200-pipes/pipe-storage-reason';

@Component({
  selector: 'table-query-bottle',
  templateUrl: 'table.html',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatTooltip,
    MatTableModule,
    OverlayModule,
    PortalModule,
    ReactiveFormsModule,
    MatMenu,
    MatMenuContent,
    MatMenuItem,
    MatContextMenuTrigger,
    MatMenuTrigger,
    PipeTimestamp,
    PipeBottleStorageStatus,
    PipeStorageReason,
  ],
  styleUrl: 'table.scss',
})
export class CompTable {
  @Input() columns: string[] = [
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
    'action',
  ];
  @Input() set models(models: Bottle[]) {
    this.tableDataSource.data = models;
  }

  @Output() onView = new EventEmitter<Bottle>();

  protected readonly oppMenuSelectedModelSig = signal<Bottle | undefined | null>(undefined);

  // *************************************************
  // Table列定义区域
  // *************************************************
  protected tableDataSource = new MatTableDataSource<Bottle>([]);
  protected trackById = (_index: number, row: Bottle) => row.id;

  protected onOppMenuOpened(item: Bottle) {
    this.oppMenuSelectedModelSig.set(item);
  }

  protected onOppMenuClosed() {
    this.oppMenuSelectedModelSig.set(undefined);
  }
}
