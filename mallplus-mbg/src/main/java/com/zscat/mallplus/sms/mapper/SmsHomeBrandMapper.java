package com.zscat.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sms.entity.SmsHomeBannerAddress;
import com.zscat.mallplus.sms.entity.SmsHomeBrand;

/**
 * <p>
 * 首页推荐品牌表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsHomeBrandMapper extends BaseMapper<SmsHomeBrand> {

    Long addAdress(SmsHomeBannerAddress entity);

}
