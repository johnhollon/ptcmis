package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>瓶体补码任务</p>
 * <p>数据库表： <code>bottle_code_complement_task</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface BottleCodeComplementTask extends Serializable {
  /**
   * <p>表名（ <code>bottle_code_complement_task</code> ）。</p>
   * 瓶体补码任务
   */
  String TB_NAME = "bottle_code_complement_task";

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
   * <p>属性（<code>sourceType</code>）的 JavaBean 属性名（<code>sourceType</code>）。</p>
   * <p>培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_SOURCETYPE = "sourceType";

  /**
   * <p>属性（<code>sourceType</code>）对应的数据库字段名（<code>source_type</code>）。</p>
   * <p>培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_SOURCETYPE = "source_type";

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
   * <p>属性（<code>defaultPlantsPerBottle</code>）的 JavaBean 属性名（<code>defaultPlantsPerBottle</code>）。</p>
   * <p>任务参数：每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_DEFAULTPLANTSPERBOTTLE = "defaultPlantsPerBottle";

  /**
   * <p>属性（<code>defaultPlantsPerBottle</code>）对应的数据库字段名（<code>default_plants_per_bottle</code>）。</p>
   * <p>任务参数：每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DEFAULTPLANTSPERBOTTLE = "default_plants_per_bottle";

  /**
   * <p>属性（<code>taskStatus</code>）的 JavaBean 属性名（<code>taskStatus</code>）。</p>
   * <p>任务状态：dictionary.bottle_code_complement_task_stat</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String BP_NAME_TASKSTATUS = "taskStatus";

  /**
   * <p>属性（<code>taskStatus</code>）对应的数据库字段名（<code>task_status</code>）。</p>
   * <p>任务状态：dictionary.bottle_code_complement_task_stat</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_TASKSTATUS = "task_status";

  /**
   * <p>属性（<code>bottles</code>）的 JavaBean 属性名（<code>bottles</code>）。</p>
   * <p>实际赋码瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLES = "bottles";

  /**
   * <p>属性（<code>bottles</code>）对应的数据库字段名（<code>bottles</code>）。</p>
   * <p>实际赋码瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLES = "bottles";

  /**
   * <p>属性（<code>plants</code>）的 JavaBean 属性名（<code>plants</code>）。</p>
   * <p>实际赋码株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTS = "plants";

  /**
   * <p>属性（<code>plants</code>）对应的数据库字段名（<code>plants</code>）。</p>
   * <p>实际赋码株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTS = "plants";

  /**
   * <p>属性（<code>creatorId</code>）的 JavaBean 属性名（<code>creatorId</code>）。</p>
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CREATORID = "creatorId";

  /**
   * <p>属性（<code>creatorId</code>）对应的数据库字段名（<code>creator_id</code>）。</p>
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CREATORID = "creator_id";

  /**
   * <p>属性（<code>creatorName</code>）的 JavaBean 属性名（<code>creatorName</code>）。</p>
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CREATORNAME = "creatorName";

  /**
   * <p>属性（<code>creatorName</code>）对应的数据库字段名（<code>creator_name</code>）。</p>
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CREATORNAME = "creator_name";

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
   * <p>属性（<code>operatorId</code>）的 JavaBean 属性名（<code>operatorId</code>）。</p>
   * <p>末次操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORID = "operatorId";

  /**
   * <p>属性（<code>operatorId</code>）对应的数据库字段名（<code>operator_id</code>）。</p>
   * <p>末次操作人用户ID，user.id</p>
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
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getExplantCollectionBatch();

  /**
   * <p>培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getSourceType();

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getGeneration();

  /**
   * <p>瓶码前缀（打印时生成6位大写字母）</p>
   * <ul>
   *   <li>COLUMN : <code>code_prefix</code></li>
   *   <li>DDL : <code>VARCHAR(6), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCodePrefix();

  /**
   * <p>任务参数：每瓶株数</p>
   * <ul>
   *   <li>COLUMN : <code>default_plants_per_bottle</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getDefaultPlantsPerBottle();

  /**
   * <p>任务状态：dictionary.bottle_code_complement_task_stat</p>
   * <ul>
   *   <li>COLUMN : <code>task_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String getTaskStatus();

  /**
   * <p>实际赋码瓶数</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getBottles();

  /**
   * <p>实际赋码株数</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlants();

  /**
   * <p>创建人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>creator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCreatorId();

  /**
   * <p>创建人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>creator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getCreatorName();

  /**
   * <p>创建时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>create_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getCreateTime();

  /**
   * <p>末次操作人用户ID，user.id</p>
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
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
