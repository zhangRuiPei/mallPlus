package com.zrp.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sms.entity.SmsHomeNewProduct;
import com.zrp.mallplus.sms.vo.HomeProductAttr;

import java.util.List;

/**
 * <p>
 * 新鲜好物表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsHomeNewProductMapper extends BaseMapper<SmsHomeNewProduct> {
    List<HomeProductAttr> queryList();
}
