package com.zrp.mallplus.sys.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zrp.mallplus.sys.entity.SysUser;
import lombok.Data;

import java.util.List;

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
     * 单个用户ID
     */
    private Long userId;

    /**
     * 多个用户ID
     */
    private List<Long> userIds;

    /**
     * 单个角色
     */
    @TableField(exist = false)
    private Long roleId;
    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleName;

    /**
     *  多个角色
     */
    @TableField(exist = false)
    private String roleIds;

    /**
     * 确认密码
     */
    @TableField(exist = false)
    private String confirmPassword;

    /**
     * code
     */
    @TableField(exist = false)
    private String code;

    /**
     * 名称
     */
    @TableField(exist = false)
    private String name;
    /**
     * 旧密码
     */
    @TableField(exist = false)
    private String oldPassword;

    private Integer pageNum;
    private Integer pageSize;

}
