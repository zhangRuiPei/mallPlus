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

    /**
     * 刷新Token
     * @param token 旧Token
     * @return
     */
    String refreshToken(String token);

    /**
     * 账号密码登录
     * @param username 账号
     * @param password 密码
     * @return
     */
    String login(String username, String password);

    /**
     * 给用户分配角色
     * @param adminId 用户ID
     * @param roleId 角色ID
     * @return
     */
    Boolean updateUserRole(Long adminId, Long roleId);

    List<SysRole> getRoleListByUserId(Long adminId);

    int updatePermissionByUserId(Long adminId, List<Long> permissionIds);

    List<SysPermission> getPermissionListByUserId(Long adminId);

    List<SysPermission> listMenuByUserId(Long adminId);

    /**
     * 新增后台用户
     * 参数 : username 用户门
     * 参数 : storeId 商铺ID
     * 参数 : password 密码(可为空)
     * 参数 : email 邮箱
     * 参数 : icon 头像URL
     * @param entity
     * @return
     */
    boolean saves(SysUserVo entity);

    /**
     * 修改店铺内用户角色
     * @param id 用户id
     * @param admin 用户角色
     * @return
     */
    boolean updates(Long id, SysUserVo admin);

    List<SysPermission> listUserPerms(Long id);

    void removePermissRedis(Long id);

    List<SysPermission> listPerms();

    Object reg(SysUserVo entity);

//    SmsCode generateCode(String phone);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    Object userCommunityRelate(UserCommunityRelate entity);

    void updatePassword(String password, String newPassword);

    /**
     * 根据条件查询用户
     * 条件 : 店铺ID  参数 : storeId
     * 条件 : 角色ID 参数 : roleId
     * 条件 : 店铺名称/用户昵称 参数 : nickName
     * @param entity
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map<String,Object> getUserList(SysUserVo entity, Integer pageNum, Integer pageSize);

    List<SysUserVo> findAll(SysUserVo entity);

    void saveSmsAndSendCode(String phone, String username, String password, String name);

    /**
     * 根据用户ID 删除对应用户 清楚角色信息
     * @param id 用户ID
     * @return
     */
    int removeById(Long id);
}
