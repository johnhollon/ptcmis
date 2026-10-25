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
import { IJT_DataBottle } from '../datas/bottle/data';
import { ModelBottleSimple } from '../../entities/api/ModelBottleSimple';
import { Bottle } from '../../entities/models/Bottle';
import { QueryCondition } from '../../entities/api/QueryCondition';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { ModelBottleChangeParent } from '../../entities/api/ModelBottleChangeParent';
import { ModelBottleChangePlants } from '../../entities/api/ModelBottleChangePlants';
import { QueryConditionBottle } from '../../entities/api/QueryConditionBottle';
import { BottleLog } from '../../entities/models/BottleLog';
import { Archive } from '../../entities/models/Archive';
import { HttpResponse } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ActionBottle {
  private dataBottle = inject(IJT_DataBottle);
  queryByCondition(
    condition: QueryCondition<QueryConditionBottle>,
  ): Observable<QueryResultPaged<Bottle>> {
    return this.dataBottle.queryByCondition(condition);
  }
  queryAllByCondition(condition: QueryConditionBottle): Observable<ModelBottleSimple[]> {
    return this.dataBottle.queryAllByCondition(condition);
  }
  changeParentBottle(model: ModelBottleChangeParent): Observable<void> {
    return this.dataBottle.changeParentBottle(model);
  }
  changePlantCount(model: ModelBottleChangePlants): Observable<void> {
    return this.dataBottle.changePlantCount(model);
  }
  queryAllEnabledIdsForTaskParents(taskId: string): Observable<ModelBottleSimple[]> {
    return this.dataBottle.queryAllEnabledIdsForTaskParents(taskId);
  }

  queryAllEnabledIdsForCodeComplementParents(taskId: string): Observable<ModelBottleSimple[]> {
    return this.dataBottle.queryAllEnabledIdsForCodeComplementParents(taskId);
  }

  queryAllEnabledIdsForInventoryAddBottle(taskId: string): Observable<ModelBottleSimple[]> {
    return this.dataBottle.queryAllEnabledIdsForInventoryAddBottle(taskId);
  }

  queryAllEnabledParentIdsForChangeParent(bottleId: string): Observable<ModelBottleSimple[]> {
    return this.dataBottle.queryAllEnabledParentIdsForChangeParent(bottleId);
  }

  queryBottlesForInventorySubBottle(taskId: string): Observable<ModelBottleSimple[]> {
    return this.dataBottle.queryBottlesForInventorySubBottle(taskId);
  }
  queryAllStorageIn(): Observable<ModelBottleSimple[]> {
    return this.dataBottle.queryAllStorageIn();
  }

  queryAllForAcclimatizationIn(): Observable<ModelBottleSimple[]> {
    return this.dataBottle.queryAllForAcclimatizationIn();
  }

  queryById(id: string): Observable<Bottle> {
    return this.dataBottle.queryById(id);
  }
  queryAllIdStorageIn(): Observable<string[]> {
    return this.dataBottle.queryAllIdStorageIn();
  }
  queryParents(idBottle: string): Observable<Bottle[]> {
    return this.dataBottle.queryParents(idBottle);
  }
  queryChildren(idBottle: string): Observable<Bottle[]> {
    return this.dataBottle.queryChildren(idBottle);
  }
  queryLogs(idBottle: string): Observable<BottleLog[]> {
    return this.dataBottle.queryLogs(idBottle);
  }
  queryArchives(idBottle: string): Observable<Archive[]> {
    return this.dataBottle.queryArchives(idBottle);
  }
  excelBottle(idBottle: string): Observable<HttpResponse<Blob>> {
    return this.dataBottle.excelBottle(idBottle);
  }
}
