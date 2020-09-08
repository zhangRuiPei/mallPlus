package com.zscat.mallplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.IgnoreAuth;
import com.zscat.mallplus.enums.ConstansValue;
import com.zscat.mallplus.pms.entity.*;
import com.zscat.mallplus.pms.mapper.PmsProductAttributeMapper;
import com.zscat.mallplus.pms.vo.PmsProductCategoryWithChildrenItem;
import com.zscat.mallplus.pms.service.*;
import com.zscat.mallplus.pms.vo.ConsultTypeCount;
import com.zscat.mallplus.pms.vo.PmsProductAttr;
import com.zscat.mallplus.pms.vo.PmsProductResult;
import com.zscat.mallplus.ums.service.RedisService;
import com.zscat.mallplus.util.JsonUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.vo.Rediskey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页内容管理Controller
 */
@RestController
@Api(tags = "GoodsController", description = "商品相关管理")
@RequestMapping("/api/pms")
public class PmsGoodsController {

    @Autowired
    private IPmsProductAttributeCategoryService productAttributeCategoryService;
    @Autowired
    private IPmsProductService pmsProductService;
    @Autowired
    private IPmsProductAttributeService productAttributeService;

    @Autowired
    private IPmsProductCategoryService productCategoryService;

    @Autowired
    private IPmsCommentService pmsCommentService;
    @Autowired
    private RedisService redisService;

    @Resource
    private PmsProductAttributeMapper pmsProductAttributeMapper;


    @IgnoreAuth
    @PostMapping(value = "/product/queryProductList")
    @ApiOperation(value = "查询上线和审核过的商品列表")
    public Object queryProductList(@RequestBody PmsProduct productQueryParam) {
        productQueryParam.setPublishStatus(1);
        productQueryParam.setVerifyStatus(1);
        productQueryParam.setNewStatus(1);
        List<PmsProduct> list = pmsProductService.list(new QueryWrapper<>(productQueryParam));
        return new CommonResult().success(list);
    }

    @IgnoreAuth
    @PostMapping(value = "/product/queryListRecommand")
    @ApiOperation(value = "推荐商品列表")
    public Object queryProductListAndrecommandStatus(@RequestBody PmsProduct productQueryParam) {
        productQueryParam.setPublishStatus(1);
        productQueryParam.setVerifyStatus(1);
        productQueryParam.setRecommandStatus(1);
        return new CommonResult().success(pmsProductService.list(new QueryWrapper<>(productQueryParam)));
    }

    /**
     * 上架和已经审核过的商品
     * @param productQueryParam
     * @return
     */
    @IgnoreAuth
    @PostMapping(value = "/product/queryProductList1")
    @ResponseBody
    public Object queryProductList1(@RequestBody PmsProduct productQueryParam) {
        productQueryParam.setPublishStatus(1);
        productQueryParam.setVerifyStatus(1);
        return new CommonResult().success(pmsProductService.findProduct(productQueryParam));
    }

    /**
     * 分类和分类下的商品
     *
     * @return
     */
    @IgnoreAuth
    @ApiOperation(value = "分类和分类下的商品")
    @GetMapping("/getProductCategoryDto")
    public Object getProductCategoryDtoByPid() {
        List<PmsProductAttributeCategory> productAttributeCategoryList = productAttributeCategoryService.list(null);
        for (PmsProductAttributeCategory gt : productAttributeCategoryList) {
            PmsProduct productQueryParam = new PmsProduct();
            productQueryParam.setProductAttributeCategoryId(gt.getId());
            productQueryParam.setPublishStatus(1);
            productQueryParam.setVerifyStatus(1);
            gt.setGoodsList(pmsProductService.list(new QueryWrapper<>(productQueryParam).select(ConstansValue.sampleGoodsList)));
        }
        return new CommonResult().success(productAttributeCategoryList);
    }


    /**
     * 查询所有一级分类及子分类
     *
     * @return
     */
    @IgnoreAuth
    @ApiOperation(value = "查询所有一级分类及子分类")
    @GetMapping("/listWithChildren")
    public Object listWithChildren() {
        List<PmsProductCategoryWithChildrenItem> list = productCategoryService.listWithChildren();
        return new CommonResult().success(list);
    }


    @IgnoreAuth
    @PostMapping(value = "/product/queryProductDetail")
    @ApiOperation(value = "查询商品详情信息")
    @ResponseBody
    public Object queryProductDetail(@RequestBody PmsProduct pmsProduct) {
        PmsProductResult productResult = new PmsProductResult();
        //获取商品基础属性
        PmsProduct product = pmsProductService.getById(pmsProduct.getId());
        //获取商品其他属性值
        List<PmsProductAttr> attrList = pmsProductAttributeMapper.getProductAttrById(pmsProduct.getId(), 0);//获取规格数据
        //获取商品评价
        List<PmsComment> pmsCommentList = pmsCommentService.getByProductId(pmsProduct.getId());  //获取该商品的前五个评论
        productResult.setProduct(product);
        productResult.setPmsProductAttrList(attrList);
        productResult.setPmsComments(pmsCommentList);

//        UmsMember umsMember = memberService.getNewCurrentMember();//与用户关联再议
//        if (umsMember != null && umsMember.getId() != null && productResult != null) {
//            MemberProductCollection findCollection = productCollectionRepository.findByMemberIdAndProductId(
//                    umsMember.getId(), id);
//            if (findCollection != null) {
//                productResult.setIs_favorite(1);
//            } else {
//                productResult.setIs_favorite(2);
//            }
//        }
        return new CommonResult().success(productResult);
    }

