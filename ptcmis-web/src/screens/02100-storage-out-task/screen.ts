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
  ViewContainerRef,
} from '@angular/core';
import { toObservable } from '@angular/core/rxjs-interop';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
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
  tap,
} from 'rxjs';
import { debounceTime, filter, map, takeUntil } from 'rxjs/operators';
import { LayoutPageFunctionSimpleTitle } from '../../components/00050-layouts/00200-layout-page-function-simple/components/title/component';
import { SubTitleStorageOutTask } from './sub-title/sub-text';
import { LayoutPageFunctionSimple } from '../../components/00050-layouts/00200-layout-page-function-simple/layout';
import { StorageOutTaskView } from '../../entities/api/StorageOutTaskView';
import { QueryResultPaged } from '../../entities/api/QueryResultPaged';
import { Pagination, UtilPagination } from '../../entities/api/pagination';
import {
  QueryConditionStorageOutTaskView,
  QueryConditionStorageOutTaskViewDefault,
} from '../../entities/api/QueryConditionStorageOutTaskView';
import { ServiceShowMessage } from '../../services/ui/service-show-error-message.service';
import { TableStorageOutTask } from './table/table';
import { FiltersStorageOutTask } from './filters/filters';
import { Paginator } from '../../components/02000-mat-overrides/pagination';
import { LayoutQuerySimple } from '../../components/00050-layouts/00200-layout-page-function-simple/layouts/query-simple/layout';
import { ActionStorageInOut } from '../../services/actions/action-storage-in-out';
import { StorageOutTaskRejBiz } from './rej-biz/comp';
import { StorageOutTaskProcess } from './process/comp';
import { StorageOutTaskFinish } from './finish/comp';
import { StorageOutTaskViewer } from './viewer/comp';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { getTissueTaskDefault, TissueTask } from '../../entities/models/TissueTask';
import { CompEditor } from './editor/comp';
import { ActionTissueTask } from '../../services/actions/action-tissue-task';
import { ServiceShowConfirmDialog } from '../../services/ui/service-show-confirm-dialog';

@Component({
  selector: 'screen-storage-out-task',
  templateUrl: 'screen.html',
  standalone: true,
  imports: [
    MatTableModule,
    OverlayModule,
    PortalModule,
    MatSnackBarModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    LayoutPageFunctionSimpleTitle,
    LayoutPageFunctionSimpleTitle,
    SubTitleStorageOutTask,
    TableStorageOutTask,
    FiltersStorageOutTask,
    Paginator,
    LayoutQuerySimple,
    LayoutPageFunctionSimple,
    StorageOutTaskRejBiz,
    StorageOutTaskProcess,
    StorageOutTaskFinish,
    StorageOutTaskRejBiz,
    StorageOutTaskProcess,
    StorageOutTaskFinish,
    StorageOutTaskViewer,
    MatButton,
    MatIcon,
    CompEditor,
    CompEditor,
  ],
})
export class ScreenStorageOutTask implements OnInit, AfterViewInit, OnDestroy {
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
  protected readonly listSig = signal<StorageOutTaskView[]>([]);
  protected readonly paginationSig = signal<Pagination>(UtilPagination.generatePageModel());
  protected readonly condition$ = new Subject<QueryConditionStorageOutTaskView>();
  private condition: QueryConditionStorageOutTaskView = {
    ...QueryConditionStorageOutTaskViewDefault,
  };

