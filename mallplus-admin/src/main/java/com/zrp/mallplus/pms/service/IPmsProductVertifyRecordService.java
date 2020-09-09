package com.zrp.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.pms.entity.PmsProductVertifyRecord;
import com.zrp.mallplus.sys.entity.SysStore;

/**
 * <p>
 * 商品审核记录 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IPmsProductVertifyRecordService extends IService<PmsProductVertifyRecord> {

    int addRecord(SysStore entity);
}
