package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sys.entity.SysTest;
import com.zscat.mallplus.sys.mapper.SysTestMapper;
import com.zscat.mallplus.sys.service.ISysTestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SysTestServiceImpl extends ServiceImpl<SysTestMapper, SysTest> implements ISysTestService {


    @Resource
    private SysTestMapper sysTestMapper;


}
