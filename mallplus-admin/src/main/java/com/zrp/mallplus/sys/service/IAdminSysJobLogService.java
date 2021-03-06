package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.AdminSysJobLog;

/**
 * <p>
 * 定时任务调度日志表 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IAdminSysJobLogService extends IService<AdminSysJobLog> {

    void addJobLog(AdminSysJobLog jobLog);
}
