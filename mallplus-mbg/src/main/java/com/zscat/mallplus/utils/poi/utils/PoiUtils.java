package com.zscat.mallplus.utils.poi.utils;

import com.google.common.collect.Maps;

import com.zscat.mallplus.utils.poi.config.Options;
import com.zscat.mallplus.utils.poi.constants.ExcelConstants;
import com.zscat.mallplus.utils.poi.constants.MessageConstants;
import com.zscat.mallplus.utils.poi.exception.PoiPlusException;
import com.zscat.mallplus.utils.poi.pojo.ExcelMapping;
import com.zscat.mallplus.utils.poi.validator.Assert;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * POI 工具类
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/21 11:45
 */
public class PoiUtils {
    private PoiUtils() {
        // Do Nothing
    }

    /**
     * 打开excel模板
     *
     * @return
     * @throws IOException
     */
    public static SXSSFWorkbook newHSSFWorkbook() {
        return new SXSSFWorkbook();
    }

    /**
     * 打开excel模板
     *
     * @param inputStream 模板文件流
     * @return
     * @throws IOException
     */
    public static Workbook newWorkbook(InputStream inputStream) throws IOException, InvalidFormatException {
        // 读取excel模板
        return WorkbookFactory.create(inputStream);
    }

    /**
     * 新建一个工作簿
     *
     * @param workBook
     * @param sheetName
     * @return
     */
    public static Sheet newSheet(Workbook workBook, String sheetName) {
        return workBook.createSheet(sheetName);
    }

    /**
     * 获取工作簿数量
     *
     * @return
     */
    public static int getSheetCount(Workbook workbook) {
        Assert.isNull(workbook, MessageConstants.MSG_WORKBOOK_IS_NULL);
        return workbook.getNumberOfSheets();
    }

    /**
     * 获取工作簿
     *
     * @param index 工作簿索引
     * @return
     */
    public static Sheet getSheet(Workbook workbook, int index) {
        Assert.isNull(workbook, MessageConstants.MSG_WORKBOOK_IS_NULL);
        return workbook.getSheetAt(index);
    }

    /**
     * 计算列宽
     *
     * @param width 用户设置的列宽
     * @return
     */
    public static int getColumnWidth(int width) {
        return 256 * width + 184;
    }

    /**
     * 新建行
     *
     * @param sheet
     * @param index
     * @return
     */
    public static Row newRow(Sheet sheet, int index) {
        return sheet.createRow(index);
    }

    /**
     * 新建单元格
     *
     * @param row
     * @param index
     * @return
     */
    public static Cell newCell(Row row, int index) {
        return row.createCell(index);
    }

    /**
     * 设置列宽
     *
     * @param sheet
     * @param index
     * @param width
     * @param value
     */
    public static void setColumnWidth(Sheet sheet, int index, Short width, String value) {
        boolean widthNotHaveConfig = (null == width || width == -1);
        if (widthNotHaveConfig && StringUtils.isNotBlank(value)) {
            sheet.setColumnWidth(index, (short) (value.length() * 2048));
        } else {
            width = widthNotHaveConfig ? 200 : width;
            sheet.setColumnWidth(index, (short) PoiUtils.getColumnWidth(width));
        }
    }

    /**
     * 计算两个单元格之间的单元格数目(同一行)
     *
     * @return int
     */
    public static int countNullCell(String ref, String ref2) {
        // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String var2 = ref2.replaceAll("\\d+", "");

        xfd = fillChar(xfd, 3, '@', true);
        var2 = fillChar(var2, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] var3 = var2.toCharArray();
        int res = (letter[0] - var3[0]) * 26 * 26 + (letter[1] - var3[1]) * 26 + (letter[2] - var3[2]);
        return res - 1;
    }

    private static String fillChar(String str, int len, char let, boolean isPre) {
        int var1 = str.length();
        if (var1 < len) {
            if (isPre) {
                for (int i = 0; i < (len - var1); i++) {
                    str = StringUtils.join(let, str);
                }
            } else {
                for (int i = 0; i < (len - var1); i++) {
                    str = StringUtils.join(str, let);
                }
            }
        }
        return str;
    }

    /**
     * 读取导入文件 开始导入的起始行号
     *
     * @param in
     * @param excelMapping
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static int getBeginReadRowIndex(InputStream in, ExcelMapping excelMapping) throws IOException, InvalidFormatException {
        Workbook workbook = PoiUtils.newWorkbook(in);
        Sheet sheet = PoiUtils.getSheet(workbook, 0);

        Map<Integer, Integer> map = Maps.newConcurrentMap();

        // 获得总列数
        int columnNum = sheet.getRow(0).getPhysicalNumberOfCells();
        int maxRow = 10;
        for (int rowIdx = 0; rowIdx < maxRow; rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) {
                continue;
            }

            int c = 0;
            for (int colIdx = 0; colIdx < columnNum; colIdx++) {
                Cell cell = row.getCell(colIdx);
                if (null != cell && cell.getCellTypeEnum().equals(CellType.STRING)) {
                    String cellValue = cell.getStringCellValue().trim();
                    if (ExcelPropertyUtils.checkExcelProperty(cellValue, excelMapping)) {
                        c++;
                    }
                }
            }
            if (c > 0) {
                map.put(rowIdx, c);
            }
        }

        int excelPropertySize = excelMapping.getPropertyList().size();
        int result = -1;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == excelPropertySize) {
                result = entry.getKey();
                break;
            }
        }

        if (result == -1) {
            throw new PoiPlusException("xlsx 导入文件格式有误，请确定导入文件格式无误后，从新导入！");
        }

        return result + 1;
    }

    /**
     * 这是模板下拉选择
     *
     * @param sheet     工作簿
     * @param options   自定义下拉配置
     * @param firstRow  起始行
     * @param endRow    结束行
     * @param firstCell 起始列
     * @param endCell   结束列
     */
    public static void setColumnCellOptions(Sheet sheet, Options options, int firstRow, int endRow, int firstCell, int endCell) {
        if (null != options) {
            String[] datasource = options.get();
            if (null != datasource && datasource.length > 0) {
                if (datasource.length > ExcelConstants.OPTIONS_MAX_SIZE) {
                    throw new PoiPlusException("Options item too much.");
                }
                DataValidationHelper validationHelper = sheet.getDataValidationHelper();
                DataValidationConstraint explicitListConstraint = validationHelper.createExplicitListConstraint(datasource);
                CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCell, endCell);
                DataValidation validation = validationHelper.createValidation(explicitListConstraint, regions);
                validation.setSuppressDropDownArrow(true);
                validation.createErrorBox("提示", "请从下拉列表选取！");
                validation.setShowErrorBox(true);
                sheet.addValidationData(validation);
            }
        }
    }
}
