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

import {
  AfterViewInit,
  Component,
  ComponentRef,
  computed,
  Input,
  OnInit,
  signal,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatRipple } from '@angular/material/core';
import { MatIcon } from '@angular/material/icon';
import { MatDrawer, MatDrawerContainer } from '@angular/material/sidenav';
import {
  MatTree,
  MatTreeNestedDataSource,
  MatTreeNode,
  MatTreeNodeDef,
  MatTreeNodePadding,
  MatTreeNodeToggle,
} from '@angular/material/tree';
import { NavNode } from '../../../entities/ui/NavNode';
import { DirectiveHorizontalScroll } from '../../00100-directives/directive-horizontal-scroll';

@Component({
  selector: 'layout-main-simple',
  standalone: true,
  imports: [
    MatDrawerContainer,
    MatDrawer,
    MatChipsModule,
    DirectiveHorizontalScroll,
    MatTree,
    MatTreeNode,
    MatTreeNodeDef,
    MatTreeNodePadding,
    MatTreeNodeToggle,
    MatRipple,
    MatIcon,
    MatIconButton,
  ],
  templateUrl: './layout.html',
  styleUrl: './layout.scss',
})
export class LayoutMainSimple implements OnInit, AfterViewInit {
  ngOnInit(): void {}
  ngAfterViewInit(): void {
    const indexNode = this.dataSource.data[0]!.children![0];
    this.selectNode(indexNode);
  }
  // 导航树
  @Input() set navNodes(navNodes: NavNode[]) {
    this.dataSource.data = navNodes;
  }
  // 界面树的数据源
  protected readonly dataSource = new MatTreeNestedDataSource<NavNode>();
  // 当前选中的树节点ID
  protected selectedNodeIdSig = signal<string>('');
  // 当前已打开的标签页列表
  protected tabsOpenedSig = signal<NavNode[]>([]);
  // 防止 view 层过多通过读取 selectedNodeIdSig 进行判断。
  protected selectedTabsSig = computed(() => {
    const selectedTabs: { [key: string]: boolean } = {};
    selectedTabs[this.selectedNodeIdSig()] = true;
    return selectedTabs;
  });
  // 获取当前节点的子节点
  getChildren = (node: NavNode): NavNode[] => node.children ?? [];
  // 判断是否有子节点
  hasSubNav = (_: number, node: NavNode) => this.getChildren(node).length > 0;
  // 组件缓存池，常驻实例
  private cache: CachedTab[] = [];
  // 顶层 Drawer
  @ViewChild('topLevelDrawer', { read: MatDrawer, static: true })
  private _topLevelDrawer!: MatDrawer;
  // 选中Tab内容显示区域
  // 动态宿主容器，用来放置页面组件
  @ViewChild('topLevelTabContent', { read: ViewContainerRef, static: true })
  private _topLevelTabContent!: ViewContainerRef;

  get topLevelDrawer(): MatDrawer {
    return this._topLevelDrawer;
  }
  toggleTopLevelDrawer() {
    return this._topLevelDrawer.toggle();
  }
  openTopLevelDrawer() {
    return this._topLevelDrawer.open();
  }
  closeTopLevelDrawer() {
    return this._topLevelDrawer.close();
  }
  removeTab(tabIndex: number) {
    const currentTabs = this.tabsOpenedSig();

    // 如果只剩最后一个标签页，则阻止关闭。
    if (currentTabs.length <= 1) return;

    const closingTab = currentTabs[tabIndex];

    const cacheIndex = this.cache.findIndex((c) => c.node.id === closingTab.id);
    if (cacheIndex !== -1) {
      this.cache[cacheIndex].componentRef.destroy();
      this.cache.splice(cacheIndex, 1);
    }

    const newTabs = currentTabs.filter((_, i) => i !== tabIndex);
    this.tabsOpenedSig.set(newTabs);

    let newSelectedIndex = tabIndex;
    if (tabIndex >= newTabs.length) {
      newSelectedIndex = newTabs.length - 1;
    }
    this.switchActiveTab(newSelectedIndex);
  }
  selectNode(node: NavNode): void {
    // 含有子节点的功能模块不能被选中打开。
    if (node.children && node.children.length > 0) return;
    // 尝试判断是否存留在已打开的标签页。
    const currentTabs = this.tabsOpenedSig();
    const existIndex = currentTabs.findIndex((t) => t.id === node.id);
    // 如果存留在已打开标签页，则切换显示内容即可。
    if (existIndex >= 0) {
      // 切换显示缓存内容
      this.switchActiveTab(existIndex);
      return;
    }
    // 通过节点ID拿到组件类型。
    const comp = node.componentType;
    if (!comp) return;
    // 同步已打开标签页
    const newTabs = [...currentTabs, node];
    this.tabsOpenedSig.set(newTabs);
    const newIndex = newTabs.length - 1;
    // 向缓存添加组件
    const componentRef = this._topLevelTabContent.createComponent(comp);
    componentRef.location.nativeElement.style.display = 'none';
    this.cache.push({ node, componentRef });
    // 切换显示内容
    this.switchActiveTab(newIndex);
  }
  // 切换显示指定标签组件
  private switchActiveTab(activeTabIndex: number): void {
    const activeTab = this.tabsOpenedSig()[activeTabIndex];
    if (!activeTab) return;
    if (activeTab.id === this.selectedNodeIdSig()) return;
    this.cache.forEach((item) => {
      const isActive = item.node.id === activeTab.id;
      item.componentRef.location.nativeElement.style.display = isActive ? 'block' : 'none';
    });
    this.selectedNodeIdSig.set(activeTab.id);
  }
}
// 组件缓存池
interface CachedTab {
  node: NavNode;
  componentRef: ComponentRef<unknown>;
}
