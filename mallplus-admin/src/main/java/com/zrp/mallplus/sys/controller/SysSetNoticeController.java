package com.zrp.mallplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sys.entity.SysSetNotice;
import com.zrp.mallplus.sys.service.ISysSetNoticeService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by EDZ on 2020/8/3.
 */

@Slf4j
@RestController
@Api(tags = "SysNoticeController", description = "公告")
@RequestMapping("/sys/notice")
public class SysSetNoticeController {

    @Resource
    private ISysSetNoticeService iSysSetNoticeService;



    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有公告表列表")
    @ApiOperation("根据条件查询所有公告表列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('sys:notice:read')")
    public Object getSysNoticeByPage(SysSetNotice entity,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        try {

            return  new CommonResult().success(iSysSetNoticeService.page(new Page<SysSetNotice>(pageNum,pageSize),new QueryWrapper(entity)));
        }catch (Exception e){
            log.error("根据条件查询所有公告表列表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }



    @SysLog(MODULE = "sys", REMARK = "保存公告表")
    @ApiOperation("保存公告表")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('sys:notice:create')")
    public  Object saveSysNotice(@RequestBody SysSetNotice entity){
        try {
            entity.setCtime(new Date());
            if(iSysSetNoticeService.save(entity)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("保存公告表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }


   @SysLog(MODULE = "sys", REMARK = "更新公告表")
    @ApiOperation("更新公告表")
    @PostMapping(value = "/update/{id}")
//    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Object updateSysNotice(@RequestBody SysSetNotice entity) {
        try {
            if (iSysSetNoticeService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新公告表：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }




    @SysLog(MODULE = "sys", REMARK = "删除公告表")
    @ApiOperation("删除公告表")
    @GetMapping(value = "/delete/{id}")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBuildNotice(@ApiParam ("公告表id") @PathVariable Integer id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("公告表id");
            }
            if (iSysSetNoticeService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除公告表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }




    @ApiOperation(value = "批量删除公告表")
    @PostMapping(value = "/delete/batch")
    @SysLog(MODULE = "sys", REMARK = "批量删除公告表")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = iSysSetNoticeService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, SysSetNotice entity) {
        // 模拟从数据库获取需要导出的数据
        List<SysSetNotice> personList = iSysSetNoticeService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出公告表数据", "公告表数据", SysSetNotice.class, "导出公告表数据.xls", response);

    }

    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<SysSetNotice> personList = EasyPoiUtils.importExcel(file, SysSetNotice.class);
        iSysSetNoticeService.saveBatch(personList);
    }

}
