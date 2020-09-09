package com.zrp.mallplus.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.cms.entity.CmsFavorite;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-06-15
 */
public interface ICmsFavoriteService extends IService<CmsFavorite> {
    int addProduct(CmsFavorite productCollection);


    List<CmsFavorite> listProduct(Long memberId, int type);

    List<CmsFavorite> listCollect(Long memberId);
}
