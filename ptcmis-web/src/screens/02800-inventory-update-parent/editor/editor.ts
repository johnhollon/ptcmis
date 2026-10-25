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
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatError, MatFormField, MatInput, MatLabel, MatSuffix } from '@angular/material/input';
import { finalize, map, of, Subject, switchMap, tap } from 'rxjs';
import { Bottle } from '../../../entities/models/Bottle';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { filter, takeUntil } from 'rxjs/operators';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { ServiceShowConfirmDialog } from '../../../services/ui/service-show-confirm-dialog';
import { ActionBottle } from '../../../services/actions/action-bottle';
import { MatTooltip } from '@angular/material/tooltip';
import { MatDivider } from '@angular/material/list';
import { PipeBottleStorageStatus } from '../../../components/00200-pipes/pipe-bottle-storage-status';
import { PipeStorageReason } from '../../../components/00200-pipes/pipe-storage-reason';
import {
  CdkFixedSizeVirtualScroll,
  CdkVirtualForOf,
  CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';
import { CompCodeItemParent } from '../../02400-inventory-add-bottle/code-item-parent/code';
import { DateRange, ModelDateRange } from '../../../components/00500-form-fields/date-range';
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from '@angular/material/autocomplete';
import { MatIcon } from '@angular/material/icon';
import { SelectUser } from '../../../components/user/select/select-user';
import { ModelBottleSimple } from '../../../entities/api/ModelBottleSimple';
import { User } from '../../../entities/models/User';
import { ServiceGlobalCodeScanner } from '../../../services/ui/service-global-code-scanner';

@Component({
  selector: 'editor-inventory-update-parent',
  standalone: true,
  templateUrl: 'editor.html',
  styleUrls: ['editor.scss'],
  imports: [
    MatFormField,
    MatLabel,
    MatError,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    LayoutDrawerOppSimple,
    PipeTimestamp,
    MatTooltip,
    MatDivider,
    PipeBottleStorageStatus,
    PipeStorageReason,
    CdkFixedSizeVirtualScroll,
    CdkVirtualForOf,
    CdkVirtualScrollViewport,
    CompCodeItemParent,
    DateRange,
    MatAutocomplete,
    MatAutocompleteTrigger,
    MatIcon,
    MatOption,
    SelectUser,
    MatIconButton,
    MatSuffix,
  ],
})
export class CompEditor implements OnInit, OnDestroy {
  @Input() editable = true;
  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();
  @Input() model: Bottle = null as unknown as Bottle;

  dispose$ = new Subject<void>();

  protected updateVersionSig = signal<number>(0);
  private readonly parentMap = new Map<string, ModelBottleSimple>();
  protected hasParentsSig = computed(() => {
    return this.updateVersionSig() >= 0 && this.parentMap.size > 0;
  });
  // 过滤后的瓶ID
  protected parentSig = computed(() => {
    if (this.updateVersionSig() < 0 || this.parentMap.size < 1) return [] as ModelBottleSimple[];
    const codePrefix = this.codePrefixInputValueForParentSig();
    const codeSeq = this.codeSeqInputValueForParentSig();
    const userId = this.userIdInputValueForParentSig();
    const dateRange = this.dateRangeInputValueForParentSig();
    if (!codePrefix && !userId && !dateRange)
      return [...this.parentMap.values()].sort((a, b) => a.id.localeCompare(b.id));
    if (codePrefix && codeSeq) {
      const id = `${codePrefix}-${String(codeSeq).padStart(4, '0')}`;
      const parent = this.parentMap.get(id);
      if (!parent) return [];
      if (userId && parent.lastOperatorId !== userId) return [];
      if (dateRange) {
        if (dateRange.start && dateRange.start < parent.taskTime) return [];
        if (dateRange.end && dateRange.end > parent.taskTime) return [];
      }
      return [parent];
    }
    const ids: ModelBottleSimple[] = [];
    if (codePrefix) {
      for (let index = 1; index <= 9999; index++) {
        const id = `${codePrefix}-${String(index).padStart(4, '0')}`;
        const parent = this.parentMap.get(id);
        if (parent) {
          if (userId && parent.lastOperatorId !== userId) continue;
          if (dateRange) {
            if (dateRange.start && dateRange.start > parent.taskTime) continue;
            if (dateRange.end && dateRange.end < parent.taskTime) continue;
          }
          ids.push(parent);
        }
      }
      return ids;
    }
    [...this.parentMap.values()].forEach((parent) => {
      if (userId && parent.lastOperatorId !== userId) return;
      if (dateRange) {
        if (dateRange.start && dateRange.start > parent.taskTime) return;
        if (dateRange.end && dateRange.end < parent.taskTime) return;
      }
      ids.push(parent);
    });
    return ids.sort((a, b) => a.id.localeCompare(b.id));
  });
  // 所有瓶ID前缀
  private codePrefixForParentList: string[] = [];
  // 瓶ID 过滤条件
  protected codePrefixFormControlForParent = new FormControl('');
  private codePrefixInputValueForParentSig = signal<string>('');
  protected codeSeqFormControlForParent = new FormControl<number | null>(null);
  private codeSeqInputValueForParentSig = signal<string>('');
  private userIdInputValueForParentSig = signal<string>('');
  private dateRangeInputValueForParentSig = signal<ModelDateRange | null>(null);
  // 过滤后的瓶ID前缀
  protected codePrefixForParentFilteredSig = signal<string[]>([]);
  // 瓶码前缀 autocomplete 输入框
  private codePrefixInputElementForParent = viewChild.required<ElementRef<HTMLInputElement>>(
    'codePrefixInputForParent',
  );
  protected requestFilterCodePrefixListForParent() {
    const filterValue = this.codePrefixInputElementForParent().nativeElement.value.toUpperCase();
    this.codePrefixForParentFilteredSig.set(
      this.codePrefixForParentList.filter((o) => o.includes(filterValue)),
    );
  }
  protected operatorFormControlForParent = new FormControl<User>(null as unknown as User);
  protected operatorTimeFormControlForParent = new FormControl<ModelDateRange>(
    null as unknown as ModelDateRange,
  );

  private actionBottle = inject(ActionBottle);
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private serviceShowMessage = inject(ServiceShowMessage);
  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);

  protected formControlParent = new FormControl<string>('');
  ngOnInit(): void {
    this.operatorFormControlForParent.valueChanges
      .pipe(
        map((user) => user?.id ?? ''),
        takeUntil(this.dispose$),
      )
      .subscribe((userId) => this.userIdInputValueForParentSig.set(userId));
    this.operatorTimeFormControlForParent.valueChanges
      .pipe(takeUntil(this.dispose$))
      .subscribe((dataRange) => this.dateRangeInputValueForParentSig.set(dataRange));
    this.codePrefixFormControlForParent.valueChanges
      .pipe(
        map((codePrefix) => (codePrefix ?? '').trim().toUpperCase()),
        takeUntil(this.dispose$),
      )
      .subscribe((codePrefix) => this.codePrefixInputValueForParentSig.set(codePrefix));
    this.codeSeqFormControlForParent.valueChanges
      .pipe(takeUntil(this.dispose$))
      .subscribe((codeSeq) => {
        if (codeSeq === null || codeSeq === undefined || codeSeq < 1) {
          this.codeSeqInputValueForParentSig.set('');
          return;
        }
        codeSeq = Number(codeSeq);
        if (!Number.isInteger(codeSeq)) {
          this.codeSeqInputValueForParentSig.set('');
          return;
        }
        this.codeSeqInputValueForParentSig.set(codeSeq.toString());
      });
    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  private doQuery() {
    this.parentMap.clear();
    this.onRequestStartLoading.emit('正在查询瓶码...');
    this.actionBottle
      .queryAllEnabledParentIdsForChangeParent(this.model.id)
      .pipe(
        tap((bottles) => {
          const codePrefixForParent = new Set<string>();
          bottles.forEach((bottle) => {
            if (bottle.id !== this.model.id) {
              codePrefixForParent.add(bottle.id.substring(0, 6).toUpperCase());
              this.parentMap.set(bottle.id, bottle);
            }
          });
          this.codePrefixForParentList = [...codePrefixForParent].sort((a, b) =>
            a.localeCompare(b),
          );
        }),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        complete: () => {
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestSetParentBottle(bottle: ModelBottleSimple) {
    this.formControlParent.setValue(bottle.id);
  }

  protected requestScanSetParentBottle() {
    this.serviceGlobalCodeScanner
      .start('扫码选择母瓶')
      .pipe(takeUntil(this.dispose$))
      .subscribe((v) => {
        const item = this.parentMap.get(v);
        if (!item) return;
        this.requestSetParentBottle(item);
      });
  }

  protected requestSave() {
    const parentId = this.formControlParent.value ?? '';
    this.serviceShowConfirmDialog
      .show(
        parentId ? `确认更正母瓶为 ${parentId}？` : '确认删除母瓶？',
        parentId ? '操作确认' : '确认删除母瓶',
      )
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在更正母瓶...')),
        switchMap((_) =>
          this.actionBottle.changeParentBottle({
            id: this.model.id,
            idParentNew: parentId,
          }),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('更正成功。');
          this.onSaved.emit();
          this.onCancel.emit();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestCancel() {
    this.onCancel.emit();
  }
}
