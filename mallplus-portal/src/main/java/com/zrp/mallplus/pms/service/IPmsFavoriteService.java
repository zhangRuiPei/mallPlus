package com.zrp.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.pms.entity.PmsFavorite;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-06-15
 */
public interface IPmsFavoriteService extends IService<PmsFavorite> {
    int addProduct(PmsFavorite productCollection);


    List<PmsFavorite> listProduct(Long memberId, int type);

    List<PmsFavorite> listCollect(Long memberId);
}
