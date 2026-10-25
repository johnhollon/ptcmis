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

import chenhuiran.ptcmisserver.stubs.entities.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

// 组培任务视图
@Data
public class ModelTissueTaskView implements Serializable {
  // 任务ID
  private String taskId;
  // 任务信息
  private TissueTask task;
  // 关联母代组培瓶
  private List<TissueTaskBottle> bottlesParentAll;
  // 关联子代组培瓶
  private List<TissueTaskBottle> bottlesChildrenAll;
  // 已发生的操作组
  private List<TissueTaskOppGroup> oppGroup;
  // 入库单
  private List<StorageInOut> storageInList;
  // 出库单
  private List<StorageInOut> storageOutList;
  // 实际使用母代瓶数
  private int actualParentBottles;
  // 实际使用母代株数
  private int actualParentPlants;
  // 操作过程中已淘汰母代瓶数
  private int discardParentBottles;
  // 操作过程中已淘汰母代株数
  private int discardParentPlants;
  // 留种母代瓶数
  private int keepParentBottles;
  // 留种母代株数
  private int keepParentPlants;
  // 实际生产子代瓶数
  private int actualChildrenBottles;
  // 实际生产子代株数
  private int actualChildrenPlants;
  // 操作过程中已淘汰子代瓶数
  private int discardChildrenBottles;
  // 操作过程中已淘汰子代株数
  private int discardChildrenPlants;
  // 已申请出库母代瓶数
  private int appliedStorageOutParentBottles;
  // 已申请出库母代株数
  private int appliedStorageOutParentPlants;
  // 实际已出库母代瓶数
  private int actualStorageOutParentBottles;
  // 实际已出库母代株数
  private int actualStorageOutParentPlants;
  // 已申请入库母代留种瓶数
  private int appliedStorageInParentKeepBottles;
  // 已申请入库母代留种株数
  private int appliedStorageInParentKeepPlants;
  // 实际已入库母代留种瓶数
  private int actualStorageInParentKeepBottles;
  // 实际已入库母代留种株数
  private int actualStorageInParentKeepPlants;
  // 已申请入库瓶数
  private int appliedStorageInChildrenBottles;
  // 已申请入库株数
  private int appliedStorageInChildrenPlants;
  // 实际已入库瓶数
  private int actualStorageInChildrenBottles;
  // 实际已入库株数
  private int actualStorageInChildrenPlants;
  // 入库状态。none 表示无需入库
  private String storageInStatus;

  // 任务关联的质检员列表（TissueTaskUser）
  private List<User> inspectors;
  // 任务关联的操作员列表（TissueTaskUser）
  private List<User> operators;
  // 实际操作过的质检员列表（TissueTaskUser）
  private List<User> actualInspectors;
  // 实际操作过的操作员列表（TissueTaskUser）
  private List<User> actualOperators;
}
