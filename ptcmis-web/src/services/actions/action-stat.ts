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
import { QueryConditionStatInventory } from '../../entities/api/QueryConditionStatInventory';
import { ModelStatInventoryTransaction } from '../../entities/api/ModelStatInventoryTransaction';
import { IJT_DataStat } from '../datas/stat/data';
import { ModeStatInventory } from '../../entities/api/ModeStatInventory';
import { QueryConditionStatWorkload } from '../../entities/api/QueryConditionStatWorkload';
import { ModeStatWorkload } from '../../entities/api/ModeStatWorkload';
import { ModelStatWorkloadTransaction } from '../../entities/api/ModelStatWorkloadTransaction';
import { HttpResponse } from '@angular/common/http';
import { ModeStatInventorySimple } from '../../entities/api/ModeStatInventorySimple';

@Injectable({ providedIn: 'root' })
export class ActionStat {
  private dataStat = inject(IJT_DataStat);
  statInventorySimple(condition: QueryConditionStatInventory): Observable<ModeStatInventorySimple> {
    return this.dataStat.statInventorySimple(condition);
  }
  statInventory(condition: QueryConditionStatInventory): Observable<ModeStatInventory[]> {
    return this.dataStat.statInventory(condition);
  }
  statInventoryTransaction(
    condition: QueryConditionStatInventory,
  ): Observable<ModelStatInventoryTransaction> {
    return this.dataStat.statInventoryTransaction(condition);
  }
  statWorkload(condition: QueryConditionStatWorkload): Observable<ModeStatWorkload[]> {
    return this.dataStat.statWorkload(condition);
  }
  statWorkloadTransaction(
    condition: QueryConditionStatWorkload,
  ): Observable<ModelStatWorkloadTransaction> {
    return this.dataStat.statWorkloadTransaction(condition);
  }
  excelStatInventorySimple(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>>{
    return this.dataStat.excelStatInventorySimple(condition);
  }
  excelStatInventory(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>> {
    return this.dataStat.excelStatInventory(condition);
  }
  excelStatInventoryAlert(condition: QueryConditionStatInventory): Observable<HttpResponse<Blob>> {
    return this.dataStat.excelStatInventoryAlert(condition);
  }
  excelStatInventoryTransaction(
    condition: QueryConditionStatInventory,
  ): Observable<HttpResponse<Blob>> {
    return this.dataStat.excelStatInventoryTransaction(condition);
  }
  excelStatWorkload(condition: QueryConditionStatWorkload): Observable<HttpResponse<Blob>> {
    return this.dataStat.excelStatWorkload(condition);
  }
  excelStatWorkloadTransaction(
    condition: QueryConditionStatWorkload,
  ): Observable<HttpResponse<Blob>> {
    return this.dataStat.excelStatWorkloadTransaction(condition);
  }
}
