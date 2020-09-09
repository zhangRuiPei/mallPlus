package com.zrp.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrp.mallplus.pms.entity.PmsComment;

import java.util.List;

/**
 * <p>
 * 商品评价表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IPmsCommentService extends IService<PmsComment> {

    List<PmsComment> getByProductId(Long productId);

    PmsComment getCommentInfo(Long productId, Long orderNo);
}
