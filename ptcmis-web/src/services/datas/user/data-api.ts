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
import { map, Observable, switchMap, tap } from 'rxjs';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionUser } from '../../../entities/api/QueryConditionUser';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { User } from '../../../entities/models/User';
import { DataUser } from './data';

// 员工（用户）信息。
export class DataUserApi implements DataUser {
  private http = inject(HttpClient);

  private _userEnabledList: User[] = [];
  get userEnabledList(): User[] {
    return this._userEnabledList;
  }

  queryByIdByAdmin(id: string): Observable<User> {
    return this.http.post<User>('/api/user/queryByIdByAdmin', id, { responseType: 'json' });
  }

  queryByCondition(
    condition: QueryCondition<QueryConditionUser>,
  ): Observable<QueryResultPaged<User>> {
    return this.http.post<QueryResultPaged<User>>('/api/user/queryByCondition', condition, {
      responseType: 'json',
    });
  }

  queryAllEnabled(): Observable<User[]> {
    return this.http
      .post<User[]>('/api/user/queryAllEnabled', '', { responseType: 'json' })
      .pipe(tap((result) => (this._userEnabledList = result ?? [])));
  }

  saveByAdmin(user: User): Observable<void> {
    return this.http.post<void>('/api/user/saveByAdmin', user, { responseType: 'text' }).pipe(
      switchMap((_) => this.queryAllEnabled()),
      map((_) => undefined),
    );
  }

  toggleEnabled(id: string): Observable<void> {
    return this.http.post<void>('/api/user/toggleEnabled', id, { responseType: 'text' }).pipe(
      switchMap((_) => this.queryAllEnabled()),
      map((_) => undefined),
    );
  }
  updatePassword(model: { new: string; old: string }): Observable<void> {
    return this.http.post<void>('/api/user/updatePassword', model, { responseType: 'text' });
  }
}
