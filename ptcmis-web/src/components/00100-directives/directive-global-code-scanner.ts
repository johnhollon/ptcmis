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

import { Directive, EventEmitter, HostListener, inject, OnDestroy, Output } from '@angular/core';
import { Subject } from 'rxjs';
import { ServiceGlobalCodeScanner } from '../../services/ui/service-global-code-scanner';
import { takeUntil } from 'rxjs/operators';

@Directive({
  selector: '[globalCodeScanner]',
  standalone: true,
})
export class DirectiveGlobalCodeScanner implements OnDestroy {
  @Output() onScanResult = new EventEmitter<string>();

  private dispose$ = new Subject<void>();

  private serviceGlobalCodeScanner = inject(ServiceGlobalCodeScanner);

  @HostListener('click')
  onClick(): void {
    this.serviceGlobalCodeScanner
      .start()
      .pipe(takeUntil(this.dispose$))
      .subscribe((v) => this.onScanResult.next(v));
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
