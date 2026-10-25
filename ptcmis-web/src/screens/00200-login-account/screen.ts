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

import {Component, inject, OnDestroy, OnInit, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatButton} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {Router} from '@angular/router';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {DirectiveDeClick} from '../../components/00100-directives/directive-de-click';
import {IJT_DataPassport} from '../../services/datas/passport/data';
import {IJT_RepositoryMessages} from '../../services/utils/00020-messages/repository';

@Component({
  selector: 'screen-login-account',
  standalone: true,
  imports: [ReactiveFormsModule, DirectiveDeClick, MatFormField, MatLabel, MatButton, MatInput, MatIconModule],
  template: `

    <h1>登录组培信息系统</h1>
    <div class="form" [formGroup]="loginForm">
      <!-- 用户名输入框 -->
      <mat-form-field class="full-width">
        <mat-label>
          <mat-icon>person</mat-icon>
          用户名
        </mat-label>
        <input matInput type="text" formControlName="username" placeholder="请输入用户名">
      </mat-form-field>

      <!-- 密码输入框 -->
      <mat-form-field class="full-width">
        <mat-label>
          <mat-icon>key</mat-icon>
          密码
        </mat-label>
        <input matInput type="password" formControlName="password" placeholder="请输入密码">
      </mat-form-field>

      <!-- 底部消息提示容器 -->
      <div class="error">
        {{ loginMessageSig() }}
      </div>
    </div>

    <!-- 登录按钮 -->
    <button matButton="filled" class="login-btn" (de-click)="onAccountLogin()">登录</button>
  `,
  styles: `
    :host {
      display: block;
      position: absolute;
      overflow: hidden;
      width: 328px;
      height: 429px;
      padding: 0 16px;
      text-align: center;
      left: 50%;
      top: 55%;
      transform: translateY(-75%) translateX(-50%);
    }

    .toggle-btn {
      position: absolute;
      right: -2px;
      top: -2px;
      font-size: 65px;
      background-color: var(--mat-sys-surface-container-high);
      color: var(--mat-sys-outline-variant);
      margin: 2px 1px 0 0;
      overflow: hidden;
      cursor: pointer;
      --size: 72px;
      width: var(--size);
      height: var(--size);
      clip-path: polygon(0 0, var(--size) 0, var(--size) var(--size), var(--size) var(--size));
    }

    .form {
      display: flex;
      flex-direction: column;
      gap: 1em;
      padding: 32px;
      margin-top: 32px;
    }

    .login-btn {
      width: 6em;
      margin: 0 auto;
    }

    .error {
      color: var(--mat-sys-error);
    }
  `
})
export class ScreenLoginAccount implements OnInit, OnDestroy {

  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);
  private readonly dataPassport = inject(IJT_DataPassport);
  private readonly serviceMessage = inject(IJT_RepositoryMessages);

  private readonly dispose$ = new Subject<void>();

  // 登录表单
  loginForm: FormGroup = this.fb.group({
    username: [null, [Validators.required]],
    password: [null, [Validators.required]]
  });
  // 登录提示文字
  protected readonly loginMessageSig = signal('');
  // 账号密码登录
  onAccountLogin(): void {
    if (this.loginForm.invalid) {
      this.loginMessageSig.set('请输入用户名和密码');
      return;
    }
    this.dataPassport.login(this.loginForm.value).pipe(
      takeUntil(this.dispose$),
    ).subscribe({
      error: error => this.loginMessageSig.set(this.serviceMessage.getMessages(error)),
      complete: () => {
        if (!this.dataPassport.role || !this.dataPassport.currentToken) {
          this.router.navigate(['/login-account']).catch();
          return;
        }
        this.router.navigate(['/index']).catch();
      },
    });
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.dispose$.next();
    this.dispose$.complete();
  }
}
