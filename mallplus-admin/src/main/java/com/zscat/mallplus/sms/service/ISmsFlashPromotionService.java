package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sms.entity.SmsFlashPromotion;

/**
 * 限时购表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsFlashPromotionService extends IService<SmsFlashPromotion> {
    /**
     * 修改上下线状态
     */
    int updateStatus(Long id, Integer status);
}
