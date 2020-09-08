package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZRF on 2020/8/13.
 */
@Data
@TableName("ums_member_goods_log")
public class UmsMemberGoodsLog extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员或商品id
     */
    @TableField("share_id")
    private Long shareId;

    /**
     * 会员或商品name
     */
    @TableField("name")
    private String name;

    /**
     *操作者
     */
    @TableField("operate_man")
    private String operateMan;

    /**
     *操作前
     */
    @TableField("operate_before")
    private String operateBefore;

    /**
     *操作后
     */
    @TableField("operate_after")
    private String operateAfter;

    /**
     *操作时间
     */
    @TableField("operate_time")
    private Date operateTime;

    /**
     *类型（0会员；1商品）
     */
    @TableField("type")
    private Integer type;

    /**
     *备注
     */
    @TableField("note")
    private String note;


}
