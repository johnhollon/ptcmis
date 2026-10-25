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

import { Component, OnInit, ChangeDetectionStrategy, Input } from '@angular/core';

@Component({
  selector: 'image-overlay-desc-common',
  standalone: true,
  template: `
  <div class="image-can-zoom-desc-container" (click)="$event.stopPropagation();">
    <div class="image-can-zoom-desc-opp" (click)="onToggleMinisDesc($event)">
      <i class="open-icon icon mt" [class.opened]="isMinisDesc">expand_more</i>
    </div>
    <div class="image-can-zoom-desc" [class.hide]="isMinisDesc">
      <ng-content></ng-content>
    </div>
  </div>
  `,
  styles: [
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImageOverlayDescCommon implements OnInit {

  /** 是否最小化备注信息。 */
  @Input() isMinisDesc = false;
  constructor() { }

  ngOnInit(): void {
  }

  /**
   * 最小化/恢复备注信息。
   */
  onToggleMinisDesc(event?: any) {
    event?.stopPropagation();
    this.isMinisDesc = !this.isMinisDesc;
  }

}
