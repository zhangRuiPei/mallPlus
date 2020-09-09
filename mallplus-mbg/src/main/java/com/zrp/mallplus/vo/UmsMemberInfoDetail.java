package com.zrp.mallplus.vo;

import com.zrp.mallplus.sms.entity.SmsCouponHistory;
import com.zrp.mallplus.ums.entity.UmsMember;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
public class UmsMemberInfoDetail implements Serializable {

    private UmsMember member;
    private List<SmsCouponHistory> histories;
}
