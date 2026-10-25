package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.CodePrintDetail} 接口。</p>
 * <p>瓶码打印记录明细</p>
 * <p>数据库表： <code>code_print_detail</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>idPrint</code>: 参见 {@link #getIdPrint()} 。</li>
 *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.CodePrintDetail}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class CodePrintDetailDTO implements Cloneable, CodePrintDetail {
  /**
   * <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String idPrint;

  /**
   * <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  private String code;

  /**
   * <p>瓶码信息：品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String varietyCode;

  /**
   * <p>瓶码信息：外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  private Integer explantCollectionBatch;

  /**
   * <p>瓶码信息：代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private Integer generation;

  /**
   * <p>瓶码信息：操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String operatorName;

  /**
   * <p>瓶码信息：建瓶时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private Long operateTime;

  /**
   * <p>无参构造方法。</p>
   * <p>瓶码打印记录明细</p>
   * <p>数据库表： <code>code_print_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idPrint</code>: 参见 {@link #getIdPrint()} 。</li>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   */
  public CodePrintDetailDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>瓶码打印记录明细</p>
   * <p>数据库表： <code>code_print_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idPrint</code>: 参见 {@link #getIdPrint()} 。</li>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   * @param idPrint <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param code <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   *
   * @param varietyCode <p>瓶码信息：品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param explantCollectionBatch <p>瓶码信息：外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   *
   * @param generation <p>瓶码信息：代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param operatorName <p>瓶码信息：操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param operateTime <p>瓶码信息：建瓶时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   */
  public CodePrintDetailDTO(String idPrint, String code, String varietyCode,
      Integer explantCollectionBatch, Integer generation, String operatorName, Long operateTime) {
    this.idPrint = idPrint;
    this.code = code;
    this.varietyCode = varietyCode;
    this.explantCollectionBatch = explantCollectionBatch;
    this.generation = generation;
    this.operatorName = operatorName;
    this.operateTime = operateTime;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>瓶码打印记录明细</p>
   * <p>数据库表： <code>code_print_detail</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>idPrint</code>: 参见 {@link #getIdPrint()} 。</li>
   *   <li>属性 <code>code</code>: 参见 {@link #getCode()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public CodePrintDetailDTO(CodePrintDetail model) {
    this.idPrint = model.getIdPrint();
    this.code = model.getCode();
    this.varietyCode = model.getVarietyCode();
    this.explantCollectionBatch = model.getExplantCollectionBatch();
    this.generation = model.getGeneration();
    this.operatorName = model.getOperatorName();
    this.operateTime = model.getOperateTime();
  }

  /**
   * <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getIdPrint() {
    return idPrint;
  }

  /**
   * <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  @Override
  public String getCode() {
    return code;
  }

  /**
   * <p>瓶码信息：品种编号（子品种）</p>
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
   * <p>瓶码信息：外植体采集批次</p>
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
   * <p>瓶码信息：代数</p>
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
   * <p>瓶码信息：操作人姓名，user.name</p>
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
   * <p>瓶码信息：建瓶时间 UTC 毫秒时间戳</p>
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
   * <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param idPrint <p>所属瓶码打印记录。 code_print.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_print</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setIdPrint(String idPrint) {
    this.idPrint = idPrint;
  }

  /**
   * <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   * @param code <p>瓶码</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(11), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * <p>瓶码信息：品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param varietyCode <p>瓶码信息：品种编号（子品种）</p>
   * <ul>
   *   <li>COLUMN : <code>variety_code</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setVarietyCode(String varietyCode) {
    this.varietyCode = varietyCode;
  }

  /**
   * <p>瓶码信息：外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   * @param explantCollectionBatch <p>瓶码信息：外植体采集批次</p>
   * <ul>
   *   <li>COLUMN : <code>explant_collection_batch</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  public void setExplantCollectionBatch(Integer explantCollectionBatch) {
    this.explantCollectionBatch = explantCollectionBatch;
  }

  /**
   * <p>瓶码信息：代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param generation <p>瓶码信息：代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setGeneration(Integer generation) {
    this.generation = generation;
  }

  /**
   * <p>瓶码信息：操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param operatorName <p>瓶码信息：操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setOperatorName(String operatorName) {
    this.operatorName = operatorName;
  }

  /**
   * <p>瓶码信息：建瓶时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param operateTime <p>瓶码信息：建瓶时间 UTC 毫秒时间戳</p>
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
  public CodePrintDetailDTO cloneSimple() {
    try{
      return (CodePrintDetailDTO)clone();
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
    if(other instanceof CodePrintDetailDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.CodePrintDetail o) return fetchId().equals(o.fetchId());
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
