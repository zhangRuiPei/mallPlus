package com.zrp.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.sys.entity.SysPageConfig;
import com.zrp.mallplus.sys.mapper.SysPageConfigMapper;
import com.zrp.mallplus.sys.service.ISysPageConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by EDZ on 2020/8/10.
 */
@Service
public class SysPageConfigServiceImpl extends ServiceImpl<SysPageConfigMapper,SysPageConfig> implements ISysPageConfigService {

    @Resource()
    private SysPageConfigMapper sysPageConfigMapper;

    public List<SysPageConfig> getPageConfigList(){
        return sysPageConfigMapper.getPageConfigList();
    }
}
