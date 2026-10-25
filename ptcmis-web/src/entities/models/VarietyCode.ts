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

// 品种编号（子品种信息）
import {APIConst} from '../api/const-api';

export interface VarietyCode {
  // 子品种编号
  code: string;
  // 所属品种
  variety: string;
  // 是否有效
  important: string;  
  // 是否有效
  enabled: string;
}

export const varietyCodeDefault: VarietyCode = {
  code: '',
  variety: '',
  important: APIConst.booleans.BOOLEAN_FALSE,
  enabled: APIConst.booleans.BOOLEAN_TRUE,
};
