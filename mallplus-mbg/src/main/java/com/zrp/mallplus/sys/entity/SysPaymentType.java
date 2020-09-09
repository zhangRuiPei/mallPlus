package com.zrp.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by EDZ on 2020/8/4.
 */
@Data
@TableName("sys_payment_type")
public class SysPaymentType extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    /**
     * 支付名称
     */
    @TableField("pay_name")
    private String payName;

    /**
     *
     */
    @TableField("pay_type")
    private Integer payType;

    /**
     * 状态（0禁用；1启用）
     */
    @TableField("status")
    private Integer status;

    /**
     *
     */
    @TableField("create_time")
    private Date createTime;

    /**
     *
     */
    @TableField("create_by")
    private String createBy;


    /**
     *
     */
    @TableField("update_time")
    private Date  updateTime;

    /**
     *
     */
    @TableField("update_by")
    private String updateBy;

    @TableField("store_id")
    private Integer storeId;
}
