package com.zscat.mallplus.live.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zscat.mallplus.live.entity.AliyunLive;
import com.zscat.mallplus.live.entity.AnchorApply;
import com.zscat.mallplus.live.mapper.AnchorApplyMapper;
import com.zscat.mallplus.live.service.AnchorApplyService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;



/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class AnchorApplyServiceImpl extends ServiceImpl<AnchorApplyMapper, AnchorApply> implements AnchorApplyService {
    @Override
    public Map<String, Object> findAll(AnchorApply entity, Integer pageNum, Integer pageSize) {
        Map<String,Object> map = new HashMap();

        PageHelper.startPage(pageNum,pageSize);
        Page<AnchorApply> all = (Page<AnchorApply>) this.baseMapper.findAll();
        map.put("data",all.getResult());
        map.put("total",all.getTotal());
        return map;
    }
}
