package com.zscat.mallplus.controller;

import com.zscat.mallplus.oms.service.IOmsOrderService;
import com.zscat.mallplus.oms.vo.OrderParam;
import com.zscat.mallplus.sms.entity.SmsCoupon;
import com.zscat.mallplus.sms.entity.SmsCouponHistory;
import com.zscat.mallplus.sms.entity.SmsGroup;
import com.zscat.mallplus.sms.service.ISmsCouponService;
import com.zscat.mallplus.sms.service.ISmsHomeAdvertiseService;
import com.zscat.mallplus.sms.vo.HomeFlashPromotion;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "MarketingController", description = "营销")
@RequestMapping("/api/market")
public class MarketingController {


    @Resource
    private ISmsHomeAdvertiseService smsHomeAdvertiseService;

    @Resource
    private IOmsOrderService orderService;

    @Resource
    private ISmsCouponService couponService;

    @Autowired

    /**
     * 首页秒杀商品
     * @return
     */
    @ApiOperation("首页秒杀商品")
    @PostMapping(value = "/homeFlashPromotionList")
    @ResponseBody
    public Object homeFlashPromotionList(){
        List<HomeFlashPromotion> homeFlashPromotions = smsHomeAdvertiseService.homeFlashPromotionList();
        return new CommonResult().success(homeFlashPromotions);
    }


    /**
     * 所有时间内的秒杀
     * @return
     */
    @ApiOperation("所有上线的秒杀商品")
    @PostMapping(value = "/FlashPromotionList")
    @ResponseBody
    public Object FlashPromotionList(){
        List<HomeFlashPromotion> homeFlashPromotions = smsHomeAdvertiseService.FlashPromotionList();
        return new CommonResult().success(homeFlashPromotions);
    }


    /**
     * 最新十条优惠信息
     * @return
     */
    @ApiOperation("最新十条拼团商品")
    @PostMapping(value = "/lastGroupGoods")
    @ResponseBody
    public Object lastGroupGoods(){
        List<SmsGroup> smsGroups = smsHomeAdvertiseService.lastGroupGoods(10);
        return new CommonResult().success(smsGroups);
    }


    /**
     * 参团 和 开团
     * @param orderParam
     * @return
     */
    @ApiOperation("参团 开团")
    @PostMapping(value = "/acceptGroup")
    @ResponseBody
    public Object acceptGroup(@RequestBody OrderParam orderParam){
        return orderService.acceptGroup(orderParam);
    }


    /**
     * 领取指定优惠券
     * @param couponId
     * @return
     */
    @ApiOperation("领取指定优惠券")
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Long couponId) {
        return couponService.add(couponId);
    }

    /**
     * 用户优惠券列表
     * @param useStatus
     * @return
     */
    @ApiOperation("获取用户优惠券列表")
    @PostMapping(value = "/list")
    @ResponseBody
    public Object list(Integer useStatus) {
        List<SmsCouponHistory> couponHistoryList = couponService.list(useStatus);
        return new CommonResult().success(couponHistoryList);
    }

    /**
     * 所有可领取的优惠券
     * @return
     */
    @GetMapping(value = "/allCoupon")
    @ResponseBody
    public Object allCoupon() {
        List<SmsCoupon> couponList = new ArrayList<>();
        couponList = couponService.selectNotRecive();
        return new CommonResult().success(couponList);
    }

}
