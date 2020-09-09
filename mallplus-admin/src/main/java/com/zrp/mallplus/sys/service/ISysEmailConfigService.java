package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.SysEmailConfig;
import com.zrp.mallplus.sys.vo.EmailVo;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface ISysEmailConfigService extends IService<SysEmailConfig> {

    /**
     * 发送邮件
     *
     * @param emailVo     邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    @Async
    void send(EmailVo emailVo, SysEmailConfig emailConfig) throws Exception;
}
