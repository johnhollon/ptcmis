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
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.StorageInOut} 接口。</p>
 * <p>该类实现了 {@link org.springframework.data.domain.Persistable} 接口。</p>
 * <p>出库/入库单</p>
 * <p>数据库表： <code>storage_in_out</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.StorageInOut}构造方法。</li>
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
    name = "storage_in_out"
)
public class StorageInOutPO implements Cloneable, Persistable<String>, StorageInOut {
  /**
   * 为了实现 Persistable 接口的 isNew() 方法而创建的 Transient 属性。
   */
  @Transient
  boolean is___new___for___persistable___ = true;

  /**
   * <p>ID主键</p>
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
   * <p>出入库类别，标记出库还是入库。 dictionary = storage_in_out_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "in_out"
  )
  private String inOut;

  /**
   * <p>申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "bottles_applied"
  )
  private Integer bottlesApplied = 0;

  /**
   * <p>申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "plants_applied"
  )
  private Integer plantsApplied = 0;

  /**
   * <p>申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "bottles_keep_in_applied"
  )
  private Integer bottlesKeepInApplied = 0;

  /**
   * <p>申请入库株数中母代留种株数（是 plants_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "plants_keep_in_applied"
  )
  private Integer plantsKeepInApplied = 0;

  /**
   * <p>实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "bottles_actual"
  )
  private Integer bottlesActual = 0;

  /**
   * <p>实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "plants_actual"
  )
  private Integer plantsActual = 0;

  /**
   * <p>实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "bottles_keep_in_actual"
  )
  private Integer bottlesKeepInActual = 0;

  /**
   * <p>实际入库株数中母代留种株数（是 plants_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "plants_keep_in_actual"
  )
  private Integer plantsKeepInActual = 0;

  /**
   * <p>淘汰瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "bottles_discard"
  )
  private Integer bottlesDiscard = 0;

  /**
   * <p>淘汰株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "plants_discard"
  )
  private Integer plantsDiscard = 0;

  /**
   * <p>废弃瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "bottles_voided"
  )
  private Integer bottlesVoided = 0;

  /**
   * <p>废弃株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "plants_voided"
  )
  private Integer plantsVoided = 0;

  /**
   * <p>是否在统计中忽略该入库单。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Column(
      name = "ignore_stats"
  )
  private String ignoreStats = "0";

  /**
   * <p>出入库原因。 dictionary = storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "reason"
  )
  private String reason;

  /**
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  @Column(
      name = "status"
  )
  private String status = "apply";

  /**
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "opp_reason"
  )
  private String oppReason;

  /**
   * <p>产生出入库业务的组培任务ID。tissue_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "id_task"
  )
  private String idTask = "";

  /**
   * <p>产生出入库业务的质检任务ID。inspection_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_inspection</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "id_inspection"
  )
  private String idInspection = "";

  /**
   * <p>申请人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "applicant_id"
  )
  private String applicantId;

  /**
   * <p>申请人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "applicant_name"
  )
  private String applicantName;

  /**
   * <p>申请时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "applicant_time"
  )
  private Long applicantTime;

  /**
   * <p>末次操作人ID，user.id</p>
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
   * <p>末次操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Column(
      name = "operator_time"
  )
  private Long operatorTime = 0L;

  /**
   * <p>无参构造方法。</p>
   * <p>出库/入库单</p>
   * <p>数据库表： <code>storage_in_out</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public StorageInOutPO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>出库/入库单</p>
   * <p>数据库表： <code>storage_in_out</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param inOut <p>出入库类别，标记出库还是入库。 dictionary = storage_in_out_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param bottlesApplied <p>申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantsApplied <p>申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param bottlesKeepInApplied <p>申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantsKeepInApplied <p>申请入库株数中母代留种株数（是 plants_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param bottlesActual <p>实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantsActual <p>实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param bottlesKeepInActual <p>实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantsKeepInActual <p>实际入库株数中母代留种株数（是 plants_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param bottlesDiscard <p>淘汰瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantsDiscard <p>淘汰株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param bottlesVoided <p>废弃瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantsVoided <p>废弃株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param ignoreStats <p>是否在统计中忽略该入库单。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param reason <p>出入库原因。 dictionary = storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param status <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   *
   * @param oppReason <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idTask <p>产生出入库业务的组培任务ID。tissue_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param idInspection <p>产生出入库业务的质检任务ID。inspection_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_inspection</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param applicantId <p>申请人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param applicantName <p>申请人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param applicantTime <p>申请时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param operatorId <p>末次操作人ID，user.id</p>
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
   * @param operatorTime <p>末次操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   */
  public StorageInOutPO(String id, String inOut, Integer bottlesApplied, Integer plantsApplied,
      Integer bottlesKeepInApplied, Integer plantsKeepInApplied, Integer bottlesActual,
      Integer plantsActual, Integer bottlesKeepInActual, Integer plantsKeepInActual,
      Integer bottlesDiscard, Integer plantsDiscard, Integer bottlesVoided, Integer plantsVoided,
      String ignoreStats, String reason, String status, String oppReason, String idTask,
      String idInspection, String applicantId, String applicantName, Long applicantTime,
      String operatorId, String operatorName, Long operatorTime) {
    this.id = id;
    this.inOut = inOut;
    this.bottlesApplied = bottlesApplied;
    this.plantsApplied = plantsApplied;
    this.bottlesKeepInApplied = bottlesKeepInApplied;
    this.plantsKeepInApplied = plantsKeepInApplied;
    this.bottlesActual = bottlesActual;
    this.plantsActual = plantsActual;
    this.bottlesKeepInActual = bottlesKeepInActual;
    this.plantsKeepInActual = plantsKeepInActual;
    this.bottlesDiscard = bottlesDiscard;
    this.plantsDiscard = plantsDiscard;
    this.bottlesVoided = bottlesVoided;
    this.plantsVoided = plantsVoided;
    this.ignoreStats = ignoreStats;
    this.reason = reason;
    this.status = status;
    this.oppReason = oppReason;
    this.idTask = idTask;
    this.idInspection = idInspection;
    this.applicantId = applicantId;
    this.applicantName = applicantName;
    this.applicantTime = applicantTime;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.operatorTime = operatorTime;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>出库/入库单</p>
   * <p>数据库表： <code>storage_in_out</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public StorageInOutPO(StorageInOut model) {
    this.id = model.getId();
    this.inOut = model.getInOut();
    this.bottlesApplied = model.getBottlesApplied();
    this.plantsApplied = model.getPlantsApplied();
    this.bottlesKeepInApplied = model.getBottlesKeepInApplied();
    this.plantsKeepInApplied = model.getPlantsKeepInApplied();
    this.bottlesActual = model.getBottlesActual();
    this.plantsActual = model.getPlantsActual();
    this.bottlesKeepInActual = model.getBottlesKeepInActual();
    this.plantsKeepInActual = model.getPlantsKeepInActual();
    this.bottlesDiscard = model.getBottlesDiscard();
    this.plantsDiscard = model.getPlantsDiscard();
    this.bottlesVoided = model.getBottlesVoided();
    this.plantsVoided = model.getPlantsVoided();
    this.ignoreStats = model.getIgnoreStats();
    this.reason = model.getReason();
    this.status = model.getStatus();
    this.oppReason = model.getOppReason();
    this.idTask = model.getIdTask();
    this.idInspection = model.getIdInspection();
    this.applicantId = model.getApplicantId();
    this.applicantName = model.getApplicantName();
    this.applicantTime = model.getApplicantTime();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
    this.operatorTime = model.getOperatorTime();
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
   * <p>ID主键</p>
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
   * <p>出入库类别，标记出库还是入库。 dictionary = storage_in_out_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getInOut() {
    return inOut;
  }

  /**
   * <p>申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getBottlesApplied() {
    return bottlesApplied;
  }

  /**
   * <p>申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantsApplied() {
    return plantsApplied;
  }

  /**
   * <p>申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getBottlesKeepInApplied() {
    return bottlesKeepInApplied;
  }

  /**
   * <p>申请入库株数中母代留种株数（是 plants_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantsKeepInApplied() {
    return plantsKeepInApplied;
  }

  /**
   * <p>实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getBottlesActual() {
    return bottlesActual;
  }

  /**
   * <p>实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantsActual() {
    return plantsActual;
  }

  /**
   * <p>实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getBottlesKeepInActual() {
    return bottlesKeepInActual;
  }

  /**
   * <p>实际入库株数中母代留种株数（是 plants_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantsKeepInActual() {
    return plantsKeepInActual;
  }

  /**
   * <p>淘汰瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getBottlesDiscard() {
    return bottlesDiscard;
  }

  /**
   * <p>淘汰株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantsDiscard() {
    return plantsDiscard;
  }

  /**
   * <p>废弃瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getBottlesVoided() {
    return bottlesVoided;
  }

  /**
   * <p>废弃株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantsVoided() {
    return plantsVoided;
  }

  /**
   * <p>是否在统计中忽略该入库单。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getIgnoreStats() {
    return ignoreStats;
  }

  /**
   * <p>出入库原因。 dictionary = storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getReason() {
    return reason;
  }

  /**
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  @Override
  public String getStatus() {
    return status;
  }

  /**
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getOppReason() {
    return oppReason;
  }

  /**
   * <p>产生出入库业务的组培任务ID。tissue_task.id。</p>
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
   * <p>产生出入库业务的质检任务ID。inspection_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_inspection</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdInspection() {
    return idInspection;
  }

  /**
   * <p>申请人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getApplicantId() {
    return applicantId;
  }

  /**
   * <p>申请人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getApplicantName() {
    return applicantName;
  }

  /**
   * <p>申请时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Long getApplicantTime() {
    return applicantTime;
  }

  /**
   * <p>末次操作人ID，user.id</p>
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
   * <p>末次操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getOperatorTime() {
    return operatorTime;
  }

  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>出入库类别，标记出库还是入库。 dictionary = storage_in_out_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param inOut <p>出入库类别，标记出库还是入库。 dictionary = storage_in_out_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setInOut(String inOut) {
    this.inOut = inOut;
  }

  /**
   * <p>申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottlesApplied <p>申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottlesApplied(Integer bottlesApplied) {
    this.bottlesApplied = bottlesApplied;
  }

  /**
   * <p>申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantsApplied <p>申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantsApplied(Integer plantsApplied) {
    this.plantsApplied = plantsApplied;
  }

  /**
   * <p>申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottlesKeepInApplied <p>申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottlesKeepInApplied(Integer bottlesKeepInApplied) {
    this.bottlesKeepInApplied = bottlesKeepInApplied;
  }

  /**
   * <p>申请入库株数中母代留种株数（是 plants_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantsKeepInApplied <p>申请入库株数中母代留种株数（是 plants_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantsKeepInApplied(Integer plantsKeepInApplied) {
    this.plantsKeepInApplied = plantsKeepInApplied;
  }

  /**
   * <p>实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottlesActual <p>实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottlesActual(Integer bottlesActual) {
    this.bottlesActual = bottlesActual;
  }

  /**
   * <p>实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantsActual <p>实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantsActual(Integer plantsActual) {
    this.plantsActual = plantsActual;
  }

  /**
   * <p>实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottlesKeepInActual <p>实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottlesKeepInActual(Integer bottlesKeepInActual) {
    this.bottlesKeepInActual = bottlesKeepInActual;
  }

  /**
   * <p>实际入库株数中母代留种株数（是 plants_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantsKeepInActual <p>实际入库株数中母代留种株数（是 plants_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantsKeepInActual(Integer plantsKeepInActual) {
    this.plantsKeepInActual = plantsKeepInActual;
  }

  /**
   * <p>淘汰瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottlesDiscard <p>淘汰瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottlesDiscard(Integer bottlesDiscard) {
    this.bottlesDiscard = bottlesDiscard;
  }

  /**
   * <p>淘汰株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantsDiscard <p>淘汰株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantsDiscard(Integer plantsDiscard) {
    this.plantsDiscard = plantsDiscard;
  }

  /**
   * <p>废弃瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottlesVoided <p>废弃瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottlesVoided(Integer bottlesVoided) {
    this.bottlesVoided = bottlesVoided;
  }

  /**
   * <p>废弃株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantsVoided <p>废弃株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantsVoided(Integer plantsVoided) {
    this.plantsVoided = plantsVoided;
  }

  /**
   * <p>是否在统计中忽略该入库单。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param ignoreStats <p>是否在统计中忽略该入库单。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setIgnoreStats(String ignoreStats) {
    this.ignoreStats = ignoreStats;
  }

  /**
   * <p>出入库原因。 dictionary = storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param reason <p>出入库原因。 dictionary = storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   * @param status <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param oppReason <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setOppReason(String oppReason) {
    this.oppReason = oppReason;
  }

  /**
   * <p>产生出入库业务的组培任务ID。tissue_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idTask <p>产生出入库业务的组培任务ID。tissue_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdTask(String idTask) {
    this.idTask = idTask;
  }

  /**
   * <p>产生出入库业务的质检任务ID。inspection_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_inspection</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idInspection <p>产生出入库业务的质检任务ID。inspection_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_inspection</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdInspection(String idInspection) {
    this.idInspection = idInspection;
  }

  /**
   * <p>申请人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param applicantId <p>申请人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setApplicantId(String applicantId) {
    this.applicantId = applicantId;
  }

  /**
   * <p>申请人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param applicantName <p>申请人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setApplicantName(String applicantName) {
    this.applicantName = applicantName;
  }

  /**
   * <p>申请时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param applicantTime <p>申请时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setApplicantTime(Long applicantTime) {
    this.applicantTime = applicantTime;
  }

  /**
   * <p>末次操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorId <p>末次操作人ID，user.id</p>
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
   * <p>末次操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param operatorTime <p>末次操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setOperatorTime(Long operatorTime) {
    this.operatorTime = operatorTime;
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
  public StorageInOutPO cloneSimple() {
    try{
      return (StorageInOutPO)clone();
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
    if(other instanceof StorageInOutPO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.StorageInOut o) return fetchId().equals(o.fetchId());
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
