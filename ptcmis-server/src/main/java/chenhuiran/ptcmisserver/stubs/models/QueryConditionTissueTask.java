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

@Data
public class QueryConditionTissueTask implements Serializable {
  // 关键字，涵盖范围：品种名称、子品种编码、任务创建人、任务质检人（被分配负责质检以及实际质检人员的姓名）、任务操作人（被分配负责操作以及实际操作人员的姓名）
  private String keyword;
  // 外植体采集批次
  private Integer explantCollectionBatch;
  // 任务类别：dictionary.tissue_task_type
  private String taskType;
  // 任务状态：dictionary.tissue_task_status
  private String taskStatus;
  // 任务结束时间范围： 任务结束时间 >= start <= end
  private ModelDateRange finishTime;
  // 任务创建时间范围： 任务结束时间 >= start <= end
  private ModelDateRange createTime;
  // 任务开始时间范围： 任务结束时间 >= start <= end
  private ModelDateRange startTime;
}
