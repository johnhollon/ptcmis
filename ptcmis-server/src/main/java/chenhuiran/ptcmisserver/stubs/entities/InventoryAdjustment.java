package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>盘点调差</p>
 * <p>数据库表： <code>inventory_adjustment</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface InventoryAdjustment extends Serializable {
  /**
   * <p>表名（ <code>inventory_adjustment</code> ）。</p>
   * 盘点调差
   */
  String TB_NAME = "inventory_adjustment";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>调差ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>调差ID</p>
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
   * <p>属性（<code>status</code>）的 JavaBean 属性名（<code>status</code>）。</p>
   * <p>任务状态：dictionary.inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String BP_NAME_STATUS = "status";

  /**
   * <p>属性（<code>status</code>）对应的数据库字段名（<code>status</code>）。</p>
   * <p>任务状态：dictionary.inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_STATUS = "status";

  /**
   * <p>属性（<code>bottles</code>）的 JavaBean 属性名（<code>bottles</code>）。</p>
   * <p>调差瓶数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_BOTTLES = "bottles";

  /**
   * <p>属性（<code>bottles</code>）对应的数据库字段名（<code>bottles</code>）。</p>
   * <p>调差瓶数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BOTTLES = "bottles";

  /**
   * <p>属性（<code>plants</code>）的 JavaBean 属性名（<code>plants</code>）。</p>
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_PLANTS = "plants";

  /**
   * <p>属性（<code>plants</code>）对应的数据库字段名（<code>plants</code>）。</p>
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PLANTS = "plants";

  /**
   * <p>属性（<code>reason</code>）的 JavaBean 属性名（<code>reason</code>）。</p>
   * <p>调差原因</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_REASON = "reason";

  /**
   * <p>属性（<code>reason</code>）对应的数据库字段名（<code>reason</code>）。</p>
   * <p>调差原因</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_REASON = "reason";

  /**
   * <p>属性（<code>operatorId</code>）的 JavaBean 属性名（<code>operatorId</code>）。</p>
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORID = "operatorId";

  /**
   * <p>属性（<code>operatorId</code>）对应的数据库字段名（<code>operator_id</code>）。</p>
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORID = "operator_id";

  /**
   * <p>属性（<code>operatorName</code>）的 JavaBean 属性名（<code>operatorName</code>）。</p>
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_OPERATORNAME = "operatorName";

  /**
   * <p>属性（<code>operatorName</code>）对应的数据库字段名（<code>operator_name</code>）。</p>
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATORNAME = "operator_name";

  /**
   * <p>属性（<code>operateTime</code>）的 JavaBean 属性名（<code>operateTime</code>）。</p>
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String BP_NAME_OPERATETIME = "operateTime";

  /**
   * <p>属性（<code>operateTime</code>）对应的数据库字段名（<code>operate_time</code>）。</p>
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_OPERATETIME = "operate_time";

  /**
   * <p>调差ID</p>
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
   * <p>代数</p>
   * <ul>
   *   <li>COLUMN : <code>generation</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getGeneration();

  /**
   * <p>任务状态：dictionary.inventory_adjustment_status</p>
   * <ul>
   *   <li>COLUMN : <code>status</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT "created"</code></li>
   * </ul>
   */
  String getStatus();

  /**
   * <p>调差瓶数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>bottles</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getBottles();

  /**
   * <p>调差株数。变小记作负数，变大记作正数。</p>
   * <ul>
   *   <li>COLUMN : <code>plants</code></li>
   *   <li>DDL : <code>INT(10), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getPlants();

  /**
   * <p>调差原因</p>
   * <ul>
   *   <li>COLUMN : <code>reason</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getReason();

  /**
   * <p>操作人用户ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>operator_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorId();

  /**
   * <p>操作人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>operator_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getOperatorName();

  /**
   * <p>操作时间 UTC 毫秒时间戳</p>
   * <ul>
   *   <li>COLUMN : <code>operate_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT 0L</code></li>
   * </ul>
   */
  Long getOperateTime();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
