SELECT COUNT(1) FROM variety_code;
SELECT COUNT(1) FROM variety_code WHERE variety = '甘草';
SELECT COUNT(1) FROM variety_code WHERE variety = '桔梗';
SELECT * FROM variety_code WHERE variety != '甘草' AND variety != '桔梗';
SELECT COUNT(1) FROM variety_code WHERE important = '1';


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

TRUNCATE TABLE inventory_transaction;
INSERT INTO inventory_transaction
SELECT CONCAT('BASEG0', variety_code), 'baseline', '','', variety, variety_code, 0, 0, g0, '', '', '', -377423280000000 FROM inv_temp WHERE g0 IS NOT NULL AND g0 != 0
UNION ALL
SELECT CONCAT('BASEG1', variety_code), 'baseline', '','', variety, variety_code, 1, 0, g1, '', '', '', -377423280000000 FROM inv_temp WHERE g1 IS NOT NULL AND g1 != 0 
UNION ALL
SELECT CONCAT('BASEG2', variety_code), 'baseline', '','', variety, variety_code, 2, 0, g2, '', '', '', -377423280000000 FROM inv_temp WHERE g2 IS NOT NULL AND g2 != 0 
UNION ALL
SELECT CONCAT('BASEG3', variety_code), 'baseline', '','', variety, variety_code, 3, 0, g3, '', '', '', -377423280000000 FROM inv_temp WHERE g3 IS NOT NULL AND g3 != 0 
UNION ALL
SELECT CONCAT('BASEG4', variety_code), 'baseline', '','', variety, variety_code, 4, 0, g4, '', '', '', -377423280000000 FROM inv_temp WHERE g4 IS NOT NULL AND g4 != 0 
UNION ALL
SELECT CONCAT('BASEG5', variety_code), 'baseline', '','', variety, variety_code, 5, 0, g5, '', '', '', -377423280000000 FROM inv_temp WHERE g5 IS NOT NULL AND g5 != 0 
UNION ALL
SELECT CONCAT('BASEG6', variety_code), 'baseline', '','', variety, variety_code, 6, 0, g6, '', '', '', -377423280000000 FROM inv_temp WHERE g6 IS NOT NULL AND g6 != 0 
UNION ALL
SELECT CONCAT('BASEG7', variety_code), 'baseline', '','', variety, variety_code, 7, 0, g7, '', '', '', -377423280000000 FROM inv_temp WHERE g7 IS NOT NULL AND g7 != 0 ;

