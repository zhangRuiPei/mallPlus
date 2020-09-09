package com.zrp.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.utils.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@TableName("ums_member_blance_log")
public class UmsMemberBlanceLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 0 全部', '1 消费', '2 退款', '3 充值', '4 提现', '5 佣金', '7 平台调整
     */
    private Integer type;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 加减余额原因
     */
    @TableField("remark_reason")
    private String remarkReason;

    public String getRemarkReason() {
        return remarkReason;
    }

    public void setRemarkReason(String remarkReason) {
        this.remarkReason = remarkReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UmsMemberBlanceLog{" +
                ", id=" + id +
                ", memberId=" + memberId +
                ", price=" + price +
                ", type=" + type +
                ", note=" + note +
                ", createTime=" + createTime +
                ", remarkReason=" + remarkReason+
                "}";
    }
}
