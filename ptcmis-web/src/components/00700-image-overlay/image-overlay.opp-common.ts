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
import { ImageOverlayContainer } from './image-overlay.container';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';

/**
 * 图片浮层普通操作（包括关闭、调整大小等）。
 */
@Component({
  selector: 'image-overlay-opp-common',
  standalone: true,
  template: `
    <div class="image-can-zoom-opps-container" (click)="$event.stopPropagation()">
      <span (click)="onClose()" matTooltip="关闭（Esc键或点击空白处）">
        <mat-icon>close</mat-icon>
      </span>
      <span (click)="scaleUp()" matTooltip="放大（鼠标滚轮向上）">
        <mat-icon>add</mat-icon>
      </span>
      <span (click)="scaleDown()" matTooltip="缩小（鼠标滚轮向下）">
        <mat-icon>horizontal_rule</mat-icon>
      </span>
    </div>
  `,
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [MatIcon, MatTooltip],
})
export class ImageOverlayOppCommon implements OnInit {
  constructor() {}

  @Input() overlayContainer: ImageOverlayContainer | null | undefined;
  @Input() canNotDownload: string | null | undefined;

  ngOnInit(): void {}

  onClose() {
    if (!!this.overlayContainer) {
      this.overlayContainer.close();
    }
  }

  scaleUp() {
    if (!!this.overlayContainer) {
      this.overlayContainer.scaleUp();
    }
  }

  scaleDown() {
    if (!!this.overlayContainer) {
      this.overlayContainer.scaleDown();
    }
  }

  saveImage() {
    if (!!this.overlayContainer) {
      this.overlayContainer.saveImage();
    }
  }
}
