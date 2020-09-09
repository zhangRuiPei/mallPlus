package com.zrp.mallplus.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 敲李奶奶
 * @Version: 1.0.0
 * @time 2020/9/9 9:50
 * @remark: 总后台 用户表
 */
@Data
@TableName("admin_sys_user")
public class AdminSysUser {


    /**
     * 用户ID
     */
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;

    /**
     * 部门ID
     */
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 登录账户
     */
    @TableField(value = "login_name")
    private String loginName;

    /**
     * 用户昵称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     *用户类型（00系统用户）
     */
    @TableField(value = "user_type")
    private String userType;

    /**
     * 用户邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 联系号码
     */
    @TableField(value = "phone_number")
    private String phoneNumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 头像路径
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 最后登陆IP
     */
    @TableField(value = "login_ip")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @TableField(value = "login_date")
    private Date loginDate;

    /**
     * 帐号状态（0正常 1停用）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField(value = "del_flag")
    private String delFlag;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 版本号
     */
    @TableField(value = "version")
    private Integer version;
}
