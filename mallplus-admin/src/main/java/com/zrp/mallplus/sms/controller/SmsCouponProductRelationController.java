package com.zrp.mallplus.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sms.entity.SmsCouponProductRelation;
import com.zrp.mallplus.sms.service.ISmsCouponProductRelationService;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 优惠券和产品的关系表
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "SmsCouponProductRelationController", description = "优惠券和产品的关系表管理")
@RequestMapping("/sms/SmsCouponProductRelation")
public class SmsCouponProductRelationController {
    @Resource
    private ISmsCouponProductRelationService ISmsCouponProductRelationService;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有优惠券和产品的关系表列表")
    @ApiOperation("根据条件查询所有优惠券和产品的关系表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sms:SmsCouponProductRelation:read')")
    public Object getSmsCouponProductRelationByPage(SmsCouponProductRelation entity,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsCouponProductRelationService.page(new Page<SmsCouponProductRelation>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有优惠券和产品的关系表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "保存优惠券和产品的关系表")
    @ApiOperation("保存优惠券和产品的关系表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sms:SmsCouponProductRelation:create')")
    public Object saveSmsCouponProductRelation(@RequestBody SmsCouponProductRelation entity) {
        try {
            if (ISmsCouponProductRelationService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存优惠券和产品的关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新优惠券和产品的关系表")
    @ApiOperation("更新优惠券和产品的关系表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sms:SmsCouponProductRelation:update')")
    public Object updateSmsCouponProductRelation(@RequestBody SmsCouponProductRelation entity) {
        try {
            if (ISmsCouponProductRelationService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新优惠券和产品的关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除优惠券和产品的关系表")
    @ApiOperation("删除优惠券和产品的关系表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sms:SmsCouponProductRelation:delete')")
    public Object deleteSmsCouponProductRelation(@ApiParam("优惠券和产品的关系表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("优惠券和产品的关系表id");
            }
            if (ISmsCouponProductRelationService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除优惠券和产品的关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给优惠券和产品的关系表分配优惠券和产品的关系表")
    @ApiOperation("查询优惠券和产品的关系表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sms:SmsCouponProductRelation:read')")
    public Object getSmsCouponProductRelationById(@ApiParam("优惠券和产品的关系表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("优惠券和产品的关系表id");
            }
            SmsCouponProductRelation coupon = ISmsCouponProductRelationService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询优惠券和产品的关系表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除优惠券和产品的关系表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除优惠券和产品的关系表")
    @PreAuthorize("hasAuthority('sms:SmsCouponProductRelation:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsCouponProductRelationService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
