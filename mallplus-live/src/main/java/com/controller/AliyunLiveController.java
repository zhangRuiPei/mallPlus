package com.controller;


import com.model.AjaxResult;
import com.util.AliyunLiveUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/aliyunLive")
public class AliyunLiveController {




    /**
     * 播流
     */
    @PostMapping("addLive")
    @ResponseBody
    public AjaxResult addLive(String appName, String streamName) {
        String pushUrl = AliyunLiveUtil.createPushUrl(appName,streamName);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("pushUrl", pushUrl);
        return ajax;
    }



    /**
     * 推流
     */
    @GetMapping("getLive")
    @ResponseBody
    public AjaxResult getLive(String appName, String streamName) {
        Map<String, String> pullUrl = AliyunLiveUtil.createPullUrl(appName,streamName);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("pullUrl", pullUrl);
        return ajax;
    }







}
