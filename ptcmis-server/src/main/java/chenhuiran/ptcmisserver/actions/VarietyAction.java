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

import chenhuiran.ptcmisserver.managers.VarietyManager;
import chenhuiran.ptcmisserver.stubs.entities.Const;
import chenhuiran.ptcmisserver.stubs.entities.Variety;
import chenhuiran.ptcmisserver.stubs.entities.VarietyDTO;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import com.chenhuiran.commons.lang.annotations.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/variety")
public class VarietyAction {

  @Autowired
  private VarietyManager varietyManager;

  @PostMapping("/queryByCondition")
  @Permission(Const.USER_ROLE_ADMIN)
  public QueryResultPaged<? extends Variety> queryByCondition(@RequestBody QueryCondition<String> condition) {
    return varietyManager.queryByCondition(condition);
  }

  @PostMapping("/queryAllEnabled")
  public List<? extends Variety> queryAllEnabled() {
    return varietyManager.queryAllEnabled();
  }

  @PostMapping("/save")
  @Permission(Const.USER_ROLE_ADMIN)
  public void save(@RequestBody VarietyDTO variety) {
    varietyManager.save(variety);
  }

  @PostMapping("/toggleEnabled")
  @Permission(Const.USER_ROLE_ADMIN)
  public void toggleEnabled(@RequestBody String name) {
    varietyManager.toggleEnabled(name);
  }
}
