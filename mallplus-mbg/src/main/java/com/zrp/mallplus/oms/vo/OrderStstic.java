package com.zrp.mallplus.oms.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:  订单DTO
 */
@Data
public class OrderStstic {
    //会员ID
    private Long memberId;
    //会员消费数
    private int totalCount;
    //会员消费总价
    private BigDecimal totalPayAmount;
    private int memberCount;
    private Long objId;
}
