package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsFlashPromotionSession;
import com.zscat.mallplus.sms.mapper.SmsFlashPromotionSessionMapper;
import com.zscat.mallplus.sms.service.ISmsFlashPromotionProductRelationService;
import com.zscat.mallplus.sms.service.ISmsFlashPromotionSessionService;
import com.zscat.mallplus.sms.vo.SmsFlashPromotionSessionDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 限时购场次表 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsFlashPromotionSessionServiceImpl extends ServiceImpl<SmsFlashPromotionSessionMapper, SmsFlashPromotionSession> implements ISmsFlashPromotionSessionService {

    @Resource
    private SmsFlashPromotionSessionMapper promotionSessionMapper;
    @Resource
    private ISmsFlashPromotionProductRelationService relationService;

    @Override
    public List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> result = new ArrayList<>();

        List<SmsFlashPromotionSession> list = promotionSessionMapper.selectList(new QueryWrapper<>(new SmsFlashPromotionSession()).eq("status", 1));
        for (SmsFlashPromotionSession promotionSession : list) {
            SmsFlashPromotionSessionDetail detail = new SmsFlashPromotionSessionDetail();
            BeanUtils.copyProperties(promotionSession, detail);
            int count = relationService.getCount(flashPromotionId, promotionSession.getId());
            detail.setProductCount(count);
            result.add(detail);
        }
        return result;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotionSession promotionSession = new SmsFlashPromotionSession();
        promotionSession.setId(id);
        promotionSession.setStatus(status);
        return promotionSessionMapper.updateById(promotionSession);
    }
}