    @IgnoreAuth
    @ApiOperation(value = "查询所有一级分类及子分类")
    @GetMapping(value = "/attr/list")
    public Object getList(@RequestParam(value = "cid", required = false, defaultValue = "0") Long cid,
                          @RequestParam(value = "type") Integer type,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        PmsProductAttribute q = new PmsProductAttribute();
        q.setType(type);
        q.setProductAttributeCategoryId(cid);
        IPage<PmsProductAttribute> productAttributeList = productAttributeService.page(new Page<>(pageSize, pageNum), new QueryWrapper<>(q));
        return new CommonResult().success(productAttributeList);
    }

    @IgnoreAuth
    @ApiOperation("获取某个商品的评价")
    @PostMapping(value = "/consult/list")
    @ResponseBody
    public Object list(@RequestBody PmsProduct productQueryParam) {
        PmsComment pmsComment = new PmsComment();
        pmsComment.setProductId(productQueryParam.getGoodsId());
        List<PmsComment> list = null;
        String consultJson = redisService.get(Rediskey.PmsProductConsult + pmsComment.getProductId());
        if (consultJson != null) {
            list = JsonUtils.jsonToList(consultJson, PmsComment.class);
        } else {
            list = pmsCommentService.list(new QueryWrapper<>(pmsComment));
            redisService.set(Rediskey.PmsProductConsult + productQueryParam.getGoodsId(), JsonUtils.objectToJson(list));
            redisService.expire(Rediskey.PmsProductConsult + productQueryParam.getGoodsId(), 24 * 60 * 60);
        }
        int goods = 0;
        int general = 0;
        int bad = 0;
        List<PmsComment> goodList = new ArrayList<>();
        List<PmsComment> badList = new ArrayList<>();
        List<PmsComment> generalList = new ArrayList<>();
        ConsultTypeCount count = new ConsultTypeCount();
        for (PmsComment consult : list) {
            if (consult.getStoreId() != null) {
                if (consult.getStar() == 1|| consult.getStar() ==2) {
                    bad++;
                    badList.add(consult);
                }
                if (consult.getStar() == 3) {
                    general++;
                    generalList.add(consult);
                }
                if (consult.getStar() == 4 ||consult.getStar() ==5) {
                    goods++;
                    goodList.add(consult);
                }
            }
        }
        count.setAll(goods + general + bad);
        count.setBad(bad);
        count.setGeneral(general);
        count.setGoods(goods);
        List<PmsComment> productConsults = pmsCommentService.list(new QueryWrapper<>(pmsComment));
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("goodList",goodList);
        objectMap.put("badList",badList);
        objectMap.put("generalList",generalList);
        objectMap.put("AllList", productConsults);
        objectMap.put("count", count);
        return new CommonResult().success(objectMap);
    }


    /**
     * 商品分类和分类下的商品
     * @return
     */
    @IgnoreAuth
    @GetMapping(value = "/product/selectProjectByCategory")
    @ApiOperation(value = "商品分类和分类下的商品")
    @ResponseBody
    public Object selectProjectByCategory() {
        List<PmsProductCategoryVo> productCategoryServiceFather = productCategoryService.findFather();
        return new CommonResult().success(productCategoryServiceFather);
    }


    /**
     * 分类和分类下的商品
     * @return
     */
    @IgnoreAuth
    @PostMapping(value = "/product/listfindByCategoryId")
    @ApiOperation(value = "分类和分类下的商品")
    @ResponseBody
    public Object listfindByCategoryId(@RequestBody PmsProduct pmsProduct){
        List<PmsProduct> pmsProducts = pmsProductService.listfindByCategoryId(pmsProduct.getProductCategoryId());
        List<PmsProduct> pmsProducts1 = new ArrayList<>();
        long currIdx = (pmsProduct.getPageNum() > 1 ? (pmsProduct.getPageNum() -1) * pmsProduct.getPageSize() : 0);
        for (long i = 0; i < pmsProduct.getPageSize() && i < pmsProducts.size() - currIdx; i++) {
            PmsProduct pmsProduct1 = pmsProducts.get((int) (currIdx + i));
            pmsProducts1.add(pmsProduct1);
        }
        return new CommonResult().success(pmsProducts1);
    }
}
