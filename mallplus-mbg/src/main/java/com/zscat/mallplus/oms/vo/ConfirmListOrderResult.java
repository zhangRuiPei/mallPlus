package com.zscat.mallplus.oms.vo;


import com.zscat.mallplus.ums.entity.UmsMemberReceiveAddress;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 确认单信息封装
 */
@Data
public class ConfirmListOrderResult {

    private UmsMemberReceiveAddress address;
    //包含优惠信息的购物车信息
    private List<ConfirmOrderResult> confirmOrderResultList;
    //用户收货地址列表
    private List<UmsMemberReceiveAddress> memberReceiveAddressList;

    private BigDecimal blance;

    private BigDecimal totalPayAmount;
}
