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

package chenhuiran.ptcmisserver.caches;

import chenhuiran.ptcmisserver.repository.DictionaryItemDAO;
import com.chenhuiran.commons.lang.StringUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DictionaryCache {
  @Autowired
  private DictionaryItemDAO dictionaryItemDAO;

  private final Map<String, Map<String, String>> cache = new HashMap<>();

  @PostConstruct
  public void loadCache() {
    cache.clear();
    var all = dictionaryItemDAO.findAll();
    for (var i : all) {
      var dicId = i.getIdDictionary();
      cache.computeIfAbsent(dicId, (k) -> new HashMap<>()).put(i.getCode(), i.getName());
    }
  }

  public String getName(String dicId, String itemCode) {
    return cache.getOrDefault(dicId, new HashMap<>()).getOrDefault(itemCode, StringUtil.EMPTY);
  }
}
