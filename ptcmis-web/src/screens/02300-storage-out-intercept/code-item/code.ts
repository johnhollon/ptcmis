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

import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  signal,
  SimpleChanges
} from '@angular/core';
import { Subject } from 'rxjs';
import { MatTooltip } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';

export interface BottleSimple {
  id: string;
  plants: number;
  status: '' | 'discard';
  reason: string;
}

@Component({
  selector: 'comp-code-item-with-opp-intercept',
  templateUrl: 'code.html',
  styleUrls: ['code.scss'],
  standalone: true,
  imports: [MatTooltip, MatIconButton, MatIcon, MatProgressSpinnerModule],
})
export class CompCodeItemWithOpp implements OnInit, OnDestroy, OnChanges {
  @Input() editable = true;
  @Input() showReason = true;
  @Input() model: BottleSimple = {
    id: '',
    plants: 0,
    status: '',
    reason: '',
  };
  @Input() discarded = false;
  @Output() onRequestUpdateToDiscard = new EventEmitter<string>();
  @Output() onRequestUpdateToNormal = new EventEmitter<string>();
  @Output() onRequestUpdateDiscardReason = new EventEmitter<BottleSimple>();
  protected updateVersionSig = signal(0);

  private dispose$ = new Subject<void>();

  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  ngOnChanges(changes: SimpleChanges): void {
    const { model } = changes;
    if (model) {
      this.updateVersionSig.update((v) => v + 1);
    }
  }
}
