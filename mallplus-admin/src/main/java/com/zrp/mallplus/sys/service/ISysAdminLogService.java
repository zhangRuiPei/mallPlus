package com.zrp.mallplus.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.SysAdminLog;
import com.zrp.mallplus.vo.LogParam;
import com.zrp.mallplus.vo.LogStatisc;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISysAdminLogService extends IService<SysAdminLog> {
    List<LogStatisc> selectPageExt(LogParam entity);
}
