package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>出/入库单明细</p>
 * <p>数据库表： <code>storage_in_out_bottle</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>idInOut</code>: 参见 {@link #getIdInOut()} 。</li>
 *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface StorageInOutBottle extends Serializable {
  /**
   * <p>表名（ <code>storage_in_out_bottle</code> ）。</p>
   * 出/入库单明细
   */
  String TB_NAME = "storage_in_out_bottle";

  /**
   * <p>属性（<code>idInOut</code>）的 JavaBean 属性名（<code>idInOut</code>）。</p>
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDINOUT = "idInOut";

  /**
   * <p>属性（<code>idInOut</code>）对应的数据库字段名（<code>id_in_out</code>）。</p>
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDINOUT = "id_in_out";

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
   * <p>属性（<code>variety</code>）的 JavaBean 属性名（<code>variety</code>）。</p>
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>variety</code>）对应的数据库字段名（<code>variety</code>）。</p>
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_VARIETY = "variety";

  /**
   * <p>属性（<code>varietyCode</code>）的 JavaBean 属性名（<code>varietyCode</code>）。</p>
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_VARIETYCODE = "varietyCode";

  /**
   * <p>属性（<code>varietyCode</code>）对应的数据库字段名（<code>variety_code</code>）。</p>
   * <p>子品种编号。主键</p>
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
   * <p>属性（<code>ignoreStats</code>）的 JavaBean 属性名（<code>ignoreStats</code>）。</p>
   * <p>是否在统计中忽略该明细。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_IGNORESTATS = "ignoreStats";

  /**
   * <p>属性（<code>ignoreStats</code>）对应的数据库字段名（<code>ignore_stats</code>）。</p>
   * <p>是否在统计中忽略该明细。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IGNORESTATS = "ignore_stats";

  /**
   * <p>属性（<code>inOutBottleType</code>）的 JavaBean 属性名（<code>inOutBottleType</code>）。</p>
   * <p>培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out_bottle_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_INOUTBOTTLETYPE = "inOutBottleType";

  /**
   * <p>属性（<code>inOutBottleType</code>）对应的数据库字段名（<code>in_out_bottle_type</code>）。</p>
   * <p>培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out_bottle_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_INOUTBOTTLETYPE = "in_out_bottle_type";

  /**
   * <p>属性（<code>plantCountApplied</code>）的 JavaBean 属性名（<code>plantCountApplied</code>）。</p>
   * <p>瓶内株数（申请出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_applied</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTCOUNTAPPLIED = "plantCountApplied";

  /**
   * <p>属性（<code>plantCountApplied</code>）对应的数据库字段名（<code>plant_count_applied</code>）。</p>
   * <p>瓶内株数（申请出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_applied</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTCOUNTAPPLIED = "plant_count_applied";

  /**
   * <p>属性（<code>plantCountActual</code>）的 JavaBean 属性名（<code>plantCountActual</code>）。</p>
   * <p>瓶内株数（实际出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTCOUNTACTUAL = "plantCountActual";

  /**
   * <p>属性（<code>plantCountActual</code>）对应的数据库字段名（<code>plant_count_actual</code>）。</p>
   * <p>瓶内株数（实际出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTCOUNTACTUAL = "plant_count_actual";

  /**
   * <p>属性（<code>keep</code>）的 JavaBean 属性名（<code>keep</code>）。</p>
   * <p>若作为母瓶入库，是否为留种入库。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String BP_NAME_KEEP = "keep";

  /**
   * <p>属性（<code>keep</code>）对应的数据库字段名（<code>keep</code>）。</p>
   * <p>若作为母瓶入库，是否为留种入库。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_KEEP = "keep";

  /**
   * <p>属性（<code>status</code>）的 JavaBean 属性名（<code>status</code>）。</p>
   * <p>出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_STATUS = "status";

  /**
   * <p>属性（<code>status</code>）对应的数据库字段名（<code>status</code>）。</p>
   * <p>出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_STATUS = "status";

  /**
   * <p>属性（<code>discardReason</code>）的 JavaBean 属性名（<code>discardReason</code>）。</p>
   * <p>淘汰原因</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_DISCARDREASON = "discardReason";

  /**
   * <p>属性（<code>discardReason</code>）对应的数据库字段名（<code>discard_reason</code>）。</p>
   * <p>淘汰原因</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DISCARDREASON = "discard_reason";

  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdInOut();

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getIdBottle();

  /**
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getVariety();

  /**
   * <p>子品种编号。主键</p>
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
   * <p>是否在统计中忽略该明细。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getIgnoreStats();

  /**
   * <p>培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out_bottle_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getInOutBottleType();

  /**
   * <p>瓶内株数（申请出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_applied</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantCountApplied();

  /**
   * <p>瓶内株数（实际出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlantCountActual();

  /**
   * <p>若作为母瓶入库，是否为留种入库。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  String getKeep();

  /**
   * <p>出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getStatus();

  /**
   * <p>淘汰原因</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getDiscardReason();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    String seperator = "\u001E";
    StringBuilder result = new StringBuilder();
    result.append(seperator).append(getIdInOut() == null ? "\u001F" : getIdInOut());
    result.append(seperator).append(getIdBottle() == null ? "\u001F" : getIdBottle());
    return result.toString();
  }
}
