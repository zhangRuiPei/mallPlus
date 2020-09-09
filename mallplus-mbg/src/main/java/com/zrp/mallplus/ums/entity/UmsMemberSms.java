package com.zrp.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by EDZ on 2020/8/11.
 */
@TableName("ums_member_sms")
@Data
public class UmsMemberSms extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("phone")
    private String phone;

    @TableField("sign_name")
    private String signName;

    @TableField("template_code")
    private String templateCode;
    /**
     * 参数
     */
    @TableField("params")
    private String params;
    /**
     * 阿里云返回的
     */
    @TableField("bizId")
    private String bizId;
    /**
     * 阿里云返回的code
     */
    @TableField("code")
    private String code;
    /**
     * 阿里云返回的
     */
    @TableField("message")
    private String message;

    @TableField("create_time")
    private Date createTime;
    /**
     * 所属店铺
     */
    @TableField("store_id")
    private Integer storeId;

}
