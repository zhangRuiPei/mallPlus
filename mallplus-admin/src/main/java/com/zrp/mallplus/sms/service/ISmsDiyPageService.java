package com.zrp.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sms.entity.SmsDiyPage;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsDiyPageService extends IService<SmsDiyPage> {

    Integer selDiyPageTypeId(Integer typeId, Long id);

    Object selDiyPageDetail(SmsDiyPage entity);

    Integer selectCounts(Long id, String name);
}
