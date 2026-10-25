package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
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
 */
public interface BottleCodeSequence extends Serializable {
  /**
   * <p>表名（ <code>bottle_code_sequence</code> ）。</p>
   * 瓶码序列计数器表
   */
  String TB_NAME = "bottle_code_sequence";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>主键，固定为 0</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>TINYINT UNSIGNED(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>主键，固定为 0</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>TINYINT UNSIGNED(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>currentValue</code>）的 JavaBean 属性名（<code>currentValue</code>）。</p>
   * <p>下一个应用的编码数值</p>
   * <ul>
   *   <li>COLUMN : <code>current_value</code></li>
   *   <li>DDL : <code>BIGINT UNSIGNED(20), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTVALUE = "currentValue";

  /**
   * <p>属性（<code>currentValue</code>）对应的数据库字段名（<code>current_value</code>）。</p>
   * <p>下一个应用的编码数值</p>
   * <ul>
   *   <li>COLUMN : <code>current_value</code></li>
   *   <li>DDL : <code>BIGINT UNSIGNED(20), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTVALUE = "current_value";

  /**
   * <p>主键，固定为 0</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>TINYINT UNSIGNED(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getId();

  /**
   * <p>下一个应用的编码数值</p>
   * <ul>
   *   <li>COLUMN : <code>current_value</code></li>
   *   <li>DDL : <code>BIGINT UNSIGNED(20), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getCurrentValue();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return (getId() == null || getId().toString() == null) ? "\u001F" : getId().toString();
  }
}
