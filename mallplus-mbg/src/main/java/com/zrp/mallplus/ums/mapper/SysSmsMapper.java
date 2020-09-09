package com.zrp.mallplus.ums.mapper;


import com.zrp.mallplus.ums.entity.SysSms;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * * 程序名 : SysSmsMapper
 * 建立日期: 2018-07-09
 * 作者 : someday
 * 模块 : 短信中心
 * 描述 : 短信crud
 * 备注 : version20180709001
 * <p>
 * 修改历史
 * 序号 	       日期 		        修改人 		         修改原因
 */
@Mapper
public interface SysSmsMapper  {

    int saveSysSms(SysSms sms);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_sms(phone, signName, templateCode, params, day, createTime, updateTime) "
            + "values(#{phone}, #{signName}, #{templateCode}, #{params}, #{day}, #{createTime}, #{updateTime})")
    int saves(SysSms sms);

    @Select("select * from sys_sms t where t.id = #{id}")
    SysSms findById(Long id);

    @Select("select * from sys_sms t where t.id = #{id}")
    SysSms findSysSmsById(Long id);

    int updates(SysSms sms);

    int update(SysSms sms);

    int count(Map<String, Object> params);

    List<SysSms> findList(Map<String, Object> params);
}
