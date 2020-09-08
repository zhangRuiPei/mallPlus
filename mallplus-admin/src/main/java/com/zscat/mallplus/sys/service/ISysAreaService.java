package com.zscat.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sys.entity.SysArea;
import com.zscat.mallplus.sys.vo.AreaWithChildrenItem;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISysAreaService extends IService<SysArea> {

    List<AreaWithChildrenItem> listWithChildren();
}
