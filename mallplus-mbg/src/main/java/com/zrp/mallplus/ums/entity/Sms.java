package com.zrp.mallplus.ums.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Sms implements Serializable {

    /**
     *
     */
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
