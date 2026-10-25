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
 *
 */

package chenhuiran.ptcmisserver.stubs.models;

public class ModelBottleSimpleImp implements ModelBottleSimple {
  private String id;
  private Integer plantCount;
  private String discarded;
  private String discardReason;
  private Long taskTime;
  private String lastOperatorId;
  private String lastOperatorName;
  private String variety;
  private String varietyCode;
  private Integer explantCollectionBatch;
  private Integer generation;

  public ModelBottleSimpleImp() {
  }

  public ModelBottleSimpleImp(
    String id, Integer plantCount, String discarded,
    String discardReason, Long taskTime, String lastOperatorId,
    String lastOperatorName, String variety, String varietyCode,
    Integer explantCollectionBatch, Integer generation
  ) {
    this.id = id;
    this.plantCount = plantCount;
    this.discarded = discarded;
    this.discardReason = discardReason;
    this.taskTime = taskTime;
    this.lastOperatorId = lastOperatorId;
    this.lastOperatorName = lastOperatorName;
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.explantCollectionBatch = explantCollectionBatch;
    this.generation = generation;
  }

  @Override
  public String getDiscarded() {
    return discarded;
  }

  public void setDiscarded(String discarded) {
    this.discarded = discarded;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public Integer getPlantCount() {
    return plantCount;
  }

  public void setPlantCount(Integer plantCount) {
    this.plantCount = plantCount;
  }

  @Override
  public String getDiscardReason() {
    return discardReason;
  }

  public void setDiscardReason(String discardReason) {
    this.discardReason = discardReason;
  }

  @Override
  public Long getTaskTime() {
    return taskTime;
  }

  public void setTaskTime(Long taskTime) {
    this.taskTime = taskTime;
  }

  @Override
  public String getLastOperatorId() {
    return lastOperatorId;
  }

  public void setLastOperatorId(String lastOperatorId) {
    this.lastOperatorId = lastOperatorId;
  }

  @Override
  public String getLastOperatorName() {
    return lastOperatorName;
  }

  public void setLastOperatorName(String lastOperatorName) {
    this.lastOperatorName = lastOperatorName;
  }

  @Override
  public String getVariety() {
    return variety;
  }

  public void setVariety(String variety) {
    this.variety = variety;
  }

  @Override
  public String getVarietyCode() {
    return varietyCode;
  }

  public void setVarietyCode(String varietyCode) {
    this.varietyCode = varietyCode;
  }

  @Override
  public Integer getExplantCollectionBatch() {
    return explantCollectionBatch;
  }

  public void setExplantCollectionBatch(Integer explantCollectionBatch) {
    this.explantCollectionBatch = explantCollectionBatch;
  }

  @Override
  public Integer getGeneration() {
    return generation;
  }

  public void setGeneration(Integer generation) {
    this.generation = generation;
  }
}
