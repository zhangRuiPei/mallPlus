package com.zscat.mallplus.sys.util;

/**
 * 支付状态
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public enum AliPayStatusEnum {

    FINISHED("交易成功", "TRADE_FINISHED"),

    SUCCESS("支付成功", "TRADE_SUCCESS"),

    BUYER_PAY("交易创建", "WAIT_BUYER_PAY"),

    CLOSED("交易关闭", "TRADE_CLOSED");

    private String value;

    AliPayStatusEnum(String name, String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
