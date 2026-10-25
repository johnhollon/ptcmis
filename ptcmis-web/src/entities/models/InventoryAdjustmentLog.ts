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
 * 盘点调差操作日志 - InventoryAdjustmentLog
 * 数据库表：inventory_adjustment_log
 * 对应后端：chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentLog
 */
export interface InventoryAdjustmentLog {
  /**
   * ID主键
   * COLUMN: id, VARCHAR(32)
   */
  id: string;

  /**
   * 调差ID. inventory_adjustment.id
   * COLUMN: id_adjustment, VARCHAR(32)
   */
  idAdjustment: string;

  /**
   * 状态。dictionary = inventory_adjustment_status
   * COLUMN: status, VARCHAR(32), 默认 "created"
   */
  status: string;

  /**
   * 操作人ID，user.id
   * COLUMN: operator_id, VARCHAR(32)
   */
  operatorId: string;

  /**
   * 操作人姓名，user.name
   * COLUMN: operator_name, VARCHAR(32)
   */
  operatorName: string;

  /**
   * 操作时间，UTC 毫秒
   * COLUMN: operator_time, BIGINT(19)
   */
  operatorTime: number;
}
