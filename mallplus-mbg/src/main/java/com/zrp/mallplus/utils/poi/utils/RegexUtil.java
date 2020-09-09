package com.zrp.mallplus.utils.poi.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * 正则表达式帮助类
 *
 * @author yangdaxin
 * @version 创建时间 2019/1/23 9:25
 */
public class RegexUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexUtil.class);

    private RegexUtil() {
        // Do Nothing
    }

    private static final LoadingCache<String, Pattern> REGEX_PATTERN_LOADING_CACHE =
            CacheBuilder.newBuilder()
                    .maximumSize(5)
                    .build(new CacheLoader<String, Pattern>() {

                        @Override
                        public Pattern load(String pattern) {
                            return Pattern.compile(pattern);
                        }
                    });

    public static Boolean isMatches(String pattern, Object value) {
        try {
            String valueString = (String) value;
            return REGEX_PATTERN_LOADING_CACHE.get(pattern).matcher(valueString).matches();
        } catch (ExecutionException e) {
            LOGGER.error("正则表达式验证异常.");
        }
        return false;
    }
}
