package com.zrp.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sms.entity.SmsFlashPromotionSession;
import com.zrp.mallplus.sms.vo.SmsFlashPromotionSessionDetail;

import java.util.List;

/**
 * 限时购场次表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISmsFlashPromotionSessionService extends IService<SmsFlashPromotionSession> {

    /**
     * 修改场次启用状态
     */
    int updateStatus(Long id, Integer status);

    /**
     * 获取全部可选场次及其数量
     */
    List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);
}
