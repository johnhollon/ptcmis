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

import chenhuiran.ptcmisserver.repository.*;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.*;
import com.chenhuiran.commons.lang.Id;
import com.chenhuiran.commons.lang.StringUtil;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BottleManager {
  @Autowired
  private TissueTaskDAO tissueTaskDAO;
  @Autowired
  private BottleCodeComplementTaskDAO bottleCodeComplementTaskDAO;
  @Autowired
  private InventoryAddBottleDAO inventoryAddBottleDAO;
  @Autowired
  private BottleDAO bottleDAO;
  @Autowired
  private BottleLogDAO bottleLogDAO;
  @Autowired
  private ArchiveDAO archiveDAO;
  @Autowired
  private BottleRelationDAO bottleRelationDAO;
  @PersistenceContext
  private EntityManager entityManager;

  public QueryResultPaged<? extends Bottle> queryByCondition(QueryCondition<QueryConditionBottle> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();
    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.ASC, Bottle.BP_NAME_ID)
    );

    Specification<BottlePO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (conditionModel != null) {
        String keyword = conditionModel.getKeyword();
        if (keyword != null && !keyword.isBlank()) {
          String likeVal = "%" + keyword.trim() + "%";
          Predicate p1 = cb.like(root.get(Bottle.BP_NAME_VARIETY), likeVal);
          Predicate p2 = cb.like(root.get(Bottle.BP_NAME_VARIETYCODE), likeVal);
          Predicate p3 = cb.like(root.get(Bottle.BP_NAME_LASTOPERATORNAME), likeVal);
          Predicate p4 = cb.like(root.get(Bottle.BP_NAME_ID), likeVal);
          predicateList.add(cb.or(p1, p2, p3, p4));
        }
        var explantCollectionBatch = conditionModel.getExplantCollectionBatch();
        if (explantCollectionBatch != null && explantCollectionBatch > 0) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_EXPLANTCOLLECTIONBATCH), explantCollectionBatch));
        }
        var generation = conditionModel.getGeneration();
        if (generation != null && generation >= 0) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_GENERATION), generation));
        }
        var discarded = conditionModel.getDiscarded();
        if (discarded != null && !discarded.isBlank()) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_DISCARDED), discarded));
        }
        var innerVariety = conditionModel.getInnerVariety();
        if (innerVariety != null && !innerVariety.isBlank()) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_VARIETY), innerVariety));
        }
        var innerVarietyCode = conditionModel.getInnerVarietyCode();
        if (innerVarietyCode != null && !innerVarietyCode.isBlank()) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_VARIETYCODE), innerVarietyCode));
        }
        var currentStorageStatus = conditionModel.getCurrentStorageStatus();
        if (currentStorageStatus != null && !currentStorageStatus.isBlank()) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_CURRENTSTORAGESTATUS), currentStorageStatus));
        }
        var taskTime = conditionModel.getTaskTime();
        if (taskTime != null) {
          var createTimeStart = taskTime.getStart();
          if (createTimeStart != null && createTimeStart > 0) {
            predicateList.add(cb.greaterThanOrEqualTo(root.get(Bottle.BP_NAME_TASKTIME), createTimeStart));
          }
          var createTimeEnd = taskTime.getEnd();
          if (createTimeEnd != null && createTimeEnd > 0) {
            predicateList.add(cb.lessThanOrEqualTo(root.get(Bottle.BP_NAME_TASKTIME), createTimeEnd));
          }
        }
      }
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    Page<BottleDTO> dtoPage = bottleDAO.findAll(spec, pageable).map(BottleDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());
    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }


  public List<ModelBottleSimple> queryAllByCondition(QueryConditionBottle conditionModel) {
    Specification<BottlePO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (conditionModel != null) {
        String keyword = conditionModel.getKeyword();
        if (keyword != null && !keyword.isBlank()) {
          String likeVal = "%" + keyword.trim() + "%";
          Predicate p1 = cb.like(root.get(Bottle.BP_NAME_VARIETY), likeVal);
          Predicate p2 = cb.like(root.get(Bottle.BP_NAME_VARIETYCODE), likeVal);
          Predicate p3 = cb.like(root.get(Bottle.BP_NAME_LASTOPERATORNAME), likeVal);
          Predicate p4 = cb.like(root.get(Bottle.BP_NAME_ID), likeVal);
          predicateList.add(cb.or(p1, p2, p3, p4));
        }
        var explantCollectionBatch = conditionModel.getExplantCollectionBatch();
        if (explantCollectionBatch != null && explantCollectionBatch > 0) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_EXPLANTCOLLECTIONBATCH), explantCollectionBatch));
        }
        var generation = conditionModel.getGeneration();
        if (generation != null && generation >= 0) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_GENERATION), generation));
        }
        var discarded = conditionModel.getDiscarded();
        if (discarded != null && !discarded.isBlank()) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_DISCARDED), discarded));
        }
        var innerVariety = conditionModel.getInnerVariety();
        if (innerVariety != null && !innerVariety.isBlank()) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_VARIETY), innerVariety));
        }
        var innerVarietyCode = conditionModel.getInnerVarietyCode();
        if (innerVarietyCode != null && !innerVarietyCode.isBlank()) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_VARIETYCODE), innerVarietyCode));
        }
        var currentStorageStatus = conditionModel.getCurrentStorageStatus();
        if (currentStorageStatus != null && !currentStorageStatus.isBlank()) {
          predicateList.add(cb.equal(root.get(Bottle.BP_NAME_CURRENTSTORAGESTATUS), currentStorageStatus));
        }
        var taskTime = conditionModel.getTaskTime();
        if (taskTime != null) {
          var createTimeStart = taskTime.getStart();
          if (createTimeStart != null && createTimeStart > 0) {
            predicateList.add(cb.greaterThanOrEqualTo(root.get(Bottle.BP_NAME_TASKTIME), createTimeStart));
          }
          var createTimeEnd = taskTime.getEnd();
          if (createTimeEnd != null && createTimeEnd > 0) {
            predicateList.add(cb.lessThanOrEqualTo(root.get(Bottle.BP_NAME_TASKTIME), createTimeEnd));
          }
        }
      }
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    return bottleDAO.findBy(spec, q -> q.as(ModelBottleSimple.class).all());
  }

  @Transactional
  public void changeParentBottle(ModelBottleChangeParent model, User currentUser) {
    Assert.notNull(model, "变更信息不能为空");
    Assert.hasText(model.getId(), "未知的培养瓶");
    var parentIdNew = model.getIdParentNew() == null ? StringUtil.EMPTY : model.getIdParentNew();
    if (parentIdNew.equals(model.getId())) {
      throw new ErrorMessageException("母瓶不能是当前培养瓶");
    }
    // 查询子瓶是否存在
    var bottlePO = bottleDAO.findById(model.getId()).orElse(null);
    if (bottlePO == null) throw new ErrorMessageException("未知的培养瓶");

    // 查询新母瓶是否存在
    var parentBottleNew = bottleDAO.findById(parentIdNew).orElse(null);
    if (!parentIdNew.isBlank() && parentBottleNew == null) throw new ErrorMessageException("未知的母代培养瓶");
    if (parentBottleNew != null) {
      if (!parentBottleNew.getVariety().equals(bottlePO.getVariety())) {
        throw new ErrorMessageException("母瓶品种与子瓶不一致");
      }
      if (!parentBottleNew.getVarietyCode().equals(bottlePO.getVarietyCode())) {
        throw new ErrorMessageException("母瓶品种编码与子瓶不一致");
      }
      if (!Objects.equals(parentBottleNew.getExplantCollectionBatch(), bottlePO.getExplantCollectionBatch())) {
        throw new ErrorMessageException("母瓶、子瓶外植体采集批次不一致");
      }
      bottlePO.setIdParent(parentIdNew);
    }

    // 1. 获取被修改瓶的所有后代（包含自己）
    List<String> childIds = bottleRelationDAO.findAllChildIds(model.getId());
    if (childIds.isEmpty()) {
      childIds = new ArrayList<>();
      childIds.add(model.getId());
    }
    if (!parentIdNew.isBlank() && childIds.contains(parentIdNew)) {
      throw new ErrorMessageException("不能选择当前培养瓶的子代、后代作为母瓶");
    }

    // 2. 获取被修改瓶原来全部祖先列表（旧链）
    List<BottleRelationPO> oldParentRelations = bottleRelationDAO.findParentRelations(model.getId());
    List<String> oldParentIds = oldParentRelations.stream()
      .map(BottleRelationPO::getParentId)
      .toList();

    // 3. 删除旧链关系：后代们和旧祖先之间所有记录
    bottleRelationDAO.deleteRelationForUpdateParentBottle(childIds, oldParentIds);

    // 4. 获取新母瓶NewParent的完整祖先链
    List<BottleRelationPO> saveList = new ArrayList<>();
    if (parentBottleNew != null) {
      List<BottleRelationPO> newParentRelations = bottleRelationDAO.findParentRelations(parentIdNew);
      // 转成：Map<祖先ID, 祖先depth>
      var parentMapNew = newParentRelations.stream()
        .collect(Collectors.toMap(
          BottleRelationPO::getParentId,
          BottleRelationPO::getDepth
        ));
      // 5. 批量构建新母链所有祖先，生成关系
      for (String childId : childIds) {
        for (var entry : parentMapNew.entrySet()) {
          String parentId = entry.getKey();
          int depthParent = entry.getValue();
          BottleRelationPO rel = new BottleRelationPO();
          rel.setId(Id.g());
          rel.setChildId(childId);
          rel.setParentId(parentId);
          if (!childId.equals(model.getId())) {
            rel.setDepth(depthParent + 2);
          } else {
            rel.setDepth(depthParent + 1);
          }
          saveList.add(rel);
        }
      }
    }

    // 5.1 被修改瓶的自身记录 depth=0
    BottleRelationPO self = new BottleRelationPO();
    self.setId(Id.g());
    self.setChildId(model.getId());
    self.setParentId(model.getId());
    self.setDepth(0);
    saveList.add(self);
    for (String childId : childIds) {
      if (!childId.equals(model.getId())) {
        BottleRelationPO br = new BottleRelationPO();
        br.setId(Id.g());
        br.setChildId(childId);
        br.setParentId(model.getId());
        br.setDepth(1);
        saveList.add(br);
      }
    }

    // 培养瓶日志
    List<BottleLogPO> bottleLogs = new ArrayList<>();
    var now = System.currentTimeMillis();
    for (String childId : childIds) {
      BottleLogPO logForStorage = new BottleLogPO();
      logForStorage.setId(Id.g());
      logForStorage.setIdBottle(childId);
      logForStorage.setOppTime(now);
      logForStorage.setTableName(Bottle.TB_NAME);
      logForStorage.setRecordId(childId);
      if (model.getId().equals(childId)) {
        logForStorage.setBizType(parentBottleNew == null ? "删除母瓶" : ("更正母瓶为" + parentIdNew));
      } else {
        logForStorage.setBizType(parentBottleNew == null ? "删除祖代母瓶（联动）" : ("联动更正祖代母瓶为" + parentIdNew));
      }
      if (model.getId().equals(childId)) {
        logForStorage.setDescription(StringUtil.EMPTY);
      } else {
        logForStorage.setDescription("因祖代母瓶" + model.getId() + "更正母瓶，联动更正祖代母瓶。");
      }
      logForStorage.setInspectorId(StringUtil.EMPTY);
      logForStorage.setInspectorName(StringUtil.EMPTY);
      logForStorage.setOperatorId(currentUser.getId());
      logForStorage.setOperatorName(currentUser.getName());
      logForStorage.setOperateStatus(StringUtil.EMPTY);
      logForStorage.setStorageStatus(StringUtil.EMPTY);
      logForStorage.setStorageReason(StringUtil.EMPTY);
      logForStorage.setDiscarded(Const.BOOLEAN_0);
      logForStorage.setDiscardReason(StringUtil.EMPTY);
      // 向待处理列表中插入组培瓶操作日志信息
      bottleLogs.add(logForStorage);
    }

    // 批量保存
    final int batchSize = 500;
    for (int i = 0; i < saveList.size(); i++) {
      entityManager.persist(saveList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    for (int i = 0; i < bottleLogs.size(); i++) {
      entityManager.persist(bottleLogs.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void changePlantCount(ModelBottleChangePlants model, User currentUser) {
    Assert.notNull(model, "变更信息不能为空");
    Assert.hasText(model.getId(), "未知的培养瓶");
    Assert.notNull(model.getPlants(), "必须填写瓶内株数");
    if (model.getPlants() < 1) throw new ErrorMessageException("株数必须大于零");
    var po = bottleDAO.findById(model.getId()).orElse(null);
    if (po == null) throw new ErrorMessageException("未知的培养瓶");
    if (Objects.equals(po.getPlantCount(), model.getPlants())) {
      return;
    }
    var plantsOld = po.getPlantCount();
    po.setPlantCount(model.getPlants());

    var now = System.currentTimeMillis();
    // 出入库流水
    InventoryTransactionPO transactionPO = new InventoryTransactionPO();
    transactionPO.setId(Id.g());
    transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_ADJUSTMENT);
    transactionPO.setTableName(Bottle.TB_NAME);
    transactionPO.setRecordId(po.getId());
    transactionPO.setVariety(po.getVariety());
    transactionPO.setVarietyCode(po.getVarietyCode());
    transactionPO.setGeneration(po.getGeneration());
    transactionPO.setBottles(0);
    transactionPO.setPlants(model.getPlants() - plantsOld);
    transactionPO.setDescription("更正瓶内株数");
    transactionPO.setOperatorId(currentUser.getId());
    transactionPO.setOperatorName(currentUser.getName());
    transactionPO.setOperateTime(now);
    entityManager.persist(transactionPO);

    // 工作量流水-操作员
    WorkloadTransactionPO workloadTransaction = new WorkloadTransactionPO();
    workloadTransaction.setId(Id.g());
    workloadTransaction.setUserId(po.getLastOperatorId());
    workloadTransaction.setUserName(po.getLastOperatorName());
    workloadTransaction.setUserRole(Const.TISSUE_TASK_USER_ROLE_OPERATOR);
    workloadTransaction.setBizType(Const.WORKLOAD_TX_BIZ_TYPE_ADJUSTMENT);
    workloadTransaction.setTableName(Bottle.TB_NAME);
    workloadTransaction.setRecordId(po.getId());
    workloadTransaction.setVariety(po.getVariety());
    workloadTransaction.setVarietyCode(po.getVarietyCode());
    workloadTransaction.setGeneration(po.getGeneration());
    workloadTransaction.setBottles(0);
    workloadTransaction.setPlants(model.getPlants() - plantsOld);
    workloadTransaction.setDescription("更正瓶内株数");
    workloadTransaction.setOperatorId(currentUser.getId());
    workloadTransaction.setOperatorName(currentUser.getName());
    workloadTransaction.setOperateTime(now);
    entityManager.persist(workloadTransaction);

    // 工作量流水-质检员
    workloadTransaction = new WorkloadTransactionPO();
    workloadTransaction.setId(Id.g());
    workloadTransaction.setUserId(po.getLastInspectorId());
    workloadTransaction.setUserName(po.getLastInspectorName());
    workloadTransaction.setUserRole(Const.TISSUE_TASK_USER_ROLE_INSPECTOR);
    workloadTransaction.setBizType(Const.WORKLOAD_TX_BIZ_TYPE_ADJUSTMENT);
    workloadTransaction.setTableName(Bottle.TB_NAME);
    workloadTransaction.setRecordId(po.getId());
    workloadTransaction.setVariety(po.getVariety());
    workloadTransaction.setVarietyCode(po.getVarietyCode());
    workloadTransaction.setGeneration(po.getGeneration());
    workloadTransaction.setBottles(0);
    workloadTransaction.setPlants(model.getPlants() - plantsOld);
    workloadTransaction.setDescription("更正瓶内株数");
    workloadTransaction.setOperatorId(currentUser.getId());
    workloadTransaction.setOperatorName(currentUser.getName());
    workloadTransaction.setOperateTime(now);
    entityManager.persist(workloadTransaction);

    BottleLogPO logForStorage = new BottleLogPO();
    logForStorage.setId(Id.g());
    logForStorage.setIdBottle(model.getId());
    logForStorage.setOppTime(now);
    logForStorage.setTableName(Bottle.TB_NAME);
    logForStorage.setRecordId(model.getId());
    logForStorage.setBizType("更正株数");
    logForStorage.setDescription("瓶内株数由" + plantsOld + "株改为" + model.getPlants() + "株");
    logForStorage.setInspectorId(StringUtil.EMPTY);
    logForStorage.setInspectorName(StringUtil.EMPTY);
    logForStorage.setOperatorId(currentUser.getId());
    logForStorage.setOperatorName(currentUser.getName());
    logForStorage.setOperateStatus(StringUtil.EMPTY);
    logForStorage.setStorageStatus(StringUtil.EMPTY);
    logForStorage.setStorageReason(StringUtil.EMPTY);
    logForStorage.setDiscarded(Const.BOOLEAN_0);
    logForStorage.setDiscardReason(StringUtil.EMPTY);
    entityManager.persist(logForStorage);
  }

  public List<ModelBottleSimple> queryAllEnabledIdsForTaskParents(String taskId) {
    if (taskId == null || taskId.isBlank()) return List.of();
    var task = tissueTaskDAO.findById(taskId).orElse(null);
    if (task == null) return List.of();
    var variety = task.getVariety();

    Specification<BottlePO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_VARIETY), variety));
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_CURRENTSTORAGESTATUS), Const.BOTTLE_STORAGE_STATUS_IN));
      return cb.and(predicateList.toArray(new Predicate[0]));
    };

    return bottleDAO.findBy(spec, q -> q.as(ModelBottleSimple.class).all());
  }

  public List<ModelBottleSimple> queryAllEnabledIdsForCodeComplementParents(String taskId) {
    if (taskId == null || taskId.isBlank()) return List.of();
    var task = bottleCodeComplementTaskDAO.findById(taskId).orElse(null);
    if (task == null) return List.of();

    String varietyCode = task.getVarietyCode();
    var explantCollectionBatch = task.getExplantCollectionBatch();

    var taskGeneration = task.getGeneration() == null ? 0 : task.getGeneration();
    String sourceType = task.getSourceType();
    if (!Const.STORAGE_REASON_ROOTING.equals(sourceType) && taskGeneration - 1 < 0) {
      return List.of();
    }

    Specification<BottlePO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      // 公共条件：品种编码、外植体批次
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_VARIETYCODE), varietyCode));
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_EXPLANTCOLLECTIONBATCH), explantCollectionBatch));

      Predicate genPredicate;
      if (Const.STORAGE_REASON_ROOTING.equals(sourceType)) {
        // (generation = taskGeneration AND sourceType != rooting) OR (generation = taskGeneration -1 AND sourceType = rooting)
        Predicate p1 = cb.and(
          cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration),
          cb.notEqual(root.get(BottlePO.BP_NAME_SOURCETYPE), Const.STORAGE_REASON_ROOTING)
        );
        Predicate p2 = cb.and(
          cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration - 1),
          cb.equal(root.get(BottlePO.BP_NAME_SOURCETYPE), Const.STORAGE_REASON_ROOTING)
        );
        genPredicate = cb.or(p1, p2);
      } else {
        // 否则：generation = taskGeneration -1
        genPredicate = cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration - 1);
      }
      predicateList.add(genPredicate);
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    return bottleDAO.findBy(spec, q -> q.as(ModelBottleSimple.class).all());
  }

  public List<ModelBottleSimple> queryAllEnabledIdsForInventoryAddBottle(String taskId) {
    if (taskId == null || taskId.isBlank()) return List.of();
    var task = inventoryAddBottleDAO.findById(taskId).orElse(null);
    if (task == null) return List.of();

    String varietyCode = task.getVarietyCode();
    var explantCollectionBatch = task.getExplantCollectionBatch();

    var taskGeneration = task.getGeneration() == null ? 0 : task.getGeneration();
    String sourceType = task.getSourceType();
    if (!Const.STORAGE_REASON_ROOTING.equals(sourceType) && taskGeneration - 1 < 0) {
      return List.of();
    }

    Specification<BottlePO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      // 公共条件：品种编码、外植体批次
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_VARIETYCODE), varietyCode));
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_EXPLANTCOLLECTIONBATCH), explantCollectionBatch));

      Predicate genPredicate;
      if (Const.STORAGE_REASON_ROOTING.equals(sourceType)) {
        // (generation = taskGeneration AND sourceType != rooting) OR (generation = taskGeneration -1 AND sourceType = rooting)
        Predicate p1 = cb.and(
          cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration),
          cb.notEqual(root.get(BottlePO.BP_NAME_SOURCETYPE), Const.STORAGE_REASON_ROOTING)
        );
        Predicate p2 = cb.and(
          cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration - 1),
          cb.equal(root.get(BottlePO.BP_NAME_SOURCETYPE), Const.STORAGE_REASON_ROOTING)
        );
        genPredicate = cb.or(p1, p2);
      } else {
        // 否则：generation = taskGeneration -1
        genPredicate = cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration - 1);
      }
      predicateList.add(genPredicate);
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    return bottleDAO.findBy(spec, q -> q.as(ModelBottleSimple.class).all());
  }

  public List<ModelBottleSimple> queryAllEnabledParentIdsForChangeParent(String bottleId) {
    if (bottleId == null || bottleId.isBlank()) return List.of();
    var bottle = bottleDAO.findById(bottleId).orElse(null);
    if (bottle == null) return List.of();

    String varietyCode = bottle.getVarietyCode();
    var explantCollectionBatch = bottle.getExplantCollectionBatch();

    var taskGeneration = bottle.getGeneration() == null ? 0 : bottle.getGeneration();
    String sourceType = bottle.getSourceType();
    if (!Const.STORAGE_REASON_ROOTING.equals(sourceType) && taskGeneration - 1 < 0) {
      return List.of();
    }

    // 查询当前瓶所有子代、后代ID
    List<String> descendantIds = bottleRelationDAO.findAllChildIds(bottleId);

    Specification<BottlePO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      // 公共条件：品种编码、外植体批次
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_VARIETYCODE), varietyCode));
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_EXPLANTCOLLECTIONBATCH), explantCollectionBatch));
      predicateList.add(cb.notEqual(root.get(BottlePO.BP_NAME_ID), bottleId));

      if (!descendantIds.isEmpty()) {
        predicateList.add(cb.not(root.get(BottlePO.BP_NAME_ID).in(descendantIds)));
      }

      Predicate genPredicate;
      if (Const.STORAGE_REASON_ROOTING.equals(sourceType)) {
        // (generation = taskGeneration AND sourceType != rooting) OR (generation = taskGeneration -1 AND sourceType = rooting)
        Predicate p1 = cb.and(
          cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration),
          cb.notEqual(root.get(BottlePO.BP_NAME_SOURCETYPE), Const.STORAGE_REASON_ROOTING)
        );
        Predicate p2 = cb.and(
          cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration - 1),
          cb.equal(root.get(BottlePO.BP_NAME_SOURCETYPE), Const.STORAGE_REASON_ROOTING)
        );
        genPredicate = cb.or(p1, p2);
      } else {
        // 否则：generation = taskGeneration -1
        genPredicate = cb.equal(root.get(BottlePO.BP_NAME_GENERATION), taskGeneration - 1);
      }
      predicateList.add(genPredicate);
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    return bottleDAO.findBy(spec, q -> q.as(ModelBottleSimple.class).all());
  }

  public List<ModelBottleSimple> queryAllStorageIn() {
    Specification<BottlePO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_CURRENTSTORAGESTATUS), Const.BOTTLE_STORAGE_STATUS_IN));
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    return bottleDAO.findBy(spec, q -> q.as(ModelBottleSimple.class).all());
  }

  public List<ModelBottleSimpleImp> queryBottlesForInventorySubBottle(String idInOut) {
    return bottleDAO.findBottlesForInventorySubBottle(idInOut);
  }

  public List<ModelBottleSimple> queryAllForAcclimatizationIn() {
    Specification<BottlePO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_CURRENTSTORAGESTATUS), Const.BOTTLE_STORAGE_STATUS_OUT));
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_CURRENTCULTURESTAGE), Const.TISSUE_TASK_TYPE_ACCLIMATIZATION));
      predicateList.add(cb.equal(root.get(BottlePO.BP_NAME_LASTISSUEDREASON), Const.STORAGE_REASON_ACCLIMATIZATION));
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    return bottleDAO.findBy(spec, q -> q.as(ModelBottleSimple.class).all());
  }

  public Bottle queryById(String bottleId) {
    var po = bottleDAO.findById(bottleId).orElse(null);
    return po == null ? new BottleDTO() : new BottleDTO(po);
  }

  public List<String> queryAllIdStorageIn() {
    return bottleDAO.findAllByStorageStatus(Const.BOTTLE_STORAGE_STATUS_IN);
  }

  public List<BottleDTO> queryParents(String id) {
    return bottleDAO.findParents(id).stream().map(BottleDTO::new).toList();
  }

  public List<BottleDTO> queryChildren(String id) {
    return bottleDAO.findChildren(id).stream().map(BottleDTO::new).toList();
  }

  public List<BottleLogDTO> queryLogs(String idBottle) {
    return bottleLogDAO.findByIdBottleOrderByOppTimeAsc(idBottle).stream().map(BottleLogDTO::new).toList();
  }

  public List<ArchiveDTO> queryArchives(String idBottle) {
    return archiveDAO.findArchiveByBottleId(idBottle).stream().map(ArchiveDTO::new).toList();
  }
}
