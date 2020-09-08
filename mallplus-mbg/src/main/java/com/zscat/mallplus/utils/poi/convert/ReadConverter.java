package com.zscat.mallplus.utils.poi.convert;


import com.zscat.mallplus.utils.poi.exception.PoiPlusReadConverterException;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ReadConverter {
    /**
     * 将value转换成指定的值, 读取时映射到实体中
     *
     * @param value
     * @return 转换后的值
     * @throws PoiPlusReadConverterException
     */
    Object convert(Object value) throws PoiPlusReadConverterException;
}