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

import { InjectionToken } from '@angular/core';
import { Observable } from 'rxjs';
import { ModelBottleSimple } from '../../../entities/api/ModelBottleSimple';
import { Bottle } from '../../../entities/models/Bottle';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { QueryConditionBottle } from '../../../entities/api/QueryConditionBottle';
import { ModelBottleChangeParent } from '../../../entities/api/ModelBottleChangeParent';
import { ModelBottleChangePlants } from '../../../entities/api/ModelBottleChangePlants';
import { Archive } from '../../../entities/models/Archive';
import { BottleLog } from '../../../entities/models/BottleLog';
import { QueryConditionStatWorkload } from '../../../entities/api/QueryConditionStatWorkload';
import { HttpResponse } from '@angular/common/http';

export const IJT_DataBottle = new InjectionToken<DataBottle>('DataBottle');

export interface DataBottle {
  queryByCondition(
    condition: QueryCondition<QueryConditionBottle>,
  ): Observable<QueryResultPaged<Bottle>>;
  queryAllByCondition(condition: QueryConditionBottle): Observable<ModelBottleSimple[]>;
  changeParentBottle(model: ModelBottleChangeParent): Observable<void>;
  changePlantCount(model: ModelBottleChangePlants): Observable<void>;
  queryAllEnabledIdsForTaskParents(taskId: string): Observable<ModelBottleSimple[]>;
  queryAllEnabledIdsForCodeComplementParents(taskId: string): Observable<ModelBottleSimple[]>;
  queryAllEnabledIdsForInventoryAddBottle(taskId: string): Observable<ModelBottleSimple[]>;
  queryAllEnabledParentIdsForChangeParent(bottleId: string): Observable<ModelBottleSimple[]>;
  queryBottlesForInventorySubBottle(taskId: string): Observable<ModelBottleSimple[]>;
  queryAllStorageIn(): Observable<ModelBottleSimple[]>;
  queryAllForAcclimatizationIn(): Observable<ModelBottleSimple[]>;
  queryById(id: string): Observable<Bottle>;
  queryAllIdStorageIn(): Observable<string[]>;
  queryParents(idBottle: string): Observable<Bottle[]>;
  queryChildren(idBottle: string): Observable<Bottle[]>;
  queryLogs(idBottle: string): Observable<BottleLog[]>;
  queryArchives(idBottle: string): Observable<Archive[]>;
  excelBottle(idBottle: string): Observable<HttpResponse<Blob>>;
}
