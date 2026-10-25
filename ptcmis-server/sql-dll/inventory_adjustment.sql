-- ==============================================
-- 盘点调差表
-- ==============================================
DROP TABLE IF EXISTS `inventory_adjustment`;
CREATE TABLE `inventory_adjustment` (
  `id` VARCHAR(32) NOT NULL COMMENT '调差ID',
  `variety` VARCHAR(32) NOT NULL COMMENT '品种 variety.name',
  `variety_code` VARCHAR(32) NOT NULL COMMENT '品种编号（子品种） variety_code.code',
  `generation` TINYINT NOT NULL DEFAULT 0 COMMENT '代数',
  `status` VARCHAR(32) NOT NULL DEFAULT 'created' COMMENT '任务状态：dictionary.inventory_adjustment_status',
  `bottles` INT NOT NULL DEFAULT 0 COMMENT '调差瓶数。变小记作负数，变大记作正数。',
  `plants` INT NOT NULL DEFAULT 0 COMMENT '调差株数。变小记作负数，变大记作正数。',
  `reason` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '调差原因',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作人用户ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作人姓名，user.name',
  `operate_time` BIGINT NOT NULL DEFAULT 0 COMMENT '操作时间 UTC 毫秒时间戳',
  PRIMARY KEY (`id`)
) COMMENT='盘点调差';

-- ==============================================
-- 盘点调差操作日志
-- ==============================================
DROP TABLE IF EXISTS `inventory_adjustment_log`;
CREATE TABLE `inventory_adjustment_log` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID主键',
  `id_adjustment` VARCHAR(32) NOT NULL COMMENT '调差ID. inventory_adjustment.id',
  `status` VARCHAR(32) NOT NULL DEFAULT 'created' COMMENT '状态。 dictionary = inventory_adjustment_status',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作人ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作人姓名，user.name',
  `operator_time` BIGINT NOT NULL DEFAULT 0 COMMENT '操作时间，UTC 毫秒',
  PRIMARY KEY (`id`)
) COMMENT='盘点调差操作日志';
