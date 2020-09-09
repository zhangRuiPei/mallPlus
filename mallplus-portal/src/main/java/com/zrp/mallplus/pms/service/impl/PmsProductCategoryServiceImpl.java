package com.zrp.mallplus.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrp.mallplus.pms.entity.*;
import com.zrp.mallplus.pms.mapper.PmsProductCategoryMapper;
import com.zrp.mallplus.pms.mapper.PmsProductMapper;
import com.zrp.mallplus.pms.service.IPmsProductCategoryService;
import com.zrp.mallplus.util.Objectx;
import org.springframework.stereotype.Service;
import com.zrp.mallplus.pms.vo.PmsProductCategoryWithChildrenItem;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements IPmsProductCategoryService {

    @Resource
    private PmsProductCategoryMapper categoryMapper;

    @Resource
    private PmsProductMapper pmsProductMapper;

    /**
     * 分类以及子类
     * @return
     */
    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return categoryMapper.listWithChildren();
    }





    @Override
    public List<PmsProductCategoryVo> findFather(){
        List<PmsProductCategory> father = categoryMapper.findFather();
        List<PmsProductCategoryVo> productCategoryVoList = new ArrayList<>();
        for (PmsProductCategory pmsProductCategory1 :father){

            PmsProductCategoryVo pmsProductCategoryVo = new PmsProductCategoryVo();
            pmsProductCategoryVo.setPmsProductCategory(pmsProductCategory1);
            productCategoryVoList.add(pmsProductCategoryVo);
            List<PmsProduct> pmsProducts = pmsProductMapper.listfindByCategoryId(pmsProductCategory1.getId());
            List<PmsProduct> pmsProducts1 = new ArrayList<>();
            for (PmsProduct pmsProduct:pmsProducts){
                pmsProducts1.add(pmsProduct);
                pmsProductCategoryVo.setProductList(pmsProducts1);
            }
            findSub1(pmsProductCategoryVo);
        }
        return productCategoryVoList;
    }

    private void findSub1(PmsProductCategoryVo pmsProductCategory) {
        List<PmsProductCategory> fatherId = categoryMapper.findChrildForFatherId(pmsProductCategory.getId());
        List<PmsProductCategoryVo> productCategoryVoList = new ArrayList<>();
        if (Objectx.isNotNull(fatherId)){
            for (PmsProductCategory pmsProductCategory1:fatherId){
                PmsProductCategoryVo pmsProductCategory2 = new PmsProductCategoryVo();
                pmsProductCategory2.setPmsProductCategory(pmsProductCategory1);
                productCategoryVoList.add(pmsProductCategory2);
                List<PmsProduct> pmsProducts = pmsProductMapper.listfindByCategoryId(pmsProductCategory1.getId());
                List<PmsProduct> pmsProducts1 = new ArrayList<>();
                for (PmsProduct pmsProduct:pmsProducts){
                    pmsProducts1.add(pmsProduct);
                    pmsProductCategory2.setProductList(pmsProducts1);
                }
                findSub(pmsProductCategory2);
            }
        }else {
            return;
        }
        pmsProductCategory.setProductCategoryVoList(productCategoryVoList);
    }


    private void findSub(PmsProductCategoryVo pmsProductCategory){
        List<PmsProductCategory> fatherId = categoryMapper.findChrildForFatherId(pmsProductCategory.getId());
        List<PmsProductCategoryVo> productCategoryVoList = new ArrayList<>();
        if (Objectx.isNotNull(fatherId)) {
            for (PmsProductCategory staffDept:fatherId){
                PmsProductCategoryVo dto2  = new PmsProductCategoryVo();
                dto2.setPmsProductCategory(staffDept);
                productCategoryVoList.add(dto2);
                findSub(dto2);
            }
        }else {
            return ;
        }
        pmsProductCategory.setProductCategoryVoList(productCategoryVoList);
    }


}
