package com.zrp.mallplus.ums.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.ums.entity.UmsMemberBlanceLog;
import com.zrp.mallplus.ums.service.IUmsMemberBlanceLogService;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "UmsMemberBlanceLogController", description = "管理")
@RequestMapping("/ums/UmsMemberBlanceLog")
public class UmsMemberBlanceLogController {
    @Resource
    private IUmsMemberBlanceLogService IUmsMemberBlanceLogService;

    @SysLog(MODULE = "ums", REMARK = "根据条件查询所有列表")
    @ApiOperation("根据条件查询所有列表")
    @GetMapping(value = "/list")
    public Object getUmsMemberBlanceLogByPage(UmsMemberBlanceLog entity,
                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IUmsMemberBlanceLogService.page(new Page<UmsMemberBlanceLog>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "ums", REMARK = "给分配")
    @ApiOperation("查询明细")
    @GetMapping(value = "/{id}")
    public Object getUmsMemberBlanceLogById(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            UmsMemberBlanceLog coupon = IUmsMemberBlanceLogService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }


}
