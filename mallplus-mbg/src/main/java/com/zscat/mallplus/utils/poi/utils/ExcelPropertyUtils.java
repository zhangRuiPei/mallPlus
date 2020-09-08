package com.zscat.mallplus.utils.poi.utils;


import com.zscat.mallplus.utils.poi.convert.WriteConverter;
import com.zscat.mallplus.utils.poi.exception.PoiPlusException;
import com.zscat.mallplus.utils.poi.pojo.ExcelMapping;
import com.zscat.mallplus.utils.poi.pojo.ExcelProperty;
import com.zscat.mallplus.utils.poi.validator.Assert;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Excel实体属性帮助类
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/21 13:38
 */
public class ExcelPropertyUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelPropertyUtils.class);

    private ExcelPropertyUtils() {
        // Do Nothing
    }

    /**
     * 获取Excel实体属性
     *
     * @param excelMapping
     * @param key
     * @return
     */
    public static ExcelProperty getExcelProperty(ExcelMapping excelMapping, String key) {
        ExcelProperty excelProperty = null;
        List<ExcelProperty> propertyList = excelMapping.getPropertyList();
        for (ExcelProperty property : propertyList) {
            if (key.equals(property.getName())) {
                excelProperty = property;
                break;
            }
        }

        Assert.isNull(excelProperty, "key can't find of ExcelProperty.key=【" + key + "】.");

        return excelProperty;
    }

    /**
     * 处理日期格式数据
     *
     * @param cellValue  excel单元格数据
     * @param dateFormat 日期格式
     * @return
     */
    public static String buildCellValueDate(Object cellValue, String dateFormat) {
        String var1 = "";
        if (cellValue instanceof Date) {
            var1 = DateUtil.format(dateFormat, (Date) cellValue);
        } else if (cellValue instanceof String) {
            try {
                Date parse = DateUtil.getEnglishLocalDf().parse((String) cellValue);
                var1 = DateUtil.format(dateFormat, parse);
            } catch (ParseException e) {
                LOGGER.error("build cell Value by date error.", e);
            }
        }
        return var1;
    }

    /**
     * 解析写入内容转换表达式
     *
     * @param propertyValue 需要转换的值
     * @param converterExp  表达式
     * @return
     */
    public static Object convertByExp(Object propertyValue, String converterExp) {
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (itemArray[0].equals(propertyValue)) {
                return itemArray[1];
            }
        }
        return propertyValue;
    }

    /**
     * 获取对象字段值
     *
     * @param entity   对象
     * @param property 字段
     * @return
     */
    public static String getCellValueByExcelProperty(Object entity, ExcelProperty property) {
        Object cellValue;
        try {
            cellValue = BeanUtils.getProperty(entity, property.getName());
        } catch (Exception e) {
            throw new PoiPlusException(e);
        }

        if (null == cellValue) {
            return "";
        }

        Object var1 = cellValue;

        // 处理日期格式
        String dateFormat = property.getDateFormat();
        if (StringUtils.isNotBlank(dateFormat)) {
            var1 = ExcelPropertyUtils.buildCellValueDate(cellValue, dateFormat);
        }

        // writeConverterExp、writeConverter
        String writeConverterExp = property.getWriteConverterExp();
        WriteConverter writeConverter = property.getWriteConverter();
        if (StringUtils.isNotBlank(writeConverterExp)) {
            try {
                var1 = ExcelPropertyUtils.convertByExp(cellValue, writeConverterExp);
            } catch (Exception e) {
                throw new PoiPlusException(e);
            }
        } else if (null != writeConverter) {
            var1 = writeConverter.convert(cellValue);
        }

        return String.valueOf(var1);
    }

    /**
     * 检查输入值是否为Excel title
     *
     * @param value
     * @param excelMapping
     * @return
     */
    public static boolean checkExcelProperty(String value, ExcelMapping excelMapping) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        for (ExcelProperty property : excelMapping.getPropertyList()) {
            if (property.getTitle().contains(value) || value.contains(property.getTitle())) {
                return true;
            }
        }
        return false;
    }
}
