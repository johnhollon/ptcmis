package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>数据字典项目</p>
 * <p>数据库表： <code>dictionary_item</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>idDictionary</code>: 参见 {@link #getIdDictionary()} 。</li>
 *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface DictionaryItem extends Serializable {
  /**
   * <p>表名（ <code>dictionary_item</code> ）。</p>
   * 数据字典项目
   */
  String TB_NAME = "dictionary_item";

  /**
   * <p>属性（<code>idDictionary</code>）的 JavaBean 属性名（<code>idDictionary</code>）。</p>
   * <p>所属数据字典类型ID，dictionary.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_dictionary</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDDICTIONARY = "idDictionary";

  /**
   * <p>属性（<code>idDictionary</code>）对应的数据库字段名（<code>id_dictionary</code>）。</p>
   * <p>所属数据字典类型ID，dictionary.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_dictionary</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDDICTIONARY = "id_dictionary";

  /**
   * <p>属性（<code>code</code>）的 JavaBean 属性名（<code>code</code>）。</p>
   * <p>数据字典项目编码。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_CODE = "code";

  /**
   * <p>属性（<code>code</code>）对应的数据库字段名（<code>code</code>）。</p>
   * <p>数据字典项目编码。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CODE = "code";

  /**
   * <p>属性（<code>name</code>）的 JavaBean 属性名（<code>name</code>）。</p>
   * <p>数据字典项目名称</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_NAME = "name";

  /**
   * <p>属性（<code>name</code>）对应的数据库字段名（<code>name</code>）。</p>
   * <p>数据字典项目名称</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_NAME = "name";

  /**
   * <p>所属数据字典类型ID，dictionary.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_dictionary</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdDictionary();

  /**
   * <p>数据字典项目编码。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getCode();

  /**
   * <p>数据字典项目名称</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getName();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    String seperator = "\u001E";
    StringBuilder result = new StringBuilder();
    result.append(seperator).append(getIdDictionary() == null ? "\u001F" : getIdDictionary());
    result.append(seperator).append(getCode() == null ? "\u001F" : getCode());
    return result.toString();
  }
}
