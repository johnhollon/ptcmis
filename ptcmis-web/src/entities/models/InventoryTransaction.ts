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
 * 库存流水
 * 数据库表：inventory_transaction
 */
export interface InventoryTransaction {
  /** 主键 */
  id: string;
  /** 业务类型(出库/入库/调差)。dictionary.inventory_tx_biz_type */
  bizType: string;
  /** 来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件 */
  tableName: string;
  /** 来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件 */
  recordId: string;
  /** 品种 */
  variety: string;
  /** 品种编号（子品种） */
  varietyCode: string;
  /** 外植体采集批次 */
  explantCollectionBatch: number;
  /** 代数 */
  generation: number;
  /** 瓶数。库存变小记作负数，变大记作正数。 */
  bottles: number;
  /** 株数。库存变小记作负数，变大记作正数。 */
  plants: number;
  /** 备注信息 */
  description: string;
  /** 操作人用户ID，user.id */
  operatorId: string;
  /** 操作人姓名，user.name */
  operatorName: string;
  /** 操作时间 UTC 毫秒时间戳 */
  operateTime: number;
}

export const InventoryTransactionDefault: InventoryTransaction = {
  id: '',
  bizType: '',
  tableName: '',
  recordId: '',
  variety: '',
  varietyCode: '',
  explantCollectionBatch: 0,
  generation: 0,
  bottles: 0,
  plants: 0,
  description: '',
  operatorId: '',
  operatorName: '',
  operateTime: 0,
};
