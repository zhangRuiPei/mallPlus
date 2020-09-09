package com.zrp.mallplus.oms.vo;

import com.zrp.mallplus.oms.entity.OmsCartItem;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */

public class IteamCarVo {
    List<OmsCartItem> omsCartItems;

    public List<OmsCartItem> getOmsCartItems() {
        return omsCartItems;
    }

    public List<OmsCartItem> setOmsCartItems(List<OmsCartItem> omsCartItems) {
        this.omsCartItems = omsCartItems;
        return omsCartItems;
    }
}
