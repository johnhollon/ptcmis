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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageOutTaskView implements Serializable {
  //======== storage_in_out 出库单字段 ========
  private String id;
  private String inOut;
  private Integer bottlesApplied;
  private Integer plantsApplied;
  private Integer bottlesKeepInApplied;
  private Integer plantsKeepInApplied;
  private Integer bottlesActual;
  private Integer plantsActual;
  private Integer bottlesKeepInActual;
  private Integer plantsKeepInActual;
  private Integer bottlesDiscard;
  private Integer plantsDiscard;
  private Integer bottlesVoided;
  private Integer plantsVoided;
  private String reason;
  private String status;
  private String oppReason;
  private String idTask;
  private String idInspection;
  private String applicantId;
  private String applicantName;
  private Long applicantTime;
  private String operatorId;
  private String operatorName;
  private Long operatorTime;

  //======== tissue_task 组培任务左连接带出字段 ========
  private String variety;
  private Integer explantCollectionBatch;
  private String taskType;
  private String taskStatus;
  private String taskCreatorUserId;
  private String taskCreatorUserName;
  private Long taskCreateTime;
  private Long taskStartTime;
  private Long taskFinishTime;
}
