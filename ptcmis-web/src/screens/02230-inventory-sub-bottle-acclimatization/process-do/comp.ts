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
import { CompCodeItem } from '../code-item/code';
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
import { ServiceShowConfirmDialog } from '../../../services/ui/service-show-confirm-dialog';
import { ModelBottleSimple } from '../../../entities/api/ModelBottleSimple';
import { DateRange, ModelDateRange } from '../../../components/00500-form-fields/date-range';
import { User } from '../../../entities/models/User';
import { SelectUser } from '../../../components/user/select/select-user';
import { ServiceGlobalCodeScanner } from '../../../services/ui/service-global-code-scanner';

@Component({
  standalone: true,
  selector: 'process-do-inventory-sub-bottle-acclimatization',
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
    CompCodeItem,
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
    DateRange,
    SelectUser,
  ],
})
export class CompProcessDo implements OnInit, OnDestroy {
  @Input() model: StorageInOut = DefaultStorageInOut;
  @Input() editable = true;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();

  // 更新版本
  protected updateVersionSig = signal<number>(0);

  // 在库瓶
  private readonly allBottleMap = new Map<string, ModelBottleSimple>();
  protected allBottleSig = computed(() => {
    if (this.updateVersionSig() < 0 || this.allBottleMap.size < 1) return [] as ModelBottleSimple[];
    const codePrefix = this.codePrefixInputValueForAllSig();
    const codeSeq = this.codeSeqInputValueForAllSig();
    const userId = this.userIdInputValueForAllSig();
    const dateRange = this.dateRangeInputValueForAllSig();
    const varietyCode = this.varietyCodeInputValueForAllSig();
    const generation = this.generationInputValueForAllSig();
    const batch = this.batchInputValueForAllSig();
    if (!codePrefix && !userId && !dateRange && !varietyCode && generation == null && !batch)
      return [...this.allBottleMap.values()].sort((a, b) => a.id.localeCompare(b.id));
    if (codePrefix && codeSeq) {
      const id = `${codePrefix}-${String(codeSeq).padStart(4, '0')}`;
      const bottle = this.allBottleMap.get(id);
      if (!bottle) return [];
      if (userId && bottle.lastOperatorId !== userId) return [];
      if (dateRange) {
        if (dateRange.start && dateRange.start < bottle.taskTime) return [];
        if (dateRange.end && dateRange.end > bottle.taskTime) return [];
      }
      if (
        generation !== null &&
        generation !== undefined &&
        generation >= 0 &&
        bottle.generation !== generation
      )
        return [];
      if (
        batch !== null &&
        batch !== undefined &&
        batch > 0 &&
        bottle.explantCollectionBatch !== batch
      )
        return [];
      if (
        varietyCode &&
        !bottle.variety.includes(varietyCode) &&
        !bottle.varietyCode.includes(varietyCode)
      )
        return [];
      return [bottle];
    }
    const ids: ModelBottleSimple[] = [];
    if (codePrefix) {
      for (let index = 1; index <= 9999; index++) {
        const id = `${codePrefix}-${String(index).padStart(4, '0')}`;
        const bottle = this.allBottleMap.get(id);
        if (bottle) {
          if (userId && bottle.lastOperatorId !== userId) continue;
          if (dateRange) {
            if (dateRange.start && dateRange.start > bottle.taskTime) continue;
            if (dateRange.end && dateRange.end < bottle.taskTime) continue;
          }
          if (
            generation !== null &&
            generation !== undefined &&
            generation >= 0 &&
            bottle.generation !== generation
          )
            continue;
          if (
            batch !== null &&
            batch !== undefined &&
            batch > 0 &&
            bottle.explantCollectionBatch !== batch
          )
            continue;
          if (
            varietyCode &&
            !bottle.variety.includes(varietyCode) &&
            !bottle.varietyCode.includes(varietyCode)
          )
            continue;
          ids.push(bottle);
        }
      }
      return ids;
    }
    [...this.allBottleMap.values()].forEach((parent) => {
      if (userId && parent.lastOperatorId !== userId) return;
      if (dateRange) {
        if (dateRange.start && dateRange.start > parent.taskTime) return;
        if (dateRange.end && dateRange.end < parent.taskTime) return;
      }
      if (
        generation !== null &&
        generation !== undefined &&
        generation >= 0 &&
        parent.generation !== generation
      )
        return;
      if (
        batch !== null &&
        batch !== undefined &&
        batch > 0 &&
        parent.explantCollectionBatch !== batch
      )
        return;
      if (
        varietyCode &&
        !parent.variety.includes(varietyCode) &&
        !parent.varietyCode.includes(varietyCode)
      )
        return;
      ids.push(parent);
    });
    return ids.sort((a, b) => a.id.localeCompare(b.id));
  });
  private codePrefixForAllList: string[] = [];
  protected codePrefixFormControlForAll = new FormControl('');
  private codePrefixInputValueForAllSig = signal<string>('');
  protected codePrefixForAllFilteredSig = signal<string[]>([]);
  private codePrefixInputElementForAll =
    viewChild.required<ElementRef<HTMLInputElement>>('codePrefixInputForAll');
  protected codeSeqFormControlForAll = new FormControl<number | null>(null);
  private codeSeqInputValueForAllSig = signal<string>('');
  protected requestFilterCodePrefixListForAll() {
    const filterValue = this.codePrefixInputElementForAll().nativeElement.value.toUpperCase();
    this.codePrefixForAllFilteredSig.set(
      this.codePrefixForAllList.filter((o) => o.includes(filterValue)),
    );
  }
  protected varietyCodeFormControlForAll = new FormControl<string | null>(null);
  protected varietyCodeInputValueForAllSig = signal<string>('');
  protected generationFormControlForAll = new FormControl<number | null>(null);
  protected generationInputValueForAllSig = signal<number | null>(null);
  protected batchFormControlForAll = new FormControl<number | null>(null);
  protected batchInputValueForAllSig = signal<number>(0);
  protected operatorFormControlForAll = new FormControl<User>(null as unknown as User);
  private userIdInputValueForAllSig = signal<string>('');
  protected operatorTimeFormControlForAll = new FormControl<ModelDateRange>(
    null as unknown as ModelDateRange,
  );
  private dateRangeInputValueForAllSig = signal<ModelDateRange | null>(null);

