package com.zscat.mallplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zscat.mallplus.annotation.IgnoreAuth;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.entity.UmsMemberReceiveAddress;
import com.zscat.mallplus.ums.mapper.UmsMemberReceiveAddressMapper;
import com.zscat.mallplus.ums.service.IUmsMemberReceiveAddressService;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员收货地址管理Controller
 */
@RestController
@Api(tags = "UmsMemberReceiveAddressController", description = "会员收货地址管理")
@RequestMapping("/api/address")
public class UmsMemberReceiveAddressController {

    @Resource
    private IUmsMemberReceiveAddressService memberReceiveAddressService;
    @Resource
    private UmsMemberReceiveAddressMapper addressMapper;
    @Resource
    private IUmsMemberService memberService;


    /**
     * 新增收获地址
     * @param receiveAddress
     * @return
     */
    @ResponseBody
    @PostMapping("/installAddress")
    public Object installAddress(@RequestBody  UmsMemberReceiveAddress receiveAddress){
        receiveAddress.setMemberId(memberService.getNewCurrentMember().getId());
        if (receiveAddress.getDefaultStatus()==1){
            Long id = memberService.getNewCurrentMember().getId();
            List<UmsMemberReceiveAddress> listById = memberReceiveAddressService.findListById(id);
            for (UmsMemberReceiveAddress umsMemberReceiveAddress:listById){
                umsMemberReceiveAddress.setDefaultStatus(0);
                memberReceiveAddressService.updateById(umsMemberReceiveAddress);
            }
        }
        boolean save = memberReceiveAddressService.save(receiveAddress);
        return new CommonResult().success(save);
    }


    /**
     * 删除收获地址
     * @param receiveAddress
     * @return
     */
    @ApiOperation("删除收货地址")
    @PostMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestBody UmsMemberReceiveAddress receiveAddress) {
        boolean count = memberReceiveAddressService.removeById(receiveAddress.getId());
        if (count) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    /**
     * 修改收获地址
     * @param address
     * @return
     */
    @ApiOperation("修改收货地址")
    @PostMapping(value = "/save")
    @ResponseBody
    public Object update(@RequestBody UmsMemberReceiveAddress address) {
        boolean count = false;
        address.setMemberId(memberService.getNewCurrentMember().getId());
        if (address.getDefaultStatus() == 1) {
            addressMapper.updateStatusByMember(address.getMemberId());
        }
        if (address != null && address.getId() != null && address.getId() > 0) {
            count = memberReceiveAddressService.updateById(address);
        } else {
            count = memberReceiveAddressService.save(address);
        }
        if (count) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }


    /**
     * 显示所有收货地址
     * @return
     */
    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @GetMapping(value = "/list")
    @ResponseBody
    public Object list() {
        UmsMember umsMember = memberService.getNewCurrentMember();
        if (umsMember != null && umsMember.getId() != null) {
            List<UmsMemberReceiveAddress> addressList = memberReceiveAddressService.list(new QueryWrapper<UmsMemberReceiveAddress>().eq("member_id", umsMember.getId()));
            return new CommonResult().success(addressList);
        }
        return new ArrayList<UmsMemberReceiveAddress>();
    }

    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @PostMapping(value = "/detail")
    @ResponseBody
    public Object getItem(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getById(id);
        return new CommonResult().success(address);
    }

    @IgnoreAuth
    @ApiOperation("显示默认收货地址")
    @RequestMapping(value = "/getItemDefautl", method = RequestMethod.GET)
    @ResponseBody
    public Object getItemDefautl() {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getDefaultItem();
        return new CommonResult().success(address);
    }

    /**
     * 设为默认地址
     * @param address
     * @return
     */
    @ApiOperation("设为默认地址")
    @PostMapping(value = "/address-set-default")
    @ResponseBody
    public Object setDefault(@RequestBody UmsMemberReceiveAddress address) {
        int count = memberReceiveAddressService.setDefault(address.getId());
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
}
