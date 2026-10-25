-- ==============================================
-- 瓶体补码任务表
-- ==============================================
DROP TABLE IF EXISTS `bottle_code_complement_task`;
CREATE TABLE `bottle_code_complement_task` (
  `id` VARCHAR(32) NOT NULL COMMENT '任务ID',
  `variety` VARCHAR(32) NOT NULL COMMENT '品种 variety.name',
  `variety_code` VARCHAR(32) NOT NULL COMMENT '品种编号（子品种） variety_code.code',
  `explant_collection_batch` INT NOT NULL DEFAULT 0 COMMENT '外植体采集批次',
  `source_type` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '培养瓶产生来源，培养瓶产生于哪个业务。dictionary.storage_reason',
  `generation` TINYINT NOT NULL DEFAULT 0 COMMENT '代数',
  `code_prefix` VARCHAR(6) NOT NULL DEFAULT '' COMMENT '瓶码前缀（打印时生成6位大写字母）',
  `default_plants_per_bottle` SMALLINT NOT NULL DEFAULT 0 COMMENT '任务参数：每瓶株数',
  `task_status` VARCHAR(32) NOT NULL DEFAULT 'created' COMMENT '任务状态：dictionary.bottle_code_complement_task_stat',
  `bottles` INT NOT NULL DEFAULT 0 COMMENT '实际赋码瓶数',
  `plants` INT NOT NULL DEFAULT 0 COMMENT '实际赋码株数',
  `creator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人用户ID，user.id',
  `creator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '创建人姓名，user.name',
  `create_time` BIGINT NOT NULL DEFAULT 0 COMMENT '创建时间 UTC 毫秒时间戳',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '末次操作人用户ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '末次操作人姓名，user.name',
  `start_time` BIGINT NOT NULL DEFAULT 0 COMMENT '开始时间 UTC 毫秒时间戳',
  `finish_time` BIGINT NOT NULL DEFAULT 0 COMMENT '完成时间 UTC 毫秒时间戳',
  PRIMARY KEY (`id`)
) COMMENT='瓶体补码任务';

-- ==============================================
-- 瓶体补码任务明细
-- ==============================================
DROP TABLE IF EXISTS `bottle_code_complement_task_detail`;
CREATE TABLE `bottle_code_complement_task_detail` (
  `id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '瓶码(培养瓶ID, bottle.id)。',
  `id_task` VARCHAR(32) NOT NULL COMMENT '所属任务ID。bottle_code_complement_task.id',
  `id_parent` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '母瓶ID，空串代表母瓶为系统上线前的母瓶',
  `plant_count` SMALLINT NOT NULL DEFAULT 0 COMMENT '瓶内株数',
  `inspector_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '质检员ID，user.id',
  `inspector_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '质检员姓名，user.name',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作员ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作员姓名，user.name',
  `operator_time` BIGINT NOT NULL DEFAULT 0 COMMENT '操作时间 UTC 毫秒时间戳',
  `status` VARCHAR(32) NOT NULL DEFAULT 'created' COMMENT '赋码状态：dictionary.bottle_code_complement_code_stat',
  PRIMARY KEY (`id`),
  KEY `idx_bcctd_task` (`id_task`) COMMENT '根据任务ID查询'
) COMMENT='瓶体补码任务明细';
