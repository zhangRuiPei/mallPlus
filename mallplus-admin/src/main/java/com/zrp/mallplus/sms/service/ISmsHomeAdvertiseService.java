package com.zrp.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sms.entity.SmsHomeAdvertise;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页轮播广告表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsHomeAdvertiseService extends IService<SmsHomeAdvertise> {

    /**
     * 更新推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 修改推荐排序
     */
    int updateSort(Long id, Integer sort);

    /**
     * 条件查询所有
     *
     * @return*/
    Map<String, Object> findAll(SmsHomeAdvertise SmsHomeAdvertise , Integer pageNum, Integer pageSize);


}
