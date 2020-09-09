package com.zrp.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sys.entity.SysIconClassify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by EDZ on 2020/8/5.
 */
@Mapper
public interface SysIconClassifyMapper  extends BaseMapper<SysIconClassify>{
    @Select("SELECT * from sys_icon_classify ")
    List<SysIconClassify> getClassify();

}
