package com.zrp.mallplus.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.live.entity.AliyunLive;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface AliyunLiveMapper extends BaseMapper<AliyunLive> {

    List<AliyunLive> findList(AliyunLive aliyunLive);
}
