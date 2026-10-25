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

import chenhuiran.ptcmisserver.repository.InventoryTransactionDAO;
import chenhuiran.ptcmisserver.stubs.entities.Const;
import chenhuiran.ptcmisserver.stubs.entities.InventoryTransaction;
import chenhuiran.ptcmisserver.stubs.entities.InventoryTransactionDTO;
import chenhuiran.ptcmisserver.stubs.entities.InventoryTransactionPO;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryConditionStorageInventoryBaseline;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import com.chenhuiran.commons.lang.Id;
import com.chenhuiran.commons.lang.StringUtil;
import com.chenhuiran.commons.lang.exceptions.ErrorMessageException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class StorageInventoryBaselineManager {
  private static final long baselineOperateTime = -377_423_280_000_000L;
  @Autowired
  private InventoryTransactionDAO inventoryTransactionDAO;

  public QueryResultPaged<? extends InventoryTransaction> queryByCondition(QueryCondition<QueryConditionStorageInventoryBaseline> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();
    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.ASC, InventoryTransaction.BP_NAME_VARIETY, InventoryTransaction.BP_NAME_VARIETYCODE, InventoryTransaction.BP_NAME_GENERATION)
    );

    Specification<InventoryTransactionPO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();

      if (conditionModel != null) {
        String keyword = conditionModel.getKeyword();
        keyword = keyword == null ? StringUtil.EMPTY : keyword.trim();
        if (!keyword.isBlank()) {
          String likeVal = "%" + conditionModel.getKeyword().trim() + "%";
          Predicate p1 = cb.like(root.get(InventoryTransactionPO.BP_NAME_VARIETY), likeVal);
          Predicate p2 = cb.like(root.get(InventoryTransactionPO.BP_NAME_VARIETYCODE), likeVal);
          predicateList.add(cb.or(p1, p2));
        }
        if (conditionModel.getGeneration() != null && conditionModel.getGeneration() >= 0) {
          predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_GENERATION), conditionModel.getGeneration()));
        }
        predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_OPERATETIME), baselineOperateTime));
        predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_BIZTYPE), Const.INVENTORY_TX_BIZ_TYPE_BASELINE));
      }

      return cb.and(predicateList.toArray(new Predicate[0]));
    };

    // 执行分页查询并使用 Page.map 转换为 DTO
    Page<InventoryTransactionDTO> dtoPage = inventoryTransactionDAO.findAll(spec, pageable).map(InventoryTransactionDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());

    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }

  @Transactional
  public void save(InventoryTransactionDTO storageInventoryBaseline) {
    Assert.notNull(storageInventoryBaseline, "信息不能为空");
    Assert.hasText(storageInventoryBaseline.getVariety(), "品种名称不能为空");
    Assert.hasText(storageInventoryBaseline.getVarietyCode(), "品种编号（子品种）不能为空");
    Assert.notNull(storageInventoryBaseline.getGeneration(), "代数不能为空");
    Assert.notNull(storageInventoryBaseline.getBottles(), "瓶数不能为空");
    Assert.notNull(storageInventoryBaseline.getPlants(), "株数不能为空");
    if (storageInventoryBaseline.getGeneration() < 0) {
      throw new ErrorMessageException("代数不能小于零");
    }
    storageInventoryBaseline.setBizType(Const.INVENTORY_TX_BIZ_TYPE_BASELINE);
    storageInventoryBaseline.setTableName(StringUtil.EMPTY);
    storageInventoryBaseline.setRecordId(StringUtil.EMPTY);
    storageInventoryBaseline.setVariety(storageInventoryBaseline.getVariety().toUpperCase());
    storageInventoryBaseline.setVarietyCode(storageInventoryBaseline.getVarietyCode().toUpperCase());
    storageInventoryBaseline.setDescription(StringUtil.EMPTY);
    storageInventoryBaseline.setOperatorId(StringUtil.EMPTY);
    storageInventoryBaseline.setOperatorName(StringUtil.EMPTY);
    storageInventoryBaseline.setOperateTime(baselineOperateTime);
    storageInventoryBaseline.setId(Id.g());

    PredicateSpecification<InventoryTransactionPO> spec = (root, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();

      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_BIZTYPE), Const.INVENTORY_TX_BIZ_TYPE_BASELINE));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_TABLENAME), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_RECORDID), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_VARIETY), storageInventoryBaseline.getVariety()));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_VARIETYCODE), storageInventoryBaseline.getVarietyCode()));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_GENERATION), storageInventoryBaseline.getGeneration()));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_DESCRIPTION), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_OPERATORID), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_OPERATORNAME), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(InventoryTransactionPO.BP_NAME_OPERATETIME), baselineOperateTime));

      return cb.and(predicateList.toArray(new Predicate[0]));
    };

    inventoryTransactionDAO.delete(spec);
    inventoryTransactionDAO.save(new InventoryTransactionPO(storageInventoryBaseline));
  }
}
