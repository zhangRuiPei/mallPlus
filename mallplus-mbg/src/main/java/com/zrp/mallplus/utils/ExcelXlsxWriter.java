package com.zrp.mallplus.utils;

import com.google.common.collect.Maps;
import com.zrp.mallplus.utils.poi.constants.ExcelConstants;
import com.zrp.mallplus.utils.poi.pojo.ExcelCell;
import com.zrp.mallplus.utils.poi.pojo.ExcelData;
import com.zrp.mallplus.utils.poi.pojo.ExcelMapping;
import com.zrp.mallplus.utils.poi.pojo.ExcelProperty;
import com.zrp.mallplus.utils.poi.utils.ExcelPropertyUtils;
import com.zrp.mallplus.utils.poi.utils.PoiUtils;
import com.zrp.mallplus.utils.poi.validator.Assert;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;


import java.util.List;
import java.util.Map;

/**
 * @author yangdaxin
 * @version 创建时间 2019/1/21 10:35
 */
public class ExcelXlsxWriter {
    private Workbook workBook;
    private final Sheet sheet;
    private final ExcelMapping excelMapping;
    private Map<String, ExcelCell> excelEntityFieldMap = Maps.newConcurrentMap();
    private Map<String, ExcelCell> excelCustomFieldMap = Maps.newConcurrentMap();
    private CellStyle headerCellStyle = null;

    /**
     * 是否有导出模板，默认有
     */
    private boolean hasTemp;

    /**
     * 是否是下载模板
     */
    private boolean isTemp;

    public ExcelXlsxWriter(Workbook workBook, Sheet sheet, ExcelMapping excelMapping, boolean hasTemp, boolean isTemp) {
        Assert.isNull(sheet, "Sheet is not null.");

        this.workBook = workBook;
        this.sheet = sheet;
        this.excelMapping = excelMapping;
        this.hasTemp = hasTemp;
        this.isTemp = isTemp;

        // 读取模板信息
        if (hasTemp) {
            this.readExcelFieldMap(sheet, this.excelCustomFieldMap, this.excelEntityFieldMap);
            Assert.notEmpty(this.excelEntityFieldMap, "Excel Template error.");
        }
    }

    /**
     * Excel sheet数据写入
     *
     * @param excelData 数据源
     */
    public void writeData(ExcelData excelData) {
        if (this.hasTemp) {
            // 有导出模板
            // 写自定义数据
            this.writeCustomData(excelData.getCustomFieldMap());

            // 写用户数据
            this.writeEntityDataByTemplate(excelData);
        } else {
            // 无导出模板

            // 写文件头
            this.writeHeaderData();

            if (!this.isTemp) {
                //写文件数据
                this.writeEntityData(excelData);
            } else {
                // 写表达式数据
                this.writeTemplateExpData();
            }
        }
    }

    /**
     * 写自定义数据
     *
     * @param map 自定义数据
     */
    private void writeCustomData(Map<String, String> map) {
        if (map == null || map.isEmpty() || this.excelCustomFieldMap.isEmpty()) {
            return;
        }

        // 提取模板中数据源相关字段位置信息，包括：行、列、单元格样式
        for (Map.Entry<String, ExcelCell> entry : this.excelCustomFieldMap.entrySet()) {
            String key = entry.getKey();
            ExcelCell poiCell = entry.getValue();
            if (map.containsKey(key)) {
                // 获取行
                Row row = sheet.getRow(poiCell.getRowIdx());
                // 获取列
                Cell cell = row.getCell(poiCell.getColIdx());
                // 填值
                cell.setCellValue(map.get(key));
            }
        }
    }

    /**
     * 写数据库字段数据
     *
     * @param excelData 数据源
     */
    private void writeEntityDataByTemplate(ExcelData excelData) {
        List<?> dataList = excelData.getDataList();

        if (dataList.isEmpty()) {
            return;
        }

        for (Map.Entry<String, ExcelCell> entry : this.excelEntityFieldMap.entrySet()) {
            String key = entry.getKey();
            ExcelCell poiCell = entry.getValue();

            for (int i = 0; i < dataList.size(); i++) {
                // 获取行
                Row row = this.sheet.getRow(poiCell.getRowIdx() + i);
                if (row == null) {
                    row = this.sheet.createRow(poiCell.getRowIdx() + i);
                }

                // 获取列
                Cell cell = row.getCell(poiCell.getColIdx());
                if (cell == null) {
                    cell = row.createCell(poiCell.getColIdx());
                }

                // 设置单元格样式
                cell.setCellStyle(poiCell.getCellStyle());

                ExcelProperty property = ExcelPropertyUtils.getExcelProperty(this.excelMapping, key);

                // 填充单元格数据
                String cellValue = ExcelPropertyUtils.getCellValueByExcelProperty(dataList.get(i), property);
                cell.setCellValue(cellValue);

                // 设置列宽
                PoiUtils.setColumnWidth(this.sheet, poiCell.getColIdx(), property.getWidth(), property.getTitle());
            }
        }
    }

