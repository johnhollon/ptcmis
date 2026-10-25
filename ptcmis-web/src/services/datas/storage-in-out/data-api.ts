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

import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { DataStorageInOut } from './data';
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

export class DataStorageInOutApi implements DataStorageInOut {
  private http = inject(HttpClient);
  queryAllLogs(storageInOutId: string): Observable<StorageInOutLog[]> {
    return this.http.post<StorageInOutLog[]>('/api/storage-in-out/queryAllLogs', storageInOutId, {
      responseType: 'json',
    });
  }
  queryByCondition(
    condition: QueryCondition<QueryConditionStorageOutTaskView>,
  ): Observable<QueryResultPaged<StorageOutTaskView>> {
    return this.http.post<QueryResultPaged<StorageOutTaskView>>(
      '/api/storage-in-out/queryByCondition',
      condition,
      {
        responseType: 'json',
      },
    );
  }
  queryBottles(storageInOutId: string): Observable<StorageInOutBottle[]> {
    return this.http.post<StorageInOutBottle[]>(
      '/api/storage-in-out/queryBottles',
      storageInOutId,
      {
        responseType: 'json',
      },
    );
  }
  rejBiz(model: { id: string; idTask: string; oppReason: string }): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/rejBiz', model, {
      responseType: 'text',
    });
  }
  rejStore(model: { id: string; idTask: string; oppReason: string }): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/rejStore', model, {
      responseType: 'text',
    });
  }
  outProcessForTask(model: ModelStorageOutTaskProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/outProcessForTask', model, {
      responseType: 'text',
    });
  }
  outDoneForTask(model: ModelStorageOutTaskProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/outDoneForTask', model, {
      responseType: 'text',
    });
  }
  rejBizForIn(model: { id: string; idTask: string; oppReason: string }): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/rejBizForIn', model, {
      responseType: 'text',
    });
  }
  inDoneForTask(inOutId: string): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/inDoneForTask', inOutId, {
      responseType: 'text',
    });
  }
  queryInspectOutByCondition(
    condition: QueryCondition<QueryConditionStorageOutTaskView>,
  ): Observable<QueryResultPaged<StorageInOut>> {
    return this.http.post<QueryResultPaged<StorageInOut>>(
      '/api/storage-in-out/queryInspectOutByCondition',
      condition,
      {
        responseType: 'json',
      },
    );
  }
  createInspectOut(): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/createInspectOut', {
      responseType: 'text',
    });
  }
  outProcessForInspect(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/outProcessForInspect', model, {
      responseType: 'text',
    });
  }
  outFinishForInspect(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/outFinishForInspect', model, {
      responseType: 'text',
    });
  }
  deleteForInspect(inOutId: string): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/deleteForInspect', inOutId, {
      responseType: 'text',
    });
  }
  createInventorySubBottle(): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/createInventorySubBottle', {
      responseType: 'text',
    });
  }
  outProcessForInventorySubBottle(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/outProcessForInventorySubBottle', model, {
      responseType: 'text',
    });
  }
  outFinishForInventorySubBottle(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/outFinishForInventorySubBottle', model, {
      responseType: 'text',
    });
  }
  deleteForInventorySubBottle(inOutId: string): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/deleteForInventorySubBottle', inOutId, {
      responseType: 'text',
    });
  }
  createAcclimatizationOut(): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/createAcclimatizationOut', {
      responseType: 'text',
    });
  }
  outProcessForAcclimatizationOut(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/outProcessForAcclimatizationOut', model, {
      responseType: 'text',
    });
  }
  outFinishForAcclimatizationOut(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/outFinishForAcclimatizationOut', model, {
      responseType: 'text',
    });
  }
  deleteForAcclimatizationOut(inOutId: string): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/deleteForAcclimatizationOut', inOutId, {
      responseType: 'text',
    });
  }
  createAcclimatizationIn(): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/createAcclimatizationIn', {
      responseType: 'text',
    });
  }
  inProcessForAcclimatizationIn(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/inProcessForAcclimatizationIn', model, {
      responseType: 'text',
    });
  }
  inFinishForAcclimatizationIn(model: ModelStorageOutInspectProcessDetail): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/inFinishForAcclimatizationIn', model, {
      responseType: 'text',
    });
  }
  deleteForAcclimatizationIn(inOutId: string): Observable<void> {
    return this.http.post<void>('/api/storage-in-out/deleteForAcclimatizationIn', inOutId, {
      responseType: 'text',
    });
  }
  uploadArchives(inOutId: string, description: string, files: File[]): Observable<Archive[]> {
    const formData = new FormData();
    formData.append('inOutId', inOutId);
    formData.append('description', description ?? '');
    for (const file of files) {
      formData.append('files', file);
    }
    return this.http.post<Archive[]>('/api/storage-in-out/uploadArchives', formData);
  }
  queryArchives(inOutId: string): Observable<Archive[]> {
    return this.http.post<Archive[]>('/api/storage-in-out/queryArchives', inOutId, {
      responseType: 'json',
    });
  }
}
