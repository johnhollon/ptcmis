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
public class QueryConditionStorageInOut implements Serializable {
  // 产生出入库业务的组培任务ID。tissue_task.id。
  private String idTask;
  // 产生出入库业务的质检任务ID。inspection_task.id。
  private String idInspection;
  // 人员姓名，搜索范围，申请人姓名、末次操作人姓名
  private String name;
  // 出入库状态
  private String status;
  // 申请时间
  private ModelDateRange applicantTime;
  // 末次操作时间
  private ModelDateRange operatorTime;
}
