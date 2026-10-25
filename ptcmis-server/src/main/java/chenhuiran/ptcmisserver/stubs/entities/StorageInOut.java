package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>出库/入库单</p>
 * <p>数据库表： <code>storage_in_out</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface StorageInOut extends Serializable {
  /**
   * <p>表名（ <code>storage_in_out</code> ）。</p>
   * 出库/入库单
   */
  String TB_NAME = "storage_in_out";

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
   * <p>属性（<code>inOut</code>）的 JavaBean 属性名（<code>inOut</code>）。</p>
   * <p>出入库类别，标记出库还是入库。 dictionary = storage_in_out_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_INOUT = "inOut";

  /**
   * <p>属性（<code>inOut</code>）对应的数据库字段名（<code>in_out</code>）。</p>
   * <p>出入库类别，标记出库还是入库。 dictionary = storage_in_out_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_INOUT = "in_out";

  /**
   * <p>属性（<code>bottlesApplied</code>）的 JavaBean 属性名（<code>bottlesApplied</code>）。</p>
   * <p>申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLESAPPLIED = "bottlesApplied";

  /**
   * <p>属性（<code>bottlesApplied</code>）对应的数据库字段名（<code>bottles_applied</code>）。</p>
   * <p>申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLESAPPLIED = "bottles_applied";

  /**
   * <p>属性（<code>plantsApplied</code>）的 JavaBean 属性名（<code>plantsApplied</code>）。</p>
   * <p>申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTSAPPLIED = "plantsApplied";

  /**
   * <p>属性（<code>plantsApplied</code>）对应的数据库字段名（<code>plants_applied</code>）。</p>
   * <p>申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTSAPPLIED = "plants_applied";

  /**
   * <p>属性（<code>bottlesKeepInApplied</code>）的 JavaBean 属性名（<code>bottlesKeepInApplied</code>）。</p>
   * <p>申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLESKEEPINAPPLIED = "bottlesKeepInApplied";

  /**
   * <p>属性（<code>bottlesKeepInApplied</code>）对应的数据库字段名（<code>bottles_keep_in_applied</code>）。</p>
   * <p>申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLESKEEPINAPPLIED = "bottles_keep_in_applied";

  /**
   * <p>属性（<code>plantsKeepInApplied</code>）的 JavaBean 属性名（<code>plantsKeepInApplied</code>）。</p>
   * <p>申请入库株数中母代留种株数（是 plants_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTSKEEPINAPPLIED = "plantsKeepInApplied";

  /**
   * <p>属性（<code>plantsKeepInApplied</code>）对应的数据库字段名（<code>plants_keep_in_applied</code>）。</p>
   * <p>申请入库株数中母代留种株数（是 plants_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTSKEEPINAPPLIED = "plants_keep_in_applied";

  /**
   * <p>属性（<code>bottlesActual</code>）的 JavaBean 属性名（<code>bottlesActual</code>）。</p>
   * <p>实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLESACTUAL = "bottlesActual";

  /**
   * <p>属性（<code>bottlesActual</code>）对应的数据库字段名（<code>bottles_actual</code>）。</p>
   * <p>实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLESACTUAL = "bottles_actual";

  /**
   * <p>属性（<code>plantsActual</code>）的 JavaBean 属性名（<code>plantsActual</code>）。</p>
   * <p>实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTSACTUAL = "plantsActual";

  /**
   * <p>属性（<code>plantsActual</code>）对应的数据库字段名（<code>plants_actual</code>）。</p>
   * <p>实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTSACTUAL = "plants_actual";

  /**
   * <p>属性（<code>bottlesKeepInActual</code>）的 JavaBean 属性名（<code>bottlesKeepInActual</code>）。</p>
   * <p>实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLESKEEPINACTUAL = "bottlesKeepInActual";

  /**
   * <p>属性（<code>bottlesKeepInActual</code>）对应的数据库字段名（<code>bottles_keep_in_actual</code>）。</p>
   * <p>实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLESKEEPINACTUAL = "bottles_keep_in_actual";

  /**
   * <p>属性（<code>plantsKeepInActual</code>）的 JavaBean 属性名（<code>plantsKeepInActual</code>）。</p>
   * <p>实际入库株数中母代留种株数（是 plants_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTSKEEPINACTUAL = "plantsKeepInActual";

  /**
   * <p>属性（<code>plantsKeepInActual</code>）对应的数据库字段名（<code>plants_keep_in_actual</code>）。</p>
   * <p>实际入库株数中母代留种株数（是 plants_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTSKEEPINACTUAL = "plants_keep_in_actual";

  /**
   * <p>属性（<code>bottlesDiscard</code>）的 JavaBean 属性名（<code>bottlesDiscard</code>）。</p>
   * <p>淘汰瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLESDISCARD = "bottlesDiscard";

  /**
   * <p>属性（<code>bottlesDiscard</code>）对应的数据库字段名（<code>bottles_discard</code>）。</p>
   * <p>淘汰瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLESDISCARD = "bottles_discard";

  /**
   * <p>属性（<code>plantsDiscard</code>）的 JavaBean 属性名（<code>plantsDiscard</code>）。</p>
   * <p>淘汰株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTSDISCARD = "plantsDiscard";

  /**
   * <p>属性（<code>plantsDiscard</code>）对应的数据库字段名（<code>plants_discard</code>）。</p>
   * <p>淘汰株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTSDISCARD = "plants_discard";

  /**
   * <p>属性（<code>bottlesVoided</code>）的 JavaBean 属性名（<code>bottlesVoided</code>）。</p>
   * <p>废弃瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLESVOIDED = "bottlesVoided";

  /**
   * <p>属性（<code>bottlesVoided</code>）对应的数据库字段名（<code>bottles_voided</code>）。</p>
   * <p>废弃瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLESVOIDED = "bottles_voided";

  /**
   * <p>属性（<code>plantsVoided</code>）的 JavaBean 属性名（<code>plantsVoided</code>）。</p>
   * <p>废弃株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTSVOIDED = "plantsVoided";

  /**
   * <p>属性（<code>plantsVoided</code>）对应的数据库字段名（<code>plants_voided</code>）。</p>
   * <p>废弃株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTSVOIDED = "plants_voided";

  /**
   * <p>属性（<code>ignoreStats</code>）的 JavaBean 属性名（<code>ignoreStats</code>）。</p>
   * <p>是否在统计中忽略该入库单。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_IGNORESTATS = "ignoreStats";

  /**
   * <p>属性（<code>ignoreStats</code>）对应的数据库字段名（<code>ignore_stats</code>）。</p>
   * <p>是否在统计中忽略该入库单。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IGNORESTATS = "ignore_stats";

  /**
   * <p>属性（<code>reason</code>）的 JavaBean 属性名（<code>reason</code>）。</p>
   * <p>出入库原因。 dictionary = storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_REASON = "reason";

  /**
   * <p>属性（<code>reason</code>）对应的数据库字段名（<code>reason</code>）。</p>
   * <p>出入库原因。 dictionary = storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_REASON = "reason";

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
   * <p>属性（<code>idTask</code>）的 JavaBean 属性名（<code>idTask</code>）。</p>
   * <p>产生出入库业务的组培任务ID。tissue_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_IDTASK = "idTask";

  /**
   * <p>属性（<code>idTask</code>）对应的数据库字段名（<code>id_task</code>）。</p>
   * <p>产生出入库业务的组培任务ID。tissue_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDTASK = "id_task";

  /**
   * <p>属性（<code>idInspection</code>）的 JavaBean 属性名（<code>idInspection</code>）。</p>
   * <p>产生出入库业务的质检任务ID。inspection_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_inspection</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_IDINSPECTION = "idInspection";

  /**
   * <p>属性（<code>idInspection</code>）对应的数据库字段名（<code>id_inspection</code>）。</p>
   * <p>产生出入库业务的质检任务ID。inspection_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_inspection</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDINSPECTION = "id_inspection";

  /**
   * <p>属性（<code>applicantId</code>）的 JavaBean 属性名（<code>applicantId</code>）。</p>
   * <p>申请人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_APPLICANTID = "applicantId";

  /**
   * <p>属性（<code>applicantId</code>）对应的数据库字段名（<code>applicant_id</code>）。</p>
   * <p>申请人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_APPLICANTID = "applicant_id";

  /**
   * <p>属性（<code>applicantName</code>）的 JavaBean 属性名（<code>applicantName</code>）。</p>
   * <p>申请人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_APPLICANTNAME = "applicantName";

  /**
   * <p>属性（<code>applicantName</code>）对应的数据库字段名（<code>applicant_name</code>）。</p>
   * <p>申请人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_APPLICANTNAME = "applicant_name";

  /**
   * <p>属性（<code>applicantTime</code>）的 JavaBean 属性名（<code>applicantTime</code>）。</p>
   * <p>申请时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_APPLICANTTIME = "applicantTime";

  /**
   * <p>属性（<code>applicantTime</code>）对应的数据库字段名（<code>applicant_time</code>）。</p>
   * <p>申请时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_APPLICANTTIME = "applicant_time";

  /**
   * <p>属性（<code>operatorId</code>）的 JavaBean 属性名（<code>operatorId</code>）。</p>
   * <p>末次操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORID = "operatorId";

  /**
   * <p>属性（<code>operatorId</code>）对应的数据库字段名（<code>operator_id</code>）。</p>
   * <p>末次操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORID = "operator_id";

  /**
   * <p>属性（<code>operatorName</code>）的 JavaBean 属性名（<code>operatorName</code>）。</p>
   * <p>末次操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORNAME = "operatorName";

  /**
   * <p>属性（<code>operatorName</code>）对应的数据库字段名（<code>operator_name</code>）。</p>
   * <p>末次操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORNAME = "operator_name";

  /**
   * <p>属性（<code>operatorTime</code>）的 JavaBean 属性名（<code>operatorTime</code>）。</p>
   * <p>末次操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORTIME = "operatorTime";

  /**
   * <p>属性（<code>operatorTime</code>）对应的数据库字段名（<code>operator_time</code>）。</p>
   * <p>末次操作时间，UTC 毫秒</p>
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
   * <p>出入库类别，标记出库还是入库。 dictionary = storage_in_out_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getInOut();

  /**
   * <p>申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getBottlesApplied();

  /**
   * <p>申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantsApplied();

  /**
   * <p>申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getBottlesKeepInApplied();

  /**
   * <p>申请入库株数中母代留种株数（是 plants_applied 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_applied</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantsKeepInApplied();

  /**
   * <p>实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getBottlesActual();

  /**
   * <p>实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantsActual();

  /**
   * <p>实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getBottlesKeepInActual();

  /**
   * <p>实际入库株数中母代留种株数（是 plants_actual 的其中数）</p>
   * <ul>
   *   <li>COLUMN : <code>plants_keep_in_actual</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantsKeepInActual();

  /**
   * <p>淘汰瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getBottlesDiscard();

  /**
   * <p>淘汰株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_discard</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantsDiscard();

  /**
   * <p>废弃瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getBottlesVoided();

  /**
   * <p>废弃株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants_voided</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantsVoided();

  /**
   * <p>是否在统计中忽略该入库单。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getIgnoreStats();

  /**
   * <p>出入库原因。 dictionary = storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getReason();

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
   * <p>产生出入库业务的组培任务ID。tissue_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdTask();

  /**
   * <p>产生出入库业务的质检任务ID。inspection_task.id。</p>
   * <ul>
   *   <li>COLUMN : <code>id_inspection</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdInspection();

  /**
   * <p>申请人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getApplicantId();

  /**
   * <p>申请人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getApplicantName();

  /**
   * <p>申请时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>applicant_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  Long getApplicantTime();

  /**
   * <p>末次操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorId();

  /**
   * <p>末次操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorName();

  /**
   * <p>末次操作时间，UTC 毫秒</p>
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
