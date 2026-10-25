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

import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { map, Observable } from 'rxjs';
import {
  ComponentDialogConfirm,
  DialogConfirmData,
} from '../../components/00360-dialogs/component-dialog-confirm';
import { MatButtonAppearance } from '@angular/material/button';

@Injectable({ providedIn: 'root' })
export class ServiceShowConfirmDialog {
  constructor(private readonly matDialog: MatDialog) {}

  /**
   * 显示操作确认对话框
   * @param message 消息内容
   * @param buttons 按钮，默认 ['确认','取消']
   * @param title 标题，默认'操作确认'
   * @param configs 宽度，默认 380，单位像素
   * - width 宽度，默认 380，单位像素
   * - buttonsAlign 按钮对齐方式，默认 end
   * - disableClose 是否禁止 ESC 或 backdrop 关闭，默认 false
   * - panelClass 面板样式类，默认 ''
   * @return 用户点击按钮的索引位（从0开始，与 buttons参数索引位相同），或 -1（未点击任何按钮）
   */
  show(
    message: string,
    title: string = '操作确认',
    buttons: (string | { text: string; appearance?: MatButtonAppearance })[] = [
      { text: '确认', appearance: 'filled' },
      { text: '取消' },
    ],
    configs: {
      width?: number;
      buttonsAlign?: 'start' | 'center' | 'end';
      disableClose?: boolean;
      panelClass?: string;
    } = {
      width: 500,
      buttonsAlign: 'end',
      disableClose: false,
      panelClass: '',
    },
  ): Observable<number> {
    const ref = this.matDialog.open<ComponentDialogConfirm, DialogConfirmData, number | undefined>(
      ComponentDialogConfirm,
      {
        width: `${configs?.width || 500}px`,
        disableClose: configs?.disableClose ?? false,
        panelClass: configs?.panelClass ?? '',
        data: { title, buttons, message, buttonsAlign: configs?.buttonsAlign ?? 'end' },
      },
    );
    return ref.afterClosed().pipe(map((result) => result ?? -1));
  }
}
