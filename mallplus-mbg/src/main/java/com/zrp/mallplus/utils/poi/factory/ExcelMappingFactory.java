package com.zrp.mallplus.utils.poi.factory;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import com.zrp.mallplus.utils.poi.annotation.Excel;
import com.zrp.mallplus.utils.poi.annotation.ExcelField;
import com.zrp.mallplus.utils.poi.config.Options;
import com.zrp.mallplus.utils.poi.convert.ReadConverter;
import com.zrp.mallplus.utils.poi.convert.WriteConverter;
import com.zrp.mallplus.utils.poi.exception.PoiPlusException;
import com.zrp.mallplus.utils.poi.pojo.ExcelMapping;
import com.zrp.mallplus.utils.poi.pojo.ExcelProperty;
import com.zrp.mallplus.utils.poi.utils.StringUtil;
import com.zrp.mallplus.utils.poi.validator.Validator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public class ExcelMappingFactory {

    private ExcelMappingFactory() {
    }

    private static final LoadingCache<Class<?>, ExcelMapping> MAPPING_LOADING_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .build(new CacheLoader<Class<?>, ExcelMapping>() {
                @Override
                public ExcelMapping load(Class<?> clazz) throws InstantiationException, IllegalAccessException {
                    return loadExcelMapping(clazz);
                }
            });

    /**
     * 后去Excel实体属性
     *
     * @param clazz
     * @return
     */
    public static ExcelMapping get(Class<?> clazz) {
        try {
            return MAPPING_LOADING_CACHE.get(clazz);
        } catch (Exception e) {
            throw new PoiPlusException(e);
        }
    }

    /**
     * 加载@Excel、@ExcelField实体属性
     *
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static ExcelMapping loadExcelMapping(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        ExcelMapping excelMapping = new ExcelMapping();
        Excel excel = clazz.getAnnotation(Excel.class);
        if (null == excel) {
            throw new PoiPlusException("[" + clazz.getName() + "] @Excel annotations not found.");
        }
        excelMapping.setName(StringUtils.isNotBlank(excel.value()) ? excel.value() : StringUtil.getUUID());
        ExcelProperty excelMappingProperty;
        Field[] fields = clazz.getDeclaredFields();
        List<ExcelProperty> propertyList = Lists.newArrayList();
        for (Field field : fields) {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (null != excelField) {
                WriteConverter writeConverter = excelField.writeConverter().equals(ExcelField.Void.class) ? null : excelField.writeConverter().newInstance();
                ReadConverter readConverter = excelField.readConverter().equals(ExcelField.Void.class) ? null : excelField.readConverter().newInstance();
                Validator validator = excelField.validator().equals(ExcelField.Void.class) ? null : excelField.validator().newInstance();
                Options options = excelField.options().equals(ExcelField.Void.class) ? null : excelField.options().newInstance();

                excelMappingProperty = ExcelProperty.builder()
                        .name(StringUtils.isBlank(excelField.name()) ? field.getName() : excelField.name())
                        .title(excelField.title())
                        .required(excelField.required())
                        .width(excelField.width())
                        .maxLength(excelField.maxLength())
                        .dateFormat(excelField.dateFormat())
                        .options(options)
                        .writeConverterExp(excelField.writeConverterExp())
                        .writeConverter(writeConverter)
                        .readConverterExp(excelField.readConverterExp())
                        .readConverter(readConverter)
                        .regularExp(excelField.regularExp())
                        .regularExpMessage(excelField.regularExpMessage())
                        .validator(validator)
                        .build();
                propertyList.add(excelMappingProperty);
            }
        }
        if (propertyList.isEmpty()) {
            throw new PoiPlusException("[" + clazz.getName() + "] @ExcelField annotations not found.");
        }
        excelMapping.setPropertyList(propertyList);
        return excelMapping;
    }
}
