package com.zscat.mallplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.bill.entity.BillPayments;
import com.zscat.mallplus.bill.service.IBillPaymentsService;
import com.zscat.mallplus.exception.ApiMallPlusException;
import com.zscat.mallplus.oms.entity.*;
import com.zscat.mallplus.oms.service.*;
import com.zscat.mallplus.oms.vo.ConfirmOrderResult;
import com.zscat.mallplus.oms.vo.OmsOrderDetail;
import com.zscat.mallplus.oms.vo.OrderParam;
import com.zscat.mallplus.oms.vo.TbThanks;
import com.zscat.mallplus.pms.entity.PmsComment;
import com.zscat.mallplus.pms.service.IPmsCommentService;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.ums.service.RedisService;
import com.zscat.mallplus.util.JsonUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.vo.Rediskey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
@Api(tags = "OmsPortalOrderController", description = "订单管理")
@RequestMapping("/api/order")
public class OmsPortalOrderController {

    @Autowired
    private IOmsOrderService orderService;
    @Autowired
    private IUmsMemberService memberService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IOmsOrderItemService orderItemService;

    @Resource
    private IBillPaymentsService iBillPaymentsService;

    @Resource
    private IOmsOrderReturnReasonService iOmsOrderReturnReasonService;

    @Resource
    private IOmsOrderReturnApplyService iOmsOrderReturnApplyService;

    @Resource
    private IOmsOrderOperateHistoryService omsOrderOperateHistoryService;


    @Autowired
    private IPmsCommentService pmsCommentService;

    /**
     * 订单列表   分页
     * @param queryParam
     * @return
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestBody OmsOrder queryParam) {
        queryParam.setMemberId(memberService.getNewCurrentMember().getId());
        IPage<OmsOrder> page = orderService.page(new Page<OmsOrder>(queryParam.getPageNum(), queryParam.getPageSize()), new QueryWrapper<>(queryParam));
        return new CommonResult().success(page);
    }

    /**
     * 订单以及订单下的商品  分页
     * @param queryParam
     * @return
     */
    @PostMapping(value = "/listForOrderAndProduct")
    @ResponseBody
    public Object listForOrderAndProduct(@RequestBody OmsOrder queryParam){
      queryParam.setMemberId(memberService.getNewCurrentMember().getId());
        Integer status = queryParam.getStatus();
        List<OmsOrder> orderList = new ArrayList<>();
        if (status == 15){
            List<OmsOrder> orderList1 = orderService.findListBy13(queryParam.getMemberId());
            List<OmsOrder> orderList2 = orderService.findListBy14(queryParam.getMemberId());
            if (orderList1 ==null & orderList2 ==null){
                new CommonResult().success();
            }
            orderList.addAll(orderList1);
            orderList.addAll(orderList2);
        for (OmsOrder order : orderList) {
            List<OmsOrderItem> list = orderItemService.findListByorderSn(order.getOrderSn());
            for (OmsOrderItem omsOrderItem:list){
                Long quantity = 0L;
                Integer productQuantity = omsOrderItem.getProductQuantity();
                quantity = productQuantity+quantity;
                order.setQuantity(quantity);
            }
            order.setOmsOrderItemList(list);
            }
        }else if(status ==0) {
            orderList = orderService.findAll(queryParam.getMemberId());
            for (OmsOrder order : orderList) {
                List<OmsOrderItem> list = orderItemService.findListByorderSn(order.getOrderSn());
                for (OmsOrderItem omsOrderItem:list){
                    Long quantity = 0L;
                    Integer productQuantity = omsOrderItem.getProductQuantity();
                    quantity = productQuantity+quantity;
                    order.setQuantity(quantity);
                }
                order.setOmsOrderItemList(list);
            }
        }else {
            orderList = orderService.list(new QueryWrapper<>(queryParam));
            for (OmsOrder order : orderList) {
                List<OmsOrderItem> list = orderItemService.findListByorderSn(order.getOrderSn());
                for (OmsOrderItem omsOrderItem:list){
                    Long quantity = 0L;
                    Integer productQuantity = omsOrderItem.getProductQuantity();
                    quantity = productQuantity+quantity;
                    order.setQuantity(quantity);
                }
                order.setOmsOrderItemList(list);
            }
        }
        List<OmsOrder> omsOrders = new ArrayList<OmsOrder>();
        long currIdx = (queryParam.getPageNum() > 1 ? (queryParam.getPageNum() -1) * queryParam.getPageSize() : 0);
        for (long i = 0; i < queryParam.getPageSize() && i < orderList.size() - currIdx; i++) {
            OmsOrder memberArticleBean = orderList.get((int) (currIdx + i));
            omsOrders.add(memberArticleBean);
        }
        return new CommonResult().success(omsOrders);
    }









