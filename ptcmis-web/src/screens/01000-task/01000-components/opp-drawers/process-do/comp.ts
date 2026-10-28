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
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatFormField, MatInput, MatLabel, MatSuffix } from '@angular/material/input';
import { MatTooltip } from '@angular/material/tooltip';
import { catchError, finalize, map, of, Subject, switchMap, tap } from 'rxjs';
import { LayoutDrawerOppSimple } from '../../../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { PipeTimestamp } from '../../../../../components/00200-pipes/pipe-timestamp';
import { SelectUser } from '../../../../../components/user/select/select-user';
import { TissueTaskBottle } from '../../../../../entities/models/TissueTaskBottle';
import { User } from '../../../../../entities/models/User';
import { ServiceShowMessage } from '../../../../../services/ui/service-show-error-message.service';
import { CompCodeItemSimple } from '../../code-item-simple/code';
import {
  DialogTaskAddOppGroupBottleChild,
  DialogTaskAddOppGroupBottleChildInputData,
  DialogTaskAddOppGroupBottleChildResponseData,
} from './dialogs/add-opp-group-bottle-child/dialog';
import {
  DialogTaskOppGroupAssignment,
  DialogTaskOppGroupAssignmentInputData,
  DialogTaskOppGroupAssignmentResponseData,
} from './dialogs/assignment/dialog';
import { MatDialog } from '@angular/material/dialog';
import { filter, takeUntil } from 'rxjs/operators';
import { MatDivider } from '@angular/material/list';
import { ServiceShowConfirmDialog } from '../../../../../services/ui/service-show-confirm-dialog';
import {
  DialogTaskOppGroupUpdate,
  DialogTaskOppGroupUpdateInputData,
  DialogTaskOppGroupUpdateResponseData,
} from './dialogs/update-info/dialog';
import { CompCodeItemWithOpp } from '../../code-item-with-opp/code';
import {
  MatContextMenuTrigger,
  MatMenu,
  MatMenuContent,
  MatMenuItem,
} from '@angular/material/menu';
import {
  DialogTaskBottlePlantUpdate,
  DialogTaskBottlePlantUpdateData,
} from './dialogs/update-plant/dialog';
import { DialogTaskBottleDiscard, DialogTaskBottleDiscardData } from './dialogs/discard/dialog';
import {
  DialogTaskPrintCodeForAddBottles,
  DialogTaskPrintCodeForAddBottlesInputData,
  DialogTaskPrintCodeForAddBottlesResponseData,
} from './dialogs/print-code-for-add-bottles/dialog';
import {
  DialogTaskSetProcessSettings,
  DialogTaskSetProcessSettingsInputData,
  DialogTaskSetProcessSettingsResponseData,
} from './dialogs/process-settings/dialog';
import {
  DialogTaskCreateStorageOut,
  DialogTaskCreateStorageOutInputData,
  DialogTaskCreateStorageOutResponseData,
} from '../dialogs/storage-out/dialog-create';
import { ServiceTissueTaskProcess } from '../process/service';
import { TissueTaskChartBarParentBottles } from './charts/chart-bar-parent-bottles';
import { TissueTaskChartBarParentBottlesKeep } from './charts/chart-bar-parent-bottles-keep';
import { TissueTaskChartBarParentPlants } from './charts/chart-bar-parent-plants';
import { TissueTaskChartBarParentPlantsKeep } from './charts/chart-bar-parent-plants-keep';
import { TissueTaskChartBarChildBottlesTask } from './charts/chart-bar-child-bottles-task';
import { TissueTaskChartBarChildPlantsTask } from './charts/chart-bar-child-plants-task';
import { TissueTaskChartBarChildBottlesOppGroup } from './charts/chart-bar-child-bottles-opp-group';
import { TissueTaskChartBarChildPlantsOppGroup } from './charts/chart-bar-child-plants-opp-group';
import { ServiceGlobalCodeScanner } from '../../../../../services/ui/service-global-code-scanner';
import { SelectVarietyCodeSimple } from '../../../../../components/variety-code/select/select-variety-code-simple';

