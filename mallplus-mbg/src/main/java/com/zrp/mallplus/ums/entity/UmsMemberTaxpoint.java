package com.zrp.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by EDZ on 2020/8/12.
 */
@Data
@TableName("ums_member_taxpoint")
public class UmsMemberTaxpoint extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableField("id")
    private Integer id;
    /**
     *是否开启多商户（0不开启；1开启）
     */
    @TableField("open_busine")
    private Integer openBusine;

    /**
     *是否开启门店（0不开启；1开启）
     */
    @TableField("open_store")
    private Integer openStore;

    /**
     *提现是否扣除分销佣金（0不开启；1开启）
     */
    @TableField("is_deduct")
    private Integer isDeduct;

    /**
     *商户税率
     */
    @TableField("busine_rate")
    private Integer busineRate;

    /**
     *商户提现限制
     */
    @TableField("cash_out")
    private Integer cashOut;


}
