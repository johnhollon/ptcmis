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

import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { QueryCondition } from '../../entities/api/QueryCondition';
import { QueryConditionBottleCodeComplementTask } from '../../entities/api/QueryConditionBottleCodeComplementTask';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { BottleCodeComplementTask } from '../../entities/models/BottleCodeComplementTask';
import { BottleCodeComplementTaskDetail } from '../../entities/models/BottleCodeComplementTaskDetail';
import { ModelPrintBottleCodeForComplement } from '../../entities/api/ModelPrintBottleCodeForComplement';
import { ModelBottleCodeComplementTaskProcess } from '../../entities/api/ModelBottleCodeComplementTaskProcess';
import { IJT_DataBottleCodeComplementTask } from '../datas/bottle-code-complement-task/data';
import { Archive } from '../../entities/models/Archive';
import { ModelPrintBottleCodeForAddBottles } from '../../entities/api/ModelPrintBottleCodeForAddBottles';

@Injectable({
  providedIn: 'root',
})
export class ActionBottleCodeComplementTask {
  private dataBottleCodeComplementTask = inject(IJT_DataBottleCodeComplementTask);
  queryByCondition(
    condition: QueryCondition<QueryConditionBottleCodeComplementTask>,
  ): Observable<QueryResultPaged<BottleCodeComplementTask>> {
    return this.dataBottleCodeComplementTask.queryByCondition(condition);
  }
  queryOrCreateCodePrefix(taskId: string): Observable<string> {
    return this.dataBottleCodeComplementTask.queryOrCreateCodePrefix(taskId);
  }
  queryAllCodesInTask(taskId: string): Observable<BottleCodeComplementTaskDetail[]> {
    return this.dataBottleCodeComplementTask.queryAllCodesInTask(taskId);
  }
  save(model: BottleCodeComplementTask): Observable<void> {
    return this.dataBottleCodeComplementTask.save(model);
  }
  delete(taskId: string): Observable<void> {
    return this.dataBottleCodeComplementTask.delete(taskId);
  }
  printCode(
    model: ModelPrintBottleCodeForComplement,
  ): Observable<ModelPrintBottleCodeForAddBottles> {
    return this.dataBottleCodeComplementTask.printCode(model);
  }
  process(model: ModelBottleCodeComplementTaskProcess): Observable<void> {
    return this.dataBottleCodeComplementTask.process(model);
  }
  done(taskId: string): Observable<void> {
    return this.dataBottleCodeComplementTask.done(taskId);
  }
  uploadArchives(taskId: string, files: File[]): Observable<Archive[]> {
    return this.dataBottleCodeComplementTask.uploadArchives(taskId, files);
  }
  queryArchives(taskId: string): Observable<Archive[]> {
    return this.dataBottleCodeComplementTask.queryArchives(taskId);
  }
}
