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
  ElementRef,
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
  signal,
  viewChild,
} from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { finalize, map, of, Subject, switchMap, tap } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import {
  CdkFixedSizeVirtualScroll,
  CdkVirtualForOf,
  CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';
import { ActionStorageInOut } from '../../../services/actions/action-storage-in-out';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { ActionBottle } from '../../../services/actions/action-bottle';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from '@angular/material/autocomplete';
import { ModelStorageOutInspectProcessDetail } from '../../../entities/api/ModelStorageOutInspectProcessDetail';
import { DefaultStorageInOut, StorageInOut } from '../../../entities/models/StorageInOut';
import { BottleSimple, CompCodeItemWithOpp } from '../code-item/code';
import { PipeStorageInOutStatus } from '../../../components/00200-pipes/pipe-storage-in-out-status';
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
  MatTableDataSource,
} from '@angular/material/table';
import { StorageInOutLog } from '../../../entities/models/StorageInOutLog';
import { SelectorDiscardReason } from '../../../components/00500-form-fields/selector-discard-reason';
import { DialogTaskBottleDiscard, DialogTaskBottleDiscardData } from './dialog-discard';
import { MatDialog } from '@angular/material/dialog';
import { ServiceGlobalCodeScanner } from '../../../services/ui/service-global-code-scanner';
import { ServiceShowConfirmDialog } from '../../../services/ui/service-show-confirm-dialog';

@Component({
  standalone: true,
  selector: 'storage-out-intercept-process-do',
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
    PipeTimestamp,
    LayoutDrawerOppSimple,
    MatIcon,
    MatTooltip,
    CdkVirtualForOf,
    MatAutocompleteTrigger,
    MatAutocomplete,
    MatOption,
    CompCodeItemWithOpp,
    PipeStorageInOutStatus,
    MatCell,
    MatCellDef,
    MatColumnDef,
    MatHeaderCell,
    MatHeaderRow,
    MatHeaderRowDef,
    MatRow,
    MatRowDef,
    MatTable,
    MatHeaderCellDef,
    SelectorDiscardReason,
  ],
})
export class StorageOutTaskProcessDo implements OnInit, OnDestroy {
  @Input() model: StorageInOut = DefaultStorageInOut;
  protected getModel(id: string, status: '' | 'discard'): BottleSimple {
    const result = { id, status, plants: 0, reason: '' };
    if (this.updateVersionSig() < 0) return result;
    const model = this.discardMap.get(id);
    if (model) return { ...model };
    return result;
  }
  @Input() editable = true;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();

  // 缓存已查询的瓶信息
  private loadedBottleMap = new Map<string, number>();

  // 所有在库瓶ID
  private allIdSet = new Set<string>();
  protected allIdListSig = signal<string[]>([]);

  // 过滤后的瓶ID
  protected allIdFilteredSigReadonly = computed(() => {
    const codePrefix = this.codePrefixInputValueForAllSig();
    const codeSeq = this.codeSeqInputValueForAllSig();
    if (!codePrefix) return this.allIdListSig();
    if (codeSeq) {
      const id = `${codePrefix}-${String(codeSeq).padStart(4, '0')}`;
      if (!this.allIdSet.has(id)) return [];
      return [id];
    }
    const ids: string[] = [];
    for (let index = 1; index <= 9999; index++) {
      const id = `${codePrefix}-${String(index).padStart(4, '0')}`;
      if (!this.allIdSet.has(id)) continue;
      ids.push(id);
    }
    return ids;
  });

  // 淘汰瓶
  protected discardMap = new Map<string, BottleSimple>();
  protected discardIdFilteredSigReadonly = computed(() => {
    const version = this.updateVersionSig();
    const codePrefix = this.codePrefixInputValueForDiscardSig();
    const codeSeq = this.codeSeqInputValueForDiscardSig();
    if (version < 0 || !codePrefix) {
      return [...this.discardMap.keys()].sort((a, b) => a.localeCompare(b));
    }
    if (codeSeq) {
      const id = `${codePrefix}-${String(codeSeq).padStart(4, '0')}`;
      if (!this.discardMap.has(id)) return [];
      return [id];
    }
    const ids: string[] = [];
    for (let index = 1; index <= 9999; index++) {
      const id = `${codePrefix}-${String(index).padStart(4, '0')}`;
      if (!this.discardMap.has(id)) continue;
      ids.push(id);
    }
    return ids;
  });
  protected discardStatSig = computed(() => {
    let bottles = 0;
    let plants = 0;
    const version = this.updateVersionSig();
    if (version < 0) return { bottles, plants };

    this.discardMap.forEach((discard) => {
      plants += discard.plants ?? 0;
    });
    bottles = this.discardMap.size;
    return { bottles, plants };
  });

