package com.zrp.mallplus.sys.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zrp.mallplus.sys.entity.SysUser;
import lombok.Data;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
@Data
public class SysUserVo extends SysUser {

    /**
     * 单个角色
     */
    @TableField(exist = false)
    private Long roleId;
    @TableField(exist = false)
    private String roleName;

    /**
     *  多个角色
     */
    @TableField(exist = false)
    private String roleIds;

    @TableField(exist = false)
    private String confimpassword;

    @TableField(exist = false)
    private String code;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String oldPassword;

}
