package com.zrp.mallplus.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String MODULE() default "操作模块";

    String REMARK() default "操作日志";
}
