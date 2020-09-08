package com.zscat.mallplus.jifen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.jifen.entity.JifenDonateRule;
import com.zscat.mallplus.jifen.mapper.JifenDonateRuleMapper;
import com.zscat.mallplus.jifen.service.IJifenDonateRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class JifenDonateRuleServiceImpl extends ServiceImpl
        <JifenDonateRuleMapper, JifenDonateRule> implements IJifenDonateRuleService {

    @Resource
    private JifenDonateRuleMapper jifenDonateRuleMapper;


}
