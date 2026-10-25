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

import { StorageInOut } from '../models/StorageInOut';
import { getTissueTaskDefault, TissueTask, TissueTaskType } from '../models/TissueTask';
import { TissueTaskBottle } from '../models/TissueTaskBottle';
import { TissueTaskOppGroup } from '../models/TissueTaskOppGroup';
import { User } from '../models/User';

// 组培任务视图
export interface TissueTaskView {
  // 任务ID
  taskId: string;
  // 任务信息
  task: TissueTask;
  // 关联母代组培瓶
  bottlesParentAll: TissueTaskBottle[];
  // 关联子代组培瓶
  bottlesChildrenAll: TissueTaskBottle[];
  // 已发生的操作组
  oppGroup: TissueTaskOppGroup[];
  // 入库单
  storageInList: StorageInOut[];
  // 出库单
  storageOutList: StorageInOut[];
  // 实际使用母代瓶数
  actualParentBottles: number;
  // 实际使用母代株数
  actualParentPlants: number;
  // 操作过程中已淘汰母代瓶数
  discardParentBottles: number;
  // 操作过程中已淘汰母代株数
  discardParentPlants: number;
  // 留种母代瓶数
  keepParentBottles: number;
  // 留种母代株数
  keepParentPlants: number;
  // 实际生产子代瓶数
  actualChildrenBottles: number;
  // 实际生产子代株数
  actualChildrenPlants: number;
  // 操作过程中已淘汰子代瓶数
  discardChildrenBottles: number;
  // 操作过程中已淘汰子代株数
  discardChildrenPlants: number;
  // 已申请出库母代瓶数
  appliedStorageOutParentBottles: number;
  // 已申请出库母代株数
  appliedStorageOutParentPlants: number;
  // 实际已出库母代瓶数
  actualStorageOutParentBottles: number;
  // 实际已出库母代株数
  actualStorageOutParentPlants: number;
  // 已申请入库母代留种瓶数
  appliedStorageInParentKeepBottles: number;
  // 已申请入库母代留种株数
  appliedStorageInParentKeepPlants: number;
  // 实际已入库母代留种瓶数
  actualStorageInParentKeepBottles: number;
  // 实际已入库母代留种株数
  actualStorageInParentKeepPlants: number;
  // 已申请入库瓶数
  appliedStorageInChildrenBottles: number;
  // 已申请入库株数
  appliedStorageInChildrenPlants: number;
  // 实际已入库瓶数
  actualStorageInChildrenBottles: number;
  // 实际已入库株数
  actualStorageInChildrenPlants: number;
  // 入库状态。none 表示无需入库
  storageInStatus: string;
  // 任务关联的质检员列表
  inspectors: User[];
  // 任务关联的操作员列表
  operators: User[];
  // 实际操作过的质检员列表
  actualInspectors: User[];
  // 实际操作过的操作员列表
  actualOperators: User[];
}

export function getTissueTaskViewDefault(taskType: TissueTaskType | ''): TissueTaskView {
  const task = getTissueTaskDefault(taskType);
  return {
    // 任务ID
    taskId: task.id,
    // 任务信息
    task: task,
    // 关联母代组培瓶
    bottlesParentAll: [],
    // 关联子代组培瓶
    bottlesChildrenAll: [],
    // 已发生的操作组
    oppGroup: [],
    // 入库单
    storageInList: [],
    // 出库单
    storageOutList: [],
    // 实际使用母代瓶数
    actualParentBottles: 0,
    // 实际使用母代株数
    actualParentPlants: 0,
    // 操作过程中已淘汰母代瓶数
    discardParentBottles: 0,
    // 操作过程中已淘汰母代株数
    discardParentPlants: 0,
    // 留种母代瓶数
    keepParentBottles: 0,
    // 留种母代株数
    keepParentPlants: 0,
    // 实际生产子代瓶数
    actualChildrenBottles: 0,
    // 实际生产子代株数
    actualChildrenPlants: 0,
    // 操作过程中已淘汰子代瓶数
    discardChildrenBottles: 0,
    // 操作过程中已淘汰子代株数
    discardChildrenPlants: 0,
    // 已申请出库母代瓶数
    appliedStorageOutParentBottles: 0,
    // 已申请出库母代株数
    appliedStorageOutParentPlants: 0,
    // 实际已出库母代瓶数
    actualStorageOutParentBottles: 0,
    // 实际已出库母代株数
    actualStorageOutParentPlants: 0,
    // 已申请入库母代留种瓶数
    appliedStorageInParentKeepBottles: 0,
    // 已申请入库母代留种株数
    appliedStorageInParentKeepPlants: 0,
    // 实际已入库母代留种瓶数
    actualStorageInParentKeepBottles: 0,
    // 实际已入库母代留种株数
    actualStorageInParentKeepPlants: 0,
    // 已申请入库瓶数
    appliedStorageInChildrenBottles: 0,
    // 已申请入库株数
    appliedStorageInChildrenPlants: 0,
    // 实际已入库瓶数
    actualStorageInChildrenBottles: 0,
    // 实际已入库株数
    actualStorageInChildrenPlants: 0,
    // 入库状态。none 表示无需入库
    storageInStatus: '',
    // 任务关联的质检员列表
    inspectors: [],
    // 任务关联的操作员列表
    operators: [],
    // 实际操作过的质检员列表
    actualInspectors: [],
    // 实际操作过的操作员列表
    actualOperators: [],
  };
}
