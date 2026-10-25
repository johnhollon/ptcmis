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
import { IJT_DataPassport } from '../datas/passport/data';

@Injectable({ providedIn: 'root' })
export class ActionPassport {
  private dataPassport = inject(IJT_DataPassport);
  get currentToken(): string {
    return this.dataPassport.currentToken;
  }
  get currentUserId(): string {
    return this.dataPassport.currentUserId;
  }
  get currentUserAccount(): string {
    return this.dataPassport.currentUserAccount;
  }
  get currentNameOfUser(): string {
    return this.dataPassport.currentNameOfUser;
  }
  get currentName(): string {
    return this.currentNameOfUser;
  }
  get role(): string {
    return this.dataPassport.role;
  }
}
