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

import { OverlayConfig } from '@angular/cdk/overlay';
import { TemplateRef } from '@angular/core';

/** 图片浮层配置信息 */
export class ImageOverlayOption {
  /** 图片src */
  public src: string = '';
  /** 操作页面模板 */
  public oppTemplate: TemplateRef<any> | null = null;
  /** 用户自定义数据 */
  public data: any;
  /** 浮层设置 */
  public overlayConfig: OverlayConfig | null | undefined;
}
