package com.zrp.mallplus.bill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.bill.entity.BillAftersales;
import com.zrp.mallplus.bill.service.IBillAftersalesService;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@Api("退货单表")
@RestController
@RequestMapping("/bill/billAftersales")
public class BillAftersalesController {

    @Resource
    private IBillAftersalesService billAftersalesService;

    @SysLog(MODULE = "bill", REMARK = "根据条件查询所有退货单表")
    @ApiOperation("根据条件查询所有退货单表")
    @GetMapping(value = "/list")
    public Object getCmsHelpByPage(BillAftersales entity,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(billAftersalesService.page(new Page<BillAftersales>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有帮助表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }


}

