package chenhuiran.ptcmisserver.stubs.entities;

import java.io.Serializable;

/**
 * <p>实体类存根接口。该接口包括数据库所有字段的 getter 方法签名。</p>
 * <p>该接口提供默认的 {@link #fetchId()} 方法实现。该方法可以获得能够表达对象在数据库中唯一的字符串值。</p>
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
 */
public interface Archive extends Serializable {
  /**
   * <p>表名（ <code>archive</code> ）。</p>
   * 附件材料
   */
  String TB_NAME = "archive";

  /**
   * <p>属性（<code>id</code>）的 JavaBean 属性名（<code>id</code>）。</p>
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_ID = "id";

  /**
   * <p>属性（<code>id</code>）对应的数据库字段名（<code>id</code>）。</p>
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_ID = "id";

  /**
   * <p>属性（<code>tableName</code>）的 JavaBean 属性名（<code>tableName</code>）。</p>
   * <p>数据库表名</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_TABLENAME = "tableName";

  /**
   * <p>属性（<code>tableName</code>）对应的数据库字段名（<code>table_name</code>）。</p>
   * <p>数据库表名</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_TABLENAME = "table_name";

  /**
   * <p>属性（<code>recordId</code>）的 JavaBean 属性名（<code>recordId</code>）。</p>
   * <p>数据库主键ID</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_RECORDID = "recordId";

  /**
   * <p>属性（<code>recordId</code>）对应的数据库字段名（<code>record_id</code>）。</p>
   * <p>数据库主键ID</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_RECORDID = "record_id";

  /**
   * <p>属性（<code>bizType</code>）的 JavaBean 属性名（<code>bizType</code>）。</p>
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_BIZTYPE = "bizType";

  /**
   * <p>属性（<code>bizType</code>）对应的数据库字段名（<code>biz_type</code>）。</p>
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_BIZTYPE = "biz_type";

  /**
   * <p>属性（<code>description</code>）的 JavaBean 属性名（<code>description</code>）。</p>
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_DESCRIPTION = "description";

  /**
   * <p>属性（<code>description</code>）对应的数据库字段名（<code>description</code>）。</p>
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_DESCRIPTION = "description";

  /**
   * <p>属性（<code>fileName</code>）的 JavaBean 属性名（<code>fileName</code>）。</p>
   * <p>文件名</p>
   * <ul>
   *   <li>COLUMN : <code>file_name</code></li>
   *   <li>DDL : <code>VARCHAR(512), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_FILENAME = "fileName";

  /**
   * <p>属性（<code>fileName</code>）对应的数据库字段名（<code>file_name</code>）。</p>
   * <p>文件名</p>
   * <ul>
   *   <li>COLUMN : <code>file_name</code></li>
   *   <li>DDL : <code>VARCHAR(512), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_FILENAME = "file_name";

  /**
   * <p>属性（<code>contentType</code>）的 JavaBean 属性名（<code>contentType</code>）。</p>
   * <p>文件类型(mime type)</p>
   * <ul>
   *   <li>COLUMN : <code>content_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_CONTENTTYPE = "contentType";

  /**
   * <p>属性（<code>contentType</code>）对应的数据库字段名（<code>content_type</code>）。</p>
   * <p>文件类型(mime type)</p>
   * <ul>
   *   <li>COLUMN : <code>content_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_CONTENTTYPE = "content_type";

  /**
   * <p>属性（<code>serverFilePath</code>）的 JavaBean 属性名（<code>serverFilePath</code>）。</p>
   * <p>服务器存储路径</p>
   * <ul>
   *   <li>COLUMN : <code>server_file_path</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_SERVERFILEPATH = "serverFilePath";

  /**
   * <p>属性（<code>serverFilePath</code>）对应的数据库字段名（<code>server_file_path</code>）。</p>
   * <p>服务器存储路径</p>
   * <ul>
   *   <li>COLUMN : <code>server_file_path</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_SERVERFILEPATH = "server_file_path";

  /**
   * <p>属性（<code>uploaderId</code>）的 JavaBean 属性名（<code>uploaderId</code>）。</p>
   * <p>上传人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_UPLOADERID = "uploaderId";

  /**
   * <p>属性（<code>uploaderId</code>）对应的数据库字段名（<code>uploader_id</code>）。</p>
   * <p>上传人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_UPLOADERID = "uploader_id";

  /**
   * <p>属性（<code>uploaderName</code>）的 JavaBean 属性名（<code>uploaderName</code>）。</p>
   * <p>传人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_UPLOADERNAME = "uploaderName";

  /**
   * <p>属性（<code>uploaderName</code>）对应的数据库字段名（<code>uploader_name</code>）。</p>
   * <p>传人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_UPLOADERNAME = "uploader_name";

  /**
   * <p>属性（<code>uploadTime</code>）的 JavaBean 属性名（<code>uploadTime</code>）。</p>
   * <p>传时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>upload_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String BP_NAME_UPLOADTIME = "uploadTime";

  /**
   * <p>属性（<code>uploadTime</code>）对应的数据库字段名（<code>upload_time</code>）。</p>
   * <p>传时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>upload_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String TABLE_CL_NAME_UPLOADTIME = "upload_time";

  /**
   * <p>ID主键</p>
   * <ul>
   *   <li>COLUMN : <code>id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getId();

  /**
   * <p>数据库表名</p>
   * <ul>
   *   <li>COLUMN : <code>table_name</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getTableName();

  /**
   * <p>数据库主键ID</p>
   * <ul>
   *   <li>COLUMN : <code>record_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getRecordId();

  /**
   * <p>业务类型，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>biz_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getBizType();

  /**
   * <p>业务备注，文本型描述</p>
   * <ul>
   *   <li>COLUMN : <code>description</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getDescription();

  /**
   * <p>文件名</p>
   * <ul>
   *   <li>COLUMN : <code>file_name</code></li>
   *   <li>DDL : <code>VARCHAR(512), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getFileName();

  /**
   * <p>文件类型(mime type)</p>
   * <ul>
   *   <li>COLUMN : <code>content_type</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getContentType();

  /**
   * <p>服务器存储路径</p>
   * <ul>
   *   <li>COLUMN : <code>server_file_path</code></li>
   *   <li>DDL : <code>VARCHAR(128), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getServerFilePath();

  /**
   * <p>上传人ID，user.id</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_id</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getUploaderId();

  /**
   * <p>传人姓名，user.name</p>
   * <ul>
   *   <li>COLUMN : <code>uploader_name</code></li>
   *   <li>DDL : <code>VARCHAR(32), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  String getUploaderName();

  /**
   * <p>传时间，UTC 毫秒</p>
   * <ul>
   *   <li>COLUMN : <code>upload_time</code></li>
   *   <li>DDL : <code>BIGINT(19), MANDATORY, DEFAULT NULL</code></li>
   * </ul>
   */
  Long getUploadTime();

  /**
   * <p>获得能够表达对象在数据库中唯一的字符串值。返回值不可能为 null （ null 均被“<code>&#92;u001F</code>”替换）；返回值也不会被自动赋值为空字符串（除非其数据库唯一性的字符串表达原本就为空字符串）。</p>
   */
  default String fetchId() {
    return getId() == null ? "\u001F" : getId();
  }
}
