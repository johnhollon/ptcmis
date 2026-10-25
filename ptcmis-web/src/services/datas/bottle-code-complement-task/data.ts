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

import { InjectionToken } from '@angular/core';
import { Observable } from 'rxjs';
import { BottleCodeComplementTask } from '../../../entities/models/BottleCodeComplementTask';
import { BottleCodeComplementTaskDetail } from '../../../entities/models/BottleCodeComplementTaskDetail';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionBottleCodeComplementTask } from '../../../entities/api/QueryConditionBottleCodeComplementTask';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { ModelPrintBottleCodeForComplement } from '../../../entities/api/ModelPrintBottleCodeForComplement';
import { ModelBottleCodeComplementTaskProcess } from '../../../entities/api/ModelBottleCodeComplementTaskProcess';
import { Archive } from '../../../entities/models/Archive';
import { HttpEvent } from '@angular/common/http';
import { ModelPrintBottleCodeForAddBottles } from '../../../entities/api/ModelPrintBottleCodeForAddBottles';

export const IJT_DataBottleCodeComplementTask = new InjectionToken<DataBottleCodeComplementTask>(
  'DataBottleCodeComplementTask',
);

export interface DataBottleCodeComplementTask {
  /**
   * 条件分页查询瓶体补码任务
   */
  queryByCondition(
    condition: QueryCondition<QueryConditionBottleCodeComplementTask>,
  ): Observable<QueryResultPaged<BottleCodeComplementTask>>;

  queryOrCreateCodePrefix(taskId: string): Observable<string>;

  /**
   * 查询任务下全部明细瓶码
   */
  queryAllCodesInTask(taskId: string): Observable<BottleCodeComplementTaskDetail[]>;

  /**
   * 保存任务
   */
  save(model: BottleCodeComplementTask): Observable<void>;

  /**
   * 删除任务
   */
  delete(taskId: string): Observable<void>;

  /**
   * 打印瓶码
   */
  printCode(
    model: ModelPrintBottleCodeForComplement,
  ): Observable<ModelPrintBottleCodeForAddBottles>;

  /**
   * 执行处理
   */
  process(model: ModelBottleCodeComplementTaskProcess): Observable<void>;

  /**
   * 完成任务
   */
  done(taskId: string): Observable<void>;

  /**
   * 上传任务附件
   * @param taskId 任务ID
   * @param files 文件列表
   * @returns 返回该任务全部附件数组
   */
  uploadArchives(taskId: string, files: File[]): Observable<Archive[]>;
  queryArchives(taskId: string): Observable<Archive[]>;
}
