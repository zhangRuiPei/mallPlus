package com.zrp.mallplus.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.sms.entity.SmsCouponHistory;
import com.zrp.mallplus.sms.vo.SmsCouponHistoryDetail;

import java.util.List;

/**
 * <p>
 * 优惠券使用、领取历史表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsCouponHistoryMapper extends BaseMapper<SmsCouponHistory> {

    List<SmsCouponHistoryDetail> getDetailList(Long memberId);
}
