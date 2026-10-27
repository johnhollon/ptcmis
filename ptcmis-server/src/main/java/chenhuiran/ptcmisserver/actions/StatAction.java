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
import chenhuiran.ptcmisserver.managers.StatManager;
import chenhuiran.ptcmisserver.stubs.entities.Const;
import chenhuiran.ptcmisserver.stubs.models.*;
import com.chenhuiran.commons.lang.Rq;
import com.chenhuiran.commons.lang.StringUtil;
import com.chenhuiran.commons.lang.exceptions.ErrorMessageException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
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
import java.text.Collator;
import java.util.*;

@RestController
@RequestMapping("/api/stat")
public class StatAction {

  @Autowired
  private StatManager statManager;
  @Autowired
  private DictionaryCache dictionaryCache;

  @PostMapping("statInventorySimple")
  public ModeStatInventorySimple statInventorySimple(@RequestBody QueryConditionStatInventory condition) {
    return statManager.statInventorySimple(condition);
  }

  @PostMapping("statInventory")
  public List<ModeStatInventory> statInventory(@RequestBody QueryConditionStatInventory condition) {
    return statManager.statInventory(condition);
  }

  @PostMapping("statInventoryTransaction")
  public ModelStatInventoryTransaction statInventoryTransaction(@RequestBody QueryConditionStatInventory condition) {
    return statManager.statInventoryTransaction(condition);
  }

  @PostMapping("statWorkload")
  public List<ModeStatWorkload> statWorkload(@RequestBody QueryConditionStatWorkload condition) {
    return statManager.statWorkload(condition);
  }

  @PostMapping("statWorkloadTransaction")
  public ModelStatWorkloadTransaction statWorkloadTransaction(@RequestBody QueryConditionStatWorkload condition) {
    return statManager.statWorkloadTransaction(condition);
  }

