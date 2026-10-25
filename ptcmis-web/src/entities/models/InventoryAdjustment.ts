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
 * 盘点调差 - InventoryAdjustment
 * 数据库表：inventory_adjustment
 * 对应后端：chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustment
 */
export interface InventoryAdjustment {
  /**
   * 调差ID
   * COLUMN: id, VARCHAR(32)
   */
  id: string;

  /**
   * 品种 variety.name
   * COLUMN: variety, VARCHAR(32)
   */
  variety: string;

  /**
   * 品种编号（子品种） variety_code.code
   * COLUMN: variety_code, VARCHAR(32)
   */
  varietyCode: string;

  /**
   * 代数
   * COLUMN: generation, TINYINT(3)
   */
  generation: number;

  /**
   * 任务状态：dictionary.inventory_adjustment_status
   * COLUMN: status, VARCHAR(32), 默认 "created"
   */
  status: string;

  /**
   * 调差瓶数。变小记作负数，变大记作正数。
   * COLUMN: bottles, INT(10)
   */
  bottles: number;

  /**
   * 调差株数。变小记作负数，变大记作正数。
   * COLUMN: plants, INT(10)
   */
  plants: number;

  /**
   * 调差原因
   * COLUMN: reason, VARCHAR(128)
   */
  reason: string;

  /**
   * 操作人用户ID，user.id
   * COLUMN: operator_id, VARCHAR(32)
   */
  operatorId: string;

  /**
   * 操作人姓名，user.name
   * COLUMN: operator_name, VARCHAR(32)
   */
  operatorName: string;

  /**
   * 操作时间 UTC 毫秒时间戳
   * COLUMN: operate_time, BIGINT(19)
   */
  operateTime: number;
}

export const InventoryAdjustmentDefault: InventoryAdjustment = {
  /**
   * 调差ID
   * COLUMN: id, VARCHAR(32)
   */
  id: '',

  /**
   * 品种 variety.name
   * COLUMN: variety, VARCHAR(32)
   */
  variety: '',

  /**
   * 品种编号（子品种） variety_code.code
   * COLUMN: variety_code, VARCHAR(32)
   */
  varietyCode: '',

  /**
   * 代数
   * COLUMN: generation, TINYINT(3)
   */
  generation: 0,

  /**
   * 任务状态：dictionary.inventory_adjustment_status
   * COLUMN: status, VARCHAR(32), 默认 "created"
   */
  status: '',

  /**
   * 调差瓶数。变小记作负数，变大记作正数。
   * COLUMN: bottles, INT(10)
   */
  bottles: 0,

  /**
   * 调差株数。变小记作负数，变大记作正数。
   * COLUMN: plants, INT(10)
   */
  plants: 0,

  /**
   * 调差原因
   * COLUMN: reason, VARCHAR(128)
   */
  reason: '',

  /**
   * 操作人用户ID，user.id
   * COLUMN: operator_id, VARCHAR(32)
   */
  operatorId: '',

  /**
   * 操作人姓名，user.name
   * COLUMN: operator_name, VARCHAR(32)
   */
  operatorName: '',

  /**
   * 操作时间 UTC 毫秒时间戳
   * COLUMN: operate_time, BIGINT(19)
   */
  operateTime: 0,
};
