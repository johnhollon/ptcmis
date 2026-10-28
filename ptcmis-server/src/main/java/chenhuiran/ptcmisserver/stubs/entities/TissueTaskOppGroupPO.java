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
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.TissueTaskOppGroup} 接口。</p>
 * <p>该类实现了 {@link org.springframework.data.domain.Persistable} 接口。</p>
 * <p>组培任务‑操作分配组</p>
 * <p>数据库表： <code>tissue_task_opp_group</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.TissueTaskOppGroup}构造方法。</li>
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
    name = "tissue_task_opp_group"
)
public class TissueTaskOppGroupPO implements Cloneable, Persistable<String>, TissueTaskOppGroup {
  /**
   * 为了实现 Persistable 接口的 isNew() 方法而创建的 Transient 属性。
   */
  @Transient
  boolean is___new___for___persistable___ = true;

  /**
   * <p>操作分配组ID。主键</p>
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
   * <p>所属任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "id_task"
  )
  private String idTask;

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "variety_code"
  )
  private String varietyCode = "";

  /**
   * <p>母瓶株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "parent_plant_count"
  )
  private Integer parentPlantCount = 0;

  /**
   * <p>母瓶瓶码（ID） bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "id_parent_bottle"
  )
  private String idParentBottle = "";

  /**
   * <p>母瓶是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Column(
      name = "parent_keep"
  )
  private String parentKeep = "0";

  /**
   * <p>母瓶留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "parent_keep_plant_count"
  )
  private Integer parentKeepPlantCount = 0;

  /**
   * <p>分配人ID（员工ID），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "allocator_id"
  )
  private String allocatorId = "";

  /**
   * <p>分配人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "allocator_name"
  )
  private String allocatorName = "";

  /**
   * <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Column(
      name = "allocate_time"
  )
  private Long allocateTime = 0L;

  /**
   * <p>完成时间 UTC毫秒</p>
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
   * <p>被分配质检员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "assignee_inspector_id"
  )
  private String assigneeInspectorId = "";

  /**
   * <p>被分配质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "assignee_inspector_name"
  )
  private String assigneeInspectorName = "";

  /**
   * <p>被分配操作员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "assignee_operator_id"
  )
  private String assigneeOperatorId = "";

  /**
   * <p>被分配操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "assignee_operator_name"
  )
  private String assigneeOperatorName = "";

  /**
   * <p>实际质检员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "inspector_id"
  )
  private String inspectorId = "";

  /**
   * <p>实际质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "inspector_name"
  )
  private String inspectorName = "";

  /**
   * <p>实际操作员ID，user.name</p>
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
   * <p>实际操作员姓名，user.name</p>
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
   * <p>无参构造方法。</p>
   * <p>组培任务‑操作分配组</p>
   * <p>数据库表： <code>tissue_task_opp_group</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public TissueTaskOppGroupPO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>组培任务‑操作分配组</p>
   * <p>数据库表： <code>tissue_task_opp_group</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>操作分配组ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idTask <p>所属任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param varietyCode <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param parentPlantCount <p>母瓶株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param idParentBottle <p>母瓶瓶码（ID） bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param parentKeep <p>母瓶是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param parentKeepPlantCount <p>母瓶留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param allocatorId <p>分配人ID（员工ID），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param allocatorName <p>分配人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
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
   * @param assigneeInspectorId <p>被分配质检员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param assigneeInspectorName <p>被分配质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param assigneeOperatorId <p>被分配操作员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param assigneeOperatorName <p>被分配操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param inspectorId <p>实际质检员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param inspectorName <p>实际质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorId <p>实际操作员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorName <p>实际操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   */
  public TissueTaskOppGroupPO(String id, String idTask, String varietyCode,
      Integer parentPlantCount, String idParentBottle, String parentKeep,
      Integer parentKeepPlantCount, String allocatorId, String allocatorName, Long allocateTime,
      Long finishTime, String assigneeInspectorId, String assigneeInspectorName,
      String assigneeOperatorId, String assigneeOperatorName, String inspectorId,
      String inspectorName, String operatorId, String operatorName) {
    this.id = id;
    this.idTask = idTask;
    this.varietyCode = varietyCode;
    this.parentPlantCount = parentPlantCount;
    this.idParentBottle = idParentBottle;
    this.parentKeep = parentKeep;
    this.parentKeepPlantCount = parentKeepPlantCount;
    this.allocatorId = allocatorId;
    this.allocatorName = allocatorName;
    this.allocateTime = allocateTime;
    this.finishTime = finishTime;
    this.assigneeInspectorId = assigneeInspectorId;
    this.assigneeInspectorName = assigneeInspectorName;
    this.assigneeOperatorId = assigneeOperatorId;
    this.assigneeOperatorName = assigneeOperatorName;
    this.inspectorId = inspectorId;
    this.inspectorName = inspectorName;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>组培任务‑操作分配组</p>
   * <p>数据库表： <code>tissue_task_opp_group</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public TissueTaskOppGroupPO(TissueTaskOppGroup model) {
    this.id = model.getId();
    this.idTask = model.getIdTask();
    this.varietyCode = model.getVarietyCode();
    this.parentPlantCount = model.getParentPlantCount();
    this.idParentBottle = model.getIdParentBottle();
    this.parentKeep = model.getParentKeep();
    this.parentKeepPlantCount = model.getParentKeepPlantCount();
    this.allocatorId = model.getAllocatorId();
    this.allocatorName = model.getAllocatorName();
    this.allocateTime = model.getAllocateTime();
    this.finishTime = model.getFinishTime();
    this.assigneeInspectorId = model.getAssigneeInspectorId();
    this.assigneeInspectorName = model.getAssigneeInspectorName();
    this.assigneeOperatorId = model.getAssigneeOperatorId();
    this.assigneeOperatorName = model.getAssigneeOperatorName();
    this.inspectorId = model.getInspectorId();
    this.inspectorName = model.getInspectorName();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
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
   * <p>操作分配组ID。主键</p>
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
   * <p>所属任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getIdTask() {
    return idTask;
  }

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getVarietyCode() {
    return varietyCode;
  }

  /**
   * <p>母瓶株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getParentPlantCount() {
    return parentPlantCount;
  }

  /**
   * <p>母瓶瓶码（ID） bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdParentBottle() {
    return idParentBottle;
  }

  /**
   * <p>母瓶是否留种。 dictionary.boolean</p>
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
   * <p>母瓶留种株数。</p>
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
   * <p>分配人ID（员工ID），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getAllocatorId() {
    return allocatorId;
  }

  /**
   * <p>分配人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getAllocatorName() {
    return allocatorName;
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
   * <p>被分配质检员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getAssigneeInspectorId() {
    return assigneeInspectorId;
  }

  /**
   * <p>被分配质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getAssigneeInspectorName() {
    return assigneeInspectorName;
  }

  /**
   * <p>被分配操作员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getAssigneeOperatorId() {
    return assigneeOperatorId;
  }

  /**
   * <p>被分配操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getAssigneeOperatorName() {
    return assigneeOperatorName;
  }

  /**
   * <p>实际质检员ID，user.name</p>
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
   * <p>实际质检员姓名，user.name</p>
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
   * <p>实际操作员ID，user.name</p>
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
   * <p>实际操作员姓名，user.name</p>
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
   * <p>操作分配组ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>操作分配组ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>所属任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param idTask <p>所属任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setIdTask(String idTask) {
    this.idTask = idTask;
  }

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param varietyCode <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setVarietyCode(String varietyCode) {
    this.varietyCode = varietyCode;
  }

  /**
   * <p>母瓶株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param parentPlantCount <p>母瓶株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setParentPlantCount(Integer parentPlantCount) {
    this.parentPlantCount = parentPlantCount;
  }

  /**
   * <p>母瓶瓶码（ID） bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idParentBottle <p>母瓶瓶码（ID） bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdParentBottle(String idParentBottle) {
    this.idParentBottle = idParentBottle;
  }

  /**
   * <p>母瓶是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param parentKeep <p>母瓶是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setParentKeep(String parentKeep) {
    this.parentKeep = parentKeep;
  }

  /**
   * <p>母瓶留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param parentKeepPlantCount <p>母瓶留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setParentKeepPlantCount(Integer parentKeepPlantCount) {
    this.parentKeepPlantCount = parentKeepPlantCount;
  }

  /**
   * <p>分配人ID（员工ID），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param allocatorId <p>分配人ID（员工ID），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setAllocatorId(String allocatorId) {
    this.allocatorId = allocatorId;
  }

  /**
   * <p>分配人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param allocatorName <p>分配人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setAllocatorName(String allocatorName) {
    this.allocatorName = allocatorName;
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
   * <p>被分配质检员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param assigneeInspectorId <p>被分配质检员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setAssigneeInspectorId(String assigneeInspectorId) {
    this.assigneeInspectorId = assigneeInspectorId;
  }

  /**
   * <p>被分配质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param assigneeInspectorName <p>被分配质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setAssigneeInspectorName(String assigneeInspectorName) {
    this.assigneeInspectorName = assigneeInspectorName;
  }

  /**
   * <p>被分配操作员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param assigneeOperatorId <p>被分配操作员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setAssigneeOperatorId(String assigneeOperatorId) {
    this.assigneeOperatorId = assigneeOperatorId;
  }

  /**
   * <p>被分配操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param assigneeOperatorName <p>被分配操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setAssigneeOperatorName(String assigneeOperatorName) {
    this.assigneeOperatorName = assigneeOperatorName;
  }

  /**
   * <p>实际质检员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param inspectorId <p>实际质检员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setInspectorId(String inspectorId) {
    this.inspectorId = inspectorId;
  }

  /**
   * <p>实际质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param inspectorName <p>实际质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setInspectorName(String inspectorName) {
    this.inspectorName = inspectorName;
  }

  /**
   * <p>实际操作员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorId <p>实际操作员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * <p>实际操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorName <p>实际操作员姓名，user.name</p>
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
  public TissueTaskOppGroupPO cloneSimple() {
    try{
      return (TissueTaskOppGroupPO)clone();
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
    if(other instanceof TissueTaskOppGroupPO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.TissueTaskOppGroup o) return fetchId().equals(o.fetchId());
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
