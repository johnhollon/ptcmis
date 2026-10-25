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
import { InventoryAddBottle } from '../../../entities/models/InventoryAddBottle';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';

@Component({
  selector: 'sub-title-inventory-add-bottle',
  standalone: true,
  imports: [MatTooltip, PipeTimestamp, PipeTimestamp],
  template: `
    @if (!!model && !!model) {
      <span>{{ subTitle }}</span>
      <span
        class="mat-text-primary mat-font-body-lg"
        style="max-width: 180px;display:inline-block;overflow: hidden;text-overflow: ellipsis;margin:0 2px;"
        matTooltip="{{ model.variety + ' - ' + model.varietyCode }}"
      >
        {{ model.varietyCode }}
      </span>
      <span class="mat-text-primary mat-font-headline-sm" matTooltip="外植体采集批次">
        [P-{{ model.explantCollectionBatch || '00000000' }}]
      </span>
      <span class="mat-text-primary mat-font-headline-sm" matTooltip="代数">
        [G-{{ model.generation }}]
      </span>
      <span class="mat-font-body-md">
        由
        <span class="mat-text-primary mat-font-body-lg">{{ model.creatorName }}</span>
        创建于
        <span class="mat-text-primary">{{ model.createTime | timestamp }}</span>
      </span>
    }
  `,
  styles: [':host { display: contents; }'],
})
export class CompSubTitle {
  @Input() subTitle = '';
  @Input() model: InventoryAddBottle | null = null;
}
