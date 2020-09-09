package com.zrp.mallplus.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.oms.entity.OmsCartItem;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface OmsCartItemMapper extends BaseMapper<OmsCartItem> {

    Integer countCart(Long id);
}
