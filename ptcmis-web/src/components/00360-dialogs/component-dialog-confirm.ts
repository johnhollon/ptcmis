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

import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonAppearance, MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'dialog-confirm',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule],
  template: `
    @if (data.title) {
      <h2 mat-dialog-title>{{ data.title }}</h2>
    }
    <mat-dialog-content>
      <p>{{ data.message }}</p>
    </mat-dialog-content>
    @if (hasAnyValidButton()) {
      <mat-dialog-actions [align]="data.buttonsAlign ?? 'end'">
        @for (button of data.buttons; track $index) {
          @if (text(button)) {
            <button [matButton]="appearance(button)" (click)="onClick($index)">
              {{ text(button) }}
            </button>
          }
        }
      </mat-dialog-actions>
    }
  `,
})
export class ComponentDialogConfirm {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogConfirmData,
    private dialogRef: MatDialogRef<ComponentDialogConfirm, number | undefined>,
  ) {}

  onClick(index: number): void {
    this.dialogRef.close(index);
  }

  protected text(button: string | { text: string; appearance?: MatButtonAppearance }): string {
    if (typeof button === 'string') return button;
    return button.text ?? '';
  }

  protected appearance(
    button: string | { text: string; appearance?: MatButtonAppearance },
  ): MatButtonAppearance {
    if (typeof button === 'string') return 'text';
    return button.appearance ?? 'text';
  }

  protected hasAnyValidButton(): boolean {
    return this.data.buttons.some((b) => !!this.text(b));
  }
}

export interface DialogConfirmData {
  title?: string;
  message: string;
  buttons: (string | { text: string; appearance?: MatButtonAppearance })[];
  buttonsAlign?: 'start' | 'center' | 'end';
}
