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


/** Axios / HTTP 请求错误解析插件 */
export const parserAxiosError: TYPES_MESSAGES.Parser = (error: any) => {
  const isAxios = error?.isAxiosError || (error?.config && error?.response);
  if (!isAxios) return null;

  const status = error.response?.status;
  const statusText = error.response?.statusText || '';
  const url = error.config?.url || 'Unknown URL';
  const serverData = error.response?.data;

  let msg = status
    ? `[HTTP ${status} ${statusText}] Request failed: ${url}`
    : `[Network Error] ${error.message} (${url})`;

  if (serverData) {
    const detail = typeof serverData === 'string' ? serverData : JSON.stringify(serverData, null, 2);
    msg += `\nResponse Data: ${detail}`;
  }

  // 根据 HTTP 状态码或网络错误类型分类放入 errors 或 warnings
  // 4xx / 5xx / 网络失败默认放入 errors
  return {
    errors: [msg],
    warnings: [],
    infos: []
  };
};
