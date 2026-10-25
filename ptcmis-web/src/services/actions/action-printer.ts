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
import { IJT_DataPrinter } from '../datas/printer/data';
import { ModelCodePrint } from '../../entities/api/ModelCodePrint';
import { QueryConditionCodePrint } from '../../entities/api/QueryConditionCodePrint';
import { QueryCondition } from '../../entities/api/QueryCondition';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { CodePrint } from '../../entities/models/CodePrint';
import { CodePrintDetail } from '../../entities/models/CodePrintDetail';

@Injectable({ providedIn: 'root' })
export class ActionPrinter {
  private dataPrinter = inject(IJT_DataPrinter);
  queryByCondition(
    condition: QueryCondition<QueryConditionCodePrint>,
  ): Observable<QueryResultPaged<CodePrint>> {
    return this.dataPrinter.queryByCondition(condition);
  }
  queryAllDetails(idPrint: string): Observable<CodePrintDetail[]> {
    return this.dataPrinter.queryAllDetails(idPrint);
  }
  print(model: ModelCodePrint, save = true): Observable<void> {
    return this.dataPrinter.print(model, save);
  }
  queryAllPrinters(): Observable<{ name: string; selected: boolean }[]> {
    return this.dataPrinter.queryAllPrinters();
  }
  selectPrinter(name: string): Observable<{ name: string; selected: boolean }[]> {
    return this.dataPrinter.selectPrinter(name);
  }
}
