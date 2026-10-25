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
  ElementRef,
  Input,
  OnChanges,
  OnDestroy,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormField, MatInput, MatLabel } from '@angular/material/input';
import * as echarts from 'echarts/core';
import { debounceOppFactory } from '../../../../../../services/utils/debounce';

@Component({
  standalone: true,
  selector: 'tissue-task-storage-out-stat-do',
  templateUrl: 'comp.html',
  styleUrl: 'comp.scss',
  imports: [MatFormField, MatLabel, MatInput, ReactiveFormsModule],
})
export class CompStorageOutStatDo implements AfterViewInit, OnChanges, OnDestroy {
  @Input() model: {
    planBottles: number;
    planPlants: number;
    applyBottles: number;
    applyPlants: number;
    unProcessBottles: number;
    unProcessPlants: number;
    processBottles: number;
    processPlants: number;
    rejStoreBottles: number;
    rejStorePlants: number;
    rejBizBottles: number;
    rejBizPlants: number;
    doneBottles: number;
    donePlants: number;
    outBottles: number;
    outPlants: number;
  } | null = null;

  @ViewChild('chartContainerForBottles') chartContainerForBottles!: ElementRef<HTMLDivElement>;
  private chartInstanceForBottles: echarts.ECharts | null = null;
  private resizeObserverForBottles?: ResizeObserver;

  @ViewChild('chartContainerForPlants') chartContainerForPlants!: ElementRef<HTMLDivElement>;
  private chartInstanceForPlants: echarts.ECharts | null = null;
  private resizeObserverForPlants?: ResizeObserver;

  ngAfterViewInit(): void {
    const debouncedResizeForBottles = debounceOppFactory(() => {
      this.initBottlesChartIfReady();
    }, 150);
    this.resizeObserverForBottles = new ResizeObserver(debouncedResizeForBottles);
    this.resizeObserverForBottles.observe(this.chartContainerForBottles.nativeElement);

    const debouncedResizeForPlants = debounceOppFactory(() => {
      this.initPlantsChartIfReady();
    }, 150);
    this.resizeObserverForPlants = new ResizeObserver(debouncedResizeForPlants);
    this.resizeObserverForPlants.observe(this.chartContainerForPlants.nativeElement);
    this.updateChartOptionForPlants();
  }

  ngOnChanges(changes: SimpleChanges): void {
    const { model } = changes;
    if (model) {
      if (this.chartInstanceForBottles) {
        this.updateChartOptionForBottles();
      }
      if (this.chartInstanceForPlants) {
        this.updateChartOptionForPlants();
      }
    }
  }

  ngOnDestroy(): void {
    this.resizeObserverForBottles?.disconnect();
    this.chartInstanceForBottles?.dispose();
    this.chartInstanceForBottles = null;

    this.resizeObserverForPlants?.disconnect();
    this.chartInstanceForPlants?.dispose();
    this.chartInstanceForPlants = null;
  }

  private initBottlesChartIfReady() {
    const dom = this.chartContainerForBottles.nativeElement;
    if (!dom) return;
    // 尺寸为0直接跳过，不init
    if (dom.clientWidth <= 0 || dom.clientHeight <= 0) return;

    if (!this.chartInstanceForBottles) {
      this.chartInstanceForBottles = echarts.init(dom);
    } else {
      this.chartInstanceForBottles.resize();
    }
    this.updateChartOptionForBottles();
  }

