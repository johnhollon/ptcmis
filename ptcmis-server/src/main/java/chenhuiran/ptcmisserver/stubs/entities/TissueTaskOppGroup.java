package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>组培任务‑操作分配组</p>
 * <p>数据库表： <code>tissue_task_opp_group</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface TissueTaskOppGroup extends Serializable {
  /**
   * <p>表名（ <code>tissue_task_opp_group</code> ）。</p>
   * 组培任务‑操作分配组
   */
  String TB_NAME = "tissue_task_opp_group";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>操作分配组ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>操作分配组ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>idTask</code>）的 JavaBean 属性名（<code>idTask</code>）。</p>
   * <p>所属任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDTASK = "idTask";

  /**
   * <p>属性（<code>idTask</code>）对应的数据库字段名（<code>id_task</code>）。</p>
   * <p>所属任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDTASK = "id_task";

  /**
   * <p>属性（<code>varietyCode</code>）的 JavaBean 属性名（<code>varietyCode</code>）。</p>
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_VARIETYCODE = "varietyCode";

  /**
   * <p>属性（<code>varietyCode</code>）对应的数据库字段名（<code>variety_code</code>）。</p>
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETYCODE = "variety_code";

  /**
   * <p>属性（<code>parentPlantCount</code>）的 JavaBean 属性名（<code>parentPlantCount</code>）。</p>
   * <p>母瓶株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PARENTPLANTCOUNT = "parentPlantCount";

  /**
   * <p>属性（<code>parentPlantCount</code>）对应的数据库字段名（<code>parent_plant_count</code>）。</p>
   * <p>母瓶株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PARENTPLANTCOUNT = "parent_plant_count";

  /**
   * <p>属性（<code>idParentBottle</code>）的 JavaBean 属性名（<code>idParentBottle</code>）。</p>
   * <p>母瓶瓶码（ID） bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_IDPARENTBOTTLE = "idParentBottle";

  /**
   * <p>属性（<code>idParentBottle</code>）对应的数据库字段名（<code>id_parent_bottle</code>）。</p>
   * <p>母瓶瓶码（ID） bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDPARENTBOTTLE = "id_parent_bottle";

  /**
   * <p>属性（<code>parentKeep</code>）的 JavaBean 属性名（<code>parentKeep</code>）。</p>
   * <p>母瓶是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_PARENTKEEP = "parentKeep";

  /**
   * <p>属性（<code>parentKeep</code>）对应的数据库字段名（<code>parent_keep</code>）。</p>
   * <p>母瓶是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PARENTKEEP = "parent_keep";

  /**
   * <p>属性（<code>parentKeepPlantCount</code>）的 JavaBean 属性名（<code>parentKeepPlantCount</code>）。</p>
   * <p>母瓶留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PARENTKEEPPLANTCOUNT = "parentKeepPlantCount";

  /**
   * <p>属性（<code>parentKeepPlantCount</code>）对应的数据库字段名（<code>parent_keep_plant_count</code>）。</p>
   * <p>母瓶留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PARENTKEEPPLANTCOUNT = "parent_keep_plant_count";

  /**
   * <p>属性（<code>allocatorId</code>）的 JavaBean 属性名（<code>allocatorId</code>）。</p>
   * <p>分配人ID（员工ID），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ALLOCATORID = "allocatorId";

  /**
   * <p>属性（<code>allocatorId</code>）对应的数据库字段名（<code>allocator_id</code>）。</p>
   * <p>分配人ID（员工ID），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ALLOCATORID = "allocator_id";

  /**
   * <p>属性（<code>allocatorName</code>）的 JavaBean 属性名（<code>allocatorName</code>）。</p>
   * <p>分配人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ALLOCATORNAME = "allocatorName";

  /**
   * <p>属性（<code>allocatorName</code>）对应的数据库字段名（<code>allocator_name</code>）。</p>
   * <p>分配人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ALLOCATORNAME = "allocator_name";

  /**
   * <p>属性（<code>allocateTime</code>）的 JavaBean 属性名（<code>allocateTime</code>）。</p>
   * <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_ALLOCATETIME = "allocateTime";

  /**
   * <p>属性（<code>allocateTime</code>）对应的数据库字段名（<code>allocate_time</code>）。</p>
   * <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ALLOCATETIME = "allocate_time";

  /**
   * <p>属性（<code>finishTime</code>）的 JavaBean 属性名（<code>finishTime</code>）。</p>
   * <p>完成时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_FINISHTIME = "finishTime";

  /**
   * <p>属性（<code>finishTime</code>）对应的数据库字段名（<code>finish_time</code>）。</p>
   * <p>完成时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_FINISHTIME = "finish_time";

  /**
   * <p>属性（<code>assigneeInspectorId</code>）的 JavaBean 属性名（<code>assigneeInspectorId</code>）。</p>
   * <p>被分配质检员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ASSIGNEEINSPECTORID = "assigneeInspectorId";

  /**
   * <p>属性（<code>assigneeInspectorId</code>）对应的数据库字段名（<code>assignee_inspector_id</code>）。</p>
   * <p>被分配质检员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ASSIGNEEINSPECTORID = "assignee_inspector_id";

  /**
   * <p>属性（<code>assigneeInspectorName</code>）的 JavaBean 属性名（<code>assigneeInspectorName</code>）。</p>
   * <p>被分配质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ASSIGNEEINSPECTORNAME = "assigneeInspectorName";

  /**
   * <p>属性（<code>assigneeInspectorName</code>）对应的数据库字段名（<code>assignee_inspector_name</code>）。</p>
   * <p>被分配质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ASSIGNEEINSPECTORNAME = "assignee_inspector_name";

  /**
   * <p>属性（<code>assigneeOperatorId</code>）的 JavaBean 属性名（<code>assigneeOperatorId</code>）。</p>
   * <p>被分配操作员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ASSIGNEEOPERATORID = "assigneeOperatorId";

  /**
   * <p>属性（<code>assigneeOperatorId</code>）对应的数据库字段名（<code>assignee_operator_id</code>）。</p>
   * <p>被分配操作员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ASSIGNEEOPERATORID = "assignee_operator_id";

  /**
   * <p>属性（<code>assigneeOperatorName</code>）的 JavaBean 属性名（<code>assigneeOperatorName</code>）。</p>
   * <p>被分配操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ASSIGNEEOPERATORNAME = "assigneeOperatorName";

  /**
   * <p>属性（<code>assigneeOperatorName</code>）对应的数据库字段名（<code>assignee_operator_name</code>）。</p>
   * <p>被分配操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ASSIGNEEOPERATORNAME = "assignee_operator_name";

  /**
   * <p>属性（<code>inspectorId</code>）的 JavaBean 属性名（<code>inspectorId</code>）。</p>
   * <p>实际质检员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_INSPECTORID = "inspectorId";

  /**
   * <p>属性（<code>inspectorId</code>）对应的数据库字段名（<code>inspector_id</code>）。</p>
   * <p>实际质检员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_INSPECTORID = "inspector_id";

  /**
   * <p>属性（<code>inspectorName</code>）的 JavaBean 属性名（<code>inspectorName</code>）。</p>
   * <p>实际质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_INSPECTORNAME = "inspectorName";

  /**
   * <p>属性（<code>inspectorName</code>）对应的数据库字段名（<code>inspector_name</code>）。</p>
   * <p>实际质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_INSPECTORNAME = "inspector_name";

  /**
   * <p>属性（<code>operatorId</code>）的 JavaBean 属性名（<code>operatorId</code>）。</p>
   * <p>实际操作员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORID = "operatorId";

  /**
   * <p>属性（<code>operatorId</code>）对应的数据库字段名（<code>operator_id</code>）。</p>
   * <p>实际操作员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORID = "operator_id";

  /**
   * <p>属性（<code>operatorName</code>）的 JavaBean 属性名（<code>operatorName</code>）。</p>
   * <p>实际操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORNAME = "operatorName";

  /**
   * <p>属性（<code>operatorName</code>）对应的数据库字段名（<code>operator_name</code>）。</p>
   * <p>实际操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORNAME = "operator_name";

  /**
   * <p>操作分配组ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>所属任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdTask();

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getVarietyCode();

  /**
   * <p>母瓶株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getParentPlantCount();

  /**
   * <p>母瓶瓶码（ID） bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdParentBottle();

  /**
   * <p>母瓶是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getParentKeep();

  /**
   * <p>母瓶留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getParentKeepPlantCount();

  /**
   * <p>分配人ID（员工ID），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getAllocatorId();

  /**
   * <p>分配人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>allocator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getAllocatorName();

  /**
   * <p>分配时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>allocate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getAllocateTime();

  /**
   * <p>完成时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getFinishTime();

  /**
   * <p>被分配质检员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getAssigneeInspectorId();

  /**
   * <p>被分配质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getAssigneeInspectorName();

  /**
   * <p>被分配操作员ID（计划指派），user.id</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getAssigneeOperatorId();

  /**
   * <p>被分配操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>assignee_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getAssigneeOperatorName();

  /**
   * <p>实际质检员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getInspectorId();

  /**
   * <p>实际质检员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getInspectorName();

  /**
   * <p>实际操作员ID，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorId();

  /**
   * <p>实际操作员姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorName();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
