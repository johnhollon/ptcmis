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
  Component,
  EventEmitter,
  inject,
  Input,
  OnDestroy,
  OnInit,
  Output,
  signal,
} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import {
  BottleCodeComplementTask,
  BottleCodeComplementTaskDefault,
} from '../../../entities/models/BottleCodeComplementTask';
import { finalize, Subject, switchMap } from 'rxjs';
import { LayoutDrawerOppSimple } from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { MatButton } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { ArchivePanel } from '../../../components/00600-archive/archive';
import { takeUntil } from 'rxjs/operators';
import { ServiceUploadOverlay } from '../../../services/ui/service-upload-overlay';
import { ActionBottleCodeComplementTask } from '../../../services/actions/action-bottle-code-complement-task';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { Archive } from '../../../entities/models/Archive';
import { ActionArchive } from '../../../services/actions/action-archive';

@Component({
  standalone: true,
  selector: 'archive-bottle-code-complement-task',
  templateUrl: 'archive.html',
  styleUrl: 'archive.scss',
  imports: [ReactiveFormsModule, LayoutDrawerOppSimple, MatButton, MatListModule, ArchivePanel],
})
export class CompArchive implements OnInit, OnDestroy {
  @Input() model: BottleCodeComplementTask = BottleCodeComplementTaskDefault;
  @Input() editable = true;
  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();

  protected archivesSig = signal<Archive[]>([]);

  private dispose$ = new Subject<void>();

  private readonly serviceUploadOverlay = inject(ServiceUploadOverlay);
  private readonly serviceShowMessage = inject(ServiceShowMessage);
  private readonly actionArchive = inject(ActionArchive);
  private readonly actionBottleCodeComplementTask = inject(ActionBottleCodeComplementTask);

  ngOnInit(): void {
    this.actionBottleCodeComplementTask
      .queryArchives(this.model.id)
      .pipe(takeUntil(this.dispose$))
      .subscribe({
        next: (data) => this.archivesSig.set(data),
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected requestUpload(model: BottleCodeComplementTask) {
    if (!this.editable) return;
    const uploadPanel = this.serviceUploadOverlay.show();
    uploadPanel.accept = 'image/png,image/jpeg,image/gif';
    uploadPanel.onCancel
      .pipe(takeUntil(this.dispose$))
      .subscribe((_) => this.serviceUploadOverlay.close());

    uploadPanel.startUpload
      .pipe(takeUntil(uploadPanel.dispose$), takeUntil(this.dispose$))
      .subscribe((files) => {
        uploadPanel.uploading.set(true);
        this.actionBottleCodeComplementTask
          .uploadArchives(model.id, files)
          .pipe(
            takeUntil(uploadPanel.dispose$),
            takeUntil(this.dispose$),
            finalize(() => {
              uploadPanel.uploading.set(false);
            }),
          )
          .subscribe({
            next: (archives) => {
              this.serviceUploadOverlay.close();
              this.serviceShowMessage.showInfo('上传完成。');
              this.archivesSig.set(archives);
            },
            error: (error) => this.serviceShowMessage.showError(error),
          });
      });
  }

  protected onDelete(archiveId: string): void {
    if (!this.editable) return;
    if (!archiveId) return;
    this.onRequestStartLoading.emit('正在删除...');
    this.actionArchive
      .delete(archiveId)
      .pipe(
        switchMap((_) => this.actionBottleCodeComplementTask.queryArchives(this.model.id)),
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        next: (archives) => {
          this.archivesSig.set(archives);
        },
        error: (error) => this.serviceShowMessage.showError(error),
      });
  }
}
