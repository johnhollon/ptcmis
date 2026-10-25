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
import chenhuiran.ptcmisserver.managers.StorageInOutManager;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.*;
import com.chenhuiran.commons.lang.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/storage-in-out")
public class StorageInOutAction {

  @Autowired
  private StorageInOutManager storageInOutManager;
  @Autowired
  private ArchiveManager archiveManager;
  @Autowired
  private DictionaryCache dictionaryCache;

  @PostMapping("/queryAllLogs")
  public List<? extends StorageInOutLog> queryAllLogs(@RequestBody String storageInOutId) {
    return storageInOutManager.queryAllLogs(storageInOutId);
  }

  @PostMapping("/queryByCondition")
  public QueryResultPaged<StorageOutTaskView> queryByCondition(@RequestBody QueryCondition<QueryConditionStorageOutTaskView> condition) {
    return storageInOutManager.queryByCondition(condition);
  }

  @PostMapping("/queryBottles")
  public List<? extends StorageInOutBottle> queryBottles(@RequestBody String inOutId) {
    return storageInOutManager.queryBottles(inOutId);
  }

  @PostMapping("/rejBiz")
  public void rejBiz(HttpServletRequest request, @RequestBody ModelStorageOutTaskRej rejBiz) {
    storageInOutManager.rejBiz(rejBiz, (User) request.getAttribute("user"));
  }

