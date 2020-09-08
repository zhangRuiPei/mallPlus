package com.zscat.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sms.entity.SmsHomeAdvertise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 首页轮播广告表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsHomeAdvertiseMapper extends BaseMapper<SmsHomeAdvertise> {

    List<SmsHomeAdvertise> findAll(@Param("type") Integer type);
}
