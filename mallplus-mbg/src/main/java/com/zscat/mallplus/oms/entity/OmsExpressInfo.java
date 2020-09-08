package com.zscat.mallplus.oms.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 订单表
 * @author 敲李奶奶
 */
@Getter
@Setter
@TableName("oms_express_info")
public class OmsExpressInfo implements Serializable {

  private static final long serialVersionUID = 1L;


  private Long id;
  private Long orderId;
  private String expressCorpId;
  private String expressNo;
  private Long expressStatus;
  private String expressDetail;
  private String updateTime;
  private Long storeId;




}
