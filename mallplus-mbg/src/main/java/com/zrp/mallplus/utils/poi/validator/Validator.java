package com.zrp.mallplus.utils.poi.validator;

/**
 * @author yangdaxin
 * @version 创建时间 2019/1/2 09:21
 */
public interface Validator {

    /**
     * 验证单元格的值, 若验证失败, 请返回错误消息.
     *
     * @param value 单元格值
     * @return null or errorMessage
     */
    String valid(Object value);
}
