package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>组培任务-操作分配组‑培养瓶关联明细</p>
 * <p>数据库表： <code>tissue_task_bottle</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>idTask</code>: 参见 {@link #getIdTask()} 。</li>
 *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface TissueTaskBottle extends Serializable {
  /**
   * <p>表名（ <code>tissue_task_bottle</code> ）。</p>
   * 组培任务-操作分配组‑培养瓶关联明细
   */
  String TB_NAME = "tissue_task_bottle";

  /**
   * <p>属性（<code>idTask</code>）的 JavaBean 属性名（<code>idTask</code>）。</p>
   * <p>任务ID。tissue_task.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_IDTASK = "idTask";

  /**
   * <p>属性（<code>idTask</code>）对应的数据库字段名（<code>id_task</code>）。</p>
   * <p>任务ID。tissue_task.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDTASK = "id_task";

  /**
   * <p>属性（<code>idOppGroup</code>）的 JavaBean 属性名（<code>idOppGroup</code>）。</p>
   * <p>操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_IDOPPGROUP = "idOppGroup";

  /**
   * <p>属性（<code>idOppGroup</code>）对应的数据库字段名（<code>id_opp_group</code>）。</p>
   * <p>操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDOPPGROUP = "id_opp_group";

  /**
   * <p>属性（<code>idBottle</code>）的 JavaBean 属性名（<code>idBottle</code>）。</p>
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_IDBOTTLE = "idBottle";

  /**
   * <p>属性（<code>idBottle</code>）对应的数据库字段名（<code>id_bottle</code>）。</p>
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDBOTTLE = "id_bottle";

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
   * <p>代际</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_GENERATION = "generation";

  /**
   * <p>属性（<code>generation</code>）对应的数据库字段名（<code>generation</code>）。</p>
   * <p>代际</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_GENERATION = "generation";

  /**
   * <p>属性（<code>plantCountOriginal</code>）的 JavaBean 属性名（<code>plantCountOriginal</code>）。</p>
   * <p>瓶内株数（任务前）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_original</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTCOUNTORIGINAL = "plantCountOriginal";

  /**
   * <p>属性（<code>plantCountOriginal</code>）对应的数据库字段名（<code>plant_count_original</code>）。</p>
   * <p>瓶内株数（任务前）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_original</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTCOUNTORIGINAL = "plant_count_original";

  /**
   * <p>属性（<code>plantCountActual</code>）的 JavaBean 属性名（<code>plantCountActual</code>）。</p>
   * <p>瓶内株数（实际）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTCOUNTACTUAL = "plantCountActual";

  /**
   * <p>属性（<code>plantCountActual</code>）对应的数据库字段名（<code>plant_count_actual</code>）。</p>
   * <p>瓶内株数（实际）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTCOUNTACTUAL = "plant_count_actual";

  /**
   * <p>属性（<code>roleInTask</code>）的 JavaBean 属性名（<code>roleInTask</code>）。</p>
   * <p>任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_ROLEINTASK = "roleInTask";

  /**
   * <p>属性（<code>roleInTask</code>）对应的数据库字段名（<code>role_in_task</code>）。</p>
   * <p>任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ROLEINTASK = "role_in_task";

  /**
   * <p>属性（<code>parentKeep</code>）的 JavaBean 属性名（<code>parentKeep</code>）。</p>
   * <p>若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_PARENTKEEP = "parentKeep";

  /**
   * <p>属性（<code>parentKeep</code>）对应的数据库字段名（<code>parent_keep</code>）。</p>
   * <p>若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PARENTKEEP = "parent_keep";

  /**
   * <p>属性（<code>parentKeepPlantCount</code>）的 JavaBean 属性名（<code>parentKeepPlantCount</code>）。</p>
   * <p>若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PARENTKEEPPLANTCOUNT = "parentKeepPlantCount";

  /**
   * <p>属性（<code>parentKeepPlantCount</code>）对应的数据库字段名（<code>parent_keep_plant_count</code>）。</p>
   * <p>若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PARENTKEEPPLANTCOUNT = "parent_keep_plant_count";

  /**
   * <p>属性（<code>parentLastTaskType</code>）的 JavaBean 属性名（<code>parentLastTaskType</code>）。</p>
   * <p>母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>parent_last_task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_PARENTLASTTASKTYPE = "parentLastTaskType";

  /**
   * <p>属性（<code>parentLastTaskType</code>）对应的数据库字段名（<code>parent_last_task_type</code>）。</p>
   * <p>母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>parent_last_task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PARENTLASTTASKTYPE = "parent_last_task_type";

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
   * <p>属性（<code>childCodeStatus</code>）的 JavaBean 属性名（<code>childCodeStatus</code>）。</p>
   * <p>子瓶瓶码状态。 dictionary.bottle_code_status_for_task</p>
   * <ul>
   *   <li>COLUMN : <code>child_code_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  String BP_NAME_CHILDCODESTATUS = "childCodeStatus";

  /**
   * <p>属性（<code>childCodeStatus</code>）对应的数据库字段名（<code>child_code_status</code>）。</p>
   * <p>子瓶瓶码状态。 dictionary.bottle_code_status_for_task</p>
   * <ul>
   *   <li>COLUMN : <code>child_code_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CHILDCODESTATUS = "child_code_status";

  /**
   * <p>属性（<code>discardReason</code>）的 JavaBean 属性名（<code>discardReason</code>）。</p>
   * <p>若淘汰，体现淘汰原因。</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_DISCARDREASON = "discardReason";

  /**
   * <p>属性（<code>discardReason</code>）对应的数据库字段名（<code>discard_reason</code>）。</p>
   * <p>若淘汰，体现淘汰原因。</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DISCARDREASON = "discard_reason";

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
   * <p>任务ID。tissue_task.id。联合主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdTask();

  /**
   * <p>操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdOppGroup();

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdBottle();

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getExplantCollectionBatch();

  /**
   * <p>代际</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getGeneration();

  /**
   * <p>瓶内株数（任务前）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_original</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantCountOriginal();

  /**
   * <p>瓶内株数（实际）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantCountActual();

  /**
   * <p>任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getRoleInTask();

  /**
   * <p>若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getParentKeep();

  /**
   * <p>若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getParentKeepPlantCount();

  /**
   * <p>母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>parent_last_task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getParentLastTaskType();

  /**
   * <p>操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperateStatus();

  /**
   * <p>子瓶瓶码状态。 dictionary.bottle_code_status_for_task</p>
   * <ul>
   *   <li>COLUMN : <code>child_code_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  String getChildCodeStatus();

  /**
   * <p>若淘汰，体现淘汰原因。</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getDiscardReason();

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
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    String seperator = "\u001E";
    StringBuilder result = new StringBuilder();
    result.append(seperator).append(getIdTask() == null ? "\u001F" : getIdTask());
    result.append(seperator).append(getIdBottle() == null ? "\u001F" : getIdBottle());
    return result.toString();
  }
}
