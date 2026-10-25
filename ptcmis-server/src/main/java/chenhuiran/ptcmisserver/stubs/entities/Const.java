package chenhuiran.ptcmisserver.stubs.entities;

public interface Const {
  /**
   * <p>布尔值</p>
   * <p>假</p>
   */
  String BOOLEAN_0 = "0";

  /**
   * <p>布尔值</p>
   * <p>真</p>
   */
  String BOOLEAN_1 = "1";

  /**
   * <p>出入库类型</p>
   * <p>入库</p>
   */
  String STORAGE_IN_OUT_TYPE_IN = "in";

  /**
   * <p>出入库类型</p>
   * <p>出库</p>
   */
  String STORAGE_IN_OUT_TYPE_OUT = "out";

  /**
   * <p>出入库明细（培养瓶）出入库类别</p>
   * <p>子瓶入库</p>
   */
  String STORAGE_IN_OUT_BOTTLE_TYPE_CHILD_IN = "child_in";

  /**
   * <p>出入库明细（培养瓶）出入库类别</p>
   * <p>质检出库</p>
   */
  String STORAGE_IN_OUT_BOTTLE_TYPE_INTERCEPT_OUT = "intercept_out";

  /**
   * <p>出入库明细（培养瓶）出入库类别</p>
   * <p>库存增瓶</p>
   */
  String STORAGE_IN_OUT_BOTTLE_TYPE_INVENTORY_ADD = "inventory_add";

  /**
   * <p>出入库明细（培养瓶）出入库类别</p>
   * <p>库存减瓶</p>
   */
  String STORAGE_IN_OUT_BOTTLE_TYPE_INVENTORY_SUB = "inventory_sub";

  /**
   * <p>出入库明细（培养瓶）出入库类别</p>
   * <p>母瓶入库</p>
   */
  String STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_IN = "parent_in";

  /**
   * <p>出入库明细（培养瓶）出入库类别</p>
   * <p>留种入库</p>
   */
  String STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_KEEP_IN = "parent_keep_in";

  /**
   * <p>出入库明细（培养瓶）出入库类别</p>
   * <p>母瓶出库</p>
   */
  String STORAGE_IN_OUT_BOTTLE_TYPE_PARENT_OUT = "parent_out";

  /**
   * <p>出入库明细（培养瓶）状态</p>
   * <p>淘汰</p>
   */
  String STORAGE_IN_OUT_BOTTLE_STATUS_DISCARD = "discard";

  /**
   * <p>出入库明细（培养瓶）状态</p>
   * <p>完成</p>
   */
  String STORAGE_IN_OUT_BOTTLE_STATUS_DONE = "done";

  /**
   * <p>出入库明细（培养瓶）状态</p>
   * <p>正常</p>
   */
  String STORAGE_IN_OUT_BOTTLE_STATUS_NORMAL = "normal";

  /**
   * <p>出入库明细（培养瓶）状态</p>
   * <p>废弃</p>
   */
  String STORAGE_IN_OUT_BOTTLE_STATUS_VOIDED = "voided";

  /**
   * <p>出入库原因</p>
   * <p>炼苗</p>
   */
  String STORAGE_REASON_ACCLIMATIZATION = "acclimatization";

  /**
   * <p>出入库原因</p>
   * <p>瓶体补码</p>
   */
  String STORAGE_REASON_CODE_COMPLEMENT = "code_complement";

  /**
   * <p>出入库原因</p>
   * <p>分化</p>
   */
  String STORAGE_REASON_DIFFERENTIATION = "differentiation";

  /**
   * <p>出入库原因</p>
   * <p>诱导</p>
   */
  String STORAGE_REASON_INDUCTION = "induction";

  /**
   * <p>出入库原因</p>
   * <p>质检淘汰</p>
   */
  String STORAGE_REASON_INSPECTION_DISCARD = "inspection_discard";

  /**
   * <p>出入库原因</p>
   * <p>质检废弃</p>
   */
  String STORAGE_REASON_INSPECTION_VOID = "inspection_void";

