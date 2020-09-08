package com.zscat.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.ums.entity.UmsMemberTaxpoint;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IUmsMemberTaxpointService extends IService<UmsMemberTaxpoint> {
    List<UmsMemberTaxpoint> getList();
}
