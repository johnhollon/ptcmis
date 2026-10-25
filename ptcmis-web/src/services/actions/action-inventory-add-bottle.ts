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
import { QueryConditionInventoryAddBottle } from '../../entities/api/QueryConditionInventoryAddBottle';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { InventoryAddBottle } from '../../entities/models/InventoryAddBottle';
import { InventoryAddBottleDetail } from '../../entities/models/InventoryAddBottleDetail';
import { ModelPrintBottleCodeForComplement } from '../../entities/api/ModelPrintBottleCodeForComplement';
import { ModelInventoryAddBottleProcess } from '../../entities/api/ModelInventoryAddBottleProcess';
import { Archive } from '../../entities/models/Archive';
import { ModelPrintBottleCodeForAddBottles } from '../../entities/api/ModelPrintBottleCodeForAddBottles';
import { IJT_DataInventoryAddBottle } from '../datas/inventory-add-bottle/data';

@Injectable({
  providedIn: 'root',
})
export class ActionInventoryAddBottle {
  private dataInventoryAddBottle = inject(IJT_DataInventoryAddBottle);
  queryByCondition(
    condition: QueryCondition<QueryConditionInventoryAddBottle>,
  ): Observable<QueryResultPaged<InventoryAddBottle>> {
    return this.dataInventoryAddBottle.queryByCondition(condition);
  }
  queryOrCreateCodePrefix(taskId: string): Observable<string> {
    return this.dataInventoryAddBottle.queryOrCreateCodePrefix(taskId);
  }
  queryAllCodesInTask(taskId: string): Observable<InventoryAddBottleDetail[]> {
    return this.dataInventoryAddBottle.queryAllCodesInTask(taskId);
  }
  save(model: InventoryAddBottle): Observable<void> {
    return this.dataInventoryAddBottle.save(model);
  }
  delete(taskId: string): Observable<void> {
    return this.dataInventoryAddBottle.delete(taskId);
  }
  printCode(
    model: ModelPrintBottleCodeForComplement,
  ): Observable<ModelPrintBottleCodeForAddBottles> {
    return this.dataInventoryAddBottle.printCode(model);
  }
  process(model: ModelInventoryAddBottleProcess): Observable<void> {
    return this.dataInventoryAddBottle.process(model);
  }
  done(taskId: string): Observable<void> {
    return this.dataInventoryAddBottle.done(taskId);
  }
  uploadArchives(taskId: string, files: File[]): Observable<Archive[]> {
    return this.dataInventoryAddBottle.uploadArchives(taskId, files);
  }
  queryArchives(taskId: string): Observable<Archive[]> {
    return this.dataInventoryAddBottle.queryArchives(taskId);
  }
}
