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

// data-api.ts
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { QueryConditionInventoryAdjustment } from '../../../entities/api/QueryConditionInventoryAdjustment';
import { InventoryAdjustment } from '../../../entities/models/InventoryAdjustment';
import { DataInventoryAdjustment } from './data';
import { InventoryAdjustmentLog } from '../../../entities/models/InventoryAdjustmentLog';

export class DataInventoryAdjustmentApi implements DataInventoryAdjustment {
  private http = inject(HttpClient);

  queryByCondition(
    condition: QueryCondition<QueryConditionInventoryAdjustment>,
  ): Observable<QueryResultPaged<InventoryAdjustment>> {
    return this.http.post<QueryResultPaged<InventoryAdjustment>>(
      '/api/inventory-adjustment/queryByCondition',
      condition,
      { responseType: 'json' },
    );
  }
  queryAllLogs(idAdjustment: string): Observable<InventoryAdjustmentLog[]> {
    return this.http.post<InventoryAdjustmentLog[]>(
      '/api/inventory-adjustment/queryAllLogs',
      idAdjustment,
      { responseType: 'json' },
    );
  }
  save(model: InventoryAdjustment): Observable<void> {
    return this.http.post<void>('/api/inventory-adjustment/save', model, { responseType: 'text' });
  }
  delete(taskId: string): Observable<void> {
    return this.http.post<void>('/api/inventory-adjustment/delete', taskId, {
      responseType: 'text',
    });
  }
  done(model: InventoryAdjustment): Observable<void> {
    return this.http.post<void>('/api/inventory-adjustment/done', model, {
      responseType: 'text',
    });
  }
}
