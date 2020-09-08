package com.zscat.mallplus.jifen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.jifen.entity.JifenSignRule;
import com.zscat.mallplus.jifen.mapper.JifenSignRuleMapper;
import com.zscat.mallplus.jifen.service.IJifenSignRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class JifenSignRuleServiceImpl extends ServiceImpl
        <JifenSignRuleMapper, JifenSignRule> implements IJifenSignRuleService {

    @Resource
    private JifenSignRuleMapper jifenSignRuleMapper;


}
