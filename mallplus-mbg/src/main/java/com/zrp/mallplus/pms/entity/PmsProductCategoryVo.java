package com.zrp.mallplus.pms.entity;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public class PmsProductCategoryVo extends PmsProductCategory {



    PmsProductCategory pmsProductCategory = new PmsProductCategory();

    private List<PmsProductCategoryVo> productCategoryVoList;

    private List<PmsProduct> productList;

//    public PmsProductCategory getPmsProductCategory() {
//        return pmsProductCategory;
//    }

    public void setPmsProductCategory(PmsProductCategory pmsProductCategory) {
        this.pmsProductCategory = pmsProductCategory;
    }

    public List<PmsProductCategoryVo> getProductCategoryVoList() {
        return productCategoryVoList;
    }

    public void setProductCategoryVoList(List<PmsProductCategoryVo> productCategoryVoList) {
        this.productCategoryVoList = productCategoryVoList;
    }

    public List<PmsProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<PmsProduct> productList) {
        this.productList = productList;
    }


    @Override
    public Long getId() {
        return pmsProductCategory.getId();
    }

    /**
     * 上机分类的编号：0表示一级分类
     */
    @Override
    public Long getParentId() {
        return pmsProductCategory.getParentId();
    }

    @Override
    public String getName() {
        return pmsProductCategory.getName();
    }

    /**
     * 分类级别：0->1级；1->2级
     */
    @Override
    public Integer getLevel() {
        return pmsProductCategory.getLevel();
    }



    /**
     * 是否显示在导航栏：0->不显示；1->显示
     */
    @Override
    public Integer getNavStatus() {
        return pmsProductCategory.getNavStatus();
    }

    /**
     * 显示状态：0->不显示；1->显示
     */
    @Override
    public Integer getShowStatus() {
        return pmsProductCategory.getShowStatus();
    }

    /**
     * 是否是首页分类0-->不是，1-->是
     */
    @Override
    public Integer getIndexStatus() {
        return pmsProductCategory.getIndexStatus();
    }

    @Override
    public Integer getSort() {
        return pmsProductCategory.getSort();
    }

    /**
     * 图标
     */
    @Override
    public String getIcon() {
        return pmsProductCategory.getIcon();
    }



    /**
     * 描述
     */
    @Override
    public String getDescription() {
        return pmsProductCategory.getDescription();
    }





    @Override
    public void setId(Long id) {
        pmsProductCategory.setId(id);
    }

    /**
     * 上机分类的编号：0表示一级分类
     * @param parentId
     */
    @Override
    public void setParentId(Long parentId) {
        pmsProductCategory.setParentId(parentId);
    }

    @Override
    public void setName(String name) {
        pmsProductCategory.setName(name);
    }

    /**
     * 分类级别：0->1级；1->2级
     * @param level
     */
    @Override
    public void setLevel(Integer level) {
        pmsProductCategory.setLevel(level);
    }



    /**
     * 是否显示在导航栏：0->不显示；1->显示
     * @param navStatus
     */
    @Override
    public void setNavStatus(Integer navStatus) {
        pmsProductCategory.setNavStatus(navStatus);
    }

    /**
     * 显示状态：0->不显示；1->显示
     * @param showStatus
     */
    @Override
    public void setShowStatus(Integer showStatus) {
        pmsProductCategory.setShowStatus(showStatus);
    }

    /**
     * 是否是首页分类0-->不是，1-->是
     * @param indexStatus
     */
    @Override
    public void setIndexStatus(Integer indexStatus) {
        pmsProductCategory.setIndexStatus(indexStatus);
    }

    @Override
    public void setSort(Integer sort) {
        pmsProductCategory.setSort(sort);
    }

    /**
     * 图标
     * @param icon
     */
    @Override
    public void setIcon(String icon) {
        pmsProductCategory.setIcon(icon);
    }



    /**
     * 描述
     * @param description
     */
    @Override
    public void setDescription(String description) {
        pmsProductCategory.setDescription(description);
    }



}
