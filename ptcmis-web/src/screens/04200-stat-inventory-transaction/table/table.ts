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
import { PortalModule } from '@angular/cdk/portal';
import { Component, computed, inject } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatTooltip } from '@angular/material/tooltip';
import { PipeTimestamp } from '../../../components/00200-pipes/pipe-timestamp';
import { InventoryTransaction } from '../../../entities/models/InventoryTransaction';
import { ServiceStatInventoryTransaction } from '../service';
import { PipeInventoryTransactionBizType } from '../../../components/00200-pipes/pipe-inventory-transaction-biz-type';

@Component({
  selector: 'table-stat-inventory-transaction',
  templateUrl: 'table.html',
  standalone: true,
  imports: [
    MatTooltip,
    MatTableModule,
    OverlayModule,
    PortalModule,
    ReactiveFormsModule,
    PipeTimestamp,
    PipeInventoryTransactionBizType,
  ],
  styleUrl: 'table.scss',
})
export class CompTable {
  protected readonly headColumnsSig = computed(() => {
    if (this.service.updateVersionSig() < 0) return [] as string[];
    return this.service.columns.map((column) => `head-${column}`);
  });
  protected readonly startDataColumnsSig = computed(() => {
    if (this.service.updateVersionSig() < 0) return [] as string[];
    return this.service.columns.map((column) => `startData-${column}`);
  });
  protected readonly endDataColumnsSig = computed(() => {
    if (this.service.updateVersionSig() < 0) return [] as string[];
    return this.service.columns.map((column) => `endData-${column}`);
  });

  protected trackById = (_index: number, row: InventoryTransaction) => row.id;

  protected service = inject(ServiceStatInventoryTransaction);
}
