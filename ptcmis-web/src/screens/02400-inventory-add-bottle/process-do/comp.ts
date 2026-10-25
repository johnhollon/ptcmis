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
  CdkFixedSizeVirtualScroll,
  CdkVirtualForOf,
  CdkVirtualScrollViewport,
} from '@angular/cdk/scrolling';
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
import { FormBuilder, FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteTrigger, MatOption } from '@angular/material/autocomplete';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';
import {
  MatError,
  MatFormField,
  MatInput,
  MatInputModule,
  MatLabel,
} from '@angular/material/input';
import { MatTooltip } from '@angular/material/tooltip';
import { finalize, map, of, Subject, switchMap, tap } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { SelectUser } from '../../../components/user/select/select-user';
import { ModelBottleSimple } from '../../../entities/api/ModelBottleSimple';
import {
  InventoryAddBottle,
  InventoryAddBottleDefault,
} from '../../../entities/models/InventoryAddBottle';
import { InventoryAddBottleDetail } from '../../../entities/models/InventoryAddBottleDetail';
import { User } from '../../../entities/models/User';
import { ActionBottle } from '../../../services/actions/action-bottle';
import { ActionInventoryAddBottle } from '../../../services/actions/action-inventory-add-bottle';
import { ServiceShowConfirmDialog } from '../../../services/ui/service-show-confirm-dialog';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { CompCodeItemParent } from '../code-item-parent/code';
import { CompCode } from '../component-code-item/code';
import {
  DialogAssignCode,
  DialogAssignCodeInputData,
  DialogAssignCodeResponseData,
} from './dialogs/dialog-assign-code';
import {
  MatDatepicker,
  MatDatepickerInput,
  MatDatepickerModule,
  MatDatepickerToggle,
} from '@angular/material/datepicker';
import {
  MatTimepicker,
  MatTimepickerInput,
  MatTimepickerOption,
  MatTimepickerToggle,
} from '@angular/material/timepicker';
import {
  MatContextMenuTrigger,
  MatMenu,
  MatMenuContent,
  MatMenuItem,
} from '@angular/material/menu';
import { ActionPrinter } from '../../../services/actions/action-printer';
import { ServiceGlobalCodeScanner } from '../../../services/ui/service-global-code-scanner';
import { DateRange, ModelDateRange } from '../../../components/00500-form-fields/date-range';
import { PipeStorageReason } from '../../../components/00200-pipes/pipe-storage-reason';
import { BottleCodeComplementTaskDefault } from '../../../entities/models/BottleCodeComplementTask';

@Component({
  standalone: true,
  selector: 'process-do-inventory-add-bottle',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    MatInputModule,
    ReactiveFormsModule,
    MatButton,
    CdkVirtualScrollViewport,
    CdkFixedSizeVirtualScroll,
    PipeTimestamp,
    LayoutDrawerOppSimple,
    CompCode,
    CdkVirtualForOf,
    MatIcon,
    MatTooltip,
    SelectUser,
    CompCodeItemParent,
    MatAutocomplete,
    MatAutocompleteTrigger,
    MatOption,
    MatError,
    MatDatepickerInput,
    MatDatepicker,
    MatDatepickerToggle,
    MatDatepickerModule,
    MatTimepickerInput,
    MatTimepicker,
    MatTimepickerToggle,
    MatContextMenuTrigger,
    MatMenu,
    MatMenuContent,
    MatMenuItem,
    DateRange,
    MatIconButton,
    PipeStorageReason,
  ],
})
export class CompProcessDo implements OnInit, OnDestroy {
  @Input() editable = true;
  @Input() finishable = false;
  @Input() model: InventoryAddBottle = InventoryAddBottleDefault;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();

