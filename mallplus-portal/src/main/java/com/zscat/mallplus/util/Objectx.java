package com.zscat.mallplus.util;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public class Objectx {
    /**
     * 对象保持值
     *
     * @param des
     * @param src
     */
    public static void saveValue(Object des, Object src) {
        if (isNull(src)) {
            return;
        }
        des = src;
    }

    /**
     * 各类对象是否为空
     *
     * @return
     */
    public static boolean isNull(Object obj) {
        if (obj instanceof String) {
            return null == obj || "".equals(obj);
        }
        return null == obj;
    }

    public static <T extends Collection> boolean isEmpty(T obj) {
        return isNull(obj) || obj.size() == 0;
    }

    public static <T extends Collection> boolean isNotEmpty(T obj) {
        return !isEmpty(obj);
    }

    /**
     * 对象不为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 清洗值
     *
     * @param obj
     * @return
     */
    public static <T, V extends T> T value(T obj, V v) {
        if (isNotNull(obj)) {
            return obj;
        }
        return v;
    }

    /**
     * 清洗值
     *
     * @param obj
     * @return
     */
    public static <T> T value(T obj, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        if (null != obj) {
            return obj;
        }
        return clazz.newInstance();
    }

    /**
     * 克隆对象
     *
     * @param srcObj
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T cloneObject(T srcObj) throws Exception {
        return (T) BeanUtils.cloneBean(srcObj);
    }

    /**
     * 从集合中获得一个
     *
     * @param t
     * @param <T>
     * @param <E>
     * @return
     */
    public static <T extends Collection, E> E getOne(T t) {
        if (isNull(t)) return null;
        Iterator<E> iterator = t.iterator();
        while (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * 找一个非空的值
     *
     * @param tt
     * @param <T>
     * @return
     */
    public static <T> T value(T... tt) {
        Assert.isTrue(isNotNull(tt));
        for (T t : tt) {
            if (isNotNull(t)) {
                return t;
            }
        }
        return tt[0];
    }

    /**
     * 判断对象是否不等
     *
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T> boolean isNotEquals(T t1, T t2) {
        return !isEquals(t1, t2);
    }

    /**
     * 判断对象是否相等
     *
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    public static <T> boolean isEquals(T t1, T t2) {
        return t1.equals(t2);
    }
}
