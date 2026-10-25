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
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.InventoryAddBottleDetail} 接口。</p>
 * <p>该类实现了 {@link org.springframework.data.domain.Persistable} 接口。</p>
 * <p>库存增瓶任务明细</p>
 * <p>数据库表： <code>inventory_add_bottle_detail</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.InventoryAddBottleDetail}构造方法。</li>
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
    name = "inventory_add_bottle_detail"
)
public class InventoryAddBottleDetailPO implements Cloneable, Persistable<String>, InventoryAddBottleDetail {
  /**
   * 为了实现 Persistable 接口的 isNew() 方法而创建的 Transient 属性。
   */
  @Transient
  boolean is___new___for___persistable___ = true;

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "id"
  )
  @Id
  private String id = "";

  /**
   * <p>所属任务ID。inventory_add_bottle.id</p>
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
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "id_parent"
  )
  private String idParent = "";

  /**
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Column(
      name = "plant_count"
  )
  private Integer plantCount = 0;

  /**
   * <p>质检员ID，user.id</p>
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
   * <p>质检员姓名，user.name</p>
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
   * <p>操作员ID，user.id</p>
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
   * <p>操作员姓名，user.name</p>
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
   * <p>操作时间 UTC 毫秒时间戳</p>
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
   * <p>赋码状态：dictionary.bottle_code_complement_code_stat</p>
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
   * <p>无参构造方法。</p>
   * <p>库存增瓶任务明细</p>
   * <p>数据库表： <code>inventory_add_bottle_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public InventoryAddBottleDetailPO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>库存增瓶任务明细</p>
   * <p>数据库表： <code>inventory_add_bottle_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param idTask <p>所属任务ID。inventory_add_bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idParent <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param plantCount <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
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
   * @param operatorTime <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param status <p>赋码状态：dictionary.bottle_code_complement_code_stat</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   *
   */
  public InventoryAddBottleDetailPO(String id, String idTask, String idParent, Integer plantCount,
      String inspectorId, String inspectorName, String operatorId, String operatorName,
      Long operatorTime, String status) {
    this.id = id;
    this.idTask = idTask;
    this.idParent = idParent;
    this.plantCount = plantCount;
    this.inspectorId = inspectorId;
    this.inspectorName = inspectorName;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.operatorTime = operatorTime;
    this.status = status;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>库存增瓶任务明细</p>
   * <p>数据库表： <code>inventory_add_bottle_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public InventoryAddBottleDetailPO(InventoryAddBottleDetail model) {
    this.id = model.getId();
    this.idTask = model.getIdTask();
    this.idParent = model.getIdParent();
    this.plantCount = model.getPlantCount();
    this.inspectorId = model.getInspectorId();
    this.inspectorName = model.getInspectorName();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
    this.operatorTime = model.getOperatorTime();
    this.status = model.getStatus();
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
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * <p>所属任务ID。inventory_add_bottle.id</p>
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
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdParent() {
    return idParent;
  }

  /**
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantCount() {
    return plantCount;
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
   * <p>操作时间 UTC 毫秒时间戳</p>
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
   * <p>赋码状态：dictionary.bottle_code_complement_code_stat</p>
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
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param id <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>所属任务ID。inventory_add_bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param idTask <p>所属任务ID。inventory_add_bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setIdTask(String idTask) {
    this.idTask = idTask;
  }

  /**
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idParent <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdParent(String idParent) {
    this.idParent = idParent;
  }

  /**
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantCount <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantCount(Integer plantCount) {
    this.plantCount = plantCount;
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
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param operatorTime <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setOperatorTime(Long operatorTime) {
    this.operatorTime = operatorTime;
  }

  /**
   * <p>赋码状态：dictionary.bottle_code_complement_code_stat</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   * @param status <p>赋码状态：dictionary.bottle_code_complement_code_stat</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  public void setStatus(String status) {
    this.status = status;
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
  public InventoryAddBottleDetailPO cloneSimple() {
    try{
      return (InventoryAddBottleDetailPO)clone();
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
    if(other instanceof InventoryAddBottleDetailPO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.InventoryAddBottleDetail o) return fetchId().equals(o.fetchId());
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
