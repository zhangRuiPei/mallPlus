package com.zscat.mallplus.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.live.entity.AliyunLiveRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AliyunLiveRecordsMapper extends BaseMapper<AliyunLiveRecords> {
    List<AliyunLiveRecords> findAll(@Param("record") AliyunLiveRecords entity);

}
