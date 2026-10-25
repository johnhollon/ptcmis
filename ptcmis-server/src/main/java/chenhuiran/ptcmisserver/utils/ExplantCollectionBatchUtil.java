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
 *
 */

package chenhuiran.ptcmisserver.utils;

public class ExplantCollectionBatchUtil {
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static boolean check(long num) {
    if (num < 0) {
      return false;
    }
    if (num == 0) return true;
    String str = String.valueOf(num);
    if (str.length() != 8) {
      return false;
    }
    int year, month, day;
    try {
      year = Integer.parseInt(str.substring(0, 4));
      month = Integer.parseInt(str.substring(4, 6));
      day = Integer.parseInt(str.substring(6, 8));
    } catch (NumberFormatException e) {
      return false;
    }
    try {
      java.time.LocalDate.of(year, month, day);
      return true;
    } catch (java.time.DateTimeException e) {
      return false;
    }
  }
}
