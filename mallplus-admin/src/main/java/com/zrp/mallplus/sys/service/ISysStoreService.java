package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.SysStore;

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
