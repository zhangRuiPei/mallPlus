package com.zscat.mallplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sys.entity.SysIconManage;
import com.zscat.mallplus.sys.service.ISysIconMnageService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by EDZ on 2020/8/5.
 */

@Slf4j
@RestController
@Api(tags = "SysIconManageController", description = "icon管理")
@RequestMapping("/sys/iconManage")
public class SysIconManageController {
    @Resource
    private ISysIconMnageService iSysIconMnageService;



    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有icon列表")
    @ApiOperation("根据条件查询所有icon列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('sys:notice:read')")
    public Object getSysNoticeByPage(SysIconManage sysIconManage,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        try {
            return new CommonResult().success(iSysIconMnageService.page(new Page<SysIconManage>(pageNum, pageSize), new QueryWrapper<>(sysIconManage)));
        }catch (Exception e){
            log.error("根据条件查询所有icon列表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }



    @SysLog(MODULE = "sys", REMARK = "保存icon列表")
    @ApiOperation("保存icon列表")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('sys:notice:create')")
    public  Object saveSysNotice(@RequestBody SysIconManage sysIconManage){
        try {

            if(iSysIconMnageService.save(sysIconManage)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("保存icon列表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }


   @SysLog(MODULE = "sys", REMARK = "更新icon列表")
    @ApiOperation("更新icon列表")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Object updateSysNotice(@RequestBody SysIconManage sysIconManage) {
        try {
            if (iSysIconMnageService.updateById(sysIconManage)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新icon列表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }


    @ApiOperation(value = "批量删除icon列表")
    @PostMapping(value = "/delete/batch")
    @SysLog(MODULE = "sys", REMARK = "批量删除icon列表")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = iSysIconMnageService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {

            return new CommonResult().failed();
        }
    }

}
