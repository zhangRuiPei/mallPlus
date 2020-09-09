package com.zrp.mallplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sys.entity.SysVersionControl;
import com.zrp.mallplus.sys.service.ISysUserService;
import com.zrp.mallplus.sys.service.ISysVersionControlService;
import com.zrp.mallplus.util.EasyPoiUtils;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by EDZ on 2020/8/4.
 */

@Slf4j
@RestController
@Api(tags = "SysVersionContro", description = "系统版本控制")
@RequestMapping("/sys/versionControl")
public class SysVersionControlController {

    @Resource
    private ISysVersionControlService iSysVersionControlService;
    @Resource
    private ISysUserService sysUserService;

    @SysLog(MODULE = "sys", REMARK = "查询版本控制列表")
    @ApiOperation("查询版本控制列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('sys:notice:read')")
    public Object getSysNoticeByPage(SysVersionControl entity,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        try {

            return  new CommonResult().success(iSysVersionControlService.page(new Page<SysVersionControl>(pageNum,pageSize),new QueryWrapper(entity)));
        }catch (Exception e){
            log.error("根据条件查询版本控制列表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }




    @SysLog(MODULE = "sys", REMARK = "保存版本控制表")
    @ApiOperation("保存版本控制表")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('sys:notice:create')")
    public  Object saveSysNotice(@RequestBody SysVersionControl entity,Principal principal){
            entity.setCreateBy(principal.getName());
            entity.setCreateTime(new Date());
        try {
            if(iSysVersionControlService.save(entity)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("保存版本控制表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }


   @SysLog(MODULE = "sys", REMARK = "更新版本控制表")
    @ApiOperation("更新版本控制表")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Object updateSysNotice(@RequestBody SysVersionControl entity,Principal principal) {
        entity.setUpdateBy(principal.getName());
        entity.setUpdateTime(new Date());
        try {
            if (iSysVersionControlService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新版本控制表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }




    @SysLog(MODULE = "sys", REMARK = "删除版本控制表")
    @ApiOperation("删除版本控制表")
    @GetMapping(value = "/delete/{id}")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBuildNotice(@ApiParam ("版本控制表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("版本控制表id");
            }
            if (iSysVersionControlService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除版本控制表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }




    @ApiOperation(value = "批量删除版本控制表")
    @PostMapping(value = "/delete/batch")
    @SysLog(MODULE = "sys", REMARK = "批量删除版本控制表")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = iSysVersionControlService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, SysVersionControl entity) {
        // 模拟从数据库获取需要导出的数据
        List<SysVersionControl> personList = iSysVersionControlService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出版本控制数据", "版本控制数据", SysVersionControl.class, "导出版本控制数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<SysVersionControl> personList = EasyPoiUtils.importExcel(file, SysVersionControl.class);
        iSysVersionControlService.saveBatch(personList);
    }



}
