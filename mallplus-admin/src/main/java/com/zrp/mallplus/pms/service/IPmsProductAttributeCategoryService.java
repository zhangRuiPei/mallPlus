package com.zrp.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.pms.entity.PmsProductAttributeCategory;
import com.zrp.mallplus.pms.vo.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IPmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {

    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
