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

// 组培瓶
export interface Bottle {
  // id，瓶码
  id: string;

  //**********************************
  // 本瓶信息
  //**********************************
  // 品种
  variety: string;
  // 品种编号（表示子品种）
  varietyCode: string;
  // 外植体采集批次
  explantCollectionBatch: number;
  // 代际
  generation: number;
  // 瓶内株数
  plantCount: number;
  // 形成该瓶的任务执行时间
  taskTime: number;
  // 产生来源，培养瓶产生于哪个业务。dictionary.storage_reason
  sourceType: string;

  //**********************************
  // 母瓶信息
  //**********************************
  // 母瓶ID，可为''，表示来自系统上线前已有母瓶
  idParent: string;
  // 母瓶代际
  parentGeneration: number;

  //**********************************
  // 任务状态信息
  //**********************************
  // 当前所在任务ID，可为''，表示当前不在任务中
  currentIdTask: string;
  // 当前组培任务操作分配组ID
  currentIdOppGroup: string;
  // 当前组培工序: induction（诱导）；differentiation（分化）；multiplication（增殖）；rooting（生根）；acclimatization（炼苗）；none（不在任务中）
  currentCultureStage: string;
  // 当前任务角色：parent（母瓶）；child（子瓶已用）；none（未在任务中）
  currentRoleInTask: string;
  // 当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean
  currentParentKeep: string;
  // 当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。
  currentParentKeepPlantCount: number;
  // 当前任务瓶码状态: coded（已打码，待用）；used（已打码，已用）；voided（已作废）；none（未纳入瓶码管理）
  currentOperateStatus: string;
  // 当前任务质检员ID
  currentInspectorId: string;
  // 当前任务质检员姓名
  currentInspectorName: string;
  // 当前任务操作员ID
  currentOperatorId: string;
  // 当前任务操作员姓名
  currentOperatorName: string;
  // 末次所在任务ID，可为''，表示从未发生过任务
  lastIdTask: string;
  // 末次组培任务操作分配组ID
  lastIdOppGroup: string;
  // 末次组培工序: induction（诱导）；differentiation（分化）；multiplication（增殖）；rooting（生根）；acclimatization（炼苗）；none（从未发生过任务）
  lastCultureStage: string;
  // 末次任务角色：parent（母瓶）；child（子瓶已用）；none（从未发生过任务）
  lastRoleInTask: string;
  // 当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean
  lastParentKeep: string;
  // 当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。
  lastParentKeepPlantCount: number;
  // 末次任务瓶码状态: coded（已打码，待用）；used（已打码，已用）；voided（已作废）；none（从未发生过任务）
  lastOperateStatus: string;
  // 末次任务质检员ID
  lastInspectorId: string;
  // 末次任务质检员姓名
  lastInspectorName: string;
  // 末次任务操作员ID
  lastOperatorId: string;
  // 末次任务操作员姓名
  lastOperatorName: string;

  //**********************************
  // 仓储状态信息
  //**********************************
  // 当前仓储状态: in（在库）；out（出库-生命周期完结）；pending（已出库待入库）
  currentStorageStatus: string;
  // 末次入库用户ID
  lastReceivedUserId: string;
  // 末次入库人姓名
  lastReceivedUserName: string;
  // 末次入库时间
  lastReceivedTime: number;
  // 末次入库原因
  lastReceivedReason: string;
  // 末次出库用户ID
  lastIssuedUserId: string;
  // 末次出库人姓名
  lastIssuedUserName: string;
  // 末次出库时间
  lastIssuedTime: number;
  // 末次出库原因
  lastIssuedReason: string;

  //**********************************
  // 绩效/废弃信息
  //**********************************
  // 是否已被淘汰
  discarded: string;
  // 是否计入绩效
  performanceIncluded: string;
  // 淘汰原因
  discardReason: string;
  // 淘汰原因（备注）
  discardReasonDesc: string;
  // 淘汰时间 UTC 毫秒
  discardTime: number;
  // 执行淘汰人用户ID
  discardUserId: string;
  // 执行淘汰人姓名
  discardUserName: string;
}
