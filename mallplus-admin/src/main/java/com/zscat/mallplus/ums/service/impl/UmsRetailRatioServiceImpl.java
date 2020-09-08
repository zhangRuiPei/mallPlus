package com.zscat.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.ums.entity.UmsRetailRatio;
import com.zscat.mallplus.ums.mapper.UmsRetailRatioMapper;
import com.zscat.mallplus.ums.service.IUmsRetailRatioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class UmsRetailRatioServiceImpl extends ServiceImpl<UmsRetailRatioMapper,UmsRetailRatio> implements IUmsRetailRatioService {
    @Resource
    private UmsRetailRatioMapper umsRetailRatioMapper;


    public List<UmsRetailRatio> getList(){
        return  umsRetailRatioMapper.getList();
    }
}
