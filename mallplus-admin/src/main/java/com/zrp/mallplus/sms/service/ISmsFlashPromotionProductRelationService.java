package com.zrp.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sms.entity.SmsFlashPromotionProductRelation;

/**
 * 商品限时购与商品关系表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsFlashPromotionProductRelationService extends IService<SmsFlashPromotionProductRelation> {


    int getCount(Long flashPromotionId, Long id);
}
