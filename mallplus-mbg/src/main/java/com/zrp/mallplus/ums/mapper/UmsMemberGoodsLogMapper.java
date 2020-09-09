package com.zrp.mallplus.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.ums.entity.UmsMemberGoodsLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by ${ZRF} on 2020/8/13.
 */
public interface UmsMemberGoodsLogMapper extends BaseMapper<UmsMemberGoodsLog>{
    List<UmsMemberGoodsLog> getLogList(@Param("record") UmsMemberGoodsLog entity);
}