@Component({
  standalone: true,
  selector: 'tissue-task-process-do',
  templateUrl: 'comp.html',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    MatIcon,
    MatTooltip,
    LayoutDrawerOppSimple,
    SelectUser,
    CdkFixedSizeVirtualScroll,
    CdkVirtualForOf,
    CdkVirtualScrollViewport,
    CompCodeItemSimple,
    PipeTimestamp,
    MatDivider,
    CompCodeItemWithOpp,
    MatContextMenuTrigger,
    MatMenu,
    MatMenuContent,
    MatMenuItem,
    MatIconButton,
    MatSuffix,
    TissueTaskChartBarParentBottles,
    TissueTaskChartBarParentBottlesKeep,
    TissueTaskChartBarParentPlants,
    TissueTaskChartBarParentPlantsKeep,
    TissueTaskChartBarChildBottlesTask,
    TissueTaskChartBarChildPlantsTask,
    TissueTaskChartBarChildBottlesOppGroup,
    TissueTaskChartBarChildPlantsOppGroup,
    SelectVarietyCodeSimple,
  ],
  styleUrls: ['comp.scss'],
})
export class TissueTaskProcessDo implements OnInit, OnDestroy {
  protected readonly serviceTissueTaskProcess = inject(ServiceTissueTaskProcess);

  @Input() editable = true;
  @Output() onSaved = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onCancel = new EventEmitter<void>();

  protected readonly editableSig = computed(() => {
    if (!this.editable) {
      return false;
    }
    return this.serviceTissueTaskProcess.taskSig().task.taskStatus !== 'finished';
  });
  protected readonly oppGroupSelectedSig = signal<string>('');

