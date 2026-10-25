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

package chenhuiran.ptcmisserver.interceptors;

import chenhuiran.ptcmisserver.managers.PassportManager;
import chenhuiran.ptcmisserver.stubs.entities.Const;
import chenhuiran.ptcmisserver.stubs.entities.User;
import chenhuiran.ptcmisserver.stubs.entities.UserDTO;
import com.chenhuiran.commons.lang.annotations.Permission;
import com.chenhuiran.commons.lang.exceptions.HttpException401;
import com.chenhuiran.commons.lang.exceptions.HttpException403;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {

  @Autowired
  private PassportManager passportManager;

  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {

    if (!(handler instanceof HandlerMethod handlerMethod)) {
      return true;
    }

    Permission annotation = handlerMethod.getMethodAnnotation(Permission.class);

    boolean needToken = annotation == null || annotation.value() == null || !annotation.value().equals(Const.USER_ROLE_NONE);
    // TODO 开发调试取消权限认证
//    UserDTO user1 = new UserDTO();
//    user1.setCode("admin");
//    user1.setId("admin");
//    user1.setName("超级管理员");
//    user1.setMobile("admin");
//    user1.setPassword("admin");
//    user1.setEnabled(Const.BOOLEAN_1);
//    request.setAttribute("user", user1);
//    if (!needToken || true) return true;


    if (!needToken) return true;

    String token = request.getHeader("x-ptcmis-token");
    if (token == null || token.isEmpty()) {
      throw new HttpException401("登录前你不能操作此功能。");
    }

    User user = passportManager.getUserByToken(token);
    if (user == null || user.getRole() == null || user.getRole().isBlank() || (
      !user.getRole().equals(Const.USER_ROLE_ADMIN)
        && !user.getRole().equals(Const.USER_ROLE_INVENTORY)
        && !user.getRole().equals(Const.USER_ROLE_QUERY)
        && !user.getRole().equals(Const.USER_ROLE_TASK)
        && !user.getRole().equals(Const.USER_ROLE_TASK_AND_INVENTORY)
    )) {
      throw new HttpException401("你的账户不具备操作此功能的权限。");
    }

    boolean needAdmin = annotation != null && annotation.value() != null && annotation.value().equals(Const.USER_ROLE_ADMIN);
    if (needAdmin && !user.getRole().equals(Const.USER_ROLE_ADMIN)) {
      throw new HttpException403("你的账户不具备操作此功能的权限。");
    }

    request.setAttribute("user", user);
    return true;
  }

  @Nullable
  public static User getUser(@NonNull HttpServletRequest request) {
    Object userObj = request.getAttribute("user");
    if (!(userObj instanceof User user)) return null;
    return user;
  }
}