    /**
     * 写文件头
     */
    private void writeHeaderData() {
        Row row = PoiUtils.newRow(this.sheet, 0);

        List<ExcelProperty> propertyList = this.excelMapping.getPropertyList();
        for (int colIdx = 0; colIdx < propertyList.size(); colIdx++) {
            ExcelProperty property = propertyList.get(colIdx);
            Cell cell = PoiUtils.newCell(row, colIdx);

            // 设置单元格样式
            cell.setCellStyle(this.getHeaderCellStyle(this.workBook));

            // 设置标题
            String headerColumnValue = property.getTitle();
            if (null != property.getRequired() && property.getRequired()) {
                headerColumnValue = (headerColumnValue + "[*]");
            }
            cell.setCellValue(headerColumnValue);

            // 设置列宽
            PoiUtils.setColumnWidth(this.sheet, colIdx, property.getWidth(), headerColumnValue);
        }
    }

    /**
     * 写文件数据
     *
     * @param excelData
     */
    private void writeEntityData(ExcelData excelData) {
        List<?> dataList = excelData.getDataList();

        if (dataList.isEmpty()) {
            return;
        }

        for (int rowIdx = 0; rowIdx < dataList.size(); rowIdx++) {
            Row row = PoiUtils.newRow(this.sheet, rowIdx + 1);
            List<ExcelProperty> propertyList = this.excelMapping.getPropertyList();
            for (int colIdx = 0; colIdx < propertyList.size(); colIdx++) {
                ExcelProperty property = propertyList.get(colIdx);
                Cell cell = PoiUtils.newCell(row, colIdx);
                // 填充单元格数据
                String cellValue = ExcelPropertyUtils.getCellValueByExcelProperty(dataList.get(rowIdx), property);
                cell.setCellValue(cellValue);

                // 设置自定义下拉值
                PoiUtils.setColumnCellOptions(this.sheet, property.getOptions(), rowIdx + 1, dataList.size() + 1, colIdx, colIdx + 1);
            }
        }
    }

    /**
     * 写模板表达式数据
     */
    private void writeTemplateExpData() {
        Row row = PoiUtils.newRow(this.sheet, 1);
        List<ExcelProperty> propertyList = this.excelMapping.getPropertyList();
        for (int colIdx = 0; colIdx < propertyList.size(); colIdx++) {
            ExcelProperty property = propertyList.get(colIdx);
            Cell cell = PoiUtils.newCell(row, colIdx);
            String exp = StringUtils.join(ExcelConstants.FIELD_PREFIX, property.getName());
            cell.setCellValue(exp);
        }
    }

    /**
     * 获取头部样式
     *
     * @param workBook
     * @return
     */
    private CellStyle getHeaderCellStyle(Workbook workBook) {
        if (null == headerCellStyle) {
            headerCellStyle = workBook.createCellStyle();
            Font font = workBook.createFont();
            headerCellStyle.setFillForegroundColor((short) 12);
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setBorderTop(BorderStyle.DOTTED);
            headerCellStyle.setBorderRight(BorderStyle.DOTTED);
            headerCellStyle.setBorderBottom(BorderStyle.DOTTED);
            headerCellStyle.setBorderLeft(BorderStyle.DOTTED);
            // 对齐
            headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
            headerCellStyle.setFillForegroundColor(IndexedColors.GREEN.index);
            headerCellStyle.setFillBackgroundColor(IndexedColors.GREEN.index);
            font.setColor(IndexedColors.WHITE.index);
            // 应用标题字体到标题样式
            headerCellStyle.setFont(font);
            //设置单元格文本形式
            DataFormat dataFormat = workBook.createDataFormat();
            headerCellStyle.setDataFormat(dataFormat.getFormat("@"));
        }
        return headerCellStyle;
    }

    /**
     * 提取模板中数据源相关字段位置信息，包括：行、列、单元格样式
     *
     * @param sheet 工作簿
     * @param map1  自定义字段集
     * @param map2  数据源字段集
     */
    private void readExcelFieldMap(Sheet sheet, Map<String, ExcelCell> map1, Map<String, ExcelCell> map2) {
        // 获得总列数
        int columnNum = sheet.getRow(0).getPhysicalNumberOfCells();
        // 获得总行数
        int rowNum = sheet.getLastRowNum();

        // 提取模板中数据源相关字段位置信息，包括：行、列、单元格样式
        for (int rowIdx = 0; rowIdx <= rowNum; rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            for (int colIdx = 0; colIdx <= columnNum; colIdx++) {
                // 获取单元格
                Cell cell = row.getCell(colIdx);
                if (cell != null) {
                    // 单元格值
                    String cellValue = cell.getStringCellValue().trim();
                    // 单元格内容不满足模板要求
                    if (StringUtils.isNotBlank(cellValue)) {
                        ExcelCell poiCell = new ExcelCell(rowIdx, colIdx, cell.getCellStyle());
                        if (cellValue.indexOf(ExcelConstants.CUSTOM_FIELD_PREFIX) == 0) {
                            // 自定义数据源字段
                            String field = cellValue.replace(ExcelConstants.CUSTOM_FIELD_PREFIX, "");
                            map1.put(field, poiCell);
                        } else if (cellValue.indexOf(ExcelConstants.FIELD_PREFIX) == 0) {
                            // 数据源字段
                            String field = cellValue.replace(ExcelConstants.FIELD_PREFIX, "");
                            map2.put(field, poiCell);
                        }
                    }
                }
            }
        }
    }
}
