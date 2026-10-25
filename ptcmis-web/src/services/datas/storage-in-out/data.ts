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
import { StorageInOutLog } from '../../../entities/models/StorageInOutLog';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionStorageOutTaskView } from '../../../entities/api/QueryConditionStorageOutTaskView';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { StorageOutTaskView } from '../../../entities/api/StorageOutTaskView';
import { StorageInOutBottle } from '../../../entities/models/StorageInOutBottle';
import { ModelStorageOutTaskProcessDetail } from '../../../entities/api/ModelStorageOutTaskProcessDetail';
import { Archive } from '../../../entities/models/Archive';
import { StorageInOut } from '../../../entities/models/StorageInOut';
import { ModelStorageOutInspectProcessDetail } from '../../../entities/api/ModelStorageOutInspectProcessDetail';

export const IJT_DataStorageInOut = new InjectionToken<DataStorageInOut>('DataStorageInOut');

export interface DataStorageInOut {
  queryAllLogs(storageInOutId: string): Observable<StorageInOutLog[]>;
  queryByCondition(
    condition: QueryCondition<QueryConditionStorageOutTaskView>,
  ): Observable<QueryResultPaged<StorageOutTaskView>>;
  queryBottles(storageInOutId: string): Observable<StorageInOutBottle[]>;
  rejBiz(model: { id: string; idTask: string; oppReason: string }): Observable<void>;
  rejStore(model: { id: string; idTask: string; oppReason: string }): Observable<void>;
  outProcessForTask(model: ModelStorageOutTaskProcessDetail): Observable<void>;
  outDoneForTask(model: ModelStorageOutTaskProcessDetail): Observable<void>;
  rejBizForIn(model: { id: string; idTask: string; oppReason: string }): Observable<void>;
  inDoneForTask(inOutId: string): Observable<void>;
  queryInspectOutByCondition(
    condition: QueryCondition<QueryConditionStorageOutTaskView>,
  ): Observable<QueryResultPaged<StorageInOut>>;
  createInspectOut(): Observable<void>;
  outProcessForInspect(model: ModelStorageOutInspectProcessDetail): Observable<void>;
  outFinishForInspect(model: ModelStorageOutInspectProcessDetail): Observable<void>;
  deleteForInspect(inOutId: string): Observable<void>;
  createInventorySubBottle(): Observable<void>;
  outProcessForInventorySubBottle(model: ModelStorageOutInspectProcessDetail): Observable<void>;
  outFinishForInventorySubBottle(model: ModelStorageOutInspectProcessDetail): Observable<void>;
  deleteForInventorySubBottle(inOutId: string): Observable<void>;
  createAcclimatizationOut(): Observable<void>;
  outProcessForAcclimatizationOut(model: ModelStorageOutInspectProcessDetail): Observable<void>;
  outFinishForAcclimatizationOut(model: ModelStorageOutInspectProcessDetail): Observable<void>;
  deleteForAcclimatizationOut(inOutId: string): Observable<void>;
  createAcclimatizationIn(): Observable<void>;
  inProcessForAcclimatizationIn(model: ModelStorageOutInspectProcessDetail): Observable<void>;
  inFinishForAcclimatizationIn(model: ModelStorageOutInspectProcessDetail): Observable<void>;
  deleteForAcclimatizationIn(inOutId: string): Observable<void>;
  uploadArchives(inOutId: string, description: string, files: File[]): Observable<Archive[]>;
  queryArchives(inOutId: string): Observable<Archive[]>;
}
