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

import {
  AfterViewInit,
  Component,
  EventEmitter,
  Injectable,
  Injector,
  OnDestroy,
  Output,
  signal,
} from '@angular/core';
import { Overlay, OverlayConfig, OverlayRef } from '@angular/cdk/overlay';
import { ComponentPortal } from '@angular/cdk/portal';
import { Observable, Subject, takeUntil } from 'rxjs';
import { CdkTrapFocus } from '@angular/cdk/a11y';

const ALLOW_SCAN_CHARS = new Set<string>('ACDEFHIJKNPRTUVXY-0123456789'.split(''));
const SCAN_GAP_THRESHOLD = 80;
const VALID_PATTERN = /^[ACDEFHIJKNPRTUVXY]{6}-[0-9]{4}$/;

@Injectable({ providedIn: 'root' })
export class ServiceGlobalCodeScanner implements OnDestroy {
  private overlayRef: OverlayRef | null = null;
  private readonly cfg: OverlayConfig;

  constructor(
    private readonly overlay: Overlay,
    private readonly injector: Injector,
  ) {
    this.cfg = {
      hasBackdrop: true,
      backdropClass: 'cdk-overlay-transparent-backdrop',
      scrollStrategy: this.overlay.scrollStrategies.block(),
      positionStrategy: this.overlay.position().global().centerHorizontally().top('0px'),
    };
  }

  ngOnInit(): void {}

  start(message = ''): Observable<string> {
    this.close();
    return this.openOverlay(message);
  }
  close(): void {
    if (this.overlayRef) {
      this.overlayRef.dispose();
      this.overlayRef = null;
    }
  }

  private openOverlay(message: string): Observable<string> {
    this.overlayRef = this.overlay.create(this.cfg);
    const portal = new ComponentPortal(GlobalScanOverlayComponent, null, this.injector);
    const compRef = this.overlayRef.attach(portal);

    compRef.instance.message.set(message);
    compRef.instance.onClosed
      .pipe(takeUntil(compRef.instance.dispose$))
      .subscribe(() => this.close());

    // 点击背景关闭
    this.overlayRef
      .backdropClick()
      .pipe(takeUntil(compRef.instance.dispose$))
      .subscribe(() => this.close());

    // 接收overlay扫码输出
    return compRef.instance.onScanResult.pipe(takeUntil(compRef.instance.dispose$));
  }

  ngOnDestroy(): void {
    this.close();
  }
}

@Component({
  selector: 'app-global-scan-overlay',
  standalone: true,
  template: `
    <div
      class="scan-overlay-box"
      (click)="onClosed.emit()"
      [cdkTrapFocus]="true"
      [cdkTrapFocusAutoCapture]="true"
    >
      <h2>正在接收扫码枪输入</h2>
      @if (message(); as msg) {
        <h3>{{ msg }}</h3>
      }
      <p>点击或按下[ESC]退出</p>
      <input
        tabindex="0"
        style="width:0;height:0;opacity: 0;position: absolute;top:-999999999px"
        readonly
      />
    </div>
  `,
  styles: [
    `
      h2, h3 {
        padding: 0;
        margin: 24px 0;
      }

      h2 {
        margin-top: 0;
      }

      .scan-overlay-box {
        min-width: 480px;
        padding: var(--mat-sys-corner-large);
        background: var(--mat-sys-error);
        color: var(--mat-sys-on-error);
        border-radius: var(--mat-sys-corner-large);
        box-shadow: var(--mat-sys-level5);
        text-align: center;
      }
    `,
  ],
  imports: [CdkTrapFocus],
})
class GlobalScanOverlayComponent implements OnDestroy, AfterViewInit {
  message = signal('');
  @Output() onScanResult = new EventEmitter<string>();
  @Output() onClosed = new EventEmitter<void>();

  private charBuffer: string[] = [];
  private keyHandler: ((e: KeyboardEvent) => void) | null = null;

  private lastKeyTime = 0;
  private isScanDevice = false;

  dispose$ = new Subject<void>();

  ngAfterViewInit(): void {
    this.keyHandler = (ev) => this.onKeyDown(ev);
    document.addEventListener('keydown', this.keyHandler, true);
  }

  private onKeyDown(evt: KeyboardEvent): void {
    const key = evt.key;
    if (key === 'Escape') {
      evt.stopPropagation();
      this.onClosed.emit();
      return;
    }
    if (key === 'Backspace') {
      this.charBuffer.length = 0;
      return;
    }

    const now = performance.now();
    const gap = now - this.lastKeyTime;
    this.lastKeyTime = now;

    if (gap < SCAN_GAP_THRESHOLD) {
      this.isScanDevice = true;
    }

    if (key === 'Enter') {
      if (this.isScanDevice) {
        const value = this.charBuffer.join('');
        if (VALID_PATTERN.test(value)) {
          this.onScanResult.emit(value);
        }
      }
      this.charBuffer.length = 0;
      this.isScanDevice = false;
      return;
    }

    if (ALLOW_SCAN_CHARS.has(key)) {
      this.charBuffer.push(key);
    }
  }

  ngOnDestroy(): void {
    if (this.keyHandler) {
      document.removeEventListener('keydown', this.keyHandler, true);
      this.keyHandler = null;
    }
    this.charBuffer.length = 0;
    this.isScanDevice = false;
    this.lastKeyTime = 0;
    this.dispose$.next();
    this.dispose$.complete();
  }
}
