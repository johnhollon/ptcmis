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

import { CdkDrag } from '@angular/cdk/drag-drop';
import { OverlayRef } from '@angular/cdk/overlay';
import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  inject,
  Input,
  OnDestroy,
  OnInit,
  signal,
  TemplateRef,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { SafeResourceUrl } from '@angular/platform-browser';
import { fromEvent, Subject } from 'rxjs';
import { filter, map, takeUntil, tap } from 'rxjs/operators';
import { ImageOverlayOption } from './image-overlay.option';
import { CdkPortalOutlet, TemplatePortal } from '@angular/cdk/portal';

@Component({
  selector: 'image-overlay-container',
  template: `
    <div #backdrop class="image-overlay-container">
      <div
        #container
        [class.shown]="loadStatus === 1"
        [class.hide]="loadStatus !== 1"
        class="image-overlay"
        style="display:none;"
      >
        <img
          #img
          class="image-overlay-image"
          [src]="imageSrc()"
          cdkDrag
          cdkDragRootElement=".image-overlay"
        />
      </div>
      @if (!!opp) {
        <div class="image-overlay-opp-container">
          <ng-template cdkPortalOutlet #opp></ng-template>
        </div>
      }
    </div>
  `,
  styles: [
    `
      .image-overlay-container {
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, 0.3);
        .image-overlay {
          .image-overlay-image {
            width: 100%;
            height: 100%;
          }
        }
        .on-loading,
        .on-failed {
          display: block;
          width: 100%;
          height: 100%;
          transform: translateX(50%) translateY(50%);
          color: #fff;
          font-size: 18px;
          user-select: none;
        }
        .shown {
          display: block;
        }
        .hide {
          display: none;
        }
      }
    `,
  ],
  imports: [CdkDrag, CdkPortalOutlet],
  standalone: true,
})
export class ImageOverlayContainer implements OnInit, OnDestroy, AfterViewInit {
  /** 用户自定义数据。*/
  public data: any;
  /** 承载图片的canvas */
  @ViewChild('img', { static: true }) private img?: ElementRef<HTMLImageElement>;
  @ViewChild('container', { static: true }) private container?: ElementRef<HTMLDivElement>;
  @ViewChild('backdrop', { static: true }) private backdrop?: ElementRef<HTMLDivElement>;
  @ViewChild(CdkDrag) private cdkDrag?: CdkDrag;
  /** 键盘事件。传递 KeyboardEvent.key */
  public keyEvent$ = new Subject<string>();
  /** 图片操作面板的模板引用 */
  @ViewChild('opp', { read: CdkPortalOutlet }) oppOutlet!: CdkPortalOutlet;
  opp: TemplateRef<any> | null = null;
  imageSrc = signal<string | SafeResourceUrl>('');
  /** 图片是否已加载成功 0-正在加载，1-加载成功，-1-加载失败 */
  @Input() loadStatus = 0;

  private imageHeight = 0;
  private imageWidth = 0;

  private containerHeight = 0;
  private containerWidth = 0;

  private resizeObserver?: ResizeObserver;
  private dispose$: Subject<void> = new Subject<void>();

  private top = 0;
  private bottom = 0;
  private left = 0;
  private right = 0;
  private width = 0;
  private height = 0;

  private _viewContainerRef = inject(ViewContainerRef);

  constructor(
    private overlayRef: OverlayRef,
    private option: ImageOverlayOption,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    if (!!this.option && !!this.option.src) {
      this.imageSrc.set(this.option.src);
    }
    this.opp = this.option?.oppTemplate;
  }

  ngAfterViewInit(): void {
    if (!!this.opp) {
      const portal = new TemplatePortal(this.opp, this._viewContainerRef, { $implicit: this });
      this.oppOutlet.attach(portal);
    }

    if (!this.img || !this.container || !this.backdrop) {
      return;
    }

    // 图片加载成功
    this.img.nativeElement.onload = () => {
      this.imageHeight = this.img!.nativeElement.naturalHeight;
      this.imageWidth = this.img!.nativeElement.naturalWidth;
      this.resetPosition();
    };

    // 图片加载失败
    this.img.nativeElement.onerror = () => {
      this.loadStatus = -1;
      this.cdr.markForCheck();
    };

    // 容器当前大小
    this.containerHeight = this.container.nativeElement.clientHeight;
    this.containerWidth = this.container.nativeElement.clientWidth;

    // 容器大小改变（https://developer.mozilla.org/zh-CN/docs/Web/API/ResizeObserver）
    this.resizeObserver = new ResizeObserver((entries) => {
      if (!!entries && entries.length > 0 && entries[0].target === this.backdrop?.nativeElement) {
        const rect = entries[0].contentBoxSize[0];
        this.containerHeight = rect.blockSize;
        this.containerWidth = rect.inlineSize;
        this.resetPosition();
      }
    });
    this.resizeObserver!.observe(this.backdrop.nativeElement);

    // 鼠标点击事件。
    fromEvent(this.backdrop.nativeElement, 'click')
      .pipe(takeUntil(this.dispose$))
      .subscribe((_) => {
        this.overlayRef.detach();
      });
    fromEvent(this.container.nativeElement, 'click')
      .pipe(takeUntil(this.dispose$))
      .subscribe((event) => {
        event?.preventDefault(); // 阻止默认事件，防止被错误关闭
        event?.stopPropagation(); // 阻止事件传递，防止被错误关闭
      });

    // 鼠标滚轴事件。
    fromEvent<WheelEvent>(this.backdrop.nativeElement, 'mousewheel')
      .pipe(takeUntil(this.dispose$))
      .subscribe((event) => {
        event?.preventDefault(); // 阻止默认事件，防止界面滚动
        event?.stopPropagation(); // 阻止事件传递，防止界面滚动
      });

    // 鼠标滚轴事件。
    fromEvent<WheelEvent>(this.container.nativeElement, 'mousewheel')
      .pipe(
        takeUntil(this.dispose$),
        tap((event) => {
          event?.preventDefault(); // 阻止默认事件，防止界面滚动
          event?.stopPropagation(); // 阻止事件传递，防止界面滚动
        }),
        filter((_) => this.loadStatus === 1), // 图片已加载完成
      )
      .subscribe((event: WheelEvent) => {
        let toBig = (event?.deltaY ?? 0) < 0;
        if (toBig) {
          this.scaleUp(event.offsetX, event.offsetY);
        } else {
          this.scaleDown(event.offsetX, event.offsetY);
        }
      });

    // 键盘事件
    this.keyHandler = (ev) => this.onKeyDown(ev);
    document.addEventListener('keydown', this.keyHandler, true);

    // 设置 container 默认样式
    this.img.nativeElement.style.display = 'none';
    this.container.nativeElement.style.display = 'none';
    this.container.nativeElement.style.position = 'absolute';
    this.container.nativeElement.style.top = '0';
    this.container.nativeElement.style.left = '0';
  }

