DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `id` varchar(32) NOT NULL COMMENT '数据字典类型ID',
  `name` varchar(32) NOT NULL COMMENT '数据字典类型名称',
  PRIMARY KEY (`id`)
) COMMENT='数据字典类型';

DROP TABLE IF EXISTS `dictionary_item`;
CREATE TABLE `dictionary_item` (
  `id_dictionary` varchar(32) NOT NULL COMMENT '所属数据字典类型ID，dictionary.id。联合主键',
  `code` varchar(32) NOT NULL COMMENT '数据字典项目编码。联合主键',
  `name` varchar(32) NOT NULL COMMENT '数据字典项目名称',
  PRIMARY KEY (`id_dictionary`,`code`)
) COMMENT='数据字典项目';

-- 布尔值
insert into dictionary (id, name) values('boolean', '布尔值');
-- 布尔值
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('boolean', '0', '假'),
('boolean', '1', '真');

-- 员工系统角色
insert into dictionary (id, name) values('user_role', '员工系统角色');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('user_role','admin','管理员'),
('user_role','query','仅开放查询权限'),
('user_role','inventory','仓库管理权限'),
('user_role','task','任务执行权限'),
('user_role','task_and_inventory','仓库管理及任务执行权限'),
('user_role','none','无系统使用权限');

-- 组培任务类型
insert into dictionary (id, name) values('tissue_task_type', '组培任务类型');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('tissue_task_type','induction','诱导'),
('tissue_task_type','differentiation','分化'),
('tissue_task_type','multiplication','增殖'),
('tissue_task_type','rooting','生根'),
('tissue_task_type','acclimatization','炼苗');

-- 组培任务状态
insert into dictionary (id, name) values('tissue_task_status', '组培任务状态');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('tissue_task_status','created','未开始'),
('tissue_task_status','coded','已打码'),
('tissue_task_status','processing','执行中'),
('tissue_task_status','canceled','已取消'),
('tissue_task_status','finished','已完成');


-- 培养瓶任务角色
insert into dictionary (id, name) values('bottle_task_role', '培养瓶任务角色');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('bottle_task_role','parent','母瓶'),
('bottle_task_role','child','子瓶'),
('bottle_task_role','none','未在任务中');

-- 培养瓶操作状态
insert into dictionary (id, name) values('bottle_operate_status', '培养瓶操作状态');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('bottle_operate_status','coded','任务待用'),
('bottle_operate_status','assigned','已分配操作'),
('bottle_operate_status','finished','已操作完成（成功）'),
('bottle_operate_status','discard','已操作完成（废弃）'),
('bottle_operate_status','voided','已作废（因瓶码污损等原因无法分配）'),
('bottle_operate_status','none','未曾使用'),
('bottle_operate_status','inventory_add','库存增瓶'),
('bottle_operate_status','inventory_sub','库存减瓶'),
('bottle_operate_status','complement','瓶体补码');

-- 任务子瓶瓶码状态
insert into dictionary (id, name) values('bottle_code_status_for_task', '任务子瓶瓶码状态');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('bottle_code_status_for_task','none','初始状态'),
('bottle_code_status_for_task','printed','已打印'),
('bottle_code_status_for_task','checked','已核对');

-- 培养瓶仓储状态
insert into dictionary (id, name) values('bottle_storage_status', '培养瓶仓储状态');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('bottle_storage_status','in','在库'),
('bottle_storage_status','out','出库‑生命周期完结');

-- 培养瓶淘汰原因
insert into dictionary (id, name) values('bottle_discard_reason', '培养瓶淘汰原因');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('bottle_discard_reason','in','在库'),
('bottle_discard_reason','pending','已出库待入库'),
('bottle_discard_reason','out','出库‑生命周期完结');

-- 员工参与组培任务身份
insert into dictionary (id, name) values('tissue_task_user_role', '员工参与组培任务身份');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('tissue_task_user_role','operator','操作员'),
('tissue_task_user_role','inspector','质检员');

-- 出入库类型
insert into dictionary (id, name) values('storage_in_out_type', '出入库类型');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('storage_in_out_type','in','入库'),
('storage_in_out_type','out','出库');

