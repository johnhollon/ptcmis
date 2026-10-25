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

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import { Bottle } from '../../../entities/models/Bottle';
import {
  LayoutDrawerOppSimple
} from '../../../components/00050-layouts/00200-layout-page-function-simple/layouts/drawer-opp-simple/layout';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { MatTooltip } from '@angular/material/tooltip';
import { MatDivider } from '@angular/material/list';
import { PipeBottleStorageStatus } from '../../../components/00200-pipes/pipe-bottle-storage-status';
import { PipeStorageReason } from '../../../components/00200-pipes/pipe-storage-reason';

@Component({
  selector: 'editor-query-bottle',
  standalone: true,
  templateUrl: 'editor.html',
  styleUrls: ['editor.scss'],
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    MatButton,
    LayoutDrawerOppSimple,
    PipeTimestamp,
    MatTooltip,
    MatDivider,
    PipeBottleStorageStatus,
    PipeStorageReason,
  ],
})
export class CompEditor {
  @Output() onCancel = new EventEmitter<void>();
  @Output() onExport = new EventEmitter<void>();
  @Input() model: Bottle = null as unknown as Bottle;

  protected requestExport() {
    this.onExport.emit();
  }
}
