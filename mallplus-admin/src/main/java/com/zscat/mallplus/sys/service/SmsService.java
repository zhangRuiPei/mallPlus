package com.zscat.mallplus.sys.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.zscat.mallplus.ums.entity.SysSms;

import java.util.Map;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface SmsService  {

    /**
     * 保存短信
     *
     * @param sms
     * @param params
     */
    void save(SysSms sms, Map<String, String> params);

    /**
     * 保存短信
     *
     * @param sms
     * @param
     */
    void add(SysSms sms);

    /**
     * 修改短信
     *
     * @param sms
     */
    void update(SysSms sms);

    /**
     * 查询短信
     *
     * @param id
     * @return
     */
    SysSms findById(Long id);


    /**
     * 发送短信
     */
    SendSmsResponse sendSmsMsg(SysSms sms);
}
