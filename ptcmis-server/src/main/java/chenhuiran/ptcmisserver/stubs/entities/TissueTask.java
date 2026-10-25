package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>组培生产任务</p>
 * <p>数据库表： <code>tissue_task</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface TissueTask extends Serializable {
  /**
   * <p>表名（ <code>tissue_task</code> ）。</p>
   * 组培生产任务
   */
  String TB_NAME = "tissue_task";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>variety</code>）的 JavaBean 属性名（<code>variety</code>）。</p>
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>variety</code>）对应的数据库字段名（<code>variety</code>）。</p>
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>varietyCode</code>）的 JavaBean 属性名（<code>varietyCode</code>）。</p>
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETYCODE = "varietyCode";

  /**
   * <p>属性（<code>varietyCode</code>）对应的数据库字段名（<code>variety_code</code>）。</p>
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETYCODE = "variety_code";

  /**
   * <p>属性（<code>explantCollectionBatch</code>）的 JavaBean 属性名（<code>explantCollectionBatch</code>）。</p>
   * <p>外植体采集批次，仅用于诱导任务</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_EXPLANTCOLLECTIONBATCH = "explantCollectionBatch";

  /**
   * <p>属性（<code>explantCollectionBatch</code>）对应的数据库字段名（<code>explant_collection_batch</code>）。</p>
   * <p>外植体采集批次，仅用于诱导任务</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_EXPLANTCOLLECTIONBATCH = "explant_collection_batch";

  /**
   * <p>属性（<code>estimatedParentBottles</code>）的 JavaBean 属性名（<code>estimatedParentBottles</code>）。</p>
   * <p>预计使用母代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_ESTIMATEDPARENTBOTTLES = "estimatedParentBottles";

  /**
   * <p>属性（<code>estimatedParentBottles</code>）对应的数据库字段名（<code>estimated_parent_bottles</code>）。</p>
   * <p>预计使用母代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ESTIMATEDPARENTBOTTLES = "estimated_parent_bottles";

  /**
   * <p>属性（<code>estimatedParentPlants</code>）的 JavaBean 属性名（<code>estimatedParentPlants</code>）。</p>
   * <p>预计使用母代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_ESTIMATEDPARENTPLANTS = "estimatedParentPlants";

  /**
   * <p>属性（<code>estimatedParentPlants</code>）对应的数据库字段名（<code>estimated_parent_plants</code>）。</p>
   * <p>预计使用母代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ESTIMATEDPARENTPLANTS = "estimated_parent_plants";

  /**
   * <p>属性（<code>estimatedParentKeepBottles</code>）的 JavaBean 属性名（<code>estimatedParentKeepBottles</code>）。</p>
   * <p>预计母代留种瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_ESTIMATEDPARENTKEEPBOTTLES = "estimatedParentKeepBottles";

  /**
   * <p>属性（<code>estimatedParentKeepBottles</code>）对应的数据库字段名（<code>estimated_parent_keep_bottles</code>）。</p>
   * <p>预计母代留种瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ESTIMATEDPARENTKEEPBOTTLES = "estimated_parent_keep_bottles";

  /**
   * <p>属性（<code>estimatedParentKeepPlants</code>）的 JavaBean 属性名（<code>estimatedParentKeepPlants</code>）。</p>
   * <p>预计母代留种株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_ESTIMATEDPARENTKEEPPLANTS = "estimatedParentKeepPlants";

  /**
   * <p>属性（<code>estimatedParentKeepPlants</code>）对应的数据库字段名（<code>estimated_parent_keep_plants</code>）。</p>
   * <p>预计母代留种株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ESTIMATEDPARENTKEEPPLANTS = "estimated_parent_keep_plants";

  /**
   * <p>属性（<code>estimatedChildrenBottles</code>）的 JavaBean 属性名（<code>estimatedChildrenBottles</code>）。</p>
   * <p>预计生产子代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_ESTIMATEDCHILDRENBOTTLES = "estimatedChildrenBottles";

  /**
   * <p>属性（<code>estimatedChildrenBottles</code>）对应的数据库字段名（<code>estimated_children_bottles</code>）。</p>
   * <p>预计生产子代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ESTIMATEDCHILDRENBOTTLES = "estimated_children_bottles";

  /**
   * <p>属性（<code>estimatedChildrenPlants</code>）的 JavaBean 属性名（<code>estimatedChildrenPlants</code>）。</p>
   * <p>预计生产子代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_ESTIMATEDCHILDRENPLANTS = "estimatedChildrenPlants";

  /**
   * <p>属性（<code>estimatedChildrenPlants</code>）对应的数据库字段名（<code>estimated_children_plants</code>）。</p>
   * <p>预计生产子代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ESTIMATEDCHILDRENPLANTS = "estimated_children_plants";

  /**
   * <p>属性（<code>defaultPlantsPerParentBottle</code>）的 JavaBean 属性名（<code>defaultPlantsPerParentBottle</code>）。</p>
   * <p>任务参数：母代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_parent_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_DEFAULTPLANTSPERPARENTBOTTLE = "defaultPlantsPerParentBottle";

  /**
   * <p>属性（<code>defaultPlantsPerParentBottle</code>）对应的数据库字段名（<code>default_plants_per_parent_bottle</code>）。</p>
   * <p>任务参数：母代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_parent_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DEFAULTPLANTSPERPARENTBOTTLE = "default_plants_per_parent_bottle";

  /**
   * <p>属性（<code>defaultPlantsPerChildBottle</code>）的 JavaBean 属性名（<code>defaultPlantsPerChildBottle</code>）。</p>
   * <p>任务参数：子代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_child_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_DEFAULTPLANTSPERCHILDBOTTLE = "defaultPlantsPerChildBottle";

  /**
   * <p>属性（<code>defaultPlantsPerChildBottle</code>）对应的数据库字段名（<code>default_plants_per_child_bottle</code>）。</p>
   * <p>任务参数：子代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_child_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DEFAULTPLANTSPERCHILDBOTTLE = "default_plants_per_child_bottle";

  /**
   * <p>属性（<code>codePrefix</code>）的 JavaBean 属性名（<code>codePrefix</code>）。</p>
   * <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CODEPREFIX = "codePrefix";

  /**
   * <p>属性（<code>codePrefix</code>）对应的数据库字段名（<code>code_prefix</code>）。</p>
   * <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CODEPREFIX = "code_prefix";

  /**
   * <p>属性（<code>creatorUserId</code>）的 JavaBean 属性名（<code>creatorUserId</code>）。</p>
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CREATORUSERID = "creatorUserId";

  /**
   * <p>属性（<code>creatorUserId</code>）对应的数据库字段名（<code>creator_user_id</code>）。</p>
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CREATORUSERID = "creator_user_id";

  /**
   * <p>属性（<code>creatorUserName</code>）的 JavaBean 属性名（<code>creatorUserName</code>）。</p>
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CREATORUSERNAME = "creatorUserName";

  /**
   * <p>属性（<code>creatorUserName</code>）对应的数据库字段名（<code>creator_user_name</code>）。</p>
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CREATORUSERNAME = "creator_user_name";

  /**
   * <p>属性（<code>taskType</code>）的 JavaBean 属性名（<code>taskType</code>）。</p>
   * <p>任务类别：dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_TASKTYPE = "taskType";

  /**
   * <p>属性（<code>taskType</code>）对应的数据库字段名（<code>task_type</code>）。</p>
   * <p>任务类别：dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_TASKTYPE = "task_type";

  /**
   * <p>属性（<code>taskStatus</code>）的 JavaBean 属性名（<code>taskStatus</code>）。</p>
   * <p>任务状态：dictionary.tissue_task_status</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String BP_NAME_TASKSTATUS = "taskStatus";

  /**
   * <p>属性（<code>taskStatus</code>）对应的数据库字段名（<code>task_status</code>）。</p>
   * <p>任务状态：dictionary.tissue_task_status</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_TASKSTATUS = "task_status";

  /**
   * <p>属性（<code>createTime</code>）的 JavaBean 属性名（<code>createTime</code>）。</p>
   * <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_CREATETIME = "createTime";

  /**
   * <p>属性（<code>createTime</code>）对应的数据库字段名（<code>create_time</code>）。</p>
   * <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CREATETIME = "create_time";

  /**
   * <p>属性（<code>startTime</code>）的 JavaBean 属性名（<code>startTime</code>）。</p>
   * <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_STARTTIME = "startTime";

  /**
   * <p>属性（<code>startTime</code>）对应的数据库字段名（<code>start_time</code>）。</p>
   * <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_STARTTIME = "start_time";

  /**
   * <p>属性（<code>finishTime</code>）的 JavaBean 属性名（<code>finishTime</code>）。</p>
   * <p>完成时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_FINISHTIME = "finishTime";

  /**
   * <p>属性（<code>finishTime</code>）对应的数据库字段名（<code>finish_time</code>）。</p>
   * <p>完成时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_FINISHTIME = "finish_time";

  /**
   * <p>属性（<code>modifiable</code>）的 JavaBean 属性名（<code>modifiable</code>）。</p>
   * <p>是否开启强制编辑，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>modifiable</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_MODIFIABLE = "modifiable";

  /**
   * <p>属性（<code>modifiable</code>）对应的数据库字段名（<code>modifiable</code>）。</p>
   * <p>是否开启强制编辑，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>modifiable</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_MODIFIABLE = "modifiable";

  /**
   * <p>任务ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>品种 variety.name</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getVariety();

  /**
   * <p>品种编号（子品种） variety_code.code</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getVarietyCode();

  /**
   * <p>外植体采集批次，仅用于诱导任务</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getExplantCollectionBatch();

  /**
   * <p>预计使用母代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getEstimatedParentBottles();

  /**
   * <p>预计使用母代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getEstimatedParentPlants();

  /**
   * <p>预计母代留种瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getEstimatedParentKeepBottles();

  /**
   * <p>预计母代留种株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_parent_keep_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getEstimatedParentKeepPlants();

  /**
   * <p>预计生产子代瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getEstimatedChildrenBottles();

  /**
   * <p>预计生产子代株数</p>
   * <ul>
   *   <li>COLUMN : <code>estimated_children_plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getEstimatedChildrenPlants();

  /**
   * <p>任务参数：母代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_parent_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getDefaultPlantsPerParentBottle();

  /**
   * <p>任务参数：子代默认每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_child_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getDefaultPlantsPerChildBottle();

  /**
   * <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCodePrefix();

  /**
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCreatorUserId();

  /**
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCreatorUserName();

  /**
   * <p>任务类别：dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>task_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getTaskType();

  /**
   * <p>任务状态：dictionary.tissue_task_status</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String getTaskStatus();

  /**
   * <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getCreateTime();

  /**
   * <p>开始时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>start_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getStartTime();

  /**
   * <p>完成时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>finish_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getFinishTime();

  /**
   * <p>是否开启强制编辑，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>modifiable</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getModifiable();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
