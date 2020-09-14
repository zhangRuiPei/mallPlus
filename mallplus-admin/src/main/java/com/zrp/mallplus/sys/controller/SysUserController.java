package com.zrp.mallplus.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.build.mapper.UserCommunityRelateMapper;
import com.zrp.mallplus.sys.entity.SysPermission;
import com.zrp.mallplus.sys.entity.SysRole;
import com.zrp.mallplus.sys.entity.SysUser;
import com.zrp.mallplus.sys.mapper.SysPermissionMapper;
import com.zrp.mallplus.sys.mapper.SysUserMapper;
import com.zrp.mallplus.sys.service.ISysPermissionService;
import com.zrp.mallplus.sys.service.ISysRoleService;
import com.zrp.mallplus.sys.service.ISysUserService;
import com.zrp.mallplus.sys.vo.SysUserVo;
import com.zrp.mallplus.ums.service.RedisService;
import com.zrp.mallplus.util.JsonUtil;
import com.zrp.mallplus.util.UserUtils;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import com.zrp.mallplus.vo.Rediskey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time 2020/9/9 10:27
 * @remark: 后台用户表
 */
@Slf4j
@Api(value = "用户管理", description = "", tags = {"用户管理"})
@RestController
@RequestMapping("/sys/sysUser")
public class SysUserController extends ApiController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysPermissionService permissionService;
    @Resource
    private SysPermissionMapper permissionMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private UserCommunityRelateMapper userCommunityRelateMapper;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private SysUserMapper sysUserMapper;


    /**
     * 条件查询所有用户
     * 条件 : 店铺ID  参数 : storeId
     * 条件 : 角色ID 参数 : roleId
     * 条件 : 店铺名称/用户昵称 参数 : nickName
     * @param entity
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "根据条件查询所有用户列表")
    @ApiOperation("根据条件查询所有用户列表")
    @GetMapping(value = "/list")
    @Transactional
    @PreAuthorize("hasAuthority('sys:sysUser:getListByPage')")
    public Object getListByPage(@RequestBody(required = false) SysUserVo entity) {
        if(entity.getStoreId()!=null && entity.getStoreId()!=null){
            entity.setStoreId(null);
        }
        return new CommonResult().success(sysUserService.getUserList(entity,entity.getPageNum(),entity.getPageSize()));
    }

    /**
     * 给新用户开通账号
     * 参数 : username 用户门
     * 参数 : storeId 商铺ID
     * 参数 : password 密码(可为空)
     * 参数 : email 邮箱
     * 参数 : icon 头像URL
     * @param entity 用户实体类
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "新用户开通账号")
    @ApiOperation("新用户开通账号")
    @PostMapping(value = "/register")
    @Transactional
    @PreAuthorize("hasAuthority('sys:sysUser:saveUser')")
    public Object saveUser(@RequestBody(required = false) SysUserVo entity) {
        try {
            if (ValidatorUtils.empty(entity.getStoreId())){
                entity.setStoreId(UserUtils.getCurrentMember().getStoreId());
            }
            if (sysUserService.saves(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存用户：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    /**
     * 根据ID 更新用户角色信息
     * @param entity
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "更新商铺内用户角色")
    @ApiOperation("更新商铺内用户角色")
    @PostMapping(value = "/update")
    @Transactional
    @PreAuthorize("hasAuthority('sys:sysUser:updateUser')")
    public Object updateUser(@RequestBody(required = false) SysUserVo entity) {
        try {
            if (sysUserService.updates(entity.getId(), entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新用户：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    /**
     * 修改用户密码
     * @param entity
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "修改用户密码")
    @ApiOperation("修改用户密码")
    @PostMapping(value = "/updateUser")
    @Transactional
    @PreAuthorize("hasAuthority('sys:sysUser:updateSysUserPassword')")
    public Object updateSysUser(@RequestBody(required = false) SysUserVo entity) {

        if(entity.getPassword()!=null && !"".equals(entity.getPassword())){
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        try {
            if (sysUserService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新用户：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    /**
     * 删除用户
     * @param sysUserVo 用户ID : sysUserVo.getId
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "删除用户")
    @ApiOperation("删除用户")
    @PostMapping(value = "/delete")
    @PreAuthorize("hasAuthority('sys:sysUser:deleteUser')")
    public Object deleteUser(@RequestBody(required = false) SysUserVo sysUserVo) {
        try {
            if (ValidatorUtils.empty(sysUserVo.getId())) {
                return new CommonResult().paramFailed("用户id");
            }
             SysUser user = sysUserService.getById(sysUserVo.getId());
           if (user.getIsAdmin() != null && user.getIsAdmin() == 1) {
                return new CommonResult().paramFailed("管理员账号不能删除");
            }
            int count = sysUserService.removeById(sysUserVo.getId());
            if (count>0) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除用户：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    /**
     * 批量删除用户
     * @param sysUserVo 参数 : sysUserVo.getUserIds
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "批量删除用户")
    @ApiOperation("批量删除用户")
    @PostMapping(value = "/delete/batch")
    @Transactional
    @PreAuthorize("hasAuthority('sys:sysUser:deleteUserBatch')")
    public Object deleteUserBatch(@RequestBody(required = false) SysUserVo sysUserVo) {
        try {
            for(Long id:sysUserVo.getUserIds()){
                SysUser user = sysUserService.getById(id);
                if (user.getIsAdmin() != null && user.getIsAdmin() == 1) {
                    return new CommonResult().paramFailed("管理员账号不能删除");
                }
                sysUserService.removeById(id);
            }
            return new CommonResult().success();
        } catch (Exception e) {
            log.error("删除用户：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
    }


    /**
     * 根据用户ID 查询用户详细信息
     * @param entity 参数 : SysUserVo.getId
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "查询用户明细")
    @ApiOperation("查询用户明细")
    @PostMapping(value = "getUserById")
    @PreAuthorize("hasAuthority('sys:sysUser:getUserById')")
    public Object getUserById(@RequestBody(required = false) SysUserVo entity) {
        try {
            if (ValidatorUtils.empty(entity.getId())) {
                return new CommonResult().paramFailed("用户id");
            }
            SysUser coupon = sysUserService.getById(entity.getId());
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询用户明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    /**
     * 刷新Token
     * @param request
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "刷新token")
    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/token/refresh")
    @ResponseBody
    public Object refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = sysUserService.refreshToken(token);
        if (refreshToken == null) {
            return new CommonResult().failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new CommonResult().success(tokenMap);
    }

    /**
     * 用户 账号密码登录 修改最后登陆时间
     * @param sysUserVo
     * @return token
     */
    @SysLog(MODULE = "sys", REMARK = "登录以后返回token")
    @ApiOperation(value = "登录以后返回token")
    @PostMapping(value = "/login")
    @ResponseBody
    public Object login1 (@RequestBody(required = false) SysUserVo sysUserVo) {
        try {
            String token = sysUserService.login(sysUserVo.getUsername(), sysUserVo.getPassword());
            if (token == null) {
                return new CommonResult().paramFailed("用户名或密码错误");
            }
            SysUser queryU = new SysUser();
            queryU.setUsername(sysUserVo.getUsername());
            SysUser umsAdmin = sysUserService.getOne(new QueryWrapper<>(queryU));
            umsAdmin.setLoginTime(new Date());
            sysUserService.updateById(umsAdmin);
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            tokenMap.put("userInfo", UserUtils.getCurrentMember());
            return new CommonResult().success(tokenMap);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
    }

    @SysLog(MODULE = "sys", REMARK = "获取当前登录用户信息")
    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/info")
    @ResponseBody
    public Object getAdminInfo(Principal principal) {
        String username = principal.getName();
        SysUser queryU = new SysUser();
        queryU.setUsername(username);
        SysUser umsAdmin = sysUserService.getOne(new QueryWrapper<>(queryU));
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("roles", new String[]{"TEST"});
        if (umsAdmin != null) {
            data.put("sysUser", umsAdmin);
            data.put("icon", umsAdmin.getIcon());
            data.put("userId", umsAdmin.getId());
            data.put("storeId", umsAdmin.getStoreId());
        }
        return new CommonResult().success(data);
    }

    @SysLog(MODULE = "sys", REMARK = "登出功能")
    @ApiOperation(value = "登出功能")
    @GetMapping(value = "/logout")
    @ResponseBody
    public Object logout() {
        return new CommonResult().success(null);
    }

    /**
     * 给用户分配角色
     * @param sysUserVo 参数 : adminId 用户ID
     * @param sysUserVo 参数 : roleId 角色ID
     * @return
     */
    @SysLog(MODULE = "sys", REMARK = "给用户分配角色")
    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/role/update")
    @ResponseBody
    public Object updateRole(@RequestBody(required = false) SysUserVo sysUserVo) {
        Boolean flag = sysUserService.updateUserRole(sysUserVo.getUserId(), sysUserVo.getRoleId());
        if (flag) {
            //更新，删除时候，如果redis里有权限列表，重置
            if (!redisService.exists(String.format(Rediskey.menuList, sysUserVo.getUserId()))) {
                List<SysPermission> list = permissionMapper.listUserPerms(sysUserVo.getUserId());
                String key = String.format(Rediskey.menuList, sysUserVo.getUserId());
                redisService.set(key, JsonUtil.objectToJson(list));
                return list;
            }
            return new CommonResult().success();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "获取指定用户的角色")
    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getRoleList(@PathVariable Long adminId) {
        List<SysRole> roleList = sysUserService.getRoleListByUserId(adminId);
        return new CommonResult().success(roleList);
    }

    @SysLog(MODULE = "sys", REMARK = "获取指定用户的角色")
    @ApiOperation("获取指定用户的角色")
    @RequestMapping(value = "/userRoleCheck", method = RequestMethod.GET)
    @ResponseBody
    public Object userRoleCheck(@RequestParam("adminId") Long adminId) {
        List<SysRole> roleList = sysUserService.getRoleListByUserId(adminId);
        List<SysRole> allroleList = roleService.list(new QueryWrapper<>());
        if (roleList != null && roleList.size() > 0) {
            for (SysRole a : allroleList) {
                for (SysRole u : roleList) {
                    if (u != null && u.getId() != null) {
                        if (a.getId().equals(u.getId())) {
                            a.setChecked(true);
                        }
                    }
                }
            }
            return new CommonResult().success(allroleList);
        }
        return new CommonResult().success(allroleList);
    }

    @SysLog(MODULE = "sys", REMARK = "给用户分配+-权限")
    @ApiOperation("给用户分配+-权限")
    @PostMapping(value = "/permission/update")
    @ResponseBody
    public Object updatePermission(@RequestParam("userId") Long userId,
                                   @RequestParam("permissionIds") List<Long> permissionIds) {
        int count = sysUserService.updatePermissionByUserId(userId, permissionIds);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sys", REMARK = "获取用户所有权限（包括+-权限）")
    @ApiOperation("获取用户所有权限（包括+-权限）")
    @GetMapping(value = "/permission/{adminId}")
    @ResponseBody
    public Object getPermissionList(@PathVariable Long adminId) {
        List<SysPermission> permissionList = sysUserService.getPermissionListByUserId(adminId);
        return new CommonResult().success(permissionList);
    }

    @ApiOperation("修改展示状态")
    @RequestMapping(value = "/update/updateShowStatus")
    @ResponseBody
    @SysLog(MODULE = "sys", REMARK = "修改展示状态")
    public Object updateShowStatus(@RequestParam("ids") Long ids,
                                   @RequestParam("showStatus") Integer showStatus) {
        SysUser role = new SysUser();
        role.setId(ids);
        role.setStatus(showStatus);
        sysUserService.updateById(role);

        return new CommonResult().success();

    }

    @ApiOperation("修改密码")
    @GetMapping(value = "/updatePassword")
    @ResponseBody
    @SysLog(MODULE = "sys", REMARK = "修改密码")
    public Object updatePassword(@RequestParam("password") String password,
                                 @RequestParam("renewPassword") String renewPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (ValidatorUtils.empty(password)) {
            return new CommonResult().failed("参数为空");
        }
        if (ValidatorUtils.empty(renewPassword)) {
            return new CommonResult().failed("参数为空");
        }
        if (ValidatorUtils.empty(newPassword)) {
            return new CommonResult().failed("参数为空");
        }
        if (!renewPassword.equals(newPassword)) {
            return new CommonResult().failed("新密码不一致!");
        }
        try {
            sysUserService.updatePassword(password, newPassword);
        } catch (Exception e) {
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().success();

    }




}

