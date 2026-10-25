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

// 分页模型：默认每页记录数
import {PageEvent} from '@angular/material/paginator';

export const PAGE_SIZE_DEFAULT = 100;
// 分页模型：默认页码
export const PAGE_DEFAULT = 0;

// 分页模型
export interface Pagination {
  // 总记录数
  total?: number;
  // 总页数
  pageTotal?: number;
  // 每页记录数
  pageSize?: number;
  // 页码
  page?: number;
  // 当页首行在所有记录中的索引号（从0开始）
  firstIndex?: number;
  // 当页尾行在所有记录中的索引号（从0开始）
  lastIndex?: number;
}

// 排序模型：排序方式：正序
export const ASC = 'ASC';
// 排序模型：排序方式：倒序
export const DESC = 'DESC';

// 排序模型
export interface Sorter {
  // 参与排序的数据库中的字段名
  columnName?: string;
  // 排序方式
  seq?: string;
}

// 分页模型工具
export class UtilPagination {
  private _pagination: Pagination = UtilPagination.generatePageModel();
  // 分页模型
  public get pagination() {
    return this._pagination;
  }
  // 分页模型
  public set pagination(pagination: Pagination) {
    this._pagination = UtilPagination.standardPageModel(pagination);
  }
  // 创建一个分页模型实例。
  public static generatePageModel(): Pagination {
    return {
      total: 0,
      pageTotal: 0,
      pageSize: PAGE_SIZE_DEFAULT,
      page: PAGE_DEFAULT,
      firstIndex: 0,
      lastIndex: 0
    };
  }
  // 从 Angular material 分页器所使用的分页模型转为普通分页模型。
  public static fromMat(pageEvent: PageEvent | undefined | null, pagination?: Pagination): Pagination {
    const standard = UtilPagination.standardPageModel(pagination);
    standard.page = (pageEvent?.pageIndex ?? PAGE_DEFAULT) < 0 ? PAGE_DEFAULT : (pageEvent?.pageIndex ?? PAGE_DEFAULT);
    standard.pageSize = (pageEvent?.pageSize ?? PAGE_SIZE_DEFAULT) < 0 ? PAGE_SIZE_DEFAULT : (pageEvent?.pageSize ?? PAGE_SIZE_DEFAULT);
    return UtilPagination.standardPageModel(standard);
  }
  // 规范化分页模型。若不传参数，则构造新的分页模型。
  public static standardPageModel(pagination: Pagination | undefined | null): Pagination {
    if (!pagination) {
      return UtilPagination.generatePageModel();
    }
    const clone: Pagination = {...pagination};
    if (!clone.firstIndex) {
      clone.firstIndex = 0;
    }
    if (!clone.lastIndex) {
      clone.lastIndex = 0;
    }
    if (!clone.page || clone.page < PAGE_DEFAULT) {
      clone.page = PAGE_DEFAULT;
    }
    if (!clone.pageSize || clone.pageSize < 1) {
      clone.pageSize = PAGE_SIZE_DEFAULT;
    }
    if (!clone.pageTotal || clone.pageTotal < 0) {
      clone.pageTotal = 0;
    }
    if (!clone.total || clone.total < 0) {
      clone.total = 0;
    }
    return clone;
  }
  // 分页模型工具。若不传参数，则构造新的分页模型作为 pagination 属性值。
  constructor(pagination: Pagination | undefined | null) {
    this._pagination = UtilPagination.standardPageModel(pagination);
  }
  // 是否还有更多记录。
  public get hasMore(): boolean {
    return !((this._pagination.total || 0) < 1
      || (this._pagination.firstIndex || 0) >= (this._pagination.total || 0)
      || (this._pagination.pageTotal || 0) < (this._pagination.page || PAGE_DEFAULT));
  }
  // 总记录数
  public get total(): number {
    return (this._pagination.total || 0) < 0 ? 0 : (this._pagination.total || 0);
  }
  // 总页数
  public get pageTotal(): number {
    return (this._pagination.pageTotal || 0) < 0 ? 0 : (this._pagination.pageTotal || 0);
  }
  // 每页记录数
  public get pageSize(): number {
    return (this._pagination.pageSize || 0) < 1 ? PAGE_SIZE_DEFAULT : (this._pagination.pageSize || PAGE_SIZE_DEFAULT);
  }
  // 每页记录数
  public set pageSize(pageSize: number) {
    this._pagination.pageSize = pageSize < 1 ? PAGE_SIZE_DEFAULT : pageSize;
  }
  // 页码
  public get page(): number {
    return (this._pagination.page || 0) < PAGE_DEFAULT ? PAGE_DEFAULT : (this._pagination.page || PAGE_DEFAULT);
  }
  // 页码
  public set page(page: number) {
    this._pagination.page = page < PAGE_DEFAULT ? PAGE_DEFAULT : page;
  }
  // 首行记录索引
  public get firstIndex(): number {
    return (this._pagination.firstIndex || 0) < 0 ? 0 : (this._pagination.firstIndex || 0);
  }
  // 尾行记录索引
  public get lastIndex(): number {
    return (this._pagination.lastIndex || 0) < 0 ? 0 : (this._pagination.lastIndex || 0);
  }
}

