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

package chenhuiran.ptcmisserver.managers;

import chenhuiran.ptcmisserver.repository.StatDAO;
import chenhuiran.ptcmisserver.stubs.entities.InventoryTransaction;
import chenhuiran.ptcmisserver.stubs.entities.InventoryTransactionDTO;
import chenhuiran.ptcmisserver.stubs.entities.WorkloadTransaction;
import chenhuiran.ptcmisserver.stubs.entities.WorkloadTransactionDTO;
import chenhuiran.ptcmisserver.stubs.models.*;
import com.chenhuiran.commons.lang.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;

@Service
public class StatManager {

  @Autowired
  private StatDAO statDAO;

  public ModeStatInventorySimple statInventorySimple(QueryConditionStatInventory condition) {
    var endTime = condition.getDateRange() == null ? Long.MAX_VALUE : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? Long.MAX_VALUE : condition.getDateRange().getEnd();
    var list = statDAO.statInventorySimple(
      endTime,
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getVarietyCodeImportant(),
      condition.getGeneration()
    );
    var maxGeneration = 0;
    var sum = new ModeStatInventorySimpleRecord(
      "合计",
      StringUtil.EMPTY,
      new HashMap<>(),
      0L,
      0L
    );
    var varietyCodeMap = new HashMap<String, ModeStatInventorySimpleRecord>();
    for (var model : list) {
      var varietyCode = model.getVarietyCode();
      if (varietyCode != null && !varietyCode.isBlank()) {
        var record = varietyCodeMap.computeIfAbsent(varietyCode, k -> new ModeStatInventorySimpleRecord(
          model.getVariety(),
          varietyCode,
          new HashMap<>(),
          0L,
          0L
        ));
        var generation = model.getGeneration();
        if (generation != null && generation > -1) {
          record.getGenerationMap().put(generation, new ModeStatInventorySimpleRecordGenerationItem(model.getBottleEnd(), model.getPlantEnd()));
          record.setBottleSum(record.getBottleSum() + model.getBottleEnd());
          record.setPlantSum(record.getPlantSum() + model.getPlantEnd());
          maxGeneration = Math.max(maxGeneration, generation);
          var sumModel = sum.getGenerationMap().computeIfAbsent(generation, k -> new ModeStatInventorySimpleRecordGenerationItem(0L, 0L));
          sumModel.setBottle(sumModel.getBottle() + model.getBottleEnd());
          sumModel.setPlant(sumModel.getPlant() + model.getPlantEnd());
          sum.setBottleSum(sum.getBottleSum() + model.getBottleEnd());
          sum.setPlantSum(sum.getPlantSum() + model.getPlantEnd());
        }
      }
    }

    maxGeneration = Math.max(maxGeneration, 5);

    var collator = Collator.getInstance(Locale.CHINA);
    var result = new ModeStatInventorySimple(maxGeneration,
      new ArrayList<>(
        varietyCodeMap.values().stream().sorted(
          Comparator.comparing(ModeStatInventorySimpleRecord::getVariety, collator)
            .thenComparing(ModeStatInventorySimpleRecord::getVarietyCode, collator)
        ).toList()
      )
    );
    result.getData().add(sum);

    return result;
  }

  public List<ModeStatInventory> statInventory(QueryConditionStatInventory condition) {
    var startTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
    var endTime = condition.getDateRange() == null ? Long.MAX_VALUE : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? Long.MAX_VALUE : condition.getDateRange().getEnd();
    return statDAO.statInventory(
      startTime,
      endTime,
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getVarietyCodeImportant(),
      condition.getGeneration()
    );
  }

  public ModelStatInventoryTransaction statInventoryTransaction(QueryConditionStatInventory condition) {
    var startTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
    var endTime = condition.getDateRange() == null ? Long.MAX_VALUE : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? Long.MAX_VALUE : condition.getDateRange().getEnd();
    // 查询上期结转
    var startData = statDAO.statInventoryTransactionSum(
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getVarietyCodeImportant(),
      condition.getGeneration(),
      startTime,
      "lt"
    );
    // 查询期末余额
    var endData = statDAO.statInventoryTransactionSum(
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getVarietyCodeImportant(),
      condition.getGeneration(),
      endTime,
      "le"
    );
    // 查询明细
    List<? extends InventoryTransaction> detailData = statDAO.statInventoryTransactionDetail(
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getVarietyCodeImportant(),
      condition.getGeneration(),
      startTime,
      endTime
    );
    // 组织返回值
    var result = new ModelStatInventoryTransaction();
    result.setStartData(startData == null || startData.isEmpty() ? new InventoryTransactionDTO() : startData.getFirst());
    result.setDetails(detailData == null ? new ArrayList<>() : detailData);
    result.setEndData(endData == null || endData.isEmpty() ? new InventoryTransactionDTO() : endData.getFirst());

    return result;
  }

  public List<ModeStatWorkload> statWorkload(QueryConditionStatWorkload condition) {
    var startTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
    var endTime = condition.getDateRange() == null ? Long.MAX_VALUE : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? Long.MAX_VALUE : condition.getDateRange().getEnd();
    return statDAO.statWorkload(
      condition.getUserId(),
      condition.getUserRole(),
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getGeneration(),
      startTime,
      endTime
    );
  }

  public ModelStatWorkloadTransaction statWorkloadTransaction(QueryConditionStatWorkload condition) {
    var startTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
    var endTime = condition.getDateRange() == null ? Long.MAX_VALUE : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? Long.MAX_VALUE : condition.getDateRange().getEnd();
    // 查询上期结转
    var startData = statDAO.statWorkloadTransactionSum(
      condition.getUserId(),
      condition.getUserRole(),
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getGeneration(),
      startTime,
      "lt"
    );
    // 查询期末余额
    var endData = statDAO.statWorkloadTransactionSum(
      condition.getUserId(),
      condition.getUserRole(),
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getGeneration(),
      endTime,
      "le"
    );
    // 查询明细
    List<? extends WorkloadTransaction> detailData = statDAO.statWorkloadTransactionDetail(
      condition.getUserId(),
      condition.getUserRole(),
      condition.getVariety(),
      condition.getVarietyCode(),
      condition.getGeneration(),
      startTime,
      endTime
    );

    // 组织返回值
    var result = new ModelStatWorkloadTransaction();
    result.setStartData(startData == null || startData.isEmpty() ? new WorkloadTransactionDTO() : startData.getFirst());
    result.setDetails(detailData == null ? new ArrayList<>() : detailData);
    result.setEndData(endData == null || endData.isEmpty() ? new WorkloadTransactionDTO() : endData.getFirst());

    return result;
  }

}
