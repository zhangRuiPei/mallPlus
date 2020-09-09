package com.zrp.mallplus.sys.controller;

import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sys.entity.SysAgreement;
import com.zrp.mallplus.sys.service.ISysAgreementService;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by EDZ on 2020/8/1.
 * @author  zrf
 */

@Slf4j
@RestController
@Api(tags = "SysAgreementController", description = "应用协议")
@RequestMapping("/sys/sysAgreement")
public class SysAgreementController {

    @Resource
    private ISysAgreementService iSysAgreementService;

    @SysLog(MODULE = "sys", REMARK = "查询所有列表")
    @ApiOperation("查询所有列表")
    @GetMapping(value = "/list/{id}")
//    @PreAuthorize("hasAuthority('sys:SysAgreement:read')")
    public Object getAgreement(@ApiParam("id") @PathVariable Integer id){
        try {
            return new CommonResult().success(iSysAgreementService.getAgreements(id));
        }catch (Exception e){
            log.error("查询所有列表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }



    @SysLog(MODULE = "sys" , REMARK = "更新")
    @ApiOperation("更新")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('sys:SysAgreeement:update')")
    public Object updateAgree(@RequestBody SysAgreement sysAgreement){
        try {
            if(iSysAgreementService.updateAgree(sysAgreement)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("更新 %",e.getMessage(),e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }










/*    @SysLog(MODULE = "sys", REMARK = "保存")
    @ApiOperation("保存")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('sys:SysAgreement:create')")
    public  Object saveAgreement(@RequestBody SysAgreement sysAgreement){
        sysAgreement.setCreateTime(new Date());
        try {
            if(iSysAgreementService.save(sysAgreement)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("保存 %"+e.getMessage(),e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();

    }*/


/*
    @SysLog(MODULE = "sys",REMARK = "删除")
    @ApiOperation("删除")
    @PostMapping(value = "/delete/{id}")
//    @PreAuthorize("hasAuthority('sys:SysAgreeement:delete')")
    public Object deleteAgree(@ApiParam("id") @PathVariable Integer id){
        try {
            if(ValidatorUtils.empty(id)){
                return new CommonResult().paramFailed("id");
            }
            if(iSysAgreementService.removeById(id)){

                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("删除 %",e.getMessage(),e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }*/
}
