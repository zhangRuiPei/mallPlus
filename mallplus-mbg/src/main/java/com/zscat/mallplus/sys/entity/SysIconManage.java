package com.zscat.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by EDZ on 2020/8/5.
 */
@Data
@TableName("sys_icon_manage")
public class SysIconManage extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    /**
     * ICON分类
     */
    @TableField("type_id")
    private Integer typeId;

    /**
     * ICON分类
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 图片
     */
    @TableField("picture")
    private String picture;

    /**
     * 公告
     */
    @TableField("notice")
    private String notice;

    /**
     * 小程序跳转路径
     */
    @TableField("small_program")
    private String smallProgram;

    /**
     * 安卓跳转路径
     */
    @TableField("android_path")
    private String androidPath;

    /**
     * ISO跳转路径
     */
    @TableField("ios_path")
    private String iosPath;

    @TableField("store_id")
    private Integer storeId;


}
