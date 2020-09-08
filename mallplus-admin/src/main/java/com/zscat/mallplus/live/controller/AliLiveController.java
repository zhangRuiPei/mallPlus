package com.zscat.mallplus.live.controller;



import com.util.AliyunLiveUtil;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.live.entity.AliyunLive;
import com.zscat.mallplus.live.service.AliyunLiveservice;
import com.zscat.mallplus.sys.entity.SysUser;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.util.UserUtils;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@RequestMapping("/aliyunLive")
public class AliLiveController {

    @Resource
    private AliyunLiveservice aliyunLiveservice;


    @Resource
    private IUmsMemberService umsMemberService;

    /**
     * 开播
     * @param appName
     * @param streamName
     * @param icon
     * @param roomName
     * @param roomTitle
     * @param request
     * @return
     */
    @PostMapping("startLive")
    @ResponseBody
    public Object startLive(String appName, String streamName, String icon, String roomName, String roomTitle, List<Long> ids, HttpServletRequest request){
        Random random = new Random();
        int roomNumber = random.nextInt(99999);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        String pushUrl = AliyunLiveUtil.createPushUrl(appName,streamName);
        Map<String, String> pullUrl = AliyunLiveUtil.createPullUrl( appName, streamName);
        String rtmpUrl = pullUrl.get("rtmpUrl");
        String flvUrl = pullUrl.get("flvUrl");
        String m3u8Url = pullUrl.get("m3u8Url");
        SysUser currentMember = UserUtils.getCurrentMember();
        Long id = currentMember.getId();
        AliyunLive aliyunLive = new AliyunLive();
        aliyunLive.setSteamName(streamName);
        aliyunLive.setAppName(appName);
        aliyunLive.setRtmpUrl(rtmpUrl);
        aliyunLive.setFlvUrl(flvUrl);
        aliyunLive.setM3u8Url(m3u8Url);
        aliyunLive.setAddLive(pushUrl);
        aliyunLive.setCreateTime(format);
        aliyunLive.setUserId(Math.toIntExact(id));
        aliyunLive.setIcon(icon);
        aliyunLive.setRoonName(roomName);
        aliyunLive.setRoomTitle(roomTitle);
        aliyunLive.setRoomNumber(roomNumber);
        aliyunLive.setLongs(ids);
        boolean save = aliyunLiveservice.save(aliyunLive);
        return new CommonResult().success(save);
    }






    @SysLog(MODULE = "live", REMARK = "直播间列表")
    @ApiOperation("根据条件查询所有直播间列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('live:Aliyun:read')")
    public Object getLiveByPage(AliyunLive entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        try {

//            return new CommonResult().success(aliyunLiveservice.page(new Page<AliyunLive>(pageNum, pageSize), new QueryWrapper<>(entity).orderByAsc("create_time")));
            return new CommonResult().success(aliyunLiveservice.findAll(entity, pageNum, pageSize));
        } catch (Exception e) {
            log.error("根据条件查询所有会员表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "live", REMARK = "保存直播间")
    @ApiOperation("保存直播间")
    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('live:Aliyun:create')")
    public Object saveLive(@RequestBody AliyunLive entity) {
        try {
            Integer sixRundom = aliyunLiveservice.getSixRundom();
            entity.setRoomNumber(sixRundom);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            entity.setCreateTime(df.format(new Date()));
            entity.setRoonStatus(0);
            entity.setIsShow(1);
            if (aliyunLiveservice.addLive(entity)==1) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存直播间：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "live", REMARK = "修改直播间")
    @ApiOperation("修改直播间")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('live:Aliyun:update')")
    @Transactional
    public Object updateUmsMember(@RequestBody AliyunLive entity) {
        try {
            if (entity.getRoomNumber()!=null) {
                if (umsMemberService.updateRoomNumById(entity)) {

                    if (aliyunLiveservice.updateById(entity)) {
                        return new CommonResult().success();
                    }
                }
            }
            if (aliyunLiveservice.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("修改直播间：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }
    

    @ApiOperation(value = "批量删除直播间")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "live", REMARK = "批量删除直播间")
//    @PreAuthorize("hasAuthority('ums:UmsMember:delete')")
    public Object deleteBatch( @RequestParam("ids") List<Long> ids) {
        boolean count = aliyunLiveservice.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }
}
