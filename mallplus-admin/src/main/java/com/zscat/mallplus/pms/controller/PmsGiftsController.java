package com.zscat.mallplus.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.pms.entity.PmsGifts;
import com.zscat.mallplus.pms.service.IPmsGiftsService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
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
 * 礼品表
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "PmsGiftsController", description = "礼品表管理")
@RequestMapping("/pms/PmsGifts")
public class PmsGiftsController {
    @Resource
    private IPmsGiftsService IPmsGiftsService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有礼品表列表")
    @ApiOperation("根据条件查询所有礼品表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('pms:PmsGifts:read')")
    public Object getPmsGiftsByPage(PmsGifts entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            if (ValidatorUtils.notEmpty(entity.getTitle())) {
                return new CommonResult().success(IPmsGiftsService.page(new Page<PmsGifts>(pageNum, pageSize), new QueryWrapper<PmsGifts>(new PmsGifts()).like("title", entity.getTitle())));
            }
            return new CommonResult().success(IPmsGiftsService.page(new Page<PmsGifts>(pageNum, pageSize), new QueryWrapper<>(entity)));

        } catch (Exception e) {
            log.error("根据条件查询所有礼品表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存礼品表")
    @ApiOperation("保存礼品表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('pms:PmsGifts:create')")
    public Object savePmsGifts(@RequestBody PmsGifts entity) {
        try {
            if (IPmsGiftsService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存礼品表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新礼品表")
    @ApiOperation("更新礼品表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGifts:update')")
    public Object updatePmsGifts(@RequestBody PmsGifts entity) {
        try {
            if (IPmsGiftsService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新礼品表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除礼品表")
    @ApiOperation("删除礼品表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGifts:delete')")
    public Object deletePmsGifts(@ApiParam("礼品表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("礼品表id");
            }
            if (IPmsGiftsService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除礼品表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给礼品表分配礼品表")
    @ApiOperation("查询礼品表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('pms:PmsGifts:read')")
    public Object getPmsGiftsById(@ApiParam("礼品表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("礼品表id");
            }
            PmsGifts coupon = IPmsGiftsService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询礼品表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除礼品表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除礼品表")
    @PreAuthorize("hasAuthority('pms:PmsGifts:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsGiftsService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation(value = "批量更新显示状态")
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量更新显示状态")
    public Object updateShowStatus(@RequestParam("ids") Long ids,
                                   @RequestParam("showStatus") Integer showStatus) {
        PmsGifts g = new PmsGifts();
        g.setId(ids);
        g.setShowStatus(showStatus);
        if (IPmsGiftsService.updateById(g)) {
            return new CommonResult().success();
        } else {
            return new CommonResult().failed();
        }
    }

}
