package com.zrp.mallplus.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.pms.entity.PmsProductAttributeValue;

/**
 * <p>
 * 存储产品参数信息的表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface PmsProductAttributeValueMapper extends BaseMapper<PmsProductAttributeValue> {


    void addproductAttributeValue(PmsProductAttributeValue pv);

}
