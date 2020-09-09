package com.zrp.mallplus.bo;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Data
public class AddProdcut {


    /**
     * sort : 0
     * transfeeMethod : 1
     * nocommission : 0
     * totalcnf : 0
     * isYouhui : 1
     * publishStatus : 1
     * productProperties : 1,0,
     * type : 1
     * isburst : 0
     * ishot : 0
     * isnew : 0
     * istui : 0
     * name : 商品标题
     * productSn : 商品编号
     * keywords : 关键字
     * description : 简介
     * productCategoryName : 清洁
     * productCategoryId : 1005013
     * unit : 单位
     * stock : 1111
     * sale : 111
     * price : 123
     * promotionPrice : 122
     * transfee : 10
     * list_img : http://yjlive160322.oss-cn-beijing.aliyuncs.com/web-20200722-4732949cf9d24260b4210178ebd255fe.jpg
     * banner_img : http://yjlive160322.oss-cn-beijing.aliyuncs.com/web-20200722-1d7b8079d72c460db073b1d27b98ecf1.png
     * img : http://yjlive160322.oss-cn-beijing.aliyuncs.com/web-20200722-e6ad9c57fbc84918981251a204958f08.png,http://yjlive160322.oss-cn-beijing.aliyuncs.com/web-20200722-8d892a0f7fe34379ad0371956a9ab9cc.pn
     * hasoption : 1
     * option_arr : [{"title0":"11","total":"1","market_price":"1","product_price":"1","cost_price":"1","vip_price":"1","weight":"1"},{"title0":"22","total":"2","market_price":"2","product_price":"2","cost_price":"2","vip_price":"2","weight":"2"}]
     * pec_arr : [{"title":"1","displayorder":"","spec_item":"11,22"}]
     * detailHtml : <p><img src="http://yjlive160322.oss-cn-beijing.aliyuncs.com/web-20200722-c547b72316f347a08dcf10a68f6f1a4c.jpg" style="max-width:100%;"><br></p><p>123</p>
     * storeId : 1
     */

    private String sort;
    private int transfeeMethod;
    private int nocommission;
    private int totalcnf;
    private int isYouhui;
    private int publishStatus;
    private String productProperties;
    private int type;
    private int isburst;
    private int ishot;
    private int isnew;
    private int istui;
    private String name;
    private String productSn;
    private String keywords;
    private String description;
    private String productCategoryName;
    private Long productCategoryId;
    private String unit;
    private String stock;
    private String sale;
    private BigDecimal price;
    private BigDecimal promotionPrice;
    private BigDecimal transfee;
    private String list_img;
    private String banner_img;
    private String img;
    private int hasoption;
    private String detailHtml;
    private int storeId;
    private String option_arr;
    private String pec_arr;


}
