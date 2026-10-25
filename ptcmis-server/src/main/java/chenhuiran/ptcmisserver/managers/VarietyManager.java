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

import chenhuiran.ptcmisserver.repository.VarietyDAO;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.QueryCondition;
import chenhuiran.ptcmisserver.stubs.models.QueryResultPaged;
import jakarta.persistence.criteria.Predicate;
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
public class VarietyManager {

  @Autowired
  private VarietyDAO varietyDAO;

  public QueryResultPaged<? extends Variety> queryByCondition(QueryCondition<String> condition) {
    var pageModel = condition.getPage() == null ? new com.chenhuiran.commons.lang.models.Page() : condition.getPage();
    var conditionModel = condition.getCondition();
    Pageable pageable = PageRequest.of(
      pageModel.getPage(),
      pageModel.getPageSize(),
      Sort.by(Sort.Direction.ASC, Variety.BP_NAME_NAME)
    );

    Specification<VarietyPO> spec = (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();

      if (conditionModel != null) {
        String likeVal = "%" + conditionModel.trim() + "%";
        Predicate p1 = cb.like(root.get(VarietyPO.BP_NAME_NAME), likeVal);
        predicateList.add(p1);
      }

      return cb.and(predicateList.toArray(new Predicate[0]));
    };

    // 执行分页查询并使用 Page.map 转换为 DTO
    Page<VarietyDTO> dtoPage = varietyDAO.findAll(spec, pageable).map(VarietyDTO::new);
    pageModel.updateTotal(dtoPage.getTotalElements());

    return new QueryResultPaged<>(dtoPage.getContent(), pageModel);
  }

  public List<VarietyDTO> queryAllEnabled() {
    var result = varietyDAO.findByEnabled(Const.BOOLEAN_1);
    if (result == null) return new ArrayList<>();
    return result.stream().map(VarietyDTO::new).toList();
  }

  @Transactional
  public void save(VarietyDTO variety) {
    Assert.notNull(variety, "品种信息不能为空");
    Assert.hasText(variety.getName(), "品种名称不能为空");
    variety.setName(variety.getName().toUpperCase());
    var po = varietyDAO.findById(variety.getName()).orElse(null);
    if (po == null) {
      po = new VarietyPO();
    }
    BeanUtils.copyProperties(variety, po);
    if (po.getEnabled() == null || po.getEnabled().isEmpty()) {
      po.setEnabled(Const.BOOLEAN_1);
    }
    if (po.isNew()) {
      varietyDAO.save(po);
    }
  }

  @Transactional
  public void toggleEnabled(String name) {
    var po = varietyDAO.findById(name).orElse(null);
    if (po == null) {
      return;
    }
    if (po.getEnabled() == null || !po.getEnabled().equals(Const.BOOLEAN_1)) po.setEnabled(Const.BOOLEAN_1);
    else po.setEnabled(Const.BOOLEAN_0);
  }
}
