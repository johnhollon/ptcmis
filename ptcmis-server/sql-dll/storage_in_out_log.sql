-- ==============================================
-- 出库/入库单操作日志
-- ==============================================
DROP TABLE IF EXISTS `storage_in_out_log`;
CREATE TABLE `storage_in_out_log` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID主键',
  `id_in_out` VARCHAR(32) NOT NULL COMMENT '出入库ID. storage_in_out.id',
  `status` VARCHAR(32) NOT NULL DEFAULT 'apply' COMMENT '状态。 dictionary = storage_in_out_status',
  `opp_reason` VARCHAR(128) NOT NULL COMMENT '操作原因。 驳回、作废原因（手工填写）',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作人ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作人姓名，user.name',
  `operator_time` BIGINT NOT NULL DEFAULT 0 COMMENT '操作时间，UTC 毫秒',
  PRIMARY KEY (`id`)
) COMMENT='出库/入库单操作日志';


