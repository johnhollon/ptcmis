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

import { Observable, of, switchMap } from 'rxjs';
import { DataPrinter } from './data';
import { ModelCodePrint } from '../../../entities/api/ModelCodePrint';
import { QueryCondition } from '../../../entities/api/QueryCondition';
import { QueryConditionCodePrint } from '../../../entities/api/QueryConditionCodePrint';
import { QueryResultPaged } from '../../../entities/api/QueryResultPaged';
import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CodePrint } from '../../../entities/models/CodePrint';
import { CodePrintDetail } from '../../../entities/models/CodePrintDetail';

interface PrinterItem {
  name: string;
  selected: boolean;
}

const LODOP = (globalThis as any).LODOP;
if (LODOP) {
  LODOP.PRINT_INIT('');
}

const STORAGE_KEY = 'ptcmis_selected_printer';

export class DataPrinterApi implements DataPrinter {
  private http = inject(HttpClient);

  queryByCondition(
    condition: QueryCondition<QueryConditionCodePrint>,
  ): Observable<QueryResultPaged<CodePrint>> {
    return this.http.post<QueryResultPaged<CodePrint>>(
      '/api/code-print/queryByCondition',
      condition,
      {
        responseType: 'json',
      },
    );
  }
  queryAllDetails(idPrint: string): Observable<CodePrintDetail[]> {
    return this.http.post<CodePrintDetail[]>('/api/code-print/queryAllDetails', idPrint, {
      responseType: 'json',
    });
  }
  print(model: ModelCodePrint, save: boolean): Observable<void> {
    if (!save) return this.printByLocalPrinter(model.codePrint.description, model.codePrintDetails);
    return this.http
      .post<void>('/api/code-print/save', model, {
        responseType: 'text',
      })
      .pipe(
        switchMap((_) =>
          this.printByLocalPrinter(model.codePrint.description, model.codePrintDetails),
        ),
      );
  }

  private selectedPrinterName: string | null = null;
  private currentTaskIndex: number = 0;

  constructor() {
    const saved = localStorage.getItem(STORAGE_KEY);
    if (saved) {
      this.selectedPrinterName = saved;
    }
  }

  queryAllPrinters(): Observable<PrinterItem[]> {
    if (!LODOP) {
      return of([]);
    }
    const count = LODOP.GET_PRINTER_COUNT();
    const list: PrinterItem[] = [];
    for (let i = 0; i < count; i++) {
      const name = LODOP.GET_PRINTER_NAME(i);
      list.push({
        name,
        selected: name === this.selectedPrinterName,
      });
    }
    return of(list);
  }

  selectPrinter(name: string): Observable<PrinterItem[]> {
    if (!LODOP) {
      return of([]);
    }
    // 内存保存
    this.selectedPrinterName = name;
    // 持久化存入localStorage，刷新页面、关闭浏览器都还在
    localStorage.setItem(STORAGE_KEY, name);

    const count = LODOP.GET_PRINTER_COUNT();
    const list: PrinterItem[] = [];
    for (let i = 0; i < count; i++) {
      const pName = LODOP.GET_PRINTER_NAME(i);
      list.push({
        name: pName,
        selected: pName === this.selectedPrinterName,
      });
    }
    return of(list);
  }

  private printByLocalPrinter(taskName: string, codes: CodePrintDetail[]): Observable<void> {
    if (!LODOP) throw '尚未安装打印组件';
    return of(this.selectedPrinterName).pipe(
      switchMap(async (savedPrinter) => {
        let printerName = savedPrinter;
        // 如果没有保存的打印机，弹出系统选择框
        if (!printerName) {
          LODOP.PRINT_INIT('');
          const printerIndex = LODOP.SET_PRINTER_INDEXA();
          if (printerIndex < 0) {
            throw '未选择打印机';
          }
          printerName = LODOP.GET_VALUE('PRINTER_NAME', 0);
          if (!printerName) {
            throw '未选择打印机';
          }
          this.selectedPrinterName = printerName;
          localStorage.setItem(STORAGE_KEY, printerName);
        }
        return printerName;
      }),
      switchMap((printer) => {
        return new Observable<void>((subscriber) => {
          LODOP.PRINT_INIT(`${taskName}-${++this.currentTaskIndex}`);
          LODOP.SET_PRINT_MODE('SEND_RAW_DATA_ENCODE', 'GBK');
          const ok = LODOP.SET_PRINTER_INDEX(printer);
          if (!ok) throw '连接打印机失败';
          LODOP.On_Return = (_taskId: any, value: any) => {
            if (value) {
              subscriber.next();
              subscriber.complete();
            } else {
              subscriber.error('发送打印命令失败。');
            }
          };
          const data =
            codes
              .map((code) => {
                const date = new Date(code.operateTime);
                const dateString =
                  date.getFullYear() +
                  '-' +
                  String(date.getMonth() + 1).padStart(2, '0') +
                  '-' +
                  String(date.getDate()).padStart(2, '0');
                return `SIZE 40 mm,20 mm\r\nGAP 2 mm,0 mm\r\nDIRECTION 1\r\nCLS\r\nQRCODE 10,16,H,6,A,0,"${code.code}"\r\nTEXT 144,16,"TSS24.BF2",0,1,1,"${code.code}"\r\nTEXT 144,42,"TSS24.BF2",0,1,1,"${code.varietyCode}"\r\nTEXT 144,68,"TSS24.BF2",0,1,1,"P${String(code.explantCollectionBatch ?? 0).padStart(8, '0')} D${String(code.generation ?? 0).padStart(3, '0')}"\r\nTEXT 144,94,"TSS24.BF2",0,1,1,"${code.operatorName}"\r\nTEXT 144,120,"TSS24.BF2",0,1,1,"${dateString}"\r\nPRINT 1`;
              })
              .join('\r\n') + '\r\n';
          LODOP.SEND_PRINT_RAWDATA(data);
        });
      }),
    );
  }
}
