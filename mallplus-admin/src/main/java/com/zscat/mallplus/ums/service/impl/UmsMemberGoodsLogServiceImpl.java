package com.zscat.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zscat.mallplus.ums.entity.UmsMemberGoodsLog;
import com.zscat.mallplus.ums.mapper.UmsMemberGoodsLogMapper;
import com.zscat.mallplus.ums.service.IUmsMemberGoodsLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZRF on 2020/8/13.
 */
@Service
public class UmsMemberGoodsLogServiceImpl extends ServiceImpl<UmsMemberGoodsLogMapper,UmsMemberGoodsLog> implements IUmsMemberGoodsLogService {
    @Resource
    private UmsMemberGoodsLogMapper umsMemberGoodsLogMapper;


    public Map<String,Object> getLogList(UmsMemberGoodsLog entity, Integer pageNum, Integer pageSize){
        Map<String,Object> map = new HashMap<>();

        PageHelper.startPage(pageNum,pageSize);

        List<UmsMemberGoodsLog> logLists = umsMemberGoodsLogMapper.getLogList(entity);

        Page<UmsMemberGoodsLog> all = (Page<UmsMemberGoodsLog>)logLists;
        map.put("data",all.getResult());
        map.put("total",all.getTotal());

        return map;
    }
}
