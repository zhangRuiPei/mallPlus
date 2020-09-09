package com.zrp.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by EDZ on 2020/8/6.
 * @author   zrf
 * 公告（页面配置）
 *
 */
@TableName("sys_pagecfg_notice")
@Data
public class SysPageCfgNotic extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    private String name;
    private String picture;
    private String content;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    /**
     * 小程序跳转路径
     */
    @TableField("url_wechat")
    private String urlWechat;

    /**
     * 安卓跳转路径
     */
    @TableField("url_android")
    private String urlAndroid;

    /**
     * iOS跳转路径
     */
    @TableField("url_ios")
    private String urlIos;

    @TableField("store_id")
    private Integer storeId;
}
