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
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.BottleCodeSequence} 接口。</p>
 * <p>该类实现了 {@link org.springframework.data.domain.Persistable} 接口。</p>
 * <p>瓶码序列计数器表</p>
 * <p>数据库表： <code>bottle_code_sequence</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.BottleCodeSequence}构造方法。</li>
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
    name = "bottle_code_sequence"
)
public class BottleCodeSequencePO implements Cloneable, Persistable<Integer>, BottleCodeSequence {
  /**
   * 为了实现 Persistable 接口的 isNew() 方法而创建的 Transient 属性。
   */
  @Transient
  boolean is___new___for___persistable___ = true;

  /**
   * <p>主键，固定为 0</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>TINYINT UNSIGNED(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Column(
      name = "id"
  )
  @Id
  private Integer id;

  /**
   * <p>下一个应用的编码数值</p>
   * <ul>
   *   <li>COLUMN : <code>current_value</code></li>
   *   <li>DDL : <code>BIGINT UNSIGNED(20), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Column(
      name = "current_value"
  )
  private Long currentValue = 0L;

  /**
   * <p>无参构造方法。</p>
   * <p>瓶码序列计数器表</p>
   * <p>数据库表： <code>bottle_code_sequence</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public BottleCodeSequencePO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>瓶码序列计数器表</p>
   * <p>数据库表： <code>bottle_code_sequence</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>主键，固定为 0</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>TINYINT UNSIGNED(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param currentValue <p>下一个应用的编码数值</p>
   * <ul>
   *   <li>COLUMN : <code>current_value</code></li>
   *   <li>DDL : <code>BIGINT UNSIGNED(20), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   */
  public BottleCodeSequencePO(Integer id, Long currentValue) {
    this.id = id;
    this.currentValue = currentValue;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>瓶码序列计数器表</p>
   * <p>数据库表： <code>bottle_code_sequence</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public BottleCodeSequencePO(BottleCodeSequence model) {
    this.id = model.getId();
    this.currentValue = model.getCurrentValue();
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
   * <p>主键，固定为 0</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>TINYINT UNSIGNED(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getId() {
    return id;
  }

  /**
   * <p>下一个应用的编码数值</p>
   * <ul>
   *   <li>COLUMN : <code>current_value</code></li>
   *   <li>DDL : <code>BIGINT UNSIGNED(20), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getCurrentValue() {
    return currentValue;
  }

  /**
   * <p>主键，固定为 0</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>TINYINT UNSIGNED(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>主键，固定为 0</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>TINYINT UNSIGNED(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * <p>下一个应用的编码数值</p>
   * <ul>
   *   <li>COLUMN : <code>current_value</code></li>
   *   <li>DDL : <code>BIGINT UNSIGNED(20), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param currentValue <p>下一个应用的编码数值</p>
   * <ul>
   *   <li>COLUMN : <code>current_value</code></li>
   *   <li>DDL : <code>BIGINT UNSIGNED(20), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setCurrentValue(Long currentValue) {
    this.currentValue = currentValue;
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
  public BottleCodeSequencePO cloneSimple() {
    try{
      return (BottleCodeSequencePO)clone();
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
    if(other instanceof BottleCodeSequencePO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.BottleCodeSequence o) return fetchId().equals(o.fetchId());
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
