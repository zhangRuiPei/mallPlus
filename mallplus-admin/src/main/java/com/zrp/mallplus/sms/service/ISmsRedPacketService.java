package com.zrp.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sms.entity.SmsRedPacket;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsRedPacketService extends IService<SmsRedPacket> {
    int acceptRedPacket(Integer id);

    int createRedPacket(SmsRedPacket redPacket);
}
