package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.TissueTask} 接口。</p>
 * <p>组培生产任务</p>
 * <p>数据库表： <code>tissue_task</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.TissueTask}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class TissueTaskDTO implements Cloneable, TissueTask {
  /**
   * <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String id;

  /**
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String variety;

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String varietyCode;

  /**
   * <p>外植体采集批次，仅用于诱导任务</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer explantCollectionBatch;

  /**
   * <p>预计使用母代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer estimatedParentBottles;

  /**
   * <p>预计使用母代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer estimatedParentPlants;

  /**
   * <p>预计母代留种瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer estimatedParentKeepBottles;

  /**
   * <p>预计母代留种株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer estimatedParentKeepPlants;

  /**
   * <p>预计生产子代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer estimatedChildrenBottles;

  /**
   * <p>预计生产子代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer estimatedChildrenPlants;

  /**
   * <p>任务参数：母代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_parent_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer defaultPlantsPerParentBottle;

  /**
   * <p>任务参数：子代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_child_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer defaultPlantsPerChildBottle;

  /**
   * <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String codePrefix;

  /**
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String creatorUserId;

  /**
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String creatorUserName;

  /**
   * <p>任务类别：dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String taskType;

  /**
   * <p>任务状态：dictionary.tissue_task_status</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  private String taskStatus;

  /**
   * <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long createTime;

  /**
   * <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long startTime;

  /**
   * <p>完成时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long finishTime;

  /**
   * <p>是否开启强制编辑，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>modifiable</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  private String modifiable;

  /**
   * <p>无参构造方法。</p>
   * <p>组培生产任务</p>
   * <p>数据库表： <code>tissue_task</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public TissueTaskDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>组培生产任务</p>
   * <p>数据库表： <code>tissue_task</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param variety <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param varietyCode <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param explantCollectionBatch <p>外植体采集批次，仅用于诱导任务</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param estimatedParentBottles <p>预计使用母代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param estimatedParentPlants <p>预计使用母代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param estimatedParentKeepBottles <p>预计母代留种瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param estimatedParentKeepPlants <p>预计母代留种株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param estimatedChildrenBottles <p>预计生产子代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param estimatedChildrenPlants <p>预计生产子代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param defaultPlantsPerParentBottle <p>任务参数：母代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_parent_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param defaultPlantsPerChildBottle <p>任务参数：子代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_child_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param codePrefix <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param creatorUserId <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param creatorUserName <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param taskType <p>任务类别：dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param taskStatus <p>任务状态：dictionary.tissue_task_status</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   *
   * @param createTime <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param startTime <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param finishTime <p>完成时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param modifiable <p>是否开启强制编辑，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>modifiable</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   */
  public TissueTaskDTO(String id, String variety, String varietyCode,
      Integer explantCollectionBatch, Integer estimatedParentBottles, Integer estimatedParentPlants,
      Integer estimatedParentKeepBottles, Integer estimatedParentKeepPlants,
      Integer estimatedChildrenBottles, Integer estimatedChildrenPlants,
      Integer defaultPlantsPerParentBottle, Integer defaultPlantsPerChildBottle, String codePrefix,
      String creatorUserId, String creatorUserName, String taskType, String taskStatus,
      Long createTime, Long startTime, Long finishTime, String modifiable) {
    this.id = id;
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.explantCollectionBatch = explantCollectionBatch;
    this.estimatedParentBottles = estimatedParentBottles;
    this.estimatedParentPlants = estimatedParentPlants;
    this.estimatedParentKeepBottles = estimatedParentKeepBottles;
    this.estimatedParentKeepPlants = estimatedParentKeepPlants;
    this.estimatedChildrenBottles = estimatedChildrenBottles;
    this.estimatedChildrenPlants = estimatedChildrenPlants;
    this.defaultPlantsPerParentBottle = defaultPlantsPerParentBottle;
    this.defaultPlantsPerChildBottle = defaultPlantsPerChildBottle;
    this.codePrefix = codePrefix;
    this.creatorUserId = creatorUserId;
    this.creatorUserName = creatorUserName;
    this.taskType = taskType;
    this.taskStatus = taskStatus;
    this.createTime = createTime;
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.modifiable = modifiable;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>组培生产任务</p>
   * <p>数据库表： <code>tissue_task</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public TissueTaskDTO(TissueTask model) {
    this.id = model.getId();
    this.variety = model.getVariety();
    this.varietyCode = model.getVarietyCode();
    this.explantCollectionBatch = model.getExplantCollectionBatch();
    this.estimatedParentBottles = model.getEstimatedParentBottles();
    this.estimatedParentPlants = model.getEstimatedParentPlants();
    this.estimatedParentKeepBottles = model.getEstimatedParentKeepBottles();
    this.estimatedParentKeepPlants = model.getEstimatedParentKeepPlants();
    this.estimatedChildrenBottles = model.getEstimatedChildrenBottles();
    this.estimatedChildrenPlants = model.getEstimatedChildrenPlants();
    this.defaultPlantsPerParentBottle = model.getDefaultPlantsPerParentBottle();
    this.defaultPlantsPerChildBottle = model.getDefaultPlantsPerChildBottle();
    this.codePrefix = model.getCodePrefix();
    this.creatorUserId = model.getCreatorUserId();
    this.creatorUserName = model.getCreatorUserName();
    this.taskType = model.getTaskType();
    this.taskStatus = model.getTaskStatus();
    this.createTime = model.getCreateTime();
    this.startTime = model.getStartTime();
    this.finishTime = model.getFinishTime();
    this.modifiable = model.getModifiable();
  }

  /**
   * <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
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
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getVarietyCode() {
    return varietyCode;
  }

  /**
   * <p>外植体采集批次，仅用于诱导任务</p>
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
   * <p>预计使用母代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getEstimatedParentBottles() {
    return estimatedParentBottles;
  }

  /**
   * <p>预计使用母代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getEstimatedParentPlants() {
    return estimatedParentPlants;
  }

  /**
   * <p>预计母代留种瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getEstimatedParentKeepBottles() {
    return estimatedParentKeepBottles;
  }

  /**
   * <p>预计母代留种株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getEstimatedParentKeepPlants() {
    return estimatedParentKeepPlants;
  }

  /**
   * <p>预计生产子代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getEstimatedChildrenBottles() {
    return estimatedChildrenBottles;
  }

  /**
   * <p>预计生产子代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getEstimatedChildrenPlants() {
    return estimatedChildrenPlants;
  }

  /**
   * <p>任务参数：母代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_parent_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getDefaultPlantsPerParentBottle() {
    return defaultPlantsPerParentBottle;
  }

  /**
   * <p>任务参数：子代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_child_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getDefaultPlantsPerChildBottle() {
    return defaultPlantsPerChildBottle;
  }

  /**
   * <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCodePrefix() {
    return codePrefix;
  }

  /**
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCreatorUserId() {
    return creatorUserId;
  }

  /**
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCreatorUserName() {
    return creatorUserName;
  }

  /**
   * <p>任务类别：dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getTaskType() {
    return taskType;
  }

  /**
   * <p>任务状态：dictionary.tissue_task_status</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  @Override
  public String getTaskStatus() {
    return taskStatus;
  }

  /**
   * <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getCreateTime() {
    return createTime;
  }

  /**
   * <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getStartTime() {
    return startTime;
  }

  /**
   * <p>完成时间 UTC 毫秒时间戳</p>
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
   * <p>是否开启强制编辑，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>modifiable</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getModifiable() {
    return modifiable;
  }

  /**
   * <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param variety <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVariety(String variety) {
    this.variety = variety;
  }

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param varietyCode <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVarietyCode(String varietyCode) {
    this.varietyCode = varietyCode;
  }

  /**
   * <p>外植体采集批次，仅用于诱导任务</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param explantCollectionBatch <p>外植体采集批次，仅用于诱导任务</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setExplantCollectionBatch(Integer explantCollectionBatch) {
    this.explantCollectionBatch = explantCollectionBatch;
  }

  /**
   * <p>预计使用母代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param estimatedParentBottles <p>预计使用母代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setEstimatedParentBottles(Integer estimatedParentBottles) {
    this.estimatedParentBottles = estimatedParentBottles;
  }

  /**
   * <p>预计使用母代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param estimatedParentPlants <p>预计使用母代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setEstimatedParentPlants(Integer estimatedParentPlants) {
    this.estimatedParentPlants = estimatedParentPlants;
  }

  /**
   * <p>预计母代留种瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param estimatedParentKeepBottles <p>预计母代留种瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setEstimatedParentKeepBottles(Integer estimatedParentKeepBottles) {
    this.estimatedParentKeepBottles = estimatedParentKeepBottles;
  }

  /**
   * <p>预计母代留种株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param estimatedParentKeepPlants <p>预计母代留种株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setEstimatedParentKeepPlants(Integer estimatedParentKeepPlants) {
    this.estimatedParentKeepPlants = estimatedParentKeepPlants;
  }

  /**
   * <p>预计生产子代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param estimatedChildrenBottles <p>预计生产子代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setEstimatedChildrenBottles(Integer estimatedChildrenBottles) {
    this.estimatedChildrenBottles = estimatedChildrenBottles;
  }

  /**
   * <p>预计生产子代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param estimatedChildrenPlants <p>预计生产子代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setEstimatedChildrenPlants(Integer estimatedChildrenPlants) {
    this.estimatedChildrenPlants = estimatedChildrenPlants;
  }

  /**
   * <p>任务参数：母代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_parent_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param defaultPlantsPerParentBottle <p>任务参数：母代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_parent_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setDefaultPlantsPerParentBottle(Integer defaultPlantsPerParentBottle) {
    this.defaultPlantsPerParentBottle = defaultPlantsPerParentBottle;
  }

  /**
   * <p>任务参数：子代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_child_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param defaultPlantsPerChildBottle <p>任务参数：子代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_child_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setDefaultPlantsPerChildBottle(Integer defaultPlantsPerChildBottle) {
    this.defaultPlantsPerChildBottle = defaultPlantsPerChildBottle;
  }

  /**
   * <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param codePrefix <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCodePrefix(String codePrefix) {
    this.codePrefix = codePrefix;
  }

  /**
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param creatorUserId <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCreatorUserId(String creatorUserId) {
    this.creatorUserId = creatorUserId;
  }

  /**
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param creatorUserName <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCreatorUserName(String creatorUserName) {
    this.creatorUserName = creatorUserName;
  }

  /**
   * <p>任务类别：dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param taskType <p>任务类别：dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  /**
   * <p>任务状态：dictionary.tissue_task_status</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   * @param taskStatus <p>任务状态：dictionary.tissue_task_status</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
  }

  /**
   * <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param createTime <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  /**
   * <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param startTime <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  /**
   * <p>完成时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param finishTime <p>完成时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setFinishTime(Long finishTime) {
    this.finishTime = finishTime;
  }

  /**
   * <p>是否开启强制编辑，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>modifiable</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param modifiable <p>是否开启强制编辑，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>modifiable</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setModifiable(String modifiable) {
    this.modifiable = modifiable;
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
  public TissueTaskDTO cloneSimple() {
    try{
      return (TissueTaskDTO)clone();
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
    if(other instanceof TissueTaskDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.TissueTask o) return fetchId().equals(o.fetchId());
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
