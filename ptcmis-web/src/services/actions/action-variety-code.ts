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

import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {QueryCondition} from '../../entities/api/QueryCondition';
import {QueryResultPaged} from '../../entities/api/QueryResultPaged';
import {VarietyCode} from '../../entities/models/VarietyCode';
import {IJT_DataVarietyCode} from '../datas/variety-code/data';

@Injectable({providedIn: 'root'})
export class ActionVarietyCode {
  private data = inject(IJT_DataVarietyCode);

  get varietyCodeEnabledList(): VarietyCode[] {
    return this.data.varietyCodeEnabledList;
  }

  queryByCondition(condition: QueryCondition<string>): Observable<QueryResultPaged<VarietyCode>> {
    return this.data.queryByCondition(condition);
  }

  queryAllEnabled(): Observable<VarietyCode[]> {
    return this.data.queryAllEnabled();
  }

  save(item: VarietyCode): Observable<void> {
    return this.data.save(item);
  }

  toggleEnabled(name: string): Observable<void> {
    return this.data.toggleEnabled(name);
  }
}
