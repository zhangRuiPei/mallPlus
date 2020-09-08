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
 * Created by EDZ on 2020/8/3.
 *
 * 系统设置--公告表
 *
 * @author zrf
 */
@TableName("sys_notice")
@Data
public class SysSetNotice  extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    /**
     *公告标题
     */
    @TableField("title")
    private String title;

    /**
     *公告内容
     */
    @TableField("content")
    private String content;

    /**
     *公告类型
     */
    @TableField("type")
    private Integer type;

    /**
     *创建时间
     */
    @TableField("ctime")
    private Date ctime;

    /**
     *排序
     */
    @TableField("sort")
    private Integer sort;


    /**
     *软删除位  有时间代表已删除
     */
    @TableField("isdel")
    private Integer isdel;

    /**
     *所属店铺
     */
    @TableField("store_id")
    private Integer store_id;
}
