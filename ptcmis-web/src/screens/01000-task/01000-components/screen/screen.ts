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
  Input,
  OnDestroy,
  OnInit,
  signal,
  TemplateRef,
  ViewChild,
  ViewContainerRef,
} from '@angular/core';
import { toObservable } from '@angular/core/rxjs-interop';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';
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
import { debounceTime, takeUntil } from 'rxjs/operators';
import { LayoutPageFunctionSimpleTitle } from '../../../../components/00050-layouts/00200-layout-page-function-simple/components/title/component';
import { LayoutPageFunctionSimple } from '../../../../components/00050-layouts/00200-layout-page-function-simple/layout';
import { LayoutQuerySimple } from '../../../../components/00050-layouts/00200-layout-page-function-simple/layouts/query-simple/layout';
import { PipeTaskType } from '../../../../components/00200-pipes/pipe-task-type';
import { Paginator } from '../../../../components/02000-mat-overrides/pagination';
import { Pagination, UtilPagination } from '../../../../entities/api/pagination';
import { QueryResultPaged } from '../../../../entities/api/QueryResultPaged';
import { getTissueTaskViewDefault, TissueTaskView } from '../../../../entities/api/TissueTaskView';
import {
  Condition,
  ConditionDefault,
  TissueTask,
  TissueTaskType,
} from '../../../../entities/models/TissueTask';
import { ActionTissueTask } from '../../../../services/actions/action-tissue-task';
import { ServiceShowMessage } from '../../../../services/ui/service-show-error-message.service';
import { ComponentFiltersTissueTask } from '../filters/component';
import { CompEditor } from '../opp-drawers/editor/comp';
import { CompProcess, TissueTaskProcessSubModule } from '../opp-drawers/process/comp';
import { SubTitleTissueTask } from '../sub-title/sub-text';
import { ComponentTableTissueTask } from '../table/component';

@Component({
  selector: 'screen-tissue-task',
  templateUrl: 'screen.html',
  standalone: true,
  imports: [
    MatTableModule,
    OverlayModule,
    PortalModule,
    MatSnackBarModule,
    MatDialogModule,
    FormsModule,
    MatButton,
    MatIcon,
    ReactiveFormsModule,
    LayoutPageFunctionSimple,
    LayoutPageFunctionSimpleTitle,
    LayoutQuerySimple,
    ComponentFiltersTissueTask,
    ComponentTableTissueTask,
    Paginator,
    SubTitleTissueTask,
    PipeTaskType,
    CompEditor,
    CompProcess,
  ],
})
export class ScreenTissueTask implements OnInit, AfterViewInit, OnDestroy {
  @Input() taskType: TissueTaskType | '' = '';
  @Input() updateChildGeneration: boolean = true;

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
  protected readonly listSig = signal<TissueTaskView[]>([]);
  protected readonly paginationSig = signal<Pagination>(UtilPagination.generatePageModel());
  protected readonly condition$ = new Subject<Condition>();
  private condition: Condition = ConditionDefault;

