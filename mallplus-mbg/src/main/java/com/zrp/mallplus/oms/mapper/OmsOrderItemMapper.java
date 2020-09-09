package com.zrp.mallplus.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.oms.entity.OmsOrderItem;

import java.util.List;

/**
 * <p>
 * 订单中所包含的商品 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface OmsOrderItemMapper extends BaseMapper<OmsOrderItem> {

    List<OmsOrderItem> findListByorderSn(String orderSn);

}
