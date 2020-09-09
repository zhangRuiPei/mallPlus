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
     * 根据用户查询用户
     * @param username
     * @return
     */
    SysUserVo selectByUserName(String username);


    List<SysUser> findAll(SysUser entity);
    List<SysUser> getUserLists(@Param("record") SysUser entity);

    @Select("select id,role_id,admin_id from sys_user_role where admin_id=#{userId}")
    SysUserRole getUserRoleByUserId(@Param("userId") Long userId);
}
