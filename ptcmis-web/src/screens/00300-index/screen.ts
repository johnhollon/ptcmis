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

import { Component, inject, OnInit, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIconButton } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { LayoutMainSimple } from '../../components/00050-layouts/00100-layout-main-simple/layout';
import { NavNode } from '../../entities/ui/NavNode';
import { IJT_DataPassport } from '../../services/datas/passport/data';
import { ScreenVariety } from '../00400-sys-params-variety/screen';
import { ScreenVarietyCode } from '../00500-sys-params-variety-code/screen';
import { ScreenUser } from '../00600-sys-params-user/screen';
import { ScreenStorageInventoryBaseline } from '../00700-storage-inventory-baseline/screen';
import { ScreenTaskInduction } from '../01100-task-Induction/screen';
import { ScreenStorageOutTask } from '../02100-storage-out-task/screen';
import { ScreenBottleCodeComplementTask } from '../02050-bottle-code-complement/screen';
import { ScreenTaskDifferentiation } from '../01200-task-differentiation/screen';
import { ScreenStorageInTask } from '../02200-storage-in-task/screen';
import { ScreenTaskRooting } from '../01400-task-rooting/screen';
import { ScreenStorageOutInspect } from '../02300-storage-out-intercept/screen';
import { ScreenInventoryAddBottle } from '../02400-inventory-add-bottle/screen';
import { ScreenInventoryOut } from '../02500-inventory-sub-bottle/screen';
import { ScreenInventoryAdjustment } from '../02600-inventory-asjustment/screen';
import { ScreenCodePrint } from '../03100-code-print/screen';
import { ScreenInventoryUpdatePlants } from '../02700-inventory-update-plants/screen';
import { ScreenInventoryUpdateParent } from '../02800-inventory-update-parent/screen';
import { ScreenCodePrintBottle } from '../03200-code-print-bottle/screen';
import { ScreenCurrentUser } from '../00900-current-user/screen';
import { ScreenPrintSettings } from '../00950-print-settings/screen';
import { ScreenStatInventoryTransaction } from '../04200-stat-inventory-transaction/screen';
import { ScreenWorkloadBaseline } from '../00800-workload-baseline/screen';
import { ScreenStatInventory } from '../04180-stat-inventory/screen';
import { ScreenStatWorkloadTransaction } from '../04170-stat-workload-transaction/screen';
import { ScreenStatWorkload } from '../04160-stat-workload/screen';
import { ScreenQueryBottle } from '../04300-query-bottle/screen';
import { ScreenStatInventoryAlert } from '../04190-stat-inventory-alert/screen';
import { ScreenInventorySubBottleAcclimatization } from '../02230-inventory-sub-bottle-acclimatization/screen';
import { ScreenInventoryAddBottleAcclimatization } from '../02260-inventory-add-bottle-acclimatization/screen';
import { ScreenStatInventorySimple } from '../04250-stat-inventory-simple/screen';

@Component({
  selector: 'screen-index',
  standalone: true,
  imports: [ReactiveFormsModule, MatIconModule, MatToolbar, MatIconButton, LayoutMainSimple],
  templateUrl: './screen.html',
})
export class ScreenIndex implements OnInit {
  private dataPassport = inject(IJT_DataPassport);
  protected navNodesSig = signal<NavNode[]>([]);

  ngOnInit(): void {
    this.navNodesSig.set(this.filterNavTree(NAV_DATA));
  }

  // 递归过滤导航树，移除admin标记且当前用户不是管理员的节点
  private filterNavTree(nodes: NavNode[]): NavNode[] {
    const result: NavNode[] = [];
    for (const node of nodes) {
      // 当前节点需要管理员权限，且用户非管理员，直接跳过
      if (node.roles.indexOf(this.dataPassport.role) < 0) continue;
      const newNode: NavNode = { ...node };
      // 递归过滤子菜单
      if (newNode.children && newNode.children.length > 0) {
        newNode.children = this.filterNavTree(newNode.children);
      }
      result.push(newNode);
    }
    return result;
  }
}

