package com.zrp.mallplus.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.cms.entity.CmsSubject;
import com.zrp.mallplus.cms.entity.CmsSubjectCategory;
import com.zrp.mallplus.cms.mapper.CmsSubjectCategoryMapper;
import com.zrp.mallplus.cms.mapper.CmsSubjectMapper;
import com.zrp.mallplus.cms.service.ICmsSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class CmsSubjectServiceImpl extends ServiceImpl<CmsSubjectMapper, CmsSubject> implements ICmsSubjectService {
    @Resource
    private CmsSubjectMapper subjectMapper;


    @Resource
    private CmsSubjectCategoryMapper subjectCategoryMapper;

    @Override
    @Transactional
    public boolean saves(CmsSubject entity) {
        entity.setCreateTime(new Date());
        subjectMapper.insert(entity);
        CmsSubjectCategory category = subjectCategoryMapper.selectById(entity.getCategoryId());
        category.setSubjectCount(category.getSubjectCount() + 1);
        subjectCategoryMapper.updateById(category);
        return true;
    }

    @Override
    public int updateRecommendStatus(Long ids, Integer recommendStatus) {
        CmsSubject record = new CmsSubject();
        record.setRecommendStatus(recommendStatus);
        return subjectMapper.update(record, new QueryWrapper<CmsSubject>().eq("id", ids));
    }

    @Override
    public int updateShowStatus(Long ids, Integer showStatus) {
        CmsSubject record = new CmsSubject();
        record.setShowStatus(showStatus);
        return subjectMapper.update(record, new QueryWrapper<CmsSubject>().eq("id", ids));
    }
}
