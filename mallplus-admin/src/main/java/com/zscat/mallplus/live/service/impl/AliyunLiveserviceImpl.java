package com.zscat.mallplus.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zscat.mallplus.live.entity.AliyunLive;
import com.zscat.mallplus.live.mapper.AliyunMapper;
import com.zscat.mallplus.live.service.AliyunLiveservice;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.mapper.UmsMemberMapper;
import com.zscat.mallplus.util.ColUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class AliyunLiveserviceImpl extends ServiceImpl<AliyunMapper, AliyunLive> implements AliyunLiveservice {

    @Resource
    private UmsMemberMapper umsMemberMapper;

    @Override
    public Map<String, Object> findAll(AliyunLive entity, Integer pageNum, Integer pageSize) {
        Map<String,Object> map = new HashMap();

        PageHelper.startPage(pageNum,pageSize);
      /*  Page<AliyunLive> all = (Page<AliyunLive>) this.baseMapper.findAll().stream().sorted(Comparator.comparing(AliyunLive::getSort).reversed())
                .sorted(Comparator.comparing(AliyunLive::getCreateTime).reversed())
                .collect(Collectors.toList());
*/
        Page<AliyunLive> all = (Page<AliyunLive>) this.baseMapper.findAll(entity);
        map.put("data",all.getResult());
        map.put("total",all.getTotal());
        return map;
    }

    @Override
    public Integer findRoomById(Integer sixRundom) {
        return this.baseMapper.findRoomById(sixRundom);
    }

    @Override
    public Integer getSixRundom() {

        ColUtil colUtil = new ColUtil();
        Integer sixRundom = colUtil.getSixRundom();
        if (findRoomById(sixRundom)!=null){
            getSixRundom();
        }
        return sixRundom;
    }

    @Override
    public Integer addDefult(Long umsMemberId) {
        Integer sixRundom = getSixRundom();
        UmsMember umsMember = umsMemberMapper.selectById(umsMemberId);
        if (umsMember!=null){
            AliyunLive entity = new AliyunLive();
            entity.setRoomNumber(sixRundom);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            entity.setCreateTime(df.format(new Date()));
            entity.setRoonStatus(0);
            entity.setIsShow(1);
            entity.setIcon(umsMember.getIcon());
            entity.setRoonName(umsMember.getNickname()+"的直播间");
            entity.setRoonStatus(0);
            entity.setSort(0);
           if (addLive(entity)==1){
               sixRundom=null;
               return sixRundom;
           }
        }
        return sixRundom;
    }

    @Override
    public Integer addLive(AliyunLive entity) {

        return this.baseMapper.addLive(entity);
    }
}
