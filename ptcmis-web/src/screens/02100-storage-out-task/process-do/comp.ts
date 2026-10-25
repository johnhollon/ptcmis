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
import { ActionBottle } from '../../../services/actions/action-bottle';
import { ModelBottleSimple } from '../../../entities/api/ModelBottleSimple';
import { StorageInOutBottle } from '../../../entities/models/StorageInOutBottle';
import { APIConst } from '../../../entities/api/const-api';
import { binarySearchInsertPos } from '../../../services/utils/binarySearchInsertPos';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { CompCode } from '../component-code-item/code';
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from '@angular/material/autocomplete';
import { ModelStorageOutTaskProcessDetail } from '../../../entities/api/ModelStorageOutTaskProcessDetail';
import { ServiceGlobalCodeScanner } from '../../../services/ui/service-global-code-scanner';

@Component({
  standalone: true,
  selector: 'storage-out-task-process-do',
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
    MatIcon,
    MatTooltip,
    CdkVirtualForOf,
    CompCode,
    CompCode,
    MatAutocompleteTrigger,
    MatAutocomplete,
    MatOption,
  ],
})
export class StorageOutTaskProcessDo implements OnInit, OnDestroy {
  @Input() model: StorageOutTaskView = StorageOutTaskViewDefault;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();

  protected allMap = new Map<string, ModelBottleSimple>();
  protected usedMap = new Map<string, StorageInOutBottle>();
  private codePrefixSig = signal<string>('');
  private codeSeqSig = signal<string>('');
  protected allSig = signal<string[]>([]);
  protected allFilteredSig = computed(() => {
    const codePrefix = this.codePrefixSig();
    const codeSeq = this.codeSeqSig();
    if (!codePrefix) return this.allSig();
    if (codeSeq) {
      const id = `${codePrefix}-${String(codeSeq).padStart(4, '0')}`;
      const model = this.allMap.get(id);
      if (!model) return [];
      return [id];
    }
    const ids: string[] = [];
    for (let index = 1; index <= 9999; index++) {
      const id = `${codePrefix}-${String(index).padStart(4, '0')}`;
      const model = this.allMap.get(id);
      if (!model) continue;
      ids.push(id);
    }
    return ids;
  });
  protected allCodePrefixSig = signal<string[]>([]);
  protected codePrefixFilteredSig = signal<string[]>([]);
  protected usedSig = signal<string[]>([]);
  protected updateVersionSig = signal<number>(0);
  protected usedStatSig = computed(() => {
    let bottles = 0;
    let plants = 0;
    const list = this.usedSig();
    const version = this.updateVersionSig();
    if (version >= 0) {
      list.forEach((id) => {
        plants += this.usedMap.get(id)?.plantCountActual ?? 0;
      });
      bottles = this.usedMap.size;
    }
    return { bottles, plants };
  });

  protected codePrefixFormControl = new FormControl('');
  protected codeSeqFormControl = new FormControl<number | null>(null);

  private readonly dispose$ = new Subject<void>();

