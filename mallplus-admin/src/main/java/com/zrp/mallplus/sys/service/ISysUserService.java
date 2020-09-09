package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.build.entity.UserCommunityRelate;
import com.zrp.mallplus.sys.entity.SysPermission;
import com.zrp.mallplus.sys.entity.SysRole;
import com.zrp.mallplus.sys.entity.SysUser;
import com.zrp.mallplus.sys.vo.SysUserVo;

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

    boolean saves(SysUserVo entity);

    boolean updates(Long id, SysUserVo admin);

    List<SysPermission> listUserPerms(Long id);

    void removePermissRedis(Long id);

    List<SysPermission> listPerms();

    Object reg(SysUserVo entity);

//    SmsCode generateCode(String phone);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    Object userCommunityRelate(UserCommunityRelate entity);

    void updatePassword(String password, String newPassword);

    Map<String,Object> getUserList(SysUser entity, Integer pageNum, Integer pagesize);
    List<SysUser> findAll(SysUser entity);

    void saveSmsAndSendCode(String phone, String username, String password, String name);

    int removeById(Long id);
}
