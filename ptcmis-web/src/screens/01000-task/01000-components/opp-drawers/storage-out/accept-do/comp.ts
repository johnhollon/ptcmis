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
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  signal,
  SimpleChanges,
} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, Subject, switchMap, tap } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import {
  CdkFixedSizeVirtualScroll,
  CdkVirtualForOf,
  CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';
import { ActionStorageInOut } from '../../../../../../services/actions/action-storage-in-out';
import { ServiceShowMessage } from '../../../../../../services/ui/service-show-error-message.service';
import { StorageInOutBottle } from '../../../../../../entities/models/StorageInOutBottle';
import { APIConst } from '../../../../../../entities/api/const-api';
import { CompCode } from '../component-code-item/code';
import { StorageInOut } from '../../../../../../entities/models/StorageInOut';
import { MatIcon } from '@angular/material/icon';
import { PipeStorageInOutStatus } from '../../../../../../components/00200-pipes/pipe-storage-in-out-status';
import { ServiceTissueTaskProcess } from '../../process/service';
import {
  DialogTaskStorageOutRejStore,
  DialogTaskStorageOutRejStoreData,
} from '../../dialogs/storage-out/dialog-rej-store';
import { MatDialog } from '@angular/material/dialog';
import { MatTooltip } from '@angular/material/tooltip';
import { ServiceShowConfirmDialog } from '../../../../../../services/ui/service-show-confirm-dialog';
import { ServiceGlobalCodeScanner } from '../../../../../../services/ui/service-global-code-scanner';

@Component({
  standalone: true,
  selector: 'tissue-task-storage-out-accept-do',
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
    CdkVirtualForOf,
    CompCode,
    MatIcon,
    PipeStorageInOutStatus,
    MatTooltip,
  ],
})
export class CompStorageOutAcceptDo implements OnInit, OnDestroy, OnChanges {
  @Input() editable = true;
  @Input() model: {
    planBottles: number;
    planPlants: number;
    applyBottles: number;
    applyPlants: number;
    doneBottles: number;
    donePlants: number;
    outBottles: number;
    outPlants: number;
  } | null = null;
  @Input() storageInOut: StorageInOut | null = null;

  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();

  private readonly updateVersionSig = signal(0);
  protected allMap = new Map<string, StorageInOutBottle>();
  protected normalListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.allMap.values()]
      .filter((bottle) => {
        return bottle && bottle.status === 'normal';
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  });
  protected normalStatSig = computed(() => {
    const result = {
      plants: 0,
      bottles: 0,
    };
    if (this.updateVersionSig() < 0) return result;
    const list = this.normalListSig();
    list.forEach((b) => {
      result.plants += b.plantCountActual ?? 0;
    });
    result.bottles = list.length;
    return result;
  });
  protected doneListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.allMap.values()]
      .filter((bottle) => {
        return bottle && bottle.status === 'done';
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  });
  protected doneStatSig = computed(() => {
    const result = {
      plants: 0,
      bottles: 0,
    };
    if (this.updateVersionSig() < 0) return result;
    const list = this.doneListSig();
    list.forEach((b) => {
      result.plants += b.plantCountActual ?? 0;
    });
    result.bottles = list.length;
    return result;
  });

  private readonly dispose$ = new Subject<void>();

  private serviceTissueTaskProcess = inject(ServiceTissueTaskProcess);
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);
  private actionStorageInOut = inject(ActionStorageInOut);
  private serviceShowMessage = inject(ServiceShowMessage);
  private readonly dialog = inject(MatDialog);

  protected requestRejStore() {
    if (!this.editable) return;
    if (!this.storageInOut) return;
    // 打开对话框。
    const dialogInputData: DialogTaskStorageOutRejStoreData = {
      id: this.storageInOut.id,
      idTask: this.storageInOut.idTask,
      oppReason: '',
    };

    const dialogRef = this.dialog.open<
      DialogTaskStorageOutRejStore,
      DialogTaskStorageOutRejStoreData,
      DialogTaskStorageOutRejStoreData
    >(DialogTaskStorageOutRejStore, {
      data: dialogInputData,
      width: '300px',
    });

    dialogRef
      .afterClosed()
      .pipe(
        filter((v) => !!v),
        tap((_) => this.onRequestStartLoading.emit('正在驳回库房...')),
        switchMap((v) => this.serviceTissueTaskProcess.storageOutRejStore(v)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('驳回库房成功。');
          this.onSaved.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestFinishCheck() {
    if (!this.editable) return;
    if (!this.storageInOut) return;
    if (this.normalListSig().length > 0) {
      this.serviceShowMessage.showError('尚有未核对的已出库母瓶，无法完成接收。');
      return;
    }
    this.serviceShowConfirmDialog
      .show('确认已核对所有出库母瓶，完成出库接收？', '操作确认')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在完成出库接收...')),
        switchMap((_) =>
          this.serviceTissueTaskProcess.storageOutAccept({
            inOutId: this.storageInOut!.id,
            issueTaskId: this.storageInOut!.idTask,
            usedList: this.doneListSig(),
          }),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('完成出库接收。');
          this.onSaved.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleCheckedScan() {
    if (!this.editable) return;
    this.serviceGlobalCodeScanner
      .start('扫码接收出库母瓶')
      .pipe(takeUntil(this.dispose$))
      .subscribe((v) => this.requestBottleChecked(v));
  }

  protected requestBottleCheckedAll() {
    if (!this.editable) return;
    this.serviceShowConfirmDialog
      .show('确认已核对所有出库母瓶，一键完成接收？', '操作确认')
      .pipe(
        filter((v) => v === 0),
        takeUntil(this.dispose$),
      )
      .subscribe({
        next: (_) => {
          this.allMap.forEach((b) => {
            b.status = 'done';
          });
          this.updateVersionSig.update((v) => v + 1);
        },
      });
  }

  protected requestBottleChecked(idBottle: string) {
    if (!this.editable) return;
    const bottle = this.allMap.get(idBottle);
    if (!bottle) return;
    bottle.status = 'done';
    this.updateVersionSig.update((v) => v + 1);
  }

  protected requestBottleNormal(idBottle: string) {
    if (!this.editable) return;
    const bottle = this.allMap.get(idBottle);
    if (!bottle) return;
    bottle.status = 'normal';
    this.updateVersionSig.update((v) => v + 1);
  }

  ngOnInit(): void {
    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  ngOnChanges(changes: SimpleChanges): void {
    const { storageInOut } = changes;
    if (storageInOut) {
      this.doQuery();
    }
  }

  private doQuery() {
    if (!this.storageInOut) return;
    this.actionStorageInOut
      .queryBottles(this.storageInOut.id)
      .pipe(takeUntil(this.dispose$))
      .subscribe({
        next: (bottles) => {
          this.allMap.clear();
          bottles.forEach((bottle) => {
            this.allMap.set(bottle.idBottle, bottle);
          });
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected readonly APIConst = APIConst;
}
