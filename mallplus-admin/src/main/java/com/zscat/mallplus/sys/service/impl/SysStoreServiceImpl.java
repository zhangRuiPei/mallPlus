package com.zscat.mallplus.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.bill.entity.BakGoods;
import com.zscat.mallplus.component.OssAliyunUtil;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.service.IPmsProductService;
import com.zscat.mallplus.sys.entity.SysStore;
import com.zscat.mallplus.sys.entity.SysUser;
import com.zscat.mallplus.sys.entity.SysUserRole;
import com.zscat.mallplus.sys.mapper.SysStoreMapper;
import com.zscat.mallplus.sys.mapper.SysUserMapper;
import com.zscat.mallplus.sys.service.ISysStoreService;
import com.zscat.mallplus.sys.service.ISysUserRoleService;
import com.zscat.mallplus.sys.service.ISysUserService;
import com.zscat.mallplus.sys.service.SmsService;
import com.zscat.mallplus.ums.entity.SysSms;
import com.zscat.mallplus.utils.ValidatorUtils;
import com.zscat.mallplus.vo.SmsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@Service
public class SysStoreServiceImpl extends ServiceImpl<SysStoreMapper, SysStore> implements ISysStoreService {

    @Autowired
    OssAliyunUtil aliyunOSSUtil;
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private IPmsProductService productService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Resource
    private SmsService smsService;



    @Transactional
    @Override
    public boolean saveStore(SysStore entity) {
       return true;
    }

    @Override
    @Transactional
    public Boolean addSysUser(SysStore entity) {
        Boolean aboolean = false;

        SysStore sysStore = this.baseMapper.selectById(entity.getId());


        String md5Password = passwordEncoder.encode("123456");
        SysUser ordUser = userMapper.selectOne(new QueryWrapper<SysUser>().eq("username", sysStore.getContactMobile()));
        if (ordUser!=null){
            ordUser.setStatus(1);
            ordUser.setPassword(md5Password);
            if (userMapper.updateById(ordUser)==1){
                sysUserService.saveSmsAndSendCode(sysStore.getContactMobile(),ordUser.getUsername(),"123456",sysStore.getContactName());
                aboolean =true;
            }
        }else {

        SysUser sysUser = new SysUser();
        sysUser.setUsername(sysStore.getContactMobile());
        sysUser.setIcon(sysStore.getLogo());
        sysUser.setNickName(sysStore.getContactName());
        sysUser.setCreateTime(new Date());
        sysUser.setStatus(0);
        sysUser.setStoreId(sysStore.getId());
        sysUser.setStoreName(sysStore.getName());
        sysUser.setPassword(md5Password);
       if (userMapper.insert(sysUser)==1){
           SysUserRole sysUserRole = new SysUserRole();
           sysUserRole.setAdminId(sysUser.getId());
           sysUserRole.setRoleId(5L);
           sysUserRole.setStoreId(sysUser.getStoreId());
           sysUserRoleService.save(sysUserRole);
           sysUserService.saveSmsAndSendCode(sysStore.getContactMobile(),sysUser.getUsername(),"123456",sysStore.getContactName());
           aboolean= true;
       }
        }
        return aboolean;
    }

    @Override
    public void sendRefuse(SysStore entity) {
        SysStore sysStore = this.baseMapper.selectById(entity.getId());

        /*SmsVo smsVo = new SmsVo();
        smsVo.setName(name);
        smsVo.setUsername(username);
        smsVo.setPassword(password);*/
        Map<String, String> map = new HashMap<>();
        map.put("name",sysStore.getContactName());
        map.put("refuseDetail",entity.getDetail());
        String smsvo = JSON.toJSONString(map);


        SysSms sms = new SysSms();
        sms.setPhone(sysStore.getContactMobile());
        sms.setParams(smsvo);
        Map<String, String> params = new HashMap<>();
        params.put("code", smsvo);
        params.put("admin", "admin");

        sms.setTemplateCode("SMS_199790725");
        smsService.add(sms);

        //异步调用阿里短信接口发送短信
        CompletableFuture.runAsync(() -> {
            try {
                smsService.sendSmsMsg(sms);
            } catch (Exception e) {
                params.put("err",  e.getMessage());
                e.printStackTrace();
                log.error("发送短信失败：{}", e.getMessage());
            }

        });
    }

    void createG(BakGoods gg, Integer storeId) {
        PmsProduct g = new PmsProduct();

        g.setName(gg.getName());
        g.setSubTitle(gg.getBrief());
        g.setDescription(gg.getBrief());
        g.setDetailHtml(gg.getDetail());
        g.setDetailMobileHtml(gg.getDetail());
        g.setDetailTitle(gg.getBrief());
        g.setDetailDesc(gg.getBrief());

        g.setPic(gg.getPicUrl());

        g.setAlbumPics(gg.getPicUrl());
        if (ValidatorUtils.notEmpty(gg.getCounterPrice())) {
            g.setPrice(gg.getCounterPrice());
        }
        if (ValidatorUtils.notEmpty(gg.getRetailPrice())) {
            g.setOriginalPrice(gg.getRetailPrice());
        }

        g.setSort(gg.getSortOrder());
        g.setSale(gg.getCategoryId());
        g.setStock(gg.getId());
        g.setLowStock(0);
        g.setPublishStatus(1);
        g.setGiftPoint(gg.getCategoryId());
        g.setGiftGrowth(gg.getCategoryId());
        g.setPromotionType(0);
        g.setVerifyStatus(1);
        g.setProductSn(gg.getGoodsSn());
        g.setQsType(1);
        g.setNewStatus(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        g.setCreateTime(df.format(new Date()));

        g.setBrandId(gg.getBrandId().longValue());

        g.setProductCategoryId(gg.getCategoryId().longValue());

        g.setProductAttributeCategoryId(gg.getCategoryId().longValue());
        productService.save(g);
    }

  /*  *//**
     * 保存短信记录，并发送商家入驻消息
     *
     * @param phone
     *//*
    private void saveSmsAndSendCode(String phone, String username, String password, String name) {
        Map<String, String> params1 = new HashMap<>();
        params1.put("name",name);
        params1.put("username",username);
        params1.put("password",password);

        String smsvo = JSON.toJSONString(params1);


        SysSms sms = new SysSms();
        sms.setPhone(phone);
        sms.setParams(smsvo);
        Map<String, String> params = new HashMap<>();
        params.put("code", smsvo);
        params.put("admin", "admin");
        smsService.save(sms, params);

        //异步调用阿里短信接口发送短信
        CompletableFuture.runAsync(() -> {
            try {
                smsService.sendSmsMsg(sms);
            } catch (Exception e) {
                params.put("err",  e.getMessage());
                smsService.save(sms, params);
                e.printStackTrace();
                log.error("发送短信失败：{}", e.getMessage());
            }

        });
    }*/

}
