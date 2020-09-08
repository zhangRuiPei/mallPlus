package com.zscat.mallplus.utils.poi.utils;

import java.util.Collection;
import java.util.UUID;

/**
 * 字符串帮助类
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/21 16:21
 */
public class StringUtil {
    private StringUtil() {
        // Do Nothing
    }

    public static String[] toStringArray(Collection<String> collection) {
        return collection.toArray(new String[0]);
    }

    /**
     * 获取uuid
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
