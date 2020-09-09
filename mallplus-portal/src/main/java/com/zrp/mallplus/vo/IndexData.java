package com.zrp.mallplus.vo;


import com.zrp.mallplus.cms.entity.CmsSubject;
import com.zrp.mallplus.pms.entity.PmsProductAttributeCategory;
import com.zrp.mallplus.sms.entity.SmsCoupon;
import com.zrp.mallplus.sms.entity.SmsHomeAdvertise;
import com.zrp.mallplus.sms.entity.SmsRedPacket;
import com.zrp.mallplus.sms.vo.HomeFlashPromotion;
import com.zrp.mallplus.sms.vo.HomeProductAttr;
import com.zrp.mallplus.vo.pms.CateProduct;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18 0018.
 */
@Data
public class IndexData {
    private List<TArticleDO> module_list;
    private List<SmsHomeAdvertise> banner_list;
    private List<TArticleDO> nav_icon_list;
    private List<PmsProductAttributeCategory> cat_list;
    private HomeFlashPromotion homeFlashPromotion;
    private List<HomeProductAttr> new_products;
    private List<HomeProductAttr> hot_products;
    private List<CateProduct> cate_products;
    private int cat_goods_cols;
    private List<TArticleDO> block_list;
    private List<SmsCoupon> coupon_list;
    private List<CmsSubject> subjectList;

    private List<SmsRedPacket> redPacketList;


}
