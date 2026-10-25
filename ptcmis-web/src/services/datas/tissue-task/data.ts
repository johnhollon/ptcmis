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

import { InjectionToken } from '@angular/core';
import { Observable } from 'rxjs';
import { ModelTissueTaskPrintBottleCode } from '../../../entities/api/ModelTissueTaskPrintBottleCode';
import { ModelTissueTaskProcessSettings } from '../../../entities/api/ModelTissueTaskProcessSettings';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { TissueTaskView } from '../../../entities/api/TissueTaskView';
import { Condition, TissueTask } from '../../../entities/models/TissueTask';
import { StorageInOut } from '../../../entities/models/StorageInOut';
import { ModelStorageOutTaskProcessDetail } from '../../../entities/api/ModelStorageOutTaskProcessDetail';
import { ModelTissueTaskPrintBottleCodeForAddBottles } from '../../../entities/api/ModelTissueTaskPrintBottleCodeForAddBottles';
import { ModelTissueTaskProcessDetail } from '../../../entities/api/ModelTissueTaskProcessDetail';
import { ModelPrintBottleCodeForAddBottles } from '../../../entities/api/ModelPrintBottleCodeForAddBottles';
import { Archive } from '../../../entities/models/Archive';
import { StorageOutTaskView } from '../../../entities/api/StorageOutTaskView';

export const IJT_DataTissueTask = new InjectionToken<DataTissueTask>('DataTissueTask');

export interface DataTissueTask {
  queryByTaskId(taskId: string): Observable<TissueTaskView>;
  queryByCondition(
    condition: QueryCondition<Condition>,
  ): Observable<QueryResultPaged<TissueTaskView>>;
  queryOrCreateCodePrefix(taskId: string): Observable<string>;
  createTask(task: TissueTask): Observable<void>;
  deleteTask(taskId: string): Observable<void>;
  updateTask(task: TissueTask): Observable<TissueTaskView>;
  printBottleCode(model: ModelTissueTaskPrintBottleCode): Observable<TissueTaskView>;
  printBottleCodeForAddBottles(
    model: ModelTissueTaskPrintBottleCodeForAddBottles,
  ): Observable<ModelPrintBottleCodeForAddBottles>;
  saveProcessSettings(settings: ModelTissueTaskProcessSettings): Observable<TissueTaskView>;
  deleteStorageOut(model: StorageOutTaskView): Observable<void>;
  acceptStorageOut(model: ModelStorageOutTaskProcessDetail): Observable<TissueTaskView>;
  saveProcess(processDetail: ModelTissueTaskProcessDetail): Observable<TissueTaskView>;
  updateTaskStatusToFinish(taskId: string): Observable<TissueTaskView>;
  updateTaskStatusFromFinishToProcess(taskId: string): Observable<TissueTaskView>;
  applyStorageIn(taskId: string): Observable<TissueTaskView>;
  deleteStorageIn(inOutId: string): Observable<TissueTaskView>;
  uploadArchives(taskId: string, description: string, files: File[]): Observable<Archive[]>;
  queryArchives(taskId: string): Observable<Archive[]>;
}
