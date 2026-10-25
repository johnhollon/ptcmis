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

import {Component, effect, EventEmitter, Input, Output, signal, ViewChild} from '@angular/core';
import {MatDrawer, MatDrawerContainer} from '@angular/material/sidenav';
import {ComponentLoading5dots} from '../../00400-loadings/component-loading-5dots';

@Component({
  selector: 'layout-page-function-simple',
  standalone: true,
  imports: [
    MatDrawerContainer,
    MatDrawer,
    ComponentLoading5dots
  ],
  templateUrl: './layout.html',
  styleUrl: './layout.scss'
})
export class LayoutPageFunctionSimple {
  @Input() loadingMessage: string = '';
  @Input() set loading(loading: boolean) {
    if (loading !== this.loadingSig()) this.loadingSig.set(loading);
  }
  @Output() onDrawerClosed = new EventEmitter();
  @Output() onDrawerOpened = new EventEmitter();
  protected loadingSig = signal<boolean>(false);

  protected readonly drawerStatusSig = signal<boolean>(false);

  constructor() {
    effect(() => {
      const drawerStatus = this.drawerStatusSig();
      if (drawerStatus === true) {
        this.onDrawerOpened.emit();
      } else {
        this.onDrawerClosed.emit();
      }
    });
  }

  @ViewChild('drawer', {read: MatDrawer, static: true})
  private _drawer!: MatDrawer;

  get drawer(): MatDrawer {
    return this._drawer;
  }
  toggleDrawer() {
    return this._drawer.toggle();
  }
  openDrawer() {
    return this._drawer.open();
  }
  closeDrawer() {
    return this._drawer.close();
  }
}
