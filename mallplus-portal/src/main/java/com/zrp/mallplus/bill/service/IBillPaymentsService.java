package com.zrp.mallplus.bill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.bill.entity.BillPayments;

/**
 * <p>
 * 支付单表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
public interface IBillPaymentsService extends IService<BillPayments> {

     BillPayments findBillPaymentsBy(String paymentId);

     BillPayments findBillTradeNo(String tradeNo);

}
