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
import chenhuiran.ptcmisserver.managers.BottleManager;
import chenhuiran.ptcmisserver.stubs.entities.*;
import chenhuiran.ptcmisserver.stubs.models.*;
import com.chenhuiran.commons.lang.Rq;
import com.chenhuiran.commons.lang.StringUtil;
import com.chenhuiran.commons.lang.exceptions.ErrorMessageException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/bottle")
public class BottleAction {

  @Autowired
  private BottleManager bottleManager;
  @Autowired
  private DictionaryCache dictionaryCache;

  @PostMapping("/queryByCondition")
  public QueryResultPaged<? extends Bottle> queryByCondition(@RequestBody QueryCondition<QueryConditionBottle> condition) {
    return bottleManager.queryByCondition(condition);
  }

  @PostMapping("/queryAllByCondition")
  public List<ModelBottleSimple> queryAllByCondition(@RequestBody QueryConditionBottle condition) {
    return bottleManager.queryAllByCondition(condition);
  }

  @PostMapping("/changeParentBottle")
  public void changeParentBottle(HttpServletRequest request, @RequestBody ModelBottleChangeParent model) {
    bottleManager.changeParentBottle(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/changePlantCount")
  public void changePlantCount(HttpServletRequest request, @RequestBody ModelBottleChangePlants model) {
    bottleManager.changePlantCount(model, (User) request.getAttribute("user"));
  }

  @PostMapping("/queryAllEnabledIdsForTaskParents")
  public List<ModelBottleSimple> queryAllEnabledIdsForTaskParents(@RequestBody String taskId) {
    return bottleManager.queryAllEnabledIdsForTaskParents(taskId);
  }

  @PostMapping("/queryAllEnabledIdsForCodeComplementParents")
  public List<ModelBottleSimple> queryAllEnabledIdsForCodeComplementParents(@RequestBody String taskId) {
    return bottleManager.queryAllEnabledIdsForCodeComplementParents(taskId);
  }

  @PostMapping("/queryAllEnabledIdsForInventoryAddBottle")
  public List<ModelBottleSimple> queryAllEnabledIdsForInventoryAddBottle(@RequestBody String taskId) {
    return bottleManager.queryAllEnabledIdsForInventoryAddBottle(taskId);
  }

  @PostMapping("/queryAllEnabledParentIdsForChangeParent")
  public List<ModelBottleSimple> queryAllEnabledParentIdsForChangeParent(@RequestBody String bottleId) {
    return bottleManager.queryAllEnabledParentIdsForChangeParent(bottleId);
  }

  @PostMapping("/queryAllStorageIn")
  public List<ModelBottleSimple> queryAllStorageIn() {
    return bottleManager.queryAllStorageIn();
  }

  @PostMapping("/queryBottlesForInventorySubBottle")
  public List<ModelBottleSimpleImp> queryBottlesForInventorySubBottle(@RequestBody String idInOut) {
    return bottleManager.queryBottlesForInventorySubBottle(idInOut);
  }

  @PostMapping("/queryAllForAcclimatizationIn")
  public List<ModelBottleSimple> queryAllForAcclimatizationIn() {
    return bottleManager.queryAllForAcclimatizationIn();
  }

  @PostMapping("/queryById")
  public Bottle queryById(@RequestBody String bottleId) {
    return bottleManager.queryById(bottleId);
  }

  @PostMapping("/queryAllIdStorageIn")
  public List<String> queryAllIdStorageIn() {
    return bottleManager.queryAllIdStorageIn();
  }

  @PostMapping("/queryParents")
  public List<BottleDTO> queryParents(@RequestBody String id) {
    return bottleManager.queryParents(id);
  }

  @PostMapping("/queryChildren")
  public List<BottleDTO> queryChildren(@RequestBody String id) {
    return bottleManager.queryChildren(id);
  }

  @PostMapping("/queryLogs")
  public List<BottleLogDTO> queryLogs(@RequestBody String id) {
    return bottleManager.queryLogs(id);
  }

  @PostMapping("/queryArchives")
  public List<ArchiveDTO> queryArchives(@RequestBody String id) {
    return bottleManager.queryArchives(id);
  }

  @PostMapping("excelBottle")
  public void excelBottle(@RequestBody String id, HttpServletResponse response) {
    var bottle = bottleManager.queryById(id);
    if (bottle == null) return;

    var parents = bottleManager.queryParents(id);
    var children = bottleManager.queryChildren(id);
    var logs = bottleManager.queryLogs(id);

    java.time.Instant now = Rq.getNow();

    String sourceType = dictionaryCache.getName("storage_reason", bottle.getSourceType());
    sourceType = sourceType == null || sourceType.isBlank() ? StringUtil.EMPTY : sourceType;

    var sbFilename = new StringBuilder("培养瓶档案-[" + bottle.getId() + "]-[" + Rq.formatCompactYMD(now) + "]");
    var sbConditionDesc = new StringBuilder("培养瓶信息： [");
    sbConditionDesc.append(bottle.getId());
    sbConditionDesc.append("] ");
    sbConditionDesc.append(bottle.getVariety());
    sbConditionDesc.append("，");
    sbConditionDesc.append(bottle.getVarietyCode());
    if (!sourceType.isBlank()) {
      sbConditionDesc.append(" 建瓶来源[");
      sbConditionDesc.append(sourceType);
      sbConditionDesc.append("]");
    }
    sbConditionDesc.append(" 代际[D-");
    sbConditionDesc.append(bottle.getGeneration());
    sbConditionDesc.append("]");
    sbConditionDesc.append(" 外植体采集批次[P-");
    sbConditionDesc.append(bottle.getExplantCollectionBatch());
    sbConditionDesc.append("]");


    var sbConditionDateDesc = new StringBuilder("生成时间：");
    sbConditionDateDesc.append(Rq.formatChineseYMD(now));

    var resource = new ClassPathResource("excel-templates/template-bottle.xlsx");
    try (InputStream is = resource.getInputStream();
         var workbook = new XSSFWorkbook(is);
         var os = response.getOutputStream()) {

      // Sheet - 培养瓶档案
      {
        var sheet = workbook.getSheetAt(0);
        sheet.getRow(1).getCell(0).setCellValue(sbConditionDateDesc.toString());
        sheet.getRow(2).getCell(1).setCellValue(bottle.getId());
        sheet.getRow(3).getCell(1).setCellValue(bottle.getVariety() + "，" + bottle.getVarietyCode());
        sheet.getRow(4).getCell(1).setCellValue(bottle.getPlantCount() + "株");
        sheet.getRow(5).getCell(1).setCellValue(bottle.getGeneration());
        sheet.getRow(5).getCell(3).setCellValue(bottle.getExplantCollectionBatch().toString());
        sheet.getRow(6).getCell(1).setCellValue(bottle.getIdParent() == null || bottle.getIdParent().isBlank() ? "——" : bottle.getIdParent());
        sheet.getRow(7).getCell(1).setCellValue(sourceType.isBlank() ? "——" : sourceType);
        sheet.getRow(7).getCell(3).setCellValue(bottle.getTaskTime() != null && bottle.getTaskTime() > 0L ? Rq.formatChineseYMD(bottle.getTaskTime()) : "——");
        sheet.getRow(8).getCell(1).setCellValue(bottle.getLastOperatorName() == null || bottle.getLastOperatorName().isBlank() ? "——" : bottle.getLastOperatorName());
        sheet.getRow(10).getCell(1).setCellValue(bottle.getDiscarded() != null && bottle.getDiscarded().equals(Const.BOOLEAN_1) ? "已淘汰" : "未淘汰");
        sheet.getRow(10).getCell(3).setCellValue(bottle.getDiscarded() != null && bottle.getDiscarded().equals(Const.BOOLEAN_1) ? bottle.getDiscardReason() == null || bottle.getDiscardReason().isBlank() ? "未知" : bottle.getDiscardReason() : "——");
        sheet.getRow(11).getCell(1).setCellValue(bottle.getDiscarded() != null && bottle.getDiscarded().equals(Const.BOOLEAN_1) ? bottle.getDiscardUserName() == null || bottle.getDiscardUserName().isBlank() ? "未知" : bottle.getDiscardUserName() : "——");
        sheet.getRow(11).getCell(3).setCellValue(bottle.getDiscarded() != null && bottle.getDiscarded().equals(Const.BOOLEAN_1) ? bottle.getDiscardTime() == null || bottle.getDiscardTime() < 1L ? "未知" : Rq.formatChineseYMD(bottle.getDiscardTime()) : "——");
        String currentStorageStatus = bottle.getCurrentStorageStatus() == null || bottle.getCurrentStorageStatus().isBlank() ? StringUtil.EMPTY : dictionaryCache.getName("bottle_storage_status", bottle.getCurrentStorageStatus());
        sheet.getRow(13).getCell(1).setCellValue(currentStorageStatus.isBlank() ? "——" : currentStorageStatus);
        String lastReceivedReason = bottle.getLastReceivedReason() == null || bottle.getLastReceivedReason().isBlank() ? StringUtil.EMPTY : dictionaryCache.getName("storage_reason", bottle.getLastReceivedReason());
        sheet.getRow(14).getCell(1).setCellValue(lastReceivedReason.isBlank() ? "——" : lastReceivedReason);
        sheet.getRow(15).getCell(1).setCellValue(bottle.getLastReceivedUserName() == null || bottle.getLastReceivedUserName().isBlank() ? "——" : bottle.getLastReceivedUserName());
        sheet.getRow(15).getCell(3).setCellValue(bottle.getLastReceivedTime() == null || bottle.getLastReceivedTime() < 1L ? "——" : Rq.formatChineseYMD(bottle.getLastReceivedTime()));
        String lastIssuedReason = bottle.getLastIssuedReason() == null || bottle.getLastIssuedReason().isBlank() ? StringUtil.EMPTY : dictionaryCache.getName("storage_reason", bottle.getLastIssuedReason());
        sheet.getRow(16).getCell(1).setCellValue(lastIssuedReason.isBlank() ? "——" : lastIssuedReason);
        sheet.getRow(17).getCell(1).setCellValue(bottle.getLastIssuedUserName() == null || bottle.getLastIssuedUserName().isBlank() ? "——" : bottle.getLastIssuedUserName());
        sheet.getRow(17).getCell(3).setCellValue(bottle.getLastIssuedTime() == null || bottle.getLastIssuedTime() < 1L ? "——" : Rq.formatChineseYMD(bottle.getLastIssuedTime()));
      }

      // Sheet - 母瓶
      {
        var sheet = workbook.getSheetAt(1);
        int ROW_INDEX_START = 3;

        sheet.getRow(1).getCell(0).setCellValue(sbConditionDesc.toString());
        sheet.getRow(1).getCell(8).setCellValue(sbConditionDateDesc.toString());

        // 复制第一行的样式
        CellStyle style0 = workbook.createCellStyle();
        style0.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(0).getCellStyle());
        CellStyle style1 = workbook.createCellStyle();
        style1.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(1).getCellStyle());
        CellStyle style2 = workbook.createCellStyle();
        style2.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(2).getCellStyle());
        CellStyle style3 = workbook.createCellStyle();
        style3.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(3).getCellStyle());
        CellStyle style4 = workbook.createCellStyle();
        style4.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(4).getCellStyle());
        CellStyle style5 = workbook.createCellStyle();
        style5.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(5).getCellStyle());
        CellStyle style6 = workbook.createCellStyle();
        style6.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(6).getCellStyle());
        CellStyle style7 = workbook.createCellStyle();
        style7.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(7).getCellStyle());
        CellStyle style8 = workbook.createCellStyle();
        style8.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(8).getCellStyle());
        CellStyle style9 = workbook.createCellStyle();
        style9.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(9).getCellStyle());
        CellStyle style10 = workbook.createCellStyle();
        style10.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(10).getCellStyle());
        CellStyle style11 = workbook.createCellStyle();
        style11.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(11).getCellStyle());

        Row delRow = sheet.getRow(ROW_INDEX_START);
        if (delRow != null) {
          sheet.removeRow(delRow);
        }

        int index = -1;

        for (var parent : parents) {
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(parent.getId());
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(parent.getVariety());
          cell = row.createCell(2);
          cell.setCellStyle(style2);
          cell.setCellValue(parent.getVarietyCode());
          cell = row.createCell(3);
          cell.setCellStyle(style3);
          cell.setCellValue(parent.getExplantCollectionBatch().toString());
          cell = row.createCell(4);
          cell.setCellStyle(style4);
          cell.setCellValue(parent.getGeneration().toString());
          cell = row.createCell(5);
          cell.setCellStyle(style5);
          cell.setCellValue(parent.getPlantCount().toString());
          cell = row.createCell(6);
          cell.setCellStyle(style6);
          sourceType = dictionaryCache.getName("storage_reason", parent.getSourceType());
          sourceType = sourceType == null || sourceType.isBlank() ? StringUtil.EMPTY : sourceType;
          cell.setCellValue(sourceType.isBlank() ? "——" : sourceType);
          cell = row.createCell(7);
          cell.setCellStyle(style7);
          cell.setCellValue(parent.getTaskTime() != null && parent.getTaskTime() > 0L ? Rq.formatChineseYMD(parent.getTaskTime()) : "——");
          cell = row.createCell(8);
          cell.setCellStyle(style8);
          cell.setCellValue(parent.getDiscarded() != null && parent.getDiscarded().equals(Const.BOOLEAN_1) ? "已淘汰" : "未淘汰");
          cell = row.createCell(9);
          cell.setCellStyle(style9);
          String currentStorageStatus = parent.getCurrentStorageStatus() == null || parent.getCurrentStorageStatus().isBlank() ? StringUtil.EMPTY : dictionaryCache.getName("bottle_storage_status", parent.getCurrentStorageStatus());
          cell.setCellValue(currentStorageStatus.isBlank() ? "——" : currentStorageStatus);
          cell = row.createCell(10);
          cell.setCellStyle(style10);
          cell.setCellValue(parent.getLastOperatorName() == null || parent.getLastOperatorName().isBlank() ? "——" : parent.getLastOperatorName());
          cell = row.createCell(11);
          cell.setCellStyle(style11);
          cell.setCellValue(parent.getIdParent() == null || parent.getIdParent().isBlank() ? "——" : parent.getIdParent());
        }

        sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 11));
      }

      // Sheet - 子瓶
      {
        var sheet = workbook.getSheetAt(2);
        int ROW_INDEX_START = 3;

        sheet.getRow(1).getCell(0).setCellValue(sbConditionDesc.toString());
        sheet.getRow(1).getCell(8).setCellValue(sbConditionDateDesc.toString());

        // 复制第一行的样式
        CellStyle style0 = workbook.createCellStyle();
        style0.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(0).getCellStyle());
        CellStyle style1 = workbook.createCellStyle();
        style1.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(1).getCellStyle());
        CellStyle style2 = workbook.createCellStyle();
        style2.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(2).getCellStyle());
        CellStyle style3 = workbook.createCellStyle();
        style3.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(3).getCellStyle());
        CellStyle style4 = workbook.createCellStyle();
        style4.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(4).getCellStyle());
        CellStyle style5 = workbook.createCellStyle();
        style5.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(5).getCellStyle());
        CellStyle style6 = workbook.createCellStyle();
        style6.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(6).getCellStyle());
        CellStyle style7 = workbook.createCellStyle();
        style7.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(7).getCellStyle());
        CellStyle style8 = workbook.createCellStyle();
        style8.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(8).getCellStyle());
        CellStyle style9 = workbook.createCellStyle();
        style9.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(9).getCellStyle());
        CellStyle style10 = workbook.createCellStyle();
        style10.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(10).getCellStyle());
        CellStyle style11 = workbook.createCellStyle();
        style11.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(11).getCellStyle());

        Row delRow = sheet.getRow(ROW_INDEX_START);
        if (delRow != null) {
          sheet.removeRow(delRow);
        }

        int index = -1;

        for (var child : children) {
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(child.getId());
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(child.getVariety());
          cell = row.createCell(2);
          cell.setCellStyle(style2);
          cell.setCellValue(child.getVarietyCode());
          cell = row.createCell(3);
          cell.setCellStyle(style3);
          cell.setCellValue(child.getExplantCollectionBatch().toString());
          cell = row.createCell(4);
          cell.setCellStyle(style4);
          cell.setCellValue(child.getGeneration().toString());
          cell = row.createCell(5);
          cell.setCellStyle(style5);
          cell.setCellValue(child.getPlantCount().toString());
          cell = row.createCell(6);
          cell.setCellStyle(style6);
          sourceType = dictionaryCache.getName("storage_reason", child.getSourceType());
          sourceType = sourceType == null || sourceType.isBlank() ? StringUtil.EMPTY : sourceType;
          cell.setCellValue(sourceType.isBlank() ? "——" : sourceType);
          cell = row.createCell(7);
          cell.setCellStyle(style7);
          cell.setCellValue(child.getTaskTime() != null && child.getTaskTime() > 0L ? Rq.formatChineseYMD(child.getTaskTime()) : "——");
          cell = row.createCell(8);
          cell.setCellStyle(style8);
          cell.setCellValue(child.getDiscarded() != null && child.getDiscarded().equals(Const.BOOLEAN_1) ? "已淘汰" : "未淘汰");
          cell = row.createCell(9);
          cell.setCellStyle(style9);
          String currentStorageStatus = child.getCurrentStorageStatus() == null || child.getCurrentStorageStatus().isBlank() ? StringUtil.EMPTY : dictionaryCache.getName("bottle_storage_status", child.getCurrentStorageStatus());
          cell.setCellValue(currentStorageStatus.isBlank() ? "——" : currentStorageStatus);
          cell = row.createCell(10);
          cell.setCellStyle(style10);
          cell.setCellValue(child.getLastOperatorName() == null || child.getLastOperatorName().isBlank() ? "——" : child.getLastOperatorName());
          cell = row.createCell(11);
          cell.setCellStyle(style11);
          cell.setCellValue(child.getIdParent() == null || child.getIdParent().isBlank() ? "——" : child.getIdParent());
        }

        sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 11));
      }

      // Sheet - 日志
      {
        var sheet = workbook.getSheetAt(3);
        int ROW_INDEX_START = 3;

        sheet.getRow(1).getCell(0).setCellValue(sbConditionDesc.toString());
        sheet.getRow(1).getCell(7).setCellValue(sbConditionDateDesc.toString());

        // 复制第一行的样式
        CellStyle style0 = workbook.createCellStyle();
        style0.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(0).getCellStyle());
        CellStyle style1 = workbook.createCellStyle();
        style1.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(1).getCellStyle());
        CellStyle style2 = workbook.createCellStyle();
        style2.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(2).getCellStyle());
        CellStyle style3 = workbook.createCellStyle();
        style3.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(3).getCellStyle());
        CellStyle style4 = workbook.createCellStyle();
        style4.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(4).getCellStyle());
        CellStyle style5 = workbook.createCellStyle();
        style5.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(5).getCellStyle());
        CellStyle style6 = workbook.createCellStyle();
        style6.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(6).getCellStyle());
        CellStyle style7 = workbook.createCellStyle();
        style7.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(7).getCellStyle());
        CellStyle style8 = workbook.createCellStyle();
        style8.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(8).getCellStyle());
        CellStyle style9 = workbook.createCellStyle();
        style9.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(9).getCellStyle());

        Row delRow = sheet.getRow(ROW_INDEX_START);
        if (delRow != null) {
          sheet.removeRow(delRow);
        }

        int index = -1;

        for (var log : logs) {
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(log.getBizType() == null || log.getBizType().isBlank() ? "——" : log.getBizType());
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(log.getDescription() == null || log.getDescription().isBlank() ? "——" : log.getDescription());
          cell = row.createCell(2);
          cell.setCellStyle(style2);
          var operateStatus = log.getOperateStatus() == null || log.getOperateStatus().isBlank() ? StringUtil.EMPTY : dictionaryCache.getName("bottle_operate_status", log.getOperateStatus());
          cell.setCellValue(operateStatus.isBlank() ? "——" : operateStatus);
          cell = row.createCell(3);
          cell.setCellStyle(style3);
          var storageStatus = log.getStorageStatus() == null || log.getStorageStatus().isBlank() ? StringUtil.EMPTY : dictionaryCache.getName("bottle_storage_status", log.getStorageStatus());
          cell.setCellValue(storageStatus.isBlank() ? "——" : storageStatus);
          cell = row.createCell(4);
          cell.setCellStyle(style4);
          var storageReason = log.getStorageReason() == null || log.getStorageReason().isBlank() ? StringUtil.EMPTY : dictionaryCache.getName("storage_reason", log.getStorageReason());
          cell.setCellValue(storageReason.isBlank() ? "——" : storageReason);
          cell = row.createCell(5);
          cell.setCellStyle(style5);
          cell.setCellValue(log.getDiscarded() != null && log.getDiscarded().equals(Const.BOOLEAN_1) ? "已淘汰" : "未淘汰");
          cell = row.createCell(6);
          cell.setCellStyle(style6);
          cell.setCellValue(log.getDiscarded() != null && log.getDiscarded().equals(Const.BOOLEAN_1) ? log.getDiscardReason() == null || log.getDiscardReason().isBlank() ? "未知" : log.getDiscardReason() : "——");
          cell = row.createCell(7);
          cell.setCellStyle(style7);
          cell.setCellValue(log.getInspectorName() == null || log.getInspectorName().isBlank() ? "——" : log.getInspectorName());
          cell = row.createCell(8);
          cell.setCellStyle(style8);
          cell.setCellValue(log.getOperatorName() == null || log.getOperatorName().isBlank() ? "——" : log.getOperatorName());
          cell = row.createCell(9);
          cell.setCellStyle(style9);
          cell.setCellValue(log.getOppTime() != null && log.getOppTime() > 0L ? Rq.formatChineseYMD(log.getOppTime()) : "——");
        }

        sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 9));
      }

      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      String encodedName = URLEncoder.encode(sbFilename.toString(), StandardCharsets.UTF_8) + ".xlsx";
      response.setHeader("Content-Disposition", "attachment;filename=" + encodedName);
      response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
      workbook.write(os);
      os.flush();
    } catch (Throwable e) {
      throw new ErrorMessageException("导出失败。", e);
    }
  }
}
