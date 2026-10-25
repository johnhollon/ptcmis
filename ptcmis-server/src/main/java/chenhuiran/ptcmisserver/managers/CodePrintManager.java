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

import chenhuiran.ptcmisserver.repository.CodePrintDAO;
import chenhuiran.ptcmisserver.repository.CodePrintDetailDAO;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.ModelCodePrint;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryConditionCodePrint;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import com.chenhuiran.commons.lang.Id;
import com.chenhuiran.commons.lang.exceptions.ErrorMessageException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodePrintManager {

  @Autowired
  private CodePrintDAO codePrintDAO;
  @Autowired
  private CodePrintDetailDAO codePrintDetailDAO;
  @PersistenceContext
  private EntityManager entityManager;

  public QueryResultPaged<? extends CodePrint> queryByCondition(QueryCondition<QueryConditionCodePrint> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();
    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.DESC, CodePrint.BP_NAME_OPERATETIME)
    );

    Specification<CodePrintPO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (conditionModel != null) {
        String operatorName = conditionModel.getOperatorName();
        if (operatorName != null && !operatorName.isBlank()) {
          predicateList.add(cb.like(root.get(CodePrint.BP_NAME_OPERATORNAME), "%" + operatorName.trim() + "%"));
        }
        String description = conditionModel.getDescription();
        if (description != null && !description.isBlank()) {
          predicateList.add(cb.like(root.get(CodePrint.BP_NAME_DESCRIPTION), "%" + description.trim() + "%"));
        }
        String tableName = conditionModel.getTableName();
        if (tableName != null && !tableName.isBlank()) {
          predicateList.add(cb.equal(root.get(CodePrint.BP_NAME_TABLENAME), tableName));
        }
        var operateTime = conditionModel.getOperateTime();
        if (operateTime != null) {
          var operateTimeStart = operateTime.getStart();
          if (operateTimeStart != null && operateTimeStart > 0) {
            predicateList.add(cb.greaterThanOrEqualTo(root.get(CodePrint.BP_NAME_OPERATETIME), operateTimeStart));
          }
          var operateTimeEnd = operateTime.getEnd();
          if (operateTimeEnd != null && operateTimeEnd > 0) {
            predicateList.add(cb.lessThanOrEqualTo(root.get(CodePrint.BP_NAME_OPERATETIME), operateTimeEnd));
          }
        }
      }
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    Page<CodePrintDTO> dtoPage = codePrintDAO.findAll(spec, pageable).map(CodePrintDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());
    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }

  public List<? extends CodePrintDetail> queryAllDetails(String idPrint) {
    return codePrintDetailDAO.findByIdPrintOrderByCodeAsc(idPrint).stream().map(CodePrintDetailDTO::new).toList();
  }

  @Transactional
  public void save(ModelCodePrint model, User currentUser) {
    Assert.notNull(model, "待打印信息不能为空");
    Assert.notNull(model.getCodePrint(), "待打印信息不能为空");
    Assert.hasText(model.getCodePrint().getTableName(), "打印类型不能为空");
    Assert.hasText(model.getCodePrint().getRecordId(), "打印关联信息不能为空");
    Assert.hasText(model.getCodePrint().getDescription(), "打印说明信息不能为空");
    Assert.notEmpty(model.getCodePrintDetails(), "没有需要打印的瓶码");
    var codePrint = model.getCodePrint();
    codePrint.setId(Id.g());
    codePrint.setOperateTime(System.currentTimeMillis());
    codePrint.setOperatorId(currentUser.getId());
    codePrint.setOperatorName(currentUser.getName());

    var codePrintPO = new CodePrintPO(codePrint);
    codePrintDAO.save(codePrintPO);

    var details = model.getCodePrintDetails();
    var detailPOList = new ArrayList<CodePrintDetailPO>();
    for (var detail : details) {
      detail.setIdPrint(codePrint.getId());
      var code = detail.getCode();
      Assert.hasText(code, "瓶码不能为空");
      Assert.hasText(detail.getVarietyCode(), "瓶码 " + code + " 的品种编码不能为空");
      Assert.notNull(detail.getExplantCollectionBatch(), "瓶码 " + code + " 的外植体采集批次不能为空");
      if (detail.getExplantCollectionBatch() < 1)
        throw new ErrorMessageException("瓶码 " + code + " 的外植体采集批次不能小于 1");
      Assert.notNull(detail.getGeneration(), "瓶码 " + code + " 的代数不能为空");
      if (detail.getGeneration() < 0)
        throw new ErrorMessageException("瓶码 " + code + " 的代数不能小于 0");
      Assert.hasText(detail.getOperatorName(), "瓶码 " + code + " 的操作人姓名不能为空");
      Assert.notNull(detail.getOperateTime(), "瓶码 " + code + " 的建瓶时间不能为空");
      detailPOList.add(new CodePrintDetailPO(detail));
    }

    final int batchSize = 500;
    for (int i = 0; i < detailPOList.size(); i++) {
      entityManager.persist(detailPOList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }
}
