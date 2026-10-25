package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>出库/入库单操作日志</p>
 * <p>数据库表： <code>storage_in_out_log</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface StorageInOutLog extends Serializable {
  /**
   * <p>表名（ <code>storage_in_out_log</code> ）。</p>
   * 出库/入库单操作日志
   */
  String TB_NAME = "storage_in_out_log";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>idInOut</code>）的 JavaBean 属性名（<code>idInOut</code>）。</p>
   * <p>出入库ID. storage_in_out.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDINOUT = "idInOut";

  /**
   * <p>属性（<code>idInOut</code>）对应的数据库字段名（<code>id_in_out</code>）。</p>
   * <p>出入库ID. storage_in_out.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDINOUT = "id_in_out";

  /**
   * <p>属性（<code>status</code>）的 JavaBean 属性名（<code>status</code>）。</p>
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  String BP_NAME_STATUS = "status";

  /**
   * <p>属性（<code>status</code>）对应的数据库字段名（<code>status</code>）。</p>
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_STATUS = "status";

  /**
   * <p>属性（<code>oppReason</code>）的 JavaBean 属性名（<code>oppReason</code>）。</p>
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_OPPREASON = "oppReason";

  /**
   * <p>属性（<code>oppReason</code>）对应的数据库字段名（<code>opp_reason</code>）。</p>
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPPREASON = "opp_reason";

  /**
   * <p>属性（<code>operatorId</code>）的 JavaBean 属性名（<code>operatorId</code>）。</p>
   * <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORID = "operatorId";

  /**
   * <p>属性（<code>operatorId</code>）对应的数据库字段名（<code>operator_id</code>）。</p>
   * <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORID = "operator_id";

  /**
   * <p>属性（<code>operatorName</code>）的 JavaBean 属性名（<code>operatorName</code>）。</p>
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORNAME = "operatorName";

  /**
   * <p>属性（<code>operatorName</code>）对应的数据库字段名（<code>operator_name</code>）。</p>
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORNAME = "operator_name";

  /**
   * <p>属性（<code>operatorTime</code>）的 JavaBean 属性名（<code>operatorTime</code>）。</p>
   * <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORTIME = "operatorTime";

  /**
   * <p>属性（<code>operatorTime</code>）对应的数据库字段名（<code>operator_time</code>）。</p>
   * <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORTIME = "operator_time";

  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>出入库ID. storage_in_out.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdInOut();

  /**
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  String getStatus();

  /**
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getOppReason();

  /**
   * <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorId();

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorName();

  /**
   * <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getOperatorTime();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
