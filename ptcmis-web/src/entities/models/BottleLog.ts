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
 * 组培瓶操作日志
 * 数据库表： bottle_log
 */
export interface BottleLog {
  /** ID */
  id: string;
  /** 瓶码ID */
  idBottle: string;
  /** 执行时间 UTC毫秒 */
  oppTime: number;
  /** 数据库表名。可根据 table_name, record_id 在 archive 表中查询对应附件 */
  tableName: string;
  /** 数据库主键ID。可根据 table_name, record_id 在 archive 表中查询对应附件 */
  recordId: string;
  /** 业务类型，文本型描述 */
  bizType: string;
  /** 业务备注，文本型描述 */
  description: string;
  /** 质检员ID。 user.id */
  inspectorId: string;
  /** 质检员姓名。 user.name */
  inspectorName: string;
  /** 操作员ID。 user.id */
  operatorId: string;
  /** 操作员姓名。 user.name */
  operatorName: string;
  /** 操作状态。 dictionary.bottle_operate_status */
  operateStatus: string;
  /** 仓储状态。 dictionary.bottle_storage_status */
  storageStatus: string;
  /** 末次出库原因。 dictionary.storage_reason */
  storageReason: string;
  /** 是否淘汰。dictionary = boolean */
  discarded: string;
  /** 淘汰原因。 dictionary.bottle_discard_reason */
  discardReason: string;
}
