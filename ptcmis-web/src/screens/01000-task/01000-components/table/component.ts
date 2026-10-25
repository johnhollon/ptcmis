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
import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
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
import { PipeTaskStatus } from '../../../../components/00200-pipes/pipe-task-status';
import { PipeTimestamp } from '../../../../components/00200-pipes/pipe-timestamp';
import { APIConst } from '../../../../entities/api/const-api';
import { TissueTaskView } from '../../../../entities/api/TissueTaskView';
import { User } from '../../../../entities/models/User';
import { TissueTaskType } from '../../../../entities/models/TissueTask';
import { PipeStorageInOutStatus } from '../../../../components/00200-pipes/pipe-storage-in-out-status';

@Component({
  selector: 'table-tissue-task',
  templateUrl: 'component.html',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    MatTooltip,
    MatTableModule,
    OverlayModule,
    PortalModule,
    PipeTaskStatus,
    PipeTimestamp,
    PipeTaskStatus,
    PipeTimestamp,
    ReactiveFormsModule,
    MatMenu,
    MatMenuContent,
    MatMenuItem,
    MatContextMenuTrigger,
    MatMenuTrigger,
    PipeStorageInOutStatus,
  ],
  styleUrl: 'component.scss',
})
export class ComponentTableTissueTask implements OnInit {
  @Input() taskType: TissueTaskType | '' = '';
  @Input() set models(models: TissueTaskView[]) {
    this.tableDataSource.data = models;
  }

  @Output() onView = new EventEmitter<TissueTaskView>();
  @Output() onEdit = new EventEmitter<TissueTaskView>();
  @Output() onProcess = new EventEmitter<TissueTaskView>();
  @Output() onUpdateToProcess = new EventEmitter<TissueTaskView>();
  @Output() onStorageIn = new EventEmitter<TissueTaskView>();
  @Output() onDelete = new EventEmitter<TissueTaskView>();

  protected readonly oppMenuSelectedModelSig = signal<TissueTaskView | undefined | null>(undefined);

  // *************************************************
  // Table列定义区域
  // *************************************************
  protected defaultTableColumns: string[] = [];
  protected tableDataSource = new MatTableDataSource<TissueTaskView>([]);
  protected trackById = (_index: number, row: TissueTaskView) => row.taskId;

  ngOnInit(): void {
    if (this.taskType === 'induction') {
      this.defaultTableColumns = [
        'variety',
        'varietyCode',
        'explantCollectionBatch',
        'taskStatus',
        'storageInStatus',
        'codePrefix',
        'parentPlants',
        'childrenBottles',
        'childrenPlants',
        'inspectorNames',
        'operatorNames',
        'creatorUserName',
        'createTime',
        'startTime',
        'finishTime',
        'action',
      ];
    } else if (this.taskType === 'acclimatization') {
      this.defaultTableColumns = [
        'variety',
        'varietyCode',
        'taskStatus',
        'storageInStatus',
        'parentBottles',
        'parentPlants',
        'parentKeepBottles',
        'parentKeepPlants',
        'inspectorNames',
        'operatorNames',
        'creatorUserName',
        'createTime',
        'startTime',
        'finishTime',
        'action',
      ];
    } else {
      this.defaultTableColumns = [
        'variety',
        'varietyCode',
        'taskStatus',
        'storageInStatus',
        'codePrefix',
        'parentBottles',
        'parentPlants',
        'parentKeepBottles',
        'parentKeepPlants',
        'childrenBottles',
        'childrenPlants',
        'inspectorNames',
        'operatorNames',
        'creatorUserName',
        'createTime',
        'startTime',
        'finishTime',
        'action',
      ];
    }
  }

  protected onOppMenuOpened(item: TissueTaskView) {
    this.oppMenuSelectedModelSig.set(item);
  }

  protected onOppMenuClosed() {
    this.oppMenuSelectedModelSig.set(undefined);
  }

  protected names(users: User[]) {
    return users?.map((user) => user.name).join(', ') ?? '';
  }
  protected readonly APIConst = APIConst;
}
