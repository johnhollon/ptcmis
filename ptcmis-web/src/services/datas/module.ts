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

import { EnvironmentProviders, Provider } from '@angular/core';
import { IJT_DataBottleCodeSequence } from './bottle-code-sequence/data';
import { DataBottleCodeSequenceApi } from './bottle-code-sequence/data-api';
import { IJT_DataPassport } from './passport/data';
import { DataPassportApi } from './passport/data-api';
import { IJT_DataPrinter } from './printer/data';
import { DataPrinterApi } from './printer/data-api';
import { IJT_DataStorageInventoryBaseline } from './storage-inventory-baseline/data';
import { DataStorageInventoryBaselineApi } from './storage-inventory-baseline/data-api';
import { IJT_DataTissueTask } from './tissue-task/data';
import { DataTissueTaskApi } from './tissue-task/data-api';
import { IJT_DataUser } from './user/data';
import { DataUserApi } from './user/data-api';
import { IJT_DataVarietyCode } from './variety-code/data';
import { DataVarietyCodeApi } from './variety-code/data-api';
import { IJT_DataVariety } from './variety/data';
import { DataVarietyApi } from './variety/data-api';
import { IJT_DataStorageInOut } from './storage-in-out/data';
import { DataStorageInOutApi } from './storage-in-out/data-api';
import { DataBottleApi } from './bottle/data-api';
import { IJT_DataBottle } from './bottle/data';
import { IJT_DataBottleCodeComplementTask } from './bottle-code-complement-task/data';
import { DataBottleCodeComplementTaskApi } from './bottle-code-complement-task/data-api';
import { IJT_DataArchive } from './archive/data';
import { DataArchiveApi } from './archive/data-api';
import { IJT_DataInventoryAddBottle } from './inventory-add-bottle/data';
import { DataInventoryAddBottleApi } from './inventory-add-bottle/data-api';
import { DataInventoryAdjustmentApi } from './inventory-adjustment/data-api';
import { IJT_DataInventoryAdjustment } from './inventory-adjustment/data';
import { IJT_DataStat } from './stat/data';
import { DataStatApi } from './stat/data-api';
import { IJT_DataWorkloadBaseline } from './workload-baseline/data';
import { DataWorkloadBaselineApi } from './workload-baseline/data-api';

export const provideDataServices: () => (Provider | EnvironmentProviders)[] = () => {
  return [
    { provide: IJT_DataBottleCodeSequence, useClass: DataBottleCodeSequenceApi },
    { provide: IJT_DataPassport, useClass: DataPassportApi },
    { provide: IJT_DataVariety, useClass: DataVarietyApi },
    { provide: IJT_DataVarietyCode, useClass: DataVarietyCodeApi },
    { provide: IJT_DataUser, useClass: DataUserApi },
    { provide: IJT_DataStorageInventoryBaseline, useClass: DataStorageInventoryBaselineApi },
    { provide: IJT_DataTissueTask, useClass: DataTissueTaskApi },
    { provide: IJT_DataPrinter, useClass: DataPrinterApi },
    { provide: IJT_DataStorageInOut, useClass: DataStorageInOutApi },
    { provide: IJT_DataBottle, useClass: DataBottleApi },
    { provide: IJT_DataBottleCodeComplementTask, useClass: DataBottleCodeComplementTaskApi },
    { provide: IJT_DataInventoryAddBottle, useClass: DataInventoryAddBottleApi },
    { provide: IJT_DataInventoryAdjustment, useClass: DataInventoryAdjustmentApi },
    { provide: IJT_DataArchive, useClass: DataArchiveApi },
    { provide: IJT_DataStat, useClass: DataStatApi },
    { provide: IJT_DataWorkloadBaseline, useClass: DataWorkloadBaselineApi },
  ];
};

export const provideDataServicesMock: () => (Provider | EnvironmentProviders)[] = () => {
  return [];
};
