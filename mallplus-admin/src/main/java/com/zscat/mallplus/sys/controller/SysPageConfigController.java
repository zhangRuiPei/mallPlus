package com.zscat.mallplus.sys.controller;

import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sys.entity.SysPageConfig;
import com.zscat.mallplus.sys.service.ISysPageConfigService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "SysPageConfigController", description = "页面配置")
@RequestMapping("/sys/pageConfig")
public class SysPageConfigController {
    @Resource
    private ISysPageConfigService iSysPageConfigService;



    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有页面配置")
    @ApiOperation("根据条件查询所有页面配置")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('sys:notice:read')")
    public Object getSysPageConfig(SysPageConfig sysPageConfig,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        try {
            return new CommonResult().success(iSysPageConfigService.getPageConfigList());
//            return new CommonResult().success(iSysPageConfigService.page(new Page<SysPageConfig>(pageNum, pageSize), new QueryWrapper<>(sysPageConfig)));

        }catch (Exception e){
            log.error("根据条件查询所有页面配置: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }



    @SysLog(MODULE = "sys", REMARK = "更新页面配置")
    @ApiOperation("更新页面配置")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('sys:notice:update')")
    public Object updateSysPage(@RequestBody SysPageConfig sysPageConfig) {
        try {
            if(sysPageConfig.getId()==1){
                SysPageConfig spc1 = new SysPageConfig();
                SysPageConfig spc2 = new SysPageConfig();
                spc1.setId(2L);
                spc1.setStatus(0);
                iSysPageConfigService.updateById(spc1);
                spc2.setId(3L);
                spc2.setStatus(0);
                iSysPageConfigService.updateById(spc2);
            }else if(sysPageConfig.getId()==2){
                SysPageConfig spc1 = new SysPageConfig();
                SysPageConfig spc2 = new SysPageConfig();
                spc1.setId(1L);
                spc1.setStatus(0);
                iSysPageConfigService.updateById(spc1);
                spc2.setId(3L);
                spc2.setStatus(0);
                iSysPageConfigService.updateById(spc2);
            }else{
                SysPageConfig spc1 = new SysPageConfig();
                SysPageConfig spc2 = new SysPageConfig();
                spc1.setId(1L);
                spc1.setStatus(0);
                iSysPageConfigService.updateById(spc1);
                spc2.setId(2L);
                spc2.setStatus(0);
                iSysPageConfigService.updateById(spc2);
            }
            sysPageConfig.setStatus(1);
            if (iSysPageConfigService.updateById(sysPageConfig)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("根据条件查询所有页面配置：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }


}
