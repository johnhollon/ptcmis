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
 * 瓶码打印记录明细 - CodePrintDetail
 * 数据库表：code_print_detail
 * 后端：chenhuiran.ptcmisserver.stubs.entities.CodePrintDetail
 */
export interface CodePrintDetail {
  /**
   * 所属瓶码打印记录。code_print.id
   * COLUMN: id_print, VARCHAR(32)
   */
  idPrint: string;

  /**
   * 瓶码
   * COLUMN: code, VARCHAR(11)
   */
  code: string;

  /**
   * 瓶码信息：品种编号（子品种）
   * COLUMN: variety_code, VARCHAR(32)
   */
  varietyCode: string;

  /**
   * 瓶码信息：外植体采集批次
   * COLUMN: explant_collection_batch, INT(10)
   */
  explantCollectionBatch: number;

  /**
   * 瓶码信息：代数
   * COLUMN: generation, TINYINT(3)
   */
  generation: number;

  /**
   * 瓶码信息：操作人姓名，user.name
   * COLUMN: operator_name, VARCHAR(32)
   */
  operatorName: string;

  /**
   * 瓶码信息：操作时间 UTC 毫秒时间戳
   * COLUMN: operate_time, BIGINT(19)
   */
  operateTime: number;
}

export const CodePrintDetailDefault: CodePrintDetail = {
  idPrint: '',
  code: '',
  varietyCode: '',
  explantCollectionBatch: 0,
  generation: 0,
  operatorName: '',
  operateTime: 0,
};