  // *************************************************
  // 编辑、查看等操作区
  // *************************************************
  protected selectedModelSig = signal<StorageOutTaskView>({} as StorageOutTaskView);
  protected selectedTaskSig = signal<TissueTask>({} as TissueTask);
  private _viewContainerRef = inject(ViewContainerRef);
  // portal outlets
  @ViewChild('titleDescriptionOutlet', { read: CdkPortalOutlet, static: true })
  private titleDescriptionOutlet!: CdkPortalOutlet;
  @ViewChild('titleButtonsOutlet', { read: CdkPortalOutlet, static: true })
  private titleButtonsOutlet!: CdkPortalOutlet;
  @ViewChild('drawerOutlet', { read: CdkPortalOutlet, static: true })
  private drawerOutlet!: CdkPortalOutlet;
  // portals - title description
  @ViewChild('titleDescriptionPortalAdd', { read: TemplateRef, static: true })
  private titleDescriptionPortalAdd!: TemplateRef<void>;
  private _titleDescriptionPortalAdd!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalEdit', { read: TemplateRef, static: true })
  private titleDescriptionPortalEdit!: TemplateRef<void>;
  private _titleDescriptionPortalEdit!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalViewer', { read: TemplateRef, static: true })
  private titleDescriptionPortalViewer!: TemplateRef<void>;
  private _titleDescriptionPortalViewer!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalProcess', { read: TemplateRef, static: true })
  private titleDescriptionPortalProcess!: TemplateRef<void>;
  private _titleDescriptionPortalProcess!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalRejBiz', { read: TemplateRef, static: true })
  private titleDescriptionPortalRejBiz!: TemplateRef<void>;
  private _titleDescriptionPortalRejBiz!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalFinish', { read: TemplateRef, static: true })
  private titleDescriptionPortalFinish!: TemplateRef<void>;
  private _titleDescriptionPortalFinish!: TemplatePortal<void>;
  // portals - title buttons
  @ViewChild('titleButtonsPortalAdd', { read: TemplateRef, static: true })
  private titleButtonsPortalAdd!: TemplateRef<void>;
  private _titleButtonsPortalAdd!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalProcess', { read: TemplateRef, static: true })
  private titleButtonsPortalProcess!: TemplateRef<void>;
  private _titleButtonsPortalProcess!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalFinish', { read: TemplateRef, static: true })
  private titleButtonsPortalFinish!: TemplateRef<void>;
  private _titleButtonsPortalFinish!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalViewer', { read: TemplateRef, static: true })
  private titleButtonsPortalViewer!: TemplateRef<void>;
  private _titleButtonsPortalViewer!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalRejBiz', { read: TemplateRef, static: true })
  private titleButtonsPortalRejBiz!: TemplateRef<void>;
  private _titleButtonsPortalRejBiz!: TemplatePortal<void>;
  // portals - drawers
  @ViewChild('drawerPortalAdd', { read: TemplateRef, static: true })
  private drawerPortalAdd!: TemplateRef<void>;
  private _drawerPortalAdd!: TemplatePortal<void>;
  @ViewChild('drawerPortalEdit', { read: TemplateRef, static: true })
  private drawerPortalEdit!: TemplateRef<void>;
  private _drawerPortalEdit!: TemplatePortal<void>;
  @ViewChild('drawerPortalRejBiz', { read: TemplateRef, static: true })
  private drawerPortalRejBiz!: TemplateRef<void>;
  private _drawerPortalRejBiz!: TemplatePortal<void>;
  @ViewChild('drawerPortalProcess', { read: TemplateRef, static: true })
  private drawerPortalProcess!: TemplateRef<void>;
  private _drawerPortalProcess!: TemplatePortal<void>;
  @ViewChild('drawerPortalFinish', { read: TemplateRef, static: true })
  private drawerPortalFinish!: TemplateRef<void>;
  private _drawerPortalFinish!: TemplatePortal<void>;
  @ViewChild('drawerPortalViewer', { read: TemplateRef, static: true })
  private drawerPortalViewer!: TemplateRef<void>;
  private _drawerPortalViewer!: TemplatePortal<void>;

  // *************************************************
  // 子模块
  // *************************************************
  protected readonly subModuleProcessSig = signal<string>('process');
  protected readonly subModuleRejBizSig = signal<string>('process');
  protected readonly subModuleFinishSig = signal<string>('process');
  protected readonly subModuleViewerSig = signal<string>('process');

  // *************************************************
  // 自定义服务区域
  // *************************************************
  private readonly serviceShowErrorMessage = inject(ServiceShowMessage);
  private readonly actionStorageInOut = inject(ActionStorageInOut);
  private readonly actionTissueTask = inject(ActionTissueTask);
  private readonly serviceDialog = inject(MatDialog);
  private serviceShowConfirmDialog = inject(ServiceShowConfirmDialog);

