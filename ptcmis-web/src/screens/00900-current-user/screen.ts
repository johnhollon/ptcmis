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
import { finalize, Subject, switchMap, tap } from 'rxjs';
import { LayoutPageFunctionSimpleTitle } from '../../components/00050-layouts/00200-layout-page-function-simple/components/title/component';
import { LayoutPageFunctionSimple } from '../../components/00050-layouts/00200-layout-page-function-simple/layout';
import { LayoutDrawerOppSimple } from '../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { APIConst } from '../../entities/api/const-api';
import { ActionPassport } from '../../services/actions/action-passport';
import { MatButton } from '@angular/material/button';
import { PipeRole } from '../../components/00200-pipes/pipe-role';
import { MatDialog } from '@angular/material/dialog';
import { DialogUpdatePassword, DialogUpdatePasswordData } from './dialog-update-password';
import { filter, takeUntil } from 'rxjs/operators';
import { ActionUser } from '../../services/actions/action-user';
import { ServiceShowMessage } from '../../services/ui/service-show-error-message.service';

@Component({
  selector: 'screen-current-user',
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
    MatFormField,
    MatInput,
    MatLabel,
    MatButton,
    PipeRole,
  ],
})
export class ScreenCurrentUser implements OnInit, OnDestroy, AfterViewInit {
  private readonly dispose$ = new Subject<void>();
  protected serviceShowMessage = inject(ServiceShowMessage);
  protected actionPassport = inject(ActionPassport);
  protected actionUser = inject(ActionUser);
  private readonly dialog = inject(MatDialog);

  // *************************************************
  // Loading
  // *************************************************
  protected readonly loadingSig = signal<boolean>(false);
  protected readonly loadingMessageSig = signal<string>('');

  constructor() {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {}

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  requestUpdatePassword() {
    const dialogInputData: DialogUpdatePasswordData = {
      new: '',
      newRepeat: '',
      old: '',
    };
    const dialogRef = this.dialog.open<
      DialogUpdatePassword,
      DialogUpdatePasswordData,
      DialogUpdatePasswordData
    >(DialogUpdatePassword, {
      data: dialogInputData,
      width: '300px',
    });

    dialogRef
      .afterClosed()
      .pipe(
        filter((v) => !!v),
        tap((_) => {
          this.loadingMessageSig.set('正在更改密码...');
          this.loadingSig.set(true);
        }),
        switchMap((v) => this.actionUser.updatePassword(v)),
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        next: (_) => this.serviceShowMessage.showInfo('更改成功'),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  protected readonly APIConst = APIConst;
}
