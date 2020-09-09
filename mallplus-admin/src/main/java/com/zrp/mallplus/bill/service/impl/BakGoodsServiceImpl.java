package com.zrp.mallplus.bill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.bill.entity.BakGoods;
import com.zrp.mallplus.bill.mapper.BakGoodsMapper;
import com.zrp.mallplus.bill.service.IBakGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time Auto
 * @remark:商品基本信息表 服务实现类
 */
@Service
public class BakGoodsServiceImpl extends ServiceImpl<BakGoodsMapper, BakGoods> implements IBakGoodsService {



    @Autowired
    private BakGoodsMapper bakGoodsMapper;


    @Override
    public List<BakGoods> selectNotPar() {
        return bakGoodsMapper.selectNotPar();
    }
}
