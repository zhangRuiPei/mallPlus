package com.zrp.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.pms.entity.PmsProductCategory;
import com.zrp.mallplus.pms.entity.PmsProductCategoryVo;
import com.zrp.mallplus.pms.vo.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * <p>
 * 产品分类 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IPmsProductCategoryService extends IService<PmsProductCategory> {

    List<PmsProductCategoryWithChildrenItem> listWithChildren();


    List<PmsProductCategoryVo> findFather();
}
