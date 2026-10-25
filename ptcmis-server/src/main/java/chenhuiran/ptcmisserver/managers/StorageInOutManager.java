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

import chenhuiran.ptcmisserver.caches.DictionaryCache;
import chenhuiran.ptcmisserver.repository.*;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.*;
import com.chenhuiran.commons.lang.Id;
import com.chenhuiran.commons.lang.StringUtil;
import com.chenhuiran.commons.lang.exceptions.ErrorMessageException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class StorageInOutManager {
  @Autowired
  private DictionaryCache dictionaryCache;
  @Autowired
  private TissueTaskDAO tissueTaskDAO;
  @Autowired
  private TissueTaskOppGroupDAO tissueTaskOppGroupDAO;
  @Autowired
  private StorageInOutDAO storageInOutDAO;
  @Autowired
  private StorageInOutLogDAO storageInOutLogDAO;
  @Autowired
  private StorageInOutBottleDAO storageInOutBottleDAO;
  @Autowired
  private TissueTaskBottleDAO tissueTaskBottleDAO;
  @Autowired
  private BottleDAO bottleDAO;
  @Autowired
  private BottleRelationDAO bottleRelationDAO;
  @PersistenceContext
  private EntityManager entityManager;

  @Nullable
  public StorageInOut queryById(String storageInOutId) {
    var po = storageInOutDAO.findById(storageInOutId).orElse(null);
    return po == null ? null : new StorageInOutDTO(po);
  }

  public List<? extends StorageInOutLog> queryAllLogs(String storageInOutId) {
    return storageInOutLogDAO.findByIdInOutOrderByOperatorTimeDesc(storageInOutId);
  }

  public QueryResultPaged<StorageOutTaskView> queryByCondition(QueryCondition<QueryConditionStorageOutTaskView> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    java.util.function.BiFunction<Root<StorageInOutPO>, Root<TissueTaskPO>, List<Predicate>> buildPredicates = (root, ttRoot) -> {
      List<Predicate> predicates = new ArrayList<>();
      // 手动创建连接
      predicates.add(cb.equal(root.get(StorageInOut.BP_NAME_IDTASK), ttRoot.get(TissueTask.BP_NAME_ID)));
      predicates.add(cb.isNotNull(root.get(StorageInOut.BP_NAME_IDTASK)));
      if (conditionModel != null) {
        if (conditionModel.getStatus() != null && !conditionModel.getStatus().isBlank()) {
          predicates.add(cb.equal(root.get(StorageInOut.BP_NAME_STATUS), conditionModel.getStatus()));
        }
        if (conditionModel.getInOutType() != null && !conditionModel.getInOutType().isBlank()) {
          predicates.add(cb.equal(root.get(StorageInOut.BP_NAME_INOUT), conditionModel.getInOutType()));
        }
        if (conditionModel.getReason() != null && !conditionModel.getReason().isBlank()) {
          predicates.add(cb.equal(root.get(StorageInOut.BP_NAME_REASON), conditionModel.getReason()));
        }
        ModelDateRange applicantTime = conditionModel.getApplicantTime();
        if (applicantTime != null) {
          if (applicantTime.getStart() != null && applicantTime.getStart() >= 1) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(StorageInOut.BP_NAME_APPLICANTTIME), applicantTime.getStart()));
          }
          if (applicantTime.getEnd() != null && applicantTime.getEnd() >= 1) {
            predicates.add(cb.lessThanOrEqualTo(root.get(StorageInOut.BP_NAME_APPLICANTTIME), applicantTime.getEnd()));
          }
        }
        ModelDateRange operatorTime = conditionModel.getOperatorTime();
        if (operatorTime != null) {
          if (operatorTime.getStart() != null && operatorTime.getStart() >= 1) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(StorageInOut.BP_NAME_OPERATORTIME), operatorTime.getStart()));
          }
          if (operatorTime.getEnd() != null && operatorTime.getEnd() >= 1) {
            predicates.add(cb.lessThanOrEqualTo(root.get(StorageInOut.BP_NAME_OPERATORTIME), operatorTime.getEnd()));
          }
        }
        if (conditionModel.getTaskType() != null && !conditionModel.getTaskType().isBlank()) {
          predicates.add(cb.equal(ttRoot.get(TissueTask.BP_NAME_TASKTYPE), conditionModel.getTaskType()));
        }
        if (conditionModel.getTaskStatus() != null && !conditionModel.getTaskStatus().isBlank()) {
          predicates.add(cb.equal(ttRoot.get(TissueTask.BP_NAME_TASKSTATUS), conditionModel.getTaskStatus()));
        }
        if (conditionModel.getVariety() != null && !conditionModel.getVariety().isBlank()) {
          predicates.add(cb.equal(ttRoot.get(TissueTask.BP_NAME_VARIETY), conditionModel.getVariety()));
        }
        if (conditionModel.getVarietyCode() != null && !conditionModel.getVarietyCode().isBlank()) {
          predicates.add(cb.equal(ttRoot.get(TissueTask.BP_NAME_VARIETYCODE), conditionModel.getVarietyCode()));
        }
        if (conditionModel.getKeyword() != null && !conditionModel.getKeyword().isBlank()) {
          String likeVal = "%" + conditionModel.getKeyword().trim() + "%";
          Predicate p1 = cb.like(root.get(StorageInOut.BP_NAME_OPERATORNAME), likeVal);
          Predicate p2 = cb.like(root.get(StorageInOut.BP_NAME_APPLICANTNAME), likeVal);
          Predicate p3 = cb.like(ttRoot.get(TissueTask.BP_NAME_VARIETY), likeVal);
          Predicate p4 = cb.like(ttRoot.get(TissueTask.BP_NAME_VARIETYCODE), likeVal);
          predicates.add(cb.or(p1, p2, p3, p4));
        }
      }
      return predicates;
    };

    // 查询数据
    CriteriaQuery<StorageOutTaskView> cq = cb.createQuery(StorageOutTaskView.class);
    Root<StorageInOutPO> root = cq.from(StorageInOutPO.class);
    Root<TissueTaskPO> ttRoot = cq.from(TissueTaskPO.class);

    cq.where(cb.and(buildPredicates.apply(root, ttRoot).toArray(new Predicate[0])));
    cq.orderBy(cb.desc(root.get("operatorTime")));
    cq.select(cb.construct(StorageOutTaskView.class,
      root.get("id"), root.get("inOut"), root.get("bottlesApplied"), root.get("plantsApplied"),
      root.get("bottlesKeepInApplied"), root.get("plantsKeepInApplied"), root.get("bottlesActual"),
      root.get("plantsActual"), root.get("bottlesKeepInActual"), root.get("plantsKeepInActual"),
      root.get("bottlesDiscard"), root.get("plantsDiscard"),
      root.get("bottlesVoided"), root.get("plantsVoided"),
      root.get("reason"), root.get("status"), root.get("oppReason"), root.get("idTask"),
      root.get("idInspection"), root.get("applicantId"), root.get("applicantName"),
      root.get("applicantTime"), root.get("operatorId"), root.get("operatorName"),
      root.get("operatorTime"), ttRoot.get("variety"), ttRoot.get("varietyCode"), ttRoot.get("explantCollectionBatch"),
      ttRoot.get("taskType"), ttRoot.get("taskStatus"),
      ttRoot.get("creatorUserId"), ttRoot.get("creatorUserName"), ttRoot.get("createTime"),
      ttRoot.get("startTime"), ttRoot.get("finishTime")
    ));

    // 查询 Count
    CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
    Root<StorageInOutPO> countRoot = countCq.from(StorageInOutPO.class);
    Root<TissueTaskPO> countTtRoot = countCq.from(TissueTaskPO.class);
    countCq.where(cb.and(buildPredicates.apply(countRoot, countTtRoot).toArray(new Predicate[0])));
    countCq.select(cb.countDistinct(countRoot));
    long total = entityManager.createQuery(countCq).getSingleResult();

    // 组织分页
    TypedQuery<StorageOutTaskView> typedQuery = entityManager.createQuery(cq);
    typedQuery.setFirstResult(pageModel.getPage() * pageModel.getPageSize());
    typedQuery.setMaxResults(pageModel.getPageSize());
    List<StorageOutTaskView> contentList = typedQuery.getResultList();

    pageModel.updateTotal(total);
    return new QueryResultPaged<>(contentList, pageModel);
  }

  public List<? extends StorageInOutBottle> queryBottles(String inOutId) {
    return storageInOutBottleDAO.findByIdInOutOrderByIdBottleAsc(inOutId);
  }

  @Transactional
  public void rejBiz(ModelStorageOutTaskRej rejBiz, User currentUser) {
    Assert.notNull(rejBiz, "驳回信息不能为空");
    Assert.hasText(rejBiz.getId(), "出库申请不能为空");
    Assert.hasText(rejBiz.getIdTask(), "组培任务不能为空");
    Assert.hasText(rejBiz.getOppReason(), "驳回原因不能为空");

    String id = rejBiz.getId();
    var po = storageInOutDAO.findById(id).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库申请信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_APPLY) && !status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED) && !status.equals(Const.STORAGE_IN_OUT_STATUS_REJ_STORE))
      throw new ErrorMessageException("当前出库业务已被处理，无法驳回。");

    String idTask = rejBiz.getIdTask();
    var task = tissueTaskDAO.findById(idTask).orElse(null);
    if (task == null) {
      throw new ErrorMessageException("无法找到组培任务信息");
    }

    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(id);

    var now = System.currentTimeMillis();
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_REJ_BIZ);
    po.setOppReason(rejBiz.getOppReason());
    po.setPlantsActual(0);
    po.setBottlesActual(0);

    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(rejBiz.getOppReason());
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);
  }

  @Transactional
  public void rejStore(ModelStorageOutTaskRej rejBiz, User currentUser) {
    Assert.notNull(rejBiz, "驳回信息不能为空");
    Assert.hasText(rejBiz.getId(), "出库申请不能为空");
    Assert.hasText(rejBiz.getIdTask(), "组培任务不能为空");
    Assert.hasText(rejBiz.getOppReason(), "驳回原因不能为空");

    String id = rejBiz.getId();
    var po = storageInOutDAO.findById(id).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库申请信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_DONE))
      throw new ErrorMessageException("当前出库业务已被处理，无法驳回。");

    String idTask = rejBiz.getIdTask();
    var task = tissueTaskDAO.findById(idTask).orElse(null);
    if (task == null) {
      throw new ErrorMessageException("无法找到组培任务信息");
    }

    var now = System.currentTimeMillis();
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_REJ_STORE);
    po.setOppReason(rejBiz.getOppReason());

    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(rejBiz.getOppReason());
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);
  }

  @Transactional
  public void outProcessForTask(ModelStorageOutTaskProcessDetail model, User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");
    Assert.hasText(model.getIssueTaskId(), "出库对应的组培任务不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库申请信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (
      !status.equals(Const.STORAGE_IN_OUT_STATUS_APPLY)
        && !status.equals(Const.STORAGE_IN_OUT_STATUS_REJ_STORE)
        && !status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED)
    ) throw new ErrorMessageException("当前出库业务已被处理，无法继续执行。");

    String issueTaskId = model.getIssueTaskId();
    var task = tissueTaskDAO.findById(issueTaskId).orElse(null);
    if (task == null) throw new ErrorMessageException("无法找到组培任务信息");
    if (
      task.getTaskStatus().equals(Const.TISSUE_TASK_STATUS_CANCELED)
        || task.getTaskStatus().equals(Const.TISSUE_TASK_STATUS_FINISHED)
    ) throw new ErrorMessageException("组培任务已结束，无法为其办理出库业务");

    var usedList = model.getUsedList();
    if (usedList != null) {
      for (var m : usedList) {
        if (m.getPlantCountActual() == null || m.getPlantCountActual() < 1) {
          throw new ErrorMessageException("出库培养瓶存疑，" + m.getIdBottle() + "的株数错误，必须大于零。");
        }
      }
      long bottleCount = bottleDAO.countBottles1(
        task.getVariety(),
        task.getVarietyCode(),
        Const.BOTTLE_STORAGE_STATUS_IN,
        Const.BOOLEAN_0,
        usedList.stream().map(ModelStorageInOutDetail::getIdBottle).toList()
      );
      if (bottleCount != usedList.size())
        throw new ErrorMessageException("出库培养瓶存疑，请刷新可用瓶后再次执行出库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 计算瓶数/株数
    int plantActual = 0;
    int plantApply = 0;
    int bottlesActual = 0;
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (usedList != null) {
      // 查询出库瓶
      var bottlePOs = bottleDAO.findAllByIdIn(usedList.stream().map(ModelStorageInOutDetail::getIdBottle).toList());
      var bottlePOMap = new HashMap<String, BottlePO>();
      for (var bottlePO : bottlePOs) {
        bottlePOMap.put(bottlePO.getId(), bottlePO);
      }
      for (var used : usedList) {
        var bottle = bottlePOMap.get(used.getIdBottle());
        if (bottle == null) continue;
        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(bottle.getId());
        b.setVariety(bottle.getVariety());
        b.setVarietyCode(bottle.getVarietyCode());
        b.setExplantCollectionBatch(bottle.getExplantCollectionBatch());
        b.setGeneration(bottle.getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_OUT);
        b.setPlantCountApplied(bottle.getPlantCount());
        b.setPlantCountActual(used.getPlantCountActual());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_NORMAL);
        b.setDiscardReason(StringUtil.EMPTY);
        bottles.add(b);

        plantApply += bottle.getPlantCount();
        plantActual += used.getPlantCountActual();
        bottlesActual++;
      }
    }

    // 更新出库单数量
    po.setBottlesApplied(-bottlesActual);
    po.setBottlesActual(-bottlesActual);
    po.setPlantsApplied(-plantApply);
    po.setPlantsActual(-plantActual);
    po.setBottlesDiscard(0);
    po.setPlantsDiscard(0);

    // 更新任务母瓶信息
    task.setEstimatedParentBottles(bottlesActual);
    task.setEstimatedParentPlants(plantActual);

    var now = System.currentTimeMillis();
    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);

    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void outDoneForTask(ModelStorageOutTaskProcessDetail model, User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");
    Assert.hasText(model.getIssueTaskId(), "出库对应的组培任务不能为空");
    String inOutId = model.getInOutId();

    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库申请信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED)
      && !status.equals(Const.STORAGE_IN_OUT_STATUS_REJ_STORE))
      throw new ErrorMessageException("当前出库尚未执行，无法备出。");

    String issueTaskId = model.getIssueTaskId();
    if (!issueTaskId.equals(po.getIdTask())) throw new ErrorMessageException("无法找到组培任务信息");
    var task = tissueTaskDAO.findById(issueTaskId).orElse(null);
    if (task == null) throw new ErrorMessageException("无法找到组培任务信息");
    if (
      task.getTaskStatus().equals(Const.TISSUE_TASK_STATUS_CANCELED)
        || task.getTaskStatus().equals(Const.TISSUE_TASK_STATUS_FINISHED)
    ) throw new ErrorMessageException("组培任务已结束，无法为其办理出库业务");

    var usedList = model.getUsedList();
    if (usedList != null && !usedList.isEmpty()) {
      for (var m : usedList) {
        if (m.getPlantCountActual() == null || m.getPlantCountActual() < 1)
          throw new ErrorMessageException("出库培养瓶存疑，" + m.getIdBottle() + "的株数错误，必须大于零。");
      }
      long bottleCount = bottleDAO.countBottles1(
        task.getVariety(),
        task.getVarietyCode(),
        Const.BOTTLE_STORAGE_STATUS_IN,
        Const.BOOLEAN_0,
        usedList.stream().map(ModelStorageInOutDetail::getIdBottle).toList()
      );
      if (bottleCount != usedList.size())
        throw new ErrorMessageException("出库培养瓶存疑，请刷新可用瓶后再次执行出库。");
    } else {
      throw new ErrorMessageException("未实际出库。");
    }

    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 计算瓶数/株数
    int plantActual = 0;
    int plantApply = 0;
    int bottlesActual = 0;
    // 组织关联瓶信息
    var inOutBottles = new ArrayList<StorageInOutBottlePO>();

    // 查询出库瓶
    var bottlePOs = bottleDAO.findAllByIdIn(usedList.stream().map(ModelStorageInOutDetail::getIdBottle).toList());
    var bottlePOMap = new HashMap<String, BottlePO>();
    for (var bottlePO : bottlePOs) {
      bottlePOMap.put(bottlePO.getId(), bottlePO);
    }
    for (var used : usedList) {
      var bottle = bottlePOMap.get(used.getIdBottle());
      if (bottle == null) continue;
      var b = new StorageInOutBottlePO();
      b.setIdInOut(inOutId);
      b.setIdBottle(bottle.getId());
      b.setVariety(bottle.getVariety());
      b.setVarietyCode(bottle.getVarietyCode());
      b.setExplantCollectionBatch(bottle.getExplantCollectionBatch());
      b.setGeneration(bottle.getGeneration());
      b.setIgnoreStats(Const.BOOLEAN_0);
      b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_OUT);
      b.setPlantCountApplied(bottle.getPlantCount());
      b.setPlantCountActual(used.getPlantCountActual());
      b.setKeep(Const.BOOLEAN_0);
      b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_NORMAL);
      b.setDiscardReason(StringUtil.EMPTY);
      inOutBottles.add(b);

      plantApply += bottle.getPlantCount();
      plantActual += used.getPlantCountActual();
      bottlesActual++;
    }

    // 更新出库单数量
    po.setBottlesApplied(-bottlesActual);
    po.setBottlesActual(-bottlesActual);
    po.setPlantsApplied(-plantApply);
    po.setPlantsActual(-plantActual);
    po.setBottlesDiscard(0);
    po.setPlantsDiscard(0);

    // 更新任务母瓶信息
    task.setEstimatedParentBottles(bottlesActual);
    task.setEstimatedParentPlants(plantActual);

    var now = System.currentTimeMillis();
    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_DONE);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);

    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < inOutBottles.size(); i++) {
      entityManager.persist(inOutBottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void rejBizForIn(ModelStorageOutTaskRej rejBiz, User currentUser) {
    Assert.notNull(rejBiz, "驳回信息不能为空");
    Assert.hasText(rejBiz.getId(), "入库申请不能为空");
    Assert.hasText(rejBiz.getIdTask(), "组培任务不能为空");
    Assert.hasText(rejBiz.getOppReason(), "驳回原因不能为空");

    String id = rejBiz.getId();
    var po = storageInOutDAO.findById(id).orElse(null);
    if (po == null || !Const.STORAGE_IN_OUT_TYPE_IN.equals(po.getInOut()))
      throw new ErrorMessageException("无法找到入库申请信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("入库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_APPLY) && !status.equals(Const.STORAGE_IN_OUT_STATUS_REJ_STORE))
      throw new ErrorMessageException("当前入库业务已被处理，无法驳回。");

    String idTask = rejBiz.getIdTask();
    var task = tissueTaskDAO.findById(idTask).orElse(null);
    if (task == null) {
      throw new ErrorMessageException("无法找到组培任务信息");
    }

    var now = System.currentTimeMillis();
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_REJ_BIZ);
    po.setOppReason(rejBiz.getOppReason());

    // 组织入库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(rejBiz.getOppReason());
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);
  }

  @Transactional
  public void inDoneForTask(String inOutId, User currentUser) {
    Assert.hasText(inOutId, "入库信息不能为空");

    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null || !Const.STORAGE_IN_OUT_TYPE_IN.equals(po.getInOut()))
      throw new ErrorMessageException("无法找到入库申请信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("入库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_APPLY))
      throw new ErrorMessageException("无法对此入库申请执行入库操作。");

    String issueTaskId = po.getIdTask();
    var task = tissueTaskDAO.findById(issueTaskId).orElse(null);
    if (task == null) throw new ErrorMessageException("无法找到组培任务信息");
    if (!task.getTaskStatus().equals(Const.TISSUE_TASK_STATUS_FINISHED))
      throw new ErrorMessageException("组培任务尚未结束，无法为其办理入库业务");

    // 操作组
    var oppGroupList = tissueTaskOppGroupDAO.findByIdTaskIn(List.of(issueTaskId));
    var oppGroupParentBottleMap = new HashMap<String, String>();
    var oppGroupMap = new HashMap<String, TissueTaskOppGroupPO>();

    // 查询培养瓶信息
    var bottles = tissueTaskBottleDAO.findByIdTaskInOrderByIdBottleAsc(List.of(issueTaskId));
    var bottlesInList = new ArrayList<StorageInOutBottlePO>();
    var bottlesList = new ArrayList<TissueTaskBottlePO>();
    var parentBottleIdList = new ArrayList<String>();
    if (bottles != null) {
      for (var bottle : bottles) {
        // 淘汰瓶
        if (bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) && bottle.getPlantCountActual() != null && bottle.getPlantCountActual() > 0) {
          bottlesList.add(bottle);
          continue;
        }
        var inOutBottleType = bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT) && bottle.getParentKeep().equals(Const.BOOLEAN_1)
          ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_KEEP_IN
          : bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT) && bottle.getIdOppGroup().isBlank()
          ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN
          : bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_CHILD) && bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_FINISHED)
          ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_CHILD_IN
          : StringUtil.EMPTY;
        if (!inOutBottleType.isBlank()) {
          // 校验株数
          if (bottle.getPlantCountActual() == null || bottle.getPlantCountActual() < 1) {
            throw new ErrorMessageException("入库培养瓶存疑，" + bottle.getIdBottle() + "的株数错误，必须大于零。");
          }
          // 母瓶ID列表
          if (bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT)) {
            parentBottleIdList.add(bottle.getIdBottle());
          }
          // 任务瓶
          bottlesList.add(bottle);
          // 出入库培养瓶明细
          var plantCount = bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT) && bottle.getParentKeep().equals(Const.BOOLEAN_1) ? bottle.getParentKeepPlantCount() : bottle.getPlantCountActual();
          var bottleInPo = new StorageInOutBottlePO();
          bottleInPo.setIdInOut(inOutId);
          bottleInPo.setIdBottle(bottle.getIdBottle());
          bottleInPo.setVariety(task.getVariety());
          bottleInPo.setVarietyCode(task.getVarietyCode());
          bottleInPo.setExplantCollectionBatch(bottle.getExplantCollectionBatch());
          bottleInPo.setGeneration(bottle.getGeneration());
          bottleInPo.setIgnoreStats(Const.BOOLEAN_0);
          bottleInPo.setInOutBottleType(inOutBottleType);
          bottleInPo.setPlantCountApplied(plantCount);
          bottleInPo.setPlantCountActual(plantCount);
          bottleInPo.setKeep(bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT) && bottle.getParentKeep().equals(Const.BOOLEAN_1) ? Const.BOOLEAN_1 : Const.BOOLEAN_0);
          bottleInPo.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DONE);
          bottleInPo.setDiscardReason(StringUtil.EMPTY);
          bottlesInList.add(bottleInPo);
        }
      }
    }

    if (!parentBottleIdList.isEmpty()) {
      long bottleCount = bottleDAO.countBottles1(
        task.getVariety(),
        task.getVarietyCode(),
        Const.BOTTLE_STORAGE_STATUS_OUT,
        Const.BOOLEAN_0,
        parentBottleIdList
      );
      if (bottleCount != parentBottleIdList.size())
        throw new ErrorMessageException("待入库的母瓶数量与仓储实际数量不符。待入库的" + parentBottleIdList.size() + "个母瓶，仓储中仅有" + bottleCount + "瓶处于已出库待入库状态。");
    }

    if (bottlesInList.isEmpty()) {
      throw new ErrorMessageException("没有需要入库的培养瓶。");
    }

    // 子瓶的母瓶ID列表
    var parentBottleIds = new ArrayList<String>();
    if (oppGroupList != null && !oppGroupList.isEmpty()) {
      for (var oppGroup : oppGroupList) {
        oppGroupMap.put(oppGroup.getId(), oppGroup);
        String parentBottleId = oppGroup.getIdParentBottle() == null ? StringUtil.EMPTY : oppGroup.getIdParentBottle();
        oppGroupParentBottleMap.put(oppGroup.getId(), parentBottleId);
        if (!parentBottleId.isBlank()) {
          parentBottleIds.add(parentBottleId);
        }
      }
    }
    // 子瓶的母瓶列表
    List<BottlePO> parentBottles;
    if (!parentBottleIds.isEmpty()) {
      parentBottles = bottleDAO.findAllByIdIn(parentBottleIds);
    } else {
      parentBottles = new ArrayList<>();
    }
    Map<String, BottlePO> parentBottleMap = new HashMap<>();
    for (var parent : parentBottles) {
      parentBottleMap.put(parent.getId(), parent);
    }

    // 组培瓶日志
    List<BottleLogPO> bottleLogs = new ArrayList<>();
    var now = System.currentTimeMillis();
    String reason = dictionaryCache.getName("storage_reason", po.getReason());
    String inOutType = dictionaryCache.getName("storage_in_out_type", po.getInOut());
    String logStorageBizType = reason + inOutType;
    String logTaskBizType = dictionaryCache.getName("tissue_task_type", task.getTaskType());
    // 新建子瓶
    List<BottlePO> childBottles = new ArrayList<>();
    // 实际用到的母瓶ID
    Set<String> parentIds = new HashSet<>();
    // 工作量流水。 bizType/role/userId/
    var workloadTransactionMap = new HashMap<String, HashMap<String, HashMap<String, HashMap<String, Map<String, Map<Integer, WorkloadTransactionPO>>>>>>();
    // 仓储流水。 bizType/generation
    var inventoryTransactionMap = new HashMap<String, Map<Integer, InventoryTransactionPO>>();

    for (var bottle : bottlesList) {
      // 子代培养瓶
      if (bottle.getFinishTime() != null && bottle.getFinishTime() > 0L && Const.BOTTLE_TASK_ROLE_CHILD.equals(bottle.getRoleInTask())) {
        // 母瓶信息
        String idParent = oppGroupParentBottleMap.get(bottle.getIdOppGroup());
        idParent = idParent == null ? StringUtil.EMPTY : idParent;
        if (!idParent.isBlank()) {
          parentIds.add(idParent);
        }
        var parent = parentBottleMap.get(idParent);

        BottlePO childBottle = new BottlePO();
        // 基本信息
        childBottle.setId(bottle.getIdBottle());
        childBottle.setVariety(task.getVariety());
        childBottle.setVarietyCode(task.getVarietyCode());
        childBottle.setExplantCollectionBatch(parent == null ? task.getExplantCollectionBatch() : parent.getExplantCollectionBatch());
        if (parent == null) {
          childBottle.setGeneration(0);
        } else {
          if (task.getTaskType().equals(Const.TISSUE_TASK_TYPE_ROOTING)) {
            if (parent.getSourceType().equals(Const.STORAGE_REASON_INDUCTION)
              || parent.getSourceType().equals(Const.STORAGE_REASON_DIFFERENTIATION)
              || parent.getSourceType().equals(Const.STORAGE_REASON_MULTIPLICATION)
              || parent.getSourceType().equals(Const.STORAGE_REASON_ACCLIMATIZATION)) {
              childBottle.setGeneration(parent.getGeneration());
            } else {
              childBottle.setGeneration(parent.getGeneration() + 1);
            }
          } else {
            childBottle.setGeneration(parent.getGeneration() + 1);
          }
        }
        childBottle.setPlantCount(bottle.getPlantCountActual());
        childBottle.setTaskTime(bottle.getFinishTime());
        childBottle.setSourceType(task.getTaskType());
        childBottle.setIdParent(idParent);
        // 组培任务信息（当前）
        childBottle.setCurrentIdTask(StringUtil.EMPTY);
        childBottle.setCurrentIdOppGroup(StringUtil.EMPTY);
        childBottle.setCurrentCultureStage(StringUtil.EMPTY);
        childBottle.setCurrentRoleInTask(Const.BOTTLE_TASK_ROLE_NONE);
        childBottle.setCurrentParentKeep(Const.BOOLEAN_0);
        childBottle.setCurrentParentKeepPlantCount(0);
        childBottle.setCurrentOperateStatus(Const.BOTTLE_OPERATE_STATUS_NONE);
        childBottle.setCurrentInspectorId(StringUtil.EMPTY);
        childBottle.setCurrentInspectorName(StringUtil.EMPTY);
        childBottle.setCurrentOperatorId(StringUtil.EMPTY);
        childBottle.setCurrentOperatorName(StringUtil.EMPTY);
        // 组培任务信息（末次）
        childBottle.setLastIdTask(task.getId());
        childBottle.setLastIdOppGroup(bottle.getIdOppGroup());
        childBottle.setLastCultureStage(task.getTaskType());
        childBottle.setLastRoleInTask(bottle.getRoleInTask());
        childBottle.setLastParentKeep(bottle.getParentKeep());
        childBottle.setLastParentKeepPlantCount(bottle.getParentKeepPlantCount());
        childBottle.setLastOperateStatus(bottle.getOperateStatus());
        childBottle.setLastInspectorId(bottle.getInspectorId());
        childBottle.setLastInspectorName(bottle.getInspectorName());
        childBottle.setLastOperatorId(bottle.getOperatorId());
        childBottle.setLastOperatorName(bottle.getOperatorName());
        // 仓储信息
        childBottle.setCurrentStorageStatus(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? Const.BOTTLE_STORAGE_STATUS_OUT : Const.BOTTLE_STORAGE_STATUS_IN);
        childBottle.setLastReceivedUserId(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? StringUtil.EMPTY : currentUser.getId());
        childBottle.setLastReceivedUserName(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? StringUtil.EMPTY : currentUser.getName());
        childBottle.setLastReceivedTime(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? 0L : now);
        childBottle.setLastReceivedReason(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? StringUtil.EMPTY : task.getTaskType());
        childBottle.setLastIssuedUserId(StringUtil.EMPTY);
        childBottle.setLastIssuedUserName(StringUtil.EMPTY);
        childBottle.setLastIssuedTime(0L);
        childBottle.setLastIssuedReason(StringUtil.EMPTY);
        // 淘汰信息
        childBottle.setDiscarded(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? Const.BOOLEAN_1 : Const.BOOLEAN_0);
        childBottle.setPerformanceIncluded(Const.BOOLEAN_1);
        childBottle.setDiscardReason(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? bottle.getDiscardReason() : StringUtil.EMPTY);
        childBottle.setDiscardReasonDesc(StringUtil.EMPTY);
        childBottle.setDiscardTime(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? bottle.getFinishTime() : 0L);
        childBottle.setDiscardUserId(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? bottle.getInspectorId() : StringUtil.EMPTY);
        childBottle.setDiscardUserName(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? bottle.getInspectorName() : StringUtil.EMPTY);
        // 向待处理列表中插入组培瓶信息
        childBottles.add(childBottle);

        // 组织子瓶工作量流水
        var workloadTransactionBizType = childBottle.getDiscarded().equals(Const.BOOLEAN_1) ? Const.WORKLOAD_TX_BIZ_TYPE_DISCARD : Const.WORKLOAD_TX_BIZ_TYPE_CHILD;
        // 工作量流水-操作员
        var workloadTransactionPO = workloadTransactionMap.computeIfAbsent(workloadTransactionBizType, (k) -> new HashMap<>())
          .computeIfAbsent(Const.TISSUE_TASK_USER_ROLE_OPERATOR, (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getOperatorId(), (k) -> new HashMap<>())
          .computeIfAbsent(childBottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(childBottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(childBottle.getGeneration(), (k) -> {
            WorkloadTransactionPO workloadTxPO = new WorkloadTransactionPO();
            workloadTxPO.setId(Id.g());
            workloadTxPO.setUserId(childBottle.getLastOperatorId());
            workloadTxPO.setUserName(childBottle.getLastOperatorName());
            workloadTxPO.setUserRole(Const.TISSUE_TASK_USER_ROLE_OPERATOR);
            workloadTxPO.setBizType(workloadTransactionBizType);
            workloadTxPO.setTableName(TissueTask.TB_NAME);
            workloadTxPO.setRecordId(task.getId());
            workloadTxPO.setVariety(childBottle.getVariety());
            workloadTxPO.setVarietyCode(childBottle.getVarietyCode());
            workloadTxPO.setGeneration(childBottle.getGeneration());
            workloadTxPO.setBottles(0);
            workloadTxPO.setPlants(0);
            workloadTxPO.setDescription(logTaskBizType + " - " + dictionaryCache.getName("workload_tx_biz_type", workloadTransactionBizType));
            workloadTxPO.setOperatorId(currentUser.getId());
            workloadTxPO.setOperatorName(currentUser.getName());
            workloadTxPO.setOperateTime(now);
            return workloadTxPO;
          });
        // 生产子瓶/淘汰要记录正数
        workloadTransactionPO.setBottles(workloadTransactionPO.getBottles() + 1);
        workloadTransactionPO.setPlants(workloadTransactionPO.getPlants() + childBottle.getPlantCount());
        // 工作量流水-质检员
        workloadTransactionPO = workloadTransactionMap.computeIfAbsent(workloadTransactionBizType, (k) -> new HashMap<>())
          .computeIfAbsent(Const.TISSUE_TASK_USER_ROLE_INSPECTOR, (k) -> new HashMap<>())
          .computeIfAbsent(childBottle.getLastInspectorId(), (k) -> new HashMap<>())
          .computeIfAbsent(childBottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(childBottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(childBottle.getGeneration(), (k) -> {
            WorkloadTransactionPO workloadTxPO = new WorkloadTransactionPO();
            workloadTxPO.setId(Id.g());
            workloadTxPO.setUserId(childBottle.getLastInspectorId());
            workloadTxPO.setUserName(childBottle.getLastInspectorName());
            workloadTxPO.setUserRole(Const.TISSUE_TASK_USER_ROLE_INSPECTOR);
            workloadTxPO.setBizType(workloadTransactionBizType);
            workloadTxPO.setTableName(TissueTask.TB_NAME);
            workloadTxPO.setRecordId(task.getId());
            workloadTxPO.setVariety(childBottle.getVariety());
            workloadTxPO.setVarietyCode(childBottle.getVarietyCode());
            workloadTxPO.setGeneration(childBottle.getGeneration());
            workloadTxPO.setBottles(0);
            workloadTxPO.setPlants(0);
            workloadTxPO.setDescription(logTaskBizType + " - " + dictionaryCache.getName("workload_tx_biz_type", workloadTransactionBizType));
            workloadTxPO.setOperatorId(currentUser.getId());
            workloadTxPO.setOperatorName(currentUser.getName());
            workloadTxPO.setOperateTime(now);
            return workloadTxPO;
          });
        // 生产子瓶/淘汰要记录正数
        workloadTransactionPO.setBottles(workloadTransactionPO.getBottles() + 1);
        workloadTransactionPO.setPlants(workloadTransactionPO.getPlants() + childBottle.getPlantCount());
        // 仓储流水
        var inventoryTransactionPO = inventoryTransactionMap.computeIfAbsent(Const.STORAGE_IN_OUT_BOTTLE_TYPE_CHILD_IN, k -> new HashMap<>())
          .computeIfAbsent(childBottle.getGeneration(), (k) -> {
            InventoryTransactionPO transactionPO = new InventoryTransactionPO();
            transactionPO.setId(Id.g());
            transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_IN);
            transactionPO.setTableName(StorageInOut.TB_NAME);
            transactionPO.setRecordId(po.getId());
            transactionPO.setVariety(childBottle.getVariety());
            transactionPO.setVarietyCode(childBottle.getVarietyCode());
            transactionPO.setGeneration(childBottle.getGeneration());
            transactionPO.setBottles(0);
            transactionPO.setPlants(0);
            transactionPO.setDescription(logStorageBizType + " - " + dictionaryCache.getName("storage_in_out_bottle_type", Const.STORAGE_IN_OUT_BOTTLE_TYPE_CHILD_IN));
            transactionPO.setOperatorId(currentUser.getId());
            transactionPO.setOperatorName(currentUser.getName());
            transactionPO.setOperateTime(now);
            return transactionPO;
          });
        inventoryTransactionPO.setBottles(inventoryTransactionPO.getBottles() + 1);
        inventoryTransactionPO.setPlants(inventoryTransactionPO.getPlants() + bottle.getPlantCountActual());
      }
      // 组培瓶操作日志(任务-分配)
      if (bottle.getAllocateTime() != 0L) {
        BottleLogPO logForTask = new BottleLogPO();
        logForTask.setId(Id.g());
        logForTask.setIdBottle(bottle.getIdBottle());
        logForTask.setOppTime(bottle.getAllocateTime());
        logForTask.setTableName(TissueTask.TB_NAME);
        logForTask.setRecordId(task.getId());
        logForTask.setBizType(logTaskBizType + " - 分配作业组");
        logForTask.setDescription(StringUtil.EMPTY);
        logForTask.setInspectorId(bottle.getInspectorId());
        logForTask.setInspectorName(bottle.getInspectorName());
        logForTask.setOperatorId(bottle.getOperatorId());
        logForTask.setOperatorName(bottle.getOperatorName());
        logForTask.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_ASSIGNED);
        logForTask.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
        logForTask.setStorageReason(task.getTaskType());
        logForTask.setDiscarded(Const.BOOLEAN_0);
        logForTask.setDiscardReason(StringUtil.EMPTY);
        bottleLogs.add(logForTask);
      }
      if (bottle.getFinishTime() != 0L) {
        // 组培瓶操作日志(任务-完成)
        BottleLogPO logForTask = new BottleLogPO();
        logForTask.setId(Id.g());
        logForTask.setIdBottle(bottle.getIdBottle());
        logForTask.setOppTime(bottle.getFinishTime());
        logForTask.setTableName(TissueTask.TB_NAME);
        logForTask.setRecordId(task.getId());
        logForTask.setBizType(logTaskBizType + (bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? " - 作业淘汰" : " - 完成作业"));
        logForTask.setDescription(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? "子瓶淘汰" : StringUtil.EMPTY);
        logForTask.setInspectorId(bottle.getInspectorId());
        logForTask.setInspectorName(bottle.getInspectorName());
        logForTask.setOperatorId(bottle.getOperatorId());
        logForTask.setOperatorName(bottle.getOperatorName());
        logForTask.setOperateStatus(bottle.getOperateStatus());
        logForTask.setStorageStatus(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? Const.BOTTLE_STORAGE_STATUS_OUT : Const.BOTTLE_STORAGE_STATUS_IN);
        logForTask.setStorageReason(task.getTaskType());
        logForTask.setDiscarded(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? Const.BOOLEAN_1 : Const.BOOLEAN_0);
        logForTask.setDiscardReason(bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_DISCARD) ? bottle.getDiscardReason() : StringUtil.EMPTY);
        bottleLogs.add(logForTask);
      }

      var inOutBottleType = bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT) && bottle.getParentKeep().equals(Const.BOOLEAN_1)
        ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_KEEP_IN
        : bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT) && bottle.getIdOppGroup().isBlank()
        ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN
        : bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_CHILD) && bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_FINISHED)
        ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_CHILD_IN
        : StringUtil.EMPTY;
      if (!inOutBottleType.isBlank()) {
        // 组培瓶操作日志(入库)
        BottleLogPO logForTask = new BottleLogPO();
        logForTask.setId(Id.g());
        logForTask.setIdBottle(bottle.getIdBottle());
        logForTask.setOppTime(now);
        logForTask.setTableName(StorageInOut.TB_NAME);
        logForTask.setRecordId(po.getId());
        logForTask.setBizType(logStorageBizType + " - " + dictionaryCache.getName("storage_in_out_bottle_type", inOutBottleType));
        logForTask.setDescription(StringUtil.EMPTY);
        logForTask.setInspectorId(StringUtil.EMPTY);
        logForTask.setInspectorName(StringUtil.EMPTY);
        logForTask.setOperatorId(currentUser.getId());
        logForTask.setOperatorName(currentUser.getName());
        logForTask.setOperateStatus(bottle.getOperateStatus());
        logForTask.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_IN);
        logForTask.setStorageReason(task.getTaskType());
        logForTask.setDiscarded(Const.BOOLEAN_0);
        logForTask.setDiscardReason(StringUtil.EMPTY);
        bottleLogs.add(logForTask);

        if (inOutBottleType.equals(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_KEEP_IN)) {
          // 组织子瓶工作量流水
          var workloadTransactionBizType = Const.WORKLOAD_TX_BIZ_TYPE_PARENT_KEEP;
          // 工作量流水-操作员
          var workloadTransactionPO = workloadTransactionMap.computeIfAbsent(workloadTransactionBizType, (k) -> new HashMap<>())
            .computeIfAbsent(Const.TISSUE_TASK_USER_ROLE_OPERATOR, (k) -> new HashMap<>())
            .computeIfAbsent(bottle.getOperatorId(), (k) -> new HashMap<>())
            .computeIfAbsent(task.getVariety(), (k) -> new HashMap<>())
            .computeIfAbsent(task.getVarietyCode(), (k) -> new HashMap<>())
            .computeIfAbsent(bottle.getGeneration(), (k) -> {
              WorkloadTransactionPO workloadTxPO = new WorkloadTransactionPO();
              workloadTxPO.setId(Id.g());
              workloadTxPO.setUserId(bottle.getOperatorId());
              workloadTxPO.setUserName(bottle.getOperatorName());
              workloadTxPO.setUserRole(Const.TISSUE_TASK_USER_ROLE_OPERATOR);
              workloadTxPO.setBizType(workloadTransactionBizType);
              workloadTxPO.setTableName(TissueTask.TB_NAME);
              workloadTxPO.setRecordId(task.getId());
              workloadTxPO.setVariety(task.getVariety());
              workloadTxPO.setVarietyCode(task.getVarietyCode());
              workloadTxPO.setGeneration(bottle.getGeneration());
              workloadTxPO.setBottles(0);
              workloadTxPO.setPlants(0);
              workloadTxPO.setDescription(logTaskBizType + " - " + dictionaryCache.getName("workload_tx_biz_type", workloadTransactionBizType));
              workloadTxPO.setOperatorId(currentUser.getId());
              workloadTxPO.setOperatorName(currentUser.getName());
              workloadTxPO.setOperateTime(now);
              return workloadTxPO;
            });
          // 母瓶留种要记录正数
          workloadTransactionPO.setBottles(workloadTransactionPO.getBottles() + 1);
          workloadTransactionPO.setPlants(workloadTransactionPO.getPlants() + bottle.getParentKeepPlantCount());
          // 工作量流水-质检员
          workloadTransactionPO = workloadTransactionMap.computeIfAbsent(workloadTransactionBizType, (k) -> new HashMap<>())
            .computeIfAbsent(Const.TISSUE_TASK_USER_ROLE_INSPECTOR, (k) -> new HashMap<>())
            .computeIfAbsent(bottle.getInspectorId(), (k) -> new HashMap<>())
            .computeIfAbsent(task.getVariety(), (k) -> new HashMap<>())
            .computeIfAbsent(task.getVarietyCode(), (k) -> new HashMap<>())
            .computeIfAbsent(bottle.getGeneration(), (k) -> {
              WorkloadTransactionPO workloadTxPO = new WorkloadTransactionPO();
              workloadTxPO.setId(Id.g());
              workloadTxPO.setUserId(bottle.getInspectorId());
              workloadTxPO.setUserName(bottle.getInspectorName());
              workloadTxPO.setUserRole(Const.TISSUE_TASK_USER_ROLE_INSPECTOR);
              workloadTxPO.setBizType(workloadTransactionBizType);
              workloadTxPO.setTableName(TissueTask.TB_NAME);
              workloadTxPO.setRecordId(task.getId());
              workloadTxPO.setVariety(task.getVariety());
              workloadTxPO.setVarietyCode(task.getVarietyCode());
              workloadTxPO.setGeneration(bottle.getGeneration());
              workloadTxPO.setBottles(0);
              workloadTxPO.setPlants(0);
              workloadTxPO.setDescription(logTaskBizType + " - " + dictionaryCache.getName("workload_tx_biz_type", workloadTransactionBizType));
              workloadTxPO.setOperatorId(currentUser.getId());
              workloadTxPO.setOperatorName(currentUser.getName());
              workloadTxPO.setOperateTime(now);
              return workloadTxPO;
            });
          // 母瓶留种要记录正数
          workloadTransactionPO.setBottles(workloadTransactionPO.getBottles() + 1);
          workloadTransactionPO.setPlants(workloadTransactionPO.getPlants() + bottle.getParentKeepPlantCount());
          // 仓储流水
          var inventoryTransactionPO = inventoryTransactionMap.computeIfAbsent(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_KEEP_IN, k -> new HashMap<>())
            .computeIfAbsent(bottle.getGeneration(), (k) -> {
              InventoryTransactionPO transactionPO = new InventoryTransactionPO();
              transactionPO.setId(Id.g());
              transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_IN);
              transactionPO.setTableName(StorageInOut.TB_NAME);
              transactionPO.setRecordId(po.getId());
              transactionPO.setVariety(task.getVariety());
              transactionPO.setVarietyCode(task.getVarietyCode());
              transactionPO.setGeneration(bottle.getGeneration());
              transactionPO.setBottles(0);
              transactionPO.setPlants(0);
              transactionPO.setDescription(logStorageBizType + " - " + dictionaryCache.getName("storage_in_out_bottle_type", Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_KEEP_IN));
              transactionPO.setOperatorId(currentUser.getId());
              transactionPO.setOperatorName(currentUser.getName());
              transactionPO.setOperateTime(now);
              return transactionPO;
            });
          inventoryTransactionPO.setBottles(inventoryTransactionPO.getBottles() + 1);
          inventoryTransactionPO.setPlants(inventoryTransactionPO.getPlants() + bottle.getParentKeepPlantCount());
        } else if (inOutBottleType.equals(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN)) {
          // 仓储流水
          var inventoryTransactionPO = inventoryTransactionMap.computeIfAbsent(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN, k -> new HashMap<>())
            .computeIfAbsent(bottle.getGeneration(), (k) -> {
              InventoryTransactionPO transactionPO = new InventoryTransactionPO();
              transactionPO.setId(Id.g());
              transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_IN);
              transactionPO.setTableName(StorageInOut.TB_NAME);
              transactionPO.setRecordId(po.getId());
              transactionPO.setVariety(task.getVariety());
              transactionPO.setVarietyCode(task.getVarietyCode());
              transactionPO.setGeneration(bottle.getGeneration());
              transactionPO.setBottles(0);
              transactionPO.setPlants(0);
              transactionPO.setDescription(logStorageBizType + " - " + dictionaryCache.getName("storage_in_out_bottle_type", Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN));
              transactionPO.setOperatorId(currentUser.getId());
              transactionPO.setOperatorName(currentUser.getName());
              transactionPO.setOperateTime(now);
              return transactionPO;
            });
          inventoryTransactionPO.setBottles(inventoryTransactionPO.getBottles() + 1);
          inventoryTransactionPO.setPlants(inventoryTransactionPO.getPlants() + bottle.getPlantCountActual());
        }
      }
    }

    // 组织子瓶代际关系。
    List<BottleRelationPO> childBottleRelations = new ArrayList<>();
    var parentBottleRelationMap = new HashMap<String, List<BottleRelationDTO>>();
    // 建立自身代际关系
    if (!childBottles.isEmpty()) {
      for (var bottle : childBottles) {
        childBottleRelations.add(new BottleRelationPO(
          Id.g(),
          bottle.getId(),
          bottle.getId(),
          0
        ));
      }
    }
    if (!childBottles.isEmpty() && !parentIds.isEmpty()) {
      var parentIdList = parentIds.stream().toList();
      bottleRelationDAO.findAllByChildIdIn(parentIdList).stream().map(BottleRelationDTO::new).forEach(r -> parentBottleRelationMap.computeIfAbsent(r.getChildId(), k -> new ArrayList<>()).add(r));
      for (var bottle : childBottles) {
        // 复制母瓶代际关系（深度+1）
        parentBottleRelationMap.getOrDefault(bottle.getIdParent(), new ArrayList<>()).forEach(r -> childBottleRelations.add(new BottleRelationPO(
          Id.g(),
          bottle.getId(),
          r.getParentId(),
          r.getDepth() + 1
        )));
      }
    }

    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_FINISHED);
    po.setOppReason(StringUtil.EMPTY);
    po.setBottlesActual(po.getBottlesApplied());
    po.setPlantsActual(po.getPlantsApplied());
    po.setBottlesKeepInActual(po.getBottlesKeepInApplied());
    po.setPlantsKeepInActual(po.getPlantsKeepInApplied());
    // 组织入库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);

    // 组织工作量流水
    var workloadTransactionList = new ArrayList<WorkloadTransactionPO>();
    var bizTypes = workloadTransactionMap.keySet();
    for (var bizType : bizTypes) {
      var roles = workloadTransactionMap.get(bizType).keySet();
      for (var role : roles) {
        var userIds = workloadTransactionMap.get(bizType).get(role).keySet();
        for (var userId : userIds) {
          var varieties = workloadTransactionMap.get(bizType).get(role).get(userId).keySet();
          for (var variety : varieties) {
            var varietyCodes = workloadTransactionMap.get(bizType).get(role).get(userId).get(variety).keySet();
            for (var varietyCode : varietyCodes) {
              var generations = workloadTransactionMap.get(bizType).get(role).get(userId).get(variety).get(varietyCode).keySet();
              for (var generation : generations) {
                var stat = workloadTransactionMap.get(bizType).get(role).get(userId).get(variety).get(varietyCode).get(generation);
                workloadTransactionList.add(stat);
              }
            }
          }
        }
      }
    }

    // 组织库存流水
    var inventoryTransactionList = new ArrayList<InventoryTransactionPO>();
    var bizTypes1 = inventoryTransactionMap.keySet();
    for (var bizType : bizTypes1) {
      var generations = inventoryTransactionMap.get(bizType).keySet();
      for (var generation : generations) {
        var stat = inventoryTransactionMap.get(bizType).get(generation);
        inventoryTransactionList.add(stat);
      }
    }

    // 更新母瓶信息
    String updateParentBottleSql = """
      UPDATE bottle b
        INNER JOIN tissue_task_bottle tb ON b.id = tb.id_bottle
        INNER JOIN tissue_task t ON tb.id_task = t.id
      SET
        b.plant_count = (CASE WHEN tb.parent_keep = '1' THEN tb.parent_keep_plant_count ELSE b.plant_count END),
        b.current_id_task = '',
        b.current_id_opp_group = '',
        b.current_culture_stage = '',
        b.current_role_in_task = 'none',
        b.current_parent_keep = '0',
        b.current_parent_keep_plant_count = 0,
        b.current_operate_status = 'none',
        b.current_inspector_id = '',
        b.current_inspector_name = '',
        b.current_operator_id = '',
        b.current_operator_name = '',
        b.last_id_task = tb.id_task,
        b.last_id_opp_group = tb.id_opp_group,
        b.last_culture_stage = t.task_type,
        b.last_role_in_task = tb.role_in_task,
        b.last_parent_keep = tb.parent_keep,
        b.last_parent_keep_plant_count = (CASE WHEN tb.parent_keep = '1' THEN tb.parent_keep_plant_count ELSE 0 END),
        b.last_operate_status = tb.operate_status,
        b.last_inspector_id = tb.inspector_id,
        b.last_inspector_name = tb.inspector_name,
        b.last_operator_id = tb.operator_id,
        b.last_operator_name = tb.operator_name,
        b.current_storage_status = 'in',
        b.last_received_user_id = :userId,
        b.last_received_user_name = :nameOfUser,
        b.last_received_time = :now,
        b.last_received_reason = t.task_type
      WHERE
        tb.id_task = :idTask
        AND (tb.parent_keep = '1' OR tb.id_opp_group IS NULL OR tb.id_opp_group = '')
        AND tb.role_in_task = 'parent'
      """;
    Query nativeQuery = entityManager.createNativeQuery(updateParentBottleSql);
    nativeQuery.setParameter("idTask", task.getId());
    nativeQuery.setParameter("userId", currentUser.getId());
    nativeQuery.setParameter("nameOfUser", currentUser.getName());
    nativeQuery.setParameter("now", now);
    nativeQuery.executeUpdate();
    entityManager.flush();
    entityManager.clear();

    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottlesInList.size(); i++) {
      entityManager.persist(bottlesInList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
    for (int i = 0; i < childBottles.size(); i++) {
      entityManager.persist(childBottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
    for (int i = 0; i < childBottleRelations.size(); i++) {
      entityManager.persist(childBottleRelations.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
    // 插入培养瓶日志
    for (int i = 0; i < bottleLogs.size(); i++) {
      entityManager.persist(bottleLogs.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    for (int i = 0; i < workloadTransactionList.size(); i++) {
      entityManager.persist(workloadTransactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    for (int i = 0; i < inventoryTransactionList.size(); i++) {
      entityManager.persist(inventoryTransactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  public QueryResultPaged<StorageInOut> queryInspectOutByCondition(QueryCondition<QueryConditionStorageOutTaskView> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();

    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.DESC, StorageInOut.BP_NAME_APPLICANTTIME)
    );

    Specification<StorageInOutPO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();

      if (conditionModel != null) {
        if (conditionModel.getKeyword() != null && !conditionModel.getKeyword().isBlank()) {
          String likeVal = "%" + conditionModel.getKeyword().trim() + "%";
          Predicate p1 = cb.like(root.get(StorageInOut.BP_NAME_APPLICANTNAME), likeVal);
          Predicate p2 = cb.like(root.get(StorageInOut.BP_NAME_OPERATORNAME), likeVal);
          predicateList.add(cb.or(p1, p2));
        }
        if (conditionModel.getStatus() != null && !conditionModel.getStatus().isBlank()) {
          predicateList.add(cb.equal(root.get(StorageInOut.BP_NAME_STATUS), conditionModel.getStatus()));
        }
        if (conditionModel.getInOutType() != null && !conditionModel.getInOutType().isBlank()) {
          predicateList.add(cb.equal(root.get(StorageInOut.BP_NAME_INOUT), conditionModel.getInOutType()));
        }
        if (conditionModel.getReason() != null && !conditionModel.getReason().isBlank()) {
          predicateList.add(cb.equal(root.get(StorageInOut.BP_NAME_REASON), conditionModel.getReason()));
        }
        ModelDateRange applicantTime = conditionModel.getApplicantTime();
        if (applicantTime != null) {
          if (applicantTime.getStart() != null && applicantTime.getStart() >= 1) {
            predicateList.add(cb.greaterThanOrEqualTo(root.get(StorageInOut.BP_NAME_APPLICANTTIME), applicantTime.getStart()));
          }
          if (applicantTime.getEnd() != null && applicantTime.getEnd() >= 1) {
            predicateList.add(cb.lessThanOrEqualTo(root.get(StorageInOut.BP_NAME_APPLICANTTIME), applicantTime.getEnd()));
          }
        }
        ModelDateRange operatorTime = conditionModel.getOperatorTime();
        if (operatorTime != null) {
          if (operatorTime.getStart() != null && operatorTime.getStart() >= 1) {
            predicateList.add(cb.greaterThanOrEqualTo(root.get(StorageInOut.BP_NAME_OPERATORTIME), operatorTime.getStart()));
          }
          if (operatorTime.getEnd() != null && operatorTime.getEnd() >= 1) {
            predicateList.add(cb.lessThanOrEqualTo(root.get(StorageInOut.BP_NAME_OPERATORTIME), operatorTime.getEnd()));
          }
        }
      }

      return cb.and(predicateList.toArray(new Predicate[0]));
    };

    Page<StorageInOut> dtoPage = storageInOutDAO.findAll(spec, pageable).map(StorageInOutDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());

    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }

  @Transactional
  public void createInspectOut(@NonNull User currentUser) {

    // 组织出库单
    var now = System.currentTimeMillis();

    StorageInOutPO po = new StorageInOutPO();
    po.setId(Id.g());
    po.setInOut(Const.STORAGE_IN_OUT_TYPE_OUT);
    po.setBottlesApplied(0);
    po.setPlantsApplied(0);
    po.setBottlesKeepInApplied(0);
    po.setPlantsKeepInApplied(0);
    po.setBottlesActual(0);
    po.setPlantsActual(0);
    po.setBottlesKeepInActual(0);
    po.setPlantsKeepInActual(0);
    po.setBottlesDiscard(0);
    po.setPlantsDiscard(0);
    po.setBottlesVoided(0);
    po.setPlantsVoided(0);
    po.setIgnoreStats(Const.BOOLEAN_0);
    po.setReason(Const.STORAGE_REASON_INSPECTION_DISCARD);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    po.setIdTask(StringUtil.EMPTY);
    po.setIdInspection(StringUtil.EMPTY);
    po.setApplicantId(currentUser.getId());
    po.setApplicantName(currentUser.getName());
    po.setApplicantTime(now);
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    this.storageInOutDAO.save(po);

    // 组织入库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);

    this.storageInOutLogDAO.save(log);
  }

  @Transactional
  public void outProcessForInspect(ModelStorageOutInspectProcessDetail model, User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED))
      throw new ErrorMessageException("当前出库业务已被处理，无法继续执行。");

    var bottleIdList = new ArrayList<String>();
    var discardList = model.getDiscardList();
    if (discardList != null) {
      for (var m : discardList) {
        bottleIdList.add(m.getIdBottle());
        if (m.getPlantCountActual() == null || m.getPlantCountActual() < 1) {
          throw new ErrorMessageException("出库培养瓶存疑，" + m.getIdBottle() + "的株数错误，必须大于零。");
        }
      }
      long bottleCount = bottleDAO.countBottles2(Const.BOTTLE_STORAGE_STATUS_IN, bottleIdList);
      if (bottleCount != discardList.size())
        throw new ErrorMessageException("出库培养瓶存疑，请刷新可用瓶后再次执行出库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 查询培养瓶信息
    var bottleMap = new HashMap<String, Bottle>();
    for (var b : this.bottleDAO.findAllByIdIn(bottleIdList)) {
      bottleMap.put(b.getId(), b);
    }

    // 计算瓶数/株数
    int plantActual = 0;
    int bottlesActual = 0;
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (discardList != null) {
      for (var discard : discardList) {
        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(discard.getIdBottle());
        b.setVariety(bottleMap.get(discard.getIdBottle()).getVariety());
        b.setVarietyCode(bottleMap.get(discard.getIdBottle()).getVarietyCode());
        b.setExplantCollectionBatch(bottleMap.get(discard.getIdBottle()).getExplantCollectionBatch());
        b.setGeneration(bottleMap.get(discard.getIdBottle()).getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_INTERCEPT_OUT);
        b.setPlantCountApplied(bottleMap.get(discard.getIdBottle()).getPlantCount());
        b.setPlantCountActual(bottleMap.get(discard.getIdBottle()).getPlantCount());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DISCARD);
        b.setDiscardReason(discard.getReason());
        bottles.add(b);

        plantActual += bottleMap.get(discard.getIdBottle()).getPlantCount();
        bottlesActual++;
      }
    }

    // 更新出库单数量
    po.setBottlesActual(-bottlesActual);
    po.setPlantsActual(-plantActual);
    po.setBottlesApplied(-bottlesActual);
    po.setPlantsApplied(-plantActual);
    po.setBottlesDiscard(-bottlesActual);
    po.setPlantsDiscard(-plantActual);

    var now = System.currentTimeMillis();
    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);

    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }


  @Transactional
  public void outFinishForInspect(ModelStorageOutInspectProcessDetail model, User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED))
      throw new ErrorMessageException("当前出库业务已被处理，无法继续执行。");

    var bottleIdList = new ArrayList<String>();
    var discardList = model.getDiscardList();
    if (discardList != null) {
      for (var m : discardList) {
        bottleIdList.add(m.getIdBottle());
      }
      long bottleCount = bottleDAO.countBottles2(Const.BOTTLE_STORAGE_STATUS_IN, bottleIdList);
      if (bottleCount != discardList.size())
        throw new ErrorMessageException("出库培养瓶存疑，请刷新可用瓶后再次执行出库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 查询培养瓶信息
    var bottleMap = new HashMap<String, BottlePO>();
    for (var b : this.bottleDAO.findAllByIdIn(bottleIdList)) {
      bottleMap.put(b.getId(), b);
    }

    var now = System.currentTimeMillis();

    // 计算瓶数/株数/培养瓶操作日志
    int plantActual = 0;
    int bottlesActual = 0;
    List<BottleLogPO> bottleLogs = new ArrayList<>();
    var transitionMap = new HashMap<String, Map<String, Map<Integer, InventoryTransactionPO>>>();
    var workloadTransactionMap = new HashMap<String, HashMap<String, HashMap<String, Map<String, Map<Integer, WorkloadTransactionPO>>>>>();
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (discardList != null) {
      for (var discard : discardList) {
        var bottle = bottleMap.get(discard.getIdBottle());
        bottle.setCurrentStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
        bottle.setLastIssuedReason(Const.STORAGE_REASON_INSPECTION_DISCARD);
        bottle.setLastIssuedTime(now);
        bottle.setLastIssuedUserId(currentUser.getId());
        bottle.setLastIssuedUserName(currentUser.getName());
        bottle.setDiscarded(Const.BOOLEAN_1);
        bottle.setDiscardReason(discard.getReason());
        bottle.setDiscardTime(now);
        bottle.setDiscardUserId(currentUser.getId());
        bottle.setDiscardUserName(currentUser.getName());


        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(discard.getIdBottle());
        b.setVariety(bottle.getVariety());
        b.setVarietyCode(bottle.getVarietyCode());
        b.setExplantCollectionBatch(bottle.getExplantCollectionBatch());
        b.setGeneration(bottle.getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_INTERCEPT_OUT);
        b.setPlantCountApplied(bottle.getPlantCount());
        b.setPlantCountActual(bottle.getPlantCount());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DISCARD);
        b.setDiscardReason(discard.getReason());
        bottles.add(b);

        plantActual += bottle.getPlantCount();
        bottlesActual++;

        var stat = transitionMap.computeIfAbsent(bottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), (k) -> {
            InventoryTransactionPO transactionPO = new InventoryTransactionPO();
            transactionPO.setId(Id.g());
            transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_OUT);
            transactionPO.setTableName(StorageInOut.TB_NAME);
            transactionPO.setRecordId(po.getId());
            transactionPO.setVariety(bottle.getVariety());
            transactionPO.setVarietyCode(bottle.getVarietyCode());
            transactionPO.setGeneration(bottle.getGeneration());
            transactionPO.setBottles(0);
            transactionPO.setPlants(0);
            transactionPO.setDescription("质检淘汰出库");
            transactionPO.setOperatorId(currentUser.getId());
            transactionPO.setOperatorName(currentUser.getName());
            transactionPO.setOperateTime(now);
            return transactionPO;
          });
        stat.setBottles(stat.getBottles() - 1);
        stat.setPlants(stat.getPlants() - bottle.getPlantCount());

        // 工作量流水-操作员
        var workloadTransactionPO = workloadTransactionMap.computeIfAbsent(Const.TISSUE_TASK_USER_ROLE_OPERATOR, (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getLastOperatorId(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), (k) -> {
            WorkloadTransactionPO workloadTxPO = new WorkloadTransactionPO();
            workloadTxPO.setId(Id.g());
            workloadTxPO.setUserId(bottle.getLastOperatorId());
            workloadTxPO.setUserName(bottle.getLastOperatorName());
            workloadTxPO.setUserRole(Const.TISSUE_TASK_USER_ROLE_OPERATOR);
            workloadTxPO.setBizType(Const.WORKLOAD_TX_BIZ_TYPE_DISCARD);
            workloadTxPO.setTableName(StorageInOut.TB_NAME);
            workloadTxPO.setRecordId(po.getId());
            workloadTxPO.setVariety(bottle.getVariety());
            workloadTxPO.setVarietyCode(bottle.getVarietyCode());
            workloadTxPO.setGeneration(bottle.getGeneration());
            workloadTxPO.setBottles(0);
            workloadTxPO.setPlants(0);
            workloadTxPO.setDescription("质检淘汰出库");
            workloadTxPO.setOperatorId(currentUser.getId());
            workloadTxPO.setOperatorName(currentUser.getName());
            workloadTxPO.setOperateTime(now);
            return workloadTxPO;
          });
        // 淘汰要记录正数
        workloadTransactionPO.setBottles(workloadTransactionPO.getBottles() + 1);
        workloadTransactionPO.setPlants(workloadTransactionPO.getPlants() + bottle.getPlantCount());
        // 工作量流水-质检员
        workloadTransactionPO = workloadTransactionMap.computeIfAbsent(Const.TISSUE_TASK_USER_ROLE_INSPECTOR, (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getLastInspectorId(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), (k) -> {
            WorkloadTransactionPO workloadTxPO = new WorkloadTransactionPO();
            workloadTxPO.setId(Id.g());
            workloadTxPO.setUserId(bottle.getLastInspectorId());
            workloadTxPO.setUserName(bottle.getLastInspectorName());
            workloadTxPO.setUserRole(Const.TISSUE_TASK_USER_ROLE_INSPECTOR);
            workloadTxPO.setBizType(Const.WORKLOAD_TX_BIZ_TYPE_DISCARD);
            workloadTxPO.setTableName(StorageInOut.TB_NAME);
            workloadTxPO.setRecordId(po.getId());
            workloadTxPO.setVariety(bottle.getVariety());
            workloadTxPO.setVarietyCode(bottle.getVarietyCode());
            workloadTxPO.setGeneration(bottle.getGeneration());
            workloadTxPO.setBottles(0);
            workloadTxPO.setPlants(0);
            workloadTxPO.setDescription("质检淘汰出库");
            workloadTxPO.setOperatorId(currentUser.getId());
            workloadTxPO.setOperatorName(currentUser.getName());
            workloadTxPO.setOperateTime(now);
            return workloadTxPO;
          });
        // 淘汰要记录正数
        workloadTransactionPO.setBottles(workloadTransactionPO.getBottles() + 1);
        workloadTransactionPO.setPlants(workloadTransactionPO.getPlants() + bottle.getPlantCount());


        // 组培瓶操作日志(质检)
        BottleLogPO logForTask = new BottleLogPO();
        logForTask.setId(Id.g());
        logForTask.setIdBottle(discard.getIdBottle());
        logForTask.setOppTime(now);
        logForTask.setTableName(StorageInOut.TB_NAME);
        logForTask.setRecordId(po.getId());
        logForTask.setBizType("质检淘汰出库");
        logForTask.setDescription(StringUtil.EMPTY);
        logForTask.setInspectorId(currentUser.getId());
        logForTask.setInspectorName(currentUser.getName());
        logForTask.setOperatorId(StringUtil.EMPTY);
        logForTask.setOperatorName(StringUtil.EMPTY);
        logForTask.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_NONE);
        logForTask.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
        logForTask.setStorageReason(Const.STORAGE_REASON_INSPECTION_DISCARD);
        logForTask.setDiscarded(Const.BOOLEAN_1);
        logForTask.setDiscardReason(discard.getReason());
        bottleLogs.add(logForTask);
      }
    }

    if (bottlesActual < 1 || plantActual < 1) {
      throw new ErrorMessageException("无有效作业，无法完成质检出库。");
    }

    // 组织出库单仓储流水
    var transactionList = new ArrayList<InventoryTransactionPO>();
    var varieties = transitionMap.keySet();
    for (var variety : varieties) {
      var varietyCodes = transitionMap.get(variety).keySet();
      for (var varietyCode : varietyCodes) {
        var generations = transitionMap.get(variety).get(varietyCode).keySet();
        for (var generation : generations) {
          var stat = transitionMap.get(variety).get(varietyCode).get(generation);
          transactionList.add(stat);
        }
      }
    }

    // 组织工作量流水
    var workloadTransactionList = new ArrayList<WorkloadTransactionPO>();
    var roles = workloadTransactionMap.keySet();
    for (var role : roles) {
      var userIds = workloadTransactionMap.get(role).keySet();
      for (var userId : userIds) {
        varieties = workloadTransactionMap.get(role).get(userId).keySet();
        for (var variety : varieties) {
          var varietyCodes = workloadTransactionMap.get(role).get(userId).get(variety).keySet();
          for (var varietyCode : varietyCodes) {
            var generations = workloadTransactionMap.get(role).get(userId).get(variety).get(varietyCode).keySet();
            for (var generation : generations) {
              var stat = workloadTransactionMap.get(role).get(userId).get(variety).get(varietyCode).get(generation);
              workloadTransactionList.add(stat);
            }
          }
        }
      }
    }

    // 更新出库单数量
    po.setBottlesActual(-bottlesActual);
    po.setPlantsActual(-plantActual);
    po.setBottlesApplied(-bottlesActual);
    po.setPlantsApplied(-plantActual);
    po.setBottlesDiscard(-bottlesActual);
    po.setPlantsDiscard(-plantActual);

    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_FINISHED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);


    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
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

    for (int i = 0; i < transactionList.size(); i++) {
      entityManager.persist(transactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    for (int i = 0; i < workloadTransactionList.size(); i++) {
      entityManager.persist(workloadTransactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void deleteForInspect(@NonNull String inOutId) {
    Assert.hasText(inOutId, "出库信息不能为空");

    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null || po.getStatus() == null || po.getId() == null) {
      throw new ErrorMessageException("无法获得出库信息");
    }
    var status = po.getStatus();
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED)) {
      throw new ErrorMessageException("无法删除此出库申请");
    }

    storageInOutDAO.delete(po);
    storageInOutBottleDAO.deleteAllByIdInOut(po.getId());
    storageInOutLogDAO.deleteAllByIdInOutIn(List.of(po.getId()));
  }

  @Transactional
  public void createInventorySubBottle(@NonNull User currentUser) {

    // 组织出库单
    var now = System.currentTimeMillis();

    StorageInOutPO po = new StorageInOutPO();
    po.setId(Id.g());
    po.setInOut(Const.STORAGE_IN_OUT_TYPE_OUT);
    po.setBottlesApplied(0);
    po.setPlantsApplied(0);
    po.setBottlesKeepInApplied(0);
    po.setPlantsKeepInApplied(0);
    po.setBottlesActual(0);
    po.setPlantsActual(0);
    po.setBottlesKeepInActual(0);
    po.setPlantsKeepInActual(0);
    po.setBottlesDiscard(0);
    po.setPlantsDiscard(0);
    po.setBottlesVoided(0);
    po.setPlantsVoided(0);
    po.setIgnoreStats(Const.BOOLEAN_0);
    po.setReason(Const.STORAGE_REASON_INVENTORY_SUB);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    po.setIdTask(StringUtil.EMPTY);
    po.setIdInspection(StringUtil.EMPTY);
    po.setApplicantId(currentUser.getId());
    po.setApplicantName(currentUser.getName());
    po.setApplicantTime(now);
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    this.storageInOutDAO.save(po);

    // 组织入库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);

    this.storageInOutLogDAO.save(log);
  }

  @Transactional
  public void outProcessForInventorySubBottle(ModelStorageOutInspectProcessDetail model, User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED))
      throw new ErrorMessageException("当前出库业务已被处理，无法继续执行。");

    var bottleIdList = new ArrayList<String>();
    var discardList = model.getDiscardList();
    if (discardList != null) {
      for (var m : discardList) {
        bottleIdList.add(m.getIdBottle());
        if (m.getPlantCountActual() == null || m.getPlantCountActual() < 1) {
          throw new ErrorMessageException("出库培养瓶存疑，" + m.getIdBottle() + "的株数错误，必须大于零。");
        }
      }
      long bottleCount = bottleDAO.countBottles2(Const.BOTTLE_STORAGE_STATUS_IN, bottleIdList);
      if (bottleCount != discardList.size())
        throw new ErrorMessageException("出库培养瓶存疑，请刷新可用瓶后再次执行出库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 查询培养瓶信息
    var bottleMap = new HashMap<String, Bottle>();
    for (var b : this.bottleDAO.findAllByIdIn(bottleIdList)) {
      bottleMap.put(b.getId(), b);
    }

    // 计算瓶数/株数
    int plantActual = 0;
    int bottlesActual = 0;
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (discardList != null) {
      for (var discard : discardList) {
        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(discard.getIdBottle());
        b.setVariety(bottleMap.get(discard.getIdBottle()).getVariety());
        b.setVarietyCode(bottleMap.get(discard.getIdBottle()).getVarietyCode());
        b.setExplantCollectionBatch(bottleMap.get(discard.getIdBottle()).getExplantCollectionBatch());
        b.setGeneration(bottleMap.get(discard.getIdBottle()).getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_INVENTORY_SUB);
        b.setPlantCountApplied(bottleMap.get(discard.getIdBottle()).getPlantCount());
        b.setPlantCountActual(bottleMap.get(discard.getIdBottle()).getPlantCount());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DONE);
        b.setDiscardReason(StringUtil.EMPTY);
        bottles.add(b);

        plantActual += bottleMap.get(discard.getIdBottle()).getPlantCount();
        bottlesActual++;
      }
    }

    // 更新出库单数量
    po.setBottlesActual(-bottlesActual);
    po.setPlantsActual(-plantActual);
    po.setBottlesApplied(-bottlesActual);
    po.setPlantsApplied(-plantActual);

    var now = System.currentTimeMillis();
    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);

    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }


  @Transactional
  public void outFinishForInventorySubBottle(ModelStorageOutInspectProcessDetail model, User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED))
      throw new ErrorMessageException("当前出库业务已被处理，无法继续执行。");

    var bottleIdList = new ArrayList<String>();
    var discardList = model.getDiscardList();
    if (discardList != null) {
      for (var m : discardList) {
        bottleIdList.add(m.getIdBottle());
      }
      long bottleCount = bottleDAO.countBottles2(Const.BOTTLE_STORAGE_STATUS_IN, bottleIdList);
      if (bottleCount != discardList.size())
        throw new ErrorMessageException("出库培养瓶存疑，请刷新可用瓶后再次执行出库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 查询培养瓶信息
    var bottleMap = new HashMap<String, BottlePO>();
    for (var b : this.bottleDAO.findAllByIdIn(bottleIdList)) {
      bottleMap.put(b.getId(), b);
    }

    var now = System.currentTimeMillis();

    // 计算瓶数/株数/培养瓶操作日志
    int plantActual = 0;
    int bottlesActual = 0;
    List<BottleLogPO> bottleLogs = new ArrayList<>();
    var transitionMap = new HashMap<String, Map<String, Map<Integer, InventoryTransactionPO>>>();
    var workloadTransactionMap = new HashMap<String, HashMap<String, HashMap<String, Map<String, Map<Integer, WorkloadTransactionPO>>>>>();
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (discardList != null) {
      for (var discard : discardList) {
        var bottle = bottleMap.get(discard.getIdBottle());
        bottle.setCurrentStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
        bottle.setLastIssuedReason(Const.STORAGE_REASON_INVENTORY_SUB);
        bottle.setLastIssuedTime(now);
        bottle.setLastIssuedUserId(currentUser.getId());
        bottle.setLastIssuedUserName(currentUser.getName());
        bottle.setDiscarded(Const.BOOLEAN_0);
        bottle.setDiscardReason(StringUtil.EMPTY);
        bottle.setDiscardTime(0L);
        bottle.setDiscardUserId(StringUtil.EMPTY);
        bottle.setDiscardUserName(StringUtil.EMPTY);


        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(discard.getIdBottle());
        b.setVariety(bottle.getVariety());
        b.setVarietyCode(bottle.getVarietyCode());
        b.setExplantCollectionBatch(bottle.getExplantCollectionBatch());
        b.setGeneration(bottle.getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_INVENTORY_SUB);
        b.setPlantCountApplied(bottle.getPlantCount());
        b.setPlantCountActual(bottle.getPlantCount());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DONE);
        b.setDiscardReason(StringUtil.EMPTY);
        bottles.add(b);

        plantActual += bottle.getPlantCount();
        bottlesActual++;

        // 库存流水
        var stat = transitionMap.computeIfAbsent(bottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), (k) -> {
            InventoryTransactionPO transactionPO = new InventoryTransactionPO();
            transactionPO.setId(Id.g());
            transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_OUT);
            transactionPO.setTableName(StorageInOut.TB_NAME);
            transactionPO.setRecordId(po.getId());
            transactionPO.setVariety(bottle.getVariety());
            transactionPO.setVarietyCode(bottle.getVarietyCode());
            transactionPO.setGeneration(bottle.getGeneration());
            transactionPO.setBottles(0);
            transactionPO.setPlants(0);
            transactionPO.setDescription("库存减瓶");
            transactionPO.setOperatorId(currentUser.getId());
            transactionPO.setOperatorName(currentUser.getName());
            transactionPO.setOperateTime(now);
            return transactionPO;
          });
        stat.setBottles(stat.getBottles() - 1);
        stat.setPlants(stat.getPlants() - bottle.getPlantCount());

        // 工作量流水-操作员
        var workloadTransactionPO = workloadTransactionMap.computeIfAbsent(Const.TISSUE_TASK_USER_ROLE_OPERATOR, (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getLastOperatorId(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), (k) -> {
            WorkloadTransactionPO workloadTxPO = new WorkloadTransactionPO();
            workloadTxPO.setId(Id.g());
            workloadTxPO.setUserId(bottle.getLastOperatorId());
            workloadTxPO.setUserName(bottle.getLastOperatorName());
            workloadTxPO.setUserRole(Const.TISSUE_TASK_USER_ROLE_OPERATOR);
            workloadTxPO.setBizType(Const.WORKLOAD_TX_BIZ_TYPE_INVENTORY_SUB);
            workloadTxPO.setTableName(StorageInOut.TB_NAME);
            workloadTxPO.setRecordId(po.getId());
            workloadTxPO.setVariety(bottle.getVariety());
            workloadTxPO.setVarietyCode(bottle.getVarietyCode());
            workloadTxPO.setGeneration(bottle.getGeneration());
            workloadTxPO.setBottles(0);
            workloadTxPO.setPlants(0);
            workloadTxPO.setDescription("库存减瓶");
            workloadTxPO.setOperatorId(currentUser.getId());
            workloadTxPO.setOperatorName(currentUser.getName());
            workloadTxPO.setOperateTime(now);
            return workloadTxPO;
          });
        workloadTransactionPO.setBottles(workloadTransactionPO.getBottles() - 1);
        workloadTransactionPO.setPlants(workloadTransactionPO.getPlants() - bottle.getPlantCount());
        // 工作量流水-质检员
        workloadTransactionPO = workloadTransactionMap.computeIfAbsent(Const.TISSUE_TASK_USER_ROLE_INSPECTOR, (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getLastInspectorId(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), (k) -> {
            WorkloadTransactionPO workloadTxPO = new WorkloadTransactionPO();
            workloadTxPO.setId(Id.g());
            workloadTxPO.setUserId(bottle.getLastInspectorId());
            workloadTxPO.setUserName(bottle.getLastInspectorName());
            workloadTxPO.setUserRole(Const.TISSUE_TASK_USER_ROLE_INSPECTOR);
            workloadTxPO.setBizType(Const.WORKLOAD_TX_BIZ_TYPE_INVENTORY_SUB);
            workloadTxPO.setTableName(StorageInOut.TB_NAME);
            workloadTxPO.setRecordId(po.getId());
            workloadTxPO.setVariety(bottle.getVariety());
            workloadTxPO.setVarietyCode(bottle.getVarietyCode());
            workloadTxPO.setGeneration(bottle.getGeneration());
            workloadTxPO.setBottles(0);
            workloadTxPO.setPlants(0);
            workloadTxPO.setDescription("库存减瓶");
            workloadTxPO.setOperatorId(currentUser.getId());
            workloadTxPO.setOperatorName(currentUser.getName());
            workloadTxPO.setOperateTime(now);
            return workloadTxPO;
          });
        workloadTransactionPO.setBottles(workloadTransactionPO.getBottles() - 1);
        workloadTransactionPO.setPlants(workloadTransactionPO.getPlants() - bottle.getPlantCount());

        // 组培瓶操作日志(质检)
        BottleLogPO logForTask = new BottleLogPO();
        logForTask.setId(Id.g());
        logForTask.setIdBottle(discard.getIdBottle());
        logForTask.setOppTime(now);
        logForTask.setTableName(StorageInOut.TB_NAME);
        logForTask.setRecordId(po.getId());
        logForTask.setBizType("库存减瓶");
        logForTask.setDescription(StringUtil.EMPTY);
        logForTask.setInspectorId(StringUtil.EMPTY);
        logForTask.setInspectorName(StringUtil.EMPTY);
        logForTask.setOperatorId(StringUtil.EMPTY);
        logForTask.setOperatorName(StringUtil.EMPTY);
        logForTask.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_NONE);
        logForTask.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
        logForTask.setStorageReason(Const.STORAGE_REASON_INVENTORY_SUB);
        logForTask.setDiscarded(Const.BOOLEAN_0);
        logForTask.setDiscardReason(StringUtil.EMPTY);
        bottleLogs.add(logForTask);
      }
    }

    if (bottlesActual < 1 || plantActual < 1) {
      throw new ErrorMessageException("无有效作业，无法完成出库。");
    }

    // 组织出库单仓储流水
    var transactionList = new ArrayList<InventoryTransactionPO>();
    var varieties = transitionMap.keySet();
    for (var variety : varieties) {
      var varietyCodes = transitionMap.get(variety).keySet();
      for (var varietyCode : varietyCodes) {
        var generations = transitionMap.get(variety).get(varietyCode).keySet();
        for (var generation : generations) {
          var stat = transitionMap.get(variety).get(varietyCode).get(generation);
          transactionList.add(stat);
        }
      }
    }

    // 组织工作量流水
    var workloadTransactionList = new ArrayList<WorkloadTransactionPO>();
    var roles = workloadTransactionMap.keySet();
    for (var role : roles) {
      var userIds = workloadTransactionMap.get(role).keySet();
      for (var userId : userIds) {
        varieties = workloadTransactionMap.get(role).get(userId).keySet();
        for (var variety : varieties) {
          var varietyCodes = workloadTransactionMap.get(role).get(userId).get(variety).keySet();
          for (var varietyCode : varietyCodes) {
            var generations = workloadTransactionMap.get(role).get(userId).get(variety).get(varietyCode).keySet();
            for (var generation : generations) {
              var stat = workloadTransactionMap.get(role).get(userId).get(variety).get(varietyCode).get(generation);
              workloadTransactionList.add(stat);
            }
          }
        }
      }
    }

    // 更新出库单数量
    po.setBottlesActual(-bottlesActual);
    po.setBottlesApplied(-bottlesActual);
    po.setPlantsActual(-plantActual);
    po.setPlantsApplied(-plantActual);

    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_FINISHED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);


    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
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

    for (int i = 0; i < transactionList.size(); i++) {
      entityManager.persist(transactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    for (int i = 0; i < workloadTransactionList.size(); i++) {
      entityManager.persist(workloadTransactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void deleteForInventorySubBottle(@NonNull String inOutId) {
    Assert.hasText(inOutId, "出库信息不能为空");

    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null || po.getStatus() == null || po.getId() == null) {
      throw new ErrorMessageException("无法获得出库信息");
    }
    var status = po.getStatus();
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED)) {
      throw new ErrorMessageException("无法删除此出库申请");
    }

    storageInOutDAO.delete(po);
    storageInOutBottleDAO.deleteAllByIdInOut(po.getId());
    storageInOutLogDAO.deleteAllByIdInOutIn(List.of(po.getId()));
  }


  @Transactional
  public void createAcclimatizationOut(@NonNull User currentUser) {

    // 组织出库单
    var now = System.currentTimeMillis();

    StorageInOutPO po = new StorageInOutPO();
    po.setId(Id.g());
    po.setInOut(Const.STORAGE_IN_OUT_TYPE_OUT);
    po.setBottlesApplied(0);
    po.setPlantsApplied(0);
    po.setBottlesKeepInApplied(0);
    po.setPlantsKeepInApplied(0);
    po.setBottlesActual(0);
    po.setPlantsActual(0);
    po.setBottlesKeepInActual(0);
    po.setPlantsKeepInActual(0);
    po.setBottlesDiscard(0);
    po.setPlantsDiscard(0);
    po.setBottlesVoided(0);
    po.setPlantsVoided(0);
    po.setIgnoreStats(Const.BOOLEAN_0);
    po.setReason(Const.STORAGE_REASON_ACCLIMATIZATION);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    po.setIdTask(StringUtil.EMPTY);
    po.setIdInspection(StringUtil.EMPTY);
    po.setApplicantId(currentUser.getId());
    po.setApplicantName(currentUser.getName());
    po.setApplicantTime(now);
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    this.storageInOutDAO.save(po);

    // 组织入库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);

    this.storageInOutLogDAO.save(log);
  }

  @Transactional
  public void outProcessForAcclimatizationOut(ModelStorageOutInspectProcessDetail model, User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED))
      throw new ErrorMessageException("当前出库业务已被处理，无法继续执行。");

    var bottleIdList = new ArrayList<String>();
    var discardList = model.getDiscardList();
    if (discardList != null) {
      for (var m : discardList) {
        bottleIdList.add(m.getIdBottle());
        if (m.getPlantCountActual() == null || m.getPlantCountActual() < 1) {
          throw new ErrorMessageException("出库培养瓶存疑，" + m.getIdBottle() + "的株数错误，必须大于零。");
        }
      }
      long bottleCount = bottleDAO.countBottles2(Const.BOTTLE_STORAGE_STATUS_IN, bottleIdList);
      if (bottleCount != discardList.size())
        throw new ErrorMessageException("出库培养瓶存疑，请刷新可用瓶后再次执行出库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 查询培养瓶信息
    var bottleMap = new HashMap<String, Bottle>();
    for (var b : this.bottleDAO.findAllByIdIn(bottleIdList)) {
      bottleMap.put(b.getId(), b);
    }

    // 计算瓶数/株数
    int plantActual = 0;
    int bottlesActual = 0;
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (discardList != null) {
      for (var discard : discardList) {
        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(discard.getIdBottle());
        b.setVariety(bottleMap.get(discard.getIdBottle()).getVariety());
        b.setVarietyCode(bottleMap.get(discard.getIdBottle()).getVarietyCode());
        b.setExplantCollectionBatch(bottleMap.get(discard.getIdBottle()).getExplantCollectionBatch());
        b.setGeneration(bottleMap.get(discard.getIdBottle()).getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_OUT);
        b.setPlantCountApplied(bottleMap.get(discard.getIdBottle()).getPlantCount());
        b.setPlantCountActual(bottleMap.get(discard.getIdBottle()).getPlantCount());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DONE);
        b.setDiscardReason(StringUtil.EMPTY);
        bottles.add(b);

        plantActual += bottleMap.get(discard.getIdBottle()).getPlantCount();
        bottlesActual++;
      }
    }

    // 更新出库单数量
    po.setBottlesActual(-bottlesActual);
    po.setPlantsActual(-plantActual);
    po.setBottlesApplied(-bottlesActual);
    po.setPlantsApplied(-plantActual);

    var now = System.currentTimeMillis();
    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);

    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void outFinishForAcclimatizationOut(ModelStorageOutInspectProcessDetail model, User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED))
      throw new ErrorMessageException("当前出库业务已被处理，无法继续执行。");

    var bottleIdList = new ArrayList<String>();
    var discardList = model.getDiscardList();
    if (discardList != null) {
      for (var m : discardList) {
        bottleIdList.add(m.getIdBottle());
      }
      long bottleCount = bottleDAO.countBottles2(Const.BOTTLE_STORAGE_STATUS_IN, bottleIdList);
      if (bottleCount != discardList.size())
        throw new ErrorMessageException("出库培养瓶存疑，请刷新可用瓶后再次执行出库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 查询培养瓶信息
    var bottleMap = new HashMap<String, BottlePO>();
    for (var b : this.bottleDAO.findAllByIdIn(bottleIdList)) {
      bottleMap.put(b.getId(), b);
    }

    var now = System.currentTimeMillis();

    // 计算瓶数/株数/培养瓶操作日志
    int plantActual = 0;
    int bottlesActual = 0;
    List<BottleLogPO> bottleLogs = new ArrayList<>();
    var transitionMap = new HashMap<String, Map<String, Map<Integer, InventoryTransactionPO>>>();
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (discardList != null) {
      for (var discard : discardList) {
        var bottle = bottleMap.get(discard.getIdBottle());
        bottle.setCurrentIdTask(StringUtil.EMPTY);
        bottle.setCurrentIdOppGroup(StringUtil.EMPTY);
        bottle.setCurrentCultureStage(Const.TISSUE_TASK_TYPE_ACCLIMATIZATION);
        bottle.setCurrentRoleInTask(Const.BOTTLE_TASK_ROLE_PARENT);
        bottle.setCurrentParentKeep(Const.BOOLEAN_0);
        bottle.setCurrentParentKeepPlantCount(0);
        bottle.setCurrentOperateStatus(Const.BOTTLE_OPERATE_STATUS_FINISHED);
        bottle.setCurrentInspectorId(StringUtil.EMPTY);
        bottle.setCurrentInspectorName(StringUtil.EMPTY);
        bottle.setCurrentOperatorId(StringUtil.EMPTY);
        bottle.setCurrentOperatorName(StringUtil.EMPTY);
        bottle.setCurrentStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
        bottle.setLastIssuedUserId(currentUser.getId());
        bottle.setLastIssuedUserName(currentUser.getName());
        bottle.setLastIssuedTime(now);
        bottle.setLastIssuedReason(Const.STORAGE_REASON_ACCLIMATIZATION);

        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(discard.getIdBottle());
        b.setVariety(bottle.getVariety());
        b.setVarietyCode(bottle.getVarietyCode());
        b.setExplantCollectionBatch(bottle.getExplantCollectionBatch());
        b.setGeneration(bottle.getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_OUT);
        b.setPlantCountApplied(bottle.getPlantCount());
        b.setPlantCountActual(bottle.getPlantCount());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DONE);
        b.setDiscardReason(StringUtil.EMPTY);
        bottles.add(b);

        plantActual += bottle.getPlantCount();
        bottlesActual++;

        // 库存流水
        var stat = transitionMap.computeIfAbsent(bottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), (k) -> {
            InventoryTransactionPO transactionPO = new InventoryTransactionPO();
            transactionPO.setId(Id.g());
            transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_OUT);
            transactionPO.setTableName(StorageInOut.TB_NAME);
            transactionPO.setRecordId(po.getId());
            transactionPO.setVariety(bottle.getVariety());
            transactionPO.setVarietyCode(bottle.getVarietyCode());
            transactionPO.setGeneration(bottle.getGeneration());
            transactionPO.setBottles(0);
            transactionPO.setPlants(0);
            transactionPO.setDescription("炼苗出库");
            transactionPO.setOperatorId(currentUser.getId());
            transactionPO.setOperatorName(currentUser.getName());
            transactionPO.setOperateTime(now);
            return transactionPO;
          });
        stat.setBottles(stat.getBottles() - 1);
        stat.setPlants(stat.getPlants() - bottle.getPlantCount());

        // 组培瓶操作日志(质检)
        BottleLogPO logForTask = new BottleLogPO();
        logForTask.setId(Id.g());
        logForTask.setIdBottle(discard.getIdBottle());
        logForTask.setOppTime(now);
        logForTask.setTableName(StorageInOut.TB_NAME);
        logForTask.setRecordId(po.getId());
        logForTask.setBizType("炼苗出库");
        logForTask.setDescription(StringUtil.EMPTY);
        logForTask.setInspectorId(StringUtil.EMPTY);
        logForTask.setInspectorName(StringUtil.EMPTY);
        logForTask.setOperatorId(StringUtil.EMPTY);
        logForTask.setOperatorName(StringUtil.EMPTY);
        logForTask.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_NONE);
        logForTask.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
        logForTask.setStorageReason(Const.STORAGE_REASON_ACCLIMATIZATION);
        logForTask.setDiscarded(Const.BOOLEAN_0);
        logForTask.setDiscardReason(StringUtil.EMPTY);
        bottleLogs.add(logForTask);
      }
    }

    if (bottlesActual < 1 || plantActual < 1) {
      throw new ErrorMessageException("无有效作业，无法完成出库。");
    }

    // 组织出库单仓储流水
    var transactionList = new ArrayList<InventoryTransactionPO>();
    var varieties = transitionMap.keySet();
    for (var variety : varieties) {
      var varietyCodes = transitionMap.get(variety).keySet();
      for (var varietyCode : varietyCodes) {
        var generations = transitionMap.get(variety).get(varietyCode).keySet();
        for (var generation : generations) {
          var stat = transitionMap.get(variety).get(varietyCode).get(generation);
          transactionList.add(stat);
        }
      }
    }

    // 更新出库单数量
    po.setBottlesApplied(-bottlesActual);
    po.setBottlesActual(-bottlesActual);
    po.setPlantsApplied(-plantActual);
    po.setPlantsActual(-plantActual);

    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_FINISHED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);


    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
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

    for (int i = 0; i < transactionList.size(); i++) {
      entityManager.persist(transactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void deleteForAcclimatizationOut(@NonNull String inOutId) {
    Assert.hasText(inOutId, "出库信息不能为空");

    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null || po.getStatus() == null || po.getId() == null) {
      throw new ErrorMessageException("无法获得出库信息");
    }
    var status = po.getStatus();
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED)) {
      throw new ErrorMessageException("无法删除此出库信息");
    }

    storageInOutDAO.delete(po);
    storageInOutBottleDAO.deleteAllByIdInOut(po.getId());
    storageInOutLogDAO.deleteAllByIdInOutIn(List.of(po.getId()));
  }


  @Transactional
  public void createAcclimatizationIn(@NonNull User currentUser) {

    // 组织出库单
    var now = System.currentTimeMillis();

    StorageInOutPO po = new StorageInOutPO();
    po.setId(Id.g());
    po.setInOut(Const.STORAGE_IN_OUT_TYPE_IN);
    po.setBottlesApplied(0);
    po.setPlantsApplied(0);
    po.setBottlesKeepInApplied(0);
    po.setPlantsKeepInApplied(0);
    po.setBottlesActual(0);
    po.setPlantsActual(0);
    po.setBottlesKeepInActual(0);
    po.setPlantsKeepInActual(0);
    po.setBottlesDiscard(0);
    po.setPlantsDiscard(0);
    po.setBottlesVoided(0);
    po.setPlantsVoided(0);
    po.setIgnoreStats(Const.BOOLEAN_0);
    po.setReason(Const.STORAGE_REASON_ACCLIMATIZATION);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    po.setIdTask(StringUtil.EMPTY);
    po.setIdInspection(StringUtil.EMPTY);
    po.setApplicantId(currentUser.getId());
    po.setApplicantName(currentUser.getName());
    po.setApplicantTime(now);
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    this.storageInOutDAO.save(po);

    // 组织入库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);

    this.storageInOutLogDAO.save(log);
  }

  @Transactional
  public void inProcessForAcclimatizationIn(ModelStorageOutInspectProcessDetail model, User currentUser) {
    Assert.notNull(model, "入库信息不能为空");
    Assert.hasText(model.getInOutId(), "入库信息不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到入库信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("入库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED))
      throw new ErrorMessageException("当前入库业务已被处理，无法继续执行。");

    var bottleIdList = new ArrayList<String>();
    var discardList = model.getDiscardList();
    if (discardList != null) {
      for (var m : discardList) {
        bottleIdList.add(m.getIdBottle());
        if (m.getPlantCountActual() == null || m.getPlantCountActual() < 1) {
          throw new ErrorMessageException("入库培养瓶存疑，" + m.getIdBottle() + "的株数错误，必须大于零。");
        }
      }
      long bottleCount = bottleDAO.countBottles2(Const.BOTTLE_STORAGE_STATUS_OUT, bottleIdList);
      if (bottleCount != discardList.size())
        throw new ErrorMessageException("入库培养瓶存疑，请刷新可用瓶后再次执行入库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 查询培养瓶信息
    var bottleMap = new HashMap<String, Bottle>();
    for (var b : this.bottleDAO.findAllByIdIn(bottleIdList)) {
      bottleMap.put(b.getId(), b);
    }

    // 计算瓶数/株数
    int plantActual = 0;
    int bottlesActual = 0;
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (discardList != null) {
      for (var discard : discardList) {
        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(discard.getIdBottle());
        b.setVariety(bottleMap.get(discard.getIdBottle()).getVariety());
        b.setVarietyCode(bottleMap.get(discard.getIdBottle()).getVarietyCode());
        b.setExplantCollectionBatch(bottleMap.get(discard.getIdBottle()).getExplantCollectionBatch());
        b.setGeneration(bottleMap.get(discard.getIdBottle()).getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN);
        b.setPlantCountApplied(bottleMap.get(discard.getIdBottle()).getPlantCount());
        b.setPlantCountActual(bottleMap.get(discard.getIdBottle()).getPlantCount());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DONE);
        b.setDiscardReason(StringUtil.EMPTY);
        bottles.add(b);

        plantActual += bottleMap.get(discard.getIdBottle()).getPlantCount();
        bottlesActual++;
      }
    }

    // 更新出库单数量
    po.setBottlesActual(bottlesActual);
    po.setPlantsActual(plantActual);
    po.setBottlesApplied(bottlesActual);
    po.setPlantsApplied(plantActual);

    var now = System.currentTimeMillis();
    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_STARTED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);

    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void inFinishForAcclimatizationIn(ModelStorageOutInspectProcessDetail model, User currentUser) {
    Assert.notNull(model, "入库信息不能为空");
    Assert.hasText(model.getInOutId(), "入库信息不能为空");

    String inOutId = model.getInOutId();
    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到入库信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("入库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED))
      throw new ErrorMessageException("当前入库业务已被处理，无法继续执行。");

    var bottleIdList = new ArrayList<String>();
    var discardList = model.getDiscardList();
    if (discardList != null) {
      for (var m : discardList) {
        bottleIdList.add(m.getIdBottle());
      }
      long bottleCount = bottleDAO.countBottles2(Const.BOTTLE_STORAGE_STATUS_OUT, bottleIdList);
      if (bottleCount != discardList.size())
        throw new ErrorMessageException("入库培养瓶存疑，请刷新可用瓶后再次执行入库。");
    }
    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);

    // 查询培养瓶信息
    var bottleMap = new HashMap<String, BottlePO>();
    for (var b : this.bottleDAO.findAllByIdIn(bottleIdList)) {
      bottleMap.put(b.getId(), b);
    }

    var now = System.currentTimeMillis();

    // 计算瓶数/株数/培养瓶操作日志
    int plantActual = 0;
    int bottlesActual = 0;
    List<BottleLogPO> bottleLogs = new ArrayList<>();
    var transitionMap = new HashMap<String, Map<String, Map<Integer, InventoryTransactionPO>>>();
    // 组织关联瓶信息
    var bottles = new ArrayList<StorageInOutBottlePO>();
    if (discardList != null) {
      for (var discard : discardList) {
        var bottle = bottleMap.get(discard.getIdBottle());
        bottle.setCurrentIdTask(StringUtil.EMPTY);
        bottle.setCurrentIdOppGroup(StringUtil.EMPTY);
        bottle.setCurrentCultureStage(StringUtil.EMPTY);
        bottle.setCurrentRoleInTask(Const.BOTTLE_TASK_ROLE_NONE);
        bottle.setCurrentParentKeep(Const.BOOLEAN_0);
        bottle.setCurrentParentKeepPlantCount(0);
        bottle.setCurrentOperateStatus(Const.BOTTLE_OPERATE_STATUS_NONE);
        bottle.setCurrentInspectorId(StringUtil.EMPTY);
        bottle.setCurrentInspectorName(StringUtil.EMPTY);
        bottle.setCurrentOperatorId(StringUtil.EMPTY);
        bottle.setCurrentOperatorName(StringUtil.EMPTY);
        bottle.setLastIdTask(StringUtil.EMPTY);
        bottle.setLastIdOppGroup(StringUtil.EMPTY);
        bottle.setLastCultureStage(Const.TISSUE_TASK_TYPE_ACCLIMATIZATION);
        bottle.setLastRoleInTask(Const.BOTTLE_TASK_ROLE_PARENT);
        bottle.setLastParentKeep(Const.BOOLEAN_0);
        bottle.setLastParentKeepPlantCount(0);
        bottle.setLastOperateStatus(Const.BOTTLE_OPERATE_STATUS_FINISHED);
        bottle.setLastInspectorId(StringUtil.EMPTY);
        bottle.setLastInspectorName(StringUtil.EMPTY);
        bottle.setLastOperatorId(StringUtil.EMPTY);
        bottle.setLastOperatorName(StringUtil.EMPTY);
        bottle.setCurrentStorageStatus(Const.BOTTLE_STORAGE_STATUS_IN);
        bottle.setLastReceivedUserId(currentUser.getId());
        bottle.setLastReceivedUserName(currentUser.getName());
        bottle.setLastReceivedTime(now);
        bottle.setLastReceivedReason(Const.STORAGE_REASON_ACCLIMATIZATION);

        var b = new StorageInOutBottlePO();
        b.setIdInOut(inOutId);
        b.setIdBottle(discard.getIdBottle());
        b.setVariety(bottle.getVariety());
        b.setVarietyCode(bottle.getVarietyCode());
        b.setExplantCollectionBatch(bottle.getExplantCollectionBatch());
        b.setGeneration(bottle.getGeneration());
        b.setIgnoreStats(Const.BOOLEAN_0);
        b.setInOutBottleType(Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN);
        b.setPlantCountApplied(bottle.getPlantCount());
        b.setPlantCountActual(bottle.getPlantCount());
        b.setKeep(Const.BOOLEAN_0);
        b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DONE);
        b.setDiscardReason(StringUtil.EMPTY);
        bottles.add(b);

        plantActual += bottle.getPlantCount();
        bottlesActual++;

        // 库存流水
        var stat = transitionMap.computeIfAbsent(bottle.getVariety(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getVarietyCode(), (k) -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), (k) -> {
            InventoryTransactionPO transactionPO = new InventoryTransactionPO();
            transactionPO.setId(Id.g());
            transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_IN);
            transactionPO.setTableName(StorageInOut.TB_NAME);
            transactionPO.setRecordId(po.getId());
            transactionPO.setVariety(bottle.getVariety());
            transactionPO.setVarietyCode(bottle.getVarietyCode());
            transactionPO.setGeneration(bottle.getGeneration());
            transactionPO.setBottles(0);
            transactionPO.setPlants(0);
            transactionPO.setDescription("炼苗还库");
            transactionPO.setOperatorId(currentUser.getId());
            transactionPO.setOperatorName(currentUser.getName());
            transactionPO.setOperateTime(now);
            return transactionPO;
          });
        stat.setBottles(stat.getBottles() + 1);
        stat.setPlants(stat.getPlants() + bottle.getPlantCount());

        // 组培瓶操作日志(质检)
        BottleLogPO logForTask = new BottleLogPO();
        logForTask.setId(Id.g());
        logForTask.setIdBottle(discard.getIdBottle());
        logForTask.setOppTime(now);
        logForTask.setTableName(StorageInOut.TB_NAME);
        logForTask.setRecordId(po.getId());
        logForTask.setBizType("炼苗还库");
        logForTask.setDescription(StringUtil.EMPTY);
        logForTask.setInspectorId(StringUtil.EMPTY);
        logForTask.setInspectorName(StringUtil.EMPTY);
        logForTask.setOperatorId(StringUtil.EMPTY);
        logForTask.setOperatorName(StringUtil.EMPTY);
        logForTask.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_NONE);
        logForTask.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_IN);
        logForTask.setStorageReason(Const.STORAGE_REASON_ACCLIMATIZATION);
        logForTask.setDiscarded(Const.BOOLEAN_0);
        logForTask.setDiscardReason(StringUtil.EMPTY);
        bottleLogs.add(logForTask);
      }
    }

    if (bottlesActual < 1 || plantActual < 1) {
      throw new ErrorMessageException("无有效作业，无法完成入库。");
    }

    // 组织出库单仓储流水
    var transactionList = new ArrayList<InventoryTransactionPO>();
    var varieties = transitionMap.keySet();
    for (var variety : varieties) {
      var varietyCodes = transitionMap.get(variety).keySet();
      for (var varietyCode : varietyCodes) {
        var generations = transitionMap.get(variety).get(varietyCode).keySet();
        for (var generation : generations) {
          var stat = transitionMap.get(variety).get(varietyCode).get(generation);
          transactionList.add(stat);
        }
      }
    }

    // 更新出库单数量
    po.setBottlesActual(bottlesActual);
    po.setPlantsActual(plantActual);
    po.setBottlesApplied(bottlesActual);
    po.setPlantsApplied(plantActual);

    // 更新操作信息
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_FINISHED);
    po.setOppReason(StringUtil.EMPTY);
    // 组织出库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(po.getStatus());
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);
    this.storageInOutLogDAO.save(log);


    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
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

    for (int i = 0; i < transactionList.size(); i++) {
      entityManager.persist(transactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional
  public void deleteForAcclimatizationIn(@NonNull String inOutId) {
    Assert.hasText(inOutId, "入库信息不能为空");

    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null || po.getStatus() == null || po.getId() == null) {
      throw new ErrorMessageException("无法获得入库信息");
    }
    var status = po.getStatus();
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_STARTED)) {
      throw new ErrorMessageException("无法删除此入库信息");
    }

    storageInOutDAO.delete(po);
    storageInOutBottleDAO.deleteAllByIdInOut(po.getId());
    storageInOutLogDAO.deleteAllByIdInOutIn(List.of(po.getId()));
  }
}
