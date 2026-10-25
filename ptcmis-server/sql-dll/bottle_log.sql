DROP TABLE IF EXISTS `bottle_log`;
CREATE TABLE `bottle_log` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID',
  `id_bottle` VARCHAR(32) NOT NULL COMMENT '瓶码ID',
  `opp_time` BIGINT NOT NULL DEFAULT 0 COMMENT '执行时间 UTC毫秒',
  `table_name` VARCHAR(128) NOT NULL COMMENT '数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件',
  `record_id` VARCHAR(32) NOT NULL COMMENT '数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件',
  `biz_type` VARCHAR(64) NOT NULL COMMENT '业务类型，文本型描述',
  `description` VARCHAR(128) NOT NULL COMMENT '业务备注，文本型描述',
  `inspector_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '质检员ID。 user.id',
  `inspector_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '质检员姓名。 user.name',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作员ID。 user.id',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作员姓名。 user.name',
  `operate_status` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作状态。 dictionary.bottle_operate_status',
  `storage_status` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '仓储状态。 dictionary.bottle_storage_status',
  `storage_reason` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '末次出库原因。 dictionary.storage_reason',
  `discarded` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否淘汰。dictionary = boolean',
  `discard_reason` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '淘汰原因。 dictionary.bottle_discard_reason',
  PRIMARY KEY (`id`)
) COMMENT='组培瓶操作日志';