-- 出入库状态
INSERT INTO dictionary (id, name) VALUES('storage_in_out_status', '出入库状态');
INSERT INTO dictionary_item (id_dictionary, code, name) VALUES
('storage_in_out_status','apply','已申请'),
('storage_in_out_status','started','作业中'),
('storage_in_out_status','done','已作业'),
('storage_in_out_status','rej_store','退库务'),
('storage_in_out_status','rej_biz','退经办'),
('storage_in_out_status','canceled','已取消'),
('storage_in_out_status','checked','已复核'),
('storage_in_out_status','finished','已办结');

-- 出入库原因
insert into dictionary (id, name) values('storage_reason', '出入库原因');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('storage_reason','induction','诱导'),
('storage_reason','differentiation','分化'),
('storage_reason','multiplication','增殖'),
('storage_reason','rooting','生根'),
('storage_reason','acclimatization','炼苗'),
('storage_reason','code_complement','瓶体补码'),
('storage_reason','inventory_add','库存增瓶'),
('storage_reason','inventory_sub','库存减瓶'),
('storage_reason','inspection_discard','质检淘汰'),
('storage_reason','inspection_void','质检废弃');

-- 出入库明细（培养瓶）状态
insert into dictionary (id, name) values('storage_in_out_bottle_status', '出入库明细（培养瓶）状态');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('storage_in_out_bottle_status','normal','正常'),
('storage_in_out_bottle_status','done','完成'),
('storage_in_out_bottle_status','voided','废弃'),
('storage_in_out_bottle_status','discard','淘汰');

-- 出入库明细（培养瓶）出入库类别
insert into dictionary (id, name) values('storage_in_out_bottle_type', '出入库明细（培养瓶）出入库类别');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('storage_in_out_bottle_type','parent_out','母瓶出库'),
('storage_in_out_bottle_type','intercept_out','质检出库'),
('storage_in_out_bottle_type','parent_in','母瓶入库'),
('storage_in_out_bottle_type','parent_keep_in','留种入库'),
('storage_in_out_bottle_type','child_in','子瓶入库'),
('storage_in_out_bottle_type','inventory_add','库存增瓶'),
('storage_in_out_bottle_type','inventory_sub','库存减瓶');

-- 盘点调差状态
insert into dictionary (id, name) values('inventory_adjustment_status', '盘点调差状态');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('inventory_adjustment_status','created','已创建'),
('inventory_adjustment_status','started','执行中'),
('inventory_adjustment_status','finished','已完成');

-- 瓶体补码任务状态
insert into dictionary (id, name) values('bottle_code_complement_task_stat', '瓶体补码任务状态');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('bottle_code_complement_task_stat','created','已创建'),
('bottle_code_complement_task_stat','started','执行中'),
('bottle_code_complement_task_stat','finished','已完成');


-- 瓶体补码瓶码状态
insert into dictionary (id, name) values('bottle_code_complement_code_stat', '瓶体补码瓶码状态');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('bottle_code_complement_code_stat','coded','已分配瓶码'),
('bottle_code_complement_code_stat','process','补码中'),
('bottle_code_complement_code_stat','printed','已打印'),
('bottle_code_complement_code_stat','checked','已核对');


-- 库存流水业务类型
insert into dictionary (id, name) values('inventory_tx_biz_type', '库存流水业务类型');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('inventory_tx_biz_type','baseline','库存基线'),
('inventory_tx_biz_type','in','入库'),
('inventory_tx_biz_type','out','出库'),
('inventory_tx_biz_type','adjustment','调差');

-- 工作量流水业务类型
insert into dictionary (id, name) values('workload_tx_biz_type', '工作量流水业务类型');
INSERT INTO dictionary_item (id_dictionary,code,name) VALUES
('workload_tx_biz_type','child','子瓶生产'),
('workload_tx_biz_type','inventory_in','库存增瓶'),
('workload_tx_biz_type','inventory_sub','库存减瓶'),
('workload_tx_biz_type','parent_keep','母瓶留种'),
('workload_tx_biz_type','discard','淘汰'),
('workload_tx_biz_type','adjustment','调差');

