package com.zscat.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.oms.entity.OmsPayments;
import com.zscat.mallplus.oms.mapper.OmsPaymentsMapper;
import com.zscat.mallplus.oms.service.IOmsPaymentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class OmsPaymentsServiceImpl extends ServiceImpl<OmsPaymentsMapper, OmsPayments> implements IOmsPaymentsService {

    @Resource
    private OmsPaymentsMapper omsPaymentsMapper;


}
