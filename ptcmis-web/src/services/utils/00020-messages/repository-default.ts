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

import {TYPES_MESSAGES} from '../../../entities/ui/types';
import {parserAxiosError} from './datas/parser-axios-error';
import {parserCustomHttpError} from './datas/parser-custom-http-error';
import {parserStatsError} from './datas/parser-stats-error';
import {parserViteError} from './datas/parser-vite-error';
import {RepositoryMessages} from './repository';

export class RepositoryMessagesDefault implements RepositoryMessages {
  private parsers: TYPES_MESSAGES.Parser[] = [];

  constructor() {
    this.registerParser(parserStatsError);
    this.registerParser(parserAxiosError);
    this.registerParser(parserCustomHttpError);
    this.registerParser(parserViteError);
  }

  registerParser(parser: TYPES_MESSAGES.Parser): void {
    this.parsers.unshift(parser);
  }

  getMessages(errorWarningInfoDebugOrString1: unknown, errorWarningInfoDebugOrString2?: unknown, errorWarningInfoDebugOrString3?: unknown): string {
    const {
      errors,
      warnings,
      infos
    } = this.calcMessages(errorWarningInfoDebugOrString1, errorWarningInfoDebugOrString2, errorWarningInfoDebugOrString3);
    const all = [...errors, ...warnings, ...infos];
    return all.length > 0 ? all.join('\n') : '';
  }

  calcMessages(errorWarningInfoDebugOrString1: unknown, errorWarningInfoDebugOrString2?: unknown, errorWarningInfoDebugOrString3?: unknown) {
    const args = [errorWarningInfoDebugOrString1, errorWarningInfoDebugOrString2, errorWarningInfoDebugOrString3].filter(
      (arg) => arg !== undefined && arg !== null
    );

    let prefix = '';
    const rawErrors: string[] = [];
    const rawWarnings: string[] = [];
    const rawInfos: string[] = [];

    for (const arg of args) {
      if (typeof arg === 'string') {
        prefix = arg;
        rawInfos.push(arg); // 字符串先作为普通信息暂存
      } else {
        const res = this.parseSingleArg(arg, prefix);
        rawErrors.push(...res.errors);
        rawWarnings.push(...res.warnings);
        rawInfos.push(...res.infos);
      }
    }

    // 去重处理
    const errors = Array.from(new Set(rawErrors));
    const warnings = Array.from(new Set(rawWarnings));
    let infos = Array.from(new Set(rawInfos));

    // 关键逻辑：只有当没有任何 error 和 warning 时，才保留 infos
    if (errors.length > 0 || warnings.length > 0) {
      infos = [];
    }

    return {errors, warnings, infos};
  }

  /** 单个参数解析流程 */
  private parseSingleArg(arg: any, prefix?: string): TYPES_MESSAGES.Messages {
    if (!arg) return {errors: [], warnings: [], infos: []};

    // 1. 遍历注册表中的 ErrorParser
    for (const parser of this.parsers) {
      const result = parser(arg);
      if (result) {
        return this.applyPrefixToResult(result, prefix);
      }
    }

    // 2. 通用 Error 兜底解析
    const genericResult = this.parseGenericError(arg);
    return this.applyPrefixToResult(genericResult, prefix);
  }

  /** 给解析出的各种消息统一挂载前缀 */
  private applyPrefixToResult(result: TYPES_MESSAGES.Messages, prefix?: string): TYPES_MESSAGES.Messages {
    if (!prefix) return result;

    const addPrefix = (m: string) => `[${prefix}] ${m}`;
    return {
      errors: result.errors.map(addPrefix),
      warnings: result.warnings.map(addPrefix),
      infos: result.infos.map(addPrefix)
    };
  }

  /** 通用/标准 Error 兜底解析逻辑 */
  private parseGenericError(errorWarningInfoDebugOrString: any): TYPES_MESSAGES.Messages {
    const errors: string[] = [];
    const warnings: string[] = [];
    const infos: string[] = [];

    if (errorWarningInfoDebugOrString instanceof Error) {
      errors.push(errorWarningInfoDebugOrString.stack || `${errorWarningInfoDebugOrString.name}: ${errorWarningInfoDebugOrString.message}`);
    } else if (typeof errorWarningInfoDebugOrString === 'object') {
      const errList = errorWarningInfoDebugOrString.errors ?? errorWarningInfoDebugOrString.error;
      const warnList = errorWarningInfoDebugOrString.warnings ?? errorWarningInfoDebugOrString.warning;

      if (errList) {
        errors.push(typeof errList === 'string' ? errList : JSON.stringify(errList, null, 2));
      }
      if (warnList) {
        warnings.push(typeof warnList === 'string' ? warnList : JSON.stringify(warnList, null, 2));
      }

      const msg = errorWarningInfoDebugOrString.message ?? errorWarningInfoDebugOrString.msg;
      if (msg && typeof msg === 'string') {
        // 如果对象本身有 errList 或 warnList，就把 msg 归入对应列表，否则归入 errors
        if (errList) errors.push(msg);
        else if (warnList) warnings.push(msg);
        else errors.push(msg);
      } else if (!errList && !warnList) {
        try {
          errors.push(JSON.stringify(errorWarningInfoDebugOrString, null, 2));
        } catch {
          errors.push(Object.prototype.toString.call(errorWarningInfoDebugOrString));
        }
      }
    } else if (typeof errorWarningInfoDebugOrString === 'string') {
      errors.push(errorWarningInfoDebugOrString);
    } else {
      // 基础数据类型
      errors.push(JSON.stringify(errorWarningInfoDebugOrString));
    }

    return {errors, warnings, infos};
  }
}
