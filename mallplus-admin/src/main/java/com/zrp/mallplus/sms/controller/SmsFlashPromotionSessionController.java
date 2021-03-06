package com.zrp.mallplus.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sms.entity.SmsFlashPromotionSession;
import com.zrp.mallplus.sms.service.ISmsFlashPromotionSessionService;
import com.zrp.mallplus.sms.vo.SmsFlashPromotionSessionDetail;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 限时购场次表
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "SmsFlashPromotionSessionController", description = "限时购场次表管理")
@RequestMapping("/sms/SmsFlashPromotionSession")
public class SmsFlashPromotionSessionController {
    @Resource
    private ISmsFlashPromotionSessionService ISmsFlashPromotionSessionService;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有限时购场次表列表")
    @ApiOperation("根据条件查询所有限时购场次表列表")
    @GetMapping(value = "/list")
    public Object getSmsFlashPromotionSessionByPage(SmsFlashPromotionSession entity,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            Object data = ISmsFlashPromotionSessionService.page(new Page<SmsFlashPromotionSession>(pageNum, pageSize), new QueryWrapper<>(entity));
            return new CommonResult().success(ISmsFlashPromotionSessionService.page(new Page<SmsFlashPromotionSession>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有限时购场次表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "保存限时购场次表")
    @ApiOperation("保存限时购场次表")
    @PostMapping(value = "/create")
    public Object saveSmsFlashPromotionSession(@RequestBody SmsFlashPromotionSession entity) {
        try {
            entity.setCreateTime(new Date());
            if (ISmsFlashPromotionSessionService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存限时购场次表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "更新限时购场次表")
    @ApiOperation("更新限时购场次表")
    @PostMapping(value = "/update/{id}")
    public Object updateSmsFlashPromotionSession(@RequestBody SmsFlashPromotionSession entity) {
        try {
            if (ISmsFlashPromotionSessionService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新限时购场次表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除限时购场次表")
    @ApiOperation("删除限时购场次表")
    @GetMapping(value = "/delete/{id}")
    public Object deleteSmsFlashPromotionSession(@ApiParam("限时购场次表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("限时购场次表id");
            }
            if (ISmsFlashPromotionSessionService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除限时购场次表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给限时购场次表分配限时购场次表")
    @ApiOperation("查询限时购场次表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sms:SmsFlashPromotionSession:read')")
    public Object getSmsFlashPromotionSessionById(@ApiParam("限时购场次表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("限时购场次表id");
            }
            SmsFlashPromotionSession coupon = ISmsFlashPromotionSessionService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询限时购场次表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除限时购场次表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除限时购场次表")
    @PreAuthorize("hasAuthority('sms:SmsFlashPromotionSession:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsFlashPromotionSessionService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "sms", REMARK = "修改启用状态")
    @ApiOperation("修改启用状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateStatus(@PathVariable Long id, Integer status) {
        int count = ISmsFlashPromotionSessionService.updateStatus(id, status);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "获取全部可选场次及其数量")
    @ApiOperation("获取全部可选场次及其数量")
    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
    @ResponseBody
    public Object selectList(Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> promotionSessionList = ISmsFlashPromotionSessionService.selectList(flashPromotionId);
        return new CommonResult().success(promotionSessionList);
    }
}
