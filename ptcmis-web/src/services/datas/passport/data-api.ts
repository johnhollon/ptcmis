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
import { Observable, tap } from 'rxjs';
import { ModelLogin } from '../../../entities/api/ModelLogin';
import { DataPassport } from './data';

// 通行证。
export class DataPassportApi implements DataPassport {
  private http = inject(HttpClient);
  #currentToken: string = '';
  #currentUserId: string = '';
  #currentUserAccount: string = '';
  #currentNameOfUser: string = '';
  #role: string = '';
  get currentToken(): string {
    return this.#currentToken;
  }
  get currentUserId(): string {
    // // TODO 开发调试取消权限认证
    // return this.#currentUserId || '0';
    return this.#currentUserId;
  }
  get currentUserAccount(): string {
    // TODO 开发调试取消权限认证
    // return this.#currentUserAccount || 'admin';
    return this.#currentUserAccount;
  }
  get currentNameOfUser(): string {
    // TODO 开发调试取消权限认证
    // return this.#currentNameOfUser || '超级管理员';
    return this.#currentNameOfUser;
  }
  get role(): string {
    // TODO 开发调试取消权限认证
    // return this.#role || 'admin';
    return this.#role;
  }
  login(
    modelLogin: ModelLogin,
  ): Observable<{ token: string; userId: string; name: string; username: string; role: string }> {
    return this.http
      .post<{ token: string; userId: string; name: string; username: string; role: string }>(
        '/api/passport/login',
        modelLogin,
        {
          responseType: 'json',
        },
      )
      .pipe(
        tap((res) => {
          this.#currentToken = res?.token ?? '';
          this.#currentUserId = res?.userId ?? '';
          this.#currentUserAccount = res?.username ?? '';
          this.#currentNameOfUser = res?.name ?? '';
          this.#role = res?.role ?? '';
        }),
      );
  }
}
