package com.zrp.mallplus.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.oms.entity.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单退货申请 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface OmsOrderReturnApplyMapper extends BaseMapper<OmsOrderReturnApply> {

    OmsOrderReturnApply findReturnApply(@Param("productId") Long productId,
                                        @Param("username") String username,
                                        @Param("orderId") Long orderId);

}
