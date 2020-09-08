package com.zscat.mallplus.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.ums.entity.UmsMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:  会员表Mapper
 */
public interface UmsMemberMapper extends BaseMapper<UmsMember> {

    List<UmsMember> listByDate(@Param("date") String date, @Param("type") Integer type);

    UmsMember findByOpenId(@Param("openId")String openId);

    UmsMember findByPhone(@Param("phone")String phone);

    List<UmsMember> findAll(@Param("record")UmsMember entity);

    int updateByIdCanNull(@Param("record")UmsMember umsMember);
}
