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
import { DataInventoryAddBottle } from './data';
import { InventoryAddBottle } from '../../../entities/models/InventoryAddBottle';
import { InventoryAddBottleDetail } from '../../../entities/models/InventoryAddBottleDetail';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionInventoryAddBottle } from '../../../entities/api/QueryConditionInventoryAddBottle';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { ModelPrintBottleCodeForComplement } from '../../../entities/api/ModelPrintBottleCodeForComplement';
import { ModelInventoryAddBottleProcess } from '../../../entities/api/ModelInventoryAddBottleProcess';
import { Archive } from '../../../entities/models/Archive';
import { ModelPrintBottleCodeForAddBottles } from '../../../entities/api/ModelPrintBottleCodeForAddBottles';

export class DataInventoryAddBottleApi implements DataInventoryAddBottle {
  private http = inject(HttpClient);

  queryByCondition(
    condition: QueryCondition<QueryConditionInventoryAddBottle>,
  ): Observable<QueryResultPaged<InventoryAddBottle>> {
    return this.http.post<QueryResultPaged<InventoryAddBottle>>(
      '/api/inventory-add-bottle/queryByCondition',
      condition,
      { responseType: 'json' },
    );
  }

  queryOrCreateCodePrefix(taskId: string): Observable<string> {
    return this.http.post<string>(
      '/api/inventory-add-bottle/queryOrCreateCodePrefix',
      taskId,
      { responseType: 'text' },
    );
  }

  queryAllCodesInTask(taskId: string): Observable<InventoryAddBottleDetail[]> {
    return this.http.post<InventoryAddBottleDetail[]>(
      '/api/inventory-add-bottle/queryAllCodesInTask',
      taskId,
      { responseType: 'json' },
    );
  }

  save(model: InventoryAddBottle): Observable<void> {
    return this.http.post<void>('/api/inventory-add-bottle/save', model, {
      responseType: 'text',
    });
  }

  delete(taskId: string): Observable<void> {
    return this.http.post<void>('/api/inventory-add-bottle/delete', taskId, {
      responseType: 'text',
    });
  }

  printCode(
    model: ModelPrintBottleCodeForComplement,
  ): Observable<ModelPrintBottleCodeForAddBottles> {
    return this.http.post<ModelPrintBottleCodeForAddBottles>(
      '/api/inventory-add-bottle/printCode',
      model,
      {
        responseType: 'json',
      },
    );
  }

  process(model: ModelInventoryAddBottleProcess): Observable<void> {
    return this.http.post<void>('/api/inventory-add-bottle/process', model, {
      responseType: 'text',
    });
  }

  done(taskId: string): Observable<void> {
    return this.http.post<void>('/api/inventory-add-bottle/done', taskId, {
      responseType: 'text',
    });
  }

  uploadArchives(taskId: string, files: File[]): Observable<Archive[]> {
    const formData = new FormData();
    formData.append('task', taskId);
    for (const file of files) {
      formData.append('files', file);
    }
    return this.http.post<Archive[]>('/api/inventory-add-bottle/uploadArchives', formData);
  }
  queryArchives(taskId: string): Observable<Archive[]> {
    return this.http.post<Archive[]>('/api/inventory-add-bottle/queryArchives', taskId, {
      responseType: 'json',
    });
  }
}