    @ApiOperation("获取订单详情:订单信息、商品信息、操作记录")
    @PostMapping(value = "/detail")
    @ResponseBody
    public Object detail(@RequestBody OmsOrder queryParam ) {
        OmsOrder orderDetailResult = null;
        String bannerJson = redisService.get(Rediskey.PmsProductResult + queryParam.getId());
        if (bannerJson != null) {
            orderDetailResult = JsonUtils.jsonToPojo(bannerJson, OmsOrderDetail.class);
        } else {
            orderDetailResult = orderService.getById(queryParam.getId());
            OmsOrderItem query = new OmsOrderItem();
            query.setOrderId(queryParam.getId());
            List<OmsOrderItem> orderItemList = orderItemService.list(new QueryWrapper<>(query));
            BillPayments billPaymentsBy = iBillPaymentsService.findBillPaymentsBy(orderDetailResult.getOrderSn());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
            String tradeNo = billPaymentsBy.getTradeNo();
            Long ctime = billPaymentsBy.getCtime();
            String format = simpleDateFormat.format(ctime);
            orderDetailResult.setOrderItemList(orderItemList);
            orderDetailResult.setZhifhuliushuihao(tradeNo);
            orderDetailResult.setFukuanshijian(format);
            redisService.set(Rediskey.PmsProductResult + queryParam.getId(), JsonUtils.objectToJson(orderDetailResult));
            redisService.expire(Rediskey.PmsProductResult + queryParam.getId(), 10 * 60);
        }

        return new CommonResult().success(orderDetailResult);
    }


