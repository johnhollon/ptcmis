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

import { ModelDateRange } from '../../components/00500-form-fields/date-range';

function getDefaultDateRange(dateType: '按月' | '按季' | '按年' | '自定义'): ModelDateRange {
  const now = new Date();
  const y = now.getFullYear();
  const m = now.getMonth();
  switch (dateType) {
    case '按月':
      return {
        start: new Date(y, m, 1, 0, 0, 0).getTime(),
        end: new Date(y, m + 1, 1, 0, 0, 0).getTime() - 1,
      };
    case '按季': {
      const qStartMonth = Math.floor(m / 3) * 3;
      return {
        start: new Date(y, qStartMonth, 1, 0, 0, 0).getTime(),
        end: new Date(y, qStartMonth + 3, 1, 0, 0, 0).getTime() - 1,
      };
    }
    case '按年':
      return {
        start: new Date(y, 0, 1, 0, 0, 0).getTime(),
        end: new Date(y + 1, 0, 1, 0, 0, 0).getTime() - 1,
      };
    case '自定义':
    default:
      return { start: 0, end: 0 };
  }
}
/**
 * 根据年份，获取【按年】时间范围
 * @param year 例如 2026
 */
export function getYearDateRange(year: number): ModelDateRange {
  const start = new Date(year, 0, 1, 0, 0, 0).getTime();
  const end = new Date(year + 1, 0, 1, 0, 0, 0).getTime() - 1;
  return { start, end };
}

/**
 * 根据年月Date对象，获取【按月】时间范围
 * @param date 只读取年份+月份，日期忽略
 */
export function getMonthDateRange1(date: Date): ModelDateRange {
  const y = date.getFullYear();
  const m = date.getMonth();
  const start = new Date(y, m, 1, 0, 0, 0).getTime();
  const end = new Date(y, m + 1, 1, 0, 0, 0).getTime() - 1;
  return { start, end };
}

/**
 * 根据年份 + 季度号(1/2/3/4) 获取【按季度】时间范围
 * @param year 年份，如2026
 * @param quarter 季度：1,2,3,4
 */
export function getQuarterDateRange(year: number, quarter: 1 | 2 | 3 | 4): ModelDateRange {
  const qStartMonth = (quarter - 1) * 3;
  const start = new Date(year, qStartMonth, 1, 0, 0, 0).getTime();
  const end = new Date(year, qStartMonth + 3, 1, 0, 0, 0).getTime() - 1;
  return { start, end };
}
/**
 * 根据【年、月整数】计算月份时间范围
 * @param year 年份，如 2026
 * @param month 月份，**自然月 1~12**（不是js的0起始）
 */
export function getMonthDateRange(year: number, month: number): ModelDateRange {
  // JS Date month: 0=1月，所以减1
  const jsMonth = month - 1;
  const start = new Date(year, jsMonth, 1, 0, 0, 0).getTime();
  const end = new Date(year, jsMonth + 1, 1, 0, 0, 0).getTime() - 1;
  return { start, end };
}
/**
 * 获取当前日期对应的季度，返回 1 | 2 | 3 | 4
 */
export function getCurrentQuarter(): 1 | 2 | 3 | 4 {
  const now = new Date();
  const month = now.getMonth(); // 0~11
  return (Math.floor(month / 3) + 1) as 1 | 2 | 3 | 4;
}

/**
 * 获取当前日期对应的月份（自然月，1~12）
 */
export function getCurrentMonth(): number {
  const now = new Date();
  return now.getMonth() + 1;
}

/**
 * 获取当前日期对应的年份
 */
export function getCurrentYear(): number {
  const now = new Date();
  return now.getFullYear();
}
/**
 * 判断数字是否为 YYYYMMDD 格式的8位合法日期
 * @param num 待校验正数
 * @returns boolean
 */
export function isYmd8Number(num: number): boolean {
  // 1. 必须是整数、正数
  if (!Number.isInteger(num) || num <= 0) return false;

  const str = num.toString();
  // 2. 严格8位
  if (str.length !== 8) return false;

  // 3. 拆分年月日
  const year = Number(str.slice(0, 4));
  const month = Number(str.slice(4, 6));
  const day = Number(str.slice(6, 8));

  // 月份范围
  if (month < 1 || month > 12) return false;
  // 日期范围简单兜底 + 使用Date校验合法性（自动处理闰月、大小月）
  const date = new Date(year, month - 1, day);
  return (
    date.getFullYear() === year &&
    date.getMonth() === month - 1 &&
    date.getDate() === day
  );
}
