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

import { computed, inject, Injectable, OnDestroy, signal } from '@angular/core';
import { QueryConditionStatInventory } from '../../entities/api/QueryConditionStatInventory';
import { map, Observable, Subject, tap } from 'rxjs';
import { ActionStat } from '../../services/actions/action-stat';
import { takeUntil } from 'rxjs/operators';
import { ModeStatInventory } from '../../entities/api/ModeStatInventory';

@Injectable()
export class ServiceStatInventoryAlert implements OnDestroy {
  private readonly dispose$ = new Subject<void>();
  private readonly actionStat = inject(ActionStat);

  readonly tableDataSig = computed(() => {
    const data = this.#dataSig();
    const { bottles, plants, generation } = this.alertCountSig();
    return data.filter(
      (d) =>
        (d.bottleEnd <= bottles || d.plantEnd <= plants) &&
        (generation == null || d.generation === generation),
    );
  });

  requestUpdateAlertCount(alertCount: {
    bottles: number;
    plants: number;
    generation: number | null;
  }) {
    this.alertCountSig.set(alertCount);
  }

  requestQuery(condition: QueryConditionStatInventory): Observable<void> {
    return this.actionStat.statInventory(condition).pipe(
      tap((result) => {
        this.#dataSig.set(result);
        this.#expandedNodeIdsSig.set(new Set());
      }),
      map((_) => undefined),
      takeUntil(this.dispose$),
    );
  }

  readonly #expandedNodeIdsSig = signal<Set<string>>(new Set());
  readonly #dataSig = signal<ModeStatInventory[]>([]);
  readonly alertCountSig = signal<{
    bottles: number;
    plants: number;
    generation: number | null;
  }>({
    bottles: 5,
    plants: 50,
    generation: null,
  });

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
