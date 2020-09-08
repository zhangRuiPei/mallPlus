package com.zscat.mallplus.oms.vo;


import com.zscat.mallplus.cms.entity.CmsSubject;
import com.zscat.mallplus.pms.entity.PmsBrand;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductAttributeCategory;
import com.zscat.mallplus.pms.entity.PmsSmallNaviconCategory;
import com.zscat.mallplus.sms.entity.SmsCoupon;
import com.zscat.mallplus.sms.entity.SmsHomeAdvertise;
import com.zscat.mallplus.sms.vo.HomeFlashPromotion;
import com.zscat.mallplus.sys.entity.SysStore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页内容返回信息封装
 */
@Getter
@Setter
public class HomeContentResult {
    //小程序nav
    List<PmsSmallNaviconCategory> navList;
    //订单信息
    List<ActivityVo> activityList;
    //轮播广告
    private List<SmsHomeAdvertise> advertiseList;
    //推荐品牌
    private List<PmsBrand> brandList;
    //推荐商铺
    private List<SysStore> storeList;
    //当前秒杀场次
    private HomeFlashPromotion homeFlashPromotion;
    //新商品信息
    private List<PmsProduct> newProductList;
    //最热商品
    private List<PmsProduct> hotProductList;
    //打折商品
    private List<PmsProduct> saleProductList;
    //推荐专题
    private List<CmsSubject> subjectList;
    //产品属性分类表
    private List<PmsProductAttributeCategory> cat_list;
    //优惠券列表
    private List<SmsCoupon> couponList;

}
