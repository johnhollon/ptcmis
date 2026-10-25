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
 * 瓶体补码任务查询条件
 */
@Data
public class QueryConditionInventoryAddBottle implements Serializable {
  /**
   * 关键字
   */
  private String keyword;

  /**
   * 任务状态
   */
  private String taskStatus;

  /**
   * 母代代际，允许null
   */
  private Integer generation;

  /**
   * 外植体采集批次，允许null
   */
  private Integer explantCollectionBatch;

  /**
   * 创建时间范围
   */
  private ModelDateRange createTime;

  /**
   * 完成时间范围
   */
  private ModelDateRange finishTime;

}

