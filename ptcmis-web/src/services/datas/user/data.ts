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
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionUser } from '../../../entities/api/QueryConditionUser';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { User } from '../../../entities/models/User';

export const IJT_DataUser = new InjectionToken<DataUser>('DataUser');

// 员工（用户）信息。
export interface DataUser {
  readonly userEnabledList: User[];

  queryByIdByAdmin(id: string): Observable<User>;

  queryByCondition(
    condition: QueryCondition<QueryConditionUser>,
  ): Observable<QueryResultPaged<User>>;

  queryAllEnabled(): Observable<User[]>;

  saveByAdmin(user: User): Observable<void>;

  toggleEnabled(id: string): Observable<void>;
  updatePassword(model: { new: string; old: string }): Observable<void>;
}
