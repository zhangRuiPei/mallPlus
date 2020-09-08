package com.zscat.mallplus.pms.vo;

import com.zscat.mallplus.pms.entity.PmsProduct;

import java.util.List;

/**
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public class PmsProductList extends PmsProduct {

    private List<PmsProduct> pmsProducts;

    private String pmsCategory;

    public List<PmsProduct> getPmsProducts() {
        return pmsProducts;
    }

    public void setPmsProducts(List<PmsProduct> pmsProducts) {
        this.pmsProducts = pmsProducts;
    }

    public String getPmsCategory() {
        return pmsCategory;
    }

    public void setPmsCategory(String pmsCategory) {
        this.pmsCategory = pmsCategory;
    }
}
