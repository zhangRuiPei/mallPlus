package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.SysPageConfig;

import java.util.List;

/**
 * Created by EDZ on 2020/8/10.
 */

public interface ISysPageConfigService extends IService<SysPageConfig> {
    List<SysPageConfig> getPageConfigList();
}
