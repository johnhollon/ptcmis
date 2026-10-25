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

const RE_ASCII = /^[\x00-\x7F]*$/;

export function binarySearchInsertPos(arr: string[], target: string): number {
  let low = 0;
  let high = arr.length;
  while (low < high) {
    const mid = (low + high) >>> 1;
    const item = arr[mid];
    let cmp: number;
    if (RE_ASCII.test(target) && RE_ASCII.test(item)) {
      cmp = target < item ? -1 : target > item ? 1 : 0;
    } else {
      cmp = target.localeCompare(item);
    }
    if (cmp < 0) high = mid;
    else if (cmp > 0) low = mid + 1;
    else return -1;
  }
  return low;
}
