package com.zrp.mallplus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zrp.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by EDZ on 2020/8/10.
 */
@Data
@TableName("sys_page_config")
public class SysPageConfig extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("url")
    private String url;

    @TableField("status")
    private Integer status;
}
