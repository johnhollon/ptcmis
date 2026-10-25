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

import chenhuiran.ptcmisserver.repository.InventoryAdjustmentDAO;
import chenhuiran.ptcmisserver.repository.InventoryAdjustmentLogDAO;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryConditionInventoryAdjustment;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import chenhuiran.ptcmisserver.utils.ExplantCollectionBatchUtil;
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
public class InventoryAdjustmentManager {

  @Autowired
  private InventoryAdjustmentDAO inventoryAdjustmentDAO;
  @Autowired
  private InventoryAdjustmentLogDAO inventoryAdjustmentLogDAO;
  @PersistenceContext
  private EntityManager entityManager;

  public QueryResultPaged<? extends InventoryAdjustment> queryByCondition(QueryCondition<QueryConditionInventoryAdjustment> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();
    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.DESC, InventoryAdjustment.BP_NAME_OPERATETIME)
    );

    Specification<InventoryAdjustmentPO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (conditionModel != null) {
        String keyword = conditionModel.getKeyword();
        if (keyword != null && !keyword.isBlank()) {
          String likeVal = "%" + keyword.trim() + "%";
          Predicate p1 = cb.like(root.get(InventoryAdjustment.BP_NAME_VARIETY), likeVal);
          Predicate p2 = cb.like(root.get(InventoryAdjustment.BP_NAME_VARIETYCODE), likeVal);
          Predicate p3 = cb.like(root.get(InventoryAdjustment.BP_NAME_OPERATORNAME), likeVal);
          predicateList.add(cb.or(p1, p2, p3));
        }
        var taskStatus = conditionModel.getStatus();
        if (taskStatus != null && !taskStatus.isBlank()) {
          predicateList.add(cb.equal(root.get(InventoryAdjustment.BP_NAME_STATUS), taskStatus));
        }
        var generation = conditionModel.getGeneration();
        if (generation != null && generation >= 0) {
          predicateList.add(cb.equal(root.get(InventoryAdjustment.BP_NAME_GENERATION), generation));
        }
        var operateTime = conditionModel.getOperateTime();
        if (operateTime != null) {
          var operateTimeStart = operateTime.getStart();
          if (operateTimeStart != null && operateTimeStart > 0) {
            predicateList.add(cb.greaterThanOrEqualTo(root.get(InventoryAdjustment.BP_NAME_OPERATETIME), operateTimeStart));
          }
          var operateTimeEnd = operateTime.getEnd();
          if (operateTimeEnd != null && operateTimeEnd > 0) {
            predicateList.add(cb.lessThanOrEqualTo(root.get(InventoryAdjustment.BP_NAME_OPERATETIME), operateTimeEnd));
          }
        }
      }
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    Page<InventoryAdjustmentDTO> dtoPage = inventoryAdjustmentDAO.findAll(spec, pageable).map(InventoryAdjustmentDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());
    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }

  public List<? extends InventoryAdjustmentLog> queryAllLogs(String idAdjustment) {
    return inventoryAdjustmentLogDAO.findByIdAdjustmentOrderByOperatorTimeDesc(idAdjustment).stream().map(InventoryAdjustmentLogDTO::new).toList();
  }

  @Transactional
  public void save(InventoryAdjustmentDTO model, User currentUser) {
    Assert.notNull(model, "盘点调差信息不能为空");
    Assert.hasText(model.getVariety(), "品种不能为空");
    Assert.hasText(model.getVarietyCode(), "品种编码不能为空");
    Assert.hasText(model.getReason(), "调差原因不能为空");
    Assert.notNull(model.getGeneration(), "代数不能为空");
    Assert.notNull(model.getPlants(), "株数不能为空");
    Assert.notNull(model.getBottles(), "瓶数不能为空");
    if (model.getGeneration() < 0) throw new ErrorMessageException("代数不得小于零");

    // 根据ID查询任务信息。
    var po = inventoryAdjustmentDAO.findById(model.getId()).orElse(null);
    // 没有查到，则是新建任务。
    if (po == null) {
      po = new InventoryAdjustmentPO();
      po.setId(Id.g());
      po.setStatus(Const.INVENTORY_ADJUSTMENT_STATUS_CREATED);
    } else if (po.getStatus().equals(Const.INVENTORY_ADJUSTMENT_STATUS_CREATED)) {
      po.setStatus(Const.INVENTORY_ADJUSTMENT_STATUS_STARTED);
    }

    // 校验任务状态。
    var dbStatus = po.getStatus();
    if (dbStatus.equals(Const.INVENTORY_ADJUSTMENT_STATUS_FINISHED)) {
      throw new ErrorMessageException("不可编辑已完成的盘点调差信息");
    }

    // 保存
    long now = System.currentTimeMillis();
    po.setVariety(model.getVariety());
    po.setVarietyCode(model.getVarietyCode());
    po.setGeneration(model.getGeneration());
    po.setStatus(po.getStatus());
    po.setBottles(model.getBottles());
    po.setPlants(model.getPlants());
    po.setReason(model.getReason());
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperateTime(now);
    if (po.isNew()) inventoryAdjustmentDAO.save(po);

    // 日志
    InventoryAdjustmentLogPO log = new InventoryAdjustmentLogPO();
    log.setId(Id.g());
    log.setIdAdjustment(po.getId());
    log.setStatus(po.getStatus());
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.inventoryAdjustmentLogDAO.save(log);
  }

  @Transactional
  public void delete(String taskId) {
    Assert.hasText(taskId, "盘点调差信息不能为空");
    var po = inventoryAdjustmentDAO.findById(taskId.trim()).orElse(null);
    if (po == null) return;

    // 校验状态
    var dbStatus = po.getStatus();
    if (dbStatus == null
      ||
      (
        !dbStatus.equals(Const.INVENTORY_ADJUSTMENT_STATUS_CREATED)
          && !dbStatus.equals(Const.INVENTORY_ADJUSTMENT_STATUS_STARTED)
      )
    ) throw new ErrorMessageException("不可删除已完成的信息");

    // 删除任务
    inventoryAdjustmentDAO.deleteById(taskId.trim());
    // 删除日志
    inventoryAdjustmentLogDAO.deleteBatchByIdAdjustment(taskId.trim());
  }

  @Transactional
  public void done(InventoryAdjustmentDTO model, User currentUser) {
    Assert.notNull(model, "盘点调差信息不能为空");
    Assert.hasText(model.getVariety(), "品种不能为空");
    Assert.hasText(model.getVarietyCode(), "品种编码不能为空");
    Assert.hasText(model.getReason(), "调差原因不能为空");
    Assert.notNull(model.getGeneration(), "代数不能为空");
    Assert.notNull(model.getPlants(), "株数不能为空");
    Assert.notNull(model.getBottles(), "瓶数不能为空");
    if (model.getGeneration() < 0) throw new ErrorMessageException("代数不得小于零");

    // 根据ID查询任务信息。
    var po = inventoryAdjustmentDAO.findById(model.getId()).orElse(null);
    // 没有查到，则是新建任务。
    if (po == null) {
      throw new ErrorMessageException("无法找到此盘点调差信息");
    }

    // 校验任务状态。
    var dbStatus = po.getStatus();
    if (dbStatus.equals(Const.INVENTORY_ADJUSTMENT_STATUS_FINISHED)) {
      throw new ErrorMessageException("不可对已完成的盘点调差信息执行此操作");
    }
    po.setStatus(Const.INVENTORY_ADJUSTMENT_STATUS_FINISHED);

    // 保存
    long now = System.currentTimeMillis();
    po.setVariety(model.getVariety());
    po.setVarietyCode(model.getVarietyCode());
    po.setGeneration(model.getGeneration());
    po.setStatus(po.getStatus());
    po.setBottles(model.getBottles());
    po.setPlants(model.getPlants());
    po.setReason(model.getReason());
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperateTime(now);
    if (po.isNew()) inventoryAdjustmentDAO.save(po);

    // 日志
    InventoryAdjustmentLogPO log = new InventoryAdjustmentLogPO();
    log.setId(Id.g());
    log.setIdAdjustment(po.getId());
    log.setStatus(po.getStatus());
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.inventoryAdjustmentLogDAO.save(log);

    // 库存流水
    InventoryTransactionPO transactionPO = new InventoryTransactionPO();
    transactionPO.setId(Id.g());
    transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_ADJUSTMENT);
    transactionPO.setTableName(InventoryAdjustment.TB_NAME);
    transactionPO.setRecordId(po.getId());
    transactionPO.setVariety(po.getVariety());
    transactionPO.setVarietyCode(po.getVarietyCode());
    transactionPO.setGeneration(po.getGeneration());
    transactionPO.setBottles(po.getBottles());
    transactionPO.setPlants(po.getPlants());
    transactionPO.setDescription(po.getReason());
    transactionPO.setOperatorId(currentUser.getId());
    transactionPO.setOperatorName(currentUser.getName());
    transactionPO.setOperateTime(now);
    entityManager.persist(transactionPO);
  }
}
