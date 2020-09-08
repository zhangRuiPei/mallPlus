package com.zscat.mallplus.ums.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark: 积分消费设置
 */
@Data
public class SysSms implements Serializable {

    private static final long serialVersionUID = -4648169168575518593L;
    private Long id;
    private String phone;
    private String signName;
    private String templateCode;
    private String params;
    private String bizId;
    private String code;
    private String message;
    private Date day;
    private Date createTime;
    private Date updateTime;
}
