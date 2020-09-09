package com.zrp.mallplus.ums.service;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.ums.entity.UmsMemberSms;

import java.util.Map;

/**
 * Created by EDZ on 2020/8/11.
 */
public interface IUmsMemberSmsService extends IService<UmsMemberSms> {

    Map<String,Object> getSmsList(UmsMemberSms entity, Integer pageNum, Integer pagesize);

    boolean sandSaveSms(String phone) throws ClientException, InterruptedException;
}
