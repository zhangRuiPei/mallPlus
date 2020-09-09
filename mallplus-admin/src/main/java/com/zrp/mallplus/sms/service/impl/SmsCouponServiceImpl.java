package com.zrp.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.sms.entity.SmsCoupon;
import com.zrp.mallplus.sms.entity.SmsCouponProductCategoryRelation;
import com.zrp.mallplus.sms.entity.SmsCouponProductRelation;
import com.zrp.mallplus.sms.mapper.SmsCouponMapper;
import com.zrp.mallplus.sms.mapper.SmsCouponProductCategoryRelationMapper;
import com.zrp.mallplus.sms.mapper.SmsCouponProductRelationMapper;
import com.zrp.mallplus.sms.service.ISmsCouponProductCategoryRelationService;
import com.zrp.mallplus.sms.service.ISmsCouponProductRelationService;
import com.zrp.mallplus.sms.service.ISmsCouponService;
import com.zrp.mallplus.sms.vo.SmsCouponParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 优惠卷表 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements ISmsCouponService {

    @Resource
    private SmsCouponMapper couponMapper;
    @Resource
    private SmsCouponProductRelationMapper productRelationMapper;
    @Resource
    private SmsCouponProductCategoryRelationMapper productCategoryRelationMapper;
    @Resource
    private ISmsCouponProductRelationService productRelationDao;
    @Resource
    private ISmsCouponProductCategoryRelationService productCategoryRelationDao;

    @Override
    public boolean saves(SmsCouponParam couponParam) {
        couponParam.setCount(couponParam.getPublishCount());
        couponParam.setUseCount(0);
        couponParam.setReceiveCount(0);
        //插入优惠券表
        int count = couponMapper.insert(couponParam);
        //插入优惠券和商品关系表
        if (couponParam.getUseType().equals(2)) {
            for (SmsCouponProductRelation productRelation : couponParam.getProductRelationList()) {
                productRelation.setCouponId(couponParam.getId());
            }
            productRelationDao.saveBatch(couponParam.getProductRelationList());
        }
        //插入优惠券和商品分类关系表
        if (couponParam.getUseType().equals(1)) {
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            productCategoryRelationDao.saveBatch(couponParam.getProductCategoryRelationList());
        }
        return true;
    }

    @Override
    public boolean updateByIds(SmsCouponParam couponParam) {
        couponParam.setId(couponParam.getId());
        int count = couponMapper.updateById(couponParam);
        //删除后插入优惠券和商品关系表
        if (couponParam.getUseType().equals(2)) {
            for (SmsCouponProductRelation productRelation : couponParam.getProductRelationList()) {
                productRelation.setCouponId(couponParam.getId());
            }
            deleteProductRelation(couponParam.getId());
            productRelationDao.saveBatch(couponParam.getProductRelationList());
        }
        //删除后插入优惠券和商品分类关系表
        if (couponParam.getUseType().equals(1)) {
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            deleteProductCategoryRelation(couponParam.getId());
            productCategoryRelationDao.saveBatch(couponParam.getProductCategoryRelationList());
        }
        return true;
    }

    private void deleteProductCategoryRelation(Long id) {
        productCategoryRelationMapper.delete(new QueryWrapper<>(new SmsCouponProductCategoryRelation()).eq("coupon_id", id));
    }

    private void deleteProductRelation(Long id) {
        productRelationMapper.delete(new QueryWrapper<>(new SmsCouponProductRelation()).eq("coupon_id", id));
    }

    @Override
    public int delete(Long id) {
        //删除优惠券
        int count = couponMapper.deleteById(id);
        //删除商品关联
        deleteProductRelation(id);
        //删除商品分类关联
        deleteProductCategoryRelation(id);
        return count;
    }

    @Override
    public SmsCouponParam getItem(Long id) {
        return couponMapper.getItem(id);
    }
}
