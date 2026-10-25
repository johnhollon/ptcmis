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
  name: 'timestamp',
})
export class PipeTimestamp implements PipeTransform {
  /**
   * 毫秒时间戳格式化
   * @param value 毫秒时间戳 number|null|undefined
   * @param format 格式模板 yyyy‑MM‑dd HH:mm:ss
   * @param keepYear 若年份等于当前年，是否保留4位年份。
   */
  transform(
    value: number | undefined | null,
    format = 'yyyy-MM-dd HH:mm:ss',
    keepYear = true,
  ): string | null | undefined {
    if (!value && value !== 0) return '';
    if (typeof value !== 'number') return '';
    if (value === 0) return '';

    const date = new Date(value);

    const y = date.getUTCFullYear();
    const M = this.pad(date.getMonth() + 1);
    const d = this.pad(date.getDate());
    const H = this.pad(date.getHours());
    const m = this.pad(date.getMinutes());
    const s = this.pad(date.getSeconds());

    return format
      .replace('yyyy', String(y))
      .replace('MM', M)
      .replace('dd', d)
      .replace('HH', H)
      .replace('mm', m)
      .replace('ss', s);
  }

  private pad(n: number) {
    return String(n).padStart(2, '0');
  }
}
