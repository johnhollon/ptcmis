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

import chenhuiran.ptcmisserver.stubs.entities.StorageInOutBottlePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StorageInOutBottleDAO extends JpaRepository<StorageInOutBottlePO, String>, JpaSpecificationExecutor<StorageInOutBottlePO> {
  List<StorageInOutBottlePO> findByIdInOutOrderByIdBottleAsc(String idInOut);

  @Modifying
  @Query("DELETE FROM StorageInOutBottlePO tu WHERE tu.idInOut = :idInOut")
  void deleteAllByIdInOut(@Param("idInOut") String idInOut);

  @Modifying
  @Query("DELETE FROM StorageInOutBottlePO tu WHERE tu.idInOut in :idInOut")
  void deleteAllByIdInOutIn(@Param("idInOut") List<String> idInOutList);
}
