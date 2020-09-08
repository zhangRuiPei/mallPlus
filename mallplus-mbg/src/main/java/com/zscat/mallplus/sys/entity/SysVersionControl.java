package com.zscat.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by EDZ on 2020/8/4.
 */
@Data
@TableName("sys_version_control")
public class SysVersionControl extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    /**
     *版本号
     */
    @TableField("version_number")
    private  String versionNumber;

    /**
     *是否强更（0是；1否）
     */
    @TableField("is_update")
    private  Integer isUpdate;

    /**
     *
     */
    @TableField("create_time")
    private  Date createTime;

    /**
     *
     */
    @TableField("create_by")
    private  String createBy;

    /**
     *
     */
    @TableField("update_time")
    private Date updateTime;


    @TableField("update_by")
    private  String updateBy;

    @TableField("store_id")
    private Integer storeId;

    /**
     *描述
     */
    @TableField("version_desc")
    private String versionDesc;


}
