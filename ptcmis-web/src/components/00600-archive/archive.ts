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
  effect,
  ElementRef,
  EventEmitter,
  input,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  signal,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { MatIconButton } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { PipeTimestamp } from '../00200-pipes/pipe-timestamp';
import { ImageOverlayDirective } from '../00700-image-overlay/image-overlay.directive';
import { ImageOverlayOppCommon } from '../00700-image-overlay/image-overlay.opp-common';
import { Archive } from '../../entities/models/Archive';
import { DirectiveArchiveIdSrc } from '../00100-directives/directive-archive-id-src';

@Component({
  standalone: true,
  selector: 'archive-panel',
  templateUrl: 'archive.html',
  styleUrl: 'archive.scss',
  imports: [
    ReactiveFormsModule,
    MatListModule,
    MatIcon,
    PipeTimestamp,
    MatIconButton,
    MatTooltip,
    PipeTimestamp,
    ImageOverlayDirective,
    ImageOverlayOppCommon,
    DirectiveArchiveIdSrc,
  ],
})
export class ArchivePanel implements OnInit, OnDestroy, AfterViewInit {
  archives = input([] as Archive[], {
    transform: (v: Archive[] | null | undefined) => v ?? [],
  });
  @Input() editable = true;
  @Output() onRequestDelete = new EventEmitter<string>();

  protected selectedArchiveSig = signal<Archive | null>(null);
  private readonly selectedArchiveEffect;

  private dispose$ = new Subject<void>();

  @ViewChild('image', { read: ElementRef }) private image?: ElementRef<HTMLDivElement>;
  @ViewChild('imageContainer', { read: ElementRef, static: true })
  private imageContainer!: ElementRef<HTMLDivElement>;
  private resizeObserver?: ResizeObserver;
  private imageHeight = 0;
  private imageWidth = 0;
  private containerHeight = 0;
  private containerWidth = 0;

  constructor() {
    this.selectedArchiveEffect = effect(() => {
      const selectedArchive = this.selectedArchiveSig();
      let index = -1;
      if (selectedArchive) {
        index = this.archives().findIndex((item) => item.id === selectedArchive.id);
      }
      if (index >= 0) {
        this.selectedArchiveSig.set(this.archives()[index]);
      } else {
        this.selectedArchiveSig.set(this.archives().length > 0 ? this.archives()[0] : null);
      }
    });
  }

  ngOnInit(): void {
    if (this.archives().length > 0) {
      this.selectedArchiveSig.set(this.archives()[0]);
    }
  }
  ngAfterViewInit(): void {
    // 容器当前大小
    if (!!this.imageContainer) {
      this.containerHeight = this.imageContainer.nativeElement.clientHeight;
      this.containerWidth = this.imageContainer.nativeElement.clientWidth;

      // 容器大小改变（https://developer.mozilla.org/zh-CN/docs/Web/API/ResizeObserver）
      this.resizeObserver = new ResizeObserver((entries) => {
        if (
          !!entries &&
          entries.length > 0 &&
          entries[0].target === this.imageContainer?.nativeElement
        ) {
          const rect = entries[0].contentBoxSize[0];
          this.containerHeight = rect.blockSize;
          this.containerWidth = rect.inlineSize;
          this.resetPosition();
        }
      });

      this.resizeObserver!.observe(this.imageContainer.nativeElement);
    }
  }
  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.selectedArchiveEffect.destroy();
  }

  protected onImageLoaded(event: any) {
    this.imageHeight = event?.height || 0;
    this.imageWidth = event?.width || 0;
    this.resetPosition();
  }
  private resetPosition() {
    if (this.imageHeight <= 0 || this.imageWidth <= 0) {
      return;
    }
    const maxHeight = this.containerHeight * 0.95;
    const maxWidth = this.containerWidth * 0.95;
    const imageScale = this.imageWidth / this.imageHeight;
    const containerScale = maxWidth / maxHeight;

    let width: number;
    let height: number;
    if (imageScale > containerScale) {
      width = maxWidth;
      height = width / imageScale;
    } else {
      height = maxHeight;
      width = height * imageScale;
    }

    const left = (this.containerWidth - width) / 2;
    const top = (this.containerHeight - height) / 2;

    if (!this.image) {
      return;
    }
    this.image.nativeElement.style.top = top + 'px';
    this.image.nativeElement.style.bottom = top + 'px';
    this.image.nativeElement.style.left = left + 'px';
    this.image.nativeElement.style.right = left + 'px';
    this.image.nativeElement.style.width = width + 'px';
    this.image.nativeElement.style.height = height + 'px';
  }
}