  // *************************************************
  // 编辑、查看等操作区
  // *************************************************
  protected selectedModelSig = signal<TissueTaskView>({} as TissueTaskView);
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
  @ViewChild('titleDescriptionPortalAdd', { read: TemplateRef, static: true })
  private titleDescriptionPortalAdd!: TemplateRef<void>;
  private _titleDescriptionPortalAdd!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalEdit', { read: TemplateRef, static: true })
  private titleDescriptionPortalEdit!: TemplateRef<void>;
  private _titleDescriptionPortalEdit!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalStorageOut', { read: TemplateRef, static: true })
  private titleDescriptionPortalStorageOut!: TemplateRef<void>;
  private _titleDescriptionPortalStorageOut!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalProcess', { read: TemplateRef, static: true })
  private titleDescriptionPortalProcess!: TemplateRef<void>;
  private _titleDescriptionPortalProcess!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalFinish', { read: TemplateRef, static: true })
  private titleDescriptionPortalFinish!: TemplateRef<void>;
  private _titleDescriptionPortalFinish!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalUpdateToProcess', { read: TemplateRef, static: true })
  private titleDescriptionPortalUpdateToProcess!: TemplateRef<void>;
  private _titleDescriptionPortalUpdateToProcess!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalStorageIn', { read: TemplateRef, static: true })
  private titleDescriptionPortalStorageIn!: TemplateRef<void>;
  private _titleDescriptionPortalStorageIn!: TemplatePortal<void>;
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
  @ViewChild('titleButtonsPortalFinish', { read: TemplateRef, static: true })
  private titleButtonsPortalFinish!: TemplateRef<void>;
  private _titleButtonsPortalFinish!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalUpdateToProcess', { read: TemplateRef, static: true })
  private titleButtonsPortalUpdateToProcess!: TemplateRef<void>;
  private _titleButtonsPortalUpdateToProcess!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalStorageIn', { read: TemplateRef, static: true })
  private titleButtonsPortalStorageIn!: TemplateRef<void>;
  private _titleButtonsPortalStorageIn!: TemplatePortal<void>;
  // portals - drawers
  @ViewChild('drawerPortalViewer', { read: TemplateRef, static: true })
  private drawerPortalViewer!: TemplateRef<void>;
  private _drawerPortalViewer!: TemplatePortal<void>;
  @ViewChild('drawerPortalAdd', { read: TemplateRef, static: true })
  private drawerPortalAdd!: TemplateRef<void>;
  private _drawerPortalAdd!: TemplatePortal<void>;
  @ViewChild('drawerPortalEdit', { read: TemplateRef, static: true })
  private drawerPortalEdit!: TemplateRef<void>;
  private _drawerPortalEdit!: TemplatePortal<void>;
  @ViewChild('drawerPortalStorageOut', { read: TemplateRef, static: true })
  private drawerPortalStorageOut!: TemplateRef<void>;
  private _drawerPortalStorageOut!: TemplatePortal<void>;
  @ViewChild('drawerPortalProcess', { read: TemplateRef, static: true })
  private drawerPortalProcess!: TemplateRef<void>;
  private _drawerPortalProcess!: TemplatePortal<void>;
  @ViewChild('drawerPortalFinish', { read: TemplateRef, static: true })
  private drawerPortalFinish!: TemplateRef<void>;
  private _drawerPortalFinish!: TemplatePortal<void>;
  @ViewChild('drawerPortalUpdateToProcess', { read: TemplateRef, static: true })
  private drawerPortalUpdateToProcess!: TemplateRef<void>;
  private _drawerPortalUpdateToProcess!: TemplatePortal<void>;
  @ViewChild('drawerPortalStorageIn', { read: TemplateRef, static: true })
  private drawerPortalStorageIn!: TemplateRef<void>;
  private _drawerPortalStorageIn!: TemplatePortal<void>;

  // *************************************************
  // 任务执行区域
  // *************************************************
  protected processSubModuleSig = signal<TissueTaskProcessSubModule>('process');
  protected viewerSubModuleSig = signal<TissueTaskProcessSubModule>('stat');
  protected updateToProcessSubModuleSig = signal<TissueTaskProcessSubModule>('stat');
  protected storageInSubModuleSig = signal<TissueTaskProcessSubModule>('storage-in');

  // *************************************************
  // 自定义服务区域
  // *************************************************
  private readonly serviceShowErrorMessage = inject(ServiceShowMessage);
  private readonly actionTissueTask = inject(ActionTissueTask);
  private readonly serviceDialog = inject(MatDialog);

