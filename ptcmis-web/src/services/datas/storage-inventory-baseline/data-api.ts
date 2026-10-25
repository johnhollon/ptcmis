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
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionStorageInventoryBaseline } from '../../../entities/api/QueryConditionStorageInventoryBaseline';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { DataStorageInventoryBaseline } from './data';
import { InventoryTransaction } from '../../../entities/models/InventoryTransaction';

export class DataStorageInventoryBaselineApi implements DataStorageInventoryBaseline {
  private http = inject(HttpClient);

  queryByCondition(
    condition: QueryCondition<QueryConditionStorageInventoryBaseline>,
  ): Observable<QueryResultPaged<InventoryTransaction>> {
    return this.http.post<QueryResultPaged<InventoryTransaction>>(
      '/api/storage-inventory-baseline/queryByCondition',
      condition,
      { responseType: 'json' },
    );
  }
  save(item: InventoryTransaction): Observable<void> {
    return this.http.post<void>('/api/storage-inventory-baseline/save', item, {
      responseType: 'text',
    });
  }
}
