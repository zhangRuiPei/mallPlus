package com.zscat.mallplus.fenxiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.fenxiao.entity.FenxiaoConfig;
import com.zscat.mallplus.fenxiao.mapper.FenxiaoConfigMapper;
import com.zscat.mallplus.fenxiao.service.IFenxiaoConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class FenxiaoConfigServiceImpl extends ServiceImpl
        <FenxiaoConfigMapper, FenxiaoConfig> implements IFenxiaoConfigService {

    @Resource
    private FenxiaoConfigMapper fenxiaoConfigMapper;


}
