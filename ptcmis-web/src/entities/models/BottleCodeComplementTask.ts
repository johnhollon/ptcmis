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

/**
 * 瓶体补码任务主表
 * 全部字段禁止 null / undefined，驼峰命名
 */
export interface BottleCodeComplementTask {
  /** 任务ID */
  id: string;
  /** 品种 variety.name */
  variety: string;
  /** 品种编号（子品种） variety_code.code */
  varietyCode: string;
  // 外植体采集批次
  explantCollectionBatch: number;
  // 培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason
  sourceType: string;
  /** 母代代际 */
  generation: number;
  /** 瓶码前缀（打印时生成6位大写字母） */
  codePrefix: string;
  /** 任务参数：每瓶株数 */
  defaultPlantsPerBottle: number;
  /** 任务状态：dictionary.bottle_code_complement_task_status */
  taskStatus: string;
  /** 实际赋码瓶数 */
  bottles: number;
  /** 实际赋码株数 */
  plants: number;
  /** 创建人用户ID，user.id */
  creatorId: string;
  /** 创建人姓名，user.name */
  creatorName: string;
  /** 创建时间 UTC 毫秒时间戳 */
  createTime: number;
  /** 末次操作人用户ID，user.id */
  operatorId: string;
  /** 末次操作人姓名，user.name */
  operatorName: string;
  /** 开始时间 UTC 毫秒时间戳 */
  startTime: number;
  /** 完成时间 UTC 毫秒时间戳 */
  finishTime: number;
}

/**
 * BottleCodeComplementTask 默认常量，接口名 + Default
 */
export const BottleCodeComplementTaskDefault: BottleCodeComplementTask = {
  id: '',
  variety: '',
  varietyCode: '',
  explantCollectionBatch: 0,
  sourceType: '',
  generation: 0,
  codePrefix: '',
  defaultPlantsPerBottle: 0,
  taskStatus: 'created',
  bottles: 0,
  plants: 0,
  creatorId: '',
  creatorName: '',
  createTime: 0,
  operatorId: '',
  operatorName: '',
  startTime: 0,
  finishTime: 0,
};
