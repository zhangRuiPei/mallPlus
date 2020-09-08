package com.zscat.mallplus.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.build.entity.UserCommunityRelate;
import com.zscat.mallplus.sys.entity.SysPermission;
import com.zscat.mallplus.sys.entity.SysRole;
import com.zscat.mallplus.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface ISysUserService extends IService<SysUser> {

    String refreshToken(String token);

    String login(String username, String password);

    Boolean updateUserRole(Long adminId, Long roleId);

    List<SysRole> getRoleListByUserId(Long adminId);

    int updatePermissionByUserId(Long adminId, List<Long> permissionIds);

    List<SysPermission> getPermissionListByUserId(Long adminId);

    List<SysPermission> listMenuByUserId(Long adminId);

    boolean saves(SysUser entity);

    boolean updates(Long id, SysUser admin);

    List<SysPermission> listUserPerms(Long id);

    void removePermissRedis(Long id);

    List<SysPermission> listPerms();

    Object reg(SysUser entity);

//    SmsCode generateCode(String phone);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    Object userCommunityRelate(UserCommunityRelate entity);

    void updatePassword(String password, String newPassword);

    Map<String,Object> getUserList(SysUser entity, Integer pageNum, Integer pagesize);
    List<SysUser> findAll(SysUser entity);

    void saveSmsAndSendCode(String phone, String username, String password, String name);
    int removeById(Long id);
}
