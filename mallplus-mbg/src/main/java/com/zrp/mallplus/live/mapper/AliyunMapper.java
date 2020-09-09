package com.zrp.mallplus.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.live.entity.AliyunLive;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AliyunMapper extends BaseMapper<AliyunLive> {

    List<AliyunLive> findList(AliyunLive aliyunLive);


    List<AliyunLive> findAll(@Param("record") AliyunLive entity);

    Integer findRoomById(@Param("roomId") Integer sixRundom);

    Integer addLive(@Param("record") AliyunLive entity);

    Integer updateByIdCanNull(@Param("record") AliyunLive aliyunLive);
}
