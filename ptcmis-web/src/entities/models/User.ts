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

// 用户（员工信息）
import {genId} from '../../services/utils/00010-id';
import {APIConst} from '../api/const-api';

export interface User {
  // 用户ID
  id: string;
  // 姓名
  name: string;
  // 编号
  code: string;
  // 手机号码
  mobile: string;
  // 密码
  password: string;
  // 是否有效
  enabled: string;
  // 系统角色。可以为''，表示无权使用系统。
  role: Role;
}

export type Condition = {
  keyword: string;
  role: Role | '',
};

export type Role = 'admin' | 'user' | 'none';

export const userDefault: User = {
  id: genId(),
  name: '',
  code: '',
  mobile: '',
  password: '',
  enabled: APIConst.booleans.BOOLEAN_TRUE,
  role: 'none',
}