  @PostMapping("excelStatInventorySimple")
  public void excelStatInventorySimple(@RequestBody QueryConditionStatInventory condition, HttpServletResponse response) {
    var statModel = statManager.statInventorySimple(condition);
    var maxGeneration = statModel.getMaxGeneration();
    //  增加 品种/品种编号/重点品种合计，共4列
    var columnsCount = (maxGeneration + 1) + 4;
    var minColumnsCount = 9;
    var dataColumnIndex = 3;

    var sbFilename = new StringBuilder("库存简报[" + Rq.formatCompactYMD(Rq.getNow()) + "]");
    var sbConditionDesc = new StringBuilder();
    if (condition.getVariety() != null && !condition.getVariety().isBlank()) {
      sbFilename.append("-");
      sbFilename.append(condition.getVariety());
      sbConditionDesc.append("品种：");
      sbConditionDesc.append(condition.getVariety());
      if (condition.getVarietyCode() != null && !condition.getVarietyCode().isBlank()) {
        sbFilename.append("-");
        sbFilename.append(condition.getVarietyCode());
        sbConditionDesc.append("-");
        sbConditionDesc.append(condition.getVarietyCode());
      }
    }
    if (condition.getVarietyCodeImportant() != null && !condition.getVarietyCodeImportant().isBlank()) {
      var desc = condition.getVarietyCodeImportant().equals(Const.BOOLEAN_0) ? "普通品种" : "重点品种";
      sbFilename.append("-");
      sbFilename.append(desc);
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append(desc);
    }
    if (condition.getGeneration() != null && condition.getGeneration() > -1) {
      sbFilename.append("-D");
      sbFilename.append(condition.getGeneration());
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append("D");
      sbConditionDesc.append(condition.getGeneration());
    }
    String conditionDesc = sbConditionDesc.isEmpty() ? StringUtil.EMPTY : "【统计条件】 " + sbConditionDesc;

    var sbConditionDateDesc = new StringBuilder("截至");
    var dateTypeCondition = condition.getDateType();
    if (dateTypeCondition.equals("按月") || dateTypeCondition.equals("按年") || dateTypeCondition.equals("按季")) {
      var dateTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? 0L : condition.getDateRange().getEnd();
      var instant = Rq.toInstant(dateTime < 1L ? System.currentTimeMillis() : dateTime);
      var year = Rq.getYearString(instant);
      var month = Rq.getMonth(instant);

      sbConditionDateDesc.append(year);
      sbConditionDateDesc.append("年");
      sbFilename.append("-截至");
      sbFilename.append(year);
      sbFilename.append("年");

      if (dateTypeCondition.equals("按月")) {
        sbConditionDateDesc.append(month);
        sbConditionDateDesc.append("月");
        sbFilename.append(month);
        sbFilename.append("月");
      } else if (dateTypeCondition.equals("按季")) {
        if (month >= 1 && month <= 3) {
          sbConditionDateDesc.append("第一季度");
          sbFilename.append("第一季度");
        } else if (month >= 4 && month <= 6) {
          sbConditionDateDesc.append("第二季度");
          sbFilename.append("第二季度");
        } else if (month >= 7 && month <= 9) {
          sbConditionDateDesc.append("第三季度");
          sbFilename.append("第三季度");
        } else if (month >= 10 && month <= 12) {
          sbConditionDateDesc.append("第四季度");
          sbFilename.append("第四季度");
        }

      }
    } else {
      var dateTimeEnd = condition.getDateRange() == null ? 0L : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? 0L : condition.getDateRange().getEnd();
      var instantEnd = Rq.toInstant(dateTimeEnd < 1L ? System.currentTimeMillis() : dateTimeEnd);
      sbConditionDateDesc.append(Rq.getYearString(instantEnd));
      sbConditionDateDesc.append("年");
      sbConditionDateDesc.append(Rq.getMonthString(instantEnd));
      sbConditionDateDesc.append("月");
      sbConditionDateDesc.append(Rq.getDayString(instantEnd));
      sbConditionDateDesc.append("日");
      sbFilename.append("-截至");
      sbFilename.append(Rq.getYearString(instantEnd));
      sbFilename.append("年");
      sbFilename.append(Rq.getMonthString(instantEnd));
      sbFilename.append("月");
      sbFilename.append(Rq.getDayString(instantEnd));
      sbFilename.append("日");
    }
    String conditionDateDesc = "统计周期：" + sbConditionDateDesc;

    var resource = new ClassPathResource("excel-templates/template-stat-inventory-simple.xlsx");
    try (InputStream is = resource.getInputStream();
         var workbook = new XSSFWorkbook(is);
         var os = response.getOutputStream()) {

      int ROW_INDEX_START = 3;

      // Sheet - 库存简报【株数统计】
      {
        var sheet = workbook.getSheetAt(0);

        // 表头
        Cell cellTitle = sheet.getRow(0).getCell(0);
        CellStyle styleTitle = workbook.createCellStyle();
        styleTitle.cloneStyleFrom(cellTitle.getCellStyle());
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, columnsCount - 1);
        sheet.addMergedRegion(region);
        cellTitle.setCellStyle(styleTitle);
        cellTitle.setCellValue("库存简报【株数统计】");
        // 统计描述
        Cell cellConditionDesc = sheet.getRow(1).getCell(0);
        cellConditionDesc.setCellValue(conditionDesc);
        // 统计周期
        Cell cellConditionDateDesc = sheet.getRow(1).getCell(6);
        CellStyle styleConditionDateDesc = workbook.createCellStyle();
        styleConditionDateDesc.cloneStyleFrom(cellConditionDateDesc.getCellStyle());
        region = new CellRangeAddress(1, 1, 6, columnsCount - 1);
        sheet.addMergedRegion(region);
        cellConditionDateDesc.setCellStyle(styleConditionDateDesc);
        cellConditionDateDesc.setCellValue(conditionDateDesc);

        // 复制表头样式
        CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.cloneStyleFrom(sheet.getRow(ROW_INDEX_START - 1).getCell(3).getCellStyle());
        // 按需增加表头
        if (columnsCount > minColumnsCount) {
          var row = sheet.getRow(ROW_INDEX_START - 1);
          // 之所以-1，是因为要给合计留一列
          for (var columnIndex = minColumnsCount; columnIndex < columnsCount - 1; columnIndex++) {
            var cell = row.createCell(columnIndex);
            cell.setCellStyle(styleHeader);
            cell.setCellValue((6 + columnIndex - minColumnsCount) + "代株数");
            // 2926约为80磅
            sheet.setColumnWidth(columnIndex, 2926);
          }
          var cell = row.createCell(columnsCount - 1);
          cell.setCellStyle(styleHeader);
          cell.setCellValue("合计");
          sheet.setColumnWidth(columnsCount - 1, 2926);
        }
        // 复制第一行的样式
        CellStyle style0 = workbook.createCellStyle();
        style0.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(0).getCellStyle());
        CellStyle style1 = workbook.createCellStyle();
        style1.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(1).getCellStyle());
        CellStyle style2 = workbook.createCellStyle();
        style2.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(3).getCellStyle());

        Row delRow = sheet.getRow(ROW_INDEX_START);
        if (delRow != null) {
          sheet.removeRow(delRow);
        }

        int index = -1;

        var statList = statModel.getData();
        for (var stat : statList) {
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(stat.getVariety());
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(stat.getVarietyCode());
          cell = row.createCell(2);
          cell.setCellStyle(style1);
          cell.setCellValue(stat.getImportant() != null && stat.getImportant().equals(Const.BOOLEAN_1) ? "重点品种" : StringUtil.EMPTY);
          // 之所以-1，是因为要给合计留一列
          for (var columnIndex = dataColumnIndex; columnIndex < columnsCount - 1; columnIndex++) {
            cell = row.createCell(columnIndex);
            cell.setCellStyle(style2);
            var model = stat.getGenerationMap().getOrDefault(columnIndex - dataColumnIndex, null);
            if (model != null && model.getPlant() != null && model.getPlant() != 0) {
              cell.setCellValue(model.getPlant());
            }
          }
          cell = row.createCell(columnsCount - 1);
          cell.setCellStyle(style2);
          cell.setCellValue(stat.getPlantSum());
        }


        // 添加过滤
        sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, columnsCount - 1));
        // 添加冻结行
        sheet.createFreezePane(0, ROW_INDEX_START);
      }


      // Sheet - 库存简报【瓶数统计】
      {
        var sheet = workbook.getSheetAt(1);

        // 表头
        Cell cellTitle = sheet.getRow(0).getCell(0);
        CellStyle styleTitle = workbook.createCellStyle();
        styleTitle.cloneStyleFrom(cellTitle.getCellStyle());
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, columnsCount - 1);
        sheet.addMergedRegion(region);
        cellTitle.setCellStyle(styleTitle);
        cellTitle.setCellValue("库存简报【瓶数统计】");
        // 统计描述
        Cell cellConditionDesc = sheet.getRow(1).getCell(0);
        cellConditionDesc.setCellValue(conditionDesc);
        // 统计周期
        Cell cellConditionDateDesc = sheet.getRow(1).getCell(6);
        CellStyle styleConditionDateDesc = workbook.createCellStyle();
        styleConditionDateDesc.cloneStyleFrom(cellConditionDateDesc.getCellStyle());
        region = new CellRangeAddress(1, 1, 6, columnsCount - 1);
        sheet.addMergedRegion(region);
        cellConditionDateDesc.setCellStyle(styleConditionDateDesc);
        cellConditionDateDesc.setCellValue(conditionDateDesc);

        // 复制表头样式
        CellStyle styleHeader = workbook.createCellStyle();
        styleHeader.cloneStyleFrom(sheet.getRow(ROW_INDEX_START - 1).getCell(3).getCellStyle());
        // 按需增加表头
        if (columnsCount > minColumnsCount) {
          var row = sheet.getRow(ROW_INDEX_START - 1);
          // 之所以-1，是因为要给合计留一列
          for (var columnIndex = minColumnsCount; columnIndex < columnsCount - 1; columnIndex++) {
            var cell = row.createCell(columnIndex);
            cell.setCellStyle(styleHeader);
            cell.setCellValue((6 + columnIndex - minColumnsCount) + "代瓶数");
            // 2926约为80磅
            sheet.setColumnWidth(columnIndex, 2926);
          }
          var cell = row.createCell(columnsCount - 1);
          cell.setCellStyle(styleHeader);
          cell.setCellValue("合计");
          sheet.setColumnWidth(columnsCount - 1, 2926);
        }
        // 复制第一行的样式
        CellStyle style0 = workbook.createCellStyle();
        style0.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(0).getCellStyle());
        CellStyle style1 = workbook.createCellStyle();
        style1.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(1).getCellStyle());
        CellStyle style2 = workbook.createCellStyle();
        style2.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(3).getCellStyle());

        Row delRow = sheet.getRow(ROW_INDEX_START);
        if (delRow != null) {
          sheet.removeRow(delRow);
        }

        int index = -1;

        var statList = statModel.getData();
        for (var stat : statList) {
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(stat.getVariety());
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(stat.getVarietyCode());
          cell = row.createCell(2);
          cell.setCellStyle(style1);
          cell.setCellValue(stat.getImportant() != null && stat.getImportant().equals(Const.BOOLEAN_1) ? "重点品种" : StringUtil.EMPTY);
          // 之所以-1，是因为要给合计留一列
          for (var columnIndex = dataColumnIndex; columnIndex < columnsCount - 1; columnIndex++) {
            cell = row.createCell(columnIndex);
            cell.setCellStyle(style2);
            var model = stat.getGenerationMap().getOrDefault(columnIndex - dataColumnIndex, null);
            if (model != null && model.getBottle() != null && model.getBottle() != 0) {
              cell.setCellValue(model.getBottle());
            }
          }
          cell = row.createCell(columnsCount - 1);
          cell.setCellStyle(style2);
          cell.setCellValue(stat.getBottleSum());
        }

        // 添加过滤
        sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, columnsCount - 1));
        // 添加冻结行
        sheet.createFreezePane(0, ROW_INDEX_START);
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

  @PostMapping("excelStatInventory")
  public void excelStatInventory(@RequestBody QueryConditionStatInventory condition, HttpServletResponse response) {
    List<ModeStatInventory> statList = statManager.statInventory(condition);

    var sumModel = new ModeStatInventory(
      "合计",
      StringUtil.EMPTY,
      StringUtil.EMPTY,
      -1,
      0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L
    );
    var varietyMap = new HashMap<String, ModeStatInventory>();
    var varietyCodeMap = new HashMap<String, Map<String, ModeStatInventory>>();
    var detailMap = new HashMap<String, Map<String, List<ModeStatInventory>>>();
    for (var stat : statList) {
      String variety = stat.getVariety();
      String varietyCode = stat.getVarietyCode();
      String important = stat.getImportant();

      var varietyStat = varietyMap.computeIfAbsent(variety, k -> new ModeStatInventory(
        variety,
        StringUtil.EMPTY,
        StringUtil.EMPTY,
        -1,
        0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L
      ));
      varietyStat.setBottleBegin(varietyStat.getBottleBegin() + stat.getBottleBegin());
      varietyStat.setBottleIn(varietyStat.getBottleIn() + stat.getBottleIn());
      varietyStat.setBottleOut(varietyStat.getBottleOut() + stat.getBottleOut());
      varietyStat.setBottleAdj(varietyStat.getBottleAdj() + stat.getBottleAdj());
      varietyStat.setBottleEnd(varietyStat.getBottleEnd() + stat.getBottleEnd());
      varietyStat.setPlantBegin(varietyStat.getPlantBegin() + stat.getPlantBegin());
      varietyStat.setPlantIn(varietyStat.getPlantIn() + stat.getPlantIn());
      varietyStat.setPlantOut(varietyStat.getPlantOut() + stat.getPlantOut());
      varietyStat.setPlantAdj(varietyStat.getPlantAdj() + stat.getPlantAdj());
      varietyStat.setPlantEnd(varietyStat.getPlantEnd() + stat.getPlantEnd());

      var varietyCodeStat = varietyCodeMap.computeIfAbsent(variety, k -> new HashMap<>()).computeIfAbsent(varietyCode, k -> new ModeStatInventory(
        variety,
        varietyCode,
        important,
        -1,
        0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L
      ));
      varietyCodeStat.setBottleBegin(varietyCodeStat.getBottleBegin() + stat.getBottleBegin());
      varietyCodeStat.setBottleIn(varietyCodeStat.getBottleIn() + stat.getBottleIn());
      varietyCodeStat.setBottleOut(varietyCodeStat.getBottleOut() + stat.getBottleOut());
      varietyCodeStat.setBottleAdj(varietyCodeStat.getBottleAdj() + stat.getBottleAdj());
      varietyCodeStat.setBottleEnd(varietyCodeStat.getBottleEnd() + stat.getBottleEnd());
      varietyCodeStat.setPlantBegin(varietyCodeStat.getPlantBegin() + stat.getPlantBegin());
      varietyCodeStat.setPlantIn(varietyCodeStat.getPlantIn() + stat.getPlantIn());
      varietyCodeStat.setPlantOut(varietyCodeStat.getPlantOut() + stat.getPlantOut());
      varietyCodeStat.setPlantAdj(varietyCodeStat.getPlantAdj() + stat.getPlantAdj());
      varietyCodeStat.setPlantEnd(varietyCodeStat.getPlantEnd() + stat.getPlantEnd());


      sumModel.setBottleBegin(sumModel.getBottleBegin() + stat.getBottleBegin());
      sumModel.setBottleIn(sumModel.getBottleIn() + stat.getBottleIn());
      sumModel.setBottleOut(sumModel.getBottleOut() + stat.getBottleOut());
      sumModel.setBottleAdj(sumModel.getBottleAdj() + stat.getBottleAdj());
      sumModel.setBottleEnd(sumModel.getBottleEnd() + stat.getBottleEnd());
      sumModel.setPlantBegin(sumModel.getPlantBegin() + stat.getPlantBegin());
      sumModel.setPlantIn(sumModel.getPlantIn() + stat.getPlantIn());
      sumModel.setPlantOut(sumModel.getPlantOut() + stat.getPlantOut());
      sumModel.setPlantAdj(sumModel.getPlantAdj() + stat.getPlantAdj());
      sumModel.setPlantEnd(sumModel.getPlantEnd() + stat.getPlantEnd());

      if (stat.getGeneration() != null && stat.getGeneration() > -1) {
        var detailStatList = detailMap.computeIfAbsent(variety, k -> new HashMap<>()).computeIfAbsent(varietyCode, k -> new ArrayList<>());
        detailStatList.add(stat);
      }
    }

    var sbFilename = new StringBuilder("库存统计[" + Rq.formatCompactYMD(Rq.getNow()) + "]");
    var sbConditionDesc = new StringBuilder();
    if (condition.getVariety() != null && !condition.getVariety().isBlank()) {
      sbFilename.append("-");
      sbFilename.append(condition.getVariety());
      sbConditionDesc.append("品种：");
      sbConditionDesc.append(condition.getVariety());
      if (condition.getVarietyCode() != null && !condition.getVarietyCode().isBlank()) {
        sbFilename.append("-");
        sbFilename.append(condition.getVarietyCode());
        sbConditionDesc.append("-");
        sbConditionDesc.append(condition.getVarietyCode());
      }
    }
    if (condition.getVarietyCodeImportant() != null && !condition.getVarietyCodeImportant().isBlank()) {
      var desc = condition.getVarietyCodeImportant().equals(Const.BOOLEAN_0) ? "普通品种" : "重点品种";
      sbFilename.append("-");
      sbFilename.append(desc);
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append(desc);
    }
    if (condition.getGeneration() != null && condition.getGeneration() > -1) {
      sbFilename.append("-D");
      sbFilename.append(condition.getGeneration());
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append("D");
      sbConditionDesc.append(condition.getGeneration());
    }
    String conditionDesc = sbConditionDesc.isEmpty() ? StringUtil.EMPTY : "【统计条件】 " + sbConditionDesc;

    var sbConditionDateDesc = new StringBuilder();
    var dateTypeCondition = condition.getDateType();
    if (dateTypeCondition.equals("按月") || dateTypeCondition.equals("按年") || dateTypeCondition.equals("按季")) {
      var dateTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instant = Rq.toInstant(dateTime < 1L ? System.currentTimeMillis() : dateTime);
      var year = Rq.getYearString(instant);
      var month = Rq.getMonth(instant);

      sbConditionDateDesc.append(year);
      sbConditionDateDesc.append("年");
      sbFilename.append("-");
      sbFilename.append(year);
      sbFilename.append("年");

      if (dateTypeCondition.equals("按月")) {
        sbConditionDateDesc.append(month);
        sbConditionDateDesc.append("月");
        sbFilename.append(month);
        sbFilename.append("月");
      } else if (dateTypeCondition.equals("按季")) {
        if (month >= 1 && month <= 3) {
          sbConditionDateDesc.append("第一季度");
          sbFilename.append("第一季度");
        } else if (month >= 4 && month <= 6) {
          sbConditionDateDesc.append("第二季度");
          sbFilename.append("第二季度");
        } else if (month >= 7 && month <= 9) {
          sbConditionDateDesc.append("第三季度");
          sbFilename.append("第三季度");
        } else if (month >= 10 && month <= 12) {
          sbConditionDateDesc.append("第四季度");
          sbFilename.append("第四季度");
        }
      }
    } else {
      var dateTimeStart = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instantStart = Rq.toInstant(dateTimeStart);
      var dateTimeEnd = condition.getDateRange() == null ? 0L : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? 0L : condition.getDateRange().getEnd();
      var instantEnd = Rq.toInstant(dateTimeEnd < 1L ? System.currentTimeMillis() : dateTimeEnd);
      if (dateTimeStart > 0L) {
        sbConditionDateDesc.append("自");
        sbConditionDateDesc.append(Rq.getYearString(instantStart));
        sbConditionDateDesc.append("年");
        sbConditionDateDesc.append(Rq.getMonthString(instantStart));
        sbConditionDateDesc.append("月");
        sbConditionDateDesc.append(Rq.getDayString(instantStart));
        sbConditionDateDesc.append("日");
        sbFilename.append("-自");
        sbFilename.append(Rq.getYearString(instantStart));
        sbFilename.append("年");
        sbFilename.append(Rq.getMonthString(instantStart));
        sbFilename.append("月");
        sbFilename.append(Rq.getDayString(instantStart));
        sbFilename.append("日");
      } else {
        sbConditionDateDesc.append("截");
        sbFilename.append("-截");
      }
      sbConditionDateDesc.append("至");
      sbConditionDateDesc.append(Rq.getYearString(instantEnd));
      sbConditionDateDesc.append("年");
      sbConditionDateDesc.append(Rq.getMonthString(instantEnd));
      sbConditionDateDesc.append("月");
      sbConditionDateDesc.append(Rq.getDayString(instantEnd));
      sbConditionDateDesc.append("日");
      sbFilename.append("至");
      sbFilename.append(Rq.getYearString(instantEnd));
      sbFilename.append("年");
      sbFilename.append(Rq.getMonthString(instantEnd));
      sbFilename.append("月");
      sbFilename.append(Rq.getDayString(instantEnd));
      sbFilename.append("日");
    }
    String conditionDateDesc = "统计周期：" + sbConditionDateDesc;

    var resource = new ClassPathResource("excel-templates/template-stat-inventory.xlsx");
    try (InputStream is = resource.getInputStream();
         var workbook = new XSSFWorkbook(is);
         var os = response.getOutputStream()) {

      var sheet = workbook.getSheetAt(0);
      int ROW_INDEX_START = 4;

      // 统计描述
      Cell cellConditionDesc = sheet.getRow(1).getCell(0);
      cellConditionDesc.setCellValue(conditionDesc);
      // 统计周期
      Cell cellConditionDateDesc = sheet.getRow(1).getCell(8);
      cellConditionDateDesc.setCellValue(conditionDateDesc);

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
      CellStyle style12 = workbook.createCellStyle();
      style12.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(12).getCellStyle());

      Row delRow = sheet.getRow(ROW_INDEX_START);
      if (delRow != null) {
        sheet.removeRow(delRow);
      }

      int index = -1;

      var collator = Collator.getInstance(Locale.CHINA);
      var varietyList = varietyMap.values().stream().sorted(Comparator.comparing(ModeStatInventory::getVariety, collator)).toList();
      for (var varietyStat : varietyList) {
        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue(varietyStat.getVariety());
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        cell.setCellValue(varietyStat.getBottleBegin());
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        cell.setCellValue(varietyStat.getBottleIn());
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        cell.setCellValue(varietyStat.getBottleOut());
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(varietyStat.getBottleAdj());
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        cell.setCellValue(varietyStat.getBottleEnd());
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        cell.setCellValue(varietyStat.getPlantBegin());
        cell = row.createCell(9);
        cell.setCellStyle(style9);
        cell.setCellValue(varietyStat.getPlantIn());
        cell = row.createCell(10);
        cell.setCellStyle(style10);
        cell.setCellValue(varietyStat.getPlantOut());
        cell = row.createCell(11);
        cell.setCellStyle(style11);
        cell.setCellValue(varietyStat.getPlantAdj());
        cell = row.createCell(12);
        cell.setCellStyle(style12);
        cell.setCellValue(varietyStat.getPlantEnd());

        var varietyCodeDataMap = varietyCodeMap.get(varietyStat.getVariety());
        if (varietyCodeDataMap != null) {
          var varietyCodeList = varietyCodeDataMap.values().stream().sorted(Comparator.comparing(ModeStatInventory::getVarietyCode, collator)).toList();
          for (var varietyCodeStat : varietyCodeList) {
            index++;
            row = sheet.createRow(ROW_INDEX_START + index);
            row.setHeightInPoints(24);
            cell = row.createCell(0);
            cell.setCellStyle(style0);
            cell.setCellValue("   " + varietyCodeStat.getVariety());
            cell = row.createCell(1);
            cell.setCellStyle(style1);
            cell.setCellValue(varietyCodeStat.getVarietyCode());
            cell = row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellValue(StringUtil.EMPTY);
            cell = row.createCell(3);
            cell.setCellStyle(style3);
            cell.setCellValue(varietyCodeStat.getBottleBegin());
            cell = row.createCell(4);
            cell.setCellStyle(style4);
            cell.setCellValue(varietyCodeStat.getBottleIn());
            cell = row.createCell(5);
            cell.setCellStyle(style5);
            cell.setCellValue(varietyCodeStat.getBottleOut());
            cell = row.createCell(6);
            cell.setCellStyle(style6);
            cell.setCellValue(varietyCodeStat.getBottleAdj());
            cell = row.createCell(7);
            cell.setCellStyle(style7);
            cell.setCellValue(varietyCodeStat.getBottleEnd());
            cell = row.createCell(8);
            cell.setCellStyle(style8);
            cell.setCellValue(varietyCodeStat.getPlantBegin());
            cell = row.createCell(9);
            cell.setCellStyle(style9);
            cell.setCellValue(varietyCodeStat.getPlantIn());
            cell = row.createCell(10);
            cell.setCellStyle(style10);
            cell.setCellValue(varietyCodeStat.getPlantOut());
            cell = row.createCell(11);
            cell.setCellStyle(style11);
            cell.setCellValue(varietyCodeStat.getPlantAdj());
            cell = row.createCell(12);
            cell.setCellStyle(style12);
            cell.setCellValue(varietyCodeStat.getPlantEnd());

            var detailList = detailMap.computeIfAbsent(varietyCodeStat.getVariety(), k -> new HashMap<>()).computeIfAbsent(varietyCodeStat.getVarietyCode(), k -> new ArrayList<>());
            for (var detailStat : detailList) {
              index++;
              row = sheet.createRow(ROW_INDEX_START + index);
              row.setHeightInPoints(24);
              cell = row.createCell(0);
              cell.setCellStyle(style0);
              cell.setCellValue("      " + detailStat.getVariety());
              cell = row.createCell(1);
              cell.setCellStyle(style1);
              cell.setCellValue(detailStat.getVarietyCode());
              cell = row.createCell(2);
              cell.setCellStyle(style2);
              cell.setCellValue(detailStat.getGeneration());
              cell = row.createCell(3);
              cell.setCellStyle(style3);
              cell.setCellValue(detailStat.getBottleBegin());
              cell = row.createCell(4);
              cell.setCellStyle(style4);
              cell.setCellValue(detailStat.getBottleIn());
              cell = row.createCell(5);
              cell.setCellStyle(style5);
              cell.setCellValue(detailStat.getBottleOut());
              cell = row.createCell(6);
              cell.setCellStyle(style6);
              cell.setCellValue(detailStat.getBottleAdj());
              cell = row.createCell(7);
              cell.setCellStyle(style7);
              cell.setCellValue(detailStat.getBottleEnd());
              cell = row.createCell(8);
              cell.setCellStyle(style8);
              cell.setCellValue(detailStat.getPlantBegin());
              cell = row.createCell(9);
              cell.setCellStyle(style9);
              cell.setCellValue(detailStat.getPlantIn());
              cell = row.createCell(10);
              cell.setCellStyle(style10);
              cell.setCellValue(detailStat.getPlantOut());
              cell = row.createCell(11);
              cell.setCellStyle(style11);
              cell.setCellValue(detailStat.getPlantAdj());
              cell = row.createCell(12);
              cell.setCellStyle(style12);
              cell.setCellValue(detailStat.getPlantEnd());
            }
          }
        }
      }

      index++;
      var row = sheet.createRow(ROW_INDEX_START + index);
      row.setHeightInPoints(24);
      var cell = row.createCell(0);
      cell.setCellStyle(style0);
      cell.setCellValue(sumModel.getVariety());
      cell = row.createCell(1);
      cell.setCellStyle(style1);
      cell.setCellValue(StringUtil.EMPTY);
      cell = row.createCell(2);
      cell.setCellStyle(style2);
      cell.setCellValue(StringUtil.EMPTY);
      cell = row.createCell(3);
      cell.setCellStyle(style3);
      cell.setCellValue(sumModel.getBottleBegin());
      cell = row.createCell(4);
      cell.setCellStyle(style4);
      cell.setCellValue(sumModel.getBottleIn());
      cell = row.createCell(5);
      cell.setCellStyle(style5);
      cell.setCellValue(sumModel.getBottleOut());
      cell = row.createCell(6);
      cell.setCellStyle(style6);
      cell.setCellValue(sumModel.getBottleAdj());
      cell = row.createCell(7);
      cell.setCellStyle(style7);
      cell.setCellValue(sumModel.getBottleEnd());
      cell = row.createCell(8);
      cell.setCellStyle(style8);
      cell.setCellValue(sumModel.getPlantBegin());
      cell = row.createCell(9);
      cell.setCellStyle(style9);
      cell.setCellValue(sumModel.getPlantIn());
      cell = row.createCell(10);
      cell.setCellStyle(style10);
      cell.setCellValue(sumModel.getPlantOut());
      cell = row.createCell(11);
      cell.setCellStyle(style11);
      cell.setCellValue(sumModel.getPlantAdj());
      cell = row.createCell(12);
      cell.setCellStyle(style12);
      cell.setCellValue(sumModel.getPlantEnd());

      sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 12));

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

  @PostMapping("excelStatInventoryAlert")
  public void excelStatInventoryAlert(@RequestBody QueryConditionStatInventory condition, HttpServletResponse response) {
    List<ModeStatInventory> statList = statManager.statInventory(condition);

    var sbFilename = new StringBuilder("库存预警[" + Rq.formatCompactYMD(Rq.getNow()) + "]");
    var sbConditionDesc = new StringBuilder();
    if (condition.getVariety() != null && !condition.getVariety().isBlank()) {
      sbFilename.append("-");
      sbFilename.append(condition.getVariety());
      sbConditionDesc.append("品种：");
      sbConditionDesc.append(condition.getVariety());
      if (condition.getVarietyCode() != null && !condition.getVarietyCode().isBlank()) {
        sbFilename.append("-");
        sbFilename.append(condition.getVarietyCode());
        sbConditionDesc.append("-");
        sbConditionDesc.append(condition.getVarietyCode());
      }
    }
    if (condition.getVarietyCodeImportant() != null && !condition.getVarietyCodeImportant().isBlank()) {
      var desc = condition.getVarietyCodeImportant().equals(Const.BOOLEAN_0) ? "普通品种" : "重点品种";
      sbFilename.append("-");
      sbFilename.append(desc);
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append(desc);
    }
    if (condition.getGeneration() != null && condition.getGeneration() > -1) {
      sbFilename.append("-D");
      sbFilename.append(condition.getGeneration());
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append("D");
      sbConditionDesc.append(condition.getGeneration());
    }
    String conditionDesc = sbConditionDesc.isEmpty() ? StringUtil.EMPTY : "【统计条件】 " + sbConditionDesc;

    var sbConditionDateDesc = new StringBuilder();
    var dateTypeCondition = condition.getDateType();
    if (dateTypeCondition.equals("按月") || dateTypeCondition.equals("按年") || dateTypeCondition.equals("按季")) {
      var dateTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instant = Rq.toInstant(dateTime < 1L ? System.currentTimeMillis() : dateTime);
      var year = Rq.getYearString(instant);
      var month = Rq.getMonth(instant);

      sbConditionDateDesc.append(year);
      sbConditionDateDesc.append("年");
      sbFilename.append("-");
      sbFilename.append(year);
      sbFilename.append("年");

      if (dateTypeCondition.equals("按月")) {
        sbConditionDateDesc.append(month);
        sbConditionDateDesc.append("月");
        sbFilename.append(month);
        sbFilename.append("月");
      } else if (dateTypeCondition.equals("按季")) {
        if (month >= 1 && month <= 3) {
          sbConditionDateDesc.append("第一季度");
          sbFilename.append("第一季度");
        } else if (month >= 4 && month <= 6) {
          sbConditionDateDesc.append("第二季度");
          sbFilename.append("第二季度");
        } else if (month >= 7 && month <= 9) {
          sbConditionDateDesc.append("第三季度");
          sbFilename.append("第三季度");
        } else if (month >= 10 && month <= 12) {
          sbConditionDateDesc.append("第四季度");
          sbFilename.append("第四季度");
        }
      }
    } else {
      var dateTimeStart = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instantStart = Rq.toInstant(dateTimeStart);
      var dateTimeEnd = condition.getDateRange() == null ? 0L : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? 0L : condition.getDateRange().getEnd();
      var instantEnd = Rq.toInstant(dateTimeEnd < 1L ? System.currentTimeMillis() : dateTimeEnd);
      if (dateTimeStart > 0L) {
        sbConditionDateDesc.append("自");
        sbConditionDateDesc.append(Rq.getYearString(instantStart));
        sbConditionDateDesc.append("年");
        sbConditionDateDesc.append(Rq.getMonthString(instantStart));
        sbConditionDateDesc.append("月");
        sbConditionDateDesc.append(Rq.getDayString(instantStart));
        sbConditionDateDesc.append("日");
        sbFilename.append("-自");
        sbFilename.append(Rq.getYearString(instantStart));
        sbFilename.append("年");
        sbFilename.append(Rq.getMonthString(instantStart));
        sbFilename.append("月");
        sbFilename.append(Rq.getDayString(instantStart));
        sbFilename.append("日");
      } else {
        sbConditionDateDesc.append("截");
        sbFilename.append("-截");
      }
      sbConditionDateDesc.append("至");
      sbConditionDateDesc.append(Rq.getYearString(instantEnd));
      sbConditionDateDesc.append("年");
      sbConditionDateDesc.append(Rq.getMonthString(instantEnd));
      sbConditionDateDesc.append("月");
      sbConditionDateDesc.append(Rq.getDayString(instantEnd));
      sbConditionDateDesc.append("日");
      sbFilename.append("至");
      sbFilename.append(Rq.getYearString(instantEnd));
      sbFilename.append("年");
      sbFilename.append(Rq.getMonthString(instantEnd));
      sbFilename.append("月");
      sbFilename.append(Rq.getDayString(instantEnd));
      sbFilename.append("日");
    }
    String conditionDateDesc = "统计周期：" + sbConditionDateDesc;

    var resource = new ClassPathResource("excel-templates/template-stat-inventory-alert.xlsx");
    try (InputStream is = resource.getInputStream();
         var workbook = new XSSFWorkbook(is);
         var os = response.getOutputStream()) {

      var sheet = workbook.getSheetAt(0);
      int ROW_INDEX_START = 4;

      // 统计描述
      Cell cellConditionDesc = sheet.getRow(1).getCell(0);
      cellConditionDesc.setCellValue(conditionDesc);
      // 统计周期
      Cell cellConditionDateDesc = sheet.getRow(1).getCell(8);
      cellConditionDateDesc.setCellValue(conditionDateDesc);

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
      CellStyle style12 = workbook.createCellStyle();
      style12.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(12).getCellStyle());

      Row delRow = sheet.getRow(ROW_INDEX_START);
      if (delRow != null) {
        sheet.removeRow(delRow);
      }

      int index = -1;

      for (var stat : statList) {
        if (stat.getBottleEnd() > (condition.getBottleAlertMin() == null ? Long.MAX_VALUE : condition.getBottleAlertMin())
          && stat.getPlantEnd() > (condition.getPlantAlertMin() == null ? Long.MAX_VALUE : condition.getPlantAlertMin())) {
          continue;
        }
        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue(stat.getVariety());
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue(stat.getVarietyCode());
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(stat.getGeneration() == null || stat.getGeneration() < 0 ? StringUtil.EMPTY : stat.getGeneration().toString());
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        cell.setCellValue(stat.getBottleBegin());
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        cell.setCellValue(stat.getBottleIn());
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        cell.setCellValue(stat.getBottleOut());
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(stat.getBottleAdj());
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        cell.setCellValue(stat.getBottleEnd());
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        cell.setCellValue(stat.getPlantBegin());
        cell = row.createCell(9);
        cell.setCellStyle(style9);
        cell.setCellValue(stat.getPlantIn());
        cell = row.createCell(10);
        cell.setCellStyle(style10);
        cell.setCellValue(stat.getPlantOut());
        cell = row.createCell(11);
        cell.setCellStyle(style11);
        cell.setCellValue(stat.getPlantAdj());
        cell = row.createCell(12);
        cell.setCellStyle(style12);
        cell.setCellValue(stat.getPlantEnd());
      }

      sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 12));

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

  @PostMapping("excelStatInventoryTransaction")
  public void excelStatInventoryTransaction(@RequestBody QueryConditionStatInventory condition, HttpServletResponse response) {
    var statModel = statManager.statInventoryTransaction(condition);

    var sbFilename = new StringBuilder("库存流水[" + Rq.formatCompactYMD(Rq.getNow()) + "]");
    var sbConditionDesc = new StringBuilder();
    if (condition.getVariety() != null && !condition.getVariety().isBlank()) {
      sbFilename.append("-");
      sbFilename.append(condition.getVariety());
      sbConditionDesc.append("品种：");
      sbConditionDesc.append(condition.getVariety());
      if (condition.getVarietyCode() != null && !condition.getVarietyCode().isBlank()) {
        sbFilename.append("-");
        sbFilename.append(condition.getVarietyCode());
        sbConditionDesc.append("-");
        sbConditionDesc.append(condition.getVarietyCode());
      }
    }
    if (condition.getVarietyCodeImportant() != null && !condition.getVarietyCodeImportant().isBlank()) {
      var desc = condition.getVarietyCodeImportant().equals(Const.BOOLEAN_0) ? "普通品种" : "重点品种";
      sbFilename.append("-");
      sbFilename.append(desc);
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append(desc);
    }
    if (condition.getGeneration() != null && condition.getGeneration() > -1) {
      sbFilename.append("-D");
      sbFilename.append(condition.getGeneration());
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append("D");
      sbConditionDesc.append(condition.getGeneration());
    }
    String conditionDesc = sbConditionDesc.isEmpty() ? StringUtil.EMPTY : "【统计条件】 " + sbConditionDesc;

    var sbConditionDateDesc = new StringBuilder();
    var dateTypeCondition = condition.getDateType();
    if (dateTypeCondition.equals("按月") || dateTypeCondition.equals("按年") || dateTypeCondition.equals("按季")) {
      var dateTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instant = Rq.toInstant(dateTime < 1L ? System.currentTimeMillis() : dateTime);
      var year = Rq.getYearString(instant);
      var month = Rq.getMonth(instant);

      sbConditionDateDesc.append(year);
      sbConditionDateDesc.append("年");
      sbFilename.append("-");
      sbFilename.append(year);
      sbFilename.append("年");

      if (dateTypeCondition.equals("按月")) {
        sbConditionDateDesc.append(month);
        sbConditionDateDesc.append("月");
        sbFilename.append(month);
        sbFilename.append("月");
      } else if (dateTypeCondition.equals("按季")) {
        if (month >= 1 && month <= 3) {
          sbConditionDateDesc.append("第一季度");
          sbFilename.append("第一季度");
        } else if (month >= 4 && month <= 6) {
          sbConditionDateDesc.append("第二季度");
          sbFilename.append("第二季度");
        } else if (month >= 7 && month <= 9) {
          sbConditionDateDesc.append("第三季度");
          sbFilename.append("第三季度");
        } else if (month >= 10 && month <= 12) {
          sbConditionDateDesc.append("第四季度");
          sbFilename.append("第四季度");
        }

      }
    } else {
      var dateTimeStart = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instantStart = Rq.toInstant(dateTimeStart);
      var dateTimeEnd = condition.getDateRange() == null ? 0L : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? 0L : condition.getDateRange().getEnd();
      var instantEnd = Rq.toInstant(dateTimeEnd < 1L ? System.currentTimeMillis() : dateTimeEnd);
      if (dateTimeStart > 0L) {
        sbConditionDateDesc.append("自");
        sbConditionDateDesc.append(Rq.getYearString(instantStart));
        sbConditionDateDesc.append("年");
        sbConditionDateDesc.append(Rq.getMonthString(instantStart));
        sbConditionDateDesc.append("月");
        sbConditionDateDesc.append(Rq.getDayString(instantStart));
        sbConditionDateDesc.append("日");
        sbFilename.append("-自");
        sbFilename.append(Rq.getYearString(instantStart));
        sbFilename.append("年");
        sbFilename.append(Rq.getMonthString(instantStart));
        sbFilename.append("月");
        sbFilename.append(Rq.getDayString(instantStart));
        sbFilename.append("日");
      } else {
        sbConditionDateDesc.append("截");
        sbFilename.append("-截");
      }
      sbConditionDateDesc.append("至");
      sbConditionDateDesc.append(Rq.getYearString(instantEnd));
      sbConditionDateDesc.append("年");
      sbConditionDateDesc.append(Rq.getMonthString(instantEnd));
      sbConditionDateDesc.append("月");
      sbConditionDateDesc.append(Rq.getDayString(instantEnd));
      sbConditionDateDesc.append("日");
      sbFilename.append("至");
      sbFilename.append(Rq.getYearString(instantEnd));
      sbFilename.append("年");
      sbFilename.append(Rq.getMonthString(instantEnd));
      sbFilename.append("月");
      sbFilename.append(Rq.getDayString(instantEnd));
      sbFilename.append("日");
    }
    String conditionDateDesc = "统计周期：" + sbConditionDateDesc;

    var resource = new ClassPathResource("excel-templates/template-stat-inventory-tx.xlsx");
    try (InputStream is = resource.getInputStream();
         var workbook = new XSSFWorkbook(is);
         var os = response.getOutputStream()) {

      var sheet = workbook.getSheetAt(0);
      int ROW_INDEX_START = 3;

      // 统计描述
      Cell cellConditionDesc = sheet.getRow(1).getCell(0);
      cellConditionDesc.setCellValue(conditionDesc);
      // 统计周期
      Cell cellConditionDateDesc = sheet.getRow(1).getCell(6);
      cellConditionDateDesc.setCellValue(conditionDateDesc);

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

      var stat = statModel.getStartData();
      if (stat != null) {
        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue("上期结转");
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(stat.getBottles());
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        cell.setCellValue(stat.getPlants());
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(9);
        cell.setCellStyle(style9);
        cell.setCellValue(StringUtil.EMPTY);
      }

      var statList = statModel.getDetails();
      if (statList != null && !statList.isEmpty()) {
        for (var i = 1; i <= statList.size(); i++) {
          stat = statList.get(i - 1);
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(i);
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(stat.getVariety());
          cell = row.createCell(2);
          cell.setCellStyle(style2);
          cell.setCellValue(stat.getVarietyCode());
          cell = row.createCell(3);
          cell.setCellStyle(style3);
          cell.setCellValue(stat.getGeneration() == null ? StringUtil.EMPTY : stat.getGeneration().toString());
          cell = row.createCell(4);
          cell.setCellStyle(style4);
          cell.setCellValue(stat.getBizType() == null ? StringUtil.EMPTY : dictionaryCache.getName("inventory_tx_biz_type", stat.getBizType()));
          cell = row.createCell(5);
          cell.setCellStyle(style5);
          cell.setCellValue(stat.getDescription() == null ? StringUtil.EMPTY : stat.getDescription());
          cell = row.createCell(6);
          cell.setCellStyle(style6);
          cell.setCellValue(stat.getBottles());
          cell = row.createCell(7);
          cell.setCellStyle(style7);
          cell.setCellValue(stat.getPlants());
          cell = row.createCell(8);
          cell.setCellStyle(style8);
          cell.setCellValue(stat.getOperateTime() == null || stat.getOperateTime() < 1L ? StringUtil.EMPTY : Rq.formatISOYMDHMS(stat.getOperateTime()));
          cell = row.createCell(9);
          cell.setCellStyle(style9);
          cell.setCellValue(stat.getOperatorName() == null ? StringUtil.EMPTY : stat.getOperatorName());
        }
      }

      stat = statModel.getEndData();
      if (stat != null) {
        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue("期末余额");
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(stat.getBottles());
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        cell.setCellValue(stat.getPlants());
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(9);
        cell.setCellStyle(style9);
        cell.setCellValue(StringUtil.EMPTY);
      }

      sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 9));

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

  @PostMapping("excelStatWorkload")
  public void excelStatWorkload(@RequestBody QueryConditionStatWorkload condition, HttpServletResponse response) {
    List<ModeStatWorkload> statList = statManager.statWorkload(condition);

    String userRole = condition.getUserRole() == null ? "操作员" : condition.getUserRole().equals("inspector") ? "质检员" : "操作员";

    var sumModel = new ModeStatWorkload(
      StringUtil.EMPTY,
      "合计",
      userRole,
      StringUtil.EMPTY,
      StringUtil.EMPTY,
      -1,
      0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,
      0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L
    );
    var userMap = new HashMap<String, ModeStatWorkload>();
    var userVarietyMap = new HashMap<String, Map<String, ModeStatWorkload>>();
    var userVarietyCodeMap = new HashMap<String, Map<String, Map<String, ModeStatWorkload>>>();
    var detailMap = new HashMap<String, Map<String, Map<String, List<ModeStatWorkload>>>>();
    for (var stat : statList) {
      String userId = stat.getUserId();
      String userName = stat.getUserName();
      String variety = stat.getVariety() == null ? StringUtil.EMPTY : stat.getVariety();
      String varietyCode = stat.getVarietyCode() == null ? StringUtil.EMPTY : stat.getVarietyCode();

      var userStat = userMap.computeIfAbsent(userId, k -> new ModeStatWorkload(
        userId,
        userName,
        userRole,
        StringUtil.EMPTY,
        StringUtil.EMPTY,
        -1,
        0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,
        0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L
      ));
      userStat.setBottleBegin(userStat.getBottleBegin() + stat.getBottleBegin());
      userStat.setBottleIn(userStat.getBottleIn() + stat.getBottleIn());
      userStat.setBottleOut(userStat.getBottleOut() + stat.getBottleOut());
      userStat.setBottleAdj(userStat.getBottleAdj() + stat.getBottleAdj());
      userStat.setBottleEnd(userStat.getBottleEnd() + stat.getBottleEnd());
      userStat.setBottleBeginDiscard(userStat.getBottleBeginDiscard() + stat.getBottleBeginDiscard());
      userStat.setBottleDiscard(userStat.getBottleDiscard() + stat.getBottleDiscard());
      userStat.setBottleEndDiscard(userStat.getBottleEndDiscard() + stat.getBottleEndDiscard());
      userStat.setPlantBegin(userStat.getPlantBegin() + stat.getPlantBegin());
      userStat.setPlantIn(userStat.getPlantIn() + stat.getPlantIn());
      userStat.setPlantOut(userStat.getPlantOut() + stat.getPlantOut());
      userStat.setPlantAdj(userStat.getPlantAdj() + stat.getPlantAdj());
      userStat.setPlantEnd(userStat.getPlantEnd() + stat.getPlantEnd());
      userStat.setPlantBeginDiscard(userStat.getPlantBeginDiscard() + stat.getPlantBeginDiscard());
      userStat.setPlantDiscard(userStat.getPlantDiscard() + stat.getPlantDiscard());
      userStat.setPlantEndDiscard(userStat.getPlantEndDiscard() + stat.getPlantEndDiscard());

      var userVarietyStat = userVarietyMap.computeIfAbsent(userId, k -> new HashMap<>()).computeIfAbsent(variety, k -> new ModeStatWorkload(
        userId,
        userName,
        userRole,
        variety,
        StringUtil.EMPTY,
        -1,
        0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,
        0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L
      ));
      userVarietyStat.setBottleBegin(userVarietyStat.getBottleBegin() + stat.getBottleBegin());
      userVarietyStat.setBottleIn(userVarietyStat.getBottleIn() + stat.getBottleIn());
      userVarietyStat.setBottleOut(userVarietyStat.getBottleOut() + stat.getBottleOut());
      userVarietyStat.setBottleAdj(userVarietyStat.getBottleAdj() + stat.getBottleAdj());
      userVarietyStat.setBottleEnd(userVarietyStat.getBottleEnd() + stat.getBottleEnd());
      userVarietyStat.setBottleBeginDiscard(userVarietyStat.getBottleBeginDiscard() + stat.getBottleBeginDiscard());
      userVarietyStat.setBottleDiscard(userVarietyStat.getBottleDiscard() + stat.getBottleDiscard());
      userVarietyStat.setBottleEndDiscard(userVarietyStat.getBottleEndDiscard() + stat.getBottleEndDiscard());
      userVarietyStat.setPlantBegin(userVarietyStat.getPlantBegin() + stat.getPlantBegin());
      userVarietyStat.setPlantIn(userVarietyStat.getPlantIn() + stat.getPlantIn());
      userVarietyStat.setPlantOut(userVarietyStat.getPlantOut() + stat.getPlantOut());
      userVarietyStat.setPlantAdj(userVarietyStat.getPlantAdj() + stat.getPlantAdj());
      userVarietyStat.setPlantEnd(userVarietyStat.getPlantEnd() + stat.getPlantEnd());
      userVarietyStat.setPlantBeginDiscard(userVarietyStat.getPlantBeginDiscard() + stat.getPlantBeginDiscard());
      userVarietyStat.setPlantDiscard(userVarietyStat.getPlantDiscard() + stat.getPlantDiscard());
      userVarietyStat.setPlantEndDiscard(userVarietyStat.getPlantEndDiscard() + stat.getPlantEndDiscard());

      var userVarietyCodeStat = userVarietyCodeMap.computeIfAbsent(userId, k -> new HashMap<>())
        .computeIfAbsent(variety, k -> new HashMap<>())
        .computeIfAbsent(varietyCode, k -> new ModeStatWorkload(
          userId,
          userName,
          userRole,
          variety,
          varietyCode,
          -1,
          0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,
          0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L
        ));
      userVarietyCodeStat.setBottleBegin(userVarietyCodeStat.getBottleBegin() + stat.getBottleBegin());
      userVarietyCodeStat.setBottleIn(userVarietyCodeStat.getBottleIn() + stat.getBottleIn());
      userVarietyCodeStat.setBottleOut(userVarietyCodeStat.getBottleOut() + stat.getBottleOut());
      userVarietyCodeStat.setBottleAdj(userVarietyCodeStat.getBottleAdj() + stat.getBottleAdj());
      userVarietyCodeStat.setBottleEnd(userVarietyCodeStat.getBottleEnd() + stat.getBottleEnd());
      userVarietyCodeStat.setBottleBeginDiscard(userVarietyCodeStat.getBottleBeginDiscard() + stat.getBottleBeginDiscard());
      userVarietyCodeStat.setBottleDiscard(userVarietyCodeStat.getBottleDiscard() + stat.getBottleDiscard());
      userVarietyCodeStat.setBottleEndDiscard(userVarietyCodeStat.getBottleEndDiscard() + stat.getBottleEndDiscard());
      userVarietyCodeStat.setPlantBegin(userVarietyCodeStat.getPlantBegin() + stat.getPlantBegin());
      userVarietyCodeStat.setPlantIn(userVarietyCodeStat.getPlantIn() + stat.getPlantIn());
      userVarietyCodeStat.setPlantOut(userVarietyCodeStat.getPlantOut() + stat.getPlantOut());
      userVarietyCodeStat.setPlantAdj(userVarietyCodeStat.getPlantAdj() + stat.getPlantAdj());
      userVarietyCodeStat.setPlantEnd(userVarietyCodeStat.getPlantEnd() + stat.getPlantEnd());
      userVarietyCodeStat.setPlantBeginDiscard(userVarietyCodeStat.getPlantBeginDiscard() + stat.getPlantBeginDiscard());
      userVarietyCodeStat.setPlantDiscard(userVarietyCodeStat.getPlantDiscard() + stat.getPlantDiscard());
      userVarietyCodeStat.setPlantEndDiscard(userVarietyCodeStat.getPlantEndDiscard() + stat.getPlantEndDiscard());


      sumModel.setBottleBegin(sumModel.getBottleBegin() + stat.getBottleBegin());
      sumModel.setBottleIn(sumModel.getBottleIn() + stat.getBottleIn());
      sumModel.setBottleOut(sumModel.getBottleOut() + stat.getBottleOut());
      sumModel.setBottleAdj(sumModel.getBottleAdj() + stat.getBottleAdj());
      sumModel.setBottleEnd(sumModel.getBottleEnd() + stat.getBottleEnd());
      sumModel.setBottleBeginDiscard(sumModel.getBottleBeginDiscard() + stat.getBottleBeginDiscard());
      sumModel.setBottleDiscard(sumModel.getBottleDiscard() + stat.getBottleDiscard());
      sumModel.setBottleEndDiscard(sumModel.getBottleEndDiscard() + stat.getBottleEndDiscard());
      sumModel.setPlantBegin(sumModel.getPlantBegin() + stat.getPlantBegin());
      sumModel.setPlantIn(sumModel.getPlantIn() + stat.getPlantIn());
      sumModel.setPlantOut(sumModel.getPlantOut() + stat.getPlantOut());
      sumModel.setPlantAdj(sumModel.getPlantAdj() + stat.getPlantAdj());
      sumModel.setPlantEnd(sumModel.getPlantEnd() + stat.getPlantEnd());
      sumModel.setPlantBeginDiscard(sumModel.getPlantBeginDiscard() + stat.getPlantBeginDiscard());
      sumModel.setPlantDiscard(sumModel.getPlantDiscard() + stat.getPlantDiscard());
      sumModel.setPlantEndDiscard(sumModel.getPlantEndDiscard() + stat.getPlantEndDiscard());

      if (stat.getGeneration() != null && stat.getGeneration() > -1) {
        var detailStatList = detailMap.computeIfAbsent(userId, k -> new HashMap<>())
          .computeIfAbsent(variety, k -> new HashMap<>())
          .computeIfAbsent(varietyCode, k -> new ArrayList<>());
        detailStatList.add(stat);
      }
    }

    var sbFilename = new StringBuilder("绩效统计-" + userRole + "[" + Rq.formatCompactYMD(Rq.getNow()) + "]");
    var sbConditionDesc = new StringBuilder();
    if (condition.getVariety() != null && !condition.getVariety().isBlank()) {
      sbFilename.append("-");
      sbFilename.append(condition.getVariety());
      sbConditionDesc.append("品种：");
      sbConditionDesc.append(condition.getVariety());
      if (condition.getVarietyCode() != null && !condition.getVarietyCode().isBlank()) {
        sbFilename.append("-");
        sbFilename.append(condition.getVarietyCode());
        sbConditionDesc.append("-");
        sbConditionDesc.append(condition.getVarietyCode());
      }
    }
    if (condition.getGeneration() != null && condition.getGeneration() > -1) {
      sbFilename.append("-D");
      sbFilename.append(condition.getGeneration());
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append("D");
      sbConditionDesc.append(condition.getGeneration());
    }
    String conditionDesc = sbConditionDesc.isEmpty() ? StringUtil.EMPTY : "【统计条件】 " + sbConditionDesc;

    var sbConditionDateDesc = new StringBuilder();
    var dateTypeCondition = condition.getDateType();
    if (dateTypeCondition.equals("按月") || dateTypeCondition.equals("按年") || dateTypeCondition.equals("按季")) {
      var dateTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instant = Rq.toInstant(dateTime < 1L ? System.currentTimeMillis() : dateTime);
      var year = Rq.getYearString(instant);
      var month = Rq.getMonth(instant);

      sbConditionDateDesc.append(year);
      sbConditionDateDesc.append("年");
      sbFilename.append("-");
      sbFilename.append(year);
      sbFilename.append("年");

      if (dateTypeCondition.equals("按月")) {
        sbConditionDateDesc.append(month);
        sbConditionDateDesc.append("月");
        sbFilename.append(month);
        sbFilename.append("月");
      } else if (dateTypeCondition.equals("按季")) {
        if (month >= 1 && month <= 3) {
          sbConditionDateDesc.append("第一季度");
          sbFilename.append("第一季度");
        } else if (month >= 4 && month <= 6) {
          sbConditionDateDesc.append("第二季度");
          sbFilename.append("第二季度");
        } else if (month >= 7 && month <= 9) {
          sbConditionDateDesc.append("第三季度");
          sbFilename.append("第三季度");
        } else if (month >= 10 && month <= 12) {
          sbConditionDateDesc.append("第四季度");
          sbFilename.append("第四季度");
        }
      }
    } else {
      var dateTimeStart = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instantStart = Rq.toInstant(dateTimeStart);
      var dateTimeEnd = condition.getDateRange() == null ? 0L : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? 0L : condition.getDateRange().getEnd();
      var instantEnd = Rq.toInstant(dateTimeEnd < 1L ? System.currentTimeMillis() : dateTimeEnd);
      if (dateTimeStart > 0L) {
        sbConditionDateDesc.append("自");
        sbConditionDateDesc.append(Rq.getYearString(instantStart));
        sbConditionDateDesc.append("年");
        sbConditionDateDesc.append(Rq.getMonthString(instantStart));
        sbConditionDateDesc.append("月");
        sbConditionDateDesc.append(Rq.getDayString(instantStart));
        sbConditionDateDesc.append("日");
        sbFilename.append("-自");
        sbFilename.append(Rq.getYearString(instantStart));
        sbFilename.append("年");
        sbFilename.append(Rq.getMonthString(instantStart));
        sbFilename.append("月");
        sbFilename.append(Rq.getDayString(instantStart));
        sbFilename.append("日");
      } else {
        sbConditionDateDesc.append("截");
        sbFilename.append("-截");
      }
      sbConditionDateDesc.append("至");
      sbConditionDateDesc.append(Rq.getYearString(instantEnd));
      sbConditionDateDesc.append("年");
      sbConditionDateDesc.append(Rq.getMonthString(instantEnd));
      sbConditionDateDesc.append("月");
      sbConditionDateDesc.append(Rq.getDayString(instantEnd));
      sbConditionDateDesc.append("日");
      sbFilename.append("至");
      sbFilename.append(Rq.getYearString(instantEnd));
      sbFilename.append("年");
      sbFilename.append(Rq.getMonthString(instantEnd));
      sbFilename.append("月");
      sbFilename.append(Rq.getDayString(instantEnd));
      sbFilename.append("日");
    }
    String conditionDateDesc = "统计周期：" + sbConditionDateDesc;

    var resource = new ClassPathResource("excel-templates/template-stat-workload.xlsx");
    try (InputStream is = resource.getInputStream();
         var workbook = new XSSFWorkbook(is);
         var os = response.getOutputStream()) {

      var collator = Collator.getInstance(Locale.CHINA);

      // Sheet - 绩效简报
      {
        var sheet = workbook.getSheetAt(0);
        int ROW_INDEX_START = 4;

        // 表头
        Cell cellTitle = sheet.getRow(0).getCell(0);
        cellTitle.setCellValue("绩效简报【" + userRole + "绩效】");
        // 统计描述
        Cell cellConditionDesc = sheet.getRow(1).getCell(0);
        cellConditionDesc.setCellValue(conditionDesc);
        // 统计周期
        Cell cellConditionDateDesc = sheet.getRow(1).getCell(11);
        cellConditionDateDesc.setCellValue(conditionDateDesc);

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
        CellStyle style12 = workbook.createCellStyle();
        style12.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(12).getCellStyle());
        CellStyle style13 = workbook.createCellStyle();
        style13.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(13).getCellStyle());
        CellStyle style14 = workbook.createCellStyle();
        style14.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(14).getCellStyle());
        CellStyle style15 = workbook.createCellStyle();
        style15.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(15).getCellStyle());
        CellStyle style16 = workbook.createCellStyle();
        style16.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(16).getCellStyle());

        Row delRow = sheet.getRow(ROW_INDEX_START);
        if (delRow != null) {
          sheet.removeRow(delRow);
        }

        int index = -1;

        var userList = userMap.values().stream().sorted(Comparator.comparing(ModeStatWorkload::getUserName, collator)).toList();
        for (var userStat : userList) {
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(userStat.getUserName());

          cell = row.createCell(1);
          cell.setCellStyle(style1);
          var bottles = userStat.getBottleEnd() - userStat.getBottleBegin();
          cell.setCellValue(bottles);
          cell = row.createCell(2);
          cell.setCellStyle(style2);
          var bottleCurrentSum = sumModel.getBottleEnd() - sumModel.getBottleBegin();
          var bottlePercent = bottles < 1L ? 0d : bottleCurrentSum < 1L ? 1d : ((double) bottles / bottleCurrentSum);
          cell.setCellValue(bottlePercent);
          cell = row.createCell(3);
          cell.setCellStyle(style3);
          var bottleDiscard = userStat.getBottleDiscard();
          bottleDiscard = bottleDiscard == null || bottleDiscard < 1L ? 0L : bottleDiscard;
          if (bottleDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscard);
          cell = row.createCell(4);
          cell.setCellStyle(style4);
          var bottleDiscardPercent = bottleDiscard < 1L ? 0d : (bottles + bottleDiscard) < 1L ? 1d : ((double) bottleDiscard / (bottles + bottleDiscard));
          if (bottleDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscardPercent);
          cell = row.createCell(5);
          cell.setCellStyle(style5);
          var bottleEnd = userStat.getBottleEnd();
          cell.setCellValue(bottleEnd);
          cell = row.createCell(6);
          cell.setCellStyle(style6);
          var bottleEndSum = sumModel.getBottleEnd();
          var bottleEndPercent = bottleEnd < 1L ? 0d : bottleEndSum < 1L ? 1d : ((double) bottleEnd / bottleEndSum);
          cell.setCellValue(bottleEndPercent);
          cell = row.createCell(7);
          cell.setCellStyle(style7);
          var bottleDiscardEnd = userStat.getBottleEndDiscard();
          if (bottleDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscardEnd);
          cell = row.createCell(8);
          cell.setCellStyle(style8);
          var bottleDiscardEndPercent = bottleDiscardEnd < 1L ? 0d : (bottleEnd + bottleDiscardEnd) < 1L ? 1d : ((double) bottleDiscardEnd / (bottleEnd + bottleDiscardEnd));
          if (bottleDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscardEndPercent);


          cell = row.createCell(9);
          cell.setCellStyle(style9);
          var plants = userStat.getPlantEnd() - userStat.getPlantBegin();
          cell.setCellValue(plants);
          cell = row.createCell(10);
          cell.setCellStyle(style10);
          var plantCurrentSum = sumModel.getPlantEnd() - sumModel.getPlantBegin();
          var plantPercent = plants < 1L ? 0d : plantCurrentSum < 1L ? 1d : ((double) plants / plantCurrentSum);
          cell.setCellValue(plantPercent);
          cell = row.createCell(11);
          cell.setCellStyle(style11);
          var plantDiscard = userStat.getPlantDiscard();
          plantDiscard = plantDiscard == null || plantDiscard < 1L ? 0L : plantDiscard;
          if (plantDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscard);
          cell = row.createCell(12);
          cell.setCellStyle(style12);
          var plantDiscardPercent = plantDiscard < 1L ? 0d : (plants + plantDiscard) < 1L ? 1d : ((double) plantDiscard / (plants + plantDiscard));
          if (plantDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscardPercent);
          cell = row.createCell(13);
          cell.setCellStyle(style13);
          var plantEnd = userStat.getPlantEnd();
          cell.setCellValue(plantEnd);
          cell = row.createCell(14);
          cell.setCellStyle(style14);
          var plantEndSum = sumModel.getPlantEnd();
          var plantEndPercent = plantEnd < 1L ? 0d : plantEndSum < 1L ? 1d : ((double) plantEnd / plantEndSum);
          cell.setCellValue(plantEndPercent);
          cell = row.createCell(15);
          cell.setCellStyle(style15);
          var plantDiscardEnd = userStat.getPlantEndDiscard();
          if (plantDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscardEnd);
          cell = row.createCell(16);
          cell.setCellStyle(style16);
          var plantDiscardEndPercent = plantDiscardEnd < 1L ? 0d : (plantEnd + plantDiscardEnd) < 1L ? 1d : ((double) plantDiscardEnd / (plantEnd + plantDiscardEnd));
          if (plantDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscardEndPercent);
        }

        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue(sumModel.getUserName());

        cell = row.createCell(1);
        cell.setCellStyle(style1);
        var bottles = sumModel.getBottleEnd() - sumModel.getBottleBegin();
        cell.setCellValue(bottles);
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(1d);
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        var bottleDiscard = sumModel.getBottleDiscard();
        bottleDiscard = bottleDiscard == null || bottleDiscard < 1L ? 0L : bottleDiscard;
        if (bottleDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscard);
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        var bottleDiscardPercent = bottleDiscard < 1L ? 0d : (bottles + bottleDiscard) < 1L ? 1d : ((double) bottleDiscard / (bottles + bottleDiscard));
        if (bottleDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscardPercent);
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        var bottleEnd = sumModel.getBottleEnd();
        cell.setCellValue(bottleEnd);
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(1d);
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        var bottleDiscardEnd = sumModel.getBottleEndDiscard();
        if (bottleDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscardEnd);
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        var bottleDiscardEndPercent = bottleDiscardEnd < 1L ? 0d : (bottleEnd + bottleDiscardEnd) < 1L ? 1d : ((double) bottleDiscardEnd / (bottleEnd + bottleDiscardEnd));
        if (bottleDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscardEndPercent);


        cell = row.createCell(9);
        cell.setCellStyle(style9);
        var plants = sumModel.getPlantEnd() - sumModel.getPlantBegin();
        cell.setCellValue(plants);
        cell = row.createCell(10);
        cell.setCellStyle(style10);
        cell.setCellValue(1d);
        cell = row.createCell(11);
        cell.setCellStyle(style11);
        var plantDiscard = sumModel.getPlantDiscard();
        plantDiscard = plantDiscard == null || plantDiscard < 1L ? 0L : plantDiscard;
        if (plantDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscard);
        cell = row.createCell(12);
        cell.setCellStyle(style12);
        var plantDiscardPercent = plantDiscard < 1L ? 0d : (plants + plantDiscard) < 1L ? 1d : ((double) plantDiscard / (plants + plantDiscard));
        if (plantDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscardPercent);
        cell = row.createCell(13);
        cell.setCellStyle(style13);
        var plantEnd = sumModel.getPlantEnd();
        cell.setCellValue(plantEnd);
        cell = row.createCell(14);
        cell.setCellStyle(style14);
        cell.setCellValue(1d);
        cell = row.createCell(15);
        cell.setCellStyle(style15);
        var plantDiscardEnd = sumModel.getPlantEndDiscard();
        if (plantDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscardEnd);
        cell = row.createCell(16);
        cell.setCellStyle(style16);
        var plantDiscardEndPercent = plantDiscardEnd < 1L ? 0d : (plantEnd + plantDiscardEnd) < 1L ? 1d : ((double) plantDiscardEnd / (plantEnd + plantDiscardEnd));
        if (plantDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscardEndPercent);

        sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 16));
      }

      // Sheet - 绩效详情-按瓶数统计
      {
        var sheet = workbook.getSheetAt(1);
        int ROW_INDEX_START = 4;

        // 表头
        Cell cellTitle = sheet.getRow(0).getCell(0);
        cellTitle.setCellValue("绩效详情-按瓶数统计【" + userRole + "绩效】");
        // 统计描述
        Cell cellConditionDesc = sheet.getRow(1).getCell(0);
        cellConditionDesc.setCellValue(conditionDesc);
        // 统计周期
        Cell cellConditionDateDesc = sheet.getRow(1).getCell(10);
        cellConditionDateDesc.setCellValue(conditionDateDesc);

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
        CellStyle style12 = workbook.createCellStyle();
        style12.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(12).getCellStyle());
        CellStyle style13 = workbook.createCellStyle();
        style13.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(13).getCellStyle());
        CellStyle style14 = workbook.createCellStyle();
        style14.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(14).getCellStyle());

        Row delRow = sheet.getRow(ROW_INDEX_START);
        if (delRow != null) {
          sheet.removeRow(delRow);
        }

        int index = -1;

        var userList = userMap.values().stream().sorted(Comparator.comparing(ModeStatWorkload::getUserName, collator)).toList();
        for (var userStat : userList) {
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(userStat.getUserName());
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(StringUtil.EMPTY);
          cell = row.createCell(2);
          cell.setCellStyle(style2);
          cell.setCellValue(StringUtil.EMPTY);
          cell = row.createCell(3);
          cell.setCellStyle(style3);
          cell.setCellValue(StringUtil.EMPTY);
          cell = row.createCell(4);
          cell.setCellStyle(style4);
          cell.setCellValue(userStat.getBottleBegin());
          cell = row.createCell(5);
          cell.setCellStyle(style5);
          cell.setCellValue(userStat.getBottleIn());
          cell = row.createCell(6);
          cell.setCellStyle(style6);
          cell.setCellValue(userStat.getBottleOut());
          cell = row.createCell(7);
          cell.setCellStyle(style7);
          cell.setCellValue(userStat.getBottleAdj());
          cell = row.createCell(8);
          cell.setCellStyle(style8);
          var bottles = userStat.getBottleEnd() - userStat.getBottleBegin();
          cell.setCellValue(bottles);
          cell = row.createCell(9);
          cell.setCellStyle(style9);
          var bottleEnd = userStat.getBottleEnd();
          cell.setCellValue(bottleEnd);
          cell = row.createCell(10);
          cell.setCellStyle(style10);
          var bottleDiscardBegin = userStat.getBottleBeginDiscard();
          if (bottleDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscardBegin);
          cell = row.createCell(11);
          cell.setCellStyle(style11);
          var bottleDiscard = userStat.getBottleDiscard();
          bottleDiscard = bottleDiscard == null || bottleDiscard < 1L ? 0L : bottleDiscard;
          if (bottleDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscard);
          cell = row.createCell(12);
          cell.setCellStyle(style12);
          var bottleDiscardPercent = bottleDiscard < 1L ? 0d : (bottles + bottleDiscard) < 1L ? 1d : ((double) bottleDiscard / (bottles + bottleDiscard));
          if (bottleDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscardPercent);
          cell = row.createCell(13);
          cell.setCellStyle(style13);
          var bottleDiscardEnd = userStat.getBottleEndDiscard();
          if (bottleDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscardEnd);
          cell = row.createCell(14);
          cell.setCellStyle(style14);
          var bottleDiscardEndPercent = bottleDiscardEnd < 1L ? 0d : (bottleEnd + bottleDiscardEnd) < 1L ? 1d : ((double) bottleDiscardEnd / (bottleEnd + bottleDiscardEnd));
          if (bottleDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(bottleDiscardEndPercent);

          var varietyList = userVarietyMap.computeIfAbsent(userStat.getUserId(), k -> new HashMap<>()).values().stream().sorted(Comparator.comparing(ModeStatWorkload::getVariety, collator)).toList();
          for (var varietyStat : varietyList) {
            index++;
            row = sheet.createRow(ROW_INDEX_START + index);
            row.setHeightInPoints(24);
            cell = row.createCell(0);
            cell.setCellStyle(style0);
            cell.setCellValue("   " + varietyStat.getUserName());
            cell = row.createCell(1);
            cell.setCellStyle(style1);
            cell.setCellValue(varietyStat.getVariety());
            cell = row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellValue(StringUtil.EMPTY);
            cell = row.createCell(3);
            cell.setCellStyle(style3);
            cell.setCellValue(StringUtil.EMPTY);
            cell = row.createCell(4);
            cell.setCellStyle(style4);
            cell.setCellValue(varietyStat.getBottleBegin());
            cell = row.createCell(5);
            cell.setCellStyle(style5);
            cell.setCellValue(varietyStat.getBottleIn());
            cell = row.createCell(6);
            cell.setCellStyle(style6);
            cell.setCellValue(varietyStat.getBottleOut());
            cell = row.createCell(7);
            cell.setCellStyle(style7);
            cell.setCellValue(varietyStat.getBottleAdj());
            cell = row.createCell(8);
            cell.setCellStyle(style8);
            bottles = varietyStat.getBottleEnd() - varietyStat.getBottleBegin();
            cell.setCellValue(bottles);
            cell = row.createCell(9);
            cell.setCellStyle(style9);
            bottleEnd = varietyStat.getBottleEnd();
            cell.setCellValue(bottleEnd);
            cell = row.createCell(10);
            cell.setCellStyle(style10);
            bottleDiscardBegin = varietyStat.getBottleBeginDiscard();
            if (bottleDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(bottleDiscardBegin);
            cell = row.createCell(11);
            cell.setCellStyle(style11);
            bottleDiscard = varietyStat.getBottleDiscard();
            bottleDiscard = bottleDiscard == null || bottleDiscard < 1L ? 0L : bottleDiscard;
            if (bottleDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(bottleDiscard);
            cell = row.createCell(12);
            cell.setCellStyle(style12);
            bottleDiscardPercent = bottleDiscard < 1L ? 0d : (bottles + bottleDiscard) < 1L ? 1d : ((double) bottleDiscard / (bottles + bottleDiscard));
            if (bottleDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(bottleDiscardPercent);
            cell = row.createCell(13);
            cell.setCellStyle(style13);
            bottleDiscardEnd = varietyStat.getBottleEndDiscard();
            if (bottleDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(bottleDiscardEnd);
            cell = row.createCell(14);
            cell.setCellStyle(style14);
            bottleDiscardEndPercent = bottleDiscardEnd < 1L ? 0d : (bottleEnd + bottleDiscardEnd) < 1L ? 1d : ((double) bottleDiscardEnd / (bottleEnd + bottleDiscardEnd));
            if (bottleDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(bottleDiscardEndPercent);


            var varietyCodeList = userVarietyCodeMap.computeIfAbsent(varietyStat.getUserId(), k -> new HashMap<>())
              .computeIfAbsent(varietyStat.getVariety(), k -> new HashMap<>()).values().stream().sorted(Comparator.comparing(ModeStatWorkload::getVarietyCode, collator)).toList();
            for (var varietyCodeStat : varietyCodeList) {
              index++;
              row = sheet.createRow(ROW_INDEX_START + index);
              row.setHeightInPoints(24);
              cell = row.createCell(0);
              cell.setCellStyle(style0);
              cell.setCellValue("      " + varietyCodeStat.getUserName());
              cell = row.createCell(1);
              cell.setCellStyle(style1);
              cell.setCellValue(varietyCodeStat.getVariety());
              cell = row.createCell(2);
              cell.setCellStyle(style2);
              cell.setCellValue(varietyCodeStat.getVarietyCode());
              cell = row.createCell(3);
              cell.setCellStyle(style3);
              cell.setCellValue(StringUtil.EMPTY);
              cell = row.createCell(4);
              cell.setCellStyle(style4);
              cell.setCellValue(varietyCodeStat.getBottleBegin());
              cell = row.createCell(5);
              cell.setCellStyle(style5);
              cell.setCellValue(varietyCodeStat.getBottleIn());
              cell = row.createCell(6);
              cell.setCellStyle(style6);
              cell.setCellValue(varietyCodeStat.getBottleOut());
              cell = row.createCell(7);
              cell.setCellStyle(style7);
              cell.setCellValue(varietyCodeStat.getBottleAdj());
              cell = row.createCell(8);
              cell.setCellStyle(style8);
              bottles = varietyCodeStat.getBottleEnd() - varietyCodeStat.getBottleBegin();
              cell.setCellValue(bottles);
              cell = row.createCell(9);
              cell.setCellStyle(style9);
              bottleEnd = varietyCodeStat.getBottleEnd();
              cell.setCellValue(bottleEnd);
              cell = row.createCell(10);
              cell.setCellStyle(style10);
              bottleDiscardBegin = varietyCodeStat.getBottleBeginDiscard();
              if (bottleDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(bottleDiscardBegin);
              cell = row.createCell(11);
              cell.setCellStyle(style11);
              bottleDiscard = varietyCodeStat.getBottleDiscard();
              bottleDiscard = bottleDiscard == null || bottleDiscard < 1L ? 0L : bottleDiscard;
              if (bottleDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(bottleDiscard);
              cell = row.createCell(12);
              cell.setCellStyle(style12);
              bottleDiscardPercent = bottleDiscard < 1L ? 0d : (bottles + bottleDiscard) < 1L ? 1d : ((double) bottleDiscard / (bottles + bottleDiscard));
              if (bottleDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(bottleDiscardPercent);
              cell = row.createCell(13);
              cell.setCellStyle(style13);
              bottleDiscardEnd = varietyCodeStat.getBottleEndDiscard();
              if (bottleDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(bottleDiscardEnd);
              cell = row.createCell(14);
              cell.setCellStyle(style14);
              bottleDiscardEndPercent = bottleDiscardEnd < 1L ? 0d : (bottleEnd + bottleDiscardEnd) < 1L ? 1d : ((double) bottleDiscardEnd / (bottleEnd + bottleDiscardEnd));
              if (bottleDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(bottleDiscardEndPercent);

              var detailList = detailMap.computeIfAbsent(varietyCodeStat.getUserId(), k -> new HashMap<>())
                .computeIfAbsent(varietyCodeStat.getVariety(), k -> new HashMap<>())
                .computeIfAbsent(varietyCodeStat.getVarietyCode(), k -> new ArrayList<>());
              for (var detailStat : detailList) {
                index++;
                row = sheet.createRow(ROW_INDEX_START + index);
                row.setHeightInPoints(24);
                cell = row.createCell(0);
                cell.setCellStyle(style0);
                cell.setCellValue("         " + detailStat.getUserName());
                cell = row.createCell(1);
                cell.setCellStyle(style1);
                cell.setCellValue(detailStat.getVariety());
                cell = row.createCell(2);
                cell.setCellStyle(style2);
                cell.setCellValue(detailStat.getVarietyCode());
                cell = row.createCell(3);
                cell.setCellStyle(style3);
                cell.setCellValue(detailStat.getGeneration() == null ? StringUtil.EMPTY : detailStat.getGeneration().toString());
                cell = row.createCell(4);
                cell.setCellStyle(style4);
                cell.setCellValue(detailStat.getBottleBegin());
                cell = row.createCell(5);
                cell.setCellStyle(style5);
                cell.setCellValue(detailStat.getBottleIn());
                cell = row.createCell(6);
                cell.setCellStyle(style6);
                cell.setCellValue(detailStat.getBottleOut());
                cell = row.createCell(7);
                cell.setCellStyle(style7);
                cell.setCellValue(detailStat.getBottleAdj());
                cell = row.createCell(8);
                cell.setCellStyle(style8);
                bottles = detailStat.getBottleEnd() - detailStat.getBottleBegin();
                cell.setCellValue(bottles);
                cell = row.createCell(9);
                cell.setCellStyle(style9);
                bottleEnd = detailStat.getBottleEnd();
                cell.setCellValue(bottleEnd);
                cell = row.createCell(10);
                cell.setCellStyle(style10);
                bottleDiscardBegin = detailStat.getBottleBeginDiscard();
                if (bottleDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(bottleDiscardBegin);
                cell = row.createCell(11);
                cell.setCellStyle(style11);
                bottleDiscard = detailStat.getBottleDiscard();
                bottleDiscard = bottleDiscard == null || bottleDiscard < 1L ? 0L : bottleDiscard;
                if (bottleDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(bottleDiscard);
                cell = row.createCell(12);
                cell.setCellStyle(style12);
                bottleDiscardPercent = bottleDiscard < 1L ? 0d : (bottles + bottleDiscard) < 1L ? 1d : ((double) bottleDiscard / (bottles + bottleDiscard));
                if (bottleDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(bottleDiscardPercent);
                cell = row.createCell(13);
                cell.setCellStyle(style13);
                bottleDiscardEnd = detailStat.getBottleEndDiscard();
                if (bottleDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(bottleDiscardEnd);
                cell = row.createCell(14);
                cell.setCellStyle(style14);
                bottleDiscardEndPercent = bottleDiscardEnd < 1L ? 0d : (bottleEnd + bottleDiscardEnd) < 1L ? 1d : ((double) bottleDiscardEnd / (bottleEnd + bottleDiscardEnd));
                if (bottleDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(bottleDiscardEndPercent);
              }
            }
          }
        }

        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue("         " + sumModel.getUserName());
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue(sumModel.getVariety());
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(sumModel.getVarietyCode());
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        cell.setCellValue(sumModel.getGeneration() == null ? StringUtil.EMPTY : sumModel.getGeneration().toString());
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        cell.setCellValue(sumModel.getBottleBegin());
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        cell.setCellValue(sumModel.getBottleIn());
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(sumModel.getBottleOut());
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        cell.setCellValue(sumModel.getBottleAdj());
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        var bottles = sumModel.getBottleEnd() - sumModel.getBottleBegin();
        cell.setCellValue(bottles);
        cell = row.createCell(9);
        cell.setCellStyle(style9);
        var bottleEnd = sumModel.getBottleEnd();
        cell.setCellValue(bottleEnd);
        cell = row.createCell(10);
        cell.setCellStyle(style10);
        var bottleDiscardBegin = sumModel.getBottleBeginDiscard();
        if (bottleDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscardBegin);
        cell = row.createCell(11);
        cell.setCellStyle(style11);
        var bottleDiscard = sumModel.getBottleDiscard();
        bottleDiscard = bottleDiscard == null || bottleDiscard < 1L ? 0L : bottleDiscard;
        if (bottleDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscard);
        cell = row.createCell(12);
        cell.setCellStyle(style12);
        var bottleDiscardPercent = bottleDiscard < 1L ? 0d : (bottles + bottleDiscard) < 1L ? 1d : ((double) bottleDiscard / (bottles + bottleDiscard));
        if (bottleDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscardPercent);
        cell = row.createCell(13);
        cell.setCellStyle(style13);
        var bottleDiscardEnd = sumModel.getBottleEndDiscard();
        if (bottleDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscardEnd);
        cell = row.createCell(14);
        cell.setCellStyle(style14);
        var bottleDiscardEndPercent = bottleDiscardEnd < 1L ? 0d : (bottleEnd + bottleDiscardEnd) < 1L ? 1d : ((double) bottleDiscardEnd / (bottleEnd + bottleDiscardEnd));
        if (bottleDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(bottleDiscardEndPercent);

        sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 14));
      }

      // Sheet - 绩效详情-按株数统计
      {
        var sheet = workbook.getSheetAt(2);
        int ROW_INDEX_START = 4;

        // 表头
        Cell cellTitle = sheet.getRow(0).getCell(0);
        cellTitle.setCellValue("绩效详情-按株数统计【" + userRole + "绩效】");
        // 统计描述
        Cell cellConditionDesc = sheet.getRow(1).getCell(0);
        cellConditionDesc.setCellValue(conditionDesc);
        // 统计周期
        Cell cellConditionDateDesc = sheet.getRow(1).getCell(10);
        cellConditionDateDesc.setCellValue(conditionDateDesc);

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
        CellStyle style12 = workbook.createCellStyle();
        style12.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(12).getCellStyle());
        CellStyle style13 = workbook.createCellStyle();
        style13.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(13).getCellStyle());
        CellStyle style14 = workbook.createCellStyle();
        style14.cloneStyleFrom(sheet.getRow(ROW_INDEX_START).getCell(14).getCellStyle());

        Row delRow = sheet.getRow(ROW_INDEX_START);
        if (delRow != null) {
          sheet.removeRow(delRow);
        }

        int index = -1;

        var userList = userMap.values().stream().sorted(Comparator.comparing(ModeStatWorkload::getUserName, collator)).toList();
        for (var userStat : userList) {
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(userStat.getUserName());
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(StringUtil.EMPTY);
          cell = row.createCell(2);
          cell.setCellStyle(style2);
          cell.setCellValue(StringUtil.EMPTY);
          cell = row.createCell(3);
          cell.setCellStyle(style3);
          cell.setCellValue(StringUtil.EMPTY);
          cell = row.createCell(4);
          cell.setCellStyle(style4);
          cell.setCellValue(userStat.getPlantBegin());
          cell = row.createCell(5);
          cell.setCellStyle(style5);
          cell.setCellValue(userStat.getPlantIn());
          cell = row.createCell(6);
          cell.setCellStyle(style6);
          cell.setCellValue(userStat.getPlantOut());
          cell = row.createCell(7);
          cell.setCellStyle(style7);
          cell.setCellValue(userStat.getPlantAdj());
          cell = row.createCell(8);
          cell.setCellStyle(style8);
          var plants = userStat.getPlantEnd() - userStat.getPlantBegin();
          cell.setCellValue(plants);
          cell = row.createCell(9);
          cell.setCellStyle(style9);
          var plantEnd = userStat.getPlantEnd();
          cell.setCellValue(plantEnd);
          cell = row.createCell(10);
          cell.setCellStyle(style10);
          var plantDiscardBegin = userStat.getPlantBeginDiscard();
          if (plantDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscardBegin);
          cell = row.createCell(11);
          cell.setCellStyle(style11);
          var plantDiscard = userStat.getPlantDiscard();
          plantDiscard = plantDiscard == null || plantDiscard < 1L ? 0L : plantDiscard;
          if (plantDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscard);
          cell = row.createCell(12);
          cell.setCellStyle(style12);
          var plantDiscardPercent = plantDiscard < 1L ? 0d : (plants + plantDiscard) < 1L ? 1d : ((double) plantDiscard / (plants + plantDiscard));
          if (plantDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscardPercent);
          cell = row.createCell(13);
          cell.setCellStyle(style13);
          var plantDiscardEnd = userStat.getPlantEndDiscard();
          if (plantDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscardEnd);
          cell = row.createCell(14);
          cell.setCellStyle(style14);
          var plantDiscardEndPercent = plantDiscardEnd < 1L ? 0d : (plantEnd + plantDiscardEnd) < 1L ? 1d : ((double) plantDiscardEnd / (plantEnd + plantDiscardEnd));
          if (plantDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
          else cell.setCellValue(plantDiscardEndPercent);

          var varietyList = userVarietyMap.computeIfAbsent(userStat.getUserId(), k -> new HashMap<>()).values().stream().sorted(Comparator.comparing(ModeStatWorkload::getVariety, collator)).toList();
          for (var varietyStat : varietyList) {
            index++;
            row = sheet.createRow(ROW_INDEX_START + index);
            row.setHeightInPoints(24);
            cell = row.createCell(0);
            cell.setCellStyle(style0);
            cell.setCellValue("   " + varietyStat.getUserName());
            cell = row.createCell(1);
            cell.setCellStyle(style1);
            cell.setCellValue(varietyStat.getVariety());
            cell = row.createCell(2);
            cell.setCellStyle(style2);
            cell.setCellValue(StringUtil.EMPTY);
            cell = row.createCell(3);
            cell.setCellStyle(style3);
            cell.setCellValue(StringUtil.EMPTY);
            cell = row.createCell(4);
            cell.setCellStyle(style4);
            cell.setCellValue(varietyStat.getPlantBegin());
            cell = row.createCell(5);
            cell.setCellStyle(style5);
            cell.setCellValue(varietyStat.getPlantIn());
            cell = row.createCell(6);
            cell.setCellStyle(style6);
            cell.setCellValue(varietyStat.getPlantOut());
            cell = row.createCell(7);
            cell.setCellStyle(style7);
            cell.setCellValue(varietyStat.getPlantAdj());
            cell = row.createCell(8);
            cell.setCellStyle(style8);
            plants = varietyStat.getPlantEnd() - varietyStat.getPlantBegin();
            cell.setCellValue(plants);
            cell = row.createCell(9);
            cell.setCellStyle(style9);
            plantEnd = varietyStat.getPlantEnd();
            cell.setCellValue(plantEnd);
            cell = row.createCell(10);
            cell.setCellStyle(style10);
            plantDiscardBegin = varietyStat.getPlantBeginDiscard();
            if (plantDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(plantDiscardBegin);
            cell = row.createCell(11);
            cell.setCellStyle(style11);
            plantDiscard = varietyStat.getPlantDiscard();
            plantDiscard = plantDiscard == null || plantDiscard < 1L ? 0L : plantDiscard;
            if (plantDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(plantDiscard);
            cell = row.createCell(12);
            cell.setCellStyle(style12);
            plantDiscardPercent = plantDiscard < 1L ? 0d : (plants + plantDiscard) < 1L ? 1d : ((double) plantDiscard / (plants + plantDiscard));
            if (plantDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(plantDiscardPercent);
            cell = row.createCell(13);
            cell.setCellStyle(style13);
            plantDiscardEnd = varietyStat.getPlantEndDiscard();
            if (plantDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(plantDiscardEnd);
            cell = row.createCell(14);
            cell.setCellStyle(style14);
            plantDiscardEndPercent = plantDiscardEnd < 1L ? 0d : (plantEnd + plantDiscardEnd) < 1L ? 1d : ((double) plantDiscardEnd / (plantEnd + plantDiscardEnd));
            if (plantDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
            else cell.setCellValue(plantDiscardEndPercent);


            var varietyCodeList = userVarietyCodeMap.computeIfAbsent(varietyStat.getUserId(), k -> new HashMap<>())
              .computeIfAbsent(varietyStat.getVariety(), k -> new HashMap<>()).values().stream().sorted(Comparator.comparing(ModeStatWorkload::getVarietyCode, collator)).toList();
            for (var varietyCodeStat : varietyCodeList) {
              index++;
              row = sheet.createRow(ROW_INDEX_START + index);
              row.setHeightInPoints(24);
              cell = row.createCell(0);
              cell.setCellStyle(style0);
              cell.setCellValue("      " + varietyCodeStat.getUserName());
              cell = row.createCell(1);
              cell.setCellStyle(style1);
              cell.setCellValue(varietyCodeStat.getVariety());
              cell = row.createCell(2);
              cell.setCellStyle(style2);
              cell.setCellValue(varietyCodeStat.getVarietyCode());
              cell = row.createCell(3);
              cell.setCellStyle(style3);
              cell.setCellValue(StringUtil.EMPTY);
              cell = row.createCell(4);
              cell.setCellStyle(style4);
              cell.setCellValue(varietyCodeStat.getPlantBegin());
              cell = row.createCell(5);
              cell.setCellStyle(style5);
              cell.setCellValue(varietyCodeStat.getPlantIn());
              cell = row.createCell(6);
              cell.setCellStyle(style6);
              cell.setCellValue(varietyCodeStat.getPlantOut());
              cell = row.createCell(7);
              cell.setCellStyle(style7);
              cell.setCellValue(varietyCodeStat.getPlantAdj());
              cell = row.createCell(8);
              cell.setCellStyle(style8);
              plants = varietyCodeStat.getPlantEnd() - varietyCodeStat.getPlantBegin();
              cell.setCellValue(plants);
              cell = row.createCell(9);
              cell.setCellStyle(style9);
              plantEnd = varietyCodeStat.getPlantEnd();
              cell.setCellValue(plantEnd);
              cell = row.createCell(10);
              cell.setCellStyle(style10);
              plantDiscardBegin = varietyCodeStat.getPlantBeginDiscard();
              if (plantDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(plantDiscardBegin);
              cell = row.createCell(11);
              cell.setCellStyle(style11);
              plantDiscard = varietyCodeStat.getPlantDiscard();
              plantDiscard = plantDiscard == null || plantDiscard < 1L ? 0L : plantDiscard;
              if (plantDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(plantDiscard);
              cell = row.createCell(12);
              cell.setCellStyle(style12);
              plantDiscardPercent = plantDiscard < 1L ? 0d : (plants + plantDiscard) < 1L ? 1d : ((double) plantDiscard / (plants + plantDiscard));
              if (plantDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(plantDiscardPercent);
              cell = row.createCell(13);
              cell.setCellStyle(style13);
              plantDiscardEnd = varietyCodeStat.getPlantEndDiscard();
              if (plantDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(plantDiscardEnd);
              cell = row.createCell(14);
              cell.setCellStyle(style14);
              plantDiscardEndPercent = plantDiscardEnd < 1L ? 0d : (plantEnd + plantDiscardEnd) < 1L ? 1d : ((double) plantDiscardEnd / (plantEnd + plantDiscardEnd));
              if (plantDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
              else cell.setCellValue(plantDiscardEndPercent);

              var detailList = detailMap.computeIfAbsent(varietyCodeStat.getUserId(), k -> new HashMap<>())
                .computeIfAbsent(varietyCodeStat.getVariety(), k -> new HashMap<>())
                .computeIfAbsent(varietyCodeStat.getVarietyCode(), k -> new ArrayList<>());
              for (var detailStat : detailList) {
                index++;
                row = sheet.createRow(ROW_INDEX_START + index);
                row.setHeightInPoints(24);
                cell = row.createCell(0);
                cell.setCellStyle(style0);
                cell.setCellValue("         " + detailStat.getUserName());
                cell = row.createCell(1);
                cell.setCellStyle(style1);
                cell.setCellValue(detailStat.getVariety());
                cell = row.createCell(2);
                cell.setCellStyle(style2);
                cell.setCellValue(detailStat.getVarietyCode());
                cell = row.createCell(3);
                cell.setCellStyle(style3);
                cell.setCellValue(detailStat.getGeneration() == null ? StringUtil.EMPTY : detailStat.getGeneration().toString());
                cell = row.createCell(4);
                cell.setCellStyle(style4);
                cell.setCellValue(detailStat.getPlantBegin());
                cell = row.createCell(5);
                cell.setCellStyle(style5);
                cell.setCellValue(detailStat.getPlantIn());
                cell = row.createCell(6);
                cell.setCellStyle(style6);
                cell.setCellValue(detailStat.getPlantOut());
                cell = row.createCell(7);
                cell.setCellStyle(style7);
                cell.setCellValue(detailStat.getPlantAdj());
                cell = row.createCell(8);
                cell.setCellStyle(style8);
                plants = detailStat.getPlantEnd() - detailStat.getPlantBegin();
                cell.setCellValue(plants);
                cell = row.createCell(9);
                cell.setCellStyle(style9);
                plantEnd = detailStat.getPlantEnd();
                cell.setCellValue(plantEnd);
                cell = row.createCell(10);
                cell.setCellStyle(style10);
                plantDiscardBegin = detailStat.getPlantBeginDiscard();
                if (plantDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(plantDiscardBegin);
                cell = row.createCell(11);
                cell.setCellStyle(style11);
                plantDiscard = detailStat.getPlantDiscard();
                plantDiscard = plantDiscard == null || plantDiscard < 1L ? 0L : plantDiscard;
                if (plantDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(plantDiscard);
                cell = row.createCell(12);
                cell.setCellStyle(style12);
                plantDiscardPercent = plantDiscard < 1L ? 0d : (plants + plantDiscard) < 1L ? 1d : ((double) plantDiscard / (plants + plantDiscard));
                if (plantDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(plantDiscardPercent);
                cell = row.createCell(13);
                cell.setCellStyle(style13);
                plantDiscardEnd = detailStat.getPlantEndDiscard();
                if (plantDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(plantDiscardEnd);
                cell = row.createCell(14);
                cell.setCellStyle(style14);
                plantDiscardEndPercent = plantDiscardEnd < 1L ? 0d : (plantEnd + plantDiscardEnd) < 1L ? 1d : ((double) plantDiscardEnd / (plantEnd + plantDiscardEnd));
                if (plantDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
                else cell.setCellValue(plantDiscardEndPercent);
              }
            }
          }
        }

        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue("         " + sumModel.getUserName());
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue(sumModel.getVariety());
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(sumModel.getVarietyCode());
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        cell.setCellValue(sumModel.getGeneration() == null ? StringUtil.EMPTY : sumModel.getGeneration().toString());
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        cell.setCellValue(sumModel.getPlantBegin());
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        cell.setCellValue(sumModel.getPlantIn());
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(sumModel.getPlantOut());
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        cell.setCellValue(sumModel.getPlantAdj());
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        var plants = sumModel.getPlantEnd() - sumModel.getPlantBegin();
        cell.setCellValue(plants);
        cell = row.createCell(9);
        cell.setCellStyle(style9);
        var plantEnd = sumModel.getPlantEnd();
        cell.setCellValue(plantEnd);
        cell = row.createCell(10);
        cell.setCellStyle(style10);
        var plantDiscardBegin = sumModel.getPlantBeginDiscard();
        if (plantDiscardBegin < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscardBegin);
        cell = row.createCell(11);
        cell.setCellStyle(style11);
        var plantDiscard = sumModel.getPlantDiscard();
        plantDiscard = plantDiscard == null || plantDiscard < 1L ? 0L : plantDiscard;
        if (plantDiscard < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscard);
        cell = row.createCell(12);
        cell.setCellStyle(style12);
        var plantDiscardPercent = plantDiscard < 1L ? 0d : (plants + plantDiscard) < 1L ? 1d : ((double) plantDiscard / (plants + plantDiscard));
        if (plantDiscardPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscardPercent);
        cell = row.createCell(13);
        cell.setCellStyle(style13);
        var plantDiscardEnd = sumModel.getPlantEndDiscard();
        if (plantDiscardEnd < 1L) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscardEnd);
        cell = row.createCell(14);
        cell.setCellStyle(style14);
        var plantDiscardEndPercent = plantDiscardEnd < 1L ? 0d : (plantEnd + plantDiscardEnd) < 1L ? 1d : ((double) plantDiscardEnd / (plantEnd + plantDiscardEnd));
        if (plantDiscardEndPercent == 0d) cell.setCellValue(StringUtil.EMPTY);
        else cell.setCellValue(plantDiscardEndPercent);

        sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 14));
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

  @PostMapping("excelStatWorkloadTransaction")
  public void excelStatWorkloadTransaction(@RequestBody QueryConditionStatWorkload condition, HttpServletResponse response) {
    var statModel = statManager.statWorkloadTransaction(condition);

    String userRole = condition.getUserRole() == null ? "操作员" : condition.getUserRole().equals("inspector") ? "质检员" : "操作员";

    var sbFilename = new StringBuilder("绩效流水-" + userRole + "[" + Rq.formatCompactYMD(Rq.getNow()) + "]");
    var sbConditionDesc = new StringBuilder();
    if (condition.getVariety() != null && !condition.getVariety().isBlank()) {
      sbFilename.append("-");
      sbFilename.append(condition.getVariety());
      sbConditionDesc.append("品种：");
      sbConditionDesc.append(condition.getVariety());
      if (condition.getVarietyCode() != null && !condition.getVarietyCode().isBlank()) {
        sbFilename.append("-");
        sbFilename.append(condition.getVarietyCode());
        sbConditionDesc.append("-");
        sbConditionDesc.append(condition.getVarietyCode());
      }
    }
    if (condition.getGeneration() != null && condition.getGeneration() > -1) {
      sbFilename.append("-D");
      sbFilename.append(condition.getGeneration());
      if (sbConditionDesc.isEmpty()) {
        sbConditionDesc.append(" ");
      }
      sbConditionDesc.append("D");
      sbConditionDesc.append(condition.getGeneration());
    }
    String conditionDesc = sbConditionDesc.isEmpty() ? StringUtil.EMPTY : "【统计条件】 " + sbConditionDesc;

    var sbConditionDateDesc = new StringBuilder();
    var dateTypeCondition = condition.getDateType();
    if (dateTypeCondition.equals("按月") || dateTypeCondition.equals("按年") || dateTypeCondition.equals("按季")) {
      var dateTime = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instant = Rq.toInstant(dateTime < 1L ? System.currentTimeMillis() : dateTime);
      var year = Rq.getYearString(instant);
      var month = Rq.getMonth(instant);

      sbConditionDateDesc.append(year);
      sbConditionDateDesc.append("年");
      sbFilename.append("-");
      sbFilename.append(year);
      sbFilename.append("年");

      if (dateTypeCondition.equals("按月")) {
        sbConditionDateDesc.append(month);
        sbConditionDateDesc.append("月");
        sbFilename.append(month);
        sbFilename.append("月");
      } else if (dateTypeCondition.equals("按季")) {
        if (month >= 1 && month <= 3) {
          sbConditionDateDesc.append("第一季度");
          sbFilename.append("第一季度");
        } else if (month >= 4 && month <= 6) {
          sbConditionDateDesc.append("第二季度");
          sbFilename.append("第二季度");
        } else if (month >= 7 && month <= 9) {
          sbConditionDateDesc.append("第三季度");
          sbFilename.append("第三季度");
        } else if (month >= 10 && month <= 12) {
          sbConditionDateDesc.append("第四季度");
          sbFilename.append("第四季度");
        }

      }
    } else {
      var dateTimeStart = condition.getDateRange() == null ? 0L : condition.getDateRange().getStart() == null || condition.getDateRange().getStart() < 1L ? 0L : condition.getDateRange().getStart();
      var instantStart = Rq.toInstant(dateTimeStart);
      var dateTimeEnd = condition.getDateRange() == null ? 0L : condition.getDateRange().getEnd() == null || condition.getDateRange().getEnd() < 1L ? 0L : condition.getDateRange().getEnd();
      var instantEnd = Rq.toInstant(dateTimeEnd < 1L ? System.currentTimeMillis() : dateTimeEnd);
      if (dateTimeStart > 0L) {
        sbConditionDateDesc.append("自");
        sbConditionDateDesc.append(Rq.getYearString(instantStart));
        sbConditionDateDesc.append("年");
        sbConditionDateDesc.append(Rq.getMonthString(instantStart));
        sbConditionDateDesc.append("月");
        sbConditionDateDesc.append(Rq.getDayString(instantStart));
        sbConditionDateDesc.append("日");
        sbFilename.append("-自");
        sbFilename.append(Rq.getYearString(instantStart));
        sbFilename.append("年");
        sbFilename.append(Rq.getMonthString(instantStart));
        sbFilename.append("月");
        sbFilename.append(Rq.getDayString(instantStart));
        sbFilename.append("日");
      } else {
        sbConditionDateDesc.append("截");
        sbFilename.append("-截");
      }
      sbConditionDateDesc.append("至");
      sbConditionDateDesc.append(Rq.getYearString(instantEnd));
      sbConditionDateDesc.append("年");
      sbConditionDateDesc.append(Rq.getMonthString(instantEnd));
      sbConditionDateDesc.append("月");
      sbConditionDateDesc.append(Rq.getDayString(instantEnd));
      sbConditionDateDesc.append("日");
      sbFilename.append("至");
      sbFilename.append(Rq.getYearString(instantEnd));
      sbFilename.append("年");
      sbFilename.append(Rq.getMonthString(instantEnd));
      sbFilename.append("月");
      sbFilename.append(Rq.getDayString(instantEnd));
      sbFilename.append("日");
    }
    String conditionDateDesc = "统计周期：" + sbConditionDateDesc;

    var resource = new ClassPathResource("excel-templates/template-stat-workload-tx.xlsx");
    try (InputStream is = resource.getInputStream();
         var workbook = new XSSFWorkbook(is);
         var os = response.getOutputStream()) {

      var sheet = workbook.getSheetAt(0);
      int ROW_INDEX_START = 3;

      // 表头
      Cell cellTitleDesc = sheet.getRow(0).getCell(0);
      cellTitleDesc.setCellValue("绩效流水【" + userRole + "】");
      // 统计描述
      Cell cellConditionDesc = sheet.getRow(1).getCell(0);
      cellConditionDesc.setCellValue(conditionDesc);
      // 统计周期
      Cell cellConditionDateDesc = sheet.getRow(1).getCell(7);
      cellConditionDateDesc.setCellValue(conditionDateDesc);

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

      Row delRow = sheet.getRow(ROW_INDEX_START);
      if (delRow != null) {
        sheet.removeRow(delRow);
      }

      int index = -1;

      var stat = statModel.getStartData();
      if (stat != null) {
        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue("上期结转");
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        cell.setCellValue(stat.getBottles());
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        cell.setCellValue(stat.getPlants());
        cell = row.createCell(9);
        cell.setCellStyle(style9);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(10);
        cell.setCellStyle(style10);
        cell.setCellValue(StringUtil.EMPTY);
      }

      var statList = statModel.getDetails();
      if (statList != null && !statList.isEmpty()) {
        for (var i = 1; i <= statList.size(); i++) {
          stat = statList.get(i - 1);
          index++;
          var row = sheet.createRow(ROW_INDEX_START + index);
          row.setHeightInPoints(24);
          var cell = row.createCell(0);
          cell.setCellStyle(style0);
          cell.setCellValue(i);
          cell = row.createCell(1);
          cell.setCellStyle(style1);
          cell.setCellValue(stat.getUserName());
          cell = row.createCell(2);
          cell.setCellStyle(style2);
          cell.setCellValue(stat.getVariety());
          cell = row.createCell(3);
          cell.setCellStyle(style3);
          cell.setCellValue(stat.getVarietyCode());
          cell = row.createCell(4);
          cell.setCellStyle(style4);
          cell.setCellValue(stat.getGeneration() == null ? StringUtil.EMPTY : stat.getGeneration().toString());
          cell = row.createCell(5);
          cell.setCellStyle(style5);
          cell.setCellValue(stat.getBizType() == null ? StringUtil.EMPTY : dictionaryCache.getName("workload_tx_biz_type", stat.getBizType()));
          cell = row.createCell(6);
          cell.setCellStyle(style6);
          cell.setCellValue(stat.getDescription() == null ? StringUtil.EMPTY : stat.getDescription());
          cell = row.createCell(7);
          cell.setCellStyle(style7);
          cell.setCellValue(stat.getBottles());
          cell = row.createCell(8);
          cell.setCellStyle(style8);
          cell.setCellValue(stat.getPlants());
          cell = row.createCell(9);
          cell.setCellStyle(style9);
          cell.setCellValue(stat.getOperateTime() == null || stat.getOperateTime() < 1L ? StringUtil.EMPTY : Rq.formatISOYMDHMS(stat.getOperateTime()));
          cell = row.createCell(10);
          cell.setCellStyle(style10);
          cell.setCellValue(stat.getOperatorName() == null ? StringUtil.EMPTY : stat.getOperatorName());
        }
      }

      stat = statModel.getEndData();
      if (stat != null) {
        index++;
        var row = sheet.createRow(ROW_INDEX_START + index);
        row.setHeightInPoints(24);
        var cell = row.createCell(0);
        cell.setCellStyle(style0);
        cell.setCellValue("期末余额");
        cell = row.createCell(1);
        cell.setCellStyle(style1);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(2);
        cell.setCellStyle(style2);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(3);
        cell.setCellStyle(style3);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(4);
        cell.setCellStyle(style4);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(5);
        cell.setCellStyle(style5);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(6);
        cell.setCellStyle(style6);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(7);
        cell.setCellStyle(style7);
        cell.setCellValue(stat.getBottles());
        cell = row.createCell(8);
        cell.setCellStyle(style8);
        cell.setCellValue(stat.getPlants());
        cell = row.createCell(9);
        cell.setCellStyle(style9);
        cell.setCellValue(StringUtil.EMPTY);
        cell = row.createCell(10);
        cell.setCellStyle(style10);
        cell.setCellValue(StringUtil.EMPTY);
      }

      sheet.setAutoFilter(new CellRangeAddress(ROW_INDEX_START - 1, ROW_INDEX_START + index, 0, 10));

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
