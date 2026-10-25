DROP TABLE IF EXISTS `code_print`;
CREATE TABLE `code_print` (
  `id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '主键',
  `table_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件',
  `record_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件',
  `description` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '备注信息',
  `operator_id` VARCHAR(32) NOT NULL COMMENT '操作人用户ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL COMMENT '操作人姓名，user.name',
  `operate_time` BIGINT NOT NULL COMMENT '操作时间 UTC 毫秒时间戳',
  PRIMARY KEY (`id`),
  KEY `idx_code_p_ref` (`table_name`,`record_id`),
  KEY `idx_code_p_opt` (`operate_time`)
) COMMENT='瓶码打印记录';

DROP TABLE IF EXISTS `code_print_detail`;
CREATE TABLE `code_print_detail` (
  `id_print` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '所属瓶码打印记录。 code_print.id',
  `code` VARCHAR(11) NOT NULL DEFAULT '' COMMENT '瓶码',
  `variety_code` VARCHAR(32) NOT NULL COMMENT '瓶码信息：品种编号（子品种）',
  `explant_collection_batch` INT NOT NULL DEFAULT 0 COMMENT '瓶码信息：外植体采集批次',
  `generation` TINYINT NOT NULL COMMENT '瓶码信息：代数',
  `operator_name` VARCHAR(32) NOT NULL COMMENT '瓶码信息：操作人姓名，user.name',
  `operate_time` BIGINT NOT NULL COMMENT '瓶码信息：建瓶时间 UTC 毫秒时间戳',
  PRIMARY KEY (`id_print`, `code`),
  KEY `idx_code_pd_ref` (`id_print`),
  KEY `idx_code_pd_code` (`code`)
) COMMENT='瓶码打印记录明细';
