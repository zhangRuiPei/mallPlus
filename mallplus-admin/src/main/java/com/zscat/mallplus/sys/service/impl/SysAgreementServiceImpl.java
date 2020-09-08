package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.sys.entity.SysAgreement;
import com.zscat.mallplus.sys.mapper.SysAgreementMapper;
import com.zscat.mallplus.sys.service.ISysAgreementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by EDZ on 2020/8/1.
 * @author   zrf
 */

@Service
public class SysAgreementServiceImpl extends ServiceImpl<SysAgreementMapper,SysAgreement> implements ISysAgreementService {

    /**
     * 查询协议列表
     */
    @Resource
    private  SysAgreementMapper sysAgreementMapper;


    @Override
    public List<SysAgreement> getAgreements(Integer id){
        return sysAgreementMapper.selectAgreements(id);
    }

    @Override
    public Boolean updateAgree(SysAgreement sysAgreement){
        return sysAgreementMapper.updateAgree(sysAgreement);

    }
}
