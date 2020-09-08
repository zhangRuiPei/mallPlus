package com.zscat.mallplus.oms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.oms.entity.OmsOrder;
import com.zscat.mallplus.oms.vo.OmsMoneyInfoParam;
import com.zscat.mallplus.oms.vo.OmsOrderDeliveryParam;
import com.zscat.mallplus.oms.vo.OmsReceiverInfoParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IOmsOrderService extends IService<OmsOrder> {
    /**
     * 修改订单收货人信息
     */
    @Transactional
    int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);

    /**
     * 修改订单费用信息
     */
    @Transactional
    int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam);

    /**
     * 修改订单备注
     */
    @Transactional
    int updateNote(Long id, String note, Integer status);


    /**
     * 批量发货
     */
    @Transactional
    int delivery(List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 批量关闭订单
     */
    @Transactional
    int close(List<Long> ids, String note);

    @Transactional
    int singleDelivery(OmsOrderDeliveryParam deliveryParamList);

    /**
     * 订单日统计
     *
     * @param date
     * @return
     */
    Map orderDayStatic(String date);

    /**
     * 订单月统计
     *
     * @param date
     * @return
     */
    Map orderMonthStatic(String date);

    Object dayStatic(String date, Integer type);

    /**
     * 订单列表+头像
     * */
    Map<String, Object> getOList(OmsOrder entity,Integer pageNum,Integer pageSize);

    String sendWXRefund(OmsOrder omsOrder) throws Exception;

    Boolean updateOrdById(OmsOrder omsOrder,Integer NowStoreId,String refuseNote);
}
