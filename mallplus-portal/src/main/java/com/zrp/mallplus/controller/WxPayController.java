package com.zrp.mallplus.controller;

import com.zrp.mallplus.common.AuthUtil;
import com.zrp.mallplus.common.HttpRequest;
import com.zrp.mallplus.common.WXPayUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@RestController
@RequestMapping("/wxPay")
public class WxPayController {


    /**
     * 统一微信下单功能
     * @param request
     * @param response
     * @param ofMoney
     * @return
     */
    @RequestMapping("/unionOrders")
    @ResponseBody
    public Map<String,String> unionOrders(HttpServletRequest request, HttpServletResponse response, BigDecimal ofMoney) {
        try {
        String openId = null;
        Map<String,String> stringMap = new HashMap<String, String>();
        stringMap.put("appid", AuthUtil.APPID);
        stringMap.put("body","易烊千玺");
        stringMap.put("mch_id",AuthUtil.MCHID);
        stringMap.put("nonce_str", WXPayUtil.generateNonceStr());
        stringMap.put("open_id",openId);
        stringMap.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-",""));
        stringMap.put("spbill_create_ip",WXPayUtil.getIp(request));
        stringMap.put("total_free", String.valueOf(ofMoney));
        stringMap.put("trade_type","JSAPI");
        stringMap.put("notify_url","");
        String sign = WXPayUtil.generateSignature(stringMap,AuthUtil.PATERNERKEY);
        stringMap.put("sign",sign);
        String xml = WXPayUtil.mapToXml(stringMap);
        String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String xmlStr = HttpRequest.httpsRequest(unifiedorder_url, "POST", xml);
        String returnUrl="";

        if (xmlStr.indexOf("SUCCESS")!=-1){
            Map<String,String> map = WXPayUtil.xmlToMap(xmlStr);
            returnUrl = map.get("prepay_id");
        }

        Date date = new Date();
        long time = date.getTime();
        Map<String,String> map = new HashMap<String, String>();
        map.put("addId",AuthUtil.APPID);
        map.put("timeStamp",time + "");
        map.put("nonceStr",WXPayUtil.generateNonceStr());
        map.put("signType","MD5");
        map.put("package","prepay_id=" + returnUrl);
        String paySign = WXPayUtil.generateSignature(map,AuthUtil.PATERNERKEY);
        map.put("paySign",paySign);
        return map;
    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
    }


    /**
     * 微信支付回调
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/notify")
    public String callBack(HttpServletRequest request,HttpServletResponse response){
        InputStream is = null;
        try {
            is = request.getInputStream();
            String xml = WXPayUtil.InputStream2String(is);
            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);
            if (notifyMap.get("result_code").equals("SUCCESS")){
                String out_trade_no = notifyMap.get("out_trade_no");
                String total_free = notifyMap.get("total_free");
                BigDecimal amountPay = (new BigDecimal(total_free).divide(new BigDecimal("100"))).setScale(2);// 将分转换成元-实际支付金额:元
                System.out.println("===notify===回调方法已经被调！！！");
            }
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
             if (is!=null){
                 try {
                     is.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
        }
        return null;
    }
}
