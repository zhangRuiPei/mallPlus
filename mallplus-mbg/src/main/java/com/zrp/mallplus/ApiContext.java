package com.zrp.mallplus;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;


@Component
public class ApiContext {

    private static final String KEY_CURRENT_PROVIDER_ID = "KEY_CURRENT_PROVIDER_ID";

    private static final Map<String, Object> mContext = Maps.newConcurrentMap();
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    private static ThreadLocal<ConcurrentMap> contextHolder = new ThreadLocal<>();

    public Long getCurrentProviderId() {
        return (Long) mContext.get(KEY_CURRENT_PROVIDER_ID);
    }

    public void setCurrentProviderId(Long providerId) {
        mContext.put(KEY_CURRENT_PROVIDER_ID, providerId);
    }
}
