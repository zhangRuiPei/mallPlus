package com.zrp.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sms.entity.SmsGroup;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsGroupMapper extends BaseMapper<SmsGroup> {
    SmsGroup getByGoodsId(Long goodsId);
}
