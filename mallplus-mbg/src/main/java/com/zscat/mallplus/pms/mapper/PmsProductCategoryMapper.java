package com.zscat.mallplus.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductCategory;
import com.zscat.mallplus.pms.vo.PmsProductCategoryWithChildrenItem;
import com.zscat.mallplus.pms.vo.PmsProductList;

import java.util.List;

/**
 */
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    /**
     * 分类以及子类
     * @return
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();

    List<PmsProductList> selectProjectByCategory(Long categoryId);

    List<PmsProductCategory> findFather();

    List<PmsProductCategory> findChrildForFatherId(Long id);

    List<PmsProductCategory> findMenuAll();

}
