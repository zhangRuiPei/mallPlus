package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsContentMsg;
import com.zscat.mallplus.sms.mapper.SmsContentMsgMapper;
import com.zscat.mallplus.sms.service.ISmsContentMsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsContentMsgServiceImpl extends ServiceImpl
        <SmsContentMsgMapper, SmsContentMsg> implements ISmsContentMsgService {

    @Resource
    private SmsContentMsgMapper smsContentMsgMapper;


}
