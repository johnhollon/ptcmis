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

// 组培任务培养瓶
export interface TissueTaskBottle {
  // 任务ID。tissue_task.id。联合主键
  idTask: string;
  // 品种
  variety: string;
  // 品种编号（表示子品种）
  varietyCode: string;
  // 操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引
  idOppGroup: string;
  // 瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引
  idBottle: string;
  // 外植体采集批次
  explantCollectionBatch: number;
  // 代际数
  generation: number;
  // 瓶内株数（任务前）
  plantCountOriginal: number;
  // 瓶内株数（实际）
  plantCountActual: number;
  // 任务角色。 dictionary.bottle_task_role
  roleInTask: string;
  // 若作为母瓶使用，是否留种。 dictionary.boolean
  parentKeep: string;
  // 若作为母瓶使用，且留种，留种株数。
  parentKeepPlantCount: number;
  // 母瓶末次任务类型，用于子代是否递增代数。 dictionary.tissue_task_type
  parentLastTaskType: string;
  // 操作状态。 dictionary.bottle_operate_status
  operateStatus: string;
  // 子瓶瓶码状态。 dictionary.bottle_code_status_for_task
  childCodeStatus: string;
  // 若淘汰，体现淘汰原因。
  discardReason: string;
  // 分配时间（UTC毫秒）
  allocateTime: number;
  // 完成时间 UTC毫秒
  finishTime: number;
  // 质检员ID
  inspectorId: string;
  // 质检员姓名
  inspectorName: string;
  // 操作员ID
  operatorId: string;
  // 操作员姓名
  operatorName: string;
}
