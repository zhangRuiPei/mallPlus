package com.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AliyunLiveUtil {
    private static final Logger log = LoggerFactory.getLogger(AliyunLiveUtil.class);


    /**
     * 推流域名
     */
//    @Value("${aliyun.live.push.domain}")
    private static String aliyunLivePushDomain = "pushsdk.artsheep.cn";

    /**
     * 拉流域名
     */
//    @Value("${aliyun.live.pull.domain}")
    private static String aliyunLivePullDomain = "livesdk.artsheep.cn";

    /**
     * 直播测试appName
     */
    private static String aliyunLiveAppName;

    /**
     * 直播测试streamName
     */
    private static String aliyunLiveStreamName;

    /**
     * 推流鉴权url key
     */
//    @Value("${aliyun.live.push.ident.key}")
    private static String aliyunLivePushIdentKey = "9oe0IL5CrR";

    /**
     * 拉流鉴权url key
     */
//    @Value("${aliyun.live.pull.ident.key}")
    private static String aliyunLivePullIdentKey = "LhtNvB9AMg";

    /**
     * 鉴权url的有效时间（秒），默认30分钟，1800秒 key
     */
//    @Value("${aliyun.live.ident.url.validTime}")
    private static Integer aliyunLiveIdentUrlValidTime = 86400;


    /**
     * 根据源id创建该id的推流url
     *
     * @param
     * @param
     * @return
     */
    public static String createPushUrl(String appName,String streamName) {

        // 计算过期时间
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000) + aliyunLiveIdentUrlValidTime);

        // 组合推流域名前缀
//      rtmp://{pushDomain}/{appName}/{streamName}
        String rtmpUrl = StrUtil.format("rtmp://{}/{}/{}", aliyunLivePushDomain, appName, streamName);
        log.debug("推流域名前缀，rtmpUrl=" + rtmpUrl);

        // 组合md5加密串
//      /{appName}/{streamName}-{timestamp}-0-0-{pushIdentKey}
        String md5Url = StrUtil.format("/{}/{}-{}-0-0-{}", appName, streamName, timestamp, aliyunLivePushIdentKey);

        // md5加密
        String md5Str = DigestUtil.md5Hex(md5Url);
        log.debug("md5加密串，md5Url=" + md5Url + "------md5加密结果，md5Str=" + md5Str);

        // 组合最终鉴权过的推流域名
//      {rtmpUrl}?auth_key={timestamp}-0-0-{md5Str}
        String finallyPushUrl = StrUtil.format("{}?auth_key={}-0-0-{}", rtmpUrl, timestamp, md5Str);
        log.debug("最终鉴权过的推流域名=" + finallyPushUrl);

        return finallyPushUrl;
    }

    /**
     * 创建拉流域名，key=rtmpUrl、flvUrl、m3u8Url，代表三种拉流类型域名
     *
     * @param
     * @param
     * @return
     */
    public static Map<String, String> createPullUrl(String appName,String streamName) {


        // 计算过期时间
        String timestamp = String.valueOf((System.currentTimeMillis() / 1000) + aliyunLiveIdentUrlValidTime);

        // 组合通用域名
//      {pullDomain}/{appName}/{streamName}
        String pullUrl = StrUtil.format("{}/{}/{}", aliyunLivePullDomain, appName, streamName);
        log.debug("组合通用域名，pullUrl=" + pullUrl);

        // 组合md5加密串
//      /{appName}/{streamName}-{timestamp}-0-0-{pullIdentKey}
        String md5Url = StrUtil.format("/{}/{}-{}-0-0-{}", appName, streamName, timestamp, aliyunLivePullIdentKey);
        String md5FlvUrl = StrUtil.format("/{}/{}.flv-{}-0-0-{}", appName, streamName, timestamp, aliyunLivePullIdentKey);
        String md5M3u8Url = StrUtil.format("/{}/{}.m3u8-{}-0-0-{}", appName, streamName, timestamp, aliyunLivePullIdentKey);

        // md5加密
        String md5Str = DigestUtil.md5Hex(md5Url);
        String md5FlvStr = DigestUtil.md5Hex(md5FlvUrl);
        String md5M3u8Str = DigestUtil.md5Hex(md5M3u8Url);
        log.debug("md5加密串，md5Url    =" + md5Url + "       ------     md5加密结果，md5Str=" + md5Str);
        log.debug("md5加密串，md5FlvUrl =" + md5FlvUrl + "    ------    md5加密结果，md5FlvStr=" + md5FlvStr);
        log.debug("md5加密串，md5M3u8Url=" + md5M3u8Url + "   ------    md5加密结果，md5M3u8Str=" + md5M3u8Str);

        // 组合三种拉流域名前缀
//        rtmp://{pullUrl}?auth_key={timestamp}-0-0-{md5Str}
        String rtmpUrl = StrUtil.format("rtmp://{}?auth_key={}-0-0-{}", pullUrl, timestamp, md5Str);
//        http://{pullUrl}.flv?auth_key={timestamp}-0-0-{md5FlvStr}
        String flvUrl = StrUtil.format("http://{}.flv?auth_key={}-0-0-{}", pullUrl, timestamp, md5FlvStr);
//        http://{pullUrl}.m3u8?auth_key={timestamp}-0-0-{md5M3u8Str}
        String m3u8Url = StrUtil.format("http://{}.m3u8?auth_key={}-0-0-{}", pullUrl, timestamp, md5M3u8Str);

        log.debug("最终鉴权过的拉流rtmp域名=" + rtmpUrl);
        log.debug("最终鉴权过的拉流flv域名 =" + flvUrl);
        log.debug("最终鉴权过的拉流m3u8域名=" + m3u8Url);

        HashMap<String, String> urlMap = new HashMap<>();
        urlMap.put("rtmpUrl", rtmpUrl);
        urlMap.put("flvUrl", flvUrl);
        urlMap.put("m3u8Url", m3u8Url);

        return urlMap;
    }
}
