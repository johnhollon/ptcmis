package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
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
 */
public interface VarietyCode extends Serializable {
  /**
   * <p>表名（ <code>variety_code</code> ）。</p>
   * 品种编号（子品种信息）
   */
  String TB_NAME = "variety_code";

  /**
   * <p>属性（<code>code</code>）的 JavaBean 属性名（<code>code</code>）。</p>
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_CODE = "code";

  /**
   * <p>属性（<code>code</code>）对应的数据库字段名（<code>code</code>）。</p>
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CODE = "code";

  /**
   * <p>属性（<code>variety</code>）的 JavaBean 属性名（<code>variety</code>）。</p>
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>variety</code>）对应的数据库字段名（<code>variety</code>）。</p>
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>important</code>）的 JavaBean 属性名（<code>important</code>）。</p>
   * <p>是否为重点品种，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>important</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_IMPORTANT = "important";

  /**
   * <p>属性（<code>important</code>）对应的数据库字段名（<code>important</code>）。</p>
   * <p>是否为重点品种，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>important</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IMPORTANT = "important";

  /**
   * <p>属性（<code>enabled</code>）的 JavaBean 属性名（<code>enabled</code>）。</p>
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String BP_NAME_ENABLED = "enabled";

  /**
   * <p>属性（<code>enabled</code>）对应的数据库字段名（<code>enabled</code>）。</p>
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ENABLED = "enabled";

  /**
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getCode();

  /**
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getVariety();

  /**
   * <p>是否为重点品种，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>important</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getImportant();

  /**
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String getEnabled();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getCode() == null ? "\u001F" : getCode();
  }
}
