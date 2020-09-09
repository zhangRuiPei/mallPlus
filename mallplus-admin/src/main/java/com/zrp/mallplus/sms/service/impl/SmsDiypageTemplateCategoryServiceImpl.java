package com.zrp.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.sms.entity.SmsDiypageTemplateCategory;
import com.zrp.mallplus.sms.mapper.SmsDiypageTemplateCategoryMapper;
import com.zrp.mallplus.sms.service.ISmsDiypageTemplateCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsDiypageTemplateCategoryServiceImpl extends ServiceImpl<SmsDiypageTemplateCategoryMapper, SmsDiypageTemplateCategory> implements ISmsDiypageTemplateCategoryService {

    @Resource
    private SmsDiypageTemplateCategoryMapper smsDiypageTemplateCategoryMapper;


    @Override
    public Object selTemplateCategory() {
        return smsDiypageTemplateCategoryMapper.selectList(new QueryWrapper<>());
    }
}
