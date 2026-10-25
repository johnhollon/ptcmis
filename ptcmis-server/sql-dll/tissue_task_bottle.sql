DROP TABLE IF EXISTS `tissue_task_bottle`;
CREATE TABLE `tissue_task_bottle` (
  `id_task` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '任务ID。tissue_task.id。联合主键',
  `id_opp_group` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作分配组ID。 tissue_task_opp_group.id。联合主键，联合唯一索引',
  `id_bottle` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引',
  `explant_collection_batch` INT NOT NULL DEFAULT 0 COMMENT '外植体采集批次',
  `generation` TINYINT NOT NULL DEFAULT 0 COMMENT '代际',
  `plant_count_original` SMALLINT NOT NULL DEFAULT 0 COMMENT '瓶内株数（任务前）',
  `plant_count_actual` SMALLINT NOT NULL DEFAULT 0 COMMENT '瓶内株数（实际）',
  `role_in_task` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '任务角色。 dictionary.bottle_task_role',
  `parent_keep` VARCHAR(32) NOT NULL DEFAULT '0' COMMENT '若作为母瓶使用，是否留种。 dictionary.boolean',
  `parent_keep_plant_count` SMALLINT NOT NULL DEFAULT 0 COMMENT '若作为母瓶使用，且留种，留种株数。',
  'parent_last_task_type' VARCHAR(32) NOT NULL DEFAULT '' COMMENT '母瓶产生来源，用于子代是否递增代数。 bottle.source_type, dictionary.storage_reason',
  `operate_status` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作状态。 dictionary.bottle_operate_status',
  `child_code_status` VARCHAR(32) NOT NULL DEFAULT 'none' COMMENT '子瓶瓶码状态。 dictionary.bottle_code_status_for_task',
  `discard_reason` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '若淘汰，体现淘汰原因。',
  `allocate_time` BIGINT NOT NULL DEFAULT 0 COMMENT '分配时间 UTC毫秒',
  `finish_time` BIGINT NOT NULL DEFAULT 0 COMMENT '完成时间 UTC毫秒',
  `inspector_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '质检员ID，user.id',
  `inspector_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '质检员姓名，user.name',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作员ID，user.id',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '操作员姓名，user.name',

  PRIMARY KEY (`id_task`,`id_bottle`)
) COMMENT='组培任务-操作分配组‑培养瓶关联明细';
