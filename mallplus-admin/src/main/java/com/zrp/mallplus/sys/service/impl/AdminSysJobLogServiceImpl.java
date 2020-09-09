package com.zrp.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.sys.entity.AdminSysJobLog;
import com.zrp.mallplus.sys.mapper.AdminSysJobLogMapper;
import com.zrp.mallplus.sys.service.IAdminSysJobLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务调度日志表 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class AdminSysJobLogServiceImpl extends ServiceImpl<AdminSysJobLogMapper, AdminSysJobLog> implements IAdminSysJobLogService {

    @Override
    public void addJobLog(AdminSysJobLog jobLog) {
        this.baseMapper.insert(jobLog);
    }
}
