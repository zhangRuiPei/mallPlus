package com.zrp.mallplus.bill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.bill.entity.BakGoods;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IBakGoodsService extends IService<BakGoods> {

    public List<BakGoods> selectNotPar();

}
