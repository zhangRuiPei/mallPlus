package com.zrp.mallplus.pms.vo;


import com.zrp.mallplus.pms.entity.PmsProduct;
import com.zrp.mallplus.sms.entity.SmsGroupMember;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 创建和修改商品时使用的参数
 * https://github.com/shenzhuan/mallplus on 2018/4/28.
 */
@Data
public class PmsProductAndGroup extends PmsProduct {
    private Map<String, List<SmsGroupMember>> map;
    private String isGroup = "1"; //1 可以发起团购
    private String is_favorite;
}
