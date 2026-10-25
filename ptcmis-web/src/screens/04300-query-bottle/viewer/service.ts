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

import { inject, Injectable, OnDestroy, signal } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { BottleLog } from '../../../entities/models/BottleLog';
import { map, Observable, Subject, switchMap, tap } from 'rxjs';
import { Archive } from '../../../entities/models/Archive';
import { Bottle } from '../../../entities/models/Bottle';
import { ActionBottle } from '../../../services/actions/action-bottle';
import { takeUntil } from 'rxjs/operators';

@Injectable()
export class ServiceQueryBottle implements OnDestroy {
  tableLogsDataSource = new MatTableDataSource<BottleLog>([]);
  #archivesSig = signal<Archive[]>([]);
  archivesSig = this.#archivesSig.asReadonly();
  #bottleSig = signal<Bottle>({} as unknown as Bottle);
  bottleSig = this.#bottleSig.asReadonly();
  #parentsSig = signal<Bottle[]>([]);
  parentsSig = this.#parentsSig.asReadonly();
  #childrenSig = signal<Bottle[]>([]);
  childrenSig = this.#childrenSig.asReadonly();

  private readonly dispose$ = new Subject<void>();

  private readonly actionBottle = inject(ActionBottle);

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  requestQuery(bottle: Bottle): Observable<void> {
    return this.actionBottle.queryLogs(bottle.id).pipe(
      tap((logs) => (this.tableLogsDataSource.data = logs || [])),
      switchMap((_) => this.actionBottle.queryArchives(bottle.id)),
      tap((archives) => this.#archivesSig.set(archives || [])),
      switchMap((_) => this.actionBottle.queryParents(bottle.id)),
      tap((parents) => this.#parentsSig.set(parents || [])),
      switchMap((_) => this.actionBottle.queryChildren(bottle.id)),
      tap((children) => this.#childrenSig.set(children || [])),
      tap((_) => this.#bottleSig.set(bottle)),
      map((_) => undefined),
      takeUntil(this.dispose$),
    ) as Observable<void>;
  }
}
