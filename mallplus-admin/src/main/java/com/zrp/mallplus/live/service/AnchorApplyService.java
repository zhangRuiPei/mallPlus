package com.zrp.mallplus.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.live.entity.AnchorApply;

import java.util.Map;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface AnchorApplyService extends IService<AnchorApply> {
    Map<String,Object> findAll(AnchorApply entity, Integer pageNum, Integer pageSize);
}
