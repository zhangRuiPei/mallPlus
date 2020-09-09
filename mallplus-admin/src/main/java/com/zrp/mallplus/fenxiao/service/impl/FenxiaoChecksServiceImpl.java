package com.zrp.mallplus.fenxiao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.fenxiao.entity.FenxiaoChecks;
import com.zrp.mallplus.fenxiao.mapper.FenxiaoChecksMapper;
import com.zrp.mallplus.fenxiao.service.IFenxiaoChecksService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class FenxiaoChecksServiceImpl extends ServiceImpl
        <FenxiaoChecksMapper, FenxiaoChecks> implements IFenxiaoChecksService {

    @Resource
    private FenxiaoChecksMapper fenxiaoChecksMapper;


}
