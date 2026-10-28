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

import { computed, effect, inject, Injectable, OnDestroy, signal } from '@angular/core';
import { ActionTissueTask } from '../../../../../services/actions/action-tissue-task';
import {
  getTissueTaskViewDefault,
  TissueTaskView,
} from '../../../../../entities/api/TissueTaskView';
import { Observable, of, Subject, switchMap } from 'rxjs';
import { TissueTaskBottle } from '../../../../../entities/models/TissueTaskBottle';
import { TissueTaskOppGroup } from '../../../../../entities/models/TissueTaskOppGroup';
import {
  getDefaultStorageInOutForTask,
  StorageInOut,
} from '../../../../../entities/models/StorageInOut';
import { User } from '../../../../../entities/models/User';
import { map, takeUntil, tap } from 'rxjs/operators';
import { ActionPassport } from '../../../../../services/actions/action-passport';
import { ActionBottleCodeSequence } from '../../../../../services/actions/action-bottle-code-sequence';
import { TissueTaskUser } from '../../../../../entities/models/TissueTaskUser';
import { APIConst } from '../../../../../entities/api/const-api';
import { DialogTaskOppGroupAssignmentResponseData } from '../process-do/dialogs/assignment/dialog';
import { genId } from '../../../../../services/utils/00010-id';
import {
  ModelOppGroupEmployeeStat,
  ModelOppGroupEmployeeStatDefault,
  ModelOppGroupStat,
  ModelOppGroupStatDefault,
} from './model-opp-group-stat';
import { DialogTaskOppGroupUpdateResponseData } from '../process-do/dialogs/update-info/dialog';
import { DialogTaskAddOppGroupBottleChildResponseData } from '../process-do/dialogs/add-opp-group-bottle-child/dialog';
import { DialogTaskStorageOutRejStoreData } from '../dialogs/storage-out/dialog-rej-store';
import { ActionStorageInOut } from '../../../../../services/actions/action-storage-in-out';
import { ModelStorageOutTaskProcessDetail } from '../../../../../entities/api/ModelStorageOutTaskProcessDetail';
import { ActionPrinter } from '../../../../../services/actions/action-printer';

export interface ProcessSettings {
  defaultPlantsPerChildBottle: number;
  inspectors: User[];
  operators: User[];
}

@Injectable()
export class ServiceTissueTaskProcess implements OnDestroy {
  private actionTissueTask = inject(ActionTissueTask);
  private actionPassport = inject(ActionPassport);
  private actionStorageInOut = inject(ActionStorageInOut);
  private actionBottleCodeSequence = inject(ActionBottleCodeSequence);
  private actionPrinter = inject(ActionPrinter);

  init(tissueTaskView: TissueTaskView) {
    this.#organizeData(tissueTaskView, false);
  }

