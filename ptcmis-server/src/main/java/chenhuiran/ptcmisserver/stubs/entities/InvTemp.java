package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p></p>
 * <p>数据库表： <code>inv_temp</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>varietyCode</code>: 参见 {@link #getVarietyCode()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface InvTemp extends Serializable {
  /**
   * <p>表名（ <code>inv_temp</code> ）。</p>
   */
  String TB_NAME = "inv_temp";

  /**
   * <p>属性（<code>variety</code>）的 JavaBean 属性名（<code>variety</code>）。</p>
   * <p>字段： <code>variety</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>variety</code>）对应的数据库字段名（<code>variety</code>）。</p>
   * <p>字段： <code>variety</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>varietyCode</code>）的 JavaBean 属性名（<code>varietyCode</code>）。</p>
   * <p>字段： <code>variety_code</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETYCODE = "varietyCode";

  /**
   * <p>属性（<code>varietyCode</code>）对应的数据库字段名（<code>variety_code</code>）。</p>
   * <p>字段： <code>variety_code</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETYCODE = "variety_code";

  /**
   * <p>属性（<code>g0</code>）的 JavaBean 属性名（<code>g0</code>）。</p>
   * <p>字段： <code>g0</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g0</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_G0 = "g0";

  /**
   * <p>属性（<code>g0</code>）对应的数据库字段名（<code>g0</code>）。</p>
   * <p>字段： <code>g0</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g0</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_G0 = "g0";

  /**
   * <p>属性（<code>g1</code>）的 JavaBean 属性名（<code>g1</code>）。</p>
   * <p>字段： <code>g1</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g1</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_G1 = "g1";

  /**
   * <p>属性（<code>g1</code>）对应的数据库字段名（<code>g1</code>）。</p>
   * <p>字段： <code>g1</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g1</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_G1 = "g1";

  /**
   * <p>属性（<code>g2</code>）的 JavaBean 属性名（<code>g2</code>）。</p>
   * <p>字段： <code>g2</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g2</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_G2 = "g2";

  /**
   * <p>属性（<code>g2</code>）对应的数据库字段名（<code>g2</code>）。</p>
   * <p>字段： <code>g2</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g2</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_G2 = "g2";

  /**
   * <p>属性（<code>g3</code>）的 JavaBean 属性名（<code>g3</code>）。</p>
   * <p>字段： <code>g3</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g3</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_G3 = "g3";

  /**
   * <p>属性（<code>g3</code>）对应的数据库字段名（<code>g3</code>）。</p>
   * <p>字段： <code>g3</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g3</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_G3 = "g3";

  /**
   * <p>属性（<code>g4</code>）的 JavaBean 属性名（<code>g4</code>）。</p>
   * <p>字段： <code>g4</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g4</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_G4 = "g4";

  /**
   * <p>属性（<code>g4</code>）对应的数据库字段名（<code>g4</code>）。</p>
   * <p>字段： <code>g4</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g4</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_G4 = "g4";

  /**
   * <p>属性（<code>g5</code>）的 JavaBean 属性名（<code>g5</code>）。</p>
   * <p>字段： <code>g5</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g5</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_G5 = "g5";

  /**
   * <p>属性（<code>g5</code>）对应的数据库字段名（<code>g5</code>）。</p>
   * <p>字段： <code>g5</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g5</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_G5 = "g5";

  /**
   * <p>属性（<code>g6</code>）的 JavaBean 属性名（<code>g6</code>）。</p>
   * <p>字段： <code>g6</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g6</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_G6 = "g6";

  /**
   * <p>属性（<code>g6</code>）对应的数据库字段名（<code>g6</code>）。</p>
   * <p>字段： <code>g6</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g6</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_G6 = "g6";

  /**
   * <p>属性（<code>g7</code>）的 JavaBean 属性名（<code>g7</code>）。</p>
   * <p>字段： <code>g7</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g7</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_G7 = "g7";

  /**
   * <p>属性（<code>g7</code>）对应的数据库字段名（<code>g7</code>）。</p>
   * <p>字段： <code>g7</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g7</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_G7 = "g7";

  /**
   * <p>字段： <code>variety</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getVariety();

  /**
   * <p>字段： <code>variety_code</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getVarietyCode();

  /**
   * <p>字段： <code>g0</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g0</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getG0();

  /**
   * <p>字段： <code>g1</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g1</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getG1();

  /**
   * <p>字段： <code>g2</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g2</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getG2();

  /**
   * <p>字段： <code>g3</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g3</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getG3();

  /**
   * <p>字段： <code>g4</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g4</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getG4();

  /**
   * <p>字段： <code>g5</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g5</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getG5();

  /**
   * <p>字段： <code>g6</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g6</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getG6();

  /**
   * <p>字段： <code>g7</code>。</p>
   * <ul>
   *   <li>COLUMN : <code>g7</code></li>
   *   <li>DDL : <code>INT(10), NULLABLE, DEFAULT NULL</code></li>
   * </ul>
   */
  Integer getG7();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getVarietyCode() == null ? "\u001F" : getVarietyCode();
  }
}
