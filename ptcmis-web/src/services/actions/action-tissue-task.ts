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

import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ModelTissueTaskPrintBottleCode } from '../../entities/api/ModelTissueTaskPrintBottleCode';
import { ModelTissueTaskProcessSettings } from '../../entities/api/ModelTissueTaskProcessSettings';
import { QueryCondition } from '../../entities/api/QueryCondition';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { TissueTaskView } from '../../entities/api/TissueTaskView';
import { Condition, TissueTask } from '../../entities/models/TissueTask';
import { IJT_DataTissueTask } from '../datas/tissue-task/data';
import { StorageInOut } from '../../entities/models/StorageInOut';
import { ModelStorageOutTaskProcessDetail } from '../../entities/api/ModelStorageOutTaskProcessDetail';
import { ModelTissueTaskPrintBottleCodeForAddBottles } from '../../entities/api/ModelTissueTaskPrintBottleCodeForAddBottles';
import { ModelTissueTaskProcessDetail } from '../../entities/api/ModelTissueTaskProcessDetail';
import { ModelPrintBottleCodeForAddBottles } from '../../entities/api/ModelPrintBottleCodeForAddBottles';
import { Archive } from '../../entities/models/Archive';
import { StorageOutTaskView } from '../../entities/api/StorageOutTaskView';

@Injectable({ providedIn: 'root' })
export class ActionTissueTask {
  private dataTissueTask = inject(IJT_DataTissueTask);

  queryByTaskId(taskId: string): Observable<TissueTaskView> {
    return this.dataTissueTask.queryByTaskId(taskId);
  }
  queryByCondition(
    condition: QueryCondition<Condition>,
  ): Observable<QueryResultPaged<TissueTaskView>> {
    return this.dataTissueTask.queryByCondition(condition);
  }
  queryOrCreateCodePrefix(taskId: string): Observable<string> {
    return this.dataTissueTask.queryOrCreateCodePrefix(taskId);
  }
  createTask(task: TissueTask): Observable<void> {
    return this.dataTissueTask.createTask(task);
  }
  deleteTask(taskId: string): Observable<void> {
    return this.dataTissueTask.deleteTask(taskId);
  }
  updateTask(task: TissueTask): Observable<TissueTaskView> {
    return this.dataTissueTask.updateTask(task);
  }
  printBottleCode(model: ModelTissueTaskPrintBottleCode): Observable<TissueTaskView> {
    return this.dataTissueTask.printBottleCode(model);
  }
  printBottleCodeForAddBottles(
    model: ModelTissueTaskPrintBottleCodeForAddBottles,
  ): Observable<ModelPrintBottleCodeForAddBottles> {
    return this.dataTissueTask.printBottleCodeForAddBottles(model);
  }
  saveProcessSettings(settings: ModelTissueTaskProcessSettings): Observable<TissueTaskView> {
    return this.dataTissueTask.saveProcessSettings(settings);
  }
  deleteStorageOut(model: StorageOutTaskView): Observable<void> {
    return this.dataTissueTask.deleteStorageOut(model);
  }
  acceptStorageOut(model: ModelStorageOutTaskProcessDetail): Observable<TissueTaskView> {
    return this.dataTissueTask.acceptStorageOut(model);
  }
  saveProcess(processDetail: ModelTissueTaskProcessDetail): Observable<TissueTaskView> {
    return this.dataTissueTask.saveProcess(processDetail);
  }
  updateTaskStatusToFinish(taskId: string): Observable<TissueTaskView> {
    return this.dataTissueTask.updateTaskStatusToFinish(taskId);
  }
  updateTaskStatusFromFinishToProcess(taskId: string): Observable<TissueTaskView> {
    return this.dataTissueTask.updateTaskStatusFromFinishToProcess(taskId);
  }
  applyStorageIn(taskId: string): Observable<TissueTaskView> {
    return this.dataTissueTask.applyStorageIn(taskId);
  }
  deleteStorageIn(inOutId: string): Observable<TissueTaskView> {
    return this.dataTissueTask.deleteStorageIn(inOutId);
  }
  uploadArchives(taskId: string, description: string, files: File[]): Observable<Archive[]> {
    return this.dataTissueTask.uploadArchives(taskId, description, files);
  }
  queryArchives(taskId: string): Observable<Archive[]> {
    return this.dataTissueTask.queryArchives(taskId);
  }
}
