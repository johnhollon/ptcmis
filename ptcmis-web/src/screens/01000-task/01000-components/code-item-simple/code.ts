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
import { Subject } from 'rxjs';
import { TissueTaskBottle } from '../../../../entities/models/TissueTaskBottle';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
  selector: 'comp-code-item-simple-tissue-task',
  templateUrl: 'code.html',
  styleUrls: ['code.scss'],
  standalone: true,
  imports: [MatTooltip],
})
export class CompCodeItemSimple implements OnDestroy {
  @Input() model: TissueTaskBottle | null = null;

  private dispose$ = new Subject<void>();

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
