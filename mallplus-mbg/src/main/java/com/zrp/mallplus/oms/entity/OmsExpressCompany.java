package com.zrp.mallplus.oms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("oms_express_company")
public class OmsExpressCompany {

  private long id;
  private String expressCorpId;
  private String expressCorpName;
  private long status;
  private String logoUrl;
  private long storeId;

}
