package com.zrp.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.oms.entity.OmsOrderItem;
import com.zrp.mallplus.oms.mapper.OmsOrderItemMapper;
import com.zrp.mallplus.oms.service.IOmsOrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单中所包含的商品 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements IOmsOrderItemService {


    @Resource
    private OmsOrderItemMapper omsOrderItemMapper;

    @Override
    public List<OmsOrderItem> findListByorderSn(String orderSn) {
        List<OmsOrderItem> listByorderSn = omsOrderItemMapper.findListByorderSn(orderSn);
        return listByorderSn;
    }
}
