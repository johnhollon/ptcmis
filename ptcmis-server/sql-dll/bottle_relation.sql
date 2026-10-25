DROP TABLE IF EXISTS `bottle_relation`;
CREATE TABLE `bottle_relation` (
  `id` VARCHAR(32) NOT NULL COMMENT 'ID',
  `child_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '后代培养瓶ID，bottle.id',
  `parent_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '祖先培养瓶ID，bottle.id',
  `depth` TINYINT NOT NULL DEFAULT 0 COMMENT '代际距离；0=自身，1直接父瓶，2祖父...',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bottle_relation` (`child_id`, `parent_id`) COMMENT '唯一约束：同一个后代‑祖先不允许重复记录',
  KEY `idx_child` (`child_id`) COMMENT '根据后代查询所有祖先',
  KEY `idx_parent` (`parent_id`) COMMENT '根据祖先查询所有后代'
) COMMENT='组培瓶代际追溯表';
