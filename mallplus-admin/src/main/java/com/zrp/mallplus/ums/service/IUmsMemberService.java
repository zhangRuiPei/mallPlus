package com.zrp.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.live.entity.AliyunLive;
import com.zrp.mallplus.ums.entity.UmsMember;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IUmsMemberService extends IService<UmsMember> {

    void updataMemberOrderInfo();

    Map<String,Object> findAll(UmsMember entity, Integer pageNum, Integer pageSize);
    boolean updateMemberGoodsLog(UmsMember entity, String operMan);
    Boolean updateRoomNumById(AliyunLive entity);
}
