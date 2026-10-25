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

// data-api.ts
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { DataBottleCodeComplementTask } from './data';
import { BottleCodeComplementTask } from '../../../entities/models/BottleCodeComplementTask';
import { BottleCodeComplementTaskDetail } from '../../../entities/models/BottleCodeComplementTaskDetail';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionBottleCodeComplementTask } from '../../../entities/api/QueryConditionBottleCodeComplementTask';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { ModelPrintBottleCodeForComplement } from '../../../entities/api/ModelPrintBottleCodeForComplement';
import { ModelBottleCodeComplementTaskProcess } from '../../../entities/api/ModelBottleCodeComplementTaskProcess';
import { Archive } from '../../../entities/models/Archive';
import { ModelPrintBottleCodeForAddBottles } from '../../../entities/api/ModelPrintBottleCodeForAddBottles';

export class DataBottleCodeComplementTaskApi implements DataBottleCodeComplementTask {
  private http = inject(HttpClient);

  queryByCondition(
    condition: QueryCondition<QueryConditionBottleCodeComplementTask>,
  ): Observable<QueryResultPaged<BottleCodeComplementTask>> {
    return this.http.post<QueryResultPaged<BottleCodeComplementTask>>(
      '/api/bottle-code-complement-task/queryByCondition',
      condition,
      { responseType: 'json' },
    );
  }

  queryOrCreateCodePrefix(taskId: string): Observable<string> {
    return this.http.post<string>(
      '/api/bottle-code-complement-task/queryOrCreateCodePrefix',
      taskId,
      { responseType: 'text' },
    );
  }

  queryAllCodesInTask(taskId: string): Observable<BottleCodeComplementTaskDetail[]> {
    return this.http.post<BottleCodeComplementTaskDetail[]>(
      '/api/bottle-code-complement-task/queryAllCodesInTask',
      taskId,
      { responseType: 'json' },
    );
  }

  save(model: BottleCodeComplementTask): Observable<void> {
    return this.http.post<void>('/api/bottle-code-complement-task/save', model, {
      responseType: 'text',
    });
  }

  delete(taskId: string): Observable<void> {
    return this.http.post<void>('/api/bottle-code-complement-task/delete', taskId, {
      responseType: 'text',
    });
  }

  printCode(
    model: ModelPrintBottleCodeForComplement,
  ): Observable<ModelPrintBottleCodeForAddBottles> {
    return this.http.post<ModelPrintBottleCodeForAddBottles>(
      '/api/bottle-code-complement-task/printCode',
      model,
      {
        responseType: 'json',
      },
    );
  }

  process(model: ModelBottleCodeComplementTaskProcess): Observable<void> {
    return this.http.post<void>('/api/bottle-code-complement-task/process', model, {
      responseType: 'text',
    });
  }

  done(taskId: string): Observable<void> {
    return this.http.post<void>('/api/bottle-code-complement-task/done', taskId, {
      responseType: 'text',
    });
  }

  uploadArchives(taskId: string, files: File[]): Observable<Archive[]> {
    const formData = new FormData();
    formData.append('task', taskId);
    for (const file of files) {
      formData.append('files', file);
    }
    return this.http.post<Archive[]>('/api/bottle-code-complement-task/uploadArchives', formData);
  }
  queryArchives(taskId: string): Observable<Archive[]> {
    return this.http.post<Archive[]>('/api/bottle-code-complement-task/queryArchives', taskId, {
      responseType: 'json',
    });
  }
}
