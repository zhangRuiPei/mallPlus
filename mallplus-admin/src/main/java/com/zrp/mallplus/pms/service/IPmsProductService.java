package com.zrp.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.pms.entity.PmsProduct;
import com.zrp.mallplus.pms.entity.PmsProductVertifyRecord;
import com.zrp.mallplus.pms.entity.PmsSkuStock;
import com.zrp.mallplus.pms.vo.PmsProductParam;
import com.zrp.mallplus.pms.vo.PmsProductResult;
import com.zrp.mallplus.vo.PecArr;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务类
 * @Author: 乔黎莱莱
 * @Version: 1.0.0
 * @time Auto
 * @remark:
 */
public interface IPmsProductService extends IService<PmsProduct> {
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);


    Integer addList(String JsonProduct);



    /**
     * 根据商品编号获取更新信息
     */
    PmsProductResult getUpdateInfo(Long id);

    /**
     * 更新商品
     */
    @Transactional
    int update(Long id, PmsProductParam productParam);


    int updatePmsPro(PmsProduct pmsProduct);

    /**
     * 批量修改审核状态
     *
     * @param ids          产品id
     * @param verifyStatus 审核状态
     * @param detail       审核详情
     */
    @Transactional
    int updateVerifyStatus(Long ids, Integer verifyStatus, String detail);

    /**
     * 批量修改商品上架状态
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus,Integer storeId);

    /**
     * 批量修改商品推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 批量修改新品状态
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    int updateisFenxiao(List<Long> ids, Integer newStatus);

    /**
     * 批量删除商品
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    /**
     * 根据商品名称或者货号模糊查询
     */
    List<PmsProduct> list(String keyword);

    List<PmsProductVertifyRecord> getProductVertifyRecord(Long id);


     Long addList(PmsProduct pmsProduct, List<PmsSkuStock> skuStockList, List<PecArr> pec_arr);

    PmsProduct getProductById(Long id);

    Map<String,Object> getList(PmsProduct entity, Integer pageNum, Integer pageSize);

    void removeMultiple(List<Long> id);

}
