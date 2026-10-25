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

import {ComponentPortal} from '@angular/cdk/portal';
import {inject, Injectable} from '@angular/core';
import {Overlay, OverlayRef} from '@angular/cdk/overlay';
import {ComponentOverlayConfirm} from '../../components/00300-overlays/component-overlay-confirm';

@Injectable({providedIn: 'root'})
export class ServiceConfirmOverlay {
  private readonly overlay = inject(Overlay);
  private confirmOverlayRef: OverlayRef | null = null;

  showConfirmOverlay(): ComponentOverlayConfirm;
  showConfirmOverlay(event?: Event): ComponentOverlayConfirm;
  showConfirmOverlay(event?: Event, model?: unknown): ComponentOverlayConfirm;
  showConfirmOverlay(event?: Event, model?: unknown): ComponentOverlayConfirm {
    this.closeConfirmOverlay();

    let anchorEl: HTMLElement | undefined;
    if (event?.target) {
      anchorEl = event.target as HTMLElement;
    }

    const positionStrategy = anchorEl
      ? this.overlay.position()
        .flexibleConnectedTo(anchorEl)
        .withPositions([
          //首选：弹窗左上角对齐按钮左下角，向下弹出
          {
            originX: 'start',
            originY: 'bottom',
            overlayX: 'start',
            overlayY: 'top',
            offsetY: 6
          },
          //回落1：弹窗左下角对齐按钮左上角，向上弹出（底部空间不足）
          {
            originX: 'start',
            originY: 'top',
            overlayX: 'start',
            overlayY: 'bottom',
            offsetY: -6
          },
          //回落2：右对齐向下（右侧空间不够回弹方案）
          {
            originX: 'end',
            originY: 'bottom',
            overlayX: 'end',
            overlayY: 'top',
            offsetY: 6
          },
          //回落3：右对齐向上
          {
            originX: 'end',
            originY: 'top',
            overlayX: 'end',
            overlayY: 'bottom',
            offsetY: -6
          }
        ])
      : this.overlay.position()
        .global()
        .centerHorizontally()
        .centerVertically();

    this.confirmOverlayRef = this.overlay.create({
      positionStrategy,
      scrollStrategy: this.overlay.scrollStrategies.close(),
      hasBackdrop: true,
      backdropClass: 'cdk-overlay-transparent-backdrop'
    });

    this.confirmOverlayRef.backdropClick().subscribe(() => {
      this.closeConfirmOverlay();
    });

    const portal = new ComponentPortal(ComponentOverlayConfirm);
    const compRef = this.confirmOverlayRef.attach(portal);
    return compRef.instance;
  }

  closeConfirmOverlay(): void {
    if (this.confirmOverlayRef) {
      this.confirmOverlayRef.dispose();
      this.confirmOverlayRef = null;
    }
  }
}
