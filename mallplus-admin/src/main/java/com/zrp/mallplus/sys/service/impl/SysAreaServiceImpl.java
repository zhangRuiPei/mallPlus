package com.zrp.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.sys.entity.SysArea;
import com.zrp.mallplus.sys.mapper.SysAreaMapper;
import com.zrp.mallplus.sys.service.ISysAreaService;
import com.zrp.mallplus.sys.vo.AreaWithChildrenItem;
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
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements ISysAreaService {

    @Resource
    SysAreaMapper sysAreaMapper;

    @Override
    public List<AreaWithChildrenItem> listWithChildren() {
        return sysAreaMapper.listWithChildren();
    }
}


