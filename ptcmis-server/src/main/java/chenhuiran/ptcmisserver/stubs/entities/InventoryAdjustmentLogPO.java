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
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentLog} 接口。</p>
 * <p>该类实现了 {@link org.springframework.data.domain.Persistable} 接口。</p>
 * <p>盘点调差操作日志</p>
 * <p>数据库表： <code>inventory_adjustment_log</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentLog}构造方法。</li>
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
    name = "inventory_adjustment_log"
)
public class InventoryAdjustmentLogPO implements Cloneable, Persistable<String>, InventoryAdjustmentLog {
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
   * <p>调差ID. inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "id_adjustment"
  )
  private String idAdjustment;

  /**
   * <p>状态。 dictionary = inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  @Column(
      name = "status"
  )
  private String status = "created";

  /**
   * <p>操作人ID，user.id</p>
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
   * <p>操作人姓名，user.name</p>
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
   * <p>操作时间，UTC 毫秒</p>
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
   * <p>盘点调差操作日志</p>
   * <p>数据库表： <code>inventory_adjustment_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public InventoryAdjustmentLogPO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>盘点调差操作日志</p>
   * <p>数据库表： <code>inventory_adjustment_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idAdjustment <p>调差ID. inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param status <p>状态。 dictionary = inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   *
   * @param operatorId <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorName <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorTime <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   */
  public InventoryAdjustmentLogPO(String id, String idAdjustment, String status, String operatorId,
      String operatorName, Long operatorTime) {
    this.id = id;
    this.idAdjustment = idAdjustment;
    this.status = status;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.operatorTime = operatorTime;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>盘点调差操作日志</p>
   * <p>数据库表： <code>inventory_adjustment_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public InventoryAdjustmentLogPO(InventoryAdjustmentLog model) {
    this.id = model.getId();
    this.idAdjustment = model.getIdAdjustment();
    this.status = model.getStatus();
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
   * <p>调差ID. inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getIdAdjustment() {
    return idAdjustment;
  }

  /**
   * <p>状态。 dictionary = inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  @Override
  public String getStatus() {
    return status;
  }

  /**
   * <p>操作人ID，user.id</p>
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
   * <p>操作人姓名，user.name</p>
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
   * <p>操作时间，UTC 毫秒</p>
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
   * <p>调差ID. inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param idAdjustment <p>调差ID. inventory_adjustment.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_adjustment</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setIdAdjustment(String idAdjustment) {
    this.idAdjustment = idAdjustment;
  }

  /**
   * <p>状态。 dictionary = inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   * @param status <p>状态。 dictionary = inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorId <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorName <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  /**
   * <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param operatorTime <p>操作时间，UTC 毫秒</p>
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
  public InventoryAdjustmentLogPO cloneSimple() {
    try{
      return (InventoryAdjustmentLogPO)clone();
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
    if(other instanceof InventoryAdjustmentLogPO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentLog o) return fetchId().equals(o.fetchId());
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
