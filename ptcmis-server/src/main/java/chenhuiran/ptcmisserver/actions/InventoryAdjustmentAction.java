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

import chenhuiran.ptcmisserver.managers.ArchiveManager;
import chenhuiran.ptcmisserver.managers.InventoryAdjustmentManager;
import chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustment;
import chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentDTO;
import chenhuiran.ptcmisserver.stubs.entities.InventoryAdjustmentLog;
import chenhuiran.ptcmisserver.stubs.entities.User;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryConditionInventoryAdjustment;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-adjustment")
public class InventoryAdjustmentAction {
  @Autowired
  private InventoryAdjustmentManager inventoryAdjustmentManager;
  @Autowired
  private ArchiveManager archiveManager;

  @PostMapping("/queryByCondition")
  public QueryResultPaged<? extends InventoryAdjustment> queryByCondition(@RequestBody QueryCondition<QueryConditionInventoryAdjustment> condition) {
    return inventoryAdjustmentManager.queryByCondition(condition);
  }

  @PostMapping("/queryAllLogs")
  public List<? extends InventoryAdjustmentLog> queryAllLogs(@RequestBody String idAdjustment) {
    return inventoryAdjustmentManager.queryAllLogs(idAdjustment);
  }

  @PostMapping("/save")
  public void save(HttpServletRequest request, @RequestBody InventoryAdjustmentDTO model) {
    inventoryAdjustmentManager.save(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/delete")
  public void delete(@RequestBody String taskId) {
    inventoryAdjustmentManager.delete(taskId);
    archiveManager.deleteByTableAndId(InventoryAdjustment.TB_NAME, taskId);
  }

  @PostMapping("/done")
  public void done(HttpServletRequest request, @RequestBody InventoryAdjustmentDTO model) {
    inventoryAdjustmentManager.done(model, (User) request.getAttribute("user"));
  }
}
