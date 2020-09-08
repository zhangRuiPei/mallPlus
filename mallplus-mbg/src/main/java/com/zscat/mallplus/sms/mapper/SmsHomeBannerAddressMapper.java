package com.zscat.mallplus.sms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sms.entity.SmsHomeBannerAddress;

public interface SmsHomeBannerAddressMapper extends BaseMapper<SmsHomeBannerAddress> {

    Long insertSmsHomeBanner(SmsHomeBannerAddress smsHomeBannerAddress);
}
