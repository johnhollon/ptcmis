package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>组培瓶</p>
 * <p>数据库表： <code>bottle</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface Bottle extends Serializable {
  /**
   * <p>表名（ <code>bottle</code> ）。</p>
   * 组培瓶
   */
  String TB_NAME = "bottle";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>variety</code>）的 JavaBean 属性名（<code>variety</code>）。</p>
   * <p>品种</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>variety</code>）对应的数据库字段名（<code>variety</code>）。</p>
   * <p>品种</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>varietyCode</code>）的 JavaBean 属性名（<code>varietyCode</code>）。</p>
   * <p>品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETYCODE = "varietyCode";

  /**
   * <p>属性（<code>varietyCode</code>）对应的数据库字段名（<code>variety_code</code>）。</p>
   * <p>品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETYCODE = "variety_code";

  /**
   * <p>属性（<code>explantCollectionBatch</code>）的 JavaBean 属性名（<code>explantCollectionBatch</code>）。</p>
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_EXPLANTCOLLECTIONBATCH = "explantCollectionBatch";

  /**
   * <p>属性（<code>explantCollectionBatch</code>）对应的数据库字段名（<code>explant_collection_batch</code>）。</p>
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_EXPLANTCOLLECTIONBATCH = "explant_collection_batch";

  /**
   * <p>属性（<code>generation</code>）的 JavaBean 属性名（<code>generation</code>）。</p>
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_GENERATION = "generation";

  /**
   * <p>属性（<code>generation</code>）对应的数据库字段名（<code>generation</code>）。</p>
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_GENERATION = "generation";

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
   * <p>属性（<code>taskTime</code>）的 JavaBean 属性名（<code>taskTime</code>）。</p>
   * <p>形成该瓶的任务执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>task_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_TASKTIME = "taskTime";

  /**
   * <p>属性（<code>taskTime</code>）对应的数据库字段名（<code>task_time</code>）。</p>
   * <p>形成该瓶的任务执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>task_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_TASKTIME = "task_time";

  /**
   * <p>属性（<code>sourceType</code>）的 JavaBean 属性名（<code>sourceType</code>）。</p>
   * <p>产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_SOURCETYPE = "sourceType";

  /**
   * <p>属性（<code>sourceType</code>）对应的数据库字段名（<code>source_type</code>）。</p>
   * <p>产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_SOURCETYPE = "source_type";

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
   * <p>属性（<code>parentGeneration</code>）的 JavaBean 属性名（<code>parentGeneration</code>）。</p>
   * <p>母瓶代数</p>
   * <ul>
   *   <li>COLUMN : <code>parent_generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PARENTGENERATION = "parentGeneration";

  /**
   * <p>属性（<code>parentGeneration</code>）对应的数据库字段名（<code>parent_generation</code>）。</p>
   * <p>母瓶代数</p>
   * <ul>
   *   <li>COLUMN : <code>parent_generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PARENTGENERATION = "parent_generation";

  /**
   * <p>属性（<code>currentIdTask</code>）的 JavaBean 属性名（<code>currentIdTask</code>）。</p>
   * <p>当前所在任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTIDTASK = "currentIdTask";

  /**
   * <p>属性（<code>currentIdTask</code>）对应的数据库字段名（<code>current_id_task</code>）。</p>
   * <p>当前所在任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTIDTASK = "current_id_task";

  /**
   * <p>属性（<code>currentIdOppGroup</code>）的 JavaBean 属性名（<code>currentIdOppGroup</code>）。</p>
   * <p>当前操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTIDOPPGROUP = "currentIdOppGroup";

  /**
   * <p>属性（<code>currentIdOppGroup</code>）对应的数据库字段名（<code>current_id_opp_group</code>）。</p>
   * <p>当前操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTIDOPPGROUP = "current_id_opp_group";

  /**
   * <p>属性（<code>currentCultureStage</code>）的 JavaBean 属性名（<code>currentCultureStage</code>）。</p>
   * <p>当前工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>current_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTCULTURESTAGE = "currentCultureStage";

  /**
   * <p>属性（<code>currentCultureStage</code>）对应的数据库字段名（<code>current_culture_stage</code>）。</p>
   * <p>当前工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>current_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTCULTURESTAGE = "current_culture_stage";

  /**
   * <p>属性（<code>currentRoleInTask</code>）的 JavaBean 属性名（<code>currentRoleInTask</code>）。</p>
   * <p>当前任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>current_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTROLEINTASK = "currentRoleInTask";

  /**
   * <p>属性（<code>currentRoleInTask</code>）对应的数据库字段名（<code>current_role_in_task</code>）。</p>
   * <p>当前任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>current_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTROLEINTASK = "current_role_in_task";

  /**
   * <p>属性（<code>currentParentKeep</code>）的 JavaBean 属性名（<code>currentParentKeep</code>）。</p>
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTPARENTKEEP = "currentParentKeep";

  /**
   * <p>属性（<code>currentParentKeep</code>）对应的数据库字段名（<code>current_parent_keep</code>）。</p>
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTPARENTKEEP = "current_parent_keep";

  /**
   * <p>属性（<code>currentParentKeepPlantCount</code>）的 JavaBean 属性名（<code>currentParentKeepPlantCount</code>）。</p>
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTPARENTKEEPPLANTCOUNT = "currentParentKeepPlantCount";

  /**
   * <p>属性（<code>currentParentKeepPlantCount</code>）对应的数据库字段名（<code>current_parent_keep_plant_count</code>）。</p>
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTPARENTKEEPPLANTCOUNT = "current_parent_keep_plant_count";

  /**
   * <p>属性（<code>currentOperateStatus</code>）的 JavaBean 属性名（<code>currentOperateStatus</code>）。</p>
   * <p>当前操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTOPERATESTATUS = "currentOperateStatus";

  /**
   * <p>属性（<code>currentOperateStatus</code>）对应的数据库字段名（<code>current_operate_status</code>）。</p>
   * <p>当前操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTOPERATESTATUS = "current_operate_status";

  /**
   * <p>属性（<code>currentInspectorId</code>）的 JavaBean 属性名（<code>currentInspectorId</code>）。</p>
   * <p>当前质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTINSPECTORID = "currentInspectorId";

  /**
   * <p>属性（<code>currentInspectorId</code>）对应的数据库字段名（<code>current_inspector_id</code>）。</p>
   * <p>当前质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTINSPECTORID = "current_inspector_id";

  /**
   * <p>属性（<code>currentInspectorName</code>）的 JavaBean 属性名（<code>currentInspectorName</code>）。</p>
   * <p>当前质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTINSPECTORNAME = "currentInspectorName";

  /**
   * <p>属性（<code>currentInspectorName</code>）对应的数据库字段名（<code>current_inspector_name</code>）。</p>
   * <p>当前质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTINSPECTORNAME = "current_inspector_name";

  /**
   * <p>属性（<code>currentOperatorId</code>）的 JavaBean 属性名（<code>currentOperatorId</code>）。</p>
   * <p>当前操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTOPERATORID = "currentOperatorId";

  /**
   * <p>属性（<code>currentOperatorId</code>）对应的数据库字段名（<code>current_operator_id</code>）。</p>
   * <p>当前操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTOPERATORID = "current_operator_id";

  /**
   * <p>属性（<code>currentOperatorName</code>）的 JavaBean 属性名（<code>currentOperatorName</code>）。</p>
   * <p>当前操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTOPERATORNAME = "currentOperatorName";

  /**
   * <p>属性（<code>currentOperatorName</code>）对应的数据库字段名（<code>current_operator_name</code>）。</p>
   * <p>当前操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTOPERATORNAME = "current_operator_name";

  /**
   * <p>属性（<code>lastIdTask</code>）的 JavaBean 属性名（<code>lastIdTask</code>）。</p>
   * <p>末次任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTIDTASK = "lastIdTask";

  /**
   * <p>属性（<code>lastIdTask</code>）对应的数据库字段名（<code>last_id_task</code>）。</p>
   * <p>末次任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTIDTASK = "last_id_task";

  /**
   * <p>属性（<code>lastIdOppGroup</code>）的 JavaBean 属性名（<code>lastIdOppGroup</code>）。</p>
   * <p>末次操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTIDOPPGROUP = "lastIdOppGroup";

  /**
   * <p>属性（<code>lastIdOppGroup</code>）对应的数据库字段名（<code>last_id_opp_group</code>）。</p>
   * <p>末次操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTIDOPPGROUP = "last_id_opp_group";

  /**
   * <p>属性（<code>lastCultureStage</code>）的 JavaBean 属性名（<code>lastCultureStage</code>）。</p>
   * <p>末次工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>last_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTCULTURESTAGE = "lastCultureStage";

  /**
   * <p>属性（<code>lastCultureStage</code>）对应的数据库字段名（<code>last_culture_stage</code>）。</p>
   * <p>末次工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>last_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTCULTURESTAGE = "last_culture_stage";

  /**
   * <p>属性（<code>lastRoleInTask</code>）的 JavaBean 属性名（<code>lastRoleInTask</code>）。</p>
   * <p>末次任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>last_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTROLEINTASK = "lastRoleInTask";

  /**
   * <p>属性（<code>lastRoleInTask</code>）对应的数据库字段名（<code>last_role_in_task</code>）。</p>
   * <p>末次任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>last_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTROLEINTASK = "last_role_in_task";

  /**
   * <p>属性（<code>lastParentKeep</code>）的 JavaBean 属性名（<code>lastParentKeep</code>）。</p>
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_LASTPARENTKEEP = "lastParentKeep";

  /**
   * <p>属性（<code>lastParentKeep</code>）对应的数据库字段名（<code>last_parent_keep</code>）。</p>
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTPARENTKEEP = "last_parent_keep";

  /**
   * <p>属性（<code>lastParentKeepPlantCount</code>）的 JavaBean 属性名（<code>lastParentKeepPlantCount</code>）。</p>
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_LASTPARENTKEEPPLANTCOUNT = "lastParentKeepPlantCount";

  /**
   * <p>属性（<code>lastParentKeepPlantCount</code>）对应的数据库字段名（<code>last_parent_keep_plant_count</code>）。</p>
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTPARENTKEEPPLANTCOUNT = "last_parent_keep_plant_count";

  /**
   * <p>属性（<code>lastOperateStatus</code>）的 JavaBean 属性名（<code>lastOperateStatus</code>）。</p>
   * <p>末次操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>last_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTOPERATESTATUS = "lastOperateStatus";

  /**
   * <p>属性（<code>lastOperateStatus</code>）对应的数据库字段名（<code>last_operate_status</code>）。</p>
   * <p>末次操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>last_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTOPERATESTATUS = "last_operate_status";

  /**
   * <p>属性（<code>lastInspectorId</code>）的 JavaBean 属性名（<code>lastInspectorId</code>）。</p>
   * <p>末次质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTINSPECTORID = "lastInspectorId";

  /**
   * <p>属性（<code>lastInspectorId</code>）对应的数据库字段名（<code>last_inspector_id</code>）。</p>
   * <p>末次质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTINSPECTORID = "last_inspector_id";

  /**
   * <p>属性（<code>lastInspectorName</code>）的 JavaBean 属性名（<code>lastInspectorName</code>）。</p>
   * <p>末次质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTINSPECTORNAME = "lastInspectorName";

  /**
   * <p>属性（<code>lastInspectorName</code>）对应的数据库字段名（<code>last_inspector_name</code>）。</p>
   * <p>末次质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTINSPECTORNAME = "last_inspector_name";

  /**
   * <p>属性（<code>lastOperatorId</code>）的 JavaBean 属性名（<code>lastOperatorId</code>）。</p>
   * <p>末次操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTOPERATORID = "lastOperatorId";

  /**
   * <p>属性（<code>lastOperatorId</code>）对应的数据库字段名（<code>last_operator_id</code>）。</p>
   * <p>末次操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTOPERATORID = "last_operator_id";

  /**
   * <p>属性（<code>lastOperatorName</code>）的 JavaBean 属性名（<code>lastOperatorName</code>）。</p>
   * <p>末次操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTOPERATORNAME = "lastOperatorName";

  /**
   * <p>属性（<code>lastOperatorName</code>）对应的数据库字段名（<code>last_operator_name</code>）。</p>
   * <p>末次操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTOPERATORNAME = "last_operator_name";

  /**
   * <p>属性（<code>currentStorageStatus</code>）的 JavaBean 属性名（<code>currentStorageStatus</code>）。</p>
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CURRENTSTORAGESTATUS = "currentStorageStatus";

  /**
   * <p>属性（<code>currentStorageStatus</code>）对应的数据库字段名（<code>current_storage_status</code>）。</p>
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CURRENTSTORAGESTATUS = "current_storage_status";

  /**
   * <p>属性（<code>lastReceivedUserId</code>）的 JavaBean 属性名（<code>lastReceivedUserId</code>）。</p>
   * <p>末次入库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTRECEIVEDUSERID = "lastReceivedUserId";

  /**
   * <p>属性（<code>lastReceivedUserId</code>）对应的数据库字段名（<code>last_received_user_id</code>）。</p>
   * <p>末次入库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTRECEIVEDUSERID = "last_received_user_id";

  /**
   * <p>属性（<code>lastReceivedUserName</code>）的 JavaBean 属性名（<code>lastReceivedUserName</code>）。</p>
   * <p>末次入库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTRECEIVEDUSERNAME = "lastReceivedUserName";

  /**
   * <p>属性（<code>lastReceivedUserName</code>）对应的数据库字段名（<code>last_received_user_name</code>）。</p>
   * <p>末次入库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTRECEIVEDUSERNAME = "last_received_user_name";

  /**
   * <p>属性（<code>lastReceivedTime</code>）的 JavaBean 属性名（<code>lastReceivedTime</code>）。</p>
   * <p>末次入库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_LASTRECEIVEDTIME = "lastReceivedTime";

  /**
   * <p>属性（<code>lastReceivedTime</code>）对应的数据库字段名（<code>last_received_time</code>）。</p>
   * <p>末次入库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTRECEIVEDTIME = "last_received_time";

  /**
   * <p>属性（<code>lastReceivedReason</code>）的 JavaBean 属性名（<code>lastReceivedReason</code>）。</p>
   * <p>末次入库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTRECEIVEDREASON = "lastReceivedReason";

  /**
   * <p>属性（<code>lastReceivedReason</code>）对应的数据库字段名（<code>last_received_reason</code>）。</p>
   * <p>末次入库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTRECEIVEDREASON = "last_received_reason";

  /**
   * <p>属性（<code>lastIssuedUserId</code>）的 JavaBean 属性名（<code>lastIssuedUserId</code>）。</p>
   * <p>末次出库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTISSUEDUSERID = "lastIssuedUserId";

  /**
   * <p>属性（<code>lastIssuedUserId</code>）对应的数据库字段名（<code>last_issued_user_id</code>）。</p>
   * <p>末次出库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTISSUEDUSERID = "last_issued_user_id";

  /**
   * <p>属性（<code>lastIssuedUserName</code>）的 JavaBean 属性名（<code>lastIssuedUserName</code>）。</p>
   * <p>末次出库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTISSUEDUSERNAME = "lastIssuedUserName";

  /**
   * <p>属性（<code>lastIssuedUserName</code>）对应的数据库字段名（<code>last_issued_user_name</code>）。</p>
   * <p>末次出库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTISSUEDUSERNAME = "last_issued_user_name";

  /**
   * <p>属性（<code>lastIssuedTime</code>）的 JavaBean 属性名（<code>lastIssuedTime</code>）。</p>
   * <p>末次出库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_LASTISSUEDTIME = "lastIssuedTime";

  /**
   * <p>属性（<code>lastIssuedTime</code>）对应的数据库字段名（<code>last_issued_time</code>）。</p>
   * <p>末次出库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTISSUEDTIME = "last_issued_time";

  /**
   * <p>属性（<code>lastIssuedReason</code>）的 JavaBean 属性名（<code>lastIssuedReason</code>）。</p>
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_LASTISSUEDREASON = "lastIssuedReason";

  /**
   * <p>属性（<code>lastIssuedReason</code>）对应的数据库字段名（<code>last_issued_reason</code>）。</p>
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_LASTISSUEDREASON = "last_issued_reason";

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
   * <p>属性（<code>performanceIncluded</code>）的 JavaBean 属性名（<code>performanceIncluded</code>）。</p>
   * <p>是否计入绩效。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>performance_included</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String BP_NAME_PERFORMANCEINCLUDED = "performanceIncluded";

  /**
   * <p>属性（<code>performanceIncluded</code>）对应的数据库字段名（<code>performance_included</code>）。</p>
   * <p>是否计入绩效。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>performance_included</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PERFORMANCEINCLUDED = "performance_included";

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
   * <p>属性（<code>discardReasonDesc</code>）的 JavaBean 属性名（<code>discardReasonDesc</code>）。</p>
   * <p>淘汰备注</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason_desc</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_DISCARDREASONDESC = "discardReasonDesc";

  /**
   * <p>属性（<code>discardReasonDesc</code>）对应的数据库字段名（<code>discard_reason_desc</code>）。</p>
   * <p>淘汰备注</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason_desc</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DISCARDREASONDESC = "discard_reason_desc";

  /**
   * <p>属性（<code>discardTime</code>）的 JavaBean 属性名（<code>discardTime</code>）。</p>
   * <p>淘汰时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>discard_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_DISCARDTIME = "discardTime";

  /**
   * <p>属性（<code>discardTime</code>）对应的数据库字段名（<code>discard_time</code>）。</p>
   * <p>淘汰时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>discard_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DISCARDTIME = "discard_time";

  /**
   * <p>属性（<code>discardUserId</code>）的 JavaBean 属性名（<code>discardUserId</code>）。</p>
   * <p>淘汰执行人ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_DISCARDUSERID = "discardUserId";

  /**
   * <p>属性（<code>discardUserId</code>）对应的数据库字段名（<code>discard_user_id</code>）。</p>
   * <p>淘汰执行人ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DISCARDUSERID = "discard_user_id";

  /**
   * <p>属性（<code>discardUserName</code>）的 JavaBean 属性名（<code>discardUserName</code>）。</p>
   * <p>淘汰执行人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_DISCARDUSERNAME = "discardUserName";

  /**
   * <p>属性（<code>discardUserName</code>）对应的数据库字段名（<code>discard_user_name</code>）。</p>
   * <p>淘汰执行人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DISCARDUSERNAME = "discard_user_name";

  /**
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>品种</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getVariety();

  /**
   * <p>品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getVarietyCode();

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getExplantCollectionBatch();

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getGeneration();

  /**
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantCount();

  /**
   * <p>形成该瓶的任务执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>task_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getTaskTime();

  /**
   * <p>产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getSourceType();

  /**
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdParent();

  /**
   * <p>母瓶代数</p>
   * <ul>
   *   <li>COLUMN : <code>parent_generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getParentGeneration();

  /**
   * <p>当前所在任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentIdTask();

  /**
   * <p>当前操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentIdOppGroup();

  /**
   * <p>当前工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>current_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentCultureStage();

  /**
   * <p>当前任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>current_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentRoleInTask();

  /**
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getCurrentParentKeep();

  /**
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getCurrentParentKeepPlantCount();

  /**
   * <p>当前操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentOperateStatus();

  /**
   * <p>当前质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentInspectorId();

  /**
   * <p>当前质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentInspectorName();

  /**
   * <p>当前操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentOperatorId();

  /**
   * <p>当前操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentOperatorName();

  /**
   * <p>末次任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastIdTask();

  /**
   * <p>末次操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastIdOppGroup();

  /**
   * <p>末次工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>last_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastCultureStage();

  /**
   * <p>末次任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>last_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastRoleInTask();

  /**
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getLastParentKeep();

  /**
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getLastParentKeepPlantCount();

  /**
   * <p>末次操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>last_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastOperateStatus();

  /**
   * <p>末次质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastInspectorId();

  /**
   * <p>末次质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastInspectorName();

  /**
   * <p>末次操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastOperatorId();

  /**
   * <p>末次操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastOperatorName();

  /**
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCurrentStorageStatus();

  /**
   * <p>末次入库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastReceivedUserId();

  /**
   * <p>末次入库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastReceivedUserName();

  /**
   * <p>末次入库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getLastReceivedTime();

  /**
   * <p>末次入库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastReceivedReason();

  /**
   * <p>末次出库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastIssuedUserId();

  /**
   * <p>末次出库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastIssuedUserName();

  /**
   * <p>末次出库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getLastIssuedTime();

  /**
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getLastIssuedReason();

  /**
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getDiscarded();

  /**
   * <p>是否计入绩效。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>performance_included</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String getPerformanceIncluded();

  /**
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getDiscardReason();

  /**
   * <p>淘汰备注</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason_desc</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getDiscardReasonDesc();

  /**
   * <p>淘汰时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>discard_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getDiscardTime();

  /**
   * <p>淘汰执行人ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getDiscardUserId();

  /**
   * <p>淘汰执行人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getDiscardUserName();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
