package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.VarietyCode} 接口。</p>
 * <p>品种编号（子品种信息）</p>
 * <p>数据库表： <code>variety_code</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.VarietyCode}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class VarietyCodeDTO implements Cloneable, VarietyCode {
  /**
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String code;

  /**
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String variety;

  /**
   * <p>是否为重点品种，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>important</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  private String important;

  /**
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  private String enabled;

  /**
   * <p>无参构造方法。</p>
   * <p>品种编号（子品种信息）</p>
   * <p>数据库表： <code>variety_code</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   */
  public VarietyCodeDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>品种编号（子品种信息）</p>
   * <p>数据库表： <code>variety_code</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   * @param code <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param variety <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param important <p>是否为重点品种，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>important</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param enabled <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   *
   */
  public VarietyCodeDTO(String code, String variety, String important, String enabled) {
    this.code = code;
    this.variety = variety;
    this.important = important;
    this.enabled = enabled;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>品种编号（子品种信息）</p>
   * <p>数据库表： <code>variety_code</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public VarietyCodeDTO(VarietyCode model) {
    this.code = model.getCode();
    this.variety = model.getVariety();
    this.important = model.getImportant();
    this.enabled = model.getEnabled();
  }

  /**
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getCode() {
    return code;
  }

  /**
   * <p>所属品种。variety.id</p>
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
   * <p>是否为重点品种，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>important</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getImportant() {
    return important;
  }

  /**
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  @Override
  public String getEnabled() {
    return enabled;
  }

  /**
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param code <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param variety <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVariety(String variety) {
    this.variety = variety;
  }

  /**
   * <p>是否为重点品种，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>important</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param important <p>是否为重点品种，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>important</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setImportant(String important) {
    this.important = important;
  }

  /**
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   * @param enabled <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  public void setEnabled(String enabled) {
    this.enabled = enabled;
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
  public VarietyCodeDTO cloneSimple() {
    try{
      return (VarietyCodeDTO)clone();
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
    if(other instanceof VarietyCodeDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.VarietyCode o) return fetchId().equals(o.fetchId());
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
