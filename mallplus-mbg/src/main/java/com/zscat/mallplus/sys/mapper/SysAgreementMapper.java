package com.zscat.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sys.entity.SysAgreement;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by EDZ on 2020/8/1.
 * @author    zrf
 */
public interface SysAgreementMapper extends BaseMapper<SysAgreement>{

    @Select("select * from sys_agreement where id=#{id}")
    List<SysAgreement> selectAgreements(Integer id);


    @Update("update sys_agreement set content = #{content} where id=#{id}")
    Boolean updateAgree(SysAgreement sysAgreement);
}
