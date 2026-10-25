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

import {Component, EventEmitter, Input, Output, Service, signal} from '@angular/core';
import {MatPaginatorIntl, MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import {Subject} from 'rxjs';
import {PAGE_DEFAULT, PAGE_SIZE_DEFAULT, Pagination, UtilPagination} from '../../entities/api/pagination';

@Service({autoProvided: false})
export class CustomPaginatorIntl implements MatPaginatorIntl {
  changes = new Subject<void>();

  firstPageLabel = `首页`;
  itemsPerPageLabel = `每页记录数：`;
  lastPageLabel = `尾页`;
  nextPageLabel = '下页';
  previousPageLabel = '上页';

  getRangeLabel(page: number, pageSize: number, length: number): string {
    if (length === 0) {
      return `没有查询到数据。`;
    }
    const amountPages = Math.ceil(length / pageSize);
    return `第 ${page + 1} 页，共 ${amountPages} 页；当前显示第 ${page * pageSize + 1} 条 至 第 ${Math.min((page + 1) * pageSize, length)} 条，共 ${length} 条`;
  }
}


@Component({
  selector: 'paginator',
  standalone: true,
  template: `
    <mat-paginator data-content-for="pagination"
                   [length]="paginationSig().total || 0"
                   [pageSize]="paginationSig().pageSize || PAGE_SIZE_DEFAULT"
                   [pageIndex]="(paginationSig().page || PAGE_DEFAULT)"
                   [pageSizeOptions]="[20, 50, PAGE_SIZE_DEFAULT, 200, 500]"
                   [showFirstLastButtons]="true"
                   (page)="onPageChanged($event)"
                   aria-label="Select page">
    </mat-paginator>
  `,
  imports: [MatPaginatorModule],
  providers: [{provide: MatPaginatorIntl, useClass: CustomPaginatorIntl}],
})
export class Paginator {
  @Input() set page(page: Pagination) {
    this.paginationSig.set(page);
  }
  @Output() change = new EventEmitter<Pagination>();
  protected paginationSig = signal<Pagination>(UtilPagination.generatePageModel());
  protected onPageChanged(page: PageEvent) {
    const pageModel = UtilPagination.fromMat(page, this.paginationSig());
    this.paginationSig.set(pageModel);
    this.change.emit(pageModel);
  }
  protected readonly PAGE_SIZE_DEFAULT = PAGE_SIZE_DEFAULT;
  protected readonly PAGE_DEFAULT = PAGE_DEFAULT;
}
