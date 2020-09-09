package com.zrp.mallplus.live.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zrp.mallplus.live.entity.AliyunLiveRecords;
import com.zrp.mallplus.live.mapper.AliyunLiveRecordsMapper;
import com.zrp.mallplus.live.service.AliyunLiveRecordsService;
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
public class AliyunLiveRecordsServiceImpl extends ServiceImpl<AliyunLiveRecordsMapper, AliyunLiveRecords> implements AliyunLiveRecordsService {
    @Override
    public Map<String, Object> findAll(AliyunLiveRecords entity, Integer pageNum, Integer pageSize) {
        Map<String,Object> map = new HashMap();

        PageHelper.startPage(pageNum,pageSize);
      /*  Page<AliyunLive> all = (Page<AliyunLive>) this.baseMapper.findAll().stream().sorted(Comparator.comparing(AliyunLive::getSort).reversed())
                .sorted(Comparator.comparing(AliyunLive::getCreateTime).reversed())
                .collect(Collectors.toList());
*/
        Page<AliyunLiveRecords> all = (Page<AliyunLiveRecords>) this.baseMapper.findAll(entity);
        map.put("data",all.getResult());
        map.put("total",all.getTotal());
        return map;
    }
}
