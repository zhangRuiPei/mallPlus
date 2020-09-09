package com.zrp.mallplus.ums.service.impl;


import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.zrp.mallplus.ums.entity.SysSms;
import com.zrp.mallplus.ums.mapper.SmsDao;
import com.zrp.mallplus.ums.service.SmsService;
import com.zrp.mallplus.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;



@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private IAcsClient acsClient;
    @Value("${aliyun.sms.sign.name}")
    private String signName;
    @Value("${aliyun.sms.template.code}")
    private String templateCode;

    @Resource
    private SmsDao smsDao;

    @Override
    public SendSmsResponse sendSmsMsg(SysSms sms) {
        if (sms.getSignName() == null) {
            sms.setSignName(this.signName);
        }
        if (sms.getTemplateCode() == null) {
            sms.setTemplateCode(this.templateCode);
        }
        // 阿里云短信官网demo代码
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(sms.getPhone());
        request.setSignName(sms.getSignName());
        request.setTemplateCode(sms.getTemplateCode());
        request.setTemplateParam(sms.getParams());
        request.setOutId(sms.getId().toString());

        SendSmsResponse response = null;
        try {
            response = acsClient.getAcsResponse(request);
            if (response != null) {
                log.info("发送短信结果：code:{}，message:{}，requestId:{}，bizId:{}", response.getCode(), response.getMessage(),
                        response.getRequestId(), response.getBizId());
                sms.setCode(response.getCode());
                sms.setMessage(response.getMessage());
                sms.setBizId(response.getBizId());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        update(sms);
        return response;
    }

    @Transactional
    @Override
    public void save(SysSms sms, Map<String, String> params) {
        if (!CollectionUtils.isEmpty(params)) {
            sms.setParams(JsonUtils.objectToJson(params));
        }

        sms.setCreateTime(new Date());
        sms.setUpdateTime(sms.getCreateTime());
        sms.setDay(sms.getCreateTime());

        smsDao.save(sms);
    }

    @Transactional
    @Override
    public void update(SysSms sms) {
        sms.setUpdateTime(new Date());
        smsDao.update(sms);
    }

    @Override
    public SysSms findById(Long id) {
        return smsDao.findById(id);
    }


}
