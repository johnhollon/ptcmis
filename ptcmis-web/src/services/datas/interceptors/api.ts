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

import {HttpErrorResponse, HttpEvent, HttpHandlerFn, HttpRequest} from '@angular/common/http';
import {inject} from '@angular/core';
import {catchError, Observable, throwError} from 'rxjs';
import {IJT_CONSTS_API_ROOT} from '../../../main';
import {IJT_DataPassport} from '../passport/data';


export function apiIntercept(request: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {

  const apiRoot = inject(IJT_CONSTS_API_ROOT);
  const dataPassport = inject(IJT_DataPassport);

  let url = request.url;
  if (!/^https?:\/\//.test(url)) url = apiRoot.replace(/\/+$/, '') + '/' + url.replace(/^\/+/, '');

  return next(request.clone({
    url: url,
    method: request.method,
    headers: request.headers.append('x-ptcmis-token', dataPassport.currentToken),
  })).pipe(catchError((error: HttpErrorResponse) => throwError(error.error)));
}
