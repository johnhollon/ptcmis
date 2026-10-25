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

import chenhuiran.ptcmisserver.managers.CodePrintManager;
import chenhuiran.ptcmisserver.stubs.entities.CodePrint;
import chenhuiran.ptcmisserver.stubs.entities.CodePrintDetail;
import chenhuiran.ptcmisserver.stubs.entities.User;
import chenhuiran.ptcmisserver.stubs.models.ModelCodePrint;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryConditionCodePrint;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/code-print")
public class CodePrintAction {
  @Autowired
  private CodePrintManager inventoryAdjustmentManager;

  @PostMapping("/queryByCondition")
  public QueryResultPaged<? extends CodePrint> queryByCondition(@RequestBody QueryCondition<QueryConditionCodePrint> condition) {
    return inventoryAdjustmentManager.queryByCondition(condition);
  }

  @PostMapping("/queryAllDetails")
  public List<? extends CodePrintDetail> queryAllDetails(@RequestBody String idPrint) {
    return inventoryAdjustmentManager.queryAllDetails(idPrint);
  }

  @PostMapping("/save")
  public void save(HttpServletRequest request, @RequestBody ModelCodePrint model) {
    inventoryAdjustmentManager.save(model, (User) request.getAttribute("user"));
  }
}
