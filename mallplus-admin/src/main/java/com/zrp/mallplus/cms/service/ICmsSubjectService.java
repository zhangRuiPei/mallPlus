package com.zrp.mallplus.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.cms.entity.CmsSubject;

/**
 * 专题表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ICmsSubjectService extends IService<CmsSubject> {

    int updateRecommendStatus(Long ids, Integer recommendStatus);

    int updateShowStatus(Long ids, Integer showStatus);

    boolean saves(CmsSubject entity);
}
