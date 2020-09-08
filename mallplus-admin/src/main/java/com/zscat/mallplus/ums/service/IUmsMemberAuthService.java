package com.zscat.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.ums.entity.UmsMemberAuth;

import java.util.Map;

/**
 * Created by EDZ on 2020/8/12.
 */
public interface IUmsMemberAuthService extends IService<UmsMemberAuth> {

    Map<String,Object> getAuthList(UmsMemberAuth entity, Integer pageNum, Integer pagesize);

}
