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
 *
 */

package chenhuiran.ptcmisserver.stubs.models;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件
 */
@Data
public class QueryConditionBottle implements Serializable {
  // 关键字，涵盖范围：编号、品种名称、子品种编码、操作人
  private String keyword;
  // 外植体采集批次
  private Integer explantCollectionBatch;
  // 代数
  private Integer generation;
  private String innerVariety;
  private String innerVarietyCode;
  // 是否已淘汰
  private String discarded;
  // 建瓶时间
  private ModelDateRange taskTime;
  // 仓储状态
  private String currentStorageStatus;
}

