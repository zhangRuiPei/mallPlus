package com.zscat.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sys.entity.SysUser;
import com.zscat.mallplus.sys.entity.SysUserRole;
import com.zscat.mallplus.sys.entity.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUserVo selectByUserName(String username);


    List<SysUser> findAll(SysUser entity);
    List<SysUser> getUserLists(@Param("record") SysUser entity);

    @Select("select id,role_id,admin_id from sys_user_role where admin_id=#{userId}")
    SysUserRole getUserRoleByUserId(@Param("userId") Long userId);
}
