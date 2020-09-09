package com.zrp.mallplus.utils.poi.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * 日期帮助类
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/21 14:49
 */
public class DateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private DateUtil() {
        // Do Nothing
    }

    private static final LoadingCache<String, SimpleDateFormat> DATE_FORMAT_LOADING_CACHE =
            CacheBuilder.newBuilder()
                    .maximumSize(5)
                    .build(new CacheLoader<String, SimpleDateFormat>() {

                        @Override
                        public SimpleDateFormat load(String pattern) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                            dateFormat.setLenient(true);
                            return dateFormat;
                        }
                    });

    public static Date parse(String pattern, Object value) throws ExecutionException, ParseException {
        String valueString = (String) value;
        return DATE_FORMAT_LOADING_CACHE.get(pattern).parse(valueString);
    }

    public static String format(String pattern, Date value) {
        try {
            return DATE_FORMAT_LOADING_CACHE.get(pattern).format(value);
        } catch (ExecutionException e) {
            LOGGER.error("data format error", e);
        }
        return value.toString();
    }

    public static SimpleDateFormat getEnglishLocalDf() {
        return new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    }
}
