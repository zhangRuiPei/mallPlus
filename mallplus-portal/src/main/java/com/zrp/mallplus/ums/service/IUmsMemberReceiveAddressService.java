package com.zrp.mallplus.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.ums.entity.UmsMemberReceiveAddress;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IUmsMemberReceiveAddressService extends IService<UmsMemberReceiveAddress> {

    UmsMemberReceiveAddress getDefaultItem();

    int setDefault(Long id);

    List<UmsMemberReceiveAddress> findListById(Long memberId);
}
