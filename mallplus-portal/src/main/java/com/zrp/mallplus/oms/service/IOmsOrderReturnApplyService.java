package com.zrp.mallplus.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.oms.entity.OmsOrderReturnApply;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface IOmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {

    OmsOrderReturnApply findReturnApply(Long productId,String username,Long orderId);

}
