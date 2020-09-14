package com.zrp.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sys.entity.SysUser;
import com.zrp.mallplus.sys.entity.SysUserRole;
import com.zrp.mallplus.sys.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time 2020/9/9 10:27
 * @remark: 后台用户表 mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 根据用户名查询用户详细信息
     * 条件 : 用户门 参数 : username
     * @param username
     * @return
     */
    SysUserVo selectByUserName(String username);


    List<SysUserVo> findAll(SysUserVo entity);

    /**
     * 根据条件查询用户
     * 条件 : 店铺ID  参数 : storeId
     * 条件 : 角色ID 参数 : roleId
     * 条件 : 店铺名称/用户昵称 参数 : nickName
     * @param entity
     * @return
     */
    List<SysUserVo> getUserLists(@Param("record") SysUserVo entity);

    /**
     * 根据用户ID查询 用户角色表
     * @param userId 用户ID
     * @return
     */
    @Select("select id,role_id,admin_id from sys_user_role where admin_id=#{userId}")
    SysUserRole getUserRoleByUserId(@Param("userId") Long userId);
}
