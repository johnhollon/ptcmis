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

import chenhuiran.ptcmisserver.stubs.entities.BottleCodeComplementTaskDetailPO;
import chenhuiran.ptcmisserver.stubs.entities.BottlePO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BottleCodeComplementTaskDetailDAO extends JpaRepository<BottleCodeComplementTaskDetailPO, String>, JpaSpecificationExecutor<BottleCodeComplementTaskDetailPO> {
  List<BottleCodeComplementTaskDetailPO> findByIdTaskOrderByIdAsc(String idTask);

  List<BottleCodeComplementTaskDetailPO> findByIdTaskAndStatus(String idTask, String status);

  List<BottleCodeComplementTaskDetailPO> findByIdTaskAndStatusIn(String idTask, List<String> statusList);

  @Modifying
  @Query("DELETE FROM BottleCodeComplementTaskDetailPO tu WHERE tu.idTask = :idTask")
  void deleteBatchByIdTask(@Param("idTask") String idTask);

  @Modifying
  @Query("UPDATE BottleCodeComplementTaskDetailPO tu SET tu.status = :newStatus WHERE tu.idTask = :idTask AND tu.status = :originalStatus")
  void updateAllStatus(@Param("idTask") String idTask, @Param("originalStatus") String originalStatus, @Param("newStatus") String newStatus);

  @Query("""
      SELECT count(d)
      FROM BottlePO d
      WHERE d.id in :idList
        AND d.variety = :variety
        AND d.varietyCode = :varietyCode
        AND d.explantCollectionBatch = :explantCollectionBatch
        AND d.generation = :generation
    """)
  long countForParentId(@Param("idList") List<String> idList, @Param("variety") String variety, @Param("varietyCode") String varietyCode, @Param("explantCollectionBatch") int explantCollectionBatch, @Param("generation") int generation);

  @Query("""
      SELECT count(d)
      FROM BottleCodeComplementTaskDetailPO d
      WHERE d.idTask = :idTask
        AND d.idParent is not null
        AND d.idParent <> ''
        AND NOT EXISTS (
         SELECT 1 FROM BottlePO b
         WHERE b.id = d.idParent
           AND b.variety = :variety
           AND b.varietyCode = :varietyCode
           AND b.explantCollectionBatch = :explantCollectionBatch
           AND b.generation = :generation
      )
    """)
  long countUnexistsParents(@Param("idTask") String idTask, @Param("variety") String variety, @Param("varietyCode") String varietyCode, @Param("explantCollectionBatch") int explantCollectionBatch, @Param("generation") int generation);

  @Query("""
      SELECT b
      FROM BottlePO b
      WHERE EXISTS (
         SELECT 1 FROM BottleCodeComplementTaskDetailPO d
         WHERE b.id = d.idParent AND d.idTask = :codeComplementTaskId
      )
    """)
  List<BottlePO> findForCodeComplementDetailParents(@Param("codeComplementTaskId") String codeComplementTaskId);

  @Query("select tu FROM BottleCodeComplementTaskDetailPO tu WHERE tu.idTask = :idTask order by tu.id desc")
  Page<BottleCodeComplementTaskDetailPO> queryChildBottleMaxCode(@Param("idTask") String idTask, Pageable pageable);
}