  constructor() {
    // 搜索框防抖
    const filter$ = this.condition$
      .pipe(
        startWith(ConditionDefault),
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
              return of<QueryResultPaged<TissueTaskView>>({ list: [], page: this.paginationSig() });
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
    this._titleDescriptionPortalAdd = new TemplatePortal(
      this.titleDescriptionPortalAdd,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalEdit = new TemplatePortal(
      this.titleDescriptionPortalEdit,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalStorageOut = new TemplatePortal(
      this.titleDescriptionPortalStorageOut,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalProcess = new TemplatePortal(
      this.titleDescriptionPortalProcess,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalFinish = new TemplatePortal(
      this.titleDescriptionPortalFinish,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalUpdateToProcess = new TemplatePortal(
      this.titleDescriptionPortalUpdateToProcess,
      this._viewContainerRef,
    );
    this._titleDescriptionPortalStorageIn = new TemplatePortal(
      this.titleDescriptionPortalStorageIn,
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
    this._titleButtonsPortalFinish = new TemplatePortal(
      this.titleButtonsPortalFinish,
      this._viewContainerRef,
    );
    this._titleButtonsPortalUpdateToProcess = new TemplatePortal(
      this.titleButtonsPortalUpdateToProcess,
      this._viewContainerRef,
    );
    this._titleButtonsPortalStorageIn = new TemplatePortal(
      this.titleButtonsPortalStorageIn,
      this._viewContainerRef,
    );
    this._drawerPortalAdd = new TemplatePortal(this.drawerPortalAdd, this._viewContainerRef);
    this._drawerPortalEdit = new TemplatePortal(this.drawerPortalEdit, this._viewContainerRef);
    this._drawerPortalViewer = new TemplatePortal(this.drawerPortalViewer, this._viewContainerRef);
    this._drawerPortalStorageOut = new TemplatePortal(
      this.drawerPortalStorageOut,
      this._viewContainerRef,
    );
    this._drawerPortalProcess = new TemplatePortal(
      this.drawerPortalProcess,
      this._viewContainerRef,
    );
    this._drawerPortalViewer = new TemplatePortal(this.drawerPortalViewer, this._viewContainerRef);
    this._drawerPortalFinish = new TemplatePortal(this.drawerPortalFinish, this._viewContainerRef);
    this._drawerPortalUpdateToProcess = new TemplatePortal(
      this.drawerPortalUpdateToProcess,
      this._viewContainerRef,
    );
    this._drawerPortalStorageIn = new TemplatePortal(
      this.drawerPortalStorageIn,
      this._viewContainerRef,
    );
    // 设置默认 portalOutlet
    if (this.taskType === 'induction') {
      this.titleButtonsOutlet.attach(this._titleButtonsPortalAdd);
    }
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.condition$.complete();
  }

  protected onFiltersChanged(condition: Condition) {
    this.condition$.next(condition);
  }

  protected onPageChanged(pageModel: Pagination) {
    this.paginationSig.set(pageModel);
  }

  protected requestView(model: TissueTaskView) {
    this.selectedModelSig.set(model);
    this.viewerSubModuleSig.set('stat');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalView,
      titleButtonsPortal: this._titleButtonsPortalViewer,
      drawerPortal: this._drawerPortalViewer,
    });
    this.openDrawer();
  }

  protected requestAdd() {
    this.selectedModelSig.set(getTissueTaskViewDefault(this.taskType));
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalAdd,
      titleButtonsPortal: undefined,
      drawerPortal: this._drawerPortalAdd,
    });
    this.openDrawer();
  }

  protected requestEdit(model: TissueTaskView) {
    this.selectedModelSig.set(model);
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalEdit,
      titleButtonsPortal: undefined,
      drawerPortal: this._drawerPortalEdit,
    });
    this.openDrawer();
  }

  protected requestProcess(model: TissueTaskView) {
    this.selectedModelSig.set(model);
    this.processSubModuleSig.set('process');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalProcess,
      titleButtonsPortal: this._titleButtonsPortalProcess,
      drawerPortal: this._drawerPortalProcess,
    });
    this.openDrawer();
  }

  protected requestUpdateToProcess(model: TissueTaskView) {
    this.selectedModelSig.set(model);
    this.updateToProcessSubModuleSig.set('process');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalUpdateToProcess,
      titleButtonsPortal: this._titleButtonsPortalUpdateToProcess,
      drawerPortal: this._drawerPortalUpdateToProcess,
    });
    this.openDrawer();
  }

  protected requestStorageIn(model: TissueTaskView) {
    this.selectedModelSig.set(model);
    this.viewerSubModuleSig.set('storage-in');
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalStorageIn,
      titleButtonsPortal: this._titleButtonsPortalStorageIn,
      drawerPortal: this._drawerPortalStorageIn,
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

  protected doQuery(): Observable<QueryResultPaged<TissueTaskView>> {
    if (!!this.taskType) {
      this.condition.taskType = this.taskType;
    }
    return this.actionTissueTask
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

  protected doDelete(model: TissueTaskView) {
    this.loadingMessageSig.set('正在执行删除...');
    this.loadingSig.set(true);
    this.actionTissueTask
      .deleteTask(model.taskId)
      .pipe(
        switchMap(() => this.doQuery()),
        takeUntil(this.dispose$),
        finalize(() => this.loadingSig.set(false)),
      )
      .subscribe({
        next: (_) => {
          this.serviceShowErrorMessage.showInfo('删除成功。');
          this.closeDrawer();
        },
        error: (error: unknown) => {
          this.serviceShowErrorMessage.showError(error);
        },
      });
  }

  protected requestChangeTitleButtons(portal: TemplatePortal | null | undefined) {
    this.titleButtonsOutlet.detach();
    if (portal) this.titleButtonsOutlet.attach(portal);
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
    if (this.taskType === 'induction') {
      this.switchPortalContent({ titleButtonsPortal: this._titleButtonsPortalAdd });
    }
  }

  protected onDrawerOpened() {}
}