  updateChartOptionForBottles() {
    const plan = this.model?.planBottles || 0;
    const applied = this.model?.applyBottles || 0;
    const unProcess = this.model?.unProcessBottles || 0;
    const process = this.model?.processBottles || 0;
    const rejStore = this.model?.rejStoreBottles || 0;
    const rejBiz = this.model?.rejBizBottles || 0;
    const done = (this.model?.doneBottles || 0) - (this.model?.outBottles || 0);
    const out = this.model?.outBottles || 0;

    const base = Math.max(plan, applied);
    const completeRate = ((out / (plan || 1)) * 100).toFixed(2);

    const options = {
      tooltip: {
        trigger: 'item',
        formatter: function (params: any) {
          if(!params.name) return '';
          if (params.seriesName === '计划') {
            return `<span style="font-weight: 600;">计划使用母代瓶数：${plan} 瓶</span><br/>已申请出库母代瓶数：${applied}<br/>已申请占计划：${((applied / (plan || 1)) * 100).toFixed(2)}%`;
          } else if (params.seriesName === '已申请') {
            return `<span style="font-weight: 600;">已申请出库母代瓶数：${applied} 瓶</span><br/>计划使用母代瓶数：${plan}<br/>已申请占计划：${((applied / (plan || 1)) * 100).toFixed(2)}%`;
          } else if (params.seriesName === '出库明细') {
            return `<span style="font-weight: 600;">${params.name}：${params.value} 瓶</span><br/>已申请出库母代瓶数：${applied}，${params.name}占比${((params.value / (applied || 1)) * 100).toFixed(2)}%<br/>计划使用母代瓶数：${plan}，${params.name}占比${((params.value / (plan || 1)) * 100).toFixed(2)}%`;
          }
          return params.name;
        },
      },
      series: [
        // 外层 计划数
        {
          name: '计划',
          type: 'pie',
          radius: ['78%', '88%'],
          center: ['50%', '50%'],
          itemStyle: { borderRadius: 4 },
          label: { show: false, position: 'inside' },
          data: [
            { name: '计划瓶数', value: plan, itemStyle: { color: '#304FFE' } },
            { name: '', value: base - plan, itemStyle: { color: 'transparent' } },
          ],
        },
        // 中层 申请数
        {
          name: '已申请',
          type: 'pie',
          radius: ['62%', '75%'],
          center: ['50%', '50%'],
          itemStyle: { borderRadius: 4 },
          label: { show: false, position: 'inside' },
          data: [
            { name: '已申请瓶数', value: applied, itemStyle: { color: '#43A047' } },
            { name: '', value: base - applied, itemStyle: { color: 'transparent' } },
          ],
        },
        // 内层 业务明细
        {
          name: '出库明细',
          type: 'pie',
          radius: ['40%', '58%'],
          center: ['50%', '50%'],
          itemStyle: { borderRadius: 4 },
          label: { show: false, position: 'inside' },
          data: [
            { name: '已接收瓶数', value: out, itemStyle: { color: '#43A047' } },
            { name: '已备出瓶数', value: done, itemStyle: { color: '#00897B' } },
            { name: '作业中瓶数', value: process, itemStyle: { color: '#0288D1' } },
            { name: '待作业瓶数', value: unProcess, itemStyle: { color: '#3949AB' } },
            { name: '驳回库房瓶数', value: rejStore, itemStyle: { color: '#FF1744' } },
            { name: '驳回业务瓶数', value: rejBiz, itemStyle: { color: '#D84315' } },
            {
              name: '',
              value: applied - (out + done + process + unProcess + rejStore + rejBiz),
              itemStyle: { color: 'transparent' },
            },
            { name: '', value: base - applied, itemStyle: { color: 'transparent' } },
          ],
        },
      ],
      graphic: [
        {
          type: 'text',
          left: 'center',
          top: 'center',
          style: {
            text: `完成度:${completeRate}%`,
            fontSize: 13,
            fill: '#43A047',
          },
        },
      ],
    };

    this.chartInstanceForBottles?.setOption(options);
  }

  private initPlantsChartIfReady() {
    const dom = this.chartContainerForPlants.nativeElement;
    if (!dom) return;
    if (dom.clientWidth <= 0 || dom.clientHeight <= 0) return;

    if (!this.chartInstanceForPlants) {
      this.chartInstanceForPlants = echarts.init(dom);
    } else {
      this.chartInstanceForPlants.resize();
    }
    this.updateChartOptionForPlants();
  }

