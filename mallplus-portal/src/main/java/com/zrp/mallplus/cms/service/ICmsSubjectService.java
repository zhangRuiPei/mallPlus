package com.zrp.mallplus.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.cms.entity.CmsSubject;
import com.zrp.mallplus.vo.timeline.Timeline;

import java.util.List;

/**
 * <p>
 * 专题表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface ICmsSubjectService extends IService<CmsSubject> {

    List<CmsSubject> getRecommendSubjectList(int pageNum, int pageSize);

    int countByToday(Long id);

    Object reward(Long aid, int coin);

    List<Timeline> listTimeLine();
}