  /**
   * <p>出入库原因</p>
   * <p>库存增瓶</p>
   */
  String STORAGE_REASON_INVENTORY_ADD = "inventory_add";

  /**
   * <p>出入库原因</p>
   * <p>库存减瓶</p>
   */
  String STORAGE_REASON_INVENTORY_SUB = "inventory_sub";

  /**
   * <p>出入库原因</p>
   * <p>增殖</p>
   */
  String STORAGE_REASON_MULTIPLICATION = "multiplication";

  /**
   * <p>出入库原因</p>
   * <p>生根</p>
   */
  String STORAGE_REASON_ROOTING = "rooting";

  /**
   * <p>出入库状态</p>
   * <p>已申请</p>
   */
  String STORAGE_IN_OUT_STATUS_APPLY = "apply";

  /**
   * <p>出入库状态</p>
   * <p>已取消</p>
   */
  String STORAGE_IN_OUT_STATUS_CANCELED = "canceled";

  /**
   * <p>出入库状态</p>
   * <p>已复核</p>
   */
  String STORAGE_IN_OUT_STATUS_CHECKED = "checked";

  /**
   * <p>出入库状态</p>
   * <p>已作业</p>
   */
  String STORAGE_IN_OUT_STATUS_DONE = "done";

  /**
   * <p>出入库状态</p>
   * <p>已办结</p>
   */
  String STORAGE_IN_OUT_STATUS_FINISHED = "finished";

  /**
   * <p>出入库状态</p>
   * <p>退经办</p>
   */
  String STORAGE_IN_OUT_STATUS_REJ_BIZ = "rej_biz";

  /**
   * <p>出入库状态</p>
   * <p>退库务</p>
   */
  String STORAGE_IN_OUT_STATUS_REJ_STORE = "rej_store";

  /**
   * <p>出入库状态</p>
   * <p>作业中</p>
   */
  String STORAGE_IN_OUT_STATUS_STARTED = "started";

  /**
   * <p>工作量流水业务类型</p>
   * <p>调差</p>
   */
  String WORKLOAD_TX_BIZ_TYPE_ADJUSTMENT = "adjustment";

  /**
   * <p>工作量流水业务类型</p>
   * <p>子瓶生产</p>
   */
  String WORKLOAD_TX_BIZ_TYPE_CHILD = "child";

  /**
   * <p>工作量流水业务类型</p>
   * <p>淘汰</p>
   */
  String WORKLOAD_TX_BIZ_TYPE_DISCARD = "discard";

  /**
   * <p>工作量流水业务类型</p>
   * <p>库存增瓶</p>
   */
  String WORKLOAD_TX_BIZ_TYPE_INVENTORY_IN = "inventory_in";

  /**
   * <p>工作量流水业务类型</p>
   * <p>库存减瓶</p>
   */
  String WORKLOAD_TX_BIZ_TYPE_INVENTORY_SUB = "inventory_sub";

  /**
   * <p>工作量流水业务类型</p>
   * <p>母瓶留种</p>
   */
  String WORKLOAD_TX_BIZ_TYPE_PARENT_KEEP = "parent_keep";

  /**
   * <p>库存流水业务类型</p>
   * <p>调差</p>
   */
  String INVENTORY_TX_BIZ_TYPE_ADJUSTMENT = "adjustment";

  /**
   * <p>库存流水业务类型</p>
   * <p>库存基线</p>
   */
  String INVENTORY_TX_BIZ_TYPE_BASELINE = "baseline";

  /**
   * <p>库存流水业务类型</p>
   * <p>入库</p>
   */
  String INVENTORY_TX_BIZ_TYPE_IN = "in";

  /**
   * <p>库存流水业务类型</p>
   * <p>出库</p>
   */
  String INVENTORY_TX_BIZ_TYPE_OUT = "out";

  /**
   * <p>盘点调差状态</p>
   * <p>已创建</p>
   */
  String INVENTORY_ADJUSTMENT_STATUS_CREATED = "created";

  /**
   * <p>盘点调差状态</p>
   * <p>已完成</p>
   */
  String INVENTORY_ADJUSTMENT_STATUS_FINISHED = "finished";

