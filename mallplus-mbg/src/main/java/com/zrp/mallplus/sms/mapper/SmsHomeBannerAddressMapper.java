package com.zrp.mallplus.sms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sms.entity.SmsHomeBannerAddress;

public interface SmsHomeBannerAddressMapper extends BaseMapper<SmsHomeBannerAddress> {

    Long insertSmsHomeBanner(SmsHomeBannerAddress smsHomeBannerAddress);
}
