package com.zrp.mallplus.sms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.sms.entity.SmsHomeBannerAddress;
import com.zrp.mallplus.sms.service.ISmsHomeBannerAddressService;
import com.zrp.mallplus.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 轮播图地址管理
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@RequestMapping("sms/BannerAddress")
public class SmsHomeBannerAddressController {

    @Resource
    private ISmsHomeBannerAddressService ISmsHomeBannerAddressService;


    @SysLog(MODULE = "sms", REMARK = "保存轮播图地址")
    @ResponseBody
    @PostMapping("/saveBannerAddress")
    public Object saveBannerAddress(@RequestBody SmsHomeBannerAddress smsHomeBannerAddress){
        try {
            SmsHomeBannerAddress address = ISmsHomeBannerAddressService.getOne(new QueryWrapper<SmsHomeBannerAddress>().eq("address", smsHomeBannerAddress.getAddress()));
            if (address!=null){
                return new CommonResult().failed(500,"当前位置已存在,请重试");
            }
            if (ISmsHomeBannerAddressService.addSmsHome(smsHomeBannerAddress)>0L) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存后轮播图地址：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "sms", REMARK = "修改轮播图地址")
    @ResponseBody
    @PostMapping("/updateBannerAddress")
    public Object updateBannerAddress(@RequestBody SmsHomeBannerAddress entity){
        try {
            //保存数据
            if (ISmsHomeBannerAddressService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存后轮播图地址：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "批量删除轮播图")
    @ResponseBody
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public Object deleteBannerAddress(@RequestParam("ids") List<Long> ids) {

        if (ids.size()==0){
           return new CommonResult().failed(500,"请选中地址再删除！！");
        }
        boolean count = ISmsHomeBannerAddressService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "sms", REMARK = "所有轮播图地址")
    @ResponseBody
    @RequestMapping(value = "/bannerAddressList", method = RequestMethod.GET)
    public Object BannerAddressList(SmsHomeBannerAddress entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            return  new CommonResult().success(ISmsHomeBannerAddressService.page(new Page<SmsHomeBannerAddress>(pageNum,pageSize),new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有轮播图地址列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();

    }

}
