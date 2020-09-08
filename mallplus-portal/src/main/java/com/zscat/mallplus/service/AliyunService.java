package com.zscat.mallplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.live.entity.AliyunLive;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface AliyunService extends IService<AliyunLive> {

    List<AliyunLive> findList(AliyunLive aliyunLive);
}
