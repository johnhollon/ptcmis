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
  inject,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  signal,
  SimpleChanges,
} from '@angular/core';
import { finalize, Subject } from 'rxjs';
import { MatTooltip } from '@angular/material/tooltip';
import { ActionBottle } from '../../../services/actions/action-bottle';
import { takeUntil } from 'rxjs/operators';
import { Bottle } from '../../../entities/models/Bottle';
import { MatProgressSpinner, MatProgressSpinnerModule } from '@angular/material/progress-spinner';
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
  imports: [MatTooltip, MatProgressSpinner, MatIconButton, MatIcon, MatProgressSpinnerModule],
})
export class CompCodeItemWithOpp implements OnInit, OnDestroy, OnChanges {
  @Input() editable = true;
  @Input() model: BottleSimple = {
    id: '',
    plants: 0,
    status: '',
    reason: '',
  };
  @Output() onUnExists = new EventEmitter<string>();
  @Output() onLoaded = new EventEmitter<BottleSimple>();
  @Output() onRequestUpdateToDiscard = new EventEmitter<string>();
  @Output() onRequestUpdateToNormal = new EventEmitter<string>();
  @Output() onRequestUpdateDiscardReason = new EventEmitter<BottleSimple>();
  protected loadingSig = signal<boolean>(false);
  protected updateVersionSig = signal(0);

  private actionBottle = inject(ActionBottle);

  private dispose$ = new Subject<void>();

  ngOnInit(): void {
    if (this.model !== null && this.model.status === 'discard' && !this.model.plants) {
      this.loadingSig.set(true);
      this.actionBottle
        .queryById(this.model.id)
        .pipe(
          takeUntil(this.dispose$),
          finalize(() => this.loadingSig.set(false)),
        )
        .subscribe({
          next: (discard: Bottle) => {
            if (!discard || !discard.id || discard.currentStorageStatus !== 'in') {
              this.onUnExists.emit(this.model.id);
              return;
            }
            this.model = {
              id: discard.id,
              plants: discard.plantCount,
              status: 'discard',
              reason: this.model.reason,
            };
            this.onLoaded.emit(this.model);
            this.updateVersionSig.update((v) => v + 1);
          },
        });
    }
  }

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
