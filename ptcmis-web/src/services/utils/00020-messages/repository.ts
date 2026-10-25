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
import {TYPES_MESSAGES} from '../../../entities/ui/types';

export const IJT_RepositoryMessages = new InjectionToken<RepositoryMessages>('RepositoryMessages');

export interface RepositoryMessages {
  /** 注册自定义错误解析器（新注册的优先匹配） */
  registerParser(parser: TYPES_MESSAGES.Parser): void;
  /** 提取解析后的消息文本（换行拼接） */
  getMessages(errorWarningInfoDebugOrString1: unknown, errorWarningInfoDebugOrString2?: unknown, errorWarningInfoDebugOrString3?: unknown): string;
  /** 解析各类错误，返回结构化的 MessageResult */
  calcMessages(errorWarningInfoDebugOrString1: unknown, errorWarningInfoDebugOrString2?: unknown, errorWarningInfoDebugOrString3?: unknown): TYPES_MESSAGES.Messages;
}
