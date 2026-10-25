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

import { Component, effect, inject, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { DataNode, ServiceStatWorkload } from '../service';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
  selector: 'table-stat-workload',
  templateUrl: 'table.html',
  styleUrls: ['table.scss'],
  standalone: true,
  imports: [MatTableModule, MatIconModule, MatButtonModule, MatTooltip],
})
export class CompTable implements OnDestroy {
  protected service = inject(ServiceStatWorkload);

  protected trackById(_i: number, node: DataNode): string {
    return node.id;
  }

  protected detailsDataSource = new MatTableDataSource<DataNode>([]);
  private readonly effectBind = effect(() => {
    this.detailsDataSource.data = this.service.tableDataSig();
  });

  ngOnDestroy(): void {
    this.effectBind.destroy();
  }

  // 列定义-流水明细
  columns: string[] = [
    'userName',
    'variety',
    'varietyCode',
    'generation',

    'bottleBegin',
    'bottleIn',
    'bottleOut',
    'bottleAdj',
    'bottleCurrent',
    'bottleCurrentPercent',
    'bottleEnd',
    'bottleEndPercent',
    'bottleBeginDiscard',
    'bottleDiscard',
    'bottleDiscardPercent',
    'bottleEndDiscard',
    'bottleEndDiscardPercent',

    'plantBegin',
    'plantIn',
    'plantOut',
    'plantAdj',
    'plantCurrent',
    'plantCurrentPercent',
    'plantEnd',
    'plantEndPercent',
    'plantBeginDiscard',
    'plantDiscard',
    'plantDiscardPercent',
    'plantEndDiscard',
    'plantEndDiscardPercent',
  ];
  headerGroupColumns: string[] = [
    'group-userName',
    'group-variety',
    'group-varietyCode',
    'group-generation',
    'group-bottle',
    'group-plant',
  ];
  headerColumns: string[] = [
    'bottleBegin',
    'bottleIn',
    'bottleOut',
    'bottleAdj',
    'bottleCurrent',
    'bottleCurrentPercent',
    'bottleEnd',
    'bottleEndPercent',
    'bottleBeginDiscard',
    'bottleDiscard',
    'bottleDiscardPercent',
    'bottleEndDiscard',
    'bottleEndDiscardPercent',
    'plantBegin',
    'plantIn',
    'plantOut',
    'plantAdj',
    'plantCurrent',
    'plantCurrentPercent',
    'plantEnd',
    'plantEndPercent',
    'plantBeginDiscard',
    'plantDiscard',
    'plantDiscardPercent',
    'plantEndDiscard',
    'plantEndDiscardPercent',
  ];
  sumColumns: string[] = [
    'sum-userName',
    'bottleBegin',
    'bottleIn',
    'bottleOut',
    'bottleAdj',
    'bottleCurrent',
    'bottleCurrentPercent',
    'bottleEnd',
    'bottleEndPercent',
    'bottleBeginDiscard',
    'bottleDiscard',
    'bottleDiscardPercent',
    'bottleEndDiscard',
    'bottleEndDiscardPercent',
    'plantBegin',
    'plantIn',
    'plantOut',
    'plantAdj',
    'plantCurrent',
    'plantCurrentPercent',
    'plantEnd',
    'plantEndPercent',
    'plantBeginDiscard',
    'plantDiscard',
    'plantDiscardPercent',
    'plantEndDiscard',
    'plantEndDiscardPercent',
  ];
}
