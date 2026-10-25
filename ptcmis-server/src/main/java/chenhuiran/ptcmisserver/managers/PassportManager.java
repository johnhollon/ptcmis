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

package chenhuiran.ptcmisserver.managers;

import chenhuiran.ptcmisserver.repository.UserDAO;
import chenhuiran.ptcmisserver.stubs.entities.Const;
import chenhuiran.ptcmisserver.stubs.entities.User;
import chenhuiran.ptcmisserver.stubs.entities.UserDTO;
import chenhuiran.ptcmisserver.stubs.entities.UserPO;
import chenhuiran.ptcmisserver.stubs.models.ModelLogin;
import chenhuiran.ptcmisserver.stubs.models.ModelLoginResponse;
import com.chenhuiran.commons.lang.Id;
import com.chenhuiran.commons.lang.exceptions.HttpException401;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.Map;

@Service
public class PassportManager {

  private final Map<String, User> passportCache = new ConcurrentReferenceHashMap<>();

  @Autowired
  private UserDAO userDAO;

  @NonNull
  public ModelLoginResponse login(ModelLogin modelLogin) {
    UserPO userPO = userDAO.findByMobileAndPassword(modelLogin.getUsername(), modelLogin.getPassword()).orElse(null);
    User user = userPO == null ? null : new UserDTO(userPO);
    if (
      user == null || user.getRole() == null
        || (
          !user.getRole().equals(Const.USER_ROLE_ADMIN)
            && !user.getRole().equals(Const.USER_ROLE_INVENTORY)
            && !user.getRole().equals(Const.USER_ROLE_QUERY)
            && !user.getRole().equals(Const.USER_ROLE_TASK)
            && !user.getRole().equals(Const.USER_ROLE_TASK_AND_INVENTORY)
      )
    ) {
      throw new HttpException401("你的账户不能使用本系统。");
    }
    var token = Id.g();
    passportCache.put(token, user);
    var result = new ModelLoginResponse();
    result.setName(user.getName());
    result.setUserId(user.getId());
    result.setToken(token);
    result.setUsername(user.getMobile());
    result.setRole(user.getRole());
    return result;
  }

  @Nullable
  public User getUserByToken(String token) {
    return passportCache.get(token);
  }

}
