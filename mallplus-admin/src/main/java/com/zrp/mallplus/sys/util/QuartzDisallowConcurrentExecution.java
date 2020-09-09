package com.zrp.mallplus.sys.util;

import com.zrp.mallplus.sys.entity.AdminSysJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, AdminSysJob job) throws Exception {
        JobInvokeUtil.invokeMethod(job);
    }
}
