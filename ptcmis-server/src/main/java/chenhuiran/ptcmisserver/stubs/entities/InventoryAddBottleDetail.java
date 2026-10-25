package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>库存增瓶任务明细</p>
 * <p>数据库表： <code>inventory_add_bottle_detail</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface InventoryAddBottleDetail extends Serializable {
  /**
   * <p>表名（ <code>inventory_add_bottle_detail</code> ）。</p>
   * 库存增瓶任务明细
   */
  String TB_NAME = "inventory_add_bottle_detail";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>idTask</code>）的 JavaBean 属性名（<code>idTask</code>）。</p>
   * <p>所属任务ID。inventory_add_bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDTASK = "idTask";

  /**
   * <p>属性（<code>idTask</code>）对应的数据库字段名（<code>id_task</code>）。</p>
   * <p>所属任务ID。inventory_add_bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDTASK = "id_task";

  /**
   * <p>属性（<code>idParent</code>）的 JavaBean 属性名（<code>idParent</code>）。</p>
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_IDPARENT = "idParent";

  /**
   * <p>属性（<code>idParent</code>）对应的数据库字段名（<code>id_parent</code>）。</p>
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDPARENT = "id_parent";

  /**
   * <p>属性（<code>plantCount</code>）的 JavaBean 属性名（<code>plantCount</code>）。</p>
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTCOUNT = "plantCount";

  /**
   * <p>属性（<code>plantCount</code>）对应的数据库字段名（<code>plant_count</code>）。</p>
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTCOUNT = "plant_count";

  /**
   * <p>属性（<code>inspectorId</code>）的 JavaBean 属性名（<code>inspectorId</code>）。</p>
   * <p>质检员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_INSPECTORID = "inspectorId";

  /**
   * <p>属性（<code>inspectorId</code>）对应的数据库字段名（<code>inspector_id</code>）。</p>
   * <p>质检员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_INSPECTORID = "inspector_id";

  /**
   * <p>属性（<code>inspectorName</code>）的 JavaBean 属性名（<code>inspectorName</code>）。</p>
   * <p>质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_INSPECTORNAME = "inspectorName";

  /**
   * <p>属性（<code>inspectorName</code>）对应的数据库字段名（<code>inspector_name</code>）。</p>
   * <p>质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_INSPECTORNAME = "inspector_name";

  /**
   * <p>属性（<code>operatorId</code>）的 JavaBean 属性名（<code>operatorId</code>）。</p>
   * <p>操作员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORID = "operatorId";

  /**
   * <p>属性（<code>operatorId</code>）对应的数据库字段名（<code>operator_id</code>）。</p>
   * <p>操作员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORID = "operator_id";

  /**
   * <p>属性（<code>operatorName</code>）的 JavaBean 属性名（<code>operatorName</code>）。</p>
   * <p>操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORNAME = "operatorName";

  /**
   * <p>属性（<code>operatorName</code>）对应的数据库字段名（<code>operator_name</code>）。</p>
   * <p>操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORNAME = "operator_name";

  /**
   * <p>属性（<code>operatorTime</code>）的 JavaBean 属性名（<code>operatorTime</code>）。</p>
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORTIME = "operatorTime";

  /**
   * <p>属性（<code>operatorTime</code>）对应的数据库字段名（<code>operator_time</code>）。</p>
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORTIME = "operator_time";

  /**
   * <p>属性（<code>status</code>）的 JavaBean 属性名（<code>status</code>）。</p>
   * <p>赋码状态：dictionary.bottle_code_complement_code_stat</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String BP_NAME_STATUS = "status";

  /**
   * <p>属性（<code>status</code>）对应的数据库字段名（<code>status</code>）。</p>
   * <p>赋码状态：dictionary.bottle_code_complement_code_stat</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_STATUS = "status";

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>所属任务ID。inventory_add_bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdTask();

  /**
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdParent();

  /**
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantCount();

  /**
   * <p>质检员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getInspectorId();

  /**
   * <p>质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getInspectorName();

  /**
   * <p>操作员ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorId();

  /**
   * <p>操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorName();

  /**
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getOperatorTime();

  /**
   * <p>赋码状态：dictionary.bottle_code_complement_code_stat</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String getStatus();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
