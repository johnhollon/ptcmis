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

export interface QueryConditionStorageInOut {
  // 产生出入库业务的组培任务ID。tissue_task.id。
  idTask: string;
  // 产生出入库业务的质检任务ID。inspection_task.id。
  idInspection: string;
  // 人员姓名，搜索范围，申请人姓名、末次操作人姓名
  name: string;
  // 出入库状态
  status: string;
  // 申请时间
  applicantTime: ModelDateRange;
  // 末次操作时间
  operatorTime: ModelDateRange;
}

export const QueryConditionStorageInOutDefault: QueryConditionStorageInOut = {
  idTask: '',
  idInspection: '',
  // 人员姓名，搜索范围，申请人姓名、末次操作人姓名
  name: '',
  // 出入库状态
  status: '',
  // 申请时间
  applicantTime: { start: null, end: null },
  // 末次操作时间
  operatorTime: { start: null, end: null },
};
