package com.zscat.mallplus.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.oms.vo.CartProduct;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.vo.PmsProductResult;
import com.zscat.mallplus.pms.vo.PromotionProduct;
import com.zscat.mallplus.sms.entity.SmsHomeBannerAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface PmsProductMapper extends BaseMapper<PmsProduct> {





    CartProduct getCartProduct(@Param("id") Long id);

    List<PromotionProduct> getPromotionProductList(@Param("ids") List<Long> ids);

    PmsProductResult getUpdateInfo(Long id);

    Integer countGoodsByToday(@Param("id") Long id);

    List<PmsProduct> listByDate(@Param("date") String date, @Param("type") Integer type);

    PmsProduct findById(@Param("id") Long id);


    List<PmsProduct> findProduct(PmsProduct pmsProduct);


    List<PmsProduct> listfindByCategoryId(@Param("productCategoryId") Long productCategoryId);

    void addProduct(PmsProduct pmsProduct);

    //    Long addAdress(SmsHomeBannerAddress smsHomeBannerAddress);
    List<SmsHomeBannerAddress> addAdress(SmsHomeBannerAddress smsHomeBannerAddress);

    List<PmsProduct> getOList(@Param("record") PmsProduct entity);
}
