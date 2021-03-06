package com.zrp.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.oms.entity.OmsCompanyAddress;
import com.zrp.mallplus.oms.mapper.OmsCompanyAddressMapper;
import com.zrp.mallplus.oms.service.IOmsCompanyAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class OmsCompanyAddressServiceImpl extends ServiceImpl<OmsCompanyAddressMapper, OmsCompanyAddress> implements IOmsCompanyAddressService {

    @Resource
    private OmsCompanyAddressMapper omsCompanyAddressMapper;


}
