/**
 * <p>IJPay 让支付触手可及，封装了微信支付、支付宝支付、银联支付常用的支付方式以及各种常用的接口。</p>
 * <p>
 * <p>不依赖任何第三方 mvc 框架，仅仅作为工具使用简单快速完成支付模块的开发，可轻松嵌入到任何系统里。 </p>
 * <p>
 * <p>IJPay 交流群: 723992875</p>
 * <p>
 * <p>Node.js 版: https://gitee.com/javen205/TNW</p>
 * <p>
 * <p>企业付款对账单下载 Model</p>
 *
 * @author Javen
 */
package com.zrp.mallplus.qqpay.model;

import com.zrp.mallplus.core.model.BaseModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DownloadTransferBillModel extends BaseModel {
    private String mch_id;
    private String nonce_str;
    private String bill_date;
    private String sign;
}
