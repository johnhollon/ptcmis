DROP TABLE IF EXISTS `inventory_transaction`;
CREATE TABLE `inventory_transaction` (
  `id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '主键',
  `biz_type` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '业务类型(出库/入库/调差)。dictionary.inventory_tx_biz_type',
  `table_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '来源数据库表名。可根据 table_name, record_id 在 achive 表中查询对应附件',
  `record_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '来源数据库主键ID。可根据 table_name, record_id 在 achive 表中查询对应附件',
  `variety` VARCHAR(32) NOT NULL COMMENT '品种',
  `variety_code` VARCHAR(32) NOT NULL COMMENT '品种编号（子品种）',
  `generation` TINYINT NOT NULL COMMENT '代数',
  `bottles` INT NOT NULL DEFAULT 0 COMMENT '瓶数。库存变小记作负数，变大记作正数。',
  `plants` INT NOT NULL DEFAULT 0 COMMENT '株数。库存变小记作负数，变大记作正数。',
  `description` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '备注信息',
  `operator_id` VARCHAR(32) NOT NULL COMMENT '操作人用户ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL COMMENT '操作人姓名，user.name',
  `operate_time` BIGINT NOT NULL COMMENT '操作时间 UTC 毫秒时间戳',
  PRIMARY KEY (`id`),
  KEY `idx_inventory_t_ref` (`table_name`,`record_id`),
  KEY `idx_inventory_t_v` (`variety`),
  KEY `idx_inventory_t_vc` (`variety_code`),
  KEY `idx_inventory_t_vg` (`variety`, `generation`),
  KEY `idx_inventory_t_vcg` (`variety_code`, `generation`),
  KEY `idx_inventory_t_vvcg` (`variety`, `variety_code`, `generation`),
  KEY `idx_operate_time` (`operate_time`)
) COMMENT='库存流水';
