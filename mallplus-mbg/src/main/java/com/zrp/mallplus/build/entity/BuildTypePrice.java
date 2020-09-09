package com.zrp.mallplus.build.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Setter
@Getter
@TableName("build_type_price")
public class BuildTypePrice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    private String remark;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 社区编号
     */
    @TableField("community_id")
    private Long communityId;


}
