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
import { IJT_DataStorageInOut } from '../datas/storage-in-out/data';
import { StorageInOutLog } from '../../entities/models/StorageInOutLog';
import { QueryCondition } from '../../entities/api/QueryCondition';
import { QueryConditionStorageOutTaskView } from '../../entities/api/QueryConditionStorageOutTaskView';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { StorageOutTaskView } from '../../entities/api/StorageOutTaskView';
import { StorageInOutBottle } from '../../entities/models/StorageInOutBottle';
import { ModelStorageOutTaskProcessDetail } from '../../entities/api/ModelStorageOutTaskProcessDetail';
import { Archive } from '../../entities/models/Archive';
import { StorageInOut } from '../../entities/models/StorageInOut';
import { ModelStorageOutInspectProcessDetail } from '../../entities/api/ModelStorageOutInspectProcessDetail';

@Injectable({ providedIn: 'root' })
export class ActionStorageInOut {
  private dataStorageInOut = inject(IJT_DataStorageInOut);

  queryAllLogs(storageInOutId: string): Observable<StorageInOutLog[]> {
    return this.dataStorageInOut.queryAllLogs(storageInOutId);
  }
  queryByCondition(
    condition: QueryCondition<QueryConditionStorageOutTaskView>,
  ): Observable<QueryResultPaged<StorageOutTaskView>> {
    return this.dataStorageInOut.queryByCondition(condition);
  }
  queryBottles(storageInOutId: string): Observable<StorageInOutBottle[]> {
    return this.dataStorageInOut.queryBottles(storageInOutId);
  }
  rejBiz(model: { id: string; idTask: string; oppReason: string }): Observable<void> {
    return this.dataStorageInOut.rejBiz(model);
  }
  rejStore(model: { id: string; idTask: string; oppReason: string }): Observable<void> {
    return this.dataStorageInOut.rejStore(model);
  }
  outProcessForTask(model: ModelStorageOutTaskProcessDetail): Observable<void> {
    return this.dataStorageInOut.outProcessForTask(model);
  }
  outDoneForTask(model: ModelStorageOutTaskProcessDetail): Observable<void> {
    return this.dataStorageInOut.outDoneForTask(model);
  }
  rejBizForIn(model: { id: string; idTask: string; oppReason: string }): Observable<void> {
    return this.dataStorageInOut.rejBizForIn(model);
  }
  inDoneForTask(inOutId: string): Observable<void> {
    return this.dataStorageInOut.inDoneForTask(inOutId);
  }
  queryInspectOutByCondition(
    condition: QueryCondition<QueryConditionStorageOutTaskView>,
  ): Observable<QueryResultPaged<StorageInOut>> {
    return this.dataStorageInOut.queryInspectOutByCondition(condition);
  }
  createInspectOut(): Observable<void> {
    return this.dataStorageInOut.createInspectOut();
  }
  outProcessForInspect(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.dataStorageInOut.outProcessForInspect(model);
  }
  outFinishForInspect(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.dataStorageInOut.outFinishForInspect(model);
  }
  deleteForInspect(inOutId: string): Observable<void> {
    return this.dataStorageInOut.deleteForInspect(inOutId);
  }
  createInventorySubBottle(): Observable<void> {
    return this.dataStorageInOut.createInventorySubBottle();
  }
  outProcessForInventorySubBottle(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.dataStorageInOut.outProcessForInventorySubBottle(model);
  }
  outFinishForInventorySubBottle(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.dataStorageInOut.outFinishForInventorySubBottle(model);
  }
  deleteForInventorySubBottle(inOutId: string): Observable<void> {
    return this.dataStorageInOut.deleteForInventorySubBottle(inOutId);
  }
  createAcclimatizationOut(): Observable<void> {
    return this.dataStorageInOut.createAcclimatizationOut();
  }
  outProcessForAcclimatizationOut(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.dataStorageInOut.outProcessForAcclimatizationOut(model);
  }
  outFinishForAcclimatizationOut(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.dataStorageInOut.outFinishForAcclimatizationOut(model);
  }
  deleteForAcclimatizationOut(inOutId: string): Observable<void> {
    return this.dataStorageInOut.deleteForAcclimatizationOut(inOutId);
  }
  createAcclimatizationIn(): Observable<void> {
    return this.dataStorageInOut.createAcclimatizationIn();
  }
  inProcessForAcclimatizationIn(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.dataStorageInOut.inProcessForAcclimatizationIn(model);
  }
  inFinishForAcclimatizationIn(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.dataStorageInOut.inFinishForAcclimatizationIn(model);
  }
  deleteForAcclimatizationIn(inOutId: string): Observable<void> {
    return this.dataStorageInOut.deleteForAcclimatizationIn(inOutId);
  }
  uploadArchives(inOutId: string, description: string, files: File[]): Observable<Archive[]> {
    return this.dataStorageInOut.uploadArchives(inOutId, description, files);
  }
  queryArchives(inOutId: string): Observable<Archive[]> {
    return this.dataStorageInOut.queryArchives(inOutId);
  }
}
