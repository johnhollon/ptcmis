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
import { StorageOutTaskView, StorageOutTaskViewDefault } from '../../../entities/api/StorageOutTaskView';
import { CompArchive } from '../archive/archive';
import { StorageOutTaskLogs } from '../logs/comp';
import { StorageOutTaskRejBizDo } from '../rej-biz-do/comp';

@Component({
  standalone: true,
  selector: 'storage-out-task-rej-biz',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [ReactiveFormsModule, CompArchive, StorageOutTaskLogs, StorageOutTaskRejBizDo],
})
export class StorageOutTaskRejBiz {
  @Input() subModule = 'process';
  @Input() model: StorageOutTaskView = StorageOutTaskViewDefault;

  @Output() onCancel = new EventEmitter<void>();
  @Output() onRequestStartLoading = new EventEmitter<string>();
  @Output() onRequestStopLoading = new EventEmitter<void>();
  @Output() onSaved = new EventEmitter<void>();
}
