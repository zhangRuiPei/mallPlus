package com.zrp.mallplus.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.ums.entity.UmsMember;
import com.zrp.mallplus.ums.entity.UmsMemberReceiveAddress;
import com.zrp.mallplus.ums.mapper.UmsMemberReceiveAddressMapper;
import com.zrp.mallplus.ums.service.IUmsMemberReceiveAddressService;
import com.zrp.mallplus.ums.service.IUmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements IUmsMemberReceiveAddressService {

    @Resource
    private UmsMemberReceiveAddressMapper addressMapper;
    @Autowired
    private IUmsMemberService memberService;

    @Override
    public UmsMemberReceiveAddress getDefaultItem() {

        UmsMember currentMember = memberService.getNewCurrentMember();
        UmsMemberReceiveAddress q = new UmsMemberReceiveAddress();
        q.setDefaultStatus(1);
        q.setMemberId(currentMember.getId());
        return this.getOne(new QueryWrapper<>(q));
    }

    @Transactional
    @Override
    public int setDefault(Long id) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        addressMapper.updateStatusByMember(currentMember.getId());

        UmsMemberReceiveAddress def = new UmsMemberReceiveAddress();
        def.setId(id);
        def.setDefaultStatus(1);
        this.updateById(def);
        return 1;
    }

    @Override
    public List<UmsMemberReceiveAddress> findListById(Long memberId) {
        return addressMapper.findListById(memberId);
    }
}
