package com.zrp.mallplus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sys.entity.SysPermission;
import com.zrp.mallplus.sys.entity.SysPermissionNode;
import com.zrp.mallplus.sys.service.ISysPermissionService;
import com.zrp.mallplus.ums.service.RedisService;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 后台用户权限表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "SysPermissionController", description = "后台用户权限表管理")
@RequestMapping("/sys/SysPermission")
public class SysPermissionController extends BaseController {
    @Resource
    private ISysPermissionService ISysPermissionService;

    @Resource
    private RedisService redisService;

    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有后台用户权限表列表")
    @ApiOperation("根据条件查询所有后台用户权限表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sys:SysPermission:read')")
    public Object getRoleByPage(SysPermission entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            if (ValidatorUtils.notEmpty(entity.getName())) {
                return new CommonResult().success(ISysPermissionService.list(new QueryWrapper<SysPermission>(new SysPermission()).like("name", entity.getName()).orderByAsc("sort")));
            }
            return new CommonResult().success(ISysPermissionService.list(new QueryWrapper<>(entity).orderByAsc("sort")));
        } catch (Exception e) {
            log.error("根据条件查询所有后台用户权限表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "保存后台用户权限表")
    @ApiOperation("保存后台用户权限表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sys:SysPermission:create')")
    public Object saveRole(@RequestBody SysPermission entity) {
        try {
            entity.setStatus(1);
            entity.setCreateTime(new Date());
            entity.setSort(0);
            if (ISysPermissionService.save(entity)) {
//                ISysPermissionService.addRedis();
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "更新后台用户权限表")
    @ApiOperation("更新后台用户权限表")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sys:SysPermission:update')")
    public Object updateRole(@RequestBody SysPermission entity) {
        try {
            if (ISysPermissionService.updateById(entity)) {
//                ISysPermissionService.addRedis();
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "sys", REMARK = "更新后台用户权限表")
    @ApiOperation("更新后台用户权限表")
    @PostMapping(value = "/update/showStatus")
    @PreAuthorize("hasAuthority('sys:SysPermission:update')")
    public Object updateRoleStatus(@RequestParam("ids") Long ids, @RequestParam("showStatus") Integer showStatus) {
        try {
            SysPermission entity = new SysPermission();
            entity.setId(ids);
            entity.setStatus(showStatus);
            /*entity.setUrl(url);
            entity.setName(name);*/
            if (ISysPermissionService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "删除后台用户权限表")
    @ApiOperation("删除后台用户权限表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sys:SysPermission:delete')")
    public Object deleteRole(@ApiParam("后台用户权限表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("后台用户权限表id");
            }
            if (ISysPermissionService.removeById(id)) {
//                ISysPermissionService.addRedis();
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除后台用户权限表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "给后台用户权限表分配后台用户权限表")
    @ApiOperation("查询后台用户权限表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sys:SysPermission:read')")
    public Object getRoleById(@ApiParam("后台用户权限表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("后台用户权限表id");
            }
            SysPermission coupon = ISysPermissionService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询后台用户权限表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
    }


    @ApiOperation(value = "批量删除后台用户权限表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除后台用户权限表")
    @PreAuthorize("hasAuthority('sys:SysPermission:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISysPermissionService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "sys", REMARK = "获取所有权限列表")
    @ApiOperation("获取所有权限列表")
    @RequestMapping(value = "/findPermissions", method = RequestMethod.GET)
    @ResponseBody
    public Object findPermissions(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        Long userId = getCurrentUser().getId();
        if (getCurrentUser().getIsAdmin() != null && getCurrentUser().getIsAdmin() == 1L) {
            return new CommonResult().success(ISysPermissionService.getAllPermission());
        }
        return new CommonResult().success(ISysPermissionService.getPermissionsByUserId(userId));
    }







    @SysLog(MODULE = "sys",REMARK = "层级结构返回所有接口权限")
    @ApiOperation("层级返回所有接口权限列表")
    @GetMapping(value = "/interfacePermis")
    @ResponseBody
    public Object allInterfacePermis(){

//        return new CommonResult().success(ISysPermissionService.getAllPermission());
        List<SysPermissionNode> permissionNodes = ISysPermissionService.getInterfacePermis();
        return new CommonResult().success(permissionNodes);
//        return new CommonResult().success(ISysPermissionService.getInterfacePermis());
    }


    /**roleId
     *         test测试
     * @param
     * @return
     */

    @SysLog(MODULE = "sys",REMARK = "测试")
    @ApiOperation("测试")
    @GetMapping(value = "/testPer")
    @ResponseBody
    public Object testPermission(){
        return new CommonResult().success(ISysPermissionService.testInterfacePermission());
    }


    @SysLog(MODULE = "sys",REMARK = "层级结构返回角色接口权限")
    @ApiOperation("层级结构返回角色接口权限")
    @GetMapping(value = "/roleInterface/{roleId}")
    @ResponseBody
    public Object roleInterfaceList(@ApiParam("role id") @PathVariable Long roleId){
        List<SysPermissionNode> permissionNodes = ISysPermissionService.getRoleInterface(roleId);

//        System.out.println("-------------------------"+permissionNodes.size());
//        return new CommonResult().success(ISysPermissionService.getRoleInterface(roleId));
        return new CommonResult().success(permissionNodes);
    }







    @SysLog(MODULE = "sys",REMARK = "层级结构返回角色菜单")
    @ApiOperation("层级返回角色菜单列表")
    @GetMapping(value = "/roleList/{roleId}")
    @ResponseBody
    public Object roleTreeList(@ApiParam("role id") @PathVariable Long roleId){
        List<SysPermissionNode> permissionNodes = ISysPermissionService.getPermissionByRole(roleId);
        return new CommonResult().success(permissionNodes);

    }




    @SysLog(MODULE = "sys", REMARK = "以层级结构返回所有菜单权限")
    @ApiOperation("以层级结构返回所有菜单权限")
    @GetMapping(value = "/treeList")
    @ResponseBody
    public Object treeList() {
        List<SysPermissionNode> permissionNodeList = ISysPermissionService.treeList();
        return new CommonResult().success(permissionNodeList);
    }

    @SysLog(MODULE = "sys", REMARK = "修改菜单权限，层级结构返回")
    @ApiOperation("修改菜单权限，层级结构返回")
    @PostMapping(value = "/updateMenus")
    @ResponseBody
    public Object updateMenu(@RequestBody SysPermission entity){


        return null;
    }


}
