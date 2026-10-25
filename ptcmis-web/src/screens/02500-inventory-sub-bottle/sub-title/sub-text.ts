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
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { StorageInOut } from '../../../entities/models/StorageInOut';

@Component({
  selector: 'sub-title-inventory-out',
  standalone: true,
  imports: [PipeTimestamp],
  template: `
    @if (!!model) {
      <span>{{ subTitle }}</span>
      <span class="mat-font-body-md">
        由
        <span class="mat-text-primary mat-font-body-lg">{{ model.applicantName }}</span>
        创建于
        <span class="mat-text-primary">{{ model.applicantTime | timestamp }}</span>
      </span>
    }
  `,
  styles: [':host { display: contents; }'],
})
export class CompSubTitle {
  @Input() subTitle = '';
  @Input() model: StorageInOut | null = null;
}
