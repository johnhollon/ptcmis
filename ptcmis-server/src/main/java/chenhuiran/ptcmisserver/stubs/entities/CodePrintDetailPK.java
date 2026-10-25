package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>联合主键 PK 实体类。包含数据表所有主键字段对应的属性。</p>
 * <p>瓶码打印记录明细</p>
 * <p>数据库表： <code>code_print_detail</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>idPrint</code>: 参见 {@link #getIdPrint()} 。</li>
 *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>无参构造方法。</li>
 *   <li>全参构造方法（参数顺序与数据库表主键字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.CodePrintDetail} 构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class CodePrintDetailPK implements Cloneable, Serializable {
  /**
   * <p>表名（ <code>code_print_detail</code> ）。</p>
   * 瓶码打印记录明细
   */
  public static final String TB_NAME = "code_print_detail";

  /**
   * <p>属性（<code>idPrint</code>）对应的数据库字段名（<code>id_print</code>）。</p>
   * <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public static final String TABLE_CL_NAME_IDPRINT = "id_print";

  /**
   * <p>属性（<code>code</code>）对应的数据库字段名（<code>code</code>）。</p>
   * <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public static final String TABLE_CL_NAME_CODE = "code";

  /**
   * <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String idPrint = "";

  /**
   * <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String code = "";

  /**
   * <p>无参构造方法。</p>
   * <p>瓶码打印记录明细</p>
   * <p>数据库表： <code>code_print_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idPrint</code>: 参见 {@link #getIdPrint()} 。</li>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   */
  public CodePrintDetailPK() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>瓶码打印记录明细</p>
   * <p>数据库表： <code>code_print_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idPrint</code>: 参见 {@link #getIdPrint()} 。</li>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   * @param idPrint <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param code <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   */
  public CodePrintDetailPK(String idPrint, String code) {
    this.idPrint = idPrint;
    this.code = code;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>瓶码打印记录明细</p>
   * <p>数据库表： <code>code_print_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idPrint</code>: 参见 {@link #getIdPrint()} 。</li>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public CodePrintDetailPK(CodePrintDetail model) {
    this.idPrint = model.getIdPrint();
    this.code = model.getCode();
  }

  /**
   * <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public String getIdPrint() {
    return idPrint;
  }

  /**
   * <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public String getCode() {
    return code;
  }

  /**
   * <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idPrint <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdPrint(String idPrint) {
    this.idPrint = idPrint;
  }

  /**
   * <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param code <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  public String fetchId() {
    String seperator = "\u001E";
    StringBuilder result = new StringBuilder();
    result.append(seperator).append(getIdPrint() == null ? "\u001F" : getIdPrint());
    result.append(seperator).append(getCode() == null ? "\u001F" : getCode());
    return result.toString();
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
  public CodePrintDetailPK cloneSimple() {
    try{
      return (CodePrintDetailPK)clone();
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
    if(other instanceof CodePrintDetailPK o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.CodePrintDetail o) return fetchId().equals(o.fetchId());
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
