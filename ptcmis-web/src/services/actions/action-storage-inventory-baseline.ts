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
import { QueryCondition } from '../../entities/api/QueryCondition';
import { QueryConditionStorageInventoryBaseline } from '../../entities/api/QueryConditionStorageInventoryBaseline';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { IJT_DataStorageInventoryBaseline } from '../datas/storage-inventory-baseline/data';
import { InventoryTransaction } from '../../entities/models/InventoryTransaction';

@Injectable({ providedIn: 'root' })
export class ActionStorageInventoryBaseline {
  private dataStorageInventoryBaseline = inject(IJT_DataStorageInventoryBaseline);

  queryByCondition(
    condition: QueryCondition<QueryConditionStorageInventoryBaseline>,
  ): Observable<QueryResultPaged<InventoryTransaction>> {
    return this.dataStorageInventoryBaseline.queryByCondition(condition);
  }

  save(item: InventoryTransaction): Observable<void> {
    return this.dataStorageInventoryBaseline.save(item);
  }
}
