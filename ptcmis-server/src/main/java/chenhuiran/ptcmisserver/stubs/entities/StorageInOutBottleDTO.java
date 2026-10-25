package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.StorageInOutBottle} 接口。</p>
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
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.StorageInOutBottle}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class StorageInOutBottleDTO implements Cloneable, StorageInOutBottle {
  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String idInOut;

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String idBottle;

  /**
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String variety;

  /**
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String varietyCode;

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer explantCollectionBatch;

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer generation;

  /**
   * <p>是否在统计中忽略该明细。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  private String ignoreStats;

  /**
   * <p>培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out_bottle_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String inOutBottleType;

  /**
   * <p>瓶内株数（申请出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_applied</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer plantCountApplied;

  /**
   * <p>瓶内株数（实际出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer plantCountActual;

  /**
   * <p>若作为母瓶入库，是否为留种入库。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  private String keep;

  /**
   * <p>出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String status;

  /**
   * <p>淘汰原因</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String discardReason;

  /**
   * <p>无参构造方法。</p>
   * <p>出/入库单明细</p>
   * <p>数据库表： <code>storage_in_out_bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idInOut</code>: 参见 {@link #getIdInOut()} 。</li>
   *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
   * </ul>
   */
  public StorageInOutBottleDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>出/入库单明细</p>
   * <p>数据库表： <code>storage_in_out_bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idInOut</code>: 参见 {@link #getIdInOut()} 。</li>
   *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
   * </ul>
   * @param idInOut <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idBottle <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param variety <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param varietyCode <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param explantCollectionBatch <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param generation <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param ignoreStats <p>是否在统计中忽略该明细。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param inOutBottleType <p>培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out_bottle_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param plantCountApplied <p>瓶内株数（申请出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_applied</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plantCountActual <p>瓶内株数（实际出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param keep <p>若作为母瓶入库，是否为留种入库。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param status <p>出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param discardReason <p>淘汰原因</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   */
  public StorageInOutBottleDTO(String idInOut, String idBottle, String variety, String varietyCode,
      Integer explantCollectionBatch, Integer generation, String ignoreStats,
      String inOutBottleType, Integer plantCountApplied, Integer plantCountActual, String keep,
      String status, String discardReason) {
    this.idInOut = idInOut;
    this.idBottle = idBottle;
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.explantCollectionBatch = explantCollectionBatch;
    this.generation = generation;
    this.ignoreStats = ignoreStats;
    this.inOutBottleType = inOutBottleType;
    this.plantCountApplied = plantCountApplied;
    this.plantCountActual = plantCountActual;
    this.keep = keep;
    this.status = status;
    this.discardReason = discardReason;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>出/入库单明细</p>
   * <p>数据库表： <code>storage_in_out_bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idInOut</code>: 参见 {@link #getIdInOut()} 。</li>
   *   <li>属性 <code>idBottle</code>: 参见 {@link #getIdBottle()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public StorageInOutBottleDTO(StorageInOutBottle model) {
    this.idInOut = model.getIdInOut();
    this.idBottle = model.getIdBottle();
    this.variety = model.getVariety();
    this.varietyCode = model.getVarietyCode();
    this.explantCollectionBatch = model.getExplantCollectionBatch();
    this.generation = model.getGeneration();
    this.ignoreStats = model.getIgnoreStats();
    this.inOutBottleType = model.getInOutBottleType();
    this.plantCountApplied = model.getPlantCountApplied();
    this.plantCountActual = model.getPlantCountActual();
    this.keep = model.getKeep();
    this.status = model.getStatus();
    this.discardReason = model.getDiscardReason();
  }

  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getIdInOut() {
    return idInOut;
  }

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdBottle() {
    return idBottle;
  }

  /**
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getVariety() {
    return variety;
  }

  /**
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getVarietyCode() {
    return varietyCode;
  }

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getExplantCollectionBatch() {
    return explantCollectionBatch;
  }

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getGeneration() {
    return generation;
  }

  /**
   * <p>是否在统计中忽略该明细。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getIgnoreStats() {
    return ignoreStats;
  }

  /**
   * <p>培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out_bottle_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getInOutBottleType() {
    return inOutBottleType;
  }

  /**
   * <p>瓶内株数（申请出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_applied</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantCountApplied() {
    return plantCountApplied;
  }

  /**
   * <p>瓶内株数（实际出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantCountActual() {
    return plantCountActual;
  }

  /**
   * <p>若作为母瓶入库，是否为留种入库。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getKeep() {
    return keep;
  }

  /**
   * <p>出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getStatus() {
    return status;
  }

  /**
   * <p>淘汰原因</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getDiscardReason() {
    return discardReason;
  }

  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param idInOut <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setIdInOut(String idInOut) {
    this.idInOut = idInOut;
  }

  /**
   * <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idBottle <p>瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引</p>
   * <ul>
   *   <li>COLUMN : <code>id_bottle</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdBottle(String idBottle) {
    this.idBottle = idBottle;
  }

  /**
   * <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param variety <p>所属品种。variety.id</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVariety(String variety) {
    this.variety = variety;
  }

  /**
   * <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param varietyCode <p>子品种编号。主键</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVarietyCode(String varietyCode) {
    this.varietyCode = varietyCode;
  }

  /**
   * <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param explantCollectionBatch <p>外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setExplantCollectionBatch(Integer explantCollectionBatch) {
    this.explantCollectionBatch = explantCollectionBatch;
  }

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param generation <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setGeneration(Integer generation) {
    this.generation = generation;
  }

  /**
   * <p>是否在统计中忽略该明细。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param ignoreStats <p>是否在统计中忽略该明细。 dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>ignore_stats</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setIgnoreStats(String ignoreStats) {
    this.ignoreStats = ignoreStats;
  }

  /**
   * <p>培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out_bottle_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param inOutBottleType <p>培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type</p>
   * <ul>
   *   <li>COLUMN : <code>in_out_bottle_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setInOutBottleType(String inOutBottleType) {
    this.inOutBottleType = inOutBottleType;
  }

  /**
   * <p>瓶内株数（申请出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_applied</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantCountApplied <p>瓶内株数（申请出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_applied</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantCountApplied(Integer plantCountApplied) {
    this.plantCountApplied = plantCountApplied;
  }

  /**
   * <p>瓶内株数（实际出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantCountActual <p>瓶内株数（实际出入库数量，均记正数）</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count_actual</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantCountActual(Integer plantCountActual) {
    this.plantCountActual = plantCountActual;
  }

  /**
   * <p>若作为母瓶入库，是否为留种入库。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param keep <p>若作为母瓶入库，是否为留种入库。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setKeep(String keep) {
    this.keep = keep;
  }

  /**
   * <p>出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param status <p>出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * <p>淘汰原因</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param discardReason <p>淘汰原因</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setDiscardReason(String discardReason) {
    this.discardReason = discardReason;
  }

  /**
   * <p>通过 {@link #fetchId()} 的值计算哈希。</p>
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return fetchId().hashCode();
  }

  /**
   * 简单克隆。
   */
  @SuppressWarnings("UseSpecificCatch")
  public StorageInOutBottleDTO cloneSimple() {
    try{
      return (StorageInOutBottleDTO)clone();
    } catch (Throwable ignored) {
      return null;
    }
  }

  /**
   * <p>通过 {@link #fetchId()} 的值比较两个对象是否相等（支持传入字符串进行比较）。</p>
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("UseSpecificCatch")
  public boolean equals(Object other) {
    if(other == null) return false;
    if(other instanceof String s) return fetchId().equals(s);
    if(other instanceof StorageInOutBottleDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.StorageInOutBottle o) return fetchId().equals(o.fetchId());
    try{
      java.lang.reflect.Method fetchIdMethod = other.getClass().getMethod("fetchId");
      Object o = fetchIdMethod.invoke(other);
      if(o == null) return false;
      return fetchId().equals(o);
    } catch (Throwable ignored) {
      return false;
    }
  }
}
