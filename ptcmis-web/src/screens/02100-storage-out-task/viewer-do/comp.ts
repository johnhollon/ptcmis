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
  Component,
  computed,
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import {
  CdkFixedSizeVirtualScroll,
  CdkVirtualForOf,
  CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';
import {
  StorageOutTaskView,
  StorageOutTaskViewDefault,
} from '../../../entities/api/StorageOutTaskView';
import { ActionStorageInOut } from '../../../services/actions/action-storage-in-out';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { PipeTaskType } from '../../../components/00200-pipes/pipe-task-type';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { StorageInOutBottle } from '../../../entities/models/StorageInOutBottle';
import { APIConst } from '../../../entities/api/const-api';
import { CompCode } from '../component-code-item/code';

@Component({
  standalone: true,
  selector: 'storage-out-task-viewer-do',
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
    PipeTaskType,
    PipeTimestamp,
    LayoutDrawerOppSimple,
    CdkVirtualForOf,
    CompCode,
    CompCode,
  ],
})
export class StorageOutTaskViewerDo implements OnInit, OnDestroy {
  @Input() model: StorageOutTaskView = StorageOutTaskViewDefault;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();

  protected usedMap = new Map<string, StorageInOutBottle>();
  protected usedSig = signal<string[]>([]);
  protected usedStatSig = computed(() => {
    let plants = 0;
    const list = this.usedSig();
    list.forEach((id) => {
      plants += this.usedMap.get(id)?.plantCountActual ?? 0;
    });
    const bottles = this.usedMap.size;
    return { bottles, plants };
  });

  private readonly dispose$ = new Subject<void>();

  private actionStorageInOut = inject(ActionStorageInOut);
  private serviceShowMessage = inject(ServiceShowMessage);

  private doQuery() {
    this.onRequestStartLoading.emit('正在查询出库信息...');
    this.actionStorageInOut
      .queryBottles(this.model.id)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (bottles) => {
          const usedList: string[] = [];
          const codePrefixSet = new Set<string>();
          bottles.forEach((bottle) => {
            if (
              bottle.status === APIConst.storageInOutBottleStatus.normal ||
              bottle.status === APIConst.storageInOutBottleStatus.done
            ) {
              usedList.push(bottle.idBottle);
              this.usedMap.set(bottle.idBottle, bottle);
            }
            codePrefixSet.add(bottle.idBottle.substring(0, 6));
          });
          this.usedSig.set(usedList.sort((a, b) => a.localeCompare(b)));
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  ngOnInit(): void {
    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
