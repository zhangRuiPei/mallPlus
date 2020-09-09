package com.zrp.mallplus.bill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.bill.entity.BakGoods;

import java.util.List;

/**
 * <p>
 * 商品基本信息表 Mapper 接口
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface BakGoodsMapper extends BaseMapper<BakGoods> {

    public List<BakGoods> selectNotPar();

}
