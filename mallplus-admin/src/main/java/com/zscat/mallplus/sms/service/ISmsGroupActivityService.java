package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sms.entity.SmsGroupActivity;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsGroupActivityService extends IService<SmsGroupActivity> {

    int updateShowStatus(Long ids, Integer status);
}
