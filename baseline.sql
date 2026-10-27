# Excel公式（基于库存简表Excel模板，需删除重点品种列），株数：
="insert into inv_temp values ("&IF(A4="","NULL","'"&A4&"'")&","&IF(B4="","NULL","'"&B4&"'")&","&IF(C4="","NULL",C4)&","&IF(D4="","NULL",D4)&","&IF(E4="","NULL",E4)&","&IF(F4="","NULL",F4)&","&IF(G4="","NULL",G4)&","&IF(H4="","NULL",H4)&","&IF(I4="","NULL",I4)&","&IF(J4="","NULL",J4)&");"
# Excel公式（基于库存简表Excel模板，需删除重点品种列），瓶数：
="insert into inv_temp_1 values ("&IF(A4="","NULL","'"&A4&"'")&","&IF(B4="","NULL","'"&B4&"'")&","&IF(C4="","NULL",C4)&","&IF(D4="","NULL",D4)&","&IF(E4="","NULL",E4)&","&IF(F4="","NULL",F4)&","&IF(G4="","NULL",G4)&","&IF(H4="","NULL",H4)&","&IF(I4="","NULL",I4)&","&IF(J4="","NULL",J4)&");"

# 创建临时表

# 7代株数：
CREATE TABLE `inv_temp` (
  `variety` varchar(32) NOT NULL,
  `variety_code` varchar(32) NOT NULL,
  `g0` int DEFAULT NULL,
  `g1` int DEFAULT NULL,
  `g2` int DEFAULT NULL,
  `g3` int DEFAULT NULL,
  `g4` int DEFAULT NULL,
  `g5` int DEFAULT NULL,
  `g6` int DEFAULT NULL,
  `g7` int DEFAULT NULL,
  PRIMARY KEY (`variety_code`)
) ;
# 7代瓶数：
CREATE TABLE `inv_temp_1` (
  `variety` varchar(32) NOT NULL,
  `variety_code` varchar(32) NOT NULL,
  `g0` int DEFAULT NULL,
  `g1` int DEFAULT NULL,
  `g2` int DEFAULT NULL,
  `g3` int DEFAULT NULL,
  `g4` int DEFAULT NULL,
  `g5` int DEFAULT NULL,
  `g6` int DEFAULT NULL,
  `g7` int DEFAULT NULL,
  PRIMARY KEY (`variety_code`)
) ;






# 执行插入库存基线信息
DELETE FROM inventory_transaction WHERE biz_type = 'baseline' AND id LIKE 'BASEG%';
INSERT INTO inventory_transaction
(
    id,
    biz_type,
    TABLE_NAME,
    record_id,
    variety,
    variety_code,
    generation,
    bottles,
    plants,
    DESCRIPTION,
    operator_id,
    operator_name,
    operate_time
)
WITH wide_src AS (
    SELECT
        COALESCE(t.variety, t1.variety) AS variety,
        COALESCE(t.variety_code, t1.variety_code) AS variety_code,
        COALESCE(t.g0, 0) AS g0p,
        COALESCE(t.g1, 0) AS g1p,
        COALESCE(t.g2, 0) AS g2p,
        COALESCE(t.g3, 0) AS g3p,
        COALESCE(t.g4, 0) AS g4p,
        COALESCE(t.g5, 0) AS g5p,
        COALESCE(t.g6, 0) AS g6p,
        COALESCE(t.g7, 0) AS g7p,
        COALESCE(t1.g0, 0) AS g0b,
        COALESCE(t1.g1, 0) AS g1b,
        COALESCE(t1.g2, 0) AS g2b,
        COALESCE(t1.g3, 0) AS g3b,
        COALESCE(t1.g4, 0) AS g4b,
        COALESCE(t1.g5, 0) AS g5b,
        COALESCE(t1.g6, 0) AS g6b,
        COALESCE(t1.g7, 0) AS g7b
    FROM inv_temp t
    LEFT JOIN inv_temp_1 t1 ON t.variety_code = t1.variety_code
    UNION
    SELECT
        t1.variety,
        t1.variety_code,
        0 AS g0p,0 AS g1p,0 AS g2p,0 AS g3p,0 AS g4p,0 AS g5p,0 AS g6p,0 AS g7p,
        t1.g0,t1.g1,t1.g2,t1.g3,t1.g4,t1.g5,t1.g6,t1.g7
    FROM inv_temp_1 t1
    WHERE NOT EXISTS (SELECT 1 FROM inv_temp t WHERE t.variety_code = t1.variety_code)
),
num AS (
    SELECT 0 AS g UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3
    UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7
)
SELECT
    CONCAT('BASEG', num.g, w.variety_code),
    'baseline',
    '',
    '',
    w.variety,
    w.variety_code,
    num.g AS generation,
    CASE num.g
        WHEN 0 THEN w.g0b WHEN 1 THEN w.g1b WHEN 2 THEN w.g2b WHEN 3 THEN w.g3b
        WHEN 4 THEN w.g4b WHEN 5 THEN w.g5b WHEN 6 THEN w.g6b WHEN 7 THEN w.g7b
    END AS bottles,
    CASE num.g
        WHEN 0 THEN w.g0p WHEN 1 THEN w.g1p WHEN 2 THEN w.g2p WHEN 3 THEN w.g3p
        WHEN 4 THEN w.g4p WHEN 5 THEN w.g5p WHEN 6 THEN w.g6p WHEN 7 THEN w.g7p
    END AS plants,
    '',
    '',
    '',
    -377423280000000
FROM wide_src w
CROSS JOIN num
WHERE NOT (
    (CASE num.g
        WHEN 0 THEN w.g0b WHEN 1 THEN w.g1b WHEN 2 THEN w.g2b WHEN 3 THEN w.g3b
        WHEN 4 THEN w.g4b WHEN 5 THEN w.g5b WHEN 6 THEN w.g6b WHEN 7 THEN w.g7b
    END = 0)
    AND
    (CASE num.g
        WHEN 0 THEN w.g0p WHEN 1 THEN w.g1p WHEN 2 THEN w.g2p WHEN 3 THEN w.g3p
        WHEN 4 THEN w.g4p WHEN 5 THEN w.g5p WHEN 6 THEN w.g6p WHEN 7 THEN w.g7p
    END = 0)
)
AND NOT EXISTS (
    SELECT 1
    FROM inventory_transaction tx
    WHERE tx.biz_type = 'baseline'
      AND tx.variety_code = w.variety_code
      AND tx.generation = num.g
);