  private actionStorageInOut = inject(ActionStorageInOut);
  private actionBottle = inject(ActionBottle);
  private serviceShowMessage = inject(ServiceShowMessage);
  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);

  protected requestSave() {
    if (!this.model.id || !this.model.idTask) return;
    const usedList: any[] = [];
    this.usedMap.forEach((value) => {
      usedList.push({
        idBottle: value.idBottle,
        plantCountApplied: value.plantCountApplied,
        plantCountActual: value.plantCountActual,
      });
    });
    const model: ModelStorageOutTaskProcessDetail = {
      inOutId: this.model.id,
      issueTaskId: this.model.idTask,
      usedList: usedList,
    };
    this.onRequestStartLoading.emit('正在保存出库操作信息...');
    this.actionStorageInOut
      .outProcessForTask(model)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('保存出库操作信息成功。');
          this.onSaved.emit();
          this.onCancel.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  private doQuery() {
    this.onRequestStartLoading.emit('正在查询可用培养瓶...');
    this.actionStorageInOut
      .queryBottles(this.model.id)
      .pipe(
        tap((bottles) => {
          const usedList: string[] = [];
          bottles.forEach((bottle) => {
            if (bottle.status === APIConst.storageInOutBottleStatus.normal) {
              usedList.push(bottle.idBottle);
              this.usedMap.set(bottle.idBottle, bottle);
            }
          });
          this.usedSig.set(usedList.sort((a, b) => a.localeCompare(b)));
        }),
        switchMap((_) => {
          return this.actionBottle.queryAllEnabledIdsForTaskParents(this.model.idTask);
        }),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (allAvailable) => {
          const codePrefixSet = new Set<string>();
          allAvailable.forEach((available) => {
            this.allMap.set(available.id, available);
            codePrefixSet.add(available.id.substring(0, 6));
          });
          this.allSig.set(allAvailable.map((a) => a.id).sort((a, b) => a.localeCompare(b)));
          this.allCodePrefixSig.set(Array.from(codePrefixSet).sort((a, b) => a.localeCompare(b)));
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  requestScanUsed() {
    this.serviceGlobalCodeScanner
      .start('扫码出库')
      .pipe(takeUntil(this.dispose$))
      .subscribe((v) => {
        this.requestUpdateToUsed(v);
      });
  }

  requestBatchUsed(countInput: string | number | null | undefined) {
    const prefixInput = this.codePrefixSig();
    // 必须输入了前缀
    if (!prefixInput || !prefixInput.trim() || prefixInput.trim().length !== 6) return;
    // 用户输入的瓶数
    let count = Number(countInput);
    if (!Number.isInteger(count) || count < 1) return;
    // 当前过滤后的可用ID列表
    const list = this.allFilteredSig();
    count = Math.min(count, list.length);
    let selected = 0;
    for (const id of list) {
      if (selected >= count) break;
      const model = this.allMap.get(id);
      if (!model) continue;
      const used = this.usedMap.get(id);
      if (used) continue;
      this.usedMap.set(id, {
        idInOut: this.model.id,
        idBottle: id,
        variety: this.model.variety,
        varietyCode: this.model.varietyCode,
        explantCollectionBatch: this.model.explantCollectionBatch,
        generation: this.model.parentGeneration,
        ignoreStats: APIConst.booleans.BOOLEAN_FALSE,
        inOutBottleType: 'parent_out',
        plantCountApplied: model.plantCount,
        plantCountActual: model.plantCount,
        keep: APIConst.booleans.BOOLEAN_FALSE,
        status: APIConst.storageInOutBottleStatus.normal,
        discardReason: '',
      });
      this.usedSig.update((ids) => {
        const insertPos = binarySearchInsertPos(ids, id);
        if (insertPos < 0) return ids;
        const nextArr = [...ids];
        nextArr.splice(insertPos, 0, id);
        return nextArr;
      });
      selected++;
    }
  }

  protected requestUpdateToUsed(id: string) {
    const model = this.allMap.get(id);
    if (!model) return;
    const used = this.usedMap.get(id);
    if (used) return;
    this.usedMap.set(id, {
      idInOut: this.model.id,
      idBottle: id,
      variety: this.model.variety,
      varietyCode: this.model.varietyCode,
      explantCollectionBatch: this.model.explantCollectionBatch,
      generation: this.model.parentGeneration,
      ignoreStats: APIConst.booleans.BOOLEAN_FALSE,
      inOutBottleType: 'parent_out',
      plantCountApplied: model.plantCount,
      plantCountActual: model.plantCount,
      keep: APIConst.booleans.BOOLEAN_FALSE,
      status: APIConst.storageInOutBottleStatus.normal,
      discardReason: '',
    });
    this.usedSig.update((ids) => {
      const insertPos = binarySearchInsertPos(ids, id);
      if (insertPos < 0) return ids;
      const nextArr = [...ids];
      nextArr.splice(insertPos, 0, id);
      return nextArr;
    });
  }

  protected requestUpdateToNone(id: string) {
    const used = this.usedMap.get(id);
    if (!used) return;
    if (used) {
      this.usedMap.delete(id);
      this.usedSig.update((ids) => {
        const idx = ids.indexOf(id);
        if (idx === -1) return ids;
        const nextArr = [...ids];
        nextArr.splice(idx, 1);
        return nextArr;
      });
    }
    this.updateVersionSig.update((v) => v + 1);
  }

  protected requestUpdatePlantCount(id: string, plantCount: number) {
    const model = this.allMap.get(id);
    if (!model) return;
    if (!plantCount || plantCount < 0) return;
    let count = Number(plantCount);
    if (!Number.isInteger(count)) return;

    const used = this.usedMap.get(id);
    if (used) {
      used.plantCountActual = plantCount;
    }
    model.plantCount = plantCount;
    this.updateVersionSig.update((v) => v + 1);
  }

  private codePrefixInput = viewChild.required<ElementRef<HTMLInputElement>>('codePrefixInput');
  protected filterCodePrefixList() {
    const filterValue = this.codePrefixInput().nativeElement.value.toUpperCase();
    this.codePrefixFilteredSig.set(
      this.allCodePrefixSig().filter((o) => o.toUpperCase().includes(filterValue)),
    );
  }

  ngOnInit(): void {
    this.codePrefixFormControl.valueChanges
      .pipe(
        map((codePrefix) => (codePrefix ?? '').trim().toUpperCase()),
        takeUntil(this.dispose$),
      )
      .subscribe((codePrefix) => this.codePrefixSig.set(codePrefix));
    this.codeSeqFormControl.valueChanges.pipe(takeUntil(this.dispose$)).subscribe((codeSeq) => {
      if (codeSeq === null || codeSeq === undefined || codeSeq < 1) {
        this.codeSeqSig.set('');
        return;
      }
      codeSeq = Number(codeSeq);
      if (!Number.isInteger(codeSeq)) {
        this.codeSeqSig.set('');
        return;
      }
      this.codeSeqSig.set(codeSeq.toString());
    });

    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