  @PostMapping("/outProcessForTask")
  public void outProcessForTask(HttpServletRequest request, @RequestBody ModelStorageOutTaskProcessDetail model) {
    storageInOutManager.outProcessForTask(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/outDoneForTask")
  public void outDoneForTask(HttpServletRequest request, @RequestBody ModelStorageOutTaskProcessDetail model) {
    storageInOutManager.outDoneForTask(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/rejStore")
  public void rejStore(HttpServletRequest request, @RequestBody ModelStorageOutTaskRej rejBiz) {
    storageInOutManager.rejStore(rejBiz, (User) request.getAttribute("user"));
  }

  @PostMapping("/rejBizForIn")
  public void rejBizForIn(HttpServletRequest request, @RequestBody ModelStorageOutTaskRej rejBiz) {
    storageInOutManager.rejBizForIn(rejBiz, (User) request.getAttribute("user"));
  }

  @PostMapping("/inDoneForTask")
  public void inDoneForTask(HttpServletRequest request, @RequestBody String inOutId) {
    storageInOutManager.inDoneForTask(inOutId, (User) request.getAttribute("user"));
  }


  @PostMapping("/queryInspectOutByCondition")
  public QueryResultPaged<StorageInOut> queryInspectOutByCondition(@RequestBody QueryCondition<QueryConditionStorageOutTaskView> condition) {
    return storageInOutManager.queryInspectOutByCondition(condition);
  }

  @PostMapping("/createInspectOut")
  public void createInspectOut(HttpServletRequest request) {
    storageInOutManager.createInspectOut((User) request.getAttribute("user"));
  }

  @PostMapping("/outProcessForInspect")
  public void outProcessForInspect(HttpServletRequest request, @RequestBody ModelStorageOutInspectProcessDetail model) {
    storageInOutManager.outProcessForInspect(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/outFinishForInspect")
  public void outFinishForInspect(HttpServletRequest request, @RequestBody ModelStorageOutInspectProcessDetail model) {
    storageInOutManager.outFinishForInspect(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/deleteForInspect")
  public void deleteForInspect(@RequestBody String inOutId) {
    storageInOutManager.deleteForInspect(inOutId);
    archiveManager.deleteByTableAndId(StorageInOut.TB_NAME, inOutId);
  }

  @PostMapping("/createInventorySubBottle")
  public void createInventorySubBottle(HttpServletRequest request) {
    storageInOutManager.createInventorySubBottle((User) request.getAttribute("user"));
  }

  @PostMapping("/outProcessForInventorySubBottle")
  public void outProcessForInventorySubBottle(HttpServletRequest request, @RequestBody ModelStorageOutInspectProcessDetail model) {
    storageInOutManager.outProcessForInventorySubBottle(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/outFinishForInventorySubBottle")
  public void outFinishForInventorySubBottle(HttpServletRequest request, @RequestBody ModelStorageOutInspectProcessDetail model) {
    storageInOutManager.outFinishForInventorySubBottle(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/deleteForInventorySubBottle")
  public void deleteForInventorySubBottle(@RequestBody String inOutId) {
    storageInOutManager.deleteForInventorySubBottle(inOutId);
    archiveManager.deleteByTableAndId(StorageInOut.TB_NAME, inOutId);
  }

  @PostMapping("/createAcclimatizationOut")
  public void createAcclimatizationOut(HttpServletRequest request) {
    storageInOutManager.createAcclimatizationOut((User) request.getAttribute("user"));
  }

  @PostMapping("/outProcessForAcclimatizationOut")
  public void outProcessForAcclimatizationOut(HttpServletRequest request, @RequestBody ModelStorageOutInspectProcessDetail model) {
    storageInOutManager.outProcessForAcclimatizationOut(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/outFinishForAcclimatizationOut")
  public void outFinishForAcclimatizationOut(HttpServletRequest request, @RequestBody ModelStorageOutInspectProcessDetail model) {
    storageInOutManager.outFinishForAcclimatizationOut(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/deleteForAcclimatizationOut")
  public void deleteForAcclimatizationOut(@RequestBody String inOutId) {
    storageInOutManager.deleteForAcclimatizationOut(inOutId);
    archiveManager.deleteByTableAndId(StorageInOut.TB_NAME, inOutId);
  }

  @PostMapping("/createAcclimatizationIn")
  public void createAcclimatizationIn(HttpServletRequest request) {
    storageInOutManager.createAcclimatizationIn((User) request.getAttribute("user"));
  }

  @PostMapping("/inProcessForAcclimatizationIn")
  public void inProcessForAcclimatizationIn(HttpServletRequest request, @RequestBody ModelStorageOutInspectProcessDetail model) {
    storageInOutManager.inProcessForAcclimatizationIn(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/inFinishForAcclimatizationIn")
  public void inFinishForAcclimatizationIn(HttpServletRequest request, @RequestBody ModelStorageOutInspectProcessDetail model) {
    storageInOutManager.inFinishForAcclimatizationIn(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/deleteForAcclimatizationIn")
  public void deleteForAcclimatizationIn(@RequestBody String inOutId) {
    storageInOutManager.deleteForAcclimatizationIn(inOutId);
    archiveManager.deleteByTableAndId(StorageInOut.TB_NAME, inOutId);
  }

  @PostMapping(value = "/queryArchives")
  public List<ArchiveDTO> queryArchives(@RequestBody String inOutId) {
    return archiveManager.queryByTableAndId(StorageInOut.TB_NAME, inOutId);
  }

  @PostMapping(value = "/uploadArchives")
  public List<ArchiveDTO> uploadArchives(HttpServletRequest request,
                                         @RequestPart("inOutId") String inOutId,
                                         @RequestPart("description") String description,
                                         @RequestPart("files") MultipartFile[] files) throws Exception {
    User user = (User) request.getAttribute("user");

    var inOut = storageInOutManager.queryById(inOutId);
    if (inOut == null) {
      return List.of();
    }

    String reason = dictionaryCache.getName("storage_reason", inOut.getReason());
    String inOutType = dictionaryCache.getName("storage_in_out_type", inOut.getInOut());

    String desc = description == null ? StringUtil.EMPTY : description.trim();

    archiveManager.uploadMulti(
      StorageInOut.TB_NAME,
      inOutId,
      reason + inOutType,
      desc,
      files,
      user.getId(),
      user.getName()
    );

    return archiveManager.queryByTableAndId(StorageInOut.TB_NAME, inOutId);
  }
}
