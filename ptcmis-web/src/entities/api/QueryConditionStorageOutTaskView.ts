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

export interface QueryConditionStorageOutTaskView {
  inOutType: string;

  keyword: string;

  status: string;
  reason: string;
  applicantTime: ModelDateRange;
  operatorTime: ModelDateRange;

  taskType: string;
  taskStatus: string;
  variety: string;
  varietyCode: string;

  parentGeneration: number | null;
}

export const QueryConditionStorageOutTaskViewDefault: QueryConditionStorageOutTaskView = {
  inOutType: '',

  keyword: '',

  status: '',
  reason: '',
  applicantTime: { start: 0, end: 0 },
  operatorTime: { start: 0, end: 0 },

  taskType: '',
  taskStatus: '',
  variety: '',
  varietyCode: '',

  parentGeneration: null,
};
