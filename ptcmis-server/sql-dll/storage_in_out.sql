-- ==============================================
-- 出库/入库单
-- ==============================================
DROP TABLE IF EXISTS `storage_in_out`;
CREATE TABLE `storage_in_out` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID主键',
  `in_out` VARCHAR(32) NOT NULL COMMENT '出入库类别，标记出库还是入库。 dictionary = storage_in_out_type',
  `bottles_applied` INT NOT NULL DEFAULT 0 COMMENT '申请出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_applied）',
  `plants_applied` INT NOT NULL DEFAULT 0 COMMENT '申请出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_applied）',
  `bottles_keep_in_applied` INT NOT NULL DEFAULT 0 COMMENT '申请入库瓶数中母代留种瓶数（是 bottles_applied 的其中数）',
  `plants_keep_in_applied` INT NOT NULL DEFAULT 0 COMMENT '申请入库株数中母代留种株数（是 plants_applied 的其中数）',
  `bottles_actual` INT NOT NULL DEFAULT 0 COMMENT '实际出入库瓶数（出记作负数，入记作正数，入包含 bottles_keep_in_actual）',
  `plants_actual` INT NOT NULL DEFAULT 0 COMMENT '实际出入库株数（出记作负数，入记作正数，入包含 plants_keep_in_actual）',
  `bottles_keep_in_actual` INT NOT NULL DEFAULT 0 COMMENT '实际入库瓶数中母代留种瓶数（是 bottles_actual 的其中数）',
  `plants_keep_in_actual` INT NOT NULL DEFAULT 0 COMMENT '实际入库株数中母代留种株数（是 plants_actual 的其中数）',
  `bottles_discard` INT NOT NULL DEFAULT 0 COMMENT '淘汰瓶数',
  `plants_discard` INT NOT NULL DEFAULT 0 COMMENT '淘汰株数',
  `bottles_voided` INT NOT NULL DEFAULT 0 COMMENT '废弃瓶数',
  `plants_voided` INT NOT NULL DEFAULT 0 COMMENT '废弃株数',
  `ignore_stats` VARCHAR(32) NOT NULL DEFAULT '0' COMMENT '是否在统计中忽略该入库单。 dictionary = boolean',
  `reason` VARCHAR(32) NOT NULL COMMENT '出入库原因。 dictionary = storage_reason',
  `status` VARCHAR(32) NOT NULL DEFAULT 'apply' COMMENT '状态。 dictionary = storage_in_out_status',
  `opp_reason` VARCHAR(128) NOT NULL COMMENT '操作原因。 驳回、作废原因（手工填写）',
  `id_task` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '产生出入库业务的组培任务ID。tissue_task.id。',
  `id_inspection` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '产生出入库业务的质检任务ID。inspection_task.id。',
  `applicant_id` VARCHAR(32) NOT NULL COMMENT '申请人ID，user.id',
  `applicant_name` VARCHAR(32) NOT NULL COMMENT '申请人姓名，user.name',
  `applicant_time` BIGINT NOT NULL COMMENT '申请时间，UTC 毫秒',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '末次操作人ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '末次操作人姓名，user.name',
  `operator_time` BIGINT NOT NULL DEFAULT 0 COMMENT '末次操作时间，UTC 毫秒',
  PRIMARY KEY (`id`)
) COMMENT='出库/入库单';


