package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.SysWebLog;
import com.zrp.mallplus.vo.LogParam;
import com.zrp.mallplus.vo.LogStatisc;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISysWebLogService extends IService<SysWebLog> {

    List<LogStatisc> selectPageExt(LogParam entity);
}
