package com.zscat.mallplus.oms.vo;

import com.zscat.mallplus.oms.entity.OmsCartItem;
import lombok.Getter;
import lombok.Setter;

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
