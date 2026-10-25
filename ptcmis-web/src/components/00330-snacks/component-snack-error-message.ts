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

import {Component, EventEmitter, inject, Input, Output} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MAT_SNACK_BAR_DATA, MatSnackBarAction, MatSnackBarActions, MatSnackBarLabel, MatSnackBarRef} from '@angular/material/snack-bar';

@Component({
  selector: 'app-delete-confirm-overlay',
  standalone: true,
  imports: [MatButtonModule, MatSnackBarLabel, MatSnackBarActions, MatSnackBarAction, MatIcon],
  template: `
    
    <span matSnackBarLabel><mat-icon>error</mat-icon> {{ message }}</span>
    <span matSnackBarActions>
      <button matButton matSnackBarAction (click)="snackBarRef.dismissWithAction()">关闭</button>
    </span>
  `,
  styles: [`
    :host {
      display: flex;
      align-items: center;

      .mat-mdc-snack-bar-label {
        display: flex;
        align-items: center;
        gap: var(--mat-sys-corner-small);
      }
    }
  `],
})
export class ComponentSnackErrorMessage {
  protected message = inject<string>(MAT_SNACK_BAR_DATA);
  snackBarRef = inject(MatSnackBarRef);
}
