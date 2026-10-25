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

import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ModelTissueTaskPrintBottleCode } from '../../../entities/api/ModelTissueTaskPrintBottleCode';
import { ModelTissueTaskProcessSettings } from '../../../entities/api/ModelTissueTaskProcessSettings';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { TissueTaskView } from '../../../entities/api/TissueTaskView';
import { Condition, TissueTask } from '../../../entities/models/TissueTask';
import { DataTissueTask } from './data';
import { StorageInOut } from '../../../entities/models/StorageInOut';
import { ModelStorageOutTaskProcessDetail } from '../../../entities/api/ModelStorageOutTaskProcessDetail';
import { ModelTissueTaskPrintBottleCodeForAddBottles } from '../../../entities/api/ModelTissueTaskPrintBottleCodeForAddBottles';
import { ModelTissueTaskProcessDetail } from '../../../entities/api/ModelTissueTaskProcessDetail';
import { ModelPrintBottleCodeForAddBottles } from '../../../entities/api/ModelPrintBottleCodeForAddBottles';
import { Archive } from '../../../entities/models/Archive';
import { StorageOutTaskView } from '../../../entities/api/StorageOutTaskView';

export class DataTissueTaskApi implements DataTissueTask {
  private http = inject(HttpClient);

  queryByTaskId(taskId: string): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/queryByTaskId', taskId, {
      responseType: 'json',
    });
  }
  queryByCondition(
    condition: QueryCondition<Condition>,
  ): Observable<QueryResultPaged<TissueTaskView>> {
    return this.http.post<QueryResultPaged<TissueTaskView>>(
      '/api/tissue-task/queryByCondition',
      condition,
      { responseType: 'json' },
    );
  }
  queryOrCreateCodePrefix(taskId: string): Observable<string> {
    return this.http.post<string>('/api/tissue-task/queryOrCreateCodePrefix', taskId, {
      responseType: 'text',
    });
  }
  createTask(task: TissueTask): Observable<void> {
    return this.http.post<void>('/api/tissue-task/createTask', task, {
      responseType: 'json',
    });
  }
  deleteTask(taskId: string): Observable<void> {
    return this.http.post<void>('/api/tissue-task/deleteTask', taskId, { responseType: 'text' });
  }
  updateTask(task: TissueTask): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/updateTask', task, {
      responseType: 'json',
    });
  }
  printBottleCode(model: ModelTissueTaskPrintBottleCode): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/printBottleCode', model, {
      responseType: 'json',
    });
  }
  printBottleCodeForAddBottles(
    model: ModelTissueTaskPrintBottleCodeForAddBottles,
  ): Observable<ModelPrintBottleCodeForAddBottles> {
    return this.http.post<ModelPrintBottleCodeForAddBottles>(
      '/api/tissue-task/printBottleCodeForAddBottles',
      model,
      {
        responseType: 'json',
      },
    );
  }
  saveProcessSettings(settings: ModelTissueTaskProcessSettings): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/saveProcessSettings', settings, {
      responseType: 'json',
    });
  }
  deleteStorageOut(model: StorageOutTaskView): Observable<void> {
    return this.http.post<void>('/api/tissue-task/deleteStorageOut', model, {
      responseType: 'json',
    });
  }
  acceptStorageOut(model: ModelStorageOutTaskProcessDetail): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/acceptStorageOut', model, {
      responseType: 'json',
    });
  }
  saveProcess(processDetail: ModelTissueTaskProcessDetail): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/saveProcess', processDetail, {
      responseType: 'json',
    });
  }
  updateTaskStatusToFinish(taskId: string): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/updateTaskStatusToFinish', taskId, {
      responseType: 'json',
    });
  }
  updateTaskStatusFromFinishToProcess(taskId: string): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>(
      '/api/tissue-task/updateTaskStatusFromFinishToProcess',
      taskId,
      {
        responseType: 'json',
      },
    );
  }
  applyStorageIn(taskId: string): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/applyStorageIn', taskId, {
      responseType: 'json',
    });
  }
  deleteStorageIn(inOutId: string): Observable<TissueTaskView> {
    return this.http.post<TissueTaskView>('/api/tissue-task/deleteStorageIn', inOutId, {
      responseType: 'json',
    });
  }
  uploadArchives(taskId: string, description: string, files: File[]): Observable<Archive[]> {
    const formData = new FormData();
    formData.append('recordId', taskId);
    formData.append('description', description ?? '');
    for (const file of files) {
      formData.append('files', file);
    }
    return this.http.post<Archive[]>('/api/tissue-task/uploadArchives', formData);
  }
  queryArchives(taskId: string): Observable<Archive[]> {
    return this.http.post<Archive[]>('/api/tissue-task/queryArchives', taskId, {
      responseType: 'json',
    });
  }
}
