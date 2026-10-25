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
import {
  AfterViewInit,
  Component,
  computed,
  effect,
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { finalize, Subject } from 'rxjs';
import { ComponentFiltersStorageOut } from './filter/filter';
import { QueryConditionStorageInOut } from '../../../../../entities/api/QueryConditionStorageInOut';
import { ServiceShowMessage } from '../../../../../services/ui/service-show-error-message.service';
import { MatButton } from '@angular/material/button';
import { LayoutDrawerOppSimple } from '../../../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { TissueTaskStorageOutItem } from './out-item/comp';
import { CompStorageOutEditorDo } from './editor-do/comp';
import { ServiceTissueTaskProcess } from '../process/service';
import {
  DialogTaskCreateStorageOut,
  DialogTaskCreateStorageOutInputData,
  DialogTaskCreateStorageOutResponseData,
} from '../dialogs/storage-out/dialog-create';
import { takeUntil } from 'rxjs/operators';
import { CompStorageOutStatDo } from './stat-do/comp';
import { ArchivePanel } from '../../../../../components/00600-archive/archive';
import { Archive } from '../../../../../entities/models/Archive';
import { ActionStorageInOut } from '../../../../../services/actions/action-storage-in-out';
import { CompStorageOutAcceptDo } from './accept-do/comp';

@Component({
  selector: 'tissue-task-storage-out',
  templateUrl: 'comp.html',
  standalone: true,
  imports: [
    MatTableModule,
    OverlayModule,
    PortalModule,
    MatSnackBarModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    ComponentFiltersStorageOut,
    MatButton,
    LayoutDrawerOppSimple,
    TissueTaskStorageOutItem,
    CompStorageOutEditorDo,
    CompStorageOutStatDo,
    ArchivePanel,
    CompStorageOutAcceptDo,
  ],
  styleUrl: 'comp.scss',
})
export class TissueTaskStorageOut implements OnInit, AfterViewInit, OnDestroy {
  protected serviceTissueTaskProcess = inject(ServiceTissueTaskProcess);

  @Input() editable = true;
  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();

  protected subModuleSelectedSig = signal<string>('baseInfo');
  protected selectedStorageOutIdSig = signal<string>('');
  protected storageOutConditionSig = signal<QueryConditionStorageInOut | null>(null);
  protected storageOutFilteredSigReadonly = computed(() => {
    const storageOutList = this.serviceTissueTaskProcess.storageOutsSig();
    const condition = this.storageOutConditionSig();
    if (!condition) return storageOutList;
    return storageOutList.filter((storage) => {
      const name = condition.name ?? '';
      const status = condition.status ?? '';
      if (name) {
        const matchApplicant = storage.applicantName?.includes(name) ?? false;
        const matchOperator = storage.operatorName?.includes(name) ?? false;
        if (!matchApplicant && !matchOperator) {
          return false;
        }
      }
      if (status && storage.status !== status) {
        return false;
      }
      return true;
    });
  });

  protected archivesSig = signal<Archive[]>([]);
  private readonly archivesEffect = effect(() => {
    const selectedStorageId = this.selectedStorageOutIdSig();
    if (!selectedStorageId) {
      this.archivesSig.set([]);
      return;
    }
    this.actionStorageInOut
      .queryArchives(selectedStorageId)
      .pipe(takeUntil(this.dispose$))
      .subscribe({
        next: (data) => this.archivesSig.set(data),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  });

  private readonly dispose$ = new Subject<void>();

  private readonly serviceShowMessage = inject(ServiceShowMessage);
  private actionStorageInOut = inject(ActionStorageInOut);
  private readonly dialog = inject(MatDialog);

  constructor() {}

  ngOnInit(): void {
    this.serviceTissueTaskProcess.storageOutSelectedIndex$
      .pipe(takeUntil(this.dispose$))
      .subscribe((v) => this.selectedStorageOutIdSig.set(v));
  }

  ngAfterViewInit(): void {}

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.archivesEffect.destroy();
  }

  requestRefresh() {
    this.onRequestStartLoading.emit('正在刷新状态...');
    this.serviceTissueTaskProcess
      .refresh()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.onSaved.emit();
          this.serviceShowMessage.showInfo('刷新状态成功。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestSelectStorageInOut(id: string) {
    this.subModuleSelectedSig.set('baseInfo');
    this.selectedStorageOutIdSig.set(id);
  }
}
