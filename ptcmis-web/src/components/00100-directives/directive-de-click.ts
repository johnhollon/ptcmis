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

import {Directive, EventEmitter, HostListener, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Subject} from 'rxjs';
import {debounceTime, takeUntil, throttle} from 'rxjs/operators';

/**
 * 防抖点击指令。首次触发点击放行，后续连续点击需等待一定时长（由 de-time 定义，默认 200 毫秒）
 *
 * exportAs: 'DeClick'
 *
 * 使用方法：
 *
 * ``` html
 * <!--默认防抖时长200毫秒-->
 * <button (de-click)="doSomething($event)">按钮</button>
 * <!--设置防抖时长1000毫秒-->
 * <button (de-click)="doSomething($event)" de-time="1000">按钮</button>
 * <!--使用exportAs(DeClick)-->
 * <button (de-click)="doSomething($event)" de-time="1000" #button="DeClick">按钮</button>
 * ```
 */
@Directive({
  selector: '[de-click]',
  exportAs: 'DeClick',
  standalone: true,
})
export class DirectiveDeClick implements OnInit, OnDestroy {
  @Input('de-time')
  public debounceTime: number = 500;

  @Output('de-click')
  public onClick: EventEmitter<any> = new EventEmitter<any>();

  // 主事件流（用于限流过滤）
  readonly #emitEvent$: Subject<any> = new Subject<any>();
  // 信号重置流（每一次点击都会向这里发送信号，触发 debounceTime 重新计时）
  readonly #reset$: Subject<void> = new Subject<void>();
  // 销毁流
  readonly #dispose$: Subject<void> = new Subject<void>();

  ngOnInit(): void {
    this.#emitEvent$.pipe(
      takeUntil(this.#dispose$),
      // throttle 配合自定义的 reset$ 作为 duration
      throttle(
        () => this.#reset$.pipe(
          takeUntil(this.#dispose$),
          debounceTime(this.debounceTime),
        ),
        {leading: true, trailing: false}
      )
    ).subscribe(event => {
      this.emitClick(event);
    });
  }

  public emitClick(value: any): void {
    this.onClick.emit(value);
  }

  ngOnDestroy(): void {
    this.#dispose$.next();
    this.#dispose$.complete();
    this.#emitEvent$.complete();
    this.#reset$.complete();
  }

  @HostListener('click', ['$event'])
  public listenClick(event: any): void {
    if (event.preventDefault) {
      event.preventDefault();
    }
    if (event.stopPropagation) {
      event.stopPropagation();
    }
    // 发送给主事件流（会被 throttle 拦截或放行）
    this.#emitEvent$.next(event);

    // 无论当前处于冷却期还是放行状态，每次点击都通知 reset$ 重置定时器
    this.#reset$.next();
  }
}
