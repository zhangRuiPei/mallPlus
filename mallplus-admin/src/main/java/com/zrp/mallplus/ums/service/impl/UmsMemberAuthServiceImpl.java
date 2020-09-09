package com.zrp.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zrp.mallplus.ums.entity.UmsMemberAuth;
import com.zrp.mallplus.ums.mapper.UmsMemberAuthMapper;
import com.zrp.mallplus.ums.service.IUmsMemberAuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by EDZ on 2020/8/12.
 */
@Service
public class UmsMemberAuthServiceImpl extends ServiceImpl<UmsMemberAuthMapper,UmsMemberAuth> implements IUmsMemberAuthService {

    @Resource
    private UmsMemberAuthMapper umsMemberAuthMapper;

    public Map<String,Object> getAuthList(UmsMemberAuth entity, Integer pageNum, Integer pageSize){
        Map<String,Object> map = new HashMap<>();

        PageHelper.startPage(pageNum,pageSize);

        List<UmsMemberAuth> authLists = umsMemberAuthMapper.getAuthLists(entity);

        Page<UmsMemberAuth> all = (Page<UmsMemberAuth>)authLists ;
        map.put("data",all.getResult());
        map.put("total",all.getTotal());

        return map;

    }
}
