package com.zrp.mallplus.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 商品审核记录
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("pms_product_vertify_record")
public class PmsProductVertifyRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("product_id")
    private Long productId;

    @TableField("create_time")
    private String createTime;

    /**
     * 审核人
     */
    @TableField("vertify_man")
    private String vertifyMan;

    private Integer status;

    /**
     * 反馈详情
     */
    private String detail;

    @Override
    public String toString() {
        return "PmsProductVertifyRecord{" +
                ", id=" + id +
                ", productId=" + productId +
                ", createTime=" + createTime +
                ", vertifyMan=" + vertifyMan +
                ", status=" + status +
                ", detail=" + detail +
                "}";
    }
}
