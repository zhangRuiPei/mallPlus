package com.zrp.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zrp.mallplus.enums.OrderStatus;
import com.zrp.mallplus.oms.entity.OmsOrder;
import com.zrp.mallplus.oms.entity.OmsOrderOperateHistory;
import com.zrp.mallplus.oms.mapper.OmsOrderMapper;
import com.zrp.mallplus.oms.mapper.OmsOrderOperateHistoryMapper;
import com.zrp.mallplus.oms.service.IOmsOrderOperateHistoryService;
import com.zrp.mallplus.oms.service.IOmsOrderService;
import com.zrp.mallplus.oms.vo.OmsMoneyInfoParam;
import com.zrp.mallplus.oms.vo.OmsOrderDeliveryParam;
import com.zrp.mallplus.oms.vo.OmsReceiverInfoParam;
import com.zrp.mallplus.pms.entity.PmsProduct;
import com.zrp.mallplus.pms.mapper.PmsProductMapper;
import com.zrp.mallplus.sys.util.applet.WXPayConstants;
import com.zrp.mallplus.sys.util.applet.WXPayUtil;
import com.zrp.mallplus.sys.util.applet.WechatUtil;
import com.zrp.mallplus.ums.entity.SysAppletSet;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.mapper.UmsMemberMapper;
import com.zrp.mallplus.util.FileUtil;
import com.zrp.mallplus.utils.ValidatorUtils;
import com.zrp.mallplus.utils.poi.utils.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {

    @Resource
    private OmsOrderMapper orderMapper;
    @Resource
    private UmsMemberMapper memberMapper;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private IOmsOrderOperateHistoryService orderOperateHistoryDao;
    @Resource
    private OmsOrderOperateHistoryMapper orderOperateHistoryMapper;

    @Override
    public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
        //批量发货
        int count = orderMapper.delivery(deliveryParamList);
        if (count > 0) {
            //添加操作记录
            List<OmsOrderOperateHistory> operateHistoryList = deliveryParamList.stream()
                    .map(omsOrderDeliveryParam -> {
                        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                        history.setOrderId(omsOrderDeliveryParam.getOrderId());
                        history.setCreateTime(new Date());
                        history.setOperateMan("后台管理员");
                        history.setPreStatus(OrderStatus.TO_DELIVER.getValue());
                        history.setOrderStatus(OrderStatus.DELIVERED.getValue());
                        history.setNote("完成发货");
                        return history;
                    }).collect(Collectors.toList());
            orderOperateHistoryDao.saveBatch(operateHistoryList);
        }
        return count;
    }

    @Override
    public int singleDelivery(OmsOrderDeliveryParam deliveryParamList) {
        OmsOrder order = new OmsOrder();
        order.setId(deliveryParamList.getOrderId());
        order.setDeliverySn(deliveryParamList.getDeliverySn());
        order.setDeliveryCompany(deliveryParamList.getDeliveryCompany());
        order.setStatus(OrderStatus.DELIVERED.getValue());
        //批量发货
        int count = orderMapper.updateById(order);
        if (count > 0) {

            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(deliveryParamList.getOrderId());
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setPreStatus(OrderStatus.TO_DELIVER.getValue());
            history.setOrderStatus(OrderStatus.DELIVERED.getValue());
            history.setNote("完成发货");


            orderOperateHistoryDao.save(history);
        }
        return count;
    }

    @Override
    public Map orderDayStatic(String date) {
        Map list = orderMapper.orderDayStatic(date);
        return list;
    }

    @Override
    public Map orderMonthStatic(String date) {
        Map list = orderMapper.orderMonthStatic(date);
        return list;
    }

    @Override
    public Object dayStatic(String date, Integer type) {
        List<OmsOrder> orders = orderMapper.listByDate(date, type);
        List<UmsMember> members = memberMapper.listByDate(date, type);
        List<PmsProduct> products = productMapper.listByDate(date, type);
        int nowOrderCount = 0; // 今日订单
        BigDecimal nowOrderPay = new BigDecimal(0); //今日销售总额
        for (OmsOrder order : orders) {
            if (order.getStatus() < 9) {
                nowOrderCount++;
                nowOrderPay = nowOrderPay.add(order.getPayAmount());
            }
        }
        int mallCount = 0; // 当日
        int femallount = 0; // 当日
        for (UmsMember member : members) {
            if (member.getGender() == null || member.getGender() == 1) {
                mallCount++;
            } else {
                femallount++;
            }
        }
        int onCount = 0;
        int offCount = 0;

        int noStock = 0;

        for (PmsProduct goods : products) {
            if (goods.getPublishStatus() == 1) { // 上架状态：0->下架；1->上架
                onCount++;
            }
            if (goods.getPublishStatus() == 0) { // 上架状态：0->下架；1->上架
                offCount++;
            }
            if (ValidatorUtils.empty(goods.getStock()) || goods.getStock() < 1) { // 上架状态：0->下架；1->上架
                noStock++;
            }
        }
        Map<String, Object> map = new HashMap();
        map.put("nowOrderCount", nowOrderCount);
        map.put("nowOrderPay", nowOrderPay);
        map.put("mallCount", mallCount);
        map.put("femallount", femallount);
        map.put("onCount", onCount);
        map.put("offCount", offCount);
        map.put("noStock", noStock);

        map.put("memberCount", memberMapper.selectCount(new QueryWrapper<>()));
        map.put("goodsCount", productMapper.selectCount(new QueryWrapper<>()));
        map.put("orderCount", orderMapper.selectCount(new QueryWrapper<>()));
        return map;

    }

    @Override
    public Map<String, Object> getOList(OmsOrder entity,Integer pageNum,Integer pageSize) {

        Map<String,Object> map = new HashMap();
        List<OmsOrder> oList =null;
        if (pageNum!=null&&pageSize!=null){

        PageHelper.startPage(pageNum,pageSize);
        Page<OmsOrder> all = (Page<OmsOrder>) this.baseMapper.getOList(entity);
            map.put("data",all.getResult());
            map.put("total",all.getTotal());
            return map;
        }else {

            oList=this.baseMapper.getOList(entity);
        }
        map.put("data",oList);
        return map;


    }



    @Override
    public int close(List<Long> ids, String note) {
        OmsOrder record = new OmsOrder();
        record.setStatus(4);
        int count = orderMapper.update(record, new QueryWrapper<OmsOrder>().eq("delete_status", 0).in("id", ids));
        List<OmsOrderOperateHistory> historyList = ids.stream().map(orderId -> {
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(4);
            history.setNote("订单关闭:" + note);
            return history;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.saveBatch(historyList);
        return count;
    }

    @Override
    public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setModifyTime(new Date());
        int count = orderMapper.updateById(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(receiverInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(moneyInfoParam.getOrderId());
        order.setFreightAmount(moneyInfoParam.getFreightAmount());
        order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
        order.setModifyTime(new Date());
        int count = orderMapper.updateById(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(moneyInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(moneyInfoParam.getStatus());
        history.setNote("修改费用信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderMapper.updateById(order);
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息：" + note);
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public String sendWXRefund(OmsOrder omsOrder) throws Exception {

        SysAppletSet appletByStoreId = orderMapper.findAppletByStoreId(omsOrder.getStoreId());
        Map<Object,Object> map = new HashMap<>();
        map.put("appid",appletByStoreId.getAppid());
        map.put("mch_id",appletByStoreId.getMchid());
        map.put("out_trade_no",omsOrder.getOrderSn());
        map.put("nonce_str", StringUtil.getUUID());
        map.put("out_refund_no", WechatUtil.getBundleId());
        map.put("total_fee", omsOrder.getPayAmount().doubleValue()*100);
        map.put("refund_fee", omsOrder.getPayAmount().doubleValue()*100);
        map.put("sign",WechatUtil.arraySign(map,appletByStoreId.getPaySignKey()));
        String mapToXml = WXPayUtil.mapToXml(map);
        String xml = doRefund(appletByStoreId.getMchid(), WXPayConstants.REFUND_URL, mapToXml);

        return xml;
    }

    @Override
    @Transactional
    public Boolean updateOrdById(OmsOrder omsOrder,Integer nowStoreId,String refuseNote) {

        OmsOrder ordOmsOrder = orderMapper.selectById(omsOrder.getId());

        orderMapper.updateById(omsOrder);


        if (omsOrder.getStatus()==(OrderStatus.REFUND.getValue())){
            try {
                this.sendWXRefund(omsOrder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(omsOrder.getId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(omsOrder.getStatus());
        history.setPreStatus(ordOmsOrder.getStatus());
        history.setNote(refuseNote);
        orderOperateHistoryMapper.insert(history);



        return null;
    }

    public static String doRefund(String mchId, String url, String data) throws Exception {
        /**
         * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //这里自行实现我是使用数据库配置将证书上传到了服务器可以使用 FileInputStream读取本地文件
        BufferedInputStream inputStream = FileUtil.getInputStream("src\\main\\resources\\cert\\apiclient_cert.p12");
        try {
            //这里写密码..默认是你的MCHID
            keyStore.load(inputStream, mchId.toCharArray());
        } finally {
            inputStream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                //这里也是写密码的
                .loadKeyMaterial(keyStore, mchId.toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                //接受到返回信息
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }


}
