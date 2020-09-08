package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sms.entity.SmsBasicGifts;
import com.zscat.mallplus.sms.entity.SmsHomeBannerAddress;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsHomeBannerAddressService  extends IService<SmsHomeBannerAddress> {

    Long addSmsHome(SmsHomeBannerAddress smsHomeBannerAddress );

}
