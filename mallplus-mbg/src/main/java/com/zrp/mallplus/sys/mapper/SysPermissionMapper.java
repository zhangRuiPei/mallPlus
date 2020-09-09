package com.zrp.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sys.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台用户权限表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据userID查询菜单
     * @param id
     * @return
     */
    List<SysPermission> listMenuByUserId(Long id);

    @Select("SELECT  p.*  FROM sys_user_permission r " +
            " LEFT JOIN sys_permission p ON r.permission_id = p.id " +
            " WHERE r.admin_id = #{id} and p.status=1")
    List<SysPermission> getPermissionByUserId(Long id);
    List<SysPermission> getPermissionListByUserId(Long id);

    List<SysPermission> getPermissionList(Long roleId);

    /**
     * 根据userID查权限
     * @param id
     * @return
     */
    List<SysPermission> listUserPerms(Long id);
    //查询所有菜单权限
    @Select("SELECT icon,id,type,name,url,pid FROM sys_permission WHERE type in(0,1) ORDER BY sort ASC" )
    List<SysPermission> getAllPermissionLists();

    //查询角色接口权限
    @Select("SELECT p.icon,p.id,p.name,p.url,p.pid FROM sys_role_permission r LEFT JOIN sys_permission p ON r.permission_id = p.id " +
            " WHERE r.role_id = #{roleId} and p.status=1 and p.value is not null")
    List<SysPermission> getRoleInterface(Long roleId);


    //查询所有接口权限
//    @Select("SELECT * from sys_permission where value IS NOT NULL")
    @Select("SELECT * from sys_permission where status=1")
    List<SysPermission> getInterfacePermission();

}