  private readonly dispose$ = new Subject<void>();

  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private actionBottle = inject(ActionBottle);
  private actionPrinter = inject(ActionPrinter);
  private actionInventoryAddBottle = inject(ActionInventoryAddBottle);
  private serviceShowMessage = inject(ServiceShowMessage);
  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);
  private readonly dialog = inject(MatDialog);

  protected updateVersionSig = signal<number>(0);
  protected savedVersion = 0;
  protected allMap = new Map<string, InventoryAddBottleDetail>();
  protected printedSig = computed(() => {
    if (this.updateVersionSig() < 0 || this.allMap.size < 1)
      return [] as InventoryAddBottleDetail[];
    return [...this.allMap.values()].filter((b) => b.status === 'coded');
  });
  protected usedSig = computed(() => {
    if (this.updateVersionSig() < 0 || this.allMap.size < 1)
      return [] as InventoryAddBottleDetail[];
    return [...this.allMap.values()].filter(
      (b) => b.status === 'process' || b.status === 'printed',
    );
  });
  protected usedStatSig = computed(() => {
    let plants = 0;
    let bottles = 0;
    if (this.updateVersionSig() < 0) return { bottles, plants };
    const list = this.usedSig();
    list.forEach((b) => {
      plants += b.plantCount ?? 0;
    });
    bottles = list.length;
    return { bottles, plants };
  });
  protected checkedSig = computed(() => {
    if (this.updateVersionSig() < 0 || this.allMap.size < 1)
      return [] as InventoryAddBottleDetail[];
    return [...this.allMap.values()].filter((b) => b.status === 'checked');
  });
  protected checkedStatSig = computed(() => {
    let plants = 0;
    let bottles = 0;
    if (this.updateVersionSig() < 0) return { bottles, plants };
    const list = this.checkedSig();
    list.forEach((b) => {
      plants += b.plantCount ?? 0;
    });
    bottles = list.length;
    return { bottles, plants };
  });
  private readonly parentMap = new Map<string, ModelBottleSimple>();
  protected hasParentSig = computed(() => this.updateVersionSig() >= 0 && this.parentMap.size > 0);
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

  // 补码form
  private fb = inject(FormBuilder);
  protected readonly formComplementBatch = this.fb.group({
    childBottleCount: 0,
    parentId: '',
    inspector: null as unknown as User,
    operator: null as unknown as User,
    plants: 0,
    operateDate: null as unknown as Date,
    operateTime: null as unknown as Date,
  });

  protected readonly customTimeOptions: MatTimepickerOption<Date>[] = [
    { label: '上午', value: new Date(0, 0, 0, 9, 0, 0) },
    { label: '下午', value: new Date(0, 0, 0, 15, 0, 0) },
    { label: '晚间', value: new Date(0, 0, 0, 20, 0, 0) },
  ];

  private doQuery() {
    this.onRequestStartLoading.emit('正在查询瓶码...');
    this.actionInventoryAddBottle
      .queryAllCodesInTask(this.model.id)
      .pipe(
        tap((bottles) => {
          bottles.forEach((bottle) => {
            this.allMap.set(bottle.id, bottle);
          });
        }),
        filter((_) => this.editable),
        switchMap((_) => {
          if (!this.editable) return of([]);
          return this.actionBottle.queryAllEnabledIdsForInventoryAddBottle(this.model.id);
        }),
        tap((bottles) => {
          const codePrefixForParent = new Set<string>();
          bottles.forEach((bottle) => {
            codePrefixForParent.add(bottle.id.substring(0, 6).toUpperCase());
            this.parentMap.set(bottle.id, bottle);
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
          this.savedVersion = this.updateVersionSig();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestSetParentBottle(bottle: ModelBottleSimple) {
    this.formComplementBatch.get('parentId')?.setValue(bottle.id);
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

  protected requestComplementBatch() {
    if (!this.formComplementBatch.valid) {
      this.formComplementBatch.markAllAsTouched();
      return;
    }
    const formValue = this.formComplementBatch.value;
    const operateDate = formValue.operateDate;
    const operateTime = formValue.operateTime;
    const parentId = formValue.parentId;
    let time = 0;
    if (operateDate && operateTime) {
      time = new Date(
        operateDate.getFullYear(),
        operateDate.getMonth(),
        operateDate.getDate(),
        operateTime.getHours(),
        operateTime.getMinutes(),
        operateTime.getSeconds(),
      ).getTime();
    }
    const printedList = this.printedSig();
    const count = Math.min(formValue.childBottleCount ?? 0, printedList.length);
    const bottles = printedList.slice(0, count);
    const updateStatus = (bottles: InventoryAddBottleDetail[]) => {
      for (const model of bottles) {
        model.status = 'process';
        model.idParent = formValue.parentId ?? '';
        model.plantCount = formValue.plants ?? 0;
        model.inspectorId = formValue.inspector?.id ?? '';
        model.inspectorName = formValue.inspector?.name ?? '';
        model.operatorId = formValue.operator?.id ?? '';
        model.operatorName = formValue.operator?.name ?? '';
        model.operatorTime = time;
      }
      this.formComplementBatch.get('parentId')?.setValue('');
      this.updateVersionSig.update((v) => v + 1);
    };
    if (this.model.generation < 1 || !!parentId) {
      updateStatus(bottles);
      return;
    } else {
      this.serviceShowConfirmDialog
        .show('尚未选择母瓶，是否继续执行补码？', '操作确认')
        .pipe(
          takeUntil(this.dispose$),
          filter((v) => v === 0),
        )
        .subscribe({
          next: (_) => {
            updateStatus(bottles);
          },
        });
    }
  }

  protected requestAssignCode() {
    if (!this.editable) return;
    this.onRequestStartLoading.emit('正在查询瓶码前缀...');
    // 首先执行保存
    this.actionInventoryAddBottle
      .process({
        taskId: this.model.id,
        details: Array.from(this.allMap.values()),
      })
      .pipe(
        // 查询或创建瓶码前缀
        switchMap((_) => {
          return this.actionInventoryAddBottle.queryOrCreateCodePrefix(this.model.id);
        }),
        tap((codePrefix) => {
          this.model.codePrefix = codePrefix;
          this.onSaved.emit();
          this.onRequestStopLoading.emit();
        }),
        // 打开对话框
        switchMap((codePrefix) => {
          const dialogInputData: DialogAssignCodeInputData = {
            codePrefix: codePrefix,
            bottleCountAssigned: this.allMap.size,
            childBottleCount: 0,
          };
          const dialogRef = this.dialog.open<
            DialogAssignCode,
            DialogAssignCodeInputData,
            DialogAssignCodeResponseData
          >(DialogAssignCode, {
            data: dialogInputData,
            width: '300px',
          });
          return dialogRef.afterClosed();
        }),
        filter((v) => !!v),
        tap((_) => {
          this.onRequestStartLoading.emit('正在分配瓶码...');
        }),
        // 分配瓶码
        switchMap((v) => {
          return this.actionInventoryAddBottle.printCode({
            taskId: this.model.id,
            codePrefix: v.codePrefix,
            bottleCount: v.childBottleCount,
          });
        }),
        // 查询瓶码
        switchMap((_) => this.actionInventoryAddBottle.queryAllCodesInTask(this.model.id)),
        tap((bottles) => {
          bottles.forEach((bottle) => {
            this.allMap.set(bottle.id, bottle);
          });
        }),
        // 更改版本
        tap((_) => this.updateVersionSig.update((v) => v + 1)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: () => {
          this.onSaved.emit();
          this.savedVersion = this.updateVersionSig();
          this.serviceShowMessage.showInfo(`分配成功。`);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestFinish() {
    if (!this.editable && !this.finishable) return;
    this.serviceShowConfirmDialog
      .show('确认已核实无误，完成此任务？', '确认操作')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在完成任务...')),
        switchMap((_) => {
          if (!this.editable) return of();
          return this.actionInventoryAddBottle.process({
            taskId: this.model.id,
            details: Array.from(this.allMap.values()),
          });
        }),
        tap((_) => {
          if (this.editable) this.savedVersion = this.updateVersionSig();
        }),
        switchMap((_) => this.actionInventoryAddBottle.done(this.model.id)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.onSaved.emit();
          this.onCancel.emit();
          this.onRequestStartLoading.emit('成功完成任务。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestSave() {
    this.onRequestStartLoading.emit('正在保存执行数据...');
    this.actionInventoryAddBottle
      .process({
        taskId: this.model.id,
        details: Array.from(this.allMap.values()),
      })
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowMessage.showInfo('保存成功。');
          this.onSaved.emit();
          this.savedVersion = this.updateVersionSig();
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestPrintSingle(item: InventoryAddBottleDetail) {
    if (!this.editable || item.status === 'coded') return;
    this.serviceShowConfirmDialog
      .show(
        '确认打印此瓶码？若之前曾打印，应以新打印的瓶码为准，将旧瓶码从瓶身揭下并丢弃。',
        '确认打印',
      )
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在打印...')),
        switchMap((_) =>
          this.actionPrinter.print({
            codePrint: {
              id: '',
              tableName: 'inventory_add_bottle',
              recordId: this.model.id,
              description: '单独打印库存增瓶',
              operatorId: '',
              operatorName: '',
              operateTime: 0,
            },
            codePrintDetails: [
              {
                idPrint: '',
                code: item.id,
                varietyCode: this.model.varietyCode,
                explantCollectionBatch: this.model.explantCollectionBatch,
                generation: this.model.generation,
                operatorName: item.operatorName,
                operateTime: item.operatorTime,
              },
            ],
          }),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          if (item.status === 'process') {
            item.status = 'printed';
            this.updateVersionSig.update((v) => v + 1);
          }
          this.serviceShowMessage.showInfo('已发送打印任务。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestUpdateStatusToCoded(item: InventoryAddBottleDetail) {
    if (!this.editable || item.status !== 'process') return;
    this.serviceShowConfirmDialog
      .show(
        '确认恢复为待用状态？恢复后，将重置该培养瓶的所有信息。若瓶身带有瓶码，应揭掉丢弃。',
        '确认恢复',
      )
      .pipe(
        filter((v) => v === 0),
        takeUntil(this.dispose$),
      )
      .subscribe({
        next: (_) => {
          item.status = 'coded';
          item.idParent = '';
          item.plantCount = 0;
          item.inspectorId = '';
          item.inspectorName = '';
          item.operatorId = '';
          item.operatorName = '';
          item.operatorTime = 0;
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestUpdateStatusToChecked(item: InventoryAddBottleDetail) {
    if (!this.editable || item.status !== 'printed') return;
    this.serviceShowConfirmDialog
      .show('确认该瓶已粘贴瓶码且已核对无误？', '确认核对无误')
      .pipe(
        filter((v) => v === 0),
        takeUntil(this.dispose$),
      )
      .subscribe({
        next: (_) => {
          item.status = 'checked';
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestUpdateStatusToProcess(item: InventoryAddBottleDetail) {
    if (!this.editable || item.status !== 'printed') return;
    this.serviceShowConfirmDialog
      .show('确认撤销该瓶的打印状态？应丢弃已打印的瓶码贴纸。', '确认撤销打印状态')
      .pipe(
        filter((v) => v === 0),
        takeUntil(this.dispose$),
      )
      .subscribe({
        next: (_) => {
          item.status = 'process';
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestUpdateStatusToPrinted(item: InventoryAddBottleDetail) {
    if (!this.editable || item.status !== 'checked') return;
    this.serviceShowConfirmDialog
      .show('确认撤销该瓶的核对状态？', '确认撤销核对状态')
      .pipe(
        filter((v) => v === 0),
        takeUntil(this.dispose$),
      )
      .subscribe({
        next: (_) => {
          item.status = 'printed';
          this.updateVersionSig.update((v) => v + 1);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestPrintBatch() {
    if (!this.editable) return;
    const list = this.usedSig().filter((b) => b.status === 'process');
    if (list.length === 0) {
      this.serviceShowMessage.showError('没有需要打印的瓶码');
      return;
    }

    this.serviceShowConfirmDialog
      .show(
        `确认批量打印${list.length}个瓶码？若曾打印过瓶码，应仔细核对，以新打印的为准，从瓶身揭下并丢弃原打印的瓶码。`,
        '确认批量打印',
      )
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在打印...')),
        switchMap((_) =>
          this.actionPrinter.print({
            codePrint: {
              id: '',
              tableName: 'inventory_add_bottle',
              recordId: this.model.id,
              description: '批量打印库存增瓶',
              operatorId: '',
              operatorName: '',
              operateTime: 0,
            },
            codePrintDetails: list.map((item) => ({
              idPrint: '',
              code: item.id,
              varietyCode: this.model.varietyCode,
              explantCollectionBatch: this.model.explantCollectionBatch,
              generation: this.model.generation,
              operatorName: item.operatorName,
              operateTime: item.operatorTime,
            })),
          }),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          list.forEach((item) => {
            item.status = 'printed';
          });
          this.updateVersionSig.update((v) => v + 1);
          this.serviceShowMessage.showInfo('已发送打印任务。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestScanCheck() {
    this.serviceGlobalCodeScanner
      .start('扫码核对')
      .pipe(takeUntil(this.dispose$))
      .subscribe((v) => {
        const item = this.allMap.get(v);
        if (!item || item.status !== 'printed') return;
        item.status = 'checked';
        this.updateVersionSig.update((v) => v + 1);
      });
  }

  private isDataSyncSavedConfirmed = false;
  protected requestCancel() {
    if (this.editable && this.updateVersionSig() !== this.savedVersion) {
      if (!this.isDataSyncSavedConfirmed) {
        this.serviceShowConfirmDialog
          .show('尚有执行信息未保存。应查看执行详情以确认是否需与服务器同步数据。', '数据需同步', [
            '确定',
          ])
          .pipe(takeUntil(this.dispose$))
          .subscribe({ complete: () => (this.isDataSyncSavedConfirmed = true) });
      } else {
        this.serviceShowConfirmDialog
          .show('尚有执行信息未保存。你确认要丢弃任务执行数据吗？', '再次确认放弃保存', [
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
                  }
                });
            }
          });
      }
    } else {
      this.onCancel.emit();
    }
  }

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

    this.formComplementBatch.get('inspector')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择质检员。',
        };
      }
      return null;
    });
    this.formComplementBatch.get('operator')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应选择操作员。',
        };
      }
      return null;
    });
    this.formComplementBatch.get('childBottleCount')?.addValidators((control) => {
      if (
        !control.value ||
        control.value > 999 ||
        !Number.isInteger(control.value) ||
        control.value > this.printedSig().length
      ) {
        return {
          error: `应大于0、小于${this.printedSig().length}。`,
        };
      }
      return null;
    });
    this.formComplementBatch.get('plants')?.addValidators((control) => {
      if (!control.value || control.value > 999 || !Number.isInteger(control.value)) {
        return {
          error: '应大于0、小于1,000。',
        };
      }
      return null;
    });
    this.formComplementBatch.get('operateDate')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应填写此项。',
        };
      }
      return null;
    });
    this.formComplementBatch.get('operateTime')?.addValidators((control) => {
      if (!control.value) {
        return {
          error: '应填写此项。',
        };
      }
      return null;
    });
    if (this.model.generation > 0) {
      this.formComplementBatch.get('parentId')?.addValidators((control) => {
        if (!!control.value && !this.parentMap.get(control.value)) {
          return {
            error: '母瓶不存在。',
          };
        }
        return null;
      });
    }
    this.doQuery();
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  // 右键上下文菜单选中的培养瓶
  protected bottleOppMenuSelectedSig = signal<InventoryAddBottleDetail | null>(null);

  protected onBottleOppMenuOpened(item: InventoryAddBottleDetail) {
    this.bottleOppMenuSelectedSig.set(item);
  }

  protected onBottleOppMenuClosed() {
    this.bottleOppMenuSelectedSig.set(null);
  }
}
