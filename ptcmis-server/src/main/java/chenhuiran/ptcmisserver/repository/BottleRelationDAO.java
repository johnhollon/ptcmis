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

import chenhuiran.ptcmisserver.stubs.entities.BottleRelationPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BottleRelationDAO extends JpaRepository<BottleRelationPO, String>, JpaSpecificationExecutor<BottleRelationPO> {
  List<BottleRelationPO> findAllByChildIdIn(List<String> childId);

  // 获取某个瓶子的所有祖先（包含自身）
  @Query("SELECT br FROM BottleRelationPO br WHERE br.childId = :bottleId")
  List<BottleRelationPO> findParentRelations(@Param("bottleId") String bottleId);

  // 获取某个瓶子以及它全部后代ID集合
  @Query("SELECT DISTINCT br.childId FROM BottleRelationPO br WHERE br.parentId = :bottleId")
  List<String> findAllChildIds(@Param("bottleId") String bottleId);

  // 删除：child在后代集合，parent在旧祖先集合
  @Modifying
  @Query("DELETE FROM BottleRelationPO br WHERE br.childId IN :childIds AND br.parentId IN :oldParentIds")
  void deleteRelationForUpdateParentBottle(
    @Param("childIds") List<String> childIds,
    @Param("oldParentIds") List<String> oldParentIds
  );
}
