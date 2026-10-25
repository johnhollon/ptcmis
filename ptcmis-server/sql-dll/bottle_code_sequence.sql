DROP TABLE IF EXISTS `bottle_code_sequence`;
CREATE TABLE `bottle_code_sequence` (
  `id` TINYINT UNSIGNED NOT NULL COMMENT '主键，固定为 0',
  `current_value` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '下一个应用的编码数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='瓶码序列计数器表';

INSERT INTO `bottle_code_sequence` (`id`, `current_value`) VALUES (0, 0);
