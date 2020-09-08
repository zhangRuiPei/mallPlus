package com.zscat.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.pms.entity.PmsProductCategory;
import com.zscat.mallplus.pms.vo.PmsProductCategoryWithChildrenItem;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IPmsProductCategoryService extends IService<PmsProductCategory> {

    List<PmsProductCategoryWithChildrenItem> listWithChildren();

    int updateNavStatus(List<Long> ids, Integer navStatus);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    int updateIndexStatus(List<Long> ids, Integer indexStatus);

    boolean updateAnd(PmsProductCategory entity);

    boolean saveAnd(PmsProductCategory entity);

    List<Map> findAllMenu();

    List<Map> findAllFen();

}
