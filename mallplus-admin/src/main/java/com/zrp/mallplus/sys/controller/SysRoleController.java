package com.zrp.mallplus.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sys.entity.SysRole;
import com.zrp.mallplus.sys.entity.SysRolePermission;
import com.zrp.mallplus.sys.service.ISysRoleService;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台角色表 前端控制器
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Slf4j
@Api(value = "角色管理", description = "", tags = {"角色管理"})
@RestController
@RequestMapping("/sys/sysRole")
public class SysRoleController extends ApiController {

    @Resource
    private ISysRoleService sysRoleService;

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有角色列表")
    @ApiOperation("根据条件查询所有角色列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('sys:role:read')")
    public Object getRoleByPage(SysRole entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(sysRoleService.page(new Page<SysRole>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有角色列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }



    @ApiOperation(value = "批量删除角色")
    @PostMapping(value = "/delete/batch")
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除角色")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = sysRoleService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "sys" ,REMARK = "修改角色权限")//sys_role(后台用户角色表)
    @ApiOperation("修改角色权限")
    @PostMapping(value = "/updateRole")
//    @PreAuthorize("hasAuthority('sys:role:update')")
    public Object updateRole(@RequestParam("id") Long id,@RequestParam("menuIds") List<Long> menuIds){
        System.out.println("====================="+id+"================"+menuIds.toString());
        try {
            if(sysRoleService.updateSysRole(id,menuIds)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("修改角色权限：%s" ,e.getMessage(),e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();

    }

    @SysLog(MODULE = "sys" ,REMARK = "新增角色")
    @ApiOperation("新增角色")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sys:role:create')")
    public Object createRole(@RequestBody SysRole entity){
        try {
            if(sysRoleService.saves(entity)){
                return new CommonResult().success();
            }
        }catch (Exception e){
            log.error("新增角色:%s",e.getMessage(),e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }



/*    @SysLog(MODULE = "sys",REMARK = "查询所有权限")
    @ApiOperation("查询所有权限")
    @ResponseBody
    @GetMapping("/permissionList")
    public Object getPermission(){
        try {
            return new CommonResult().success(adminSysMenuMapper.getMenus());
        }catch (Exception e){
            log.error("",e.getMessage(),e);
        }
        return new CommonResult().failed();
    }*/


    @SysLog(MODULE = "sys", REMARK = "获取相应角色权限")
    @ApiOperation("获取相应角色权限")
    @RequestMapping(value = "/permission/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getPermissionList(@PathVariable Long roleId) {
        List<SysRolePermission> permissionLists = sysRoleService.getRolePermission(roleId);
        return new CommonResult().success(permissionLists);
    }

    @SysLog(MODULE = "sys", REMARK = "获取相应角色权限-单表")
    @ApiOperation("获取相应角色权限-单表")
    @RequestMapping(value = "/rolePermission/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Object rolePermission(@PathVariable Long roleId) {
        List<SysRolePermission> rolePermission = sysRoleService.getRolePermission(roleId);
        return new CommonResult().success(rolePermission);
    }

    @ApiOperation("修改展示状态")
    @RequestMapping(value = "/update/updateShowStatus")
    @ResponseBody
    @SysLog(MODULE = "cms", REMARK = "修改展示状态")
    public Object updateShowStatus(@RequestParam("ids") Long ids,
                                   @RequestParam("showStatus") Integer showStatus) {
        SysRole role = new SysRole();
        role.setId(ids);
        role.setStatus(showStatus);
        sysRoleService.updates(role);

        return new CommonResult().success();

    }
}

