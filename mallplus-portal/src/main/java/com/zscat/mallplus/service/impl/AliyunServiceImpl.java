package com.zscat.mallplus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.live.entity.AliyunLive;
import com.zscat.mallplus.live.mapper.AliyunLiveMapper;
import com.zscat.mallplus.service.AliyunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class AliyunServiceImpl extends ServiceImpl<AliyunLiveMapper, AliyunLive> implements AliyunService {

    @Autowired
    private AliyunLiveMapper aliyunLiveMapper;
    @Override
    public List<AliyunLive> findList(AliyunLive aliyunLive) {
        return aliyunLiveMapper.findList(aliyunLive);
    }
}
