package chenhuiran.ptcmisserver.stubs.entities;

/**
 * <p>DTO 实体类。包含数据库所有字段对应的属性，这些属性均为 final 、只有 getter 方法。</p>
 * <p>该类实现了 {@link chenhuiran.ptcmisserver.stubs.entities.Archive} 接口。</p>
 * <p>附件材料</p>
 * <p>数据库表： <code>archive</code></p>
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
 *   <li>参数为 {@link chenhuiran.ptcmisserver.stubs.entities.Archive}构造方法。</li>
 *   <li>{@link #fetchId()} : 获得能够表达对象在数据库中唯一的字符串值。</li>
 *   <li>{@link #cloneSimple()} : 克隆。</li>
 *   <li>{@link #equals(Object)} : 基于 <code>fetchId()</code> 方法进行相同判断。</li>
 *   <li>{@link #hashCode()} : 基于 <code>fetchId()</code> 方法进行哈希。</li>
 *  </ul>
 */
public class ArchiveDTO implements Cloneable, Archive {
  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String id;

  /**
   * <p>数据库表名</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String tableName;

  /**
   * <p>数据库主键ID</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String recordId;

  /**
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String bizType;

  /**
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String description;

  /**
   * <p>文件名</p>
   * <ul>
   *   <li>COLUMN : <code>file_name</code></li>
   *   <li>DDL : <code>VARCHAR(512), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String fileName;

  /**
   * <p>文件类型(mime type)</p>
   * <ul>
   *   <li>COLUMN : <code>content_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String contentType;

  /**
   * <p>服务器存储路径</p>
   * <ul>
   *   <li>COLUMN : <code>server_file_path</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String serverFilePath;

  /**
   * <p>上传人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String uploaderId;

  /**
   * <p>传人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private String uploaderName;

  /**
   * <p>传时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>upload_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  private Long uploadTime;

  /**
   * <p>无参构造方法。</p>
   * <p>附件材料</p>
   * <p>数据库表： <code>archive</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   */
  public ArchiveDTO() {
  }

  /**
   * <p>全参数构造方法。</p>
   * <p>附件材料</p>
   * <p>数据库表： <code>archive</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param id <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param tableName <p>数据库表名</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param recordId <p>数据库主键ID</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param bizType <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param description <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param fileName <p>文件名</p>
   * <ul>
   *   <li>COLUMN : <code>file_name</code></li>
   *   <li>DDL : <code>VARCHAR(512), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param contentType <p>文件类型(mime type)</p>
   * <ul>
   *   <li>COLUMN : <code>content_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param serverFilePath <p>服务器存储路径</p>
   * <ul>
   *   <li>COLUMN : <code>server_file_path</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param uploaderId <p>上传人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param uploaderName <p>传人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   * @param uploadTime <p>传时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>upload_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   *
   */
  public ArchiveDTO(String id, String tableName, String recordId, String bizType,
      String description, String fileName, String contentType, String serverFilePath,
      String uploaderId, String uploaderName, Long uploadTime) {
    this.id = id;
    this.tableName = tableName;
    this.recordId = recordId;
    this.bizType = bizType;
    this.description = description;
    this.fileName = fileName;
    this.contentType = contentType;
    this.serverFilePath = serverFilePath;
    this.uploaderId = uploaderId;
    this.uploaderName = uploaderName;
    this.uploadTime = uploadTime;
  }

  /**
   * <p>接口对象构造方法。</p>
   * <p>附件材料</p>
   * <p>数据库表： <code>archive</code></p>
   * <p>主键：</p><ul>
   *   <li>属性 <code>id</code>: 参见 {@link #getId()} 。</li>
   * </ul>
   * @param model 使用该接口对象构造实例。
   */
  public ArchiveDTO(Archive model) {
    this.id = model.getId();
    this.tableName = model.getTableName();
    this.recordId = model.getRecordId();
    this.bizType = model.getBizType();
    this.description = model.getDescription();
    this.fileName = model.getFileName();
    this.contentType = model.getContentType();
    this.serverFilePath = model.getServerFilePath();
    this.uploaderId = model.getUploaderId();
    this.uploaderName = model.getUploaderName();
    this.uploadTime = model.getUploadTime();
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
   * <p>数据库表名</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getTableName() {
    return tableName;
  }

  /**
   * <p>数据库主键ID</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getRecordId() {
    return recordId;
  }

  /**
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getBizType() {
    return bizType;
  }

  /**
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * <p>文件名</p>
   * <ul>
   *   <li>COLUMN : <code>file_name</code></li>
   *   <li>DDL : <code>VARCHAR(512), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getFileName() {
    return fileName;
  }

  /**
   * <p>文件类型(mime type)</p>
   * <ul>
   *   <li>COLUMN : <code>content_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getContentType() {
    return contentType;
  }

  /**
   * <p>服务器存储路径</p>
   * <ul>
   *   <li>COLUMN : <code>server_file_path</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getServerFilePath() {
    return serverFilePath;
  }

  /**
   * <p>上传人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getUploaderId() {
    return uploaderId;
  }

  /**
   * <p>传人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public String getUploaderName() {
    return uploaderName;
  }

  /**
   * <p>传时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>upload_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  @Override
  public Long getUploadTime() {
    return uploadTime;
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
   * <p>数据库表名</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param tableName <p>数据库表名</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * <p>数据库主键ID</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param recordId <p>数据库主键ID</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  /**
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param bizType <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setBizType(String bizType) {
    this.bizType = bizType;
  }

  /**
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param description <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * <p>文件名</p>
   * <ul>
   *   <li>COLUMN : <code>file_name</code></li>
   *   <li>DDL : <code>VARCHAR(512), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param fileName <p>文件名</p>
   * <ul>
   *   <li>COLUMN : <code>file_name</code></li>
   *   <li>DDL : <code>VARCHAR(512), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * <p>文件类型(mime type)</p>
   * <ul>
   *   <li>COLUMN : <code>content_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param contentType <p>文件类型(mime type)</p>
   * <ul>
   *   <li>COLUMN : <code>content_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * <p>服务器存储路径</p>
   * <ul>
   *   <li>COLUMN : <code>server_file_path</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param serverFilePath <p>服务器存储路径</p>
   * <ul>
   *   <li>COLUMN : <code>server_file_path</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setServerFilePath(String serverFilePath) {
    this.serverFilePath = serverFilePath;
  }

  /**
   * <p>上传人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param uploaderId <p>上传人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setUploaderId(String uploaderId) {
    this.uploaderId = uploaderId;
  }

  /**
   * <p>传人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param uploaderName <p>传人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setUploaderName(String uploaderName) {
    this.uploaderName = uploaderName;
  }

  /**
   * <p>传时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>upload_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   * @param uploadTime <p>传时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>upload_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  public void setUploadTime(Long uploadTime) {
    this.uploadTime = uploadTime;
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
  public ArchiveDTO cloneSimple() {
    try{
      return (ArchiveDTO)clone();
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
    if(other instanceof ArchiveDTO o) return fetchId().equals(o.fetchId());
    if(other instanceof chenhuiran.ptcmisserver.stubs.entities.Archive o) return fetchId().equals(o.fetchId());
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
