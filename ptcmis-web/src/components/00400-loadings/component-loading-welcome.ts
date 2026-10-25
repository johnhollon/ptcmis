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
 * 欢迎Loading组件。
 *
 * @input text，显示的文字，实际显示且仅显示8个字符。默认值 智慧生活智慧工作
 */
@Component({
  selector: 'loading-welcome',
  imports: [],
  standalone: true,
  template: `
    <div class="cs-loader">
      <div class="cs-loader-inner title-font-face">
        <label id="label-1">{{ charsSig()[0] }}</label>
        <label id="label-2">&nbsp;{{ charsSig()[1] }}</label>
        <label id="label-3">&nbsp;{{ charsSig()[2] }}</label>
        <label id="label-4">&nbsp;{{ charsSig()[3] }}</label>
        <label>&nbsp;</label>
        <label id="label-5">{{ charsSig()[4] }}</label>
        <label id="label-6">&nbsp;{{ charsSig()[5] }}</label>
        <label id="label-7">&nbsp;{{ charsSig()[6] }}</label>
        <label id="label-8">&nbsp;{{ charsSig()[7] }}</label>
      </div>
      <p>正在处理，请稍候...</p>
    </div>
  `,
  styles: `
    p {
      position: absolute;
      left: 0;
      right: 0;
      bottom: 0;
      z-index: 2;
      padding: 8px;
      line-height: 1.6em;
      background-color: var(--mat-sys-primary);
      color: var(--mat-sys-on-primary);
      font-family: var(--mat-sys-body-medium-font);
      font-size: var(--mat-sys-body-medium-size);
    }

    .cs-loader {
      z-index: 0;
      position:absolute;
      background-color: var(--mat-sys-primary);
      top:0;
      bottom:0;
      left: 0;
      right: 0;
    }

    .cs-loader-inner {
      // 阻止用户选择文本
      user-select: none;
      transform: translateY(-50%);
      top: 50%;
      position: absolute;
      width: 100%;
      text-shadow: 1px 1px 1px var(--mat-sys-shadow);
      text-align: center;
      color: var(--mat-sys-on-primary);
      font-family: var(--mat-sys-headline-small-font);
      font-size: var(--mat-sys-headline-small-size);
    }

    .cs-loader-inner label {
      font-size: var(--mat-sys-title-large-size);
      opacity: 0;
      display: inline-block;
      width: 1.3636em;
    }

    @keyframes lol {
      0% {
        opacity: 0;
        transform: translateX(-300px)
      }

      33% {
        opacity: 1;
        transform: translateX(0)
      }

      66% {
        opacity: 1;
        transform: translateX(0)
      }

      100% {
        opacity: 0;
        transform: translateX(300px)
      }
    }

    .cs-loader-inner label:nth-child(9) {
      animation: lol 5s infinite ease-in-out
    }

    .cs-loader-inner label:nth-child(8) {
      animation: lol 5s .1s infinite ease-in-out
    }

    .cs-loader-inner label:nth-child(7) {
      animation: lol 5s .2s infinite ease-in-out
    }

    .cs-loader-inner label:nth-child(6) {
      animation: lol 5s .3s infinite ease-in-out
    }

    .cs-loader-inner label:nth-child(5) {
      animation: lol 5s .4s infinite ease-in-out
    }

    .cs-loader-inner label:nth-child(4) {
      animation: lol 5s .5s infinite ease-in-out
    }

    .cs-loader-inner label:nth-child(3) {
      animation: lol 5s .6s infinite ease-in-out
    }

    .cs-loader-inner label:nth-child(2) {
      animation: lol 5s .7s infinite ease-in-out
    }

    .cs-loader-inner label:nth-child(1) {
      animation: lol 5s .8s infinite ease-in-out
    }
  `
})
export class ComponentLoadingWelcome {
  readonly #chars = signal('智慧生活智慧工作'.split(''));
  protected readonly charsSig = this.#chars.asReadonly();

  @Input() set text(value: string) {
    value = value || '';
    value = value.trim();
    if (value.length >= 8) this.#chars.set(value.split(''));
  }
}
