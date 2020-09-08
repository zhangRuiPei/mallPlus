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
 * Created by EDZ on 2020/8/1.
 *
 *          应用协议
 * @author   zrf
 */
@Data
@TableName("sys_agreement")
public class SysAgreement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;



    @TableField("id")
    private Integer id;

    /**
     *注册协议
     */
    @TableField("content")
    private String content;



}

