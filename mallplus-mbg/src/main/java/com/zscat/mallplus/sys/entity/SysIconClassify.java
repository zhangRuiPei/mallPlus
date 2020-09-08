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
@TableName("sys_icon_classify")
public class SysIconClassify extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type= IdType.AUTO)
    private  Long id;

    @TableField("name")
    private String name;

    @TableField("status")
    private Integer status;

    @TableField("store_id")
    private Integer storeId;

}
