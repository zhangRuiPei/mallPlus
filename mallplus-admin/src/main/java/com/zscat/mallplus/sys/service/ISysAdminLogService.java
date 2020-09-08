package com.zscat.mallplus.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sys.entity.SysAdminLog;
import com.zscat.mallplus.vo.LogParam;
import com.zscat.mallplus.vo.LogStatisc;

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
