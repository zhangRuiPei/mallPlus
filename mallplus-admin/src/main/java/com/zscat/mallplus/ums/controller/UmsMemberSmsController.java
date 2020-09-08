package com.zscat.mallplus.ums.controller;

import com.aliyuncs.exceptions.ClientException;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.ums.entity.UmsMemberSms;
import com.zscat.mallplus.ums.service.IUmsMemberSmsService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by EDZ on 2020/8/11.
 */
@Slf4j
@RestController
@Api(tags = "UmsMemberSmsController", description = "会员手机登录短信验证码")
@RequestMapping("/ums/UmsMemberSms")
public class UmsMemberSmsController {
    @Resource
    private IUmsMemberSmsService iUmsMemberSmsService;



    @SysLog(MODULE = "sys", REMARK = "根据条件查询验证码列表")
    @ApiOperation("根据条件查询验证码列表")
    @GetMapping(value = "/list")
    public Object getListByPage(UmsMemberSms entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {

        return new CommonResult().success(iUmsMemberSmsService.getSmsList(entity,pageNum,pageSize));
    }


    @ApiOperation(value = "发送验证码")
    @PostMapping(value = "/getVerification")
    @ResponseBody
    @SysLog(MODULE = "ums", REMARK = "发送验证码")
    public Object operVerification(@RequestParam("phone") String phone) throws ClientException, InterruptedException{

        if(iUmsMemberSmsService.sandSaveSms(phone)){
            return new CommonResult().success();
        }else {
            return new CommonResult().failed();
        }
    }



}
