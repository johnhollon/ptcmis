package chenhuiran.ptcmisserver.stubs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.data.domain.Persistable;

/**
 * <p>持久化 PO 实体类。包含数据库所有字段对应的属性。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.BottleCodeComplementTask} 接口。</p>
 * <p>该类实现了 {@link org.springframework.data.domain.Persistable} 接口。</p>
 * <p>瓶体补码任务</p>
 * <p>数据库表： <code>bottle_code_complement_task</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>无参构造方法。</li>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.BottleCodeComplementTask}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #getId()} : 获得联合主键或表主键的值。</li>
 *   <li>{@link #isNew()} : 判断当前实例是否为“新对象”。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
@Entity
@Table(
    name = "bottle_code_complement_task"
)
public class BottleCodeComplementTaskPO implements Cloneable, Persistable<String>, BottleCodeComplementTask {
  /**
   * 为了实现 Persistable 接口的 isNew() 方法而创建的 Transient 属性。
   */
  @Transient
  boolean is___new___for___persistable___ = true;

  /**
   * <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "id"
  )
  @Id
  private String id;

  /**
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "variety"
  )
  private String variety;

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "variety_code"
  )
  private String varietyCode;

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "explant_collection_batch"
  )
  private Integer explantCollectionBatch = 0;

  /**
   * <p>培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "source_type"
  )
  private String sourceType = "";

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "generation"
  )
  private Integer generation = 0;

  /**
   * <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "code_prefix"
  )
  private String codePrefix = "";

  /**
   * <p>任务参数：每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "default_plants_per_bottle"
  )
  private Integer defaultPlantsPerBottle = 0;

  /**
   * <p>任务状态：dictionary.bottle_code_complement_task_stat</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  @Column(
      name = "task_status"
  )
  private String taskStatus = "created";

  /**
   * <p>实际赋码瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "bottles"
  )
  private Integer bottles = 0;

  /**
   * <p>实际赋码株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "plants"
  )
  private Integer plants = 0;

  /**
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "creator_id"
  )
  private String creatorId = "";

  /**
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "creator_name"
  )
  private String creatorName = "";

  /**
   * <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Column(
      name = "create_time"
  )
  private Long createTime = 0L;

  /**
   * <p>末次操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "operator_id"
  )
  private String operatorId = "";

  /**
   * <p>末次操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "operator_name"
  )
  private String operatorName = "";

  /**
   * <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Column(
      name = "start_time"
  )
  private Long startTime = 0L;

  /**
   * <p>完成时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Column(
      name = "finish_time"
  )
  private Long finishTime = 0L;

  /**
   * <p>无参构造方法。</p>
   * <p>瓶体补码任务</p>
   * <p>数据库表： <code>bottle_code_complement_task</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public BottleCodeComplementTaskPO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>瓶体补码任务</p>
   * <p>数据库表： <code>bottle_code_complement_task</code></p>
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
   * @param explantCollectionBatch <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param sourceType <p>培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param generation <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param codePrefix <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param defaultPlantsPerBottle <p>任务参数：每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param taskStatus <p>任务状态：dictionary.bottle_code_complement_task_stat</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   *
   * @param bottles <p>实际赋码瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plants <p>实际赋码株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param creatorId <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param creatorName <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param createTime <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param operatorId <p>末次操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorName <p>末次操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
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
   */
  public BottleCodeComplementTaskPO(String id, String variety, String varietyCode,
      Integer explantCollectionBatch, String sourceType, Integer generation, String codePrefix,
      Integer defaultPlantsPerBottle, String taskStatus, Integer bottles, Integer plants,
      String creatorId, String creatorName, Long createTime, String operatorId, String operatorName,
      Long startTime, Long finishTime) {
    this.id = id;
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.explantCollectionBatch = explantCollectionBatch;
    this.sourceType = sourceType;
    this.generation = generation;
    this.codePrefix = codePrefix;
    this.defaultPlantsPerBottle = defaultPlantsPerBottle;
    this.taskStatus = taskStatus;
    this.bottles = bottles;
    this.plants = plants;
    this.creatorId = creatorId;
    this.creatorName = creatorName;
    this.createTime = createTime;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.startTime = startTime;
    this.finishTime = finishTime;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>瓶体补码任务</p>
   * <p>数据库表： <code>bottle_code_complement_task</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public BottleCodeComplementTaskPO(BottleCodeComplementTask model) {
    this.id = model.getId();
    this.variety = model.getVariety();
    this.varietyCode = model.getVarietyCode();
    this.explantCollectionBatch = model.getExplantCollectionBatch();
    this.sourceType = model.getSourceType();
    this.generation = model.getGeneration();
    this.codePrefix = model.getCodePrefix();
    this.defaultPlantsPerBottle = model.getDefaultPlantsPerBottle();
    this.taskStatus = model.getTaskStatus();
    this.bottles = model.getBottles();
    this.plants = model.getPlants();
    this.creatorId = model.getCreatorId();
    this.creatorName = model.getCreatorName();
    this.createTime = model.getCreateTime();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
    this.startTime = model.getStartTime();
    this.finishTime = model.getFinishTime();
  }

  /**
   * 实现 Persistable 接口的 isNew() 方法，可用于判断对象是否是“新的”。
   */
  @Override
  public boolean isNew() {
    return is___new___for___persistable___;
  }

  /**
   * 为实现 Persistable 接口的 isNew() 方法，监听 JPA 生命周期的方法。
   */
  @PostPersist
  @PostLoad
  protected final void mark___Not___New() {
    is___new___for___persistable___ = false;
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
   * <p>培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getSourceType() {
    return sourceType;
  }

  /**
   * <p>代数</p>
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
   * <p>任务参数：每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getDefaultPlantsPerBottle() {
    return defaultPlantsPerBottle;
  }

  /**
   * <p>任务状态：dictionary.bottle_code_complement_task_stat</p>
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
   * <p>实际赋码瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getBottles() {
    return bottles;
  }

  /**
   * <p>实际赋码株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlants() {
    return plants;
  }

  /**
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCreatorId() {
    return creatorId;
  }

  /**
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCreatorName() {
    return creatorName;
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
   * <p>末次操作人用户ID，user.id</p>
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
   * <p>末次操作人姓名，user.name</p>
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
   * <p>培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param sourceType <p>培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param generation <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setGeneration(Integer generation) {
    this.generation = generation;
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
   * <p>任务参数：每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param defaultPlantsPerBottle <p>任务参数：每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setDefaultPlantsPerBottle(Integer defaultPlantsPerBottle) {
    this.defaultPlantsPerBottle = defaultPlantsPerBottle;
  }

  /**
   * <p>任务状态：dictionary.bottle_code_complement_task_stat</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   * @param taskStatus <p>任务状态：dictionary.bottle_code_complement_task_stat</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  public void setTaskStatus(String taskStatus) {
    this.taskStatus = taskStatus;
  }

  /**
   * <p>实际赋码瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottles <p>实际赋码瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottles(Integer bottles) {
    this.bottles = bottles;
  }

  /**
   * <p>实际赋码株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plants <p>实际赋码株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlants(Integer plants) {
    this.plants = plants;
  }

  /**
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param creatorId <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
  }

  /**
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param creatorName <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
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
   * <p>末次操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorId <p>末次操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * <p>末次操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorName <p>末次操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
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
  public BottleCodeComplementTaskPO cloneSimple() {
    try{
      return (BottleCodeComplementTaskPO)clone();
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
    if(other instanceof BottleCodeComplementTaskPO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.BottleCodeComplementTask o) return fetchId().equals(o.fetchId());
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
