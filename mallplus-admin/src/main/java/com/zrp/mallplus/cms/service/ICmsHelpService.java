package com.zrp.mallplus.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.cms.entity.CmsHelp;

/**
 * 帮助表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ICmsHelpService extends IService<CmsHelp> {

    boolean saves(CmsHelp entity);
}