  // 更新版本
  protected updateVersionSig = signal<number>(0);

  private readonly dispose$ = new Subject<void>();

  private actionStorageInOut = inject(ActionStorageInOut);
  private actionBottle = inject(ActionBottle);
  private serviceShowMessage = inject(ServiceShowMessage);
  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);
  private readonly serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private readonly dialog = inject(MatDialog);

  private doQuery() {
    this.onRequestStartLoading.emit('正在查询培养瓶...');
    this.actionStorageInOut
      .queryAllLogs(this.model.id)
      .pipe(
        tap((logs) => (this.tableLogsDataSource.data = logs)),
        switchMap((_) => this.actionStorageInOut.queryBottles(this.model.id)),
        tap((bottles) => {
          bottles.forEach((bottle) => {
            this.discardMap.set(bottle.idBottle, {
              id: bottle.idBottle,
              plants: bottle.plantCountActual,
              reason: bottle.discardReason,
              status: 'discard',
            });
            this.loadedBottleMap.set(bottle.idBottle, bottle.plantCountActual);
          });
        }),
        switchMap((_) => {
          if (!this.editable) return of([]);
          return this.actionBottle.queryAllIdStorageIn();
        }),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (allAvailable) => {
          const codePrefixSet = new Set<string>();
          allAvailable.forEach((available) => {
            this.allIdSet.add(available);
            codePrefixSet.add(available.substring(0, 6).toUpperCase());
          });
          this.codePrefixForAllList = [...codePrefixSet].sort((a, b) => a.localeCompare(b));
          this.allIdListSig.set(allAvailable.sort((a, b) => a.localeCompare(b)));
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  tableColumnsLogStorageOut: string[] = ['operatorTime', 'operatorName', 'status', 'oppReason'];
  protected tableLogsDataSource = new MatTableDataSource<StorageInOutLog>([]);
  protected logTrackById = (_index: number, row: StorageInOutLog) => row.id;

  protected requestUpdateToNone(id: string) {
    this.discardMap.delete(id);
    this.updateVersionSig.update((v) => v + 1);
  }

  protected defaultDiscardReasonFormControlForAll = new FormControl('');
  protected requestUpdateToDiscard(id: string, scanner = false) {
    if (!this.allIdSet.has(id)) return;
    if (this.discardMap.has(id)) return;

    const reason = this.defaultDiscardReasonFormControlForAll.value ?? '';

    if (scanner && reason) {
      const plants = this.loadedBottleMap.get(id) ?? 0;
      this.discardMap.set(id, {
        id: id,
        reason: reason,
        status: 'discard',
        plants,
      });
      this.updateVersionSig.update((v) => v + 1);
      return;
    }

    const dialogInputData: DialogTaskBottleDiscardData = {
      idBottle: id,
      reason: this.defaultDiscardReasonFormControlForAll.value ?? '',
    };

    const dialogRef = this.dialog.open<
      DialogTaskBottleDiscard,
      DialogTaskBottleDiscardData,
      DialogTaskBottleDiscardData
    >(DialogTaskBottleDiscard, {
      data: dialogInputData,
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.dispose$))
      .subscribe((result) => {
        if (result !== undefined) {
          if (!result.reason) {
            this.serviceShowMessage.showError('应选择淘汰原因。');
            return;
          }
          if (!this.allIdSet.has(result.idBottle)) return;
          const plants = this.loadedBottleMap.get(result.idBottle) ?? 0;
          this.discardMap.set(result.idBottle, {
            id: result.idBottle,
            reason: result.reason,
            status: 'discard',
            plants,
          });
          this.updateVersionSig.update((v) => v + 1);
        }
      });
  }

  protected requestSetBottleInfo(bottle: BottleSimple) {
    this.loadedBottleMap.set(bottle.id, bottle.plants);

    const model = this.discardMap.get(bottle.id);
    if (model) model.plants = bottle.plants;

    this.updateVersionSig.update((v) => v + 1);
  }

  protected requestUpdateDiscardReason(bottle: BottleSimple) {
    if (!this.allIdSet.has(bottle.id)) return;
    if (!this.discardMap.has(bottle.id)) return;

    const dialogInputData: DialogTaskBottleDiscardData = {
      idBottle: bottle.id,
      reason: bottle.reason ?? this.defaultDiscardReasonFormControlForAll.value ?? '',
    };

    const dialogRef = this.dialog.open<
      DialogTaskBottleDiscard,
      DialogTaskBottleDiscardData,
      DialogTaskBottleDiscardData
    >(DialogTaskBottleDiscard, {
      data: dialogInputData,
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.dispose$))
      .subscribe((result) => {
        if (result !== undefined) {
          if (!result.reason) {
            this.serviceShowMessage.showError('应选择淘汰原因。');
            return;
          }
          if (!this.allIdSet.has(result.idBottle)) return;
          const plants = this.loadedBottleMap.get(result.idBottle) ?? 0;
          this.discardMap.set(result.idBottle, {
            id: result.idBottle,
            reason: result.reason,
            status: 'discard',
            plants,
          });
          this.updateVersionSig.update((v) => v + 1);
        }
      });
  }

  protected requestSave() {
    if (!this.editable) return;
    if (!this.model.id) return;
    const discardList: any[] = [];
    this.discardMap.forEach((value) => {
      discardList.push({
        idBottle: value.id,
        plantCountApplied: value.plants,
        plantCountActual: value.plants,
        reason: value.reason,
      });
    });
    const model: ModelStorageOutInspectProcessDetail = {
      inOutId: this.model.id,
      issueTaskId: this.model.idTask,
      usedList: [],
      discardList: discardList,
    };
    this.onRequestStartLoading.emit('正在保存质检出库操作信息...');
    this.actionStorageInOut
      .outProcessForInspect(model)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('保存质检出库操作信息成功。');
          this.onSaved.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestFinish() {
    if (!this.editable) return;
    if (!this.model.id) return;
    const discardList: any[] = [];
    this.discardMap.forEach((value) => {
      discardList.push({
        idBottle: value.id,
        plantCountApplied: value.plants,
        plantCountActual: value.plants,
        reason: value.reason,
      });
    });
    const model: ModelStorageOutInspectProcessDetail = {
      inOutId: this.model.id,
      issueTaskId: this.model.idTask,
      usedList: [],
      discardList: discardList,
    };
    this.onRequestStartLoading.emit('正在完成质检出库...');
    this.actionStorageInOut
      .outFinishForInspect(model)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('完成质检出库操作成功。');
          this.onSaved.emit();
          this.onCancel.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestDelete() {
    if (!this.editable) return;
    if (!this.model.id) return;
    this.serviceShowConfirmDialog
      .show('确认删除？', '确认删除')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在删除...')),
        switchMap((_) => this.actionStorageInOut.deleteForInspect(this.model.id)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('删除成功。');
          this.onSaved.emit();
          this.onCancel.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestStartScan() {
    const reason = this.defaultDiscardReasonFormControlForAll.value ?? '';
    if (!!reason) {
      this.serviceGlobalCodeScanner
        .start()
        .pipe(takeUntil(this.dispose$))
        .subscribe((v) => this.requestUpdateToDiscard(v, true));
      return;
    }

    const dialogInputData: DialogTaskBottleDiscardData = {
      idBottle: '',
      reason: this.defaultDiscardReasonFormControlForAll.value ?? '',
    };

    const dialogRef = this.dialog.open<
      DialogTaskBottleDiscard,
      DialogTaskBottleDiscardData,
      DialogTaskBottleDiscardData
    >(DialogTaskBottleDiscard, {
      data: dialogInputData,
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.dispose$))
      .subscribe((result) => {
        if (result !== undefined) {
          if (!result.reason) {
            this.serviceShowMessage.showError('应选择淘汰原因。');
            return;
          }
          this.defaultDiscardReasonFormControlForAll.setValue(result.reason);
          this.serviceGlobalCodeScanner
            .start()
            .pipe(takeUntil(this.dispose$))
            .subscribe((v) => this.requestUpdateToDiscard(v, true));
        }
      });
  }

  // 所有瓶ID前缀
  private codePrefixForAllList: string[] = [];
  // 瓶ID 过滤条件
  protected codePrefixFormControlForAll = new FormControl('');
  private codePrefixInputValueForAllSig = signal<string>('');
  protected codeSeqFormControlForAll = new FormControl<number | null>(null);
  private codeSeqInputValueForAllSig = signal<string>('');
  // 过滤后的瓶ID前缀
  protected codePrefixAllFilteredSig = signal<string[]>([]);
  // 瓶码前缀 autocomplete 输入框
  private codePrefixInputElementForAll =
    viewChild.required<ElementRef<HTMLInputElement>>('codePrefixInputForAll');
  protected requestFilterCodePrefixListForAll() {
    const filterValue = this.codePrefixInputElementForAll().nativeElement.value.toUpperCase();
    this.codePrefixAllFilteredSig.set(
      this.codePrefixForAllList.filter((o) => o.includes(filterValue)),
    );
  }

  // 淘汰瓶ID前缀
  private codePrefixDiscardListSig = computed(() => {
    const version = this.updateVersionSig();
    if (version < 0) return [] as string[];
    const set = new Set<string>(
      [...this.discardMap.keys()].map((v) => v.substring(0, 6).toUpperCase()),
    );
    return [...set].sort((a, b) => a.localeCompare(b));
  });
  // 瓶ID 过滤条件
  protected codePrefixFormControlForDiscard = new FormControl('');
  private codePrefixInputValueForDiscardSig = signal<string>('');
  protected codeSeqFormControlForDiscard = new FormControl<number | null>(null);
  private codeSeqInputValueForDiscardSig = signal<string>('');
  // 过滤后的瓶ID前缀
  protected codePrefixDiscardFilteredSig = signal<string[]>([]);
  // 瓶码前缀 autocomplete 输入框
  private codePrefixInputElementForDiscard = viewChild.required<ElementRef<HTMLInputElement>>(
    'codePrefixInputForDiscard',
  );
  protected requestFilterCodePrefixListForDiscard() {
    const filterValue = this.codePrefixInputElementForDiscard().nativeElement.value.toUpperCase();
    this.codePrefixDiscardFilteredSig.set(
      this.codePrefixDiscardListSig().filter((o) => o.includes(filterValue)),
    );
  }

  ngOnInit(): void {
    this.codePrefixFormControlForAll.valueChanges
      .pipe(
        map((codePrefix) => (codePrefix ?? '').trim().toUpperCase()),
        takeUntil(this.dispose$),
      )
      .subscribe((codePrefix) => this.codePrefixInputValueForAllSig.set(codePrefix));
    this.codeSeqFormControlForAll.valueChanges
      .pipe(takeUntil(this.dispose$))
      .subscribe((codeSeq) => {
        if (codeSeq === null || codeSeq === undefined || codeSeq < 1) {
          this.codeSeqInputValueForAllSig.set('');
          return;
        }
        codeSeq = Number(codeSeq);
        if (!Number.isInteger(codeSeq)) {
          this.codeSeqInputValueForAllSig.set('');
          return;
        }
        this.codeSeqInputValueForAllSig.set(codeSeq.toString());
      });
    this.codePrefixFormControlForDiscard.valueChanges
      .pipe(
        map((codePrefix) => (codePrefix ?? '').trim().toUpperCase()),
        takeUntil(this.dispose$),
      )
      .subscribe((codePrefix) => this.codePrefixInputValueForDiscardSig.set(codePrefix));
    this.codeSeqFormControlForDiscard.valueChanges
      .pipe(takeUntil(this.dispose$))
      .subscribe((codeSeq) => {
        if (codeSeq === null || codeSeq === undefined || codeSeq < 1) {
          this.codeSeqInputValueForDiscardSig.set('');
          return;
        }
        codeSeq = Number(codeSeq);
        if (!Number.isInteger(codeSeq)) {
          this.codeSeqInputValueForDiscardSig.set('');
          return;
        }
        this.codeSeqInputValueForDiscardSig.set(codeSeq.toString());
      });

    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
