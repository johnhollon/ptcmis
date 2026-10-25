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

/**
 * 库存增瓶任务明细表
 * 全部字段禁止 null / undefined，驼峰命名
 */
export interface InventoryAddBottleDetail {
  id: string;
  idTask: string;
  idParent: string;
  plantCount: number;
  inspectorId: string;
  inspectorName: string;
  operatorId: string;
  operatorName: string;
  operatorTime: number;
  status: string;
}

/**
 * InventoryAddBottleDetail 默认常量，接口名 + Default
 */
export const InventoryAddBottleDetailDefault: InventoryAddBottleDetail = {
  id: '',
  idTask: '',
  idParent: '',
  plantCount: 0,
  inspectorId: '',
  inspectorName: '',
  operatorId: '',
  operatorName: '',
  operatorTime: 0,
  status: 'printed',
};
