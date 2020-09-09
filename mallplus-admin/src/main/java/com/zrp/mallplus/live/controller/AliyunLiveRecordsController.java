package com.zrp.mallplus.live.controller;

import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.live.entity.AliyunLiveRecords;
import com.zrp.mallplus.live.service.AliyunLiveRecordsService;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@RequestMapping("/liverecords")
public class AliyunLiveRecordsController {


    @Resource
    private AliyunLiveRecordsService aliyunLiveRecordsService;


    @SysLog(MODULE = "live", REMARK = "直播间列表")
    @ApiOperation("根据条件查询所有直播间列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('live:Aliyun:read')")
    public Object getLiveByPage(AliyunLiveRecords entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        try {

//            return new CommonResult().success(aliyunLiveservice.page(new Page<AliyunLive>(pageNum, pageSize), new QueryWrapper<>(entity).orderByAsc("create_time")));
            return new CommonResult().success(
                                        aliyunLiveRecordsService.findAll(entity, pageNum, pageSize));
        } catch (Exception e) {
            log.error("根据条件查询所有会员表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "live", REMARK = "修改直播间")
    @ApiOperation("修改直播间")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('live:Aliyun:update')")
    @Transactional
    public Object updateUmsMember(@RequestBody AliyunLiveRecords entity) {
        try {
            if (aliyunLiveRecordsService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("修改直播间：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


}
