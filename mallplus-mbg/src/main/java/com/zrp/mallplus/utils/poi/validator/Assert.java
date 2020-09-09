package com.zrp.mallplus.utils.poi.validator;


import com.zrp.mallplus.utils.poi.exception.PoiPlusException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 数据校验
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/2 09:21
 */
public interface Assert {

    /**
     * 字符串不允许为空
     *
     * @param str     字符串
     * @param message 提示信息
     */
    static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new PoiPlusException(message);
        }
    }

    /**
     * 对象不允许为Null
     *
     * @param object  对象
     * @param message 提示信息
     */
    static void isNull(Object object, String message) {
        if (object == null) {
            throw new PoiPlusException(message);
        }
    }

    /**
     * 集合不允许为空对象
     *
     * @param collection 集合
     * @param message    提示信息
     */
    static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new PoiPlusException(message);
        }
    }

    /**
     * 集合不允许为空对象
     *
     * @param map 集合
     * @param message    提示信息
     */
    static void notEmpty(Map map, String message) {
        if (map == null || map.isEmpty()) {
            throw new PoiPlusException(message);
        }
    }
}
