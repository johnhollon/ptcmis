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
import { QueryConditionWorkloadBaseline } from '../../../entities/api/QueryConditionWorkloadBaseline';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { DataWorkloadBaseline } from './data';
import { WorkloadTransaction } from '../../../entities/models/WorkloadTransaction';

export class DataWorkloadBaselineApi implements DataWorkloadBaseline {
  private http = inject(HttpClient);

  queryByCondition(
    condition: QueryCondition<QueryConditionWorkloadBaseline>,
  ): Observable<QueryResultPaged<WorkloadTransaction>> {
    return this.http.post<QueryResultPaged<WorkloadTransaction>>(
      '/api/workload-baseline/queryByCondition',
      condition,
      { responseType: 'json' },
    );
  }
  save(item: WorkloadTransaction): Observable<void> {
    return this.http.post<void>('/api/workload-baseline/save', item, {
      responseType: 'text',
    });
  }
}
