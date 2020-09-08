package com.zscat.mallplus.sms.controller;

import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sms.entity.SmsHomeAdvertise;
import com.zscat.mallplus.sms.service.ISmsHomeAdvertiseService;
import com.zscat.mallplus.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 轮播图列表
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@RequestMapping("/sms/SmsAdvertise")
public class SmsHomeAdvertiseController {

    @Resource
    private ISmsHomeAdvertiseService smsHomeAdvertiseService;

    @SysLog(MODULE = "sms", REMARK = "保存轮播图")
    @ResponseBody
    @PostMapping("/addBanner")
    public Object addBanner(@RequestBody SmsHomeAdvertise entity){
        try {

            entity.setStartTime(new Date());
            entity.setEndTime(new Date());
            //保存数据
            if (smsHomeAdvertiseService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存后轮播图：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "sms", REMARK = "批量删除轮播图")
    @ResponseBody
    @RequestMapping(value = "/deletebatch", method = RequestMethod.POST)
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {

        if (ids.size()==0){
            return new CommonResult().failed(500,"请选中轮播图再删除！！！");
        }

        boolean count = smsHomeAdvertiseService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "sms", REMARK = "所有轮播图")
    @ResponseBody
    @RequestMapping(value = "/getbannerList", method = RequestMethod.GET)
    public Object BannerList(SmsHomeAdvertise entity,
                                    @RequestParam(value = "pageNum") Integer pageNum,
                                    @RequestParam(value = "pageSize") Integer pageSize) {
        try {
//            return  new CommonResult().success(smsHomeAdvertiseService.page(new Page<SmsHomeAdvertise>(pageNum,pageSize),smsHomeAdvertiseService.findAll(entity)));

            Map<String, Object> all = smsHomeAdvertiseService.findAll(entity, pageNum, pageSize);


            return  new CommonResult().success(all);
        } catch (Exception e) {
            log.error("根据条件查询所有轮播图列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();

    }

    @SysLog(MODULE = "sms", REMARK = "修改轮播图地址")
    @ResponseBody
    @PostMapping("/updateBanner")
    public Object updateBanner(@RequestBody SmsHomeAdvertise entity){
        try {

            if (smsHomeAdvertiseService.getById(entity.getId())==null){
                return new CommonResult().failed(500,"当前id暂无商品,请重试!");

            }
            //保存数据
            if (smsHomeAdvertiseService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存后轮播图地址：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

}