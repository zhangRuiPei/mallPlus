package com.zrp.mallplus.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.sms.vo.BasicRuls;
import com.zrp.mallplus.sms.vo.BeanKv;
import com.zrp.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 * 基本营销
 *
 * @author zscat
 * @since 2019-07-07
 */
@Data
@TableName("sms_basic_marking")
public class SmsBasicMarking extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    /**
     * 0 有效 1 无效
     */
    private Integer status;

    /**
     * 活动对象1全部用户2 会员级别
     */
    @TableField("activi_user")
    private Integer activiUser;

    /**
     * 活动商品  1 按类别  2 部分商品 0 全部
     */
    @TableField("activi_goods")
    private Integer activiGoods;

    /**
     * 1 满减 2 折扣
     */
    @TableField("big_type")
    private Integer bigType;

    /**
     * 1消费金额 2 购买件数
     */
    @TableField("small_type")
    private Integer smallType;

    /**
     * 规则
     * 优惠1 * 消费满元    优惠金额 元
     * 消费满元    折扣 折
     */
    private String rules;
    private String note;
    /**
     * 部分商品列表
     */
    @TableField("goods_ds")
    private String goodsDs;

    /**
     * 会员级别
     */
    @TableField("user_level")
    private String userLevel;

    @TableField("start_time")
    private Date startTime;

    @TableField("end_time")
    private Date endTime;

    @TableField("create_time")
    private Date createTime;

    /**
     * 消费金额
     */
    @TableField(exist = false)
    private List<BasicRuls> actrule;
    /**
     * 购买件数
     */
    @TableField(exist = false)
    private List<BasicRuls> actrule1;
    @TableField(exist = false)
    private List<BeanKv> productCategoryRelationList;
    @TableField(exist = false)
    private List<BeanKv> productRelationList;

    @TableField(exist = false)
    private List<BeanKv> memberLevelList;

    @TableField(exist = false)
    private BigDecimal minAmount;

    @TableField(exist = false)
    private BasicRuls selectRule;


}