  /**
   * <p>盘点调差状态</p>
   * <p>执行中</p>
   */
  String INVENTORY_ADJUSTMENT_STATUS_STARTED = "started";

  /**
   * <p>培养瓶仓储状态</p>
   * <p>在库</p>
   */
  String BOTTLE_STORAGE_STATUS_IN = "in";

  /**
   * <p>培养瓶仓储状态</p>
   * <p>出库‑生命周期完结</p>
   */
  String BOTTLE_STORAGE_STATUS_OUT = "out";

  /**
   * <p>培养瓶操作状态</p>
   * <p>已分配操作</p>
   */
  String BOTTLE_OPERATE_STATUS_ASSIGNED = "assigned";

  /**
   * <p>培养瓶操作状态</p>
   * <p>任务待用</p>
   */
  String BOTTLE_OPERATE_STATUS_CODED = "coded";

  /**
   * <p>培养瓶操作状态</p>
   * <p>瓶体补码</p>
   */
  String BOTTLE_OPERATE_STATUS_COMPLEMENT = "complement";

  /**
   * <p>培养瓶操作状态</p>
   * <p>已操作完成（废弃）</p>
   */
  String BOTTLE_OPERATE_STATUS_DISCARD = "discard";

  /**
   * <p>培养瓶操作状态</p>
   * <p>已操作完成（成功）</p>
   */
  String BOTTLE_OPERATE_STATUS_FINISHED = "finished";

  /**
   * <p>培养瓶操作状态</p>
   * <p>库存增瓶</p>
   */
  String BOTTLE_OPERATE_STATUS_INVENTORY_ADD = "inventory_add";

  /**
   * <p>培养瓶操作状态</p>
   * <p>库存减瓶</p>
   */
  String BOTTLE_OPERATE_STATUS_INVENTORY_SUB = "inventory_sub";

  /**
   * <p>培养瓶操作状态</p>
   * <p>未曾使用</p>
   */
  String BOTTLE_OPERATE_STATUS_NONE = "none";

  /**
   * <p>培养瓶操作状态</p>
   * <p>已作废（因瓶码污损等原因无法分配）</p>
   */
  String BOTTLE_OPERATE_STATUS_VOIDED = "voided";

  /**
   * <p>培养瓶任务角色</p>
   * <p>子瓶</p>
   */
  String BOTTLE_TASK_ROLE_CHILD = "child";

  /**
   * <p>培养瓶任务角色</p>
   * <p>未在任务中</p>
   */
  String BOTTLE_TASK_ROLE_NONE = "none";

  /**
   * <p>培养瓶任务角色</p>
   * <p>母瓶</p>
   */
  String BOTTLE_TASK_ROLE_PARENT = "parent";

  /**
   * <p>培养瓶淘汰原因</p>
   * <p>在库</p>
   */
  String BOTTLE_DISCARD_REASON_IN = "in";

  /**
   * <p>培养瓶淘汰原因</p>
   * <p>出库‑生命周期完结</p>
   */
  String BOTTLE_DISCARD_REASON_OUT = "out";

  /**
   * <p>培养瓶淘汰原因</p>
   * <p>已出库待入库</p>
   */
  String BOTTLE_DISCARD_REASON_PENDING = "pending";

  /**
   * <p>瓶体补码瓶码状态</p>
   * <p>已核对</p>
   */
  String BOTTLE_CODE_COMPLEMENT_CODE_STAT_CHECKED = "checked";

  /**
   * <p>瓶体补码瓶码状态</p>
   * <p>已分配瓶码</p>
   */
  String BOTTLE_CODE_COMPLEMENT_CODE_STAT_CODED = "coded";

  /**
   * <p>瓶体补码瓶码状态</p>
   * <p>已打印</p>
   */
  String BOTTLE_CODE_COMPLEMENT_CODE_STAT_PRINTED = "printed";

  /**
   * <p>瓶体补码瓶码状态</p>
   * <p>补码中</p>
   */
  String BOTTLE_CODE_COMPLEMENT_CODE_STAT_PROCESS = "process";

