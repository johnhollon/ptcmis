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

import {Component, Input, signal} from '@angular/core';

/**
 * 5个圆点Loading组件。
 *
 * @input message，提示信息，默认值 正在处理...
 */
@Component({
  selector: 'loading-5dots',
  standalone: true,
  template: `
    <div class="loading">
      <div class="circle"></div>
      <div class="circle"></div>
      <div class="circle"></div>
      <div class="circle"></div>
      <div class="circle"></div>
      <p class="desc">{{ messageSig() }}</p>
    </div>
  `,
  styles: `
    .loading {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      text-align: center;
    }

    .desc {
      margin-top: 22px;
      font-size: var(--mat-sys-body-size);
      color: var(--mat-sys-primary);
    }

    .circle {
      height: 16px;
      width: 16px;
      margin-left: 10px;
      border-radius: 50%;
      display: inline-block;
      transform: scale(0.4);
      background-color: var(--mat-sys-primary);
      animation: bulge 2.4s infinite cubic-bezier(0.65, 0.05, 0.36, 1);
    }

    .circle:nth-child(1) {
      margin: 0;
      animation-delay: 0s;
    }

    .circle:nth-child(2) {
      animation-delay: .15s;
    }

    .circle:nth-child(3) {
      animation-delay: .3s;
    }

    .circle:nth-child(4) {
      animation-delay: .45s;
    }

    .circle:nth-child(5) {
      animation-delay: .6s;
    }

    @keyframes bulge {
      50% {
        transform: scale(1);
      }
    }
  `
})
export class ComponentLoading5dots {
  readonly #message = signal('正在处理...');
  protected readonly messageSig = this.#message.asReadonly();
  @Input()
  set message(value: string) {
    this.#message.set(value);
  }
}
