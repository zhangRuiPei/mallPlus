package com.zscat.mallplus.util;

import com.fasterxml.jackson.databind.module.SimpleModule;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public class MyModule extends SimpleModule {
    @Override
    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(Object.class, PropertyFilterMixIn.class);
    }
}
