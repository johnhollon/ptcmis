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

package chenhuiran.ptcmisserver.actions;

import chenhuiran.ptcmisserver.managers.StorageInventoryBaselineManager;
import chenhuiran.ptcmisserver.stubs.entities.Const;
import chenhuiran.ptcmisserver.stubs.entities.InventoryTransaction;
import chenhuiran.ptcmisserver.stubs.entities.InventoryTransactionDTO;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryConditionStorageInventoryBaseline;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import com.chenhuiran.commons.lang.annotations.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/storage-inventory-baseline")
public class StorageInventoryBaselineAction {

  @Autowired
  private StorageInventoryBaselineManager storageInventoryBaselineManager;

  @PostMapping("/queryByCondition")
  @Permission(Const.USER_ROLE_ADMIN)
  public QueryResultPaged<? extends InventoryTransaction> queryByCondition(@RequestBody QueryCondition<QueryConditionStorageInventoryBaseline> condition) {
    return storageInventoryBaselineManager.queryByCondition(condition);
  }

  @PostMapping("/save")
  @Permission(Const.USER_ROLE_ADMIN)
  public void save(@RequestBody InventoryTransactionDTO storageInventoryBaseline) {
    storageInventoryBaselineManager.save(storageInventoryBaseline);
  }
}
