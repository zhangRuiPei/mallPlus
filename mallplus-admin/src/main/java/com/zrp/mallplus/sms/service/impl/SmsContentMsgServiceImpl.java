package com.zrp.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.sms.entity.SmsContentMsg;
import com.zrp.mallplus.sms.mapper.SmsContentMsgMapper;
import com.zrp.mallplus.sms.service.ISmsContentMsgService;
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
