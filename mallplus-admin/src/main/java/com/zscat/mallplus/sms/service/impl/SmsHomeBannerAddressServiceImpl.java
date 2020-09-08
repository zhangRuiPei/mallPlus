package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsHomeBannerAddress;
import com.zscat.mallplus.sms.mapper.SmsHomeBannerAddressMapper;
import com.zscat.mallplus.sms.service.ISmsHomeBannerAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsHomeBannerAddressServiceImpl extends ServiceImpl<SmsHomeBannerAddressMapper, SmsHomeBannerAddress> implements ISmsHomeBannerAddressService {


    @Resource
    private SmsHomeBannerAddressMapper smsHomeBannerAddressMapper;


    @Override
    public Long addSmsHome(SmsHomeBannerAddress smsHomeBannerAddress   ) {
        return smsHomeBannerAddressMapper.insertSmsHomeBanner(smsHomeBannerAddress);
    }
}
