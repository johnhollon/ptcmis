DROP TABLE IF EXISTS `tissue_task_user`;
CREATE TABLE `tissue_task_user` (
  `id_task` VARCHAR(32) NOT NULL COMMENT '任务ID，tissue_task.id',
  `id_user` VARCHAR(32) NOT NULL COMMENT '员工ID，user.id',
  `role` VARCHAR(32) NOT NULL COMMENT '员工身份。dictionary.tissue_task_user_role',

  PRIMARY KEY (`id_task`, `id_user`,`role`)
) COMMENT='组培任务‑参与员工';
