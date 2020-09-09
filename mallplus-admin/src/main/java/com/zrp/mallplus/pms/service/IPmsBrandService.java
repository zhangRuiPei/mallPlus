package com.zrp.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.pms.entity.PmsBrand;

import java.util.List;

/**
 * <p>
 * 品牌表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IPmsBrandService extends IService<PmsBrand> {

    int updateShowStatus(List<Long> ids, Integer showStatus);

    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
