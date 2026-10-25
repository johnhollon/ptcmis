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
  effect,
  EventEmitter,
  inject,
  OnDestroy,
  Output,
  signal,
} from '@angular/core';
import { LayoutDrawerOppSimple } from '../../../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { ServiceTissueTaskProcess } from '../process/service';
import { FormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { PipeTimestamp } from '../../../../../components/00200-pipes/pipe-timestamp';
import { PipeTaskType } from '../../../../../components/00200-pipes/pipe-task-type';
import { PipeTaskStatus } from '../../../../../components/00200-pipes/pipe-task-status';
import { StorageInOutBottle } from '../../../../../entities/models/StorageInOutBottle';
import { TissueTask } from '../../../../../entities/models/TissueTask';
import { TissueTaskBottle } from '../../../../../entities/models/TissueTaskBottle';
import { APIConst } from '../../../../../entities/api/const-api';
import { PipeStorageInOutStatus } from '../../../../../components/00200-pipes/pipe-storage-in-out-status';

@Component({
  selector: 'tissue-task-process-stat-do',
  standalone: true,
  templateUrl: './comp.html',
  styleUrls: ['./comp.scss'],
  imports: [
    LayoutDrawerOppSimple,
    FormsModule,
    MatButton,
    MatTooltip,
    PipeTimestamp,
    PipeTaskType,
    PipeTaskStatus,
    PipeStorageInOutStatus,
  ],
})
export class TissueTaskProcessStatDo implements OnDestroy {
  @Output() onCancel = new EventEmitter<void>();

  protected serviceTissueTaskProcess = inject(ServiceTissueTaskProcess);

  protected readonly bottleAllMap = new Map<string, StorageInOutBottle>();
  protected updateVersionSig = signal<number>(0);
  #effect = effect(() => {
    if (this.serviceTissueTaskProcess.updateVersionOfStorageInSig() < 0) return;
    this.doQuery();
  });
  protected readonly bottleParentReturnListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'parent_in',
    );
  });
  protected readonly bottleParentReturnStatSigReadonly = computed(() => {
    const list = this.bottleParentReturnListSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleParentKeepListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'parent_keep_in',
    );
  });
  protected readonly bottleParentKeepStatSigReadonly = computed(() => {
    const list = this.bottleParentKeepListSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });
  protected readonly bottleChildInListSig = computed(() => {
    if (this.updateVersionSig() < 0) return [] as StorageInOutBottle[];
    return [...this.bottleAllMap.values()].filter(
      (bottle) => bottle.inOutBottleType === 'child_in',
    );
  });
  protected readonly bottleChildInStatSigReadonly = computed(() => {
    const list = this.bottleChildInListSig();
    const plants = list.map((b) => b.plantCountActual).reduce((p, c) => p + c, 0);
    return {
      bottleCount: list.length,
      plantCount: plants,
    };
  });

  private doQuery() {
    const task = this.serviceTissueTaskProcess.taskSig();
    const storageIn = this.serviceTissueTaskProcess.storageInSig();
    this.bottleAllMap.clear();
    if (!storageIn || !storageIn.status) return;
    const bottles = [...(task.bottlesParentAll ?? []), ...(task.bottlesChildrenAll ?? [])];
    for (const bottle of bottles) {
      const b = this.calculateStorageInOutBottle(storageIn.id, task.task, bottle);
      if (!b) continue;
      this.bottleAllMap.set(b.idBottle, b);
    }
    this.updateVersionSig.update((v) => v + 1);
  }

  private calculateStorageInOutBottle(
    idInOut: string,
    task: TissueTask,
    bottle: TissueTaskBottle,
  ): StorageInOutBottle | undefined {
    const inOutBottleType =
      bottle.roleInTask === 'parent' && bottle.parentKeep === APIConst.booleans.BOOLEAN_TRUE
        ? 'parent_keep_in'
        : bottle.roleInTask === 'parent' && !bottle.idOppGroup
          ? 'parent_in'
          : bottle.roleInTask === 'child' &&
              bottle.operateStatus === 'finished' &&
              bottle.childCodeStatus === 'checked'
            ? 'child_in'
            : '';
    if (!inOutBottleType) return undefined;
    const plantCount =
      inOutBottleType === 'parent_keep_in' ? bottle.parentKeepPlantCount : bottle.plantCountActual;
    return {
      idInOut: idInOut,
      idBottle: bottle.idBottle,
      variety: task.variety,
      varietyCode: task.varietyCode,
      explantCollectionBatch: task.explantCollectionBatch,
      // generation: bottle.roleInTask === 'parent' ? task.parentGeneration : task.childGeneration,
      generation:0,
      ignoreStats: APIConst.booleans.BOOLEAN_FALSE,
      inOutBottleType: inOutBottleType,
      plantCountApplied: plantCount,
      plantCountActual: plantCount,
      keep: APIConst.booleans.get(inOutBottleType === 'parent_keep_in'),
      status: 'normal',
      discardReason: '',
    };
  }

  ngOnDestroy(): void {
    this.#effect.destroy();
  }
}
