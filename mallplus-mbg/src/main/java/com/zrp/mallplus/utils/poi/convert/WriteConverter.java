package com.zrp.mallplus.utils.poi.convert;


import com.zrp.mallplus.utils.poi.exception.PoiPlusWriteConverterException;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface WriteConverter {
    /**
     * 将value转换成指定的值, 用于写入excel表格中
     *
     * @param value
     * @return 转换后的值
     * @throws PoiPlusWriteConverterException
     */
    String convert(Object value) throws PoiPlusWriteConverterException;
}
