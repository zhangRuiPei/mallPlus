package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsFlashPromotion;
import com.zscat.mallplus.sms.mapper.SmsFlashPromotionMapper;
import com.zscat.mallplus.sms.service.ISmsFlashPromotionService;
import org.springframework.stereotype.Service;

/**
 * 限时购表 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsFlashPromotionServiceImpl extends ServiceImpl<SmsFlashPromotionMapper, SmsFlashPromotion> implements ISmsFlashPromotionService {
    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotion flashPromotion = new SmsFlashPromotion();
        flashPromotion.setId(id);
        flashPromotion.setStatus(status);
        this.updateById(flashPromotion);
        return 1;
    }
}
