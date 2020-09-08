package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsGroupActivity;
import com.zscat.mallplus.sms.mapper.SmsGroupActivityMapper;
import com.zscat.mallplus.sms.service.ISmsGroupActivityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsGroupActivityServiceImpl extends ServiceImpl<SmsGroupActivityMapper, SmsGroupActivity> implements ISmsGroupActivityService {

    @Resource
    SmsGroupActivityMapper activityMapper;

    @Override
    public int updateShowStatus(Long ids, Integer status) {
        SmsGroupActivity record = new SmsGroupActivity();
        record.setStatus(status);
        return activityMapper.update(record, new QueryWrapper<SmsGroupActivity>().eq("id", ids));
    }
}
