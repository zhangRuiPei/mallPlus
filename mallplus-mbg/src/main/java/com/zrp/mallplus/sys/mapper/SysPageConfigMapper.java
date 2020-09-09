package com.zrp.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sys.entity.SysPageConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by EDZ on 2020/8/10.
 */
@Mapper
public interface SysPageConfigMapper extends BaseMapper<SysPageConfig>{

    @Select("select * from sys_page_config")
    List<SysPageConfig> getPageConfigList();
}
