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

import { provideHttpClient, withInterceptors } from '@angular/common/http';
import {
  InjectionToken,
  LOCALE_ID,
  provideAppInitializer,
  provideBrowserGlobalErrorListeners,
  provideZonelessChangeDetection,
} from '@angular/core';
import { provideNativeDateAdapter } from '@angular/material/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { App } from './app/app';
import { routesRoot } from './routes-root';
import { apiIntercept } from './services/datas/interceptors/api';
import { provideDataServices, provideDataServicesMock } from './services/datas/module';
import { IJT_RepositoryMessages } from './services/utils/00020-messages/repository';
import { RepositoryMessagesDefault } from './services/utils/00020-messages/repository-default';
import * as echarts from 'echarts/core';
import { BarChart, LineChart, PieChart } from 'echarts/charts';
import {
  GraphicComponent,
  GridComponent,
  LegendComponent,
  TitleComponent,
  TooltipComponent,
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

declare const ___$$$___MOCK___$$$___: boolean;
declare const ___$$$___API_ROOT___$$$___: string;

export const IJT_CONSTS_API_ROOT = new InjectionToken<string>('API_ROOT');

// echarts
echarts.use([
  CanvasRenderer,
  BarChart,
  LineChart,
  PieChart,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  GraphicComponent,
]);

bootstrapApplication(App, {
  providers: [
    // api url root
    { provide: IJT_CONSTS_API_ROOT, useValue: ___$$$___API_ROOT___$$$___ },
    // data层服务
    !!___$$$___MOCK___$$$___ ? provideDataServicesMock() : provideDataServices(),
    // 全局错误监听器
    provideBrowserGlobalErrorListeners(),
    // httpClient
    provideHttpClient(
      // api 拦截器
      withInterceptors([apiIntercept]),
    ),
    // 启用 Zoneless 无 Zone‑JS 变更检测
    provideZonelessChangeDetection(),
    // 日期服务
    provideNativeDateAdapter(),
    { provide: LOCALE_ID, useValue: 'zh-CN' },
    // 路由
    provideRouter(routesRoot),
    // APP 初始化
    provideAppInitializer(async () => {}),
    { provide: IJT_RepositoryMessages, useClass: RepositoryMessagesDefault },
  ],
}).then(() => undefined);
