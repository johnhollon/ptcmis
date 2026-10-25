package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.BottleRelation} 接口。</p>
 * <p>组培瓶代际追溯表</p>
 * <p>数据库表： <code>bottle_relation</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.BottleRelation}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class BottleRelationDTO implements Cloneable, BottleRelation {
  /**
   * <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String id;

  /**
   * <p>后代培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>child_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String childId;

  /**
   * <p>祖先培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>parent_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String parentId;

  /**
   * <p>代际距离；0=自身，1直接父瓶，2祖父...</p>
   * <ul>
   *   <li>COLUMN : <code>depth</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer depth;

  /**
   * <p>无参构造方法。</p>
   * <p>组培瓶代际追溯表</p>
   * <p>数据库表： <code>bottle_relation</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public BottleRelationDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>组培瓶代际追溯表</p>
   * <p>数据库表： <code>bottle_relation</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param childId <p>后代培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>child_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param parentId <p>祖先培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>parent_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param depth <p>代际距离；0=自身，1直接父瓶，2祖父...</p>
   * <ul>
   *   <li>COLUMN : <code>depth</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   */
  public BottleRelationDTO(String id, String childId, String parentId, Integer depth) {
    this.id = id;
    this.childId = childId;
    this.parentId = parentId;
    this.depth = depth;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>组培瓶代际追溯表</p>
   * <p>数据库表： <code>bottle_relation</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public BottleRelationDTO(BottleRelation model) {
    this.id = model.getId();
    this.childId = model.getChildId();
    this.parentId = model.getParentId();
    this.depth = model.getDepth();
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
   * <p>后代培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>child_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getChildId() {
    return childId;
  }

  /**
   * <p>祖先培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>parent_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getParentId() {
    return parentId;
  }

  /**
   * <p>代际距离；0=自身，1直接父瓶，2祖父...</p>
   * <ul>
   *   <li>COLUMN : <code>depth</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getDepth() {
    return depth;
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
   * <p>后代培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>child_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param childId <p>后代培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>child_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setChildId(String childId) {
    this.childId = childId;
  }

  /**
   * <p>祖先培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>parent_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param parentId <p>祖先培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>parent_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  /**
   * <p>代际距离；0=自身，1直接父瓶，2祖父...</p>
   * <ul>
   *   <li>COLUMN : <code>depth</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param depth <p>代际距离；0=自身，1直接父瓶，2祖父...</p>
   * <ul>
   *   <li>COLUMN : <code>depth</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setDepth(Integer depth) {
    this.depth = depth;
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
  public BottleRelationDTO cloneSimple() {
    try{
      return (BottleRelationDTO)clone();
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
    if(other instanceof BottleRelationDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.BottleRelation o) return fetchId().equals(o.fetchId());
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
