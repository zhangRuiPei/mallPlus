package com.zrp.mallplus.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrp.mallplus.sys.entity.SysEmailConfig;
import com.zrp.mallplus.sys.service.ISysEmailConfigService;
import com.zrp.mallplus.sys.vo.EmailVo;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@RestController
@RequestMapping("/sys/sysEmailConfig")
public class SysEmailConfigController {

    @Resource
    private ISysEmailConfigService emailService;


    @GetMapping(value = "/{id}")
    public Object get() {
        return new CommonResult().success(emailService.getOne(new QueryWrapper<>()));
    }


    @PostMapping(value = "/update")
    @ApiOperation("配置邮件")
    public Object emailConfig(@Validated @RequestBody SysEmailConfig emailConfig) {
        emailService.updateById(emailConfig);
        return new CommonResult().success();
    }


    @PostMapping(value = "/send")
    @ApiOperation("发送邮件")
    public Object send(@Validated @RequestBody EmailVo emailVo) throws Exception {
        emailService.send(emailVo, emailService.getOne(new QueryWrapper<>()));
        return new CommonResult().success();
    }
}

