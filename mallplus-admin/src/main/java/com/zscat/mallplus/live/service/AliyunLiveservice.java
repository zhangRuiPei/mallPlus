package com.zscat.mallplus.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.live.entity.AliyunLive;

import java.util.Map;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface AliyunLiveservice extends IService<AliyunLive> {

    Map<String, Object> findAll(AliyunLive entity, Integer pageNum, Integer pageSize);

    Integer findRoomById(Integer sixRundom);


    Integer getSixRundom();

    Integer addDefult(Long umsMemberId);

    Integer addLive(AliyunLive entity);
}
