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

import { Component, EventEmitter, inject, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
  MatTableDataSource
} from '@angular/material/table';
import { CdkFixedSizeVirtualScroll, CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { StorageInOutLog } from '../../../entities/models/StorageInOutLog';
import { ActionStorageInOut } from '../../../services/actions/action-storage-in-out';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { PipeStorageInOutStatus } from '../../../components/00200-pipes/pipe-storage-in-out-status';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import {
  LayoutDrawerOppSimple
} from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { DefaultStorageInOut, StorageInOut } from '../../../entities/models/StorageInOut';

@Component({
  standalone: true,
  selector: 'logs-inventory-out',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    CdkVirtualScrollViewport,
    CdkFixedSizeVirtualScroll,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderCellDef,
    MatCellDef,
    MatHeaderRow,
    MatRow,
    MatHeaderRowDef,
    MatRowDef,
    PipeStorageInOutStatus,
    PipeTimestamp,
    LayoutDrawerOppSimple,
  ],
})
export class CompLogs implements OnInit, OnDestroy {
  @Input() model: StorageInOut = DefaultStorageInOut;
  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();

  tableColumnsLogStorageOut: string[] = ['operatorTime', 'operatorName', 'status', 'oppReason'];
  protected tableLogsDataSource = new MatTableDataSource<StorageInOutLog>([]);
  protected logTrackById = (_index: number, row: StorageInOutLog) => row.id;

  private readonly dispose$ = new Subject<void>();

  private actionStorageInOut = inject(ActionStorageInOut);
  private serviceShowMessage = inject(ServiceShowMessage);

  ngOnInit(): void {
    this.onRequestStartLoading.emit('正在查询操作日志...');
    this.actionStorageInOut
      .queryAllLogs(this.model.id)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (logs) => (this.tableLogsDataSource.data = logs),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
