package com.zscat.mallplus.pms.vo;


import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductCategory;

import java.util.List;

/**
 */
public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {

    private List<PmsProductCategory> children;

    private List<PmsProduct> productList;

    public List<PmsProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<PmsProduct> productList) {
        this.productList = productList;
    }

    public List<PmsProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<PmsProductCategory> children) {
        this.children = children;
    }
}
