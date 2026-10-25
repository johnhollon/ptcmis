package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>用户（员工信息）</p>
 * <p>数据库表： <code>user</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface User extends Serializable {
  /**
   * <p>表名（ <code>user</code> ）。</p>
   * 用户（员工信息）
   */
  String TB_NAME = "user";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>员工ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>员工ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>name</code>）的 JavaBean 属性名（<code>name</code>）。</p>
   * <p>姓名</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_NAME = "name";

  /**
   * <p>属性（<code>name</code>）对应的数据库字段名（<code>name</code>）。</p>
   * <p>姓名</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_NAME = "name";

  /**
   * <p>属性（<code>code</code>）的 JavaBean 属性名（<code>code</code>）。</p>
   * <p>编号</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_CODE = "code";

  /**
   * <p>属性（<code>code</code>）对应的数据库字段名（<code>code</code>）。</p>
   * <p>编号</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CODE = "code";

  /**
   * <p>属性（<code>mobile</code>）的 JavaBean 属性名（<code>mobile</code>）。</p>
   * <p>手机号码</p>
   * <ul>
   *   <li>COLUMN : <code>mobile</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_MOBILE = "mobile";

  /**
   * <p>属性（<code>mobile</code>）对应的数据库字段名（<code>mobile</code>）。</p>
   * <p>手机号码</p>
   * <ul>
   *   <li>COLUMN : <code>mobile</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_MOBILE = "mobile";

  /**
   * <p>属性（<code>password</code>）的 JavaBean 属性名（<code>password</code>）。</p>
   * <p>密码</p>
   * <ul>
   *   <li>COLUMN : <code>password</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_PASSWORD = "password";

  /**
   * <p>属性（<code>password</code>）对应的数据库字段名（<code>password</code>）。</p>
   * <p>密码</p>
   * <ul>
   *   <li>COLUMN : <code>password</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_PASSWORD = "password";

  /**
   * <p>属性（<code>enabled</code>）的 JavaBean 属性名（<code>enabled</code>）。</p>
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String BP_NAME_ENABLED = "enabled";

  /**
   * <p>属性（<code>enabled</code>）对应的数据库字段名（<code>enabled</code>）。</p>
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ENABLED = "enabled";

  /**
   * <p>属性（<code>role</code>）的 JavaBean 属性名（<code>role</code>）。</p>
   * <p>员工系统角色，dictionary = user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  String BP_NAME_ROLE = "role";

  /**
   * <p>属性（<code>role</code>）对应的数据库字段名（<code>role</code>）。</p>
   * <p>员工系统角色，dictionary = user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ROLE = "role";

  /**
   * <p>员工ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>姓名</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getName();

  /**
   * <p>编号</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getCode();

  /**
   * <p>手机号码</p>
   * <ul>
   *   <li>COLUMN : <code>mobile</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getMobile();

  /**
   * <p>密码</p>
   * <ul>
   *   <li>COLUMN : <code>password</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getPassword();

  /**
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  String getEnabled();

  /**
   * <p>员工系统角色，dictionary = user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  String getRole();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
