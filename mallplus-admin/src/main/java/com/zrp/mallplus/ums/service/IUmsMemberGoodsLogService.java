package com.zrp.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.ums.entity.UmsMemberGoodsLog;

import java.util.Map;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IUmsMemberGoodsLogService extends IService<UmsMemberGoodsLog>{
    Map<String,Object> getLogList(UmsMemberGoodsLog entity, Integer pageNum, Integer pagesize);
}
