package com.zrp.mallplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrp.mallplus.annotation.IgnoreAuth;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.cms.entity.CmsSubject;
import com.zrp.mallplus.cms.service.ICmsSubjectService;
import com.zrp.mallplus.oms.service.IOmsOrderService;
import com.zrp.mallplus.oms.vo.HomeContentResult;
import com.zrp.mallplus.pms.entity.PmsProduct;
import com.zrp.mallplus.pms.service.IPmsProductAttributeCategoryService;
import com.zrp.mallplus.pms.service.IPmsProductService;
import com.zrp.mallplus.sms.entity.SmsCoupon;
import com.zrp.mallplus.sms.service.ISmsCouponService;
import com.zrp.mallplus.sms.service.ISmsHomeAdvertiseService;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.mapper.UmsMemberMapper;
import com.zrp.mallplus.ums.service.IUmsMemberService;
import com.zrp.mallplus.ums.service.RedisService;
import com.zrp.mallplus.util.JsonUtils;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.vo.Rediskey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页内容管理Controller
 */
@RestController
@Api(tags = "HomeController", description = "首页内容管理")
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private IUmsMemberService memberService;
    @Autowired
    private ISmsHomeAdvertiseService advertiseService;
    @Autowired
    private ISmsCouponService couponService;
    @Autowired
    private IPmsProductAttributeCategoryService productAttributeCategoryService;
    @Autowired
    private IPmsProductService pmsProductService;

    @Autowired
    private ICmsSubjectService subjectService;
    @Autowired
    private IOmsOrderService orderService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private UmsMemberMapper memberMapper;

    @IgnoreAuth
    @ApiOperation("首页内容页信息展示")
    @SysLog(MODULE = "home", REMARK = "首页内容页信息展示")
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public Object content() {
        List<UmsMember> log = memberMapper.selectList(new QueryWrapper<UmsMember>().ne("id", 2).last("limit 5"));
        HomeContentResult contentResult = null;
        String bannerJson = redisService.get(Rediskey.HomeContentResult);
        if (bannerJson != null) {
            contentResult = JsonUtils.jsonToPojo(bannerJson, HomeContentResult.class);
        } else {
            contentResult = advertiseService.singelContent();
            redisService.set(Rediskey.HomeContentResult, JsonUtils.objectToJson(contentResult));
            redisService.expire(Rediskey.HomeContentResult, 24 * 60 * 60);
        }
        return new CommonResult().success(contentResult);
    }

    @IgnoreAuth
    @ApiOperation("首页内容页信息展示")
    @SysLog(MODULE = "home", REMARK = "首页内容页信息展示")
    @RequestMapping(value = "/pc/content", method = RequestMethod.GET)
    public Object pcContent() {
        HomeContentResult contentResult = null;
        String bannerJson = redisService.get(Rediskey.HomeContentResult);
        if (bannerJson != null) {
            contentResult = JsonUtils.jsonToPojo(bannerJson, HomeContentResult.class);
        } else {
            contentResult = advertiseService.singelContent();
            redisService.set(Rediskey.HomeContentResult, JsonUtils.objectToJson(contentResult));
            redisService.expire(Rediskey.HomeContentResult, 24 * 60 * 60);
        }
        return new CommonResult().success(contentResult);
    }


    @IgnoreAuth
    @ApiOperation("分页获取最热商品")
    @SysLog(MODULE = "home", REMARK = "分页获取最热商品")
    @RequestMapping(value = "/hotProductList", method = RequestMethod.GET)
    public Object hotProductList(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = advertiseService.getHotProductList(pageSize, pageNum);
        return new CommonResult().success(productList);
    }

    @IgnoreAuth
    @ApiOperation("分页获取最新商品")
    @SysLog(MODULE = "home", REMARK = "分页获取最新商品")
    @RequestMapping(value = "/newProductList", method = RequestMethod.GET)
    public Object newProductList(@RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProduct> productList = advertiseService.getNewProductList(pageSize, pageNum);
        return new CommonResult().success(productList);
    }

    @IgnoreAuth
    @ApiOperation("根据分类获取专题")
    @SysLog(MODULE = "home", REMARK = "根据分类获取专题")
    @RequestMapping(value = "/subjectList", method = RequestMethod.GET)
    public Object getSubjectList(
            @RequestParam(value = "pageSize", defaultValue = "4") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<CmsSubject> subjectList = advertiseService.getRecommendSubjectList(pageSize, pageNum);
        return new CommonResult().success(subjectList);
    }

    @IgnoreAuth
    @GetMapping(value = "/subjectDetail")
    @SysLog(MODULE = "home", REMARK = "据分类获取专题")
    @ApiOperation(value = "据分类获取专题")
    public Object subjectDetail(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        CmsSubject cmsSubject = subjectService.getById(id);
        UmsMember umsMember = memberService.getNewCurrentMember();
        /*if (umsMember != null && umsMember.getId() != null) {
            MemberProductCollection findCollection = productCollectionRepository.findByMemberIdAndProductId(
                    umsMember.getId(), id);
            if(findCollection!=null){
                cmsSubject.setIs_favorite(1);
            }else{
                cmsSubject.setIs_favorite(2);
            }
        }*/
        return new CommonResult().success(cmsSubject);
    }


    @IgnoreAuth
    @SysLog(MODULE = "home", REMARK = "获取导航栏")
    @RequestMapping(value = "/navList", method = RequestMethod.GET)
    @ApiOperation(value = "获取导航栏")
    public Object getNavList() {
        return new CommonResult().success(advertiseService.getNav());
    }

    @IgnoreAuth
    @ApiOperation("优惠券列表")
    @RequestMapping(value = "/getHomeCouponList", method = RequestMethod.GET)
    public Object getHomeCouponList() {
        List<SmsCoupon> couponList = couponService.selectNotRecive();
        return new CommonResult().success(couponList);
    }

    @IgnoreAuth
    @ApiOperation("移动端首页")
    @RequestMapping(value = "/singelmobileContent", method = RequestMethod.GET)
    public Object singelmobileContent(){
        HomeContentResult homeContentResult = advertiseService.singelmobileadvertiseList();
        return new CommonResult().success(homeContentResult);
    }
}
