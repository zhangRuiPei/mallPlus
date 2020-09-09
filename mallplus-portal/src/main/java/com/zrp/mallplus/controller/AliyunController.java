package com.zrp.mallplus.controller;

import com.zrp.mallplus.live.entity.AliyunLive;
import com.zrp.mallplus.live.entity.AnchorApply;
import com.zrp.mallplus.service.AliyunService;
import com.zrp.mallplus.service.AnchorApplyService;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.service.IUmsMemberService;
import com.zrp.mallplus.ums.service.RedisService;
import com.zrp.mallplus.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@RestController
@CrossOrigin
@RequestMapping("/api/aliyun")
public class AliyunController  {

    @Autowired
    private IUmsMemberService memberService;
    @Autowired
    private AliyunService aliyunService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AnchorApplyService anchorApplyService;

    /**
     * 直播列表
     * @param aliyunLive
     * @return
     */
    @PostMapping(value = "/AliyunList")
    @ResponseBody
    public Object AliyunList(@RequestBody AliyunLive aliyunLive){
        UmsMember member = memberService.getNewCurrentMember();
        List<AliyunLive> list =null;
        list = aliyunService.findList(aliyunLive);
        return new CommonResult().success(list);
    }



    /**
     * 申请直播入住
     * @param anchorApply
     * @return
     */
    @PostMapping(value = "/applyAnchor")
    @ResponseBody
    public Object applyAnchor(@RequestBody AnchorApply anchorApply){
        UmsMember member = memberService.getNewCurrentMember();
        if (member ==null){
            throw  new  IllegalArgumentException("失败");
        }
        anchorApply.setCreateTime(new Date());
        anchorApply.setUmsMemberId(Math.toIntExact(member.getId()));
        boolean save = anchorApplyService.saveOrUpdate(anchorApply);
        return new CommonResult().success(save);

    }





}
