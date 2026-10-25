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

import {InjectionToken} from '@angular/core';
import {Observable} from 'rxjs';

export const IJT_DataBottleCodeSequence = new InjectionToken<DataBottleCodeSequence>('DataBottleCodeSequence')

// 瓶码前缀计数器。六位大写英文字母。
export interface DataBottleCodeSequence {
  // 获取下一个序列编码，计数器自动 +1
  next(): Observable<string>;
  // 查看当前编码，计数器不自增
  peek(): Observable<string>;
}
