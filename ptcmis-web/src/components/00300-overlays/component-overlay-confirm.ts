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

import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-delete-confirm-overlay',
  standalone: true,
  imports: [MatButtonModule],
  template: `
    <div class="confirm-popup">
      <div class="tip-text">{{ message }}</div>
      <div class="btn-row">
        <button mat-button (click)="cancelled.emit($event)">{{ cancelText }}</button>
        <button mat-raised-button color="warn" (click)="confirmed.emit($event)">{{ confirmText }}</button>
      </div>
    </div>
  `,
  styles: [`
    .confirm-popup {
      background: var(--mat-sys-surface);
      padding: 16px;
      min-width: 200px;
      border-radius: 4px;
      box-shadow: var(--mat-sys-level1);

      .tip-text {
        margin-bottom: 14px;
      }

      .btn-row {
        display: flex;
        gap: 10px;
        justify-content: flex-end;
      }
    }
  `]
})
export class ComponentOverlayConfirm {
  @Input() message = '';
  @Input() confirmText = '确定';
  @Input() cancelText = '取消';
  @Output() confirmed = new EventEmitter<Event>();
  @Output() cancelled = new EventEmitter<Event>();
}
