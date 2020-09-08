package com.zscat.mallplus.bill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.bill.entity.BillPayments;

/**
 * <p>
 * 支付单表 Mapper 接口
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface BillPaymentsMapper extends BaseMapper<BillPayments> {

    BillPayments findBillPaymentsBy(String paymentId);

    BillPayments findBillTradeNo(String tradeNo);

}