  // 任务
  #taskView: TissueTaskView = getTissueTaskViewDefault('');
  readonly #updateVersionOfTaskViewSig = signal<number>(0);
  readonly taskSig = computed(() => {
    if (this.#updateVersionOfTaskViewSig() < 0) return getTissueTaskViewDefault('');
    return this.#taskView;
  });

  // 任务执行信息保存同步状态
  readonly #updateVersionOfProcessInfoSig = signal<number>(0);
  readonly updateVersionOfProcessInfoSig = this.#updateVersionOfProcessInfoSig.asReadonly();
  private savedVersionOfProcessInfo = 0;
  public isProcessInfoSyncedWithServer() {
    return this.savedVersionOfProcessInfo >= this.#updateVersionOfProcessInfoSig();
  }

  // 培养瓶
  readonly #bottleAllMap = new Map<string, TissueTaskBottle>();
  getBottle(idBottle: string) {
    return this.#bottleAllMap.get(idBottle) ?? null;
  }
  getBottlesForOppGroup(idOppGroup: string, roleInTask = '') {
    return [...this.#bottleAllMap.values()]
      .filter((bottle) => {
        return (
          bottle &&
          bottle.idOppGroup === idOppGroup &&
          (!roleInTask || bottle.roleInTask === roleInTask)
        );
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  }
  readonly bottleParentUnAssignedSig = computed(() => {
    if (this.#updateVersionOfProcessInfoSig() < 0) return [] as TissueTaskBottle[];
    return [...this.#bottleAllMap.values()]
      .filter((bottle) => {
        return bottle && bottle.roleInTask === 'parent' && !bottle.idOppGroup;
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  });
  readonly bottleParentAssignedSig = computed(() => {
    if (this.#updateVersionOfProcessInfoSig() < 0) return [] as TissueTaskBottle[];
    return [...this.#bottleAllMap.values()]
      .filter((bottle) => {
        return bottle && bottle.roleInTask === 'parent' && !!bottle.idOppGroup;
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  });
  readonly bottleChildUnAssignedSig = computed(() => {
    if (this.#updateVersionOfProcessInfoSig() < 0) return [] as TissueTaskBottle[];
    return [...this.#bottleAllMap.values()]
      .filter((bottle) => {
        return bottle && bottle.roleInTask === 'child' && !bottle.idOppGroup;
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  });
  readonly bottleChildAssignedSig = computed(() => {
    if (this.#updateVersionOfProcessInfoSig() < 0) return [] as TissueTaskBottle[];
    return [...this.#bottleAllMap.values()]
      .filter((bottle) => {
        return (
          bottle &&
          bottle.roleInTask === 'child' &&
          !!bottle.idOppGroup &&
          bottle.operateStatus === 'assigned'
        );
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  });
  readonly bottleChildFinishedSig = computed(() => {
    if (this.#updateVersionOfProcessInfoSig() < 0) return [] as TissueTaskBottle[];
    return [...this.#bottleAllMap.values()]
      .filter((bottle) => {
        return (
          bottle &&
          bottle.roleInTask === 'child' &&
          !!bottle.idOppGroup &&
          bottle.operateStatus === 'finished'
        );
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  });
  readonly bottleChildDiscardSig = computed(() => {
    if (this.#updateVersionOfProcessInfoSig() < 0) return [] as TissueTaskBottle[];
    return [...this.#bottleAllMap.values()]
      .filter((bottle) => {
        return (
          bottle &&
          bottle.roleInTask === 'child' &&
          !!bottle.idOppGroup &&
          bottle.operateStatus === 'discard'
        );
      })
      .sort((a, b) => a.idBottle.localeCompare(b.idBottle));
  });

  // 操作组
  readonly #oppGroupMap = new Map<string, TissueTaskOppGroup>();
  getOppGroup(idOppGroup: string) {
    return this.#oppGroupMap.get(idOppGroup) ?? null;
  }
  readonly oppGroupsSig = computed(() => {
    if (this.#updateVersionOfProcessInfoSig() < 0) return [] as TissueTaskOppGroup[];
    return [...this.#oppGroupMap.values()].sort((a, b) => b.allocateTime - a.allocateTime);
  });

  // 操作组统计数据
  readonly oppGroupStatSig = computed(() => ['', ...this.oppGroupsSig().map((g) => g.id)]);
  readonly #oppGroupStatMap = new Map<string, ModelOppGroupStat>();
  getOppGroupStat(idOppGroup: string) {
    return this.#oppGroupStatMap.get(idOppGroup) ?? null;
  }
  readonly #oppGroupStatEffect = effect(() => {
    if (this.#updateVersionOfProcessInfoSig() < 0) return;

    this.#oppGroupStatMap.clear();

    // 任务整体统计
    const statTotal = { ...ModelOppGroupStatDefault };
    statTotal.idOppGroup = '';
    statTotal.nameOppGroup = '总览';
    statTotal.finishTime = this.#taskView.task.finishTime;
    statTotal.parentBottleCountPlan = this.#taskView.task.estimatedParentBottles;
    statTotal.parentPlantCountPlan = this.#taskView.task.estimatedParentPlants;
    statTotal.parentKeepBottleCountPlan = this.#taskView.task.estimatedParentKeepBottles;
    statTotal.parentKeepPlantCountPlan = this.#taskView.task.estimatedParentKeepPlants;
    statTotal.childBottleCountPlan = this.#taskView.task.estimatedChildrenBottles;
    statTotal.childPlantCountPlan = this.#taskView.task.estimatedChildrenPlants;
    statTotal.inspectors = [];
    statTotal.inspectorMap = new Map<string, ModelOppGroupEmployeeStat>();
    statTotal.operators = [];
    statTotal.operatorMap = new Map<string, ModelOppGroupEmployeeStat>();

    this.#oppGroupMap.forEach((oppGroup, oppGroupId) => {
      // 操作组统计
      let statOppGroup = this.#oppGroupStatMap.get(oppGroupId);
      if (!statOppGroup) {
        statOppGroup = { ...ModelOppGroupStatDefault };
        statOppGroup.idOppGroup = oppGroupId;
        statOppGroup.nameOppGroup = oppGroup.idParentBottle || oppGroup.parentPlantCount + '株';
        statOppGroup.finishTime = oppGroup.finishTime;
        statOppGroup.parentBottleCountPlan = this.#taskView.task.estimatedParentBottles;
        statOppGroup.parentPlantCountPlan = this.#taskView.task.estimatedParentPlants;
        statOppGroup.parentKeepBottleCountPlan = this.#taskView.task.estimatedParentKeepBottles;
        statOppGroup.parentKeepPlantCountPlan = this.#taskView.task.estimatedParentKeepPlants;
        statOppGroup.childBottleCountPlan = this.#taskView.task.estimatedChildrenBottles;
        statOppGroup.childPlantCountPlan = this.#taskView.task.estimatedChildrenPlants;
        statOppGroup.inspectors = [];
        statOppGroup.inspectorMap = new Map<string, ModelOppGroupEmployeeStat>();
        statOppGroup.operators = [];
        statOppGroup.operatorMap = new Map<string, ModelOppGroupEmployeeStat>();
        this.#oppGroupStatMap.set(oppGroupId, statOppGroup);
      }
      const inspectorId = oppGroup.inspectorId;
      const inspectorName = oppGroup.inspectorName;
      const operatorId = oppGroup.operatorId;
      const operatorName = oppGroup.operatorName;

      // 任务整体统计质检员和操作员
      let inspectorStatTotal = statTotal.inspectorMap.get(inspectorId);
      if (!inspectorStatTotal) {
        inspectorStatTotal = { ...ModelOppGroupEmployeeStatDefault };
        inspectorStatTotal.idUser = inspectorId;
        inspectorStatTotal.nameUser = inspectorName;
        statTotal.inspectorMap.set(inspectorId, inspectorStatTotal);
        statTotal.inspectors.push({ id: inspectorId, name: inspectorName });
      }
      let operatorStatTotal = statTotal.operatorMap.get(operatorId);
      if (!operatorStatTotal) {
        operatorStatTotal = { ...ModelOppGroupEmployeeStatDefault };
        operatorStatTotal.idUser = operatorId;
        operatorStatTotal.nameUser = operatorName;
        statTotal.operatorMap.set(operatorId, operatorStatTotal);
        statTotal.operators.push({ id: operatorId, name: operatorName });
      }
      // 操作组统计质检员和操作员
      let inspectorStatOppGroup = statOppGroup.inspectorMap.get(inspectorId);
      if (!inspectorStatOppGroup) {
        inspectorStatOppGroup = { ...ModelOppGroupEmployeeStatDefault };
        inspectorStatOppGroup.idUser = inspectorId;
        inspectorStatOppGroup.nameUser = inspectorName;
        statOppGroup.inspectorMap.set(inspectorId, inspectorStatOppGroup);
        statOppGroup.inspectors.push({ id: inspectorId, name: inspectorName });
      }
      let operatorStatOppGroup = statOppGroup.operatorMap.get(operatorId);
      if (!operatorStatOppGroup) {
        operatorStatOppGroup = { ...ModelOppGroupEmployeeStatDefault };
        operatorStatOppGroup.idUser = operatorId;
        operatorStatOppGroup.nameUser = operatorName;
        statOppGroup.operatorMap.set(operatorId, operatorStatOppGroup);
        statOppGroup.operators.push({ id: operatorId, name: operatorName });
      }

      statTotal.parentBottleCountAssigned++;
      statTotal.parentPlantCountAssigned += oppGroup.parentPlantCount;
      statOppGroup.parentBottleCountAssigned++;
      statOppGroup.parentPlantCountAssigned += oppGroup.parentPlantCount;
      inspectorStatTotal.parentBottleCountAssigned++;
      inspectorStatTotal.parentPlantCountAssigned += oppGroup.parentPlantCount;
      operatorStatTotal.parentBottleCountAssigned++;
      operatorStatTotal.parentPlantCountAssigned += oppGroup.parentPlantCount;
      inspectorStatOppGroup.parentBottleCountAssigned++;
      inspectorStatOppGroup.parentPlantCountAssigned += oppGroup.parentPlantCount;
      operatorStatOppGroup.parentBottleCountAssigned++;
      operatorStatOppGroup.parentPlantCountAssigned += oppGroup.parentPlantCount;
      if (oppGroup.parentKeep === APIConst.booleans.BOOLEAN_TRUE) {
        statTotal.parentKeepBottleCountAssigned++;
        statTotal.parentKeepPlantCountAssigned += oppGroup.parentKeepPlantCount;
        statOppGroup.parentKeepBottleCountAssigned++;
        statOppGroup.parentKeepPlantCountAssigned += oppGroup.parentKeepPlantCount;
        inspectorStatTotal.parentKeepBottleCountAssigned++;
        inspectorStatTotal.parentKeepPlantCountAssigned += oppGroup.parentKeepPlantCount;
        operatorStatTotal.parentKeepBottleCountAssigned++;
        operatorStatTotal.parentKeepPlantCountAssigned += oppGroup.parentKeepPlantCount;
        inspectorStatOppGroup.parentKeepBottleCountAssigned++;
        inspectorStatOppGroup.parentKeepPlantCountAssigned += oppGroup.parentKeepPlantCount;
        operatorStatOppGroup.parentKeepBottleCountAssigned++;
        operatorStatOppGroup.parentKeepPlantCountAssigned += oppGroup.parentKeepPlantCount;
      }
      if (!!oppGroup.finishTime) {
        statTotal.parentBottleCountFinished++;
        statOppGroup.parentBottleCountFinished++;
        inspectorStatTotal.parentBottleCountFinished++;
        inspectorStatOppGroup.parentBottleCountFinished++;
        operatorStatTotal.parentBottleCountFinished++;
        operatorStatOppGroup.parentBottleCountFinished++;
        statTotal.parentPlantCountFinished += oppGroup.parentPlantCount;
        statOppGroup.parentPlantCountFinished += oppGroup.parentPlantCount;
        inspectorStatTotal.parentPlantCountFinished += oppGroup.parentPlantCount;
        inspectorStatOppGroup.parentPlantCountFinished += oppGroup.parentPlantCount;
        operatorStatTotal.parentPlantCountFinished += oppGroup.parentPlantCount;
        operatorStatOppGroup.parentPlantCountFinished += oppGroup.parentPlantCount;
        if (oppGroup.parentKeep === APIConst.booleans.BOOLEAN_TRUE) {
          statTotal.parentKeepBottleCountFinished++;
          statOppGroup.parentKeepBottleCountFinished++;
          inspectorStatTotal.parentKeepBottleCountFinished++;
          inspectorStatOppGroup.parentKeepBottleCountFinished++;
          operatorStatTotal.parentKeepBottleCountFinished++;
          operatorStatOppGroup.parentKeepBottleCountFinished++;
          statTotal.parentKeepPlantCountFinished += oppGroup.parentKeepPlantCount;
          statOppGroup.parentKeepPlantCountFinished += oppGroup.parentKeepPlantCount;
          inspectorStatTotal.parentKeepPlantCountFinished += oppGroup.parentKeepPlantCount;
          inspectorStatOppGroup.parentKeepPlantCountFinished += oppGroup.parentKeepPlantCount;
          operatorStatTotal.parentKeepPlantCountFinished += oppGroup.parentKeepPlantCount;
          operatorStatOppGroup.parentKeepPlantCountFinished += oppGroup.parentKeepPlantCount;
        }
      } else {
        statTotal.parentBottleCountProcessing++;
        statOppGroup.parentBottleCountProcessing++;
        inspectorStatTotal.parentBottleCountProcessing++;
        inspectorStatOppGroup.parentBottleCountProcessing++;
        operatorStatTotal.parentBottleCountProcessing++;
        operatorStatOppGroup.parentBottleCountProcessing++;
        statTotal.parentPlantCountProcessing += oppGroup.parentPlantCount;
        statOppGroup.parentPlantCountProcessing += oppGroup.parentPlantCount;
        inspectorStatTotal.parentPlantCountProcessing += oppGroup.parentPlantCount;
        inspectorStatOppGroup.parentPlantCountProcessing += oppGroup.parentPlantCount;
        operatorStatTotal.parentPlantCountProcessing += oppGroup.parentPlantCount;
        operatorStatOppGroup.parentPlantCountProcessing += oppGroup.parentPlantCount;
        if (oppGroup.parentKeep === APIConst.booleans.BOOLEAN_TRUE) {
          statTotal.parentKeepBottleCountProcessing++;
          statOppGroup.parentKeepBottleCountProcessing++;
          inspectorStatTotal.parentKeepBottleCountProcessing++;
          inspectorStatOppGroup.parentKeepBottleCountProcessing++;
          operatorStatTotal.parentKeepBottleCountProcessing++;
          operatorStatOppGroup.parentKeepBottleCountProcessing++;
          statTotal.parentKeepPlantCountProcessing += oppGroup.parentKeepPlantCount;
          statOppGroup.parentKeepPlantCountProcessing += oppGroup.parentKeepPlantCount;
          inspectorStatTotal.parentKeepPlantCountProcessing += oppGroup.parentKeepPlantCount;
          inspectorStatOppGroup.parentKeepPlantCountProcessing += oppGroup.parentKeepPlantCount;
          operatorStatTotal.parentKeepPlantCountProcessing += oppGroup.parentKeepPlantCount;
          operatorStatOppGroup.parentKeepPlantCountProcessing += oppGroup.parentKeepPlantCount;
        }
      }
    });

    this.#bottleAllMap.forEach((bottle) => {
      const oppGroupId = bottle.idOppGroup;
      const roleInTask = bottle.roleInTask;
      if (!oppGroupId || !roleInTask) return;
      const oppGroup = this.#oppGroupMap.get(oppGroupId);
      if (!oppGroup) return;
      const inspectorId = bottle.inspectorId;
      const operatorId = bottle.operatorId;
      if (!inspectorId || !operatorId) return;

      let inspectorStatTotal = statTotal.inspectorMap.get(inspectorId);
      if (!inspectorStatTotal) return;
      let operatorStatTotal = statTotal.operatorMap.get(operatorId);
      if (!operatorStatTotal) return;

      let statOppGroup = this.#oppGroupStatMap.get(oppGroupId);
      if (!statOppGroup) return;
      let inspectorStatOppGroup = statOppGroup.inspectorMap.get(inspectorId);
      if (!inspectorStatOppGroup) return;
      let operatorStatOppGroup = statOppGroup.operatorMap.get(operatorId);
      if (!operatorStatOppGroup) return;

      if (roleInTask === 'child') {
        statTotal.childBottleCountAssigned++;
        statTotal.childPlantCountAssigned += bottle.plantCountActual;
        statOppGroup.childBottleCountAssigned++;
        statOppGroup.childPlantCountAssigned += bottle.plantCountActual;
        inspectorStatTotal.childBottleCountAssigned++;
        inspectorStatTotal.childPlantCountAssigned += bottle.plantCountActual;
        operatorStatTotal.childBottleCountAssigned++;
        operatorStatTotal.childPlantCountAssigned += bottle.plantCountActual;
        inspectorStatOppGroup.childBottleCountAssigned++;
        inspectorStatOppGroup.childPlantCountAssigned += bottle.plantCountActual;
        operatorStatOppGroup.childBottleCountAssigned++;
        operatorStatOppGroup.childPlantCountAssigned += bottle.plantCountActual;
        if (bottle.operateStatus === 'finished') {
          statTotal.childBottleCountFinished++;
          statTotal.childPlantCountFinished += bottle.plantCountActual;
          statOppGroup.childBottleCountFinished++;
          statOppGroup.childPlantCountFinished += bottle.plantCountActual;
          inspectorStatTotal.childBottleCountFinished++;
          inspectorStatTotal.childPlantCountFinished += bottle.plantCountActual;
          operatorStatTotal.childBottleCountFinished++;
          operatorStatTotal.childPlantCountFinished += bottle.plantCountActual;
          inspectorStatOppGroup.childBottleCountFinished++;
          inspectorStatOppGroup.childPlantCountFinished += bottle.plantCountActual;
          operatorStatOppGroup.childBottleCountFinished++;
          operatorStatOppGroup.childPlantCountFinished += bottle.plantCountActual;
        } else if (bottle.operateStatus === 'assigned') {
          statTotal.childBottleCountProcessing++;
          statTotal.childPlantCountProcessing += bottle.plantCountActual;
          statOppGroup.childBottleCountProcessing++;
          statOppGroup.childPlantCountProcessing += bottle.plantCountActual;
          inspectorStatTotal.childBottleCountProcessing++;
          inspectorStatTotal.childPlantCountProcessing += bottle.plantCountActual;
          operatorStatTotal.childBottleCountProcessing++;
          operatorStatTotal.childPlantCountProcessing += bottle.plantCountActual;
          inspectorStatOppGroup.childBottleCountProcessing++;
          inspectorStatOppGroup.childPlantCountProcessing += bottle.plantCountActual;
          operatorStatOppGroup.childBottleCountProcessing++;
          operatorStatOppGroup.childPlantCountProcessing += bottle.plantCountActual;
        } else if (bottle.operateStatus === 'discard') {
          statTotal.childBottleCountDiscard++;
          statTotal.childPlantCountDiscard += bottle.plantCountActual;
          statOppGroup.childBottleCountDiscard++;
          statOppGroup.childPlantCountDiscard += bottle.plantCountActual;
          inspectorStatTotal.childBottleCountDiscard++;
          inspectorStatTotal.childPlantCountDiscard += bottle.plantCountActual;
          inspectorStatOppGroup.childBottleCountDiscard++;
          inspectorStatOppGroup.childPlantCountDiscard += bottle.plantCountActual;
          operatorStatTotal.childBottleCountDiscard++;
          operatorStatTotal.childPlantCountDiscard += bottle.plantCountActual;
          operatorStatOppGroup.childBottleCountDiscard++;
          operatorStatOppGroup.childPlantCountDiscard += bottle.plantCountActual;
        }
      }
    });

    this.#oppGroupStatMap.set('', statTotal);
  });

  // 任务分配默认配置
  #processSettings: ProcessSettings = {
    defaultPlantsPerChildBottle: 0,
    inspectors: [],
    operators: [],
  };
  readonly #updateVersionOfProcessSettingSig = signal<number>(0);
  processSettingsSig = computed(() => {
    if (this.#updateVersionOfProcessSettingSig() < 0)
      return {
        defaultPlantsPerChildBottle: 0,
        inspectors: [],
        operators: [],
      };
    return this.#processSettings;
  });

  // 出库单
  readonly #storageOutMap = new Map<string, StorageInOut>();
  getStorageOut(idStorageOut: string) {
    return this.#storageOutMap.get(idStorageOut) ?? null;
  }
  readonly #storageOutSelectedIndex$ = new Subject<string>();
  readonly storageOutSelectedIndex$ = this.#storageOutSelectedIndex$.asObservable();
  readonly #updateVersionOfStorageOutSig = signal<number>(0);
  storageOutsSig = computed(() => {
    if (this.#updateVersionOfStorageOutSig() < 0) return [] as StorageInOut[];
    return [...this.#storageOutMap.values()].sort((a, b) => b.applicantTime - a.applicantTime);
  });
  storageOutStatSig = computed(() => {
    const stat = {
      planBottles: this.#taskView.task.estimatedParentBottles,
      planPlants: this.#taskView.task.estimatedParentPlants,
      applyBottles: 0,
      applyPlants: 0,
      unProcessBottles: 0,
      unProcessPlants: 0,
      processBottles: 0,
      processPlants: 0,
      rejStoreBottles: 0,
      rejStorePlants: 0,
      rejBizBottles: 0,
      rejBizPlants: 0,
      doneBottles: 0,
      donePlants: 0,
      outBottles: 0,
      outPlants: 0,
    };
    if (this.#updateVersionOfStorageOutSig() < 0) return stat;
    this.#storageOutMap.forEach((out) => {
      if (out.status === 'canceled') return;
      stat.applyBottles -= out.bottlesApplied;
      stat.applyPlants -= out.plantsApplied;
      if (out.status === 'done' || out.status === 'finished') {
        stat.doneBottles -= out.bottlesActual;
        stat.donePlants -= out.plantsActual;
      }
      if (out.status === 'finished') {
        stat.outBottles -= out.bottlesActual;
        stat.outPlants -= out.plantsActual;
      }
      if (out.status === 'started') {
        stat.processBottles -= out.bottlesApplied;
        stat.processPlants -= out.plantsApplied;
      } else if (out.status === 'rej_biz') {
        stat.rejBizBottles -= out.bottlesApplied;
        stat.rejBizPlants -= out.plantsApplied;
      } else if (out.status === 'rej_store') {
        stat.rejStoreBottles -= out.bottlesApplied;
        stat.rejStorePlants -= out.plantsApplied;
      } else if (out.status === 'apply') {
        stat.unProcessBottles -= out.bottlesApplied;
        stat.unProcessPlants -= out.plantsApplied;
      }
    });
    return stat;
  });

  // 入库单
  #storageIn: StorageInOut | null = null;
  readonly #updateVersionOfStorageInSig = signal<number>(0);
  readonly updateVersionOfStorageInSig = this.#updateVersionOfStorageInSig.asReadonly();
  readonly storageInSig = computed(() => {
    if (this.#updateVersionOfStorageInSig() < 0) return null;
    return this.#storageIn;
  });

  // 组织数据
  #organizeData(tissueTaskView: TissueTaskView, updateVersion = true) {
    // 任务执行信息
    this.#organizeProcessInfo(tissueTaskView, updateVersion);
    // 任务默认设置
    this.#organizeProcessSettings(tissueTaskView, updateVersion);
    // 出库单
    this.#organizeStorageOut(tissueTaskView, updateVersion);
    // 入库单
    this.#organizeStorageIn(tissueTaskView, updateVersion);
    // 任务
    this.#organizeTaskView(tissueTaskView, updateVersion);
  }
  // 组织任务视图信息
  #organizeTaskView(tissueTaskView: TissueTaskView, updateVersion = true) {
    this.#taskView = tissueTaskView;
    if (updateVersion) this.#updateVersionOfTaskViewSig.update((v) => v + 1);
  }
  // 组织任务执行信息
  #organizeProcessInfo(tissueTaskView: TissueTaskView, updateVersion = true) {
    // 培养瓶
    (tissueTaskView.bottlesParentAll ?? []).forEach((b) => {
      const bottle = { ...b };
      this.#bottleAllMap.set(bottle.idBottle, bottle);
    });
    (tissueTaskView.bottlesChildrenAll ?? []).forEach((b) => {
      const bottle = { ...b };
      this.#bottleAllMap.set(bottle.idBottle, bottle);
    });
    // 操作组
    (tissueTaskView.oppGroup ?? []).forEach((g) => {
      const oppGroup = { ...g };
      this.#oppGroupMap.set(oppGroup.id, oppGroup);
    });
    if (updateVersion) this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
  }
  // 组织任务默认设置
  #organizeProcessSettings(tissueTaskView: TissueTaskView, updateVersion = true) {
    this.#processSettings = {
      defaultPlantsPerChildBottle: tissueTaskView.task.defaultPlantsPerChildBottle,
      inspectors: tissueTaskView.inspectors,
      operators: tissueTaskView.operators,
    };
    if (updateVersion) this.#updateVersionOfProcessSettingSig.update((v) => v + 1);
  }
  // 组织出库单
  #organizeStorageOut(tissueTaskView: TissueTaskView, updateVersion = true) {
    this.#storageOutMap.clear();
    (tissueTaskView.storageOutList ?? []).forEach((g) => {
      const storageOut = { ...g };
      this.#storageOutMap.set(storageOut.id, storageOut);
    });
    if (updateVersion) this.#updateVersionOfStorageOutSig.update((v) => v + 1);
  }
  // 组织入库单
  #organizeStorageIn(tissueTaskView: TissueTaskView, updateVersion = true) {
    this.#storageIn =
      tissueTaskView.storageInList?.[0] ?? getDefaultStorageInOutForTask(tissueTaskView, 'in');
    const now = Date.now();
    if (!tissueTaskView.storageInList || tissueTaskView.storageInList.length === 0) {
      this.#storageIn.reason = tissueTaskView.task.taskType;
      this.#storageIn.status = '';
      this.#storageIn.applicantId = this.actionPassport.currentUserId;
      this.#storageIn.applicantName = this.actionPassport.currentNameOfUser;
      this.#storageIn.applicantTime = now;
      this.#storageIn.operatorId = this.actionPassport.currentUserId;
      this.#storageIn.operatorName = this.actionPassport.currentNameOfUser;
      this.#storageIn.operatorTime = now;
    }
    if (updateVersion) this.#updateVersionOfStorageInSig.update((v) => v + 1);
  }

  private dispose$ = new Subject<void>();
  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.#storageOutSelectedIndex$.complete();
    this.#oppGroupStatEffect.destroy();
  }

  queryCodePrefix(): Observable<string> {
    return this.actionTissueTask.queryOrCreateCodePrefix(this.#taskView.taskId);
  }

  refresh(): Observable<void> {
    if (
      this.taskSig().task.taskStatus === 'created' ||
      this.taskSig().task.taskStatus === 'coded' ||
      this.taskSig().task.taskStatus === 'processing'
    ) {
      return this.processInfoSave().pipe(
        map((_) => undefined),
        takeUntil(this.dispose$),
      );
    } else {
      return this.actionTissueTask.queryByTaskId(this.taskSig().taskId).pipe(
        tap((taskView) => this.#organizeData(taskView)),
        map((_) => undefined),
        takeUntil(this.dispose$),
      );
    }
  }

  taskAssignChildCode(bottleChildCount: number): Observable<string> {
    if (bottleChildCount < 1 || bottleChildCount > 9999 || !Number.isInteger(bottleChildCount)) {
      throw `补打瓶码数量应大于 0 且不大于 10,000。`;
    }
    // 首先执行保存
    return this.processInfoSave().pipe(
      // 分配瓶码
      switchMap((taskView) =>
        this.actionTissueTask
          .printBottleCodeForAddBottles({
            idTask: taskView.taskId,
            bottleCount: bottleChildCount,
          })
          .pipe(takeUntil(this.dispose$)),
      ),
      // 更新数据
      tap((model) => {
        this.#organizeData(model.taskView);
        this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig();
      }),
      map((model) => `已分配瓶码: 从 ${model.codeStart} 至 ${model.codeEnd}`),
      takeUntil(this.dispose$),
    );
  }

  taskFinish(): Observable<void> {
    return this.processInfoSave().pipe(
      switchMap((taskView) => this.actionTissueTask.updateTaskStatusToFinish(taskView.taskId)),
      tap((taskView) => {
        this.#organizeData(taskView);
        this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig();
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }

  taskFromFinishedToProcess(): Observable<void> {
    return this.actionTissueTask.updateTaskStatusFromFinishToProcess(this.#taskView.taskId).pipe(
      tap((taskView) => {
        this.#organizeData(taskView);
        this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig();
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }

  processInfoSave(): Observable<TissueTaskView> {
    return this.actionTissueTask
      .saveProcess({
        task: this.#taskView.task,
        oppGroup: [...this.#oppGroupMap.values()],
        bottles: [...this.#bottleAllMap.values()],
      })
      .pipe(
        // 更新数据
        tap((taskView) => {
          this.#organizeData(taskView);
          this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig();
        }),
        takeUntil(this.dispose$),
      );
  }

  processSettingsSave(
    inspectors: User[],
    operators: User[],
    defaultPlantsPerChildBottle: number,
  ): Observable<void> {
    const taskId = this.#taskView.taskId;
    const ins: TissueTaskUser[] = inspectors.map((u) => ({
      idTask: taskId,
      idUser: u.id,
      role: 'inspector',
    }));
    const ops: TissueTaskUser[] = operators.map((u) => ({
      idTask: taskId,
      idUser: u.id,
      role: 'operator',
    }));
    return this.actionTissueTask
      .saveProcessSettings({
        taskId: taskId,
        users: [...ins, ...ops],
        defaultPlantsPerParentBottle: 0,
        defaultPlantsPerChildBottle: defaultPlantsPerChildBottle,
      })
      .pipe(
        tap((taskView) => this.#organizeProcessSettings(taskView)),
        map((_) => undefined),
        takeUntil(this.dispose$),
      );
  }

  oppGroupAssign(data: DialogTaskOppGroupAssignmentResponseData): Observable<string> {
    if (!data) throw '错误操作。';
    if (!data.inspector || !data.operator) {
      throw '应选择质检员和操作员。';
    }
    if (
      !Number.isInteger(data.parentPlantCount) ||
      data.parentPlantCount < 1 ||
      data.parentPlantCount > 999
    ) {
      throw '母代株数应是大于 0 小于 1,000 的整数。';
    }
    const keepParentPlantCount = data.keepParentPlantCount;
    if (
      keepParentPlantCount < 0 ||
      keepParentPlantCount > 999 ||
      !Number.isInteger(keepParentPlantCount)
    ) {
      throw '留种株数应是不小于 0 ，且小于 1,000 的整数。';
    }

    const bottleChildUnAssigned = this.bottleChildUnAssignedSig();

    // 校验母瓶信息
    const bottleParent = this.getBottle(data.idParentBottle);
    if (this.#taskView.task.taskType !== 'induction') {
      if (!data.idParentBottle) {
        throw `请点击母瓶。`;
      }
      if (!bottleParent) {
        throw `母瓶 ${data.idParentBottle} 不在此任务中。`;
      }
      if (bottleParent.roleInTask !== 'parent') {
        throw `子瓶 ${data.idParentBottle} 不能作为母瓶使用。`;
      }
      if (!!bottleParent.idOppGroup) {
        throw `母瓶 ${data.idParentBottle} 已被分配作业。`;
      }
    }

    // 校验子瓶信息
    const bottleChildren: TissueTaskBottle[] = [];
    if (this.#taskView.task.taskType !== 'acclimatization') {
      if (!Number.isInteger(data.childBottleCount) || data.childBottleCount < 1) {
        throw `子瓶数量应是大于 0 的整数。`;
      }
      const childCodedBottleCount = bottleChildUnAssigned.length;
      if (data.childBottleCount > childCodedBottleCount) {
        throw `子瓶瓶码现有 ${childCodedBottleCount} 个，小于本次应分配的 ${data.childBottleCount} 个。应执行“分配子瓶瓶码”操作补足瓶码。`;
      }
      for (let index = 0; index < data.childBottleCount; index++) {
        const bottle = bottleChildUnAssigned[index];
        if (!bottle) {
          throw `子瓶已不足。`;
        }
        if (bottle.roleInTask !== 'child') {
          throw `母瓶 ${bottle.idBottle} 不能作为子瓶使用。`;
        }
        if (!!bottle.idOppGroup) {
          throw `子瓶 ${bottle.idBottle} 已被分配。`;
        }
        bottleChildren.push(bottle);
      }
    }

    const now = Date.now();

    // 生成操作组ID
    const oppGroupId = genId();

    // 更新母瓶信息
    if (bottleParent) {
      bottleParent.idOppGroup = oppGroupId;
      bottleParent.allocateTime = now;
      bottleParent.operatorId = data.operator.id;
      bottleParent.operatorName = data.operator.name;
      bottleParent.inspectorId = data.inspector.id;
      bottleParent.inspectorName = data.inspector.name;
      bottleParent.operateStatus = 'assigned';
      bottleParent.discardReason = '';
      bottleParent.childCodeStatus = 'none';
      bottleParent.parentKeepPlantCount = keepParentPlantCount;
      bottleParent.parentKeep = APIConst.booleans.get(keepParentPlantCount > 0);
    }

    // 更新子瓶信息
    for (const bottle of bottleChildren) {
      bottle.idOppGroup = oppGroupId;
      bottle.allocateTime = now;
      bottle.operatorId = data.operator.id;
      bottle.operatorName = data.operator.name;
      bottle.inspectorId = data.inspector.id;
      bottle.inspectorName = data.inspector.name;
      bottle.plantCountOriginal = 0;
      bottle.plantCountActual = data.defaultPlantsPerChildBottle;
      bottle.operateStatus = 'assigned';
      bottle.discardReason = '';
      bottle.parentKeepPlantCount = 0;
      bottle.childCodeStatus = 'none';
      if (this.#taskView.task.taskType !== 'induction' && bottleParent) {
        bottle.variety = bottleParent.variety;
        bottle.varietyCode = bottleParent.varietyCode;
        bottle.explantCollectionBatch = bottleParent.explantCollectionBatch;
        bottle.generation =
          this.#taskView.task.taskType === 'rooting' &&
          (bottleParent.parentLastTaskType === 'induction' ||
            bottleParent.parentLastTaskType === 'differentiation' ||
            bottleParent.parentLastTaskType === 'multiplication' ||
            bottleParent.parentLastTaskType === 'acclimatization')
            ? bottleParent.generation
            : bottleParent.generation + 1;
      } else if (this.#taskView.task.taskType === 'induction') {
        bottle.variety = this.#taskView.task.variety;
        bottle.varietyCode = data.varietyCode;
        bottle.explantCollectionBatch = this.#taskView.task.explantCollectionBatch;
        bottle.generation = 0;
      }
    }

    // 创建操作组
    const oppGroup: TissueTaskOppGroup = {
      id: oppGroupId,
      idTask: this.#taskView.taskId,
      varietyCode: data.varietyCode,
      idParentBottle: data.idParentBottle,
      parentPlantCount: data.parentPlantCount,
      parentKeep: APIConst.booleans.get(keepParentPlantCount > 0),
      parentKeepPlantCount: keepParentPlantCount,
      allocatorId: this.actionPassport.currentUserId,
      allocatorName: this.actionPassport.currentName,
      allocateTime: now,
      finishTime: 0,
      assigneeInspectorId: data.inspector.id,
      assigneeInspectorName: data.inspector.name,
      assigneeOperatorId: data.operator.id,
      assigneeOperatorName: data.operator.name,
      inspectorId: data.inspector.id,
      inspectorName: data.inspector.name,
      operatorId: data.operator.id,
      operatorName: data.operator.name,
    };
    this.#oppGroupMap.set(oppGroupId, oppGroup);

    // 设置版本信息
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);

    return of(oppGroupId);
  }

  oppGroupFromFinishToProcess(oppGroupId: string): Observable<void> {
    const oppGroup = this.#oppGroupMap.get(oppGroupId);
    if (!oppGroup) throw '没有此操作组。';
    const parentBottles = this.getBottlesForOppGroup(oppGroupId, 'parent');
    parentBottles.forEach((bottle) => {
      bottle.operateStatus = 'assigned';
    });
    oppGroup.finishTime = 0;
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  oppGroupFromProcessToFinish(oppGroupId: string): Observable<void> {
    const oppGroup = this.#oppGroupMap.get(oppGroupId);
    if (!oppGroup) throw '没有此操作组。';
    const parentBottles = this.getBottlesForOppGroup(oppGroupId, 'parent');
    parentBottles.forEach((bottle) => {
      bottle.operateStatus = 'finished';
    });
    oppGroup.finishTime = Date.now();
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  oppGroupUpdateInfo(data: DialogTaskOppGroupUpdateResponseData): Observable<void> {
    const oppGroupId = data.idOppGroup;
    if (!oppGroupId) {
      throw '应选择操作组。';
    }
    if (!data.inspector || !data.operator) {
      throw '应选择质检员和操作员。';
    }
    if (!data.parentPlantCount || data.parentPlantCount < 1 || data.parentPlantCount > 999) {
      throw '母代株数错误。';
    }
    const keepParentPlantCount = data.keepParentPlantCount ?? 0;
    if (this.#taskView.task.taskType != 'induction') {
      if (!data.idParentBottle) {
        throw '请选择母瓶。';
      }
      if (
        keepParentPlantCount < 0 ||
        keepParentPlantCount > 999 ||
        !Number.isInteger(keepParentPlantCount)
      ) {
        throw '留种株数应是不小于零，且小于1000的整数。';
      }
    }

    // 原母瓶
    const parentBottleOriginal = this.getBottle(data.idParentBottleOriginal);
    const parentBottleCurrent = this.getBottle(data.idParentBottle);

    // 是否更换了母瓶
    const parentBottleChanged = parentBottleOriginal !== parentBottleCurrent;
    if (parentBottleChanged) {
      if (!parentBottleCurrent) {
        throw `母瓶 ${data.idParentBottle} 不在此任务中。`;
      }
      if (parentBottleCurrent.roleInTask !== 'parent') {
        throw `子瓶 ${parentBottleCurrent.idBottle} 不能作为母瓶使用。`;
      }
      if (parentBottleCurrent.operateStatus !== 'coded') {
        throw `母瓶 ${parentBottleCurrent.idBottle} 已被分配作业。`;
      }
      // 更新原瓶信息
      if (!!parentBottleOriginal) {
        parentBottleOriginal.idOppGroup = '';
        parentBottleOriginal.allocateTime = 0;
        parentBottleOriginal.finishTime = 0;
        parentBottleOriginal.operatorId = '';
        parentBottleOriginal.operatorName = '';
        parentBottleOriginal.inspectorId = '';
        parentBottleOriginal.inspectorName = '';
        parentBottleOriginal.operateStatus = 'coded';
        parentBottleOriginal.discardReason = '';
        parentBottleOriginal.parentKeepPlantCount = 0;
        parentBottleOriginal.parentKeep = APIConst.booleans.BOOLEAN_FALSE;
        parentBottleOriginal.plantCountActual = parentBottleOriginal.plantCountOriginal;
      }
    }

    if (!!parentBottleCurrent) {
      parentBottleCurrent.idOppGroup = oppGroupId;
      parentBottleCurrent.allocateTime = parentBottleOriginal?.allocateTime || Date.now();
      parentBottleCurrent.operatorId = data.operator.id;
      parentBottleCurrent.operatorName = data.operator.name;
      parentBottleCurrent.inspectorId = data.inspector.id;
      parentBottleCurrent.inspectorName = data.inspector.name;
      parentBottleCurrent.operateStatus = 'assigned';
      parentBottleCurrent.discardReason = '';
      parentBottleCurrent.plantCountActual = data.parentPlantCount;
      parentBottleCurrent.parentKeepPlantCount = keepParentPlantCount;
      parentBottleCurrent.parentKeep = APIConst.booleans.get(keepParentPlantCount > 0);
    }

    // 更新子瓶信息
    this.#bottleAllMap.forEach((bottle) => {
      if (bottle.roleInTask === 'child' && bottle.idOppGroup === oppGroupId) {
        const operatorName = data.operator!.name;
        let varietyCode = bottle.varietyCode;
        let generation = bottle.generation;
        let explantCollectionBatch = bottle.explantCollectionBatch;
        if (this.#taskView.task.taskType !== 'induction' && parentBottleCurrent) {
          varietyCode = parentBottleCurrent.varietyCode;
          explantCollectionBatch = parentBottleCurrent.explantCollectionBatch;
          generation =
            this.#taskView.task.taskType === 'rooting' &&
            (parentBottleCurrent.parentLastTaskType === 'induction' ||
              parentBottleCurrent.parentLastTaskType === 'differentiation' ||
              parentBottleCurrent.parentLastTaskType === 'multiplication' ||
              parentBottleCurrent.parentLastTaskType === 'acclimatization')
              ? parentBottleCurrent.generation
              : parentBottleCurrent.generation + 1;
        } else if (this.#taskView.task.taskType === 'induction') {
          varietyCode = data.varietyCode;
          generation = 0;
          explantCollectionBatch = this.#taskView.task.explantCollectionBatch;
        }

        if (
          (operatorName !== bottle.operatorName ||
            varietyCode !== bottle.varietyCode ||
            generation !== bottle.generation ||
            explantCollectionBatch !== bottle.explantCollectionBatch) &&
          (bottle.childCodeStatus === 'printed' || bottle.childCodeStatus === 'checked')
        ) {
          bottle.childCodeStatus = 'none';
        }
        bottle.operatorId = data.operator!.id;
        bottle.operatorName = operatorName;
        bottle.inspectorId = data.inspector!.id;
        bottle.inspectorName = data.inspector!.name;
        bottle.varietyCode = varietyCode;
        bottle.explantCollectionBatch = explantCollectionBatch;
        bottle.generation = generation;
        if (this.#taskView.task.taskType !== 'induction' && parentBottleCurrent) {
          bottle.variety = parentBottleCurrent.variety;
        } else if (this.#taskView.task.taskType === 'induction') {
          bottle.variety = this.#taskView.task.variety;
        }
      }
    });

    // 更新操作组
    const oppGroup = this.getOppGroup(oppGroupId);
    if (oppGroup) {
      oppGroup.varietyCode =
        this.#taskView.task.taskType !== 'induction' && parentBottleCurrent
          ? parentBottleCurrent.varietyCode
          : this.#taskView.task.taskType === 'induction'
            ? data.varietyCode
            : oppGroup.varietyCode;
      oppGroup.idParentBottle = data.idParentBottle;
      oppGroup.parentPlantCount = data.parentPlantCount;
      oppGroup.parentKeep = APIConst.booleans.get(keepParentPlantCount > 0);
      oppGroup.parentKeepPlantCount = keepParentPlantCount;
      oppGroup.assigneeInspectorId = data.inspector.id;
      oppGroup.assigneeInspectorName = data.inspector.name;
      oppGroup.assigneeOperatorId = data.operator.id;
      oppGroup.assigneeOperatorName = data.operator.name;
      oppGroup.inspectorId = data.inspector.id;
      oppGroup.inspectorName = data.inspector.name;
      oppGroup.operatorId = data.operator.id;
      oppGroup.operatorName = data.operator.name;
    }
    // 设置版本信息
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  oppGroupDelete(oppGroupId: string): Observable<string> {
    if (!oppGroupId) throw '应选择操作组。';
    const oppGroup = this.getOppGroup(oppGroupId);
    if (!oppGroup) throw '应选择操作组。';

    this.#bottleAllMap.forEach((bottle) => {
      if (bottle.idOppGroup === oppGroupId) {
        bottle.idOppGroup = '';
        bottle.operateStatus = 'coded';
        bottle.discardReason = '';
        bottle.plantCountActual = bottle.plantCountOriginal;
        bottle.inspectorName = '';
        bottle.inspectorId = '';
        bottle.operatorId = '';
        bottle.operatorName = '';
        bottle.childCodeStatus = 'none';
        bottle.parentKeepPlantCount = 0;
        bottle.parentKeep = APIConst.booleans.BOOLEAN_FALSE;
        bottle.allocateTime = 0;
        bottle.finishTime = 0;
        if (bottle.roleInTask === 'child') {
          bottle.variety = '';
          bottle.varietyCode = '';
          bottle.explantCollectionBatch = 0;
          bottle.generation = 0;
        }
      }
    });
    this.#oppGroupMap.delete(oppGroupId);
    // 设置版本信息
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of(oppGroupId);
  }

  oppGroupAddChildBottle(data: DialogTaskAddOppGroupBottleChildResponseData): Observable<string> {
    const childBottleUnAssigned = this.bottleChildUnAssignedSig();
    const childCodedBottleCount = childBottleUnAssigned.length;
    if (
      data.childBottleCount < 1 ||
      data.childBottleCount > childCodedBottleCount ||
      !Number.isInteger(data.childBottleCount)
    ) {
      throw `子瓶数量应大于 0 且小于${childCodedBottleCount}。`;
    }

    // 生成操作组ID
    const oppGroupId = data.idOppGroup;
    const oppGroup = this.getOppGroup(oppGroupId);
    if (!oppGroup) throw '应选择操作组';
    if (!!oppGroup.finishTime) {
      throw '操作组已完成，不能对其增瓶。';
    }

    const now = Date.now();

    const bottleParent = this.getBottle(oppGroup.idParentBottle);

    // 更新子瓶信息
    for (let index = 0; index < data.childBottleCount; index++) {
      const bottle = childBottleUnAssigned[index];
      if (!bottle) {
        throw '子瓶已不足。';
      }
      bottle.idOppGroup = oppGroupId;
      bottle.allocateTime = now;
      bottle.operatorId = oppGroup.operatorId;
      bottle.operatorName = oppGroup.operatorName;
      bottle.inspectorId = oppGroup.inspectorId;
      bottle.inspectorName = oppGroup.inspectorName;
      bottle.plantCountOriginal = 0;
      bottle.plantCountActual = data.defaultPlantsPerChildBottle;
      bottle.operateStatus = 'assigned';
      bottle.discardReason = '';
      bottle.parentKeepPlantCount = 0;
      bottle.childCodeStatus = 'none';
      if (this.#taskView.task.taskType !== 'induction' && bottleParent) {
        bottle.variety = bottleParent.variety;
        bottle.varietyCode = bottleParent.varietyCode;
        bottle.explantCollectionBatch = bottleParent.explantCollectionBatch;
        bottle.generation =
          this.#taskView.task.taskType === 'rooting' &&
          (bottleParent.parentLastTaskType === 'induction' ||
            bottleParent.parentLastTaskType === 'differentiation' ||
            bottleParent.parentLastTaskType === 'multiplication' ||
            bottleParent.parentLastTaskType === 'acclimatization')
            ? bottleParent.generation
            : bottleParent.generation + 1;
      } else if (this.#taskView.task.taskType === 'induction') {
        bottle.variety = this.#taskView.task.variety;
        bottle.varietyCode = oppGroup.varietyCode;
        bottle.explantCollectionBatch = this.#taskView.task.explantCollectionBatch;
        bottle.generation = 0;
      }
    }
    // 设置版本信息
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of(oppGroupId);
  }

  bottleChildToAssigned(idBottle: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (!bottle || bottle.roleInTask !== 'child' || !bottle.idOppGroup) {
      throw '未知的瓶码信息。';
    }
    bottle.operateStatus = 'assigned';
    bottle.discardReason = '';
    bottle.childCodeStatus = 'none';
    bottle.finishTime = 0;
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildToUnAssigned(idBottle: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (!bottle || bottle.roleInTask !== 'child') {
      throw '未知的瓶码信息。';
    }
    bottle.idOppGroup = '';
    bottle.variety = '';
    bottle.varietyCode = '';
    bottle.explantCollectionBatch = 0;
    bottle.generation = 0;
    bottle.operateStatus = 'coded';
    bottle.discardReason = '';
    bottle.plantCountActual = bottle.plantCountOriginal;
    bottle.inspectorName = '';
    bottle.inspectorId = '';
    bottle.operatorId = '';
    bottle.operatorName = '';
    bottle.childCodeStatus = 'none';
    bottle.parentKeepPlantCount = 0;
    bottle.parentKeep = APIConst.booleans.BOOLEAN_FALSE;
    bottle.allocateTime = 0;
    bottle.finishTime = 0;
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildToFinished(idBottle: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (!bottle || bottle.roleInTask !== 'child' || !bottle.idOppGroup) {
      throw '未知的瓶码信息。';
    }
    bottle.operateStatus = 'finished';
    bottle.discardReason = '';
    bottle.finishTime = Date.now();
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildToFinishedBatch(oppGroupId: string): Observable<void> {
    const bottles = this.getBottlesForOppGroup(oppGroupId, 'child');
    for (const bottle of bottles) {
      if (bottle.operateStatus === 'assigned') {
        bottle.operateStatus = 'finished';
        bottle.discardReason = '';
        bottle.finishTime = Date.now();
      }
    }
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildToPrinted(idBottle: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (
      !bottle ||
      bottle.roleInTask !== 'child' ||
      !bottle.idOppGroup ||
      bottle.operateStatus !== 'finished'
    ) {
      throw '未知的瓶码信息。';
    }
    return this.actionPrinter
      .print({
        codePrint: {
          id: '',
          tableName: 'tissue_task',
          recordId: this.#taskView.taskId,
          description: '单独打印任务子瓶瓶码',
          operatorId: '',
          operatorName: '',
          operateTime: 0,
        },
        codePrintDetails: [
          {
            idPrint: '',
            code: idBottle,
            varietyCode: bottle.varietyCode,
            explantCollectionBatch: bottle.explantCollectionBatch,
            generation: bottle.generation,
            operatorName: bottle.operatorName,
            operateTime: bottle.finishTime,
          },
        ],
      })
      .pipe(
        tap((_) => {
          bottle.childCodeStatus = 'printed';
          this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
        }),
        takeUntil(this.dispose$),
      );
  }

  bottleChildToPrintedBatch(oppGroupId: string): Observable<void> {
    const bottles = this.getBottlesForOppGroup(oppGroupId, 'child').filter(
      (bottle) =>
        !!bottle &&
        bottle.roleInTask === 'child' &&
        bottle.operateStatus === 'finished' &&
        bottle.childCodeStatus === 'none',
    );
    if (bottles.length < 1) throw '没有尚未打印的瓶码。';
    const printModelList = bottles.map((bottle) => ({
      idPrint: '',
      code: bottle.idBottle,
      varietyCode: bottle.varietyCode,
      explantCollectionBatch: bottle.explantCollectionBatch,
      generation: bottle.generation,
      operatorName: bottle.operatorName,
      operateTime: bottle.finishTime,
    }));
    return this.actionPrinter
      .print({
        codePrint: {
          id: '',
          tableName: 'tissue_task',
          recordId: this.#taskView.taskId,
          description: '批量打印任务子瓶瓶码',
          operatorId: '',
          operatorName: '',
          operateTime: 0,
        },
        codePrintDetails: printModelList,
      })
      .pipe(
        tap((_) => {
          bottles.forEach((bottle) => (bottle.childCodeStatus = 'printed'));
          this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
        }),
        takeUntil(this.dispose$),
      );
  }

  bottleChildRevertPrinted(idBottle: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (
      !bottle ||
      bottle.roleInTask !== 'child' ||
      !bottle.idOppGroup ||
      bottle.operateStatus !== 'finished' ||
      bottle.childCodeStatus !== 'printed'
    ) {
      throw '未知的瓶码信息。';
    }
    bottle.childCodeStatus = 'none';
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildToChecked(idBottle: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (
      !bottle ||
      bottle.roleInTask !== 'child' ||
      !bottle.idOppGroup ||
      bottle.operateStatus !== 'finished' ||
      bottle.childCodeStatus !== 'printed'
    ) {
      throw '未知的瓶码信息。';
    }
    bottle.childCodeStatus = 'checked';
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildToCheckedBatch(oppGroupId: string): Observable<void> {
    const bottles = this.getBottlesForOppGroup(oppGroupId, 'child').filter(
      (bottle) =>
        !!bottle &&
        bottle.roleInTask === 'child' &&
        bottle.operateStatus === 'finished' &&
        bottle.childCodeStatus === 'printed',
    );
    if (bottles.length < 1) throw '没有尚未核对的瓶码。';
    bottles.forEach((bottle) => (bottle.childCodeStatus = 'checked'));
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildRevertChecked(idBottle: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (
      !bottle ||
      bottle.roleInTask !== 'child' ||
      !bottle.idOppGroup ||
      bottle.operateStatus !== 'finished' ||
      bottle.childCodeStatus !== 'checked'
    ) {
      throw '未知的瓶码信息。';
    }
    bottle.childCodeStatus = 'printed';
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildToDiscard(idBottle: string, reason: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (!bottle || bottle.roleInTask !== 'child' || !bottle.idOppGroup) {
      throw '未知的瓶码信息。';
    }
    if (!reason) {
      throw '应填写淘汰原因。';
    }
    bottle.operateStatus = 'discard';
    bottle.discardReason = reason;
    bottle.finishTime = Date.now();
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildRevertToNoneForDiscard(idBottle: string): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (
      !bottle ||
      bottle.roleInTask !== 'child' ||
      !bottle.idOppGroup ||
      bottle.operateStatus !== 'discard' ||
      (bottle.childCodeStatus !== 'printed' && bottle.childCodeStatus !== 'checked')
    ) {
      throw '未知的瓶码信息。';
    }
    bottle.childCodeStatus = 'none';
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildRevertToNoneForDiscardBatch(oppGroupId: string): Observable<void> {
    const bottles = this.getBottlesForOppGroup(oppGroupId, 'child').filter(
      (bottle) =>
        !!bottle &&
        bottle.roleInTask === 'child' &&
        bottle.operateStatus === 'discard' &&
        (bottle.childCodeStatus === 'printed' || bottle.childCodeStatus === 'checked'),
    );
    if (bottles.length < 1) throw '没有尚未核对的瓶码。';
    bottles.forEach((bottle) => (bottle.childCodeStatus = 'none'));
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  bottleChildUpdatePlant(idBottle: string, plants: number): Observable<void> {
    const bottle = this.#bottleAllMap.get(idBottle);
    if (!bottle || bottle.roleInTask !== 'child' || !bottle.idOppGroup) {
      throw '未知的瓶码信息。';
    }
    if (!Number.isInteger(plants) || plants < 1 || plants > 999) {
      throw `株数应是大于 0，且小于 1,000 的整数。`;
    }
    bottle.plantCountActual = plants;
    this.#updateVersionOfProcessInfoSig.update((v) => v + 1);
    return of();
  }

  storageOutRejStore(model: DialogTaskStorageOutRejStoreData): Observable<void> {
    return this.processInfoSave().pipe(
      tap((_) => (this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig())),
      switchMap((_) => this.actionStorageInOut.rejStore(model)),
      switchMap((_) => this.actionTissueTask.queryByTaskId(model.idTask)),
      tap((taskView) => {
        this.#organizeData(taskView);
        this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig();
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }

  storageOutAccept(model: ModelStorageOutTaskProcessDetail): Observable<void> {
    return this.processInfoSave().pipe(
      tap((_) => (this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig())),
      switchMap((_) => this.actionTissueTask.acceptStorageOut(model)),
      tap((taskView) => {
        this.#organizeData(taskView);
        this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig();
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }

  storageInCreate(): Observable<void> {
    const taskView = this.#taskView;
    if (taskView.task.taskStatus !== 'finished') {
      throw '任务尚未完成，无法申请入库。';
    }
    return this.actionTissueTask.applyStorageIn(taskView.taskId).pipe(
      tap((taskView) => {
        this.#organizeStorageIn(taskView);
        this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig();
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }

  storageInDelete(): Observable<void> {
    const storageIn = this.#storageIn;
    if (!storageIn) throw '尚未申请入库，无法删除。';
    return this.actionTissueTask.deleteStorageIn(storageIn.id).pipe(
      tap((taskView) => {
        this.#organizeStorageIn(taskView);
        this.savedVersionOfProcessInfo = this.#updateVersionOfProcessInfoSig();
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }
}
