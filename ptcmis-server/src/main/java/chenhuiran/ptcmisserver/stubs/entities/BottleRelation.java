package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>组培瓶代际追溯表</p>
 * <p>数据库表： <code>bottle_relation</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface BottleRelation extends Serializable {
  /**
   * <p>表名（ <code>bottle_relation</code> ）。</p>
   * 组培瓶代际追溯表
   */
  String TB_NAME = "bottle_relation";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>childId</code>）的 JavaBean 属性名（<code>childId</code>）。</p>
   * <p>后代培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>child_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_CHILDID = "childId";

  /**
   * <p>属性（<code>childId</code>）对应的数据库字段名（<code>child_id</code>）。</p>
   * <p>后代培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>child_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CHILDID = "child_id";

  /**
   * <p>属性（<code>parentId</code>）的 JavaBean 属性名（<code>parentId</code>）。</p>
   * <p>祖先培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>parent_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String BP_NAME_PARENTID = "parentId";

  /**
   * <p>属性（<code>parentId</code>）对应的数据库字段名（<code>parent_id</code>）。</p>
   * <p>祖先培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>parent_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PARENTID = "parent_id";

  /**
   * <p>属性（<code>depth</code>）的 JavaBean 属性名（<code>depth</code>）。</p>
   * <p>代际距离；0=自身，1直接父瓶，2祖父...</p>
   * <ul>
   *   <li>COLUMN : <code>depth</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String BP_NAME_DEPTH = "depth";

  /**
   * <p>属性（<code>depth</code>）对应的数据库字段名（<code>depth</code>）。</p>
   * <p>代际距离；0=自身，1直接父瓶，2祖父...</p>
   * <ul>
   *   <li>COLUMN : <code>depth</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DEPTH = "depth";

  /**
   * <p>ID</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>后代培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>child_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getChildId();

  /**
   * <p>祖先培养瓶ID，bottle.id</p>
   * <ul>
   *   <li>COLUMN : <code>parent_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT ""</code></li>
   * </ul>
   */
  String getParentId();

  /**
   * <p>代际距离；0=自身，1直接父瓶，2祖父...</p>
   * <ul>
   *   <li>COLUMN : <code>depth</code></li>
   *   <li>DDL : <code>TINYINT(3), MANDATORY, DEFAULT 0</code></li>
   * </ul>
   */
  Integer getDepth();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
