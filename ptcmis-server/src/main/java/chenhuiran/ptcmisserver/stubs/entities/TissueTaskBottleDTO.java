package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.TissueTaskBottle} 接口。</p>
 * <p>组培任务-操作分配组‑培养瓶关联明细</p>
 * <p>数据库表： <code>tissue_task_bottle</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>idTask</code>: 参见 {@link #getIdTask()} 。</li>
 *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.TissueTaskBottle}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class TissueTaskBottleDTO implements Cloneable, TissueTaskBottle {
  /**
   * <p>任务ID。tissue_task.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String idTask;

  /**
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String variety;

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), NULLABLE, DEFAULT ""</code></li>
   * </ul>
   */
  private String varietyCode;

  /**
   * <p>操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String idOppGroup;

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String idBottle;

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer explantCollectionBatch;

  /**
   * <p>代际</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer generation;

  /**
   * <p>瓶内株数（任务前）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_original</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer plantCountOriginal;

  /**
   * <p>瓶内株数（实际）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer plantCountActual;

  /**
   * <p>任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String roleInTask;

  /**
   * <p>若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  private String parentKeep;

  /**
   * <p>若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer parentKeepPlantCount;

  /**
   * <p>母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>parent_last_task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String parentLastTaskType;

  /**
   * <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String operateStatus;

  /**
   * <p>子瓶瓶码状态。 dictionary.bottle_code_status_for_task</p>
   * <ul>
   *   <li>COLUMN : <code>child_code_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  private String childCodeStatus;

  /**
   * <p>若淘汰，体现淘汰原因。</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String discardReason;

  /**
   * <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long allocateTime;

  /**
   * <p>完成时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long finishTime;

  /**
   * <p>质检员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String inspectorId;

  /**
   * <p>质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String inspectorName;

  /**
   * <p>操作员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String operatorId;

  /**
   * <p>操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String operatorName;

  /**
   * <p>无参构造方法。</p>
   * <p>组培任务-操作分配组‑培养瓶关联明细</p>
   * <p>数据库表： <code>tissue_task_bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idTask</code>: 参见 {@link #getIdTask()} 。</li>
   *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
   * </ul>
   */
  public TissueTaskBottleDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>组培任务-操作分配组‑培养瓶关联明细</p>
   * <p>数据库表： <code>tissue_task_bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idTask</code>: 参见 {@link #getIdTask()} 。</li>
   *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
   * </ul>
   * @param idTask <p>任务ID。tissue_task.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param variety <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param varietyCode <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), NULLABLE, DEFAULT ""</code></li>
   * </ul>
   *
   * @param idOppGroup <p>操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param idBottle <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param explantCollectionBatch <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param generation <p>代际</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantCountOriginal <p>瓶内株数（任务前）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_original</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantCountActual <p>瓶内株数（实际）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param roleInTask <p>任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param parentKeep <p>若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param parentKeepPlantCount <p>若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param parentLastTaskType <p>母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>parent_last_task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operateStatus <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param childCodeStatus <p>子瓶瓶码状态。 dictionary.bottle_code_status_for_task</p>
   * <ul>
   *   <li>COLUMN : <code>child_code_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   *
   * @param discardReason <p>若淘汰，体现淘汰原因。</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param allocateTime <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param finishTime <p>完成时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param inspectorId <p>质检员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param inspectorName <p>质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorId <p>操作员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorName <p>操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   */
  public TissueTaskBottleDTO(String idTask, String variety, String varietyCode, String idOppGroup,
      String idBottle, Integer explantCollectionBatch, Integer generation,
      Integer plantCountOriginal, Integer plantCountActual, String roleInTask, String parentKeep,
      Integer parentKeepPlantCount, String parentLastTaskType, String operateStatus,
      String childCodeStatus, String discardReason, Long allocateTime, Long finishTime,
      String inspectorId, String inspectorName, String operatorId, String operatorName) {
    this.idTask = idTask;
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.idOppGroup = idOppGroup;
    this.idBottle = idBottle;
    this.explantCollectionBatch = explantCollectionBatch;
    this.generation = generation;
    this.plantCountOriginal = plantCountOriginal;
    this.plantCountActual = plantCountActual;
    this.roleInTask = roleInTask;
    this.parentKeep = parentKeep;
    this.parentKeepPlantCount = parentKeepPlantCount;
    this.parentLastTaskType = parentLastTaskType;
    this.operateStatus = operateStatus;
    this.childCodeStatus = childCodeStatus;
    this.discardReason = discardReason;
    this.allocateTime = allocateTime;
    this.finishTime = finishTime;
    this.inspectorId = inspectorId;
    this.inspectorName = inspectorName;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>组培任务-操作分配组‑培养瓶关联明细</p>
   * <p>数据库表： <code>tissue_task_bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idTask</code>: 参见 {@link #getIdTask()} 。</li>
   *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public TissueTaskBottleDTO(TissueTaskBottle model) {
    this.idTask = model.getIdTask();
    this.variety = model.getVariety();
    this.varietyCode = model.getVarietyCode();
    this.idOppGroup = model.getIdOppGroup();
    this.idBottle = model.getIdBottle();
    this.explantCollectionBatch = model.getExplantCollectionBatch();
    this.generation = model.getGeneration();
    this.plantCountOriginal = model.getPlantCountOriginal();
    this.plantCountActual = model.getPlantCountActual();
    this.roleInTask = model.getRoleInTask();
    this.parentKeep = model.getParentKeep();
    this.parentKeepPlantCount = model.getParentKeepPlantCount();
    this.parentLastTaskType = model.getParentLastTaskType();
    this.operateStatus = model.getOperateStatus();
    this.childCodeStatus = model.getChildCodeStatus();
    this.discardReason = model.getDiscardReason();
    this.allocateTime = model.getAllocateTime();
    this.finishTime = model.getFinishTime();
    this.inspectorId = model.getInspectorId();
    this.inspectorName = model.getInspectorName();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
  }

  /**
   * <p>任务ID。tissue_task.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdTask() {
    return idTask;
  }

  /**
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getVariety() {
    return variety;
  }

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), NULLABLE, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getVarietyCode() {
    return varietyCode;
  }

  /**
   * <p>操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdOppGroup() {
    return idOppGroup;
  }

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdBottle() {
    return idBottle;
  }

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getExplantCollectionBatch() {
    return explantCollectionBatch;
  }

  /**
   * <p>代际</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getGeneration() {
    return generation;
  }

  /**
   * <p>瓶内株数（任务前）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_original</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantCountOriginal() {
    return plantCountOriginal;
  }

  /**
   * <p>瓶内株数（实际）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantCountActual() {
    return plantCountActual;
  }

  /**
   * <p>任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getRoleInTask() {
    return roleInTask;
  }

  /**
   * <p>若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getParentKeep() {
    return parentKeep;
  }

  /**
   * <p>若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getParentKeepPlantCount() {
    return parentKeepPlantCount;
  }

  /**
   * <p>母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>parent_last_task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getParentLastTaskType() {
    return parentLastTaskType;
  }

  /**
   * <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getOperateStatus() {
    return operateStatus;
  }

  /**
   * <p>子瓶瓶码状态。 dictionary.bottle_code_status_for_task</p>
   * <ul>
   *   <li>COLUMN : <code>child_code_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  @Override
  public String getChildCodeStatus() {
    return childCodeStatus;
  }

  /**
   * <p>若淘汰，体现淘汰原因。</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getDiscardReason() {
    return discardReason;
  }

  /**
   * <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getAllocateTime() {
    return allocateTime;
  }

  /**
   * <p>完成时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getFinishTime() {
    return finishTime;
  }

  /**
   * <p>质检员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getInspectorId() {
    return inspectorId;
  }

  /**
   * <p>质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getInspectorName() {
    return inspectorName;
  }

  /**
   * <p>操作员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getOperatorId() {
    return operatorId;
  }

  /**
   * <p>操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getOperatorName() {
    return operatorName;
  }

  /**
   * <p>任务ID。tissue_task.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idTask <p>任务ID。tissue_task.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdTask(String idTask) {
    this.idTask = idTask;
  }

  /**
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param variety <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setVariety(String variety) {
    this.variety = variety;
  }

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), NULLABLE, DEFAULT ""</code></li>
   * </ul>
   * @param varietyCode <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), NULLABLE, DEFAULT ""</code></li>
   * </ul>
   */
  public void setVarietyCode(String varietyCode) {
    this.varietyCode = varietyCode;
  }

  /**
   * <p>操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idOppGroup <p>操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdOppGroup(String idOppGroup) {
    this.idOppGroup = idOppGroup;
  }

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idBottle <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdBottle(String idBottle) {
    this.idBottle = idBottle;
  }

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param explantCollectionBatch <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setExplantCollectionBatch(Integer explantCollectionBatch) {
    this.explantCollectionBatch = explantCollectionBatch;
  }

  /**
   * <p>代际</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param generation <p>代际</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setGeneration(Integer generation) {
    this.generation = generation;
  }

  /**
   * <p>瓶内株数（任务前）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_original</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantCountOriginal <p>瓶内株数（任务前）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_original</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantCountOriginal(Integer plantCountOriginal) {
    this.plantCountOriginal = plantCountOriginal;
  }

  /**
   * <p>瓶内株数（实际）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantCountActual <p>瓶内株数（实际）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantCountActual(Integer plantCountActual) {
    this.plantCountActual = plantCountActual;
  }

  /**
   * <p>任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param roleInTask <p>任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setRoleInTask(String roleInTask) {
    this.roleInTask = roleInTask;
  }

  /**
   * <p>若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param parentKeep <p>若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setParentKeep(String parentKeep) {
    this.parentKeep = parentKeep;
  }

  /**
   * <p>若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param parentKeepPlantCount <p>若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setParentKeepPlantCount(Integer parentKeepPlantCount) {
    this.parentKeepPlantCount = parentKeepPlantCount;
  }

  /**
   * <p>母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>parent_last_task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param parentLastTaskType <p>母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>parent_last_task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setParentLastTaskType(String parentLastTaskType) {
    this.parentLastTaskType = parentLastTaskType;
  }

  /**
   * <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operateStatus <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperateStatus(String operateStatus) {
    this.operateStatus = operateStatus;
  }

  /**
   * <p>子瓶瓶码状态。 dictionary.bottle_code_status_for_task</p>
   * <ul>
   *   <li>COLUMN : <code>child_code_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   * @param childCodeStatus <p>子瓶瓶码状态。 dictionary.bottle_code_status_for_task</p>
   * <ul>
   *   <li>COLUMN : <code>child_code_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  public void setChildCodeStatus(String childCodeStatus) {
    this.childCodeStatus = childCodeStatus;
  }

  /**
   * <p>若淘汰，体现淘汰原因。</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param discardReason <p>若淘汰，体现淘汰原因。</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setDiscardReason(String discardReason) {
    this.discardReason = discardReason;
  }

  /**
   * <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param allocateTime <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setAllocateTime(Long allocateTime) {
    this.allocateTime = allocateTime;
  }

  /**
   * <p>完成时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param finishTime <p>完成时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setFinishTime(Long finishTime) {
    this.finishTime = finishTime;
  }

  /**
   * <p>质检员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param inspectorId <p>质检员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setInspectorId(String inspectorId) {
    this.inspectorId = inspectorId;
  }

  /**
   * <p>质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param inspectorName <p>质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setInspectorName(String inspectorName) {
    this.inspectorName = inspectorName;
  }

  /**
   * <p>操作员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorId <p>操作员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * <p>操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorName <p>操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  /**
   * <p>通过 {@link #fetchId()} 的值计算哈希。</p>
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return fetchId().hashCode();
  }

  /**
   * 简单克隆。
   */
  @SuppressWarnings("UseSpecificCatch")
  public TissueTaskBottleDTO cloneSimple() {
    try{
      return (TissueTaskBottleDTO)clone();
    } catch (Throwable ignored) {
      return null;
    }
  }

  /**
   * <p>通过 {@link #fetchId()} 的值比较两个对象是否相等（支持传入字符串进行比较）。</p>
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("UseSpecificCatch")
  public boolean equals(Object other) {
    if(other == null) return false;
    if(other instanceof String s) return fetchId().equals(s);
    if(other instanceof TissueTaskBottleDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.TissueTaskBottle o) return fetchId().equals(o.fetchId());
    try{
      java.lang.reflect.Method fetchIdMethod = other.getClass().getMethod("fetchId");
      Object o = fetchIdMethod.invoke(other);
      if(o == null) return false;
      return fetchId().equals(o);
    } catch (Throwable ignored) {
      return false;
    }
  }
}
