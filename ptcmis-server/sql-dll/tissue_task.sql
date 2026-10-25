-- ==============================================
-- 组培生产任务主表 TissueTask
-- ==============================================
DROP TABLE IF EXISTS `tissue_task`;
CREATE TABLE `tissue_task` (
  `id` VARCHAR(32) NOT NULL COMMENT '任务ID',
  `variety` VARCHAR(32) NOT NULL COMMENT '品种 variety.name',
  `variety_code` VARCHAR(32) NOT NULL COMMENT '品种编号（子品种） variety_code.code',
  `explant_collection_batch` INT NOT NULL DEFAULT 0 COMMENT '外植体采集批次，仅用于诱导任务',
  `estimated_parent_bottles` INT NOT NULL DEFAULT 0 COMMENT '预计使用母代瓶数',
  `estimated_parent_plants` INT NOT NULL DEFAULT 0 COMMENT '预计使用母代株数',
  `estimated_parent_keep_bottles` INT NOT NULL DEFAULT 0 COMMENT '预计母代留种瓶数',
  `estimated_parent_keep_plants` INT NOT NULL DEFAULT 0 COMMENT '预计母代留种株数',
  `estimated_children_bottles` INT NOT NULL DEFAULT 0 COMMENT '预计生产子代瓶数',
  `estimated_children_plants` INT NOT NULL DEFAULT 0 COMMENT '预计生产子代株数',
  `default_plants_per_parent_bottle` SMALLINT NOT NULL DEFAULT 0 COMMENT '任务参数：母代默认每瓶株数',
  `default_plants_per_child_bottle` SMALLINT NOT NULL DEFAULT 0 COMMENT '任务参数：子代默认每瓶株数',
  `code_prefix` VARCHAR(6) NOT NULL DEFAULT '' COMMENT '瓶码前缀（打印时生成6位大写字母）',
  `creator_user_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人用户ID，user.id',
  `creator_user_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人姓名，user.name',
  `task_type` VARCHAR(32) NOT NULL COMMENT '任务类别：dictionary.tissue_task_type',
  `task_status` VARCHAR(32) NOT NULL DEFAULT 'created' COMMENT '任务状态：dictionary.tissue_task_status',
  `create_time` BIGINT NOT NULL DEFAULT 0 COMMENT '创建时间 UTC 毫秒时间戳',
  `start_time` BIGINT NOT NULL DEFAULT 0 COMMENT '开始时间 UTC 毫秒时间戳',
  `finish_time` BIGINT NOT NULL DEFAULT 0 COMMENT '完成时间 UTC 毫秒时间戳',
  `modifiable` VARCHAR(1) NOT NULL DEFAULT '0' COMMENT '是否开启强制编辑，dictionary = boolean',
  PRIMARY KEY (`id`)
) COMMENT='组培生产任务';
