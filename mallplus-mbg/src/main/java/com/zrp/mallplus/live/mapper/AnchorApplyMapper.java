package com.zrp.mallplus.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrp.mallplus.live.entity.AnchorApply;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */

public interface AnchorApplyMapper extends BaseMapper<AnchorApply> {

    List<AnchorApply> findAll();


}
