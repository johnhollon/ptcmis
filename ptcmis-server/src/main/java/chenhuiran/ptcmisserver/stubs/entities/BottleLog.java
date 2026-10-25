package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>组培瓶操作日志</p>
 * <p>数据库表： <code>bottle_log</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface BottleLog extends Serializable {
  /**
   * <p>表名（ <code>bottle_log</code> ）。</p>
   * 组培瓶操作日志
   */
  String TB_NAME = "bottle_log";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>idBottle</code>）的 JavaBean 属性名（<code>idBottle</code>）。</p>
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDBOTTLE = "idBottle";

  /**
   * <p>属性（<code>idBottle</code>）对应的数据库字段名（<code>id_bottle</code>）。</p>
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDBOTTLE = "id_bottle";

  /**
   * <p>属性（<code>oppTime</code>）的 JavaBean 属性名（<code>oppTime</code>）。</p>
   * <p>执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>opp_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_OPPTIME = "oppTime";

  /**
   * <p>属性（<code>oppTime</code>）对应的数据库字段名（<code>opp_time</code>）。</p>
   * <p>执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>opp_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPPTIME = "opp_time";

  /**
   * <p>属性（<code>tableName</code>）的 JavaBean 属性名（<code>tableName</code>）。</p>
   * <p>数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_TABLENAME = "tableName";

  /**
   * <p>属性（<code>tableName</code>）对应的数据库字段名（<code>table_name</code>）。</p>
   * <p>数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_TABLENAME = "table_name";

  /**
   * <p>属性（<code>recordId</code>）的 JavaBean 属性名（<code>recordId</code>）。</p>
   * <p>数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_RECORDID = "recordId";

  /**
   * <p>属性（<code>recordId</code>）对应的数据库字段名（<code>record_id</code>）。</p>
   * <p>数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_RECORDID = "record_id";

  /**
   * <p>属性（<code>bizType</code>）的 JavaBean 属性名（<code>bizType</code>）。</p>
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(64), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_BIZTYPE = "bizType";

  /**
   * <p>属性（<code>bizType</code>）对应的数据库字段名（<code>biz_type</code>）。</p>
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(64), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BIZTYPE = "biz_type";

  /**
   * <p>属性（<code>description</code>）的 JavaBean 属性名（<code>description</code>）。</p>
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_DESCRIPTION = "description";

  /**
   * <p>属性（<code>description</code>）对应的数据库字段名（<code>description</code>）。</p>
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DESCRIPTION = "description";

  /**
   * <p>属性（<code>inspectorId</code>）的 JavaBean 属性名（<code>inspectorId</code>）。</p>
   * <p>质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_INSPECTORID = "inspectorId";

  /**
   * <p>属性（<code>inspectorId</code>）对应的数据库字段名（<code>inspector_id</code>）。</p>
   * <p>质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_INSPECTORID = "inspector_id";

  /**
   * <p>属性（<code>inspectorName</code>）的 JavaBean 属性名（<code>inspectorName</code>）。</p>
   * <p>质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_INSPECTORNAME = "inspectorName";

  /**
   * <p>属性（<code>inspectorName</code>）对应的数据库字段名（<code>inspector_name</code>）。</p>
   * <p>质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_INSPECTORNAME = "inspector_name";

  /**
   * <p>属性（<code>operatorId</code>）的 JavaBean 属性名（<code>operatorId</code>）。</p>
   * <p>操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORID = "operatorId";

  /**
   * <p>属性（<code>operatorId</code>）对应的数据库字段名（<code>operator_id</code>）。</p>
   * <p>操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORID = "operator_id";

  /**
   * <p>属性（<code>operatorName</code>）的 JavaBean 属性名（<code>operatorName</code>）。</p>
   * <p>操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORNAME = "operatorName";

  /**
   * <p>属性（<code>operatorName</code>）对应的数据库字段名（<code>operator_name</code>）。</p>
   * <p>操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORNAME = "operator_name";

  /**
   * <p>属性（<code>operateStatus</code>）的 JavaBean 属性名（<code>operateStatus</code>）。</p>
   * <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATESTATUS = "operateStatus";

  /**
   * <p>属性（<code>operateStatus</code>）对应的数据库字段名（<code>operate_status</code>）。</p>
   * <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATESTATUS = "operate_status";

  /**
   * <p>属性（<code>storageStatus</code>）的 JavaBean 属性名（<code>storageStatus</code>）。</p>
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_STORAGESTATUS = "storageStatus";

  /**
   * <p>属性（<code>storageStatus</code>）对应的数据库字段名（<code>storage_status</code>）。</p>
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_STORAGESTATUS = "storage_status";

  /**
   * <p>属性（<code>storageReason</code>）的 JavaBean 属性名（<code>storageReason</code>）。</p>
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>storage_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_STORAGEREASON = "storageReason";

  /**
   * <p>属性（<code>storageReason</code>）对应的数据库字段名（<code>storage_reason</code>）。</p>
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>storage_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_STORAGEREASON = "storage_reason";

  /**
   * <p>属性（<code>discarded</code>）的 JavaBean 属性名（<code>discarded</code>）。</p>
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_DISCARDED = "discarded";

  /**
   * <p>属性（<code>discarded</code>）对应的数据库字段名（<code>discarded</code>）。</p>
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DISCARDED = "discarded";

  /**
   * <p>属性（<code>discardReason</code>）的 JavaBean 属性名（<code>discardReason</code>）。</p>
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_DISCARDREASON = "discardReason";

  /**
   * <p>属性（<code>discardReason</code>）对应的数据库字段名（<code>discard_reason</code>）。</p>
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DISCARDREASON = "discard_reason";

  /**
   * <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdBottle();

  /**
   * <p>执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>opp_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getOppTime();

  /**
   * <p>数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getTableName();

  /**
   * <p>数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getRecordId();

  /**
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(64), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getBizType();

  /**
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getDescription();

  /**
   * <p>质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getInspectorId();

  /**
   * <p>质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getInspectorName();

  /**
   * <p>操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorId();

  /**
   * <p>操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorName();

  /**
   * <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperateStatus();

  /**
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getStorageStatus();

  /**
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>storage_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getStorageReason();

  /**
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getDiscarded();

  /**
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getDiscardReason();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
