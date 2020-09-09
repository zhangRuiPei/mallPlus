package com.zrp.mallplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sys.entity.SysPaymentType;
import com.zrp.mallplus.sys.service.ISysPaymentTypeService;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by EDZ on 2020/8/4.
 */

@Slf4j
@RestController
@Api(tags = "SysPaymentTypeController", description = "控制应用收款类型")
@RequestMapping("/sys/paymentType")
public class SysPaymentTypeController {


    @Resource
    private ISysPaymentTypeService iSysPaymentTypeService;




    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有支付类型列表")
    @ApiOperation("根据条件查询所有支付类型列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('sys:notice:read')")
    public Object getSysPaymentByPage(SysPaymentType sysPaymentType,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        try {
            return new CommonResult().success(iSysPaymentTypeService.page(new Page<SysPaymentType>(pageNum, pageSize), new QueryWrapper(sysPaymentType)));
        }catch (Exception e){
            log.error("根据条件查询所有支付类型列表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }



    @SysLog(MODULE = "sys", REMARK = "保存支付类型表")
    @ApiOperation("保存支付类型表")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('sys:notice:create')")
    public  Object saveSysPayment(@RequestBody SysPaymentType sysPaymentType, Principal principal){
        sysPaymentType.setCreateTime(new Date());

        sysPaymentType.setCreateBy(principal.getName());
        try {

            if(iSysPaymentTypeService.save(sysPaymentType)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("保存支付类型表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }


   @SysLog(MODULE = "sys", REMARK = "更新支付类型表")
    @ApiOperation("更新支付类型表")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Object updateSysPayment(@RequestBody SysPaymentType sysPaymentType,Principal principal) {
        sysPaymentType.setUpdateTime(new Date());
        sysPaymentType.setUpdateBy(principal.getName());
        try {
            if (iSysPaymentTypeService.updateById(sysPaymentType)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新支付类型表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }


  /*

    @SysLog(MODULE = "sys", REMARK = "删除支付类型表")
    @ApiOperation("删除支付类型表")
    @GetMapping(value = "/delete/{id}")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBuildNotice(@ApiParam ("支付类型表id") @PathVariable Integer id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("支付类型表id");
            }
            if (iSysSetNoticeService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除支付类型表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


 */

    @ApiOperation(value = "批量删除支付类型表")
    @PostMapping(value = "/delete/batch")
    @SysLog(MODULE = "sys", REMARK = "批量删除支付类型表")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = iSysPaymentTypeService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {

            return new CommonResult().failed();
        }
    }

}
