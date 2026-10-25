package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
 * <p>组培任务‑参与员工</p>
 * <p>数据库表： <code>tissue_task_user</code></p>
 * <p>主键：</p><ul>
 *   <li>属性 <code>idTask</code>: 参见 {@link #getIdTask()} 。</li>
 *   <li>属性 <code>idUser</code>: 参见 {@link #getIdUser()} 。</li>
 *   <li>属性 <code>role</code>: 参见 {@link #getRole()} 。</li>
 * </ul>
 * <p>提供静态常量，以供获得表名和字段名。</p>
 * <ul>
 *   <li>使用静态常量 <code>TB_NAME</code> 获得表名。</li>
 *   <li>使用静态常量 <code>CL_NAME_XXX</code> 获得属性（XXX为属性名）对应的字段名。</li>
 * </ul>
 */
public interface TissueTaskUser extends Serializable {
  /**
   * <p>表名（ <code>tissue_task_user</code> ）。</p>
   * 组培任务‑参与员工
   */
  String TB_NAME = "tissue_task_user";

  /**
   * <p>属性（<code>idTask</code>）的 JavaBean 属性名（<code>idTask</code>）。</p>
   * <p>任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDTASK = "idTask";

  /**
   * <p>属性（<code>idTask</code>）对应的数据库字段名（<code>id_task</code>）。</p>
   * <p>任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDTASK = "id_task";

  /**
   * <p>属性（<code>idUser</code>）的 JavaBean 属性名（<code>idUser</code>）。</p>
   * <p>员工ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_user</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_IDUSER = "idUser";

  /**
   * <p>属性（<code>idUser</code>）对应的数据库字段名（<code>id_user</code>）。</p>
   * <p>员工ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_user</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_IDUSER = "id_user";

  /**
   * <p>属性（<code>role</code>）的 JavaBean 属性名（<code>role</code>）。</p>
   * <p>员工身份。dictionary.tissue_task_user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ROLE = "role";

  /**
   * <p>属性（<code>role</code>）对应的数据库字段名（<code>role</code>）。</p>
   * <p>员工身份。dictionary.tissue_task_user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ROLE = "role";

  /**
   * <p>任务ID，tissue_task.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_task</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdTask();

  /**
   * <p>员工ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>id_user</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getIdUser();

  /**
   * <p>员工身份。dictionary.tissue_task_user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getRole();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    String seperator = "\u001E";
    StringBuilder result = new StringBuilder();
    result.append(seperator).append(getIdTask() == null ? "\u001F" : getIdTask());
    result.append(seperator).append(getIdUser() == null ? "\u001F" : getIdUser());
    result.append(seperator).append(getRole() == null ? "\u001F" : getRole());
    return result.toString();
  }
}
