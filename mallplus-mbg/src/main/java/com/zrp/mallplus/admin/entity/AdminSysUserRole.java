package com.zrp.mallplus.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time 2020/9/9 10:39
 * @remark: 总后台 用户和角色关联表
 */
@Data
@TableName(value = "admin_sys_user_role")
public class AdminSysUserRole {

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 删除状态(0 删除 1在用)
     */
    @TableField(value = "del_status")
    private Integer delStatus;
}
