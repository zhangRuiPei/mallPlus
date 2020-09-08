package com.zscat.mallplus.sms.vo;


import com.zscat.mallplus.sms.entity.*;

import java.util.List;

/**
 * 优惠券领取历史详情封装
 */
public class SmsCouponHistoryDetail extends SmsCouponHistory {
    //相关优惠券信息
    private SmsCoupon coupon;
    //优惠券关联商品
    private List<SmsCouponProductRelation> productRelationList;
    //优惠券关联商品分类
    private List<SmsCouponProductCategoryRelation> categoryRelationList;
    //优惠券关联的商家
    private List<SmsCouponStoreRelation> couponStoreRelations;

    public List<SmsCouponStoreRelation> getCouponStoreRelations() {
        return couponStoreRelations;
    }

    public void setCouponStoreRelations(List<SmsCouponStoreRelation> couponStoreRelations) {
        this.couponStoreRelations = couponStoreRelations;
    }

    public SmsCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(SmsCoupon coupon) {
        this.coupon = coupon;
    }

    public List<SmsCouponProductRelation> getProductRelationList() {
        return productRelationList;
    }

    public void setProductRelationList(List<SmsCouponProductRelation> productRelationList) {
        this.productRelationList = productRelationList;
    }

    public List<SmsCouponProductCategoryRelation> getCategoryRelationList() {
        return categoryRelationList;
    }

    public void setCategoryRelationList(List<SmsCouponProductCategoryRelation> categoryRelationList) {
        this.categoryRelationList = categoryRelationList;
    }
}