  private keyHandler: ((e: KeyboardEvent) => void) | null = null;

  ngOnDestroy(): void {
    this.resizeObserver?.disconnect();
    this.dispose$.next();
    this.dispose$.complete();
    if (this.keyHandler) {
      document.removeEventListener('keydown', this.keyHandler, true);
      this.keyHandler = null;
    }
  }

  private onKeyDown(evt: KeyboardEvent) {
    const key = evt.key;
    if (key === 'Escape') {
      evt.stopPropagation();
      this.close();
    }
  }

  public close() {
    this.overlayRef.detach();
  }

  public saveImage() {
    if (!this.img?.nativeElement) {
      return;
    }
    const canvas = document.createElement('canvas');
    canvas.width = this.img.nativeElement.naturalWidth;
    canvas.height = this.img.nativeElement.naturalHeight;
    const ctx = canvas.getContext('2d');
    ctx?.drawImage(this.img.nativeElement, 0, 0);
    const link = document.createElement('a');
    link.download = 'image.png';
    link.href = canvas.toDataURL();
    link.click();
    link.remove();
    canvas.remove();
  }
  /** x-鼠标在canvas的横坐标，y-鼠标在canvas的纵坐标 */
  public scaleUp(x?: number, y?: number) {
    x = x ?? this.width / 2;
    y = y ?? this.height / 2;
    const yt = y / this.height;
    const yb = 1 - yt;
    const xl = x / this.width;
    const xr = 1 - xl;
    this.top = this.top - this.height * yt * 0.2;
    this.bottom = this.bottom - this.height * yb * 0.2;
    this.left = this.left - this.width * xl * 0.2;
    this.right = this.right - this.width * xr * 0.2;
    this.width = this.width * 0.2 + this.width;
    this.height = this.height * 0.2 + this.height;
    this._setContainerStyles();
  }
  /** x-鼠标在canvas的横坐标，y-鼠标在canvas的纵坐标 */
  public scaleDown(x?: number, y?: number) {
    x = x ?? this.width / 2;
    y = y ?? this.height / 2;
    const yt = y / this.height;
    const yb = 1 - yt;
    const xl = x / this.width;
    const xr = 1 - xl;
    this.top = this.top + this.height * yt * 0.2;
    this.bottom = this.bottom + this.height * yb * 0.2;
    this.left = this.left + this.width * xl * 0.2;
    this.right = this.right + this.width * xr * 0.2;
    this.width = this.width - this.width * 0.2;
    this.height = this.height - this.height * 0.2;
    this._setContainerStyles();
  }

  public resetPosition() {
    if (!this.img?.nativeElement) {
      return;
    }

    const maxHeight = this.containerHeight * 0.8;
    const maxWidth = this.containerWidth * 0.8;
    const imageScale = this.imageWidth / this.imageHeight;
    this.width = this.imageWidth;
    this.height = this.imageHeight;
    if (maxWidth < this.imageWidth) {
      this.width = maxWidth;
      this.height = this.width / imageScale;
    }
    if (maxHeight < this.imageHeight) {
      this.height = maxHeight;
      this.width = this.height * imageScale;
    }

    this.left = this.right = (this.containerWidth - this.width) / 2;
    this.top = this.bottom = (this.containerHeight - this.height) / 2;

    if (this.imageHeight > maxHeight || this.imageWidth > maxWidth) {
    }

    this.cdkDrag!.reset();
    this._setContainerStyles();

    this.loadStatus = 1;
    // this.cdr.markForCheck();
  }

  /**
   * 设置容器样式
   */
  private _setContainerStyles() {
    if (!this.container?.nativeElement || !this.img?.nativeElement) {
      return;
    }
    this.container.nativeElement.style.top = this.top + 'px';
    this.container.nativeElement.style.bottom = this.bottom + 'px';
    this.container.nativeElement.style.left = this.left + 'px';
    this.container.nativeElement.style.right = this.right + 'px';
    this.container.nativeElement.style.width = this.width + 'px';
    this.container.nativeElement.style.height = this.height + 'px';
    setTimeout(() => {
      this.container!.nativeElement.style.display = 'block';
    });
    this.img.nativeElement.style.display = 'block';
  }
}
