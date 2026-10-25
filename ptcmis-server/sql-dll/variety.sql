DROP TABLE IF EXISTS `variety`;
CREATE TABLE `variety` (
  `name` VARCHAR(32) NOT NULL COMMENT '品种名称(主键)',
  `enabled` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '是否有效，dictionary = boolean',
  PRIMARY KEY (`name`)
) COMMENT='品种信息';
