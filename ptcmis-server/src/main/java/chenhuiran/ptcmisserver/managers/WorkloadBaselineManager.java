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

import chenhuiran.ptcmisserver.repository.WorkloadTransactionDAO;
import chenhuiran.ptcmisserver.stubs.entities.Const;
import chenhuiran.ptcmisserver.stubs.entities.WorkloadTransaction;
import chenhuiran.ptcmisserver.stubs.entities.WorkloadTransactionDTO;
import chenhuiran.ptcmisserver.stubs.entities.WorkloadTransactionPO;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryConditionWorkloadBaseline;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import chenhuiran.ptcmisserver.utils.ExplantCollectionBatchUtil;
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
public class WorkloadBaselineManager {
  private static final long baselineOperateTime = -377_423_280_000_000L;
  @Autowired
  private WorkloadTransactionDAO workloadTransactionDAO;

  public QueryResultPaged<? extends WorkloadTransaction> queryByCondition(QueryCondition<QueryConditionWorkloadBaseline> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();
    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.ASC, WorkloadTransaction.BP_NAME_VARIETY, WorkloadTransaction.BP_NAME_VARIETYCODE, WorkloadTransaction.BP_NAME_GENERATION)
    );

    Specification<WorkloadTransactionPO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();

      if (conditionModel != null) {
        String keyword = conditionModel.getKeyword();
        keyword = keyword == null ? StringUtil.EMPTY : keyword.trim();
        if (!keyword.isBlank()) {
          String likeVal = "%" + conditionModel.getKeyword().trim() + "%";
          Predicate p1 = cb.like(root.get(WorkloadTransactionPO.BP_NAME_VARIETY), likeVal);
          Predicate p2 = cb.like(root.get(WorkloadTransactionPO.BP_NAME_VARIETYCODE), likeVal);
          Predicate p3 = cb.like(root.get(WorkloadTransactionPO.BP_NAME_USERNAME), likeVal);
          predicateList.add(cb.or(p1, p2, p3));
        }
        if (conditionModel.getRole() != null && !conditionModel.getRole().isBlank()) {
          predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_USERROLE), conditionModel.getRole()));
        }
        if (conditionModel.getBizType() != null && !conditionModel.getBizType().isBlank()) {
          predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_BIZTYPE), conditionModel.getBizType()));
        }
        if (conditionModel.getGeneration() != null && conditionModel.getGeneration() >= 0) {
          predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_GENERATION), conditionModel.getGeneration()));
        }
        predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_OPERATETIME), baselineOperateTime));
      }

      return cb.and(predicateList.toArray(new Predicate[0]));
    };

    // 执行分页查询并使用 Page.map 转换为 DTO
    Page<WorkloadTransactionDTO> dtoPage = workloadTransactionDAO.findAll(spec, pageable).map(WorkloadTransactionDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());

    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }

  @Transactional
  public void save(WorkloadTransactionDTO storageWorkloadBaseline) {
    Assert.notNull(storageWorkloadBaseline, "信息不能为空");
    Assert.hasText(storageWorkloadBaseline.getUserId(), "员工不能为空");
    Assert.hasText(storageWorkloadBaseline.getUserName(), "员工不能为空");
    Assert.hasText(storageWorkloadBaseline.getUserRole(), "员工身份不能为空");
    Assert.hasText(storageWorkloadBaseline.getBizType(), "绩效种类不能为空");
    Assert.hasText(storageWorkloadBaseline.getVariety(), "品种名称不能为空");
    Assert.hasText(storageWorkloadBaseline.getVarietyCode(), "品种编号（子品种）不能为空");
    Assert.notNull(storageWorkloadBaseline.getGeneration(), "代数不能为空");
    Assert.notNull(storageWorkloadBaseline.getBottles(), "瓶数不能为空");
    Assert.notNull(storageWorkloadBaseline.getPlants(), "株数不能为空");
    if (storageWorkloadBaseline.getGeneration() < 0) {
      throw new ErrorMessageException("代数不能小于零");
    }
    String bizType = storageWorkloadBaseline.getBizType();
    if (!bizType.equals(Const.WORKLOAD_TX_BIZ_TYPE_DISCARD) && !bizType.equals(Const.WORKLOAD_TX_BIZ_TYPE_CHILD) && !bizType.equals(Const.WORKLOAD_TX_BIZ_TYPE_PARENT_KEEP)) {
      throw new ErrorMessageException("绩效种类只能是子瓶生产、母瓶留种或淘汰");
    }
    storageWorkloadBaseline.setTableName(StringUtil.EMPTY);
    storageWorkloadBaseline.setRecordId(StringUtil.EMPTY);
    storageWorkloadBaseline.setVariety(storageWorkloadBaseline.getVariety().toUpperCase());
    storageWorkloadBaseline.setVarietyCode(storageWorkloadBaseline.getVarietyCode().toUpperCase());
    storageWorkloadBaseline.setDescription(StringUtil.EMPTY);
    storageWorkloadBaseline.setOperatorId(StringUtil.EMPTY);
    storageWorkloadBaseline.setOperatorName(StringUtil.EMPTY);
    storageWorkloadBaseline.setOperateTime(baselineOperateTime);
    storageWorkloadBaseline.setId(Id.g());

    PredicateSpecification<WorkloadTransactionPO> spec = (root, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();

      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_USERID), storageWorkloadBaseline.getUserId()));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_USERROLE), storageWorkloadBaseline.getUserRole()));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_BIZTYPE), storageWorkloadBaseline.getBizType()));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_TABLENAME), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_RECORDID), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_VARIETY), storageWorkloadBaseline.getVariety()));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_VARIETYCODE), storageWorkloadBaseline.getVarietyCode()));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_GENERATION), storageWorkloadBaseline.getGeneration()));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_DESCRIPTION), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_OPERATORID), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_OPERATORNAME), StringUtil.EMPTY));
      predicateList.add(cb.equal(root.get(WorkloadTransactionPO.BP_NAME_OPERATETIME), baselineOperateTime));

      return cb.and(predicateList.toArray(new Predicate[0]));
    };

    workloadTransactionDAO.delete(spec);
    workloadTransactionDAO.save(new WorkloadTransactionPO(storageWorkloadBaseline));
  }
}
