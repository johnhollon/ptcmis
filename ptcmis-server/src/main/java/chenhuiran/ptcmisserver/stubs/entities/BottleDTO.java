package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.Bottle} 接口。</p>
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
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.Bottle}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class BottleDTO implements Cloneable, Bottle {
  /**
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String id;

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
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer plantCount;

  /**
   * <p>形成该瓶的任务执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>task_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long taskTime;

  /**
   * <p>产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String sourceType;

  /**
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String idParent;

  /**
   * <p>母瓶代数</p>
   * <ul>
   *   <li>COLUMN : <code>parent_generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer parentGeneration;

  /**
   * <p>当前所在任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentIdTask;

  /**
   * <p>当前操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentIdOppGroup;

  /**
   * <p>当前工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>current_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentCultureStage;

  /**
   * <p>当前任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>current_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentRoleInTask;

  /**
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  private String currentParentKeep;

  /**
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer currentParentKeepPlantCount;

  /**
   * <p>当前操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentOperateStatus;

  /**
   * <p>当前质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentInspectorId;

  /**
   * <p>当前质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentInspectorName;

  /**
   * <p>当前操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentOperatorId;

  /**
   * <p>当前操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentOperatorName;

  /**
   * <p>末次任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastIdTask;

  /**
   * <p>末次操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastIdOppGroup;

  /**
   * <p>末次工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>last_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastCultureStage;

  /**
   * <p>末次任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>last_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastRoleInTask;

  /**
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  private String lastParentKeep;

  /**
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer lastParentKeepPlantCount;

  /**
   * <p>末次操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>last_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastOperateStatus;

  /**
   * <p>末次质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastInspectorId;

  /**
   * <p>末次质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastInspectorName;

  /**
   * <p>末次操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastOperatorId;

  /**
   * <p>末次操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastOperatorName;

  /**
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String currentStorageStatus;

  /**
   * <p>末次入库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastReceivedUserId;

  /**
   * <p>末次入库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastReceivedUserName;

  /**
   * <p>末次入库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long lastReceivedTime;

  /**
   * <p>末次入库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastReceivedReason;

  /**
   * <p>末次出库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastIssuedUserId;

  /**
   * <p>末次出库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastIssuedUserName;

  /**
   * <p>末次出库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long lastIssuedTime;

  /**
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String lastIssuedReason;

  /**
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  private String discarded;

  /**
   * <p>是否计入绩效。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>performance_included</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  private String performanceIncluded;

  /**
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String discardReason;

  /**
   * <p>淘汰备注</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason_desc</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String discardReasonDesc;

  /**
   * <p>淘汰时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>discard_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long discardTime;

  /**
   * <p>淘汰执行人ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String discardUserId;

  /**
   * <p>淘汰执行人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String discardUserName;

  /**
   * <p>无参构造方法。</p>
   * <p>组培瓶</p>
   * <p>数据库表： <code>bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public BottleDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>组培瓶</p>
   * <p>数据库表： <code>bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
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
   * @param plantCount <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param taskTime <p>形成该瓶的任务执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>task_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param sourceType <p>产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param idParent <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param parentGeneration <p>母瓶代数</p>
   * <ul>
   *   <li>COLUMN : <code>parent_generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param currentIdTask <p>当前所在任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentIdOppGroup <p>当前操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentCultureStage <p>当前工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>current_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentRoleInTask <p>当前任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>current_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentParentKeep <p>当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param currentParentKeepPlantCount <p>当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param currentOperateStatus <p>当前操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentInspectorId <p>当前质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentInspectorName <p>当前质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentOperatorId <p>当前操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentOperatorName <p>当前操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastIdTask <p>末次任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastIdOppGroup <p>末次操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastCultureStage <p>末次工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>last_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastRoleInTask <p>末次任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>last_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastParentKeep <p>末次本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param lastParentKeepPlantCount <p>末次本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param lastOperateStatus <p>末次操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>last_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastInspectorId <p>末次质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastInspectorName <p>末次质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastOperatorId <p>末次操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastOperatorName <p>末次操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param currentStorageStatus <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastReceivedUserId <p>末次入库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastReceivedUserName <p>末次入库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastReceivedTime <p>末次入库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param lastReceivedReason <p>末次入库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastIssuedUserId <p>末次出库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastIssuedUserName <p>末次出库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param lastIssuedTime <p>末次出库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param lastIssuedReason <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param discarded <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   *
   * @param performanceIncluded <p>是否计入绩效。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>performance_included</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   *
   * @param discardReason <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param discardReasonDesc <p>淘汰备注</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason_desc</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param discardTime <p>淘汰时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>discard_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   * @param discardUserId <p>淘汰执行人ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param discardUserName <p>淘汰执行人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   */
  public BottleDTO(String id, String variety, String varietyCode, Integer explantCollectionBatch,
      Integer generation, Integer plantCount, Long taskTime, String sourceType, String idParent,
      Integer parentGeneration, String currentIdTask, String currentIdOppGroup,
      String currentCultureStage, String currentRoleInTask, String currentParentKeep,
      Integer currentParentKeepPlantCount, String currentOperateStatus, String currentInspectorId,
      String currentInspectorName, String currentOperatorId, String currentOperatorName,
      String lastIdTask, String lastIdOppGroup, String lastCultureStage, String lastRoleInTask,
      String lastParentKeep, Integer lastParentKeepPlantCount, String lastOperateStatus,
      String lastInspectorId, String lastInspectorName, String lastOperatorId,
      String lastOperatorName, String currentStorageStatus, String lastReceivedUserId,
      String lastReceivedUserName, Long lastReceivedTime, String lastReceivedReason,
      String lastIssuedUserId, String lastIssuedUserName, Long lastIssuedTime,
      String lastIssuedReason, String discarded, String performanceIncluded, String discardReason,
      String discardReasonDesc, Long discardTime, String discardUserId, String discardUserName) {
    this.id = id;
    this.variety = variety;
    this.varietyCode = varietyCode;
    this.explantCollectionBatch = explantCollectionBatch;
    this.generation = generation;
    this.plantCount = plantCount;
    this.taskTime = taskTime;
    this.sourceType = sourceType;
    this.idParent = idParent;
    this.parentGeneration = parentGeneration;
    this.currentIdTask = currentIdTask;
    this.currentIdOppGroup = currentIdOppGroup;
    this.currentCultureStage = currentCultureStage;
    this.currentRoleInTask = currentRoleInTask;
    this.currentParentKeep = currentParentKeep;
    this.currentParentKeepPlantCount = currentParentKeepPlantCount;
    this.currentOperateStatus = currentOperateStatus;
    this.currentInspectorId = currentInspectorId;
    this.currentInspectorName = currentInspectorName;
    this.currentOperatorId = currentOperatorId;
    this.currentOperatorName = currentOperatorName;
    this.lastIdTask = lastIdTask;
    this.lastIdOppGroup = lastIdOppGroup;
    this.lastCultureStage = lastCultureStage;
    this.lastRoleInTask = lastRoleInTask;
    this.lastParentKeep = lastParentKeep;
    this.lastParentKeepPlantCount = lastParentKeepPlantCount;
    this.lastOperateStatus = lastOperateStatus;
    this.lastInspectorId = lastInspectorId;
    this.lastInspectorName = lastInspectorName;
    this.lastOperatorId = lastOperatorId;
    this.lastOperatorName = lastOperatorName;
    this.currentStorageStatus = currentStorageStatus;
    this.lastReceivedUserId = lastReceivedUserId;
    this.lastReceivedUserName = lastReceivedUserName;
    this.lastReceivedTime = lastReceivedTime;
    this.lastReceivedReason = lastReceivedReason;
    this.lastIssuedUserId = lastIssuedUserId;
    this.lastIssuedUserName = lastIssuedUserName;
    this.lastIssuedTime = lastIssuedTime;
    this.lastIssuedReason = lastIssuedReason;
    this.discarded = discarded;
    this.performanceIncluded = performanceIncluded;
    this.discardReason = discardReason;
    this.discardReasonDesc = discardReasonDesc;
    this.discardTime = discardTime;
    this.discardUserId = discardUserId;
    this.discardUserName = discardUserName;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>组培瓶</p>
   * <p>数据库表： <code>bottle</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public BottleDTO(Bottle model) {
    this.id = model.getId();
    this.variety = model.getVariety();
    this.varietyCode = model.getVarietyCode();
    this.explantCollectionBatch = model.getExplantCollectionBatch();
    this.generation = model.getGeneration();
    this.plantCount = model.getPlantCount();
    this.taskTime = model.getTaskTime();
    this.sourceType = model.getSourceType();
    this.idParent = model.getIdParent();
    this.parentGeneration = model.getParentGeneration();
    this.currentIdTask = model.getCurrentIdTask();
    this.currentIdOppGroup = model.getCurrentIdOppGroup();
    this.currentCultureStage = model.getCurrentCultureStage();
    this.currentRoleInTask = model.getCurrentRoleInTask();
    this.currentParentKeep = model.getCurrentParentKeep();
    this.currentParentKeepPlantCount = model.getCurrentParentKeepPlantCount();
    this.currentOperateStatus = model.getCurrentOperateStatus();
    this.currentInspectorId = model.getCurrentInspectorId();
    this.currentInspectorName = model.getCurrentInspectorName();
    this.currentOperatorId = model.getCurrentOperatorId();
    this.currentOperatorName = model.getCurrentOperatorName();
    this.lastIdTask = model.getLastIdTask();
    this.lastIdOppGroup = model.getLastIdOppGroup();
    this.lastCultureStage = model.getLastCultureStage();
    this.lastRoleInTask = model.getLastRoleInTask();
    this.lastParentKeep = model.getLastParentKeep();
    this.lastParentKeepPlantCount = model.getLastParentKeepPlantCount();
    this.lastOperateStatus = model.getLastOperateStatus();
    this.lastInspectorId = model.getLastInspectorId();
    this.lastInspectorName = model.getLastInspectorName();
    this.lastOperatorId = model.getLastOperatorId();
    this.lastOperatorName = model.getLastOperatorName();
    this.currentStorageStatus = model.getCurrentStorageStatus();
    this.lastReceivedUserId = model.getLastReceivedUserId();
    this.lastReceivedUserName = model.getLastReceivedUserName();
    this.lastReceivedTime = model.getLastReceivedTime();
    this.lastReceivedReason = model.getLastReceivedReason();
    this.lastIssuedUserId = model.getLastIssuedUserId();
    this.lastIssuedUserName = model.getLastIssuedUserName();
    this.lastIssuedTime = model.getLastIssuedTime();
    this.lastIssuedReason = model.getLastIssuedReason();
    this.discarded = model.getDiscarded();
    this.performanceIncluded = model.getPerformanceIncluded();
    this.discardReason = model.getDiscardReason();
    this.discardReasonDesc = model.getDiscardReasonDesc();
    this.discardTime = model.getDiscardTime();
    this.discardUserId = model.getDiscardUserId();
    this.discardUserName = model.getDiscardUserName();
  }

  /**
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getId() {
    return id;
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
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getPlantCount() {
    return plantCount;
  }

  /**
   * <p>形成该瓶的任务执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>task_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getTaskTime() {
    return taskTime;
  }

  /**
   * <p>产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getSourceType() {
    return sourceType;
  }

  /**
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdParent() {
    return idParent;
  }

  /**
   * <p>母瓶代数</p>
   * <ul>
   *   <li>COLUMN : <code>parent_generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getParentGeneration() {
    return parentGeneration;
  }

  /**
   * <p>当前所在任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentIdTask() {
    return currentIdTask;
  }

  /**
   * <p>当前操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentIdOppGroup() {
    return currentIdOppGroup;
  }

  /**
   * <p>当前工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>current_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentCultureStage() {
    return currentCultureStage;
  }

  /**
   * <p>当前任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>current_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentRoleInTask() {
    return currentRoleInTask;
  }

  /**
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getCurrentParentKeep() {
    return currentParentKeep;
  }

  /**
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getCurrentParentKeepPlantCount() {
    return currentParentKeepPlantCount;
  }

  /**
   * <p>当前操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentOperateStatus() {
    return currentOperateStatus;
  }

  /**
   * <p>当前质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentInspectorId() {
    return currentInspectorId;
  }

  /**
   * <p>当前质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentInspectorName() {
    return currentInspectorName;
  }

  /**
   * <p>当前操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentOperatorId() {
    return currentOperatorId;
  }

  /**
   * <p>当前操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentOperatorName() {
    return currentOperatorName;
  }

  /**
   * <p>末次任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastIdTask() {
    return lastIdTask;
  }

  /**
   * <p>末次操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastIdOppGroup() {
    return lastIdOppGroup;
  }

  /**
   * <p>末次工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>last_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastCultureStage() {
    return lastCultureStage;
  }

  /**
   * <p>末次任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>last_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastRoleInTask() {
    return lastRoleInTask;
  }

  /**
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getLastParentKeep() {
    return lastParentKeep;
  }

  /**
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  @Override
  public Integer getLastParentKeepPlantCount() {
    return lastParentKeepPlantCount;
  }

  /**
   * <p>末次操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>last_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastOperateStatus() {
    return lastOperateStatus;
  }

  /**
   * <p>末次质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastInspectorId() {
    return lastInspectorId;
  }

  /**
   * <p>末次质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastInspectorName() {
    return lastInspectorName;
  }

  /**
   * <p>末次操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastOperatorId() {
    return lastOperatorId;
  }

  /**
   * <p>末次操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastOperatorName() {
    return lastOperatorName;
  }

  /**
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCurrentStorageStatus() {
    return currentStorageStatus;
  }

  /**
   * <p>末次入库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastReceivedUserId() {
    return lastReceivedUserId;
  }

  /**
   * <p>末次入库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastReceivedUserName() {
    return lastReceivedUserName;
  }

  /**
   * <p>末次入库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getLastReceivedTime() {
    return lastReceivedTime;
  }

  /**
   * <p>末次入库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastReceivedReason() {
    return lastReceivedReason;
  }

  /**
   * <p>末次出库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastIssuedUserId() {
    return lastIssuedUserId;
  }

  /**
   * <p>末次出库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastIssuedUserName() {
    return lastIssuedUserName;
  }

  /**
   * <p>末次出库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getLastIssuedTime() {
    return lastIssuedTime;
  }

  /**
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getLastIssuedReason() {
    return lastIssuedReason;
  }

  /**
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  @Override
  public String getDiscarded() {
    return discarded;
  }

  /**
   * <p>是否计入绩效。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>performance_included</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  @Override
  public String getPerformanceIncluded() {
    return performanceIncluded;
  }

  /**
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getDiscardReason() {
    return discardReason;
  }

  /**
   * <p>淘汰备注</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason_desc</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getDiscardReasonDesc() {
    return discardReasonDesc;
  }

  /**
   * <p>淘汰时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>discard_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getDiscardTime() {
    return discardTime;
  }

  /**
   * <p>淘汰执行人ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getDiscardUserId() {
    return discardUserId;
  }

  /**
   * <p>淘汰执行人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getDiscardUserName() {
    return discardUserName;
  }

  /**
   * <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>瓶码ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
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
   * <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param plantCount <p>瓶内株数</p>
   * <ul>
   *   <li>COLUMN : <code>plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setPlantCount(Integer plantCount) {
    this.plantCount = plantCount;
  }

  /**
   * <p>形成该瓶的任务执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>task_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param taskTime <p>形成该瓶的任务执行时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>task_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setTaskTime(Long taskTime) {
    this.taskTime = taskTime;
  }

  /**
   * <p>产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param sourceType <p>产生来源，培养瓶产生于哪个业务。dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>source_type</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }

  /**
   * <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idParent <p>母瓶ID，空串代表母瓶为系统上线前的母瓶</p>
   * <ul>
   *   <li>COLUMN : <code>id_parent</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdParent(String idParent) {
    this.idParent = idParent;
  }

  /**
   * <p>母瓶代数</p>
   * <ul>
   *   <li>COLUMN : <code>parent_generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param parentGeneration <p>母瓶代数</p>
   * <ul>
   *   <li>COLUMN : <code>parent_generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setParentGeneration(Integer parentGeneration) {
    this.parentGeneration = parentGeneration;
  }

  /**
   * <p>当前所在任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentIdTask <p>当前所在任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentIdTask(String currentIdTask) {
    this.currentIdTask = currentIdTask;
  }

  /**
   * <p>当前操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentIdOppGroup <p>当前操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentIdOppGroup(String currentIdOppGroup) {
    this.currentIdOppGroup = currentIdOppGroup;
  }

  /**
   * <p>当前工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>current_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentCultureStage <p>当前工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>current_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentCultureStage(String currentCultureStage) {
    this.currentCultureStage = currentCultureStage;
  }

  /**
   * <p>当前任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>current_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentRoleInTask <p>当前任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>current_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentRoleInTask(String currentRoleInTask) {
    this.currentRoleInTask = currentRoleInTask;
  }

  /**
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param currentParentKeep <p>当前本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setCurrentParentKeep(String currentParentKeep) {
    this.currentParentKeep = currentParentKeep;
  }

  /**
   * <p>当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param currentParentKeepPlantCount <p>当前本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>current_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setCurrentParentKeepPlantCount(Integer currentParentKeepPlantCount) {
    this.currentParentKeepPlantCount = currentParentKeepPlantCount;
  }

  /**
   * <p>当前操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentOperateStatus <p>当前操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentOperateStatus(String currentOperateStatus) {
    this.currentOperateStatus = currentOperateStatus;
  }

  /**
   * <p>当前质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentInspectorId <p>当前质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentInspectorId(String currentInspectorId) {
    this.currentInspectorId = currentInspectorId;
  }

  /**
   * <p>当前质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentInspectorName <p>当前质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentInspectorName(String currentInspectorName) {
    this.currentInspectorName = currentInspectorName;
  }

  /**
   * <p>当前操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentOperatorId <p>当前操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentOperatorId(String currentOperatorId) {
    this.currentOperatorId = currentOperatorId;
  }

  /**
   * <p>当前操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentOperatorName <p>当前操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>current_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentOperatorName(String currentOperatorName) {
    this.currentOperatorName = currentOperatorName;
  }

  /**
   * <p>末次任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastIdTask <p>末次任务ID。 tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastIdTask(String lastIdTask) {
    this.lastIdTask = lastIdTask;
  }

  /**
   * <p>末次操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastIdOppGroup <p>末次操作分配组ID。 tissue_task_opp_group.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_id_opp_group</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastIdOppGroup(String lastIdOppGroup) {
    this.lastIdOppGroup = lastIdOppGroup;
  }

  /**
   * <p>末次工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>last_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastCultureStage <p>末次工序。 dictionary.tissue_task_type</p>
   * <ul>
   *   <li>COLUMN : <code>last_culture_stage</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastCultureStage(String lastCultureStage) {
    this.lastCultureStage = lastCultureStage;
  }

  /**
   * <p>末次任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>last_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastRoleInTask <p>末次任务角色。 dictionary.bottle_task_role</p>
   * <ul>
   *   <li>COLUMN : <code>last_role_in_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastRoleInTask(String lastRoleInTask) {
    this.lastRoleInTask = lastRoleInTask;
  }

  /**
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param lastParentKeep <p>末次本瓶作为母代的留种情况：若作为母瓶使用，是否留种。 dictionary.boolean</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setLastParentKeep(String lastParentKeep) {
    this.lastParentKeep = lastParentKeep;
  }

  /**
   * <p>末次本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param lastParentKeepPlantCount <p>末次本瓶作为母代的留种情况：若作为母瓶使用，且留种，留种株数。</p>
   * <ul>
   *   <li>COLUMN : <code>last_parent_keep_plant_count</code></li>
   *   <li>DDL : <code>SMALLINT(5), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setLastParentKeepPlantCount(Integer lastParentKeepPlantCount) {
    this.lastParentKeepPlantCount = lastParentKeepPlantCount;
  }

  /**
   * <p>末次操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>last_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastOperateStatus <p>末次操作状态。 dictionary.bottle_operate_status</p>
   * <ul>
   *   <li>COLUMN : <code>last_operate_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastOperateStatus(String lastOperateStatus) {
    this.lastOperateStatus = lastOperateStatus;
  }

  /**
   * <p>末次质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastInspectorId <p>末次质检员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastInspectorId(String lastInspectorId) {
    this.lastInspectorId = lastInspectorId;
  }

  /**
   * <p>末次质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastInspectorName <p>末次质检员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_inspector_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastInspectorName(String lastInspectorName) {
    this.lastInspectorName = lastInspectorName;
  }

  /**
   * <p>末次操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastOperatorId <p>末次操作员ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastOperatorId(String lastOperatorId) {
    this.lastOperatorId = lastOperatorId;
  }

  /**
   * <p>末次操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastOperatorName <p>末次操作员姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastOperatorName(String lastOperatorName) {
    this.lastOperatorName = lastOperatorName;
  }

  /**
   * <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param currentStorageStatus <p>仓储状态。 dictionary.bottle_storage_status</p>
   * <ul>
   *   <li>COLUMN : <code>current_storage_status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCurrentStorageStatus(String currentStorageStatus) {
    this.currentStorageStatus = currentStorageStatus;
  }

  /**
   * <p>末次入库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastReceivedUserId <p>末次入库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastReceivedUserId(String lastReceivedUserId) {
    this.lastReceivedUserId = lastReceivedUserId;
  }

  /**
   * <p>末次入库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastReceivedUserName <p>末次入库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastReceivedUserName(String lastReceivedUserName) {
    this.lastReceivedUserName = lastReceivedUserName;
  }

  /**
   * <p>末次入库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param lastReceivedTime <p>末次入库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setLastReceivedTime(Long lastReceivedTime) {
    this.lastReceivedTime = lastReceivedTime;
  }

  /**
   * <p>末次入库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastReceivedReason <p>末次入库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_received_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastReceivedReason(String lastReceivedReason) {
    this.lastReceivedReason = lastReceivedReason;
  }

  /**
   * <p>末次出库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastIssuedUserId <p>末次出库用户ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastIssuedUserId(String lastIssuedUserId) {
    this.lastIssuedUserId = lastIssuedUserId;
  }

  /**
   * <p>末次出库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastIssuedUserName <p>末次出库人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastIssuedUserName(String lastIssuedUserName) {
    this.lastIssuedUserName = lastIssuedUserName;
  }

  /**
   * <p>末次出库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param lastIssuedTime <p>末次出库时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setLastIssuedTime(Long lastIssuedTime) {
    this.lastIssuedTime = lastIssuedTime;
  }

  /**
   * <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param lastIssuedReason <p>末次出库原因。 dictionary.storage_reason</p>
   * <ul>
   *   <li>COLUMN : <code>last_issued_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setLastIssuedReason(String lastIssuedReason) {
    this.lastIssuedReason = lastIssuedReason;
  }

  /**
   * <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   * @param discarded <p>是否淘汰。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>discarded</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "0"</code></li>
   * </ul>
   */
  public void setDiscarded(String discarded) {
    this.discarded = discarded;
  }

  /**
   * <p>是否计入绩效。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>performance_included</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   * @param performanceIncluded <p>是否计入绩效。dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>performance_included</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  public void setPerformanceIncluded(String performanceIncluded) {
    this.performanceIncluded = performanceIncluded;
  }

  /**
   * <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param discardReason <p>淘汰原因。 dictionary.bottle_discard_reason</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setDiscardReason(String discardReason) {
    this.discardReason = discardReason;
  }

  /**
   * <p>淘汰备注</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason_desc</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param discardReasonDesc <p>淘汰备注</p>
   * <ul>
   *   <li>COLUMN : <code>discard_reason_desc</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setDiscardReasonDesc(String discardReasonDesc) {
    this.discardReasonDesc = discardReasonDesc;
  }

  /**
   * <p>淘汰时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>discard_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param discardTime <p>淘汰时间 UTC毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>discard_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setDiscardTime(Long discardTime) {
    this.discardTime = discardTime;
  }

  /**
   * <p>淘汰执行人ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param discardUserId <p>淘汰执行人ID。 user.id</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setDiscardUserId(String discardUserId) {
    this.discardUserId = discardUserId;
  }

  /**
   * <p>淘汰执行人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param discardUserName <p>淘汰执行人姓名。 user.name</p>
   * <ul>
   *   <li>COLUMN : <code>discard_user_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setDiscardUserName(String discardUserName) {
    this.discardUserName = discardUserName;
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
  public BottleDTO cloneSimple() {
    try{
      return (BottleDTO)clone();
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
    if(other instanceof BottleDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.Bottle o) return fetchId().equals(o.fetchId());
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