  /**
   * <p>瓶体补码任务状态</p>
   * <p>已创建</p>
   */
  String BOTTLE_CODE_COMPLEMENT_TASK_STAT_CREATED = "created";

  /**
   * <p>瓶体补码任务状态</p>
   * <p>已完成</p>
   */
  String BOTTLE_CODE_COMPLEMENT_TASK_STAT_FINISHED = "finished";

  /**
   * <p>瓶体补码任务状态</p>
   * <p>执行中</p>
   */
  String BOTTLE_CODE_COMPLEMENT_TASK_STAT_STARTED = "started";

  /**
   * <p>任务子瓶瓶码状态</p>
   * <p>已核对</p>
   */
  String BOTTLE_CODE_STATUS_FOR_TASK_CHECKED = "checked";

  /**
   * <p>任务子瓶瓶码状态</p>
   * <p>初始状态</p>
   */
  String BOTTLE_CODE_STATUS_FOR_TASK_NONE = "none";

  /**
   * <p>任务子瓶瓶码状态</p>
   * <p>已打印</p>
   */
  String BOTTLE_CODE_STATUS_FOR_TASK_PRINTED = "printed";

  /**
   * <p>员工参与组培任务身份</p>
   * <p>质检员</p>
   */
  String TISSUE_TASK_USER_ROLE_INSPECTOR = "inspector";

  /**
   * <p>员工参与组培任务身份</p>
   * <p>操作员</p>
   */
  String TISSUE_TASK_USER_ROLE_OPERATOR = "operator";

  /**
   * <p>员工系统角色</p>
   * <p>管理员</p>
   */
  String USER_ROLE_ADMIN = "admin";

  /**
   * <p>员工系统角色</p>
   * <p>仓库管理权限</p>
   */
  String USER_ROLE_INVENTORY = "inventory";

  /**
   * <p>员工系统角色</p>
   * <p>无系统使用权限</p>
   */
  String USER_ROLE_NONE = "none";

  /**
   * <p>员工系统角色</p>
   * <p>仅开放查询权限</p>
   */
  String USER_ROLE_QUERY = "query";

  /**
   * <p>员工系统角色</p>
   * <p>任务执行权限</p>
   */
  String USER_ROLE_TASK = "task";

  /**
   * <p>员工系统角色</p>
   * <p>仓库管理及任务执行权限</p>
   */
  String USER_ROLE_TASK_AND_INVENTORY = "task_and_inventory";

  /**
   * <p>组培任务类型</p>
   * <p>炼苗</p>
   */
  String TISSUE_TASK_TYPE_ACCLIMATIZATION = "acclimatization";

  /**
   * <p>组培任务类型</p>
   * <p>分化</p>
   */
  String TISSUE_TASK_TYPE_DIFFERENTIATION = "differentiation";

  /**
   * <p>组培任务类型</p>
   * <p>诱导</p>
   */
  String TISSUE_TASK_TYPE_INDUCTION = "induction";

  /**
   * <p>组培任务类型</p>
   * <p>增殖</p>
   */
  String TISSUE_TASK_TYPE_MULTIPLICATION = "multiplication";

  /**
   * <p>组培任务类型</p>
   * <p>生根</p>
   */
  String TISSUE_TASK_TYPE_ROOTING = "rooting";

  /**
   * <p>组培任务状态</p>
   * <p>已取消</p>
   */
  String TISSUE_TASK_STATUS_CANCELED = "canceled";

  /**
   * <p>组培任务状态</p>
   * <p>已打码</p>
   */
  String TISSUE_TASK_STATUS_CODED = "coded";

  /**
   * <p>组培任务状态</p>
   * <p>未开始</p>
   */
  String TISSUE_TASK_STATUS_CREATED = "created";

  /**
   * <p>组培任务状态</p>
   * <p>已完成</p>
   */
  String TISSUE_TASK_STATUS_FINISHED = "finished";

  /**
   * <p>组培任务状态</p>
   * <p>执行中</p>
   */
  String TISSUE_TASK_STATUS_PROCESSING = "processing";
}
