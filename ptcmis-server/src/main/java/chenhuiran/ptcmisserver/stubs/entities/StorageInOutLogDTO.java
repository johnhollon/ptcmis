package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.StorageInOutLog} 接口。</p>
 * <p>出库/入库单操作日志</p>
 * <p>数据库表： <code>storage_in_out_log</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.StorageInOutLog}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class StorageInOutLogDTO implements Cloneable, StorageInOutLog {
  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String id;

  /**
   * <p>出入库ID. storage_in_out.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String idInOut;

  /**
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  private String status;

  /**
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String oppReason;

  /**
   * <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String operatorId;

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String operatorName;

  /**
   * <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  private Long operatorTime;

  /**
   * <p>无参构造方法。</p>
   * <p>出库/入库单操作日志</p>
   * <p>数据库表： <code>storage_in_out_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public StorageInOutLogDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>出库/入库单操作日志</p>
   * <p>数据库表： <code>storage_in_out_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param idInOut <p>出入库ID. storage_in_out.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param status <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   *
   * @param oppReason <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param operatorId <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorName <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param operatorTime <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   *
   */
  public StorageInOutLogDTO(String id, String idInOut, String status, String oppReason,
      String operatorId, String operatorName, Long operatorTime) {
    this.id = id;
    this.idInOut = idInOut;
    this.status = status;
    this.oppReason = oppReason;
    this.operatorId = operatorId;
    this.operatorName = operatorName;
    this.operatorTime = operatorTime;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>出库/入库单操作日志</p>
   * <p>数据库表： <code>storage_in_out_log</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public StorageInOutLogDTO(StorageInOutLog model) {
    this.id = model.getId();
    this.idInOut = model.getIdInOut();
    this.status = model.getStatus();
    this.oppReason = model.getOppReason();
    this.operatorId = model.getOperatorId();
    this.operatorName = model.getOperatorName();
    this.operatorTime = model.getOperatorTime();
  }

  /**
   * <p>ID主键</p>
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
   * <p>出入库ID. storage_in_out.id</p>
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
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  @Override
  public String getStatus() {
    return status;
  }

  /**
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getOppReason() {
    return oppReason;
  }

  /**
   * <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
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
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getOperatorName() {
    return operatorName;
  }

  /**
   * <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  @Override
  public Long getOperatorTime() {
    return operatorTime;
  }

  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>出入库ID. storage_in_out.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param idInOut <p>出入库ID. storage_in_out.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_in_out</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setIdInOut(String idInOut) {
    this.idInOut = idInOut;
  }

  /**
   * <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   * @param status <p>状态。 dictionary = storage_in_out_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "apply"</code></li>
   * </ul>
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param oppReason <p>操作原因。 驳回、作废原因（手工填写）</p>
   * <ul>
   *   <li>COLUMN : <code>opp_reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setOppReason(String oppReason) {
    this.oppReason = oppReason;
  }

  /**
   * <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorId <p>操作人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorId(String operatorId) {
    this.operatorId = operatorId;
  }

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param operatorName <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  /**
   * <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   * @param operatorTime <p>操作时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>operator_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  public void setOperatorTime(Long operatorTime) {
    this.operatorTime = operatorTime;
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
  public StorageInOutLogDTO cloneSimple() {
    try{
      return (StorageInOutLogDTO)clone();
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
    if(other instanceof StorageInOutLogDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.StorageInOutLog o) return fetchId().equals(o.fetchId());
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
