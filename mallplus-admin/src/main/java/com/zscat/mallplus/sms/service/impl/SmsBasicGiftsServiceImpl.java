package com.zscat.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sms.entity.SmsBasicGifts;
import com.zscat.mallplus.sms.mapper.SmsBasicGiftsMapper;
import com.zscat.mallplus.sms.service.ISmsBasicGiftsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsBasicGiftsServiceImpl extends ServiceImpl<SmsBasicGiftsMapper, SmsBasicGifts> implements ISmsBasicGiftsService {

    @Resource
    private SmsBasicGiftsMapper giftsMapper;

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsBasicGifts gifts = new SmsBasicGifts();
        gifts.setId(id);
        gifts.setStatus(status);
        return giftsMapper.updateById(gifts);
    }
}
