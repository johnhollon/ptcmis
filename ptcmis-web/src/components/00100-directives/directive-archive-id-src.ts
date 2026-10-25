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

import { Directive, ElementRef, inject, Input, OnDestroy, OnInit } from '@angular/core';
import { finalize, of, Subject, switchMap } from 'rxjs';
import { ActionArchive } from '../../services/actions/action-archive';
import { takeUntil } from 'rxjs/operators';

@Directive({
  selector: '[archive-id-src]',
  standalone: true,
})
export class DirectiveArchiveIdSrc implements OnDestroy {
  @Input('archive-id-src') set archiveId(archiveId: string | null | undefined) {
    this.id$.next(archiveId);
  }

  private actionArchive = inject(ActionArchive);
  constructor(private readonly el: ElementRef<HTMLImageElement>) {
    this.id$
      .pipe(
        switchMap((archiveId) => {
          if (!archiveId) {
            this.el.nativeElement.src = '';
            return of(undefined);
          }
          return this.actionArchive.download(archiveId).pipe(
            takeUntil(this.dispose$),
            finalize(() => {}),
          );
        }),
        takeUntil(this.dispose$),
      )
      .subscribe({
        next: (blob) => {
          if (!blob) this.el.nativeElement.src = '';
          else this.el.nativeElement.src = URL.createObjectURL(blob);
        },
        error: () => {
          this.el.nativeElement.src = '';
        },
      });
  }

  private dispose$ = new Subject<void>();
  private id$ = new Subject<string | null | undefined>();

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.id$.complete();
  }
}
