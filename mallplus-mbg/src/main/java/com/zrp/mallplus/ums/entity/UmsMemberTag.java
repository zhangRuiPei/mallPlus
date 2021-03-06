package com.zrp.mallplus.ums.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.utils.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:  用户标签表
 */
@TableName("ums_member_tag")
public class UmsMemberTag extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 1会员标签 2 商品标签 3 文章标签
     */
    private Integer type;

    /**
     * 1自动标签 2 手动标签
     */
    @TableField("gen_type")
    private Integer genType;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGenType() {
        return genType;
    }

    public void setGenType(Integer genType) {
        this.genType = genType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UmsMemberTag{" +
                ", id=" + id +
                ", name=" + name +
                ", type=" + type +
                ", genType=" + genType +
                ", createTime=" + createTime +
                "}";
    }
}
