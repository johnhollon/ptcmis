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
import { ModelStatInventoryTransaction } from '../../../entities/api/ModelStatInventoryTransaction';
import { QueryConditionStatInventory } from '../../../entities/api/QueryConditionStatInventory';
import { ModeStatInventory } from '../../../entities/api/ModeStatInventory';
import { QueryConditionStatWorkload } from '../../../entities/api/QueryConditionStatWorkload';
import { ModeStatWorkload } from '../../../entities/api/ModeStatWorkload';
import { ModelStatWorkloadTransaction } from '../../../entities/api/ModelStatWorkloadTransaction';
import { HttpResponse } from '@angular/common/http';
import { ModeStatInventorySimple } from '../../../entities/api/ModeStatInventorySimple';

export const IJT_DataStat = new InjectionToken<DataStat>('DataStat');

export interface DataStat {
  statInventorySimple(condition: QueryConditionStatInventory): Observable<ModeStatInventorySimple>;
  statInventory(condition: QueryConditionStatInventory): Observable<ModeStatInventory[]>;
  statInventoryTransaction(
    condition: QueryConditionStatInventory,
  ): Observable<ModelStatInventoryTransaction>;
  statWorkload(condition: QueryConditionStatWorkload): Observable<ModeStatWorkload[]>;
  statWorkloadTransaction(
    condition: QueryConditionStatWorkload,
  ): Observable<ModelStatWorkloadTransaction>;
  excelStatInventorySimple(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>>;
  excelStatInventory(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>>;
  excelStatInventoryAlert(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>>;
  excelStatInventoryTransaction(
    condition: QueryConditionStatInventory,
  ): Observable<HttpResponse<Blob>>;
  excelStatWorkload(condition: QueryConditionStatWorkload): Observable<HttpResponse<Blob>>;
  excelStatWorkloadTransaction(
    condition: QueryConditionStatWorkload,
  ): Observable<HttpResponse<Blob>>;
}
