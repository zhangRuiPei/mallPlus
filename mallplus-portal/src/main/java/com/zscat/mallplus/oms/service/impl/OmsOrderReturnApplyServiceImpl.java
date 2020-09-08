package com.zscat.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.oms.entity.OmsOrderReturnApply;
import com.zscat.mallplus.oms.mapper.OmsOrderReturnApplyMapper;
import com.zscat.mallplus.oms.service.IOmsOrderReturnApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Service
public class OmsOrderReturnApplyServiceImpl extends ServiceImpl<OmsOrderReturnApplyMapper, OmsOrderReturnApply> implements IOmsOrderReturnApplyService {


    @Resource
    private OmsOrderReturnApplyMapper omsOrderReturnApplyMapper;

    @Override
    public OmsOrderReturnApply findReturnApply(Long productId, String username,Long orderId) {
        return omsOrderReturnApplyMapper.findReturnApply(productId,username,orderId);
    }
}
