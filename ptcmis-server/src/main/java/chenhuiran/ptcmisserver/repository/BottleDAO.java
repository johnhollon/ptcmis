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

import chenhuiran.ptcmisserver.stubs.entities.BottlePO;
import chenhuiran.ptcmisserver.stubs.models.ModelBottleSimpleImp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BottleDAO extends JpaRepository<BottlePO, String>, JpaSpecificationExecutor<BottlePO> {
  @Query(value = """
    SELECT COUNT(DISTINCT b.id)
    FROM BottlePO b
    WHERE b.variety = :variety
      AND b.varietyCode = :varietyCode
      AND (:currentStorageStatus IS NULL OR :currentStorageStatus = '' OR b.currentStorageStatus = :currentStorageStatus)
      AND (:discarded IS NULL OR :discarded = '' OR b.discarded = :discarded)
      AND b.id in :idList
    """)
  long countBottles1(@Param("variety") String variety,
                     @Param("varietyCode") String varietyCode,
                     @Param("currentStorageStatus") String currentStorageStatus,
                     @Param("discarded") String discarded,
                     @Param("idList") List<String> idList);

  @Query(value = """
    SELECT COUNT(DISTINCT b.id)
    FROM BottlePO b
    WHERE (:currentStorageStatus IS NULL OR :currentStorageStatus = '' OR b.currentStorageStatus = :currentStorageStatus)
      AND b.id in :idList
    """)
  long countBottles2(@Param("currentStorageStatus") String currentStorageStatus,
                     @Param("idList") List<String> idList);

  List<BottlePO> findAllByIdIn(List<String> idList);

  @Query(value = "select b.id from BottlePO b where b.currentStorageStatus = :currentStorageStatus")
  List<String> findAllByStorageStatus(@Param("currentStorageStatus") String currentStorageStatus);

  @Query(value = """
    select new chenhuiran.ptcmisserver.stubs.models.ModelBottleSimpleImp(
      b.id,
      b.plantCount,
      b.discarded,
      b.discardReason,
      b.taskTime,
      b.lastOperatorId,
      b.lastOperatorName,
      b.variety,
      b.varietyCode,
      b.explantCollectionBatch,
      b.generation
    ) from BottlePO b, StorageInOutBottlePO tb where b.id = tb.idBottle and tb.idInOut =:idInOut order by b.id
    """)
  List<ModelBottleSimpleImp> findBottlesForInventorySubBottle(@Param("idInOut") String idInOut);

  @Query(value = """
    SELECT b FROM BottlePO b
      INNER JOIN BottleRelationPO br ON b.id = br.parentId
      WHERE
        br.childId = :id
        AND br.depth <> 0
      ORDER BY br.depth DESC, br.parentId ASC
    """)
  List<BottlePO> findParents(@Param("id") String id);

  @Query("""
    SELECT b FROM BottlePO b
      INNER JOIN BottleRelationPO br ON b.id = br.childId
      WHERE
        br.parentId = :id
        AND br.depth <> 0
      ORDER BY br.depth ASC, br.childId ASC
  """)
  List<BottlePO> findChildren(@Param("id") String id);
}
