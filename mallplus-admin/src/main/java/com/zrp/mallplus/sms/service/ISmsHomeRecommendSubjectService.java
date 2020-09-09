package com.zrp.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sms.entity.SmsHomeRecommendSubject;

import java.util.List;

/**
 * <p>
 * 首页推荐专题表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsHomeRecommendSubjectService extends IService<SmsHomeRecommendSubject> {
    /**
     * 更新推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 修改推荐排序
     */
    int updateSort(Long id, Integer sort);

    int create(List<SmsHomeRecommendSubject> homeBrandList);
}
