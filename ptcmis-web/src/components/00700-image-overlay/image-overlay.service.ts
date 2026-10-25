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

import { Overlay, OverlayRef } from '@angular/cdk/overlay';
import { ComponentPortal } from '@angular/cdk/portal';
import { Injectable, Injector, OnDestroy } from '@angular/core';
import { SafeResourceUrl } from '@angular/platform-browser';
import { Subject } from 'rxjs';
import { debounceTime, takeUntil } from 'rxjs/operators';
import { ImageOverlayContainer } from './image-overlay.container';
import { ImageOverlayOption } from './image-overlay.option';

/**
 * 全局图片缩放服务
 */
@Injectable({
  providedIn: 'root'
})
export class ImageOverlayService implements OnDestroy {

  /**  图片浮层   */
  private overlayRef?: OverlayRef | null;
  /** 请求显示浮层主题 */
  private show$ = new Subject<ImageOverlayOption>();

  /** 按键事件 */keyDown$ = new Subject<string>();
  private detachments$ = new Subject<any>();
  private dispose$ = new Subject<void>();
  private imageOverlay?: ImageOverlayContainer;

  constructor(
    public overlay: Overlay,
    private injector: Injector,
  ) {
    this.show$.pipe(
      takeUntil(this.dispose$),
      debounceTime(200),
    ).subscribe(value => this.show(value));
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  public changeImageSrc(src: string | SafeResourceUrl) {
    if (!!this.imageOverlay) {
      this.imageOverlay.imageSrc.set(src);
    }
  }

  /**
   * 打开图片浮层。
   * @param options 图片浮层选项。
   */
  public showOverlay(options: ImageOverlayOption) {
    this.show$.next(options);
  }

  /**
   * 销毁图片浮层。
   */
  public disposeOverlay() {
    this.detachments$.next(1);
    this.overlayRef?.dispose();
  }

  private show(options: ImageOverlayOption) {
    if (!!this.overlayRef) {
      this.disposeOverlay();
    }
    const overlayConfig = { ...(options?.overlayConfig || {}) };
    overlayConfig.panelClass = (overlayConfig.panelClass ? `${overlayConfig.panelClass} ` : '') + 'image-overlay-pane';
    overlayConfig.hasBackdrop = overlayConfig.hasBackdrop ?? true;
    overlayConfig.disposeOnNavigation = overlayConfig.disposeOnNavigation ?? true;
    const overlayRef = this.overlay.create(overlayConfig);
    overlayRef.detachments().pipe(
      takeUntil(this.detachments$)
    ).subscribe(_ => this.disposeOverlay());
    const injector = Injector.create({
      parent: this.injector,
      providers: [
        { provide: ImageOverlayOption, useValue: options },
        { provide: OverlayRef, useValue: overlayRef },
      ]
    });
    const portal = new ComponentPortal(ImageOverlayContainer, null, injector);
    this.overlayRef = overlayRef;
    this.imageOverlay = overlayRef.attach(portal).instance;
    if (!!this.imageOverlay) {
      this.imageOverlay.keyEvent$.pipe(
        takeUntil(this.detachments$)
      ).subscribe(key => {
        this.keyDown$.next(key);
      });
    }
  }
}

