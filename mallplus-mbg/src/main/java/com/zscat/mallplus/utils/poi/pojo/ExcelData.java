package com.zscat.mallplus.utils.poi.pojo;


import com.zscat.mallplus.utils.poi.constants.MessageConstants;
import com.zscat.mallplus.utils.poi.validator.Assert;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Getter
@ToString
public class ExcelData<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自定义字段
     */
    private Map<String, String> customFieldMap;

    /**
     * 数据源
     */
    private transient List<E> dataList;

    /**
     * 数据源类
     */
    private Class<E> entityClass;

    public ExcelData(Class<E> entityClass, Map<String, String> fieldMap, List<E> dataList) {
        Assert.notEmpty(fieldMap, MessageConstants.MSG_CUSTOM_DATA_NOT_EMPTY);
        Assert.notEmpty(dataList, MessageConstants.MSG_DATA_NOT_EMPTY);

        this.entityClass = entityClass;
        this.customFieldMap = fieldMap;
        this.dataList = dataList;
    }

    public ExcelData(Class<E> entityClass, List<E> dataList) {
        Assert.notEmpty(dataList, MessageConstants.MSG_DATA_NOT_EMPTY);

        this.entityClass = entityClass;
        this.dataList = dataList;
    }

    public ExcelData(Class<E> entityClass) {
        this.entityClass = entityClass;
    }
}
