package com.zscat.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by ZRF on 2020/8/13.
 */
@TableName("ums_retail_ratio")
@Data
public class UmsRetailRatio extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    @TableField("product_id")
    private Long productId;

    /**
     *是否分销
     */
    @TableField("partake_retail")
    private Integer partakeRetail;

    /**
     *一级分销
     */
    @TableField("one_level")
    private Integer oneLevel;

    /**
     *二级分销
     */
    @TableField("two_level")
    private Integer twoLevel;

    /**
     *三级分销
     */
    @TableField("three_level")
    private Integer threeLevel;

}
