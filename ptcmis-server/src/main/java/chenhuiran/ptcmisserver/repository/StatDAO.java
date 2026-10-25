/*
 * Copyright (C) 2026 陈慧然
 * (aka: Huiran Chen, chenhuiran, John Hollon, johnhollon)
 * ORCID: https://orcid.org/0009-0004-5899-6928
 * <chenhuiran@chenhuiran.com>
 * <johnhollon@johnhollon.com>
 *
 * This file is part of PTCMIS.
 *
 * PTCMIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PTCMIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package chenhuiran.ptcmisserver.repository;

import chenhuiran.ptcmisserver.stubs.entities.InventoryTransactionDTO;
import chenhuiran.ptcmisserver.stubs.entities.InventoryTransactionPO;
import chenhuiran.ptcmisserver.stubs.entities.WorkloadTransactionDTO;
import chenhuiran.ptcmisserver.stubs.entities.WorkloadTransactionPO;
import chenhuiran.ptcmisserver.stubs.models.ModeStatInventory;
import chenhuiran.ptcmisserver.stubs.models.ModeStatWorkload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatDAO extends JpaRepository<InventoryTransactionPO, Long> {
  @Query("""
    select new chenhuiran.ptcmisserver.stubs.models.ModeStatInventory(
        vc.variety,
        vc.code,
        t.generation,
    
        0L,
        0L,
        0L,
        0L,
        sum(case when t.operateTime <= :endTime then t.bottles else 0 end),
    
        0L,
        0L,
        0L,
        0L,
        sum(case when t.operateTime <= :endTime then t.plants else 0 end)
    )
    from InventoryTransactionPO t
    right join VarietyCodePO vc on t.varietyCode = vc.code
    where 1=1
      and (:variety is null or :variety = '' or vc.variety = :variety)
      and (:varietyCode is null or :varietyCode = '' or vc.code = :varietyCode)
      and (:varietyCodeImportant is null or :varietyCodeImportant = '' or vc.important = :varietyCodeImportant)
      and (:generation is null or :generation < 0 or t.generation = :generation)
    group by vc.variety, vc.code, t.generation
    order by vc.variety, vc.code, t.generation
    """)
  List<ModeStatInventory> statInventorySimple(
    @Param("endTime") Long endTime,
    @Param("variety") String variety,
    @Param("varietyCode") String varietyCode,
    @Param("varietyCodeImportant") String varietyCodeImportant,
    @Param("generation") Integer generation
  );

  @Query("""
    select new chenhuiran.ptcmisserver.stubs.models.ModeStatInventory(
        vc.variety,
        vc.code,
        t.generation,
    
        sum(case when t.operateTime < :startTime then t.bottles else 0 end),
        sum(case when (t.operateTime between :startTime and :endTime) and t.bizType = 'in' then t.bottles else 0 end),
        sum(case when (t.operateTime between :startTime and :endTime) and t.bizType = 'out' then t.bottles else 0 end),
        sum(case when (t.operateTime between :startTime and :endTime) and t.bizType = 'adjustment' then t.bottles else 0 end),
        sum(case when t.operateTime <= :endTime then t.bottles else 0 end),
    
        sum(case when t.operateTime < :startTime then t.plants else 0 end),
        sum(case when (t.operateTime between :startTime and :endTime) and t.bizType = 'in' then t.plants else 0 end),
        sum(case when (t.operateTime between :startTime and :endTime) and t.bizType = 'out' then t.plants else 0 end),
        sum(case when (t.operateTime between :startTime and :endTime) and t.bizType = 'adjustment' then t.plants else 0 end),
        sum(case when t.operateTime <= :endTime then t.plants else 0 end)
    )
    from InventoryTransactionPO t
    right join VarietyCodePO vc on t.varietyCode = vc.code
    where 1=1
      and (:variety is null or :variety = '' or vc.variety = :variety)
      and (:varietyCode is null or :varietyCode = '' or vc.code = :varietyCode)
      and (:varietyCodeImportant is null or :varietyCodeImportant = '' or vc.important = :varietyCodeImportant)
      and (:generation is null or :generation < 0 or t.generation = :generation)
    group by vc.variety, vc.code, t.generation
    order by vc.variety, vc.code, t.generation
    """)
  List<ModeStatInventory> statInventory(
    @Param("startTime") Long startTime,
    @Param("endTime") Long endTime,
    @Param("variety") String variety,
    @Param("varietyCode") String varietyCode,
    @Param("varietyCodeImportant") String varietyCodeImportant,
    @Param("generation") Integer generation
  );

  @Query(value = """
    SELECT new chenhuiran.ptcmisserver.stubs.entities.InventoryTransactionDTO(
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        0,
        CAST(SUM(it.bottles) AS java.lang.Integer),
        CAST(SUM(it.plants) AS java.lang.Integer),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        0L
    )
    FROM InventoryTransactionPO it
    right join VarietyCodePO vc on it.varietyCode = vc.code
    WHERE (:variety IS NULL OR :variety = '' OR it.variety = :variety)
      AND (:varietyCode IS NULL OR :varietyCode = '' OR it.varietyCode = :varietyCode)
      AND (:generation IS NULL OR :generation < 0 OR it.generation = :generation)
      AND (:varietyCodeImportant is null OR :varietyCodeImportant = '' OR vc.important = :varietyCodeImportant)
      AND (
          (:timeFlag = 'lt' AND it.operateTime < :time)
          OR (:timeFlag = 'le' AND it.operateTime <= :time)
      )
    """)
  List<InventoryTransactionDTO> statInventoryTransactionSum(
    @Param("variety") String variety,
    @Param("varietyCode") String varietyCode,
    @Param("varietyCodeImportant") String varietyCodeImportant,
    @Param("generation") Integer generation,
    @Param("time") Long time,
    @Param("timeFlag") String timeFlag
  );

  @Query(value = """
    SELECT it
    FROM InventoryTransactionPO it
    right join VarietyCodePO vc on it.varietyCode = vc.code
    WHERE (:variety IS NULL OR :variety = '' OR it.variety = :variety)
      AND (:varietyCode IS NULL OR :varietyCode = '' OR it.varietyCode = :varietyCode)
      AND (:generation IS NULL OR :generation < 0 OR it.generation = :generation)
      AND (:varietyCodeImportant is null OR :varietyCodeImportant = '' OR vc.important = :varietyCodeImportant)
      AND it.operateTime >= :startTime
      AND it.operateTime <= :endTime
    ORDER BY it.operateTime ASC, it.variety ASC, it.varietyCode ASC, it.generation ASC, it.bizType ASC
    """)
  List<InventoryTransactionPO> statInventoryTransactionDetail(
    @Param("variety") String variety,
    @Param("varietyCode") String varietyCode,
    @Param("varietyCodeImportant") String varietyCodeImportant,
    @Param("generation") Integer generation,
    @Param("startTime") Long startTime,
    @Param("endTime") Long endTime
  );

  @Query(value = """
    SELECT new chenhuiran.ptcmisserver.stubs.models.ModeStatWorkload(
        u.id,
        u.name,
        wt.userRole,
        wt.variety,
        wt.varietyCode,
        wt.generation,
    
        SUM(CASE WHEN (wt.operateTime < :startTime AND wt.bizType <> 'discard') THEN wt.bottles ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime BETWEEN :startTime AND :endTime AND wt.bizType = 'child') THEN wt.bottles ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime BETWEEN :startTime AND :endTime AND wt.bizType = 'parent_keep') THEN wt.bottles ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime BETWEEN :startTime AND :endTime AND (wt.bizType = 'inventory_in' OR wt.bizType = 'inventory_sub' OR wt.bizType = 'adjustment')) THEN wt.bottles ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime <= :endTime AND wt.bizType <> 'discard') THEN wt.bottles ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime < :startTime AND wt.bizType = 'discard') THEN wt.bottles ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime BETWEEN :startTime AND :endTime AND wt.bizType = 'discard') THEN wt.bottles ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime <= :endTime AND wt.bizType = 'discard') THEN wt.bottles ELSE 0L END),
    
        SUM(CASE WHEN (wt.operateTime < :startTime AND wt.bizType <> 'discard') THEN wt.plants ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime BETWEEN :startTime AND :endTime AND wt.bizType = 'child') THEN wt.plants ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime BETWEEN :startTime AND :endTime AND wt.bizType = 'parent_keep') THEN wt.plants ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime BETWEEN :startTime AND :endTime AND (wt.bizType = 'inventory_in' OR wt.bizType = 'inventory_sub' OR wt.bizType = 'adjustment')) THEN wt.plants ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime <= :endTime AND wt.bizType <> 'discard') THEN wt.plants ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime < :startTime AND wt.bizType = 'discard') THEN wt.plants ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime BETWEEN :startTime AND :endTime AND wt.bizType = 'discard') THEN wt.plants ELSE 0L END),
        SUM(CASE WHEN (wt.operateTime <= :endTime AND wt.bizType = 'discard') THEN wt.plants ELSE 0L END)
    )
    FROM WorkloadTransactionPO wt
    right join UserPO u on wt.userId = u.id
    WHERE u.enabled = '1'
      AND (:userId IS NULL OR :userId = '' OR wt.userId = :userId)
      AND (:userRole IS NULL OR :userRole = '' OR wt.userRole = :userRole)
      AND (:variety IS NULL OR :variety = '' OR wt.variety = :variety)
      AND (:varietyCode IS NULL OR :varietyCode = '' OR wt.varietyCode = :varietyCode)
      AND (:generation IS NULL OR :generation < 0 OR wt.generation = :generation)
      AND wt.operateTime <= :endTime
    GROUP BY u.id, u.name, wt.userRole, wt.variety, wt.varietyCode, wt.generation
    ORDER BY wt.userRole ASC, u.id ASC, u.name ASC, wt.variety ASC, wt.varietyCode ASC, wt.generation ASC
    """)
  List<ModeStatWorkload> statWorkload(
    @Param("userId") String userId,
    @Param("userRole") String userRole,
    @Param("variety") String variety,
    @Param("varietyCode") String varietyCode,
    @Param("generation") Integer generation,
    @Param("startTime") Long startTime,
    @Param("endTime") Long endTime
  );

  @Query(value = """
    SELECT new chenhuiran.ptcmisserver.stubs.entities.WorkloadTransactionDTO(
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        0,
        CAST(SUM(wt.bottles) AS java.lang.Integer),
        CAST(SUM(wt.plants) AS java.lang.Integer),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        CAST('——' AS java.lang.String),
        0L
    )
    FROM WorkloadTransactionPO wt
    right join UserPO u on wt.userId = u.id
    WHERE u.enabled = '1'
      AND (:userId IS NULL OR :userId = '' OR wt.userId = :userId)
      AND (:userRole IS NULL OR :userRole = '' OR wt.userRole = :userRole)
      AND (:variety IS NULL OR :variety = '' OR wt.variety = :variety)
      AND (:varietyCode IS NULL OR :varietyCode = '' OR wt.varietyCode = :varietyCode)
      AND (:generation IS NULL OR :generation < 0 OR wt.generation = :generation)
      AND (
          (:timeFlag = 'lt' AND wt.operateTime < :time)
          OR (:timeFlag = 'le' AND wt.operateTime <= :time)
      )
    """)
  List<WorkloadTransactionDTO> statWorkloadTransactionSum(
    @Param("userId") String userId,
    @Param("userRole") String userRole,
    @Param("variety") String variety,
    @Param("varietyCode") String varietyCode,
    @Param("generation") Integer generation,
    @Param("time") Long time,
    @Param("timeFlag") String timeFlag
  );

  @Query(value = """
    SELECT wt
    FROM WorkloadTransactionPO wt
    right join UserPO u on wt.userId = u.id
    WHERE u.enabled = '1'
      AND (:userId IS NULL OR :userId = '' OR wt.userId = :userId)
      AND (:userRole IS NULL OR :userRole = '' OR wt.userRole = :userRole)
      AND (:variety IS NULL OR :variety = '' OR wt.variety = :variety)
      AND (:varietyCode IS NULL OR :varietyCode = '' OR wt.varietyCode = :varietyCode)
      AND (:generation IS NULL OR :generation < 0 OR wt.generation = :generation)
      AND wt.operateTime >= :startTime
      AND wt.operateTime <= :endTime
    ORDER BY wt.operateTime ASC, wt.userRole ASC, wt.userId ASC, wt.userName ASC, wt.variety ASC, wt.varietyCode ASC, wt.generation ASC, wt.bizType ASC
    """)
  List<WorkloadTransactionPO> statWorkloadTransactionDetail(
    @Param("userId") String userId,
    @Param("userRole") String userRole,
    @Param("variety") String variety,
    @Param("varietyCode") String varietyCode,
    @Param("generation") Integer generation,
    @Param("startTime") Long startTime,
    @Param("endTime") Long endTime
  );
}
