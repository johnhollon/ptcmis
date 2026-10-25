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

import { Component, EventEmitter, Input, OnDestroy, Output } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { Subject } from 'rxjs';

@Component({
  selector: 'comp-code-storage-out',
  templateUrl: 'code.html',
  styleUrls: ['code.scss'],
  standalone: true,
  imports: [MatIconButton, MatIcon, MatTooltip],
})
export class CompCode implements OnDestroy {
  @Input() id = '';
  @Input() plantCount = 0;
  @Input() status = '';
  @Input() assignTitle = '核对接收';
  @Input() assignIconText = 'login';
  @Input() revertTitle = '取消接收';
  @Input() revertIconText = 'logout';
  @Input() canOpp = true;
  @Input() updateVersion = 0;
  @Output() onNormal = new EventEmitter<string>();
  @Output() onChecked = new EventEmitter<string>();

  private dispose$ = new Subject<void>();

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
