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

export interface StorageOutTaskView {
  //======== storage_in_out 出库单字段 ========
  id: string;
  inOut: string;
  bottlesApplied: number;
  plantsApplied: number;
  bottlesKeepInApplied: number;
  plantsKeepInApplied: number;
  bottlesActual: number;
  plantsActual: number;
  bottlesKeepInActual: number;
  plantsKeepInActual: number;
  bottlesVoided: number;
  plantsVoided: number;
  bottlesDiscard: number;
  plantsDiscard: number;
  reason: string;
  status: string;
  oppReason: string;
  idTask: string;
  idInspection: string;
  applicantId: string;
  applicantName: string;
  applicantTime: number;
  operatorId: string;
  operatorName: string;
  operatorTime: number;

  //======== tissue_task 组培任务左连接带出字段 ========
  variety: string;
  varietyCode: string;
  explantCollectionBatch: number;
  parentGeneration: number;
  childGeneration: number;
  taskType: string;
  taskStatus: string;
  taskCreatorUserId: string;
  taskCreatorUserName: string;
  taskCreateTime: number;
  taskStartTime: number;
  taskFinishTime: number;
}

/**
 * 默认空实例，表单初始化 / reset 使用
 * string → ""；number → 0
 */
export const StorageOutTaskViewDefault: StorageOutTaskView = {
  id: '',
  inOut: '',
  bottlesApplied: 0,
  plantsApplied: 0,
  bottlesKeepInApplied: 0,
  plantsKeepInApplied: 0,
  bottlesActual: 0,
  plantsActual: 0,
  bottlesKeepInActual: 0,
  plantsKeepInActual: 0,
  bottlesVoided: 0,
  plantsVoided: 0,
  bottlesDiscard: 0,
  plantsDiscard: 0,
  reason: '',
  status: '',
  oppReason: '',
  idTask: '',
  idInspection: '',
  applicantId: '',
  applicantName: '',
  applicantTime: 0,
  operatorId: '',
  operatorName: '',
  operatorTime: 0,

  variety: '',
  varietyCode: '',
  explantCollectionBatch: 0,
  parentGeneration: 0,
  childGeneration: 0,
  taskType: '',
  taskStatus: '',
  taskCreatorUserId: '',
  taskCreatorUserName: '',
  taskCreateTime: 0,
  taskStartTime: 0,
  taskFinishTime: 0,
};
