package com.zscat.mallplus.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.zscat.mallplus.bo.AddProdcut;
import com.zscat.mallplus.pms.entity.*;
import com.zscat.mallplus.pms.mapper.*;
import com.zscat.mallplus.pms.service.IPmsProductService;
import com.zscat.mallplus.pms.vo.PmsProductParam;
import com.zscat.mallplus.pms.vo.PmsProductResult;
import com.zscat.mallplus.ums.service.RedisService;
import com.zscat.mallplus.util.UserUtils;
import com.zscat.mallplus.vo.PecArr;
import com.zscat.mallplus.vo.Rediskey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 商品信息 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Slf4j
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {

    @Resource
    private PmsProductMapper productMapper;


    @Resource
    private PmsSkuStockMapper skuStockMapper;

    @Resource
    private PmsProductAttributeValueMapper productAttributeValueMapper;

    @Resource
    private PmsProductAttributeMapper pmsProductAttributeMapper;



    @Resource
    private PmsProductVertifyRecordMapper productVertifyRecordMapper;
    @Resource
    private RedisService redisService;

    @Resource
    private  PmsSkuStockMapper pmsSkuStockMapper;

    @Override
    public int create(PmsProductParam productParam) {
        int count;

        return 0;
    }

    /**
     * 添加商品
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addList(String JsonProduct) {

        PmsProduct pmsProduct = new PmsProduct();
        Gson gson = new Gson();
        AddProdcut addProdcut = gson.fromJson(JsonProduct, AddProdcut.class);


        Integer i = null;
        pmsProduct.setName(addProdcut.getName());//商品标题
        pmsProduct.setProductSn(addProdcut.getProductSn());//商品编号
        pmsProduct.setKeywords(addProdcut.getKeywords());//关键字
//        pmsProduct.setDetailDesc(addProdcut.getDetailDesc());//简介
        pmsProduct.setProductCategoryId(addProdcut.getProductCategoryId());//商品分类id
        pmsProduct.setProductCategoryName(addProdcut.getProductCategoryName());//商品分类名称
        pmsProduct.setIsYouhui(addProdcut.getIsYouhui());//是否可使用优惠卷(1-是，2-否)
        pmsProduct.setUnit(addProdcut.getUnit());//单位
        pmsProduct.setSort(Integer.parseInt(addProdcut.getSort()));//排序
        pmsProduct.setStock(Integer.parseInt(addProdcut.getStock()));//库存
        pmsProduct.setSale(Integer.parseInt(addProdcut.getSale()));//销量
        pmsProduct.setPrice(addProdcut.getPrice());//价格
        pmsProduct.setPublishStatus(addProdcut.getPublishStatus());//上架状态：0->下架；1->上架
        pmsProduct.setProductProperties(addProdcut.getProductProperties());//产品属性-可多选（0-推荐，1-热卖，2-爆款，3-新品）
        pmsProduct.setTransfeeMethod(addProdcut.getTransfeeMethod());//运费方式 （1-统一收费；2-运费模板）
        pmsProduct.setTransfee(addProdcut.getTransfee());//运费
        pmsProduct.setPromotionPrice(addProdcut.getPromotionPrice());//零售价
        pmsProduct.setDetailHtml(addProdcut.getDetailHtml());//产品详情网页内容
        pmsProduct.setStoreId(addProdcut.getStoreId());//商家id
        pmsProduct.setAlbumPics(addProdcut.getImg());
        pmsProduct.setPic(addProdcut.getList_img());
        pmsProduct.setDetailMobileHtml(addProdcut.getBanner_img());
        pmsProduct.setDescription(addProdcut.getDescription());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        pmsProduct.setCreateTime(df.format(new Date()));

        productMapper.addProduct(pmsProduct);
       /* List<AddProdcut.PecArrBean> pec_arr = addProdcut.getPec_arr();
        for (AddProdcut.PecArrBean pecArrBean : pec_arr) {

            PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
            PmsProductAttributeValue pv = new PmsProductAttributeValue();

            pmsProductAttribute.setProductAttributeCategoryId(addProdcut.getProductCategoryId());
            pmsProductAttribute.setName(pecArrBean.getTitle());
            pmsProductAttribute.setHandAddStatus(1);
            pmsProductAttribute.setType(0);
            pmsProductAttribute.setStoreId(addProdcut.getStoreId());
            pmsProductAttributeMapper.addpmsProductAttribute(pmsProductAttribute);

            pv.setProductId(pmsProduct.getId());
            pv.setProductAttributeId(addProdcut.getProductCategoryId());
            pv.setValue(pecArrBean.getSpec_item());
            pv.setStoreId(addProdcut.getStoreId());
            pv.setName(pecArrBean.getTitle());
            productAttributeValueMapper.addproductAttributeValue(pv);

            for (AddProdcut.OptionArrBean optionArrBean : addProdcut.getOption_arr()) {
                if (pecArrBean.getTitle().equals(optionArrBean.getTitle0())){
                    PmsProductAttributeProperties pr = new PmsProductAttributeProperties();
                    pr.setProductId(pmsProduct.getId());
                    pr.setProductAttributeId(pmsProductAttribute.getId());
                    pr.setProductAttributeValueId(pv.getId());

                    pr.setProperties(optionArrBean);

                    pr.setMarketPrice(optionArrBean.getMarket_price());
                    pr.setProductPrice(optionArrBean.getProduct_price());
                    pr.setCostPrice(optionArrBean.getCost_price());
                    pr.setVipPrice(optionArrBean.getVip_price());
                    pr.setWeight(optionArrBean.getWeight());
                    i=pmsProductAttributePropertiesMapper.insert(pr);
                }
            }
        }
*/
        return i;
    }




    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, PmsProduct product) {
        if (CollectionUtils.isEmpty(skuStockList)) return;
        int stock = 0;
        for (int i = 0; i < skuStockList.size(); i++) {
            PmsSkuStock skuStock = skuStockList.get(i);
            skuStock.setProductName(product.getName());
            if (StringUtils.isEmpty(skuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", product.getProductCategoryId()));
                //三位索引id
                sb.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sb.toString());
            }
            if (skuStock.getStock()!=null && skuStock.getStock()>0){
                stock = stock + skuStock.getStock();

            }

        }
        product.setStock(stock);
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return productMapper.getUpdateInfo(id);
    }

    @Override
    public int update(Long id, PmsProductParam productParam) {
        PmsProductResult updateInfo = productMapper.getUpdateInfo(id);
        return productMapper.updateById(updateInfo);
    }

    @Transactional
    @Override
    public int updatePmsPro(PmsProduct pmsProduct) {

        int i = productMapper.updateById(pmsProduct);
        List<Long> arr = new ArrayList<>();
        arr.add(pmsProduct.getId());
        this.removeMultiple(arr);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

        if (pmsProduct.getPecarrs()!=null) {
            List<PmsSkuStock> skustockList = pmsProduct.getSkustockList();
            //遍历多条sku获取库存
            for (int i1 = 0; i1 < skustockList.size(); i1++) {
                PmsSkuStock skuStock = skustockList.get(i1);
                skuStock.setProductName(pmsProduct.getName());
                StringBuilder sb = new StringBuilder();
                df = new SimpleDateFormat("yyyyMMdd");
                //日期
                sb.append(df.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", pmsProduct.getProductCategoryId()));
                //三位索引id
                sb.append(String.format("%03d", i1 + 1));
                skuStock.setSkuCode(sb.toString());
                skuStock.setProductId(pmsProduct.getId());

                skuStockMapper.insert(skuStock);
            }


            for (PecArr pecArr : pmsProduct.getPecarrs()) {

                PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
                PmsProductAttributeValue pv = new PmsProductAttributeValue();
                pmsProductAttribute.setStoreId(pmsProduct.getStoreId());
                pmsProductAttribute.setProductId(pmsProduct.getId());
                pmsProductAttribute.setProductAttributeCategoryId(pmsProduct.getProductCategoryId());
                pmsProductAttribute.setName(pecArr.getTitle());
                pmsProductAttribute.setHandAddStatus(1);
                pmsProductAttribute.setType(0);
                pmsProductAttribute.setStoreId(pmsProduct.getStoreId());
                pmsProductAttributeMapper.insert(pmsProductAttribute);
//                pmsProductAttributeMapper.insert(pmsProductAttribute);

                pv.setProductId(pmsProduct.getId());
                pv.setProductAttributeId(pmsProductAttribute.getId());
                pv.setValue(pecArr.getSpecItem());
                pv.setStoreId(pmsProduct.getStoreId());
                pv.setName(pmsProductAttribute.getName());
                productAttributeValueMapper.insert(pv);
            }

        }

        /**记录当前操作*/
        PmsProductVertifyRecord record = new PmsProductVertifyRecord();
        record.setProductId(pmsProduct.getId());
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        record.setCreateTime(df.format(new Date()));
        record.setStatus(pmsProduct.getVerifyStatus());
        record.setVertifyMan(pmsProduct.getVertifyMan());
        record.setDetail(pmsProduct.getDetail());
        record.setStoreId(pmsProduct.getStoreId());
        productVertifyRecordMapper.insert(record);
        return  i;
    }


    @Override
    public int updateVerifyStatus(Long ids, Integer verifyStatus, String detail) {
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(verifyStatus);
        int count = productMapper.update(product, new QueryWrapper<PmsProduct>().eq("id", ids));
        //修改完审核状态后插入审核记录
        PmsProductVertifyRecord record = new PmsProductVertifyRecord();
        record.setProductId(ids);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        record.setCreateTime(df.format(new Date()));
        record.setDetail(detail);
        record.setStatus(verifyStatus);
        record.setVertifyMan(UserUtils.getCurrentMember().getUsername());
        productVertifyRecordMapper.insert(record);
        redisService.remove(String.format(Rediskey.GOODSDETAIL, product.getId()));
        return count;
    }

    @Override
    public int updateisFenxiao(List<Long> ids, Integer newStatus) {
        PmsProduct record = new PmsProduct();
        record.setIsFenxiao(newStatus);
        clerGoodsRedis(ids);
        return productMapper.update(record, new QueryWrapper<PmsProduct>().in("id", ids));
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus,Integer storeId) {
        PmsProduct record = new PmsProduct();

        if (storeId==1){
        record.setPublishStatus(publishStatus);
        record.setVerifyStatus(1);
        }else {
        record.setPublishStatus(publishStatus);
        record.setVerifyStatus(0);
        }
        clerGoodsRedis(ids);
        return productMapper.update(record, new QueryWrapper<PmsProduct>().in("id", ids));
    }

    public void clerGoodsRedis(List<Long> ids) {
        for (Long id : ids) {
            redisService.remove(String.format(Rediskey.GOODSDETAIL, id));
        }
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct record = new PmsProduct();
        record.setRecommandStatus(recommendStatus);
        clerGoodsRedis(ids);
        return productMapper.update(record, new QueryWrapper<PmsProduct>().in("id", ids));
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct record = new PmsProduct();
        record.setNewStatus(newStatus);
        clerGoodsRedis(ids);
        return productMapper.update(record, new QueryWrapper<PmsProduct>().in("id", ids));
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PmsProduct record = new PmsProduct();
        record.setDeleteStatus(deleteStatus);
        clerGoodsRedis(ids);
        return productMapper.update(record, new QueryWrapper<PmsProduct>().in("id", ids));
    }

    @Override
    public List<PmsProduct> list(String keyword) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("delete_status", 0);

        if (!StringUtils.isEmpty(keyword)) {
            queryWrapper.like("name", keyword);

        }
        return productMapper.selectList(queryWrapper);
    }

    @Override
    public List<PmsProductVertifyRecord> getProductVertifyRecord(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", id);

        return productVertifyRecordMapper.selectList(queryWrapper);
    }



    @Override
    @Transactional
    public Long addList(PmsProduct pmsProduct, List<PmsSkuStock> skuStockList, List<PecArr> pecArrs) {


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        pmsProduct.setCreateTime(df.format(new Date()));
        if (pmsProduct.getStoreId()==1){
            pmsProduct.setVerifyStatus(1);
        }else {
            pmsProduct.setVerifyStatus(0);
        }
        productMapper.insert(pmsProduct);

        if (pecArrs!=null){
            //遍历多条sku获取库存
            for (int i = 0; i < skuStockList.size(); i++) {
                PmsSkuStock skuStock = skuStockList.get(i);
                skuStock.setProductName(pmsProduct.getName());
                StringBuilder sb = new StringBuilder();
                df = new SimpleDateFormat("yyyyMMdd");
                //日期
                sb.append(df.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", pmsProduct.getProductCategoryId()));
                //三位索引id
                sb.append(String.format("%03d", i + 1));
                skuStock.setSkuCode(sb.toString());
                skuStock.setProductId(pmsProduct.getId());
                skuStockMapper.insert(skuStock);
            }


            for (PecArr pecArr : pecArrs) {

                PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
                PmsProductAttributeValue pv = new PmsProductAttributeValue();
                pmsProductAttribute.setStoreId(pmsProduct.getStoreId());
                pmsProductAttribute.setProductId(pmsProduct.getId());
                pmsProductAttribute.setProductAttributeCategoryId(pmsProduct.getProductCategoryId());
                pmsProductAttribute.setName(pecArr.getTitle());
                pmsProductAttribute.setHandAddStatus(1);
                pmsProductAttribute.setType(0);
                pmsProductAttribute.setStoreId(pmsProduct.getStoreId());
                pmsProductAttributeMapper.insert(pmsProductAttribute);
//                pmsProductAttributeMapper.insert(pmsProductAttribute);

                pv.setProductId(pmsProduct.getId());
                pv.setProductAttributeId(pmsProductAttribute.getId());
                pv.setValue(pecArr.getSpecItem());
                pv.setStoreId(pmsProduct.getStoreId());
                pv.setName(pmsProductAttribute.getName());
                productAttributeValueMapper.insert(pv);


            }

        }
       /* int size = pecArrs.size();
        //前台只传一个时
        if (size == 1){*/

        return pmsProduct.getId();
    }



    /**
     * 建立和插入关系表操作
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            if (CollectionUtils.isEmpty(dataList)) return;
            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null);
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method insertList = dao.getClass().getMethod("saveBatch", Collection.class);
            insertList.invoke(dao, dataList);
        } catch (Exception e) {

            log.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public PmsProduct getProductById(Long id) {

//        PmsProduct product = this.baseMapper.findById(id);
        PmsProduct product = this.baseMapper.selectById(id);

        List<PecArr> pecArrs = new ArrayList<>();

        List<PmsProductAttribute> pmsProductAttributeList = pmsProductAttributeMapper.selectList(new QueryWrapper<PmsProductAttribute>().eq("product_id", product.getId()));

        for (PmsProductAttribute pmsProductAttribute : pmsProductAttributeList) {
            PmsProductAttributeValue pmsProductAttributeValue = productAttributeValueMapper.selectOne(
                                                                                                new QueryWrapper<PmsProductAttributeValue>()
                                                                                                .eq("product_attribute_id", pmsProductAttribute.getId()));
            PecArr pecArr = new PecArr();

            pecArr.setTitle(pmsProductAttribute.getName());
            pecArr.setSpecItem(pmsProductAttributeValue.getValue());
            pecArrs.add(pecArr);
        }
        product.setPecarrs(pecArrs);
        List<PmsSkuStock> pmsSkuStockList = pmsSkuStockMapper.selectList(new QueryWrapper<PmsSkuStock>().eq("product_id", product.getId()));

        product.setSkustockList(pmsSkuStockList);

        return product;
    }

    @Override
    public Map<String, Object> getList(PmsProduct entity, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum,pageSize);
        Page<PmsProduct> all = (Page<PmsProduct>) this.baseMapper.getOList(entity);
        map.put("data",all.getResult());
        map.put("total",all.getTotal());
        return map;
    }

    /**
     * 删除多规格
     * */
    @Override
    public void removeMultiple(List<Long> ids) {

        pmsProductAttributeMapper.delete(new QueryWrapper<PmsProductAttribute>().in("product_id",ids));
        productAttributeValueMapper.delete(new QueryWrapper<PmsProductAttributeValue>().in("product_id",ids));
        skuStockMapper.delete(new QueryWrapper<PmsSkuStock>().in("product_id",ids));
    }

}


