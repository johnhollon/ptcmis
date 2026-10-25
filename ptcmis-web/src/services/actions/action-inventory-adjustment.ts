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
import { QueryConditionInventoryAdjustment } from '../../entities/api/QueryConditionInventoryAdjustment';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { InventoryAdjustment } from '../../entities/models/InventoryAdjustment';
import { IJT_DataInventoryAdjustment } from '../datas/inventory-adjustment/data';
import { InventoryAdjustmentLog } from '../../entities/models/InventoryAdjustmentLog';

@Injectable({
  providedIn: 'root',
})
export class ActionInventoryAdjustment {
  private dataInventoryAdjustment = inject(IJT_DataInventoryAdjustment);
  queryByCondition(
    condition: QueryCondition<QueryConditionInventoryAdjustment>,
  ): Observable<QueryResultPaged<InventoryAdjustment>> {
    return this.dataInventoryAdjustment.queryByCondition(condition);
  }
  queryAllLogs(idAdjustment: string): Observable<InventoryAdjustmentLog[]> {
    return this.dataInventoryAdjustment.queryAllLogs(idAdjustment);
  }
  save(model: InventoryAdjustment): Observable<void> {
    return this.dataInventoryAdjustment.save(model);
  }
  delete(taskId: string): Observable<void> {
    return this.dataInventoryAdjustment.delete(taskId);
  }
  done(model: InventoryAdjustment): Observable<void> {
    return this.dataInventoryAdjustment.done(model);
  }
}