// TODO 开发调试取消权限认证
const NAV_DATA: NavNode[] = [
  {
    name: '查询统计',
    id: '00100',
    route: '',
    componentType: undefined,
    roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
    children: [
      // {
      //   name: '业务总览',
      //   id: '0010000100',
      //   route: '/0010000100',
      //   componentType: ComponentLoadingWelcome,
      // },
      {
        name: '库存简报',
        id: '0010000150',
        route: '/0010000150',
        componentType: ScreenStatInventorySimple,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
      {
        name: '库存统计',
        id: '0010000200',
        route: '/0010000200',
        componentType: ScreenStatInventory,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
      {
        name: '库存流水',
        id: '0010000300',
        route: '/0010000300',
        componentType: ScreenStatInventoryTransaction,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
      {
        name: '库存预警',
        id: '0010000350',
        route: '/0010000350',
        componentType: ScreenStatInventoryAlert,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
      {
        name: '绩效统计',
        id: '0010000400',
        route: '/0010000400',
        componentType: ScreenStatWorkload,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
      {
        name: '绩效流水',
        id: '0010000500',
        route: '/0010000500',
        componentType: ScreenStatWorkloadTransaction,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
      {
        name: '代际追溯',
        id: '0010000600',
        route: '/0010000600',
        componentType: ScreenQueryBottle,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
      // {
      //   name: '绩效统计',
      //   id: '0010000300',
      //   route: '/0010000300',
      //   componentType: ComponentLoadingWelcome,
      // },
      // {
      //   name: '代际追溯',
      //   id: '0010000400',
      //   route: '/0010000400',
      //   componentType: ComponentLoadingWelcome,
      // },
    ],
  },
  {
    name: '组培任务',
    id: '00200',
    route: '',
    componentType: undefined,
    roles: ['task', 'task_and_inventory', 'admin'],
    children: [
      {
        name: '诱导任务',
        id: '0020000100',
        route: '/0020000100',
        componentType: ScreenTaskInduction,
        roles: ['task', 'task_and_inventory', 'admin'],
      },
      {
        name: '分化任务',
        id: '0020000110',
        route: '/0020000110',
        componentType: ScreenTaskDifferentiation,
        roles: ['task', 'task_and_inventory', 'admin'],
      },
      // {
      //   name: '增殖任务',
      //   id: '0020000200',
      //   route: '/0020000200',
      //   componentType: ScreenTaskMultiplication,
      // },
      {
        name: '生根任务',
        id: '0020000300',
        route: '/0020000300',
        componentType: ScreenTaskRooting,
        roles: ['task', 'task_and_inventory', 'admin'],
      },
      // {
      //   name: '炼苗任务',
      //   id: '0020000400',
      //   route: '/0020000400',
      //   componentType: ScreenTaskAcclimatization,
      // },
    ],
  },
  {
    name: '库存管理',
    id: '00300',
    route: '',
    componentType: undefined,
    roles: ['inventory', 'task_and_inventory', 'admin'],
    children: [
      {
        name: '瓶体补码',
        id: '0030000100',
        route: '/0030000100',
        componentType: ScreenBottleCodeComplementTask,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '任务出库',
        id: '0030000200',
        route: '/0030000200',
        componentType: ScreenStorageOutTask,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '任务入库',
        id: '0030000300',
        route: '/0030000300',
        componentType: ScreenStorageInTask,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '炼苗出库',
        id: '0030000330',
        route: '/0030000330',
        componentType: ScreenInventorySubBottleAcclimatization,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '炼苗还库',
        id: '0030000360',
        route: '/0030000360',
        componentType: ScreenInventoryAddBottleAcclimatization,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '质检出库',
        id: '0030000400',
        route: '/0030000400',
        componentType: ScreenStorageOutInspect,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '库存增瓶',
        id: '0030000320',
        route: '/0030000320',
        componentType: ScreenInventoryAddBottle,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '库存减瓶',
        id: '0030000350',
        route: '/0030000350',
        componentType: ScreenInventoryOut,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '盘点调差',
        id: '0030000500',
        route: '/0030000500',
        componentType: ScreenInventoryAdjustment,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '更正株数',
        id: '0030000600',
        route: '/0030000600',
        componentType: ScreenInventoryUpdatePlants,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
      {
        name: '更正母瓶',
        id: '0030000700',
        route: '/0030000700',
        componentType: ScreenInventoryUpdateParent,
        roles: ['inventory', 'task_and_inventory', 'admin'],
      },
    ],
  },
  {
    name: '标签打印',
    id: '00400',
    route: '',
    componentType: undefined,
    roles: ['inventory', 'task', 'task_and_inventory', 'admin'],
    children: [
      {
        name: '打印记录',
        id: '0040000100',
        route: '/0040000100',
        componentType: ScreenCodePrint,
        roles: ['inventory', 'task', 'task_and_inventory', 'admin'],
      },
      {
        name: '补打瓶码',
        id: '0040000200',
        route: '/0040000200',
        componentType: ScreenCodePrintBottle,
        roles: ['inventory', 'task', 'task_and_inventory', 'admin'],
      },
    ],
  },
  {
    name: '系统管理',
    id: '90000',
    route: '',
    componentType: undefined,
    roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
    children: [
      {
        name: '参数设置',
        id: '9000000100',
        route: '',
        componentType: undefined,
        roles: ['admin'],
        children: [
          {
            name: '品种信息',
            id: '900000010000100',
            route: '/900000010000100',
            componentType: ScreenVariety,
            roles: ['admin'],
          },
          {
            name: '品种编号',
            id: '900000010000200',
            route: '/900000010000200',
            componentType: ScreenVarietyCode,
            roles: ['admin'],
          },
          {
            name: '员工信息',
            id: '900000010000300',
            route: '/900000010000300',
            componentType: ScreenUser,
            roles: ['admin'],
          },
          {
            name: '库存基线',
            id: '900000010000400',
            route: '/900000010000400',
            componentType: ScreenStorageInventoryBaseline,
            roles: ['admin'],
          },
          {
            name: '绩效基线',
            id: '900000010000500',
            route: '/900000010000500',
            componentType: ScreenWorkloadBaseline,
            roles: ['admin'],
          },
        ],
      },
      {
        name: '打印设置',
        id: '9000080000',
        route: '/9000080000',
        componentType: ScreenPrintSettings,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
      {
        name: '我的账号',
        id: '9000090000',
        route: '/9000090000',
        componentType: ScreenCurrentUser,
        roles: ['query', 'inventory', 'task', 'task_and_inventory', 'admin'],
      },
    ],
  },
];
