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

import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  standalone: true,
  name: 'role',
})
export class PipeRole implements PipeTransform {
  transform(value: string | undefined | null): unknown {
    if (value === 'admin') return '超级管理员';
    if (value === 'query') return '仅开放查询权限';
    if (value === 'inventory') return '仓库管理权限';
    if (value === 'task') return '任务执行权限';
    if (value === 'task_and_inventory') return '仓库管理及任务执行权限';
    if (value === 'none') return '无系统使用权限';
    return '无系统使用权限';
  }
}