  constructor() {
    // 搜索框防抖
    const filter$ = this.condition$
      .pipe(
        startWith(QueryConditionStorageOutTaskViewDefault),
        distinctUntilChanged((old, current) => isEqual(old, current)),
        debounceTime(500),
      )
      .pipe(tap((condition) => (this.condition = { ...condition, inOutType: 'out' })));
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
              return of<QueryResultPaged<StorageOutTaskView>>({
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
    this._titleDescriptionPortalAdd = new TemplatePortal(
      this.titleDescriptionPortalAdd,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalEdit = new TemplatePortal(
      this.titleDescriptionPortalEdit,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalViewer = new TemplatePortal(
      this.titleDescriptionPortalViewer,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalProcess = new TemplatePortal(
      this.titleDescriptionPortalProcess,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalRejBiz = new TemplatePortal(
      this.titleDescriptionPortalRejBiz,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalFinish = new TemplatePortal(
      this.titleDescriptionPortalFinish,
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
    this._titleButtonsPortalRejBiz = new TemplatePortal(
      this.titleButtonsPortalRejBiz,
      this._viewContainerRef,
    );
    this._titleButtonsPortalFinish = new TemplatePortal(
      this.titleButtonsPortalFinish,
      this._viewContainerRef,
    );
    this._titleButtonsPortalViewer = new TemplatePortal(
      this.titleButtonsPortalViewer,
      this._viewContainerRef,
    );
    this._drawerPortalRejBiz = new TemplatePortal(this.drawerPortalRejBiz, this._viewContainerRef);
    this._drawerPortalProcess = new TemplatePortal(
      this.drawerPortalProcess,
      this._viewContainerRef,
    );
    this._drawerPortalFinish = new TemplatePortal(this.drawerPortalFinish, this._viewContainerRef);
    this._drawerPortalViewer = new TemplatePortal(this.drawerPortalViewer, this._viewContainerRef);
    this._drawerPortalAdd = new TemplatePortal(this.drawerPortalAdd, this._viewContainerRef);
    this._drawerPortalEdit = new TemplatePortal(this.drawerPortalEdit, this._viewContainerRef);
    this.switchPortalContent({ titleButtonsPortal: this._titleButtonsPortalAdd });
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.condition$.complete();
  }

  protected onFiltersChanged(condition: QueryConditionStorageOutTaskView) {
    this.condition$.next(condition);
  }

  protected onPageChanged(pageModel: Pagination) {
    this.paginationSig.set(pageModel);
  }

  protected requestAdd() {
    this.selectedTaskSig.set(getTissueTaskDefault(''));
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalAdd,
      titleButtonsPortal: undefined,
      drawerPortal: this._drawerPortalAdd,
    });
    this.openDrawer();
  }

  protected requestEditTask(model: StorageOutTaskView) {
    this.loadingMessageSig.set('正在查询...');
    this.loadingSig.set(true);
    this.actionTissueTask
      .queryByTaskId(model.idTask)
      .pipe(
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        next: (task) => {
          if (!task || !task.task || !task.task.id) {
            this.serviceShowErrorMessage.showError('无法找到相关任务。');
            return;
          }
          this.selectedTaskSig.set(task.task);
          this.switchPortalContent({
            titleDescriptionPortal: this._titleDescriptionPortalEdit,
            titleButtonsPortal: undefined,
            drawerPortal: this._drawerPortalEdit,
          });
          this.openDrawer();
        },
        error: (error: unknown) => {
          this.serviceShowErrorMessage.showError(error);
        },
      });
  }

  protected requestDelete(model: StorageOutTaskView) {
    this.serviceShowConfirmDialog
      .show('确定要删除此出库业务吗？', '确认删除')
      .pipe(
        filter((v) => v === 0),
        tap((_) => this.loadingMessageSig.set('正在删除...')),
        switchMap((_) => {
          return this.actionTissueTask.deleteStorageOut(model).pipe(
            map((_) => undefined),
            takeUntil(this.dispose$),
          );
        }),
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowErrorMessage.showInfo('删除成功。');
          this.requestQuery();
        },
        error: (error) => this.serviceShowErrorMessage.showError(error),
      });
  }

  protected doAdd(model: TissueTask) {
    this.loadingMessageSig.set('正在处理...');
    this.loadingSig.set(true);
    this.actionTissueTask
      .createTask(model)
      .pipe(
        switchMap(() => this.doQuery()),
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowErrorMessage.showInfo('操作成功。');
          this.closeDrawer();
        },
        error: (error: unknown) => {
          this.serviceShowErrorMessage.showError(error);
        },
      });
  }

  protected doUpdate(model: TissueTask) {
    this.loadingMessageSig.set('正在处理...');
    this.loadingSig.set(true);
    this.actionTissueTask
      .updateTask(model)
      .pipe(
        switchMap(() => this.doQuery()),
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowErrorMessage.showInfo('操作成功。');
          this.closeDrawer();
        },
        error: (error: unknown) => {
          this.serviceShowErrorMessage.showError(error);
        },
      });
  }

  protected requestView(model: StorageOutTaskView) {
    this.selectedModelSig.set(model);
    this.subModuleViewerSig.set('process');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalViewer,
      titleButtonsPortal: this._titleButtonsPortalViewer,
      drawerPortal: this._drawerPortalViewer,
    });
    this.openDrawer();
  }

  protected requestProcess(model: StorageOutTaskView) {
    this.selectedModelSig.set(model);
    this.subModuleProcessSig.set('process');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalProcess,
      titleButtonsPortal: this._titleButtonsPortalProcess,
      drawerPortal: this._drawerPortalProcess,
    });
    this.openDrawer();
  }

  protected requestRejBiz(model: StorageOutTaskView) {
    this.selectedModelSig.set(model);
    this.subModuleRejBizSig.set('process');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalRejBiz,
      titleButtonsPortal: this._titleButtonsPortalRejBiz,
      drawerPortal: this._drawerPortalRejBiz,
    });
    this.openDrawer();
  }

  protected requestFinish(model: StorageOutTaskView) {
    this.selectedModelSig.set(model);
    this.subModuleFinishSig.set('process');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalFinish,
      titleButtonsPortal: this._titleButtonsPortalFinish,
      drawerPortal: this._drawerPortalFinish,
    });
    this.openDrawer();
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

  protected doQuery(): Observable<QueryResultPaged<StorageOutTaskView>> {
    return this.actionStorageInOut
      .queryByCondition({
        page: this.paginationSig(),
        condition: this.condition,
      })
      .pipe(
        tap((queryResult) => {
          this.listSig.set(queryResult.list);
          this.paginationSig.set(UtilPagination.standardPageModel(queryResult.page));
        }),
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
