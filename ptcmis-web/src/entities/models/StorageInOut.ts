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

// 出入库单 StorageInOut
import { TissueTaskView } from '../api/TissueTaskView';
import { genId } from '../../services/utils/00010-id';

export interface StorageInOut {
  // ID主键
  id: string;
  // 出入库类别，标记出库还是入库。 dictionary = storage_in_out_type
  inOut: string;
  // 申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）
  bottlesApplied: number;
  // 申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）
  plantsApplied: number;
  // 申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）
  bottlesKeepInApplied: number;
  // 申请入库株数中母代留种株数（是 plants_applied 的其中数）
  plantsKeepInApplied: number;
  // 实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）
  bottlesActual: number;
  // 实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）
  plantsActual: number;
  // 实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）
  bottlesKeepInActual: number;
  // 实际入库株数中母代留种株数（是 plants_actual 的其中数）
  plantsKeepInActual: number;
  // 是否在统计中忽略该入库单。 dictionary = boolean
  ignoreStats: string;
  // 出入库原因。 dictionary = storage_reason
  reason: string;
  // 状态。 dictionary = storage_in_out_status
  status: string;
  // 操作原因。 驳回、作废原因（手工填写）
  oppReason: string;
  // 产生出入库业务的组培任务ID。tissue_task.id。
  idTask: string;
  // 产生出入库业务的质检任务ID。inspection_task.id。
  idInspection: string;
  // 申请人ID，user.id
  applicantId: string;
  // 申请人姓名，user.name
  applicantName: string;
  // 申请人时间，UTC 毫秒
  applicantTime: number;
  // 末次操作人ID，user.id
  operatorId: string;
  // 末次操作人姓名，user.name
  operatorName: string;
  // 末次操作时间，UTC 毫秒
  operatorTime: number;
}

export function getDefaultStorageInOutForTask(task: TissueTaskView, inOut: string): StorageInOut {
  return {
    // ID主键
    id: genId(),
    // 出入库类别，标记出库还是入库。 dictionary = storage_in_out_type
    inOut: inOut,
    // 申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）
    bottlesApplied: 0,
    // 申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）
    plantsApplied: 0,
    // 申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）
    bottlesKeepInApplied: 0,
    // 申请入库株数中母代留种株数（是 plants_applied 的其中数）
    plantsKeepInApplied: 0,
    // 实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）
    bottlesActual: 0,
    // 实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）
    plantsActual: 0,
    // 实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）
    bottlesKeepInActual: 0,
    // 实际入库株数中母代留种株数（是 plants_actual 的其中数）
    plantsKeepInActual: 0,
    // 是否在统计中忽略该入库单。 dictionary = boolean
    ignoreStats: '',
    // 出入库原因。 dictionary = storage_reason
    reason: '',
    // 状态。 dictionary = storage_in_out_status
    status: '',
    // 操作原因。 驳回、作废原因（手工填写）
    oppReason: '',
    // 产生出入库业务的组培任务ID。tissue_task.id。
    idTask: task.taskId,
    // 产生出入库业务的质检任务ID。inspection_task.id。
    idInspection: '',
    // 申请人ID，user.id
    applicantId: '',
    // 申请人姓名，user.name
    applicantName: '',
    // 申请人时间，UTC 毫秒
    applicantTime: 0,
    // 末次操作人ID，user.id
    operatorId: '',
    // 末次操作人姓名，user.name
    operatorName: '',
    // 末次操作时间，UTC 毫秒
    operatorTime: 0,
  };
}

export const DefaultStorageInOut: StorageInOut = {
  // ID主键
  id: genId(),
  // 出入库类别，标记出库还是入库。 dictionary = storage_in_out_type
  inOut: '',
  // 申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）
  bottlesApplied: 0,
  // 申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）
  plantsApplied: 0,
  // 申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）
  bottlesKeepInApplied: 0,
  // 申请入库株数中母代留种株数（是 plants_applied 的其中数）
  plantsKeepInApplied: 0,
  // 实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）
  bottlesActual: 0,
  // 实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）
  plantsActual: 0,
  // 实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）
  bottlesKeepInActual: 0,
  // 实际入库株数中母代留种株数（是 plants_actual 的其中数）
  plantsKeepInActual: 0,
  // 是否在统计中忽略该入库单。 dictionary = boolean
  ignoreStats: '',
  // 出入库原因。 dictionary = storage_reason
  reason: '',
  // 状态。 dictionary = storage_in_out_status
  status: '',
  // 操作原因。 驳回、作废原因（手工填写）
  oppReason: '',
  // 产生出入库业务的组培任务ID。tissue_task.id。
  idTask: '',
  // 产生出入库业务的质检任务ID。inspection_task.id。
  idInspection: '',
  // 申请人ID，user.id
  applicantId: '',
  // 申请人姓名，user.name
  applicantName: '',
  // 申请人时间，UTC 毫秒
  applicantTime: 0,
  // 末次操作人ID，user.id
  operatorId: '',
  // 末次操作人姓名，user.name
  operatorName: '',
  // 末次操作时间，UTC 毫秒
  operatorTime: 0,
};