  protected readonly bottleParentUnAssignedStatSig = computed(() => {
    const list = this.serviceTissueTaskProcess.bottleParentUnAssignedSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleParentAssignedSig = computed(() => {
    const oppGroupId = this.oppGroupSelectedSig();
    const list = this.serviceTissueTaskProcess.bottleParentAssignedSig();
    if (oppGroupId === '') return list;
    return list.filter((b) => b.idOppGroup === oppGroupId);
  });
  protected readonly bottleParentAssignedStatSig = computed(() => {
    const list = this.bottleParentAssignedSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleChildAssignedSig = computed(() => {
    const oppGroupId = this.oppGroupSelectedSig();
    const list = this.serviceTissueTaskProcess.bottleChildAssignedSig();
    if (oppGroupId === '') return list;
    return list.filter((b) => b.idOppGroup === oppGroupId);
  });
  protected readonly bottleChildAssignedStatSig = computed(() => {
    const list = this.bottleChildAssignedSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleChildFinishedSig = computed(() => {
    const oppGroupId = this.oppGroupSelectedSig();
    const list = this.serviceTissueTaskProcess.bottleChildFinishedSig();
    if (oppGroupId === '') return list;
    return list.filter((b) => b.idOppGroup === oppGroupId);
  });
  protected readonly bottleChildFinishedStatSig = computed(() => {
    const list = this.bottleChildFinishedSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleChildDiscardSig = computed(() => {
    const oppGroupId = this.oppGroupSelectedSig();
    const list = this.serviceTissueTaskProcess.bottleChildDiscardSig();
    if (oppGroupId === '') return list;
    return list.filter((b) => b.idOppGroup === oppGroupId);
  });
  protected readonly bottleChildDiscardStatSig = computed(() => {
    const list = this.bottleChildDiscardSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });

  // 右键上下文菜单选中的培养瓶
  protected bottleOppMenuSelectedSig = signal<TissueTaskBottle | null>(null);

  private serviceShowMessage = inject(ServiceShowMessage);
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);
  private readonly dialog = inject(MatDialog);

  private readonly dispose$ = new Subject<void>();

  protected readonly inspectorDefaultFormControl = new FormControl<User | undefined | null>(null);
  protected readonly operatorDefaultFormControl = new FormControl<User | undefined | null>(null);
  protected readonly defaultPlantsPerChildBottleField = new FormControl<number | null>(null);
  protected readonly varietyCodeForInductionFormControl = new FormControl<string | null>(null);

  constructor() {}

  ngOnInit(): void {
    if (
      this.editableSig() &&
      this.serviceTissueTaskProcess.taskSig().task.taskType !== 'acclimatization'
    ) {
      this.defaultPlantsPerChildBottleField.addValidators((control) => {
        if (!control.value || control.value < 1 || !Number.isInteger(control.value)) {
          return {
            error: '应大于0。',
          };
        }
        return null;
      });
    }
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected requestSave() {
    if (!this.editableSig()) return;
    this.onRequestStartLoading.emit('正在保存...');
    this.serviceTissueTaskProcess
      .processInfoSave()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.onSaved.emit();
          this.serviceShowMessage.showInfo('保存成功。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestSetProcessSettings() {
    if (!this.editableSig()) return;
    const taskView = this.serviceTissueTaskProcess.taskSig();
    const processSettings = this.serviceTissueTaskProcess.processSettingsSig();
    // 打开对话框。
    const dialogInputData: DialogTaskSetProcessSettingsInputData = {
      idTask: taskView.taskId,
      showDefaultPlantsPerChildBottle: taskView.task.taskType !== 'acclimatization',
      defaultPlantsPerChildBottle: processSettings.defaultPlantsPerChildBottle,
      inspectors: processSettings.inspectors || [],
      operators: processSettings.operators || [],
    };
    const dialogRef = this.dialog.open<
      DialogTaskSetProcessSettings,
      DialogTaskSetProcessSettingsInputData,
      DialogTaskSetProcessSettingsResponseData
    >(DialogTaskSetProcessSettings, {
      data: dialogInputData,
      width: '600px',
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.dispose$))
      .subscribe((result) => {
        if (result !== undefined) {
          this.onRequestStartLoading.emit('正在保存任务分配默认参数...');
          this.serviceTissueTaskProcess
            .processSettingsSave(
              result.inspectors,
              result.operators,
              result.defaultPlantsPerChildBottle,
            )
            .pipe(
              takeUntil(this.dispose$),
              finalize(() => this.onRequestStopLoading.emit()),
            )
            .subscribe({
              next: () => {
                const currentValue = this.defaultPlantsPerChildBottleField.value;
                if (!currentValue) {
                  this.defaultPlantsPerChildBottleField.setValue(
                    result.defaultPlantsPerChildBottle,
                  );
                }
                this.onSaved.emit();
                this.serviceShowMessage.showInfo(`保存成功。`);
              },
              error: (error) => this.serviceShowMessage.showError(error),
            });
        }
      });
  }

  protected requestOppGroupAssignByScan() {
    if (!this.editableSig()) return;
    this.serviceGlobalCodeScanner
      .start('扫码母瓶分配操作组')
      .pipe(
        map((v) => this.serviceTissueTaskProcess.getBottle(v)),
        filter((bottle) => !!bottle),
        filter((bottle) => bottle.roleInTask === 'parent' && !bottle.idOppGroup),
        takeUntil(this.dispose$),
      )
      .subscribe((bottle) => {
        this.serviceGlobalCodeScanner.close();
        this.requestOppGroupAssign(bottle.idBottle, undefined, undefined);
      });
  }

  protected requestOppGroupAssign(
    idParentBottle: string | undefined,
    parentPlantCount: string | number | undefined | null,
    varietyCode: string | undefined,
  ) {
    if (!this.editableSig()) return;
    const taskView = this.serviceTissueTaskProcess.taskSig();
    let parentBottlePlantCount = 0;
    let varietyCodeInput = varietyCode;
    if (taskView.task.taskType === 'induction') {
      if (!varietyCode) {
        this.serviceShowMessage.showError('应选择品种编号。');
        return;
      }
      if (!parentPlantCount) {
        this.serviceShowMessage.showError('母代株数必须是大于 0 小于 1,000 的整数。');
        return;
      }
      parentBottlePlantCount = Number(parentPlantCount);
      if (
        !Number.isInteger(parentBottlePlantCount) ||
        parentBottlePlantCount < 1 ||
        parentBottlePlantCount > 999
      ) {
        this.serviceShowMessage.showError('母代株数必须是大于 0 小于 1,000 的整数。');
        return;
      }
    } else {
      if (!idParentBottle) {
        this.serviceShowMessage.showError('请点击母瓶。');
        return;
      }
      const parentBottle = this.serviceTissueTaskProcess.getBottle(idParentBottle);
      if (!parentBottle) {
        this.serviceShowMessage.showError('请点击母瓶。');
        return;
      }
      parentBottlePlantCount = parentBottle.plantCountActual;
      varietyCodeInput = parentBottle.varietyCode;
      if (parentBottlePlantCount < 1) {
        this.serviceShowMessage.showError(`母瓶 ${idParentBottle} 的株数错误。`);
        return;
      }
    }

    if (!varietyCodeInput) {
      this.serviceShowMessage.showError(`无法确定品种编号。`);
      return;
    }

    const dialogInputData: DialogTaskOppGroupAssignmentInputData = {
      idParentBottle: idParentBottle ?? '',
      parentPlantCount: parentBottlePlantCount,
      inspector: this.inspectorDefaultFormControl.value ?? null,
      inspectors: taskView.inspectors,
      operator: this.operatorDefaultFormControl.value ?? null,
      operators: taskView.operators,
      childBottleCount: 0,
      defaultPlantsPerChildBottle:
        this.defaultPlantsPerChildBottleField.value ||
        this.serviceTissueTaskProcess.processSettingsSig().defaultPlantsPerChildBottle ||
        taskView.task.defaultPlantsPerChildBottle,
      childBottles: this.serviceTissueTaskProcess.bottleChildUnAssignedSig(),
      showParentKeep: taskView.task.taskType !== 'induction',
      taskType: taskView.task.taskType,
      varietyCode: varietyCodeInput,
    };

    const dialogRef = this.dialog.open<
      DialogTaskOppGroupAssignment,
      DialogTaskOppGroupAssignmentInputData,
      DialogTaskOppGroupAssignmentResponseData
    >(DialogTaskOppGroupAssignment, {
      data: dialogInputData,
    });

    dialogRef
      .afterClosed()
      .pipe(
        filter((data) => !!data),
        switchMap((data) => this.serviceTissueTaskProcess.oppGroupAssign(data)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        // 设置选中操作组
        next: (result) => this.oppGroupSelectedSig.set(result),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildAssignCodeToTask() {
    if (!this.editableSig()) return;
    // 首先执行保存。
    this.onRequestStartLoading.emit('正在获取瓶码前缀...');
    of(this.serviceTissueTaskProcess.taskSig().task.codePrefix)
      .pipe(
        // 获取前缀。
        switchMap((codePrefix) =>
          !!codePrefix
            ? of(codePrefix)
            : this.serviceTissueTaskProcess.queryCodePrefix().pipe(takeUntil(this.dispose$)),
        ),
        // 打开对话框。
        switchMap((codePrefix) => {
          this.onRequestStopLoading.emit();
          const taskView = this.serviceTissueTaskProcess.taskSig();
          const dialogInputData: DialogTaskPrintCodeForAddBottlesInputData = {
            codePrefix: codePrefix,
            bottleCountAssigned: taskView.bottlesChildrenAll.length,
            bottleCountPlan: taskView.task.estimatedChildrenBottles ?? 0,
            childBottleCount: Math.max(
              0,
              Math.min(
                9999,
                (taskView.task.estimatedChildrenBottles ?? 0) -
                  (taskView.bottlesChildrenAll?.length ?? 0),
              ),
            ),
          };
          return this.dialog
            .open<
              DialogTaskPrintCodeForAddBottles,
              DialogTaskPrintCodeForAddBottlesInputData,
              DialogTaskPrintCodeForAddBottlesResponseData
            >(DialogTaskPrintCodeForAddBottles, {
              data: dialogInputData,
            })
            .afterClosed()
            .pipe(takeUntil(this.dispose$));
        }),
        filter((result) => !!result),
        tap((_) => this.onRequestStartLoading.emit('正在分配子瓶瓶码...')),
        switchMap((result) =>
          this.serviceTissueTaskProcess.taskAssignChildCode(result.childBottleCount),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (model) => {
          this.onSaved.emit();
          this.serviceShowMessage.showInfo(model);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestOppGroupToProcess(oppGroupId: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show('确认将该操作组从已完成状态切换为执行中状态吗？', '确认操作')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.oppGroupFromFinishToProcess(oppGroupId)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestOppGroupToFinish(oppGroupId: string) {
    if (!this.editableSig()) return;
    const taskView = this.serviceTissueTaskProcess.taskSig();
    const stat = this.serviceTissueTaskProcess.getOppGroupStat(oppGroupId);
    if (!stat) return;
    let message = '确认已接收并核对工作成果，将此操作组设置为已完成？';
    if (taskView.task.taskType !== 'acclimatization') {
      if (stat.childBottleCountProcessing) {
        message += `尚有${stat.childBottleCountProcessing}个瓶码未使用。`;
      }
    }
    if (stat.parentPlantCountAssigned) {
      message += `使用母代${stat.parentPlantCountAssigned}株。`;
    } else {
      this.serviceShowMessage.showError('该操作组没有使用母代，无法执行完成操作。');
      return;
    }
    if (taskView.task.taskType !== 'induction' && stat.parentKeepPlantCountAssigned) {
      message += `母代留种${stat.parentKeepPlantCountAssigned}株。`;
    }
    if (taskView.task.taskType !== 'acclimatization') {
      if (stat.childPlantCountFinished) {
        message += `完成子代${stat.childBottleCountFinished}瓶、${stat.childPlantCountFinished}株。`;
      }
      if (stat.childPlantCountDiscard) {
        message += `淘汰子代${stat.childBottleCountDiscard}瓶、${stat.childPlantCountDiscard}株。`;
      }
      if (!stat.childPlantCountFinished && !stat.childPlantCountDiscard) {
        this.serviceShowMessage.showError(
          '该操作组没有完成子代操作，也没有淘汰子代操作，无法执行完成操作。',
        );
        return;
      }
    }
    const uncheckedBottles = this.serviceTissueTaskProcess
      .getBottlesForOppGroup(oppGroupId, 'child')
      .filter(
        (bottle) =>
          bottle && bottle.operateStatus === 'finished' && bottle.childCodeStatus !== 'checked',
      );
    if (uncheckedBottles.length > 0) {
      this.serviceShowMessage.showError('在已完成的子瓶中，尚有未打印瓶码或未核对的子瓶。');
      return;
    }
    this.serviceShowConfirmDialog
      .show(message, '确认完成')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.oppGroupFromProcessToFinish(oppGroupId)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestOppGroupUpdate(oppGroupId: string) {
    if (!this.editableSig()) return;
    const oppGroup = this.serviceTissueTaskProcess.getOppGroup(oppGroupId);
    if (!oppGroup) return;
    if (!!oppGroup.finishTime) return;
    const taskView = this.serviceTissueTaskProcess.taskSig();

    this.serviceShowConfirmDialog
      .show('确认更改此操作组吗？', '确认更改')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => {
          const bottles = this.serviceTissueTaskProcess.getBottlesForOppGroup(oppGroupId, 'child');
          for (const bottle of bottles) {
            if (bottle.childCodeStatus === 'printed' || bottle.childCodeStatus === 'checked') {
              return this.serviceShowConfirmDialog.show(
                '该操作组有部分子瓶已打印瓶码，若更改此操作组的操作人或更改此操作组的品种编号、代际数、外植体采集批次，则必须将瓶码从瓶体揭下、作废，并重新打印张贴。是否更改此操作组？',
                '确认更改',
              );
            }
          }
          return of(0);
        }),
        filter((v) => v === 0),
        switchMap((_) => {
          const dialogInputData: DialogTaskOppGroupUpdateInputData = {
            taskType: taskView.task.taskType,
            variety: taskView.task.variety,
            varietyCode: oppGroup.varietyCode,
            idOppGroup: oppGroupId,
            idParentBottle: oppGroup.idParentBottle ?? '',
            parentPlantCount: oppGroup.parentPlantCount ?? 0,
            keepParentPlantCount: oppGroup.parentKeepPlantCount ?? 0,
            inspector: oppGroup.inspectorId,
            inspectors: taskView.inspectors,
            operator: oppGroup.operatorId,
            operators: taskView.operators,
            showParentKeep: taskView.task.taskType != 'induction',
            parentCodedBottles: this.serviceTissueTaskProcess
              .bottleParentUnAssignedSig()
              .map((b) => b.idBottle),
          };

          const dialogRef = this.dialog.open<
            DialogTaskOppGroupUpdate,
            DialogTaskOppGroupUpdateInputData,
            DialogTaskOppGroupUpdateResponseData
          >(DialogTaskOppGroupUpdate, {
            data: dialogInputData,
          });

          return dialogRef.afterClosed();
        }),
        filter((result) => !!result),
        switchMap((result) => this.serviceTissueTaskProcess.oppGroupUpdateInfo(result)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestOppGroupDelete(oppGroupId: string) {
    if (!this.editableSig()) return;
    const oppGroup = this.serviceTissueTaskProcess.getOppGroup(oppGroupId);
    if (!oppGroup) return;
    if (!!oppGroup.finishTime) return;
    this.serviceShowConfirmDialog
      .show(
        '确认删除此操作组吗？删除后，相关培养瓶回置为待用状态、且株数恢复为作业前株数。',
        '确认删除',
      )
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => {
          const bottles = this.serviceTissueTaskProcess.getBottlesForOppGroup(oppGroupId, 'child');
          for (const bottle of bottles) {
            if (bottle.childCodeStatus === 'printed' || bottle.childCodeStatus === 'checked') {
              return this.serviceShowConfirmDialog.show(
                '该操作组有部分子瓶已打印瓶码，删除该操作组后，必须将瓶码从瓶体揭下、作废。若要继续使用这些瓶码，应在分配作业后，重新打印。是否仍然删除该操作组？',
                '确认删除',
              );
            }
          }
          return of(0);
        }),
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.oppGroupDelete(oppGroupId)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        next: (idOppGroup) => {
          const idx = this.serviceTissueTaskProcess
            .oppGroupsSig()
            .findIndex((g) => g.id === idOppGroup);
          this.oppGroupSelectedSig.set(
            this.serviceTissueTaskProcess.oppGroupsSig()[idx - 1]?.id ?? '',
          );
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestOppGroupAddChildBottle(oppGroupId: string) {
    if (!this.editableSig()) return;
    const oppGroup = this.serviceTissueTaskProcess.getOppGroup(oppGroupId);
    if (!oppGroup) return;
    const taskView = this.serviceTissueTaskProcess.taskSig();
    const dialogInputData: DialogTaskAddOppGroupBottleChildInputData = {
      idOppGroup: oppGroupId,
      idParentBottle: oppGroup.idParentBottle ?? '',
      parentPlantCount: oppGroup.parentPlantCount,
      childBottleCount: 0,
      defaultPlantsPerChildBottle:
        this.defaultPlantsPerChildBottleField.value ||
        this.serviceTissueTaskProcess.processSettingsSig().defaultPlantsPerChildBottle ||
        taskView.task.defaultPlantsPerChildBottle,
      childBottles: this.serviceTissueTaskProcess.bottleChildUnAssignedSig().map((b) => b.idBottle),
    };

    const dialogRef = this.dialog.open<
      DialogTaskAddOppGroupBottleChild,
      DialogTaskAddOppGroupBottleChildInputData,
      DialogTaskAddOppGroupBottleChildResponseData
    >(DialogTaskAddOppGroupBottleChild, {
      data: dialogInputData,
    });

    dialogRef
      .afterClosed()
      .pipe(
        filter((result) => !!result),
        switchMap((result) => this.serviceTissueTaskProcess.oppGroupAddChildBottle(result)),
        takeUntil(this.dispose$),
      )
      .subscribe({ error: (error) => this.serviceShowMessage.showError(error) });
  }

  protected requestBottleChildToAssigned(idBottle: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(`确认将${idBottle}瓶码恢复为“操作中”状态？`, '操作确认')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildToAssigned(idBottle)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildToUnAssigned(idBottle: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(`确认取消分配${idBottle}，并将其置入待用子瓶？`, '操作确认')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildToUnAssigned(idBottle)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildToFinished(idBottle: string) {
    if (!this.editableSig()) return;
    this.serviceTissueTaskProcess
      .bottleChildToFinished(idBottle)
      .pipe(takeUntil(this.dispose$))
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildToFinishedBatch(oppGroupId: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show('确认完成全部子瓶？', '操作确认')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildToFinishedBatch(oppGroupId)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildToPrinted(idBottle: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(`确认打印${idBottle}瓶码？`, '操作确认')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在打印...')),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildToPrinted(idBottle)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => this.serviceShowMessage.showInfo('已发送打印任务。'),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildToPrintedBatch(oppGroupId: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(`确认打印所有尚未打印的瓶码？`, '操作确认')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在打印...')),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildToPrintedBatch(oppGroupId)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => this.serviceShowMessage.showInfo('已发送打印任务。'),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildRevertPrinted(idBottle: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(`确认已将${idBottle}瓶码从瓶体揭下并丢弃，恢复为未打印状态？`, '操作确认')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildRevertPrinted(idBottle)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildToChecked(idBottle: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(`确认${idBottle}瓶码已粘贴、并核对了株数，将此瓶置为已核对无误状态？`, '操作确认')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildToChecked(idBottle)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildToCheckedScan() {
    if (!this.editableSig()) return;
    this.serviceGlobalCodeScanner
      .start('扫码子瓶核对')
      .pipe(
        switchMap((v) =>
          this.serviceTissueTaskProcess.bottleChildToChecked(v).pipe(
            catchError((error) => {
              this.serviceShowMessage.showError(error);
              return of();
            }),
            takeUntil(this.dispose$),
          ),
        ),
        takeUntil(this.dispose$),
      )
      .subscribe();
  }

  protected requestBottleChildToCheckedBatch(oppGroupId: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(
        `确认所有已打印的瓶码均已粘贴、并核对了株数，将所有已打印的培养瓶都置为已核对无误状态？`,
        '操作确认',
      )
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildToCheckedBatch(oppGroupId)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildRevertChecked(idBottle: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(`确认取消${idBottle}的核对状态？`, '操作确认')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildRevertChecked(idBottle)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildToDiscard(idBottle: string) {
    if (!this.editableSig()) return;
    const bottle = this.serviceTissueTaskProcess.getBottle(idBottle);
    if (!bottle) return;
    if (bottle.roleInTask !== 'child' || !bottle.idOppGroup) {
      return;
    }

    const dialogInputData: DialogTaskBottleDiscardData = {
      title: '淘汰培养瓶',
      idBottle: bottle.idBottle,
      reason: '检测淘汰',
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
      .pipe(
        filter((result) => !!result),
        switchMap((result) =>
          this.serviceTissueTaskProcess.bottleChildToDiscard(result.idBottle, result.reason),
        ),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildUpdateDiscardReason(idBottle: string) {
    if (!this.editableSig()) return;
    const bottle = this.serviceTissueTaskProcess.getBottle(idBottle);
    if (!bottle) return;
    if (bottle.roleInTask !== 'child' || !bottle.idOppGroup) {
      return;
    }

    const dialogInputData: DialogTaskBottleDiscardData = {
      title: '更改淘汰原因',
      idBottle: bottle.idBottle,
      reason: bottle.discardReason || '检测淘汰',
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
      .pipe(
        filter((result) => !!result),
        switchMap((result) =>
          this.serviceTissueTaskProcess.bottleChildToDiscard(result.idBottle, result.reason),
        ),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildRevertToNoneForDiscard(idBottle: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(`确认已将${idBottle}瓶码从瓶体揭下并丢弃，恢复为未打印状态？`, '操作确认')
      .pipe(
        filter((v) => v === 0),
        switchMap((_) => this.serviceTissueTaskProcess.bottleChildRevertToNoneForDiscard(idBottle)),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildRevertToNoneForDiscardBatch(oppGroupId: string) {
    if (!this.editableSig()) return;
    this.serviceShowConfirmDialog
      .show(
        `确认已将所有淘汰培养瓶的瓶码从瓶体揭下并丢弃，恢复所有已淘汰培养瓶为未打印状态？`,
        '操作确认',
      )
      .pipe(
        filter((v) => v === 0),
        switchMap((_) =>
          this.serviceTissueTaskProcess.bottleChildRevertToNoneForDiscardBatch(oppGroupId),
        ),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestBottleChildUpdatePlant(idBottle: string) {
    if (!this.editableSig()) return;
    const bottle = this.serviceTissueTaskProcess.getBottle(idBottle);
    if (!bottle) return;
    if (
      bottle.roleInTask !== 'child' ||
      !bottle.idOppGroup ||
      (bottle.operateStatus !== 'finished' && bottle.operateStatus !== 'discard')
    ) {
      return;
    }

    const dialogInputData: DialogTaskBottlePlantUpdateData = {
      idBottle: bottle.idBottle,
      plantCount: bottle.plantCountActual,
    };

    const dialogRef = this.dialog.open<
      DialogTaskBottlePlantUpdate,
      DialogTaskBottlePlantUpdateData,
      DialogTaskBottlePlantUpdateData
    >(DialogTaskBottlePlantUpdate, {
      data: dialogInputData,
    });

    dialogRef
      .afterClosed()
      .pipe(
        filter((result) => !!result),
        switchMap((result) =>
          this.serviceTissueTaskProcess.bottleChildUpdatePlant(result.idBottle, result.plantCount),
        ),
        takeUntil(this.dispose$),
      )
      .subscribe({
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestTaskFinish() {
    if (!this.editableSig()) return;
    const taskView = this.serviceTissueTaskProcess.taskSig();
    if (taskView.task.taskStatus === 'finished') {
      this.serviceShowMessage.showError('无法对当前任务执行完成操作。');
      return;
    }
    const stat = this.serviceTissueTaskProcess.getOppGroupStat('');
    if (!stat) {
      this.serviceShowMessage.showError('无法对当前任务执行完成操作。');
      return;
    }
    let message = '确认完成此任务？';
    if (stat.parentPlantCountFinished) {
      if (taskView.task.taskType !== 'induction') {
        message += `计划使用母代${stat.parentBottleCountPlan}瓶、${stat.parentPlantCountPlan}株；`;
        message += `实际使用母代${stat.parentBottleCountFinished}瓶、${stat.parentPlantCountFinished}株。`;
        if (stat.parentBottleCountProcessing) {
          message += `尚有母代${stat.parentBottleCountProcessing}瓶、${stat.parentPlantCountProcessing}株未被使用。`;
        }
      } else {
        message += `计划使用母代${stat.parentPlantCountPlan}株；`;
        message += `实际使用母代${stat.parentPlantCountFinished}株。`;
      }
    } else {
      this.serviceShowMessage.showError('该任务没有使用母代，无法执行完成操作。');
      return;
    }
    if (
      taskView.task.taskType !== 'induction' &&
      (stat.parentKeepPlantCountFinished || stat.parentKeepPlantCountPlan)
    ) {
      message += `计划留种${stat.parentKeepBottleCountPlan}瓶、${stat.parentKeepPlantCountPlan}株；`;
      message += `实际留种${stat.parentKeepBottleCountFinished}瓶、${stat.parentKeepPlantCountFinished}株。`;
    }
    if (taskView.task.taskType !== 'acclimatization') {
      message += `计划生产子代${stat.childBottleCountPlan}瓶、${stat.childPlantCountPlan}株；`;
      if (stat.childPlantCountFinished) {
        message += `实际完成子代${stat.childBottleCountFinished}瓶、${stat.childPlantCountFinished}株。`;
      }
      if (stat.childPlantCountDiscard) {
        message += `淘汰子代${stat.childBottleCountDiscard}瓶、${stat.childPlantCountDiscard}株。`;
      }
      if (stat.childBottleCountProcessing) {
        message += `尚有${stat.childBottleCountProcessing}个瓶码未使用。`;
      }
      if (!stat.childPlantCountFinished && !stat.childPlantCountDiscard) {
        this.serviceShowMessage.showError(
          '该任务没有完成子代操作，也没有淘汰子代操作，无法执行完成操作。',
        );
        return;
      }
    }
    this.serviceShowConfirmDialog
      .show(message, '确认完成')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在完成任务...')),
        switchMap((_) => this.serviceTissueTaskProcess.taskFinish()),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.onSaved.emit();
          this.serviceShowMessage.showInfo('成功完成任务。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected requestUpdateTaskStatusFromFinishToProcess() {
    if (!this.editable) return;
    this.serviceShowConfirmDialog
      .show('确认将此任务恢复执行吗？', '确认恢复执行')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.onRequestStartLoading.emit('正在恢复执行...')),
        switchMap((_) => this.serviceTissueTaskProcess.taskFromFinishedToProcess()),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (_) => {
          this.onSaved.emit();
          this.onCancel.emit();
          this.serviceShowMessage.showInfo('成功恢复执行。');
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected onBottleOppMenuOpened(item: TissueTaskBottle) {
    this.bottleOppMenuSelectedSig.set(item);
  }

  protected onBottleOppMenuClosed() {
    this.bottleOppMenuSelectedSig.set(null);
  }
}
