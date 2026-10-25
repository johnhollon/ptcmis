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

import { OverlayConfig } from '@angular/cdk/overlay';
import {
  AfterViewInit,
  ChangeDetectorRef,
  Directive,
  ElementRef,
  EventEmitter,
  HostBinding,
  HostListener,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
  TemplateRef,
} from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, takeUntil } from 'rxjs/operators';
import { ImageOverlayService } from './image-overlay.service';

@Directive({
  selector: 'img.image-overlay, [image-overlay]',
  standalone: true,
  exportAs: 'ImageOverlay',
})
export class ImageOverlayDirective implements OnInit, OnDestroy, OnChanges, AfterViewInit {
  /** 用户自定义数据 */
  @Input('data') data: any;
  /** 加载完成时返回图片原始宽度和高度 */
  @Output('on-loaded') onLoaded = new EventEmitter<{ width: number; height: number }>();
  /** 键盘事件 */
  @Output('on-key-down') onKeyDown = new EventEmitter<string>();
  /** 加载成功后光标是否为手形 */
  @Input('pointer') pointer: boolean = true;
  /** 图片操作模板 */
  @Input('opp-template') oppTemplate: TemplateRef<any> | null = null;
  @Input('overlay-config') overlayConfig: OverlayConfig | null = null;
  @Input('stop-propagation') stopPropagation = true;
  @Input('prevent-default') preventDefault = true;

  @HostBinding('class.pointer') cursorPointer = false;
  @HostBinding('attr.src') src = '';

  private imageLoaded = false;
  private imageLoading = false;
  private imageHeight = 0;
  private imageWidth = 0;

  private clickEvent$: Subject<any> = new Subject<any>();
  private dispose$: Subject<void> = new Subject<void>();

  // 目标与滚动视窗交集检测（https://developer.mozilla.org/zh-CN/docs/Web/API/Intersection_Observer_API）
  private intersectionObserver?: IntersectionObserver;
  private intersectionCheck$ = new Subject<IntersectionObserverEntry>();

  constructor(
    private imageZoomService: ImageOverlayService,
    private image: ElementRef<HTMLImageElement>,
    private cdr: ChangeDetectorRef,
    private readonly el: ElementRef<HTMLImageElement>,
  ) {}

  ngOnInit(): void {

  }

  ngOnChanges(changes: SimpleChanges): void {
    let { imageSrc } = changes;
    if (imageSrc && imageSrc.currentValue !== imageSrc.previousValue) {
      this.src = imageSrc.currentValue;
    }
  }

  ngAfterViewInit(): void {
    this.imageZoomService.keyDown$
      .pipe(takeUntil(this.dispose$))
      .subscribe((key) => this.onKeyDown.emit(key));
    this.clickEvent$
      .pipe(
        takeUntil(this.dispose$),
        filter((_) => this.imageLoaded),
        debounceTime(200),
        distinctUntilChanged(),
      )
      .subscribe((_) => this.showOverlay());
    this.intersectionCheck$
      .pipe(
        takeUntil(this.dispose$),
        filter((_) => !this.imageLoaded),
        filter((_) => !this.imageLoading),
        debounceTime(200),
      )
      .subscribe((_) => this.loadImage());
    this.image.nativeElement.classList.add('failed');
    this.intersectionObserver = new IntersectionObserver((entries) => {
      if (!!entries && entries.length > 0) {
        const entry = entries[0];
        this.intersectionCheck$.next(entry);
      }
    });
    this.intersectionObserver.observe(<Element>this.image.nativeElement);
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.intersectionObserver?.disconnect();
  }

  loadImage(): void {
    this.imageLoading = true;
    this.src = (this.el.nativeElement.src ?? '') as string;
    this.cdr.markForCheck();
  }

  showOverlay(): void {
    this.imageZoomService.showOverlay({
      src: (this.el.nativeElement.src ?? '') as string,
      oppTemplate: this.oppTemplate,
      data: this.data,
      overlayConfig: this.overlayConfig,
    });
  }

  @HostListener('click', ['$event'])
  clickEventListener(event: any): void {
    if (this.stopPropagation) {
      event.stopPropagation();
    }
    if (this.preventDefault) {
      event.preventDefault();
    }
    this.clickEvent$.next(event);
  }
  @HostListener('load', ['$event'])
  imageLoadedEventListener(event: any): void {
    this.intersectionObserver?.disconnect();
    this.imageWidth = this.image.nativeElement.naturalWidth;
    this.imageHeight = this.image.nativeElement.naturalHeight;
    this.imageLoaded = true;
    this.imageLoading = false;
    this.cursorPointer = this.pointer;
    this.onLoaded.emit({ width: this.imageWidth, height: this.imageHeight });
    this.image.nativeElement.classList.remove('failed');
    this.cdr.markForCheck();
  }
  @HostListener('error', ['$event'])
  imageErrorEventListener(event: any): void {
    this.imageLoaded = false;
    this.imageLoading = false;
    this.cursorPointer = false;
    if (!this.image.nativeElement.classList.contains('failed')) {
      this.image.nativeElement.classList.add('failed');
    }
    this.cdr.markForCheck();
  }
}
