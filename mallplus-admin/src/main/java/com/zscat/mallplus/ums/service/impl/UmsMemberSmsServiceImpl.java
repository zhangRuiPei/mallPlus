package com.zscat.mallplus.ums.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zscat.mallplus.ums.entity.UmsMemberSms;
import com.zscat.mallplus.ums.mapper.UmsMemberSmsMapper;
import com.zscat.mallplus.ums.service.IUmsMemberSmsService;
import com.zscat.mallplus.util.SMSUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class UmsMemberSmsServiceImpl extends ServiceImpl<UmsMemberSmsMapper,UmsMemberSms> implements IUmsMemberSmsService {
    @Resource
    private UmsMemberSmsMapper umsMemberSmsMapper;

    @Resource
    private IUmsMemberSmsService iUmsMemberSmsService;
    @Override
    public Map<String,Object> getSmsList(UmsMemberSms entity, Integer pageNum, Integer pageSize){

            Map<String,Object> map = new HashMap<>();

            PageHelper.startPage(pageNum,pageSize);

            List<UmsMemberSms> userLists = umsMemberSmsMapper.getSmsList();

            Page<UmsMemberSms> all = (Page<UmsMemberSms>)userLists ;
            map.put("data",all.getResult());
            map.put("total",all.getTotal());

            return map;
    }


    @Override
    public boolean sandSaveSms(String phone) throws ClientException, InterruptedException{
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        String code = sb.toString();
        System.out.println("发送的验证码为：" + code);
        //发短信
        Map<String,String> map= SMSUtil.sendSms(phone, code); // TODO 填写你需要测试的手机号码
        System.out.println("短信接口返回的数据----------------");
        System.out.println("phone=" + phone);
        System.out.println("Code=" + map.get("sendSms"));
        System.out.println("Message=" + map.get("message"));
        System.out.println("RequestId=" + map.get("requestId"));
        System.out.println("BizId=" + map.get("bizId"));
        if(map.get("code") != null && map.get("code").equals("OK")){
            UmsMemberSms entity = new UmsMemberSms();
            entity.setPhone(phone);
            entity.setSignName(map.get("signName"));
            entity.setTemplateCode(map.get("templateCode"));
            entity.setParams(code);
            entity.setBizId(map.get("bizId"));
            entity.setCode(map.get("code"));
            entity.setMessage(map.get("message"));
            entity.setCreateTime(new Date());
            iUmsMemberSmsService.save(entity);
            return true;
        }else {
            return false;
        }


       /* 不删 留着 以后可能有用
        System.out.println("  ==============================================  ");
        Thread.sleep(3000L);
        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }*/



    }

}
