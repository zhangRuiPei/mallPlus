package com.zrp.mallplus.config;


import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.zrp.mallplus.ApiContext;
import com.zrp.mallplus.enums.ConstansValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
//Spring boot方式
@EnableTransactionManagement
@Configuration
@MapperScan("com.zscat.mallplus.*.mapper*")
public class MybatisPlusConfig {
    private static final List<String> IGNORE_TENANT_TABLES = ConstansValue.IGNORE_TENANT_TABLES;
    @Autowired
    private ApiContext apiContext;

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {

        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        paginationInterceptor.setDialectType("mysql");
        List<ISqlParser> sqlParsersList = new ArrayList<>();
        sqlParsersList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParsersList);
        return paginationInterceptor;
    }


    /**
     * 性能分析拦截器，不建议生产使用
     * 用来观察 SQL 执行情况及执行时长
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }
}
