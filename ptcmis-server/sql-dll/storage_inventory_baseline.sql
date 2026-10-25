-- ==============================================
-- 库存基准
-- ==============================================
DROP TABLE IF EXISTS `storage_inventory_baseline`;
CREATE TABLE `storage_inventory_baseline` (
  `variety` VARCHAR(32) NOT NULL COMMENT '所属品种。variety.id',
  `code` VARCHAR(32) NOT NULL COMMENT '子品种编号。主键',
  `generation` TINYINT NOT NULL DEFAULT 0 COMMENT '代数',
  `bottles` INT NOT NULL DEFAULT 0 COMMENT '瓶数基准',
  `plants` INT NOT NULL DEFAULT 0 COMMENT '株数基准',
  PRIMARY KEY (`code`, `generation`),
  KEY `idx_variety_code_variety` (`variety`)
) COMMENT='库存基准';
