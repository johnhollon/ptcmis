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

import { Observable } from 'rxjs';
import { DataStat } from './data';
import { inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { QueryConditionStatInventory } from '../../../entities/api/QueryConditionStatInventory';
import { ModelStatInventoryTransaction } from '../../../entities/api/ModelStatInventoryTransaction';
import { ModeStatInventory } from '../../../entities/api/ModeStatInventory';
import { QueryConditionStatWorkload } from '../../../entities/api/QueryConditionStatWorkload';
import { ModeStatWorkload } from '../../../entities/api/ModeStatWorkload';
import { ModelStatWorkloadTransaction } from '../../../entities/api/ModelStatWorkloadTransaction';
import { ModeStatInventorySimple } from '../../../entities/api/ModeStatInventorySimple';

export class DataStatApi implements DataStat {
  private http = inject(HttpClient);
  statInventorySimple(condition: QueryConditionStatInventory): Observable<ModeStatInventorySimple> {
    return this.http.post<ModeStatInventorySimple>('/api/stat/statInventorySimple', condition, {
      responseType: 'json',
    });
  }
  statInventory(condition: QueryConditionStatInventory): Observable<ModeStatInventory[]> {
    return this.http.post<ModeStatInventory[]>('/api/stat/statInventory', condition, {
      responseType: 'json',
    });
  }
  statInventoryTransaction(
    condition: QueryConditionStatInventory,
  ): Observable<ModelStatInventoryTransaction> {
    return this.http.post<ModelStatInventoryTransaction>(
      '/api/stat/statInventoryTransaction',
      condition,
      {
        responseType: 'json',
      },
    );
  }
  statWorkload(condition: QueryConditionStatWorkload): Observable<ModeStatWorkload[]> {
    return this.http.post<ModeStatWorkload[]>('/api/stat/statWorkload', condition, {
      responseType: 'json',
    });
  }
  statWorkloadTransaction(
    condition: QueryConditionStatWorkload,
  ): Observable<ModelStatWorkloadTransaction> {
    return this.http.post<ModelStatWorkloadTransaction>(
      '/api/stat/statWorkloadTransaction',
      condition,
      {
        responseType: 'json',
      },
    );
  }
  excelStatInventorySimple(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>> {
    return this.http.post<HttpResponse<Blob>>('/api/stat/excelStatInventorySimple', condition, {
      responseType: 'blob',
      observe: 'response',
    });
  }
  excelStatInventory(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>> {
    return this.http.post<HttpResponse<Blob>>('/api/stat/excelStatInventory', condition, {
      responseType: 'blob',
      observe: 'response',
    });
  }
  excelStatInventoryAlert(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>> {
    return this.http.post<HttpResponse<Blob>>('/api/stat/excelStatInventoryAlert', condition, {
      responseType: 'blob',
      observe: 'response',
    });
  }
  excelStatInventoryTransaction(
    condition: QueryConditionStatInventory,
  ): Observable<HttpResponse<Blob>> {
    return this.http.post<HttpResponse<Blob>>(
      '/api/stat/excelStatInventoryTransaction',
      condition,
      {
        responseType: 'blob',
        observe: 'response',
      },
    );
  }
  excelStatWorkload(condition: QueryConditionStatWorkload): Observable<HttpResponse<Blob>> {
    return this.http.post<HttpResponse<Blob>>('/api/stat/excelStatWorkload', condition, {
      responseType: 'blob',
      observe: 'response',
    });
  }
  excelStatWorkloadTransaction(
    condition: QueryConditionStatWorkload,
  ): Observable<HttpResponse<Blob>> {
    return this.http.post<HttpResponse<Blob>>('/api/stat/excelStatWorkloadTransaction', condition, {
      responseType: 'blob',
      observe: 'response',
    });
  }
}
