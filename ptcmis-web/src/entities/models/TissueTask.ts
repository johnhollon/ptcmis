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

// 组培任务
import {ModelDateRange} from '../../components/00500-form-fields/date-range';
import {genId} from '../../services/utils/00010-id';
import {APIConst} from '../api/const-api';

export interface TissueTask {
  // id
  id: string;
  // 品种
  variety: string;
  // 品种编号（表示子品种）
  varietyCode: string;
  // 外植体采集批次
  explantCollectionBatch: number;
  // 预计使用母代瓶数
  estimatedParentBottles: number;
  // 预计使用母代株数
  estimatedParentPlants: number;
  // 预计母代留种瓶数
  estimatedParentKeepBottles: number;
  // 预计母代留种株数
  estimatedParentKeepPlants: number;
  // 预计生产子代瓶数
  estimatedChildrenBottles: number;
  // 预计生产子代株数
  estimatedChildrenPlants: number;
  // 任务参数：默认母代每瓶株数
  defaultPlantsPerParentBottle: number;
  // 任务参数：默认子代每瓶株数
  defaultPlantsPerChildBottle: number;
  // 瓶码前缀（打印时生成6位大写字母）
  codePrefix: string;
  // 创建人用户ID
  creatorUserId: string;
  // 创建人姓名
  creatorUserName: string;
  // 任务类别。 induction（诱导）；differentiation（分化）；multiplication（增殖）；rooting（生根）；acclimatization（炼苗）
  taskType: string;
  // 任务状态。created（未开始）；processing（执行中）；finished（已完成）；coded（已打码）
  taskStatus: string;
  // 创建时间 UTC 毫秒
  createTime: number;
  // 开始时间 UTC 毫秒
  startTime: number;
  // 完成时间 UTC 毫秒
  finishTime: number;
  // 是否开启强制编辑
  modifiable: string;
}

export function getTissueTaskDefault(taskType: TissueTaskType | ''): TissueTask {
  return {
    // id
    id: genId(),
    // 品种
    variety: '',
    // 品种编号（表示子品种）
    varietyCode: '',
    // 外植体采集批次
    explantCollectionBatch: 0,
    // 预计使用母代瓶数
    estimatedParentBottles: 0,
    // 预计使用母代株数
    estimatedParentPlants: 0,
    // 预计母代留种瓶数
    estimatedParentKeepBottles: 0,
    // 预计母代留种株数
    estimatedParentKeepPlants: 0,
    // 预计生产子代瓶数
    estimatedChildrenBottles: 0,
    // 预计生产子代株数
    estimatedChildrenPlants: 0,
    // 任务参数：默认母代每瓶株数
    defaultPlantsPerParentBottle: 0,
    // 任务参数：默认子代每瓶株数
    defaultPlantsPerChildBottle: 0,
    // 瓶码前缀（打印时生成6位大写字母）
    codePrefix: '',
    // 创建人用户ID
    creatorUserId: '',
    // 创建人姓名
    creatorUserName: '',
    // 任务类别。 induction（诱导）；differentiation（分化）；multiplication（增殖）；rooting（生根）；acclimatization（炼苗）
    taskType: taskType,
    // 任务状态。created（未开始）；processing（执行中）；finished（已完成）；coded（已打码）
    taskStatus: '',
    // 创建时间 UTC 毫秒
    createTime: 0,
    // 开始时间 UTC 毫秒
    startTime: 0,
    // 完成时间 UTC 毫秒
    finishTime: 0,
    // 是否开启强制编辑
    modifiable: APIConst.booleans.BOOLEAN_FALSE,
  };
}

export type TissueTaskType = 'induction' | 'differentiation' | 'multiplication' | 'rooting' | 'acclimatization';
export type TissueTaskStatus = 'created' | 'processing' | 'finished' | 'coded';
export type Condition = {
  keyword: string;
  taskType: string;
  taskStatus: string;
  // 外植体采集批次
  explantCollectionBatch: number | null;
  finishTime: ModelDateRange;
  createTime: ModelDateRange;
  startTime: ModelDateRange;
};
export const ConditionDefault: Condition = {
  keyword: '',
  taskType: '',
  taskStatus: '',
  // 外植体采集批次
  explantCollectionBatch: null,
  finishTime: {start: 0, end: 0},
  createTime: {start: 0, end: 0},
  startTime: {start: 0, end: 0},
}
