package com.zscat.mallplus.controller;

import com.zscat.mallplus.bill.entity.BillPayments;
import com.zscat.mallplus.bill.service.IBillPaymentsService;
import com.zscat.mallplus.common.*;
import com.zscat.mallplus.oms.entity.OmsOrder;
import com.zscat.mallplus.oms.service.IOmsOrderService;
import com.zscat.mallplus.oms.vo.TbThanks;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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


@RestController
@RequestMapping("/pay")
public class sendPay {


	@Autowired
 	private IOmsOrderService omsOrderService;

	@Autowired
	private IUmsMemberService umsMemberService;


	@Autowired
	private IBillPaymentsService iBillPaymentsService;

	/**
	 * @Description 微信浏览器内微信支付/公众号支付(JSAPI)
	 * @return Map
	 */
	@RequestMapping(value = "orders")
	public @ResponseBody Object orders(@RequestBody TbThanks tbThanks,HttpServletRequest request) {
		try {


			// 拼接统一下单地址参数
			Map<String, String> paraMap = new HashMap<String, String>();
			OmsOrder orderInfo = omsOrderService.getOrderInfo(tbThanks);
			String valueOf = String.valueOf(orderInfo.getTotalAmount());
			if (valueOf.contains(".")){
				int indexof = valueOf.indexOf(".");
				valueOf = valueOf.substring(0,indexof);
			}
			int anInt = Integer.parseInt(valueOf);
			String total_fee = String.valueOf(anInt);
			UmsMember newCurrentMember = umsMemberService.getNewCurrentMember();
			paraMap.put("appid", AuthUtil.APPID); // 商家平台ID
			paraMap.put("body", "纯情小PHP"); // 商家名称-销售商品类目、String(128)
			paraMap.put("mch_id", AuthUtil.MCHID); // 商户ID
			paraMap.put("nonce_str", WXPayUtil.generateNonceStr()); // UUID
			paraMap.put("openid", newCurrentMember.getWeixinOpenid());
			paraMap.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-", ""));// 订单号,每次都不同
			paraMap.put("spbill_create_ip", WXPayUtil.getIp(request));
			paraMap.put("total_fee", "1"); // 支付金额，单位分
			paraMap.put("trade_type", "JSAPI"); // 支付类型
			paraMap.put("notify_url", "https://admin.artsheep.cn/pay/notify");// 此路径是微信服务器调用支付结果通知路径随意写
			String sign = WXPayUtil.generateSignature(paraMap, AuthUtil.PATERNERKEY);
			paraMap.put("sign", sign);
			String xml = WXPayUtil.mapToXml(paraMap);// 将所有参数(map)转xml格式

			// 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
			String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

			System.out.println("xml为：" + xml);

//			 String xmlStr = HttpRequest.sendPost(unifiedorder_url, xml);//发送post请求"统一下单接口"返回预支付id:prepay_id

			String xmlStr = HttpRequest.httpsRequest(unifiedorder_url, "POST", xml);

			System.out.println("xmlStr为：" + xmlStr);

			// 以下内容是返回前端页面的json数据
			String prepay_id = "";// 预支付id
			if (xmlStr.indexOf("SUCCESS") != -1) {
				Map<String, String> map = WXPayUtil.xmlToMap(xmlStr) ;
				prepay_id = (String) map.get("prepay_id");
			}
			Date date = new Date();
			long time = date.getTime();
			Map<String, String> payMap = new HashMap<String, String>();
			payMap.put("appId", AuthUtil.APPID);
			payMap.put("timeStamp", time + "");
			payMap.put("nonceStr", WXPayUtil.generateNonceStr());
			payMap.put("signType", "MD5");
			payMap.put("package", "prepay_id=" + prepay_id);
			String paySign = WXPayUtil.generateSignature(payMap, AuthUtil.PATERNERKEY);
			payMap.put("paySign", paySign);
			//将这个6个参数传给前端

			BillPayments billPayments = new BillPayments();
			billPayments.setIp(WXPayUtil.getIp(request));
			Date date1 = new Date();
			long ctime = date1.getTime();
			billPayments.setCtime(ctime);
			billPayments.setMoney(orderInfo.getTotalAmount());
			String orderSn = orderInfo.getOrderSn();
			billPayments.setPaymentId(orderSn);
			billPayments.setTradeNo(paraMap.get("out_trade_no"));
			billPayments.setType(1);
			billPayments.setStatus(1);
			billPayments.setParams(sign);
			billPayments.setUserId(Math.toIntExact(newCurrentMember.getId()));
			iBillPaymentsService.save(billPayments);

			return new CommonResult().success(payMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title: callBack
	 * @Description: 支付完成的回调函数
	 * @param:
	 * @return:
	 */
	@RequestMapping("/notify")
	public String callBack(HttpServletRequest request, HttpServletResponse response) {
		// System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
		InputStream is = null;
		try {
			is = request.getInputStream();// 获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			String xml = WXPayUtil.InputStream2String(is);
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);// 将微信发的xml转map
			
			System.out.println("微信返回给回调函数的信息为："+xml);
			
			if (notifyMap.get("result_code").equals("SUCCESS")) {
				String ordersSn = notifyMap.get("out_trade_no");// 商户订单号
				String amountpaid = notifyMap.get("total_fee");// 实际支付的订单金额:单位 分
				BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);// 将分转换成元-实际支付金额:元

				/*
				 * 以下是自己的业务处理------仅做参考 更新order对应字段/已支付金额/状态码
				 */
				System.out.println("===notify===回调方法已经被调！！！");
				BillPayments billPaymentsServiceById = iBillPaymentsService.findBillTradeNo(ordersSn);
				Date date = new Date();
				billPaymentsServiceById.setPaymentId(billPaymentsServiceById.getPaymentId());
				billPaymentsServiceById.setStatus(2);
				billPaymentsServiceById.setUtime(date.getTime());
				iBillPaymentsService.updateById(billPaymentsServiceById);
				omsOrderService.wxOrderPay(billPaymentsServiceById.getPaymentId());
			}
			
			// 告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
			response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
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

