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
import {MatSnackBar} from '@angular/material/snack-bar';
import {ComponentSnackErrorMessage} from '../../components/00330-snacks/component-snack-error-message';
import {IJT_RepositoryMessages} from '../utils/00020-messages/repository';

const durationMillis = 5000;

@Injectable({providedIn: 'root'})
export class ServiceShowMessage {
  private readonly serviceSnack = inject(MatSnackBar);
  private readonly serviceMessage = inject(IJT_RepositoryMessages);

  showError(error: unknown): void {
    this.serviceSnack.openFromComponent(ComponentSnackErrorMessage, {
      data: this.serviceMessage.getMessages(error) || '发生错误。',
      duration: durationMillis,
      panelClass: 'error'
    });
  }

  showInfo(message: string): void {
    this.serviceSnack.open(message, '关闭', {duration: 2500, panelClass: 'info'});
  }
}
