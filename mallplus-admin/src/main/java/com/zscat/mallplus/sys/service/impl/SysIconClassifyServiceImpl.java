package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sys.entity.SysIconClassify;
import com.zscat.mallplus.sys.mapper.SysIconClassifyMapper;
import com.zscat.mallplus.sys.service.ISysIconClassifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by EDZ on 2020/8/5.
 */
@Service
public class SysIconClassifyServiceImpl extends ServiceImpl<SysIconClassifyMapper,SysIconClassify> implements ISysIconClassifyService {
   @Resource
   private SysIconClassifyMapper sysIconClassifyMapper;

    public List<SysIconClassify> getIconClassify(){
        return sysIconClassifyMapper.getClassify();
    }


}
