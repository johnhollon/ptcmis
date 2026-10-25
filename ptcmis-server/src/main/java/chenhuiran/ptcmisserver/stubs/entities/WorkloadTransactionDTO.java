package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.WorkloadTransaction} 接口。</p>
 * <p>员工工作量流水（含淘汰量）</p>
 * <p>数据库表： <code>workload_transaction</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.WorkloadTransaction}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class WorkloadTransactionDTO implements Cloneable, WorkloadTransaction {
  /**
   * <p>主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String id;

  /**
   * <p>员工ID。user.id</p>
   * <ul>
   *   <li>COLUMN : <code>user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String userId;

  /**
   * <p>员工姓名。user.name</p>
   * <ul>
   *   <li>COLUMN : <code>user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String userName;

  /**
   * <p>员工身份，质检员还是操作员。dictionary.tissue_task_user_role</p>
   * <ul>
   *   <li>COLUMN : <code>user_role</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String userRole;

  /**
   * <p>业务类型(生产子瓶/母代留种/淘汰等)。dictionary.workload_tx_biz_type</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String bizType;

  /**
   * <p>来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String tableName;

  /**
   * <p>来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String recordId;

  /**
   * <p>品种</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String variety;

  /**
   * <p>品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String varietyCode;

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer generation;

  /**
   * <p>瓶数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer bottles;

  /**
   * <p>株数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer plants;

  /**
   * <p>备注信息</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String description;

  /**
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String operatorId;

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String operatorName;

  /**
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private Long operateTime;

  /**
   * <p>无参构造方法。</p>
   * <p>员工工作量流水（含淘汰量）</p>
   * <p>数据库表： <code>workload_transaction</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public WorkloadTransactionDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>员工工作量流水（含淘汰量）</p>
   * <p>数据库表： <code>workload_transaction</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param userId <p>员工ID。user.id</p>
   * <ul>
   *   <li>COLUMN : <code>user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param userName <p>员工姓名。user.name</p>
   * <ul>
   *   <li>COLUMN : <code>user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param userRole <p>员工身份，质检员还是操作员。dictionary.tissue_task_user_role</p>
   * <ul>
   *   <li>COLUMN : <code>user_role</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param bizType <p>业务类型(生产子瓶/母代留种/淘汰等)。dictionary.workload_tx_biz_type</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param tableName <p>来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param recordId <p>来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param variety <p>品种</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param varietyCode <p>品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param generation <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param bottles <p>瓶数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param plants <p>株数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param description <p>备注信息</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorId <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param operatorName <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param operateTime <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   */
  public WorkloadTransactionDTO(String id, String userId, String userName, String userRole,
      String bizType, String tableName, String recordId, String variety, String varietyCode,
      Integer generation, Integer bottles, Integer plants, String description, String operatorId,
      String operatorName, Long operateTime) {
    this.id = id;
    this.userId = userId;
    this.userName = userName;
    this.userRole = userRole;
    this.bizType = bizType;
    this.tableName = tableName;
    this.recordId = recordId;
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.generation = generation;
    this.bottles = bottles;
    this.plants = plants;
    this.description = description;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.operateTime = operateTime;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>员工工作量流水（含淘汰量）</p>
   * <p>数据库表： <code>workload_transaction</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public WorkloadTransactionDTO(WorkloadTransaction model) {
    this.id = model.getId();
    this.userId = model.getUserId();
    this.userName = model.getUserName();
    this.userRole = model.getUserRole();
    this.bizType = model.getBizType();
    this.tableName = model.getTableName();
    this.recordId = model.getRecordId();
    this.variety = model.getVariety();
    this.varietyCode = model.getVarietyCode();
    this.generation = model.getGeneration();
    this.bottles = model.getBottles();
    this.plants = model.getPlants();
    this.description = model.getDescription();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
    this.operateTime = model.getOperateTime();
  }

  /**
   * <p>主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * <p>员工ID。user.id</p>
   * <ul>
   *   <li>COLUMN : <code>user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getUserId() {
    return userId;
  }

  /**
   * <p>员工姓名。user.name</p>
   * <ul>
   *   <li>COLUMN : <code>user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getUserName() {
    return userName;
  }

  /**
   * <p>员工身份，质检员还是操作员。dictionary.tissue_task_user_role</p>
   * <ul>
   *   <li>COLUMN : <code>user_role</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getUserRole() {
    return userRole;
  }

  /**
   * <p>业务类型(生产子瓶/母代留种/淘汰等)。dictionary.workload_tx_biz_type</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getBizType() {
    return bizType;
  }

  /**
   * <p>来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getTableName() {
    return tableName;
  }

  /**
   * <p>来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getRecordId() {
    return recordId;
  }

  /**
   * <p>品种</p>
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
   * <p>品种编号（子品种）</p>
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
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Integer getGeneration() {
    return generation;
  }

  /**
   * <p>瓶数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getBottles() {
    return bottles;
  }

  /**
   * <p>株数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlants() {
    return plants;
  }

  /**
   * <p>备注信息</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getOperatorId() {
    return operatorId;
  }

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getOperatorName() {
    return operatorName;
  }

  /**
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Long getOperateTime() {
    return operateTime;
  }

  /**
   * <p>主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param id <p>主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>员工ID。user.id</p>
   * <ul>
   *   <li>COLUMN : <code>user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param userId <p>员工ID。user.id</p>
   * <ul>
   *   <li>COLUMN : <code>user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * <p>员工姓名。user.name</p>
   * <ul>
   *   <li>COLUMN : <code>user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param userName <p>员工姓名。user.name</p>
   * <ul>
   *   <li>COLUMN : <code>user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * <p>员工身份，质检员还是操作员。dictionary.tissue_task_user_role</p>
   * <ul>
   *   <li>COLUMN : <code>user_role</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param userRole <p>员工身份，质检员还是操作员。dictionary.tissue_task_user_role</p>
   * <ul>
   *   <li>COLUMN : <code>user_role</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  /**
   * <p>业务类型(生产子瓶/母代留种/淘汰等)。dictionary.workload_tx_biz_type</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param bizType <p>业务类型(生产子瓶/母代留种/淘汰等)。dictionary.workload_tx_biz_type</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setBizType(String bizType) {
    this.bizType = bizType;
  }

  /**
   * <p>来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param tableName <p>来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * <p>来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param recordId <p>来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  /**
   * <p>品种</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param variety <p>品种</p>
   * <ul>
   *   <li>COLUMN : <code>variety</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVariety(String variety) {
    this.variety = variety;
  }

  /**
   * <p>品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param varietyCode <p>品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVarietyCode(String varietyCode) {
    this.varietyCode = varietyCode;
  }

  /**
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param generation <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setGeneration(Integer generation) {
    this.generation = generation;
  }

  /**
   * <p>瓶数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param bottles <p>瓶数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setBottles(Integer bottles) {
    this.bottles = bottles;
  }

  /**
   * <p>株数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plants <p>株数，正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlants(Integer plants) {
    this.plants = plants;
  }

  /**
   * <p>备注信息</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param description <p>备注信息</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param operatorId <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param operatorName <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  /**
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param operateTime <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setOperateTime(Long operateTime) {
    this.operateTime = operateTime;
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
  public WorkloadTransactionDTO cloneSimple() {
    try{
      return (WorkloadTransactionDTO)clone();
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
    if(other instanceof WorkloadTransactionDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.WorkloadTransaction o) return fetchId().equals(o.fetchId());
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
