package com.zscat.mallplus.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductCategory;
import com.zscat.mallplus.pms.entity.PmsProductCategoryAttributeRelation;
import com.zscat.mallplus.pms.mapper.PmsProductCategoryAttributeRelationMapper;
import com.zscat.mallplus.pms.mapper.PmsProductCategoryMapper;
import com.zscat.mallplus.pms.mapper.PmsProductMapper;
import com.zscat.mallplus.pms.service.IPmsProductCategoryAttributeRelationService;
import com.zscat.mallplus.pms.service.IPmsProductCategoryService;
import com.zscat.mallplus.pms.vo.PmsProductCategoryWithChildrenItem;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 产品分类 服务实现类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements IPmsProductCategoryService {

    @Resource
    private PmsProductCategoryMapper categoryMapper;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private IPmsProductCategoryAttributeRelationService pmsProductCategoryAttributeRelationService;
    @Resource
    private PmsProductCategoryAttributeRelationMapper productCategoryAttributeRelationMapper;

    @Resource
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return categoryMapper.listWithChildren();
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setNavStatus(navStatus);
        return categoryMapper.update(productCategory, new QueryWrapper<PmsProductCategory>().in("id", ids));
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setShowStatus(showStatus);
        return categoryMapper.update(productCategory, new QueryWrapper<PmsProductCategory>().in("id", ids));
    }

    @Override
    public int updateIndexStatus(List<Long> ids, Integer indexStatus) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setIndexStatus(indexStatus);
        return categoryMapper.update(productCategory, new QueryWrapper<PmsProductCategory>().in("id", ids));
    }


    @Override
    public boolean updateAnd(PmsProductCategory entity) {
        PmsProductCategory productCategory = new PmsProductCategory();
        setCategoryLevel(entity);
        //更新商品分类时要更新商品中的名称
        PmsProduct product = new PmsProduct();
        product.setProductCategoryName(entity.getName());

        productMapper.update(product, new QueryWrapper<PmsProduct>().eq("product_category_id", entity.getId()));
        //同时更新筛选属性的信息
        if (!CollectionUtils.isEmpty(entity.getProductAttributeIdList())) {

            productCategoryAttributeRelationMapper.delete(new QueryWrapper<>(new PmsProductCategoryAttributeRelation()).eq("product_category_id", entity.getId()));
            insertRelationList(entity.getId(), entity.getProductAttributeIdList());
        } else {
            productCategoryAttributeRelationMapper.delete(new QueryWrapper<>(new PmsProductCategoryAttributeRelation()).eq("product_category_id", entity.getId()));

        }
        categoryMapper.updateById(entity);
        return true;
    }

    @Override
    public boolean saveAnd(PmsProductCategory entity) {
        PmsProductCategory productCategory = new PmsProductCategory();
        productCategory.setProductCount(0);
        BeanUtils.copyProperties(entity, productCategory);
        //没有父分类时为一级分类
        setCategoryLevel(productCategory);
        int count = categoryMapper.insert(productCategory);
        //创建筛选属性关联
        List<Long> productAttributeIdList = entity.getProductAttributeIdList();
        if (!CollectionUtils.isEmpty(productAttributeIdList)) {
            insertRelationList(productCategory.getId(), productAttributeIdList);
        }
        return true;
    }



    /**
     * 批量插入商品分类与筛选属性关系表
     *
     * @param productCategoryId      商品分类id
     * @param productAttributeIdList 相关商品筛选属性id集合
     */
    private void insertRelationList(Long productCategoryId, List<Long> productAttributeIdList) {
        List<PmsProductCategoryAttributeRelation> relationList = new ArrayList<>();
        for (Long productAttrId : productAttributeIdList) {
            PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
            relation.setProductAttributeId(productAttrId);
            relation.setProductCategoryId(productCategoryId);
            relationList.add(relation);
        }
        pmsProductCategoryAttributeRelationService.saveBatch(relationList);
    }

    /**
     * 根据分类的parentId设置分类的level
     */
    private void setCategoryLevel(PmsProductCategory productCategory) {
        //没有父分类时为一级分类
        if (productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            //有父分类时选择根据父分类level设置
            PmsProductCategory parentCategory = categoryMapper.selectById(productCategory.getParentId());
            if (parentCategory != null) {
                productCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                productCategory.setLevel(0);
            }
        }
    }



    @Override
    public List<Map> findAllMenu() {
        List<PmsProductCategory> menuList = findAll();//查询全部菜单列表
        return findMenuListByParentId(menuList,0);//一级菜单列表
    }
    @Override
    public List<Map> findAllFen() {
        List<PmsProductCategory> menuList = findAll();//查询全部菜单列表
        return findAllFenByParentId(menuList,0);//一级菜单列表
    }


    public List<PmsProductCategory> findAll() {
        List<PmsProductCategory> menuAll = pmsProductCategoryMapper.findMenuAll();
        return menuAll;
    }
    /**
     * 查询下级菜单ID
     * @param menuList
     * @param parentId
     * @return
     */
    private List<Map> findMenuListByParentId(List<PmsProductCategory> menuList,Integer parentId){
        List<Map> mapList=new ArrayList<>();
        for(PmsProductCategory menu:menuList){ //循环一级菜单
            if(menu.getParentId().equals(parentId.longValue())){
                Map map=new HashMap();
                map.put("ReceptionMenuModuleId",menu.getId());//菜单模块Id
                map.put("ReceptionMenuName",menu.getName());//菜单名称
                map.put("children",findMenuListByParentId(menuList,menu.getId().intValue()));
                mapList.add(map);
            }
        }
        return mapList;
    }
    /**
     * 查询下级菜单ID
     * @param menuList
     * @param parentId
     * @return
     */
    private List<Map> findAllFenByParentId(List<PmsProductCategory> menuList,Integer parentId){
        List<Map> mapList=new ArrayList<>();
        for(PmsProductCategory menu:menuList){ //循环一级菜单
            if(menu.getParentId().equals(parentId.longValue())){
                Map map=new HashMap();
                map.put("value",menu.getId());//菜单模块Id
                map.put("label",menu.getName());//菜单名称
                map.put("children",findMenuListByParentId(menuList,menu.getId().intValue()));
                mapList.add(map);
            }
        }
        return mapList;
    }
}
