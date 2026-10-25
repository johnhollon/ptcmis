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
import chenhuiran.ptcmisserver.utils.ExplantCollectionBatchUtil;
import com.chenhuiran.commons.lang.Id;
import com.chenhuiran.commons.lang.StringUtil;
import com.chenhuiran.commons.lang.exceptions.ErrorMessageException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class TissueTaskManager {

  @Autowired
  private BottleCodeSequenceManager bottleCodeSequenceManager;
  @Autowired
  private ArchiveDAO archiveDAO;
  @Autowired
  private DictionaryCache dictionaryCache;
  @Autowired
  private BottleDAO bottleDAO;
  @Autowired
  private TissueTaskDAO tissueTaskDAO;
  @Autowired
  private UserDAO userDAO;
  @Autowired
  private TissueTaskOppGroupDAO tissueTaskOppGroupDAO;
  @Autowired
  private TissueTaskUserDAO tissueTaskUserDAO;
  @Autowired
  private TissueTaskBottleDAO tissueTaskBottleDAO;
  @Autowired
  private StorageInOutDAO storageInOutDAO;
  @Autowired
  private StorageInOutBottleDAO storageInOutBottleDAO;
  @Autowired
  private StorageInOutLogDAO storageInOutLogDAO;
  @PersistenceContext
  private EntityManager entityManager;

  public ModelTissueTaskView queryByTaskId(String taskId) {
    // 查询任务
    var tissueTaskPO = tissueTaskDAO.findById(taskId).orElse(null);
    if (tissueTaskPO == null) throw new ErrorMessageException("没有此项任务。");
    var tissueTask = new TissueTaskDTO(tissueTaskPO);

    // 查询任务关联操作组
    var oppGroups = tissueTaskOppGroupDAO.findByIdTaskIn(List.of(taskId)).stream().map(TissueTaskOppGroupDTO::new).toList();
    // 查询任务关联员工信息
    var taskUsers = tissueTaskUserDAO.findByIdTaskIn(List.of(taskId)).stream().map(TissueTaskUserDTO::new).toList();
    // 查询培养瓶
    var bottleList = tissueTaskBottleDAO.findByIdTaskInOrderByIdBottleAsc(List.of(taskId)).stream().map(TissueTaskBottleDTO::new).toList();
    // 查询出入库申请单
    var storageInOutList = storageInOutDAO.findByIdTaskInOrderByOperatorTimeDesc(List.of(taskId)).stream().map(StorageInOutDTO::new).toList();

    // 查询所有员工信息
    var userAll = userDAO.findAllWithoutPassword();

    var list = organizeTissueTaskViews(List.of(tissueTask), oppGroups, taskUsers, bottleList, storageInOutList, userAll);
    return list.getFirst();
  }

  public QueryResultPaged<ModelTissueTaskView> queryByCondition(QueryCondition<QueryConditionTissueTask> condition) {

    // 组织分页模型
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.DESC, "createTime")
    );

    QueryConditionTissueTask filter = condition.getCondition();

    // 提取查询参数
    String keyword = null;
    String likeKeyword = null;
    Integer explantCollectionBatch = null;
    String taskType = null;
    String taskStatus = null;
    Long createStart = null;
    Long createEnd = null;
    Long startStart = null;
    Long startEnd = null;
    Long finishStart = null;
    Long finishEnd = null;

    if (filter != null) {
      keyword = filter.getKeyword();
      if (keyword != null && !keyword.isBlank()) {
        likeKeyword = "%" + keyword.trim().toUpperCase() + "%";
      }

      explantCollectionBatch = filter.getExplantCollectionBatch();
      taskType = filter.getTaskType();
      taskStatus = filter.getTaskStatus();

      var createTimeRange = filter.getCreateTime();
      if (createTimeRange != null && createTimeRange.getStart() != null && createTimeRange.getStart() >= 1) {
        createStart = createTimeRange.getStart();
      }
      if (createTimeRange != null && createTimeRange.getEnd() != null && createTimeRange.getEnd() >= 1) {
        createEnd = createTimeRange.getEnd();
      }

      var startTimeRange = filter.getStartTime();
      if (startTimeRange != null && startTimeRange.getStart() != null && startTimeRange.getStart() >= 1) {
        startStart = startTimeRange.getStart();
      }
      if (startTimeRange != null && startTimeRange.getEnd() != null && startTimeRange.getEnd() >= 1) {
        startEnd = startTimeRange.getEnd();
      }

      var finishTimeRange = filter.getFinishTime();
      if (finishTimeRange != null && finishTimeRange.getStart() != null && finishTimeRange.getStart() >= 1) {
        finishStart = finishTimeRange.getStart();
      }
      if (finishTimeRange != null && finishTimeRange.getEnd() != null && finishTimeRange.getEnd() >= 1) {
        finishEnd = finishTimeRange.getEnd();
      }
    }

    // 执行分页查询并使用 Page.map 转换为 DTO
    Page<TissueTaskDTO> dtoPage = tissueTaskDAO.findTaskPageByCondition(
      keyword,
      likeKeyword,
      explantCollectionBatch,
      taskType,
      taskStatus,
      createStart,
      createEnd,
      startStart,
      startEnd,
      finishStart,
      finishEnd,
      pageable).map(TissueTaskDTO::new);
    List<TissueTaskDTO> tasks = dtoPage.getContent();

    // 更新分页模型。
    pageModel.updateTotal(dtoPage.getTotalElements());

    if (CollectionUtils.isEmpty(tasks)) {
      return new QueryResultPaged<>(Collections.emptyList(), pageModel);
    }

    List<String> taskIdList = tasks.stream().map(TissueTaskDTO::getId).toList();

    // 查询任务关联操作组
    var oppGroups = tissueTaskOppGroupDAO.findByIdTaskIn(taskIdList).stream().map(TissueTaskOppGroupDTO::new).toList();
    // 查询任务关联员工信息
    var taskUsers = tissueTaskUserDAO.findByIdTaskIn(taskIdList).stream().map(TissueTaskUserDTO::new).toList();
    // 查询培养瓶
    var bottleList = tissueTaskBottleDAO.findByIdTaskInOrderByIdBottleAsc(taskIdList).stream().map(TissueTaskBottleDTO::new).toList();
    // 查询出入库申请单
    var storageInOutList = storageInOutDAO.findByIdTaskInOrderByOperatorTimeDesc(taskIdList).stream().map(StorageInOutDTO::new).toList();

    // 查询所有员工信息
    var userAll = userDAO.findAllWithoutPassword();

    // 组织视图。
    var list = organizeTissueTaskViews(tasks, oppGroups, taskUsers, bottleList, storageInOutList, userAll);
    return new QueryResultPaged<>(list, pageModel);
  }

  @Transactional
  public String queryOrCreateCodePrefix(String taskId) {
    Assert.hasText(taskId, "组培任务ID不能为空");
    TissueTaskPO po = tissueTaskDAO.findById(taskId).orElse(null);
    if (po == null) {
      throw new ErrorMessageException("无法找到此任务信息。");
    }
    var codePrefix = po.getCodePrefix();
    if (codePrefix != null && !codePrefix.isBlank()) {
      return codePrefix;
    }
    var taskStatus = po.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : po.getTaskStatus();
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_CREATED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_CODED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_PROCESSING)) {
      throw new ErrorMessageException("该任务无瓶码前缀，但不处于执行状态，无法为其分配子瓶瓶码。");
    }
    codePrefix = bottleCodeSequenceManager.next();
    po.setCodePrefix(codePrefix);
    return codePrefix;
  }

  @Transactional
  public void createTask(TissueTask task, User currentUser) {
    Assert.notNull(task, "组培任务信息不能为空");
    Assert.notNull(task.getExplantCollectionBatch(), "外植体采集批次不能为空");
    Assert.hasText(task.getTaskType(), "任务类别不能为空");
    Assert.hasText(task.getVariety(), "品种不能为空");
    Assert.hasText(task.getVarietyCode(), "子品种编号不能为空");
    int explantCollectionBatch = task.getExplantCollectionBatch();
    if (!ExplantCollectionBatchUtil.check(explantCollectionBatch)) {
      throw new ErrorMessageException("外植体采集批次只能是0，或符合 YYYYMMDD 格式的整数，如 20260101");
    }
    if (task.getTaskType().equals(Const.TISSUE_TASK_TYPE_INDUCTION)) {
      Assert.notNull(task.getEstimatedParentPlants(), "预计使用母代株数不能为空");
      if (task.getEstimatedParentPlants() < 1) {
        throw new ErrorMessageException("预计使用母代株数不能小于0。");
      }
    } else {
      if (task.getEstimatedParentKeepBottles() != null && task.getEstimatedParentKeepBottles() < 0) {
        throw new ErrorMessageException("预计母代留种瓶数不能小于0。");
      }
      if (task.getEstimatedParentKeepPlants() != null && task.getEstimatedParentKeepPlants() < 0) {
        throw new ErrorMessageException("预计母代留种株数不能小于0。");
      }
    }
    if (!task.getTaskType().equals(Const.TISSUE_TASK_TYPE_ACCLIMATIZATION)) {
      Assert.notNull(task.getEstimatedChildrenBottles(), "预计生产子代瓶数不能为空");
      if (task.getEstimatedChildrenBottles() < 1) {
        throw new ErrorMessageException("预计生产子代瓶数不能小于0。");
      }
      Assert.notNull(task.getEstimatedChildrenPlants(), "预计生产子代株数不能为空");
      if (task.getEstimatedChildrenPlants() < 1) {
        throw new ErrorMessageException("预计生产子代株数不能小于0。");
      }
    }

    long now = System.currentTimeMillis();
    String id = Id.g();
    TissueTaskPO po = new TissueTaskPO();
    po.setId(id);
    po.setVariety(task.getVariety());
    po.setVarietyCode(task.getVarietyCode());
    po.setExplantCollectionBatch(task.getExplantCollectionBatch() == null ? 0 : task.getExplantCollectionBatch());
    po.setEstimatedParentBottles(task.getEstimatedParentBottles() == null ? 0 : task.getEstimatedParentBottles());
    po.setEstimatedParentPlants(task.getEstimatedParentPlants() == null ? 0 : task.getEstimatedParentPlants());
    po.setEstimatedParentKeepBottles(task.getEstimatedParentKeepBottles() == null ? 0 : task.getEstimatedParentKeepBottles());
    po.setEstimatedParentKeepPlants(task.getEstimatedParentKeepPlants() == null ? 0 : task.getEstimatedParentKeepPlants());
    po.setEstimatedChildrenBottles(task.getEstimatedChildrenBottles() == null ? 0 : task.getEstimatedChildrenBottles());
    po.setEstimatedChildrenPlants(task.getEstimatedChildrenPlants() == null ? 0 : task.getEstimatedChildrenPlants());
    po.setDefaultPlantsPerParentBottle(task.getDefaultPlantsPerParentBottle() == null ? 0 : task.getDefaultPlantsPerParentBottle());
    po.setDefaultPlantsPerChildBottle(task.getDefaultPlantsPerChildBottle() == null ? 0 : task.getDefaultPlantsPerChildBottle());
    po.setCodePrefix(task.getCodePrefix() == null ? StringUtil.EMPTY : task.getCodePrefix());
    po.setCreatorUserId(currentUser.getId());
    po.setCreatorUserName(currentUser.getName());
    po.setTaskType(task.getTaskType());
    po.setTaskStatus(Const.TISSUE_TASK_STATUS_CREATED);
    po.setCreateTime(now);
    po.setStartTime(0L);
    po.setFinishTime(0L);
    po.setModifiable(Const.BOOLEAN_0);
    tissueTaskDAO.save(po);

    // 为除了诱导任务外的任务自动创建出库申请单。
    if (!task.getTaskType().equals(Const.TISSUE_TASK_TYPE_INDUCTION)) {
      // 组织出库单
      var idStorageOut = Id.g();
      StorageInOutPO storagePO = new StorageInOutPO();
      storagePO.setId(idStorageOut);
      storagePO.setInOut(Const.STORAGE_IN_OUT_TYPE_OUT);
      // 注意，出库记作负数。
      storagePO.setBottlesApplied(0);
      storagePO.setPlantsApplied(0);
      storagePO.setBottlesKeepInApplied(0);
      storagePO.setPlantsKeepInApplied(0);
      storagePO.setBottlesActual(0);
      storagePO.setPlantsActual(0);
      storagePO.setBottlesKeepInActual(0);
      storagePO.setPlantsKeepInActual(0);
      storagePO.setBottlesDiscard(0);
      storagePO.setPlantsDiscard(0);
      storagePO.setBottlesVoided(0);
      storagePO.setPlantsVoided(0);
      storagePO.setIgnoreStats(Const.BOOLEAN_0);
      storagePO.setReason(task.getTaskType());
      storagePO.setStatus(Const.STORAGE_IN_OUT_STATUS_APPLY);
      storagePO.setOppReason(StringUtil.EMPTY);
      storagePO.setIdTask(id);
      storagePO.setIdInspection(StringUtil.EMPTY);
      storagePO.setApplicantId(currentUser.getId());
      storagePO.setApplicantName(currentUser.getName());
      storagePO.setApplicantTime(now);
      storagePO.setOperatorId(currentUser.getId());
      storagePO.setOperatorName(currentUser.getName());
      storagePO.setOperatorTime(now);

      this.storageInOutDAO.save(storagePO);

      // 组织出库单日志
      StorageInOutLogPO log = new StorageInOutLogPO();
      log.setId(Id.g());
      log.setIdInOut(idStorageOut);
      log.setStatus(Const.STORAGE_IN_OUT_STATUS_APPLY);
      log.setOppReason(StringUtil.EMPTY);
      log.setOperatorId(currentUser.getId());
      log.setOperatorName(currentUser.getName());
      log.setOperatorTime(now);

      this.storageInOutLogDAO.save(log);
    }
  }

  /**
   * 删除任务
   *
   * @param taskId 任务ID
   * @return 关联出入库单ID列表（记录已被删除，尚需删除附件材料）
   */
  @Transactional
  public List<String> deleteTask(String taskId) {
    Assert.hasText(taskId, "组培任务信息不能为空");
    TissueTaskPO po = tissueTaskDAO.findById(taskId).orElse(null);
    if (po == null) {
      throw new ErrorMessageException("无法找到此任务信息。");
    }

    String dbTaskStatus = po.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : po.getTaskStatus();

    if (!(dbTaskStatus.equals(Const.TISSUE_TASK_STATUS_CREATED) || dbTaskStatus.equals(Const.TISSUE_TASK_STATUS_CODED))) {
      throw new ErrorMessageException("只能删除尚未执行的任务信息。");
    }

    tissueTaskUserDAO.deleteBatchByIdTask(taskId);
    tissueTaskOppGroupDAO.deleteBatchByIdTask(taskId);
    tissueTaskBottleDAO.deleteBatchByIdTask(taskId);

    var storageInOutList = this.storageInOutDAO.findByIdTaskInOrderByOperatorTimeDesc(List.of(taskId));
    var idInOutList = storageInOutList.stream().map(StorageInOutPO::getId).toList();
    if (!storageInOutList.isEmpty()) {
      boolean hasProcessingStorageBisuness = storageInOutList.stream().anyMatch(storageInOut ->
        storageInOut.getStatus().equals(Const.STORAGE_IN_OUT_STATUS_DONE) || storageInOut.getStatus().equals(Const.STORAGE_IN_OUT_STATUS_FINISHED)
      );
      if (hasProcessingStorageBisuness) {
        throw new ErrorMessageException("尚有未完成处理的出入库业务，不能删除。");
      }
      storageInOutDAO.deleteAll(storageInOutList);
      storageInOutBottleDAO.deleteAllByIdInOutIn(idInOutList);
      storageInOutLogDAO.deleteAllByIdInOutIn(idInOutList);
    }

    tissueTaskDAO.deleteById(taskId);
    return idInOutList;
  }

  @Transactional
  public ModelTissueTaskView updateTask(TissueTask task) {
    Assert.notNull(task, "组培任务信息不能为空");
    Assert.hasText(task.getId(), "组培任务ID不能为空");
    Assert.notNull(task.getExplantCollectionBatch(), "外植体采集批次不能为空");
    Assert.hasText(task.getTaskType(), "任务类别不能为空");
    Assert.hasText(task.getVariety(), "品种不能为空");
    Assert.hasText(task.getVarietyCode(), "子品种编号不能为空");

    int explantCollectionBatch = task.getExplantCollectionBatch();
    if (!ExplantCollectionBatchUtil.check(explantCollectionBatch)) {
      throw new ErrorMessageException("外植体采集批次只能是0，或符合 YYYYMMDD 格式的整数，如 20260101");
    }
    if (task.getTaskType().equals(Const.TISSUE_TASK_TYPE_INDUCTION)) {
      Assert.notNull(task.getEstimatedParentPlants(), "预计使用母代株数不能为空");
      if (task.getEstimatedParentPlants() < 1) {
        throw new ErrorMessageException("预计使用母代株数不能小于0。");
      }
    } else {
      if (task.getEstimatedParentKeepBottles() != null && task.getEstimatedParentKeepBottles() < 0) {
        throw new ErrorMessageException("预计母代留种瓶数不能小于0。");
      }
      if (task.getEstimatedParentKeepPlants() != null && task.getEstimatedParentKeepPlants() < 0) {
        throw new ErrorMessageException("预计母代留种株数不能小于0。");
      }
    }
    if (!task.getTaskType().equals(Const.TISSUE_TASK_TYPE_ACCLIMATIZATION)) {
      Assert.notNull(task.getEstimatedChildrenBottles(), "预计生产子代瓶数不能为空");
      if (task.getEstimatedChildrenBottles() < 1) {
        throw new ErrorMessageException("预计生产子代瓶数不能小于0。");
      }
      Assert.notNull(task.getEstimatedChildrenPlants(), "预计生产子代株数不能为空");
      if (task.getEstimatedChildrenPlants() < 1) {
        throw new ErrorMessageException("预计生产子代株数不能小于0。");
      }
    }

    TissueTaskPO po = tissueTaskDAO.findById(task.getId()).orElse(null);
    if (po == null) {
      throw new ErrorMessageException("无法找到此任务信息。");
    }

    String dbTaskStatus = po.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : po.getTaskStatus();
    String modifiable = po.getModifiable() == null ? Const.BOOLEAN_0 : po.getModifiable();

    if (!dbTaskStatus.equals(Const.TISSUE_TASK_STATUS_CREATED) && !modifiable.equals(Const.BOOLEAN_1)) {
      throw new ErrorMessageException("只能更改尚未执行的任务信息。");
    }

    // 业务字段更新
    po.setVariety(task.getVariety());
    po.setVarietyCode(task.getVarietyCode());
    po.setExplantCollectionBatch(task.getExplantCollectionBatch() == null ? 0 : task.getExplantCollectionBatch());
    if (task.getTaskType().equals(Const.TISSUE_TASK_TYPE_INDUCTION)) {
      po.setEstimatedParentPlants(task.getEstimatedParentPlants() == null ? 0 : task.getEstimatedParentPlants());
    }
    po.setEstimatedParentKeepBottles(task.getEstimatedParentKeepBottles() == null ? 0 : task.getEstimatedParentKeepBottles());
    po.setEstimatedParentKeepPlants(task.getEstimatedParentKeepPlants() == null ? 0 : task.getEstimatedParentKeepPlants());
    po.setEstimatedChildrenBottles(task.getEstimatedChildrenBottles() == null ? 0 : task.getEstimatedChildrenBottles());
    po.setEstimatedChildrenPlants(task.getEstimatedChildrenPlants() == null ? 0 : task.getEstimatedChildrenPlants());
    po.setDefaultPlantsPerParentBottle(task.getDefaultPlantsPerParentBottle() == null ? 0 : task.getDefaultPlantsPerParentBottle());
    po.setDefaultPlantsPerChildBottle(task.getDefaultPlantsPerChildBottle() == null ? 0 : task.getDefaultPlantsPerChildBottle());
    po.setCodePrefix(task.getCodePrefix() == null ? StringUtil.EMPTY : task.getCodePrefix());
    po.setTaskType(task.getTaskType());
    po.setTaskStatus(task.getTaskStatus() == null ? dbTaskStatus : task.getTaskStatus());
    po.setCreateTime(task.getCreateTime() == null ? System.currentTimeMillis() : task.getCreateTime());
    po.setStartTime(task.getStartTime() == null ? 0 : task.getStartTime());
    po.setFinishTime(task.getFinishTime() == null ? 0 : task.getFinishTime());
    po.setModifiable(modifiable);

    return this.queryByTaskId(task.getId());
  }

  @Transactional
  public ModelTissueTaskView printBottleCode(TissueTask task, String codePrefix, Integer bottleCount) {
    Assert.notNull(task, "组培任务信息不能为空");
    Assert.hasText(codePrefix, "瓶码前缀（六位英文大写字母）不能为空");
    if (bottleCount == null || bottleCount < 1 || bottleCount > 9999) {
      throw new ErrorMessageException("子代培养瓶数量必须大于 0 且小于 9999");
    }
    Assert.hasText(task.getVarietyCode(), "子品种编号不能为空");
    TissueTaskPO po = tissueTaskDAO.findById(task.getId()).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到此任务信息。");
    var taskStatus = po.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : po.getTaskStatus();
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_CREATED)) {
      throw new ErrorMessageException("只能为尚未执行的任务打印瓶码。");
    }

    // 更新任务状态。在执行批量插入前更新任务状态，防止成为游离态。
    po.setTaskStatus(Const.TISSUE_TASK_STATUS_CODED);
    po.setCodePrefix(codePrefix);

    List<TissueTaskBottlePO> taskBottles = new ArrayList<>();
    for (int index = 1; index <= bottleCount; index++) {
      final String bottleId = codePrefix + "-" + String.format("%04d", index);
      // 新建任务培养瓶信息。
      TissueTaskBottlePO taskBottle = new TissueTaskBottlePO();
      taskBottle.setIdTask(task.getId());
      taskBottle.setIdOppGroup(StringUtil.EMPTY);
      taskBottle.setIdBottle(bottleId);
      taskBottle.setPlantCountOriginal(0);
      taskBottle.setPlantCountActual(0);
      taskBottle.setRoleInTask(Const.BOTTLE_TASK_ROLE_CHILD);
      taskBottle.setParentKeep(Const.BOOLEAN_0);
      taskBottle.setParentKeepPlantCount(0);
      taskBottle.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_CODED);
      taskBottle.setAllocateTime(0L);
      taskBottle.setFinishTime(0L);
      taskBottle.setInspectorId(StringUtil.EMPTY);
      taskBottle.setInspectorName(StringUtil.EMPTY);
      taskBottle.setOperatorId(StringUtil.EMPTY);
      taskBottle.setOperatorName(StringUtil.EMPTY);
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

    return this.queryByTaskId(po.getId());
  }

  @Transactional
  public ModelPrintBottleCodeForAddBottles printBottleCodeForAddBottles(String idTask, Integer bottleCount) {
    Assert.hasText(idTask, "组培任务信息不能为空");
    TissueTaskPO po = tissueTaskDAO.findById(idTask).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到此任务信息。");
    var taskStatus = po.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : po.getTaskStatus();
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_CREATED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_CODED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_PROCESSING)) {
      throw new ErrorMessageException("只能为正在执行的任务打印瓶码。");
    }
    Assert.hasText(po.getCodePrefix(), "组培任务瓶码前缀（六位英文大写字母）不能为空");
    if (bottleCount == null || bottleCount < 1) {
      throw new ErrorMessageException("打印瓶码数量必须大于 0");
    }

    // 更新任务状态。
    po.setTaskStatus(Const.TISSUE_TASK_STATUS_PROCESSING);

    // 已有瓶码的最大序号。
    int maxCodeNumber = 0;

    // 查询最大瓶码
    Page<TissueTaskBottlePO> page = tissueTaskBottleDAO.queryChildBottleMaxCode(po.getId(), PageRequest.of(0, 1));
    TissueTaskBottlePO maxCodePO = page.hasContent() ? page.getContent().getFirst() : null;
    if (maxCodePO != null) {
      String maxCode = maxCodePO.getIdBottle();
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

    var codePrefix = po.getCodePrefix();
    var codeStart = StringUtil.EMPTY;
    var codeEnd = StringUtil.EMPTY;
    List<TissueTaskBottlePO> taskBottles = new ArrayList<>();
    for (int index = 1; index <= bottleCount; index++) {
      final String bottleId = codePrefix + "-" + String.format("%04d", index + maxCodeNumber);
      if (index == 1) {
        codeStart = bottleId;
      }
      codeEnd = bottleId;
      // 新建任务培养瓶信息。
      TissueTaskBottlePO taskBottle = new TissueTaskBottlePO();
      taskBottle.setIdTask(po.getId());
      taskBottle.setIdOppGroup(StringUtil.EMPTY);
      taskBottle.setIdBottle(bottleId);
      taskBottle.setPlantCountOriginal(0);
      taskBottle.setPlantCountActual(0);
      taskBottle.setRoleInTask(Const.BOTTLE_TASK_ROLE_CHILD);
      taskBottle.setParentKeep(Const.BOOLEAN_0);
      taskBottle.setParentKeepPlantCount(0);
      taskBottle.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_CODED);
      taskBottle.setAllocateTime(0L);
      taskBottle.setFinishTime(0L);
      taskBottle.setInspectorId(StringUtil.EMPTY);
      taskBottle.setInspectorName(StringUtil.EMPTY);
      taskBottle.setOperatorId(StringUtil.EMPTY);
      taskBottle.setOperatorName(StringUtil.EMPTY);
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
    result.setTaskView(this.queryByTaskId(po.getId()));
    result.setCodeStart(codeStart);
    result.setCodeEnd(codeEnd);
    return result;
  }

  @Transactional
  public ModelTissueTaskView saveProcessSettings(String taskId, List<? extends TissueTaskUser> users, int defaultPlantsPerParentBottle, int defaultPlantsPerChildBottle) {
    Assert.hasText(taskId, "组培任务信息不能为空");
    if (defaultPlantsPerParentBottle < 0 || defaultPlantsPerParentBottle > 999) {
      throw new ErrorMessageException("默认母代培养瓶每瓶株数必须大于或等于 0 且小于 999");
    }
    if (defaultPlantsPerChildBottle < 0 || defaultPlantsPerChildBottle > 999) {
      throw new ErrorMessageException("默认子代培养瓶每瓶株数必须大于或等于 0 且小于 999");
    }
    TissueTaskPO po = tissueTaskDAO.findById(taskId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到此任务信息。");
    var taskStatus = po.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : po.getTaskStatus();
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_CREATED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_CODED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_PROCESSING)) {
      throw new ErrorMessageException("只能为尚未执行、已打码或正在执行的任务配置执行参数。");
    }

    // 设置每瓶默认株数。在执行批量插入前更新任务状态，防止成为游离态。
    po.setDefaultPlantsPerParentBottle(defaultPlantsPerParentBottle);
    po.setDefaultPlantsPerChildBottle(defaultPlantsPerChildBottle);

    // 删除任务相关员工
    tissueTaskUserDAO.deleteBatchByIdTask(taskId);

    // 组织任务相关员工数据
    var taskUsers = users.stream().filter(
      u -> u.getIdUser() != null && !u.getIdUser().isBlank() && u.getRole() != null && !u.getRole().isBlank()
    ).map(u -> {
      TissueTaskUserPO taskUser = new TissueTaskUserPO(u);
      taskUser.setIdTask(taskId);
      return taskUser;
    }).toList();

    // 批量插入任务相关员工数据
    if (!taskUsers.isEmpty()) {
      final int batchSize = 500;
      for (int i = 0; i < taskUsers.size(); i++) {
        entityManager.persist(taskUsers.get(i));
        if ((i + 1) % batchSize == 0) {
          entityManager.flush();
          entityManager.clear();
        }
      }
      entityManager.flush();
      entityManager.clear();
    }

    return this.queryByTaskId(po.getId());
  }


  @Transactional
  public void deleteStorageOut(@NonNull StorageOutTaskView model) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getId(), "出库信息不能为空");
    Assert.hasText(model.getIdTask(), "组培任务信息不能为空");
    var taskId = model.getIdTask();

    TissueTaskPO task = tissueTaskDAO.findById(taskId).orElse(null);
    if (task == null) throw new ErrorMessageException("无法找到此任务信息。");
    var taskStatus = task.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : task.getTaskStatus();
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_CREATED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_CODED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_PROCESSING)) {
      throw new ErrorMessageException("只能为尚未执行、已打码或正在执行的任务办理出库业务。");
    }

    var po = storageInOutDAO.findById(model.getId()).orElse(null);
    if (po == null || po.getStatus() == null || po.getId() == null) {
      throw new ErrorMessageException("无法获得出库信息");
    }
    if (
      po.getStatus().equals(Const.STORAGE_IN_OUT_STATUS_DONE) || po.getStatus().equals(Const.STORAGE_IN_OUT_STATUS_FINISHED)
    ) {
      throw new ErrorMessageException("无法删除此出库申请");
    }

    storageInOutDAO.delete(po);
    storageInOutBottleDAO.deleteAllByIdInOut(po.getId());
    storageInOutLogDAO.deleteAllByIdInOutIn(List.of(po.getId()));

    // 同时删除任务
    tissueTaskUserDAO.deleteBatchByIdTask(taskId);
    tissueTaskOppGroupDAO.deleteBatchByIdTask(taskId);
    tissueTaskBottleDAO.deleteBatchByIdTask(taskId);
    tissueTaskDAO.deleteById(taskId);
  }

  @Transactional
  public ModelTissueTaskView acceptStorageOut(@NonNull ModelStorageOutTaskProcessDetail model, @NonNull User currentUser) {
    Assert.notNull(model, "出库信息不能为空");
    Assert.hasText(model.getInOutId(), "出库信息不能为空");
    Assert.hasText(model.getIssueTaskId(), "出库对应的组培任务不能为空");
    String inOutId = model.getInOutId();

    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null) throw new ErrorMessageException("无法找到出库申请信息");
    var status = po.getStatus();
    if (status == null || status.isBlank()) throw new ErrorMessageException("出库业务状态错误");
    if (!status.equals(Const.STORAGE_IN_OUT_STATUS_DONE))
      throw new ErrorMessageException("当前出库尚未备出，无法接收。");

    String issueTaskId = model.getIssueTaskId();
    if (!issueTaskId.equals(po.getIdTask())) throw new ErrorMessageException("无法找到组培任务信息");
    var task = tissueTaskDAO.findById(issueTaskId).orElse(null);
    if (task == null) throw new ErrorMessageException("无法找到组培任务信息");
    if (
      task.getTaskStatus().equals(Const.TISSUE_TASK_STATUS_CANCELED)
        || task.getTaskStatus().equals(Const.TISSUE_TASK_STATUS_FINISHED)
    ) throw new ErrorMessageException("组培任务已结束，无法为其办理出库业务");

    // 末次操作人便是出库人
    String issuedUserId = po.getOperatorId();
    String issuedUserName = po.getOperatorName();

    var usedList = model.getUsedList();
    if (usedList != null && !usedList.isEmpty()) {
      for (var m : usedList) {
        if (m.getPlantCountActual() == null || m.getPlantCountActual() < 1)
          throw new ErrorMessageException("出库培养瓶存疑，" + m.getIdBottle() + "的株数错误，必须大于零。");
      }
      // 校验库内培养瓶
      long bottleCount = bottleDAO.countBottles1(
        task.getVariety(),
        task.getVarietyCode(),
        Const.BOTTLE_STORAGE_STATUS_IN,
        Const.BOOLEAN_0,
        usedList.stream().map(ModelStorageInOutDetail::getIdBottle).toList()
      );
      if (bottleCount != usedList.size()) {
        throw new ErrorMessageException("出库培养瓶存疑，请与库房核实。");
      }
      // 校验是否已全部核对出库
      var bottles = this.storageInOutBottleDAO.findByIdInOutOrderByIdBottleAsc(inOutId);
      if (usedList.size() != bottles.size()) {
        throw new ErrorMessageException("尚未全部接收，无法完成。若实际出库与实际用量不符，应驳回库房，与库房协调按实际用量出库。");
      }
      var bottleIdSet = new HashSet<String>();
      for (var b : bottles) {
        bottleIdSet.add(b.getIdBottle());
      }
      for (var b : usedList) {
        if (!bottleIdSet.contains(b.getIdBottle())) {
          throw new ErrorMessageException(b.getIdBottle() + "不是本次出库母瓶。");
        }
      }
    } else {
      throw new ErrorMessageException("实际未出库，请与库房核实。");
    }

    // 计算瓶数/株数/培养瓶操作日志/自动调差
    int plantActual = 0;
    int plantApply = 0;
    int bottlesActual = 0;
    List<BottleLogPO> bottleLogs = new ArrayList<>();
    var now = System.currentTimeMillis();
    String reason = dictionaryCache.getName("storage_reason", po.getReason());
    String inOutType = dictionaryCache.getName("storage_in_out_type", po.getInOut());
    String logStorageBizType = reason + inOutType;
    String logTaskBizType = dictionaryCache.getName("tissue_task_type", task.getTaskType());
    // 组织关联瓶信息/任务母瓶信息
    var inOutBottles = new ArrayList<StorageInOutBottlePO>();
    var taskBottles = new ArrayList<TissueTaskBottlePO>();
    // 库存流水。bizType/generation/
    var inventoryTransactionPOMap = new HashMap<String, Map<Integer, InventoryTransactionPO>>();

    // 查询出库瓶
    var bottlePOs = bottleDAO.findAllByIdIn(usedList.stream().map(ModelStorageInOutDetail::getIdBottle).toList());
    var bottlePOMap = new HashMap<String, BottlePO>();
    for (var bottlePO : bottlePOs) {
      bottlePOMap.put(bottlePO.getId(), bottlePO);
    }

    for (var used : usedList) {
      var bottle = bottlePOMap.get(used.getIdBottle());
      if (bottle == null) continue;
      // 关联瓶信息
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
      b.setStatus(Const.STORAGE_IN_OUT_BOTTLE_STATUS_DONE);
      b.setDiscardReason(StringUtil.EMPTY);
      inOutBottles.add(b);
      // 任务母瓶信息
      var tb = new TissueTaskBottlePO();
      tb.setIdTask(task.getId());
      tb.setIdOppGroup(StringUtil.EMPTY);
      tb.setIdBottle(used.getIdBottle());
      tb.setExplantCollectionBatch(bottle.getExplantCollectionBatch());
      tb.setGeneration(bottle.getGeneration());
      tb.setPlantCountOriginal(used.getPlantCountActual());
      tb.setPlantCountActual(used.getPlantCountActual());
      tb.setRoleInTask(Const.BOTTLE_TASK_ROLE_PARENT);
      tb.setParentKeep(Const.BOOLEAN_0);
      tb.setParentKeepPlantCount(0);
      tb.setParentLastTaskType(bottle.getSourceType());
      tb.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_CODED);
      tb.setAllocateTime(0L);
      tb.setFinishTime(0L);
      tb.setInspectorId(StringUtil.EMPTY);
      tb.setInspectorName(StringUtil.EMPTY);
      tb.setOperatorId(StringUtil.EMPTY);
      tb.setOperatorName(StringUtil.EMPTY);
      taskBottles.add(tb);

      plantApply += bottle.getPlantCount();
      plantActual += used.getPlantCountActual();
      bottlesActual++;

      // 组培瓶操作日志(库存)
      BottleLogPO logForStorage = new BottleLogPO();
      logForStorage.setId(Id.g());
      logForStorage.setIdBottle(used.getIdBottle());
      logForStorage.setOppTime(now);
      logForStorage.setTableName(StorageInOut.TB_NAME);
      logForStorage.setRecordId(po.getId());
      logForStorage.setBizType(logStorageBizType);
      if (Objects.equals(used.getPlantCountActual(), used.getPlantCountApplied())) {
        logForStorage.setDescription(StringUtil.EMPTY);
      } else {
        logForStorage.setDescription("出库调差：原" + used.getPlantCountApplied() + "株，实际出库" + used.getPlantCountActual() + "株。");
      }
      logForStorage.setInspectorId(StringUtil.EMPTY);
      logForStorage.setInspectorName(StringUtil.EMPTY);
      logForStorage.setOperatorId(issuedUserId);
      logForStorage.setOperatorName(issuedUserName);
      logForStorage.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_NONE);
      logForStorage.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
      logForStorage.setStorageReason(task.getTaskType());
      logForStorage.setDiscarded(Const.BOOLEAN_0);
      logForStorage.setDiscardReason(StringUtil.EMPTY);
      // 向待处理列表中插入组培瓶操作日志信息
      bottleLogs.add(logForStorage);

      // 组培瓶操作日志(任务)
      BottleLogPO logForTask = new BottleLogPO();
      logForTask.setId(Id.g());
      logForTask.setIdBottle(used.getIdBottle());
      logForTask.setOppTime(now + 1); // +1毫秒 方便排序
      logForTask.setTableName(StorageInOut.TB_NAME);
      logForTask.setRecordId(po.getId());
      logForTask.setBizType(logTaskBizType + " - 母瓶领受进场");
      logForTask.setDescription("领受株数：" + used.getPlantCountActual());
      logForTask.setInspectorId(StringUtil.EMPTY);
      logForTask.setInspectorName(StringUtil.EMPTY);
      logForTask.setOperatorId(currentUser.getId());
      logForTask.setOperatorName(currentUser.getName());
      logForTask.setOperateStatus(Const.BOTTLE_OPERATE_STATUS_CODED);
      logForTask.setStorageStatus(Const.BOTTLE_STORAGE_STATUS_OUT);
      logForTask.setStorageReason(task.getTaskType());
      logForTask.setDiscarded(Const.BOOLEAN_0);
      logForTask.setDiscardReason(StringUtil.EMPTY);
      // 向待处理列表中插入组培瓶操作日志信息
      bottleLogs.add(logForTask);

      // 株数调差
      if (!Objects.equals(bottle.getPlantCount(), used.getPlantCountActual())) {
        var transactionPOModel = inventoryTransactionPOMap.computeIfAbsent(Const.INVENTORY_TX_BIZ_TYPE_ADJUSTMENT, key -> new HashMap<>())
          .computeIfAbsent(bottle.getGeneration(), key -> {
            InventoryTransactionPO transactionPO = new InventoryTransactionPO();
            transactionPO.setId(Id.g());
            transactionPO.setBizType(Const.INVENTORY_TX_BIZ_TYPE_ADJUSTMENT);
            transactionPO.setTableName(StorageInOut.TB_NAME);
            transactionPO.setRecordId(po.getId());
            transactionPO.setVariety(bottle.getVariety());
            transactionPO.setVarietyCode(bottle.getVarietyCode());
            transactionPO.setGeneration(bottle.getGeneration());
            transactionPO.setBottles(0);
            transactionPO.setPlants(0);
            transactionPO.setDescription(logTaskBizType + " - 母瓶出库自动调差");
            transactionPO.setOperatorId(issuedUserId);
            transactionPO.setOperatorName(issuedUserName);
            transactionPO.setOperateTime(now);
            return transactionPO;
          });
        transactionPOModel.setPlants(transactionPOModel.getPlants() + used.getPlantCountActual() - bottle.getPlantCount());
        // 更改组培瓶株数
        bottle.setPlantCount(used.getPlantCountActual());
      }

      // 出库流水
      var transactionPOModel = inventoryTransactionPOMap.computeIfAbsent(Const.INVENTORY_TX_BIZ_TYPE_OUT, key -> new HashMap<>())
        .computeIfAbsent(bottle.getGeneration(), key -> {
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
          transactionPO.setDescription(logTaskBizType + " - 母瓶出库");
          transactionPO.setOperatorId(issuedUserId);
          transactionPO.setOperatorName(issuedUserName);
          transactionPO.setOperateTime(now);
          return transactionPO;
        });
      transactionPOModel.setPlants(transactionPOModel.getPlants() - used.getPlantCountActual());
      transactionPOModel.setBottles(transactionPOModel.getBottles() - 1);
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

    // 更新培养瓶状态
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaUpdate<BottlePO> cu = cb.createCriteriaUpdate(BottlePO.class);
    Root<BottlePO> root = cu.from(BottlePO.class);
    cu.set(root.get(Bottle.BP_NAME_CURRENTIDTASK), task.getId());
    cu.set(root.get(Bottle.BP_NAME_CURRENTIDOPPGROUP), StringUtil.EMPTY);
    cu.set(root.get(Bottle.BP_NAME_CURRENTCULTURESTAGE), task.getTaskType());
    cu.set(root.get(Bottle.BP_NAME_CURRENTROLEINTASK), Const.BOTTLE_TASK_ROLE_PARENT);
    cu.set(root.get(Bottle.BP_NAME_CURRENTPARENTKEEP), Const.BOOLEAN_0);
    cu.set(root.get(Bottle.BP_NAME_CURRENTPARENTKEEPPLANTCOUNT), 0);
    cu.set(root.get(Bottle.BP_NAME_CURRENTOPERATESTATUS), Const.BOTTLE_OPERATE_STATUS_CODED);
    cu.set(root.get(Bottle.BP_NAME_CURRENTINSPECTORID), StringUtil.EMPTY);
    cu.set(root.get(Bottle.BP_NAME_CURRENTINSPECTORNAME), StringUtil.EMPTY);
    cu.set(root.get(Bottle.BP_NAME_CURRENTOPERATORID), StringUtil.EMPTY);
    cu.set(root.get(Bottle.BP_NAME_CURRENTOPERATORNAME), StringUtil.EMPTY);
    cu.set(root.get(Bottle.BP_NAME_CURRENTSTORAGESTATUS), Const.BOTTLE_STORAGE_STATUS_OUT);
    cu.set(root.get(Bottle.BP_NAME_LASTISSUEDUSERID), issuedUserId);
    cu.set(root.get(Bottle.BP_NAME_LASTISSUEDUSERNAME), issuedUserName);
    cu.set(root.get(Bottle.BP_NAME_LASTISSUEDTIME), now);
    cu.set(root.get(Bottle.BP_NAME_LASTISSUEDREASON), task.getTaskType());
    cu.where(root.get(Bottle.BP_NAME_ID).in(usedList.stream().map(ModelStorageInOutDetail::getIdBottle).toList()));
    entityManager.createQuery(cu).executeUpdate();

    // 删除所有关联瓶
    this.storageInOutBottleDAO.deleteAllByIdInOut(inOutId);
    entityManager.flush();
    entityManager.clear();

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

    // 插入任务瓶信息
    for (int i = 0; i < taskBottles.size(); i++) {
      entityManager.persist(taskBottles.get(i));
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

    // 组织仓储流水
    var transactionList = new ArrayList<InventoryTransactionPO>();
    var bizTypes = inventoryTransactionPOMap.keySet();
    for (var bizType : bizTypes) {
      var generations = inventoryTransactionPOMap.get(bizType).keySet();
      for (var generation : generations) {
        transactionList.add(inventoryTransactionPOMap.get(bizType).get(generation));
      }
    }
    // 插入仓储流水
    for (int i = 0; i < transactionList.size(); i++) {
      entityManager.persist(transactionList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    return this.queryByTaskId(issueTaskId);
  }

  @Transactional
  public ModelTissueTaskView saveProcess(ModelTissueTaskProcessDetail processDetail, User currentUser) {
    Assert.notNull(processDetail, "任务执行信息不能为空");
    Assert.notNull(processDetail.getTask(), "组培任务信息不能为空");
    Assert.hasText(processDetail.getTask().getId(), "组培任务信息不能为空");

    var taskPO = tissueTaskDAO.findById(processDetail.getTask().getId()).orElse(null);
    if (taskPO == null) {
      throw new ErrorMessageException("无法找到组培任务信息。");
    }
    var taskStatus = taskPO.getTaskStatus();
    if (taskStatus == null) {
      throw new ErrorMessageException("组培任务信息状态错误。");
    }
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_CREATED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_CODED) && !taskStatus.equals(Const.TISSUE_TASK_STATUS_PROCESSING)) {
      throw new ErrorMessageException("不能保存未在执行阶段的组培任务信息。");
    }

    long now = System.currentTimeMillis();

    // 设置任务状态
    taskPO.setTaskStatus(Const.TISSUE_TASK_STATUS_PROCESSING);
    if (taskPO.getStartTime() == null || taskPO.getStartTime() < 1) {
      taskPO.setStartTime(now);
    }
    // 有可能需要设置瓶码前缀
    if ((taskPO.getCodePrefix() == null || taskPO.getCodePrefix().isBlank()) && processDetail.getTask().getCodePrefix() != null && !processDetail.getTask().getCodePrefix().isBlank()) {
      taskPO.setCodePrefix(processDetail.getTask().getCodePrefix().trim().toUpperCase());
    }

    // 整理作业组信息
    var oppGroupMap = new HashMap<String, TissueTaskOppGroupPO>();
    var oppGroupList = processDetail.getOppGroup();
    if (oppGroupList != null) {
      oppGroupList.forEach(oppGroup -> {
        TissueTaskOppGroupPO oppGroupPO = new TissueTaskOppGroupPO(oppGroup);
        oppGroupMap.put(oppGroupPO.getId(), oppGroupPO);
        oppGroupPO.setIdTask(taskPO.getId());
        oppGroupPO.setId(oppGroupPO.getId() == null ? Id.g() : oppGroupPO.getId());
        oppGroupPO.setAllocateTime(oppGroupPO.getAllocateTime() == null || oppGroupPO.getAllocateTime() < 1 ? now : oppGroupPO.getAllocateTime());
        oppGroupPO.setAllocatorId(oppGroupPO.getAllocatorId() == null ? currentUser.getId() : oppGroupPO.getAllocatorId());
        oppGroupPO.setAllocatorName(oppGroupPO.getAllocatorId() == null ? currentUser.getName() : oppGroupPO.getAllocatorId());
      });
    }
    var oppGroupPOList = oppGroupMap.values().stream().toList();

    // 整理组培瓶信息
    var bottleMap = new HashMap<String, TissueTaskBottlePO>();
    var bottleList = processDetail.getBottles();
    if (bottleList != null) {
      bottleList.forEach(bottle -> {
        TissueTaskBottlePO bottlePO = new TissueTaskBottlePO(bottle);
        bottleMap.put(bottlePO.getIdBottle(), bottlePO);
        bottlePO.setIdTask(taskPO.getId());
        if (bottlePO.getIdOppGroup() != null && !bottlePO.getIdOppGroup().isBlank()) {
          String idOppGroup = bottlePO.getIdOppGroup();
          if (oppGroupMap.get(idOppGroup) == null) {
            bottlePO.setIdOppGroup(StringUtil.EMPTY);
          }
        }
      });
    }
    var bottlePOList = bottleMap.values().stream().toList();

    // 删除作业组信息
    tissueTaskOppGroupDAO.deleteBatchByIdTask(taskPO.getId());
    // 删除培养瓶信息
    tissueTaskBottleDAO.deleteBatchByIdTask(taskPO.getId());

    // 插入作业组信息
    final int batchSize = 500;
    for (int i = 0; i < oppGroupPOList.size(); i++) {
      entityManager.persist(oppGroupPOList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();
    // 插入培养瓶数据
    for (int i = 0; i < bottlePOList.size(); i++) {
      entityManager.persist(bottlePOList.get(i));
      if ((i + 1) % batchSize == 0) {
        entityManager.flush();
        entityManager.clear();
      }
    }
    entityManager.flush();
    entityManager.clear();

    return this.queryByTaskId(processDetail.getTask().getId());
  }

  @Transactional
  public ModelTissueTaskView updateTaskStatusToFinish(String idTask) {
    Assert.hasText(idTask, "组培任务信息不能为空");

    var taskPO = tissueTaskDAO.findById(idTask).orElse(null);
    if (taskPO == null) {
      throw new ErrorMessageException("无法找到组培任务信息。");
    }
    var taskStatus = taskPO.getTaskStatus();
    if (taskStatus == null) {
      throw new ErrorMessageException("组培任务信息状态错误。");
    }
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_PROCESSING)) {
      throw new ErrorMessageException("只能处理正在执行中的任务。");
    }

    // 查询是否还有执行中的工作组
    var unFinishedOppGroupCount = tissueTaskOppGroupDAO.countByIdTaskAndFinishTime(idTask, 0L);
    if (unFinishedOppGroupCount > 0L) {
      throw new ErrorMessageException("尚有未完成的作业组，无法完成该任务。");
    }

    // 校验附件材料
    if (taskPO.getTaskType().equals(Const.TISSUE_TASK_TYPE_INDUCTION)) {
      var archiveCount = archiveDAO.countByTableNameAndRecordIdAndDescription(TissueTask.TB_NAME, idTask, "执行任务");
      if (archiveCount < 1L) throw new ErrorMessageException("尚未上传附件材料。");
    }

    long now = System.currentTimeMillis();

    taskPO.setTaskStatus(Const.TISSUE_TASK_STATUS_FINISHED);
    taskPO.setFinishTime(now);

    return this.queryByTaskId(idTask);
  }

  @Transactional
  public ModelTissueTaskView updateTaskStatusFromFinishToProcess(String idTask) {
    Assert.hasText(idTask, "组培任务信息不能为空");

    var taskPO = tissueTaskDAO.findById(idTask).orElse(null);
    if (taskPO == null) {
      throw new ErrorMessageException("无法找到组培任务信息。");
    }
    var taskStatus = taskPO.getTaskStatus();
    if (taskStatus == null) {
      throw new ErrorMessageException("组培任务信息状态错误。");
    }
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_FINISHED)) {
      throw new ErrorMessageException("只能处理正在已完成的任务。");
    }

    // 查询是否还有执行中的入库业务
    var unFinishedStorageInCount = storageInOutDAO.countByIdTaskAndInOut(idTask, Const.STORAGE_IN_OUT_TYPE_IN);
    if (unFinishedStorageInCount > 0L) {
      throw new ErrorMessageException("该任务已办理过入库业务，应与库房负责人联系，删除所有入库业务后再恢复任务为执行状态。");
    }

    taskPO.setTaskStatus(Const.TISSUE_TASK_STATUS_PROCESSING);
    taskPO.setFinishTime(0L);

    return this.queryByTaskId(idTask);
  }

  @Transactional
  public ModelTissueTaskView applyStorageIn(@NonNull String taskId, @NonNull User currentUser) {
    Assert.hasText(taskId, "组培任务信息不能为空");
    TissueTaskPO task = tissueTaskDAO.findById(taskId).orElse(null);
    if (task == null) throw new ErrorMessageException("无法找到此任务信息。");
    var taskStatus = task.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : task.getTaskStatus();
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_FINISHED)) {
      throw new ErrorMessageException("只能为已完成的任务申请入库。");
    }

    // 查询是否已有执行中的入库业务
    var poList = storageInOutDAO.findByIdTaskAndInOut(taskId, Const.STORAGE_IN_OUT_TYPE_IN);
    StorageInOutPO po = new StorageInOutPO();
    po.setId(Id.g());
    if (poList != null && !poList.isEmpty()) {
      po = poList.getFirst();
      var status = po.getStatus();
      if (!status.equals(Const.STORAGE_IN_OUT_STATUS_APPLY) && !status.equals(Const.STORAGE_IN_OUT_STATUS_REJ_BIZ)) {
        throw new ErrorMessageException("无法修改此入库申请。");
      }
    }

    // 查询培养瓶信息
    var bottles = tissueTaskBottleDAO.findByIdTaskInOrderByIdBottleAsc(List.of(taskId));
    int bottlesCount = 0;
    int plantCount = 0;
    int keepBottles = 0;
    int keepPlant = 0;
    if (bottles != null) {
      for (var bottle : bottles) {
        var inOutBottleType = bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT) && bottle.getParentKeep().equals(Const.BOOLEAN_1)
          ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_KEEP_IN
          : bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT) && bottle.getIdOppGroup().isBlank()
          ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN
          : bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_CHILD) && bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_FINISHED)
          ? Const.STORAGE_IN_OUT_BOTTLE_TYPE_CHILD_IN
          : StringUtil.EMPTY;
        if (!inOutBottleType.isBlank()) {
          bottlesCount++;
          if (Const.STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_KEEP_IN.equals(inOutBottleType)) {
            plantCount += bottle.getParentKeepPlantCount();
            keepPlant += bottle.getParentKeepPlantCount();
            keepBottles++;
          } else {
            plantCount += bottle.getPlantCountActual();
          }
        }
      }
    }

    // 组织入库单
    var now = System.currentTimeMillis();

    po.setInOut(Const.STORAGE_IN_OUT_TYPE_IN);
    po.setBottlesApplied(bottlesCount);
    po.setPlantsApplied(plantCount);
    po.setBottlesKeepInApplied(keepBottles);
    po.setPlantsKeepInApplied(keepPlant);
    po.setBottlesActual(0);
    po.setPlantsActual(0);
    po.setBottlesKeepInActual(0);
    po.setPlantsKeepInActual(0);
    po.setBottlesDiscard(0);
    po.setPlantsDiscard(0);
    po.setBottlesVoided(0);
    po.setPlantsVoided(0);
    po.setIgnoreStats(Const.BOOLEAN_0);
    po.setReason(task.getTaskType());
    po.setStatus(Const.STORAGE_IN_OUT_STATUS_APPLY);
    po.setOppReason(StringUtil.EMPTY);
    po.setIdTask(taskId);
    po.setIdInspection(StringUtil.EMPTY);
    po.setApplicantId(currentUser.getId());
    po.setApplicantName(currentUser.getName());
    po.setApplicantTime(now);
    po.setOperatorId(currentUser.getId());
    po.setOperatorName(currentUser.getName());
    po.setOperatorTime(now);

    if (po.isNew()) {
      this.storageInOutDAO.save(po);
    }

    // 组织入库单日志
    StorageInOutLogPO log = new StorageInOutLogPO();
    log.setId(Id.g());
    log.setIdInOut(po.getId());
    log.setStatus(Const.STORAGE_IN_OUT_STATUS_APPLY);
    log.setOppReason(StringUtil.EMPTY);
    log.setOperatorId(currentUser.getId());
    log.setOperatorName(currentUser.getName());
    log.setOperatorTime(now);

    this.storageInOutLogDAO.save(log);

    return this.queryByTaskId(taskId);
  }

  @Transactional
  public ModelTissueTaskView deleteStorageIn(@NonNull String inOutId) {
    Assert.hasText(inOutId, "入库信息不能为空");

    var po = storageInOutDAO.findById(inOutId).orElse(null);
    if (po == null || po.getStatus() == null || po.getId() == null) {
      throw new ErrorMessageException("无法获得入库信息");
    }
    var status = po.getStatus();
    if (
      !status.equals(Const.STORAGE_IN_OUT_STATUS_APPLY)
        && !status.equals(Const.STORAGE_IN_OUT_STATUS_CANCELED)
        && !status.equals(Const.STORAGE_IN_OUT_STATUS_REJ_BIZ)
    ) {
      throw new ErrorMessageException("无法删除此入库申请");
    }

    var taskId = po.getIdTask();

    TissueTaskPO task = tissueTaskDAO.findById(taskId).orElse(null);
    if (task == null) throw new ErrorMessageException("无法找到此任务信息。");
    var taskStatus = task.getTaskStatus() == null ? Const.TISSUE_TASK_STATUS_CREATED : task.getTaskStatus();
    if (!taskStatus.equals(Const.TISSUE_TASK_STATUS_FINISHED)) {
      throw new ErrorMessageException("只能为已完成的任务办理入库业务。");
    }

    storageInOutDAO.delete(po);
    storageInOutBottleDAO.deleteAllByIdInOut(po.getId());
    storageInOutLogDAO.deleteAllByIdInOutIn(List.of(po.getId()));

    return this.queryByTaskId(taskId);
  }

  @NonNull
  private List<ModelTissueTaskView> organizeTissueTaskViews(
    @NonNull List<? extends TissueTask> tasks,
    @NonNull List<? extends TissueTaskOppGroup> oppGroups,
    @NonNull List<? extends TissueTaskUser> taskUsers,
    @NonNull List<? extends TissueTaskBottle> bottles,
    @NonNull List<? extends StorageInOut> storageInOuts,
    @NonNull List<? extends User> userAll
  ) {
    // 按任务分组
    Map<String, TissueTask> taskMapGroupByTask = new HashMap<>();
    Map<String, List<TissueTaskOppGroup>> oppGroupMapGroupByTask = new HashMap<>();
    Map<String, List<TissueTaskUser>> taskUserMapGroupByTask = new HashMap<>();
    Map<String, List<TissueTaskBottle>> bottleMapGroupByTask = new HashMap<>();
    Map<String, List<StorageInOut>> storageInOutMapGroupByTask = new HashMap<>();

    // 对任务分组
    tasks.forEach(task -> taskMapGroupByTask.put(task.getId(), task));
    // 对操作组分组
    oppGroups.forEach(oppGroup -> oppGroupMapGroupByTask.computeIfAbsent(oppGroup.getIdTask(), k -> new ArrayList<>()).add(oppGroup));
    // 对员工分组
    taskUsers.forEach(user -> taskUserMapGroupByTask.computeIfAbsent(user.getIdTask(), k -> new ArrayList<>()).add(user));
    // 对培养瓶分组
    bottles.forEach(bottle -> bottleMapGroupByTask.computeIfAbsent(bottle.getIdTask(), k -> new ArrayList<>()).add(bottle));
    // 对出入库单分组
    storageInOuts.forEach(storageInOut -> storageInOutMapGroupByTask.computeIfAbsent(storageInOut.getIdTask(), k -> new ArrayList<>()).add(storageInOut));

    // 组织员工信息映射表
    Map<String, User> userMap = new HashMap<>();
    userAll.forEach(user -> userMap.put(user.getId(), user));

    // 准备返回值。
    List<ModelTissueTaskView> result = new ArrayList<>();

    // 组织单条信息。
    taskMapGroupByTask.forEach((taskId, task) ->
      result.add(
        organizeTissueTaskView(
          task,
          oppGroupMapGroupByTask.getOrDefault(taskId, new ArrayList<>()),
          taskUserMapGroupByTask.getOrDefault(taskId, new ArrayList<>()),
          bottleMapGroupByTask.getOrDefault(taskId, new ArrayList<>()),
          storageInOutMapGroupByTask.getOrDefault(taskId, new ArrayList<>()),
          userMap
        )
      )
    );

    // 返回。
    result.sort((o1, o2) -> Long.compare(o2.getTask().getCreateTime(), o1.getTask().getCreateTime()));
    return result;
  }

  @NonNull
  private ModelTissueTaskView organizeTissueTaskView(
    @NonNull TissueTask task,
    @NonNull List<TissueTaskOppGroup> oppGroups,
    @NonNull List<TissueTaskUser> taskUsers,
    @NonNull List<TissueTaskBottle> bottles,
    @NonNull List<StorageInOut> storageInOuts,
    @NonNull Map<String, User> userMap
  ) {
    // 准备识别质检员和操作员，通过 Set 去重
    Set<String> inspectorIds = new HashSet<>();
    Set<String> operatorIds = new HashSet<>();
    Set<String> actualInspectorIds = new HashSet<>();
    Set<String> actualOperatorIds = new HashSet<>();

    // 放置已分配的质检员和操作员。
    taskUsers.forEach(taskUser -> {
      if (taskUser.getRole().equals(Const.TISSUE_TASK_USER_ROLE_INSPECTOR)) {
        inspectorIds.add(taskUser.getIdUser());
      } else if (taskUser.getRole().equals(Const.TISSUE_TASK_USER_ROLE_OPERATOR)) {
        operatorIds.add(taskUser.getIdUser());
      }
    });

    // 根据操作组信息，继续放置质检员和操作员。
    oppGroups.forEach(oppGroup -> {
      if (oppGroup.getAssigneeInspectorId() != null && !oppGroup.getAssigneeInspectorId().isBlank()) {
        inspectorIds.add(oppGroup.getAssigneeInspectorId());
      }
      if (oppGroup.getInspectorId() != null && !oppGroup.getInspectorId().isBlank()) {
        inspectorIds.add(oppGroup.getInspectorId());
        actualInspectorIds.add(oppGroup.getInspectorId());
      }
      if (oppGroup.getAssigneeOperatorId() != null && !oppGroup.getAssigneeOperatorId().isBlank()) {
        operatorIds.add(oppGroup.getAssigneeOperatorId());
      }
      if (oppGroup.getOperatorId() != null && !oppGroup.getOperatorId().isBlank()) {
        operatorIds.add(oppGroup.getOperatorId());
        actualOperatorIds.add(oppGroup.getOperatorId());
      }
    });

    // 分配子母瓶，并根据培养瓶操作员和质检员信息，继续放置操作员和质检员。
    List<TissueTaskBottle> bottlesParentAll = new ArrayList<>();
    List<TissueTaskBottle> bottlesChildrenAll = new ArrayList<>();
    int actualParentBottles = 0;
    int actualParentPlants = 0;
    int discardParentBottles = 0;
    int discardParentPlants = 0;
    int keepParentBottles = 0;
    int keepParentPlants = 0;
    int actualChildrenBottles = 0;
    int actualChildrenPlants = 0;
    int discardChildrenBottles = 0;
    int discardChildrenPlants = 0;

    // 对诱导任务的特殊处理（诱导任务无母瓶，母代株数记录在作业组）
    if (task.getTaskType().equals(Const.TISSUE_TASK_TYPE_INDUCTION)) {
      // 因为有工作组，便是已分配，故只要有工作组，就计入实际使用母代株数。
      for (TissueTaskOppGroup oppGroup : oppGroups) {
        actualParentPlants += oppGroup.getParentPlantCount();
      }
    }

    for (TissueTaskBottle bottle : bottles) {

      // 培养瓶角色校验。
      String role = bottle.getRoleInTask();
      if (role == null || role.isBlank()) continue;
      if (!role.equals(Const.BOTTLE_TASK_ROLE_CHILD) && !role.equals(Const.BOTTLE_TASK_ROLE_PARENT)) continue;

      // 放置质检员
      if (bottle.getInspectorId() != null && !bottle.getInspectorId().isBlank()) {
        inspectorIds.add(bottle.getInspectorId());
        actualInspectorIds.add(bottle.getInspectorId());
      }
      // 放置操作员
      if (bottle.getOperatorId() != null && !bottle.getOperatorId().isBlank()) {
        operatorIds.add(bottle.getOperatorId());
        actualOperatorIds.add(bottle.getOperatorId());
      }

      // 操作状态
      String operateStatus = bottle.getOperateStatus();

      // 母瓶
      if (role.equals(Const.BOTTLE_TASK_ROLE_PARENT)) {
        // 添加到母瓶列表
        bottlesParentAll.add(bottle);
        // 计算各类瓶数、株数
        if (
          // 已打码，已分配操作。只要已分配操作，便意味着已被实际使用。
          Const.BOTTLE_OPERATE_STATUS_ASSIGNED.equals(operateStatus)
            // 已打码，已操作完成（成功）
            || Const.BOTTLE_OPERATE_STATUS_FINISHED.equals(operateStatus)
            // 已打码，已操作完成（废弃）。即便废弃，也是使用了。
            || Const.BOTTLE_OPERATE_STATUS_DISCARD.equals(operateStatus)
        ) {
          actualParentBottles++;
          actualParentPlants += bottle.getPlantCountActual();
        }
        // 已打码，已操作完成（废弃）
        if (Const.BOTTLE_OPERATE_STATUS_DISCARD.equals(operateStatus)) {
          discardParentBottles++;
          discardParentPlants += bottle.getPlantCountActual();
        }
        // 计算留种瓶数和株数。只有已完成的才算实际已经留种。
        if (Const.BOTTLE_OPERATE_STATUS_FINISHED.equals(operateStatus) && Const.BOOLEAN_1.equals(bottle.getParentKeep())) {
          keepParentBottles++;
          keepParentPlants += bottle.getParentKeepPlantCount();
        }
      }
      // 子瓶
      else {
        // 添加到子瓶列表
        bottlesChildrenAll.add(bottle);
        // 计算各类瓶数、株数

        // 已打码，已分配操作。此时子瓶尚未操作完成，不能计入实际工作成果
        // || Const.BOTTLE_OPERATE_STATUS_ASSIGNED.equals(operateStatus)
        // 已打码，已操作完成（废弃）。废弃的产出物不能计入实际工作成果。
        // || Const.BOTTLE_OPERATE_STATUS_DISCARD.equals(operateStatus)

        // 已打码，已操作完成（成功）
        if (Const.BOTTLE_OPERATE_STATUS_FINISHED.equals(operateStatus)) {
          actualChildrenBottles++;
          actualChildrenPlants += bottle.getPlantCountActual();
        }

        // 已打码，已操作完成（废弃）
        else if (Const.BOTTLE_OPERATE_STATUS_DISCARD.equals(operateStatus)) {
          discardChildrenBottles++;
          discardChildrenPlants += bottle.getPlantCountActual();
        }
      }
    }

    // 分配出入库单
    List<StorageInOut> storageInList = new ArrayList<>();
    List<StorageInOut> storageOutList = new ArrayList<>();
    int appliedStorageOutParentBottles = 0;
    int appliedStorageOutParentPlants = 0;
    int actualStorageOutParentBottles = 0;
    int actualStorageOutParentPlants = 0;
    int appliedStorageInParentKeepBottles = 0;
    int appliedStorageInParentKeepPlants = 0;
    int actualStorageInParentKeepBottles = 0;
    int actualStorageInParentKeepPlants = 0;
    int appliedStorageInChildrenBottles = 0;
    int appliedStorageInChildrenPlants = 0;
    int actualStorageInChildrenBottles = 0;
    int actualStorageInChildrenPlants = 0;
    for (var storageInOut : storageInOuts) {

      boolean shouldStatsApplied = !storageInOut.getStatus().equals(Const.STORAGE_IN_OUT_STATUS_CANCELED)
        && !storageInOut.getStatus().equals(Const.STORAGE_IN_OUT_STATUS_REJ_BIZ);

      boolean shouldStatsActual = storageInOut.getStatus().equals(Const.STORAGE_IN_OUT_STATUS_FINISHED);

      if (storageInOut.getInOut().equals(Const.STORAGE_IN_OUT_TYPE_IN)) {
        storageInList.add(storageInOut);
      }
      if (storageInOut.getInOut().equals(Const.STORAGE_IN_OUT_TYPE_IN) && shouldStatsApplied) {
        appliedStorageInParentKeepBottles += storageInOut.getBottlesKeepInApplied();
        appliedStorageInParentKeepPlants += storageInOut.getPlantsKeepInApplied();
        // 除了留种以外的入库，都是子代入库
        appliedStorageInChildrenBottles += storageInOut.getBottlesApplied() - storageInOut.getBottlesKeepInApplied();
        appliedStorageInChildrenPlants += storageInOut.getPlantsApplied() - storageInOut.getPlantsKeepInApplied();
      }
      if (storageInOut.getInOut().equals(Const.STORAGE_IN_OUT_TYPE_IN) && shouldStatsActual) {
        actualStorageInParentKeepBottles += storageInOut.getBottlesKeepInActual();
        actualStorageInParentKeepPlants += storageInOut.getPlantsKeepInActual();
        // 除了留种以外的入库，都是子代入库
        actualStorageInChildrenBottles += storageInOut.getBottlesActual() - storageInOut.getBottlesKeepInActual();
        actualStorageInChildrenPlants += storageInOut.getPlantsActual() - storageInOut.getPlantsKeepInActual();
      }

      if (storageInOut.getInOut().equals(Const.STORAGE_IN_OUT_TYPE_OUT)) {
        storageOutList.add(storageInOut);
      }
      if (storageInOut.getInOut().equals(Const.STORAGE_IN_OUT_TYPE_OUT) && shouldStatsApplied) {
        // 出库的全部是母代。因出库记作负数，所以应取相反数，以使前台显示正数。
        appliedStorageOutParentBottles -= storageInOut.getBottlesApplied();
        appliedStorageOutParentPlants -= storageInOut.getPlantsApplied();
      }
      if (storageInOut.getInOut().equals(Const.STORAGE_IN_OUT_TYPE_OUT) && shouldStatsActual) {
        // 出库的全部是母代。因出库记作负数，所以应取相反数，以使前台显示正数。
        actualStorageOutParentBottles -= storageInOut.getBottlesActual();
        actualStorageOutParentPlants -= storageInOut.getPlantsActual();
      }
    }

    // 组织返回值
    ModelTissueTaskView model = new ModelTissueTaskView();
    model.setTaskId(task.getId());
    model.setTask(task);
    model.setBottlesParentAll(bottlesParentAll);
    model.setBottlesChildrenAll(bottlesChildrenAll);
    model.setOppGroup(oppGroups);
    model.setActualParentBottles(actualParentBottles);
    model.setActualParentPlants(actualParentPlants);
    model.setDiscardParentBottles(discardParentBottles);
    model.setDiscardParentPlants(discardParentPlants);
    model.setKeepParentBottles(keepParentBottles);
    model.setKeepParentPlants(keepParentPlants);
    model.setActualChildrenBottles(actualChildrenBottles);
    model.setActualChildrenPlants(actualChildrenPlants);
    model.setDiscardChildrenBottles(discardChildrenBottles);
    model.setDiscardChildrenPlants(discardChildrenPlants);
    model.setInspectors(inspectorIds.stream().map(userMap::get).filter(Objects::nonNull).toList());
    model.setOperators(operatorIds.stream().map(userMap::get).filter(Objects::nonNull).toList());
    model.setActualInspectors(actualInspectorIds.stream().map(userMap::get).filter(Objects::nonNull).toList());
    model.setActualOperators(actualOperatorIds.stream().map(userMap::get).filter(Objects::nonNull).toList());
    model.setStorageInList(storageInList);
    model.setStorageOutList(storageOutList);
    model.setAppliedStorageOutParentBottles(appliedStorageOutParentBottles);
    model.setAppliedStorageOutParentPlants(appliedStorageOutParentPlants);
    model.setActualStorageOutParentBottles(actualStorageOutParentBottles);
    model.setActualStorageOutParentPlants(actualStorageOutParentPlants);
    model.setAppliedStorageInParentKeepBottles(appliedStorageInParentKeepBottles);
    model.setAppliedStorageInParentKeepPlants(appliedStorageInParentKeepPlants);
    model.setActualStorageInParentKeepBottles(actualStorageInParentKeepBottles);
    model.setActualStorageInParentKeepPlants(actualStorageInParentKeepPlants);
    model.setAppliedStorageInChildrenBottles(appliedStorageInChildrenBottles);
    model.setAppliedStorageInChildrenPlants(appliedStorageInChildrenPlants);
    model.setActualStorageInChildrenBottles(actualStorageInChildrenBottles);
    model.setActualStorageInChildrenPlants(actualStorageInChildrenPlants);
    model.setStorageInStatus(StringUtil.EMPTY);
    if (!storageInList.isEmpty()) {
      model.setStorageInStatus(storageInList.getFirst().getStatus());
    } else if (task.getTaskStatus().equals(Const.TISSUE_TASK_STATUS_FINISHED)) {
      boolean needStorageIn = false;
      for (var bottle : bottles) {
        if (bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_PARENT)) {
          if (bottle.getParentKeep().equals(Const.BOOLEAN_1)) {
            needStorageIn = true;
            break;
          }
          if (bottle.getIdOppGroup() == null || bottle.getIdOppGroup().isBlank()) {
            needStorageIn = true;
            break;
          }
        } else if (bottle.getRoleInTask().equals(Const.BOTTLE_TASK_ROLE_CHILD)) {
          if (bottle.getOperateStatus().equals(Const.BOTTLE_OPERATE_STATUS_FINISHED) && bottle.getChildCodeStatus().equals(Const.BOTTLE_CODE_STATUS_FOR_TASK_CHECKED)) {
            needStorageIn = true;
            break;
          }
        }
      }
      if (!needStorageIn) {
        model.setStorageInStatus("none");
      } else {
        model.setStorageInStatus(StringUtil.EMPTY);
      }
    }
    return model;
  }
}
