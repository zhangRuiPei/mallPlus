package com.zrp.mallplus.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.oms.entity.OmsOrderReturnApply;
import com.zrp.mallplus.oms.vo.OmsUpdateStatusParam;

/**
 * 订单退货申请 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IOmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {
    /**
     * 修改申请状态
     */
    int updateStatus(Long id, OmsUpdateStatusParam statusParam);
}
