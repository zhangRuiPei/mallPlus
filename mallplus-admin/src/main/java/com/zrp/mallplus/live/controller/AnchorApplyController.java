package com.zrp.mallplus.live.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.live.entity.AnchorApply;
import com.zrp.mallplus.live.service.AliyunLiveservice;
import com.zrp.mallplus.live.service.AnchorApplyService;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.service.IUmsMemberService;
import com.zrp.mallplus.utils.CommonResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@RequestMapping("/live/apply")
public class AnchorApplyController {


    @Resource
    private AnchorApplyService anchorApplyService;
    @Resource
    private IUmsMemberService iUmsMemberService;
    @Resource
    private AliyunLiveservice aliyunLiveservice;



    @SysLog(MODULE = "live", REMARK = "主播审核列表")
    @ApiOperation("根据条件查询主播审核列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('live:Aliyun:read')")
    public Object getLiveByPage(AnchorApply entity,
                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        try {
                if (entity.getUsername()!=null&&entity.getStatus()!=null){

                    return new CommonResult().success(
                            anchorApplyService.page(new Page<AnchorApply>(pageNum, pageSize),
                                    new QueryWrapper<AnchorApply>().and(
                                            querywrapper -> querywrapper.like("username",entity.getUsername()).or().like("user_id_care",entity.getUsername())
                                    ).eq("status",entity.getStatus()).orderByAsc("create_time")));
                }else if (entity.getUsername()!=null){

                    return new CommonResult().success(
                            anchorApplyService.page(new Page<AnchorApply>(pageNum, pageSize),
                                    new QueryWrapper<AnchorApply>().and(
                                            querywrapper -> querywrapper.like("username",entity.getUsername()).or().like("user_id_care",entity.getUsername())
                                    ).orderByAsc("create_time")));
                }else if (entity.getStatus()!=null){
                    return new CommonResult().success(
                            anchorApplyService.page(new Page<AnchorApply>(pageNum, pageSize),
                                    new QueryWrapper<AnchorApply>().eq("status",entity.getStatus()).orderByAsc("create_time")));
                }else
                return new CommonResult().success(
                    anchorApplyService.page(new Page<AnchorApply>(pageNum, pageSize),
                            new QueryWrapper<AnchorApply>().orderByAsc("create_time")));

//            return new CommonResult().success(anchorApplyService.findAll(entity, pageNum, pageSize));
        } catch (Exception e) {
            log.error("根据条件查询所有会员表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "live", REMARK = "修改主播审核状态")
    @ApiOperation("修改主播审核状态")
    @PostMapping(value = "/update")
//    @PreAuthorize("hasAuthority('live:Aliyun:update')")
    @Transactional
    public Object updateUmsMember(@RequestBody AnchorApply entity) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            entity.setUpdateTime(df.format(new Date()));
            if (anchorApplyService.updateById(entity)) {
                if (entity.getStatus()==1){
                    UmsMember user = new UmsMember();
                    user.setId(entity.getUmsMemberId().longValue());
                    user.setIsAnchor(1);

                    Integer integer = aliyunLiveservice.addDefult(entity.getUmsMemberId().longValue());

                    if (integer==null){
                        return new CommonResult().failed();
                    }
                    user.setRoomNums(integer.toString());
                    iUmsMemberService.updateById(user);
                }

                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("修改主播审核状态：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

}
