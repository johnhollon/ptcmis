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

import { OverlayModule } from '@angular/cdk/overlay';
import { PortalModule } from '@angular/cdk/portal';
import { AfterViewInit, Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { finalize, Subject } from 'rxjs';
import { LayoutPageFunctionSimpleTitle } from '../../components/00050-layouts/00200-layout-page-function-simple/components/title/component';
import { LayoutPageFunctionSimple } from '../../components/00050-layouts/00200-layout-page-function-simple/layout';
import { LayoutDrawerOppSimple } from '../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { APIConst } from '../../entities/api/const-api';
import { takeUntil } from 'rxjs/operators';
import { ServiceShowMessage } from '../../services/ui/service-show-error-message.service';
import { MatListOption, MatSelectionList } from '@angular/material/list';
import { ActionPrinter } from '../../services/actions/action-printer';

@Component({
  selector: 'screen-print-settings',
  standalone: true,
  templateUrl: 'screen.html',
  imports: [
    MatTableModule,
    OverlayModule,
    PortalModule,
    ReactiveFormsModule,
    LayoutPageFunctionSimple,
    LayoutPageFunctionSimpleTitle,
    LayoutDrawerOppSimple,
    MatSelectionList,
    MatListOption,
  ],
})
export class ScreenPrintSettings implements OnInit, OnDestroy, AfterViewInit {
  private readonly dispose$ = new Subject<void>();
  protected serviceShowMessage = inject(ServiceShowMessage);
  protected actionPrinter = inject(ActionPrinter);

  // *************************************************
  // Loading
  // *************************************************
  protected readonly loadingSig = signal<boolean>(false);
  protected readonly loadingMessageSig = signal<string>('');

  protected printersSig = signal<{ name: string; selected: boolean }[]>([]);

  constructor() {}

  ngOnInit(): void {
    this.loadingMessageSig.set('正在检索打印设备...');
    this.loadingSig.set(true);
    this.actionPrinter
      .queryAllPrinters()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        next: (data) => {
          this.printersSig.set(data);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  ngAfterViewInit(): void {}

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  requestSetPrinter(name: string) {
    this.loadingMessageSig.set('正在设置打印设备...');
    this.loadingSig.set(true);
    this.actionPrinter
      .selectPrinter(name)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        next: (data) => {
          this.printersSig.set(data);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected readonly APIConst = APIConst;
}
