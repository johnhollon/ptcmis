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

import { OverlayModule } from '@angular/cdk/overlay';
import { CdkPortalOutlet, PortalModule, TemplatePortal } from '@angular/cdk/portal';
import {
  AfterViewInit,
  Component,
  inject,
  OnDestroy,
  OnInit,
  signal,
  TemplateRef,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import { toObservable } from '@angular/core/rxjs-interop';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { isEqual } from 'lodash-es';
import {
  catchError,
  combineLatest,
  distinctUntilChanged,
  finalize,
  Observable,
  of,
  startWith,
  Subject,
  switchMap,
  tap
} from 'rxjs';
import { debounceTime, filter, takeUntil } from 'rxjs/operators';
import {
  LayoutPageFunctionSimpleTitle
} from '../../components/00050-layouts/00200-layout-page-function-simple/components/title/component';
import { CompSubTitle } from './sub-title/sub-text';
import { LayoutPageFunctionSimple } from '../../components/00050-layouts/00200-layout-page-function-simple/layout';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { Pagination, UtilPagination } from '../../entities/api/pagination';
import {
  QueryConditionInventoryAddBottle,
  QueryConditionInventoryAddBottleDefault
} from '../../entities/api/QueryConditionInventoryAddBottle';
import { ServiceShowMessage } from '../../services/ui/service-show-error-message.service';
import { Paginator } from '../../components/02000-mat-overrides/pagination';
import {
  LayoutQuerySimple
} from '../../components/00050-layouts/00200-layout-page-function-simple/layouts/query-simple/layout';
import { CompFilters } from './filters/filters';
import { CompTable } from './table/table';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { CompEditor } from './editor/editor';
import {
  InventoryAddBottle,
  InventoryAddBottleDefault
} from '../../entities/models/InventoryAddBottle';
import { CompProcess } from './process/comp';
import { ActionInventoryAddBottle } from '../../services/actions/action-inventory-add-bottle';
import { CompViewer } from './viewer/comp';
import { ServiceShowConfirmDialog } from '../../services/ui/service-show-confirm-dialog';

@Component({
  selector: 'screen-inventory-add-bottle',
  templateUrl: 'screen.html',
  standalone: true,
  imports: [
    MatTableModule,
    OverlayModule,
    PortalModule,
    MatSnackBarModule,
    FormsModule,
    ReactiveFormsModule,
    LayoutPageFunctionSimpleTitle,
    LayoutPageFunctionSimpleTitle,
    Paginator,
    LayoutQuerySimple,
    LayoutPageFunctionSimple,
    CompFilters,
    CompTable,
    MatButton,
    MatIcon,
    CompSubTitle,
    CompEditor,
    CompProcess,
    CompViewer,
  ],
})
export class ScreenInventoryAddBottle implements OnInit, AfterViewInit, OnDestroy {
  private readonly dispose$ = new Subject<void>();
  // *************************************************
  // Loading
  // *************************************************
  protected readonly loadingSig = signal<boolean>(false);
  protected readonly loadingMessageSig = signal<string>('');

  // *************************************************
  // 页面引用
  // *************************************************
  @ViewChild('pageRef', { read: LayoutPageFunctionSimple, static: true })
  private pageRef!: LayoutPageFunctionSimple;

  // *************************************************
  // 表格、分页、筛选区域
  // *************************************************
  protected readonly listSig = signal<InventoryAddBottle[]>([]);
  protected readonly paginationSig = signal<Pagination>(UtilPagination.generatePageModel());
  protected readonly condition$ = new Subject<QueryConditionInventoryAddBottle>();
  private condition: QueryConditionInventoryAddBottle =
    QueryConditionInventoryAddBottleDefault;

  // *************************************************
  // 编辑、查看等操作区
  // *************************************************
  protected selectedModelSig = signal<InventoryAddBottle>({} as InventoryAddBottle);
  private _viewContainerRef = inject(ViewContainerRef);
  // portal outlets
  @ViewChild('titleDescriptionOutlet', { read: CdkPortalOutlet, static: true })
  private titleDescriptionOutlet!: CdkPortalOutlet;
  @ViewChild('titleButtonsOutlet', { read: CdkPortalOutlet, static: true })
  private titleButtonsOutlet!: CdkPortalOutlet;
  @ViewChild('drawerOutlet', { read: CdkPortalOutlet, static: true })
  private drawerOutlet!: CdkPortalOutlet;
  // portals - title description
  @ViewChild('titleDescriptionPortalView', { read: TemplateRef, static: true })
  private titleDescriptionPortalView!: TemplateRef<void>;
  private _titleDescriptionPortalView!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalEdit', { read: TemplateRef, static: true })
  private titleDescriptionPortalEdit!: TemplateRef<void>;
  private _titleDescriptionPortalEdit!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalProcess', { read: TemplateRef, static: true })
  private titleDescriptionPortalProcess!: TemplateRef<void>;
  private _titleDescriptionPortalProcess!: TemplatePortal<void>;
  // portals - title buttons
  @ViewChild('titleButtonsPortalAdd', { read: TemplateRef, static: true })
  private titleButtonsPortalAdd!: TemplateRef<void>;
  private _titleButtonsPortalAdd!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalProcess', { read: TemplateRef, static: true })
  private titleButtonsPortalProcess!: TemplateRef<void>;
  private _titleButtonsPortalProcess!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalViewer', { read: TemplateRef, static: true })
  private titleButtonsPortalViewer!: TemplateRef<void>;
  private _titleButtonsPortalViewer!: TemplatePortal<void>;
  // portals - drawers
  @ViewChild('drawerPortalEdit', { read: TemplateRef, static: true })
  private drawerPortalEdit!: TemplateRef<void>;
  private _drawerPortalEdit!: TemplatePortal<void>;
  @ViewChild('drawerPortalProcess', { read: TemplateRef, static: true })
  private drawerPortalProcess!: TemplateRef<void>;
  private _drawerPortalProcess!: TemplatePortal<void>;
  @ViewChild('drawerPortalView', { read: TemplateRef, static: true })
  private drawerPortalView!: TemplateRef<void>;
  private _drawerPortalView!: TemplatePortal<void>;

  // *************************************************
  // 子模块
  // *************************************************
  protected readonly subModuleProcessSig = signal<string>('process');
  protected readonly subModuleViewerSig = signal<string>('process');

  // *************************************************
  // 自定义服务区域
  // *************************************************
  private readonly serviceShowErrorMessage = inject(ServiceShowMessage);
  private readonly serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);
  private readonly actionInventoryAddBottle = inject(ActionInventoryAddBottle);

  constructor() {
    // 搜索框防抖
    const filter$ = this.condition$
      .pipe(
        startWith(QueryConditionInventoryAddBottleDefault),
        distinctUntilChanged((old, current) => isEqual(old, current)),
        debounceTime(500),
      )
      .pipe(tap((condition) => (this.condition = condition)));
    // 分页器
    const page$ = toObservable(this.paginationSig).pipe(
      // 只有当页码/每页记录数发生变化才触发查询
      distinctUntilChanged(
        (old, current) => old.page === current.page && old.pageSize === current.pageSize,
      ),
    );
    // 搜索框防抖和分页器触发查询。
    combineLatest([filter$, page$])
      .pipe(
        switchMap(() => {
          this.loadingMessageSig.set('正在查询...');
          this.loadingSig.set(true);
          return this.doQuery().pipe(
            finalize(() => {
              this.loadingSig.set(false);
            }),
            catchError((err) => {
              this.serviceShowErrorMessage.showError(err);
              return of<QueryResultPaged<InventoryAddBottle>>({
                list: [],
                page: this.paginationSig(),
              });
            }),
          );
        }),
        takeUntil(this.dispose$),
      )
      .subscribe();
  }

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    // 初始化 portal
    this._titleDescriptionPortalView = new TemplatePortal(
      this.titleDescriptionPortalView,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalEdit = new TemplatePortal(
      this.titleDescriptionPortalEdit,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalProcess = new TemplatePortal(
      this.titleDescriptionPortalProcess,
      this._viewContainerRef,
    );
    this._titleButtonsPortalAdd = new TemplatePortal(
      this.titleButtonsPortalAdd,
      this._viewContainerRef,
    );
    this._titleButtonsPortalProcess = new TemplatePortal(
      this.titleButtonsPortalProcess,
      this._viewContainerRef,
    );
    this._titleButtonsPortalViewer = new TemplatePortal(
      this.titleButtonsPortalViewer,
      this._viewContainerRef,
    );
    this._drawerPortalEdit = new TemplatePortal(this.drawerPortalEdit, this._viewContainerRef);
    this._drawerPortalProcess = new TemplatePortal(
      this.drawerPortalProcess,
      this._viewContainerRef,
    );
    this._drawerPortalView = new TemplatePortal(this.drawerPortalView, this._viewContainerRef);

    // 添加默认 portal
    this.titleButtonsOutlet.attach(this._titleButtonsPortalAdd);
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.condition$.complete();
  }

  protected onFiltersChanged(condition: QueryConditionInventoryAddBottle) {
    this.condition$.next(condition);
  }

  protected onPageChanged(pageModel: Pagination) {
    this.paginationSig.set(pageModel);
  }

  protected requestAdd() {
    this.selectedModelSig.set({ ...InventoryAddBottleDefault });
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalEdit,
      titleButtonsPortal: null,
      drawerPortal: this._drawerPortalEdit,
    });
    this.openDrawer();
  }

  protected requestEdit(model: InventoryAddBottle) {
    this.selectedModelSig.set(model);
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalEdit,
      titleButtonsPortal: null,
      drawerPortal: this._drawerPortalEdit,
    });
    this.openDrawer();
  }

  protected requestProcess(model: InventoryAddBottle) {
    this.selectedModelSig.set(model);
    this.subModuleProcessSig.set('process');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalProcess,
      titleButtonsPortal: this._titleButtonsPortalProcess,
      drawerPortal: this._drawerPortalProcess,
    });
    this.openDrawer();
  }

  protected requestView(model: InventoryAddBottle) {
    this.selectedModelSig.set(model);
    this.subModuleViewerSig.set('process');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalView,
      titleButtonsPortal: this._titleButtonsPortalViewer,
      drawerPortal: this._drawerPortalView,
    });
    this.openDrawer();
  }

  protected requestDelete(model: InventoryAddBottle) {
    this.serviceShowConfirmDialog
      .show('确认删除?', '确认删除')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.requestStartLoading('正在删除...')),
        switchMap((_) => this.actionInventoryAddBottle.delete(model.id)),
        switchMap((_) => this.doQuery()),
        takeUntil(this.dispose$),
        finalize(() => this.requestStopLoading()),
      )
      .subscribe({
        next: (_) => this.serviceShowErrorMessage.showInfo('删除成功。'),
        error: (error) => this.serviceShowErrorMessage.showError(error),
        complete: () => console.log('complete'),
      });
  }

  protected requestQuery() {
    this.loadingMessageSig.set('正在查询...');
    this.loadingSig.set(true);
    this.doQuery()
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        error: (error: unknown) => {
          this.serviceShowErrorMessage.showError(error);
        },
      });
  }

  protected requestStartLoading(message: string) {
    this.loadingMessageSig.set(message);
    this.loadingSig.set(true);
  }
  protected requestStopLoading() {
    this.loadingSig.set(false);
  }

  protected doQuery(): Observable<QueryResultPaged<InventoryAddBottle>> {
    return this.actionInventoryAddBottle
      .queryByCondition({
        page: this.paginationSig(),
        condition: this.condition,
      })
      .pipe(
        tap((queryResult) => {
          this.listSig.set(queryResult.list);
          this.paginationSig.set(UtilPagination.standardPageModel(queryResult.page));
        }),
        takeUntil(this.dispose$),
      );
  }

  private switchPortalContent(portals?: {
    titleButtonsPortal?: TemplatePortal | undefined | null;
    titleDescriptionPortal?: TemplatePortal | undefined | null;
    drawerPortal?: TemplatePortal | undefined | null;
  }): void {
    this.titleButtonsOutlet.detach();
    if (portals?.titleButtonsPortal) this.titleButtonsOutlet.attach(portals.titleButtonsPortal);
    this.titleDescriptionOutlet.detach();
    if (portals?.titleDescriptionPortal)
      this.titleDescriptionOutlet.attach(portals.titleDescriptionPortal);
    this.drawerOutlet.detach();
    if (portals?.drawerPortal) this.drawerOutlet.attach(portals.drawerPortal);
  }

  private openDrawer(): void {
    this.pageRef.openDrawer().catch((err) => this.serviceShowErrorMessage.showError(err));
  }

  protected closeDrawer() {
    this.pageRef.closeDrawer().catch((err) => this.serviceShowErrorMessage.showError(err));
  }

  protected onDrawerClosed() {
    this.switchPortalContent({ titleButtonsPortal: this._titleButtonsPortalAdd });
  }

  protected onDrawerOpened() {}
}
