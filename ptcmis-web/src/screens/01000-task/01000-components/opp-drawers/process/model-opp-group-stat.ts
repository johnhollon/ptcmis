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

export interface ModelOppGroupEmployeeStat {
  idUser: string;
  nameUser: string;
  parentBottleCountAssigned: number;
  parentBottleCountFinished: number;
  parentBottleCountProcessing: number;
  parentPlantCountAssigned: number;
  parentPlantCountFinished: number;
  parentPlantCountProcessing: number;
  parentKeepBottleCountAssigned: number;
  parentKeepBottleCountFinished: number;
  parentKeepBottleCountProcessing: number;
  parentKeepPlantCountAssigned: number;
  parentKeepPlantCountFinished: number;
  parentKeepPlantCountProcessing: number;
  childBottleCountAssigned: number;
  childBottleCountFinished: number;
  childBottleCountProcessing: number;
  childBottleCountDiscard: number;
  childPlantCountAssigned: number;
  childPlantCountFinished: number;
  childPlantCountProcessing: number;
  childPlantCountDiscard: number;
}

export const ModelOppGroupEmployeeStatDefault: ModelOppGroupEmployeeStat = {
  idUser: '',
  nameUser: '',
  parentBottleCountAssigned: 0,
  parentBottleCountFinished: 0,
  parentBottleCountProcessing: 0,
  parentPlantCountAssigned: 0,
  parentPlantCountFinished: 0,
  parentPlantCountProcessing: 0,
  parentKeepBottleCountAssigned: 0,
  parentKeepBottleCountFinished: 0,
  parentKeepBottleCountProcessing: 0,
  parentKeepPlantCountAssigned: 0,
  parentKeepPlantCountFinished: 0,
  parentKeepPlantCountProcessing: 0,
  childBottleCountAssigned: 0,
  childBottleCountFinished: 0,
  childBottleCountProcessing: 0,
  childBottleCountDiscard: 0,
  childPlantCountAssigned: 0,
  childPlantCountFinished: 0,
  childPlantCountProcessing: 0,
  childPlantCountDiscard: 0,
};

export interface ModelOppGroupStat {
  idOppGroup: string;
  nameOppGroup: string;
  finishTime: number;
  parentBottleCountPlan: number;
  parentBottleCountAssigned: number;
  parentBottleCountFinished: number;
  parentBottleCountProcessing: number;
  parentPlantCountPlan: number;
  parentPlantCountAssigned: number;
  parentPlantCountFinished: number;
  parentPlantCountProcessing: number;
  parentKeepBottleCountPlan: number;
  parentKeepBottleCountAssigned: number;
  parentKeepBottleCountFinished: number;
  parentKeepBottleCountProcessing: number;
  parentKeepPlantCountPlan: number;
  parentKeepPlantCountAssigned: number;
  parentKeepPlantCountFinished: number;
  parentKeepPlantCountProcessing: number;
  childBottleCountPlan: number;
  childBottleCountAssigned: number;
  childBottleCountFinished: number;
  childBottleCountProcessing: number;
  childBottleCountDiscard: number;
  childPlantCountPlan: number;
  childPlantCountAssigned: number;
  childPlantCountFinished: number;
  childPlantCountProcessing: number;
  childPlantCountDiscard: number;
  inspectorMap: Map<string, ModelOppGroupEmployeeStat>;
  inspectors: { id: string; name: string }[];
  operatorMap: Map<string, ModelOppGroupEmployeeStat>;
  operators: { id: string; name: string }[];
}

export const ModelOppGroupStatDefault: ModelOppGroupStat = {
  idOppGroup: '',
  nameOppGroup: '总览',
  finishTime: 0,
  parentBottleCountPlan: 0,
  parentBottleCountAssigned: 0,
  parentBottleCountFinished: 0,
  parentBottleCountProcessing: 0,
  parentPlantCountPlan: 0,
  parentPlantCountAssigned: 0,
  parentPlantCountFinished: 0,
  parentPlantCountProcessing: 0,
  parentKeepBottleCountPlan: 0,
  parentKeepBottleCountAssigned: 0,
  parentKeepBottleCountFinished: 0,
  parentKeepBottleCountProcessing: 0,
  parentKeepPlantCountPlan: 0,
  parentKeepPlantCountAssigned: 0,
  parentKeepPlantCountFinished: 0,
  parentKeepPlantCountProcessing: 0,
  childBottleCountPlan: 0,
  childBottleCountAssigned: 0,
  childBottleCountFinished: 0,
  childBottleCountProcessing: 0,
  childBottleCountDiscard: 0,
  childPlantCountPlan: 0,
  childPlantCountAssigned: 0,
  childPlantCountFinished: 0,
  childPlantCountProcessing: 0,
  childPlantCountDiscard: 0,
  inspectorMap: new Map<string, ModelOppGroupEmployeeStat>(),
  inspectors: [] as { id: string; name: string }[],
  operatorMap: new Map<string, ModelOppGroupEmployeeStat>(),
  operators: [] as { id: string; name: string }[],
};