  updateChartOptionForPlants() {
    const plan = this.model?.planPlants || 0;
    const applied = this.model?.applyPlants || 0;
    const unProcess = this.model?.unProcessPlants || 0;
    const process = this.model?.processPlants || 0;
    const rejStore = this.model?.rejStorePlants || 0;
    const rejBiz = this.model?.rejBizPlants || 0;
    const done = (this.model?.donePlants || 0) - (this.model?.outPlants || 0);
    const out = this.model?.outPlants || 0;

    const base = Math.max(plan, applied);
    const completeRate = ((out / (plan || 1)) * 100).toFixed(2);

    const options = {
      tooltip: {
        trigger: 'item',
        formatter: function (params: any) {
          if (!params.name) return '';
          if (params.seriesName === '计划') {
            return `<span style="font-weight: 600;">计划使用母代株数：${plan} 株</span><br/>已申请出库母代株数：${applied}<br/>已申请占计划：${((applied / (plan || 1)) * 100).toFixed(2)}%`;
          } else if (params.seriesName === '已申请') {
            return `<span style="font-weight: 600;">已申请出库母代株数：${applied} 株</span><br/>计划使用母代株数：${plan}<br/>已申请占计划：${((applied / (plan || 1)) * 100).toFixed(2)}%`;
          } else if (params.seriesName === '出库明细') {
            return `<span style="font-weight: 600;">${params.name}：${params.value} 株</span><br/>已申请出库母代株数：${applied}，${params.name}占比${((params.value / (applied || 1)) * 100).toFixed(2)}%<br/>计划使用母代株数：${plan}，${params.name}占比${((params.value / (plan || 1)) * 100).toFixed(2)}%`;
          }
          return params.name;
        },
      },
      series: [
        // 外层 计划数
        {
          name: '计划',
          type: 'pie',
          radius: ['78%', '88%'],
          center: ['50%', '50%'],
          itemStyle: { borderRadius: 4 },
          label: { show: false, position: 'inside' },
          data: [
            { name: '计划株数', value: plan, itemStyle: { color: '#304FFE' } },
            { name: '', value: base - plan, itemStyle: { color: 'transparent' } },
          ],
        },
        // 中层 申请数
        {
          name: '已申请',
          type: 'pie',
          radius: ['62%', '75%'],
          center: ['50%', '50%'],
          itemStyle: { borderRadius: 4 },
          label: { show: false, position: 'inside' },
          data: [
            { name: '已申请株数', value: applied, itemStyle: { color: '#43A047' } },
            { name: '', value: base - applied, itemStyle: { color: 'transparent' } },
          ],
        },
        // 内层 业务明细
        {
          name: '出库明细',
          type: 'pie',
          radius: ['40%', '58%'],
          center: ['50%', '50%'],
          itemStyle: { borderRadius: 4 },
          label: { show: false, position: 'inside' },
          data: [
            { name: '已接收株数', value: out, itemStyle: { color: '#43A047' } },
            { name: '已备出株数', value: done, itemStyle: { color: '#00897B' } },
            { name: '作业中株数', value: process, itemStyle: { color: '#0288D1' } },
            { name: '待作业株数', value: unProcess, itemStyle: { color: '#3949AB' } },
            { name: '驳回库房株数', value: rejStore, itemStyle: { color: '#FF1744' } },
            { name: '驳回业务株数', value: rejBiz, itemStyle: { color: '#D84315' } },
            {
              name: '',
              value: applied - (out + done + process + unProcess + rejStore + rejBiz),
              itemStyle: { color: 'transparent' },
            },
            { name: '', value: base - applied, itemStyle: { color: 'transparent' } },
          ],
        },
      ],
      graphic: [
        {
          type: 'text',
          left: 'center',
          top: 'center',
          style: {
            text: `完成度:${completeRate}%`,
            fontSize: 13,
            fill: '#43A047',
          },
        },
      ],
    };

    this.chartInstanceForPlants?.setOption(options);
  }
}
