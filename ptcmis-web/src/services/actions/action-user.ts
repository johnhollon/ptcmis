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
import { QueryConditionUser } from '../../entities/api/QueryConditionUser';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { User } from '../../entities/models/User';
import { IJT_DataUser } from '../datas/user/data';

@Injectable({ providedIn: 'root' })
export class ActionUser {
  private dataUser = inject(IJT_DataUser);

  get userEnabledList(): User[] {
    return this.dataUser.userEnabledList;
  }

  queryByIdByAdmin(id: string): Observable<User> {
    return this.dataUser.queryByIdByAdmin(id);
  }

  queryByCondition(
    condition: QueryCondition<QueryConditionUser>,
  ): Observable<QueryResultPaged<User>> {
    return this.dataUser.queryByCondition(condition);
  }

  queryAllEnabled(): Observable<User[]> {
    return this.dataUser.queryAllEnabled();
  }

  saveByAdmin(user: User): Observable<void> {
    return this.dataUser.saveByAdmin(user);
  }

  toggleEnabled(id: string): Observable<void> {
    return this.dataUser.toggleEnabled(id);
  }
  updatePassword(model: { new: string; old: string }): Observable<void> {
    return this.dataUser.updatePassword(model);
  }
}
