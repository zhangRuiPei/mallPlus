package com.zscat.mallplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sys.entity.SysPageCfgNotic;
import com.zscat.mallplus.sys.service.ISysPageCfgNoticService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by EDZ on 2020/8/6.
 */
@Slf4j
@RestController
@Api(tags = "SysPageCfgNoticController", description = "公告管理（页面配置）")
@RequestMapping("/sys/pageCfgNotic")
public class SysPageCfgNoticController {
    @Resource
    private ISysPageCfgNoticService iSysPageCfgNoticService;



    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有 公告（页面配置）")
    @ApiOperation("根据条件查询所有 公告（页面配置）")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('sys:notice:read')")
    public Object getSysPageNoticByPage(SysPageCfgNotic sysIconManage,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        try {
            return new CommonResult().success(iSysPageCfgNoticService.page(new Page<SysPageCfgNotic>(pageNum, pageSize), new QueryWrapper<>(sysIconManage)));
        }catch (Exception e){
            log.error("根据条件查询所有 公告（页面配置）: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }



    @SysLog(MODULE = "sys", REMARK = "保存公告（页面配置）")
    @ApiOperation("保存公告（页面配置）")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('sys:notice:create')")
    public  Object saveSysPageNotic(@RequestBody SysPageCfgNotic sysPageCfgNotic){
        sysPageCfgNotic.setCreateTime(new Date());
        try {

            if(iSysPageCfgNoticService.save(sysPageCfgNotic)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("保存公告（页面配置）: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }


   @SysLog(MODULE = "sys", REMARK = "更新公告（页面配置）")
    @ApiOperation("更新公告（页面配置）")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Object updateSysPageNotic(@RequestBody SysPageCfgNotic sysIconManage) {
        sysIconManage.setUpdateTime(new Date());
        try {
            if (iSysPageCfgNoticService.updateById(sysIconManage)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新公告（页面配置）：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }


    @ApiOperation(value = "批量删除公告（页面配置）")
    @PostMapping(value = "/delete/batch")
    @SysLog(MODULE = "sys", REMARK = "批量删除公告（页面配置）")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = iSysPageCfgNoticService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {

            return new CommonResult().failed();
        }
    }




}
