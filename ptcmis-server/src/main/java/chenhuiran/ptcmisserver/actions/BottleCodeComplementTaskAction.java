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
import chenhuiran.ptcmisserver.managers.BottleCodeComplementTaskManager;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.*;
import com.chenhuiran.commons.lang.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/bottle-code-complement-task")
public class BottleCodeComplementTaskAction {
  @Autowired
  private BottleCodeComplementTaskManager bottleCodeComplementTaskManager;
  @Autowired
  private ArchiveManager archiveManager;

  @PostMapping("/queryByCondition")
  public QueryResultPaged<? extends BottleCodeComplementTask> queryByCondition(@RequestBody QueryCondition<QueryConditionBottleCodeComplementTask> condition) {
    return bottleCodeComplementTaskManager.queryByCondition(condition);
  }

  @PostMapping("queryOrCreateCodePrefix")
  public String queryOrCreateCodePrefix(@RequestBody String taskId) {
    return bottleCodeComplementTaskManager.queryOrCreateCodePrefix(taskId);
  }

  @PostMapping("/queryAllCodesInTask")
  public List<? extends BottleCodeComplementTaskDetail> queryAllCodesInTask(@RequestBody String taskId) {
    return bottleCodeComplementTaskManager.queryAllCodesInTask(taskId);
  }

  @PostMapping("/save")
  public void save(HttpServletRequest request, @RequestBody BottleCodeComplementTaskDTO model) {
    bottleCodeComplementTaskManager.save(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/delete")
  public void delete(@RequestBody String taskId) {
    bottleCodeComplementTaskManager.delete(taskId);
    archiveManager.deleteByTableAndId(BottleCodeComplementTask.TB_NAME, taskId);
  }

  @PostMapping("/printCode")
  public ModelPrintBottleCodeForAddBottles printCode(HttpServletRequest request, @RequestBody ModelPrintBottleCodeForComplement model) {
    return bottleCodeComplementTaskManager.printCode(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/process")
  public void process(HttpServletRequest request, @RequestBody ModelBottleCodeComplementTaskProcess model) {
    bottleCodeComplementTaskManager.process(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/done")
  public void done(HttpServletRequest request, @RequestBody String taskId) {
    bottleCodeComplementTaskManager.done(taskId, (User) request.getAttribute("user"));
  }


  @PostMapping(value = "/queryArchives")
  public List<ArchiveDTO> queryArchives(@RequestBody String taskId) {
    return archiveManager.queryByTableAndId(BottleCodeComplementTask.TB_NAME, taskId);
  }

  @PostMapping(value = "/uploadArchives")
  public List<ArchiveDTO> uploadArchives(HttpServletRequest request,
                                         @RequestPart("task") String taskId,
                                         @RequestPart("files") MultipartFile[] files) throws Exception {
    User user = (User) request.getAttribute("user");

    archiveManager.uploadMulti(
      BottleCodeComplementTask.TB_NAME,
      taskId,
      "瓶体补码",
      StringUtil.EMPTY,
      files,
      user.getId(),
      user.getName()
    );

    return archiveManager.queryByTableAndId(BottleCodeComplementTask.TB_NAME, taskId);
  }
}