    /**
     * 预览订单
     * @param orderParam
     * @return
     */
    @ResponseBody
    @GetMapping("/submitPreview")
    public Object submitPreview(OrderParam orderParam) {
        try {
            ConfirmOrderResult result = orderService.submitPreview(orderParam);
            return new CommonResult().success(result);
        } catch (ApiMallPlusException e) {
            return new CommonResult().failed(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提交订单
     *
     * @param orderParam
     * @return
     */
    @ApiOperation("根据购物车信息生成订单")
    @PostMapping(value = "/generateOrder")
    @ResponseBody
    public Object generateOrder(@RequestBody OrderParam orderParam) {
        return orderService.generateOrder(orderParam);
    }


    /**
     * 支付订单
     * @param tbThanks
     * @return
     */
    @RequestMapping(value = "/payOrder")
    @ApiOperation(value = "支付订单")
    @ResponseBody
    public Object payOrder(TbThanks tbThanks) {
        int result = orderService.payOrder(tbThanks);
        return new CommonResult().success(result);
    }

    @ApiOperation("自动取消超时订单")
    @RequestMapping(value = "/cancelTimeOutOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelTimeOutOrder() {
        return orderService.cancelTimeOutOrder();
    }

    @ApiOperation("取消单个超时订单")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelOrder(Long orderId) {
        orderService.sendDelayMessageCancelOrder(orderId);
        return new CommonResult().success(null);
    }

    /**
     * 查看物流
     */
    @ApiOperation("查看物流")
    @ResponseBody
    @RequestMapping("/getWayBillInfo")
    public Object getWayBillInfo(@RequestParam(value = "orderId", required = false, defaultValue = "0") Long orderId) throws Exception {
        try {
            UmsMember member = memberService.getNewCurrentMember();
            OmsOrder order = orderService.getById(orderId);
            if (order == null) {
                return null;
            }
            if (!order.getMemberId().equals(member.getId())) {
                return new CommonResult().success("非当前用户订单");
            }
            //    ExpressInfoModel expressInfoModel = orderService.queryExpressInfo(orderId);
            return new CommonResult().success(null);
        } catch (Exception e) {
            log.error("get waybillInfo error. error=" + e.getMessage(), e);
            return new CommonResult().failed("获取物流信息失败，请稍后重试");
        }

    }
    @ApiOperation("评论")
    @ResponseBody
    @PostMapping("/orderComment")
    public Object orderComment(@RequestBody OmsOrder omsOrder){
        Object comment = orderService.orderComment(omsOrder.getOrderId(),
                omsOrder.getGoodsId(),
                omsOrder.getOmsOrderItemId(),
                omsOrder.getScore(),
                omsOrder.getImages(),
                omsOrder.getTextarea());
        return comment;
    }


    @ApiOperation("确认收货")
    @ResponseBody
    @PostMapping("/confimDelivery")
    public Object confimDelivery(@RequestBody OmsOrder omsOrder){
        return new CommonResult().success(orderService.confimDelivery(omsOrder.getId()));

    }

    @ApiOperation("仅退款")
    @ResponseBody
    @PostMapping("/applyRefund")
    public Object applyRefund(@RequestBody OmsOrder omsOrder){
        return new CommonResult().success(orderService.applyRefund(omsOrder.getId()));
    }


    @ApiOperation("退货退款")
    @ResponseBody
    @PostMapping("/applyRe")
    public Object applyRe(@RequestBody OmsOrder omsOrder){
        Object applyRe = orderService.applyRe(omsOrder.getItemId(),
                omsOrder.getReturnReson(),
                omsOrder.getItems(),
                omsOrder.getType(),
                omsOrder.getDesc(),
                omsOrder.getImages(),
                omsOrder.getReturnAmount());
        return applyRe;
    }


    @ApiOperation("根据条件查询所有退货原因表列表")
    @PostMapping(value = "/returnList")
    @ResponseBody
    public Object getOmsOrderReturnReasonByPage(@RequestBody OmsOrderReturnReason entity) {
        try {
            List<OmsOrderReturnReason> list = iOmsOrderReturnReasonService.list(new QueryWrapper<>(entity));
            return new CommonResult().success(list);
        } catch (Exception e) {
            log.error("根据条件查询所有退货原因表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("退款退货详情")
    @PostMapping(value = "/getReturnDetail")
    @ResponseBody
    public Object getReturnDetail(@RequestBody OmsOrder queryParam){
        //申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝
        UmsMember newCurrentMember = memberService.getNewCurrentMember();
        String username = newCurrentMember.getUsername();
        Long productId = queryParam.getProductId();
        Long orderId = queryParam.getOrderId();
        OmsOrderReturnApply omsOrderReturnApply = iOmsOrderReturnApplyService.findReturnApply(productId, username,orderId);
        OmsOrderOperateHistory omsOrderOperateHistory = new OmsOrderOperateHistory();
        omsOrderOperateHistory.setOrderId(orderId);
        omsOrderOperateHistory.setOrderItemId(productId);
        List<OmsOrderOperateHistory> list = omsOrderOperateHistoryService.list(new QueryWrapper<>(omsOrderOperateHistory));
        omsOrderReturnApply.setOmsOrderOperateHistories(list);
        return new CommonResult().success(omsOrderReturnApply);
    }


    @ApiOperation("订单中心  显示 已经评论的和待评论")
    @PostMapping(value = "/orderCommentCenter")
    @ResponseBody
    public Object orderCommentCenter(@RequestBody  OmsOrder queryParam){
        UmsMember newCurrentMember = memberService.getNewCurrentMember();
        queryParam.setMemberId(newCurrentMember.getId());
        List<OmsOrder> list = orderService.findAll(newCurrentMember.getId());
        List<OmsOrderItem> omsOrderItems = new ArrayList<>();
        for (OmsOrder omsOrder:list){
            String orderSn = omsOrder.getOrderSn();
            Long orderId = omsOrder.getId();
            List<OmsOrderItem> listByorderSn = orderItemService.findListByorderSn(orderSn);
            for (OmsOrderItem omsOrderItem:listByorderSn){
                if (omsOrderItem.getStatus()== queryParam.getOrderStatus()){
                    PmsComment commentInfo = pmsCommentService.getCommentInfo(omsOrderItem.getProductId(), orderId);
                    omsOrderItem.setPmsComment(commentInfo);
                    omsOrderItems.add(omsOrderItem);
                }else if ( omsOrderItem.getStatus() == queryParam.getOrderStatus()){
                    PmsComment commentInfo = pmsCommentService.getCommentInfo(omsOrderItem.getProductId(), orderId);
                    omsOrderItem.setPmsComment(commentInfo);
                    omsOrderItems.add(omsOrderItem);
                }
            }
        }
        return new CommonResult().success(omsOrderItems);
    }



    @ApiOperation("参团 开团")
    @PostMapping(value = "/acceptGroup")
    @ResponseBody
    public Object acceptGroup(@RequestBody OrderParam orderParam){
         return orderService.acceptGroup(orderParam);
    }





}
