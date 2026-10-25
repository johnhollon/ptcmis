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

import {
  AfterViewInit,
  Component,
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { Subject } from 'rxjs';
import { TissueTaskView } from '../../../../../entities/api/TissueTaskView';
import { TissueTaskProcessDo } from '../process-do/comp';
import { ServiceShowConfirmDialog } from '../../../../../services/ui/service-show-confirm-dialog';
import { takeUntil } from 'rxjs/operators';
import { TissueTaskArchive } from '../../archive/archive';
import { TissueTaskStorageOut } from '../storage-out/comp';
import { ServiceTissueTaskProcess } from './service';
import { TissueTaskProcessStatDo } from '../process-stat-do/comp';
import { CompStorageIn } from '../storage-in/comp';

export type TissueTaskProcessSubModule =
  'stat' | 'process' | 'storge-out' | 'storage-in' | 'archive';

@Component({
  standalone: true,
  selector: 'tissue-task-process',
  templateUrl: 'comp.html',
  imports: [
    TissueTaskProcessDo,
    TissueTaskArchive,
    TissueTaskStorageOut,
    TissueTaskProcessStatDo,
    CompStorageIn,
  ],
  styleUrls: ['comp.scss'],
  providers: [ServiceTissueTaskProcess],
})
export class CompProcess implements OnInit, AfterViewInit, OnDestroy {
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  protected serviceTissueTaskProcess = inject(ServiceTissueTaskProcess);

  @Input() model: TissueTaskView = null as unknown as TissueTaskView;
  @Input() subModuleSelected: TissueTaskProcessSubModule = 'stat';
  @Input() editable = true;
  @Input() storageInEnable = false;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onRequestChangeSubModule = new EventEmitter<TissueTaskProcessSubModule>();

  dispose$ = new Subject<void>();

  ngOnInit(): void {
    this.serviceTissueTaskProcess.init(this.model);
  }
  ngAfterViewInit(): void {}
  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  private isDataSyncSavedConfirmed = false;
  protected requestCancel() {
    if (this.editable && !this.serviceTissueTaskProcess.isProcessInfoSyncedWithServer()) {
      if (!this.isDataSyncSavedConfirmed) {
        this.serviceShowConfirmDialog
          .show(
            '尚有任务执行信息未保存。应查看任务执行详情以确认是否需与服务器同步数据。',
            '数据需同步',
            ['确定'],
          )
          .pipe(takeUntil(this.dispose$))
          .subscribe((_) => {
            this.isDataSyncSavedConfirmed = true;
            this.onRequestChangeSubModule.emit('process');
          });
      } else {
        this.serviceShowConfirmDialog
          .show('尚有任务执行信息未保存。你确认要丢弃任务执行数据吗？', '再次确认放弃保存', [
            { text: '保留数据，查看任务执行详情', appearance: 'filled' },
            { text: '丢弃数据', appearance: 'text' },
          ])
          .pipe(takeUntil(this.dispose$))
          .subscribe((v) => {
            if (v === 1) {
              this.serviceShowConfirmDialog
                .show('你确认要丢弃任务执行数据吗？', '确认丢弃数据', [
                  { text: '保留数据', appearance: 'filled' },
                  { text: '丢弃数据', appearance: 'text' },
                ])
                .pipe(takeUntil(this.dispose$))
                .subscribe((v) => {
                  if (v === 1) {
                    this.onCancel.emit();
                  } else {
                    this.onRequestChangeSubModule.emit('process');
                  }
                });
            } else {
              this.onRequestChangeSubModule.emit('process');
            }
          });
      }
    } else {
      this.onCancel.emit();
    }
  }
}
