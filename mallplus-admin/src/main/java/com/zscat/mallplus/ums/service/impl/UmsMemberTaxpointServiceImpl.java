package com.zscat.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.ums.entity.UmsMemberTaxpoint;
import com.zscat.mallplus.ums.mapper.UmsMemberTaxpointMapper;
import com.zscat.mallplus.ums.service.IUmsMemberTaxpointService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by EDZ on 2020/8/12.
 */
@Service
public class UmsMemberTaxpointServiceImpl extends ServiceImpl<UmsMemberTaxpointMapper,UmsMemberTaxpoint> implements IUmsMemberTaxpointService {
    @Resource
    private UmsMemberTaxpointMapper umsMemberTaxpointMapper;


    public List<UmsMemberTaxpoint> getList(){
        return umsMemberTaxpointMapper.getList();

    }
}
