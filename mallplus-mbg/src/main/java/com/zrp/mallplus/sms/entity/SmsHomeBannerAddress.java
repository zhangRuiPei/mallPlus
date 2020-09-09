package com.zrp.mallplus.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("sms_home_banner_address")
public class SmsHomeBannerAddress implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 轮播位置
     */
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


