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
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.BottleLog} 接口。</p>
 * <p>该类实现了 {@link org.springframework.data.domain.Persistable} 接口。</p>
 * <p>组培瓶操作日志</p>
 * <p>数据库表： <code>bottle_log</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.BottleLog}构造方法。</li>
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
    name = "bottle_log"
)
public class BottleLogPO implements Cloneable, Persistable<String>, BottleLog {
  /**
   * 为了实现 Persistable 接口的 isNew() 方法而创建的 Transient 属性。
   */
  @Transient
  boolean is___new___for___persistable___ = true;

  /**
   * <p>ID</p>
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
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "id_bottle"
  )
  private String idBottle;

  /**
   * <p>执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>opp_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Column(
      name = "opp_time"
  )
  private Long oppTime = 0L;

  /**
   * <p>数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "table_name"
  )
  private String tableName;

  /**
   * <p>数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "record_id"
  )
  private String recordId;

  /**
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(64), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "biz_type"
  )
  private String bizType;

  /**
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "description"
  )
  private String description;

  /**
   * <p>质检员ID。 user.id</p>
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
   * <p>质检员姓名。 user.name</p>
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
   * <p>操作员ID。 user.id</p>
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
   * <p>操作员姓名。 user.name</p>
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
   * <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "operate_status"
  )
  private String operateStatus = "";

  /**
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "storage_status"
  )
  private String storageStatus = "";

  /**
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>storage_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "storage_reason"
  )
  private String storageReason = "";

  /**
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Column(
      name = "discarded"
  )
  private String discarded = "0";

  /**
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "discard_reason"
  )
  private String discardReason = "";

  /**
   * <p>无参构造方法。</p>
   * <p>组培瓶操作日志</p>
   * <p>数据库表： <code>bottle_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public BottleLogPO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>组培瓶操作日志</p>
   * <p>数据库表： <code>bottle_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idBottle <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param oppTime <p>执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>opp_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param tableName <p>数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param recordId <p>数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param bizType <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(64), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param description <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param inspectorId <p>质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param inspectorName <p>质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorId <p>操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorName <p>操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operateStatus <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param storageStatus <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param storageReason <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>storage_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param discarded <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param discardReason <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   */
  public BottleLogPO(String id, String idBottle, Long oppTime, String tableName, String recordId,
      String bizType, String description, String inspectorId, String inspectorName,
      String operatorId, String operatorName, String operateStatus, String storageStatus,
      String storageReason, String discarded, String discardReason) {
    this.id = id;
    this.idBottle = idBottle;
    this.oppTime = oppTime;
    this.tableName = tableName;
    this.recordId = recordId;
    this.bizType = bizType;
    this.description = description;
    this.inspectorId = inspectorId;
    this.inspectorName = inspectorName;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.operateStatus = operateStatus;
    this.storageStatus = storageStatus;
    this.storageReason = storageReason;
    this.discarded = discarded;
    this.discardReason = discardReason;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>组培瓶操作日志</p>
   * <p>数据库表： <code>bottle_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public BottleLogPO(BottleLog model) {
    this.id = model.getId();
    this.idBottle = model.getIdBottle();
    this.oppTime = model.getOppTime();
    this.tableName = model.getTableName();
    this.recordId = model.getRecordId();
    this.bizType = model.getBizType();
    this.description = model.getDescription();
    this.inspectorId = model.getInspectorId();
    this.inspectorName = model.getInspectorName();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
    this.operateStatus = model.getOperateStatus();
    this.storageStatus = model.getStorageStatus();
    this.storageReason = model.getStorageReason();
    this.discarded = model.getDiscarded();
    this.discardReason = model.getDiscardReason();
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
   * <p>ID</p>
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
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getIdBottle() {
    return idBottle;
  }

  /**
   * <p>执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>opp_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getOppTime() {
    return oppTime;
  }

  /**
   * <p>数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getTableName() {
    return tableName;
  }

  /**
   * <p>数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getRecordId() {
    return recordId;
  }

  /**
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(64), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getBizType() {
    return bizType;
  }

  /**
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * <p>质检员ID。 user.id</p>
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
   * <p>质检员姓名。 user.name</p>
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
   * <p>操作员ID。 user.id</p>
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
   * <p>操作员姓名。 user.name</p>
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
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getStorageStatus() {
    return storageStatus;
  }

  /**
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>storage_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getStorageReason() {
    return storageReason;
  }

  /**
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getDiscarded() {
    return discarded;
  }

  /**
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getDiscardReason() {
    return discardReason;
  }

  /**
   * <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param idBottle <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setIdBottle(String idBottle) {
    this.idBottle = idBottle;
  }

  /**
   * <p>执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>opp_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param oppTime <p>执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>opp_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setOppTime(Long oppTime) {
    this.oppTime = oppTime;
  }

  /**
   * <p>数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param tableName <p>数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * <p>数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param recordId <p>数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  /**
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(64), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param bizType <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(64), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setBizType(String bizType) {
    this.bizType = bizType;
  }

  /**
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param description <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * <p>质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param inspectorId <p>质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setInspectorId(String inspectorId) {
    this.inspectorId = inspectorId;
  }

  /**
   * <p>质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param inspectorName <p>质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setInspectorName(String inspectorName) {
    this.inspectorName = inspectorName;
  }

  /**
   * <p>操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorId <p>操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * <p>操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorName <p>操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
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
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param storageStatus <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setStorageStatus(String storageStatus) {
    this.storageStatus = storageStatus;
  }

  /**
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>storage_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param storageReason <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>storage_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setStorageReason(String storageReason) {
    this.storageReason = storageReason;
  }

  /**
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param discarded <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setDiscarded(String discarded) {
    this.discarded = discarded;
  }

  /**
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param discardReason <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setDiscardReason(String discardReason) {
    this.discardReason = discardReason;
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
  public BottleLogPO cloneSimple() {
    try{
      return (BottleLogPO)clone();
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
    if(other instanceof BottleLogPO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.BottleLog o) return fetchId().equals(o.fetchId());
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
