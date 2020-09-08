package com.zscat.mallplus.utils.poi.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 类反射工具类
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/21 11:45
 */
public class ReflectionUtil {
    private ReflectionUtil() {
        // Do Nothing
    }

    public static void setProperty(final Object bean, final String name, final Object value)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        if (!name.contains(".")) {
            BeanUtils.setProperty(bean, name, value);
            return;
        }

        PropertyUtilsBean beanUtil = new PropertyUtilsBean();
        String[] propertyLevels = name.split("\\.");
        String propertyNameWithParent = "";
        for (int i = 0; i < propertyLevels.length; i++) {
            String p = propertyLevels[i];
            propertyNameWithParent = (propertyNameWithParent.equals("") ? p : propertyNameWithParent + "." + p);
            if (i < (propertyLevels.length - 1) &&
                    beanUtil.getProperty(bean, propertyNameWithParent) != null) {
                continue;
            }
            Class pType = beanUtil.getPropertyType(bean, propertyNameWithParent);
            if (i < (propertyLevels.length - 1)) {
                BeanUtils.setProperty(bean, propertyNameWithParent, pType.getConstructor().newInstance());
            } else {
                Constructor<String> constructor = pType.getConstructor(String.class);
                BeanUtils.setProperty(bean, propertyNameWithParent, constructor.newInstance(value));
            }
        }
    }
}
