package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.User} 接口。</p>
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
 * <p>重要方法：</p><ul>
 *   <li>全参构造方法（参数顺序与数据库字段顺序一致）。</li>
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.User}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class UserDTO implements Cloneable, User {
  /**
   * <p>员工ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String id;

  /**
   * <p>姓名</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String name;

  /**
   * <p>编号</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String code;

  /**
   * <p>手机号码</p>
   * <ul>
   *   <li>COLUMN : <code>mobile</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String mobile;

  /**
   * <p>密码</p>
   * <ul>
   *   <li>COLUMN : <code>password</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String password;

  /**
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  private String enabled;

  /**
   * <p>员工系统角色，dictionary = user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  private String role;

  /**
   * <p>无参构造方法。</p>
   * <p>用户（员工信息）</p>
   * <p>数据库表： <code>user</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public UserDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>用户（员工信息）</p>
   * <p>数据库表： <code>user</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>员工ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param name <p>姓名</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param code <p>编号</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param mobile <p>手机号码</p>
   * <ul>
   *   <li>COLUMN : <code>mobile</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param password <p>密码</p>
   * <ul>
   *   <li>COLUMN : <code>password</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param enabled <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   *
   * @param role <p>员工系统角色，dictionary = user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   *
   */
  public UserDTO(String id, String name, String code, String mobile, String password,
      String enabled, String role) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.mobile = mobile;
    this.password = password;
    this.enabled = enabled;
    this.role = role;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>用户（员工信息）</p>
   * <p>数据库表： <code>user</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public UserDTO(User model) {
    this.id = model.getId();
    this.name = model.getName();
    this.code = model.getCode();
    this.mobile = model.getMobile();
    this.password = model.getPassword();
    this.enabled = model.getEnabled();
    this.role = model.getRole();
  }

  /**
   * <p>员工ID。主键</p>
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
   * <p>姓名</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * <p>编号</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getCode() {
    return code;
  }

  /**
   * <p>手机号码</p>
   * <ul>
   *   <li>COLUMN : <code>mobile</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getMobile() {
    return mobile;
  }

  /**
   * <p>密码</p>
   * <ul>
   *   <li>COLUMN : <code>password</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  @Override
  public String getEnabled() {
    return enabled;
  }

  /**
   * <p>员工系统角色，dictionary = user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  @Override
  public String getRole() {
    return role;
  }

  /**
   * <p>员工ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param id <p>员工ID。主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * <p>姓名</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param name <p>姓名</p>
   * <ul>
   *   <li>COLUMN : <code>name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * <p>编号</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param code <p>编号</p>
   * <ul>
   *   <li>COLUMN : <code>code</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * <p>手机号码</p>
   * <ul>
   *   <li>COLUMN : <code>mobile</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param mobile <p>手机号码</p>
   * <ul>
   *   <li>COLUMN : <code>mobile</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  /**
   * <p>密码</p>
   * <ul>
   *   <li>COLUMN : <code>password</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param password <p>密码</p>
   * <ul>
   *   <li>COLUMN : <code>password</code></li>
   *   <li>DDL : <code>VARCHAR(16), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   * @param enabled <p>是否有效，dictionary = boolean</p>
   * <ul>
   *   <li>COLUMN : <code>enabled</code></li>
   *   <li>DDL : <code>VARCHAR(1), MANDATORY, DEFAULT "1"</code></li>
   * </ul>
   */
  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  /**
   * <p>员工系统角色，dictionary = user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   * @param role <p>员工系统角色，dictionary = user_role</p>
   * <ul>
   *   <li>COLUMN : <code>role</code></li>
   *   <li>DDL : <code>VARCHAR(10), MANDATORY, DEFAULT "none"</code></li>
   * </ul>
   */
  public void setRole(String role) {
    this.role = role;
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
  public UserDTO cloneSimple() {
    try{
      return (UserDTO)clone();
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
    if(other instanceof UserDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.User o) return fetchId().equals(o.fetchId());
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
