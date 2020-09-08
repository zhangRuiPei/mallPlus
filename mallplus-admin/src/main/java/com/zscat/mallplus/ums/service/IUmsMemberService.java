package com.zscat.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.live.entity.AliyunLive;
import com.zscat.mallplus.ums.entity.UmsMember;

import java.util.List;
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
