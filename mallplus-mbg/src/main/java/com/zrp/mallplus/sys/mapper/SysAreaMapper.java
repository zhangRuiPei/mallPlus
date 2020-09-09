package com.zrp.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sys.entity.SysArea;
import com.zrp.mallplus.sys.vo.AreaWithChildrenItem;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface SysAreaMapper extends BaseMapper<SysArea> {

    List<AreaWithChildrenItem> listWithChildren();
}
