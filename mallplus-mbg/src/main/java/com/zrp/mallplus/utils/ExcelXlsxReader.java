package com.zrp.mallplus.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zrp.mallplus.utils.poi.config.Options;
import com.zrp.mallplus.utils.poi.constants.ExcelCellType;
import com.zrp.mallplus.utils.poi.constants.ExcelConstants;
import com.zrp.mallplus.utils.poi.constants.MessageConstants;
import com.zrp.mallplus.utils.poi.convert.ReadConverter;
import com.zrp.mallplus.utils.poi.exception.PoiPlusEncounterNoNeedXmlException;
import com.zrp.mallplus.utils.poi.exception.PoiPlusException;
import com.zrp.mallplus.utils.poi.exception.PoiPlusReadConverterException;
import com.zrp.mallplus.utils.poi.handler.ExcelReadHandler;
import com.zrp.mallplus.utils.poi.pojo.ExcelErrorField;
import com.zrp.mallplus.utils.poi.pojo.ExcelMapping;
import com.zrp.mallplus.utils.poi.pojo.ExcelProperty;
import com.zrp.mallplus.utils.poi.utils.*;
import com.zrp.mallplus.utils.poi.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yangdaxin
 * @version 创建时间 2019/1/23 16:32
 */
public class ExcelXlsxReader extends DefaultHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelXlsxReader.class);

    private Integer currentSheetIndex = -1;
    private Integer currentRowIndex = 0;
    private Integer currentCellIndex = 0;
    private ExcelCellType nextCellType = ExcelCellType.STRING;
    private String currentCellRef;
    private String previousCellRef;
    private String maxCellRef;
    private SharedStringsTable sharedStringsTable;
    private String previousCellValue;
    private StylesTable stylesTable;
    private Boolean nextIsString = false;

    private ExcelMapping excelMapping;
    private ExcelReadHandler excelReadHandler;
    private Class<? extends Object> entityClass;
    private List<Object> excelRowObjectData = Lists.newArrayList();
    private Integer beginReadRowIndex = ExcelConstants.XLSX_DEFAULT_BEGIN_READ_ROW_INDEX;
    private Object emptyCellValue = ExcelConstants.XLSX_DEFAULT_EMPTY_CELL_VALUE;

    private static final String CHECK_MAP_KEY_OF_VALUE = "CELL_VALUE";
    private static final String CHECK_MAP_KEY_OF_ERROR = "CELL_ERROR";

    public ExcelXlsxReader(Class<? extends Object> entityClass, ExcelMapping excelMapping, ExcelReadHandler excelReadHandler) {
        this(entityClass, excelMapping, null, excelReadHandler);
    }

    public ExcelXlsxReader(Class<? extends Object> entityClass, ExcelMapping excelMapping, Integer beginReadRowIndex, ExcelReadHandler excelReadHandler) {
        this.entityClass = entityClass;
        this.excelMapping = excelMapping;
        if (null != beginReadRowIndex) {
            this.beginReadRowIndex = beginReadRowIndex;
        }
        this.excelReadHandler = excelReadHandler;
    }

    public void process(InputStream in) {
        try {
            processAll(OPCPackage.open(in));
        } catch (Exception e) {
            throw new PoiPlusException(MessageConstants.ONLY_SUPPORTED_XLSX, e);
        }
    }

    private void processAll(OPCPackage pkg) throws IOException, OpenXML4JException, SAXException {
        XSSFReader xssfReader = new XSSFReader(pkg);
        stylesTable = xssfReader.getStylesTable();
        SharedStringsTable sst = xssfReader.getSharedStringsTable();
        XMLReader parser = this.fetchSheetParser(sst);
        Iterator<InputStream> sheets = xssfReader.getSheetsData();
        while (sheets.hasNext()) {
            currentRowIndex = 0;
            currentSheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
        pkg.close();
    }

    public void process(String fileName, int sheetIndex) {
        try {
            processBySheet(sheetIndex, OPCPackage.open(fileName));
        } catch (Exception e) {
            throw new PoiPlusException(MessageConstants.ONLY_SUPPORTED_XLSX, e);
        }
    }

    public void process(InputStream in, int sheetIndex) {
        try {
            processBySheet(sheetIndex, OPCPackage.open(in));
        } catch (Exception e) {
            throw new PoiPlusException(MessageConstants.ONLY_SUPPORTED_XLSX, e);
        }
    }

    private void processBySheet(int sheetIndex, OPCPackage pkg) throws IOException, OpenXML4JException, SAXException {
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet = r.getSheet(ExcelConstants.SAX_RID_PREFIX + (sheetIndex + 1));
        currentSheetIndex++;
        InputSource sheetSource = new InputSource(sheet);
        try {
            parser.parse(sheetSource);
        } catch (PoiPlusEncounterNoNeedXmlException e) {
            sheet = r.getSheet(ExcelConstants.SAX_RID_PREFIX + (sheetIndex + 3));
            sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
        }
        sheet.close();
        pkg.close();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) {
        if (ExcelConstants.SAX_SST_ELEMENT.equals(name) || ExcelConstants.SAX_STYLESHEET_ELEMENT.equals(name)) {
            throw new PoiPlusEncounterNoNeedXmlException("error in file structure if xlsx.");
        }
        // c => 单元格
        if (ExcelConstants.SAX_C_ELEMENT.equals(name)) {
            String ref = attributes.getValue(ExcelConstants.SAX_R_ATTR);
            // 前一个单元格的位置
            previousCellRef = null == previousCellRef ? ref : currentCellRef;
            // 当前单元格的位置
            currentCellRef = ref;
            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue(ExcelConstants.SAX_T_ELEMENT);
            String cellStyleStr = attributes.getValue(ExcelConstants.SAX_S_ATTR_VALUE);
            nextIsString = (null != cellType && cellType.equals(ExcelConstants.SAX_S_ATTR_VALUE));
            // 设定单元格类型
            if (null != cellType) {
                this.setNextCellType(cellType, cellStyleStr);
            }
        }
        previousCellValue = "";
    }


    @Override
    public void endElement(String uri, String localName, String name) {
        // Process the last contents as required.
        // Do now, as characters() may be called more than once
        if (nextIsString) {
            int index = Integer.parseInt(previousCellValue);
            previousCellValue = new XSSFRichTextString(sharedStringsTable.getEntryAt(index)).toString();
            nextIsString = false;
        }

        // 处理单元格数据
        if (ExcelConstants.SAX_C_ELEMENT.equals(name)) {
            String value = this.getCellValue(previousCellValue.trim());

            // 空值补齐(中)
            if (!currentCellRef.equals(previousCellRef)) {
                for (int i = 0; i < PoiUtils.countNullCell(currentCellRef, previousCellRef); i++) {
                    excelRowObjectData.add(currentCellIndex, emptyCellValue);
                    currentCellIndex++;
                }
            }
            excelRowObjectData.add(currentCellIndex, value);
            currentCellIndex++;
        }
        // 如果标签名称为 row ，这说明已到行尾，通知回调处理当前行的数据
        else if (ExcelConstants.SAX_ROW_ELEMENT.equals(name)) {
            if (currentRowIndex == 0) {
                maxCellRef = currentCellRef;
            }
            // 空值补齐(后)
            if (null != maxCellRef) {
                for (int i = 0; i <= PoiUtils.countNullCell(maxCellRef, currentCellRef); i++) {
                    excelRowObjectData.add(currentCellIndex, emptyCellValue);
                    currentCellIndex++;
                }
            }
            try {
                this.performVerificationAndProcessFlowRow();
            } catch (Exception e) {
                LOGGER.error("Excel read error！");
            } finally {
                excelRowObjectData.clear();
                currentRowIndex++;
                currentCellIndex = 0;
                previousCellRef = null;
                currentCellRef = null;
            }
        }
    }

    @Override
    public void characters(char[] chars, int start, int length) {
        previousCellValue = previousCellValue.concat(new String(chars, start, length));
    }

    /**
     * 创建 XMLReader
     *
     * @param sst
     * @return
     * @throws SAXException
     */
    private XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader(ExcelConstants.SAX_PARSER_CLASS);
        this.sharedStringsTable = sst;
        parser.setContentHandler(this);
        return parser;
    }

    /**
     * 设定单元格类型
     *
     * @param cellType
     * @param cellStyleStr
     */
    private void setNextCellType(String cellType, String cellStyleStr) {
        nextCellType = ExcelCellType.STRING;

        switch (cellType) {
            case ExcelConstants.SAX_CELLTYPE_B:
                nextCellType = ExcelCellType.BOOL;
                break;
            case ExcelConstants.SAX_CELLTYPE_E:
                nextCellType = ExcelCellType.ERROR;
                break;
            case ExcelConstants.SAX_CELLTYPE_INLINESTR:
                nextCellType = ExcelCellType.INLINESTR;
                break;
            case ExcelConstants.SAX_CELLTYPE_STR:
                nextCellType = ExcelCellType.FORMULA;
                break;
            default:
                // Do Nothing
                break;
        }

        if (null != cellStyleStr) {
            int styleIndex = Integer.parseInt(cellStyleStr);
            XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
            String formatString = style.getDataFormatString();
            if (null == formatString) {
                nextCellType = ExcelCellType.NULL;
            }
        }
    }

    /**
     * 获取单元格值
     *
     * @param value
     * @return
     */
    private String getCellValue(String value) {
        String var1;
        switch (nextCellType) {
            case BOOL:
                var1 = value.charAt(0) == '0' ? "FALSE" : "TRUE";
                break;
            case ERROR:
                var1 = "\"ERROR:" + value + '"';
                break;
            case FORMULA:
                var1 = '"' + value + '"';
                break;
            case INLINESTR:
                var1 = new XSSFRichTextString(value).toString();
                break;
            case STRING:
                var1 = String.valueOf(value);
                break;
            default:
                var1 = value;
                break;
        }
        return var1;
    }

    private void performVerificationAndProcessFlowRow() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (currentRowIndex >= beginReadRowIndex) {
            List<ExcelProperty> propertyList = excelMapping.getPropertyList();
            Integer excelRowDataSize = excelRowObjectData.size();
            Integer excelMappingPropertySize = propertyList.size();
            // 空值补齐(前)
            for (int i = 0; i < excelMappingPropertySize - excelRowDataSize; i++) {
                excelRowObjectData.add(i, emptyCellValue);
            }

            if (!this.rowObjectDataIsAllEmptyCellValue()) {
                Object entity = entityClass.newInstance();
                List<ExcelErrorField> errorFields = Lists.newArrayList();
                for (int i = 0; i < propertyList.size(); i++) {
                    ExcelProperty property = propertyList.get(i);
                    Map<String, Object> checkAndConvertPropertyRetMap = checkAndConvertProperty(i, property, excelRowObjectData.get(i));
                    Object errorFieldObject = checkAndConvertPropertyRetMap.get(CHECK_MAP_KEY_OF_ERROR);
                    if (null != errorFieldObject) {
                        errorFields.add((ExcelErrorField) errorFieldObject);
                    }
                    if (errorFields.isEmpty()) {
                        Object propertyValue = checkAndConvertPropertyRetMap.get(CHECK_MAP_KEY_OF_VALUE);
                        ReflectionUtil.setProperty(entity, property.getName(), propertyValue);
                    }
                }
                if (errorFields.isEmpty()) {
                    excelReadHandler.onSuccess(currentSheetIndex, currentRowIndex, entity);
                    return;
                }
                excelReadHandler.onError(currentSheetIndex, currentRowIndex, errorFields);
            }
        }
    }

    /**
     * 判断一行中，所有单元格是否都为空
     *
     * @return
     */
    private boolean rowObjectDataIsAllEmptyCellValue() {
        int emptyObjectCount = 0;
        for (Object obj : excelRowObjectData) {
            if ((null == obj) || obj.equals(emptyCellValue) || StringUtils.isBlank((String) obj)) {
                emptyObjectCount++;
            }
        }
        return emptyObjectCount == excelRowObjectData.size();
    }

    /**
     * 检查并且转换属性值
     *
     * @param cellIndex     单元格号
     * @param property      excel注解属性
     * @param propertyValue excel注解属性值
     * @return
     */
    private Map<String, Object> checkAndConvertProperty(Integer cellIndex, ExcelProperty property, Object propertyValue) {
        // required
        Boolean required = property.getRequired();
        boolean var1 = null == propertyValue || StringUtils.isBlank((String) propertyValue) || ExcelConstants.XLSX_DEFAULT_EMPTY_CELL_VALUE.equals(propertyValue);
        if (null != required && required && var1) {
            return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, "单元格的值必须填写");
        }

        // maxLength
        int maxLength = property.getMaxLength();
        if (-1 != maxLength && null != propertyValue && !emptyCellValue.equals(propertyValue) && String.valueOf(propertyValue).length() > maxLength) {
            return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, "超过最大长度: " + maxLength);
        }

        // dateFormat
        String dateFormat = property.getDateFormat();
        if (StringUtils.isNotBlank(dateFormat)) {
            try {
                Date newPropertyValue = DateUtil.parse(dateFormat, propertyValue);
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, newPropertyValue, null);
            } catch (Exception e) {
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, "时间格式解析失败 [" + dateFormat + "]");
            }
        }

        // options
        Options options = property.getOptions();
        if (null != options) {
            Object[] values = options.get();
            if (null != values && values.length > 0) {
                boolean containInOptions = false;
                for (Object value : values) {
                    if (null != propertyValue && propertyValue.equals(value)) {
                        containInOptions = true;
                        break;
                    }
                }
                if (!containInOptions) {
                    return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, "[" + propertyValue + "]不是规定的下拉框的值");
                }
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, null);
            }
        }

        // regularExp
        String regularExp = property.getRegularExp();
        if (StringUtils.isNotBlank(regularExp)) {
            if (!RegexUtil.isMatches(regularExp, propertyValue)) {
                String regularExpMessage = property.getRegularExpMessage();
                String validErrorMessage = StringUtils.isNotBlank(regularExpMessage) ? regularExpMessage : "正则表达式校验失败 [" + regularExp + "]";
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, validErrorMessage);
            }
            return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, null);
        }

        // validator
        Validator validator = property.getValidator();
        if (null != validator) {
            String validErrorMessage = validator.valid(propertyValue);
            if (null != validErrorMessage) {
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, validErrorMessage);
            }
        }

        // readConverterExp && readConverter
        String readConverterExp = property.getReadConverterExp();
        ReadConverter readConverter = property.getReadConverter();
        if (StringUtils.isNotBlank(readConverterExp)) {
            try {
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, ExcelPropertyUtils.convertByExp(propertyValue, readConverterExp), null);
            } catch (Exception e) {
                String validErrorMessage = "由于readConverterExp表达式的值不规范导致转换失败";
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, validErrorMessage);
            }
        } else if (null != readConverter) {
            try {
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, readConverter.convert(propertyValue), null);
            } catch (PoiPlusReadConverterException e) {
                return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, e.getMessage());
            }
        }
        return buildCheckAndConvertPropertyRetMap(cellIndex, property, propertyValue, null);
    }

    /**
     * 构建转换结果
     *
     * @param cellIndex         单元格号
     * @param property          excel注解属性
     * @param propertyValue     excel注解属性值
     * @param validErrorMessage 提示信息
     * @return
     */
    private Map<String, Object> buildCheckAndConvertPropertyRetMap(Integer cellIndex, ExcelProperty property, Object propertyValue, String validErrorMessage) {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put(CHECK_MAP_KEY_OF_VALUE, propertyValue);
        if (null != validErrorMessage) {
            resultMap.put(CHECK_MAP_KEY_OF_ERROR, ExcelErrorField.builder()
                    .cellIndex(cellIndex)
                    .title(property.getTitle())
                    .name(property.getName())
                    .errorMessage(validErrorMessage)
                    .build());
        }
        return resultMap;
    }
}
