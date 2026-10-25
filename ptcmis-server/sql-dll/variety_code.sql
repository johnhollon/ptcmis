-- ==============================================
-- 品种编号（子品种信息）表
-- ==============================================
DROP TABLE IF EXISTS `variety_code`;
CREATE TABLE `variety_code` (
  `code` VARCHAR(32) NOT NULL COMMENT '子品种编号。主键',
  `variety` VARCHAR(32) NOT NULL COMMENT '所属品种。variety.id',
  `important` VARCHAR(1) NOT NULL COMMENT '是否为重点品种，dictionary = boolean',
  `enabled` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '是否有效，dictionary = boolean',
  PRIMARY KEY (`code`),
  KEY `idx_variety_code_variety` (`variety`)
) COMMENT='品种编号（子品种信息）';
