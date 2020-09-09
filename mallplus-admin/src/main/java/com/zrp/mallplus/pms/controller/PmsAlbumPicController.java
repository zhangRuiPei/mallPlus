package com.zrp.mallplus.pms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.pms.entity.PmsAlbumPic;
import com.zrp.mallplus.pms.service.IPmsAlbumPicService;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 画册图片表
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "PmsAlbumPicController", description = "画册图片表管理")
@RequestMapping("/pms/PmsAlbumPic")
public class PmsAlbumPicController {
    @Resource
    private IPmsAlbumPicService IPmsAlbumPicService;

    @SysLog(MODULE = "pms", REMARK = "根据条件查询所有画册图片表列表")
    @ApiOperation("根据条件查询所有画册图片表列表")
    @GetMapping(value = "/list")

    public Object getPmsAlbumPicByPage(PmsAlbumPic entity,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IPmsAlbumPicService.page(new Page<PmsAlbumPic>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有画册图片表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "保存画册图片表")
    @ApiOperation("保存画册图片表")
    @PostMapping(value = "/create")

    public Object savePmsAlbumPic(@RequestBody PmsAlbumPic entity) {
        try {
            if (IPmsAlbumPicService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存画册图片表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "更新画册图片表")
    @ApiOperation("更新画册图片表")
    @PostMapping(value = "/update/{id}")

    public Object updatePmsAlbumPic(@RequestBody PmsAlbumPic entity) {
        try {
            if (IPmsAlbumPicService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新画册图片表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "删除画册图片表")
    @ApiOperation("删除画册图片表")
    @GetMapping(value = "/delete/{id}")

    public Object deletePmsAlbumPic(@ApiParam("画册图片表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("画册图片表id");
            }
            if (IPmsAlbumPicService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除画册图片表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "pms", REMARK = "给画册图片表分配画册图片表")
    @ApiOperation("查询画册图片表明细")
    @GetMapping(value = "/{id}")

    public Object getPmsAlbumPicById(@ApiParam("画册图片表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("画册图片表id");
            }
            PmsAlbumPic coupon = IPmsAlbumPicService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询画册图片表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除画册图片表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除画册图片表")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IPmsAlbumPicService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
