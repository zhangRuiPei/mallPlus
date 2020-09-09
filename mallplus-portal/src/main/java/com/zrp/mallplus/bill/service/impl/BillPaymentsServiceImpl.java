package com.zrp.mallplus.bill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.bill.entity.BillPayments;
import com.zrp.mallplus.bill.mapper.*;
import com.zrp.mallplus.bill.service.IBillPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付单表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-09-16
 */
@Service
public class BillPaymentsServiceImpl extends ServiceImpl<BillPaymentsMapper, BillPayments> implements IBillPaymentsService {

    @Autowired
    private BillPaymentsMapper billPaymentsMapper;

    @Override
    public BillPayments findBillPaymentsBy(String paymentId) {
        return billPaymentsMapper.findBillPaymentsBy(paymentId);
    }

    @Override
    public BillPayments findBillTradeNo(String tradeNo) {
        BillPayments billTradeNo = billPaymentsMapper.findBillTradeNo(tradeNo);
        return billTradeNo;
    }
}
