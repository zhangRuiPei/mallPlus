package com.zrp.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.oms.entity.OmsPayments;
import com.zrp.mallplus.oms.mapper.OmsPaymentsMapper;
import com.zrp.mallplus.oms.service.IOmsPaymentsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付方式表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-09-14
 */
@Service
public class OmsPaymentsServiceImpl extends ServiceImpl<OmsPaymentsMapper, OmsPayments> implements IOmsPaymentsService {

}
