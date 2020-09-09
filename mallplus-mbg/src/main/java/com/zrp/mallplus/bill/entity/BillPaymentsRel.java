package com.zrp.mallplus.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 支付单明细表
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@TableName("bill_payments_rel")
public class BillPaymentsRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付单编号
     */
    @TableField("payment_id")
    private String paymentId;

    /**
     * 资源编号
     */
    @TableField("source_id")
    private String sourceId;

    /**
     * 金额
     */
    private BigDecimal money;


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "BillPaymentsRel{" +
                ", paymentId=" + paymentId +
                ", sourceId=" + sourceId +
                ", money=" + money +
                "}";
    }
}
