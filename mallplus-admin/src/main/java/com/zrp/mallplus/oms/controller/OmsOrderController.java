package com.zrp.mallplus.oms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuwenze.poi.ExcelKit;
import com.zrp.mallplus.annotation.SysLog;
import com.zrp.mallplus.enums.AllEnum;
import com.zrp.mallplus.enums.ConstansValue;
import com.zrp.mallplus.oms.entity.OmsOrder;
import com.zrp.mallplus.oms.entity.OmsOrderItem;
import com.zrp.mallplus.oms.entity.OmsOrderOperateHistory;
import com.zrp.mallplus.oms.mapper.OmsOrderMapper;
import com.zrp.mallplus.oms.mapper.OmsOrderOperateHistoryMapper;
import com.zrp.mallplus.oms.service.IOmsOrderItemService;
import com.zrp.mallplus.oms.service.IOmsOrderService;
import com.zrp.mallplus.oms.vo.OmsMoneyInfoParam;
import com.zrp.mallplus.oms.vo.OmsOrderDeliveryParam;
import com.zrp.mallplus.oms.vo.OmsReceiverInfoParam;
import com.zrp.mallplus.util.EasyPoiUtils;
import com.zrp.mallplus.utils.CommonResult;
import com.zrp.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * 订单表
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@RestController
@Api(tags = "oms", description = "订单表管理")
@RequestMapping("/oms/OmsOrder")
public class OmsOrderController {
    @Resource
    private IOmsOrderService IOmsOrderService;
    @Resource
    private IOmsOrderItemService orderItemService;

    @Resource
    private OmsOrderOperateHistoryMapper omsOrderOperateHistoryMapper;

    @Resource
    private OmsOrderMapper omsOrderMapper;

  /*  @SysLog(MODULE = "oms", REMARK = "根据条件查询所有订单表列表")
    @ApiOperation("根据条件查询所有订单表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('oms:OmsOrder:read')")
    public Object getOmsOrderByPage(OmsOrder entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IOmsOrderService.page(new Page<OmsOrder>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time").select(ConstansValue.sampleOrderList)));
        } catch (Exception e) {
            log.error("根据条件查询所有订单表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }*/

