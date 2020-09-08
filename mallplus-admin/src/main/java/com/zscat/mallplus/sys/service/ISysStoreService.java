package com.zscat.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sys.entity.SysStore;

import java.util.Map;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISysStoreService extends IService<SysStore> {


    boolean saveStore(SysStore entity);

    Boolean addSysUser(SysStore entity);

    void sendRefuse(SysStore entity);
}
