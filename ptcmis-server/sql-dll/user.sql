DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` VARCHAR(32) NOT NULL COMMENT '员工ID。主键',
  `name` VARCHAR(32) NOT NULL COMMENT '姓名',
  `code` VARCHAR(10) NOT NULL COMMENT '编号',
  `mobile` VARCHAR(16) NOT NULL COMMENT '手机号码',
  `password` VARCHAR(16) NOT NULL COMMENT '密码',
  `enabled` VARCHAR(1) NOT NULL DEFAULT '1' COMMENT '是否有效，dictionary = boolean',
  `role` VARCHAR(10) NOT NULL DEFAULT 'none' COMMENT '员工系统角色，dictionary = user_role',
  PRIMARY KEY (`id`)
) COMMENT='用户（员工信息）';

insert into user (id, name, code, mobile, password, enabled, role) values ('0', '超级管理员', 'SUPERADMIN', 'admin', 'admin', '1', 'admin');
