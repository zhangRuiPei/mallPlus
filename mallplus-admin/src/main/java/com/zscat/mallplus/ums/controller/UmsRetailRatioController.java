package com.zscat.mallplus.ums.controller;

import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.ums.entity.UmsRetailRatio;
import com.zscat.mallplus.ums.service.IUmsRetailRatioService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by ZRF on 2020/8/13.
 */
@Slf4j
@RestController
@Api(tags = "UmsRetailRatioController", description = "根据商品设置分销比例")
@RequestMapping("/ums/retailRatio")
public class UmsRetailRatioController {
    @Resource
    private IUmsRetailRatioService iUmsRetailRatioService;


    @SysLog(MODULE = "ums", REMARK = "查询分销比例")
    @ApiOperation("查询分销比例")
    @GetMapping(value = "/list")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    public Object getList() {

        return new CommonResult().success(iUmsRetailRatioService.getList());
    }


    /**
     * @param entity
     * @return
     *
     * 分销比例关联商品id
     */
    @SysLog(MODULE = "ums", REMARK = "新增分销比例")
    @ApiOperation("新增分销比例")
    @PostMapping(value = "/save")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    public Object saveRatio(@RequestBody UmsRetailRatio entity) {
        try {
            if (iUmsRetailRatioService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("新增分销比例：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "ums", REMARK = "更新分销比例")
    @ApiOperation("更新分销比例")
    @PostMapping(value = "/update")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    public Object updateRatio(@RequestBody UmsRetailRatio entity) {
        try {
            if (iUmsRetailRatioService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新分销比例：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

}
