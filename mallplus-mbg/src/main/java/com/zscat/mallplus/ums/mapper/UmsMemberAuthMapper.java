package com.zscat.mallplus.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.ums.entity.UmsMemberAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by EDZ on 2020/8/12.
 */
public interface UmsMemberAuthMapper extends BaseMapper<UmsMemberAuth> {


    List<UmsMemberAuth> getAuthLists(@Param("record") UmsMemberAuth entity);
}
