package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.SysIconClassify;

import java.util.List;


/**
 * Created by EDZ on 2020/8/5.
 */
public interface ISysIconClassifyService extends IService<SysIconClassify> {

    List<SysIconClassify> getIconClassify();
}
