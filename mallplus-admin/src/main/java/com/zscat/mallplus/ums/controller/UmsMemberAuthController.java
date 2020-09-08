package com.zscat.mallplus.ums.controller;

import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.ums.entity.UmsMemberAuth;
import com.zscat.mallplus.ums.service.IUmsMemberAuthService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by EDZ on 2020/8/12.
 */
@Slf4j
@RestController
@Api(tags = "UmsMemberAuthController", description = "会员身份验证")
@RequestMapping("/ums/UmsMemberAuth")
public class UmsMemberAuthController {
    @Resource
    private IUmsMemberAuthService iUmsMemberAuthService;

    @SysLog(MODULE = "ums", REMARK = "根据条件查询所有会员身份验证列表")
    @ApiOperation("根据条件查询所有会员身份验证列表")
    @GetMapping(value = "/list")
    public Object getListByPage(UmsMemberAuth entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        if(entity.getStoreId()!=null && entity.getStoreId()==1){
            entity.setStoreId(null);
        }
        return new CommonResult().success(iUmsMemberAuthService.getAuthList(entity,pageNum,pageSize));
    }


    @SysLog(MODULE = "sys", REMARK = "新增身份验证")
    @ApiOperation("新增身份验证")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('sys:notice:create')")
    public  Object saveAuth(@RequestBody UmsMemberAuth entity){
        entity.setCreateTime(new Date());
        try {
            if(iUmsMemberAuthService.save(entity)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("保存支付类型表: %s",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "ums", REMARK = "更新身份验证")
    @ApiOperation("更新身份验证")
    @PostMapping(value = "/update")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    public Object updateAuth(@RequestBody UmsMemberAuth entity) {
        entity.setUpdateTime(new Date());
        try {
            if (iUmsMemberAuthService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新身份验证：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    @ApiOperation(value = "批量删除身份验证")
    @PostMapping(value = "/delete/batch")
    @SysLog(MODULE = "sys", REMARK = "批量删除身份验证")
//    @PreAuthorize("hasAuthority('sys:notice:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = iUmsMemberAuthService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {

            return new CommonResult().failed();
        }
    }
}
