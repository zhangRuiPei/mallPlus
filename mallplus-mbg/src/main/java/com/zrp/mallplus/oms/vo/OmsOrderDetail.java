package com.zrp.mallplus.oms.vo;


import com.zrp.mallplus.oms.entity.OmsOrder;
import com.zrp.mallplus.oms.entity.OmsOrderItem;
import com.zrp.mallplus.oms.entity.OmsOrderOperateHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 订单详情信息
 * https://github.com/shenzhuan/mallplus on 2018/10/11.
 */
public class OmsOrderDetail extends OmsOrder {
    @Getter
    @Setter
    private List<OmsOrderItem> orderItemList;
    @Getter
    @Setter
    private List<OmsOrderOperateHistory> historyList;
}
