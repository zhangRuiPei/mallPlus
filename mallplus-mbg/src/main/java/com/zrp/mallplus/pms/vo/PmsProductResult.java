package com.zrp.mallplus.pms.vo;

import com.zrp.mallplus.pms.entity.PmsComment;
import com.zrp.mallplus.pms.entity.PmsProduct;
import lombok.Data;

import java.util.List;

/**
 * 查询单个产品进行修改时返回的结果
 */
@Data
public class PmsProductResult extends PmsProductParam {
    //商品所选分类的父id
    private Long cateParentId;
    private int is_favorite;// 1 已收藏 2 未收藏
    private List<PmsProductAttr> pmsProductAttrList;
    private PmsProduct product;
    private List<PmsComment> pmsComments;


}
