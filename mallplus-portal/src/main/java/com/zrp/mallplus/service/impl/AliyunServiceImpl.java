package com.zrp.mallplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.live.entity.AliyunLive;
import com.zrp.mallplus.live.mapper.AliyunLiveMapper;
import com.zrp.mallplus.service.AliyunService;
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
