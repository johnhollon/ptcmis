-- ==============================================
-- 出/入库单明细
-- ==============================================
DROP TABLE IF EXISTS `storage_in_out_bottle`;
CREATE TABLE `storage_in_out_bottle` (
  `id_in_out` VARCHAR(32) NOT NULL COMMENT 'ID主键',
  `id_bottle` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '瓶码(培养瓶ID, bottle.id)。联合主键，联合唯一索引',
  `variety` VARCHAR(32) NOT NULL COMMENT '所属品种。variety.id',
  `variety_code` VARCHAR(32) NOT NULL COMMENT '子品种编号。主键',
  `explant_collection_batch` INT NOT NULL DEFAULT 0 COMMENT '外植体采集批次',
  `generation` TINYINT NOT NULL DEFAULT 0 COMMENT '代数',
  `ignore_stats` VARCHAR(32) NOT NULL DEFAULT '0' COMMENT '是否在统计中忽略该明细。 dictionary = boolean',
  `in_out_bottle_type` VARCHAR(32) NOT NULL COMMENT '培养瓶出入库类别（母瓶出库、子瓶入库、留种入库、母瓶未用入库等）。 dictionary = storage_in_out_bottle_type',
  `plant_count_applied` SMALLINT NOT NULL DEFAULT 0 COMMENT '瓶内株数（申请出入库数量，均记正数）',
  `plant_count_actual` SMALLINT NOT NULL DEFAULT 0 COMMENT '瓶内株数（实际出入库数量，均记正数）',
  `keep` VARCHAR(32) NOT NULL DEFAULT '0' COMMENT '若作为母瓶入库，是否为留种入库。 dictionary.boolean',
  `status` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '出入库明细（培养瓶）状态：正常位于出入库列表中/已正常完成出入库/废弃/淘汰。 dictionary.storage_in_out_bottle_status',
  `discard_reason` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '淘汰原因',
  PRIMARY KEY (`id_in_out`,`id_bottle`)
) COMMENT='出/入库单明细';
