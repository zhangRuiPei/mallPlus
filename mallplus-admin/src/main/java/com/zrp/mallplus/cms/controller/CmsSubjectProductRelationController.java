package com.zrp.mallplus.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.cms.service.ICmsSubjectProductRelationService;
import com.zrp.mallplus.pms.entity.CmsSubjectProductRelation;
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
 * 专题商品关系表
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "CmsSubjectProductRelationController", description = "专题商品关系表管理")
@RequestMapping("/cms/CmsSubjectProductRelation")
public class CmsSubjectProductRelationController {
    @Resource
    private ICmsSubjectProductRelationService ICmsSubjectProductRelationService;

    @SysLog(MODULE = "cms", REMARK = "根据条件查询所有专题商品关系表列表")
    @ApiOperation("根据条件查询所有专题商品关系表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('cms:CmsSubjectProductRelation:read')")
    public Object getCmsSubjectProductRelationByPage(CmsSubjectProductRelation entity,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ICmsSubjectProductRelationService.page(new Page<CmsSubjectProductRelation>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有专题商品关系表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "cms", REMARK = "保存专题商品关系表")
    @ApiOperation("保存专题商品关系表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('cms:CmsSubjectProductRelation:create')")
    public Object saveCmsSubjectProductRelation(@RequestBody CmsSubjectProductRelation entity) {
        try {
            if (ICmsSubjectProductRelationService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存专题商品关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "cms", REMARK = "更新专题商品关系表")
    @ApiOperation("更新专题商品关系表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectProductRelation:update')")
    public Object updateCmsSubjectProductRelation(@RequestBody CmsSubjectProductRelation entity) {
        try {
            if (ICmsSubjectProductRelationService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新专题商品关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "cms", REMARK = "删除专题商品关系表")
    @ApiOperation("删除专题商品关系表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectProductRelation:delete')")
    public Object deleteCmsSubjectProductRelation(@ApiParam("专题商品关系表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("专题商品关系表id");
            }
            if (ICmsSubjectProductRelationService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除专题商品关系表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "cms", REMARK = "给专题商品关系表分配专题商品关系表")
    @ApiOperation("查询专题商品关系表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('cms:CmsSubjectProductRelation:read')")
    public Object getCmsSubjectProductRelationById(@ApiParam("专题商品关系表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("专题商品关系表id");
            }
            CmsSubjectProductRelation coupon = ICmsSubjectProductRelationService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询专题商品关系表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除专题商品关系表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除专题商品关系表")
    @PreAuthorize("hasAuthority('cms:CmsSubjectProductRelation:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ICmsSubjectProductRelationService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
