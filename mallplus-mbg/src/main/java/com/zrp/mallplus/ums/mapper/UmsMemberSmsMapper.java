package com.zrp.mallplus.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.ums.entity.UmsMemberSms;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by EDZ on 2020/8/11.
 */
@Mapper
public interface UmsMemberSmsMapper extends BaseMapper<UmsMemberSms>{

//    List<SysUser> getUserLists(@Param("record") SysUser entity);
      List<UmsMemberSms> getSmsList();
}
