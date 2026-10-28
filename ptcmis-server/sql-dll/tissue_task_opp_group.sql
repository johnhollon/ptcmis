DROP TABLE IF EXISTS `tissue_task_opp_group`;

CREATE TABLE `tissue_task_opp_group` (
  `id` VARCHAR(32) NOT NULL COMMENT '操作分配组ID。主键',
  `id_task` VARCHAR(32) NOT NULL COMMENT '所属任务ID，tissue_task.id',
  `variety_code` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '品种编号（子品种） variety_code.code',
  `parent_plant_count` SMALLINT NOT NULL DEFAULT 0 COMMENT '母瓶株数。',
  `id_parent_bottle` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '母瓶瓶码（ID） bottle.id',
  `parent_keep` VARCHAR(32) NOT NULL DEFAULT '0' COMMENT '母瓶是否留种。 dictionary.boolean',
  `parent_keep_plant_count` SMALLINT NOT NULL DEFAULT 0 COMMENT '母瓶留种株数。',
  `allocator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '分配人ID（员工ID），user.id',
  `allocator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '分配人姓名，user.name',
  `allocate_time` BIGINT NOT NULL DEFAULT 0 COMMENT '分配时间 UTC毫秒',
  `finish_time` BIGINT NOT NULL DEFAULT 0 COMMENT '完成时间 UTC毫秒',
  `assignee_inspector_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '被分配质检员ID（计划指派），user.id',
  `assignee_inspector_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '被分配质检员姓名，user.name',
  `assignee_operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '被分配操作员ID（计划指派），user.id',
  `assignee_operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '被分配操作员姓名，user.name',
  `inspector_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '实际质检员ID，user.name',
  `inspector_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '实际质检员姓名，user.name',
  `operator_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '实际操作员ID，user.name',
  `operator_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '实际操作员姓名，user.name',
  PRIMARY KEY (`id`),
  KEY `idx_tissue_task_opp_group_task` (`id_task`) COMMENT '根据任务查询'
) COMMENT='组培任务‑操作分配组';
