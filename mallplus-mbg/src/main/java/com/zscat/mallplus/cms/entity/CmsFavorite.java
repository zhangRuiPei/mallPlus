package com.zscat.mallplus.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-06-15
 */
@Data
@TableName("cms_favorite")
public class CmsFavorite extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("add_time")
    private Date addTime;

    /**
     * 1 商品 2 文章 3 店铺 4 及赠品
     */
    private Integer type;

    @TableField("obj_id")
    private Long objId;


    @TableField("member_id")
    private Long memberId;

    private String name;

    private String meno1;

    private String meno2;

    private String meno3;


}