  // 出库瓶
  protected readonly outBottleMap = new Map<string, ModelBottleSimple>();
  protected outBottleSig = computed(() => {
    if (this.updateVersionSig() < 0 || this.outBottleMap.size < 1) return [] as ModelBottleSimple[];
    return [...this.outBottleMap.values()].sort((a, b) => a.id.localeCompare(b.id));
  });
  protected outStatSig = computed(() => {
    let plants = 0;
    let bottles = 0;
    if (this.updateVersionSig() < 0) return { bottles, plants };
    this.outBottleMap.forEach((b) => {
      plants += b.plantCount ?? 0;
    });
    bottles = this.outBottleMap.size;
    return { bottles, plants };
  });

  private readonly dispose$ = new Subject<void>();

  private actionStorageInOut = inject(ActionStorageInOut);
  private actionBottle = inject(ActionBottle);
  private serviceShowMessage = inject(ServiceShowMessage);
  private readonly serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);

  private doQuery() {
    this.onRequestStartLoading.emit('正在查询培养瓶...');
    this.actionStorageInOut
      .queryAllLogs(this.model.id)
      .pipe(
        tap((logs) => (this.tableLogsDataSource.data = logs)),
        switchMap((_) => {
          return this.actionBottle.queryBottlesForInventorySubBottle(this.model.id);
        }),
        tap((bottles) => {
          bottles.forEach((bottle) => {
            this.outBottleMap.set(bottle.id, bottle);
          });
        }),
        switchMap((_) => {
          if (!this.editable) return of([]);
          return this.actionBottle.queryAllStorageIn();
        }),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (bottles) => {
          const codePrefixSet = new Set<string>();
          bottles.forEach((bottle) => {
            this.allBottleMap.set(bottle.id, bottle);
            codePrefixSet.add(bottle.id.substring(0, 6).toUpperCase());
          });
          this.codePrefixForAllList = [...codePrefixSet].sort((a, b) => a.localeCompare(b));
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  tableColumnsLogStorageOut: string[] = ['operatorTime', 'operatorName', 'status', 'oppReason'];
  protected tableLogsDataSource = new MatTableDataSource<StorageInOutLog>([]);
  protected logTrackById = (_index: number, row: StorageInOutLog) => row.id;

  protected requestUpdateToNone(bottle: ModelBottleSimple) {
    if (!this.editable) return;
    if (!this.outBottleMap.has(bottle.id)) return;
    this.serviceShowConfirmDialog
      .show(`确认撤销 ${bottle.id} 的出库？`)
      .pipe(
        filter((v) => v === 0),
        takeUntil(this.dispose$),
      )
      .subscribe((_) => {
        this.outBottleMap.delete(bottle.id);
        this.updateVersionSig.update((v) => v + 1);
      });
  }

  protected requestUpdateToOut(bottle: ModelBottleSimple) {
    if (!this.editable) return;
    if (this.outBottleMap.has(bottle.id)) return;
    this.serviceShowConfirmDialog
      .show(`确认对 ${bottle.id} 出库？`)
      .pipe(
        filter((v) => v === 0),
        takeUntil(this.dispose$),
      )
      .subscribe((_) => {
        this.outBottleMap.set(bottle.id, bottle);
        this.updateVersionSig.update((v) => v + 1);
      });
  }

  protected requestScanToOut() {
    this.serviceGlobalCodeScanner
      .start('扫码减瓶')
      .pipe(takeUntil(this.dispose$))
      .subscribe((v) => {
        const item = this.allBottleMap.get(v);
        if (!item) return;
        this.outBottleMap.set(item.id, item);
        this.updateVersionSig.update((v) => v + 1);
      });
  }

  protected requestSave() {
    if (!this.editable) return;
    if (!this.model.id) return;
    const discardList: any[] = [];
    this.outBottleMap.forEach((value) => {
      discardList.push({
        idBottle: value.id,
        plantCountApplied: value.plantCount,
        plantCountActual: value.plantCount,
        reason: '',
      });
    });
    const model: ModelStorageOutInspectProcessDetail = {
      inOutId: this.model.id,
      issueTaskId: this.model.idTask,
      usedList: [],
      discardList: discardList,
    };
    this.onRequestStartLoading.emit('正在保存...');
    this.actionStorageInOut
      .outProcessForAcclimatizationOut(model)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('保存成功。');
          this.onSaved.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestFinish() {
    if (!this.editable) return;
    if (!this.model.id) return;
    const discardList: any[] = [];
    this.outBottleMap.forEach((value) => {
      discardList.push({
        idBottle: value.id,
        plantCountApplied: value.plantCount,
        plantCountActual: value.plantCount,
        reason: '',
      });
    });
    const model: ModelStorageOutInspectProcessDetail = {
      inOutId: this.model.id,
      issueTaskId: this.model.idTask,
      usedList: [],
      discardList: discardList,
    };
    this.onRequestStartLoading.emit('正在完成任务...');
    this.actionStorageInOut
      .outFinishForAcclimatizationOut(model)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('完成任务操作成功。');
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
        switchMap((_) => this.actionStorageInOut.deleteForAcclimatizationOut(this.model.id)),
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

  ngOnInit(): void {
    this.operatorFormControlForAll.valueChanges
      .pipe(
        map((user) => user?.id ?? ''),
        takeUntil(this.dispose$),
      )
      .subscribe((userId) => this.userIdInputValueForAllSig.set(userId));
    this.operatorTimeFormControlForAll.valueChanges
      .pipe(takeUntil(this.dispose$))
      .subscribe((dataRange) => this.dateRangeInputValueForAllSig.set(dataRange));
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
    this.varietyCodeFormControlForAll.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((v) => {
      this.varietyCodeInputValueForAllSig.set(v ?? '');
    });
    this.generationFormControlForAll.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((v) => {
      this.generationInputValueForAllSig.set(v ?? null);
    });
    this.batchFormControlForAll.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((v) => {
      this.batchInputValueForAllSig.set(v ?? 0);
    });
    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
