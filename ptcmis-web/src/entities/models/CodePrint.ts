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
 * 瓶码打印记录 - CodePrint
 * 数据库表：code_print
 * 后端：chenhuiran.ptcmisserver.stubs.entities.CodePrint
 */
export interface CodePrint {
  /**
   * 主键
   * COLUMN: id, VARCHAR(32)
   */
  id: string;

  /**
   * 来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件
   * COLUMN: table_name, VARCHAR(32)
   */
  tableName: string;

  /**
   * 来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件
   * COLUMN: record_id, VARCHAR(32)
   */
  recordId: string;

  /**
   * 备注信息
   * COLUMN: description, VARCHAR(128)
   */
  description: string;

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

export const CodePrintDefault: CodePrint = {
  id: '',
  tableName: '',
  recordId: '',
  description: '',
  operatorId: '',
  operatorName: '',
  operateTime: 0,
};
