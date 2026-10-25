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

import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, signal } from '@angular/core';
import { MatIcon } from '@angular/material/icon';
import { MatButton } from '@angular/material/button';
import { MatList, MatListItem, MatListModule } from '@angular/material/list';
import { Subject } from 'rxjs';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { MatTooltip } from '@angular/material/tooltip';

export interface UploadFileItem {
  file: File;
  name: string;
  size: number;
  error?: string;
}

@Component({
  standalone: true,
  selector: 'upload-panel',
  templateUrl: 'upload.html',
  styleUrl: 'upload.scss',
  imports: [
    MatIcon,
    MatButton,
    MatList,
    MatListItem,
    MatProgressSpinner,
    MatListModule,
    MatTooltip,
  ],
})
export class UploadPanel implements OnInit, OnDestroy {
  @Input() accept: string = 'image/*';
  uploading = signal<boolean>(false);
  @Output() startUpload = new EventEmitter<File[]>();
  @Output() onCancel = new EventEmitter<void>();

  public fileItems: UploadFileItem[] = [];
  public isDragOver = false;
  public readonly MAX_FILE_SIZE = 5 * 1024 * 1024 * 1024;
  public dispose$ = new Subject<void>();

  ngOnInit(): void {}
  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  private dragEnterCounter = 0;
  onDragOver(e: DragEvent): void {
    if (this.uploading()) return;
    e.preventDefault();
    e.stopPropagation();
    this.isDragOver = true;
  }
  onDragEnter(e: DragEvent): void {
    if (this.uploading()) return;
    e.preventDefault();
    e.stopPropagation();
    this.dragEnterCounter++;
    this.isDragOver = this.dragEnterCounter > 0;
  }
  onDragLeave(e: DragEvent): void {
    if (this.uploading()) return;
    e.preventDefault();
    e.stopPropagation();
    this.dragEnterCounter--;
    this.isDragOver = this.dragEnterCounter > 0;
  }
  onDrop(e: DragEvent): void {
    if (this.uploading()) return;
    e.preventDefault();
    e.stopPropagation();
    this.dragEnterCounter = 0;
    this.isDragOver = false;
    if (e.dataTransfer?.files) {
      this.appendFiles(e.dataTransfer.files);
    }
  }

  onFileSelect(event: Event): void {
    if (this.uploading()) return;
    const input = event.target as HTMLInputElement;
    if (input.files) {
      this.appendFiles(input.files);
    }
    input.value = '';
  }

  requestClose(){
    if (this.uploading()) return;
    this.onCancel.emit();
  }

  private appendFiles(fileList: FileList): void {
    if (this.uploading()) return;

    // 提取已存在的文件名集合
    const existNames = new Set(this.fileItems.map((x) => x.name));

    for (let i = 0; i < fileList.length; i++) {
      const f = fileList.item(i);
      if (!f) continue;

      // 去重：同名直接跳过
      if (existNames.has(f.name)) {
        continue;
      }

      const item: UploadFileItem = {
        file: f,
        name: f.name,
        size: f.size,
      };

      if (f.size > this.MAX_FILE_SIZE) {
        item.error = `超过最大限制 ${this.formatSize(this.MAX_FILE_SIZE)}`;
      }
      if (this.accept && !this.checkFileAccept(f, this.accept)) {
        item.error = `该文件被忽略，仅允许上传：${this.accept}`;
      }

      this.fileItems.push(item);
      existNames.add(f.name);
    }
  }

  private checkFileAccept(file: File, accept: string): boolean {
    const parts = accept
      .split(',')
      .map((p) => p.trim().toLowerCase())
      .filter(Boolean);
    if (parts.length === 0) return true;

    const fileName = file.name.toLowerCase();
    const fileMime = file.type.toLowerCase();

    for (const p of parts) {
      if (p.startsWith('.')) {
        // 后缀模式： .png .jpg
        if (fileName.endsWith(p)) return true;
      } else if (p.endsWith('/*')) {
        // mime/* 模式 image/*
        const prefix = p.slice(0, -2);
        if (fileMime.startsWith(prefix)) return true;
      } else {
        // 完整mime类型 image/png
        if (fileMime === p) return true;
      }
    }
    return false;
  }

  removeItem(index: number): void {
    this.fileItems.splice(index, 1);
  }

  clearAll(): void {
    this.fileItems = [];
  }

  onTriggerUpload(): void {
    const ready = this.fileItems.filter((x) => !x.error);
    if (ready.length === 0) return;
    this.startUpload.emit(ready.map((f) => f.file));
  }

  formatSize(bytes: number): string {
    if (bytes < 1024) return bytes + ' B';
    else if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB';
    else return (bytes / (1024 * 1024)).toFixed(1) + ' MB';
  }
}
