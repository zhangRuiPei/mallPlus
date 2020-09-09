package com.zrp.mallplus.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zrp.mallplus.sms.entity.SmsHomeAdvertise;
import com.zrp.mallplus.sms.entity.SmsHomeNewProduct;
import com.zrp.mallplus.sms.mapper.SmsHomeAdvertiseMapper;
import com.zrp.mallplus.sms.mapper.SmsHomeNewProductMapper;
import com.zrp.mallplus.sms.service.ISmsHomeAdvertiseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页轮播广告表 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class SmsHomeAdvertiseServiceImpl extends ServiceImpl<SmsHomeAdvertiseMapper, SmsHomeAdvertise> implements ISmsHomeAdvertiseService {
    @Resource
    private SmsHomeNewProductMapper homeNewProductMapper;

    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeNewProduct homeNewProduct = new SmsHomeNewProduct();
        homeNewProduct.setId(id);
        homeNewProduct.setSort(sort);
        return homeNewProductMapper.updateById(homeNewProduct);
    }

    @Override
    public Map<String, Object> findAll(SmsHomeAdvertise SmsHomeAdvertise , Integer pageNum, Integer pageSize) {

        Map<String,Object> map = new HashMap();
//        ListBanner listBanner = new ListBanner();
      /*  //看好了 云行一下
        Page<SmsHomeAdvertise> page = new Page<>(pageNum, pageSize);
        page.setTotal(this.baseMapper.selectCount(new QueryWrapper<>(smsHomeAdvertise)));

        page.setOptimizeCountSql(false);

        IPage<SmsHomeAdvertise> smsHomeAdvertiseIPage = this.baseMapper.selectPage(page, queryWrapper);
        //这是记录
        List<SmsHomeAdvertise> records = smsHomeAdvertiseIPage.getRecords();
        System.out.println(records.size());
        //这是总数
        long total = smsHomeAdvertiseIPage.getTotal();
        System.out.println(total);
        //这是页数
        long pages = smsHomeAdvertiseIPage.getPages();
        System.out.println(pages);
*/
//        List<SmsHomeAdvertise> all = this.baseMapper.findAll(smsHomeAdvertise.getType());
//        listBanner.setSmsHomeAdvertiseList(all);
//
//
//
//        Integer integer = this.baseMapper.selectCount(new QueryWrapper<>(smsHomeAdvertise));
//        map.put("data",this.baseMapper.findAll(smsHomeAdvertise.getType()));
//        map.put("total",integer);


        PageHelper.startPage(pageNum,pageSize);
        Page<SmsHomeAdvertise> all = (Page<SmsHomeAdvertise>) this.baseMapper.findAll(SmsHomeAdvertise.getType());
        map.put("data",all.getResult());
        map.put("total",all.getTotal());
        return map;
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        SmsHomeNewProduct record = new SmsHomeNewProduct();
        record.setRecommendStatus(recommendStatus);
        return homeNewProductMapper.update(record, new QueryWrapper<SmsHomeNewProduct>().in("id", ids));
    }


}
