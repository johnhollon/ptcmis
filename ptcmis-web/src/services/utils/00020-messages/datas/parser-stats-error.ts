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

import {TYPES_MESSAGES} from '../../../../entities/ui/types';


/** Webpack / Rspack Stats 编译结果解析插件 */
export const parserStatsError: TYPES_MESSAGES.Parser = (stats: any) => {
  if (!stats || typeof stats.hasErrors !== 'function' || typeof stats.toJson !== 'function') {
    return null;
  }

  const errors: string[] = [];
  const warnings: string[] = [];
  const infos: string[] = [];

  try {
    const hasErrors = Boolean(stats.hasErrors && stats.hasErrors());
    const hasWarnings = Boolean(stats.hasWarnings && stats.hasWarnings());

    const info = stats.toJson({errors: true, warnings: true, all: false});

    if (hasErrors) {
      if (Array.isArray(info?.errors) && info.errors.length > 0) {
        info.errors.forEach((err: any, index: number) => {
          const detail = typeof err === 'string' ? err : err.message || JSON.stringify(err, null, 2);
          errors.push(`[Webpack 错误 ${index + 1}/${info.errors.length}]\n${detail}`);
        });
      } else {
        errors.push('[Webpack] 发生未知编译错误。');
      }
    }

    if (hasWarnings) {
      if (Array.isArray(info?.warnings) && info.warnings.length > 0) {
        info.warnings.forEach((warn: any, index: number) => {
          const detail = typeof warn === 'string' ? warn : warn.message || JSON.stringify(warn, null, 2);
          warnings.push(`[Webpack 警告 ${index + 1}/${info.warnings.length}]\n${detail}`);
        });
      }
    }
  } catch (e) {
    errors.push(`解析 Stats 详情失败: ${e instanceof Error ? e.message : String(e)}`);
  }

  return {errors, warnings, infos};
};
