package com.zrp.mallplus.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.oms.entity.OmsOrderItem;

import java.util.List;

/**
 * <p>
 * 订单中所包含的商品 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface IOmsOrderItemService extends IService<OmsOrderItem> {

    List<OmsOrderItem> findListByorderSn(String orderSn);

}
