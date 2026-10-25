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
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustment} 接口。</p>
 * <p>该类实现了 {@link org.springframework.data.domain.Persistable} 接口。</p>
 * <p>盘点调差</p>
 * <p>数据库表： <code>inventory_adjustment</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustment}构造方法。</li>
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
    name = "inventory_adjustment"
)
public class InventoryAdjustmentPO implements Cloneable, Persistable<String>, InventoryAdjustment {
  /**
   * 为了实现 Persistable 接口的 isNew() 方法而创建的 Transient 属性。
   */
  @Transient
  boolean is___new___for___persistable___ = true;

  /**
   * <p>调差ID</p>
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
   * <p>任务状态：dictionary.inventory_adjustment_status</p>
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
   * <p>调差瓶数。变小记作负数，变大记作正数。</p>
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
   * <p>调差株数。变小记作负数，变大记作正数。</p>
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
   * <p>调差原因</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Column(
      name = "reason"
  )
  private String reason = "";

  /**
   * <p>操作人用户ID，user.id</p>
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
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Column(
      name = "operate_time"
  )
  private Long operateTime = 0L;

  /**
   * <p>无参构造方法。</p>
   * <p>盘点调差</p>
   * <p>数据库表： <code>inventory_adjustment</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public InventoryAdjustmentPO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>盘点调差</p>
   * <p>数据库表： <code>inventory_adjustment</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>调差ID</p>
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
   * @param generation <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param status <p>任务状态：dictionary.inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   *
   * @param bottles <p>调差瓶数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plants <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param reason <p>调差原因</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorId <p>操作人用户ID，user.id</p>
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
   * @param operateTime <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   */
  public InventoryAdjustmentPO(String id, String variety, String varietyCode, Integer generation,
      String status, Integer bottles, Integer plants, String reason, String operatorId,
      String operatorName, Long operateTime) {
    this.id = id;
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.generation = generation;
    this.status = status;
    this.bottles = bottles;
    this.plants = plants;
    this.reason = reason;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.operateTime = operateTime;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>盘点调差</p>
   * <p>数据库表： <code>inventory_adjustment</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public InventoryAdjustmentPO(InventoryAdjustment model) {
    this.id = model.getId();
    this.variety = model.getVariety();
    this.varietyCode = model.getVarietyCode();
    this.generation = model.getGeneration();
    this.status = model.getStatus();
    this.bottles = model.getBottles();
    this.plants = model.getPlants();
    this.reason = model.getReason();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
    this.operateTime = model.getOperateTime();
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
   * <p>调差ID</p>
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
   * <p>任务状态：dictionary.inventory_adjustment_status</p>
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
   * <p>调差瓶数。变小记作负数，变大记作正数。</p>
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
   * <p>调差株数。变小记作负数，变大记作正数。</p>
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
   * <p>调差原因</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getReason() {
    return reason;
  }

  /**
   * <p>操作人用户ID，user.id</p>
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
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getOperateTime() {
    return operateTime;
  }

  /**
   * <p>调差ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>调差ID</p>
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
   * <p>任务状态：dictionary.inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   * @param status <p>任务状态：dictionary.inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * <p>调差瓶数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottles <p>调差瓶数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottles(Integer bottles) {
    this.bottles = bottles;
  }

  /**
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plants <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlants(Integer plants) {
    this.plants = plants;
  }

  /**
   * <p>调差原因</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param reason <p>调差原因</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorId <p>操作人用户ID，user.id</p>
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
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param operateTime <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setOperateTime(Long operateTime) {
    this.operateTime = operateTime;
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
  public InventoryAdjustmentPO cloneSimple() {
    try{
      return (InventoryAdjustmentPO)clone();
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
    if(other instanceof InventoryAdjustmentPO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustment o) return fetchId().equals(o.fetchId());
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
