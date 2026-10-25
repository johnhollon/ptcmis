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
import chenhuiran.ptcmisserver.stubs.models.ModelStorageOutTaskProcessDetail;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryConditionUser;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import com.chenhuiran.commons.lang.exceptions.ErrorMessageException;
import jakarta.persistence.criteria.Predicate;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManager {

  @Autowired
  private UserDAO userDAO;

  public UserDTO queryByIdByAdmin(String id) {
    return userDAO.findById(id).map(UserDTO::new).orElse(null);
  }

  public QueryResultPaged<UserDTO> queryByCondition(QueryCondition<QueryConditionUser> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();

    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.ASC, User.BP_NAME_NAME)
    );

    Specification<UserPO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();

      if (conditionModel != null) {
        // keyword: 模糊匹配 name 或 code
        if (conditionModel.getKeyword() != null && !conditionModel.getKeyword().isBlank()) {
          String likeVal = "%" + conditionModel.getKeyword().trim().toUpperCase() + "%";
          Predicate p1 = cb.like(root.get(User.BP_NAME_NAME), likeVal);
          Predicate p2 = cb.like(root.get(User.BP_NAME_CODE), likeVal);
          predicateList.add(cb.or(p1, p2));
        }

        // role: 精确匹配
        if (conditionModel.getRole() != null && !conditionModel.getRole().isBlank()) {
          predicateList.add(cb.equal(root.get(User.BP_NAME_ROLE), conditionModel.getRole()));
        }
      }

      return cb.and(predicateList.toArray(new Predicate[0]));
    };

    // 执行分页查询并使用 Page.map 转换为 DTO
    Page<UserDTO> dtoPage = userDAO.findAll(spec, pageable).map(UserDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());

    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }

  public List<UserDTO> queryAllEnabled() {
    return userDAO.findAllEnabled(Const.BOOLEAN_1);
  }

  @Transactional
  public void saveByAdmin(User user) {
    Assert.notNull(user, "员工信息不能为空");
    Assert.hasText(user.getId(), "员工ID不能为空");
    Assert.hasText(user.getCode(), "员工编码不能为空");
    Assert.hasText(user.getName(), "员工姓名不能为空");
    Assert.hasText(user.getMobile(), "手机号码不能为空");

    long count = userDAO.countByCodeAndIdNot(user.getCode().trim().toUpperCase(), user.getId());
    if (count > 0) {
      throw new IllegalArgumentException("员工编码 [" + user.getCode() + "] 已存在");
    }
    count = userDAO.countByMobileAndIdNot(user.getMobile().trim().toUpperCase(), user.getId());
    if (count > 0) {
      throw new IllegalArgumentException("手机号码 [" + user.getMobile() + "] 已存在");
    }

    UserPO userPO = userDAO.findById(user.getId()).orElseGet(UserPO::new);
    BeanUtils.copyProperties(user, userPO);
    userPO.setName(user.getName().trim().toUpperCase());
    userPO.setCode(user.getCode().trim().toUpperCase());
    userPO.setMobile(user.getMobile().trim().toUpperCase());
    if (userPO.getEnabled() == null || userPO.getEnabled().isEmpty()) {
      userPO.setEnabled(Const.BOOLEAN_1);
    }
    if (userPO.getRole() == null || userPO.getRole().isEmpty()) {
      userPO.setRole(Const.USER_ROLE_NONE);
    }

    if (userPO.isNew()) {
      userDAO.save(userPO);
    }
  }

  @Transactional
  public void toggleEnabled(String id) {
    Assert.notNull(id, "未知的员工信息");
    UserPO userPO = userDAO.findById(id).orElse(null);
    if (userPO == null) return;
    userPO.setEnabled(userPO.getEnabled().equals(Const.BOOLEAN_1) ? Const.BOOLEAN_0 : Const.BOOLEAN_1);
  }

  @Transactional
  public void updatePassword(@NonNull String passwordNew, @NonNull String passwordOld, @NonNull User currentUser) {
    Assert.hasText(passwordOld, "原密码不能为空");
    Assert.hasText(passwordNew, "新密码不能为空");
    UserPO userPO = userDAO.findById(currentUser.getId()).orElse(null);
    if (userPO == null || !userPO.getPassword().equals(passwordOld)) {
      throw new ErrorMessageException("原密码错误。");
    }
    userPO.setPassword(passwordNew);
  }
}
