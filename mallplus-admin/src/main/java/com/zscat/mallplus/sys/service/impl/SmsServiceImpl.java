package com.zscat.mallplus.sys.service.impl;


import com.alibaba.fastjson.JSON;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.zscat.mallplus.sys.service.SmsService;
import com.zscat.mallplus.ums.entity.SysSms;
import com.zscat.mallplus.ums.mapper.SysSmsMapper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
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
    private SysSmsMapper sysSmsMapper;

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
            sms.setParams(JSON.toJSONString(params));
        }

        sms.setCreateTime(new Date());
        sms.setUpdateTime(sms.getCreateTime());
        sms.setDay(sms.getCreateTime());

        sysSmsMapper.saveSysSms(sms);
    }
    @Transactional
    @Override
    public void add(SysSms sms) {
        sms.setCreateTime(new Date());
        sms.setUpdateTime(sms.getCreateTime());
        sms.setDay(sms.getCreateTime());

        sysSmsMapper.saveSysSms(sms);
    }
    @Transactional
    @Override
    public void update(SysSms sms) {
        sms.setUpdateTime(new Date());
        sysSmsMapper.updates(sms);
    }

    @Override
    public SysSms findById(Long id) {
        return sysSmsMapper.findSysSmsById(id);
    }


}
