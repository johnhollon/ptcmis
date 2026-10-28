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

// 组培任务操作分配组
export interface TissueTaskOppGroup {
  // 操作分配组ID
  id: string;
  // 所属任务ID
  idTask: string;
  varietyCode: string;
  parentPlantCount: number;
  idParentBottle: string;
  parentKeep: string;
  parentKeepPlantCount: number;
  // 分配人ID（员工ID）
  allocatorId: string;
  // 分配人姓名
  allocatorName: string;
  // 分配时间（UTC毫秒）
  allocateTime: number;
  // 完成时间 UTC毫秒
  finishTime: number;
  // 被分配质检员ID
  assigneeInspectorId: string;
  // 被分配质检员姓名
  assigneeInspectorName: string;
  // 被分配操作员ID
  assigneeOperatorId: string;
  // 被分配操作员姓名
  assigneeOperatorName: string;
  // 实际质检员ID
  inspectorId: string;
  // 实际质检员姓名
  inspectorName: string;
  // 实际操作员ID
  operatorId: string;
  // 实际操作员姓名
  operatorName: string;
}
