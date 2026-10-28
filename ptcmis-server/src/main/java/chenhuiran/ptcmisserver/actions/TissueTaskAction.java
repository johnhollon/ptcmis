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

import chenhuiran.ptcmisserver.caches.DictionaryCache;
import chenhuiran.ptcmisserver.managers.ArchiveManager;
import chenhuiran.ptcmisserver.managers.TissueTaskManager;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.*;
import com.chenhuiran.commons.lang.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tissue-task")
public class TissueTaskAction {

  @Autowired
  private TissueTaskManager tissueTaskManager;
  @Autowired
  private ArchiveManager archiveManager;
  @Autowired
  private DictionaryCache dictionaryCache;

  @PostMapping("queryByTaskId")
  public ModelTissueTaskView queryByTaskId(@RequestBody String taskId) {
    return tissueTaskManager.queryByTaskId(taskId);
  }

  @PostMapping("queryByCondition")
  public QueryResultPaged<ModelTissueTaskView> queryByCondition(@RequestBody QueryCondition<QueryConditionTissueTask> condition) {
    return tissueTaskManager.queryByCondition(condition);
  }

  @PostMapping("queryOrCreateCodePrefix")
  public String queryOrCreateCodePrefix(@RequestBody String taskId) {
    return tissueTaskManager.queryOrCreateCodePrefix(taskId);
  }

  @PostMapping("createTask")
  public void createTask(HttpServletRequest request, @RequestBody TissueTaskDTO task) {
    tissueTaskManager.createTask(task, (User) request.getAttribute("user"));
  }

  @PostMapping("deleteTask")
  public void deleteTask(@RequestBody String taskId) {
    var storageInOutIdList = tissueTaskManager.deleteTask(taskId);
    archiveManager.deleteByTableAndId(TissueTask.TB_NAME, taskId);
    archiveManager.deleteByTableAndIdIn(StorageInOut.TB_NAME, storageInOutIdList);
  }

  @PostMapping("updateTask")
  public ModelTissueTaskView updateTask(@RequestBody TissueTaskDTO task) {
    return tissueTaskManager.updateTask(task);
  }

  @PostMapping("printBottleCode")
  public ModelTissueTaskView printBottleCode(@RequestBody ModelTissueTaskPrintBottleCode model) {
    return tissueTaskManager.printBottleCode(model.getTask(), model.getCodePrefix(), model.getBottleCount());
  }

  @PostMapping("printBottleCodeForAddBottles")
  public ModelPrintBottleCodeForAddBottles printBottleCodeForAddBottles(@RequestBody ModelTissueTaskPrintBottleCodeForAddBottles model) {
    return tissueTaskManager.printBottleCodeForAddBottles(model.getIdTask(), model.getBottleCount());
  }

  @PostMapping("saveProcessSettings")
  public ModelTissueTaskView saveProcessSettings(@RequestBody ModelTissueTaskProcessSettings settings) {
    return tissueTaskManager.saveProcessSettings(settings.getTaskId(), settings.getUsers(), settings.getDefaultPlantsPerParentBottle(), settings.getDefaultPlantsPerChildBottle());
  }

  @PostMapping("deleteStorageOut")
  public void deleteStorageOut(@RequestBody StorageOutTaskView model) {
    tissueTaskManager.deleteStorageOut(model);
    archiveManager.deleteByTableAndId(StorageInOutDTO.TB_NAME, model.getId());
  }

  @PostMapping("acceptStorageOut")
  public ModelTissueTaskView acceptStorageOut(HttpServletRequest request, @RequestBody ModelStorageOutTaskProcessDetail model) {
    return tissueTaskManager.acceptStorageOut(model, (User) request.getAttribute("user"));
  }

  @PostMapping("saveProcess")
  public ModelTissueTaskView saveProcess(HttpServletRequest request, @RequestBody ModelTissueTaskProcessDetail processDetail) {
    return tissueTaskManager.saveProcess(processDetail, (User) request.getAttribute("user"));
  }

  @PostMapping("updateTaskStatusToFinish")
  public ModelTissueTaskView updateTaskStatusToFinish(@RequestBody String idTask) {
    return tissueTaskManager.updateTaskStatusToFinish(idTask);
  }

  @PostMapping("updateTaskStatusFromFinishToProcess")
  public ModelTissueTaskView updateTaskStatusFromFinishToProcess(@RequestBody String idTask) {
    return tissueTaskManager.updateTaskStatusFromFinishToProcess(idTask);
  }

  @PostMapping("applyStorageIn")
  public ModelTissueTaskView applyStorageIn(HttpServletRequest request, @RequestBody String taskId) {
    return tissueTaskManager.applyStorageIn(taskId, (User) request.getAttribute("user"));
  }

  @PostMapping("deleteStorageIn")
  public ModelTissueTaskView deleteStorageIn(@RequestBody String inOutId) {
    var result = tissueTaskManager.deleteStorageIn(inOutId);
    archiveManager.deleteByTableAndId(StorageInOutDTO.TB_NAME, inOutId);
    return result;
  }

  @PostMapping(value = "/queryArchives")
  public List<ArchiveDTO> queryArchives(@RequestBody String idTask) {
    return archiveManager.queryByTableAndId(TissueTask.TB_NAME, idTask);
  }

  @PostMapping(value = "/uploadArchives")
  public List<ArchiveDTO> uploadArchives(HttpServletRequest request,
                                         @RequestPart("recordId") String recordId,
                                         @RequestPart("description") String description,
                                         @RequestPart("files") MultipartFile[] files) throws Exception {
    User user = (User) request.getAttribute("user");
    String tableName = TissueTask.TB_NAME;

    var task = tissueTaskManager.queryByTaskId(recordId);
    if (task == null) {
      return List.of();
    }

    String desc = description == null ? StringUtil.EMPTY : description.trim();

    archiveManager.uploadMulti(
      tableName,
      recordId,
      dictionaryCache.getName("tissue_task_type", task.getTask().getTaskType()) + "(" + task.getTask().getVariety() + ")",
      desc,
      files,
      user.getId(),
      user.getName()
    );

    return archiveManager.queryByTableAndId(tableName, recordId);
  }
}
