package com.zscat.mallplus.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.ums.entity.UmsRetailRatio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ZRF on 2020/8/13.
 */
@Mapper
public interface UmsRetailRatioMapper extends BaseMapper<UmsRetailRatio> {

    @Select("select id,product_id,partake_retail,one_level,two_level,three_level from ums_retail_ratio")
    List<UmsRetailRatio> getList();
}
