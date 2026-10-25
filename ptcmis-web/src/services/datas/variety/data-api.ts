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

import {HttpClient} from '@angular/common/http';
import {inject} from '@angular/core';
import {map, Observable, switchMap, tap} from 'rxjs';
import {QueryCondition} from '../../../entities/api/QueryCondition';
import {QueryResultPaged} from '../../../entities/api/QueryResultPaged';
import {Variety} from '../../../entities/models/Variety';
import {DataVariety} from './data';

// 通行证。
export class DataVarietyApi implements DataVariety {
  private http = inject(HttpClient);

  private _varietyEnabledList: Variety[] = [];
  get varietyEnabledList(): Variety[] {
    return this._varietyEnabledList;
  }

  queryByCondition(condition: QueryCondition<string>): Observable<QueryResultPaged<Variety>> {
    return this.http.post<QueryResultPaged<Variety>>(
      '/api/variety/queryByCondition',
      condition,
      {responseType: 'json'}
    );
  }
  queryAllEnabled(): Observable<Variety[]> {
    return this.http.post<Variety[]>(
      '/api/variety/queryAllEnabled',
      null,
      {responseType: 'json'}
    ).pipe(
      tap((result) => this._varietyEnabledList = result ?? []),
    );
  }
  save(item: Variety): Observable<void> {
    return this.http.post<void>(
      '/api/variety/save',
      item,
      {responseType: 'text'}
    ).pipe(
      switchMap(_ => this.queryAllEnabled()),
      map(_ => undefined),
    );
  }
  toggleEnabled(name: string): Observable<void> {
    return this.http.post<void>(
      '/api/variety/toggleEnabled',
      name,
      {responseType: 'text'}
    ).pipe(
      switchMap(_ => this.queryAllEnabled()),
      map(_ => undefined),
    );
  }
}
