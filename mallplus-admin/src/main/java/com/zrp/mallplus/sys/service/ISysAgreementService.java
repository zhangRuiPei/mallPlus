package com.zrp.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.sys.entity.SysAgreement;

import java.util.List;

/**
 * Created by EDZ on 2020/8/1.
 */
public interface ISysAgreementService extends IService<SysAgreement>{

    List<SysAgreement> getAgreements(Integer id);

    Boolean updateAgree(SysAgreement sysAgreement);

}
