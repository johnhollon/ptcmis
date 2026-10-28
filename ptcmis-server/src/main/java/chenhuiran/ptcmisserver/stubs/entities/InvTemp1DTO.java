package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.InvTemp1} 接口。</p>
 * <p></p>
 * <p>数据库表： <code>inv_temp_1</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>varietyCode</code>: 参见 {@link #getVarietyCode()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.InvTemp1}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class InvTemp1DTO implements Cloneable, InvTemp1 {
  /**
   * <p>字段： <code>variety</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String variety;

  /**
   * <p>字段： <code>variety_code</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String varietyCode;

  /**
   * <p>字段： <code>g0</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g0</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer g0;

  /**
   * <p>字段： <code>g1</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g1</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer g1;

  /**
   * <p>字段： <code>g2</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g2</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer g2;

  /**
   * <p>字段： <code>g3</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g3</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer g3;

  /**
   * <p>字段： <code>g4</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g4</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer g4;

  /**
   * <p>字段： <code>g5</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g5</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer g5;

  /**
   * <p>字段： <code>g6</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g6</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer g6;

  /**
   * <p>字段： <code>g7</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g7</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer g7;

  /**
   * <p>无参构造方法。</p>
   * <p></p>
   * <p>数据库表： <code>inv_temp_1</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>varietyCode</code>: 参见 {@link #getVarietyCode()} 。</li>
   * </ul>
   */
  public InvTemp1DTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p></p>
   * <p>数据库表： <code>inv_temp_1</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>varietyCode</code>: 参见 {@link #getVarietyCode()} 。</li>
   * </ul>
   * @param variety <p>字段： <code>variety</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param varietyCode <p>字段： <code>variety_code</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param g0 <p>字段： <code>g0</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g0</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param g1 <p>字段： <code>g1</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g1</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param g2 <p>字段： <code>g2</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g2</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param g3 <p>字段： <code>g3</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g3</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param g4 <p>字段： <code>g4</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g4</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param g5 <p>字段： <code>g5</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g5</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param g6 <p>字段： <code>g6</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g6</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param g7 <p>字段： <code>g7</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g7</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   *
   */
  public InvTemp1DTO(String variety, String varietyCode, Integer g0, Integer g1, Integer g2,
      Integer g3, Integer g4, Integer g5, Integer g6, Integer g7) {
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.g0 = g0;
    this.g1 = g1;
    this.g2 = g2;
    this.g3 = g3;
    this.g4 = g4;
    this.g5 = g5;
    this.g6 = g6;
    this.g7 = g7;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p></p>
   * <p>数据库表： <code>inv_temp_1</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>varietyCode</code>: 参见 {@link #getVarietyCode()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public InvTemp1DTO(InvTemp1 model) {
    this.variety = model.getVariety();
    this.varietyCode = model.getVarietyCode();
    this.g0 = model.getG0();
    this.g1 = model.getG1();
    this.g2 = model.getG2();
    this.g3 = model.getG3();
    this.g4 = model.getG4();
    this.g5 = model.getG5();
    this.g6 = model.getG6();
    this.g7 = model.getG7();
  }

  /**
   * <p>字段： <code>variety</code>。</p>
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
   * <p>字段： <code>variety_code</code>。</p>
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
   * <p>字段： <code>g0</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g0</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getG0() {
    return g0;
  }

  /**
   * <p>字段： <code>g1</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g1</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getG1() {
    return g1;
  }

  /**
   * <p>字段： <code>g2</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g2</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getG2() {
    return g2;
  }

  /**
   * <p>字段： <code>g3</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g3</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getG3() {
    return g3;
  }

  /**
   * <p>字段： <code>g4</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g4</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getG4() {
    return g4;
  }

  /**
   * <p>字段： <code>g5</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g5</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getG5() {
    return g5;
  }

  /**
   * <p>字段： <code>g6</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g6</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getG6() {
    return g6;
  }

  /**
   * <p>字段： <code>g7</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g7</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getG7() {
    return g7;
  }

  /**
   * <p>字段： <code>variety</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param variety <p>字段： <code>variety</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVariety(String variety) {
    this.variety = variety;
  }

  /**
   * <p>字段： <code>variety_code</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param varietyCode <p>字段： <code>variety_code</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVarietyCode(String varietyCode) {
    this.varietyCode = varietyCode;
  }

  /**
   * <p>字段： <code>g0</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g0</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   * @param g0 <p>字段： <code>g0</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g0</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setG0(Integer g0) {
    this.g0 = g0;
  }

  /**
   * <p>字段： <code>g1</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g1</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   * @param g1 <p>字段： <code>g1</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g1</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setG1(Integer g1) {
    this.g1 = g1;
  }

  /**
   * <p>字段： <code>g2</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g2</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   * @param g2 <p>字段： <code>g2</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g2</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setG2(Integer g2) {
    this.g2 = g2;
  }

  /**
   * <p>字段： <code>g3</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g3</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   * @param g3 <p>字段： <code>g3</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g3</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setG3(Integer g3) {
    this.g3 = g3;
  }

  /**
   * <p>字段： <code>g4</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g4</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   * @param g4 <p>字段： <code>g4</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g4</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setG4(Integer g4) {
    this.g4 = g4;
  }

  /**
   * <p>字段： <code>g5</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g5</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   * @param g5 <p>字段： <code>g5</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g5</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setG5(Integer g5) {
    this.g5 = g5;
  }

  /**
   * <p>字段： <code>g6</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g6</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   * @param g6 <p>字段： <code>g6</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g6</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setG6(Integer g6) {
    this.g6 = g6;
  }

  /**
   * <p>字段： <code>g7</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g7</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   * @param g7 <p>字段： <code>g7</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g7</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setG7(Integer g7) {
    this.g7 = g7;
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
  public InvTemp1DTO cloneSimple() {
    try{
      return (InvTemp1DTO)clone();
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
    if(other instanceof InvTemp1DTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.InvTemp1 o) return fetchId().equals(o.fetchId());
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
