package com.zrp.mallplus.ums.controller;

import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.ums.entity.UmsMemberTaxpoint;
import com.zrp.mallplus.ums.service.IUmsMemberTaxpointService;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by EDZ on 2020/8/12.
 */
@Slf4j
@RestController
@Api(tags = "UmsMemberTaxpointController", description = "设置提现税点")
@RequestMapping("/ums/taxpoint")
public class UmsMemberTaxpointController {
    @Resource
    private IUmsMemberTaxpointService iUmsMemberTaxpointService;

    @SysLog(MODULE = "ums", REMARK = "查询提现税点")
    @ApiOperation("查询提现税点")
    @GetMapping(value = "/list")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    public Object getList() {
        return new CommonResult().success(iUmsMemberTaxpointService.getList());
    }



    @SysLog(MODULE = "ums", REMARK = "设置提现税点")
    @ApiOperation("设置提现税点")
    @PostMapping(value = "/update")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    public Object updateUmsMember(@RequestBody UmsMemberTaxpoint entity) {
        try {
            if (iUmsMemberTaxpointService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("设置提现税点：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

}
