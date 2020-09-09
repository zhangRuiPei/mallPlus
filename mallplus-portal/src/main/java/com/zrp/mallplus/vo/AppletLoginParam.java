package com.zrp.mallplus.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: shenzhuan
 * @Date: 2019/6/18 14:51
 * @Description:
 */
@Data
public class AppletLoginParam {

    //微信code
    private String code;

    //微信密文
    private String encrypted_data;
    //微信iv
    private String iv;

    private String signature;

    //微信用户信息
    private String userInfo;
    private String cloudID;

    //微信手机号码
    private String phone;

    //微信OPENID
    private String openid;

    //经度
    private String lat;

    //纬度
    private String lng;

    //地址
    private String address;

    //快递号
    private String expressNumber;
    //类型
    private String type;

    private Long userId;

    //头像
    private String icon;
    //昵称
    private String nickName;
    //性别
    private Integer gender;
    //生日
    private Date brithDay;
    //城市
    private String localCity;

    private String username;
}
