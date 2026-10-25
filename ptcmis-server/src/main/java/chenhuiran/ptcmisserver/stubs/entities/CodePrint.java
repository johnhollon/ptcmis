package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>瓶码打印记录</p>
 * <p>数据库表： <code>code_print</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface CodePrint extends Serializable {
  /**
   * <p>表名（ <code>code_print</code> ）。</p>
   * 瓶码打印记录
   */
  String TB_NAME = "code_print";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>tableName</code>）的 JavaBean 属性名（<code>tableName</code>）。</p>
   * <p>来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_TABLENAME = "tableName";

  /**
   * <p>属性（<code>tableName</code>）对应的数据库字段名（<code>table_name</code>）。</p>
   * <p>来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_TABLENAME = "table_name";

  /**
   * <p>属性（<code>recordId</code>）的 JavaBean 属性名（<code>recordId</code>）。</p>
   * <p>来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_RECORDID = "recordId";

  /**
   * <p>属性（<code>recordId</code>）对应的数据库字段名（<code>record_id</code>）。</p>
   * <p>来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_RECORDID = "record_id";

  /**
   * <p>属性（<code>description</code>）的 JavaBean 属性名（<code>description</code>）。</p>
   * <p>备注信息</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_DESCRIPTION = "description";

  /**
   * <p>属性（<code>description</code>）对应的数据库字段名（<code>description</code>）。</p>
   * <p>备注信息</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DESCRIPTION = "description";

  /**
   * <p>属性（<code>operatorId</code>）的 JavaBean 属性名（<code>operatorId</code>）。</p>
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORID = "operatorId";

  /**
   * <p>属性（<code>operatorId</code>）对应的数据库字段名（<code>operator_id</code>）。</p>
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORID = "operator_id";

  /**
   * <p>属性（<code>operatorName</code>）的 JavaBean 属性名（<code>operatorName</code>）。</p>
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORNAME = "operatorName";

  /**
   * <p>属性（<code>operatorName</code>）对应的数据库字段名（<code>operator_name</code>）。</p>
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORNAME = "operator_name";

  /**
   * <p>属性（<code>operateTime</code>）的 JavaBean 属性名（<code>operateTime</code>）。</p>
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_OPERATETIME = "operateTime";

  /**
   * <p>属性（<code>operateTime</code>）对应的数据库字段名（<code>operate_time</code>）。</p>
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATETIME = "operate_time";

  /**
   * <p>主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getTableName();

  /**
   * <p>来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getRecordId();

  /**
   * <p>备注信息</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getDescription();

  /**
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getOperatorId();

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getOperatorName();

  /**
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  Long getOperateTime();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
