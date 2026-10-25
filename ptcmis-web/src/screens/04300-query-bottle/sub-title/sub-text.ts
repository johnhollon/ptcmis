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

import { Component, Input } from '@angular/core';
import { MatTooltip } from '@angular/material/tooltip';
import { Bottle } from '../../../entities/models/Bottle';
import { PipeStorageReason } from '../../../components/00200-pipes/pipe-storage-reason';

@Component({
  selector: 'sub-title-query-bottle',
  standalone: true,
  imports: [MatTooltip, PipeStorageReason],
  template: `
    @if (!!model) {
      <span>{{ subTitle }}</span>
      <span class="mat-text-primary mat-font-body-lg">{{ model.id }}</span>
      <span
        class="mat-text-primary mat-font-body-lg"
        style="max-width: 180px;display:inline-block;overflow: hidden;text-overflow: ellipsis;margin:0 2px;"
        matTooltip="{{ model.variety + ' - ' + model.varietyCode }}"
      >
        {{ model.varietyCode }}
      </span>
      <span>
        <span class="mat-text-primary mat-font-headline-sm" matTooltip="代数"
        >[D{{ model.generation }}]</span
        >
      </span>
      <span>
        <span class="mat-text-primary mat-font-headline-sm" matTooltip="批次"
        >[P{{ model.explantCollectionBatch || '00000000' }}]</span
        >
      </span>
      <span>
        <span class="mat-text-primary" matTooltip="建瓶来源">{{
            model.sourceType | storageReason
          }}</span>
      </span>
    }
  `,
  styles: [':host { display: contents; }'],
})
export class CompSubTitle {
  @Input() subTitle = '';
  @Input() model: Bottle | null = null;
}