    @SysLog(MODULE = "oms", REMARK = "根据条件查询所有订单表列表")
    @ApiOperation("根据条件查询所有订单表列表")
    @GetMapping(value = "/list")
//    @PreAuthorize("hasAuthority('oms:OmsOrder:read')")
    public Object getOmsOrderByPage(OmsOrder entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {

        return new CommonResult().success(IOmsOrderService.getOList(entity, pageNum, pageSize));

    }


    @SysLog(MODULE = "oms", REMARK = "删除订单表")
    @ApiOperation("删除订单表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('oms:OmsOrder:delete')")
    public Object deleteOmsOrder(@ApiParam("订单表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("订单表id");
            }
            if (IOmsOrderService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除订单表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "给订单表分配订单表")
    @ApiOperation("查询订单表明细")
    @GetMapping(value = "/{id}")
    public Object getOmsOrderById(@ApiParam("订单表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("订单表id");
            }
            OmsOrder coupon = IOmsOrderService.getById(id);
            if (coupon != null && coupon.getId() > 0) {
                coupon.setOrderItemList(orderItemService.list(new QueryWrapper<OmsOrderItem>().eq("order_id", coupon.getId())));
                coupon.setHistoryList(omsOrderOperateHistoryMapper.selectList(new QueryWrapper<OmsOrderOperateHistory>().eq("order_id", coupon.getId())));
                return new CommonResult().success(coupon);
            }

            return new CommonResult().failed("订单已删除");
        } catch (Exception e) {
            log.error("查询订单表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除订单表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除订单表")
    @PreAuthorize("hasAuthority('oms:OmsOrder:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IOmsOrderService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @SysLog(MODULE = "oms", REMARK = "批量发货")
    @ApiOperation("批量发货")
    @RequestMapping(value = "/update/delivery", method = RequestMethod.POST)
    @ResponseBody
    public Object delivery(@RequestBody List<OmsOrderDeliveryParam> deliveryParamList) {
        int count = IOmsOrderService.delivery(deliveryParamList);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "批量发货")
    @ApiOperation("批量发货")
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    @ResponseBody
    public Object singleDelivery(@RequestBody OmsOrderDeliveryParam deliveryParamList) {
        int count = IOmsOrderService.singleDelivery(deliveryParamList);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "批量关闭订单")
    @ApiOperation("批量关闭订单")
    @RequestMapping(value = "/update/close", method = RequestMethod.POST)
    @ResponseBody
    public Object close(@RequestParam("ids") List<Long> ids, @RequestParam String note) {
        int count = IOmsOrderService.close(ids, note);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "修改收货人信息")
    @ApiOperation("修改收货人信息")
    @RequestMapping(value = "/update/receiverInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object updateReceiverInfo(@RequestBody OmsReceiverInfoParam receiverInfoParam) {
        int count = IOmsOrderService.updateReceiverInfo(receiverInfoParam);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "修改订单费用信息")
    @ApiOperation("修改订单费用信息")
    @RequestMapping(value = "/update/moneyInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object updateReceiverInfo(@RequestBody OmsMoneyInfoParam moneyInfoParam) {
        int count = IOmsOrderService.updateMoneyInfo(moneyInfoParam);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "oms", REMARK = "修改订单费用信息")
    @ApiOperation("修改订单状态")
    @RequestMapping(value = "/update/updateOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object updateOrder(@RequestBody OmsOrder omsOrder,Integer NowStoreId,String refuseNote) throws Exception {
        if (IOmsOrderService.updateOrdById(omsOrder,NowStoreId,refuseNote)) {

            return new CommonResult().success();
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "oms", REMARK = "备注订单")
    @ApiOperation("备注订单")
    @RequestMapping(value = "/update/note", method = RequestMethod.POST)
    @ResponseBody
    public Object updateNote(@RequestParam("id") Long id,
                             @RequestParam("note") String note,
                             @RequestParam("status") Integer status) {
        int count = IOmsOrderService.updateNote(id, note, status);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }





    @ApiOperation(value = "查询订单列表")
    @GetMapping(value = "/order/list")
    public Object orderList(OmsOrder order,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {

        IPage<OmsOrder> page = null;
        if (order.getStatus() != null && order.getStatus() == 0) {
            page = IOmsOrderService.page(new Page<OmsOrder>(pageNum, pageSize), new QueryWrapper<OmsOrder>().orderByDesc("create_time").select(ConstansValue.sampleOrderList));
        } else {
            page = IOmsOrderService.page(new Page<OmsOrder>(pageNum, pageSize), new QueryWrapper<>(order).orderByDesc("create_time").select(ConstansValue.sampleOrderList));

        }
        for (OmsOrder omsOrder : page.getRecords()) {
            List<OmsOrderItem> itemList = orderItemService.list(new QueryWrapper<OmsOrderItem>().eq("order_id", omsOrder.getId()).eq("type", AllEnum.OrderItemType.GOODS.code()));
            omsOrder.setOrderItemList(itemList);
        }
        return new CommonResult().success(page);
    }

    @SysLog(MODULE = "oms", REMARK = "导出订单数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, OmsOrder entity) {
        // 模拟从数据库获取需要导出的数据
        List<OmsOrder> personList = IOmsOrderService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出订单数据", "订单数据", OmsOrder.class, "导出社区数据.xls", response);

    }

    /**
     * 可用的导出订单
     */
    @SysLog(MODULE = "oms", REMARK = "导出订单数据")
    @GetMapping("/exportExcel1")
    public void exportDemo(HttpServletResponse response, OmsOrder entity,Integer pageSize,Integer pageNum) {
        // 模拟从数据库获取需要导出的数据
        Map<String, Object> oList = IOmsOrderService.getOList(entity, pageNum, pageSize);
        List<OmsOrder> result = (List<OmsOrder>) oList.get("data");
        // 导出操作
        ExcelKit.$Export(OmsOrder.class, response).downXlsx(result, false);
    }

    @SysLog(MODULE = "oms", REMARK = "根据条件查询所有订单表列表")
    @ApiOperation("查询订单明细表")
    @GetMapping(value = "/getOmsOrderItem")
//    @PreAuthorize("hasAuthority('oms:OmsOrder:read')")
    public Object getOmsOrderItem(OmsOrder entity ) {
        List<OmsOrderItem> itemList = orderItemService.list(new QueryWrapper<OmsOrderItem>().eq("order_id", entity.getId()).eq("type", AllEnum.OrderItemType.GOODS.code()));


        return new CommonResult().success(itemList);

    }

}
