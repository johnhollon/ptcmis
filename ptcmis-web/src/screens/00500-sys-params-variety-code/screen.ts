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

import {OverlayModule} from '@angular/cdk/overlay';
import {CdkPortalOutlet, PortalModule, TemplatePortal} from '@angular/cdk/portal';
import {AfterViewInit, Component, effect, EffectRef, inject, OnDestroy, OnInit, signal, TemplateRef, ViewChild, ViewContainerRef} from '@angular/core';
import {toObservable} from '@angular/core/rxjs-interop';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatFormField, MatHint, MatInput, MatLabel} from '@angular/material/input';
import {MatContextMenuTrigger, MatMenu, MatMenuContent, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatTooltip} from '@angular/material/tooltip';
import {isEqual} from 'lodash-es';
import {catchError, combineLatest, distinctUntilChanged, finalize, Observable, of, startWith, Subject, switchMap, tap} from 'rxjs';
import {debounceTime, takeUntil} from 'rxjs/operators';
import {LayoutPageFunctionSimpleTitle} from '../../components/00050-layouts/00200-layout-page-function-simple/components/title/component';
import {LayoutPageFunctionSimple} from '../../components/00050-layouts/00200-layout-page-function-simple/layout';
import {LayoutQuerySimple} from '../../components/00050-layouts/00200-layout-page-function-simple/layouts/query-simple/layout';
import {Paginator} from '../../components/02000-mat-overrides/pagination';
import {APIConst} from '../../entities/api/const-api';
import {Pagination, UtilPagination} from '../../entities/api/pagination';
import {QueryResultPaged} from '../../entities/api/QueryResultPaged';
import {VarietyCode, varietyCodeDefault} from '../../entities/models/VarietyCode';
import {ActionVarietyCode} from '../../services/actions/action-variety-code';
import {ServiceShowMessage} from '../../services/ui/service-show-error-message.service';
import {CompEditor} from './010-editor/comp';
import {CompViewer} from './020-viewer/comp';

@Component({
  selector: 'screen-variety-code',
  templateUrl: 'screen.html',
  standalone: true,
  imports: [
    MatButton,
    MatIcon,
    MatFormField,
    MatLabel,
    MatTableModule,
    OverlayModule,
    PortalModule,
    MatInput,
    MatIconButton,
    MatTooltip,
    ReactiveFormsModule,
    LayoutPageFunctionSimple,
    LayoutPageFunctionSimpleTitle,
    LayoutQuerySimple,
    CompEditor,
    Paginator,
    MatHint,
    CompViewer,
    MatMenu,
    MatMenuItem,
    MatMenuTrigger,
    MatMenuContent,
    MatContextMenuTrigger
  ],
})
export class ScreenVarietyCode implements OnInit, OnDestroy, AfterViewInit {
  private readonly dispose$ = new Subject<void>();
  private effects: EffectRef[] = [];

  // *************************************************
  // Loading
  // *************************************************
  protected readonly loadingSig = signal<boolean>(false);
  protected readonly loadingMessageSig = signal<string>('');

  // *************************************************
  // 页面引用
  // *************************************************
  @ViewChild('pageRef', {read: LayoutPageFunctionSimple, static: true})
  private pageRef!: LayoutPageFunctionSimple;

  // *************************************************
  // 表格、分页、筛选区域
  // *************************************************
  protected readonly listSig = signal<VarietyCode[]>([]);
  protected readonly tableDataSource = new MatTableDataSource<VarietyCode>([]);
  protected readonly paginationSig = signal<Pagination>(UtilPagination.generatePageModel());
  protected readonly page$: Observable<Pagination>;
  protected readonly filtersFormGroup: FormGroup;
  protected readonly filter$: Observable<any>;
  protected readonly TABLE_COLUMNS = ['variety', 'code', 'important', 'enabled', 'action'];
  protected readonly trackById = (_: number, row: VarietyCode) => row.code;
  protected readonly oppMenuSelectedModelSig = signal<VarietyCode | undefined | null>(undefined);

  // *************************************************
  // 编辑、查看等操作区
  // *************************************************
  protected selectedModelSig = signal<VarietyCode>(varietyCodeDefault);
  private _viewContainerRef = inject(ViewContainerRef);
  @ViewChild('titleDescriptionOutlet', {read: CdkPortalOutlet, static: true}) private titleDescriptionOutlet!: CdkPortalOutlet;
  @ViewChild('titleButtonsOutlet', {read: CdkPortalOutlet, static: true}) private titleButtonsOutlet!: CdkPortalOutlet;
  @ViewChild('drawerOutlet', {read: CdkPortalOutlet, static: true}) private drawerOutlet!: CdkPortalOutlet;
  @ViewChild('titleDescriptionPortalView', {read: TemplateRef, static: true}) private titleDescriptionPortalView!: TemplateRef<void>;
  private _titleDescriptionPortalView!: TemplatePortal<void>;
  @ViewChild('titleDescriptionPortalEdit', {read: TemplateRef, static: true}) private titleDescriptionPortalEdit!: TemplateRef<void>;
  private _titleDescriptionPortalEdit!: TemplatePortal<void>;
  @ViewChild('titleButtonsPortalAdd', {read: TemplateRef, static: true}) private titleButtonsPortalAdd!: TemplateRef<void>;
  private _titleButtonsPortalAdd!: TemplatePortal<void>;
  @ViewChild('drawerPortalView', {read: TemplateRef, static: true}) private drawerPortalView!: TemplateRef<void>;
  private _drawerPortalView!: TemplatePortal<void>;
  @ViewChild('drawerPortalEdit', {read: TemplateRef, static: true}) private drawerPortalEdit!: TemplateRef<void>;
  private _drawerPortalEdit!: TemplatePortal<void>;

