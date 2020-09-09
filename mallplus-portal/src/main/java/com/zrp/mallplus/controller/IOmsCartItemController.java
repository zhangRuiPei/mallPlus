package com.zrp.mallplus.controller;

import com.zrp.mallplus.oms.entity.OmsCartItem;
import com.zrp.mallplus.oms.service.IOmsCartItemService;
import com.zrp.mallplus.oms.service.IOmsOrderService;
import com.zrp.mallplus.oms.vo.IteamCarVo;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.service.IUmsMemberService;
import com.zrp.mallplus.ums.service.RedisService;
import com.zrp.mallplus.util.JsonUtils;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.vo.CarList;
import com.zrp.mallplus.vo.Rediskey;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "OmsPortalOrderController", description = "购物车")
@RequestMapping("/api/Car")
public class IOmsCartItemController {

    @Autowired
    private IOmsCartItemService service;

    @Resource
    private IOmsOrderService omsOrderService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IUmsMemberService memberService;


    /**
     * 购物车列表
     * @param
     * @param
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public Object CarList(@RequestBody CarList carList){
        UmsMember member = memberService.getNewCurrentMember();
        if (member==null){
            return new CommonResult().failed();
        }
        List<OmsCartItem> list =null;
        String omscaritem = redisService.get(Rediskey.OMSCARTITEM_RESOULT + member.getId());
        if (omscaritem !=null){
            list = (List<OmsCartItem>) JsonUtils.jsonToPojo(omscaritem, IteamCarVo.class);
        }else {
            list = service.list(member.getId(), carList.getIds());
            redisService.set(Rediskey.OMSCARTITEM_RESOULT,JsonUtils.objectToJson(list));
            redisService.expire(Rediskey.OMSCARTITEM_RESOULT,24*60*60);
        }
        return new CommonResult().success(list);
    }


    /**
     * 修改购物车数量
     * @param carList
     * @return
     */
    @PostMapping(value = "/updateOmsCar")
    @ResponseBody
    public Object updateOmsCar(@RequestBody CarList carList){
        UmsMember member = memberService.getNewCurrentMember();
        int updateQuantity = service.updateQuantity(carList.getId(), member.getId(), carList.getQuantity());
        return new CommonResult().success(updateQuantity);
    }


    /**
     * 新增 购物车 如果有就商品加一
     * @param omsCartItem
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public Object addOmsCar(@RequestBody OmsCartItem omsCartItem){
        OmsCartItem add = service.add(omsCartItem);
        return new CommonResult().success(add);
    }


    /**
     * 清空购物车
     * @param
     * @return
     */
    @PostMapping("clear")
    @ResponseBody
    public Object clear(@RequestBody CarList carList){
        int clear = service.clear(carList.getMemberId());
        return new CommonResult().success(clear);
    }


    /**
     * 批量删除
     * @return
     */
    @PostMapping("deletes")
    @ResponseBody
    public Object deleteOmsCar(@RequestBody CarList carList){
        UmsMember member = memberService.getNewCurrentMember();
        int delete = service.delete(member.getId(), carList.getIds());
        return new CommonResult().success(delete);
    }


}
