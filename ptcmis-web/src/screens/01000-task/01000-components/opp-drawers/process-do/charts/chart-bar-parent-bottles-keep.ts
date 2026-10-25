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
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  Input,
  OnChanges,
  OnDestroy,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import * as echarts from 'echarts/core';
import { debounceOppFactory } from '../../../../../../services/utils/debounce';
import { ModelOppGroupStat } from '../../process/model-opp-group-stat';

@Component({
  selector: 'tissue-task-chart-bar-parent-bottles-keep',
  standalone: true,
  template: ` <div #chart style="flex:1;align-self: stretch;position:relative;"></div> `,
  changeDetection: ChangeDetectionStrategy.OnPush,
  styles: [':host { display: contents; }'],
})
export class TissueTaskChartBarParentBottlesKeep implements AfterViewInit, OnChanges, OnDestroy {
  @Input() model: ModelOppGroupStat | null = null;

  @ViewChild('chart') chartContainer!: ElementRef<HTMLDivElement>;
  private chartInstance: echarts.ECharts | null = null;
  private resizeObserver?: ResizeObserver;

  ngAfterViewInit(): void {
    const debouncedResize = debounceOppFactory(() => {
      this.initBottlesChartIfReady();
    }, 150);
    this.resizeObserver = new ResizeObserver(debouncedResize);
    this.resizeObserver.observe(this.chartContainer.nativeElement);
  }

  ngOnChanges(changes: SimpleChanges): void {
    const { model } = changes;
    if (model) {
      if (this.chartInstance) {
        this.updateChartOption();
      }
    }
  }

  ngOnDestroy(): void {
    this.resizeObserver?.disconnect();
    this.chartInstance?.dispose();
    this.chartInstance = null;
  }

  private initBottlesChartIfReady() {
    const dom = this.chartContainer.nativeElement;
    if (!dom) return;
    // 尺寸为0直接跳过，不init
    if (dom.clientWidth <= 0 || dom.clientHeight <= 0) return;

    if (!this.chartInstance) {
      this.chartInstance = echarts.init(dom);
    } else {
      this.chartInstance.resize();
    }
    this.updateChartOption();
  }

  updateChartOption() {
    const batchName = '留种瓶数';
    const plan =
      this.model?.parentKeepBottleCountPlan || this.model?.parentKeepBottleCountAssigned || 0;
    const assigned = this.model?.parentKeepBottleCountAssigned || 0;
    const processing = this.model?.parentKeepBottleCountProcessing || 0;
    const finished = this.model?.parentKeepBottleCountFinished || 0;
    const percentName = this.model?.parentKeepBottleCountPlan
      ? '留种瓶数计划完成度'
      : '计划外留种瓶数完成度';

    const completeRate = (plan === 0 ? 0 : (finished / plan) * 100).toFixed(2);
    const base = Math.max(assigned, plan);

    const options = {
      tooltip: {
        trigger: 'axis',
        appendToBody: true,
        confine: false,
        axisPointer: {
          type: 'none',
        },
        formatter(params: any) {
          let html = `<div style="font-weight:bold">${batchName}</div>`;
          params.forEach((p: any) => {
            html += `${p.marker} ${p.seriesName}：${p.value} 瓶<br/>`;
          });
          html += `<br/><span class="text-primary">${percentName}：${completeRate}%</span>`;
          return html;
        },
      },
      grid: {
        left: 0,
        right: 0,
        top: 0,
        bottom: 0,
      },
      xAxis: {
        type: 'value',
        max: base,
        show: false,
      },
      yAxis: {
        type: 'category',
        data: [batchName],
        show: false,
      },
      series: [
        {
          name: '已完成瓶数',
          type: 'bar',
          stack: 'row1',
          barWidth: 5,
          itemStyle: { color: '#43A047' }, // md success(自定义md3辅助色)
          label: { show: false },
          emphasis: {
            itemStyle: { color: '#43A047' },
          },
          data: [finished],
        },
        {
          name: '操作中瓶数',
          type: 'bar',
          stack: 'row1',
          barWidth: 5,
          itemStyle: { color: '#5E35B1' }, // md error
          label: { show: false },
          emphasis: {
            itemStyle: { color: '#5E35B1' },
          },
          data: [processing],
        },
        {
          name: '已分配瓶数',
          type: 'bar',
          stack: 'row2',
          barWidth: 5,
          itemStyle: { color: '#039BE5' }, // md primary
          label: { show: false },
          emphasis: {
            itemStyle: { color: '#039BE5' },
          },
          data: [assigned],
        },
        {
          name: this.model?.parentKeepBottleCountPlan ? '计划留种瓶数' : '计划外留种瓶数',
          type: 'bar',
          stack: 'row3',
          barWidth: 5,
          itemStyle: { color: '#3949AB' }, // md surface-variant
          label: { show: false },
          emphasis: {
            itemStyle: { color: '#3949AB' },
          },
          data: [plan || assigned],
        },
      ],
    };

    this.chartInstance?.setOption(options);
  }
}
