package com.zscat.mallplus.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.live.entity.AliyunLiveRecords;

import java.util.Map;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface AliyunLiveRecordsService extends IService<AliyunLiveRecords> {
    Map<String, Object> findAll(AliyunLiveRecords entity, Integer pageNum, Integer pageSize);
}
