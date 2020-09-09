package com.zrp.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.cms.entity.CmsSubject;
import com.zrp.mallplus.oms.vo.HomeContentResult;
import com.zrp.mallplus.pms.entity.PmsBrand;
import com.zrp.mallplus.pms.entity.PmsProduct;
import com.zrp.mallplus.pms.entity.PmsProductAttributeCategory;
import com.zrp.mallplus.pms.entity.PmsSmallNaviconCategory;
import com.zrp.mallplus.sms.entity.SmsGroup;
import com.zrp.mallplus.sms.entity.SmsHomeAdvertise;
import com.zrp.mallplus.sms.vo.HomeFlashPromotion;
import com.zrp.mallplus.sys.entity.SysStore;
import com.zrp.mallplus.vo.home.Pages;

import java.util.List;

/**
 * <p>
 * 首页轮播广告表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface ISmsHomeAdvertiseService extends IService<SmsHomeAdvertise> {

    HomeContentResult singelContent();

    HomeContentResult singelContent1();

    List<PmsBrand> getRecommendBrandList(int pageNum, int pageSize);

    List<HomeFlashPromotion> FlashPromotionList();

    HomeFlashPromotion getHomeFlashPromotion();

    List<PmsProduct> getNewProductList(int pageNum, int pageSize);

    List<PmsProduct> getSaleProductList(int pageNum, int pageSize);

    List<PmsProduct> getHotProductList(int pageNum, int pageSize);

    List<CmsSubject> getRecommendSubjectList(int pageNum, int pageSize);

    List<SmsHomeAdvertise> getHomeAdvertiseList(int type);

    List<SmsHomeAdvertise> getHomeAdvertiseList();

    List<PmsProductAttributeCategory> getPmsProductAttributeCategories();

    List<SysStore> getStoreList(int pageNum, int pageSize);

    List<HomeFlashPromotion> homeFlashPromotionList();

    List<PmsSmallNaviconCategory> getNav();

    List<SmsGroup> lastGroupGoods(Integer pageNum);

    Pages contentNew();

    HomeContentResult contentNew1();

    HomeContentResult singelmobileadvertiseList();

    HomeContentResult contentPc();

    HomeContentResult singelmobileContent();
}
