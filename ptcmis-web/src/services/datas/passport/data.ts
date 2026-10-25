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
import { ModelLogin } from '../../../entities/api/ModelLogin';

export const IJT_DataPassport = new InjectionToken<DataPassport>('DataPassport');

// 通行证。
export interface DataPassport {
  // 当前Token
  readonly currentToken: string;
  // 当前用户ID
  readonly currentUserId: string;
  // 当前用户姓名
  readonly currentNameOfUser: string;
  // 当前用户名
  readonly currentUserAccount: string;
  // 当前用户角色
  readonly role: string;
  // 登录
  login(
    modelLogin: ModelLogin,
  ): Observable<{ token: string; userId: string; name: string; username: string }>;
}
