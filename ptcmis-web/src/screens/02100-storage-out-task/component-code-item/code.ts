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
  selector: 'comp-code',
  templateUrl: 'code.html',
  styleUrls: ['code.scss'],
  standalone: true,
  imports: [MatIconButton, MatIcon, MatTooltip],
})
export class CompCode implements OnDestroy {
  @Input() id = '';
  @Input() colored = true;
  @Input() plantCount = 0;
  @Input() status = '';
  @Input() assignTitle = '使用';
  @Input() assignIconText = 'file_download_done';
  @Input() revertTitle = '还原';
  @Input() revertIconText = 'settings_backup_restore';
  @Input() canEditPlantCount = true;
  @Input() canOpp = true;
  @Input() updateVersion = 0;
  @Output() onNone = new EventEmitter<string>();
  @Output() onUsed = new EventEmitter<string>();

  private dispose$ = new Subject<void>();

  protected editingPlants = false;

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
