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

import chenhuiran.ptcmisserver.repository.ArchiveDAO;
import chenhuiran.ptcmisserver.repository.BottleCodeComplementTaskDAO;
import chenhuiran.ptcmisserver.repository.BottleCodeComplementTaskDetailDAO;
import chenhuiran.ptcmisserver.repository.BottleRelationDAO;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.*;
import chenhuiran.ptcmisserver.utils.ExplantCollectionBatchUtil;
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

import java.util.*;

@Service
public class BottleCodeComplementTaskManager {

  @Autowired
  private BottleCodeSequenceManager bottleCodeSequenceManager;
  @Autowired
  private ArchiveDAO archiveDAO;
  @Autowired
  private BottleRelationDAO bottleRelationDAO;
  @Autowired
  private BottleCodeComplementTaskDAO bottleCodeComplementTaskDAO;
  @Autowired
  private BottleCodeComplementTaskDetailDAO bottleCodeComplementTaskDetailDAO;
  @PersistenceContext
  private EntityManager entityManager;

  public QueryResultPaged<? extends BottleCodeComplementTask> queryByCondition(QueryCondition<QueryConditionBottleCodeComplementTask> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();
    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.DESC, BottleCodeComplementTask.BP_NAME_CREATETIME)
    );

    Specification<BottleCodeComplementTaskPO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (conditionModel != null) {
        String keyword = conditionModel.getKeyword();
        if (keyword != null && !keyword.isBlank()) {
          String likeVal = "%" + keyword.trim() + "%";
          Predicate p1 = cb.like(root.get(BottleCodeComplementTask.BP_NAME_VARIETY), likeVal);
          Predicate p2 = cb.like(root.get(BottleCodeComplementTask.BP_NAME_VARIETYCODE), likeVal);
          Predicate p3 = cb.like(root.get(BottleCodeComplementTask.BP_NAME_CODEPREFIX), likeVal);
          Predicate p4 = cb.like(root.get(BottleCodeComplementTask.BP_NAME_CREATORNAME), likeVal);
          Predicate p5 = cb.like(root.get(BottleCodeComplementTask.BP_NAME_OPERATORNAME), likeVal);
          predicateList.add(cb.or(p1, p2, p3, p4, p5));
        }
        var taskStatus = conditionModel.getTaskStatus();
        if (taskStatus != null && !taskStatus.isBlank()) {
          predicateList.add(cb.equal(root.get(BottleCodeComplementTask.BP_NAME_TASKSTATUS), taskStatus));
        }
        var explantCollectionBatch = conditionModel.getExplantCollectionBatch();
        if (explantCollectionBatch != null && explantCollectionBatch > 0) {
          predicateList.add(cb.equal(root.get(BottleCodeComplementTask.BP_NAME_EXPLANTCOLLECTIONBATCH), explantCollectionBatch));
        }
        var generation = conditionModel.getGeneration();
        if (generation != null && generation >= 0) {
          predicateList.add(cb.equal(root.get(BottleCodeComplementTask.BP_NAME_GENERATION), generation));
        }
        var createTime = conditionModel.getCreateTime();
        if (createTime != null) {
          var createTimeStart = createTime.getStart();
          if (createTimeStart != null && createTimeStart > 0) {
            predicateList.add(cb.greaterThanOrEqualTo(root.get(BottleCodeComplementTask.BP_NAME_CREATETIME), createTimeStart));
          }
          var createTimeEnd = createTime.getEnd();
          if (createTimeEnd != null && createTimeEnd > 0) {
            predicateList.add(cb.lessThanOrEqualTo(root.get(BottleCodeComplementTask.BP_NAME_CREATETIME), createTimeEnd));
          }
        }
        var finishTime = conditionModel.getFinishTime();
        if (finishTime != null) {
          var finishTimeStart = finishTime.getStart();
          if (finishTimeStart != null && finishTimeStart > 0) {
            predicateList.add(cb.greaterThanOrEqualTo(root.get(BottleCodeComplementTask.BP_NAME_FINISHTIME), finishTimeStart));
          }
          var finishTimeEnd = finishTime.getEnd();
          if (finishTimeEnd != null && finishTimeEnd > 0) {
            predicateList.add(cb.lessThanOrEqualTo(root.get(BottleCodeComplementTask.BP_NAME_FINISHTIME), finishTimeEnd));
          }
        }
      }
      return cb.and(predicateList.toArray(new Predicate[0]));
    };
    Page<BottleCodeComplementTaskDTO> dtoPage = bottleCodeComplementTaskDAO.findAll(spec, pageable).map(BottleCodeComplementTaskDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());
    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }

  @Transactional
  public String queryOrCreateCodePrefix(String taskId) {
    Assert.hasText(taskId, "瓶体补码任务不能为空");
    BottleCodeComplementTaskPO po = bottleCodeComplementTaskDAO.findById(taskId).orElse(null);
    if (po == null) {
      throw new ErrorMessageException("无法找到此瓶体补码任务信息。");
    }
    var codePrefix = po.getCodePrefix();
    if (codePrefix != null && !codePrefix.isBlank()) {
      return codePrefix;
    }
    var taskStatus = po.getTaskStatus() == null ? Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_CREATED : po.getTaskStatus();
    if (!taskStatus.equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_CREATED) && !taskStatus.equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_STARTED)) {
      throw new ErrorMessageException("该瓶体补码任务无瓶码前缀，但不处于执行状态，无法为其分配子瓶瓶码。");
    }
    codePrefix = bottleCodeSequenceManager.next();
    po.setCodePrefix(codePrefix);
    return codePrefix;
  }

  public List<? extends BottleCodeComplementTaskDetail> queryAllCodesInTask(String taskId) {
    return bottleCodeComplementTaskDetailDAO.findByIdTaskOrderByIdAsc(taskId).stream().map(BottleCodeComplementTaskDetailDTO::new).toList();
  }

  @Transactional
  public void save(BottleCodeComplementTaskDTO model, User currentUser) {
    Assert.notNull(model, "瓶体补码任务不能为空");
    Assert.hasText(model.getVariety(), "品种不能为空");
    Assert.hasText(model.getVarietyCode(), "品种编码不能为空");
    if (!ExplantCollectionBatchUtil.check(model.getExplantCollectionBatch())) {
      throw new ErrorMessageException("外植体采集批次只能是0，或符合 YYYYMMDD 格式的整数，如 20260101");
    }
    if (model.getGeneration() < 0) throw new ErrorMessageException("代数不得小于零");
    if (model.getGeneration() != 0 && model.getSourceType().equals(Const.STORAGE_REASON_INDUCTION))
      throw new ErrorMessageException("诱导任务代数必须是0");
    if (model.getGeneration() == 0 && !model.getSourceType().equals(Const.STORAGE_REASON_INDUCTION) && !model.getSourceType().equals(Const.STORAGE_REASON_ROOTING))
      throw new ErrorMessageException("除了诱导和生根任务外，代数必须大于0");

    // 根据ID查询任务信息。
    var po = bottleCodeComplementTaskDAO.findById(model.getId()).orElse(null);
    // 没有查到，则是新建任务。
    if (po == null) {
      po = new BottleCodeComplementTaskPO();
      po.setId(Id.g());
      po.setTaskStatus(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_CREATED);
    }

    // 校验任务状态。
    var dbStatus = po.getTaskStatus();
    if (!dbStatus.equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_CREATED)) {
      throw new ErrorMessageException("不可编辑已开始的瓶体补码任务信息");
    }

    // 保存
    long now = System.currentTimeMillis();
    po.setVariety(model.getVariety());
    po.setVarietyCode(model.getVarietyCode());
    po.setExplantCollectionBatch(model.getExplantCollectionBatch());
    po.setSourceType(model.getSourceType());
    po.setGeneration(model.getGeneration());
    po.setCodePrefix(StringUtil.EMPTY);
    po.setDefaultPlantsPerBottle(0);
    po.setTaskStatus(po.getTaskStatus());
    po.setBottles(0);
    po.setPlants(0);
    po.setCreatorId(currentUser.getId());
    po.setCreatorName(currentUser.getName());
    po.setCreateTime(now);
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setStartTime(0L);
    po.setFinishTime(0L);
    if (po.isNew()) bottleCodeComplementTaskDAO.save(po);
  }

  @Transactional
  public void delete(String taskId) {
    Assert.hasText(taskId, "瓶体补码任务不能为空");
    var po = bottleCodeComplementTaskDAO.findById(taskId.trim()).orElse(null);
    if (po == null) return;

    // 校验状态
    var dbStatus = po.getTaskStatus();
    if (dbStatus == null
      ||
      (
        !dbStatus.equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_CREATED)
          && !dbStatus.equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_STARTED)
      )
    ) throw new ErrorMessageException("不可删除此瓶体补码任务");

    // 删除任务
    bottleCodeComplementTaskDAO.deleteById(taskId.trim());
    // 删除明细
    bottleCodeComplementTaskDetailDAO.deleteBatchByIdTask(taskId.trim());
  }

  @Transactional
  public ModelPrintBottleCodeForAddBottles printCode(ModelPrintBottleCodeForComplement model, User currentUser) {
    Assert.notNull(model, "瓶体补码任务不能为空");
    Assert.hasText(model.getTaskId(), "瓶体补码任务不能为空");
    Assert.hasText(model.getCodePrefix(), "瓶码前缀（六位英文大写字母）不能为空");
    Assert.notNull(model.getBottleCount(), "培养瓶数量不能为空");
    var bottleCount = model.getBottleCount();
    if (bottleCount == null || bottleCount < 1 || bottleCount > 9999)
      throw new ErrorMessageException("子代培养瓶数量必须大于 0 且小于 9999");

    // 校验状态
    var po = bottleCodeComplementTaskDAO.findById(model.getTaskId()).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到此任务信息。");
    var dbStatus = po.getTaskStatus();
    if (dbStatus == null
      ||
      (
        !dbStatus.equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_CREATED)
          && !dbStatus.equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_STARTED)
      )
    ) {
      throw new ErrorMessageException("该任务已完成，无法分配瓶码。");
    }

    // 更新任务状态。
    po.setTaskStatus(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_STARTED);
    po.setCodePrefix(model.getCodePrefix());
    po.setOperatorName(po.getOperatorName() == null || po.getOperatorName().isBlank() ? currentUser.getName() : po.getOperatorName());
    po.setOperatorId(po.getOperatorId() == null || po.getOperatorId().isBlank() ? currentUser.getId() : po.getOperatorId());

    // 已有瓶码的最大序号。
    int maxCodeNumber = 0;

    // 查询最大瓶码
    Page<BottleCodeComplementTaskDetailPO> page = bottleCodeComplementTaskDetailDAO.queryChildBottleMaxCode(po.getId(), PageRequest.of(0, 1));
    BottleCodeComplementTaskDetailPO maxCodePO = page.hasContent() ? page.getContent().getFirst() : null;
    if (maxCodePO != null) {
      String maxCode = maxCodePO.getId();
      if (maxCode == null || maxCode.isBlank() || maxCode.length() != 11) {
        throw new ErrorMessageException("该任务瓶码有误，无法续打瓶码。");
      }
      var maxCodeParts = maxCode.split("-");
      if (maxCodeParts.length != 2) {
        throw new ErrorMessageException("该任务瓶码有误，无法续打瓶码。");
      }

      try {
        maxCodeNumber = Integer.parseInt(maxCodeParts[1]);
      } catch (Throwable ignored) {
      }
      if (maxCodeNumber < 1) {
        throw new ErrorMessageException("该任务瓶码有误，无法续打瓶码。");
      }
    }

    if (bottleCount > (9999 - maxCodeNumber)) {
      throw new ErrorMessageException("最多还能打印" + (9999 - maxCodeNumber) + "个瓶码。");
    }

    // 新建明细信息。
    var codePrefix = po.getCodePrefix();
    var codeStart = StringUtil.EMPTY;
    var codeEnd = StringUtil.EMPTY;
    List<BottleCodeComplementTaskDetailPO> taskBottles = new ArrayList<>();
    for (int index = 1; index <= bottleCount; index++) {
      final String bottleId = codePrefix + "-" + String.format("%04d", index + maxCodeNumber);
      if (index == 1) {
        codeStart = bottleId;
      }
      codeEnd = bottleId;
      BottleCodeComplementTaskDetailPO taskBottle = new BottleCodeComplementTaskDetailPO();
      taskBottle.setId(bottleId);
      taskBottle.setIdTask(model.getTaskId());
      taskBottle.setIdParent(StringUtil.EMPTY);
      taskBottle.setPlantCount(po.getDefaultPlantsPerBottle());
      taskBottle.setStatus(Const.BOTTLE_CODE_COMPLEMENT_CODE_STAT_CODED);
      taskBottles.add(taskBottle);
    }

    // 插入培养瓶数据
    final int batchSize = 500;
    for (int i = 0; i < taskBottles.size(); i++) {
      entityManager.persist(taskBottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    var result = new ModelPrintBottleCodeForAddBottles();
    result.setCodeStart(codeStart);
    result.setCodeEnd(codeEnd);
    return result;
  }

  @Transactional
  public void process(ModelBottleCodeComplementTaskProcess model, User currentUser) {
    Assert.notNull(model, "瓶体补码任务不能为空");
    Assert.hasText(model.getTaskId(), "瓶体补码任务不能为空");

    // 校验状态
    var po = bottleCodeComplementTaskDAO.findById(model.getTaskId()).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到此任务信息");
    if (po.getTaskStatus() == null ||
      (
        !po.getTaskStatus().equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_CREATED)
          && !po.getTaskStatus().equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_STARTED)
      )
    ) throw new ErrorMessageException("无法执行此任务");


    // 校验母瓶信息
    Set<String> parentIds = new HashSet<>();
    if (model.getDetails() != null) {
      parentIds.addAll(
        model.getDetails().stream()
          .filter(m -> m.getIdParent() != null && !m.getIdParent().isBlank())
          .map(m -> m.getIdParent().trim())
          .filter(id -> !id.isBlank())
          .toList()
      );
    }
    var parentIdCount = bottleCodeComplementTaskDetailDAO.countForParentId(
      parentIds.stream().toList(),
      po.getVariety(),
      po.getVarietyCode(),
      po.getExplantCollectionBatch(),
      Math.max(po.getGeneration() - 1, 0)
    );
    if (parentIdCount != parentIds.size()) throw new ErrorMessageException("发现不存在的母瓶信息，应仔细核对。");

    // 更新任务状态。
    po.setTaskStatus(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_STARTED);
    if (po.getStartTime() == null || po.getStartTime() < 1) po.setStartTime(System.currentTimeMillis());
    po.setOperatorName(currentUser.getName());
    po.setOperatorId(currentUser.getId());

    // 删除所有明细数据
    bottleCodeComplementTaskDetailDAO.deleteBatchByIdTask(model.getTaskId());

    // 组织明细数据
    var bottles = model.getDetails() == null
      ? new ArrayList<BottleCodeComplementTaskDetailPO>()
      : model.getDetails().stream().map(BottleCodeComplementTaskDetailPO::new).toList();

    // 计算瓶数和株数
    var plantsCount = 0;
    var bottlesCount = 0;
    for (BottleCodeComplementTaskDetailPO b : bottles) {
      if (b.getStatus() != null &&
        (
          b.getStatus().equals(Const.BOTTLE_CODE_COMPLEMENT_CODE_STAT_PROCESS) ||
            b.getStatus().equals(Const.BOTTLE_CODE_COMPLEMENT_CODE_STAT_PRINTED) ||
            b.getStatus().equals(Const.BOTTLE_CODE_COMPLEMENT_CODE_STAT_CHECKED)
        )
      ) {
        if (b.getPlantCount() < 1) throw new ErrorMessageException(b.getId() + " 株数错误，应是大于零的整数。");
        bottlesCount++;
        plantsCount += b.getPlantCount();
      }
    }
    po.setPlants(plantsCount);
    po.setBottles(bottlesCount);

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
  public void done(String taskId, User currentUser) {
    Assert.hasText(taskId, "瓶体补码任务不能为空");

    // 校验状态
    var po = bottleCodeComplementTaskDAO.findById(taskId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到此任务信息");
    if (po.getTaskStatus() == null || !po.getTaskStatus().equals(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_STARTED))
      throw new ErrorMessageException("无法完成此任务");

    // 校验培养瓶状态
    var voidedList = bottleCodeComplementTaskDetailDAO.findByIdTaskAndStatusIn(
      taskId,
      List.of(
        Const.BOTTLE_CODE_COMPLEMENT_CODE_STAT_PROCESS,
        Const.BOTTLE_CODE_COMPLEMENT_CODE_STAT_PRINTED
      )
    );
    if (!voidedList.isEmpty()) throw new ErrorMessageException("尚有未核对的已补码瓶体");

    // 校验附件材料
//    var archiveCount = archiveDAO.countByTableNameAndRecordId(BottleCodeComplementTask.TB_NAME, taskId);
//    if (archiveCount < 1L) throw new ErrorMessageException("尚未上传附件材料。");

    // 校验母瓶信息
    var unexistsParentCount = bottleCodeComplementTaskDetailDAO.countUnexistsParents(taskId, po.getVariety(), po.getVarietyCode(), po.getExplantCollectionBatch(), Math.max(po.getGeneration() - 1, 0));
    if (unexistsParentCount > 0) throw new ErrorMessageException("发现不存在的母瓶信息，应仔细核对。");

    // 更新任务状态。
    po.setTaskStatus(Const.BOTTLE_CODE_COMPLEMENT_TASK_STAT_FINISHED);
    if (po.getStartTime() == null || po.getStartTime() < 1) po.setStartTime(System.currentTimeMillis());
    po.setOperatorName(currentUser.getName());
    po.setOperatorId(currentUser.getId());
    po.setFinishTime(System.currentTimeMillis());

    // 查询所有母瓶
    var parentBottles = bottleCodeComplementTaskDetailDAO.findForCodeComplementDetailParents(taskId).stream().map(BottleDTO::new).toList();
    var parentBottleMap = new HashMap<String, BottleDTO>();
    parentBottles.forEach(p -> parentBottleMap.put(p.getId(), p));
    var parentRelationMap = new HashMap<String, List<BottleRelationDTO>>();
    if (!parentBottles.isEmpty()) {
      bottleRelationDAO.findAllByChildIdIn(parentBottles.stream().map(BottleDTO::getId).toList()).stream().map(BottleRelationDTO::new).forEach(r -> {
        parentRelationMap.computeIfAbsent(r.getChildId(), k -> new ArrayList<>()).add(r);
      });
    }

    // 查询所有已用瓶
    var usedList = bottleCodeComplementTaskDetailDAO.findByIdTaskAndStatus(taskId, Const.BOTTLE_CODE_COMPLEMENT_CODE_STAT_CHECKED);

    // 创建培养瓶信息并计算瓶数/株数。
    List<BottlePO> bottles = new ArrayList<>();
    List<BottleRelationPO> bottleRelations = new ArrayList<>();
    List<BottleLogPO> bottleLogs = new ArrayList<>();
    var plantsCount = 0;
    var bottlesCount = 0;

    if (!usedList.isEmpty()) {
      var now = System.currentTimeMillis();
      for (var detail : usedList) {
        plantsCount += detail.getPlantCount();
        bottlesCount++;
        BottlePO bottle = new BottlePO();

        // 基本信息
        bottle.setId(detail.getId());
        bottle.setVariety(po.getVariety());
        bottle.setVarietyCode(po.getVarietyCode());
        bottle.setExplantCollectionBatch(po.getExplantCollectionBatch());
        bottle.setGeneration(po.getGeneration());
        bottle.setPlantCount(detail.getPlantCount());
        bottle.setTaskTime(detail.getOperatorTime());
        bottle.setSourceType(po.getSourceType());
        // 母瓶信息
        var parentId = detail.getIdParent();
        parentId = parentId == null ? StringUtil.EMPTY : parentId.trim();
        if (!parentId.isBlank()) {
          var parent = parentBottleMap.get(parentId);
          if (parent == null) {
            throw new ErrorMessageException("发现不存在的母瓶信息，应仔细核对。");
          }
          bottle.setParentGeneration(parent.getGeneration());
          // 建立代际关系
          parentRelationMap.getOrDefault(parentId, new ArrayList<>()).forEach(r -> {
            bottleRelations.add(new BottleRelationPO(
              Id.g(),
              detail.getId(),
              r.getParentId(),
              r.getDepth() + 1
            ));
          });
        } else {
          bottle.setParentGeneration(Math.max(po.getGeneration() - 1, 0));
        }
        // 建立自身代际关系
        bottleRelations.add(new BottleRelationPO(
          Id.g(),
          detail.getId(),
          detail.getId(),
          0
        ));
        bottle.setIdParent(parentId);
        // 组培任务信息（当前）
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
        // 组培任务信息（末次）
        bottle.setLastIdTask(StringUtil.EMPTY);
        bottle.setLastIdOppGroup(StringUtil.EMPTY);
        bottle.setLastCultureStage(StringUtil.EMPTY);
        bottle.setLastRoleInTask(StringUtil.EMPTY);
        bottle.setLastParentKeep(Const.BOOLEAN_0);
        bottle.setLastParentKeepPlantCount(0);
        bottle.setLastOperateStatus(po.getSourceType());
        bottle.setLastInspectorId(detail.getInspectorId());
        bottle.setLastInspectorName(detail.getInspectorName());
        bottle.setLastOperatorId(detail.getOperatorId());
        bottle.setLastOperatorName(detail.getOperatorName());
        // 仓储信息
        bottle.setCurrentStorageStatus(Const.BOTTLE_STORAGE_STATUS_IN);
        bottle.setLastReceivedUserId(currentUser.getId());
        bottle.setLastReceivedUserName(currentUser.getName());
        bottle.setLastReceivedTime(now);
        bottle.setLastReceivedReason(Const.STORAGE_REASON_CODE_COMPLEMENT);
        bottle.setLastIssuedUserId(StringUtil.EMPTY);
        bottle.setLastIssuedUserName(StringUtil.EMPTY);
        bottle.setLastIssuedTime(0L);
        bottle.setLastIssuedReason(StringUtil.EMPTY);
        // 淘汰信息
        bottle.setDiscarded(Const.BOOLEAN_0);
        bottle.setPerformanceIncluded(Const.BOOLEAN_1);
        bottle.setDiscardReason(StringUtil.EMPTY);
        bottle.setDiscardReasonDesc(StringUtil.EMPTY);
        bottle.setDiscardTime(0L);
        bottle.setDiscardUserId(StringUtil.EMPTY);
        bottle.setDiscardUserName(StringUtil.EMPTY);
        // 向待处理列表中插入组培瓶信息
        bottles.add(bottle);

        // 组培瓶操作日志
        BottleLogPO log = new BottleLogPO();
        log.setId(Id.g());
        log.setIdBottle(detail.getId());
        log.setOppTime(now);
        log.setTableName(BottleCodeComplementTask.TB_NAME);
        log.setRecordId(taskId);
        log.setBizType("瓶体补码");
        log.setDescription(StringUtil.EMPTY);
        log.setInspectorId(StringUtil.EMPTY);
        log.setInspectorName(StringUtil.EMPTY);
        log.setOperatorId(currentUser.getId());
        log.setOperatorName(currentUser.getName());
        log.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_COMPLEMENT);
        log.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_IN);
        log.setStorageReason(Const.STORAGE_REASON_CODE_COMPLEMENT);
        log.setDiscarded(Const.BOOLEAN_0);
        log.setDiscardReason(StringUtil.EMPTY);
        // 向待处理列表中插入组培瓶操作日志信息
        bottleLogs.add(log);
      }
    }

    // 更新任务数量
    po.setPlants(plantsCount);
    po.setBottles(bottlesCount);

    final int batchSize = 500;
    // 插入培养瓶数据
    for (int i = 0; i < bottles.size(); i++) {
      entityManager.persist(bottles.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    // 插入培养瓶代际关系数据
    for (int i = 0; i < bottleRelations.size(); i++) {
      entityManager.persist(bottleRelations.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    // 插入培养瓶操作日志数据
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
}
