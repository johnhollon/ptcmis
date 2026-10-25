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

import { HttpClient, HttpResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { DataBottle } from './data';
import { ModelBottleSimple } from '../../../entities/api/ModelBottleSimple';
import { Bottle } from '../../../entities/models/Bottle';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionBottle } from '../../../entities/api/QueryConditionBottle';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { ModelBottleChangeParent } from '../../../entities/api/ModelBottleChangeParent';
import { ModelBottleChangePlants } from '../../../entities/api/ModelBottleChangePlants';
import { BottleLog } from '../../../entities/models/BottleLog';
import { Archive } from '../../../entities/models/Archive';
import { QueryConditionStatWorkload } from '../../../entities/api/QueryConditionStatWorkload';

// 通行证。
export class DataBottleApi implements DataBottle {
  private http = inject(HttpClient);

  queryByCondition(
    condition: QueryCondition<QueryConditionBottle>,
  ): Observable<QueryResultPaged<Bottle>> {
    return this.http.post<QueryResultPaged<Bottle>>('/api/bottle/queryByCondition', condition, {
      responseType: 'json',
    });
  }
  queryAllByCondition(condition: QueryConditionBottle): Observable<ModelBottleSimple[]> {
    return this.http.post<ModelBottleSimple[]>('/api/bottle/queryAllByCondition', condition, {
      responseType: 'json',
    });
  }
  changeParentBottle(model: ModelBottleChangeParent): Observable<void> {
    return this.http.post<void>('/api/bottle/changeParentBottle', model, {
      responseType: 'text',
    });
  }
  changePlantCount(model: ModelBottleChangePlants): Observable<void> {
    return this.http.post<void>('/api/bottle/changePlantCount', model, {
      responseType: 'text',
    });
  }
  queryAllEnabledIdsForTaskParents(taskId: string): Observable<ModelBottleSimple[]> {
    return this.http.post<ModelBottleSimple[]>(
      '/api/bottle/queryAllEnabledIdsForTaskParents',
      taskId,
      {
        responseType: 'json',
      },
    );
  }

  queryAllEnabledIdsForCodeComplementParents(taskId: string): Observable<ModelBottleSimple[]> {
    return this.http.post<ModelBottleSimple[]>(
      '/api/bottle/queryAllEnabledIdsForCodeComplementParents',
      taskId,
      {
        responseType: 'json',
      },
    );
  }

  queryAllEnabledIdsForInventoryAddBottle(taskId: string): Observable<ModelBottleSimple[]> {
    return this.http.post<ModelBottleSimple[]>(
      '/api/bottle/queryAllEnabledIdsForInventoryAddBottle',
      taskId,
      {
        responseType: 'json',
      },
    );
  }

  queryAllEnabledParentIdsForChangeParent(bottleId: string): Observable<ModelBottleSimple[]> {
    return this.http.post<ModelBottleSimple[]>(
      '/api/bottle/queryAllEnabledParentIdsForChangeParent',
      bottleId,
      {
        responseType: 'json',
      },
    );
  }

  queryBottlesForInventorySubBottle(taskId: string): Observable<ModelBottleSimple[]> {
    return this.http.post<ModelBottleSimple[]>(
      '/api/bottle/queryBottlesForInventorySubBottle',
      taskId,
      {
        responseType: 'json',
      },
    );
  }

  queryAllStorageIn(): Observable<ModelBottleSimple[]> {
    return this.http.post<ModelBottleSimple[]>('/api/bottle/queryAllStorageIn', {
      responseType: 'json',
    });
  }

  queryAllForAcclimatizationIn(): Observable<ModelBottleSimple[]> {
    return this.http.post<ModelBottleSimple[]>('/api/bottle/queryAllForAcclimatizationIn', {
      responseType: 'json',
    });
  }

  queryById(id: string): Observable<Bottle> {
    return this.http.post<Bottle>('/api/bottle/queryById', id, {
      responseType: 'json',
    });
  }
  queryAllIdStorageIn(): Observable<string[]> {
    return this.http.post<string[]>('/api/bottle/queryAllIdStorageIn', {
      responseType: 'json',
    });
  }
  queryParents(idBottle: string): Observable<Bottle[]> {
    return this.http.post<Bottle[]>('/api/bottle/queryParents', idBottle, {
      responseType: 'json',
    });
  }
  queryChildren(idBottle: string): Observable<Bottle[]> {
    return this.http.post<Bottle[]>('/api/bottle/queryChildren', idBottle, {
      responseType: 'json',
    });
  }
  queryLogs(idBottle: string): Observable<BottleLog[]> {
    return this.http.post<BottleLog[]>('/api/bottle/queryLogs', idBottle, {
      responseType: 'json',
    });
  }
  queryArchives(idBottle: string): Observable<Archive[]> {
    return this.http.post<Archive[]>('/api/bottle/queryArchives', idBottle, {
      responseType: 'json',
    });
  }
  excelBottle(idBottle: string): Observable<HttpResponse<Blob>> {
    return this.http.post<HttpResponse<Blob>>('/api/bottle/excelBottle', idBottle, {
      responseType: 'blob',
      observe: 'response',
    });
  }
}
