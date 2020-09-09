package com.zrp.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sms.entity.SmsHomeBannerAddress;
import com.zrp.mallplus.sms.entity.SmsHomeBrand;

import java.util.List;

/**
 * <p>
 * 首页推荐品牌表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsHomeBrandService extends IService<SmsHomeBrand> {
    /**
     * 更新推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 修改品牌推荐排序
     */
    int updateSort(Long id, Integer sort);

    int create(List<SmsHomeBrand> homeBrandList);

    Long addHomeBannerAddress(SmsHomeBannerAddress smsHomeBannerAddress);

}
