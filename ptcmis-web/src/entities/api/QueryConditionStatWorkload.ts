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

export interface QueryConditionStatWorkload {
  userId: string;
  userRole: string;
  variety: string;
  varietyCode: string;
  explantCollectionBatch: number | null;
  generation: number | null;
  dateRange: ModelDateRange;
  dateType: '按月' | '按年' | '按季' | '自定义';
  detailShowType: '明细' | '分类';
}

export const QueryConditionStatWorkloadDefault: QueryConditionStatWorkload = {
  userId: '',
  userRole: 'operator',
  variety: '',
  varietyCode: '',
  explantCollectionBatch: null,
  generation: null,
  dateRange: { start: 0, end: 0 },
  dateType: '按月',
  detailShowType: '明细',
};
