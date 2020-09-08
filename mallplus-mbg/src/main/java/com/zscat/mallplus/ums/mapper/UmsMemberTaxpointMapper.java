package com.zscat.mallplus.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.ums.entity.UmsMemberTaxpoint;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by EDZ on 2020/8/12.
 */
public interface UmsMemberTaxpointMapper extends BaseMapper<UmsMemberTaxpoint> {

    @Select("select open_busine,open_store,is_deduct,busine_rate,cash_out,id from ums_member_taxpoint")
    List<UmsMemberTaxpoint> getList();
}
