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

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'storageReason',
})
export class PipeStorageReason implements PipeTransform {
  transform(value: string | undefined | null): unknown {
    if (value === 'induction') return '诱导任务';
    if (value === 'differentiation') return '分化任务';
    if (value === 'multiplication') return '增殖任务';
    if (value === 'rooting') return '生根任务';
    if (value === 'acclimatization') return '炼苗任务';
    if (value === 'code_complement') return '瓶体补码';
    if (value === 'inventory_add') return '库存增瓶';
    if (value === 'inventory_sub') return '库存减瓶';
    if (value === 'inspection_discard') return '质检淘汰';
    return '';
  }
}