  // *************************************************
  // 自定义服务区域
  // *************************************************
  private readonly fb = inject(FormBuilder);
  private readonly serviceShowErrorMessage = inject(ServiceShowMessage);
  private readonly actionVarietyCode = inject(ActionVarietyCode);

  constructor() {
    // 创建表单
    this.filtersFormGroup = this.fb.group({
      keyword: [''],
    });
    // 绑定 listSig 和 数据源。
    this.effects.push(effect(() => {
      this.tableDataSource.data = this.listSig();
    }));
    // 搜索框防抖
    this.filter$ = this.filtersFormGroup.valueChanges.pipe(
      startWith(this.filtersFormGroup.value),
      distinctUntilChanged((old, current) => isEqual(old, current)),
      debounceTime(500)
    );
    // 分页器
    this.page$ = toObservable(this.paginationSig).pipe(
      distinctUntilChanged((old, current) => isEqual(old, current)),
    );
  }

  ngOnInit(): void {
    // 搜索框防抖和分页器触发查询。
    combineLatest([this.filter$, this.page$]).pipe(
      switchMap(() => {
        this.loadingMessageSig.set('正在查询...');
        this.loadingSig.set(true);
        return this.doQuery().pipe(
          finalize(() => {
            this.loadingSig.set(false);
          }),
          catchError(err => {
            this.serviceShowErrorMessage.showError(err);
            return of<QueryResultPaged<VarietyCode>>({list: [], page: this.paginationSig()});
          })
        );
      }),
      takeUntil(this.dispose$),
    ).subscribe();
  }

  ngAfterViewInit(): void {
    // 初始化 portal
    this._titleDescriptionPortalView = new TemplatePortal(this.titleDescriptionPortalView, this._viewContainerRef);
    this._titleDescriptionPortalEdit = new TemplatePortal(this.titleDescriptionPortalEdit, this._viewContainerRef);
    this._titleButtonsPortalAdd = new TemplatePortal(this.titleButtonsPortalAdd, this._viewContainerRef);
    this._drawerPortalEdit = new TemplatePortal(this.drawerPortalEdit, this._viewContainerRef);
    this._drawerPortalView = new TemplatePortal(this.drawerPortalView, this._viewContainerRef);
    // 设置默认 portalOutlet
    this.titleButtonsOutlet.attach(this._titleButtonsPortalAdd);
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
    this.effects.forEach(effect => effect.destroy());
  }

  protected onPageChanged(pageModel: Pagination) {
    this.paginationSig.set(pageModel);
  }

  protected requestView(model: VarietyCode) {
    this.selectedModelSig.set(model);
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalView,
      drawerPortal: this._drawerPortalView,
    });
    this.openDrawer();
  }

  protected requestAdd() {
    this.selectedModelSig.set({...varietyCodeDefault});
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalEdit,
      drawerPortal: this._drawerPortalEdit
    });
    this.openDrawer();
  }

  protected requestEdit(model: VarietyCode) {
    this.selectedModelSig.set(model);
    this.switchPortalContent({
      titleDescriptionPortal: this._titleDescriptionPortalEdit,
      drawerPortal: this._drawerPortalEdit
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

  private doQuery(): Observable<QueryResultPaged<VarietyCode>> {
    return this.actionVarietyCode.queryByCondition({
      page: this.paginationSig(),
      condition: this.filtersFormGroup.value['keyword']
    }).pipe(
      tap(queryResult => {
        this.listSig.set(queryResult.list);
        this.paginationSig.set(UtilPagination.standardPageModel(queryResult.page));
      }),
    );
  }

  protected doSave(model: VarietyCode) {
    this.loadingMessageSig.set('正在处理...');
    this.loadingSig.set(true);
    this.actionVarietyCode.save(model).pipe(
      switchMap(() => this.doQuery()),
      takeUntil(this.dispose$),
      finalize(() => this.loadingSig.set(false)),
    ).subscribe({
      next: (_) => {
        this.serviceShowErrorMessage.showInfo('操作成功。');
        this.closeDrawer();
      },
      error: (error: unknown) => {
        this.serviceShowErrorMessage.showError(error);
      },
    });
  }

  private switchPortalContent(portals?: {
    titleButtonsPortal?: TemplatePortal | undefined | null,
    titleDescriptionPortal?: TemplatePortal | undefined | null,
    drawerPortal?: TemplatePortal | undefined | null
  }): void {
    this.titleButtonsOutlet.detach();
    if (portals?.titleButtonsPortal) this.titleButtonsOutlet.attach(portals.titleButtonsPortal);
    this.titleDescriptionOutlet.detach();
    if (portals?.titleDescriptionPortal) this.titleDescriptionOutlet.attach(portals.titleDescriptionPortal);
    this.drawerOutlet.detach();
    if (portals?.drawerPortal) this.drawerOutlet.attach(portals.drawerPortal);
  }

  private openDrawer(): void {
    this.pageRef.openDrawer().catch(err => this.serviceShowErrorMessage.showError(err));
  }

  protected closeDrawer() {
    this.pageRef.closeDrawer().catch(err => this.serviceShowErrorMessage.showError(err));
  }

  protected onDrawerClosed() {
    this.switchPortalContent({titleButtonsPortal: this._titleButtonsPortalAdd});
  }

  protected onDrawerOpened() {
  }

  protected onOppMenuOpened(item: VarietyCode) {
    this.oppMenuSelectedModelSig.set(item);
  }

  protected onOppMenuClosed() {
    this.oppMenuSelectedModelSig.set(undefined);
  }

  protected readonly APIConst = APIConst;
}
