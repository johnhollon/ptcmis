-- ==============================================
-- 附件材料
-- ==============================================
DROP TABLE IF EXISTS `archive`;
CREATE TABLE `archive` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID主键',
  `table_name` VARCHAR(128) NOT NULL COMMENT '数据库表名',
  `record_id` VARCHAR(32) NOT NULL COMMENT '数据库主键ID',
  `biz_type` VARCHAR(128) NOT NULL COMMENT '业务类型，文本型描述',
  `description` VARCHAR(128) NOT NULL COMMENT '业务备注，文本型描述',
  `file_name` VARCHAR(512) NOT NULL COMMENT '文件名',
  `content_type` VARCHAR(128) NOT NULL COMMENT '文件类型(mime type)',
  `server_file_path` VARCHAR(128) NOT NULL COMMENT '服务器存储路径',
  `uploader_id` VARCHAR(32) NOT NULL COMMENT '上传人ID，user.id',
  `uploader_name` VARCHAR(32) NOT NULL COMMENT '传人姓名，user.name',
  `upload_time` BIGINT NOT NULL COMMENT '传时间，UTC 毫秒',
  PRIMARY KEY (`id`)
) COMMENT='附件材料';
