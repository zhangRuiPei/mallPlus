//package com.util;
//
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
///**
// * @Author: 敲李奶奶
// * @Version: 1.0.0
// * @time Auto
// * @remark:
// */
//@Service
//@Component
//public class AliyunLiveConfig {
//
//    /**
//     * 推流域名
//     */
////    @Value("${aliyun.live.push.domain}")
//    private String aliyunLivePushDomain = "pushsdk.artsheep.cn";
//
//    /**
//     * 拉流域名
//     */
////    @Value("${aliyun.live.pull.domain}")
//    private String aliyunLivePullDomain = "livesdk.artsheep.cn";
//
//    /**
//     * 直播测试appName
//     */
//    private String aliyunLiveAppName;
//
//    /**
//     * 直播测试streamName
//     */
//    private String aliyunLiveStreamName;
//
//    /**
//     * 推流鉴权url key
//     */
////    @Value("${aliyun.live.push.ident.key}")
//    private String aliyunLivePushIdentKey = "9oe0IL5CrR";
//
//    /**
//     * 拉流鉴权url key
//     */
////    @Value("${aliyun.live.pull.ident.key}")
//    private String aliyunLivePullIdentKey = "LhtNvB9AMg";
//
//    /**
//     * 鉴权url的有效时间（秒），默认30分钟，1800秒 key
//     */
////    @Value("${aliyun.live.ident.url.validTime}")
//    private Integer aliyunLiveIdentUrlValidTime = 86400;
//
//
//    public String getAliyunLivePushDomain() {
//        return aliyunLivePushDomain;
//    }
//
//    public void setAliyunLivePushDomain(String aliyunLivePushDomain) {
//        this.aliyunLivePushDomain = aliyunLivePushDomain;
//    }
//
//    public String getAliyunLivePullDomain() {
//        return aliyunLivePullDomain;
//    }
//
//    public void setAliyunLivePullDomain(String aliyunLivePullDomain) {
//        this.aliyunLivePullDomain = aliyunLivePullDomain;
//    }
//
//    public String getAliyunLiveAppName() {
//        return aliyunLiveAppName;
//    }
//
//    public void setAliyunLiveAppName(String aliyunLiveAppName) {
//        this.aliyunLiveAppName = aliyunLiveAppName;
//    }
//
//    public String getAliyunLiveStreamName() {
//        return aliyunLiveStreamName;
//    }
//
//    public void setAliyunLiveStreamName(String aliyunLiveStreamName) {
//        this.aliyunLiveStreamName = aliyunLiveStreamName;
//    }
//
//    public String getAliyunLivePushIdentKey() {
//        return aliyunLivePushIdentKey;
//    }
//
//    public void setAliyunLivePushIdentKey(String aliyunLivePushIdentKey) {
//        this.aliyunLivePushIdentKey = aliyunLivePushIdentKey;
//    }
//
//    public String getAliyunLivePullIdentKey() {
//        return aliyunLivePullIdentKey;
//    }
//
//    public void setAliyunLivePullIdentKey(String aliyunLivePullIdentKey) {
//        this.aliyunLivePullIdentKey = aliyunLivePullIdentKey;
//    }
//
//    public Integer getAliyunLiveIdentUrlValidTime() {
//        return aliyunLiveIdentUrlValidTime;
//    }
//
//    public void setAliyunLiveIdentUrlValidTime(Integer aliyunLiveIdentUrlValidTime) {
//        this.aliyunLiveIdentUrlValidTime = aliyunLiveIdentUrlValidTime;
//    }
//}
