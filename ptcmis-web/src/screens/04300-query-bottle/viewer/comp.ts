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

import { Component, EventEmitter, inject, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { CompArchive } from '../archive/archive';
import { CompLogs } from '../logs/comp';
import { CompEditor } from '../editor/editor';
import { ServiceQueryBottle } from './service';
import { finalize, Subject } from 'rxjs';
import { Bottle } from '../../../entities/models/Bottle';
import { ServiceShowMessage } from '../../../services/ui/service-show-error-message.service';
import { takeUntil } from 'rxjs/operators';
import { CompParent } from '../parent/comp';
import { CompChildren } from '../children/comp';
import { ActionBottle } from '../../../services/actions/action-bottle';

@Component({
  standalone: true,
  selector: 'viewer-query-bottle',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [CompArchive, CompLogs, CompEditor, CompParent, CompParent, CompChildren],
  providers: [ServiceQueryBottle],
})
export class CompViewer implements OnInit, OnDestroy {
  @Input() subModule = 'baseInfo';
  @Input() bottle: Bottle | null = null;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();

  protected service = inject(ServiceQueryBottle);
  private readonly serviceShowErrorMessage = inject(ServiceShowMessage);
  private readonly actionBottle = inject(ActionBottle);
  private readonly dispose$ = new Subject<void>();

  ngOnInit(): void {
    if (!this.bottle) return;
    this.onRequestStartLoading.emit('正在查询...');
    this.service
      .requestQuery(this.bottle)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe({
        error: (error) => this.serviceShowErrorMessage.showError(error),
      });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }

  protected requestExport() {
    if (!this.bottle) return;
    this.onRequestStartLoading.emit('正在查询...');
    this.actionBottle
      .excelBottle(this.bottle.id)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.onRequestStopLoading.emit()),
      )
      .subscribe((res) => {
        const blob = res.body;
        if (!blob) return;

        const disposition = res.headers.get('content-disposition');
        let fileName = 'export.xlsx';
        if (disposition) {
          const match = disposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
          if (match && match[1]) {
            fileName = decodeURIComponent(match[1].replace(/['"]/g, ''));
          }
        }

        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        a.remove();
        URL.revokeObjectURL(url);
      });
  }
}
